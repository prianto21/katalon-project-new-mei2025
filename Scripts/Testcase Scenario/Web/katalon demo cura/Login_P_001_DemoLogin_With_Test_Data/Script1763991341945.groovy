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
import com.packages.helpers.WebHelpers

import internal.GlobalVariable

import org.openqa.selenium.Keys as Keys

Map dataRow = WebHelpers.getBoScenarioTestData("Homescreen", GlobalVariable.TEST_DATA_LOCATION + '/' + "Homescreen_TestData.xlsx", "Login_P_001_DemoLogin_With_Test_Data")
WebUI.openBrowser("https://katalon-demo-cura.herokuapp.com/")
WebUI.maximizeWindow()
WebUI.callTestCase(findTestCase('Test Cases/Testcase Detail/Web/katalon demo cura/Do_Login'), dataRow)
WebUI.callTestCase(findTestCase('Test Cases/Testcase Detail/Web/katalon demo cura/Make_Appointment_with_Test_Data'), dataRow)
WebUI.callTestCase(findTestCase('Test Cases/Testcase Detail/Web/katalon demo cura/Do_Logout'), dataRow)
WebUI.closeBrowser()