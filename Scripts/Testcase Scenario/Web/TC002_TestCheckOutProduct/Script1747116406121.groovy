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
import mainpackage.ShopScreen

import org.openqa.selenium.Keys as Keys

HomePage homepage = new HomePage()
ShopScreen shop = new ShopScreen()
// Buka browser dan navigasi ke halaman login
WebUI.openBrowser('')
WebUI.navigateToUrl('https://www.saucedemo.com/')



	'Step 1 : verify landing'
	homepage.verifyLanding()
	
	'Step 2 : input username'
	homepage.inputUserName("standard_user")
	
	'Step 3 : input password'
	homepage.inputPassword("secret_sauce")
	
	'Step 4 : tap btn login'
	homepage.tapBtnLogin()
	
	'Step 5 : verify landing'
	shop.verifyLanding()
	
//	shop.selectProduct("Sauce Labs Backpack")
	'Step 6 : tap add product'
//	shop.tapAddToProduct()

//	WebUI.waitForPageLoad(10)
// Tutup browser
WebUI.closeBrowser()