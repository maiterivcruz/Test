package util;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import elements.IElementView;


public class NUtilities {

	private final WebDriver driver;
	private static Logger logger = Logger.getLogger(NUtilities.class);
	
	private NUtilities(WebAccess accesoWeb) {
		this.driver = accesoWeb.getDriver();
	}

	public static NUtilities with(WebAccess accesoWeb) {
		return new NUtilities(accesoWeb);
	}

	public void switchToNewWindow() {
		String aux = "";
		Set<String> windows = this.getWindowHandles();
		for (String window : windows) {
			aux = window;
		}
		this.switchToWindow(aux);
	}

	public boolean checkInteger(String pNumero){
		try {
			Integer.parseInt(pNumero);
			return true;
		}catch(NumberFormatException ex){
			return false;
		}
	}
	
	
	public void waitForPopUp(IElementView element) {
		int i = 0;
		while (!popUpIsVisible(element)){
			mediumWait();
			if(i==30){
				break;
			}
			i++;
		}
	}
	
	
	

	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	public void switchToIFrame(IElementView element) {
		driver.switchTo().frame(driver.findElement(element.getSelector()));
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
		veryShortWait();
	}

	public boolean elementIsDisplayed(IElementView element) {
		try{
			return driver.findElement(element.getSelector()).isDisplayed();
		}catch (NoSuchElementException ex){
			return false;
		}
		
	}

	public boolean popUpIsVisible(IElementView element) {
		try {
			return "visible".equalsIgnoreCase(driver.findElement(element.getSelector()).getCssValue("visibility"));
		}catch (NoSuchElementException ex) {
			return false;
			
		}
	}
	
	/**
	 * Refresh screen
	 */
	public void refresh() {
		driver.navigate().refresh();
	}
	
	public boolean elementIsVisible(IElementView element) {
		try {
			return !"none".equalsIgnoreCase(driver.findElement(element.getSelector()).getCssValue("display"));
		}catch (NoSuchElementException ex){
			return false;
		}
		
	}

