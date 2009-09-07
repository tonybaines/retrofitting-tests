package com.bt.anonymous;

import static org.junit.Assert.*;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
// Need to be careful with the imports to avoid clashes
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.anyBoolean;
import static org.easymock.EasyMock.anyInt;
import static org.easymock.EasyMock.anyObject;
import static org.powermock.api.easymock.PowerMock.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JNDILookup.class)
public class TestXMLSender {

	private QueueSender mockQSndr;
	private TextMessage mockTextMsg;

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
	public void shouldSendtheSuppliedMessage() throws JMSException {
		// Simple string parameters - guess at values
		String text = "Test message";
		String messageID = "TEST-MESSAGE-ID";
		String senderFlag = "";
		// An easy class to get a suitable instance
		Logger logger = org.apache.log4j.LogManager.getLogger("TEST-LOGGER");
		// 1. Run as is to check for hidden dependencies (no exceptions)
		//new XMLSender(logger, text, messageID, senderFlag);
		// 2. Attempt to sense and assert behaviour - was a message sent?
		//     The serviceProvider (JNDILookup) provides the various collaborators - but
		//     access is through a static factory method getInstance()
		//     Standard mock frameworks won't help => PowerMock
		
		setMockExpectations(text, messageID); // Pulled out to a separate method for clarity and reuse

		new XMLSender(logger, text, messageID, senderFlag);

		// Check our expectations (for the mocks we care about)
		verify(JNDILookup.class);
		verify(mockQSndr, mockTextMsg);
	}
	
	private void setMockExpectations(String expectedMsgText, String expectedMsgID) throws JMSException {
		mockStatic(JNDILookup.class);
		// "Unexpected method call getQueueConnectionFactory(null):" - do we care about these calls?
		// JNDILookup mockJndiLookup = createMock(JNDILookup.class);
		//  - not really, but we need to make sure we can sense the queueSender.send(textMessage);
		JNDILookup mockJndiLookup = createNiceMock(JNDILookup.class);
		
		QueueConnectionFactory mockQCF = createNiceMock(QueueConnectionFactory.class);
		QueueConnection mockQC = createNiceMock(QueueConnection.class);
		QueueSession mockQS = createNiceMock(QueueSession.class);
		mockQSndr = createNiceMock(QueueSender.class);
		mockTextMsg = createNiceMock(TextMessage.class);
		
		expect(mockJndiLookup.getQueueConnectionFactory(null)).andReturn(mockQCF);
		expect(mockQCF.createQueueConnection()).andReturn(mockQC);
		expect(mockQC.createQueueSession(anyBoolean(), anyInt())).andReturn(mockQS);
		expect(mockQS.createTextMessage()).andReturn(mockTextMsg);
		expect(mockQS.createSender((Queue) anyObject())).andReturn(mockQSndr);
		
		// Finally, something we care about - these are assertions about behaviour 
		//  (so long as verify() is called)
		// First try it out with a bad value
		// mockTextMsg.setText("rubbish");
		mockTextMsg.setText(expectedMsgText);
		mockTextMsg.setStringProperty("messageId", expectedMsgID);
		mockQSndr.send(mockTextMsg);
		// Intercept the factory method and inject our mock(s)
		expect(JNDILookup.getInstance()).andReturn(mockJndiLookup);
		
		// Get the mocks ready to use
		replay(mockQCF, mockQC, mockQS, mockQSndr, mockTextMsg);
		replay(JNDILookup.class, mockJndiLookup);
	}
}
