package com.test.loggerratelimiter.ratelimitedlogger;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.loggerratelimiter.ratelimitedlogger.RateLimitedLogger;

public class RateLimitedLoggerTest {

	private ByteArrayOutputStream byteArrayOutputStream;

	@Before
	public void setup() {
		// TODO Auto-generated method stub

		this.byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));
	}

	@Test
	public void testConcurrentLogging() throws InterruptedException {
		// TODO Auto-generated method stub
		RateLimitedLogger rateLimitedLogger = new RateLimitedLogger(5, 5);
		ExecutorService ex = Executors.newFixedThreadPool(3);
		CountDownLatch cdl = new CountDownLatch(5);

		for (int i = 0; i < 5; i++) {
			int messageI = i;

			ex.execute(() -> {
				rateLimitedLogger.pushLogs("Task with task id : " + messageI + " got printed!");
				cdl.countDown();
			});
		}

		cdl.await();
		ex.shutdown();
		String output = this.byteArrayOutputStream.toString();
		System.out.println(output);
		
		for(int i = 0; i < 5; i++) {
			assertTrue(output.contains("Task with task id : " + i + " got printed!"));
		}
	}

	@Test
	public void testConcurrentLoggingWithTokenRefill() throws InterruptedException {
		// TODO Auto-generated method stub
		RateLimitedLogger rateLimitedLogger = new RateLimitedLogger(5, 5);
		ExecutorService ex = Executors.newFixedThreadPool(4);
		CountDownLatch cdl = new CountDownLatch(10);

		for (int i = 0; i < 10; i++) {
			int messageI = i;

			ex.execute(() -> {
				rateLimitedLogger.pushLogs("Task with task id : " + messageI + "!");
				cdl.countDown();
			});
		}

		ScheduledExecutorService scex = Executors.newScheduledThreadPool(1);
		scex.schedule(() -> rateLimitedLogger.refillToken(), 10, TimeUnit.MILLISECONDS);

		cdl.await();
		ex.shutdown();
		scex.shutdown();

		String output = this.byteArrayOutputStream.toString();

		for (int i = 0; i < 10; i++) {
			assertTrue(output.contains("Task with task id : " + i + "!"));
		}
	}
}
