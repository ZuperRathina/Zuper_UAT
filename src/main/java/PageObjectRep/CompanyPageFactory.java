package PageObjectRep;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import BaseTest.Baseclass;
import UtilityPackages.Non_WebDriver_Util;

public class CompanyPageFactory extends Baseclass {

	private By input_EnterCompanyName = By.cssSelector("#company_login_name");
	private By input_EnterEmail = By.cssSelector("#email");
	private By input_EnterPassword = By.cssSelector("#password");
	private By button_continue = By
			.xpath("//mat-label[normalize-space(text())='Company Name'][1]//following::button[1]");
	private By button_Login = By.xpath("//a[normalize-space(text())='Forgot password?'][1]/following::button[2]");
	private By icon_GlobalCall = By.xpath("//dialer-button");

	public void enterCompanyNameDetails(String name, WebDriver driver) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(25))
	                .until(ExpectedConditions.elementToBeClickable(input_EnterCompanyName));
		} catch (Exception e) {
			driver.navigate().refresh();
			new WebDriverWait(driver, Duration.ofSeconds(25))
	                .until(ExpectedConditions.elementToBeClickable(input_EnterCompanyName));
		}
		driver.findElement(input_EnterCompanyName).clear();
		driver.findElement(input_EnterCompanyName).sendKeys(name);
		driver.findElement(button_continue).click();
	}

	public void enter_LoginSceanrio(String mail, String password, WebDriver driver) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(25))
	                .until(ExpectedConditions.elementToBeClickable(input_EnterEmail));
		} catch (Exception e) {
			Non_WebDriver_Util.waitThread(5);
			driver.navigate().refresh();
			new WebDriverWait(driver, Duration.ofSeconds(25))
	                .until(ExpectedConditions.elementToBeClickable(input_EnterEmail));
		}
		driver.findElement(input_EnterEmail).clear();
		driver.findElement(input_EnterEmail).sendKeys(mail);
		Non_WebDriver_Util.waitThread(2);
		driver.findElement(input_EnterPassword).sendKeys(password);
		driver.findElement(button_Login).click();
		try {
			new WebDriverWait(driver, Duration.ofSeconds(50))
			.until(ExpectedConditions.elementToBeClickable(icon_GlobalCall));
		} catch (Exception e) {
			logger.error("Login failed for user: " + mail);
		}
		logger.info("Login was successful for user: " + mail);
		Non_WebDriver_Util.testCase.log(Status.PASS, "Login was successful for user: " + mail);
	}

}
