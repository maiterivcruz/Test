package test.pageobject;

import org.openqa.selenium.By;

import elements.ESelectorType;
import elements.IElementView;

public enum StaffOfficePO implements IElementView{
	/*office elements*/
	inputNameOffice("#nameOff"),
    btnAddOffice("#submitOff"),
    btnDeleteOffice("#deleteOff"),
    rowHeaderNameOffice("body > div:nth-child(1) > div:nth-child(3) > table > thead > tr > th:nth-child(1)"),
    rowHeaderChkOffice("body > div:nth-child(1) > div:nth-child(3) > table > thead > tr > th:nth-child(2)"),
    rowOffices("#officesList > tr"),
    popupOffice("#dataAlertModal"),
    msgOffice("#dataAlertModal > div.modal-content > div.modal-body"),
    chkOffice("#officesList > tr > td:nth-child(2)>input"),
    btnYesDelete("#dataConfirmOK"),
    btnNoDelete("#dataConfirmModalOff > div.modal-content > div.modal-footer > button"),
    
    btnEmptyOffice("#dataConfirmOK"),

    /*staff elements*/
    inputNameStaff("#name"),
    selectDateStaff("#date"),
    selectOffices("#offices"),
    btnAddStaff("#submit"),
    rowHeaderStaffMemberName("body > div:nth-child(2) > div:nth-child(3) > table > thead > tr > th:nth-child(1)"),
    rowHeaderOfficeName("body > div:nth-child(2) > div:nth-child(3) > table > thead > tr > th:nth-child(2)"),
    rowHeaderDateOfHire("body > div:nth-child(2) > div:nth-child(3) > table > thead > tr > th:nth-child(3)"),
    rowHeaderChkStaff("body > div:nth-child(2) > div:nth-child(3) > table > thead > tr > th:nth-child(1)"),
    btnDeleteStaff("#deleteStaff")
    //#dataAlertModal > div.modal-content > div.modal-body
    ;

	
    private final By selector;
	private final String stringSelector;
	
	private StaffOfficePO(String selector, ESelectorType... selectorType){
		if(selectorType.length>0){
			this.selector = selectorType[0].getSelector(selector);
		}else{
			this.selector = ESelectorType.CSS_SELECTOR.getSelector(selector);
		}		
		this.stringSelector = selector; 
	}

	public By getSelector() {
		// TODO Auto-generated method stub
		return selector;
	}

	public String getElementDescription() {
		// TODO Auto-generated method stub
		return stringSelector;
	}
	
}