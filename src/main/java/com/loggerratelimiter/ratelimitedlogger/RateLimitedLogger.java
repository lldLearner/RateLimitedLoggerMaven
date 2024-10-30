package com.loggerratelimiter.ratelimitedlogger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import com.loggerratelimiter.colorformatter.ColorFormatter;

import comloggerratelimiter.ratelimiter.RateLimiter;

public class RateLimitedLogger {

	private RateLimiter rateLimiter;
	private static final Logger logger = Logger.getLogger("Rate Limited Logger");

	private ConsoleHandler consoleHandler;

	public RateLimitedLogger(int currentTokens, int tokenBucketCapacity) {
		// logger <- handler <- formatter
		this.rateLimiter = new RateLimiter(currentTokens, tokenBucketCapacity);
		this.consoleHandler = new ConsoleHandler();
		this.consoleHandler.setFormatter(new ColorFormatter());
		logger.addHandler(consoleHandler);
		logger.setUseParentHandlers(false);
	}

	public synchronized void pushLogs(String message) {
		if (!rateLimiter.acquireToken()) {
			logger.warning("Message " + message + " went into waiting mode!");
			try {
				wait();
				logger.info("Message " + message + " came out of waiting mode, now publishing log!");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.severe("Message " + message + " got interrupted!");
			}
		}

		storeLog(message);
	}

	private void storeLog(String message) {
		System.out.println(message);
	}

	public synchronized void refillToken() {

		rateLimiter.refillToken();
		notifyAll();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int tokenBucketCapacity = 5;
		RateLimitedLogger rLL = new RateLimitedLogger(5, tokenBucketCapacity);
		ExecutorService ex1 = Executors.newFixedThreadPool(3);
		CountDownLatch cdl = new CountDownLatch(100);
		for (int i = 0; i < 100; i++) {
			int messageNumber = i;
			ex1.execute(() -> {
				rLL.pushLogs("Message logged by thread " + Thread.currentThread().getName() + " messageNumber "
						+ messageNumber);
				cdl.countDown();
			});
		}

		int refillRate = 60 / tokenBucketCapacity;
		ScheduledExecutorService ex2 = Executors.newScheduledThreadPool(1);
		ex2.scheduleAtFixedRate(() -> rLL.refillToken(), 0, refillRate, TimeUnit.SECONDS);
		try {
			cdl.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.severe("Current Thread " + Thread.currentThread().getName() + " Got Interrupted!");
		} finally {
			ex1.shutdown();
			ex2.shutdown();
		}
	}
}
