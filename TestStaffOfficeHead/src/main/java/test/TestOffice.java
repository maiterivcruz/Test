package test;
import org.json.JSONException;
import org.junit.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import test.controller.StaffOffice;
import util.OnFailureListener;
import util.WebAccess;

public class TestOffice extends WebAccess{
	private StaffOffice staffOff;
	
	@Parameters({"testName", "testLinkName" })
	@Test
	public void validateUI(String testName, String testLinkName){
		/*1-Check UI*/
		staffOff=new StaffOffice(this);
		staffOff.checkUI();
	}
	
	@Parameters({"testName", "testLinkName" })
	@Test
	public void validateAddOfficeRightData(){
		String nameOff="test";
		staffOff=new StaffOffice(this);
		int countOffice=staffOff.countOffices();
		/*Insert data*/
		staffOff.insertData(nameOff);
		/*Check insert*/
		Assert.assertEquals(countOffice+1, staffOff.countOffices());
	}
	@Parameters({"testName","testLinkName"})
	@Test
	public void validateAddOfficeEmptyData(){
		staffOff=new StaffOffice(this);
		/*Insert data*/
		staffOff.insertData("");
		/*check popup and message*/
		staffOff.checkMsgEmptyOffice();
		assertTest(staffOff);		
	}
	@Parameters({"testName","testLinkName"})
	@Test
	public void validateAddOfficeEqualSpace(){
		staffOff=new StaffOffice(this);
		int coutOffice=staffOff.countOffices();
		staffOff.insertData(" ");
		try{
		Assert.assertEquals(coutOffice, staffOff.countOffices());
		}catch (AssertionError e) {
			Reporter.log("<br>&nbsp;&nbsp;&nbsp;- Expected: "+ coutOffice+ " but found: "+ staffOff.countOffices());
			System.out.println(e);
			throw e;
		}
		
	}
	@Parameters({"testName","testLinkName"})
	@Test
	public void validateOfficeUnique() throws JSONException{
		staffOff=new StaffOffice(this);
		staffOff.insertData(staffOff.getOffice()); 
		staffOff.checkMsgOfficeUnique();
		assertTest(staffOff);
	}
	@Parameters({"teskName","testLinkName"})
	@Test
	public void validateDeleteOffice() throws InterruptedException{
		staffOff=new StaffOffice(this);
		int office=staffOff.countOffices();
		staffOff.deleteOffice("yes");
		Assert.assertEquals(office-1,staffOff.countOffices() );

	}
	@Parameters({"teskName","testLinkName"})
	@Test
	public void validateCancelDeleteOffice() throws InterruptedException{
		staffOff=new StaffOffice(this);
		int office=staffOff.countOffices();
		staffOff.deleteOffice("no");
		Assert.assertEquals(office,staffOff.countOffices() );

	}
	@Parameters({"testName","testLinkName"})
	@Test
	public void validateDeleteOfficeNotSelected(){
		staffOff=new StaffOffice(this);
		staffOff.deleteElementNoSelect();
		assertTest(staffOff);
	}
	@Parameters({"testName","testLinkName"})
	@Test
	public void validateDeleteOfficeWithoutAvailableOFF(){
		staffOff=new StaffOffice(this);
		staffOff.deleteOffWithoutAvailableOff();
		assertTest(staffOff);
	}
	

}
