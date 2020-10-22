package com.example.lock.qibaidu;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
 
 
/**
 * 
 * @author 王银龙 只写了这些需要其他的可以自己单独写。
 * 基于spring和redis的redisTemplate工具类
 * 针对所有的hash 都是以h开头的方法
 * 针对所有的Set 都是以s开头的方法 
 * 针对所有的List 都是以l开头的方法
 */
public class RedisUtil {
 
 
	public static String defaultRedisKey="700du_";
	private RedisTemplate<String, Object> redisTemplate;
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	//=============================common============================
	
	/**
     * redis分布式锁加锁 请及时释放锁
     * @description
     * @author zhang.wenhan 
     * @date 2019年7月22日 下午5:23:43
     * @param key 
     * @param value
     * @param time  过期时间
     * @param timeUnit 过期时间单位
     * @return 
     */
    public boolean lock(String key, String value, long time, TimeUnit timeUnit) {
    	//  execute(connection -> connection.setNX(rawKey, rawValue), true);
        boolean locked = redisTemplate.opsForValue().setIfAbsent(key, value);
        if (locked && time > 0) {
            redisTemplate.expire(key, time, timeUnit);
        }
        return locked;
    }
    
    /**
     * 释放锁
     * @description
     * @version 1.0.0
     * @author zhang.wenhan 
     * @date 2019年7月22日 下午5:25:45
     * @param key
     */
    public void unlock(String key) {
        redisTemplate.delete(key);
    }
	
	/**
	 * 指定缓存失效时间
	 * @param key 键
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean expire(String key,long time){
		try {
			if(time>0){
				redisTemplate.expire(defaultRedisKey+key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 根据key 获取过期时间
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public long getExpire(String key){
		return redisTemplate.getExpire(defaultRedisKey+key,TimeUnit.SECONDS);
	}
	
	/**
	 * 判断key是否存在
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public boolean hasKey(String key){
		try {
			return redisTemplate.hasKey(defaultRedisKey+key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除缓存
	 * @param key 可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public void del(String ... key){
		if(key!=null&&key.length>0){
			if(key.length==1){
				redisTemplate.delete(defaultRedisKey+key[0]);
			}else{
				redisTemplate.delete(CollectionUtils.arrayToList(defaultRedisKey+key));
			}
		}
	}
	
	//============================String=============================
	/**
	 * 普通缓存获取
	 * @param key 键
	 * @return 值
	 */
	public Object get(String key){
		return key==null?null:redisTemplate.opsForValue().get(defaultRedisKey+key);
	}
	
