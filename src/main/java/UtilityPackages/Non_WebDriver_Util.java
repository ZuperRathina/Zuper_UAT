package UtilityPackages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
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
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import PageObjectRep.DashboardPageFactory;
import UtilityPackages.Non_WebDriver_Util;

public class Non_WebDriver_Util {

	private static Actions actions;
	public static ExtentReports extent;
	public static ExtentTest testCase;
	public static String reportPath;

	public static void initReport() {
		if (extent == null) {
			reportPath = System.getProperty("user.dir") + "/ExtentReport.html";
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
		} else {
			System.out.println("Extent report already initialized.");
		}
	}

	public static void checkPUTResponse(String putUrl, String apiKey, String requestBody) {
		try {
			com.mashape.unirest.http.HttpResponse<JsonNode> jsonResponse = Unirest.put(putUrl)
					.header("Content-Type", "application/json").header("x-api-key", apiKey).body(requestBody).asJson();

			System.out.println(jsonResponse.getStatus());
			System.out.println(jsonResponse.getStatusText());
			System.out.println(jsonResponse.getBody());
			String responseBody = jsonResponse.getBody().toString();
			System.out.println("Response Body: " + responseBody);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static String getResponseRecentMessageObj;
	public static JSONObject dataObject;

	public static void checkGETResponse(String getUrl, String apiKey) {
		try {
			com.mashape.unirest.http.HttpResponse<JsonNode> jsonResponse = Unirest.get(getUrl)
					.header("Content-Type", "application/json").header("x-api-key", apiKey).asJson();

			System.out.println("Status: " + jsonResponse.getStatus());
			System.out.println("Status Text: " + jsonResponse.getStatusText());
			JsonNode responseBody = jsonResponse.getBody();
			JSONObject jsonObject = new JSONObject(responseBody.toString());

			if (jsonObject.has("data") && jsonObject.optJSONObject("data") != null) {
				dataObject = jsonObject.getJSONObject("data");
			} else {
				dataObject = jsonObject;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JSONObject dataObjectPOST;
	public static void checkPOSTResponse(String getUrl, String apiKey) {
		try {
			com.mashape.unirest.http.HttpResponse<JsonNode> jsonResponse = Unirest.post(getUrl).header("accept", "application/json")
					.header("Content-Type", "application/json").header("x-api-key", apiKey).body("{\"filters\":{}}").asJson();

			System.out.println("Status: " + jsonResponse.getStatus());
			System.out.println("Status Text: " + jsonResponse.getStatusText());

			JsonNode responseBody = jsonResponse.getBody();
			JSONObject jsonObject = new JSONObject(responseBody.toString());
			
			if (jsonObject.has("data") && jsonObject.optJSONObject("data") != null) {
				dataObjectPOST = jsonObject.getJSONObject("data");
			} else {
				dataObjectPOST = jsonObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printMediaDetails(String getUrl, String apiKey, WebDriver driver) {

		checkGETResponse(getUrl, apiKey);

		try {
			if (dataObject == null) {
				System.out.println("Response data is null");
				return;
			}

			JSONObject recentMessage = dataObject.optJSONObject("recent_message");

			if (recentMessage == null) {
				System.out.println("recent_message not found");
				return;
			}

			String direction = recentMessage.optString("direction", "");

			org.json.JSONArray mediaArray = recentMessage.optJSONArray("media");

			if (mediaArray == null) {
				System.out.println("Media array not found");
				return;
			}

			if (direction.equalsIgnoreCase("OUTGOING")) {
				if (DashboardPageFactory.driverValue.equalsIgnoreCase(driver.toString())) {
					Non_WebDriver_Util.testCase.log(Status.PASS,
							"Agent 1 sent attachment(s) to Agent 2. Count: " + mediaArray.length());
				} else {
					Non_WebDriver_Util.testCase.log(Status.PASS,
							"Agent 2 sent attachment(s) to Agent 1. Count: " + mediaArray.length());
				}
			} else {
				if (DashboardPageFactory.driverValue.equalsIgnoreCase(driver.toString())) {
					Non_WebDriver_Util.testCase.log(Status.PASS,
							"Agent 1 received attachment(s) from Agent 2. Count: " + mediaArray.length());
				} else {
					Non_WebDriver_Util.testCase.log(Status.PASS,
							"Agent 2 received attachment(s) from Agent 1. Count: " + mediaArray.length());
				}
			}

			for (int i = 0; i < mediaArray.length(); i++) {
				JSONObject media = mediaArray.getJSONObject(i);

				String url = media.optString("media_url", "");
				String type = media.optString("content_type", "");

				getResponseRecentMessageObj = "Attachment Type: " + type + "<br>Attachment URL: " + "<a href='" + url
						+ "' target='_blank'>" + url + "</a>";

				Non_WebDriver_Util.testCase.log(Status.PASS, getResponseRecentMessageObj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String inprogressCallUID;
	public static void getInProgressCallUID(String getUrl, String apiKey, String userNameToMatch) {
		checkPOSTResponse(getUrl, apiKey);
	    JSONArray callsArray = dataObjectPOST.getJSONArray("data");
	    boolean callFound = false;

	    for (int i = 0; i < callsArray.length(); i++) {
	        JSONObject call = callsArray.getJSONObject(i);

	        // Check status first
	        if (!"IN_PROGRESS".equalsIgnoreCase(call.optString("status"))) {
	            continue;
	        }

	        String direction = call.optString("direction");
	        JSONObject userObject = null;

	        // Get username based on call direction
	        if ("OUTGOING".equalsIgnoreCase(direction)) {
	            JSONObject fromObject = call.optJSONObject("from");
	            if (fromObject != null) {
	                userObject = fromObject.optJSONObject("user");
	            }
	        } else if ("INCOMING".equalsIgnoreCase(direction)) {
	            JSONObject toObject = call.optJSONObject("to");
	            if (toObject != null) {
	                userObject = toObject.optJSONObject("user");
	            }
	        }

	        String userName = (userObject != null)
	                ? userObject.optString("user_name", "")
	                : "";

	        // Match username
	        if (userName.equalsIgnoreCase(userNameToMatch)) {
//	            String fromNumber = call.optString("from_number");
//	            String toNumber = call.optString("to_number");
	        	inprogressCallUID = call.optString("call_uid");
	        	Non_WebDriver_Util.testCase.log(Status.PASS,
						"Inprogress Call UID is: " + inprogressCallUID);
	            System.out.println("Inprogress Call UID: " + inprogressCallUID);

	            callFound = true;
	            break;
	        }
	        dataObjectPOST=null;
	    }

	    if (!callFound) {
	        System.out.println("No matching IN_PROGRESS call found for user: " + userNameToMatch);
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

	// Counts files in the same folder uploadMultipleFiles() reads from, so
	// callers can derive an "expected attachment count" dynamically instead
	// of hardcoding a number that can drift out of sync with the folder.
	public static int countFilesInFolder(String filePath) {
		File folder = new File(filePath);
		File[] files = folder.listFiles(File::isFile);
		return files == null ? 0 : files.length;
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

//	public static void addingScreenShotToReport(ITestResult result, WebDriver driver) {
//	    try {
//
//	        if (result.getStatus() == ITestResult.FAILURE) {
//
//	            String failureReason = "No failure reason available";
//
//	            if (result.getThrowable() != null) {
//	                String message = result.getThrowable().getMessage();
//
//	                if (message != null && !message.trim().isEmpty()) {
//	                    failureReason = message;
//	                }
//	            }
//
//	            String requiredMessage = failureReason.split("\\(Session info:")[0].trim();
//
//	            if (Non_WebDriver_Util.testCase != null) {
//	                Non_WebDriver_Util.testCase
//	                        .fail(result.getName() + " Method is Failed and the Reason is: " + requiredMessage);
//
//	                if (driver != null) {
//	                    Non_WebDriver_Util.getScreenshot(driver, result.getName() + " Method Failed");
//	                }
//	            } else {
//	                System.out.println("ExtentTest testCase is null. Unable to add failure to report.");
//	            }
//
//	        } else if (result.getStatus() == ITestResult.SUCCESS) {
//
//	            if (Non_WebDriver_Util.testCase != null) {
//	                Non_WebDriver_Util.testCase.pass(result.getName() + " Method is Passed");
//
//	                if (driver != null) {
//	                    Non_WebDriver_Util.getScreenshot(driver, result.getName() + " Method Passed");
//	                }
//	            }
//
//	        } else if (result.getStatus() == ITestResult.SKIP) {
//
//	            if (Non_WebDriver_Util.testCase != null) {
//	                Non_WebDriver_Util.testCase.skip(result.getName() + " Method is Skipped");
//
//	                if (driver != null) {
//	                    Non_WebDriver_Util.getScreenshot(driver, result.getName() + " Method Skipped");
//	                }
//	            }
//	        }
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//	}
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

	public static void jsScrollToElement(WebDriver driver, By element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(element));
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

	// Scrolls the element into view then clicks it via JS - unlike
	// jsScrollAndActionClick (which uses Actions and needs a real visible
	// element), this works even on visually-hidden elements (e.g. native
	// inputs backing a custom-styled radio/checkbox/select).
	public static void jsScrollAndClick(WebDriver driver, By element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement target = driver.findElement(element);
			js.executeScript("arguments[0].scrollIntoView({block: 'center'});", target);
			js.executeScript("arguments[0].click();", target);
		} catch (Exception e) {
			System.out.println("❌ JS Scroll+Click failed: " + e.getMessage());
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

	/**
	 * Dumps the current page's HTML source to a file under
	 * <project-root>/PageDumps/<label>_<timestamp>.html, for offline inspection
	 * (e.g. finding real locators when a new page/module hasn't been mapped yet).
	 * Prints the resulting file path to the console.
	 */
	public static String dumpPageSource(WebDriver driver, String label) {
		try {
			String dumpDir = System.getProperty("user.dir") + "/PageDumps";
			File dir = new File(dumpDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String filePath = dumpDir + "/" + label + "_" + timestamp + ".html";
			try (FileWriter writer = new FileWriter(filePath)) {
				writer.write(driver.getPageSource());
			}
			System.out.println("Page source dumped to: " + filePath);
			return filePath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void playAudio(WebDriver driver, String fileName) {
		String audioPath = new File(System.getProperty("user.dir"), "Resources/AudioFiles/" + fileName + ".mp3")
				.getAbsolutePath();

		try {
			String escapedPath = audioPath.replace("\\", "\\\\").replace("\"", "\\\"");

			String script = "tell application \"Music\"\n" + "open POSIX file \"" + escapedPath + "\"\n" + "play\n"
					+ "end tell";

			Process process = new ProcessBuilder("osascript", "-e", script).start();

			int exitCode = process.waitFor();

			if (exitCode != 0) {
				System.out.println("Failed to play: " + fileName);
			}

		} catch (IOException | InterruptedException e) {
			if (e instanceof InterruptedException) {
				Thread.currentThread().interrupt();
			}
			e.printStackTrace();
		}
	}

	public static void playAudioInMusic(String fileName) {
		try {
			String audioPath = new File(System.getProperty("user.dir"), "/Resources/AudioFiles/" + fileName + ".mp3")
					.getAbsolutePath();

			String script = "tell application \"Music\"\n" + "activate\n" + "open POSIX file \""
					+ audioPath.replace("\\", "\\\\").replace("\"", "\\\"") + "\"\n" + "delay 2\n" + "play\n"
					+ "end tell";

			Process process = new ProcessBuilder("osascript", "-e", script).start();

			int exitCode = process.waitFor();

			if (exitCode != 0) {
				System.out.println("Failed to play audio in Music.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void openMusic() {
		try {
			Process process = new ProcessBuilder("osascript", "-e", "tell application \"Music\" to activate").start();

			process.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void minimizeMusic() {
		try {
			String script = "tell application \"System Events\"\n" + "if exists process \"Music\" then\n"
					+ "tell process \"Music\"\n" + "if exists window 1 then\n"
					+ "set value of attribute \"AXMinimized\" of window 1 to true\n" + "end if\n" + "end tell\n"
					+ "end if\n" + "end tell";

			Process process = new ProcessBuilder("osascript", "-e", script).start();

			int exitCode = process.waitFor();

			if (exitCode != 0) {
				System.out.println("Failed to minimize Music.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void quitMusic() {
		try {
			Process process = new ProcessBuilder("osascript", "-e", "tell application \"Music\" to quit").start();

			process.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
