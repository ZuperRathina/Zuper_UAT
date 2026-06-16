package PageObjectRep;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.aventstack.extentreports.Status;

import BaseTest.Baseclass;
import UtilityPackages.Non_WebDriver_Util;

public class CompanyPageFactory extends Baseclass {

	private WebDriver driver;

	public CompanyPageFactory() {
		this.driver = Baseclass.getDriver();
	}

	private By input_EnterCompanyName = By.cssSelector("#company_login_name");
	private By input_EnterEmail = By.cssSelector("#email");
	private By input_EnterPassword = By.cssSelector("#password");
	private By button_continue = By
			.xpath("//mat-label[normalize-space(text())='Company Name'][1]//following::button[1]");
	private By button_Login = By.xpath("//a[normalize-space(text())='Forgot password?'][1]/following::button[2]");
	
	public static WebDriver incognitoDriver;

	public void enterCompanyNameDetails(String name) {
		Non_WebDriver_Util.waitForBeClickable(driver, input_EnterCompanyName, 5);
		driver.findElement(input_EnterCompanyName).clear();
		driver.findElement(input_EnterCompanyName).sendKeys(name);
		driver.findElement(button_continue).click();
	}

	public void enter_LoginSceanrio(String mail, String password) {
		driver.findElement(input_EnterEmail).clear();
		driver.findElement(input_EnterEmail).sendKeys(mail);
		driver.findElement(input_EnterPassword).clear();
		driver.findElement(input_EnterPassword).sendKeys(password);
		driver.findElement(button_Login).click();
	}

	public void enterCompanyNameDetailsInIncognito(String name) {
		Non_WebDriver_Util.waitThread(3);
		incognitoDriver.findElement(input_EnterCompanyName).clear();
		incognitoDriver.findElement(input_EnterCompanyName).sendKeys(name);
		incognitoDriver.findElement(button_continue).click();
	}

	public void enter_LoginSceanrioInIncognito(String mail, String password) {
		try {		
			Non_WebDriver_Util.waitThread(5);
			incognitoDriver.findElement(input_EnterEmail).sendKeys(mail);
			Non_WebDriver_Util.waitThread(3);
			incognitoDriver.findElement(input_EnterPassword).sendKeys(password);
			Non_WebDriver_Util.waitThread(1);
			incognitoDriver.findElement(button_Login).click();
		} catch (Exception e) {
			Non_WebDriver_Util.waitThread(5);
			incognitoDriver.navigate().refresh();
			Non_WebDriver_Util.waitThread(3);
			incognitoDriver.findElement(input_EnterEmail).sendKeys(mail);
			Non_WebDriver_Util.waitThread(2);
			incognitoDriver.findElement(input_EnterPassword).sendKeys(password);
			Non_WebDriver_Util.waitThread(1);
			incognitoDriver.findElement(button_Login).click();
		}		
		Non_WebDriver_Util.waitThread(7);
		logger.info("Logged in as a '"+prop.getProperty("feToTransfer")+"' user");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Logged in as a '"+prop.getProperty("feToTransfer")+"' user");
	}

	public void launchBrowserInIncognito(String url) {
		logger.info("Launching Chrome in Incognito Mode");
		ChromeOptions options1 = new ChromeOptions();
		
        options1.addArguments("--incognito");
        options1.addArguments("--disable-notifications");    
        Map<String, Object> prefs = new HashMap<>();
        Map<String, Object> profile = new HashMap<>();
        Map<String, Object> contentSettings = new HashMap<>();

        contentSettings.put("media_stream_mic", 1); 
        contentSettings.put("media_stream_camera", 1);

        profile.put("managed_default_content_settings", contentSettings);
        prefs.put("profile", profile);

        options1.setExperimentalOption("prefs", prefs);

        // auto-approve mic/camera dialog
        options1.addArguments("--use-fake-ui-for-media-stream");
        options1.addArguments("force-device-scale-factor=0.97");
        incognitoDriver = new ChromeDriver(options1);
        
        incognitoDriver.navigate().to(url);
        incognitoDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) incognitoDriver;
        js.executeScript("document.body.style.zoom='90%';");
	}

}
