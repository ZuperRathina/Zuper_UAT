package PageObjectRep;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.Status;
import BaseTest.Baseclass;
import UtilityPackages.Non_WebDriver_Util;

public class ZuperConnectPageFactory extends Baseclass {

	private WebDriver driver;

	public ZuperConnectPageFactory() {

		this.driver = Baseclass.getDriver();

	}

	private By button_NewMessage = By.xpath("//button[@title='New Message']");
	private By textArea_MessageField = By.xpath("//div[@id='messageInput']//textarea");
	private By button_SendMessage = By.xpath("//span[text()='Send']//parent::button");
	private By element_LastSendMessage = By.xpath("//span[text()=' Today ']/parent::div/parent::div/div[last()]//p");
	private By element_LastReceivedMessage = By.xpath("//span[text()='New']/parent::div/parent::div/div[last()]//p");
	private By button_MarkAsRead = By.xpath("//*[contains(text(),'Mark as read')]");
	private By element_TextMessage = By.xpath("//span[text()='Text Message']");
	private By button_AddAttachments = By.xpath("//app-attachment-source-menu//button");
	private By button_ClickToUpload = By.xpath("//input[@type='file']");
	private By button_DoneOnAttachments = By.xpath("//button[text()=' Done ']");
	private By text_100Per = By.xpath("//div[text()=' 100%']");
	private By tab_Calls = By.xpath("(//button[normalize-space()='Calls'])[1]");
	private By tab_Conversation = By.xpath("//a[text()=' Conversations ']");
	private By element_AllInboxes = By.xpath("//*[text()='All Numbers']");
	private By element_ToInput = By.xpath("//ng-select//input");
	private By element_UATTest = By.xpath("//div[@class='cdk-overlay-pane']//*[@title='Twilio Rtna']");
	private By element_InboxDropDown = By.xpath("//app-call-inbox//ng-icon");
	private By element_firstListingCustomer = By.xpath("(//app-conversation-list//p[@title])[1]");

	public void navigateToReqCustomerConversation(WebDriver driver, String customerName) {
		Non_WebDriver_Util.waitThread(2);
		By reqCustomerConversation = null;
		if (DashboardPageFactory.driverValue.equalsIgnoreCase(driver.toString())) {
			reqCustomerConversation = By.xpath(String.format("//app-conversation-list//p[@title='%s']", customerName));
		} else {
			reqCustomerConversation = By.xpath(String.format("//app-conversation-list//p[@title='%s']", customerName));
		}
		new WebDriverWait(driver, Duration.ofSeconds(35))
				.until(ExpectedConditions.elementToBeClickable(reqCustomerConversation));
		driver.findElement(reqCustomerConversation).click();
	}

