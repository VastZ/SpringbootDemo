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
public class Lock 
{
	private int lockCount = 0;
	private int slockCount = 0;
	private int waitCount = 0;
	private Object shareLock = new Object();

	public void increaseWaitCount()
	{
		waitCount++;
	}
	public void decreaseWaitCount()
	{
		waitCount--;
	}
	public int getWaitCount()
	{
		return waitCount;
	}
	public void increase()
	{
		lockCount++;
	}
	public void decrease()
	{
		if(lockCount>0) {
            lockCount--;
        }
	}
	public void increaseShare()
	{
		slockCount++;
	}
	public void decreaseShare()
	{
		if(slockCount>0) {
            slockCount--;
        }
	}	
	public int getLockCount()
	{
		return lockCount;	
	}
	public int getShareLockCount()
	{
		return slockCount;
	}
}
