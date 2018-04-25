package org.com.patent.redis;


import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.com.patent.redis.pub.JedisPulisher;
import org.com.patent.redis.pub.SimpleJedisPublisher;
import org.com.patent.redis.sub.HnfRedisListener;
import org.com.patent.redis.sub.JedisSubListener;
import org.com.patent.redis.template.JedisTemplate;
import org.com.patent.redis.template.SimpleJedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

public class JedisHolder {
  private static final Logger LOGGER = LoggerFactory.getLogger(JedisHolder.class);
  private ShardedJedisPool jedisPool;
  private static JedisHolder instance = new JedisHolder();
  private AtomicBoolean status = new AtomicBoolean(false);
  private List<JedisShardInfo> shards = new LinkedList<JedisShardInfo>();
  private JedisTemplate template;
  private JedisPulisher publisher;
  private Jedis pubSession;
  private Jedis subSession;
  private boolean pubSubModle;
  private String[] topics;
  private JedisSubListener listener;

  private JedisHolder() {

  }

  public static JedisHolder custom() {
    return instance;
  }

  public void setAddressInfo(List<JedisAddress> address) {
    Iterator<JedisAddress> it = address.iterator();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("connected redis info :{}", address);
    }
    while (it.hasNext()) {
      JedisAddress adrr = it.next();
      it.remove();
      shards.add(new JedisShardInfo(adrr.getHostName(), adrr.getPort()));
    }

  }

  public void setPubSubModle(boolean pubSubModle) {
    this.pubSubModle = pubSubModle;
  }

  public void setTopic(String topic) {
    this.topics = topic.split(",");
  }

  public void setTemplate(JedisTemplate template) {
    this.template = template;
  }

  public void init() {
    if (status.compareAndSet(false, true)) {
      GenericObjectPoolConfig config = new GenericObjectPoolConfig();
      // 最大链接数
      config.setMaxTotal(100);
      // 获取链接等待时间
      config.setMaxWaitMillis(10 * 1000);
      // 空闲时检查链接的有效性
      config.setTestWhileIdle(true);
      // 半小时启动一次逐出空闲链接的巡检
      config.setTimeBetweenEvictionRunsMillis(30 * 60 * 1000);
      jedisPool = new ShardedJedisPool(config, shards);
      if (this.template != null) {
        this.template.setPool(jedisPool);
      }
      if (pubSubModle) {
        // 选择第一个地址作为发布订阅器
        pubSession = new Jedis(shards.get(0));
        publisher = new SimpleJedisPublisher(pubSession);
        if (topics == null) {
          topics = new String[] {"async_http_channel"};
        }
        subSession = new Jedis(shards.get(0));
        listener = new JedisSubListener(subSession, topics);
        listener.start();
      }
    }
  }

  public void dispose() {
    if (status.compareAndSet(true, false)) {
      jedisPool.close();
      if (pubSubModle) {
        listener.releaseSub();
        pubSession.close();
      }
    }
  }

  public JedisTemplate T() {
    return this.template;
  }

  public JedisPulisher P() throws IOException {
    if (!pubSubModle) {
      throw new IOException("please enable pubsub model!");
    }
    return this.publisher;
  }

  public void registerSubListener(String topic, HnfRedisListener redisListener) {
    if (this.listener != null) {
      this.listener.addListener(topic, redisListener);
    }
  }

  public void deletePattern(String pattern) {
    if (this.shards == null) {
      return;
    }
    for (JedisShardInfo info : this.shards) {
      Jedis jedis = null;
      try {
        jedis = new Jedis(info);
        Set<String> keys = jedis.keys(pattern);
        if (keys != null && !keys.isEmpty()) {
          jedis.del(keys.toArray(new String[keys.size()]));
        }
      } catch (Exception e) {
        LOGGER.error("error:", e);
      } finally {
        if (jedis != null) {
          jedis.close();
        }
      }
    }
  }

  public static void main(String[] args) {
    List<JedisAddress> list = new LinkedList<JedisAddress>();
    list.add(new JedisAddress("172.18.5.55", 6379));
    JedisHolder.custom().setAddressInfo(list);
    JedisHolder.custom().setTemplate(new SimpleJedisTemplate());
    JedisHolder.custom().setPubSubModle(false);
    JedisHolder.custom().init();
    String key = "abcdefghijklmn";
    // use lock example
    if (JedisHolder.custom().T().applyLock(key, -1)) {
      try {
        // TODO you business;
        // ...
        System.out.println("goog");
      } finally {
        // release lock
        JedisHolder.custom().T().releaseLock(key);
      }
    } else {
      // some one use the lock
    }
  }
}
