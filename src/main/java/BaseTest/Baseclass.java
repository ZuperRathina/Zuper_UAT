package BaseTest;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    
    public Baseclass() {
        try {
            imagePath= System.getProperty("user.dir") + "/Resources/Screenshot_20250531_172511.jpg";
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
            opts.addArguments("--disable-notifications");           
            Map<String, Object> prefs = new HashMap<>();
            Map<String, Object> profile = new HashMap<>();
            Map<String, Object> contentSettings = new HashMap<>();
            contentSettings.put("media_stream_mic", 1); 
            contentSettings.put("media_stream_camera", 1);
            profile.put("managed_default_content_settings", contentSettings);
            prefs.put("profile", profile);
            opts.setExperimentalOption("prefs", prefs);
            // auto-approve mic/camera dialog
            opts.addArguments("--use-fake-ui-for-media-stream");
            opts.addArguments("force-device-scale-factor=0.97");
            
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
        localDriver.get(prop.getProperty("baseURL"));

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