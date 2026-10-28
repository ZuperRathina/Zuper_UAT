package RunnerClass;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import BaseTest.Baseclass;
import PageObjectRep.CallsListingPageFactory;
import PageObjectRep.CompanyPageFactory;
import PageObjectRep.DashboardPageFactory;
import PageObjectRep.ZuperConnectPageFactory;
import PageObjectRep.ZuperTwilioVoicePageFactory;
import UtilityPackages.Non_WebDriver_Util;

public class TC_CombinedSanityCasesWithoutMinimize extends Baseclass {

	private CompanyPageFactory companyPageFactory;
	private DashboardPageFactory dashboardPageFactory;
	private ZuperTwilioVoicePageFactory zuperTwilioPageFactory;
	private ZuperConnectPageFactory zuperConnectPageFactory;
	private CallsListingPageFactory callsListingPageFactory;


	public static long startTime;

	@Parameters("url")
	@BeforeClass
	public void setUp(@Optional("liveNext")String url) {
		initilizeConfigNew("normal", prop.getProperty(url)); // BaseClass setup
		companyPageFactory = new CompanyPageFactory();
		dashboardPageFactory = new DashboardPageFactory();
		zuperTwilioPageFactory = new ZuperTwilioVoicePageFactory();
		zuperConnectPageFactory = new ZuperConnectPageFactory();
		callsListingPageFactory = new CallsListingPageFactory();
		startTime = System.currentTimeMillis();
	}
	
	@Test(priority = 1)
	public void verify_IncommingAndOutgoingCallsWithTwilioVoiceApp() {	
		Non_WebDriver_Util.initReport();		
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verifying Incomming and Outgoing call functionalities with Zuper Twilio app");
		System.out.println("Verifying Incomming and Outgoing call functionalities with Zuper Twilio app");
		companyPageFactory.enterCompanyNameDetails(prop.getProperty("company_Name"), getDriver());
		companyPageFactory.enter_LoginSceanrio(prop.getProperty("useremail"), prop.getProperty("password"),
				getDriver());
		dashboardPageFactory.storeDriverValue();
		dashboardPageFactory.getCurrentWindow();
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.markUserAsAvailable(getDriver());
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		zuperTwilioPageFactory.navigateToZuperTwilioVoicepage();
		zuperTwilioPageFactory.callingCustomerNumber(prop.getProperty("customerNumber"));
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.checkAndAcceptTheCall(getDriver());
		Non_WebDriver_Util.getInProgressCallUID(prop.getProperty("getCallsUSEast"), prop.getProperty("usEastAPIKey"), prop.getProperty("callInitiatedOrAcceptedUser"));
		dashboardPageFactory.muteAndUnmute(getDriver());
		Non_WebDriver_Util.openMusic();
		Non_WebDriver_Util.minimizeMusic();
		Non_WebDriver_Util.playAudio(getDriver(), "Customer");
		Non_WebDriver_Util.waitThread(12);
		dashboardPageFactory.muteAndUnmute(getDriver());
		Non_WebDriver_Util.playAudio(getDriver(), "Agent1");
		Non_WebDriver_Util.waitThread(12);
		dashboardPageFactory.putOnHold(getDriver());
		dashboardPageFactory.disconnectCallNew(getDriver());
		Non_WebDriver_Util.waitThread(2);
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.waitThread(10);
		callsListingPageFactory.getCallSummary();
		callsListingPageFactory.checkKeywords("normal");
		dashboardPageFactory.zuperToTwilio();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.callingTwilioNumberBack();
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.waitThread(3);
		zuperTwilioPageFactory.acceptingTheIncommingCallOnTwilio();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		Non_WebDriver_Util.getInProgressCallUID(prop.getProperty("getCallsUSEast"), prop.getProperty("usEastAPIKey"), prop.getProperty("callInitiatedOrAcceptedUser"));
		Non_WebDriver_Util.waitThread(2);
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.muteAndUnmute(getDriver());
		Non_WebDriver_Util.playAudio(getDriver(), "Customer");
		Non_WebDriver_Util.waitThread(12);
		dashboardPageFactory.muteAndUnmute(getDriver());
		Non_WebDriver_Util.playAudio(getDriver(), "Agent1");
		Non_WebDriver_Util.waitThread(12);
		dashboardPageFactory.putOnHold(getDriver());
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.switchToNewTab(getDriver());
		zuperTwilioPageFactory.disconnectTheCallFromTwilioEnd();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		Non_WebDriver_Util.waitThread(5);
		callsListingPageFactory.getCallSummary();
		callsListingPageFactory.checkKeywords("normal");
		dashboardPageFactory.zuperToTwilio();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		logger.info(
				"<<<<<<Incomming and Outgoing call functionalities with Zuper Twilio app is working as expected.>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Incomming and Outgoing call functionalities with Zuper Twilio app is working as expected.");
		System.out.println(
				"<<<<<<Incomming and Outgoing call functionalities with Zuper Twilio app is working as expected.>>>>>>");
	}

