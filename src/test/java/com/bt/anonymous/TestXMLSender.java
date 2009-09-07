package com.bt.anonymous;

import static org.easymock.EasyMock.anyBoolean;
import static org.easymock.EasyMock.anyInt;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
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
import javax.naming.NamingException;

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
@PrepareForTest({JNDILookup.class, MessageDrivenBean.class})
public class TestXMLSender {

	private QueueSender mockQSndr;
	private TextMessage mockTextMsg;
	private static final Logger LOGGER = org.apache.log4j.LogManager.getLogger("TEST-LOGGER");
	private String text = "Test message";
	private String messageID = "TEST-MESSAGE-ID";
	private String senderFlag = "";
	private JNDILookup mockJndiLookup;
	
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
	public void shouldSendtheSuppliedMessage() throws JMSException, NamingException {
		setMockExpectations(this.text, this.messageID, null); 
		new XMLSender(LOGGER, this.text, this.messageID, this.senderFlag);
		verifyMockExpectations();
	}
	
	// Add some more behaviour-checks
	@Test
	public void shouldRouteMessagesToTheResponseQueueByDefault() throws JMSException, NamingException {
		setMockExpectations(this.text, this.messageID, MessageDrivenBean.RESPONSE_QUEUE); 
		new XMLSender(LOGGER, this.text, this.messageID, this.senderFlag);
		verifyMockExpectations();
	}
	@Test
	public void shouldRouteMessagesToTheRedirectQueueWithTheAppropriateFlag() throws JMSException, NamingException {
		setMockExpectations(this.text, this.messageID, MessageDrivenBean.REDIRECT_QUEUE); 
		new XMLSender(LOGGER, this.text, this.messageID, "REDIRECT");
		verifyMockExpectations();
	}
	
	// Look at the sad-path scenarios
	@Test
	public void shouldSendAnEmailWhenAnExceptionIsThrown() throws NamingException {
		clearExistingExpectations();
		mockStatic(JNDILookup.class);
		mockStatic(MessageDrivenBean.class);
		expect(MessageDrivenBean.sendMail("TEST-EXCEPTION")).andReturn(true);
		this.mockJndiLookup = createMock(JNDILookup.class);
		expect(this.mockJndiLookup.getQueueConnectionFactory((String) anyObject())).andThrow(new NamingException("TEST-EXCEPTION"));
		expect(JNDILookup.getInstance()).andReturn(this.mockJndiLookup);
		replay(this.mockJndiLookup);
		replay(JNDILookup.class, MessageDrivenBean.class);
		
		new XMLSender(LOGGER, this.text, this.messageID, this.senderFlag);
		
		verify(this.mockJndiLookup);
		verify(JNDILookup.class, MessageDrivenBean.class);
	}

	/*
	 * Check our expectations (for the mocks we care about)
	 */
	private void verifyMockExpectations() {
		verify(this.mockQSndr);
		verify(this.mockTextMsg);
		verify(this.mockJndiLookup);
	}
	
	/*
	 * Pulled out to a separate method for clarity and reuse
	 */
	private void setMockExpectations(String expectedMsgText, String expectedMsgID, String expectedDestination) throws JMSException, NamingException {
		clearExistingExpectations();
		
		// Beware createNiceMock - it can save some effort but it can also mask
		// problems with your expectations
		this.mockJndiLookup = createNiceMock(JNDILookup.class);
		QueueConnectionFactory mockQCF = createNiceMock(QueueConnectionFactory.class);
		QueueConnection mockQC = createNiceMock(QueueConnection.class);
		QueueSession mockQS = createNiceMock(QueueSession.class);
		this.mockQSndr = createNiceMock(QueueSender.class);
		this.mockTextMsg = createNiceMock(TextMessage.class);
		
		expect(this.mockJndiLookup.getQueueConnectionFactory((String) anyObject())).andReturn(mockQCF);
		expect(mockQCF.createQueueConnection()).andReturn(mockQC);
		expect(mockQC.createQueueSession(anyBoolean(), anyInt())).andReturn(mockQS);
		expect(mockQS.createTextMessage()).andReturn(this.mockTextMsg);
		expect(mockQS.createSender((Queue) anyObject())).andReturn(this.mockQSndr);
		
		// Finally, something we care about - these are assertions about behaviour 
		//  (so long as verify() is called)
		// First try it out with a bad value
		// mockTextMsg.setText("rubbish");
		expect(this.mockJndiLookup.getQueue(expectedDestination != null ? expectedDestination:(String)anyObject())).andReturn(null);
		this.mockTextMsg.setText(expectedMsgText);
		this.mockTextMsg.setStringProperty("messageId", expectedMsgID);
		this.mockQSndr.send(this.mockTextMsg);
		// Intercept the factory method and inject our mock(s)
		mockStatic(JNDILookup.class);
		expect(JNDILookup.getInstance()).andReturn(this.mockJndiLookup);
		
		// Get the mocks ready to use
		replay(mockQCF, mockQC, mockQS, this.mockQSndr, this.mockTextMsg, this.mockJndiLookup);
		replay(JNDILookup.class);
	}

	private void clearExistingExpectations() {
		reset(JNDILookup.class);
		reset(MessageDrivenBean.class);
		safeReset(this.mockTextMsg, this.mockQSndr, this.mockJndiLookup);
	}

	private void safeReset(Object ...mocks) {
		for (Object mock : mocks) {
			if (mock != null) reset(mock);
		}
	}
}
