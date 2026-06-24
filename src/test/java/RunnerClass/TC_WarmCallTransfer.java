package RunnerClass;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import BaseTest.Baseclass;
import PageObjectRep.CompanyPageFactory;
import PageObjectRep.DashboardPageFactory;
import PageObjectRep.ZuperTwilioVoicePageFactory;
import UtilityPackages.Non_WebDriver_Util;

public class TC_WarmCallTransfer extends Baseclass{
	
	private CompanyPageFactory companyPageFactory;
	private DashboardPageFactory dashboardPageFactory;
	private ZuperTwilioVoicePageFactory zuperTwilioPageFactory;

    @Test
    public void verify_WarmCallTransferFunctionalities() {
    	 initilizeConfig();           //  BaseClass setup
         companyPageFactory = new CompanyPageFactory();
         dashboardPageFactory= new DashboardPageFactory();
         zuperTwilioPageFactory = new ZuperTwilioVoicePageFactory();
    	Non_WebDriver_Util.initReport();
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verifying Warm Transfer functionality");
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
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.minimizeWindow(getDriver());
		companyPageFactory.launchBrowserInIncognito(prop.getProperty("baseURL"));
		companyPageFactory.enterCompanyNameDetailsInIncognito(prop.getProperty("company_Name"));
		companyPageFactory.enter_LoginSceanrioInIncognito(prop.getProperty("feEmail"), prop.getProperty("fePassword"));
		Non_WebDriver_Util.refreshPage(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.popup_clear(CompanyPageFactory.incognitoDriver);
		Non_WebDriver_Util.maximizeWindow1(getDriver());
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.selectFEToTransfer(prop.getProperty("feToTransfer"));
		dashboardPageFactory.warmTransferringCallToFE(prop.getProperty("feToTransfer"));
		DashboardPageFactory.customerWindow = Non_WebDriver_Util.storeOriginalWindow(getDriver());
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.minimizeWindow(getDriver());
		dashboardPageFactory.switchingToFrame(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.checkAndAcceptTheCallInIncognito();
		dashboardPageFactory.switchingToDefaultContent(CompanyPageFactory.incognitoDriver);
		Non_WebDriver_Util.minimizeWindow(CompanyPageFactory.incognitoDriver);
		Non_WebDriver_Util.maximizeWindow1(getDriver());
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.mergeTheTransferedCall();
		dashboardPageFactory.putOnMute(getDriver());
		dashboardPageFactory.putOnHold(getDriver());
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.minimizeWindow(getDriver());
		Non_WebDriver_Util.maximizeWindow1(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.switchingToFrame(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.putOnMute(CompanyPageFactory.incognitoDriver);
		Non_WebDriver_Util.minimizeWindow(CompanyPageFactory.incognitoDriver);
		Non_WebDriver_Util.maximizeWindow1(getDriver());
		dashboardPageFactory.switchingToDefaultContent(CompanyPageFactory.incognitoDriver);
		zuperTwilioPageFactory.disconnectTheWarmTransferCallFromTwilioEnd();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.verifyCallIsDisconnectedAndDailerIsReadyToUse();
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.minimizeWindow(getDriver());
		Non_WebDriver_Util.maximizeWindow1(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.switchingToFrame(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.verifyCallIsDisconnectedAndDailerIsReadyToUseInIncognito();
		logger.info("<<<<<<Warm Call Transfer functionality is working as expected.>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Warm Call Transfer functionality is working as expected.");
		Non_WebDriver_Util.extent.flush();
    }

    @AfterMethod
    public void sendReportToSlack() {
    	String reportPath = System.getProperty("user.dir") + "/ExtentReport.html";
		UtilityPackages.ReportToSlack.uploadReport(reportPath, "Live", prop.getProperty("company_Name"), 0, 0, "Warm Transfer Functionality");
		getDriver().quit();
		CompanyPageFactory.incognitoDriver.quit();
    } 
}

