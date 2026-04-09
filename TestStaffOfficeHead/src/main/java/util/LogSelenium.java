package util;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.Reporter;


public class LogSelenium implements WebDriverListener {

    private By lastFindBy;
	private static Logger logger = Logger.getLogger(LogSelenium.class);

    @Override
    public void beforeTo(WebDriver.Navigation navigation, String url) {
        Reporter.log("<br>&nbsp;&nbsp;&nbsp;- Go to:'"+url+"'");
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        String value = element.getAttribute("value");
        if (value != null && !value.isEmpty()) {
            Reporter.log("<br>&nbsp;&nbsp;&nbsp;- Change element's value ("+lastFindBy+") to '"+ value +"'");
        }
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        lastFindBy = locator;
    }

    @Override
    public void beforeFindElements(WebDriver driver, By locator) {
        lastFindBy = locator;
    }

    @Override
    public void beforeClick(WebElement element) {
    }

    @Override
    public void afterClick(WebElement element) {
        Reporter.log("<br>&nbsp;&nbsp;&nbsp;- CLICK in ("+lastFindBy+")");
    }

    @Override
    public void beforeExecuteScript(WebDriver driver, String script, Object[] args) {
        Reporter.log("<br>&nbsp;&nbsp;&nbsp;- RUN javascript:" + script);
    }

    @Override
    public void afterExecuteScript(WebDriver driver, String script, Object[] args) {
        Reporter.log("<br>&nbsp;&nbsp;&nbsp;- COMPLETED execution");
    }

    @Override
    public void onError(Object target, java.lang.reflect.Method method, Object[] args, java.lang.reflect.InvocationTargetException e) {
        Throwable cause = e.getCause();
        if (cause != null && cause.getClass().equals(NoSuchElementException.class)) {
            Reporter.log("<br>&nbsp;&nbsp;&nbsp;- SELENIUM ERROR: Element no found ("+lastFindBy+")");
            logger.info("Element no found ("+lastFindBy+")");
        } else {
            Reporter.log("<br>&nbsp;&nbsp;&nbsp;- SELENIUM ERROR: " + (cause != null ? cause.toString() : e.toString()));
        }
    }

}
