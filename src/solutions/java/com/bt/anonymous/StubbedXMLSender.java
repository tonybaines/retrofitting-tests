/**
 * 
 */
package com.bt.anonymous;

import org.apache.log4j.Logger;

public class StubbedXMLSender extends XMLSender {
	public static boolean wasSendMessageCalled = false;
	StubbedXMLSender(Logger logger, String text, String messageID,
			String senderflag) {
		super(logger, text, messageID, senderflag);
	}

	/*
	 * Override the sendMessage() method to allow the constructor to be tested
	 * on it's own
	 */
	@Override
	public void sendMessage(String text, String messageID, String senderflag) {
		wasSendMessageCalled = true;
	}
	
}