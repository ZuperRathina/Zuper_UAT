package PageObjectRep;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebElement;
import com.aventstack.extentreports.Status;
import BaseTest.Baseclass;
import UtilityPackages.Non_WebDriver_Util;

public class DashboardPageFactory extends Baseclass {

	private WebDriver driver;

	public DashboardPageFactory() {
		this.driver = Baseclass.getDriver();
	}

	private By button_PopUpLatestUpdates = By.cssSelector("#pushActionRefuse");
	private By button_Jobs_Chartgroups = By.xpath("//zuper-vertical-navigation-aside-item[@id='job_group']/div");
	private By button_JobLinkFromsideBar = By.xpath("//a[contains(@href, '/jobs')]");
	private By frame_ConnectDailerPad = By.xpath("//iframe[@id='zuper-connect-frame']");
	private By button_AcceptCall = By.xpath("//p[text()='Accept']/preceding::button[1]");
	private By button_DisconnectCall = By.xpath("//div[@id='app']/div/div/div/div[3]/div[1]/div/div[1]/div[3]/button");
	private By button_RecentCalls = By.xpath("//div[@id='app']/div/div/div/div[5]/div[1]/span[1]");
	private By text_TwilioNumber = By.xpath("//div[@id='app']/div/div/div/div[3]/div[1]/div/div[1]/p[1]");
	private By input_onDailer = By.xpath("//div[@id='app']/div/div/div/div[3]/div[1]/div/div[1]//input");
	private By button_CallOnDailer = By
			.xpath("//div[@id='app']/div/div/div/div[3]/div[1]/div/div/div[2]/div[2]/div[1]/img");
	private By button_Transfer = By.xpath("//div[@id='icon_Transfer']");
	private By input_SearchUserToTransfer = By.xpath("//input[@placeholder='Search...']");
	private By searchedUserToHover = By
			.xpath("//p[text()='Available Users']/parent::div/parent::div/div[2]/div/div[2]/div");
	private By icon_callForSearchedUser = By
			.xpath("//p[text()='Available Users']/parent::div/parent::div/div[2]/div/div[2]/div/div[2]/img");
	private By button_ColdTransfer = By.xpath("//p[text()='Transfer Now']/parent::div/img");
	private By text_CallTransferred = By.xpath("//h3[text()='Call Transferred']");
	private By button_WarmTransfer = By.xpath("//p[text()='Talk First']/parent::div/img");
	private By button_MergeCall = By.xpath("//div[@id='icon_Merge Call']");
	//private By button_ZuperConnect = By.xpath("//mat-icon[@data-mat-icon-name='call-logs']");
	private By button_MinimizeDailer = By.xpath("//div[@id='zuper-connect-container']//em");
	private By icon_ReleaseNotesClose = By.xpath("//div[@class='beamerAnnouncementBarClose']");
	private By button_Hold = By.xpath("//div[@id='icon_Hold']");
	private By element_Hold = By.xpath("//div[@id='icon_Hold']/img");
	private By button_Mute = By.xpath("//div[@id='icon_Mute']");
	private By element_Mute = By.xpath("//div[@id='icon_Mute']/img");
	private By element_IconOnDailer = By.xpath("//div[@id='app']/div/div/div/div[1]//div[@id='prime-avatar']");
	private By element_Available = By.xpath("//div[text()='Available']/parent::div");
	private By icon_ContractsAndAssets = By.xpath("//zuper-vertical-navigation-aside-item[@id='contract_asset']");
	private By link_ContractsLink = By.xpath("//span[text()=' Contracts ']");
	private By link_AssetsLink = By.xpath("//span[normalize-space()='Assets']");
	private By link_RequestLink = By.xpath("//mat-icon[@data-mat-icon-name='wrench-screwdriver']");
	private By icon_GlobalCall = By.cssSelector("div[tippyname='dialer_tooltip']");
	private By element_TwilioIsReadyToUse = By
			.xpath("//p[contains(text(),'Twilio.Device Ready to make and receive calls!')] ");
//	private By element_dailer = By.xpath("//div[@class='cdk-overlay-container']/div/div");
	private By element_sensePopup = By.xpath("//app-sense-launch-popup/div");
	private By button_CloseOnSensepopup = By.xpath("//app-sense-launch-popup/div/button");
	

	public void closeSensePopup(WebDriver driver) {
		boolean isPresent = driver.findElements(element_sensePopup).size() > 0;
		Non_WebDriver_Util.waitThread(1);
		if (isPresent) {
			Non_WebDriver_Util.waitForBeClickable(driver, button_CloseOnSensepopup, 20);
			driver.findElement(button_CloseOnSensepopup).click();
		} else {
			logger.info("Sense Popup is not present");
		}
		
	}
	
