package elements;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.Constants;

public class ValidateElement {

	private static final Logger logger = Logger.getLogger(ValidateElement.class);
	private WebDriver driver;
	private List<IElementView> invisibleElements;

	private ValidateElement(WebDriver driver) {
		this.driver = driver;
		this.invisibleElements = new ArrayList<IElementView>();
	}

	public static ValidateElement with(WebDriver driver) {
		return new ValidateElement(driver);
	}

	public ValidateElement validateElementVisible(IElementView... elements) throws Exception {
		if (elements.length > 0) {
			for (IElementView elem : elements) {
				if (!isElementPresent(driver, elem.getSelector(), 2)) {
					this.invisibleElements.add(elem);
				}
			}
		}
		return this;
	}

	public ValidateElement validate(IElementView... elements) {
		try {
			
			if (elements.length > 0) {
				for (IElementView elem : elements) {
					if (!isElementPresentAndDisplayed(driver, elem.getSelector())) {

						this.invisibleElements.add(elem);
					} else {
						logger.info(elem.getElementDescription() + " is displayed");

					}
				}
			}
		} catch (NoSuchElementException e) {

		}
		return this;
	}

	public ValidateElement elementNotPresent(IElementView... elements) {
		if (elements.length > 0) {
			for (IElementView elem : elements) {
				if (isElementPresentAndDisplayed(driver, elem.getSelector())) {
					this.invisibleElements.add(elem);
				}
			}
		}
		return this;
	}

	public ValidateElement validateElementTextIsPresentJS(IElementView elements, String pStr) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Boolean result = Boolean.parseBoolean(jse.executeScript("return $(arguments[0]).text() === arguments[1];",
				driver.findElement(elements.getSelector()), pStr).toString());
		if (!result) {
			this.invisibleElements.add(elements);
		}
		return this;
	}

	public ValidateElement validateElementTextIsPresentJSAttr(IElementView elements, String pAttr, String pStr) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Boolean result = Boolean.parseBoolean(jse.executeScript("return $(arguments[0]).text() === arguments[1];",
				driver.findElement(elements.getSelector()).getAttribute(pAttr), pStr).toString());
		if (!result) {
			this.invisibleElements.add(elements);
		}
		return this;
	}

	public ValidateElement validateElementTextIsPresent(IElementView elements, String pStr) {
		if (!driver.findElement(elements.getSelector()).getText().trim().equalsIgnoreCase(pStr)) {
			this.invisibleElements.add(elements);
		}
		return this;
	}

	public ValidateElement validateElementContainsText(IElementView element, String pAttr, String pStr) {
		if (!driver.findElement(element.getSelector()).getAttribute(pAttr).contains(pStr)) {
			this.invisibleElements.add(element);
		}
		return this;
	}

	public ValidateElement validateElementContainsText(IElementView element, String pStr) {
		if (!driver.findElement(element.getSelector()).getText().contains(pStr)) {
			this.invisibleElements.add(element);
		}
		return this;
	}

	public ValidateElement validateElementTextIsNotPresent(IElementView elements, String pStr) {
		if (driver.findElement(elements.getSelector()).getText().equalsIgnoreCase(pStr)) {
			this.invisibleElements.add(elements);
		}
		return this;
	}

	public ValidateElement validateIsTextPresent(IElementView element) {
		if (!driver.getPageSource().contains(driver.findElement(element.getSelector()).getText().trim())) {
			this.invisibleElements.add(element);
		}
		return this;
	}

	public String getErrorMessages() {
		if (invisibleElements.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("Error validating element \n");
			for (IElementView elem : invisibleElements) {
				sb.append("Element not found:");
				sb.append(elem.getElementDescription());
				sb.append("\n");
			}
			return sb.toString();
		} else {
			return null;
		}
	}

	public boolean hasErrors() {

		boolean valid = this.invisibleElements.size() > 0;
		if (!valid) {
			logger.info("...Right ejecution test...");
		}
		return valid;
	}

	public static boolean isElementPresentAndDisplayed(WebDriver driver, final By byLocator) {
		try {
			return driver.findElement(byLocator).isDisplayed();
		} catch (NoSuchElementException ex) {
			return false;
		}

	}

	public static boolean isElementPresent(WebDriver driver, final By byLocator, int seconds) throws Exception {
		try {
			(new WebDriverWait(driver, seconds)).until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver d) {
					return d.findElement(byLocator);
				}
			});
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean checkForElement(final By locator) throws Exception {
		return isElementPresent(driver, locator, 5);
	}

	public void clear() {
		this.invisibleElements.clear();
	}

}
