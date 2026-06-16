package PageObjectRep;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import BaseTest.Baseclass;
import UtilityPackages.Non_WebDriver_Util;

public class AssetsPageFactory extends Baseclass {

	private WebDriver driver;

	public AssetsPageFactory() {
		this.driver = Baseclass.getDriver();
	}

	private By button_NewAsset = By.xpath("//breadcrumb//span[normalize-space()='New Asset']");
	private By input_AssetName = By.cssSelector("input#assetName");
	private By input_AssetCode = By.cssSelector("input#assetCode");
	private By dropdown_AssetCategory = By
			.xpath("//label[normalize-space()='Asset Category']/parent::div/mat-form-field");
//	private By elements_AssetCategory = By.xpath("//mat-option/div");
	private By button_SaveAsset = By.xpath("//span[text()='Save Asset']");
	private By button_CreateOnPopup = By.xpath("//button[text()=' Create ']");
	private By element_AssetTitle = By.xpath("//breadcrumb//li[2]//a");
	private By icon_AddOrganization = By.xpath("(//span[normalize-space()='Organization'])[1]//em");
	private By element_SearchOrg = By.xpath("//input[@placeholder='Search Organizations ...']");
	private By radio_SelectOrganization = By
			.xpath("//label[contains(normalize-space(),'1Plus')]/parent::div/parent::div/parent::div//input");
	private By button_ChooseOrg = By.xpath("//button[normalize-space()='Choose Organization']");
	private By message_AssetUpdatedSuccess = By.xpath("//div[text()='Asset Updated successfully']");
	private By icon_AddCustomer = By
			.xpath("(//span[normalize-space()='Contact' or normalize-space()='Customer'])[1]//em");
	private By button_ChooseCustomer = By.xpath("//button[normalize-space()='Choose Contact' or normalize-space()='Choose Customer']");
	private By button_MoreActions = By.xpath("//span[text()='More Actions']");
	private By button_Deactivate = By.xpath("//*[normalize-space()='Deactivate']");
	private By element_DeActivatedMessage = By.xpath("//div[text()='Asset Activated / Deactivated successfully']");
	private By link_AssetssOnBreadcrumbs = By.xpath("//a[text()='Assets']");
	private By textBox_Search = By.xpath("//z-view//input[@placeholder='Search']");
	private By button_Activate = By.xpath("//*[normalize-space()='Activate']");
	private By button_ActivateOnPopup = By.xpath("//button[text()=' Activate ']");
	private By button_Delete = By.xpath("(//span[normalize-space()='Delete'])[2]");
	private By button_DeleteOnPopup = By.xpath("//button[normalize-space()='Delete']");
	private By element_DeletedMessage = By.xpath("//div[text()='Asset deleted successfully']");

	public void activateAssetFromListingPage() {
		Non_WebDriver_Util.waitThread(1);
	String threeDotMenuBard = String.format("//a[normalize-space()='%s']/ancestor::tr/td[10]/button", assetTitle);
	driver.findElement(By.xpath(threeDotMenuBard)).click();
	Non_WebDriver_Util.waitForBeClickable(driver, button_Activate, 5);
	driver.findElement(button_Activate).click();
	driver.findElement(button_ActivateOnPopup).click();
	Non_WebDriver_Util.waitThread(2);
	Non_WebDriver_Util.waitForVisible(driver, element_DeActivatedMessage, 5);
	logger.info("Asset is activated from the listing page");
	Non_WebDriver_Util.testCase.log(Status.PASS,
			"Asset is activated from the listing page");
	checkAssetIsActiveOrInactiveInListing();
}

