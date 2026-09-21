package RunnerClass;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import BaseTest.Baseclass;
import PageObjectRep.CompanyPageFactory;
import PageObjectRep.DashboardPageFactory;
import PageObjectRep.RequestPageFactory;
import UtilityPackages.Non_WebDriver_Util;

public class TC_RequestCreationAnCovertToJobFunctionalities extends Baseclass {

	private CompanyPageFactory companyPageFactory;
	private DashboardPageFactory dashboardPageFactory;
	private RequestPageFactory requestPageFactory;

	@BeforeClass
	public void setUp() {
		initilizeConfig(); // BaseClass setup
		companyPageFactory = new CompanyPageFactory();
		dashboardPageFactory = new DashboardPageFactory();
		requestPageFactory = new RequestPageFactory();
	}

	@Test
	public void verify_AssetCreationDeactivateAndActivateFunctionalities() {
		Non_WebDriver_Util.initReport();
		getDriver().navigate().to(prop.getProperty("UAT_URL"));
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verify Request creation, Convert Request to Job and Update Request Status functionalities");
		companyPageFactory.enterCompanyNameDetails(prop.getProperty("company_Name"));
		companyPageFactory.enter_LoginSceanrio(prop.getProperty("useremail"), prop.getProperty("password"));
		dashboardPageFactory.popup_clear(getDriver());
		dashboardPageFactory.minimizeTheDailer(getDriver());
		dashboardPageFactory.navigateToRequest();
		requestPageFactory.createNewRequest();
		requestPageFactory.convertToJob();
		requestPageFactory.updateReqStatus();
		logger.info(
				"<<<<<<Request creation, Convert Request to Job and Update Request Status functionalities are working as expceted>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Request creation, Convert Request to Job and Update Request Status functionalities are working as expceted");
	}

	@AfterMethod
	public void creatingReport(ITestResult result) {
		Non_WebDriver_Util.addingScreenShotToReport(result, getDriver());
	}

	@AfterSuite
	public void sendFinalReport() {
		Non_WebDriver_Util.extent.flush();
	}

}
