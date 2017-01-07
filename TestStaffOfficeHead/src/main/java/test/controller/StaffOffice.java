package test.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import test.pageobject.StaffOfficePO;
import util.AbstractPageTest;
import util.TestCaseNumberUtils;
import util.WebAccess;

public class StaffOffice extends AbstractPageTest {
	private static Logger logger = Logger.getLogger(StaffOffice.class);

	public StaffOffice(WebAccess accesoWeb) {
		super(accesoWeb);
		// TODO Auto-generated constructor stub
	}

	public void checkUI() {
		// office
		screenUtil.waitForElementBySelector(StaffOfficePO.inputNameOffice);
		validator.validate(StaffOfficePO.inputNameOffice).validate(StaffOfficePO.btnAddOffice)
				.validate(StaffOfficePO.btnDeleteOffice).validate(StaffOfficePO.rowHeaderNameOffice)
				.validate(StaffOfficePO.rowHeaderChkOffice);
		// staff
		validator.validate(StaffOfficePO.inputNameStaff).validate(StaffOfficePO.selectDateStaff)
				.validate(StaffOfficePO.selectOffices).validate(StaffOfficePO.btnAddStaff)
				.validate(StaffOfficePO.rowHeaderStaffMemberName).validate(StaffOfficePO.rowHeaderOfficeName)
				.validate(StaffOfficePO.rowHeaderDateOfHire).validate(StaffOfficePO.rowHeaderChkStaff)
				.validate(StaffOfficePO.btnDeleteStaff);
	}

	public int countOffices() {
		//screenUtil.shortWait();
		screenUtil.waitForElementBySelector(StaffOfficePO.chkOffice);
		List<WebElement> offices = screenUtil.getListOfElements(StaffOfficePO.chkOffice);
		logger.info("There are :" + offices.size() + " offices");
		return offices.size();
	}

	public void insertData(String nameOff) {
		screenUtil.clickElement(StaffOfficePO.inputNameOffice);
		screenUtil.sendKeys(StaffOfficePO.inputNameOffice, nameOff);
		screenUtil.getElement(StaffOfficePO.btnAddOffice).click();
		screenUtil.waitForElementBySelector(StaffOfficePO.rowOffices);
//		List<WebElement> offices = screenUtil.getListOfElements(StaffOfficePO.rowOffices);
		// verify that the last office is the office I inserted.
//		Assert.assertEquals(offices.get(offices.size() - 1).getText(), nameOff);
	}

	public void checkMsgEmptyOffice() {
		screenUtil.elementIsDisplayed(StaffOfficePO.popupOffice);
		try{
		Assert.assertEquals(screenUtil.getText(StaffOfficePO.msgOffice), "Please fill out the name of the office.");
		}
		catch (Exception e) {
			Reporter.log(e.getMessage());
		}
	}

	public void checkMsgOfficeUnique() {
		screenUtil.elementIsDisplayed(StaffOfficePO.popupOffice);
		Assert.assertEquals(screenUtil.getText(StaffOfficePO.msgOffice), "Office names must be unique.");
	}

	public String getOffice() throws JSONException {
		String str = "{ \"name\":[ \"Marketing\" ,\"Management\", \"Development\" , \"Testing\"] }";

		JSONObject obj = new JSONObject(str);
		JSONArray arr = obj.getJSONArray("name");
		String office = arr.getString(TestCaseNumberUtils.getRandomNumberFrom(0, arr.length() - 1));
		return office;
	}

	public void deleteOffice(String confirm) {
		validator.validate(StaffOfficePO.chkOffice);
		List<WebElement> office = screenUtil.getListOfElements(StaffOfficePO.chkOffice);
		if (office.size() > 0)
			office.get(TestCaseNumberUtils.getRandomNumberFrom(0, office.size() - 1)).click();
		else
			office.get(0).click();
		screenUtil.clickElement(StaffOfficePO.btnDeleteOffice);
		if (confirm.equals("yes"))
			screenUtil.clickElement(StaffOfficePO.btnYesDelete);
		else
			screenUtil.clickElement(StaffOfficePO.btnNoDelete);

	}

	public void deleteElementNoSelect() {
		screenUtil.clickElement(StaffOfficePO.btnDeleteOffice);
		Assert.assertEquals(screenUtil.getText(StaffOfficePO.msgOffice),
				"Please select at least one office to remove.");
	}

	public void deleteOffWithoutAvailableOff() {
		int office = countOffices();
		for (int i = 0; i < office; i++) {
			deleteOffice("yes");
		}
		screenUtil.shortWait();
		screenUtil.clickElement(StaffOfficePO.btnDeleteStaff);
		Assert.assertEquals(screenUtil.getText(StaffOfficePO.msgOffice), "There are not offices.");
	}

}
