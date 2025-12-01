package com.packages.helpers

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class WebHelpers {

	static boolean parseTag(String tag) {
		if(tag && tag.trim().toLowerCase() == "ready-to-test") {
			return true
		}

		return false
	}
	//Load first Test Data based on ScenarioId and ready-to-test tag only (not filtering based on Device Id)
	static Map getScenarioTestData(Map testData, String scenarioId, boolean markFailed = true) {
		def testDataRow = testData["TestDataXLS"].find{ row -> row["ScenarioId"] == scenarioId && parseTag(row["ScenarioTag"]) }

		if(testDataRow) {
			return testDataRow
		}else {
			if(markFailed) {
				KeywordUtil.markFailed("ExecutionController: loadTestData: 'ready-to-test' ScenarioTag are not found. Please check test data 'ScenarioId': $scenarioId")
			}
			return null
		}
	}
	def static Map LoadTestData(String scenarioId, String filePath, int dataStartIdx = 1) {
		def testData = [:]

		testData["ScenarioId"] = scenarioId
		testData["TestDataFilePath"] = filePath

		readTestDataXLS(testData, dataStartIdx)

		if(testData.size() > 0) {
			testData["TestDataXLS"].each { rows ->
				print "Loading Test Data --> "
				rows.each { key, value ->
					print "$key : $value "
				}
				println ""
			}
		} else {
			println "No data loaded (or file/sheet not found) for Scenario: '$scenarioId' in File: '$filePath'"
		}

		return testData
	}


	def static Map readTestDataXLS(Map testData, int dataStartIdx) {
		def columns = []
		def rows = []
		testData["TestDataXLS"] = rows

		FileInputStream fis
		XSSFWorkbook workbook
		XSSFSheet worksheet

		try {
			File xlsFile = new File(testData["TestDataFilePath"])
			if(!xlsFile.exists()) {
				println("Target file is not found in following path: " + xlsFile.canonicalPath)
				WebUI.comment("Target file is not found in following path: " + xlsFile.canonicalPath)
			}
			fis = new FileInputStream(xlsFile)
			//fis = new FileInputStream(new File("./Data Files/SIT/" + testData["DataFileName"]));
			workbook  = new XSSFWorkbook(fis);
			def maxLength = Math.min(testData["ScenarioId"].length(), 31)
			def scenarioId = testData["ScenarioId"].toString().substring(0,maxLength)
			worksheet = workbook.getSheet(scenarioId)

			//List out all the columns
			XSSFRow columnRow = worksheet.getRow(0 + dataStartIdx)
			int colIndex = 0
			while(columnRow.getCell(colIndex)) {
				XSSFCell columnCell = columnRow.getCell(colIndex)
				columns[colIndex] = columnCell.toString()
				//println columnCell
				colIndex++
			}
			//println colIndex

			//loop the rows
			int rowIndex = 1 + dataStartIdx //data start from row 2 (for common test data template)
			while(worksheet.getRow(rowIndex)) {
				XSSFRow dataRow = worksheet.getRow(rowIndex)
				def data = [:]

				for(int i = 0; i < colIndex; i++) {
					XSSFCell dataCell = dataRow.getCell(i)
					String cellValue = dataCell.toString()
					if(cellValue.trim().endsWith(".0")) {
						cellValue = cellValue.trim().substring(0, cellValue.length() - 2)
					}
					data[columns[i]] = cellValue
				}

				rows.add(data)

				rowIndex++
			}
		}
		catch (Exception ex) {
			println("ERROR: " + ex.message)
			KeywordUtil.markFailedAndStop("Unable to load file: " + ex.message)
		}
		finally {
			if(workbook) workbook.close()
			if(fis) fis.close()

			println("Test Data Load completed!")
		}
	}


	static Map getBoScenarioTestData(String sheetName, String fileName, String scenarioId) {

		Map allDataRow = [:]
		Map scenarioDataRow = [:]
		Map loginDataRow = [:]

		KeywordUtil.logInfo("${RunConfiguration.getHostProperties()['os']}")
		KeywordUtil.logInfo("${RunConfiguration.getExecutionProperties()['drivers']['system']}")
		allDataRow = LoadTestData(sheetName, fileName)
		KeywordUtil.logInfo("masuk")
		//		loginDataRow = WebScenarioHelpers.getBoCredentialInfo(accountName)
		KeywordUtil.logInfo(loginDataRow.toString())
		scenarioDataRow = getScenarioTestData(allDataRow, scenarioId)
		KeywordUtil.logInfo(scenarioDataRow.toString())

		Map mergeDataRow = loginDataRow + scenarioDataRow
		GlobalVariable.TEST_DATA = mergeDataRow

		return mergeDataRow
	}

	/**
	 * Ambil daftar teks dari semua elemen berdasarkan XPath
	 * @param xpath XPath untuk lokasi elemen
	 * @return List<String> teks tiap elemen
	 */
	static List<String> getListOfTexts(String xpath) {

		// Buat TestObject dinamis
		TestObject dynamic = new TestObject("dynamic")
		dynamic.addProperty("xpath", ConditionType.EQUALS, xpath)

		// Ambil semua elemen
		List<WebElement> elements = WebUI.findWebElements(dynamic, 10)
		int count = elements.size()
		KeywordUtil.logInfo(count.toString())
		// Masukkan ke List<String>
		List<String> result = []
		for (WebElement e : elements) {
			result.add(e.getText())
		}

		return result
	}

	/**
	 * Fungsi untuk verify apakah expected text ada di list elemen berdasarkan xpath
	 * @param expectedText teks yang dicari
	 * @param xpath lokasi elemen-elemen pada halaman
	 * @param partialMatch true = contains(); false = exact match
	 * @return boolean hasil verifikasi
	 */
	static boolean verifyProductInList(String expectedText, String xpath, boolean partialMatch = true) {

		// Buat TestObject dinamis
		TestObject dynamicObj = new TestObject("dynamicObj")
		dynamicObj.addProperty("xpath", ConditionType.EQUALS, xpath)

		// Ambil elemen-elemen
		List<WebElement> elements = WebUI.findWebElements(dynamicObj, 10)

		List<String> texts = []
		for (WebElement e : elements) {
			texts.add(e.getText())
		}

		boolean found = false

		if (partialMatch) {
			// partial match -> contains
			found = texts.any { it.contains(expectedText) }
		} else {
			// exact match
			found = texts.contains(expectedText)
		}

		if (found) {
			KeywordUtil.markPassed("✔ Product FOUND in list: " + expectedText)
		} else {
			KeywordUtil.markFailed("✘ Product NOT FOUND. Expected: " + expectedText + "\nActual list: " + texts)
		}

		return found
	}

	public saveDataProductToExcel(String product, String scenarioId) {
		//		String targetDeviceId
		KeywordUtil.logInfo("product name: "+product+"\n"+"\n"+"scenario id : "+scenarioId)
		String xlsLoc = GlobalVariable.TEST_DATA_LOCATION + '/' + "Homescreen_TestData.xlsx"
		Map rowFilter = [:]
		rowFilter['ScenarioId'] = scenarioId
		//		rowFilter['TargetDeviceId'] = targetDeviceId

		String sheetName = 'Homescreen'
		String columnName = 'ProductName'
		//		String columnName2 = 'TotalAmount'

		int columnIndex = ExcelHelper.getColumnIndex(xlsLoc, sheetName, columnName)
		//		int columnIndex2 = ExcelHelper.getColumnIndex(xlsLoc, sheetName, columnName2)
		int rowIndex = ExcelHelper.getRowNumber(xlsLoc, sheetName, rowFilter)
		ExcelHelper.updateCellValue(xlsLoc, sheetName, columnIndex, rowIndex, product)
		//		ExcelHelper.updateCellValue(xlsLoc, sheetName, columnIndex2, rowIndex, totalAmount)
	}

	//		Excel Helpersnya

	//row number of the data relatives to starting index. returned row index start from 0
	public static int getRowNumber(String xlsLoc, String sheetName, Map criteria, int dataRowIdx = 2) {
		List xlsContent = readExcelFile(xlsLoc, sheetName)
		int rowNum = -1

		for(int i = 0; i < xlsContent.size(); i++) {
			boolean matchCriteria = true
			criteria.each { key, value ->
				println("Finding $key: $value in " + xlsContent[i] + " --> " + xlsContent[i][key])
				if(xlsContent[i][key] != value) {
					matchCriteria = false
				}
			}
			if(matchCriteria) {
				rowNum = i
				break
			}
		}
		return rowNum + dataRowIdx
	}

	public static int getColumnIndex(String xlsLoc, String sheetName, String columnName, int colNameRowIdx = 1) {
		int targetColIndex = -1

		FileInputStream fis
		XSSFWorkbook workbook
		XSSFSheet worksheet

		try {
			File xlsFile = new File(xlsLoc)
			if(!xlsFile.exists()) {
				println("Target file is not found in following path: " + xlsFile.canonicalPath)
				WebUI.comment("Target file is not found in following path: " + xlsFile.canonicalPath)
			}
			fis = new FileInputStream(xlsFile)
			workbook  = new XSSFWorkbook(fis);
			worksheet = workbook.getSheet(sheetName.substring(0, Math.min(sheetName.length(), 31))) // safeguard if supplied sheet name > 31 char

			//List out all the columns
			XSSFRow columnRow = worksheet.getRow(colNameRowIdx)
			int colIndex = 0
			while(columnRow.getCell(colIndex)) {
				XSSFCell columnCell = columnRow.getCell(colIndex)
				if(columnCell.toString().equalsIgnoreCase(columnName)) {
					targetColIndex = colIndex
					break
				}
				colIndex++
			}
		}
		catch (Exception ex) {
			println("ERROR: " + ex.message)
		}
		finally {
			if(workbook) workbook.close()
			if(fis) fis.close()

			println("Excel load completed!")
		}

		return targetColIndex
	}

	//default test data template: column name in row 1, data in row 2 (index start from 0)
	public static List readExcelFile(String xlsLoc, String sheetName, int colNameRowIdx = 1, int dataRowIdx = 2) {
		def rows = []

		FileInputStream fis
		XSSFWorkbook workbook
		XSSFSheet worksheet

		try {
			File xlsFile = new File(xlsLoc)
			if(!xlsFile.exists()) {
				println("Target file is not found in following path: " + xlsFile.canonicalPath)
				WebUI.comment("Target file is not found in following path: " + xlsFile.canonicalPath)
			}
			fis = new FileInputStream(xlsFile)
			workbook  = new XSSFWorkbook(fis);
			worksheet = workbook.getSheet(sheetName.substring(0, Math.min(sheetName.length(), 31))) // safeguard if supplied sheet name > 31 char

			//List out all the columns
			XSSFRow columnRow = worksheet.getRow(colNameRowIdx)
			int colIndex = 0
			def columns = []
			while(columnRow.getCell(colIndex)) {
				XSSFCell columnCell = columnRow.getCell(colIndex)
				columns[colIndex] = columnCell.toString()
				colIndex++
			}

			//loop the rows
			while(worksheet.getRow(dataRowIdx)) {
				XSSFRow dataRow = worksheet.getRow(dataRowIdx)
				def data = [:]

				for(int i = 0; i < colIndex; i++) {
					XSSFCell dataCell = dataRow.getCell(i)
					String cellValue = dataCell.toString()
					if(cellValue.trim().endsWith(".0")) {
						cellValue = cellValue.trim().substring(0, cellValue.length() - 2)
					}
					data[columns[i]] = cellValue
				}
				println("Rows content: $data")
				rows.add(data)

				dataRowIdx++
			}
		}
		catch (Exception ex) {
			println("ERROR: " + ex.message)
		}
		finally {
			if(workbook) workbook.close()
			if(fis) fis.close()

			println("Excel load completed!")
		}

		//return list of rows. Each item is a map of colName: value for that row.
		return rows
	}

	public static void updateCellValue(String xlsLoc, String sheetName, int colNumber, int rowNumber, String val) {
		FileInputStream fis
		FileOutputStream fos
		XSSFWorkbook workbook
		XSSFSheet worksheet

		try {
			File xlsFile = new File(xlsLoc)
			if(!xlsFile.exists()) {
				println("Target file is not found in following path: " + xlsFile.canonicalPath)
				WebUI.comment("Target file is not found in following path: " + xlsFile.canonicalPath)
			}
			fis = new FileInputStream(xlsFile)
			workbook  = new XSSFWorkbook(fis)
			worksheet = workbook.getSheet(sheetName.substring(0, Math.min(sheetName.length(), 31))) // safeguard if supplied sheet name > 31 char

			//Retrieve the row and check for null
			XSSFRow sheetrow = worksheet.getRow(rowNumber);
			if(sheetrow == null){
				sheetrow = worksheet.createRow(rowNumber);
			}
			//Update the value of cell
			XSSFCell cell = sheetrow.getCell(colNumber);
			if(cell == null){
				cell = sheetrow.createCell(colNumber);
			}
			cell.setCellValue(val);

			fos = new FileOutputStream(new File(xlsLoc));
			workbook.write(fos);
			fos.close();
		}
		catch (Exception ex) {
			println("ERROR: " + ex.message)
		}
		finally {
			if(fis) fis.close()
			if(fos) fos.close()
			//println("Excel updated!")
		}
	}
}
