package PageObjectRep;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import BaseTest.Baseclass;
import UtilityPackages.Non_WebDriver_Util;

public class ContractsPageFactory extends Baseclass {

	private WebDriver driver;

	public ContractsPageFactory() {
		this.driver = Baseclass.getDriver();
	}

	private By button_NewContract = By.xpath("//span[text()='New Contract']");
	private By input_ContractName = By.cssSelector("input#contract_name");
	private By input_ContractTerms = By.cssSelector("input#term_months");
	private By input_StartDate = By.cssSelector("input#startDate");
	private By elements_ContractTeDropdown = By.xpath("//span[text()='Pick a Contract Template']");
	private By elements_ContractTemplate = By.xpath("//mat-option");
	private By textBox_ContractDesc = By.cssSelector("body#tinymce");
	private By button_AddContact = By
			.xpath("//span[normalize-space()='Add Contact' or normalize-space()='Add Customer']");
	private By textBox_SearchContacts = By
			.xpath("//input[@placeholder='Search Contacts ...' or @placeholder='Search Customers ...']");
	private By radioButton_SearchedContact = By
			.xpath("//label[contains(normalize-space(),'1Plus')]/parent::div/parent::div/parent::div//input");
	private By button_ChooseContact = By
			.xpath("//button[normalize-space()='Choose Contact' or normalize-space()='Choose Customer']");
	private By button_SaveContract = By.xpath("//span[text()='Save Contract']");
	private By element_ContractStatus = By.xpath("//dt[text()=' Contract Status ']/following::dd[1]/badge/span/span");
	private By button_MoreActions = By.xpath("//span[text()='More Actions']");
	private By button_Deactivate = By.xpath("//*[normalize-space()='Deactivate']");
	private By button_Activate = By.xpath("//*[normalize-space()='Activate']");
	private By button_ActivateOnPopup = By.xpath("//button[text()=' Activate ']");
	private By element_ActivatedMessage = By.xpath("//div[text()='Contract Activated successfully.']");
	private By element_DeActivatedMessage = By.xpath("//*[text()='Service Contract Deactivated successfully']");
	private By button_CreateOnPopup = By.xpath("//button[text()=' Create ']");
	private By link_ContractsOnBreadcrumbs = By.xpath("//a[text()='Contracts']");
	private By element_ContractTitle = By.xpath("//breadcrumb//li[2]//a");
	private By element_DeactivateFromListing = By.xpath("//*[normalize-space()='Deactivate']");
	private By button_Delete = By.xpath("(//span[normalize-space()='Delete'])[2]");
	private By button_DeleteOnPopup = By.xpath("//button[normalize-space()='Delete']");
	private By element_DeletedMessage = By.xpath("//*[text()='Service Contract deleted successfully']");
	private By textBox_Search = By.xpath("//z-view//input[@placeholder='Search']");
	private By element_ContractCreatedMessage = By.xpath("//div[text()='Service Contract created successfully']");
	private By element_creationPopup = By.xpath("//hot-toast-container//dynamic-view/div");
	private By checkbox_SameAsServiceAddress = By.cssSelector("input#sameAsServiceAddress");
	private By element_contractNotFound = By.xpath("//*[text()='Contract not found for the given UID']");
	

