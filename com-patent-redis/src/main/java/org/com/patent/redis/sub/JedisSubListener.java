package org.com.patent.redis.sub;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class JedisSubListener extends Thread {
  private static final Logger LOGGER = LoggerFactory.getLogger(JedisSubListener.class);
  private Jedis jedis;
  private String[] topics;
  private JedisPubSub pubSub;
  private Map<String, HnfRedisListener> listeners;
  private ExecutorService service;

  public JedisSubListener(Jedis jedis, String[] topics) {
    this.jedis = jedis;
    this.topics = topics;
    this.pubSub = new HojyPubSub();
    this.listeners = new HashMap<String, HnfRedisListener>();
    service = Executors.newFixedThreadPool(20, new ThreadFactory() {
      private AtomicInteger index = new AtomicInteger(0);

      @Override
      public Thread newThread(Runnable r) {
        return new Thread(r, String.format("reds_sub_h_%d", index.getAndIncrement()));
      }
    });
  }

  public void run() {
    try {
      jedis.subscribe(pubSub, topics);
    } catch (Exception e) {
      LOGGER.error("error", e);
    }
  }

  public void addListener(String topic, HnfRedisListener listener) {
    this.listeners.put(topic, listener);
  }

  public void releaseSub() {
    this.pubSub.unsubscribe();
    this.service.shutdown();
  }

  class HojyPubSub extends JedisPubSub {
    @Override
    public void onMessage(final String channel, final String message) {
      service.execute(new Runnable() {
        @Override
        public void run() {
          HnfRedisListener process = listeners.get(channel);
          if (process != null) {
            process.onMessage(channel, message);
          } else {
            LOGGER.error("not slove topic :[{}] , message:[{}]", new Object[] {channel, message});
          }
        }
      });
    }
  }
}
