package PageObjectRep;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.core.async.NonBlockingInputFeeder;

import BaseTest.Baseclass;
import UtilityPackages.Non_WebDriver_Util;

public class RequestPageFactory extends Baseclass {

	private WebDriver driver;

	public RequestPageFactory() {
		this.driver = Baseclass.getDriver();
	}

	private By button_NewRequest = By.xpath("//span[normalize-space()='New Request']");
	private By input_RequestName = By.cssSelector("input#request_title");
	private By input_RequestDescription = By.cssSelector("body#tinymce");
	private By input_DueDate = By.cssSelector("input#request_due_date");
	private By button_AddContact = By.xpath("//span[normalize-space()='Add Contact' or normalize-space()='Add Customer']");
	private By textBox_SearchContacts = By.xpath("//input[@placeholder='Search Contacts ...' or @placeholder='Search Customers ...']");
	private By radioButton_SearchedContact = By
			.xpath("//label[contains(normalize-space(),'1Plus')]/parent::div/parent::div/parent::div//input");
	private By button_ChooseContact = By.xpath("//button[normalize-space()='Choose Contact' or normalize-space()='Choose Customer']");
	private By button_SaveRequest = By.xpath("//span[text()='Save Request']");
	private By button_CreateOnPopup = By.xpath("//button[normalize-space()='Create'] ");
	private By element_RequestCreatedMessage = By.xpath("//div[text()='Customer Request Created successfully']");
	private By icon_ConvertToJob = By.xpath("//mat-icon[@svgicon='tabler:briefcase']");
	private By element_JobCreatedMessage = By.xpath("//div[text()='Job created successfully']");
	private By button_UpdateStatusIcon= By.xpath("(//*[normalize-space()='Update Status'])[1]//ancestor::mat-icon[1]");
	private By dropdown_ChooseStatus = By.xpath("(//div[normalize-space()='Choose Request Status'])[4]/parent::div");
	private By elements_DropdownOptions = By.xpath("//ng-dropdown-panel[@aria-label='Options list'] /div/div[2]/div/span");
	//private By input_Remarks = By.xpath("driver.findElement(dropdown_ChooseStatus).click();");
	private By button_UpdateStatus = By.xpath("//button[normalize-space()='Update Status']");
	private By button_UpdateOnPopup = By.xpath("//button[normalize-space()='Update']");
	private By element_StatusUpdatedMessage = By.xpath("//div[text()='Request Status Updated successfully']");
	private By button_MoreActions = By.xpath("//span[text()='More Actions']");
	private By button_Delete = By.xpath("(//span[normalize-space()='Delete'])[2]");
	private By button_DeleteOnPopup = By.xpath("//button[normalize-space()='Delete']");
	private By element_DeletedMessage = By.xpath("//div[text()='Job deleted successfully' or text()='Request deleted successfully']");
	private By element_JobCategoryDropdown = By.xpath("//ng-select[@bindvalue='category_uid']");
	
	
	public void updateReqStatus() {
		driver.navigate().to(requestURL);
		Non_WebDriver_Util.waitForBeClickable(driver, button_UpdateStatusIcon, 10);
		driver.findElement(button_UpdateStatusIcon).click();
		Non_WebDriver_Util.waitForBeClickable(driver, dropdown_ChooseStatus, 10);
		driver.findElement(dropdown_ChooseStatus).click();
		Non_WebDriver_Util.waitThread(1);
		List<WebElement> element_Options = driver.findElements(elements_DropdownOptions);
		Non_WebDriver_Util.selectMatOptionByTextt(driver, element_Options, "Closed");
		Non_WebDriver_Util.waitThread(1);
		driver.findElement(button_UpdateStatus).click();
		driver.findElement(button_UpdateOnPopup).click();
		Non_WebDriver_Util.waitForVisible(driver, element_StatusUpdatedMessage, 10);
		logger.info("Request Status is updated to Closed");
		try {
			Non_WebDriver_Util.testCase.log(Status.PASS, "Request Status is updated to Closed");
			Non_WebDriver_Util.getScreenshot(driver, "StatusUpdated");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, button_MoreActions, 5);
		driver.findElement(button_MoreActions).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, button_Delete, 5);
		driver.findElement(button_Delete).click();
		Non_WebDriver_Util.waitForBeClickable(driver, button_DeleteOnPopup, 5);
		driver.findElement(button_DeleteOnPopup).click();
		Non_WebDriver_Util.waitForVisible(driver, element_DeletedMessage, 10);
		logger.info("Request is Deleted Successfully");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Request is Deleted Successfully");
		
	}
	
	public void convertToJob() {
		Non_WebDriver_Util.refreshPage(driver);
		Non_WebDriver_Util.waitThread(2);	
		Non_WebDriver_Util.waitForBeClickable(driver, icon_ConvertToJob, 10);
		driver.findElement(icon_ConvertToJob).click();
		Non_WebDriver_Util.waitThread(2);
		driver.findElement(element_JobCategoryDropdown).click();
		Non_WebDriver_Util.pageEnterKeyClick(driver);		
		Non_WebDriver_Util.waitThread(2);
		driver.findElement(button_CreateOnPopup).click();
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.waitForVisible(driver, element_JobCreatedMessage, 10);
		logger.info("Job has been created for the Request succesfully and the Job ID is: "+driver.getCurrentUrl());
		try {
			Non_WebDriver_Util.waitThread(1);
			Non_WebDriver_Util.testCase.log(Status.PASS, "Job has been created for the Request succesfully and the Job ID is: "+driver.getCurrentUrl());
			Non_WebDriver_Util.getScreenshot(driver, "JobCreated");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, button_MoreActions, 5);
		driver.findElement(button_MoreActions).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, button_Delete, 5);
		driver.findElement(button_Delete).click();
		Non_WebDriver_Util.waitForBeClickable(driver, button_DeleteOnPopup, 5);
		driver.findElement(button_DeleteOnPopup).click();
		Non_WebDriver_Util.waitForVisible(driver, element_DeletedMessage, 10);
		logger.info("Job is Deleted Successfully");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Job is Deleted Successfully");
		Non_WebDriver_Util.waitThread(2);
	}
	
	public static String requestURL;
	public void createNewRequest() {
		Non_WebDriver_Util.waitForBeClickable(driver, button_NewRequest, 10);
		driver.findElement(button_NewRequest).click();
		Non_WebDriver_Util.waitForBeClickable(driver, button_NewRequest, 10);
		driver.findElement(button_NewRequest).click();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedDateTime = LocalDateTime.now().format(formatter);
		driver.findElement(input_RequestName).sendKeys("UAT Request @"+formattedDateTime);
		Non_WebDriver_Util.waitThread(3);
		driver.switchTo().frame(0);
		Non_WebDriver_Util.tabKeyClick(getDriver());
		driver.switchTo().activeElement().sendKeys("Zuper Test Contract Description \n\n Zuper@123");
		//driver.findElement(input_RequestDescription).sendKeys("Zuper Test Contract Description \n\n Zuper@123 ");
		driver.switchTo().defaultContent();
		driver.findElement(input_DueDate).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.pageEnterKeyClick(driver);
		driver.findElement(button_AddContact).click();
		driver.findElement(textBox_SearchContacts).sendKeys("1Plus");
		Non_WebDriver_Util.pageEnterKeyClick(getDriver());
		Non_WebDriver_Util.waitThread(2);
		driver.findElement(radioButton_SearchedContact).click();
		driver.findElement(button_ChooseContact).click();
		Non_WebDriver_Util.waitThread(2);
		driver.findElement(button_SaveRequest).click();
		driver.findElement(button_CreateOnPopup).click();
		Non_WebDriver_Util.waitForVisible(driver, element_RequestCreatedMessage, 10);
		requestURL = driver.getCurrentUrl();
		logger.info("Request has been created succesfully and the Request ID is: "+requestURL);
		try {
			Non_WebDriver_Util.waitThread(1);
			Non_WebDriver_Util.testCase.log(Status.PASS, "Request has been created succesfully and the Request ID is: "+requestURL);
					Non_WebDriver_Util.getScreenshot(driver, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
}


}
