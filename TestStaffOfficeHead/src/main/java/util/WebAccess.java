package util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import test.pageobject.StaffOfficePO;

@Listeners(OnFailureListener.class)
public class WebAccess {

	private RemoteWebDriver webDriver = null;
    protected WebDriver driver;
	private static Logger logger = Logger.getLogger(WebAccess.class);
    WebDriverEventListener eventListener = new LogSelenium();
	private static String DRIVER_PATH_PROPERTY_NAME = "webdriver.chrome.driver";
	private static String DRIVER_PATH = "driver/chromedriver.exe";
	private static String DRIVER_PATH_PROPERTY_NAME_IE = "webdriver.ie.driver";
	private static String DRIVER_PATH_IE = "driver/IEDriverServer.exe";
	private String testClass;
	private String testName;
	private String testMethod;
	private String testLinkName;
	private String hubURLStr;
	private URL hubUrl;


	@Parameters({ "testName", "testLinkName", "browser" ,"environment"})
	@BeforeMethod
	public void handleTestMethodName(Method method, String testName, String testLinkName,String browser ,String environment) throws Exception {

		this.testName = testName;
		this.testLinkName = testLinkName;
		this.testMethod = method.getName();
		this.testClass = method.getDeclaringClass().getSimpleName();
		this.hubURLStr = "";
		this.hubUrl = null;
		
		logger.info("Test: " + testLinkName + " , " + testName);
		if(environment.equals("local")){
			hubURLStr="http://localhost:4444/wd/hub";
			
		}
		else{
			//put here the server address
			hubURLStr="";
		}
		hubUrl =  new URL(hubURLStr);
		switch (browser) {
		case "chrome":
			setUpChrome();
			break;
		case "firefox":
			setUpFirefox();
			break;
		case "iexplorer":
			setUpIExplorer();
			break;
		default:
			break;
		}
		driver.get("http://localhost:8080/javascript/index.html");
	}

	public WebAccess() {
	}

	public WebDriver getDriver() {
		return driver;
	}

	
	public void setUpChrome() throws Exception {
		logger.info("Open chrome");
		System.setProperty(DRIVER_PATH_PROPERTY_NAME, DRIVER_PATH);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("nativeEvents", true);
		ChromeOptions chromeOptions = new ChromeOptions();

		chromeOptions.addArguments("--lang=es");
		chromeOptions.addArguments("--disable-extensions");
		chromeOptions.addArguments("start-maximized");
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        webDriver = new RemoteWebDriver(hubUrl, capabilities);
        driver = new EventFiringWebDriver(webDriver).register(eventListener);
	}
	public void setUpFirefox(){
		logger.info("Open firefox");
		//System.setProperty("webdriver.gecko.driver","C:\\workspaceTJM\\selenium\\geckodriver.exe");
		DesiredCapabilities capabilities = new DesiredCapabilities().firefox();		
		webDriver = new RemoteWebDriver(hubUrl, capabilities);
	    driver = new EventFiringWebDriver(webDriver).register(eventListener);
	}
	public void setUpIExplorer(){
		logger.info("Open Internet Explorer");
		System.setProperty(DRIVER_PATH_PROPERTY_NAME_IE, DRIVER_PATH_IE);
		DesiredCapabilities capabilities = new DesiredCapabilities().internetExplorer();
		webDriver = new RemoteWebDriver(hubUrl, capabilities);
		driver= new EventFiringWebDriver(webDriver).register(eventListener);
	}
	public void assertTest(AbstractPageTest test) {
		Pair<Boolean, String> validateRes = test.validateElements();
		Assert.assertTrue(validateRes.getLeft(), validateRes.getRight());
	}

	@AfterMethod(alwaysRun = true)
    @Parameters({"browser"})
    protected void commonAfterTestMethod( String browser,ITestResult itr) throws IOException {
        try{            
            if(!browser.equals("htmlunit")){ 
                Reporter.setCurrentTestResult(itr); 
                
                if (!itr.isSuccess()){
                    Reporter.log("<br>**Here failed the test**");
                    captureScreenShot(testLinkName);
                }
               
                if (itr.isSuccess()){
                    Reporter.log("<p>TEST PASSED");
                }else{
                    Reporter.log("<p>TEST FAILED");
                }
            }            
          
        }finally {
        	String newLine = System.getProperty("line.separator");
    		logger.info("End test." +newLine);
    		 driver.quit();
		}
        
       
    }
	public  void captureScreenShot(String obj) throws IOException {
	    File screenshotFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    String nameImg="Screenshots\\"+obj+"\\img"+GetTimeStampValue()+".jpg";
	    nameImg=nameImg.replaceAll("[' ':]", "");
	    System.out.println(nameImg);
	    FileUtils.copyFile(screenshotFile,new File("test-output/"+nameImg));
	    Reporter.log("<br><img src='"+nameImg+"' width='70%' height='70%'/>");

	}

	public  String GetTimeStampValue()throws IOException{
	    Calendar cal = Calendar.getInstance();       
	    Date time=cal.getTime();
	    String timestamp=time.toString();	   
	    return timestamp;
	}
  
	public String getTestClass() {
		return testClass;
	}

	public void setTestClass(String testClass) {
		this.testClass = testClass;
	}

	public String getTestMethod() {
		return testMethod;
	}

	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}

}
