package PageObjectRep;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import BaseTest.Baseclass;
import UtilityPackages.Non_WebDriver_Util;

public class ZuperConnectPageFactory extends Baseclass {

	private WebDriver driver;

	public ZuperConnectPageFactory() {

		this.driver = Baseclass.getDriver();

	}

	private By button_NewMessage = By.xpath("//button[@title='New Message']");
//	private By element_SelectingToNumber = By.xpath("//hlm-menu-group//p[text()='(+1) 318 310 4737']");
	private By textArea_MessageField = By.xpath("//div[@id='messageInput']//textarea");
	private By button_SendMessage = By.xpath("//span[text()='Send']//parent::button");
	private By element_LastSendMessage = By.xpath("//span[text()=' Today ']/parent::div/parent::div/div[last()]//p");
	private By element_LastReceivedMessage = By.xpath("//span[text()='New']/parent::div/parent::div/div[last()]//p");
	private By element_LastReceivedMMS = By.xpath("(//span[text()='New'])[2]/parent::div/parent::div/div[last()]//p");
	//private By element_MessagesList = By.xpath("//app-conversation-list/div/div[2]/div");
	private By button_MarkAsRead = By.xpath("//*[contains(text(),'Mark as read')]");
	private By element_TextMessage = By.xpath("//span[text()='Text Message']");
	private By button_AddAttachments = By.xpath("//div[@id='messageInput']/div/div[2]/div[1]/div/button");
	private By button_ClickToUpload = By.xpath("//input[@type='file']");
	private By button_DoneOnAttachments = By.xpath("//button[text()=' Done ']");
	private By text_100Per = By.xpath("//div[text()=' 100%']");
	private By tab_Calls = By.xpath("//a[text()=' Calls ']");
	private By tab_Conversation = By.xpath("//a[text()=' Conversations ']");
	private By element_AllInboxes = By.xpath("//p[text()='All Inboxes']");
	private By element_ToInput = By.xpath("//ng-select//input");
	private By element_UATTest = By.xpath("//p[@title='UAT @15']");
	private By element_InboxDropDown = By.xpath("//app-call-inbox//ng-icon");
	private By element_firstListingCustomer = By.xpath("(//app-conversation-list//p[@title])[1]");
	
