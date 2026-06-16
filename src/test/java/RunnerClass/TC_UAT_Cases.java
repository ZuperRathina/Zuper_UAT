package RunnerClass;

import java.io.ObjectInputFilter.Status;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import BaseTest.Baseclass;
import PageObjectRep.AssetsPageFactory;
import PageObjectRep.CompanyPageFactory;
import PageObjectRep.ContractsPageFactory;
import PageObjectRep.DashboardPageFactory;
import PageObjectRep.RequestPageFactory;
import PageObjectRep.ZuperConnectPageFactory;
import PageObjectRep.ZuperTwilioVoicePageFactory;
import UtilityPackages.Non_WebDriver_Util;

public class TC_UAT_Cases extends Baseclass {

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
	public void verify_ContractCreationDeactivateAndActivateFunctionalities() {
		Non_WebDriver_Util.initReport();
		getDriver().navigate().to(prop.getProperty("UAT_URL"));
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent.createTest(
				"Verifying Contract creation, Deactivate and Activate contract and Delete Contract functionalities");
		logger.info(
				"<<<<<<Verifying Contract creation, Deactivate and Activate contract and Delete Contract functionalities>>>>>>");
		companyPageFactory.enterCompanyNameDetails(prop.getProperty("company_Name"));
		companyPageFactory.enter_LoginSceanrio(prop.getProperty("useremail"), prop.getProperty("password"));
		dashboardPageFactory.popup_clear(getDriver());
		dashboardPageFactory.minimizeTheDailer(getDriver());
		dashboardPageFactory.navigateToContracts();
		contractsPageFactory.createNewContract();
		contractsPageFactory.deactivateContractInDetailsPage();
		contractsPageFactory.activateContractInDetailsPage();
		contractsPageFactory.deactivateContractFromListingPage();
		contractsPageFactory.activateContractFromListingPage();
		contractsPageFactory.deleteContractFromDetailsPage();
		Non_WebDriver_Util.waitThread(3);
		logger.info(
				"<<<<<<Contract creation, Deactivate and Activate contract and Delete Contract functionalities are working as expceted>>>>>>");
		Non_WebDriver_Util.testCase.log(com.aventstack.extentreports.Status.PASS,
				"Contract creation, Deactivate and Activate contract and Delete Contract functionalities are working as expceted");
}

	@Test(priority = 2)
	public void verify_AssetCreationDeactivateAndActivateFunctionalities() {
		Non_WebDriver_Util.initReport();
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verifying Asset creation, Deactivate and Activate Asset and Delete Asset functionalities");
		logger.info(
				"<<<<<<Verifying Asset creation, Deactivate and Activate Asset and Delete Asset functionalities>>>>>>");
		dashboardPageFactory.navigateToAssets();
		assetsPageFactory.createNewAsset();
		assetsPageFactory.addOrgOnDetailsPage();
		assetsPageFactory.addCustomerOnDetailsPage();
		assetsPageFactory.deactivateAssetInDetailsPage();
		assetsPageFactory.navigateToAssetListingPage();
		assetsPageFactory.checkAssetIsActiveOrInactiveInListing();
		assetsPageFactory.activateAssetFromListingPage();
		assetsPageFactory.deleteAssetFromDetailsPage();
		Non_WebDriver_Util.waitThread(3);
		logger.info(
				"<<<<<<Asset creation, Deactivate and Activate Asset and Delete Asset functionalities are working as expceted>>>>>>");
		Non_WebDriver_Util.testCase.log(com.aventstack.extentreports.Status.PASS,
				"Asset creation, Deactivate and Activate Asset and Delete Asset functionalities are working as expceted");
	}

