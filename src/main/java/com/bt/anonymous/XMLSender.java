package com.bt.anonymous;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.*;
import javax.jms.*;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.apache.openejb.jee.MessageDrivenBean;

public class XMLSender {

	private Logger logger = null;

	private JNDILookup serviceProvider = null;

	XMLSender(Logger logger, String text, String messageID,
			String senderflag) {
		this.logger = logger;
		serviceProvider = JNDILookup.getInstance();
		sendMessage(text, messageID, senderflag);
	}

	public void sendMessage(String text, String messageID, String senderflag) {
		QueueConnectionFactory queueConnectionFactory = null;
		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		QueueSender queueSender = null;
		Queue queue = null;
		TextMessage textMessage = null;

		try {

			queueConnectionFactory = serviceProvider
					.getQueueConnectionFactory(MessageDrivenBean.JMS_FACTORY);

			if (senderflag.equals("REDIRECT"))

				queue = serviceProvider
						.getQueue(MessageDrivenBean.REDIRECT_QUEUE);

			else

				queue = serviceProvider
						.getQueue(MessageDrivenBean.RESPONSE_QUEUE);

			queueConnection = queueConnectionFactory.createQueueConnection();

			queueSession = queueConnection.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);

			queueSender = queueSession.createSender(queue);
			textMessage = queueSession.createTextMessage();
			textMessage.setText(text);
			textMessage.setStringProperty("messageId", messageID);
			queueConnection.start();
			queueSender.send(textMessage);
			queueSession.close();
			queueConnection.close();

			if (senderflag.equals("REDIRECT")) {
				logger.info("Request XML sent to " + MessageDrivenBean.REDIRECT_QUEUE);

			} else
				logger.info("Response XML sent to " + MessageDrivenBean.RESPONSE_QUEUE);
		}

		catch (Exception e) {

			logger.info("Unable to send message to the Queue");

			logger.error(e.getMessage());

			MessageDrivenBean.sendMail(e.getMessage());

		}

		finally

		{
			try {

				if (queueSession != null)
					queueSession.close();

				if (queueConnection != null)
					queueConnection.close();

			}

			catch (Exception e)

			{
				logger.error(e.getMessage());
			}

		}

	}

}
