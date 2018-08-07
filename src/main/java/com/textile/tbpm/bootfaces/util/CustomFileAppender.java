package com.textile.tbpm.bootfaces.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.FileAppender;

/**
 * The Class CustomFileAppender, appends the timestamp to the log being
 * generated.
 */
public class CustomFileAppender extends FileAppender {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.FileAppender#setFile(java.lang.String)
	 */
	public void setFile(String fileName) {
		if (fileName.indexOf("%timestamp") >= 0) {
			Date d = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
			fileName = fileName.replaceAll("%timestamp", format.format(d));
		}
		super.setName(fileName);
	}
}