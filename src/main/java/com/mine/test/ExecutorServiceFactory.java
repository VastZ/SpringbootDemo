package com.mine.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceFactory {
	
	private ExecutorServiceFactory() {
	}
	/**
	 * 线程池
	 */
	public static ExecutorService es = Executors.newFixedThreadPool(10);
}