	public void navigateToContracts() {
		Non_WebDriver_Util.jsScrollAndActionClick(driver, icon_ContractsAndAssets);
		Non_WebDriver_Util.waitThread(1);
		driver.findElement(link_ContractsLink).click();
	}

	public void navigateToAssets() {
		Non_WebDriver_Util.jsScrollAndActionClick(driver, icon_ContractsAndAssets);
		Non_WebDriver_Util.waitThread(1);
		driver.findElement(link_AssetsLink).click();
//		driver.navigate().to("https://uat.zuperpro.com/asset_management");
		Non_WebDriver_Util.waitThread(3);
	}

	public void navigateToRequest() {
		Non_WebDriver_Util.waitThread(3);
		Non_WebDriver_Util.jsScrollAndActionClick(driver, link_RequestLink);
		Non_WebDriver_Util.waitThread(1);
	}

	public void openDailer(WebDriver driver) {
		
		try {
			Non_WebDriver_Util.waitForBeClickable(driver, icon_GlobalCall, 20);
			driver.findElement(icon_GlobalCall).click();
			Non_WebDriver_Util.waitThread(1);
		} catch (Exception e) {
			logger.info("Unable to open the dailer");
		}
	}

	public void markUserAsAvailable(WebDriver driver) {
		Non_WebDriver_Util.waitThread(1);
		updatingDailerStatus();
	}

