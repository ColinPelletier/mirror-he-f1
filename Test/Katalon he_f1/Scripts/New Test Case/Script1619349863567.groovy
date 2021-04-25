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
import com.kms.katalon.core.annotation.Keyword as Keyword
import java.awt.Robot as Robot
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.event.KeyEvent as KeyEvent

//Delay after click on Browser Button
//Delay after paste the text
WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:8080/')

WebUI.click(findTestObject('Object Repository/Page_Home - HE-F1/button_Sign in_1'))

WebUI.setText(findTestObject('Object Repository/Page_Home - HE-F1/input_Username_username'), 'seb2')

WebUI.click(findTestObject('Object Repository/Page_Home - HE-F1/div_Password'))

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Home - HE-F1/input_Password_password'), 'RigbBhfdqOBGNlJIWM1ClA==')

WebUI.click(findTestObject('Object Repository/Page_Home - HE-F1/button_Sign in'))

WebUI.click(findTestObject('Object Repository/Page_Home - HE-F1/a_Your team'))

WebUI.click(findTestObject('Object Repository/Page_Team - HE-F1/button_Create a team'))

WebUI.setText(findTestObject('Object Repository/Page_Team - HE-F1/input_Name_name'), 'n')

WebUI.setText(findTestObject('Object Repository/Page_Team - HE-F1/input_Driver 1 name_driver1'), 'd1')

WebUI.setText(findTestObject('Object Repository/Page_Team - HE-F1/input_Driver 2 name_driver2'), 'd2')

WebUI.setText(findTestObject('Object Repository/Page_Team - HE-F1/input_Car name_carName'), 'c')

uploadFile(findTestObject('Object Repository/Page_Team - HE-F1/input_Car picture_image'), 'C:\\Users\\sebas\\Desktop\\formule-1-renault-2021.png')

WebUI.click(findTestObject('Object Repository/Page_Team - HE-F1/input_Close_btn btn-danger float-right'))

@Keyword
def uploadFile(TestObject to, String filePath) {
    WebUI.click(to)

    WebUI.delay(3)

    StringSelection ss = new StringSelection(filePath)

    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null)

    WebUI.delay(1)

    Robot robot = new Robot()

    robot.keyPress(KeyEvent.VK_ENTER)

    robot.keyRelease(KeyEvent.VK_ENTER)

    robot.keyPress(KeyEvent.VK_CONTROL)

    robot.keyPress(KeyEvent.VK_V)

    robot.keyRelease(KeyEvent.VK_V)

    robot.keyRelease(KeyEvent.VK_CONTROL)

    robot.keyPress(KeyEvent.VK_ENTER)

    robot.keyRelease(KeyEvent.VK_ENTER)
}

