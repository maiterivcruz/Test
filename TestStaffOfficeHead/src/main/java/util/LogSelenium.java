package util;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.Reporter;


public class LogSelenium implements WebDriverEventListener {

    private By lastFindBy;
	private static Logger logger = Logger.getLogger(LogSelenium.class);

    @Override
    public void beforeNavigateTo(String url, WebDriver selenium){
        Reporter.log("<br>&nbsp;&nbsp;&nbsp;- Go to:'"+url+"'");
    }

    public void beforeChangeValueOf(WebElement element, WebDriver selenium){
    }

    public void afterChangeValueOf(WebElement element, WebDriver selenium){
        if(!element.getAttribute("value").isEmpty()){
            Reporter.log("<br>&nbsp;&nbsp;&nbsp;- Change element's value ("+lastFindBy+") to '"+ element.getAttribute("value") +"'");
        }
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver selenium){
        lastFindBy = by;
    }

    @Override
    public void onException(Throwable error, WebDriver selenium){
        if (error.getClass().equals(NoSuchElementException.class)){
            Reporter.log("<br>&nbsp;&nbsp;&nbsp;- SELENIUM ERROR: Element no found ("+lastFindBy+")");
            logger.info("Element no found ("+lastFindBy+")");
        } else {
            Reporter.log("<br>&nbsp;&nbsp;&nbsp;- SELENIUM ERROR: " + error.toString());
        }
    }

    @Override
    public void beforeNavigateBack(WebDriver selenium){}
    
    @Override
    public void beforeNavigateForward(WebDriver selenium){}
    
    @Override
    public void beforeClickOn(WebElement element, WebDriver selenium){
//    	 ScreenShot(selenium);
    }
    
    @Override
    public void beforeScript(String script, WebDriver selenium){
        Reporter.log("<br>&nbsp;&nbsp;&nbsp;- RUN javascript:" + script);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver selenium){
        Reporter.log("<br>&nbsp;&nbsp;&nbsp;- CLICK in ("+lastFindBy+")");
    }
    
    @Override
    public void afterFindBy(By by, WebElement element, WebDriver selenium){}
    
    @Override
    public void afterNavigateBack(WebDriver selenium){}
    
    @Override
    public void afterNavigateForward(WebDriver selenium){}
    
    @Override
    public void afterNavigateTo(String url, WebDriver selenium){}
    
    @Override
    public void afterScript(String script, WebDriver selenium){
        Reporter.log("<br>&nbsp;&nbsp;&nbsp;- COMPLETED execution");
    }

	@Override
	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}
    
//    public void ScreenShot(WebDriver selenium ){
//    	 String fechaYhora =  FechaHandler.getInstance().getFechaHoraMils();
//         String fileName = this.getClass().getName() + fechaYhora + ".png";
//         String destDir = System.getProperty("user.dir") + "\\test-output\\Screenshot\\"+ fileName;
//         File scrFile = ((TakesScreenshot)selenium).getScreenshotAs(OutputType.FILE);
//			try {
//				FileUtils.copyFile(scrFile, new File(destDir));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        
//         String url = "file:///" + System.getProperty("user.dir") + "\\test-output\\Screenshot\\" + fileName;
//         Reporter.log("<p>Captura de pantalla final:<br>");
//         
//         Reporter.log("&nbsp;&nbsp;&nbsp;<a href=\"" + url + "\">" + fileName + " Screenshot</a>");                
//         Reporter.log("<br><img src='Screenshot\\"+fileName+"' width='800'/>");
//    }


}
