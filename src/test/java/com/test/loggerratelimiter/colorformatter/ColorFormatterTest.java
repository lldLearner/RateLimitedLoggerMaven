package com.test.loggerratelimiter.colorformatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Test;

import com.loggerratelimiter.colorformatter.ColorFormatter;

public class ColorFormatterTest {

	private static final ColorFormatter colorFormatter  = new ColorFormatter();
	
	@Test
	public void testInfoLevel() {
		LogRecord logRecord = new LogRecord(Level.INFO, "This is the test message for info!");
		String expectedOutput = "\u001B[32m[INFO] This is the test message for info!\u001B[0m\n";
		assertEquals(expectedOutput, colorFormatter.format(logRecord));
	}
	
	@Test
	public void testSevereLevel() {
		LogRecord logRecord = new LogRecord(Level.SEVERE, "This is the test message for Severe!");
		String expectedOutput = "\u001B[31m[SEVERE] This is the test message for Severe!\u001B[0m\n";
		assertEquals(expectedOutput, colorFormatter.format(logRecord));
	}
	
	@Test
	public void testWarningLevel() {
		LogRecord logRecord = new LogRecord(Level.WARNING, "This is the test message for Warning!");
		String expectedOutput = "\u001B[33m[WARNING] This is the test message for Warning!\u001B[0m\n";
		assertEquals(expectedOutput, colorFormatter.format(logRecord));
	}
	
	@Test
	public void testDefaultLevel() {
		LogRecord logRecord = new LogRecord(Level.CONFIG, "This is the test message for default!");
		assertThrows(IllegalArgumentException.class, () -> colorFormatter.format(logRecord));
	}
}