	public void veryShortWait() {
		try {
			Thread.sleep(Constants.TimeOut1Segundos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/**
	 * way 1 second / 1000 milliseconds
	 */
	public void shortWait() {
		try {
			Thread.sleep(Constants.TimeOut5Segundos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * way 2 second / 2000 milliseconds.
	 */
	public void mediumWait() {
		try {
			Thread.sleep(Constants.TimeOut3Segundos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * way 5 Segundos / 5000 Milisegundos
	 */
	public void longWait() {
		try {
			Thread.sleep(Constants.TimeOut5Segundos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void veryLongWait() {
		try {
			Thread.sleep(Constants.TimeOut10Segundos);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * return element's text
	 * 
	 * @param element
	 * @return
	 */
	public String getText(final IElementView element) {
		WebElement el = driver.findElement(element.getSelector());
		return el.getText().trim();
	}

	public String getText(final By selector) {
		WebElement el = driver.findElement(selector);
		return el.getText().trim();
	}

	public NUtilities navigate(String url) {
		logger.info("Go to page: " + url);
		this.driver.navigate().to(url);
		return this;
	}

	/**
	 * return random element from a list.
	 * 
	 * @param table
	 * @return
	 */
	public int getRandomRowID(final IElementView table) {
		List<WebElement> lista = driver.findElements(table.getSelector());
		return TestCaseNumberUtils.getRandomNumberFrom(1, lista.size());
	}

	/**
	 * select random element from dropdown
	 * 
	 * @param list
	 * @return
	 */
	public int getRandomElementDropDown(final IElementView list) {
		List<WebElement> lista = driver.findElements(list.getSelector());
		return TestCaseNumberUtils.getRandomNumberFrom(2, lista.size());
	}

	public void clickElement(final IElementView element) {
		clickElement(element.getSelector());
		logger.info("Clink element: " + element.getElementDescription());
	}

	public void clickElement(final By selector) {
		shortWait();
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(90000, TimeUnit.MILLISECONDS).pollingEvery(5500, TimeUnit.MILLISECONDS);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					logger.info("Clink element: " + selector);
					driver.findElement(selector).click();
					return true;
				} catch (StaleElementReferenceException e) {
					return false;
				}
			}
		});
	}

	public void mouseOver(final IElementView menu, final IElementView option) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement we = driver.findElement(menu.getSelector());
		mediumWait();
		executor.executeScript("arguments[0].click();", we);
		mediumWait();
		WebElement opt = driver.findElement(option.getSelector());
		opt.click();
		shortWait();
	}

	public void mouseOver(final IElementView menu) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement we = driver.findElement(menu.getSelector());
		shortWait();
		executor.executeScript("arguments[0].click();", we);
		mediumWait();
	}

	public void switchToWindow(String window) {
		driver.switchTo().window(window);
		shortWait();
	}

	public void clearInput(final IElementView element) {
		driver.findElement(element.getSelector()).clear();
		veryShortWait();
	}

	public void sendKeys(final IElementView element, final String pStr) {
		driver.findElement(element.getSelector()).sendKeys(pStr);
		logger.info("Send value :"+pStr);
		shortWait();
	}

	public String getElementAttribute(final By selector, final String pAtr) {
		return driver.findElement(selector).getAttribute(pAtr);
	}

	public String getElementAttribute(final IElementView element, final String pAttr) {
		return driver.findElement(element.getSelector()).getAttribute(pAttr).trim();
	}

	/**
	 * return WebElement list
	 * 
	 * @param element
	 * @return
	 */
	public List<WebElement> getListOfElements(final IElementView element) {
		return driver.findElements(element.getSelector());
	}

	public void selectElementFromList(final IElementView element, String pElementoDeseado) {
		List<WebElement> aux = getListOfElements(element);
		for (int i = 1; i < aux.size(); i++) {
			WebElement el = aux.get(i);
			scrollToElement(el);
			if (el.getText().contains(pElementoDeseado)) {
				el.click();
				break;
			}
		}
	}



	/**
	 * scroll until element is visible.
	 * 
	 * @param IElementView
	 *            element
	 */
	public void scrollToElement(final IElementView element) {
		WebElement el = driver.findElement(element.getSelector());
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("$(arguments[0]).scrollIntoView(true);", el);
		shortWait();
	}

	public void scrollToElementTable(final IElementView element, final String cssTableSelector) {
	  	WebElement el = driver.findElement(element.getSelector());
		By container = By.cssSelector(MessageFormat.format("{0} .ui-datatable-scrollable-body", cssTableSelector));
		WebElement cont = driver.findElement(container);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollLeft = arguments[0].scrollLeft + arguments[1];", cont, el.getLocation().x);
		shortWait();
	}

	/**
	 *scroll until element is visible.
	 * 
	 * @param By selector
	 */
	public void scrollToElement(final By selector) {
		WebElement el = driver.findElement(selector);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", el);
		shortWait();
	}

	/**
	 * scroll until element is visible.
	 * 
	 * @param WebElement
	 *            element
	 */
	public void scrollToElement(final WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", element);
		shortWait();
	}

	/**
	 * go to the last page and come back first page.
	 * 
	 * @param btnUltimaPagina
	 * @param paginacion
	 * @param btnPrimerPagina
	 * @return
	 * @throws Exception
	 */
	public int lastPage(final IElementView btnLastPage, final IElementView gagination, final IElementView btnFirstPage) throws Exception {
		int pages = 0;
		clickElement(btnLastPage);
		shortWait();
		if (getListOfElements(gagination).size() != 0) {
			List<WebElement> aux = getListOfElements(gagination);
			pages = Integer.parseInt(aux.get(aux.size() - 1).getText());
			clickElement(btnFirstPage);
		}
		shortWait();
		return pages;
	}

	public void dobleClick(By selector) {
		Actions builder = new Actions(driver);
		Action doubleClick = builder.doubleClick(driver.findElement(selector)).build();
		shortWait();
		doubleClick.perform();
	}

	/**
	 * validate alert and click button accept
	 * 
	 * @return
	 */
	public void handleAlert() {
		try {
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException ex) {

		}
	}

	public boolean isTextPresent(String str) {
		return driver.getPageSource().contains(str);
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/**
	 * convert from database date to date in format DD/MM/YYYY
	 * 
	 * @return
	 */
	public String dateFormat(String date) {
		String formattedDate;
		String newDate[] = date.split("");
		if (date.equalsIgnoreCase("0")) {
			formattedDate = "00/00/0000";
		} else {
			formattedDate = newDate[7] + newDate[8] + "/" + newDate[5] + newDate[6] + "/" + newDate[1] + newDate[2] + newDate[3] + newDate[4];
		}
		return formattedDate;
	}

	/**
	 * Format database hour in format HH:MM:SS
	 * 
	 * @param hora
	 * @return
	 * @throws Exception
	 */
	public String formatHour(String hora) throws Exception {
		String formattedHour = "";
		char[] aux = hora.toCharArray();
		if (hora.length() == 6) {
			formattedHour = Character.toString(aux[0]) + Character.toString(aux[1]) + ":" + Character.toString(aux[2]) + Character.toString(aux[3]) + ":" + Character.toString(aux[4]) + Character.toString(aux[5]);
		}
		if (hora.length() == 5) {
			formattedHour = "0" + Character.toString(aux[0]) + ":" + Character.toString(aux[1]) + Character.toString(aux[2]) + ":" + Character.toString(aux[3]) + Character.toString(aux[4]);
		}
		if (hora.equalsIgnoreCase("0")) {
			formattedHour = "00:00:00";
		}
		return formattedHour;
	}

	/**
	 * convert from database hour to hour in format HH:MM:SS
	 * 
	 * @param hour
	 * @return
	 * @throws Exception
	 */
	public String convertHour(String hour) throws Exception {
		String formattedHour = null;
		if (hour.length() == 1) {
			formattedHour = "00:0" + hour + ":00";
		}
		if (hour.length() == 2) {
			formattedHour = "00:" + hour + ":00";
		}
		if (hour.length() == 3) {
			String aux[] = hour.split("");
			formattedHour = "0" + aux[1] + ":" + aux[2] + aux[3] + ":00";
		}
		if (hour.length() == 4) {
			String aux[] = hour.split("");
			formattedHour = aux[1] + aux[2] + ":" + aux[3] + aux[4] + ":00";
		}
		if (hour.length() == 6) {
			String aux[] = hour.split("");
			formattedHour = aux[1] + aux[2] + ":" + aux[3] + aux[4] + ":" + aux[5] + aux[6];
		}
		if (hour.equalsIgnoreCase("2400")) {
			formattedHour = "00:00:00";
		}
		return formattedHour;
	}

	public WebElement getElement(IElementView element) {
		WebElement el = driver.findElement(element.getSelector());
		return el;
	}

	public WebElement getElement(By selector) {
		WebElement el = driver.findElement(selector);
		return el;
	}

	@Deprecated
	public void waitForElementById(String elementId) {           
        WebDriverWait wait = new WebDriverWait(driver, 120);
        WebElement myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementId)));
     }
	@Deprecated
    public void waitForElementByXpath(String elementXpath) {        
        WebDriverWait wait = new WebDriverWait(driver, 120);
        WebElement myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementXpath)));
     }
	@Deprecated
    public void waitForElementByCss(String elementCss) {        
        WebDriverWait wait = new WebDriverWait(driver, 120);
        WebElement myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(elementCss)));
     }
    public void waitForElementBySelector(IElementView selector) {  
//        logger.info("Waiting for Element: " + selector.getElementDescription());
        WebDriverWait wait = new WebDriverWait(driver, 200);
        WebElement myDynamicElement = wait.until(ExpectedConditions.visibilityOfElementLocated(selector.getSelector()));
     }

	
}
