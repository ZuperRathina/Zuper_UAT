package RunnerClass;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import BaseTest.Baseclass;
//import PageObjectRep.CallsListingPageFactory;
import PageObjectRep.CompanyPageFactory;
import PageObjectRep.DashboardPageFactory;
//import PageObjectRep.ZuperConnectPageFactory;
import PageObjectRep.ZuperTwilioVoicePageFactory;
import UtilityPackages.Non_WebDriver_Util;

public class TestFuncs extends Baseclass{
	
	private CompanyPageFactory companyPageFactory;
	private DashboardPageFactory dashboardPageFactory;
	//private ZuperConnectPageFactory zuperConnectPageFactory;
	private ZuperTwilioVoicePageFactory zuperTwilioPageFactory;
	//private CallsListingPageFactory callsListingPageFactory;

    @Test
    public void testFns(@Optional("liveNext") String url) {
    	initilizeConfigNew("normal",prop.getProperty(url));          
        companyPageFactory = new CompanyPageFactory();
        dashboardPageFactory= new DashboardPageFactory();
      //  zuperConnectPageFactory= new ZuperConnectPageFactory();
        zuperTwilioPageFactory = new ZuperTwilioVoicePageFactory();
      // callsListingPageFactory = new CallsListingPageFactory();
    	Non_WebDriver_Util.initReport();
		Non_WebDriver_Util.testCase = Non_WebDriver_Util.extent
				.createTest("Verifying Incoming and Outgoing Messages functionality with " +prop.getProperty("company_Name") +" account");
		companyPageFactory.enterCompanyNameDetails(prop.getProperty("company_Name"), getDriver());
		companyPageFactory.enter_LoginSceanrio(prop.getProperty("useremail"), prop.getProperty("password"), getDriver());
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
		Non_WebDriver_Util.getInProgressCallUID(prop.getProperty("getCallsUSEast"), prop.getProperty("usEastAPIKey"), prop.getProperty("callInitiatedOrAcceptedUser"));
		dashboardPageFactory.muteAndUnmute(getDriver());
		Non_WebDriver_Util.openMusic();
		Non_WebDriver_Util.minimizeMusic();
		Non_WebDriver_Util.playAudio(getDriver(), "Customer");
		Non_WebDriver_Util.waitThread(12);
		dashboardPageFactory.muteAndUnmute(getDriver());
		Non_WebDriver_Util.playAudio(getDriver(), "Agent1");
		Non_WebDriver_Util.waitThread(12);
		dashboardPageFactory.putOnHold(getDriver());
		dashboardPageFactory.disconnectCallNew(getDriver());
		Non_WebDriver_Util.waitThread(2);
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.waitThread(10);
		//callsListingPageFactory.getCallSummary();	
		dashboardPageFactory.openDailer(getDriver());
		dashboardPageFactory.zuperToTwilio();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.callingTwilioNumberBack();
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.waitThread(3);
		zuperTwilioPageFactory.acceptingTheIncommingCallOnTwilio();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		Non_WebDriver_Util.getInProgressCallUID(prop.getProperty("getCallsUSEast"), prop.getProperty("usEastAPIKey"), prop.getProperty("callInitiatedOrAcceptedUser"));
		Non_WebDriver_Util.waitThread(2);
		dashboardPageFactory.switchingToFrame(getDriver());
		dashboardPageFactory.muteAndUnmute(getDriver());
		Non_WebDriver_Util.playAudio(getDriver(), "Customer");
		Non_WebDriver_Util.waitThread(12);
		dashboardPageFactory.muteAndUnmute(getDriver());
		Non_WebDriver_Util.playAudio(getDriver(), "Agent1");
		Non_WebDriver_Util.waitThread(12);
		Non_WebDriver_Util.quitMusic();
		dashboardPageFactory.putOnHold(getDriver());
		dashboardPageFactory.switchingToDefaultContent(getDriver());
		Non_WebDriver_Util.switchToNewTab(getDriver());
		zuperTwilioPageFactory.disconnectTheCallFromTwilioEnd();
		zuperTwilioPageFactory.navigatingFromTwilioPageToZuperDashboardPage();
		Non_WebDriver_Util.waitThread(10);
		//callsListingPageFactory.getCallSummary();	
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
            if (incognitoDriver != null)
                incognitoDriver.quit();
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

//public static void main(String[] args) {
//	
//		post();
//		printInProgressCalls(dataObjectPOST, "Twilio Admin 1");
//		
//}
//public static JSONObject dataObjectPOST;
//public static void post() {
//	try {
//		com.mashape.unirest.http.HttpResponse<JsonNode> jsonResponse = Unirest.post("https://us-east-1-connect.zuperpro.com/api/telephony/calls").header("accept", "application/json")
//				.header("Content-Type", "application/json").header("x-api-key", "edd834c7bbf2fe0494c71eb017336bb3").body("{\"filters\":{}}").asJson();
//
//		System.out.println("Status: " + jsonResponse.getStatus());
//		System.out.println("Status Text: " + jsonResponse.getStatusText());
//
//		JsonNode responseBody = jsonResponse.getBody();
//		JSONObject jsonObject = new JSONObject(responseBody.toString());
//		
//		if (jsonObject.has("data") && jsonObject.optJSONObject("data") != null) {
//			dataObjectPOST = jsonObject.getJSONObject("data");
//		} else {
//			dataObjectPOST = jsonObject;
//		}
//		System.out.println(dataObjectPOST);
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//}
//
//public static void printInProgressCalls(JSONObject jsonObject, String userNameToMatch) {
//    JSONArray callsArray = jsonObject.getJSONArray("data");
//    boolean callFound = false;
//
//    for (int i = 0; i < callsArray.length(); i++) {
//        JSONObject call = callsArray.getJSONObject(i);
//
//        // Check status first
//        if (!"IN_PROGRESS".equalsIgnoreCase(call.optString("status"))) {
//            continue;
//        }
//
//        String direction = call.optString("direction");
//        JSONObject userObject = null;
//
//        // Get username based on call direction
//        if ("OUTGOING".equalsIgnoreCase(direction)) {
//            JSONObject fromObject = call.optJSONObject("from");
//            if (fromObject != null) {
//                userObject = fromObject.optJSONObject("user");
//            }
//        } else if ("INCOMING".equalsIgnoreCase(direction)) {
//            JSONObject toObject = call.optJSONObject("to");
//            if (toObject != null) {
//                userObject = toObject.optJSONObject("user");
//            }
//        }
//
//        String userName = (userObject != null)
//                ? userObject.optString("user_name", "")
//                : "";
//
//        // Match username
//        if (userName.equalsIgnoreCase(userNameToMatch)) {
//            String fromNumber = call.optString("from_number");
//            String toNumber = call.optString("to_number");
//            String callUID = call.optString("call_uid");
//
//            System.out.println("From Number: " + fromNumber);
//            System.out.println("To Number: " + toNumber);
//            System.out.println("Call UID: " + callUID);
//            System.out.println("Direction: " + direction);
//            System.out.println("User Name: " + userName);
//
//            callFound = true;
//            break;
//        }
//    }
//
//    if (!callFound) {
//        System.out.println("No matching IN_PROGRESS call found for user: " + userNameToMatch);
//    }
//}

}

