package org.com.patent.redis.pub;

public interface JedisPulisher {

	public Long publish(String topic, String message);
}

