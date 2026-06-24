package RunnerClass;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import BaseTest.Baseclass;
import PageObjectRep.CompanyPageFactory;
import PageObjectRep.DashboardPageFactory;
import PageObjectRep.ZuperTwilioVoicePageFactory;
import UtilityPackages.Non_WebDriver_Util;

public class TC_IncommingAndOutgoingCallsWithTwilioVoiceApp extends Baseclass {

	private CompanyPageFactory companyPageFactory;
	private DashboardPageFactory dashboardPageFactory;
	private ZuperTwilioVoicePageFactory zuperTwilioPageFactory;

	@Test
	public void verify_IncommingAndOutgoingFunctionalities() {
		initilizeConfig();
		companyPageFactory = new CompanyPageFactory();
		dashboardPageFactory = new DashboardPageFactory();
		zuperTwilioPageFactory = new ZuperTwilioVoicePageFactory();
		Non_WebDriver_Util.initReport();
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verify Incomming and Outgoing functionalities with Zuper Twilio app");
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

	@AfterMethod
	public void sendReportToSlack() {
		String reportPath = System.getProperty("user.dir") + "/ExtentReport.html";
		UtilityPackages.ReportToSlack.uploadReport(reportPath, "Live", prop.getProperty("company_Name"), 0, 0, "Incomming and Outgoing Call Functionalities");
		Non_WebDriver_Util.extent.flush();
		getDriver().quit();
	}
}
