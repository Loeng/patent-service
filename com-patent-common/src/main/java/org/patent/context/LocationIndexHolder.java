package org.patent.context;


import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//import io.watch.entity.LocationEntity;
import io.watch.entity.LocationIndexEntity;
import io.watch.entity.LocationMsg;
import io.watch.entity.NotifyCustomerMsg;
import io.watch.entity.SysLogEntity;
import io.watch.service.HistoryMessageService;
import io.watch.service.LocationIndexService;
import io.watch.service.SysLogService;
import io.watch.service.notify.CustomerHandler;
import io.watch.utils.SpringContextUtils;

/**
 * 定位信息分表控制类
 */
public class LocationIndexHolder {

  private static final Logger logger = LoggerFactory.getLogger(LocationIndexHolder.class);

  private static LocationIndexHolder instance = new LocationIndexHolder();
  // 线程状态
  private AtomicBoolean status = new AtomicBoolean(false);

  private IndexWorker worker;

  private NotifyWorker notify;

  @Autowired
  private LocationIndexService locationIndexService;
  
  @Autowired
  private HistoryMessageService historyMessageService;

  // 约定10W用户
  private List<Point> sections;

  private Map<String, LocationIndexEntity> indexCache = new ConcurrentHashMap<String, LocationIndexEntity>();

  private boolean async = true;

  private ExecutorService nocacheThreadPool = null;

  private LocationIndexHolder() {

  }

  public static LocationIndexHolder custom() {
    return instance;
  }

