package mainpackage

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.apache.ivy.core.module.descriptor.ExtendsDescriptor

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

public class HomePage extends BaseHelpers{
	
	private TestObject lblTitle
	private TestObject txtUsername
	private TestObject txtPassword
	private TestObject btnLogin
	
	public HomePage() {
		lblTitle = createObjectByXpath("lblTitle", "//div[@id='login_button_container']")
		txtUsername = createObjectByXpath("txtUsername", "//input[@id='user-name']")
		txtPassword = createObjectByXpath("txtPassword", "//input[@id='password']")
		btnLogin = createObjectByXpath("btnLogin", "//input[@id='login-button']")
	}
	
	public void verifyLanding() {
		WebUI.waitForElementPresent(lblTitle, 5)
	}
	
	public void inputUserName(String username) {
		WebUI.sendKeys(txtUsername, username)
		WebUI.clearText(txtUsername)
	}
	
	public void inputPassword(String password) {
		KeywordUtil.logInfo(password)
		WebUI.sendKeys(txtPassword, password)
		WebUI.clearText(txtPassword)
	}
	
	public void tapBtnLogin() {
		WebUI.click(btnLogin)
	}
	
	
	
}
