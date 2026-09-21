package RunnerClass;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import BaseTest.Baseclass;
import PageObjectRep.CompanyPageFactory;
import PageObjectRep.ContractsPageFactory;
import PageObjectRep.DashboardPageFactory;
import UtilityPackages.Non_WebDriver_Util;

public class TC_ContractCreationDeactivateAndActivateFunctionalities extends Baseclass {

	private CompanyPageFactory companyPageFactory;
	private DashboardPageFactory dashboardPageFactory;
	private ContractsPageFactory contractsPageFactory;

	@BeforeClass
	public void setUp() {
		initilizeConfig(); // BaseClass setup
		companyPageFactory = new CompanyPageFactory();
		dashboardPageFactory = new DashboardPageFactory();
		contractsPageFactory = new ContractsPageFactory();
	}

	@Test
	public void verify_ContractCreationDeactivateAndActivateFunctionalities() {
		Non_WebDriver_Util.initReport();
		getDriver().navigate().to(prop.getProperty("UAT_URL"));
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verify Contract creation, Deactivate and Activate contract and Delete Contract functionalities");
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
		logger.info(
				"<<<<<<Contract creation, Deactivate and Activate contract and Delete Contract functionalities are working as expceted>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Contract creation, Deactivate and Activate contract and Delete Contract functionalities are working as expceted");
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
