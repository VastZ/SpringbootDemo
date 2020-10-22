package com.zoro.test;

import com.zoro.common.lock.DistributedLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	/**
	 * Spring RestTemplate的便利替代。你可以获取一个普通的或发送基本HTTP认证（使用用户名和密码）的模板
	 * 这里不使用
	 */
	private  int count=100;

	private int sum = 255;

	private Lock lock = new ReentrantLock();

	private CountDownLatch countDownLatch = new CountDownLatch(count);

	private Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);

	@Autowired
	private DistributedLock distributedLock;

	@Autowired
	RedisTemplate redisTemplate;


	@Test
	public void testThread(){
		redisTemplate.opsForValue().set("test", "wocao");
		for (int i = 0; i < count; i++) {
			Thread thread = new Thread(new TestThread());
			thread.start();
			countDownLatch.countDown();
		}
	}

	class  TestThread implements Runnable{

		@Override
		public void run() {
			String key = "creditCardJobTask";
			try {
				countDownLatch.await();
				boolean lockFlag = distributedLock.lock(key, 60*1000l);
				if(!lockFlag){
					logger.info("creditCardJobTask task already begin");
					return;
				}
				print();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				boolean releaseResult = distributedLock.releaseLock(key);
				logger.debug("release lock : " + key + (releaseResult ? " success" : " failed"));
			}
		}

		public void print(){
			System.out.println(Thread.currentThread().getId()+"买票："+sum--+" 还有："+sum+"张票");
		}
	}



}
