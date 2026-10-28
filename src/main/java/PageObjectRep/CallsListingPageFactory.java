package PageObjectRep;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;

import BaseTest.Baseclass;
import UtilityPackages.Non_WebDriver_Util;

public class CallsListingPageFactory extends Baseclass {

	private WebDriver driver;

	public CallsListingPageFactory() {
		this.driver = Baseclass.getDriver();
	}

	private By elements_CallSummary = By.xpath("(//zuper-drawer)[4]//app-call-summary/div/div/div[2]/p");
	private By button_CloseCallSlideout = By.xpath("((//zuper-drawer)[4]/div/div/div[2]/div/div[1]//i)[last()]");

	public void refreshPage(WebDriver driver) {
		Non_WebDriver_Util.refreshPage(driver);
	}

	public static String callSummary;

	public void getCallSummary() {
		String url = driver.getCurrentUrl();
		String baseUrl = url.substring(0, url.indexOf(".com") + 4);
		String updatedUrl = baseUrl + "/connect/calls/" + Non_WebDriver_Util.inprogressCallUID;
		driver.navigate().to(updatedUrl);
		int attempts = 0;
		boolean isSummaryPresent = false;
		while (attempts < 5) {
			Non_WebDriver_Util.waitThread(20);
			refreshPage(driver);
			if (driver.findElements(elements_CallSummary).size() > 0) {
				isSummaryPresent = true;
				break;
			}
			attempts++;
		}
		if (isSummaryPresent) {
			Non_WebDriver_Util.waitThread(2);
			callSummary = driver.findElement(elements_CallSummary).getText();
			Non_WebDriver_Util.waitThread(5);
			Non_WebDriver_Util.testCase.log(Status.PASS, "Call Summary:" + " \n " + callSummary);
			logger.info("Call Summary:" + callSummary);
		} else {
			logger.info("Call summary is not available after 5 attempts.");
		}
		attempts = 0;
		Non_WebDriver_Util.inprogressCallUID = null;
		Non_WebDriver_Util.waitForBeClickable(driver, button_CloseCallSlideout, 10);
		driver.findElement(button_CloseCallSlideout).click();
	}

	public void checkKeywords(String callType) {
		if (callSummary == null || callSummary.trim().isEmpty()) {
			System.out.println("Call Summary: Not Available");
			return;
		}

		String[] keywords;

		if (callType.equalsIgnoreCase("normal")) {
			keywords = new String[] { "storm", "roof damage", "address", "date and time" };
		} else if (callType.equalsIgnoreCase("coldTransfer")) {
			keywords = new String[] { "Storm", "roof damage", "transferred", "address", "date and time" };
		} else if (callType.equalsIgnoreCase("warmTransfer")) {
			keywords = new String[] { "Storm", "roof damage", "teammates", "address", "date and time" };
		} else {
			System.out.println("Invalid call type: " + callType);
			return;
		}

		StringBuilder result = new StringBuilder();

		for (String keyword : keywords) {
			if (result.length() > 0) {
				result.append(" | ");
			}

			result.append(keyword).append(": ")
					.append(callSummary.toLowerCase().contains(keyword.toLowerCase()) ? "Available" : "Not Available");
		}

		System.out.println(result.toString());
		Non_WebDriver_Util.testCase.log(Status.PASS, result.toString());
		result = null;
		callSummary = null;
	}
}