  public void init() {
    if (status.compareAndSet(false, true)) {
      sections = new LinkedList<Point>();
      for (int i = 0, j = 0; i < 10; i++) {
        int x = j;
        j += 9999;
        sections.add(new Point(x, j));
        j++;
      }
      nocacheThreadPool = Executors.newCachedThreadPool(new ThreadFactory() {
        private AtomicInteger index = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
          return new Thread(r, String.format("run_nofity_t_%d", index.getAndIncrement()));
        }
      });
      worker = new IndexWorker();
      worker.start();
      notify = new NotifyWorker();
      notify.start();
    }
  }

  public void dispose() {
    if (status.compareAndSet(true, false)) {
      if (nocacheThreadPool != null) {
        nocacheThreadPool.shutdown();
        nocacheThreadPool = null;
      }
    }
  }

  public void execute(Runnable r) {
    if (nocacheThreadPool != null) {
      nocacheThreadPool.execute(r);
    }
  }

  public List<Point> getSection() {
    return this.sections;
  }

  public void setAsync(boolean async) {
    this.async = async;
  }

  /**
   * 查找索引
   * 
   * @param deviceId
   * @return
   */
  public LocationIndexEntity findIndex(Long deviceId) {
    for (Map.Entry<String, LocationIndexEntity> entry : indexCache.entrySet()) {
      LocationIndexEntity entity = entry.getValue();
      if (deviceId >= entity.getSectionStart() && deviceId <= entity.getSectionEnd()) {
        return entity;
      }
    }
    return null;
  }

  public LocationIndexEntity loadIndex(String hashKey) {
    return indexCache.get(hashKey);
  }

  public void saveIndex(String hashKey, LocationIndexEntity index) {
    this.indexCache.put(hashKey, index);
  }

  public void addMsg(Object msg) {
    if (async) {
      this.notify.addMsg(msg);
    } else {
      this.notify.notifyAtOnce(msg);
    }
  }

  public void addSysLog(SysLogEntity log) {
    this.notify.addSysLog(log);
  }

  public void addLocation(LocationEntity entity) {
    this.worker.addLocationEntity(entity);
  }

  public void addManyLocations(List<LocationEntity> entity) {
    this.worker.addManyLocationEntitys(entity);
  }

  /**
   * 生成hash键值
   * 
   * @param p
   * @return
   */
  public String makeHashKey(Point p) {
    int code = new String(p.x + "-" + p.y).hashCode();
    return Integer.toHexString(code).toUpperCase();
  }

  // 将表名称和设备id条件捆绑好
  public List<String[]> translateTableName(String deviceIds) {
    List<String[]> tableNames = new LinkedList<String[]>();
    if (StringUtils.isNotBlank(deviceIds)) {
      // 有索引根据索引查找
      Map<String, List<Long>> mmap = new HashMap<String, List<Long>>();
      String ids[] = deviceIds.split(",");
      for (int i = 0; i < ids.length; i++) {
        Long id = Long.valueOf(ids[i]);
        LocationIndexEntity index = findIndex(id);
        if (index != null) {
          List<Long> idx = mmap.get(index.getLbsTableName());
          if (idx == null) {
            idx = new LinkedList<Long>();
            mmap.put(index.getLbsTableName(), idx);
          }
          idx.add(id);
        }
      }
      Iterator<Map.Entry<String, List<Long>>> it = mmap.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry<String, List<Long>> ent = it.next();
        it.remove();
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Long id : ent.getValue()) {
          sb.append(id);
          sb.append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), ")");
        tableNames.add(new String[] {ent.getKey(), sb.toString()});
        sb.delete(0, sb.length());
      }

    } else {
      // 没有索引查找所有的表很分区
      for (Map.Entry<String, LocationIndexEntity> entry : indexCache.entrySet()) {
        tableNames.add(new String[] {entry.getValue().getLbsTableName(), ""});
      }
    }
    return tableNames;
  }

  class IndexWorker extends Thread {
    // 定时刷新索引标记
    private long refreshTime;
    // 批量入库实体集合
    private ConcurrentLinkedQueue<LocationEntity> locationCaches;

    public IndexWorker() {
      locationCaches = new ConcurrentLinkedQueue<LocationEntity>();

    }

    void addLocationEntity(LocationEntity entity) {
      this.locationCaches.add(entity);
      this.waiteUp();
    }

    void addManyLocationEntitys(Collection<LocationEntity> cc) {
      this.locationCaches.addAll(cc);
      this.waiteUp();
    }

    void addBatch() {
      Map<String, List<LocationEntity>> map = new HashMap<String, List<LocationEntity>>();
      LocationEntity entity = null;
      List<LocationEntity> notIndex = new LinkedList<LocationEntity>();
      while ((entity = locationCaches.poll()) != null) {
        LocationIndexEntity index = findIndex(entity.getDeviceId());
        if (index != null) {
          // 分表插入
          List<LocationEntity> list = map.get(index.getLbsTableName());
          if (list == null) {
            list = new LinkedList<LocationEntity>();
            map.put(index.getLbsTableName(), list);
          }
          list.add(entity);
        } else {
          notIndex.add(entity);
        }
      }
      Iterator<Map.Entry<String, List<LocationEntity>>> it = map.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry<String, List<LocationEntity>> entry = it.next();
        it.remove();
        locationIndexService.saveAll(entry.getKey(), entry.getValue());
      }
      if (!notIndex.isEmpty()) {
        Iterator<LocationEntity> itt = notIndex.iterator();
        while (itt.hasNext()) {
          LocationEntity e = itt.next();
          itt.remove();
          LocationIndexEntity index = locationIndexService.creatIndex(e.getDeviceId());
          if (index != null) {
            locationCaches.add(e);
          }
        }
      }
    }

    void refresh() {
      if (System.currentTimeMillis() > (this.refreshTime + 60000)) {
        List<LocationIndexEntity> indexs = locationIndexService.selectAll();
        if (indexs != null && indexs.size() != 0) {
          Iterator<LocationIndexEntity> it = indexs.iterator();
          while (it.hasNext()) {
            LocationIndexEntity entity = it.next();
            it.remove();
            indexCache.put(entity.getKeyHash(), entity);
          }
        }
        this.refreshTime = System.currentTimeMillis();
      }
    }

    public void run() {
      while (status.get()) {
        try {
          refresh();
          if (locationCaches.isEmpty()) {
            waiteFor(1000);
            continue;
          }
          addBatch();
        } catch (Exception e) {
          logger.error("error:", e);
        }
      }
    }

    public synchronized void waiteUp() {
      this.notifyAll();
    }

    public synchronized void waiteFor(long timeout) throws InterruptedException {
      if (timeout == -1) {
        this.wait();
      } else {
        this.wait(timeout);
      }
    }

  }

  /**
   * 消息通知进程类实例化
   * 
   * @author hojy-liuwf
   *
   */
  class NotifyWorker extends Thread {
    private ConcurrentLinkedQueue<Object> notifyQueue;
    private ConcurrentLinkedQueue<SysLogEntity> logsQueue;

    /**
     * 实例化消息通知进程
     */
    public NotifyWorker() {
      notifyQueue = new ConcurrentLinkedQueue<Object>();
      logsQueue = new ConcurrentLinkedQueue<SysLogEntity>();
    }

    /**
     * 清空通知消息
     */
    void flushMsg() {
      Object msg = null;
      while ((msg = notifyQueue.poll()) != null) {
        notifyAtOnce(msg);
      }
    }

    /**
     * 清空日志
     */
    void flushSysLog() {
      if (!logsQueue.isEmpty()) {
        List<SysLogEntity> logs = new LinkedList<SysLogEntity>();
        SysLogEntity log = null;
        while ((log = logsQueue.poll()) != null) {
          logs.add(log);
        }
        SysLogService logServoce = SpringContextUtils.getBean("sysLogService", SysLogService.class);
        logServoce.saveBatch(logs);
        logs.clear();
        logs = null;
      }
    }

    /**
     * 添加通知消息
     * 
     * @param msg
     */
    void addMsg(Object msg) {
      notifyQueue.add(msg);
      this.waiteUp();
    }

    /**
     * 添加日志
     * 
     * @param log
     */
    void addSysLog(SysLogEntity log) {
      logsQueue.add(log);
      this.waiteUp();
    }

    /**
     * 消息通知发送
     * 
     * @param msg
     */
    void notifyAtOnce(Object msg) {
      if (msg instanceof LocationMsg) {
        locationIndexService.notifyMsg((LocationMsg) msg);
      } else if (msg instanceof NotifyCustomerMsg) {
        NotifyCustomerMsg notify = (NotifyCustomerMsg) msg;
        CustomerHandler handler = (CustomerHandler) SpringContextUtils.getBean(notify.getHandlerName());
        if (handler != null) {
          handler.doNotify(notify);
        }
      }
    }

    /**
     * 消息通知唤醒
     */
    public synchronized void waiteUp() {
      this.notifyAll();
    }

    /**
     * 消息通知等待
     * 
     * @param timeout
     * @throws InterruptedException
     */
    public synchronized void waiteFor(long timeout) throws InterruptedException {
      if (timeout == -1) {
        this.wait();
      } else {
        this.wait(timeout);
      }
    }

    /**
     * 消息通知进程启动
     */
    public void run() {
      while (status.get()) {
        try {
          if (notifyQueue.isEmpty() && logsQueue.isEmpty()) {
            this.waiteFor(1000);
            continue;
          }
          flushMsg();
          flushSysLog();
        } catch (Exception e) {
          logger.error("error:", e);
        }
      }
    }
  }

}
