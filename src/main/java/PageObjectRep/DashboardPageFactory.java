package PageObjectRep;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
	private By button_Join = By.xpath("(//span[normalize-space()='Join'])[1]");
	private By button_JoinOnPrompt = By.xpath("(//button[normalize-space()='Join'])[1]");
	private By button_DisconnectCall = By
			.xpath("//span[normalize-space()='Hold']/parent::div/parent::div/following::div[1]//span");
	private By endCallOnPrompt = By.xpath("//button[normalize-space()='End Call']");
	private By button_RecentCalls = By.xpath("//div[@id='app']/div/div/div/div[5]/div[1]/span[1]");
	private By input_onDailer = By.xpath("//div[@id='app']/div/div/div/div[3]/div[1]/div/div[1]//input");
	private By button_CallOnDailer = By
			.xpath("//div[@id='app']/div/div/div/div[3]/div[1]/div/div/div[2]/div[2]/div[1]/img");
	private By button_Transfer = By.xpath("//div[@id='icon_Transfer']");
	private By input_SearchUserToTransfer = By.xpath("//input[@placeholder='Search...']");
	private By searchedUserToHover = By
			.xpath("//p[text()='Available Users']/parent::div/parent::div/div[2]/div/div[2]/div");
	private By icon_callForSearchedUser = By
			.xpath("//p[text()='Available Users']/parent::div/parent::div/div[2]/div/div[2]/div/div[2]/img");
	private By button_ColdTransfer = By.xpath("//p[text()='Transfer Now']/preceding::img[1]");
	private By text_CallTransferred = By.xpath("//h3[text()='Call Transferred']");
	private By button_WarmTransfer = By.xpath("//p[text()='Talk First']/parent::div/img");
	private By button_MergeCall = By.xpath("//div[@id='icon_Merge Call']");
	private By button_MinimizeDailer = By.xpath("//div[@id='zuper-connect-container']//em");
	private By icon_ReleaseNotesClose = By.xpath("//div[@class='beamerAnnouncementBarClose']");
	private By button_Hold = By.xpath("//div[@id='icon_Hold']");
	private By element_Hold = By.xpath("//div[@id='icon_Hold']/img");
	private By button_Mute = By.xpath("//div[@id='icon_Mute']");
	private By element_Mute = By.xpath("//div[@id='icon_Mute']/img");
	private By element_IconOnDailer = By.xpath("//div[@id='app']/div/div/div/div[1]//div[@id='prime-avatar']");
	private By element_Available = By.xpath("//div[text()='Available']/parent::div");
	private By icon_ThreeDotMenu = By.xpath("//mat-icon[@data-mat-icon-name='dots-circle-horizontal']");
	private By link_SettingsOption = By.xpath("//span[normalize-space()='Settings']");
	private By link_ZuperConnectMenu = By.xpath(
			"(//zuper-vertical-navigation-basic-item//*[normalize-space()='Communication' or normalize-space()='Zuper Connect'])[1]/parent::a");
	private By icon_GlobalCall = By.cssSelector("//button[@aria-label='Dialer']");
	private By element_TwilioIsReadyToUse = By
			.xpath("//p[contains(text(),'Twilio.Device Ready to make and receive calls!')] ");
	private By element_sensePopup = By.xpath("//app-sense-launch-popup/div");
	private By button_CloseOnSensepopup = By.xpath("//app-sense-launch-popup/div/button");
	private By element_AppTourPopup = By.xpath("//app-launch-announcement-popup");
	private By button_CloseOnAppTour = By.xpath("//app-launch-announcement-popup/div/button");
	
	public void closeAppTourPopup(WebDriver driver) {
		boolean isPresent = driver.findElements(element_AppTourPopup).size() > 0;
		Non_WebDriver_Util.waitThread(1);
		if (isPresent) {
			Non_WebDriver_Util.waitForBeClickable(driver, button_CloseOnAppTour, 20);
			driver.findElement(button_CloseOnAppTour).click();
		} else {
			logger.info("App Tour Popup is not present");
		}

	}

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

	public void navigateToSettings() {
		Non_WebDriver_Util.waitForBeClickable(driver, icon_ThreeDotMenu, 15);
		Non_WebDriver_Util.hoverTheElement(driver, icon_ThreeDotMenu);
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, link_SettingsOption, 10);
		driver.findElement(link_SettingsOption).click();
		Non_WebDriver_Util.waitThread(2);
		logger.info("Navigated to Settings page via the three-dot menu");
	}

	public void openDailer(WebDriver driver) {
		Non_WebDriver_Util.waitThread(2);
		try {
			driver.findElement(icon_GlobalCall).click();
			Non_WebDriver_Util.waitThread(1);
		} catch (Exception e) {
			logger.info("Unable to open the dailer");
		}
	}

	public void markUserAsAvailable(WebDriver driver) {
		Non_WebDriver_Util.waitThread(1);
		try {
			new WebDriverWait(driver, Duration.ofSeconds(35))
					.until(ExpectedConditions.elementToBeClickable(element_IconOnDailer));
		} catch (Exception e) {
			Non_WebDriver_Util.waitThread(5);
			driver.navigate().refresh();
			new WebDriverWait(driver, Duration.ofSeconds(25))
					.until(ExpectedConditions.elementToBeClickable(element_IconOnDailer));
		}
		driver.findElement(element_IconOnDailer).click();
		Non_WebDriver_Util.waitThread(1);
		new WebDriverWait(driver, Duration.ofSeconds(35))
				.until(ExpectedConditions.elementToBeClickable(element_Available));
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
		try {
			new WebDriverWait(driver, Duration.ofSeconds(25))
			.until(ExpectedConditions.elementToBeClickable(button_MinimizeDailer));
			Non_WebDriver_Util.waitForBeClickable(driver, button_MinimizeDailer, 50);
			driver.findElement(button_MinimizeDailer).click();
		} catch (Exception e) {
			driver.findElement(icon_GlobalCall).click();
			Non_WebDriver_Util.waitThread(1);
			driver.findElement(button_MinimizeDailer).click();
		}
	}

	public void navigateToConnectModule(WebDriver driver) {
		Non_WebDriver_Util.waitThread(1);
		try {
			Non_WebDriver_Util.waitForBeClickable(driver, link_ZuperConnectMenu, 25);
			Non_WebDriver_Util.jsScrollAndActionClick(driver, link_ZuperConnectMenu);
			Non_WebDriver_Util.waitThread(2);
		} catch (Exception e) {
			logger.info("Zuper Connect menu is not available in the left side bar, trying to navigate via URL");
			String url = driver.getCurrentUrl();
			String baseUrl = url.substring(0, url.indexOf(".com") + 4);
			String updatedUrl = baseUrl + "/connect/conversations";
			driver.navigate().to(updatedUrl);
			Non_WebDriver_Util.waitThread(2);
		}
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
		try {
			Non_WebDriver_Util.waitForBeClickable(driver, button_WarmTransfer, 15);
			driver.findElement(button_WarmTransfer).click();
			logger.info("Call is Warm Transferred to Agent 2 (" + feNameToTransfer + ")");
		} catch (Exception e) {
			Non_WebDriver_Util.waitForBeClickable(driver, icon_callForSearchedUser, 15);
			driver.findElement(icon_callForSearchedUser).click();
			Non_WebDriver_Util.waitForBeClickable(driver, button_WarmTransfer, 15);
			driver.findElement(button_WarmTransfer).click();
			logger.info("Call is Warm Transferred to Agent 2 (" + feNameToTransfer + ")");
		}
		String transferButtonStatus = driver.findElement(button_Transfer).getAttribute("class");
		if (transferButtonStatus.contains("cursor-not-allowed")) {
			logger.info("Call has been transferred to Agent 2, Waiting that user to pick the Call");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Call has been transferred to Agent 2, Waiting that user to pick the Call");
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
		try {
			Non_WebDriver_Util.waitForBeClickable(driver, button_ColdTransfer, 15);
			driver.findElement(button_ColdTransfer).click();
			logger.info("Call is Cold Transferred to Agent 2 (" + feNameToTransfer + ")");
		} catch (Exception e) {
			Non_WebDriver_Util.waitForBeClickable(driver, icon_callForSearchedUser, 15);
			driver.findElement(icon_callForSearchedUser).click();
			Non_WebDriver_Util.waitForBeClickable(driver, button_ColdTransfer, 15);
			driver.findElement(button_ColdTransfer).click();
			logger.info("Call is Cold Transferred to Agent 2 (" + feNameToTransfer + ")");
		}
		boolean isTransfered = driver.findElements(text_CallTransferred).size() > 0;
		Non_WebDriver_Util.waitThread(1);
		if (!isTransfered) {
			System.out.println("Call is not transferred, Selecting the user again");
			Non_WebDriver_Util.waitThread(1);
			Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElement(searchedUserToHover)).build().perform();
			Non_WebDriver_Util.waitForBeClickable(driver, icon_callForSearchedUser, 15);
			driver.findElement(icon_callForSearchedUser).click();
			Non_WebDriver_Util.waitThread(1);
			Non_WebDriver_Util.waitForBeClickable(driver, button_ColdTransfer, 25);
			driver.findElement(button_ColdTransfer).click();
		}
		List<WebElement> callTransferredMessage = driver.findElements(text_CallTransferred);
		if (!callTransferredMessage.isEmpty()) {
			logger.info("Call is Transferred to Agent 2 (" + feNameToTransfer + ")");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Call is Transferred to Agent 2 (" + feNameToTransfer + ")");
			System.out.println("Call is Transferred to Agent 2 (" + feNameToTransfer + ")");
			Non_WebDriver_Util.getScreenshot(driver, "");
		} else {
			logger.info("Transfer is Not Working as expected");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Transfer is Not Working as expected");
			System.out.println("Transfer is Not Working as expected");
		}
	}

	public void switchingToFrame(WebDriver driver) {
		new WebDriverWait(driver, Duration.ofSeconds(55))
				.until(ExpectedConditions.visibilityOfElementLocated(frame_ConnectDailerPad));
		driver.switchTo().frame(driver.findElement(frame_ConnectDailerPad));
		logger.info("Switched into the iFrame");
	}

	public void switchingToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	public void verifyCallIsDisconnectedAndDailerIsReadyToUse(WebDriver driver) {
		Non_WebDriver_Util.waitThread(2);
		if (driver.findElement(input_onDailer).isDisplayed()) {
			logger.info("Call is disconnected and dialer is ready to use");
			Non_WebDriver_Util.testCase.log(Status.PASS, "Call is disconnected and dialer is ready to use");
		} else {
			logger.info("Call is not disconnected and dialer is not ready to use, refreshing the page");
			Non_WebDriver_Util.refreshPage(driver);
		}
	}

	public void callingTwilioNumberBack() {
		Non_WebDriver_Util.waitThread(5);
		// driver.findElement(input_onDailer).sendKeys(DashboardPageFactory.incommingTwilioNumber);
		driver.findElement(input_onDailer).sendKeys("463 232 3489");
		Non_WebDriver_Util.waitThread(1);
		driver.findElement(button_CallOnDailer).click();
		Non_WebDriver_Util.waitThread(2);
		logger.info("Agent 1 Calling Back to Customer Number");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Agent 1 Calling Back to Customer Number");
		Non_WebDriver_Util.waitForVisible(driver, button_RecentCalls, 5);
		Non_WebDriver_Util.switchToNewTab(driver);
	}

	public static String driverValue;

	public void storeDriverValue() {
		driverValue = String.valueOf(getDriver());
	}

	public static String customerWindow;

	public void getCurrentWindow() {
		DashboardPageFactory.customerWindow = Non_WebDriver_Util.storeOriginalWindow(getDriver());
	}

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

	public void checkAndAcceptTheCall(WebDriver driver) {
		if (driverValue.equalsIgnoreCase(driver.toString())) {
			try {
				Non_WebDriver_Util.waitThread(2);
				new WebDriverWait(driver, Duration.ofSeconds(25))
						.until(ExpectedConditions.elementToBeClickable(button_AcceptCall));
				driver.findElement(button_AcceptCall).click();
				logger.info("Incoming customer call was accepted by Agent 1");
				Non_WebDriver_Util.waitThread(5);
				Non_WebDriver_Util.testCase.log(Status.PASS, "Incoming customer call was accepted by Agent 1");
				System.out.println("Incoming customer call was accepted by Agent 1");
			} catch (Exception e) {
				logger.info("Unable to accept the call for Agent 1, refreshing the page and trying again");
				Non_WebDriver_Util.refreshPage(getDriver());
				Non_WebDriver_Util.waitThread(2);
				new WebDriverWait(driver, Duration.ofSeconds(45))
						.until(ExpectedConditions.elementToBeClickable(button_Join));
				driver.findElement(button_Join).click();
				Non_WebDriver_Util.waitThread(2);
				driver.findElement(button_JoinOnPrompt).click();
				logger.info("Agent 1 joined the call after refresh");
				Non_WebDriver_Util.waitThread(5);
				Non_WebDriver_Util.testCase.log(Status.PASS, "Agent 1 joined the call after refresh");
			}
		} else {
			try {
				Non_WebDriver_Util.waitThread(2);
				new WebDriverWait(driver, Duration.ofSeconds(35))
						.until(ExpectedConditions.elementToBeClickable(button_AcceptCall));
				driver.findElement(button_AcceptCall).click();
				logger.info("Incoming customer call was accepted by Agent 2");
				Non_WebDriver_Util.waitThread(5);
				Non_WebDriver_Util.testCase.log(Status.PASS, "Incoming customer call was accepted by Agent 2");
				System.out.println("Incoming customer call was accepted by Agent 2");
			} catch (Exception e) {
				logger.info("Unable to accept the call for Agent 2");
			}
		}
		Non_WebDriver_Util.getScreenshot(driver, "IncomingCallAccepted");
		Non_WebDriver_Util.waitThread(1);
	}

	public void disconnectCallNew(WebDriver driver) {
		Non_WebDriver_Util.waitThread(1);
		try {
			Non_WebDriver_Util.waitForBeClickable(driver, button_DisconnectCall, 10);
			driver.findElement(button_DisconnectCall).click();
			Non_WebDriver_Util.waitForBeClickable(driver, endCallOnPrompt, 20);
			driver.findElement(endCallOnPrompt).click();
		} catch (Exception e) {
			logger.info("disconnectCall failed: " + e.getMessage());
			Non_WebDriver_Util.refreshPage(driver);
		}
		if (driverValue.equalsIgnoreCase(driver.toString())) {
			logger.info("Call was disconnceted by Agent 1");
		} else {
			logger.info("Call was disconnceted by Agent 2");
		}

	}

	public void quitBrowser(WebDriver driver) {
		if (driver != null) {
			driver.quit();
		} else {
			logger.info("Incognito driver is null, cannot close the browser.");
		}
	}

	public void muteAndUnmute(WebDriver driver) {
		Non_WebDriver_Util.waitForBeClickable(driver, button_Mute, 10);
		driver.findElement(button_Mute).click();
		checkingMuteOption(driver);
		
	}
}
