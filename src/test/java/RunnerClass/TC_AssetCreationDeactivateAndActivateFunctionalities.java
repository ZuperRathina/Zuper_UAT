package RunnerClass;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import BaseTest.Baseclass;
import PageObjectRep.AssetsPageFactory;
import PageObjectRep.CompanyPageFactory;
import PageObjectRep.DashboardPageFactory;
import UtilityPackages.Non_WebDriver_Util;

public class TC_AssetCreationDeactivateAndActivateFunctionalities extends Baseclass {

	private CompanyPageFactory companyPageFactory;
	private DashboardPageFactory dashboardPageFactory;
	private AssetsPageFactory assetsPageFactory;

	@BeforeClass
	public void setUp() {
		initilizeConfig(); // BaseClass setup
		companyPageFactory = new CompanyPageFactory();
		dashboardPageFactory = new DashboardPageFactory();
		assetsPageFactory = new AssetsPageFactory();
	}

	@Test
	public void verify_AssetCreationDeactivateAndActivateFunctionalities() {
		Non_WebDriver_Util.initReport();
		getDriver().navigate().to(prop.getProperty("UAT_URL"));
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verify Asset creation, Deactivate and Activate Asset and Delete Asset functionalities are working as expceted");
		companyPageFactory.enterCompanyNameDetails(prop.getProperty("company_Name"));
		companyPageFactory.enter_LoginSceanrio(prop.getProperty("useremail"), prop.getProperty("password"));
		dashboardPageFactory.popup_clear(getDriver());
		dashboardPageFactory.minimizeTheDailer(getDriver());
		dashboardPageFactory.navigateToAssets();
		assetsPageFactory.createNewAsset();
		assetsPageFactory.addOrgOnDetailsPage();
		assetsPageFactory.addCustomerOnDetailsPage();
		assetsPageFactory.deactivateAssetInDetailsPage();
		assetsPageFactory.navigateToAssetListingPage();
		assetsPageFactory.checkAssetIsActiveOrInactiveInListing();
		assetsPageFactory.activateAssetFromListingPage();
		assetsPageFactory.deleteAssetFromDetailsPage();
		logger.info(
				"<<<<<<Asset creation, Deactivate and Activate Asset and Delete Asset functionalities are working as expceted>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Asset creation, Deactivate and Activate Asset and Delete Asset functionalities are working as expceted");
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