	@Parameters("url")
	@Test(priority = 2, alwaysRun = true)
	public void verify_ColdTransferFunctionality(@Optional("liveNext")String url) {
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent.createTest("Verifying Cold Transfer functionality");
		System.out.println("Verifying Cold Transfer functionality");
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.markUserAsAvailable(getDriver());
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		dashboardPageFactory.zuperToTwilio();
		zuperTwilioPageFactory.callingCustomerNumber(prop.getProperty("customerNumber"));
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.checkAndAcceptTheCall(getDriver());	
		Non_WebDriver_Util.getInProgressCallUID(prop.getProperty("getCallsUSEast"), prop.getProperty("usEastAPIKey"), prop.getProperty("callInitiatedOrAcceptedUser"));
		dashboardPageFactory.muteAndUnmute(getDriver());
		Non_WebDriver_Util.playAudio(getDriver(), "Customer");
		Non_WebDriver_Util.waitThread(12);
		dashboardPageFactory.muteAndUnmute(getDriver());
		Non_WebDriver_Util.playAudio(getDriver(), "ColdTransferAgent1Message");
		Non_WebDriver_Util.waitThread(8);
		initilizeConfigNew("incognito", prop.getProperty(url));
		companyPageFactory.enterCompanyNameDetails(prop.getProperty("company_Name"),
				incognitoDriver);
		companyPageFactory.enter_LoginSceanrio(prop.getProperty("feEmail"), prop.getProperty("fePassword"),
				incognitoDriver);
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.refreshPage(incognitoDriver);
		Non_WebDriver_Util.waitThread(3);
		dashboardPageFactory.switchingToFrame(incognitoDriver);
		dashboardPageFactory.markUserAsAvailable(incognitoDriver);
		dashboardPageFactory.selectFEToTransfer(prop.getProperty("feToTransfer"));
		dashboardPageFactory.transferringCallToFE(prop.getProperty("feToTransfer"));
		Non_WebDriver_Util.waitThread(1);
		dashboardPageFactory.checkAndAcceptTheCall(incognitoDriver);
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.playAudio(incognitoDriver, "Agent1");
		Non_WebDriver_Util.waitThread(12);
		dashboardPageFactory.putOnHold(incognitoDriver);
		dashboardPageFactory.disconnectCallNew(incognitoDriver);
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		Non_WebDriver_Util.waitThread(5);
		callsListingPageFactory.getCallSummary();	
		callsListingPageFactory.checkKeywords("coldTransfer");
		dashboardPageFactory.zuperToTwilio();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		Non_WebDriver_Util.waitThread(2);
		logger.info("<<<<<<Cold Call Transfer functionality is working as expected.>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Cold Call Transfer functionality is working as expected.");
		System.out.println("<<<<<<Cold Call Transfer functionality is working as expected.>>>>>>");
	}
	

