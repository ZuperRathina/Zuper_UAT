package RunnerClass;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import BaseTest.Baseclass;
import PageObjectRep.CallGroupPageFactory;
import PageObjectRep.CompanyPageFactory;
import PageObjectRep.DashboardPageFactory;
import PageObjectRep.SettingsPageFactory;
import UtilityPackages.Non_WebDriver_Util;

public class TC_CallGroupFunctionalitiesOnPrelude extends Baseclass {

	private CompanyPageFactory companyPageFactory;
	private DashboardPageFactory dashboardPageFactory;
	private SettingsPageFactory settingsPageFactory;
	private CallGroupPageFactory callGroupPageFactory;

	@Test
	public void verify_CallGroupFunctionalitiesOnPrelude(@Optional("UAT") String url) {
		initilizeConfigNew("normal", prop.getProperty(url));    
		getDriver().navigate().to(prop.getProperty("nextURL"));
		companyPageFactory = new CompanyPageFactory();
		dashboardPageFactory = new DashboardPageFactory();
		settingsPageFactory = new SettingsPageFactory();
		callGroupPageFactory = new CallGroupPageFactory();
		Non_WebDriver_Util.initReport();
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verifying the Call Group creation, update, and deletion functionalities in Prelude.");		
		companyPageFactory.enterCompanyNameDetails(prop.getProperty("company_Name"), getDriver());
		companyPageFactory.enter_LoginSceanrio(prop.getProperty("useremail"), prop.getProperty("password"),
				getDriver());	
		dashboardPageFactory.navigateToSettings();
		settingsPageFactory.navigateToCallSettings();
		dashboardPageFactory.minimizeTheDailer(getDriver());
		callGroupPageFactory.createNewCallGroup();
		callGroupPageFactory.editExistingCallGroup();
		callGroupPageFactory.deleteExistingCallGroup();		
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

//		try {
//			String reportPath = System.getProperty("user.dir") + "/ExtentReport.html";
//            UtilityPackages.ReportToSlack.uploadReport(reportPath, "Live",
//                    prop.getProperty("company_Name"), 0, 0,
//                    "Call Group Creation, Update, and Deletion Functionalities in Prelude");
//			System.out.println("Slack upload completed");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		if (getDriver() != null)
			getDriver().quit();
	}
}
