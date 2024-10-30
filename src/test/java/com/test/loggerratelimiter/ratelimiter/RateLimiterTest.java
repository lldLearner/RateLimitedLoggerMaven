package com.test.loggerratelimiter.ratelimiter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import comloggerratelimiter.ratelimiter.RateLimiter;

public class RateLimiterTest {

	private RateLimiter rateLimiter;

	@Before
	public void setup() {
		this.rateLimiter = new RateLimiter(3, 5);
	}

	@Test
	public void testTokenAcquireSuccess() {
		// TODO Auto-generated method stub

		assertTrue(rateLimiter.acquireToken());
		assertTrue(rateLimiter.acquireToken());
		assertTrue(rateLimiter.acquireToken());

	}

	@Test
	public void testTokenAcquireFailure() {
		// TODO Auto-generated method stub
		rateLimiter.acquireToken();
		rateLimiter.acquireToken();
		rateLimiter.acquireToken();

		assertFalse(rateLimiter.acquireToken());
		assertFalse(rateLimiter.acquireToken());
		assertFalse(rateLimiter.acquireToken());

	}
	
	@Test
	public void testTokenRefillSuccess() {
		// TODO Auto-generated method stub

		rateLimiter.acquireToken();
		rateLimiter.acquireToken();
		rateLimiter.acquireToken();
		
		rateLimiter.refillToken();
		
		for(int i = 0; i < 5; i++) {
			assertTrue(rateLimiter.acquireToken());
		}
		
		assertFalse(rateLimiter.acquireToken());
	}
}