	@Test(priority = 3)
	public void verify_RequestCreationAnCovertToJobFunctionalities() {
		Non_WebDriver_Util.initReport();
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent.createTest(
				"Verifying Request creation, Convert Request to Job and Update Request Status functionalities");
		logger.info(
				"<<<<<<Verifying Request creation, Convert Request to Job and Update Request Status functionalities>>>>>>");
		dashboardPageFactory.navigateToRequest();
		requestPageFactory.createNewRequest();
		requestPageFactory.convertToJob();
		requestPageFactory.updateReqStatus();
		Non_WebDriver_Util.waitThread(3);
		logger.info(
				"<<<<<<Request creation, Convert Request to Job and Update Request Status functionalities are working as expceted>>>>>>");
		Non_WebDriver_Util.testCase.log(com.aventstack.extentreports.Status.PASS,
				"Request creation, Convert Request to Job and Update Request Status functionalities are working as expceted");
	}

//	@Test(priority = 4)
//	public void verify_IncommingAndOutgoingFunctionalities() {
//		Non_WebDriver_Util.initReport();
//		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
//				.createTest("Verify Incomming and Outgoing functionalities with Zuper Twilio app");
//		Non_WebDriver_Util.refreshPage(getDriver());
//		dashboardPageFactory.openDailer(getDriver());
//		dashboardPageFactory.switchingToFrame(getDriver());
//		dashboardPageFactory.markUserAsAvailable(getDriver());
//		dashboardPageFactory.switchingToDefaultContent(getDriver());
//		zuperTwilioPageFactory.navigateToZuperTwilioVoicepage();
//		zuperTwilioPageFactory.callingCustomerNumber(prop.getProperty("customerNumber"));
//		zuperTwilioPageFactory.navigateToZuperTwilioVoicepage();
//		zuperTwilioPageFactory.callingCustomerNumber(prop.getProperty("customerNumber"));
//		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
//		dashboardPageFactory.switchingToFrame(getDriver());
//		dashboardPageFactory.checkAndAcceptTheCall();
//		dashboardPageFactory.putOnHold(getDriver());
//		dashboardPageFactory.putOnMute(getDriver());
//		dashboardPageFactory.disconnectCall();
//		dashboardPageFactory.callingTwilioNumberBack();
//		dashboardPageFactory.switchingToDefaultContent(getDriver());
//		zuperTwilioPageFactory.acceptingTheIncommingCallOnTwilio();	
//		logger.info(
//				"<<<<<<Verify Incomming and Outgoing call functionalities with Zuper Twilio app is working as expected.>>>>>>");
//		Non_WebDriver_Util.testCase.log(Status.PASS,
//				"Verify Incomming and Outgoing call functionalities with Zuper Twilio app is working as expected.");
//	}
//
//	@Test(priority = 5, alwaysRun = true)
//	public void verify_ColdTransferFunctionality() {
//		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent.createTest("Verifying Cold Transfer functionality");
//		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
//		dashboardPageFactory.switchingToFrame(getDriver());
//		dashboardPageFactory.markUserAsAvailable(getDriver());
//		dashboardPageFactory.switchingToDefaultContent(getDriver());
//		dashboardPageFactory.zuperToTwilio();
//		zuperTwilioPageFactory.callingCustomerNumber(prop.getProperty("customerNumber"));
//		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
//		dashboardPageFactory.switchingToFrame(getDriver());
//		dashboardPageFactory.checkAndAcceptTheCall();
//		dashboardPageFactory.switchingToDefaultContent(getDriver());
//		Non_WebDriver_Util.minimizeWindow(getDriver());
//		companyPageFactory.launchBrowserInIncognito(prop.getProperty("UAT_URL"));
//		companyPageFactory.enterCompanyNameDetailsInIncognito(prop.getProperty("company_Name"));
//		companyPageFactory.enter_LoginSceanrioInIncognito(prop.getProperty("feEmail"), prop.getProperty("fePassword"));
//		Non_WebDriver_Util.refreshPage(CompanyPageFactory.incognitoDriver);
//		dashboardPageFactory.popup_clear(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.minimizeWindow(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.maximizeWindow1(getDriver());
//		dashboardPageFactory.switchingToFrame(getDriver());
//		dashboardPageFactory.selectFEToTransfer(prop.getProperty("feToTransfer"));
//		dashboardPageFactory.transferringCallToFE(prop.getProperty("feToTransfer"));
//		dashboardPageFactory.switchingToDefaultContent(getDriver());
//		Non_WebDriver_Util.minimizeWindow(getDriver());
//		Non_WebDriver_Util.maximizeWindow1(CompanyPageFactory.incognitoDriver);
//		dashboardPageFactory.switchingToFrame(CompanyPageFactory.incognitoDriver);
//		dashboardPageFactory.checkAndAcceptTheCallInIncognito();
//		dashboardPageFactory.putOnHold(CompanyPageFactory.incognitoDriver);
//		dashboardPageFactory.putOnMute(CompanyPageFactory.incognitoDriver);
//		dashboardPageFactory.disconnectCallFromIncognito();
//		dashboardPageFactory.switchingToDefaultContent(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.minimizeWindow(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.maximizeWindow1(getDriver());
//		logger.info("<<<<<<Cold Call Transfer functionality is working as expected.>>>>>>");
//		Non_WebDriver_Util.testCase.log(Status.PASS, "Cold Call Transfer functionality is working as expected.");
//	}
//
//	@Test(priority = 6, alwaysRun = true)
//	public void verify_WarmCallTransferFunctionalities() {
//		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent.createTest("Verifying Warm Transfer functionality");
//		Non_WebDriver_Util.refreshPage(getDriver());
//		Non_WebDriver_Util.waitThread(2);
//		DashboardPageFactory.customerWindow = Non_WebDriver_Util.storeOriginalWindow(getDriver());
//		dashboardPageFactory.zuperToTwilio();
//		zuperTwilioPageFactory.callingCustomerNumber(prop.getProperty("customerNumber"));
//		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
//		dashboardPageFactory.switchingToFrame(getDriver());
//		dashboardPageFactory.checkAndAcceptTheCall();
//		dashboardPageFactory.selectFEToTransfer(prop.getProperty("feToTransfer"));
//		dashboardPageFactory.warmTransferringCallToFE(prop.getProperty("feToTransfer"));
//		dashboardPageFactory.switchingToDefaultContent(getDriver());
//		Non_WebDriver_Util.minimizeWindow(getDriver());
//		Non_WebDriver_Util.maximizeWindow1(CompanyPageFactory.incognitoDriver);
//		dashboardPageFactory.switchingToFrame(CompanyPageFactory.incognitoDriver);
//		dashboardPageFactory.checkAndAcceptTheCallInIncognito();
//		dashboardPageFactory.switchingToDefaultContent(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.minimizeWindow(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.maximizeWindow1(getDriver());
//		dashboardPageFactory.switchingToFrame(getDriver());
//		dashboardPageFactory.mergeTheTransferedCall();
//		dashboardPageFactory.putOnMute(getDriver());
//		dashboardPageFactory.putOnHold(getDriver());
//		dashboardPageFactory.switchingToDefaultContent(getDriver());
//		Non_WebDriver_Util.minimizeWindow(getDriver());
//		Non_WebDriver_Util.maximizeWindow1(CompanyPageFactory.incognitoDriver);
//		dashboardPageFactory.switchingToFrame(CompanyPageFactory.incognitoDriver);
//		dashboardPageFactory.putOnMute(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.minimizeWindow(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.maximizeWindow1(getDriver());
//		dashboardPageFactory.switchingToDefaultContent(CompanyPageFactory.incognitoDriver);
//		zuperTwilioPageFactory.disconnectTheWarmTransferCallFromTwilioEnd();
//		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
//		dashboardPageFactory.switchingToFrame(getDriver());
//		dashboardPageFactory.verifyCallIsDisconnectedAndDailerIsReadyToUse();
//		dashboardPageFactory.switchingToDefaultContent(getDriver());
//		Non_WebDriver_Util.minimizeWindow(getDriver());
//		Non_WebDriver_Util.maximizeWindow1(CompanyPageFactory.incognitoDriver);
//		dashboardPageFactory.switchingToFrame(CompanyPageFactory.incognitoDriver);
//		dashboardPageFactory.verifyCallIsDisconnectedAndDailerIsReadyToUseInIncognito();
//		Non_WebDriver_Util.maximizeWindow1(getDriver());
//		logger.info("<<<<<<Warm Call Transfer functionality is working as expected.>>>>>>");
//		Non_WebDriver_Util.testCase.log(Status.PASS, "Warm Call Transfer functionality is working as expected.");
//	}
//
//	@Test(priority = 7, alwaysRun = true)
//	public void verify_OutgoingAndIncommingMessages() {
//		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
//				.createTest("Verifying Incomming and Outgoing Messages functionalities on "
//						+ prop.getProperty("company_Name") + " account");
//		Non_WebDriver_Util.refreshPage(getDriver());
//		dashboardPageFactory.navigateToConnectModule(getDriver());
//		zuperConnectPageFactory.createNewMessage(getDriver());
//		zuperConnectPageFactory.clickTextMessageInputSection(getDriver());
//		zuperConnectPageFactory.enterTextAndSendMessage(getDriver());
//		Non_WebDriver_Util.minimizeWindow(getDriver());
//		companyPageFactory.launchBrowserInIncognito(prop.getProperty("UAT_URL"));
//		companyPageFactory.enterCompanyNameDetailsInIncognito(prop.getProperty("messgaeUserCompanyName"));
//		companyPageFactory.enter_LoginSceanrioInIncognito(prop.getProperty("messageUserEmail"),
//				prop.getProperty("messageUserPassword"));
//		dashboardPageFactory.popup_clear(CompanyPageFactory.incognitoDriver);
//		dashboardPageFactory.minimizeTheDailer(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.maximizeWindow1(getDriver());
//		dashboardPageFactory.navigateToConnectModuleInIncognito(CompanyPageFactory.incognitoDriver);
//		zuperConnectPageFactory.selectAllInboxes(CompanyPageFactory.incognitoDriver);
//		zuperConnectPageFactory.navigateToTheRecentReceivedMessage(CompanyPageFactory.incognitoDriver);
//		zuperConnectPageFactory.checkTheIncommingMessage(CompanyPageFactory.incognitoDriver);
//		zuperConnectPageFactory.clickTextMessageInputSection(CompanyPageFactory.incognitoDriver);
//		zuperConnectPageFactory.enterTextAndSendMessageFromAnotherUser(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.minimizeWindow(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.maximizeWindow1(getDriver());
//		Non_WebDriver_Util.waitThread(5);
//		Non_WebDriver_Util.refreshPage(getDriver());
//		Non_WebDriver_Util.waitThread(1);
//		zuperConnectPageFactory.selectAllInboxes(getDriver());
//		zuperConnectPageFactory.navigateToTheRecentReceivedMessage(getDriver());
//		zuperConnectPageFactory.checkTheIncommingMessageFromAdminUser(getDriver());
//		logger.info("<<<<<<Incomming and Outgoing Messages functionality is working as expected.>>>>>>");
//		Non_WebDriver_Util.testCase.log(Status.PASS,
//				"Incomming and Outgoing Messages functionality is working as expected.");
//	}
//
//	@Test(priority = 8, alwaysRun = true)
//	public void verify_OutgoingAndIncommingMMS() {
//		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
//				.createTest("Verifying Incomming and Outgoing MMS functionalities on "
//						+ prop.getProperty("company_Name") + " account");
//		zuperConnectPageFactory.clickTextMessageInputSection(getDriver());
//		zuperConnectPageFactory.addAttachmentsToMMS(getDriver());
//		Non_WebDriver_Util.minimizeWindow(getDriver());
//		Non_WebDriver_Util.maximizeWindow1(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.waitThread(15);
//		Non_WebDriver_Util.refreshPage(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.waitThread(2);
//		zuperConnectPageFactory.selectAllInboxes(CompanyPageFactory.incognitoDriver);
//		zuperConnectPageFactory.navigateToTheRecentReceivedMessage(CompanyPageFactory.incognitoDriver);
//		zuperConnectPageFactory.checkTheIncommingMMSMessage(CompanyPageFactory.incognitoDriver);
//		zuperConnectPageFactory.addAttachmentsToMMSFromAnotherUser(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.minimizeWindow(CompanyPageFactory.incognitoDriver);
//		Non_WebDriver_Util.maximizeWindow1(getDriver());
//		Non_WebDriver_Util.waitThread(15);
//		Non_WebDriver_Util.refreshPage(getDriver());
//		Non_WebDriver_Util.waitThread(1);
//		zuperConnectPageFactory.selectAllInboxes(getDriver());
//		zuperConnectPageFactory.navigateToTheRecentReceivedMessage(getDriver());
//		zuperConnectPageFactory.selectAllInboxes(getDriver());
//		zuperConnectPageFactory.checkTheIncommingMMSMessageInAdminUser(getDriver());
//		logger.info("<<<<<<Incomming and Outgoing MMS functionality is working as expected.>>>>>>");
//		Non_WebDriver_Util.testCase.log(Status.PASS,
//				"Incomming and Outgoing MMS functionality is working as expected.");    }
//
//	
	@AfterMethod
	public void creatingReport(ITestResult result) {
		Non_WebDriver_Util.addingScreenShotToReport(result, getDriver());
	}

	@AfterSuite
	public void sendFinalReport() {
		Non_WebDriver_Util.extent.flush();
		CompanyPageFactory.incognitoDriver.quit();
		getDriver().quit();
	}
}