	/**
	 * 普通缓存放入
	 * @param key 键
	 * @param value 值
	 * @return true成功 false失败
	 */
	public boolean set(String key,Object value) {
		 try {
			redisTemplate.opsForValue().set(defaultRedisKey+key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * 普通缓存放入并设置时间
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true成功 false 失败
	 */
	public boolean set(String key,Object value,long time){
		try {
			if(time>0){
				redisTemplate.opsForValue().set(defaultRedisKey+key, value, time, TimeUnit.SECONDS);
			}else{
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 递增
	 * @param key 键
	 * @return
	 */
	public long incr(String key, long delta){  
		if(delta<0){
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(defaultRedisKey+key, delta);
    }
	
	/**
	 * 递减
	 * @param key 键
	 * @return
	 */
	public long decr(String key, long delta){  
		if(delta<0){
			throw new RuntimeException("递减因子必须大于0");
		}
        return redisTemplate.opsForValue().increment(defaultRedisKey+key, -delta);  
    }  
	
	//================================Map=================================
	/**
	 * HashGet
	 * @param key 键 不能为null
	 * @param item 项 不能为null
	 * @return 值
	 */
	public Object Hashget(String key,String item){
		return redisTemplate.opsForHash().get(defaultRedisKey+key, item);
	}
	
	/**
	 * 获取hashKey对应的所有键值
	 * @param key 键
	 * @return 对应的多个键值
	 */
	public Map<Object,Object> Hashget(String key){
		return redisTemplate.opsForHash().entries(defaultRedisKey+key);
	}
	
	/**
	 * HashSet
	 * @param key 键
	 * @param map 对应多个键值
	 * @return true 成功 false 失败
	 */
	public boolean Hashset(String key, Map<String,Object> map){  
        try {
			redisTemplate.opsForHash().putAll(defaultRedisKey+key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
	/**
	 * HashSet 并设置时间
	 * @param key 键
	 * @param map 对应多个键值
	 * @param time 时间(秒)
	 * @return true成功 false失败
	 */
    public boolean Hashset(String key, Map<String,Object> map, long time){  
        try {
			redisTemplate.opsForHash().putAll(defaultRedisKey+key, map);
			if(time>0){
				expire(defaultRedisKey+key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * @param key 键
	 * @param item 项
	 * @param value 值
	 * @return true 成功 false失败
	 */
	public boolean Hashset(String key,String item,Object value) {
		 try {
			redisTemplate.opsForHash().put(defaultRedisKey+key, item, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * @param key 键
	 * @param item 项
	 * @param value 值
	 * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @return true 成功 false失败
	 */
	public boolean Hashset(String key,String item,Object value,long time) {
		 try {
			redisTemplate.opsForHash().put(defaultRedisKey+key, item, value);
			if(time>0){
				expire(defaultRedisKey+key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除hash表中的值
	 * @param key 键 不能为null
	 * @param item 项 可以使多个 不能为null
	 */
    public void Hashdel(String key, Object... item){  
		redisTemplate.opsForHash().delete(defaultRedisKey+key,item);
    } 
    
    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean HashHasKey(String key, String item){
		return redisTemplate.opsForHash().hasKey(defaultRedisKey+key, item);
    } 
	
	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 * @param key 键
	 * @param item 项
	 * @param by 要增加几(大于0)
	 * @return
	 */
	public double Hashincr(String key, String item,double by){  
        return redisTemplate.opsForHash().increment(defaultRedisKey+key, item, by);
    }
	
	/**
	 * hash递减
	 * @param key 键
	 * @param item 项
	 * @param by 要减少记(小于0)
	 * @return
	 */
	public double Hashdecr(String key, String item,double by){  
        return redisTemplate.opsForHash().increment(defaultRedisKey+key, item,-by);  
    }  
	
	//============================set=============================
	/**
	 * 根据key获取Set中的所有值
	 * @param key 键
	 * @return
	 */
	public Set<Object> sGet(String key){
		try {
			return redisTemplate.opsForSet().members(defaultRedisKey+key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据value从一个set中查询,是否存在
	 * @param key 键
	 * @param value 值
	 * @return true 存在 false不存在
	 */
	public boolean sHasKey(String key,Object value){
		try {
			return redisTemplate.opsForSet().isMember(defaultRedisKey+key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 将数据放入set缓存
	 * @param key 键
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public long sSet(String key, Object...values) {
        try {
            return redisTemplate.opsForSet().add(defaultRedisKey+key, values);
        } catch (Exception e) {
        	e.printStackTrace();
        	return 0;
        }
    }
	
	/**
	 * 将set数据放入缓存
	 * @param key 键
	 * @param time 时间(秒)
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public long sSetAndTime(String key,long time,Object...values) {
        try {
        	Long count = redisTemplate.opsForSet().add(defaultRedisKey+key, values);
        	if(time>0) {
                expire(defaultRedisKey+key, time);
            }
            return count;
        } catch (Exception e) {
        	e.printStackTrace();
        	return 0;
        }
    }
	
	/**
	 * 获取set缓存的长度
	 * @param key 键
	 * @return
	 */
	public long sGetSetSize(String key){
		try {
			return redisTemplate.opsForSet().size(defaultRedisKey+key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 移除值为value的
	 * @param key 键
	 * @param values 值 可以是多个
	 * @return 移除的个数
	 */
	public long setRemove(String key, Object ...values) {
        try {
            Long count = redisTemplate.opsForSet().remove(defaultRedisKey+key, values);
            return count;
        } catch (Exception e) {
        	e.printStackTrace();
        	return 0;
        }
    }
    //===============================list=================================
    
	/**
	 * 获取list缓存的内容
	 * @param key 键
	 * @param start 开始
	 * @param end 结束  0 到 -1代表所有值
	 * @return
	 */
	public List<Object> ListGet(String key,long start, long end){
		try {
			return redisTemplate.opsForList().range(defaultRedisKey+key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取list缓存的长度
	 * @param key 键
	 * @return
	 */
	public long ListGetListSize(String key){
		try {
			return redisTemplate.opsForList().size(defaultRedisKey+key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 通过索引 获取list中的值
	 * @param key 键
	 * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return
	 */
	public Object ListGetIndex(String key,long index){
		try {
			return redisTemplate.opsForList().index(defaultRedisKey+key, index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public boolean ListSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(defaultRedisKey+key, value);
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
        	return false;
        }
    }
	
	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean ListSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(defaultRedisKey+key, value);
            if (time > 0) {
                expire(defaultRedisKey+key, time);
            }
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
        	return false;
        }
    }
	
	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public boolean ListSet(String key, List<Object> value) {
	    try {
			redisTemplate.opsForList().rightPushAll(defaultRedisKey+key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean ListSet(String key, List<Object> value, long time) {
	    try {
			redisTemplate.opsForList().rightPushAll(defaultRedisKey+key, value);
			if (time > 0) {
                expire(defaultRedisKey+key, time);
            }
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
	/**
	 * 根据索引修改list中的某条数据
	 * @param key 键
	 * @param index 索引
	 * @param value 值
	 * @return
	 */
	public boolean ListUpdateIndex(String key, long index,Object value) {
	    try {
			redisTemplate.opsForList().set(defaultRedisKey+key, index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    } 
	
	/**
	 * 移除N个值为value 
	 * @param key 键
	 * @param count 移除多少个
	 * @param value 值
	 * @return 移除的个数
	 */
	public long ListRemove(String key,long count,Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(defaultRedisKey+key, count, value);
			return remove;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 删除缓存
	 * @param key 可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public void delOther(String ... key){
		if(key!=null&&key.length>0){
			if(key.length==1){
				redisTemplate.delete(key[0]);
			}else{
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}
	
	public void deleteByPrex(String prex) {
        Set<String> keys = redisTemplate.keys(defaultRedisKey + prex);
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }
	public Object getohter(String key) {
		// TODO Auto-generated method stub
		return key==null?null:redisTemplate.opsForValue().get(key);
	}
	
}
