package RunnerClass;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import BaseTest.Baseclass;
import PageObjectRep.CompanyPageFactory;
import PageObjectRep.DashboardPageFactory;
import PageObjectRep.ZuperConnectPageFactory;
import UtilityPackages.Non_WebDriver_Util;

public class TC_OutgoingAndIncommingMessages extends Baseclass{
	
	private CompanyPageFactory companyPageFactory;
	private DashboardPageFactory dashboardPageFactory;
	private ZuperConnectPageFactory zuperConnectPageFactory;

    @Test
    public void verify_OutgoingAndIncommingMessages() {
    	initilizeConfig();          
        companyPageFactory = new CompanyPageFactory();
        dashboardPageFactory= new DashboardPageFactory();
        zuperConnectPageFactory= new ZuperConnectPageFactory();
    	Non_WebDriver_Util.initReport();
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verifying Incoming and Outgoing Messages functionality with " +prop.getProperty("company_Name") +" account");
		companyPageFactory.enterCompanyNameDetails(prop.getProperty("company_Name"));
		companyPageFactory.enter_LoginSceanrio(prop.getProperty("useremail"), prop.getProperty("password"));
		dashboardPageFactory.popup_clear(getDriver());
		dashboardPageFactory.minimizeTheDailer(getDriver());
		dashboardPageFactory.navigateToConnectModule(getDriver());
		zuperConnectPageFactory.createNewMessage(getDriver());
		zuperConnectPageFactory.clickTextMessageInputSection(getDriver());
		zuperConnectPageFactory.enterTextAndSendMessage(getDriver());
		Non_WebDriver_Util.minimizeWindow(getDriver());
		companyPageFactory.launchBrowserInIncognito(prop.getProperty("baseURL"));
		companyPageFactory.enterCompanyNameDetailsInIncognito(prop.getProperty("messgaeUserCompanyName"));
		companyPageFactory.enter_LoginSceanrioInIncognito(prop.getProperty("messageUserEmail"), prop.getProperty("messageUserPassword"));	
		dashboardPageFactory.popup_clear(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.closeAppTourPopup(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.closeSensePopup(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.minimizeTheDailer(CompanyPageFactory.incognitoDriver);
		dashboardPageFactory.navigateToConnectModuleInIncognito(CompanyPageFactory.incognitoDriver);
		zuperConnectPageFactory.selectAllInboxes(CompanyPageFactory.incognitoDriver);
		zuperConnectPageFactory.navigateToTheRecentReceivedMessage(CompanyPageFactory.incognitoDriver);
		zuperConnectPageFactory.checkTheIncommingMessage(CompanyPageFactory.incognitoDriver);
		zuperConnectPageFactory.clickTextMessageInputSection(CompanyPageFactory.incognitoDriver);
        zuperConnectPageFactory.enterTextAndSendMessageFromAnotherUser(CompanyPageFactory.incognitoDriver);
        Non_WebDriver_Util.minimizeWindow(CompanyPageFactory.incognitoDriver);
        Non_WebDriver_Util.maximizeWindow1(getDriver());
        Non_WebDriver_Util.waitThread(5);
		Non_WebDriver_Util.refreshPage(getDriver());
		Non_WebDriver_Util.waitThread(1);
		zuperConnectPageFactory.selectAllInboxes(getDriver());
		zuperConnectPageFactory.navigateToTheRecentReceivedMessage(getDriver());	
		zuperConnectPageFactory.checkTheIncommingMessageFromAdminUser(getDriver());	
		logger.info("<<<<<<Incoming and Outgoing Messages functionality is working as expected.>>>>>>");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Incoming and Outgoing Messages functionality is working as expected.");	
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
                    "Incomming and Outgoing Message Functionalities");
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

