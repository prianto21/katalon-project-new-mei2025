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
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import mainpackage.BaseHelpers

public class LoginScreen extends BaseHelpers{

	private TestObject btnMakeAppointment
	private TestObject lblDemoUsername
	private TestObject lblDemoPassword
	private TestObject txtUsername
	private TestObject txtPassword
	private TestObject btnLogin
	public LoginScreen() {
		btnMakeAppointment = createObjectByXpath(btnMakeAppointment, "//a[@id='btn-make-appointment']")
		lblDemoUsername = createObjectByXpath(lblDemoUsername, "//input[@aria-describedby='demo_username_label']")
		lblDemoPassword = createObjectByXpath(lblDemoPassword, "//input[@aria-describedby='demo_password_label']")
		txtUsername = createObjectByXpath(txtUsername, "//input[@id='txt-username']")
		txtPassword = createObjectByXpath(txtPassword, "//input[@id='txt-password']")
		btnLogin = createObjectByXpath(btnLogin, "//button[@id='btn-login']")
	}

	public void tapAppointment() {
		WebUI.click(btnMakeAppointment)
	}

	public String getUsername() {
		WebUI.getAttribute(lblDemoUsername, "value")
	}

	public String getPassword() {
		WebUI.getAttribute(lblDemoPassword, "value")
	}

	public void setUsername(String username) {
		WebUI.setText(txtUsername, username)
	}

	public void setPassword(String password) {
		WebUI.setText(txtPassword, password)
	}

	public void tapLogin() {
		WebUI.click(btnLogin)
	}
}
