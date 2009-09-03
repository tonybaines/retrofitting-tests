package com.bt.anonymous;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestXMLSender {

	@Test
	public void shouldBeAbleToInstantiateAnXMLSender() {
		// 1. First try all nulls in parameters - NPE since the constructor calls the sendMessage() method
		// XMLSender sender = new XMLSender(null, null, null, null);
		// 2. Use a subclass to stub out the sendMessage() method
		// XMLSender sender = new StubbedXMLSender(null, null, null, null);
		// 3. Add some assertions to check the important behaviour
		StubbedXMLSender sender = new StubbedXMLSender(null, null, null, null);
		assertTrue(sender.wasSendMessageCalled);
	}

	private class StubbedXMLSender extends XMLSender {
		public boolean wasSendMessageCalled;
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
			this.wasSendMessageCalled = true;
		}
		
	}
}
