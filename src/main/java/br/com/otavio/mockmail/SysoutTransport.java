package br.com.otavio.mockmail;

import java.io.IOException;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;

/**
 * A transport implementation that writes the mail message into console using {@link System#out}.
 * 
 * @author Otávio S Garcia
 */
public class SysoutTransport extends Transport {

	public SysoutTransport(Session session, URLName urlname) {
		super(session, urlname);
	}

	@Override
	public void sendMessage(Message msg, Address[] addresses)
		throws MessagingException {
		try {
			msg.writeTo(System.out);

		} catch (IOException e) {
			throw new MessagingException("An error occur when write the email.", e);
		}
	}
}