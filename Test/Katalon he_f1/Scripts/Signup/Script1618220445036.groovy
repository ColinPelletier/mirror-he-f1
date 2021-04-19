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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import java.time.format.DateTimeFormatter as DateTimeFormatter
import java.time.LocalDateTime as LocalDateTime

DateTimeFormatter dtf = DateTimeFormatter.ofPattern('yyyy/MM/dd-HH-mm-ss')

LocalDateTime now = LocalDateTime.now()

String DATE = dtf.format(now)

WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:8080/')

WebUI.click(findTestObject('Object Repository/Page_Login/button_Menu'))

WebUI.click(findTestObject('Object Repository/Page_Login/a_Sign in'))

WebUI.click(findTestObject('Object Repository/Page_Login/a_Sign up for free'))

WebUI.setText(findTestObject('Object Repository/Page_HE-F1/input_Username_username'), DATE)

WebUI.setText(findTestObject('Object Repository/Page_HE-F1/input_Email_email'), DATE + '@gmail.com')

WebUI.selectOptionByValue(findTestObject('Object Repository/Page_HE-F1/select_Manageur                    Ingnieur_05750b'), 
    'ENGINEER', true)

WebUI.setEncryptedText(findTestObject('Object Repository/Page_HE-F1/input_Password_password'), 'RigbBhfdqOBGNlJIWM1ClA==')

WebUI.click(findTestObject('Object Repository/Page_HE-F1/button_Register'))