	public void updatingDailerStatus() {
		driver.findElement(element_IconOnDailer).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, element_Available, 10);
		driver.findElement(element_Available).click();
		Non_WebDriver_Util.waitThread(3);
	}
	
	public void zuperToTwilio() {
		driver.switchTo().defaultContent();
		Non_WebDriver_Util.switchToNewTab(driver);
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.refreshPage(driver);
		Non_WebDriver_Util.waitForBeClickable(driver, ZuperTwilioVoicePageFactory.button_StartUpTheDevice, 2);
		driver.findElement(ZuperTwilioVoicePageFactory.button_StartUpTheDevice).click();
		Non_WebDriver_Util.waitForVisible(driver, element_TwilioIsReadyToUse, 15);
	}

	public void putOnHold(WebDriver driver) {
		String holdIconDisbledOrNot = driver.findElement(button_Hold).getAttribute("class");
		if (!holdIconDisbledOrNot.contains("cursor-not-allowed")) {
			Non_WebDriver_Util.waitForBeClickable(driver, button_Hold, 10);
			driver.findElement(button_Hold).click();
			checkingHoldOption(driver);
			Non_WebDriver_Util.waitThread(5);
			driver.findElement(button_Hold).click();
			checkingHoldOption(driver);
			Non_WebDriver_Util.waitThread(2);
		} else {
			logger.info("Hold option is not available to this flow");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Hold option is not available to this flow");
		}
	}

	public void putOnMute(WebDriver driver) {
		Non_WebDriver_Util.waitForBeClickable(driver, button_Mute, 10);
		driver.findElement(button_Mute).click();
		checkingMuteOption(driver);
		Non_WebDriver_Util.waitThread(3);
		driver.findElement(button_Mute).click();
		checkingMuteOption(driver);
		Non_WebDriver_Util.waitThread(2);
	}

	public void checkingHoldOption(WebDriver driver) {
		String srcText = driver.findElement(element_Hold).getAttribute("src");
		if (srcText.contains("1D4563")) {
			logger.info("Call is currently not in HOLD state");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Call is currently not in HOLD state");
		} else {
			logger.info("Call is currently on HOLD");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Call is currently on HOLD");
		}
		srcText = null;
	}

	public void checkingMuteOption(WebDriver driver) {
		String srcTextMute = driver.findElement(element_Mute).getAttribute("src");
		if (srcTextMute.contains("EF4444")) {
			logger.info("Call is currently in MUTE state");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Call is currently in MUTE state");
		} else {
			logger.info("Call is not Muted now");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Call is not Muted now");
		}
		srcTextMute = null;
	}

	public void closeReleaseNotes(WebDriver driver) {
		Non_WebDriver_Util.waitForVisible(driver, icon_ReleaseNotesClose, 10);
		if (!driver.findElements(icon_ReleaseNotesClose).isEmpty()) {
			driver.findElements(icon_ReleaseNotesClose).get(0).click();
		}
	}

	public void minimizeTheDailer(WebDriver driver) {
		Non_WebDriver_Util.waitForVisible(driver, button_MinimizeDailer, 10);
		Non_WebDriver_Util.waitForBeClickable(driver, button_MinimizeDailer, 10);
		driver.findElement(button_MinimizeDailer).click();
	}
	


	public void navigateToConnectModule(WebDriver driver) {
		Non_WebDriver_Util.waitThread(1);
		driver.get("https://web.zuperpro.com/connect/conversations");
	}

	public void navigateToConnectModuleInIncognito(WebDriver driver) {
		Non_WebDriver_Util.waitThread(1);
		driver.get("https://web.zuperpro.com/connect/conversations");
		Non_WebDriver_Util.waitThread(2);
	}

	public void mergeTheTransferedCall() {
		String mergeCallElementClass = driver.findElement(button_MergeCall).getAttribute("class");
		if (mergeCallElementClass.contains("cursor-not-allowed")) {
			logger.info("Call has been transferred to another user, but the recipient has not answered it yet");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Call has been transferred to another user, but the recipient has not answered it yet");
		} else {
			Non_WebDriver_Util.waitForBeClickable(driver, button_MergeCall, 5);
			driver.findElement(button_MergeCall).click();
			logger.info("Transfered Call is merged");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Transfered Call is merged");
			Non_WebDriver_Util.getScreenshot(driver, "");
		}
		Non_WebDriver_Util.waitThread(3);
	}

	public void warmTransferringCallToFE(String feNameToTransfer) {
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.waitForBeClickable(driver, button_WarmTransfer, 5);
		driver.findElement(button_WarmTransfer).click();

		String transferButtonStatus = driver.findElement(button_Transfer).getAttribute("class");
		if (transferButtonStatus.contains("cursor-not-allowed")) {
			logger.info("Call has been transferred to another user, Waiting that user to pick the Call");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Call has been transferred to another user, Waiting that user to pick the Call");
		} else {
			logger.info("Call is not being transferred");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Call is not being transferred");
		}
	}

	public void selectFEToTransfer(String feNameToTransfer) {
		Non_WebDriver_Util.waitForBeClickable(driver, button_Transfer, 15);
		driver.findElement(button_Transfer).click();
		Non_WebDriver_Util.waitForBeClickable(driver, input_SearchUserToTransfer, 5);
		driver.findElement(input_SearchUserToTransfer).sendKeys(feNameToTransfer);
		Non_WebDriver_Util.waitThread(2);
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(searchedUserToHover)).build().perform();
		Non_WebDriver_Util.waitForBeClickable(driver, icon_callForSearchedUser, 15);
		driver.findElement(icon_callForSearchedUser).click();
		Non_WebDriver_Util.waitThread(1);
	}

	public void transferringCallToFE(String feNameToTransfer) {
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.waitForBeClickable(driver, button_ColdTransfer, 5);
		driver.findElement(button_ColdTransfer).click();
		Non_WebDriver_Util.waitForVisible(driver, text_CallTransferred, 5);
		List<WebElement> callTransferredMessage = driver.findElements(text_CallTransferred);
		if (!callTransferredMessage.isEmpty()) {
			logger.info("Call is Transferred to Agent 2 (" + feNameToTransfer + ") user");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Call is Transferred to Agent 2 (" + feNameToTransfer + ") user");
			Non_WebDriver_Util.getScreenshot(driver, "");
		} else {
			logger.info("Transfer is Not Working as expected");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Transfer is Not Working as expected");
		}
	}

	public void switchingToFrame(WebDriver driver) {
		Non_WebDriver_Util.waitForVisible(driver, frame_ConnectDailerPad, 5);
		driver.switchTo().frame(driver.findElement(frame_ConnectDailerPad));
	}

	public void switchingToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	public void verifyCallIsDisconnectedAndDailerIsReadyToUse() {
		Non_WebDriver_Util.waitThread(2);
		if (driver.findElement(input_onDailer).isDisplayed()) {
			logger.info("Call is disconnected for the Admin User");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Call is disconnected for the Admin User");
		}else {
			disconnectCall();
		}
	}

	public void verifyCallIsDisconnectedAndDailerIsReadyToUseInIncognito() {
		Non_WebDriver_Util.waitThread(2);
		if (CompanyPageFactory.incognitoDriver.findElement(input_onDailer).isDisplayed()) {
			logger.info("Call is disconnected for the Another User");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Call is disconnected for the Another User");
		}
		CompanyPageFactory.incognitoDriver.quit();
	}

	public void callingTwilioNumberBack() {
		Non_WebDriver_Util.waitThread(5);
		driver.findElement(input_onDailer).sendKeys(DashboardPageFactory.incommingTwilioNumber);
		Non_WebDriver_Util.waitThread(1);
		driver.findElement(button_CallOnDailer).click();
		Non_WebDriver_Util.waitThread(2);
		logger.info("Agent 1 Calling Back to Customer Number");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Agent 1 Calling Back to Customer Number");
		Non_WebDriver_Util.waitForVisible(driver, button_RecentCalls, 5);
		Non_WebDriver_Util.switchToNewTab(driver);
	}

	public static String customerWindow;

	public void popup_clear(WebDriver driver) {
		try {
			Non_WebDriver_Util.waitForVisible(driver, button_PopUpLatestUpdates, 10);
			driver.findElement(button_PopUpLatestUpdates).click();
			logger.info("Notify popup is cleared");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Notify popup is cleared");
		} catch (Exception e) {
			logger.info("The notification popup did not appear within the expected wait time");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"The notification popup did not appear within the expected wait time");
		}
		DashboardPageFactory.customerWindow = Non_WebDriver_Util.storeOriginalWindow(getDriver());
	}
	
	public void popup_clearInIncognito(WebDriver driver) {
		try {
			Non_WebDriver_Util.waitForVisible(driver, button_PopUpLatestUpdates, 10);
			driver.findElement(button_PopUpLatestUpdates).click();
			logger.info("Notify popup is cleared");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Notify popup is cleared");
		} catch (Exception e) {
			logger.info("The notification popup did not appear within the expected wait time");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"The notification popup did not appear within the expected wait time");
		}
	}

	public void navigatToJobListionPage() {
		Non_WebDriver_Util.hover_and_click(driver, button_Jobs_Chartgroups, button_JobLinkFromsideBar);
	}

	public static String incommingTwilioNumber;

	public void checkAndAcceptTheCall() {
		Non_WebDriver_Util.waitThread(3);		
		try {
			DashboardPageFactory.incommingTwilioNumber = driver.findElement(text_TwilioNumber).getText();
			Non_WebDriver_Util.waitForVisible(driver, button_AcceptCall, 50);
			Non_WebDriver_Util.waitForBeClickable(driver, button_AcceptCall, 50);
			driver.findElement(button_AcceptCall).click();
			logger.info("Customer Incomming call is accepted by the Agent 1(Zuper Admin)");
			Non_WebDriver_Util.waitThread(5);
			Non_WebDriver_Util.testCase.log(Status.PASS, "Customer Incomming call is accepted by the Agent 1(Zuper Admin)");
		} catch (Exception e) {
			Non_WebDriver_Util.refreshPage(getDriver());
			Non_WebDriver_Util.waitThread(2);
			ZuperTwilioVoicePageFactory zp = new ZuperTwilioVoicePageFactory();
			zuperToTwilio();
			zp.disconnectTheCallFromTwilioEnd();
			Non_WebDriver_Util.waitThread(2);
			zp.callingCustomerNumber(prop.getProperty("customerNumber"));
			zp.navigatingFromTwilioPageToZuperDashboardPage();
			switchingToFrame(getDriver());
			Non_WebDriver_Util.waitForVisible(driver, button_AcceptCall, 50);
			Non_WebDriver_Util.waitForBeClickable(driver, button_AcceptCall, 50);
			driver.findElement(button_AcceptCall).click();
			logger.info("Customer Incomming call is accepted by the Agent 1(Zuper Admin)");
			Non_WebDriver_Util.waitThread(5);
			Non_WebDriver_Util.testCase.log(Status.PASS, "Customer Incomming call is accepted by the Agent 1(Zuper Admin)");
		}		
		try {
			Non_WebDriver_Util.getScreenshot(driver, "IncomingCallAccepted");
		} catch (Exception e) {

		}
		Non_WebDriver_Util.waitThread(1);
	}

	public void checkAndAcceptTheCallInIncognito() {
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.waitForVisible(CompanyPageFactory.incognitoDriver, button_AcceptCall, 10);
		Non_WebDriver_Util.waitForBeClickable(CompanyPageFactory.incognitoDriver, button_AcceptCall, 10);
		CompanyPageFactory.incognitoDriver.findElement(button_AcceptCall).click();
		logger.info("Transfered call is accepted by Agent 2 - " + prop.getProperty("feToTransfer"));
		Non_WebDriver_Util.waitThread(5);
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Transfered call is accepted by Agent 2 - " + prop.getProperty("feToTransfer"));
		Non_WebDriver_Util.getScreenshot(CompanyPageFactory.incognitoDriver, "");
		Non_WebDriver_Util.waitThread(5);
	}

	public void disconnectCallFromIncognito() {
		CompanyPageFactory.incognitoDriver.findElement(button_DisconnectCall).click();
		logger.info("Call is disconnected by Agent 2 - " + prop.getProperty("feToTransfer") + " user");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Call is disconnected by Agent 2 - " + prop.getProperty("feToTransfer") + " user");
	}

	public void disconnectCall() {
		driver.findElement(button_DisconnectCall).click();
	}

}
