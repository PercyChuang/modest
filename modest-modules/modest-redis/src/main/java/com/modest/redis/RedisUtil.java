package com.modest.redis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.modest.redis.factory.JedisPoolFactory;

public class RedisUtil {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    JedisPoolFactory jedisPoolFactory;

    /**
     * 保存值
     * 
     * @param key
     * @param value
     * @author ZHUANGPUXIANG
     */
    public void setVal(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPoolFactory.getJedis();
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("jedisPoolFactory set error!key=" + key + ",value=" + value, e);
        } finally {
            jedisPoolFactory.returnResource(jedis);
        }

    }

    /**
     * 设置+过期。操作是原子的。
     * 
     * @param key
     * @param value
     * @param seconds
     * @author ZHUANGPUXIANG
     */
    public void setValEx(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPoolFactory.getJedis();
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            logger.error("jedisPoolFactory set error!key=" + key + ",value=" + value, e);
        } finally {
            jedisPoolFactory.returnResource(jedis);
        }
    }

    /**
     * 获得值
     * 
     * @param key
     * @return
     * @author ZHUANGPUXIANG
     */
    public String getVal(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPoolFactory.getJedis();
            value = jedis.get(key);
        } catch (Exception e) {
            logger.error("jedisPoolFactory set error!key=" + key + ",value=" + value, e);
            throw e;
        } finally {
            jedisPoolFactory.returnResource(jedis);
        }
        return value;
    }

    /**
     * 删除
     * 
     * @param key
     * @return
     * @author ZHUANGPUXIANG
     */
    public String delVal(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPoolFactory.getJedis();
            jedis.del(key);
        } catch (Exception e) {
            logger.error("jedisPoolFactory set error!key=" + key + ",value=" + value, e);
            throw e;
        } finally {
            jedisPoolFactory.returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置key的值为value，如果key已经存在返回0
     * 
     * @param key
     * @param value
     * @return
     * @author ZHUANGPUXIANG
     */
    public Long setValNx(String key, String value) {
        Jedis jedis = null;
        Long result = 0L;
        try {
            jedis = jedisPoolFactory.getJedis();
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            logger.error("jedisPoolFactory set error!key=" + key + ",value=" + value, e);
            throw e;
        } finally {
            jedisPoolFactory.returnResource(jedis);
        }
        return result;
    }

    /**
     * lpush
     * 
     * @param key
     * @param value
     * @return
     * @author ZHUANGPUXIANG
     */
    public Long lpush(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = jedisPoolFactory.getJedis();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("jedisPoolFactory set error!key=" + key + ",value=" + value, e);
            throw e;
        } finally {
            jedisPoolFactory.returnResource(jedis);
        }
    }

    /**
     * 获取lpush所设置的参数
     * 
     * @param key
     * @param value
     * @return
     * @author ZHUANGPUXIANG
     */
    public List<String> lrange(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPoolFactory.getJedis();
            Long llen = jedis.llen(key);
            return jedis.lrange(key, 0L, llen);
        } catch (Exception e) {
            logger.error("jedisPoolFactory lrange error!key=" + key);
            throw e;
        } finally {
            jedisPoolFactory.returnResource(jedis);
        }
    }
    
    public int testWach(){
        //一开始的时候，就把数据库中的controller全部加载到redis的乐观锁处理的机制中。
        Jedis jedis = jedisPoolFactory.getJedis();
        
        try {
            //控
            String watch = jedis.watch("redPacketCount3");
            System.out.println(Thread.currentThread().getName()+"--"+watch);  
            
            String redPacketCount = jedis.get("redPacketCount3");
            if (redPacketCount == null) {
                return 2;
            }
            
            //用光
            Integer redPacketCountValue = Integer.valueOf(redPacketCount)-1;
            if (redPacketCountValue < 0) {
                System.out.println("库存已销光！");
                return 1;
            }
            
            //库存值的改变
            Transaction multi = jedis.multi();  
            multi.set("redPacketCount3", redPacketCountValue+"");  
            List<Object> result = multi.exec();  
            if (result == null || result.isEmpty()) {
                System. err.println( "Transaction error... 被其他事务执行");
                return 0;
            }else {
                System.out.println("剩余库存："+jedis.get("redPacketCount3")); 
            }
            return 1;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }finally{
            jedis.unwatch();
            jedisPoolFactory.returnResource(jedis);
        }
    }  
    
    public  void testWatch2(){  
        Jedis jedis = jedisPoolFactory.getJedis();  
        String watch = jedis.watch("testabcd2");  
        System.out.println(Thread.currentThread().getName()+"--"+watch);  
        Transaction multi = jedis.multi();  
        multi.incr("");
        multi.set("testabcd", "125");  
        List<Object> exec = multi.exec();  
        System.out.println("--->>"+exec);  
    }

    /**
     * 获取连接池工厂
     * @return
     */
    public JedisPoolFactory getJedisPoolFactory() {
        return jedisPoolFactory;
    }

	public void setJedisPoolFactory(JedisPoolFactory jedisPoolFactory) {
		this.jedisPoolFactory = jedisPoolFactory;
	} 
}