	public void createNewMessage(WebDriver driver) {	
		Non_WebDriver_Util.waitForBeClickable(driver, element_InboxDropDown, 5);
		driver.findElement(element_InboxDropDown).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, element_UATTest, 5);
		driver.findElement(element_UATTest).click();
		Non_WebDriver_Util.waitForBeClickable(driver, button_NewMessage, 5);
		driver.findElement(button_NewMessage).click();
		Non_WebDriver_Util.waitThread(1);
		driver.findElement(element_ToInput).click();
		driver.findElement(element_ToInput).sendKeys(prop.getProperty("messageToSent"));
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.pageEnterKeyClick(driver);
	}
	
	public void selectAllInboxes(WebDriver driver ) {
		Non_WebDriver_Util.waitForBeClickable(driver, element_InboxDropDown, 5);
		driver.findElement(element_InboxDropDown).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, element_AllInboxes, 5);
		driver.findElement(element_AllInboxes).click();
	}
	
	public void navigateToTheRecentReceivedMessage(WebDriver driver) {
		Non_WebDriver_Util.waitThread(2);
		driver.findElement(element_firstListingCustomer).click();
//		List<WebElement> messageList = driver.findElements(element_MessagesList);
//		int messagesCount = messageList.size();
//		for (int i = 1; i <= messagesCount; i++) {
//			WebElement formName = driver
//					.findElement(By.xpath("//app-conversation-list/div/div[2]/div[" + i + "]//p[1]"));
//			if (formName.getText().trim().equalsIgnoreCase(prop.getProperty("customerNumber"))) {	
//				Non_WebDriver_Util.waitThread(2);
//				formName.click();	
//				break;
//			}else {
//				driver.findElement(element_firstListingCustomer).click();
//			}
//		}
	}

	public void checkTheIncommingMMS(WebDriver driver) {
		Non_WebDriver_Util.waitThread(3);
		String lastReceivedMessage = driver.findElement(element_LastReceivedMMS).getText().trim();
		if (lastReceivedMessage.equalsIgnoreCase(sentMessage)) {
			Non_WebDriver_Util.waitForBeClickable(driver, button_MarkAsRead, 5);
			driver.findElement(button_MarkAsRead).click();
			Non_WebDriver_Util.waitThread(1);
			logger.info("Sent Message is received to the another user and this conversation is Marked as Read");
			String screenshotPathMMSReceived = Non_WebDriver_Util.getScreenshot(driver, "MMSReceived");
			try {
				Non_WebDriver_Util.testCase.log(Status.PASS,
						"Sent Message is received to the another user and this conversation is Marked as Read",
						MediaEntityBuilder.createScreenCaptureFromPath(screenshotPathMMSReceived).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			logger.info("Message is not received to another user");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Message is not received to another user");
		}
	}

	public void checkTheIncommingMessage(WebDriver driver) {
		Non_WebDriver_Util.waitThread(3);
		String lastReceivedMessage = driver.findElement(element_LastReceivedMessage).getText().trim();
		if (lastReceivedMessage.equalsIgnoreCase(sentMessage)) {
			Non_WebDriver_Util.waitForBeClickable(driver, button_MarkAsRead, 5);
			driver.findElement(button_MarkAsRead).click();
			Non_WebDriver_Util.waitThread(1);
			logger.info("Sent Message is received to the another user, Message is '"+lastReceivedMessage+"' and this conversation is Marked as Read");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Sent Message is received to the another user, Message is '"+lastReceivedMessage+"' and this conversation is Marked as Read");
			System.out.println("Sent Message is received to the another user, Message is '"+lastReceivedMessage+"' and this conversation is Marked as Read");
			Non_WebDriver_Util.getScreenshot(driver, "");
		} else {
			logger.info("Message is not received to another user");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Message is not received to another user");
		}
	}
	
	public void checkTheIncommingMMSMessage(WebDriver driver) {
		Non_WebDriver_Util.waitThread(3);
		String lastReceivedMessage = driver.findElement(element_LastReceivedMessage).getText().trim();
		if (lastReceivedMessage.equalsIgnoreCase(sentMMSMessage)) {
			Non_WebDriver_Util.waitForBeClickable(driver, button_MarkAsRead, 5);
			driver.findElement(button_MarkAsRead).click();
			Non_WebDriver_Util.waitThread(1);
			logger.info("Sent MMS is received to the another user, text is '"+lastReceivedMessage+"' and this conversation is Marked as Read");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Sent MMS is received to the another user, text is '"+lastReceivedMessage+"' and this conversation is Marked as Read");
			System.out.println("Sent MMS is received to the another user, text is '"+lastReceivedMessage+"' and this conversation is Marked as Read");
			Non_WebDriver_Util.getScreenshot(driver, "");
		} else {
			logger.info("MMS is not received to another user");
			Non_WebDriver_Util.testCase.log(Status.PASS, "MMS is not received to another user");
		}
		sentMMSMessage="";
	}
	
	public void checkTheIncommingMMSMessageInAdminUser(WebDriver driver) {
		Non_WebDriver_Util.waitThread(3);
		String lastReceivedMessage = driver.findElement(element_LastReceivedMessage).getText().trim();
		if (lastReceivedMessage.equalsIgnoreCase(sentMMSMessage)) {
			Non_WebDriver_Util.waitForBeClickable(driver, button_MarkAsRead, 5);
			driver.findElement(button_MarkAsRead).click();
			Non_WebDriver_Util.waitThread(1);
			logger.info("Sent MMS is received to the admin user, added text is '"+lastReceivedMessage+"' and updating this conversation to Marked as Read");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Sent MMS is received to the admin user, added text is '"+lastReceivedMessage+"' and updating this conversation to Marked as Read");
			System.out.println("Sent MMS is received to the admin user, added text is '"+lastReceivedMessage+"' and updating this conversation to Marked as Read");
			Non_WebDriver_Util.getScreenshot(driver, "");
		} else {
			logger.info("MMS is not received to Admin user");
			Non_WebDriver_Util.testCase.log(Status.PASS, "MMS is not received to Admin user");
		}
		sentMMSMessage="";
	}

	public void switchBtwCallToConv() {
		driver.findElement(tab_Calls).click();
		Non_WebDriver_Util.waitThread(2);
		driver.findElement(tab_Conversation).click();
	}

	public static String sentMessage;
	public static String sentMMSMessage;
	public static String sentMessageFromAnotherUser;

	public void checkTheIncommingMessageFromAdminUser(WebDriver driver) {	
		Non_WebDriver_Util.waitForVisible(driver, button_MarkAsRead, 10);
			String lasttReceivedMessage = driver.findElement(element_LastReceivedMessage).getText().trim();
			if (lasttReceivedMessage.equalsIgnoreCase(sentMessageFromAnotherUser)) {
				Non_WebDriver_Util.waitForBeClickable(driver, button_MarkAsRead, 5);
				driver.findElement(button_MarkAsRead).click();
				Non_WebDriver_Util.waitThread(1);
				logger.info("Sent Message is received to the admin user, Message is '"+lasttReceivedMessage+"' and this conversation is Marked as Read");
				Non_WebDriver_Util.testCase.log(Status.PASS,
						"Sent Message is received to the admin user, Message is '"+lasttReceivedMessage+"' and this conversation is Marked as Read");
				System.out.println("Sent Message is received to the admin user, Message is '"+lasttReceivedMessage+"' and this conversation is Marked as Read");
				Non_WebDriver_Util.getScreenshot(driver, "");
			} else {
				logger.info("Message is not received to admin user");
				Non_WebDriver_Util.testCase.log(Status.PASS, "Message is not received to admin user");
			}
		}

	public void clickTextMessageInputSection(WebDriver driver) {
		driver.findElement(element_TextMessage).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.tabKeyClick(driver);
	}

	public void addAttachmentsToMMS(WebDriver driver) {
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.waitForBeClickable(driver, button_AddAttachments, 10);
		Non_WebDriver_Util.jsScrollAndActionClick(driver, button_AddAttachments);
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.uploadMultipleFiles(driver, System.getProperty("user.dir")+prop.getProperty("fileToAttach"), button_ClickToUpload);
		Non_WebDriver_Util.waitForVisible(driver, text_100Per, 50);
		Non_WebDriver_Util.waitThread(5);
		driver.findElement(button_DoneOnAttachments).click();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDateTime = LocalDateTime.now().format(formatter);
		driver.findElement(textArea_MessageField).clear();
		Non_WebDriver_Util.waitThread(2);
		driver.findElement(textArea_MessageField).sendKeys("Zuper New MMS @ " + formattedDateTime);
		Non_WebDriver_Util.waitForBeClickable(driver, button_SendMessage, 15);
		if(driver.findElement(button_SendMessage).isEnabled()) {
			driver.findElement(button_SendMessage).click();
		}
		Non_WebDriver_Util.waitThread(3);
		ZuperConnectPageFactory.sentMMSMessage = driver.findElement(element_LastSendMessage).getText();
		logger.info("The MMS was sent to another user by an admin user and added text is "+ZuperConnectPageFactory.sentMMSMessage);
		Non_WebDriver_Util.testCase.log(Status.PASS, "The MMS was sent to another user by an admin user and added text is "+ZuperConnectPageFactory.sentMMSMessage);
		System.out.println();
		Non_WebDriver_Util.getScreenshot(driver, "The MMS was sent to another user by an admin user and added text is "+ZuperConnectPageFactory.sentMMSMessage);
	}
	
	public void addAttachmentsToMMSFromAnotherUser(WebDriver driver) {
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.waitForBeClickable(driver, button_AddAttachments, 10);
		Non_WebDriver_Util.jsScrollAndActionClick(driver, button_AddAttachments);
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.uploadMultipleFiles(driver, System.getProperty("user.dir")+prop.getProperty("fileToAttach"), button_ClickToUpload);
		Non_WebDriver_Util.waitThread(15);
		driver.findElement(button_DoneOnAttachments).click();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDateTime = LocalDateTime.now().format(formatter);
		driver.findElement(textArea_MessageField).clear();
		Non_WebDriver_Util.waitThread(2);
		driver.findElement(textArea_MessageField).sendKeys("Zuper Reply MMS @ " + formattedDateTime);
		Non_WebDriver_Util.waitForBeClickable(driver, button_SendMessage, 15);
		if(driver.findElement(button_SendMessage).isEnabled()) {
			driver.findElement(button_SendMessage).click();
		}
		Non_WebDriver_Util.waitThread(3);
		ZuperConnectPageFactory.sentMMSMessage = driver.findElement(element_LastSendMessage).getText();
		logger.info("The MMS was sent to admin user by an another user and the added text is "+ZuperConnectPageFactory.sentMMSMessage);
		Non_WebDriver_Util.testCase.log(Status.PASS, "The MMS was sent to admin user by an another user and the added text is "+ZuperConnectPageFactory.sentMMSMessage);
		System.out.println("The MMS was sent to admin user by an another user and the added text is "+ZuperConnectPageFactory.sentMMSMessage);
		Non_WebDriver_Util.getScreenshot(driver, "");
	}

	public void enterTextAndSendMessage(WebDriver driver) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDateTime = LocalDateTime.now().format(formatter);
		driver.findElement(textArea_MessageField).sendKeys(" Zuper New Message @ " + formattedDateTime);
		Non_WebDriver_Util.waitForBeClickable(driver, button_SendMessage, 15);
		if(driver.findElement(button_SendMessage).isEnabled()) {
			driver.findElement(button_SendMessage).click();
		}	
		Non_WebDriver_Util.waitThread(3);
		ZuperConnectPageFactory.sentMessage = driver.findElement(element_LastSendMessage).getText();
		Non_WebDriver_Util.waitThread(1);
		logger.info("The message was sent to another user by an admin user and the Message is "+ZuperConnectPageFactory.sentMessage);
		System.out.println("The message was sent to another user by an admin user and the Message is "+ZuperConnectPageFactory.sentMessage);
		Non_WebDriver_Util.testCase.log(Status.PASS, "The message was sent to another user by an admin user and the Message is "+ZuperConnectPageFactory.sentMessage);
		Non_WebDriver_Util.getScreenshot(driver, "");
	}

	public void enterTextAndSendMessageFromAnotherUser(WebDriver driver) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDateTime = LocalDateTime.now().format(formatter);
		driver.findElement(textArea_MessageField).sendKeys(" Zuper Reply Message @ " + formattedDateTime);
		Non_WebDriver_Util.waitForBeClickable(driver, button_SendMessage, 15);
		if(driver.findElement(button_SendMessage).isEnabled()) {
			driver.findElement(button_SendMessage).click();
		}		
		Non_WebDriver_Util.waitThread(2);
		ZuperConnectPageFactory.sentMessageFromAnotherUser = driver.findElement(element_LastSendMessage).getText()
				.trim();
		logger.info("Reply Message was sent from the another user to admin user and the Message is "+ZuperConnectPageFactory.sentMessageFromAnotherUser );
		Non_WebDriver_Util.testCase.log(Status.PASS, "Reply Message was sent from the another user to admin user and the Message is "+ZuperConnectPageFactory.sentMessageFromAnotherUser);
		System.out.println("Reply Message was sent from the another user to admin user and the Message is "+ZuperConnectPageFactory.sentMessageFromAnotherUser );
		Non_WebDriver_Util.getScreenshot(driver, "");
	}
	
	

	public void navigatingFromTwilioPageToZuperDashboardPage() {
		Non_WebDriver_Util.waitThread(5);
		Non_WebDriver_Util.returnToParentTab(driver, DashboardPageFactory.customerWindow);
		logger.info("Navigating to Zuper Dashboard page");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Navigating to Zuper Dashboard page");
	}
}
