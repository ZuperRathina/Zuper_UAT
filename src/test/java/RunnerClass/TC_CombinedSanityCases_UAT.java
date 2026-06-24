package RunnerClass;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import BaseTest.Baseclass;
import PageObjectRep.AssetsPageFactory;
import PageObjectRep.CompanyPageFactory;
import PageObjectRep.ContractsPageFactory;
import PageObjectRep.DashboardPageFactory;
import PageObjectRep.RequestPageFactory;
import PageObjectRep.ZuperConnectPageFactory;
import PageObjectRep.ZuperTwilioVoicePageFactory;
import UtilityPackages.Non_WebDriver_Util;

//@Listeners(RunnerClass.FailureListener.class)
public class TC_CombinedSanityCases_UAT extends Baseclass {

	private CompanyPageFactory companyPageFactory;
	private DashboardPageFactory dashboardPageFactory;
	private ZuperTwilioVoicePageFactory zuperTwilioPageFactory;
	private ZuperConnectPageFactory zuperConnectPageFactory;
	private ContractsPageFactory contractsPageFactory;
	private AssetsPageFactory assetsPageFactory;
	private RequestPageFactory requestPageFactory;

	@BeforeClass
	public void setUp() {
		initilizeConfig(); // BaseClass setup
		companyPageFactory = new CompanyPageFactory();
		dashboardPageFactory = new DashboardPageFactory();
		zuperTwilioPageFactory = new ZuperTwilioVoicePageFactory();
		zuperConnectPageFactory = new ZuperConnectPageFactory();
		contractsPageFactory = new ContractsPageFactory();
		assetsPageFactory = new AssetsPageFactory();
		requestPageFactory = new RequestPageFactory();
	}

	@Test(priority = 1)
	public void verify_IncommingAndOutgoingCallsWithTwilioVoiceApp() {
		Non_WebDriver_Util.initReport();
		getDriver().navigate().to(prop.getProperty("UAT_URL"));
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verify Incomming and Outgoing call functionalities with Zuper Twilio app");
		companyPageFactory.enterCompanyNameDetails(prop.getProperty("company_Name"));
		companyPageFactory.enter_LoginSceanrio(prop.getProperty("useremail"), prop.getProperty("password"));
		dashboardPageFactory.popup_clear(getDriver());
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.markUserAsAvailable(getDriver());
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		zuperTwilioPageFactory.navigateToZuperTwilioVoicepage();
		zuperTwilioPageFactory.callingCustomerNumber(prop.getProperty("customerNumber"));
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.checkAndAcceptTheCall();
		dashboardPageFactory.putOnHold(getDriver());
		dashboardPageFactory.putOnMute(getDriver());
		dashboardPageFactory.disconnectCall();
		dashboardPageFactory.zuperToTwilio();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.callingTwilioNumberBack();
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.waitThread(3);
		zuperTwilioPageFactory.acceptingTheIncommingCallOnTwilio();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		Non_WebDriver_Util.waitThread(2);
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.putOnHold(getDriver());
		dashboardPageFactory.putOnMute(getDriver());
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.switchToNewTab(getDriver());
		zuperTwilioPageFactory.disconnectTheCallFromTwilioEnd();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.verifyCallIsDisconnectedAndDailerIsReadyToUse();
		logger.info(
				"<<<<<<Incomming and Outgoing call functionalities with Zuper Twilio app is working as expected.>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Incomming and Outgoing call functionalities with Zuper Twilio app is working as expected.");
	}

