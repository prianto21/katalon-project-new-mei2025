package com.packages.helpers

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class BaseHelpers {

	public classWebBaseObject() {
	}

	public TestObject createObjectByCustom(String objName,String cusProperty,String xpath) {
		TestObject testObj = new TestObject(objName)
		testObj.addProperty(cusProperty, ConditionType.EQUALS, xpath)
		return testObj
	}

	public TestObject createObjectByXpath(String objName,String xpath) {
		TestObject testObj = new TestObject(objName)
		testObj.addProperty("xpath", ConditionType.EQUALS, xpath)
		return testObj
	}


	public TestObject createObjectById(String objName,String id) {
		TestObject testObject = new TestObject(objName)
		testObject.addProperty("id",ConditionType.EQUALS,id)
		return testObject
	}

	public TestObject createObjectByClass(TestObject objName,String clases) {
		TestObject testObj = new TestObject(objName)
		testObj.addProperty("class",ConditionType.EQUALS,clases)
		return testObj
	}

	public String parseAddedDayOfMonth(int addedDay, String pattern) {
		Calendar calendar = Calendar.getInstance()

		calendar.add(Calendar.DAY_OF_MONTH, addedDay)

		Date newDate = calendar.getTime()

		SimpleDateFormat sdf = new SimpleDateFormat(pattern)

		String formattedDate = sdf.format(newDate)

		return formattedDate
	}

	public String parseAddedDayOfMonthNew(int addedDay, String pattern) {
		LocalDate today = LocalDate.now()

		LocalDate futureDate = today.plusDays(addedDay)

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern)

		return futureDate.format(formatter)
	}
}
