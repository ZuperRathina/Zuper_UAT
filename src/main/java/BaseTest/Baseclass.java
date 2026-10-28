package BaseTest;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Baseclass {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public Properties prop;
	public String imagePath;
	protected final Logger logger = LogManager.getLogger(getClass());

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void setDriver(WebDriver driverInstance) {
		driver.set(driverInstance);
	}
	
	public WebDriver incognitoDriver;

	
	public static String usingBrowser;

	public void initilizeConfigNew(String modeType, String url) {

		logger.info("Test setup started...");
		String browser = prop.getProperty("browser");
		WebDriver localDriver = null;

		if (browser.equalsIgnoreCase("chrome")) {

			ChromeOptions options = new ChromeOptions();
			if (modeType.equalsIgnoreCase("normal")) {

				logger.info("Launching Chrome in Normal Mode");

			} else if (modeType.equalsIgnoreCase("incognito")) {

				logger.info("Launching Chrome in Incognito Mode");

				options.addArguments("--incognito");

			} else {

				throw new IllegalArgumentException("Invalid modeType: " + modeType + ". Use 'normal' or 'incognito'.");
			}
			// Disable browser notifications
			options.addArguments("--disable-notifications");

			// Start Chrome maximized
			options.addArguments("--start-maximized");

			// Start window at top-left
			options.addArguments("--window-position=0,0");

			// Fix Chrome rendering/scaling on Mac
			options.addArguments("--disable-gpu");
			options.addArguments("--disable-software-rasterizer");
			options.addArguments("--force-device-scale-factor=1");
			options.addArguments("--autoplay-policy=no-user-gesture-required");

			Map<String, Object> prefs = new HashMap<>();
			Map<String, Object> profile = new HashMap<>();
			Map<String, Object> contentSettings = new HashMap<>();

			contentSettings.put("media_stream_mic", 1);
			contentSettings.put("media_stream_camera", 1);

			profile.put("managed_default_content_settings", contentSettings);
			prefs.put("profile", profile);

			options.setExperimentalOption("prefs", prefs);

			// Automatically allow microphone/camera permission
			options.addArguments("--use-fake-ui-for-media-stream");

			localDriver = new ChromeDriver(options);

			// Store Incognito driver separately if required
			if (modeType.equalsIgnoreCase("incognito")) {
				incognitoDriver = localDriver;
			} else {
				setDriver(localDriver);
			}

		} else if (browser.equalsIgnoreCase("edge")) {

			logger.info("Launching Edge");

			localDriver = new EdgeDriver();

		} else if (browser.equalsIgnoreCase("safari")) {

			logger.info("Launching Safari");

			localDriver = new SafariDriver();

		} else {

			throw new IllegalArgumentException("Invalid browser type in config file: " + browser);
		}

		// --------------------------------------------------
		// Common Driver Configuration
		// --------------------------------------------------

		localDriver.manage().window().maximize();

		localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// --------------------------------------------------
		// Navigate to Application
		// --------------------------------------------------

		if (url != null) {
			localDriver.navigate().to(url);
		}else {
			localDriver.navigate().to(prop.getProperty("liveNext"));
		}
		

		// --------------------------------------------------
		// Apply 90% Zoom only for Incognito
		// --------------------------------------------------
		
		 // Default to normal mode

		if (modeType.equalsIgnoreCase("incognito")) {

			JavascriptExecutor js = (JavascriptExecutor) localDriver;

			js.executeScript("document.body.style.zoom='90%';");
			
			usingBrowser = "incognito";
			
		}

		// --------------------------------------------------
		// Store Driver in ThreadLocal (normal mode only - incognito must
		// never overwrite the shared driver that getDriver() returns)
		// --------------------------------------------------

		if (modeType.equalsIgnoreCase("normal")) {
			setDriver(localDriver);
			usingBrowser = "normal";
		}
	}

	public Baseclass() {
		try {
			imagePath = System.getProperty("user.dir") + "/Resources/Screenshot_20250531_172511.jpg";
			String configPath = System.getProperty("user.dir") + "/config/configure.properties";
			FileInputStream file = new FileInputStream(configPath);
			prop = new Properties();
			prop.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Initialize browser based on config
	public void initilizeConfig() {
		logger.info("Test setup started...");
		String browser = prop.getProperty("browser");

		WebDriver localDriver = null;
		if (browser.equalsIgnoreCase("chrome")) {

			ChromeOptions opts = new ChromeOptions();

			// Chrome will run in headed mode
			// DO NOT add --headless or --headless=new

			// Disable Chrome notifications
			opts.addArguments("--disable-notifications");

			// Start Chrome maximized and visible
			opts.addArguments("--start-maximized");
			opts.addArguments("--window-position=0,0");

			// Fix Chrome rendering/scaling on Mac
			opts.addArguments("--disable-gpu");
			opts.addArguments("--disable-software-rasterizer");
			opts.addArguments("--force-device-scale-factor=1");

			Map<String, Object> prefs = new HashMap<>();

			Map<String, Object> profile = new HashMap<>();

			Map<String, Object> contentSettings = new HashMap<>();

			contentSettings.put("media_stream_mic", 1);
			contentSettings.put("media_stream_camera", 1);

			profile.put("managed_default_content_settings", contentSettings);

			prefs.put("profile", profile);

			opts.setExperimentalOption("prefs", prefs);

			// Auto-approve microphone/camera dialog
			opts.addArguments("--use-fake-ui-for-media-stream");

			localDriver = new ChromeDriver(opts);
		} else if (browser.equalsIgnoreCase("edge")) {
			localDriver = new EdgeDriver();
			localDriver.manage().window().maximize();

		} else if (browser.equalsIgnoreCase("safari")) {
			localDriver = new SafariDriver(); // Safari, not IE
		} else {
			throw new IllegalArgumentException("❌ Invalid browser type in config file: " + browser);
		}

		localDriver.manage().window().maximize();
		localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		localDriver.navigate().to(prop.getProperty("liveNext"));
//		if(url != null && !url.isEmpty()) {
//			localDriver.get(url);
//		}else {
//			localDriver.get(prop.getProperty("liveNext"));
//		}

		setDriver(localDriver); // Store in ThreadLocal
	}

	// Close the browser
	public void tearDown() {
		WebDriver localDriver = Baseclass.getDriver();
		if (localDriver != null) {
			localDriver.quit();
			Baseclass.driver.remove(); // Important to clean up ThreadLocal
		}
	}
}