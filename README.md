# Mockmail

Mockmail is a simple JavaMail provider to allow you to prevent sending e-mail messages using a real transport, useful when we are testing an application. There are providers to print the message into system console or in a file.

## How to install

If you are using Maven as dependency manager you only need to add this line in your `pom.xml`:

```xml
<dependency>
	<groupId>br.com.otavio</groupId>
	<artifactId>mockmail</artifactId>
	<version>0.0.1</version>
</dependency>
```

Or you can also download the artifact [here!](http://repo1.maven.org/maven2/br/com/otavio/mockmail/0.0.1/mockmail-0.0.1.jar).

## How to use

### SysoutTransport

Allow you to print the e-mail message using system console.

```java
public static void main(String[] args) throws Exception {
	final Properties props = new Properties();
	props.put("mail.transport.protocol", "sysout");

	final Session session = Session.getInstance(props);
	final Message msg = new MimeMessage(session);
	msg.setRecipient(Message.RecipientType.TO, new InternetAddress(randomEmail()));
	msg.setFrom(new InternetAddress(randomEmail()));
	msg.setSubject("teste");
	msg.setContent("teste", "text/plain");
	msg.saveChanges();

	session.getTransport().sendMessage(msg, msg.getAllRecipients());
}
```
### FileTransport

Allow you to print the e-mail message into a file.

```java
public static void main(String[] args) throws Exception {
	final Properties props = new Properties();
	props.put("mail.transport.protocol", "file");
	props.put("mail.transport.file.path", "/tmp/maillog.txt");
	props.put("mail.transport.file.append", "false");

	final Session session = Session.getInstance(props);

	final Message msg = new MimeMessage(session);
	msg.setRecipient(Message.RecipientType.TO, new InternetAddress(randomEmail()));
	msg.setFrom(new InternetAddress(randomEmail()));
	msg.setSubject("teste");
	msg.setContent("teste", "text/plain");
	msg.saveChanges();

	session.getTransport().sendMessage(msg, msg.getAllRecipients());
}
```
