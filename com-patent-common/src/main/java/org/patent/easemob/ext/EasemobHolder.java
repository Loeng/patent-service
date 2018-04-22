package org.patent.easemob.ext;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.easemob.server.api.impl.EasemobSendMessage;
import io.watch.context.LocationIndexHolder;

public class EasemobHolder extends Thread {
  private static final Logger LOGGER = LoggerFactory.getLogger(EasemobHolder.class);
  private static EasemobHolder instance = new EasemobHolder();
  private HojyEasemobSender sender;
  private FilterChain filterChain;
  private ConcurrentLinkedQueue<HojyEasemobMsg> queue;
  private AtomicBoolean status = new AtomicBoolean(false);

  private EasemobHolder() {
    this.queue = new ConcurrentLinkedQueue<HojyEasemobMsg>();
    this.filterChain = new FilterChain();
    this.setName("easemon_msg_holder");
  }

  public static EasemobHolder custom() {
    return instance;
  }
  
  public void setSenderListener(HojyEasemobSenderListener listener){
	  if(listener!=null){
		  this.sender = new HojyEasemobSender(listener);
	  }
  }

  public void init() {
    if (status.compareAndSet(false, true)) {
      if(this.sender == null){
    	  this.sender = new HojyEasemobSender();
      }
      this.start();
      LOGGER.info("start easemon_msg_holder for checking message");
    }
  }

  public void dispose() {
    if (status.compareAndSet(true, false)) {
      try {
        this.join();
      } catch (InterruptedException e) {
        LOGGER.error("error :", e);
      }
      LOGGER.info("stop easemon_msg_holder finished");
    }
  }

  public EasemobSendMessage getSender() {
    return sender;
  }

  public void addMsgFilter(HojyMsgFilter filter) {
    this.filterChain.addFilter(filter);
  }

  public void setFilters(List<HojyMsgFilter> filters) {
    if (filters == null || filters.size() == 0) {
      return;
    }
    for (HojyMsgFilter f : filters) {
      addMsgFilter(f);
    }
  }

  protected Object emit(HojyEasemobMsg msg) {
    this.filterChain.beforeFilter(msg);
    Object result = this.sender.aroundFilterSend(msg);
    this.filterChain.afterFilter(result, msg);
    return result;
  }

  protected void emitAsync(HojyEasemobMsg msg) {
    this.queue.add(msg);
    waiteUp();

  }

  public void run() {
    while (status.get()) {
      try {
        if (this.queue.isEmpty()) {
          waiteFor(1000);
          continue;
        }
        processMsg();
      } catch (Exception e) {
        LOGGER.error("error:", e);
      }
    }
    
    try {
      if (!this.queue.isEmpty()) {
        HojyEasemobMsg msg = null;
        while ((msg = this.queue.poll()) != null) {
          emit(msg);
        }
      }
    } catch (Exception e) {
      LOGGER.error("error:", e);
    }
  }

  private synchronized void waiteUp() {
    notifyAll();
  }

  private synchronized void waiteFor(long timeout) throws InterruptedException {
    if (timeout == -1) {
      this.wait();
    } else {
      this.wait(timeout);
    }
  }

  private void processMsg() {
    HojyEasemobMsg msg = null;
    while ((msg = this.queue.poll()) != null) {
      procss(msg);
    }
  }

  private void procss(final HojyEasemobMsg msg) {
    LocationIndexHolder.custom().execute(new Runnable() {
      @Override
      public void run() {
        emit(msg);
      }
    });
  }
}

