package UtilityPackages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import UtilityPackages.Non_WebDriver_Util;

public class Non_WebDriver_Util {

	private static Actions actions;
	public static ExtentReports extent;
	public static ExtentTest testCase;
	public static String reportPath;

	public static void initReport() {
		if (extent == null) {
			reportPath = System.getProperty("user.dir") + "/ExtentReport.html";
//			File reportFile = new File(reportPath);
//			if (reportFile.exists()) {
//				reportFile.delete();
//			}
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
		}
	}

	public static void minimizeWindow(WebDriver driver) {
		driver.manage().window().setSize(new Dimension(0, 0));
	}

	public static void maximizeWindow1(WebDriver driver) {
		driver.manage().window().maximize();
	}

	public static void uploadFileUsingRobotClass(WebDriver driver, String filePath) {
		String file = filePath;
		StringSelection file2 = new StringSelection(file);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(file2, null);
		Non_WebDriver_Util.waitThread(2);
		Robot robot;
		try {
			robot = new Robot();
			robot.delay(1500);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			throw new RuntimeException("File upload failed for: " + filePath, e);
		}
	}

	public static void uploadMultipleFiles(WebDriver driver, String filePath, By uploadInputLocator) {

		File folder = new File(filePath);
		File[] files = folder.listFiles(File::isFile);

		if (files == null || files.length == 0) {
			throw new RuntimeException("No files found in the given folder path");
		}

		String filePaths = Arrays.stream(files).map(File::getAbsolutePath).collect(Collectors.joining("\n"));

		WebElement uploadInput = driver.findElement(uploadInputLocator);
		uploadInput.sendKeys(filePaths);
	}

