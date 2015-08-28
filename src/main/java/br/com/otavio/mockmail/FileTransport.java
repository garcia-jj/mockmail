package br.com.otavio.mockmail;

import static br.com.otavio.mockmail.Preconditions.checkNotEmptyString;
import static br.com.otavio.mockmail.Preconditions.checkWritable;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.newOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;

/**
 * A transport implementation that writes the mail message into a file.
 * 
 * @author Otávio S Garcia
 */
public class FileTransport extends Transport {

	private static final String PATH_PROPERTY = "mail.transport.file.path";
	private static final String APPEND_PROPERTY = "mail.transport.file.append";

	private final Properties properties;

	public FileTransport(Session session, URLName urlname) {
		super(session, urlname);
		properties = session.getProperties();
	}

	@Override
	public void sendMessage(Message msg, Address[] addresses)
		throws MessagingException {
		final String filePath = properties.getProperty(PATH_PROPERTY);
		checkNotEmptyString(filePath, "Property '%s' is required to use file transport.", PATH_PROPERTY);

		final Path file = Paths.get(filePath);
		checkWritable(file.getParent(), "The parent directory '%s' should be writable.", file.getParent());

		final OpenOption[] options = getFileOptions(file);

		try (OutputStream outputStream = newOutputStream(file, options)) {
			msg.writeTo(outputStream);
			outputStream.write(newLine());
			outputStream.write(newLine());

		} catch (IOException e) {
			throw new MessagingException("An error occur when write the email.", e);
		}
	}

	private OpenOption[] getFileOptions(Path file) {
		boolean append = Objects.equals(properties.getProperty(APPEND_PROPERTY), "true");

		if (append && exists(file)) {
			return new StandardOpenOption[] { StandardOpenOption.APPEND };
		}

		return new StandardOpenOption[0];
	}

	private byte[] newLine() {
		return System.getProperty("line.separator").getBytes();
	}
}