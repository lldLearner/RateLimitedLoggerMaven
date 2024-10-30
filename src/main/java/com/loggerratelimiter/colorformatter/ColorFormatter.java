package com.loggerratelimiter.colorformatter;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ColorFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		// TODO Auto-generated method stub
		Level level = record.getLevel();
		if(level == Level.INFO) {
			return "\u001B[32m"
                    + "[" + record.getLevel() + "] " + formatMessage(record) + "\u001B[0m" + "\n";
		} else if(level == Level.SEVERE) {
			return "\u001B[31m"
            + "[" + record.getLevel() + "] " + formatMessage(record) + "\u001B[0m" + "\n";
		} else if(level == Level.WARNING) {
			return "\u001B[33m"
            + "[" + record.getLevel() + "] " + formatMessage(record) + "\u001B[0m" + "\n";
		} else {
			throw new IllegalArgumentException();
		}
	}

}
