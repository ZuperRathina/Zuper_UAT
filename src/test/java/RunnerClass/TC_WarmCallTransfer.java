package RunnerClass;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import BaseTest.Baseclass;
import PageObjectRep.CompanyPageFactory;
import PageObjectRep.DashboardPageFactory;
import PageObjectRep.ZuperTwilioVoicePageFactory;
import UtilityPackages.Non_WebDriver_Util;

public class TC_WarmCallTransfer extends Baseclass {

	private CompanyPageFactory companyPageFactory;
	private DashboardPageFactory dashboardPageFactory;
	private ZuperTwilioVoicePageFactory zuperTwilioPageFactory;

	@Test
	public void verify_WarmCallTransferFunctionalities(@Optional("liveNext") String url) {
		initilizeConfigNew("normal", prop.getProperty(url));
		companyPageFactory = new CompanyPageFactory();
		dashboardPageFactory = new DashboardPageFactory();
		zuperTwilioPageFactory = new ZuperTwilioVoicePageFactory();
		Non_WebDriver_Util.initReport();
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent.createTest("Verifying Warm Transfer functionality");
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
		initilizeConfigNew("incognito", prop.getProperty(url));
		companyPageFactory.enterCompanyNameDetails(prop.getProperty("company_Name"), incognitoDriver);
		companyPageFactory.enter_LoginSceanrio(prop.getProperty("feEmail"), prop.getProperty("fePassword"),
				incognitoDriver);
		Non_WebDriver_Util.refreshPage(incognitoDriver);
		dashboardPageFactory.switchingToFrame(incognitoDriver);
		dashboardPageFactory.markUserAsAvailable(incognitoDriver);
		dashboardPageFactory.selectFEToTransfer(prop.getProperty("feToTransfer"));
		dashboardPageFactory.warmTransferringCallToFE(prop.getProperty("feToTransfer"));
		DashboardPageFactory.customerWindow = Non_WebDriver_Util.storeOriginalWindow(getDriver());
		dashboardPageFactory.checkAndAcceptTheCall(incognitoDriver);
		dashboardPageFactory.mergeTheTransferedCall();
		dashboardPageFactory.putOnMute(getDriver());
		dashboardPageFactory.putOnHold(getDriver());
		dashboardPageFactory.putOnMute(incognitoDriver);
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		zuperTwilioPageFactory.disconnectTheWarmTransferCallFromTwilioEnd();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.verifyCallIsDisconnectedAndDailerIsReadyToUse(getDriver());
		dashboardPageFactory.verifyCallIsDisconnectedAndDailerIsReadyToUse(incognitoDriver);
		logger.info("<<<<<<Warm Call Transfer functionality is working as expected.>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Warm Call Transfer functionality is working as expected.");
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
            UtilityPackages.ReportToSlack.uploadReport(reportPath, "Live",
                    prop.getProperty("company_Name"), 0, 0,
                    "Warm Transfer Functionality");
			System.out.println("Slack upload completed");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (incognitoDriver != null)
			incognitoDriver.quit();

		if (getDriver() != null)
			getDriver().quit();

		System.out.println("===== AFTER METHOD FINISHED =====");
	}
}
