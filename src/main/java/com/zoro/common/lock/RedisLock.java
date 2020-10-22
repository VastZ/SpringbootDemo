package com.zoro.common.lock;

import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * @ClassName RedisLock
 * @Description Todo
 * @Author yanghao
 * @Date 2018/11/15 下午8:15
 * Version 0.0.1
 **/
public class RedisLock {

    private  static String LOCK ="LOCK";
    public boolean lock(){
        return false;
    }

    public boolean tryLock(){
        String s = UUID.randomUUID().toString();
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String set = jedis.set(LOCK, s, "NX", "PX", 1000);
        if(set !=null && set.equals("OK")){
            return true;
        }
        return false;
    }

    public boolean unLock(){
        return false;
    }
}