	@Test(priority = 2, alwaysRun = true)
	public void verify_ColdTransferFunctionality() {
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent.createTest("Verifying Cold Transfer functionality");
		dashboardPageFactory.markUserAsAvailable(getDriver());
		dashboardPageFactory.zuperToTwilio();
		zuperTwilioPageFactory.callingCustomerNumber(prop.getProperty("customerNumber"));
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		dashboardPageFactory.switchingToFrame(getDriver());
		Non_WebDriver_Util.waitThread(1);
		dashboardPageFactory.checkAndAcceptTheCall();
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		companyPageFactory.launchBrowserInIncognito(prop.getProperty("UAT_URL"));
		companyPageFactory.enterCompanyNameDetailsInIncognito(prop.getProperty("company_Name"));
		companyPageFactory.enter_LoginSceanrioInIncognito(prop.getProperty("feEmail"), prop.getProperty("fePassword"));
		dashboardPageFactory.popup_clearInIncognito(CompanyPageFactory.incognitoDriver);
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.refreshPage(CompanyPageFactory.incognitoDriver);
		Non_WebDriver_Util.waitThread(2);
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.selectFEToTransfer(prop.getProperty("feToTransfer"));
		dashboardPageFactory.transferringCallToFE(prop.getProperty("feToTransfer"));
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		dashboardPageFactory.switchingToFrame(CompanyPageFactory.incognitoDriver);
		Non_WebDriver_Util.waitThread(1);
		dashboardPageFactory.checkAndAcceptTheCallInIncognito();
		dashboardPageFactory.putOnHold(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.putOnMute(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.disconnectCallFromIncognito();
		logger.info("<<<<<<Cold Call Transfer functionality is working as expected.>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Cold Call Transfer functionality is working as expected.");
	}

	@Test(priority = 3, alwaysRun = true)
	public void verify_WarmCallTransferFunctionality() {
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent.createTest("Verifying Warm Transfer functionality");
		Non_WebDriver_Util.refreshPage(getDriver());
		Non_WebDriver_Util.waitThread(3);
		DashboardPageFactory.customerWindow = Non_WebDriver_Util.storeOriginalWindow(getDriver());
		dashboardPageFactory.zuperToTwilio();
		zuperTwilioPageFactory.callingCustomerNumber(prop.getProperty("customerNumber"));
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		dashboardPageFactory.switchingToFrame(getDriver());
		Non_WebDriver_Util.waitThread(1);
		dashboardPageFactory.checkAndAcceptTheCall();
		dashboardPageFactory.selectFEToTransfer(prop.getProperty("feToTransfer"));
		dashboardPageFactory.warmTransferringCallToFE(prop.getProperty("feToTransfer"));
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.waitThread(1);
		dashboardPageFactory.checkAndAcceptTheCallInIncognito();
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.mergeTheTransferedCall();
		dashboardPageFactory.putOnMute(getDriver());
		dashboardPageFactory.putOnHold(getDriver());
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		dashboardPageFactory.putOnMute(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		zuperTwilioPageFactory.disconnectTheWarmTransferCallFromTwilioEnd();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.verifyCallIsDisconnectedAndDailerIsReadyToUse();
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		dashboardPageFactory.verifyCallIsDisconnectedAndDailerIsReadyToUseInIncognito();
		logger.info("<<<<<<Warm Call Transfer functionality is working as expected.>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Warm Call Transfer functionality is working as expected.");
	}

	@Test(priority = 4, alwaysRun = true)
	public void verify_OutgoingAndIncommingMessages() {
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verifying Incomming and Outgoing Messages functionalities on "
						+ prop.getProperty("company_Name") + " account");
		Non_WebDriver_Util.refreshPage(getDriver());
		getDriver().navigate().to("https://uat.zuperpro.com/connect/conversations");
		zuperConnectPageFactory.createNewMessage(getDriver());
		zuperConnectPageFactory.clickTextMessageInputSection(getDriver());
		zuperConnectPageFactory.enterTextAndSendMessage(getDriver());
		companyPageFactory.launchBrowserInIncognito(prop.getProperty("UAT_URL"));
		companyPageFactory.enterCompanyNameDetailsInIncognito(prop.getProperty("messgaeUserCompanyName"));
		companyPageFactory.enter_LoginSceanrioInIncognito(prop.getProperty("messageUserEmail"),
				prop.getProperty("messageUserPassword"));
		dashboardPageFactory.popup_clear(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.closeSensePopup(CompanyPageFactory.incognitoDriver);
		Non_WebDriver_Util.waitThread(1);
		dashboardPageFactory.minimizeTheDailer(CompanyPageFactory.incognitoDriver);
		CompanyPageFactory.incognitoDriver.navigate().to("https://uat.zuperpro.com/connect/conversations");;
		zuperConnectPageFactory.selectAllInboxes(CompanyPageFactory.incognitoDriver);
		zuperConnectPageFactory.navigateToTheRecentReceivedMessage(CompanyPageFactory.incognitoDriver);
		Non_WebDriver_Util.waitThread(1);
		zuperConnectPageFactory.checkTheIncommingMessage(CompanyPageFactory.incognitoDriver);
		zuperConnectPageFactory.clickTextMessageInputSection(CompanyPageFactory.incognitoDriver);
		zuperConnectPageFactory.enterTextAndSendMessageFromAnotherUser(CompanyPageFactory.incognitoDriver);
		Non_WebDriver_Util.waitThread(5);
		Non_WebDriver_Util.refreshPage(getDriver());
		Non_WebDriver_Util.waitThread(1);
		zuperConnectPageFactory.selectAllInboxes(getDriver());
		zuperConnectPageFactory.navigateToTheRecentReceivedMessage(getDriver());
		zuperConnectPageFactory.checkTheIncommingMessageFromAdminUser(getDriver());
		logger.info("<<<<<<Incomming and Outgoing Messages functionality is working as expected.>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Incomming and Outgoing Messages functionality is working as expected.");
	}

	@Test(priority = 5, alwaysRun = true)
	public void verify_OutgoingAndIncommingMMS() {
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verifying Incomming and Outgoing MMS functionalities on "
						+ prop.getProperty("company_Name") + " account");
		zuperConnectPageFactory.clickTextMessageInputSection(getDriver());
		zuperConnectPageFactory.addAttachmentsToMMS(getDriver());
		Non_WebDriver_Util.waitThread(15);
		Non_WebDriver_Util.refreshPage(CompanyPageFactory.incognitoDriver);
		Non_WebDriver_Util.waitThread(2);
		zuperConnectPageFactory.selectAllInboxes(CompanyPageFactory.incognitoDriver);
		zuperConnectPageFactory.navigateToTheRecentReceivedMessage(CompanyPageFactory.incognitoDriver);
		zuperConnectPageFactory.checkTheIncommingMMSMessage(CompanyPageFactory.incognitoDriver);
		zuperConnectPageFactory.addAttachmentsToMMSFromAnotherUser(CompanyPageFactory.incognitoDriver);
		Non_WebDriver_Util.waitThread(15);
		Non_WebDriver_Util.refreshPage(getDriver());
		Non_WebDriver_Util.waitThread(1);
		zuperConnectPageFactory.selectAllInboxes(getDriver());
		zuperConnectPageFactory.navigateToTheRecentReceivedMessage(getDriver());
		zuperConnectPageFactory.selectAllInboxes(getDriver());
		zuperConnectPageFactory.checkTheIncommingMMSMessageInAdminUser(getDriver());
		logger.info("<<<<<<Incomming and Outgoing MMS functionality is working as expected.>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Incomming and Outgoing MMS functionality is working as expected.");
	}

	@AfterMethod
	public void creatingReport(ITestResult result) {
		Non_WebDriver_Util.addingScreenShotToReport(result, getDriver());
	}

	@AfterClass
	public void sendFinalReport() {
//	long endTime = System.currentTimeMillis();
		Non_WebDriver_Util.extent.flush();
//		long duration = endTime - startTime;
//		long seconds = (duration / 1000) % 60;
//		long minutes = (duration / (1000 * 60)) % 60;
//		String reportPath = System.getProperty("user.dir") + "/ExtentReport.html";
//		UtilityPackages.ReportToSlack.uploadReport(reportPath, "Live", prop.getProperty("company_Name"), minutes,
//				seconds);
	}

	@AfterSuite
	public void closeBrowsers() {
		CompanyPageFactory.incognitoDriver.quit();
		getDriver().quit();
		// Non_WebDriver_Util.clearScreenshots();
	}
}
