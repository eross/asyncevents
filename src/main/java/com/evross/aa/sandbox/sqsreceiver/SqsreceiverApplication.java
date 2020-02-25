package com.evross.aa.sandbox.sqsreceiver;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@EnableAsync
@SpringBootApplication
public class SqsreceiverApplication implements AsyncConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SqsreceiverApplication.class, args);
	}
	
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();// (4)      
		executor.setCorePoolSize(2);      
		executor.setMaxPoolSize(100);      
		executor.setQueueCapacity(5);                                  // (5)       
		executor.initialize();      
		return executor;
	}
	
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler () {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

}
