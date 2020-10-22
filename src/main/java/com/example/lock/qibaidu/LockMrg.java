/*
 * 创建日期 2005-3-10
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.example.lock.qibaidu;

import java.util.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class LockMrg 
{
	private Hashtable locks = null;
	private static LockMrg lockMrg = null;
	private Lock nullLock = null;
	private String nullKey = null;
	
	private LockMrg()
	{
		locks = new Hashtable();
		nullLock = new Lock();
		nullKey = "null";
	}
	public Lock lock(Object key)throws Exception
	{
		Lock lock = getLock(key, true);
		synchronized(lock)
		{
			while(lock.getShareLockCount()>0||lock.getLockCount()>0) {
                lock.wait();
            }
			lock.increase();
			if(key == null) {
                locks.put(nullKey, lock);
            } else {
                locks.put(key, lock);
            }
			return lock;
		}
	}
	public void release(Object key)
	{
		Lock lock = getLock(key);
		synchronized(lock)
		{
			lock.decrease();
			lock.decreaseWaitCount();
			if(lock.getLockCount()==0&&lock.getWaitCount()==0&&lock.getShareLockCount()==0) {
                locks.remove(key);
            }
			lock.notify();
		}
	}
	private Lock getLock(Object key)
	{
		return getLock(key, false);
	}
	private Lock getLock(Object key, boolean isLock)
	{
		synchronized(lockMrg)
		{
			Lock lock = null;
			if(key == null) {
                lock = nullLock;
            } else {
                lock = (Lock)locks.get(key);
            }
			if(lock==null)
			{
				lock = new Lock();
				locks.put(key, lock);
			}
			if(isLock) {
                lock.increaseWaitCount();
            }
			return lock;
		}		
	}
	public Lock shareLock(Object key)throws Exception
	{
		Lock lock = getLock(key,true);
		synchronized(lock)
		{
			while(lock.getLockCount()>0) {
                lock.wait();
            }
			lock.increaseShare();
			locks.put(key, lock);
			return lock;
		}
	}
	public void releaseShareLock(Object key)
	{
		Lock lock = getLock(key);
		synchronized(lock)
		{
			lock.decreaseShare();
			lock.decreaseWaitCount();
			if(lock.getLockCount()==0&&lock.getWaitCount()==0&&lock.getShareLockCount()==0) {
                locks.remove(key);
            }
			lock.notify();
		}
	}
	public static synchronized LockMrg getInstance()
	{
		if(lockMrg==null)
		{
			lockMrg = new LockMrg();
		}
		return lockMrg;
	}
}
