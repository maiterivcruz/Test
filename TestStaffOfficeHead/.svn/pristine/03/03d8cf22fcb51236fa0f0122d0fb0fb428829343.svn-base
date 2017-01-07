package elements;

import org.openqa.selenium.By;

public enum ESelectorType {
	
	CSS_SELECTOR,
	XPATH_SELECTOR,
	LINK_TEXT,
	ID;

	public By getSelector(String selectorExp){
		By selector;
		switch (this) {
			case CSS_SELECTOR:
				selector = By.cssSelector(selectorExp);
				break;
			case XPATH_SELECTOR:
				selector = By.xpath(selectorExp);
				break;
			case LINK_TEXT:
				selector = By.linkText(selectorExp);
				break;
			case ID:
				selector = By.id(selectorExp);
				break;
			default:
				selector = By.cssSelector(selectorExp);
				break;
		}
		return selector;
	}
	
	
	
}
