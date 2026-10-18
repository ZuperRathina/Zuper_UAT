package RunnerClass;

import org.testng.annotations.Test;
import BaseTest.Baseclass;
import PageObjectRep.CompanyPageFactory;
import UtilityPackages.Non_WebDriver_Util;

public class TestFuncs extends Baseclass{
	
	private CompanyPageFactory companyPageFactory;


    @Test
    public void verify_OutgoingAndIncommingMessages() {
    	initilizeConfigNew("normal","");          
        companyPageFactory = new CompanyPageFactory();
    	Non_WebDriver_Util.initReport();
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verifying Incoming and Outgoing Messages functionality with " +prop.getProperty("company_Name") +" account");
		companyPageFactory.enterCompanyNameDetails(prop.getProperty("company_Name"), getDriver());
		companyPageFactory.enter_LoginSceanrio(prop.getProperty("useremail"), prop.getProperty("password"), getDriver());
		System.out.println("Login via: " + getDriver());
		initilizeConfigNew("incognito",""); 
		companyPageFactory.enterCompanyNameDetails(prop.getProperty("messgaeUserCompanyName"), incognitoDriver);
		companyPageFactory.enter_LoginSceanrio(prop.getProperty("messageUserEmail"), prop.getProperty("messageUserPassword"), incognitoDriver);
		System.out.println("Login via: " + incognitoDriver);
    }


}