	public void deleteContractFromDetailsPage() {
		String contractLink = String.format("//a[normalize-space()='%s']/ancestor::tr/td[3]//a", contractTitle);
		driver.findElement(By.xpath(contractLink)).click();
		deactivateContractInDetailsPage();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, button_MoreActions, 5);
		driver.findElement(button_MoreActions).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, button_Delete, 5);
		driver.findElement(button_Delete).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, button_DeleteOnPopup, 5);
		driver.findElement(button_DeleteOnPopup).click();
		try {
			Non_WebDriver_Util.waitForVisible(driver, element_DeletedMessage, 30);
		} catch (Exception e) {
		driver.navigate().to(contractLink);
		boolean isPresent = driver.findElements(element_contractNotFound).size() > 0;
		if (isPresent) {
			logger.info("Contract is Deleted Successfully");
		} else {
			logger.info("Contract is Not Deleted");
		}
		}	
		Non_WebDriver_Util.waitThread(1);
		logger.info("Contract is Deleted Successfully");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Contract is Deleted Successfully");
	}

	public void deactivateContractFromListingPage() {
		driver.findElement(textBox_Search).clear();
		driver.findElement(textBox_Search).sendKeys(contractTitle);
		String threeDotMenuBard = String.format("//a[normalize-space()='%s']/ancestor::tr/td[9]/button", contractTitle);
		driver.findElement(By.xpath(threeDotMenuBard)).click();
		Non_WebDriver_Util.waitForBeClickable(driver, element_DeactivateFromListing, 5);
		driver.findElement(element_DeactivateFromListing).click();
		driver.findElement(button_Deactivate).click();
		logger.info("Contract is Deactivated from the listing page");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Contract is Deactivated from the listing page");
		Non_WebDriver_Util.waitThread(2);
		checkContractIsActiveOrInactiveInListing();
	}

	public void activateContractFromListingPage() {
		Non_WebDriver_Util.waitThread(1);
		String threeDotMenuBard = String.format("//a[normalize-space()='%s']/ancestor::tr/td[9]/button", contractTitle);
		driver.findElement(By.xpath(threeDotMenuBard)).click();
		Non_WebDriver_Util.waitForBeClickable(driver, button_Activate, 5);
		driver.findElement(button_Activate).click();
		driver.findElement(button_ActivateOnPopup).click();
		logger.info("Contract is Activated from the listing page");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Contract is Activated from the listing page");
		Non_WebDriver_Util.waitThread(1);
		checkContractIsActiveOrInactiveInListing();
	}

	public void checkContractIsActiveOrInactiveInListing() {
		String contractLink = String.format("//a[normalize-space()='%s']/parent::div//span[2]", contractTitle);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
		boolean isInactive = driver.findElements(By.xpath(contractLink)).size() > 0;
		Non_WebDriver_Util.waitThread(1);
		if (isInactive) {
			logger.info("Contract is in In-Active State on Listing page");
		} else {
			logger.info("Contract is on Active State on Listing page");
		}
//		if (!driver.findElements(By.xpath(contractLink)).isEmpty()) {
//			logger.info("Contract is in In-Active State on Listing page");
//		} else {
//			logger.info("Contract is on Active State on Listing page");
//		}
	}

	public void deactivateContractInDetailsPage() {
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.waitForBeClickable(driver, button_MoreActions, 5);
		driver.findElement(button_MoreActions).click();
		Non_WebDriver_Util.waitForBeClickable(driver, button_Deactivate, 5);
		driver.findElement(button_Deactivate).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, button_Deactivate, 5);
		driver.findElement(button_Deactivate).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForVisible(driver, element_DeActivatedMessage, 15);
		logger.info("Contract is Deactivated from the details page");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Contract is Deactivated from the details page");
		Non_WebDriver_Util.waitThread(3);
		checkContractStatusOnDetailsPage();
	}

	public void activateContractInDetailsPage() {
		Non_WebDriver_Util.waitForBeClickable(driver, button_MoreActions, 5);
		driver.findElement(button_MoreActions).click();
		Non_WebDriver_Util.waitForBeClickable(driver, button_Activate, 5);
		driver.findElement(button_Activate).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, button_ActivateOnPopup, 5);
		driver.findElement(button_ActivateOnPopup).click();
		Non_WebDriver_Util.waitForVisible(driver, element_ActivatedMessage, 5);
		logger.info("Contract is Activated from the listing page");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Contract is Activated from the listing page");
		Non_WebDriver_Util.waitThread(3);
		checkContractStatusOnDetailsPage();
		driver.findElement(link_ContractsOnBreadcrumbs).click();
	}

	public static String contractTitle;

	public void createNewContract() {
		Non_WebDriver_Util.waitForBeClickable(driver, button_NewContract, 10);
		driver.findElement(button_NewContract).click();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
		String formattedDateTime = LocalDateTime.now().format(formatter);
		Non_WebDriver_Util.waitForBeClickable(driver, input_ContractName, 10);
		driver.findElement(input_ContractName).sendKeys("UAT @" + formattedDateTime);
		driver.findElement(input_ContractTerms).sendKeys("12");
		driver.findElement(input_StartDate).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.pageEnterKeyClick(getDriver());
		Non_WebDriver_Util.waitThread(1);
		driver.findElement(elements_ContractTeDropdown).click();
		Non_WebDriver_Util.waitThread(1);
		List<WebElement> elements_ContTemplate = driver.findElements(elements_ContractTemplate);
		Non_WebDriver_Util.selectMatOptionByTextt(driver, elements_ContTemplate, "SMS Contract Template");
		Non_WebDriver_Util.waitThread(1);
		driver.switchTo().frame(0);
		Non_WebDriver_Util.tabKeyClick(getDriver());
		driver.switchTo().activeElement().sendKeys("Zuper Test Contract Description \n\n Zuper@123");
//		driver.findElement(textBox_ContractDesc).sendKeys("Zuper Test Contract Description");
		driver.switchTo().defaultContent();
		driver.findElement(button_AddContact).click();
		driver.findElement(textBox_SearchContacts).sendKeys("1Plus");
		Non_WebDriver_Util.pageEnterKeyClick(getDriver());
		driver.findElement(radioButton_SearchedContact).click();
		driver.findElement(button_ChooseContact).click();
		Non_WebDriver_Util.waitThread(3);
		driver.findElement(button_SaveContract).click();
		Non_WebDriver_Util.waitThread(1);
		try {
			Non_WebDriver_Util.waitForBeClickable(driver, button_CreateOnPopup, 5);
			driver.findElement(button_CreateOnPopup).click();
		} catch (Exception e) {
			driver.findElement(checkbox_SameAsServiceAddress).click();
			driver.findElement(button_SaveContract).click();
			Non_WebDriver_Util.waitThread(1);
			Non_WebDriver_Util.waitForBeClickable(driver, button_CreateOnPopup, 5);
			driver.findElement(button_CreateOnPopup).click();
		}
//		boolean isPresent = driver.findElements(element_creationPopup).size() > 0;
//		if (isPresent) {
//			driver.findElement(checkbox_SameAsServiceAddress).click();
//			driver.findElement(button_SaveContract).click();
//			Non_WebDriver_Util.waitThread(1);
//			Non_WebDriver_Util.waitForBeClickable(driver, button_CreateOnPopup, 15);
//			driver.findElement(button_CreateOnPopup).click();
//		} else {
//			Non_WebDriver_Util.waitForBeClickable(driver, button_CreateOnPopup, 15);
//			driver.findElement(button_CreateOnPopup).click();
//		}
		String contractMsg = driver.findElement(element_ContractCreatedMessage).getText();
		String contractURL = "";
		if (contractMsg.equalsIgnoreCase("Service Contract created successfully")) {
			contractURL = driver.getCurrentUrl();
			logger.info("Contract is created and the UID is :" + contractURL);
			Non_WebDriver_Util.waitThread(2);
			String contractTitleWholeText = driver.findElement(element_ContractTitle).getText().trim();
			String[] parts = contractTitleWholeText.split("-", 2);
			ContractsPageFactory.contractTitle = parts[1].trim();
			System.out.println(ContractsPageFactory.contractTitle);			
		}
		try {
			Non_WebDriver_Util.testCase.log(Status.PASS, "Contract is created and the UID is :" + contractURL);
			Non_WebDriver_Util.getScreenshot(driver, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkContractStatusOnDetailsPage() {
		String statusText = driver.findElement(element_ContractStatus).getText().trim();
		if (statusText.equalsIgnoreCase("Active")) {
			logger.info("Contract current status is details page is 'Active'");
		} else {
			logger.info("Contract current status is details page is 'Inactive'");
		}
	}

}