	public void createNewMessage(WebDriver driver) {
		Non_WebDriver_Util.waitForBeClickable(driver, element_InboxDropDown, 5);
		driver.findElement(element_InboxDropDown).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, element_UATTest, 25);
		driver.findElement(element_UATTest).click();
		Non_WebDriver_Util.waitForBeClickable(driver, button_NewMessage, 15);
		driver.findElement(button_NewMessage).click();
		Non_WebDriver_Util.waitThread(1);
		driver.findElement(element_ToInput).click();
		driver.findElement(element_ToInput).sendKeys(prop.getProperty("messageToSent"));
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.pageEnterKeyClick(driver);
	}

	public void selectAllInboxes(WebDriver driver) {
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, element_InboxDropDown, 35);
		driver.findElement(element_InboxDropDown).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, element_AllInboxes, 25);
		driver.findElement(element_AllInboxes).click();
	}

	public void navigateToTheRecentReceivedMessage(WebDriver driver) {
		Non_WebDriver_Util.waitThread(3);
		driver.findElement(element_firstListingCustomer).click();
	}

	public void checkTheIncommingMessage(WebDriver driver) {
		Non_WebDriver_Util.waitThread(3);
		new WebDriverWait(driver, Duration.ofSeconds(25))
				.until(ExpectedConditions.elementToBeClickable(element_LastReceivedMessage));
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.jsScrollToElement(driver, element_LastReceivedMessage);
		String lastReceivedMessage = driver.findElement(element_LastReceivedMessage).getText().trim();
		if (lastReceivedMessage.equalsIgnoreCase(sentMessage)) {
			if (DashboardPageFactory.driverValue.equalsIgnoreCase(driver.toString())) {
				logger.info("Agent 2 Sent Message is received to Agent 1 and the Message is: " + lastReceivedMessage);
				Non_WebDriver_Util.testCase.log(Status.PASS,
						"SAgent 2 Sent Message is received to Agent 1 and the Message is: " + lastReceivedMessage
								+ "' and this conversation is Marked as Read");
				System.out.println(
						"Agent 2 Sent Message is received to Agent 1 and the Message is: " + lastReceivedMessage);
			} else {
				logger.info("Agent 1 Sent Message is received to Agent 2 and the Message is: " + lastReceivedMessage);
				Non_WebDriver_Util.testCase.log(Status.PASS,
						"Agent 1 Sent Message is received to Agent 2 and the Message is: " + lastReceivedMessage
								+ "' and this conversation is Marked as Read");
				System.out.println(
						"Agent 1 Sent Message is received to Agent 2 and the Message is: " + lastReceivedMessage);
			}
			markTheConversationAsRead(driver);
			Non_WebDriver_Util.getScreenshot(driver, "");
		} else {
			logger.info("Message is not received");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Message is not received");
		}
		sentMessage = "";
	}

	public void markTheConversationAsRead(WebDriver driver) {
		try {
			Non_WebDriver_Util.waitThread(2);
			new WebDriverWait(driver, Duration.ofSeconds(25))
					.until(ExpectedConditions.elementToBeClickable(button_MarkAsRead));
			driver.findElement(button_MarkAsRead).click();
			Non_WebDriver_Util.waitThread(1);
			logger.info("Conversation is Marked as Read");
			System.out.println("Conversation is Marked as Read");
		} catch (Exception e) {
			logger.info("Conversation is NOT Marked as Read");
			System.out.println("Conversation is NOT Marked as Read");
		}
	}

	public void checkTheIncommingMMSMessage(WebDriver driver) {
		Non_WebDriver_Util.waitThread(3);
		String lastReceivedMessage = driver.findElement(element_LastReceivedMessage).getText().trim();
		if (lastReceivedMessage.equalsIgnoreCase(sentMMSMessage)) {
			if (DashboardPageFactory.driverValue.equalsIgnoreCase(driver.toString())) {
				logger.info("Agent 2 Sent MMS is received to Agent 1 and sent text is: '" + lastReceivedMessage + "'");
				Non_WebDriver_Util.testCase.log(Status.PASS,
						"Agent 2 Sent MMS is received to Agent 1 and sent text is: '" + lastReceivedMessage + "'");
				System.out.println(
						"Agent 2 Sent MMS is received to Agent 1 and sent text is: '" + lastReceivedMessage + "'");
			} else {
				logger.info("Agent 1 Sent MMS is received to Agent 2 and sent text is: '" + lastReceivedMessage + "'");
				Non_WebDriver_Util.testCase.log(Status.PASS,
						"Agent 1 Sent MMS is received to Agent 2 and sent text is: '" + lastReceivedMessage + "'");
				System.out.println(
						"Agent 1 Sent MMS is received to Agent 2 and sent text is: '" + lastReceivedMessage + "'");
			}
			markTheConversationAsRead(driver);
			Non_WebDriver_Util.getScreenshot(driver, "");
		} else {
			logger.info("MMS is not received");
			Non_WebDriver_Util.testCase.log(Status.PASS, "MMS is not received");
		}
		sentMMSMessage = "";
	}

	public void switchBtwCallToConv() {
		driver.findElement(tab_Calls).click();
		Non_WebDriver_Util.waitThread(2);
		driver.findElement(tab_Conversation).click();
	}

	public void navigateToCallsTab() {
		Non_WebDriver_Util.waitForBeClickable(driver, tab_Calls, 15);
		driver.findElement(tab_Calls).click();
		Non_WebDriver_Util.waitThread(1);
	}

	public static String sentMessage;
	public static String sentMMSMessage;

	public void clickTextMessageInputSection(WebDriver driver) {
		driver.findElement(element_TextMessage).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.tabKeyClick(driver);
	}

	public void addAttachmentsToMMS(WebDriver driver) {
		Non_WebDriver_Util.waitThread(2);
		new WebDriverWait(driver, Duration.ofSeconds(25))
				.until(ExpectedConditions.elementToBeClickable(button_AddAttachments));
		Non_WebDriver_Util.jsScrollAndActionClick(driver, button_AddAttachments);
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.uploadMultipleFiles(driver,
				System.getProperty("user.dir") + prop.getProperty("fileToAttach"), button_ClickToUpload);
		Non_WebDriver_Util.waitForVisible(driver, text_100Per, 50);
		Non_WebDriver_Util.waitThread(5);
		driver.findElement(button_DoneOnAttachments).click();
		Non_WebDriver_Util.waitThread(1);
		driver.findElement(textArea_MessageField).clear();
		Non_WebDriver_Util.waitThread(2);
		if (DashboardPageFactory.driverValue.equalsIgnoreCase(driver.toString())) {
			creatingMessage("Zuper New MMS @ ", driver);
			sentMMSMessage = driver.findElement(element_LastSendMessage).getText();
			logger.info("Agent 1 sent a MMS to Agent 2 and the added text is: '" + sentMMSMessage + "'");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Agent 1 sent a MMS to Agent 2 and the added text is: '" + sentMMSMessage + "'");
			System.out.println("Agent 1 sent a MMS to Agent 2 and the added text is: '" + sentMMSMessage + "'");			
		} else {
			creatingMessage("Zuper New Reply MMS @ ", driver);
			sentMMSMessage = driver.findElement(element_LastSendMessage).getText();
			logger.info("Agent 2 sent a reply MMS to Agent 1 and the added text is: '" + sentMMSMessage + "'");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Agent 2 sent a reply MMS to Agent 1 and the added text is: '" + sentMMSMessage + "'");
			System.out.println("Agent 2 sent a reply MMS to Agent 1 and the added text is: '" + sentMMSMessage + "'");
		}
		Non_WebDriver_Util.getScreenshot(driver, "");
	}
	
	public void enterTextAndSendMessage(WebDriver driver) {
		if (DashboardPageFactory.driverValue.equalsIgnoreCase(driver.toString())) {
			creatingMessage("Zuper New Message @ ", driver);
			logger.info("Agent 1 sent a message to Agent 2 and the Message is: '" + sentMessage + "'");
			System.out.println("Agent 1 sent a message to Agent 2 and the Message is: '" + sentMessage + "'");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Agent 1 sent a message to Agent 2 and the Message is: '" + sentMessage + "'");
		} else {
			creatingMessage("Zuper New Reply Message @ ", driver);
			logger.info("Agent 2 sent a reply message to Agent 1 and the Message is: '" + sentMessage + "'");
			System.out.println("Agent 2 sent a reply message to Agent 1 and the Message is: '" + sentMessage + "'");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Agent 2 sent a reply message to Agent 1 and the Message is: '" + sentMessage + "'");
		}
		Non_WebDriver_Util.getScreenshot(driver, "");
	}
	
	public void creatingMessage(String message, WebDriver driver) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDateTime = LocalDateTime.now().format(formatter);
		driver.findElement(textArea_MessageField).sendKeys(message + formattedDateTime);
		new WebDriverWait(driver, Duration.ofSeconds(25))
				.until(ExpectedConditions.elementToBeClickable(button_SendMessage));
		if (driver.findElement(button_SendMessage).isEnabled()) {
			driver.findElement(button_SendMessage).click();
		}
		Non_WebDriver_Util.waitThread(3);
		sentMessage = driver.findElement(element_LastSendMessage).getText();
		Non_WebDriver_Util.waitThread(1);
	}

	public void navigatingFromTwilioPageToZuperDashboardPage() {
		Non_WebDriver_Util.waitThread(5);
		Non_WebDriver_Util.returnToParentTab(driver, DashboardPageFactory.customerWindow);
		logger.info("Navigating to Zuper Dashboard page");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Navigating to Zuper Dashboard page");
	}

	public void checkingAttachmnetsCountAndURL(String region, WebDriver driver, String apiKey) {
		Non_WebDriver_Util.waitThread(1);
		String currentURL = driver.getCurrentUrl();
		String[] urlParts = currentURL.split("/");
		String conversationUID = urlParts[urlParts.length - 1];
		System.out.println("Conversation UID is: " + conversationUID);	
		String conversationURL = region + conversationUID;
		System.out.println("Conversation URL is: " + conversationURL);	
		Non_WebDriver_Util.printMediaDetails(conversationURL, apiKey, driver);
		conversationURL = null;
		
	}
}