	public static void minimizeWindow() {
		try {
			Robot robot = new Robot();
			robot.setAutoDelay(200);

			// ALT + SPACE
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_SPACE);
			robot.keyRelease(KeyEvent.VK_SPACE);
			robot.keyRelease(KeyEvent.VK_ALT);

			// N (Minimize)
			robot.keyPress(KeyEvent.VK_N);
			robot.keyRelease(KeyEvent.VK_N);

		} catch (AWTException e) {
			e.printStackTrace();
		}
		Non_WebDriver_Util.waitThread(1);
	}

	public static void switchBetweenWindows() {
		try {
			Robot robot = new Robot();
			robot.setAutoDelay(200);
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_ALT);

		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public static void maximizeWindow(WebDriver driver) {
		driver.manage().window().setSize(new Dimension(1024, 768));
		Non_WebDriver_Util.waitThread(1);
	}

	public static void returnToParentTab(WebDriver driver, String originalWindow) {
		driver.switchTo().window(originalWindow); // Switch back to the original
	}

	public static void switchToNewTab(WebDriver driver) {
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(tabs.size() - 1));
	}

	public static String getScreenshot(WebDriver driver, String screenShotName) {
		String base64Screenshot = "";

		try {

			TakesScreenshot ts = (TakesScreenshot) driver;
			base64Screenshot = ts.getScreenshotAs(OutputType.BASE64);
			String img = "<img src='data:image/png;base64," + base64Screenshot + "' height='100' width='200' "
					+ "onclick=\"(function(){ " + "var w = window.open(); "
					+ "w.document.write('<img src=\\'data:image/png;base64," + base64Screenshot
					+ "\\' style=\\'width:100%\\'>'); " + "})()\" style='cursor:pointer'/>";

			Non_WebDriver_Util.testCase.info("" + "<br>" + img);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return base64Screenshot;
	}

	public static void addingScreenShotToReport(ITestResult result, WebDriver driver) {
		try {

			if (result.getStatus() == ITestResult.FAILURE) {
				String failureReason = result.getThrowable() != null ? result.getThrowable().getMessage()
						: "No exception message available";
				String requiredMessage = failureReason.split("\\(Session info:")[0].trim();

				Non_WebDriver_Util.testCase
						.fail(result.getName() + " Method is Failed and the Reason is: " + requiredMessage);
				Non_WebDriver_Util.getScreenshot(driver, result.getName() + " Method Failed");

			} else if (result.getStatus() == ITestResult.SUCCESS) {

				Non_WebDriver_Util.testCase.pass(result.getName() + " Method is Passed");
				Non_WebDriver_Util.getScreenshot(driver, result.getName() + " Method Passed");

			} else if (result.getStatus() == ITestResult.SKIP) {

				Non_WebDriver_Util.testCase.skip(result.getName() + " Method is Skipped");
				Non_WebDriver_Util.getScreenshot(driver, result.getName() + " Method Skipped");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void clearScreenshots() {

		String folderPath = System.getProperty("user.dir") + "/test-output/screenshots/";
		File folder = new File(folderPath);

		if (folder.exists() && folder.isDirectory()) {

			File[] files = folder.listFiles();

			if (files != null) {
				for (File file : files) {
					file.delete();
				}
			}

			System.out.println("All screenshots deleted successfully.");
		}
	}

	public static void waitForVisible(WebDriver driver, By elements, int timeout) {
		new WebDriverWait(driver, Duration.ofSeconds(timeout))
				.until(ExpectedConditions.visibilityOf(driver.findElement(elements)));
	}

	public static void waitForInvisible(WebDriver driver, By elements, int timeout) {
		new WebDriverWait(driver, Duration.ofSeconds(timeout))
				.until(ExpectedConditions.invisibilityOf(driver.findElement(elements)));
	}

	public static void waitForBeClickable(WebDriver driver, By by, int timeout) {
		new WebDriverWait(driver, Duration.ofSeconds(timeout))
				.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));
	}

	public static void refreshPage(WebDriver driver) {
		driver.get(driver.getCurrentUrl());
	}

	public static boolean isIncognito(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		Object result = js.executeAsyncScript("var callback = arguments[arguments.length - 1];"
				+ "navigator.storage.estimate().then(function(e) {" + "  callback(e.quota < 120000000);" + "});");

		return Boolean.parseBoolean(result.toString());
	}

	public static void hover_and_click(WebDriver driver, By hover_element, By click_element) {
		try {
			actions = new Actions(driver);
			actions.moveToElement(driver.findElement(hover_element)).pause(Duration.ofMillis(1000))
					.moveToElement(driver.findElement(click_element)).click().build().perform();
		} catch (Exception e) {
			System.err.println("[hoverAndClick] Action failed: " + e.getMessage());
		}
	}

	public static void waitThread(double seconds) {
		try {
			Thread.sleep((long) (seconds * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void hoverTheElement(WebDriver driver, By element) {
		actions = new Actions(driver);
		actions.moveToElement(driver.findElement(element)).build().perform();
	}

	public static void selectMatOptionByText(WebDriver driver, By elements, String optionText) {
		boolean found = false;
		for (WebElement option : driver.findElements(elements)) {
			String text = option.getText().trim();
			if ((text.contains(optionText)) || (text.trim().equalsIgnoreCase(optionText))) {
				Non_WebDriver_Util.waitForVisible(driver, elements, 5);
				Non_WebDriver_Util.jsScrollAndActionClick(driver, elements);
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException("Kindly provide a correct option: '" + optionText + "'");
		}
	}

	public static void waitForVisibleNew(WebDriver driver, WebElement element, int timeout) {
		new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOf(element));
	}

	public static void jsScrollAndActionClickNew(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).pause(Duration.ofMillis(300)).click().build().perform();
	}

	public static void selectMatOptionByTextt(WebDriver driver, List<WebElement> elements, String optionText) {
		boolean found = false;
		for (WebElement option : elements) {
			String text = option.getText().trim();
			if ((text.contains(optionText)) || (text.trim().equalsIgnoreCase(optionText))) {
				Non_WebDriver_Util.waitForVisibleNew(driver, option, 5);
				Non_WebDriver_Util.jsScrollAndActionClickNew(driver, option);
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException("Kindly provide a correct option: '" + optionText + "'");
		}
	}

	public static void jsClick(WebDriver driver, By element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", driver.findElement(element));
		} catch (Exception e) {
			System.out.println("❌ JS Click failed: " + e.getMessage());
		}
	}

	public static void copyToClipBoard(String number) {
		StringSelection selection = new StringSelection(number);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
	}

	public static void pasteTheContentUsingJS(WebDriver driver, String copiedContent) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.activeElement.value = arguments[0];", copiedContent);
	}

	public static void pasteTheContent() {

		try {
			Robot robot = new Robot();
			robot.delay(200);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void tabKeyClick(WebDriver driver) {
		actions = new Actions(driver);
		actions.keyDown(Keys.TAB).build().perform();
		actions.keyUp(Keys.TAB).build().perform();
	}

	// To press ENTER key
	public static void pageEnterKeyClick(WebDriver driver) {
		actions = new Actions(driver);
		actions.keyDown(Keys.ENTER).build().perform();
		actions.keyUp(Keys.ENTER).build().perform();
	}

	public static void downKeyUsingActions(WebDriver driver) {
		actions = new Actions(driver);
		actions.keyDown(Keys.ARROW_DOWN).build().perform();
		actions.keyUp(Keys.ARROW_DOWN).build().perform();
	}

	public static void EnterKeyUsingRobot() {
		try {
			Robot robot = new Robot();
			robot.delay(300);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public static void jsScrollAndActionClick(WebDriver driver, By element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
				driver.findElement(element));
		actions = new Actions(driver);
		actions.moveToElement(driver.findElement(element)).pause(Duration.ofMillis(300)).click().build().perform();
	}

	public static void verify_AssoicatedCount(WebDriver driver, By by, String expectedCount) {
		final int maxRetries = 5;
		final double firstWaitSec = 0.5;
		final int waitBetweenRetriesSec = 15;
		int attempts = 0;
		boolean isMatched = false;
		while (attempts < maxRetries) {
			if (attempts == 0) {
				Non_WebDriver_Util.waitThread(firstWaitSec);
			} else {
				Non_WebDriver_Util.waitThread(waitBetweenRetriesSec);
				Non_WebDriver_Util.refreshPage(driver);
			}

			try {
				Non_WebDriver_Util.waitForNonEmptyText(driver, by, 5);
				String text = Non_WebDriver_Util.getInnerText(driver, by);
				System.out.println(":magnifying_glass: Extracted Assoicated text: " + text);
				Integer actualCount = Non_WebDriver_Util.extractNumberFromBrackets(text);
				if (actualCount.equals(Integer.parseInt(expectedCount))) {
					isMatched = true;
					break;
				}
			} catch (Exception e) {
				System.out.println(":warning: Exception while verifying Assoicated count: " + e.getMessage());
			}
			attempts++;
		}
		if (!isMatched) {
			System.out.println(
					":x: Associated Count mismatch after " + maxRetries + " attempts. Expected = " + expectedCount);
			Assert.fail(":x: Associated Count mismatch after " + maxRetries + " attempts. Expected = " + expectedCount);
		}
	}

	public static void waitForNonEmptyText(WebDriver driver, By by, int timeoutInSeconds) {
		new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).pollingEvery(Duration.ofMillis(1000))
				.withMessage(":x: Element text did not become non-empty within " + timeoutInSeconds + " seconds")
				.until(d -> {
					try {
						Non_WebDriver_Util.waitThread(2);
						String text = driver.findElement(by).getText();
						System.out.println(":magnifying_glass: Waiting for non-empty text. Current: '" + text + "'");
						return text != null && !text.trim().isEmpty();
					} catch (Exception e) {
						System.out.println(":warning: Exception while checking element text: " + e.getMessage());
						return false;
					}
				});
	}

	public static String getInnerText(WebDriver driver, By by) {
		try {
			return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText;",
					driver.findElement(by));
		} catch (Exception e) {
			System.out.println(":warning: Error fetching innerText: " + e.getMessage());
			return "";
		}
	}

	public static int extractNumberFromBrackets(String text) {
		String[] textWithBrac = text.split("Jobs");
		String textWithBrackets = textWithBrac[1].trim();
		if (textWithBrackets.startsWith("(") && textWithBrackets.endsWith(")")) {
			String numberPart = textWithBrackets.substring(1, textWithBrackets.length() - 1);
			try {
				return Integer.parseInt(numberPart);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Not a valid number inside brackets: " + textWithBrackets);
			}
		} else {
			throw new IllegalArgumentException("Text not in bracket format: " + textWithBrackets);
		}
	}

	public static String storeOriginalWindow(WebDriver driver) {
		return driver.getWindowHandle();
	}

}
