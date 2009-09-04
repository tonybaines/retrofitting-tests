package com.bt.anonymous;

import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;

/**
 * Just a simple implementation to let the project compile without the rest of the codebase
 */
public class JNDILookup {

	public static JNDILookup getInstance() {
		return new JNDILookup();
	}

	public Queue getQueue(String responseQueue) {
		return null;
	}

	public QueueConnectionFactory getQueueConnectionFactory(String jmsFactory) {
		return null;
	}

}
