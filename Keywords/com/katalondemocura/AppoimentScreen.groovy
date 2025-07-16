package com.katalondemocura

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import mainpackage.BaseHelpers

public class AppoimentScreen extends BaseHelpers{

	private TestObject lblTitle
	private TestObject lblFacility
	private TestObject chkApplyForHospital
	private TestObject radHealtcareProgram
	private TestObject lblVisitDate
	private TestObject lblComment
	private TestObject btnBookAppointment
	private String xpathHealthProgram
	private TestObject iconCollapse
	private TestObject lblLogout

	public AppoimentScreen() {
		lblTitle = createObjectByXpath("lblTitle", "//h2")
		lblFacility = createObjectByXpath("lblFacility", "//select[@name='facility']")
		chkApplyForHospital = createObjectByXpath("chkApplyForHospital", "//input[@id='chk_hospotal_readmission']")
		lblVisitDate = createObjectByXpath("lblVisitDate", "//input[@id='txt_visit_date']")
		lblComment = createObjectByXpath("lblComment", "//textarea[@id='txt_comment']")
		btnBookAppointment = createObjectByXpath("btnBookAppointment", "//button[@id='btn-book-appointment']")
		iconCollapse = createObjectByXpath("iconCollapse", "//button[@id='btn-book-appointment']")
		lblLogout = createObjectByXpath("lblLogout", "//a[text()='Logout']")
		xpathHealthProgram="//input[@name='programs' and @value='{program}']"
	}

	public void verifyLanding() {
		WebUI.verifyElementPresent(lblTitle, 0)
	}

	public String selectFacility(String facility) {
		WebUI.waitForElementPresent(lblFacility, 4)
		WebUI.delay(1)
		WebUI.click(lblFacility)
		String strFacility = WebUI.selectOptionByValue(lblFacility, facility, false)
		return strFacility
	}

	public String checkListApply(boolean isChecked) {
		String value=""
		if(!WebUI.verifyElementChecked(chkApplyForHospital, 10,FailureHandling.OPTIONAL) && isChecked==true) {
			WebUI.delay(2)
			WebUI.click(chkApplyForHospital)
			value ="Yes"
		}else {
			value ="No"
		}
		return value
	}

	public String selectHealtcareProgram(String prog) {
		String xpath = xpathHealthProgram.replace('{program}', prog)
		radHealtcareProgram = createObjectByXpath("radHealtcareProgram", xpath)
		WebUI.waitForElementPresent(radHealtcareProgram, 5)
		WebUI.delay(1)
		WebUI.click(radHealtcareProgram)
		String strValue = WebUI.getAttribute(radHealtcareProgram, "value")
		KeywordUtil.logInfo(strValue)
		return strValue
	}

	public String setVisitDate(int addDays) {
		String futureDate = parseAddedDayOfMonth(addDays,"dd/MM/yyyy")
		KeywordUtil.logInfo(futureDate)
		WebUI.waitForElementPresent(lblVisitDate,4)
		WebUI.delay(1)
		WebUI.click(lblVisitDate)
		WebUI.sendKeys(lblVisitDate, futureDate)
	}

	public String setComment(String comment) {
		KeywordUtil.logInfo(comment)
		//		WebUI.waitForElementPresent(lblComment,4)
		WebUI.delay(1)
		WebUI.click(lblComment)
		WebUI.sendKeys(lblComment, comment)
	}

	public void tapBook() {
		WebUI.click(btnBookAppointment)
	}

	public void logOut() {
		WebUI.click(iconCollapse)
		WebUI.click(lblLogout)
	}
}
