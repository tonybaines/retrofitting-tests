package com.bt.anonymous;

/*
 * In real life this is an enterprise EJB with an interesting extra method, a 
 * simple implementation here to reduce the noise.
 */
public class MessageDrivenBean {

	public static final String JMS_FACTORY = "TEST-JMS-FACTORY";
	public static final String REDIRECT_QUEUE = "TEST-REDIRECT-QUEUE";
	public static final String RESPONSE_QUEUE = "TEST-RESPONSE-QUEUE";
	public static boolean sendMail(String message) {
// This was the original method
//		try
//		{                                                                
//			String[] cmd = {"/bin/sh",
//					"-c", 
//					"echo $HOSTNAME' : '`date`' : Internal problem  during MDB execution.\n'"
//						+message
//						+"'\n Please check the logs for *** for more details ' | mailx -s Error_in_****** [hardcoded email removed] "};
//
//			Runtime.getRuntime().exec(cmd);  
//			return true;
//		}
//		catch (Exception err) 
//		{  
//			return false;                                                                                                                                                                       
//		}
		throw new UnsupportedOperationException("Nope, sorry.  This is too complex for a test");
	}
}

