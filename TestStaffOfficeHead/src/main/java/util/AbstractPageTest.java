package util;

import org.apache.commons.lang3.tuple.Pair;

import elements.ValidateElement;



public abstract class AbstractPageTest {

	protected NUtilities screenUtil;
	protected ValidateElement validator;

	public AbstractPageTest(WebAccess accesoWeb) {
	    this.screenUtil = NUtilities.with(accesoWeb);
		this.validator = ValidateElement.with(accesoWeb.getDriver());
	}

	public Pair<Boolean, String> validateElements() {

		boolean notHasError = true;
		String errorMsg = "";
		if (validator.hasErrors()) {
			notHasError = false;
			errorMsg = validator.getErrorMessages();
		}
		validator.clear();
		Pair<Boolean, String> ret = Pair.of(notHasError, errorMsg);
		return ret;
	}

}
