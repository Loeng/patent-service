package org.com.patent.redis.pub;

import redis.clients.jedis.Jedis;

public class SimpleJedisPublisher implements JedisPulisher{

	 private Jedis jedis;

	  public SimpleJedisPublisher(Jedis jedis) {
	    this.jedis = jedis;
	  }

	  @Override
	  public Long publish(String topic, String message) {
	    return jedis.publish(topic, message);
	  }
}
