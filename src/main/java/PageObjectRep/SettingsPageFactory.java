package PageObjectRep;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import BaseTest.Baseclass;
import UtilityPackages.Non_WebDriver_Util;

public class SettingsPageFactory extends Baseclass {

	private WebDriver driver;

	public SettingsPageFactory() {
		this.driver = Baseclass.getDriver();
	}

	// TODO: built from description, not inspected HTML - verify against the real DOM.
	private By dropdown_Communication = By.xpath("//span[normalize-space()='Communication']");
	private By dropdown_ZuperConnect = By.xpath("//span[normalize-space()='Zuper Connect']");
	private By option_CallSettings = By.xpath("//span[normalize-space()='Call Settings']");

	public void openCommunicationDropdown() {
		Non_WebDriver_Util.waitForBeClickable(driver, dropdown_Communication, 15);
		Non_WebDriver_Util.jsScrollAndActionClick(driver, dropdown_Communication);
		Non_WebDriver_Util.waitThread(1);
	}

	public void openZuperConnectDropdown() {
		Non_WebDriver_Util.waitForBeClickable(driver, dropdown_ZuperConnect, 15);
		Non_WebDriver_Util.jsScrollAndActionClick(driver, dropdown_ZuperConnect);
		Non_WebDriver_Util.waitThread(1);
	}

	public void navigateToCallSettings() {
		openCommunicationDropdown();
		openZuperConnectDropdown();
		Non_WebDriver_Util.waitForBeClickable(driver, option_CallSettings, 15);
		driver.findElement(option_CallSettings).click();
		Non_WebDriver_Util.waitThread(2);
		logger.info("Navigated to Call Settings page under Communication > Zuper Connect");
	}

}
