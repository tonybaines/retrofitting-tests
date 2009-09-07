package com.bt.anonymous;

import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.NamingException;

/*
 * Just a simple implementation to let the project compile without the rest of the codebase,
 * the real thing does a look-up of objects in a JNDI server.
 */
public class JNDILookup {

	public static JNDILookup getInstance() {
		return new JNDILookup();
	}

	public Queue getQueue(String responseQueue) throws NamingException {
		return null;
	}

	public QueueConnectionFactory getQueueConnectionFactory(String jmsFactory) throws NamingException {
		return null;
	}

}