	@Test(priority = 3, alwaysRun = true)
	public void verify_WarmCallTransferFunctionality() {
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent.createTest("Verifying Warm Transfer functionality");
		System.out.println("Verifying Warm Transfer functionality");
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.markUserAsAvailable(getDriver());
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.waitThread(1);
		DashboardPageFactory.customerWindow = Non_WebDriver_Util.storeOriginalWindow(getDriver());
		dashboardPageFactory.zuperToTwilio();
		zuperTwilioPageFactory.callingCustomerNumber(prop.getProperty("customerNumber"));
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		dashboardPageFactory.switchingToFrame(getDriver());
		Non_WebDriver_Util.waitThread(1);
		dashboardPageFactory.checkAndAcceptTheCall(getDriver());
		Non_WebDriver_Util.getInProgressCallUID(prop.getProperty("getCallsUSEast"), prop.getProperty("usEastAPIKey"), prop.getProperty("callInitiatedOrAcceptedUser"));
		dashboardPageFactory.muteAndUnmute(getDriver());
		Non_WebDriver_Util.playAudio(getDriver(), "Customer");
		Non_WebDriver_Util.waitThread(12);
		dashboardPageFactory.muteAndUnmute(getDriver());
		Non_WebDriver_Util.playAudio(getDriver(), "WarmTransferAgent1Message");
		Non_WebDriver_Util.waitThread(11);
		dashboardPageFactory.selectFEToTransfer(prop.getProperty("feToTransfer"));
		dashboardPageFactory.warmTransferringCallToFE(prop.getProperty("feToTransfer"));
		Non_WebDriver_Util.waitThread(1);
		dashboardPageFactory.checkAndAcceptTheCall(incognitoDriver);
		dashboardPageFactory.mergeTheTransferedCall();
		dashboardPageFactory.muteAndUnmute(getDriver());
		Non_WebDriver_Util.playAudio(getDriver(), "Agent1");
		Non_WebDriver_Util.waitThread(12);
		Non_WebDriver_Util.quitMusic();
		dashboardPageFactory.muteAndUnmute(getDriver());
		dashboardPageFactory.putOnHold(getDriver());
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		dashboardPageFactory.putOnMute(incognitoDriver);
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		zuperTwilioPageFactory.disconnectTheWarmTransferCallFromTwilioEnd();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.verifyCallIsDisconnectedAndDailerIsReadyToUse(getDriver());
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		dashboardPageFactory.verifyCallIsDisconnectedAndDailerIsReadyToUse(incognitoDriver);
		dashboardPageFactory.quitBrowser(incognitoDriver);
		Non_WebDriver_Util.waitThread(5);
		callsListingPageFactory.getCallSummary();
		callsListingPageFactory.checkKeywords("warmTransfer");
		logger.info("<<<<<<Warm Call Transfer functionality is working as expected.>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Warm Call Transfer functionality is working as expected.");
		System.out.println("<<<<<<Warm Call Transfer functionality is working as expected.>>>>>>");
	}