	public void deleteAssetFromDetailsPage() {
		String assetLink = String.format("//a[normalize-space()='%s']/ancestor::tr/td[3]//a", assetTitle);
		driver.findElement(By.xpath(assetLink)).click();
		deactivateAssetInDetailsPage();
		Non_WebDriver_Util.waitForBeClickable(driver, button_MoreActions, 5);
		driver.findElement(button_MoreActions).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, button_Delete, 5);
		driver.findElement(button_Delete).click();
		Non_WebDriver_Util.waitForBeClickable(driver, button_DeleteOnPopup, 5);
		driver.findElement(button_DeleteOnPopup).click();
		Non_WebDriver_Util.waitForVisible(driver, element_DeletedMessage, 10);
		logger.info(assetTitle +" Asset is Deleted Successfully");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				assetTitle+" Asset is Deleted Successfully");
	}

	public void activateContractFromListingPage() {
		Non_WebDriver_Util.waitThread(1);
		String threeDotMenuBard = String.format("//a[normalize-space()='%s']/ancestor::tr/td[9]/button", assetTitle);
		driver.findElement(By.xpath(threeDotMenuBard)).click();
		Non_WebDriver_Util.waitForBeClickable(driver, button_Activate, 5);
		driver.findElement(button_Activate).click();
		driver.findElement(button_ActivateOnPopup).click();
		logger.info("Asset is Activated from the listing page");
		Non_WebDriver_Util.waitThread(2);
		checkAssetIsActiveOrInactiveInListing();
	}

	public void checkAssetIsActiveOrInactiveInListing() {
		driver.findElement(textBox_Search).clear();
		driver.findElement(textBox_Search).sendKeys(assetTitle);
		Non_WebDriver_Util.pageEnterKeyClick(driver);
		String assetLink = String.format("//a[normalize-space()='%s']/parent::div//span[2]", assetTitle);
		boolean isInactive = driver.findElements(By.xpath(assetLink)).size() > 0;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		if (isInactive) {
			logger.info("Asset is in In-Active State on Listing page");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Asset is in In-Active State on Listing page");
		} else {
			logger.info("Asset is on Active State on Listing page");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Asset is on Active State on Listing page");
		}
		
//		if (!driver.findElements(By.xpath(assetLink)).isEmpty()) {
//			logger.info("Asset is in In-Active State on Listing page");
//			Non_WebDriver_Util.testCase.log(Status.PASS,
//					"Asset is in In-Active State on Listing page");
//		} else {
//			logger.info("Asset is on Active State on Listing page");
//			Non_WebDriver_Util.testCase.log(Status.PASS,
//					"Asset is on Active State on Listing page");
//		}
	}

	public void deactivateAssetInDetailsPage() {
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, button_MoreActions, 5);
		driver.findElement(button_MoreActions).click();
		Non_WebDriver_Util.waitForBeClickable(driver, button_Deactivate, 5);
		driver.findElement(button_Deactivate).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, button_Deactivate, 5);
		driver.findElement(button_Deactivate).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForVisible(driver, element_DeActivatedMessage, 5);
		logger.info("Asset is Deactivated from the details page");
		Non_WebDriver_Util.testCase.log(Status.PASS,
				"Asset is Deactivated from the details page");
		Non_WebDriver_Util.waitThread(3);	
	}

	public void navigateToAssetListingPage() {
		driver.findElement(link_AssetssOnBreadcrumbs).click();
	}
	public void addOrgOnDetailsPage() {
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.waitForBeClickable(driver, icon_AddOrganization, 10);
		driver.findElement(icon_AddOrganization).click();
		driver.findElement(element_SearchOrg).sendKeys("plus");
		Non_WebDriver_Util.waitThread(2);
		Non_WebDriver_Util.waitForBeClickable(driver, radio_SelectOrganization, 10);
		driver.findElement(radio_SelectOrganization).click();
		Non_WebDriver_Util.waitThread(1);
		driver.findElement(button_ChooseOrg).click();
		Non_WebDriver_Util.waitThread(1);
		if (!driver.findElements(message_AssetUpdatedSuccess).isEmpty()) {
			logger.info("Organization is added to the Asset Successfully");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Organization is added to the Asset Successfully");
		} else {
			logger.info("Organization is NOT added to the Asset Successfully");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Organization is NOT added to the Asset Successfully");
		}
	}

	public void addCustomerOnDetailsPage() {
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, icon_AddCustomer, 10);
		driver.findElement(icon_AddCustomer).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, radio_SelectOrganization, 10);
		driver.findElement(radio_SelectOrganization).click();
		Non_WebDriver_Util.waitThread(1);
		driver.findElement(button_ChooseCustomer).click();
		if (!driver.findElements(message_AssetUpdatedSuccess).isEmpty()) {
			logger.info("Customer is added to the Asset Successfully");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Customer is added to the Asset Successfully");
		} else {
			logger.info("Customer is NOT added to the Asset Successfully");
			Non_WebDriver_Util.testCase.log(Status.PASS,
					"Customer is NOT added to the Asset Successfully");
		}
	}

	public static String assetTitle;

	public void createNewAsset() {
		Non_WebDriver_Util.waitForBeClickable(driver, button_NewAsset, 10);
		//driver.findElement(button_NewAsset).click();
		Non_WebDriver_Util.jsClick(driver, button_NewAsset);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDateTime = LocalDateTime.now().format(formatter);
		driver.findElement(input_AssetName).sendKeys("UAT Asset@" + formattedDateTime);
		driver.findElement(input_AssetCode).sendKeys(prop.getProperty("assetCode"));
		driver.findElement(dropdown_AssetCategory).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.downKeyUsingActions(getDriver());
		Non_WebDriver_Util.pageEnterKeyClick(getDriver());
		Non_WebDriver_Util.waitThread(1);
		driver.findElement(button_SaveAsset).click();
		driver.findElement(button_CreateOnPopup).click();
		Non_WebDriver_Util.waitThread(5);
		String assetURL = driver.getCurrentUrl();
		logger.info("Asset is created and the UID is :" + assetURL);
		String contractTitleWholeText = driver.findElement(element_AssetTitle).getText().trim();
		String[] parts = contractTitleWholeText.split("-", 2);
		AssetsPageFactory.assetTitle = parts[1].trim();
		try {
			Non_WebDriver_Util.testCase.log(Status.PASS, "Asset is created and the UID is :" + assetURL);
					Non_WebDriver_Util.getScreenshot(driver, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	

//

//

//

//	public void checkContractStatusOnDetailsPage() {
//		String statusText = driver.findElement(element_ContractStatus).getText().trim();
//		if (statusText.equalsIgnoreCase("Active")) {
//			logger.info("Contract current status is details page is 'Active'");
//		} else {
//			logger.info("Contract current status is details page is 'Inactive'");
//		}
//	}

}
