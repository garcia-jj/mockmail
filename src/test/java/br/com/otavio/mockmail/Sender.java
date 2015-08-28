package br.com.otavio.mockmail;

import static java.lang.System.currentTimeMillis;
import static java.util.UUID.randomUUID;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Sender {

	public static void main(String[] args)
		throws Exception {

		final Properties props = new Properties();

		props.put("mail.transport.protocol", "file");
		props.put("mail.transport.file.path", "/tmp/mail.txt");
		props.put("mail.transport.file.append", "false");
		props.put("mail.debug", "true");

		final Session session = Session.getInstance(props);

		final Message msg = new MimeMessage(session);

		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(randomEmail()));
		msg.setFrom(new InternetAddress(randomEmail()));
		msg.setSubject("teste");
		msg.setContent("teste", "text/plain");
		msg.saveChanges();

		session.getTransport().sendMessage(msg, msg.getAllRecipients());
	}

	private static String randomEmail() {
		return currentTimeMillis() + "@" + randomUUID() + ".com";
	}
}