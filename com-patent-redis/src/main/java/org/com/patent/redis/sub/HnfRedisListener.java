package org.com.patent.redis.sub;

public interface HnfRedisListener {

	  public void onMessage(String topic, String message);
}
