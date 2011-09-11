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
		throw new UnsupportedOperationException("Nope, sorry.  This is too complex for a test");
	}

	public Queue getQueue(String responseQueue) throws NamingException {
		throw new UnsupportedOperationException("Nope, sorry.  This is too complex for a test");
	}

	public QueueConnectionFactory getQueueConnectionFactory(String jmsFactory) throws NamingException {
		throw new UnsupportedOperationException("Nope, sorry.  This is too complex for a test");
	}

}
