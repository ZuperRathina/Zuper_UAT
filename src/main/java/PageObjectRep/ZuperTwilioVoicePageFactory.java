package PageObjectRep;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import com.aventstack.extentreports.Status;
import BaseTest.Baseclass;
import UtilityPackages.Non_WebDriver_Util;

public class ZuperTwilioVoicePageFactory extends Baseclass {

	private WebDriver driver;

	public ZuperTwilioVoicePageFactory() {

		this.driver = Baseclass.getDriver();

	}

	public static By button_StartUpTheDevice = By.cssSelector("button#startup-button");
	private By input_PhoneNumber = By.cssSelector("input#phone-number");
	private By button_CallButton = By.cssSelector("button#button-call");
	private By text_CallInProgress = By.xpath("//h2[text()='Event Log']/following::div/p[last()]");
	private By button_AcceptIncommingCallButton = By.cssSelector("button#button-accept-incoming");
	private By button_HangUpOngoingCall = By.cssSelector("button#button-hangup-incoming");
	private By button_HangUpOutgoingCall = By.cssSelector("button#button-hangup-outgoing");

	public void acceptingTheIncommingCallOnTwilio() {
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.waitForBeClickable(driver, button_StartUpTheDevice, 2);
		driver.findElement(button_StartUpTheDevice).click();
		Non_WebDriver_Util.waitForBeClickable(driver, button_AcceptIncommingCallButton, 25);
		driver.findElement(button_AcceptIncommingCallButton).click();
		String acceptedCallStatus = driver.findElement(text_CallInProgress).getText();
		Non_WebDriver_Util.waitThread(2);
		logger.info("Call received from the Agent 1 (Zuper Admin) and the call status is '" + acceptedCallStatus
				+ "' by Customer");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Call received from the Agent 1 (Zuper Admin) and the call status is '" + acceptedCallStatus
						+ "' by Customer");
		try {
			Non_WebDriver_Util.getScreenshot(driver, "IncomingCallAcceptedInTwilio");
		} catch (Exception e) {

		}
		Non_WebDriver_Util.waitThread(5);

	}

	public void disconnectTheCallFromTwilioEnd() {
		driver.findElement(button_HangUpOngoingCall).click();
		logger.info("The call was disconnected by the Customer");
		Non_WebDriver_Util.testCase.log(Status.PASS, "The call was disconnected by the Customer");
	}

	public void disconnectTheWarmTransferCallFromTwilioEnd() {
		Non_WebDriver_Util.switchToNewTab(driver);
		Non_WebDriver_Util.waitThread(3);
		Non_WebDriver_Util.waitForBeClickable(driver, button_HangUpOutgoingCall, 5);
		driver.findElement(button_HangUpOutgoingCall).click();
		logger.info("The Warm transferred call was disconnected from the Twilio end");
		Non_WebDriver_Util.testCase.log(Status.PASS, "The Warm transferred call was disconnected from the Twilio end");
		driver.close();
	}

	public void navigateToZuperTwilioVoicepage() {
		driver.switchTo().newWindow(WindowType.TAB);
		driver.navigate().to("https://staging.zuperpro.com/api/twilio/");
		Non_WebDriver_Util.waitForBeClickable(driver, button_StartUpTheDevice, 2);
		driver.findElement(button_StartUpTheDevice).click();
		logger.info("Zuper Twilio Voice page is launched and Start the Device functionality is enabled");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Zuper Twilio Voice page is launched and Start the Device functionality is enabled");
	}

	public void startUpTwilio() {
		Non_WebDriver_Util.waitForBeClickable(driver, button_StartUpTheDevice, 2);
		driver.findElement(button_StartUpTheDevice).click();
	}

	public void callingCustomerNumber(String phNumber) {
		Non_WebDriver_Util.waitForBeClickable(driver, input_PhoneNumber, 10);
		driver.findElement(input_PhoneNumber).clear();
		driver.findElement(input_PhoneNumber).sendKeys(phNumber);
		driver.findElement(button_CallButton).click();
		String callStatus = driver.findElement(text_CallInProgress).getText();
		int count = 1;
		if (!callStatus.contains("Call in progress") && count <= 3) {
			count++;
			callStatus = driver.findElement(text_CallInProgress).getText();
		}
		logger.info("Dailed to this '" + phNumber + "' number from Zuper Twilio Voice page and Call Status is "
				+ callStatus);
		Non_WebDriver_Util.testCase.log(Status.PASS, "Dailed to this '" + phNumber
				+ "' number from Zuper Twilio Voice page and Call Status is " + callStatus);
		System.out.println("Dailed to this '" + phNumber + "' number from Zuper Twilio Voice page and Call Status is "
				+ callStatus);
	}

	public void navigatingFromTwilioPageToZuperDashboardPage() {
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.returnToParentTab(driver, DashboardPageFactory.customerWindow);
		logger.info("Navigating to Zuper Dashboard page");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Navigating to Zuper Dashboard page");
	}
}
