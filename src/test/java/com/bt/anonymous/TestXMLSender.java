package com.bt.anonymous;

import static org.easymock.EasyMock.anyBoolean;
import static org.easymock.EasyMock.anyInt;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createNiceMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JNDILookup.class)
public class TestXMLSender {

	private QueueSender mockQSndr;
	private TextMessage mockTextMsg;
	private static final Logger LOGGER = org.apache.log4j.LogManager.getLogger("TEST-LOGGER");
	
	@BeforeClass
	public static void initialise() {
		// Handy minimal initialisation of Log4j
		Logger.getRootLogger().addAppender(new ConsoleAppender(
		    new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
	}

	@Test
	public void shouldBeAbleToInstantiateAnXMLSender() {
		// 1. First try all nulls in parameters - NPE since the constructor calls the sendMessage() method
		// XMLSender sender = new XMLSender(null, null, null, null);
		// 2. Use a subclass to stub out the sendMessage() method
		// XMLSender sender = new StubbedXMLSender(null, null, null, null);
		// 3. Add an assertion to check the significant behaviour (introduce a sensing variable)
		new StubbedXMLSender(null, null, null, null);
		assertTrue(StubbedXMLSender.wasSendMessageCalled);
	}
	
	// Go with the simplest happy path scenario to begin with
	@Test
	public void shouldSendtheSuppliedMessage() throws JMSException {
		// Simple string parameters - guess at values
		String text = "Test message";
		String messageID = "TEST-MESSAGE-ID";
		String senderFlag = "";
		setMockExpectations(text, messageID, (String)anyObject()); 
		new XMLSender(LOGGER, text, messageID, senderFlag);
		verifyMockExpectations();
	}
	
	// Add some more behaviour-checks
	@Test
	public void shouldRouteMessagesToTheResponseQueueByDefault() throws JMSException {
		// Simple string parameters - guess at values
		String text = "Test message";
		String messageID = "TEST-MESSAGE-ID";
		String senderFlag = "";
		setMockExpectations((String)anyObject(), (String)anyObject(), MessageDrivenBean.RESPONSE_QUEUE); 
		new XMLSender(LOGGER, text, messageID, senderFlag);
		verifyMockExpectations();
	}

	/*
	 * Check our expectations (for the mocks we care about)
	 */
	private void verifyMockExpectations() {
		verify(this.mockQSndr);
		verify(this.mockTextMsg);
	}
	
	/*
	 * Pulled out to a separate method for clarity and reuse
	 */
	private void setMockExpectations(String expectedMsgText, String expectedMsgID, String expectedDestination) throws JMSException {
		clearExistingExpectations();
		mockStatic(JNDILookup.class);
		// "Unexpected method call getQueueConnectionFactory(null):" - do we care about these calls?
		// JNDILookup mockJndiLookup = createMock(JNDILookup.class);
		//  - not really, but we need to make sure we can sense the queueSender.send(textMessage);
		JNDILookup mockJndiLookup = createNiceMock(JNDILookup.class);
		
		QueueConnectionFactory mockQCF = createNiceMock(QueueConnectionFactory.class);
		QueueConnection mockQC = createNiceMock(QueueConnection.class);
		QueueSession mockQS = createNiceMock(QueueSession.class);
		this.mockQSndr = createNiceMock(QueueSender.class);
		this.mockTextMsg = createNiceMock(TextMessage.class);
		
		expect(mockJndiLookup.getQueue(expectedDestination)).andStubReturn(null);
		expect(mockJndiLookup.getQueueConnectionFactory(null)).andStubReturn(mockQCF);
		expect(mockQCF.createQueueConnection()).andStubReturn(mockQC);
		expect(mockQC.createQueueSession(anyBoolean(), anyInt())).andStubReturn(mockQS);
		expect(mockQS.createTextMessage()).andStubReturn(this.mockTextMsg);
		expect(mockQS.createSender((Queue) anyObject())).andStubReturn(this.mockQSndr);
		
		// Finally, something we care about - these are assertions about behaviour 
		//  (so long as verify() is called)
		// First try it out with a bad value
		// mockTextMsg.setText("rubbish");
		this.mockTextMsg.setText(expectedMsgText);
		this.mockTextMsg.setStringProperty("messageId", expectedMsgID);
		this.mockQSndr.send(this.mockTextMsg);
		// Intercept the factory method and inject our mock(s)
		expect(JNDILookup.getInstance()).andReturn(mockJndiLookup);
		
		// Get the mocks ready to use
		replay(mockQCF, mockQC, mockQS, this.mockQSndr, this.mockTextMsg);
		replay(JNDILookup.class, mockJndiLookup);
	}

	private void clearExistingExpectations() {
		reset(JNDILookup.class);
		safeReset(this.mockTextMsg,this.mockQSndr);
	}

	private void safeReset(Object ...mocks) {
		for (Object mock : mocks) {
			if (mock != null) reset(mock);
		}
	}
}
