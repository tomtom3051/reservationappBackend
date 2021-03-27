package net.sijbers.csia.reservationApp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.Executor;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service("EmailService")
public class EmailService {
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

//https://mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example
//https://support.google.com/accounts/answer/185833?p=InvalidSecondFactor
	
	
	@Value("${gmail.username}")
	String gmailUser;
	@Value("${gmail.password}")
	String gmailPaswword;
	@Value("${gmail.smtphost}")
	String gmailSMTPHost;
	@Value("${gmail.smtpport}")
	String gmailSMTPPort;
	
	@Autowired
	@Qualifier("threadPoolTaskExecutor")
	Executor threadPoolTaskExecutor;
	
	
	public void startMailerThread(String emailTO, String subject, String messageBody) {
		//ThreadPoolExecutor mailerThreadPool  = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		threadPoolTaskExecutor.execute(() -> {
			//wachteffe();
			SendEmailSSL2F(emailTO,subject, messageBody );
		});
		
	}
	/*
    private void wachteffe() {
        int i = 0;
        while (i<10) {
        	logger.info("New Thread is running..." + i++);
            try {
                //Wait for one sec so it doesn't print too fast
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    */
    
    
    

	public void SendEmailTLS() {
		final String username = "unifi@sijbers.net";
		final String password = "XXXXX";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); // TLS

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("unifi@sijbers.net"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("frank@sijbers.net"));
			message.setSubject("Testing Gmail TLS");
			message.setText("Dear Mail Crawler," + "\n\n Please do not spam my email!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void SendEmailSSL() {
		final String username = "unifi@sijbers.net";
		final String password = "XXXXX";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("unifi@sijbers.net"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("frank@sijbers.net"));
			message.setSubject("Testing Gmail SSL");
			message.setText("Dear Mail Crawler," + "\n\n Please do not spam my email!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	
	public void SendEmailSSL2F(String emailTO, String subject, String messageBody) {

		Properties prop = new Properties();
		prop.put("mail.smtp.host", gmailSMTPHost);
		prop.put("mail.smtp.port", gmailSMTPPort);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", gmailSMTPPort);
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(gmailUser, gmailPaswword);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(gmailUser));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTO));
			message.setSubject(subject);
			message.setText(messageBody);

			Transport.send(message);

			logger.info("mail sent to {}", emailTO);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void SendEmailSSL2F() {
		final String username = "frank@sijbers.net";
		final String password = "nzemmeorxnnopmyg";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("frank@sijbers.net"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("frank@sijbers.net"));
			message.setSubject("Testing Gmail SSL 2FA");
			message.setText("Dear Mail Crawler 2FA," + "\n\n Please do not spam my email!  2FA");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}