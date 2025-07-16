package mainpackage

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By.ByXPath
import org.openqa.selenium.chrome.ChromeDriver

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
import io.appium.java_client.MobileElement

public class ShopScreen extends BaseHelpers{
	
	private TestObject lblTitleProduct
	private TestObject btnAddToCart
	private TestObject lblProductName
	private TestObject lblProductPrice
	private String xpathProduct
	
	public ShopScreen(String productName="") {
	lblTitleProduct = createObjectByXpath(lblTitleProduct, "//span[@class='title']")
	btnAddToCart = createObjectByXpath(btnAddToCart, "//div[@class='inventory_item_name' and text()='Sauce Labs Backpack']/ancestor::[@class='inventory_item_label']/following-sibling::[@class='pricebar']//button")
//	btnAddToCart = createObjectByXpath(btnAddToCart, "//div[text()='Sauce Labs Backpack']../following-sibling::button ")
	lblProductName = createObjectByXpath(lblProductName, "//div[contains(text(),'$productName')]")
	lblProductPrice = createObjectByXpath(lblProductPrice, "")
	xpathProduct = "//div[@class='inventory_item_name ']"
	}
	
	public void verifyLanding() {
		WebUI.verifyElementPresent(lblTitleProduct, 5)
		WebDriver driver = new ChromeDriver()
		List<WebElement> listProduct = driver.findElements(By.xpath(xpathProduct))
		Set<String> elementStr = new HashSet<>()
		
		for(WebElement el : listProduct) {
			elementStr.add(el.getText());
		}
		
		KeywordUtil.logInfo(listProduct.size().toString())
		KeywordUtil.logInfo("isi element "+ elementStr)
		

	}
	
	public void tapAddToProduct() {
		WebUI.click(btnAddToCart,FailureHandling.STOP_ON_FAILURE)
	}
	
	public void selectProduct(String product) {
		WebUI.click(lblProductName)
	}
	
}
