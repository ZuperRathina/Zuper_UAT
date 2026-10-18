package PageObjectRep;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.Status;
import BaseTest.Baseclass;
import UtilityPackages.Non_WebDriver_Util;

public class CallRoutePageFactory extends Baseclass {

	private WebDriver driver;

	public CallRoutePageFactory() {
		this.driver = Baseclass.getDriver();
	}

	// TODO: built from description, not inspected HTML - verify against the real DOM.
	private By tab_CallRoutings = By.xpath("//span[normalize-space()='Call Routings' or normalize-space()='Call Routing']");
	private By button_NewRoute = By.xpath("//span[contains(normalize-space(),'New Route')]");
	private By dialogTitle_CreateNewRoute = By.xpath(
			"//*[normalize-space()='Create new route' or normalize-space()='Create New Route' or normalize-space()='New Route']");
	private By input_RouteName = By.cssSelector("input#call_route_name");
	private By dropdown_Schedule = By.xpath("//label[normalize-space()='Schedule']/following::div[1]");
	private By option_Always24x7 = By.xpath("//hlm-option[normalize-space()='Always (24/7)']");
	private By checkbox_WelcomeGreetingMessage = By.xpath("//*[normalize-space()='Welcome Greeting Message']");
	private By textarea_WelcomeMessage = By
			.xpath("//*[normalize-space()='Welcome Greeting Message']/following::textarea[1]");
	private By radio_SpecificUser = By.xpath("//input[@id='agentConfig']");
	private By dropdown_ChooseAgent = By.xpath("//brn-select[@name='callAgent']/preceding-sibling::label[1]");
	private By elements_CallAgentOptions = By.xpath("//hlm-option[normalize-space()='Customer Experience']");
	private By dropdown_RingFor = By.xpath("//label[normalize-space()='Ring for']/following::div[1]");
	private By option_Ring20Secs = By.xpath("//hlm-option[contains(normalize-space(),'20 Secs')]");
	private By checkbox_IfNoOneAnswers = By.xpath("//*[normalize-space()='If no one answers']");
	private By radio_EndCallWithGreetingMessage = By.xpath("//*[normalize-space()='End call with greeting message']");
	private By textarea_EndCallMessage = By
			.xpath("//*[normalize-space()='End call with greeting message']/following::textarea[1]");
	private By button_CreateRoute = By.xpath("//button[normalize-space()='Create']");
	private By element_RouteCreatedMessage = By.xpath(
			"//*[contains(normalize-space(),'Route created successful') or contains(normalize-space(),'Route Created Successfully')]");
	private By button_ClosePopup = By.xpath("//button[normalize-space()='Skip']");
	private By option_EditRoute = By.xpath("//span[normalize-space()='Edit'] | //button[normalize-space()='Edit']");
	private By dialogTitle_EditRoute = By.xpath("//div[normalize-space()='Edit Call Route']");
	private By radio_CallGroup = By.xpath("//h4[normalize-space()='Call Group']");
	private By dropdown_ChooseGroup = By.xpath("//label[@for='groupConfig']/div/div[1]/label/span");
	private By elements_CallGroupOptions = By.xpath("//hlm-option[normalize-space()='PS Sanity Group']");
	private By radio_Voicemail = By.xpath("//*[normalize-space()='Voicemail']");
	private By textarea_VoicemailMessage = By.xpath("//*[normalize-space()='Voicemail']/following::textarea[1]");
	private By button_UpdateRoute = By.xpath("//button[normalize-space()='Update']");
	private By element_RouteUpdatedMessage = By.xpath(
			"//*[contains(normalize-space(),'Call Route upserted successfully') or contains(normalize-space(),'Route Updated Successfully')]");
	private By option_DeleteRoute = By.xpath("//span[normalize-space()='Delete'] | //button[normalize-space()='Delete']");
	private By button_DeleteRouteConfirm = By.xpath("//button[normalize-space()='Delete Route' or normalize-space()='Delete']");
	private By element_RouteDeletedMessage = By.xpath(
			"//*[contains(normalize-space(),'Call route deleted successfully') or contains(normalize-space(),'Route Deleted Successfully')]");

	public static String callRouteName;

	public void navigateToCallRoutingsTab() {
		Non_WebDriver_Util.waitForBeClickable(driver, tab_CallRoutings, 20);
		Non_WebDriver_Util.jsScrollAndActionClick(driver, tab_CallRoutings);
		Non_WebDriver_Util.waitThread(1);
	}

