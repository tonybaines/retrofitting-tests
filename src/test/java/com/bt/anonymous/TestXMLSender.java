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
		// 3. Add some assertions to check the significant behaviour
		StubbedXMLSender sender = new StubbedXMLSender(null, null, null, null);
		assertTrue(sender.wasSendMessageCalled);
	}
	
	// Go with the simplest happy path scenario to begin with
	@Test
	public void shouldSendtheSuppliedMessageToTheResponseQueueByDefault() {
		// Simple string parameters - guess at values
		String text = "Test message";
		String messageID = "TEST-MESSAGE-ID";
		String senderFlag = "";
		// An easy class to get a suitable instance
		Logger logger = org.apache.log4j.LogManager.getLogger("TEST-LOGGER");
		new XMLSender(logger, text, messageID, senderFlag);
		// 1. Run as is to check for hidden dependencies
		
	}
}
