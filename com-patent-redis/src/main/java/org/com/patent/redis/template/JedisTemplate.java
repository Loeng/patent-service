package org.com.patent.redis.template;


import redis.clients.jedis.ShardedJedisPool;

public interface JedisTemplate {
  public void setPool(ShardedJedisPool pool);

  /**
   * 
   * @param key
   * @return
   */
  public String getString(String key);

  /**
   * 
   * @param key 键
   * @param value 值
   * @param exp 超时时间(unit of second)
   * @return
   */
  public String setString(String key, String value, int exp);

  public String setString(String key, String value);

  public Long delString(String key);

  public Object getObject(String key);

  public String setObject(String key, Object value);

  /**
   * 
   * @param key 键
   * @param value 值
   * @param exp 超时时间(unit of second)
   * @return
   */
  public String setObject(String key, Object value, int exp);

  public long delObject(String key);

  public long incr(String key);

  /**
   * 根据一个键去申请一个并发锁
   * 
   * @param key
   * @return
   */
  public boolean applyLock(String key);

  /**
   * 根据一个键去申请一个并发锁
   * 
   * @param key
   * @param timeout 等待时间(unit of ms)
   * @return
   */
  public boolean applyLock(String key, int timeout);

  /**
   * 释放申请的锁
   * 
   * @param key
   */
  public void releaseLock(String key);
}
