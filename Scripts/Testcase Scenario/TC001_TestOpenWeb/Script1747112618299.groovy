import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import mainpackage.HomePage

import org.openqa.selenium.Keys as Keys

HomePage homepage = new HomePage()
// Buka browser dan navigasi ke halaman login
WebUI.openBrowser('https://www.saucedemo.com/')
//WebUI.navigateToUrl('')

String  listUser= ['ranto,melcy,marko']
String [] listUsername=listUser.split(",")

for(int i=0; i<listUsername.length; i++) {
	'Step 1 : verify landing'
	homepage.verifyLanding()
	
	'Step 2 : input username'
	homepage.inputUserName(listUsername[i])
	
	'Step 3 : input password'
	homepage.inputPassword(Password)
	
	'Step 4 : tap btn login'
	homepage.tapBtnLogin()
	
}

// Tutup browser
WebUI.closeBrowser()