	@Parameters("url")
	@Test(priority = 4, alwaysRun = true)
	public void verify_OutgoingAndIncommingMessages(@Optional("liveNext")String url) {
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verifying Incoming and Outgoing Messages functionalities on "
						+ prop.getProperty("company_Name") + " account");
		System.out.println("Verifying Incoming and Outgoing Messages functionalities on "
				+ prop.getProperty("company_Name") + " account");
		Non_WebDriver_Util.refreshPage(getDriver());
		dashboardPageFactory.navigateToConnectModule(getDriver());
		zuperConnectPageFactory.createNewMessage(getDriver());
		zuperConnectPageFactory.clickTextMessageInputSection(getDriver());
		zuperConnectPageFactory.enterTextAndSendMessage(getDriver());
		initilizeConfigNew("incognito", prop.getProperty(url));
		companyPageFactory.enterCompanyNameDetails(prop.getProperty("messgaeUserCompanyName"), incognitoDriver);
		companyPageFactory.enter_LoginSceanrio(prop.getProperty("messageUserEmail"), prop.getProperty("messageUserPassword"), incognitoDriver);	 		
		dashboardPageFactory.navigateToConnectModule(incognitoDriver);
		dashboardPageFactory.minimizeTheDailer(incognitoDriver);
		zuperConnectPageFactory.selectAllInboxes(incognitoDriver);
		zuperConnectPageFactory.navigateToReqCustomerConversation(incognitoDriver, prop.getProperty("reportBuilderAccCustomerName"));	
		zuperConnectPageFactory.checkTheIncommingMessage(incognitoDriver);
		zuperConnectPageFactory.clickTextMessageInputSection(incognitoDriver);
        zuperConnectPageFactory.enterTextAndSendMessage(incognitoDriver);
        Non_WebDriver_Util.waitThread(5);
		Non_WebDriver_Util.refreshPage(getDriver());
		Non_WebDriver_Util.waitThread(1);
		zuperConnectPageFactory.selectAllInboxes(getDriver());
		zuperConnectPageFactory.navigateToReqCustomerConversation(getDriver(), prop.getProperty("twilioLiveAccCustomerName"));	
		zuperConnectPageFactory.checkTheIncommingMessage(getDriver());	
		logger.info("<<<<<<Incoming and Outgoing Messages functionality is working as expected.>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Incoming and Outgoing Messages functionality is working as expected.");
		System.out.println("<<<<<<Incoming and Outgoing Messages functionality is working as expected.>>>>>>");
	}

	@Test(priority = 5, alwaysRun = true)
	public void verify_OutgoingAndIncommingMMS() {
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verifying Incoming and Outgoing MMS functionalities on " + prop.getProperty("company_Name")
						+ " account");
		System.out.println("Verifying Incoming and Outgoing MMS functionalities on " + prop.getProperty("company_Name")
				+ " account");
		zuperConnectPageFactory.clickTextMessageInputSection(getDriver());
		zuperConnectPageFactory.addAttachmentsToMMS(getDriver());
		Non_WebDriver_Util.waitThread(15);
		Non_WebDriver_Util.refreshPage(incognitoDriver);
		Non_WebDriver_Util.waitThread(2);
		zuperConnectPageFactory.selectAllInboxes(incognitoDriver);
		zuperConnectPageFactory.navigateToReqCustomerConversation(incognitoDriver, prop.getProperty("reportBuilderAccCustomerName"));	
		zuperConnectPageFactory.checkTheIncommingMMSMessage(incognitoDriver);
		zuperConnectPageFactory.addAttachmentsToMMS(incognitoDriver);
        Non_WebDriver_Util.waitThread(15);
		Non_WebDriver_Util.refreshPage(getDriver());
		Non_WebDriver_Util.waitThread(1);
		zuperConnectPageFactory.selectAllInboxes(getDriver());
		zuperConnectPageFactory.navigateToReqCustomerConversation(getDriver(), prop.getProperty("twilioLiveAccCustomerName"));	
		zuperConnectPageFactory.checkTheIncommingMMSMessage(getDriver());
		logger.info("<<<<<<Incoming and Outgoing MMS functionality is working as expected.>>>>>>");
		System.out.println("<<<<<<Incoming and Outgoing MMS functionality is working as expected.>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Incoming and Outgoing MMS functionality is working as expected.");
	}

	@AfterMethod
	public void creatingReport(ITestResult result) {
		Non_WebDriver_Util.addingScreenShotToReport(result, getDriver());
	}

	@AfterClass
	public void sendFinalReport() {
		long endTime = System.currentTimeMillis();
		Non_WebDriver_Util.extent.flush();
		long duration = endTime - startTime;
		long seconds = (duration / 1000) % 60;
		long minutes = (duration / (1000 * 60)) % 60;
		String reportPath = System.getProperty("user.dir") + "/ExtentReport.html";
		UtilityPackages.ReportToSlack.uploadReport(reportPath, "Live", prop.getProperty("company_Name"), minutes,
				seconds,"");
	}

	@AfterSuite(alwaysRun = true)
	public void closeBrowsers() {

		if (incognitoDriver != null) {
			incognitoDriver.quit();
		}

		WebDriver driver = getDriver();
		if (driver != null) {
			driver.quit();
		}

	}
}
