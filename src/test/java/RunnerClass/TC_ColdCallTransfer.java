package RunnerClass;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import BaseTest.Baseclass;
import PageObjectRep.CompanyPageFactory;
import PageObjectRep.DashboardPageFactory;
import PageObjectRep.ZuperTwilioVoicePageFactory;
import UtilityPackages.Non_WebDriver_Util;

public class TC_ColdCallTransfer extends Baseclass {

	private CompanyPageFactory companyPageFactory;
	private DashboardPageFactory dashboardPageFactory;
	private ZuperTwilioVoicePageFactory zuperTwilioPageFactory;

	@Test
	public void verify_ColdTransferFunctionality() {
		initilizeConfig();
		companyPageFactory = new CompanyPageFactory();
		dashboardPageFactory = new DashboardPageFactory();
		zuperTwilioPageFactory = new ZuperTwilioVoicePageFactory();
		Non_WebDriver_Util.initReport();
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verifying Cold Transfer functionality with " + prop.getProperty("feToTransfer") + " user");
		companyPageFactory.enterCompanyNameDetails(prop.getProperty("company_Name"));
		companyPageFactory.enter_LoginSceanrio(prop.getProperty("useremail"), prop.getProperty("password"));
		dashboardPageFactory.getCurrentWindow();
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
		Non_WebDriver_Util.maximizeWindow1(getDriver());
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.selectFEToTransfer(prop.getProperty("feToTransfer"));
		Non_WebDriver_Util.waitThread(3);
		dashboardPageFactory.transferringCallToFE(prop.getProperty("feToTransfer"));
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.minimizeWindow(getDriver());
		dashboardPageFactory.switchingToFrame(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.checkAndAcceptTheCallInIncognito();
		dashboardPageFactory.putOnHold(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.putOnMute(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.disconnectCallFromIncognito();
		dashboardPageFactory.switchingToDefaultContent(CompanyPageFactory.incognitoDriver);
		logger.info("<<<<<<Cold Call Transfer functionality is working as expected.>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Cold Call Transfer functionality is working as expected.");
	}

	@AfterTest(alwaysRun = true)
	public void sendReportToSlack() {

		System.out.println("===== AFTER METHOD STARTED =====");

		try {
			Non_WebDriver_Util.extent.flush();
			System.out.println("Extent flushed");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String reportPath = System.getProperty("user.dir") + "/ExtentReport.html";
			UtilityPackages.ReportToSlack.uploadReport(reportPath, "Live", prop.getProperty("company_Name"), 0, 0,
					"Cold Transfer Functionality");
			System.out.println("Slack upload completed");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (CompanyPageFactory.incognitoDriver != null)
				CompanyPageFactory.incognitoDriver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (getDriver() != null)
				getDriver().quit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("===== AFTER METHOD FINISHED =====");
	}
}
