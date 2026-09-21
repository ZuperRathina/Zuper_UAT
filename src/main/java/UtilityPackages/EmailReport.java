package UtilityPackages;

import java.io.File;
import java.util.Properties;

import org.testng.annotations.Test;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailReport {

	@Test
	public static void sendEmailReport() {
		// String reportPath
		String senderEmail = "zupertesting@gmail.com";
		String appPassword = "suejntlrnlgnxipi";
		String receiverEmail = "rathinakumar.k@zuper.co";
		//String receiverCCEmail = "jeevitha.k@zuper.co";

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.debug", "false");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, appPassword);
			}
		});
		session.setDebug(false);
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("vignesh.ss@zuper.co"));
			message.addRecipient(Message.RecipientType.CC,
			        new InternetAddress(receiverEmail));

//			message.addRecipient(Message.RecipientType.CC,
//			        new InternetAddress("suriyapathy.b@zuper.co"));
			
			message.setSubject("Zuper Connect Sanity Check Report");

			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText("Hi, \n\nPlease find the attached 'Zuper Connect' santity report.\n\nThanks!!!");

			MimeBodyPart attachmentPart = new MimeBodyPart();
			//String reportPath = System.getProperty("user.dir") + "/ExtentReport.html";
			attachmentPart.attachFile(new File(Non_WebDriver_Util.reportPath));

			MimeMultipart multiPart = new MimeMultipart();
			multiPart.addBodyPart(textPart);
			multiPart.addBodyPart(attachmentPart);
			message.setContent(multiPart);
			Transport.send(message);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

//	public static void main(String[] args) {
//		sendEmailReport();
	//
	// }

}
