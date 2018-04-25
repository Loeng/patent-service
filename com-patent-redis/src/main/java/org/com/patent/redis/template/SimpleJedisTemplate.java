package org.com.patent.redis.template;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class SimpleJedisTemplate implements JedisTemplate {

  private ShardedJedisPool pool;

  public SimpleJedisTemplate() {

  }

  public void setPool(ShardedJedisPool pool) {
    this.pool = pool;
  }

  @Override
  public String getString(String key) {
    ShardedJedis session = null;
    try {
      session = this.pool.getResource();
      return session.get(key);
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  @Override
  public String setString(String key, String value) {
    ShardedJedis session = null;
    try {
      session = this.pool.getResource();
      return session.set(key, value);
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  @Override
  public String setString(String key, String value, int exp) {
    ShardedJedis session = null;
    try {
      session = this.pool.getResource();
      return session.setex(key, exp, value);
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  @Override
  public Long delString(String key) {
    ShardedJedis session = null;
    try {
      session = this.pool.getResource();
      return session.del(key);
    } finally {
      if (session != null) {
        session.close();
      }
    }

  }

  @Override
  public Object getObject(String key) {
    ShardedJedis session = null;
    try {
      session = this.pool.getResource();
      ObjectInputStream ois = null;
      try {
        byte[] arry = session.get(key.getBytes());
        if (arry == null) {
          return null;
        }
        ois = new ObjectInputStream(new ByteArrayInputStream(arry));
        return ois.readObject();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (ois != null) {
          try {
            ois.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
      return session.get(key.getBytes());
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  @Override
  public String setObject(String key, Object value) {
    ShardedJedis session = null;
    ObjectOutputStream oos = null;
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream(512);
      oos = new ObjectOutputStream(out);
      session = this.pool.getResource();
      oos.writeObject(value);
      return session.set(key.getBytes(), out.toByteArray());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      if (oos != null) {
        try {
          oos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (session != null) {
        session.close();
      }
    }

  }

  @Override
  public String setObject(String key, Object value, int exp) {
    ShardedJedis session = null;
    ObjectOutputStream oos = null;
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream(512);
      oos = new ObjectOutputStream(out);
      session = this.pool.getResource();
      oos.writeObject(value);
      return session.setex(key.getBytes(), exp, out.toByteArray());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      if (oos != null) {
        try {
          oos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (session != null) {
        session.close();
      }
    }
  }

  @Override
  public long delObject(String key) {
    ShardedJedis session = null;
    try {
      session = this.pool.getResource();
      return session.del(key.getBytes());
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  @Override
  public long incr(String key) {
    ShardedJedis session = null;
    try {
      session = this.pool.getResource();
      return session.incr(key);
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  @Override
  public boolean applyLock(String key) {
    return applyLock(key, 0);
  }

  @Override
  public boolean applyLock(String key, int timeout) {
    Long r = -1l;
    ShardedJedis session = null;
    try {
      session = this.pool.getResource();
      if (timeout == 0) {
        r = session.setnx(key, "LOCK");
      } else if (timeout == -1) {
        for (; r != 1;) {
          r = doLockAndWaite(session, key, 10);
        }
      } else if (timeout > 0) {
        long now = System.currentTimeMillis();
        while (((System.currentTimeMillis() - now) < timeout) && r != 1) {
          r = doLockAndWaite(session, key, 10);
        }
      }
      return r != null && r == 1;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      if (session != null) {
        session.close();
      }
    }

  }

  private Long doLockAndWaite(ShardedJedis session, String code, int waiteTime) throws InterruptedException {
    Long r = session.setnx(code, "LOCK");
    if (r == null || r != 1) {
      synchronized (this) {
        wait(waiteTime);
      }
    }
    return r;
  }

  @Override
  public void releaseLock(String key) {
    ShardedJedis session = null;
    try {
      session = this.pool.getResource();
      session.del(key);
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }
}