	public void clickNewRouteButton() {
		Non_WebDriver_Util.waitForBeClickable(driver, button_NewRoute, 10);
		driver.findElement(button_NewRoute).click();
		Non_WebDriver_Util.waitForVisible(driver, dialogTitle_CreateNewRoute, 10);
	}

	public void enterRouteName() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM");
		String currentDay = LocalDate.now().format(formatter);
		callRouteName = "ZuperRoute@ " + currentDay;
		Non_WebDriver_Util.waitForBeClickable(driver, input_RouteName, 10);
		driver.findElement(input_RouteName).clear();
		driver.findElement(input_RouteName).sendKeys(callRouteName);
	}

	public void selectAlways24x7Schedule() {
		Non_WebDriver_Util.waitForBeClickable(driver, dropdown_Schedule, 10);
		driver.findElement(dropdown_Schedule).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, option_Always24x7, 10);
		driver.findElement(option_Always24x7).click();
	}

	public void setWelcomeGreetingMessage() {
		Non_WebDriver_Util.waitForBeClickable(driver, checkbox_WelcomeGreetingMessage, 10);
		driver.findElement(checkbox_WelcomeGreetingMessage).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, textarea_WelcomeMessage, 10);
		driver.findElement(textarea_WelcomeMessage).sendKeys("Welcome to Zuper connect test");
	}

	public void selectSpecificUserAndSetRingTime() {
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.jsScrollAndClick(driver, radio_SpecificUser);
		Non_WebDriver_Util.waitThread(1);
		driver.findElement(dropdown_ChooseAgent).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.tabKeyClick(driver);
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.pageEnterKeyClick(driver);
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, elements_CallAgentOptions, 10);
		Non_WebDriver_Util.jsScrollAndClick(driver, elements_CallAgentOptions);
		Non_WebDriver_Util.waitForBeClickable(driver, dropdown_RingFor, 10);
		driver.findElement(dropdown_RingFor).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, option_Ring20Secs, 10);
		driver.findElement(option_Ring20Secs).click();
	}

	public void setNoAnswerVoicemailGreeting() {
		Non_WebDriver_Util.waitForBeClickable(driver, checkbox_IfNoOneAnswers, 10);
		driver.findElement(checkbox_IfNoOneAnswers).click();
		Non_WebDriver_Util.waitThread(1);
		// Voicemail option is selected by default once "If no one answers" is checked.
		Non_WebDriver_Util.waitForBeClickable(driver, radio_EndCallWithGreetingMessage, 10);
		driver.findElement(radio_EndCallWithGreetingMessage).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, textarea_EndCallMessage, 10);
		driver.findElement(textarea_EndCallMessage).sendKeys("Sorry, We will reach you");
	}

	public void createRouteAndVerifySuccess() {
		Non_WebDriver_Util.waitForBeClickable(driver, button_CreateRoute, 20);
		driver.findElement(button_CreateRoute).click();
		Non_WebDriver_Util.waitForVisible(driver, element_RouteCreatedMessage, 25);
		Non_WebDriver_Util.waitThread(3);
		Non_WebDriver_Util.getScreenshot(driver, "CallRouteCreated");
		logger.info("Call Route '" + callRouteName + "' Created Successfully");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Call Route '" + callRouteName + "' Created Successfully");
		Non_WebDriver_Util.waitForBeClickable(driver, button_ClosePopup, 10);
		driver.findElement(button_ClosePopup).click();
	}

	public void createNewCallRoute() {
		navigateToCallRoutingsTab();
		clickNewRouteButton();
		enterRouteName();
		selectAlways24x7Schedule();
		setWelcomeGreetingMessage();
		selectSpecificUserAndSetRingTime();
		setNoAnswerVoicemailGreeting();
		createRouteAndVerifySuccess();
	}

	public void clickThreeDotMenuForRoute(String routeName) {
		By icon_ThreeDotMenu = By.xpath(String.format(
				"//div[normalize-space()='%s']/parent::hlm-td/parent::cdk-row/hlm-td[3]/button",
				routeName));
		Non_WebDriver_Util.waitForBeClickable(driver, icon_ThreeDotMenu, 25);
		Non_WebDriver_Util.jsClick(driver, icon_ThreeDotMenu);
		Non_WebDriver_Util.waitThread(1);
	}

	public void clickEditOptionFromMenu() {
		Non_WebDriver_Util.waitForBeClickable(driver, option_EditRoute, 10);
		driver.findElement(option_EditRoute).click();
		Non_WebDriver_Util.waitForVisible(driver, dialogTitle_EditRoute, 10);
	}

	public void updateRouteName() {
		Non_WebDriver_Util.waitThread(3);
		Non_WebDriver_Util.waitForBeClickable(driver, input_RouteName, 10);
		// Field is pre-filled with the existing name in the Edit dialog, so we don't
		// clear it - just append " Updated", and keep callRouteName in sync with the
		// real DOM value so later steps (e.g. delete) can still find this route by name.
		callRouteName = callRouteName + " Updated";
		driver.findElement(input_RouteName).sendKeys(" Updated");
		
	}

	public void switchToCallGroupAndSetRingTime() {
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.waitForBeClickable(driver, radio_CallGroup, 10);
		Non_WebDriver_Util.jsClick(driver, radio_CallGroup);
		Non_WebDriver_Util.waitThread(2);
		driver.findElement(dropdown_ChooseGroup).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.tabKeyClick(driver);
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.pageEnterKeyClick(driver);
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, elements_CallGroupOptions, 10);
		Non_WebDriver_Util.jsScrollAndClick(driver, elements_CallGroupOptions);
		Non_WebDriver_Util.waitForBeClickable(driver, dropdown_RingFor, 10);
		driver.findElement(dropdown_RingFor).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, option_Ring20Secs, 10);
		driver.findElement(option_Ring20Secs).click();
	}

	public void selectVoicemailAndUpdateMessage() {
		Non_WebDriver_Util.waitForBeClickable(driver, radio_Voicemail, 10);
		driver.findElement(radio_Voicemail).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, textarea_VoicemailMessage, 10);
		driver.findElement(textarea_VoicemailMessage).clear();
		driver.findElement(textarea_VoicemailMessage)
				.sendKeys("We are currently unavailable. Please leave a message after the tone.");
	}

	public void updateRouteAndVerifySuccess() {
		Non_WebDriver_Util.waitForBeClickable(driver, button_UpdateRoute, 10);
		driver.findElement(button_UpdateRoute).click();
		Non_WebDriver_Util.waitForVisible(driver, element_RouteUpdatedMessage, 15);
		Non_WebDriver_Util.waitThread(3);
		Non_WebDriver_Util.getScreenshot(driver, "CallRouteUpdated");
		logger.info("Call Route '" + callRouteName + "' Updated Successfully");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Call Route '" + callRouteName + "' Updated Successfully");
	}

	public void editExistingCallRoute() {
		Non_WebDriver_Util.waitThread(5);
		clickThreeDotMenuForRoute(callRouteName);
		clickEditOptionFromMenu();
		updateRouteName();
		switchToCallGroupAndSetRingTime();
		selectVoicemailAndUpdateMessage();
		updateRouteAndVerifySuccess();
	}

	public void clickDeleteOptionFromMenu() {
		Non_WebDriver_Util.waitForBeClickable(driver, option_DeleteRoute, 10);
		driver.findElement(option_DeleteRoute).click();
		Non_WebDriver_Util.waitForVisible(driver, button_DeleteRouteConfirm, 10);
	}

	public void confirmDeleteRoute() {
		Non_WebDriver_Util.waitForBeClickable(driver, button_DeleteRouteConfirm, 10);
		driver.findElement(button_DeleteRouteConfirm).click();
	}

	public void verifyRouteDeletedSuccessfully() {
		Non_WebDriver_Util.waitForVisible(driver, element_RouteDeletedMessage, 15);
		Non_WebDriver_Util.waitThread(3);
		Non_WebDriver_Util.getScreenshot(driver, "CallRouteDeleted");
		logger.info("Call Route '" + callRouteName + "' Deleted Successfully");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Call Route '" + callRouteName + "' Deleted Successfully");
	}

	public void deleteExistingCallRoute() {
		Non_WebDriver_Util.waitThread(3);
		clickThreeDotMenuForRoute(callRouteName);
		clickDeleteOptionFromMenu();
		confirmDeleteRoute();
		verifyRouteDeletedSuccessfully();
	}

}
