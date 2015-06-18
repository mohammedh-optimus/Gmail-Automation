
package com.automation.test;

import static com.automation.test.DriverScript.CONFIG;

import java.awt.Robot;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JFileChooser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Keywords collection to the project
 * @version (Revised on 18 June 2015)
 * @author vikas.tuli
 */
public class Keywords {
	
	static HashMap<String, String> globalStorage = new HashMap<String, String>();
	String ExpectedArrayList = null;
	static ArrayList<String> ArrayList1 = new ArrayList<String>();
	static ArrayList<String> ArrayList2 = new ArrayList<String>();
	static ArrayList<String> ArrayList3 = new ArrayList<String>();
	static ArrayList<String> ArrayList4 = new ArrayList<String>();
	static ArrayList<String> ArrayList5 = new ArrayList<String>();
	static ArrayList<String> ArrayList6 = new ArrayList<String>();
	static ArrayList<String> container = new ArrayList<String>();
	static ArrayList<String> retriever = new ArrayList<String>();
	static ArrayList<String> windowHandleListPrimary = new ArrayList<String>();
	ArrayList<Integer> ConsecutiveElementListIndex = new ArrayList<Integer>();
	static String frgtype;
	static Long nodeID = 0L;
	static String storeVar = "27-11-01";
	static String frgtype2;
	static Long nodeID2 = 0L;
	static ArrayList<String> browsers = new ArrayList<String>();
	public WebDriver secondDriver;
	static String toc = "";
	static int InitialNumberOfHandles = 0;
	static Long ListID = 0L;
	public WebDriver driver;
	public Actions action;
	
	// To open a particular Web browser defined in Config file
	public String openBrowser(String object, String data) {
	
		System.out.println("Opening browser");
		try {
			if (CONFIG.getProperty(data).equalsIgnoreCase("Mozilla"))
				driver = new FirefoxDriver();
			else if (CONFIG.getProperty(data).equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\IEDriver\\IEDriverServer.exe");
				DesiredCapabilities d = DesiredCapabilities.internetExplorer();
				driver = new InternetExplorerDriver(d);
				driver.manage().window().maximize();
			} else if (CONFIG.getProperty(data).equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver", CONFIG.getProperty("chromeDriverPath"));
				driver = new ChromeDriver();
			}
			long implicitWaitTime = Long.parseLong(CONFIG.getProperty("implicitwait"));
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		} catch (Exception E) {
			return Constants.KEYWORD_FAIL + E.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To navigate the browser on a particular Website defined in Config file
	public String navigate(String object, String data) {
	
		System.out.println("Navigating to URL");
		try {
			driver.navigate().to(CONFIG.getProperty(data));
			// globalStorage.put("projectID", "P0250");
			// globalStorage.put("analysisID", "P0250W0002");
			// globalStorage.put("kbID", "737-NG 0005");
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to navigate";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To navigate back to previous page
	public String navigateBack(String object, String data) {
	
		System.out.println("Navigating to previous page");
		try {
			driver.navigate().back();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to navigate back";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// necessary in each project to capture screenshots
	public void captureScreenShot(String Filename) throws IOException {
	
		System.out.println("taking screen shot according to config");
		// capture screenshots
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\Screenshots\\" + Filename + ".jpg"));
		} catch (Exception e) {
			System.out.println("not able to take screen shots" + e.getMessage());
		}
	}
	
	// To Click an element on a page by its text
	public String clickByText(String object, String data) {
	
		System.out.println("Clicking on link by its text ");
		try {
			driver.findElement(By.linkText(data)).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To click any element by sending enter key when Xpath is used
	public String pressEnterByXpath(String object, String data) {
	
		System.out.println("Clicking on the button");
		try {
			driver.findElement(By.xpath(object)).sendKeys(Keys.ENTER);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click by enter" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To click any element by sending enter key when ID is used
	public String pressEnterByID(String object, String data) {
	
		System.out.println("Clicking on the button");
		try {
			driver.findElement(By.xpath(object)).sendKeys(Keys.ENTER);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To click on an element by its id
	public String clickByID(String object, String data) {
	
		System.out.println("Clicking on the element");
		try {
			driver.findElement(By.id(object)).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method clicks on an element by using javascript
	public String clickUsingJSByID(String object, String data) {
	
		System.out.println("Clicking on the element");
		try {
			waitTillElementIsClickable(object, "id");
			WebElement ele = driver.findElement(By.id(object));
			JavascriptExecutor executr = (JavascriptExecutor) driver;
			executr.executeScript("arguments[0].click();", ele);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method returns an alert
	public String testalert(String object, String data) {
	
		System.out.println("test method");
		try {
			JavascriptExecutor executr = (JavascriptExecutor) driver;
			executr.executeScript("return window.alert.myAlertText");
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To do a simple click on an element by its xpath
	public String clickByXpath(String object, String data) {
	
		System.out.println("Clicking on any element");
		try {
			driver.findElement(By.xpath(object)).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To select the values from the Integer list by ID,please suffix "k" after
	// each value in sheet
	public String selectIntListByID(String object, String data) {
	
		System.out.println("Selecting from list");
		try {
			Select dropDown = new Select(driver.findElement(By.id(object)));
			dropDown.selectByVisibleText(data.split("k")[0]);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To select a Radio Button with some pattern of string values,store the
	// pattern value in test data and put a "|" in place of pattern in Xpath.
	public String selectRadioByXpath(String object, String data) {
	
		System.out.println("Selecting a radio button");
		try {
			String temp[] = object.split(Constants.DATA_SPLIT);
			driver.findElement(By.xpath(temp[0] + data + temp[1])).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To select a Radio Button with some pattern of string values,store the
	// pattern value in test data and put a "|" in place of pattern in ID.
	public String selectRadioByID(String object, String data) {
	
		System.out.println("Selecting a radio button");
		try {
			String temp[] = object.split(Constants.DATA_SPLIT);
			driver.findElement(By.id(temp[0] + data + temp[1])).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To select a radio button with pattern of integer value, store the pattern
	// value in test data and put a "|" in place of pattern in ID, suffix "k" in
	// your test data
	public String selectIntRadioByID(String object, String data) {
	
		System.out.println("Selecting a radio button");
		String temp[] = object.split(Constants.DATA_SPLIT);
		try {
			driver.findElement(By.id(temp[0] + data.split("k")[0] + temp[1])).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button" + temp[0] + data.split(".")[0] + temp[1];
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To select a radio button with pattern of integer value, store the pattern
	// value in test data and put a "|" in place of pattern in ID,suffix "k" in
	// your test data
	public String selectIntRadioByXpath(String object, String data) {
	
		System.out.println("Selecting a radio button");
		String temp[] = object.split(Constants.DATA_SPLIT);
		try {
			driver.findElement(By.xpath(temp[0] + data.split("k")[0] + temp[1])).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button" + temp[0] + data.split(".")[0] + temp[1];
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To verify radio selection which has pattern of string values using ID
	public String verifyRadioSelectedByID(String object, String data) {
	
		System.out.println("Verify Radio Selected");
		try {
			String temp[] = object.split(Constants.DATA_SPLIT);
			String checked = driver.findElement(By.id(temp[0] + data + temp[1])).getAttribute("checked");
			if (checked.equals("true"))
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button";
		}
		return Constants.KEYWORD_FAIL + "- Radio not selected";
	}
	
	// To verify a Radio with pattern of integer value is selected using
	// ID,suffix "k" in your test data
	public String verifyIntRadioSelected(String object, String data) {
	
		System.out.println("Verifying Radio having int patern is Selected");
		String checked = null;
		try {
			String temp[] = object.split(Constants.DATA_SPLIT);
			checked = driver.findElement(By.xpath(temp[0] + data.split("k")[0] + temp[1])).getAttribute("checked");
			if (!checked.equals(null))
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button" + e.getMessage();
		}
		return Constants.KEYWORD_FAIL + "- Radio not selected" + checked;
	}
	
	// To click the button by which an alert is generated and then cancel the
	// alert using Xpath
	public String dismissAlertButtonByXpath(String object, String data) {
	
		System.out.println("Clicking on button and dismissing the alert");
		try {
			driver.findElement(By.xpath(object)).click();
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to dismiss alert";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To click the button by which an alert is generated and then cancel the
	// alert using id
	public String dismissAlertButtonByID(String object, String data) {
	
		System.out.println("Clicking on button and dismissing the alert");
		try {
			driver.findElement(By.id(object)).click();
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to dismiss alert";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To click the button by which an alert is generated and then accept the
	// alert using Xpath
	public String acceptAlertButton(String object, String data) {
	
		System.out.println("Clicking on button and acccepting the alert");
		try {
			driver.findElement(By.xpath(object)).click();
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to accept alert";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method switches to an alert
	public String test(String object, String data) {
	
		System.out.println("switch to alertalert");
		try {
			driver.switchTo().alert();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to accept alert";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To Accept a simple alert
	public String acceptAlert(String object, String data) {
	
		System.out.println("acccepting the browser alert");
		try {
			driver.switchTo().alert().accept();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to accept alert";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To dismiss a simple alert
	public String dismissAlert(String object, String data) {
	
		System.out.println("acccepting the browser alert");
		try {
			driver.switchTo().alert().dismiss();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to accept alert";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To verify Alert text
	public String verifyAlertText(String object, String data) {
	
		System.out.println("acccepting the browser alert");
		try {
			String actual = driver.switchTo().alert().getText();
			System.out.println(actual);
			String expected = data;
			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + "text not match" + expected + "--" + actual;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to find alert" + e.getMessage();
		}
	}
	
	// To verify the text of some link by ID
	public String verifyLinkTextByID(String object, String data) {
	
		System.out.println("Verifying link Text");
		try {
			String actual = driver.findElement(By.id(object)).getText();
			String expected = data;
			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + "--Link text not verified--" + expected + "--" + actual;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Link text not verified" + e.getMessage();
		}
	}
	
	// To verify the text of a button by ID
	public String verifyButtonTextByID(String object, String data) {
	
		System.out.println("Verifying the button text");
		try {
			String actual = driver.findElement(By.id(object)).getText();
			String expected = data;
			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- Button text not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// To verify that a list has all element define in sheet by ID
	public String verifyAllListElementsByID(String object, String data) {
	
		System.out.println("Verifying the selection of the list");
		try {
			WebElement droplist = driver.findElement(By.id(object));
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
			String temp = data;
			String allElements[] = temp.split(",");
			if (allElements.length != droplist_cotents.size())
				return Constants.KEYWORD_FAIL + "- size of lists do not match";
			for (int i = 0; i < droplist_cotents.size(); i++) {
				if (!allElements[i].equals(droplist_cotents.get(i).getText())) {
					return Constants.KEYWORD_FAIL + "- Element not found - " + allElements[i];
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// Verify that a right int value is selected from list by ID
	public String verifyIntListByID(String object, String data) {
	
		System.out.println("Verifying all the list elements");
		try {
			String expectedVal = data;
			WebElement droplist = driver.findElement(By.id(object));
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
			String actualVal = null;
			for (int i = 0; i < droplist_cotents.size(); i++) {
				String selected_status = droplist_cotents.get(i).getAttribute("selected");
				if (selected_status != null)
					actualVal = droplist_cotents.get(i).getText();
			}
			if (!expectedVal.contains(actualVal))
				return Constants.KEYWORD_FAIL + actualVal + "Value not in list - " + expectedVal;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find list. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To uncheck a check box by ID
	public String unCheckCheckBoxByID(String object, String data) {
	
		System.out.println("Unchecking checkBox");
		try {
			String checked = driver.findElement(By.id(object)).getAttribute("checked");
			if (checked != null)
				driver.findElement(By.id(object)).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To verify that a check box is checked by id
	public String verifyCheckBoxCheckedByID(String object, String data) {
	
		System.out.println("Verifying checkbox selected");
		try {
			String checked = driver.findElement(By.id(object)).getAttribute("checked");
			if (checked != null)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " - Not checked";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find checkbox";
		}
	}
	
	// To verify that a checkBox is unchecked by ID
	public String verifyCheckBoxUncheckedByID(String object, String data) {
	
		System.out.println("Verifying checkbox unchecked");
		try {
			String checked = driver.findElement(By.id(object)).getAttribute("checked");
			if (checked == null)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " - Not selected";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find checkbox";
		}
	}
	
	// To verify the url of a page
	public String verifyUrl(String object, String data) {
	
		System.out.println("Verifying the text");
		try {
			String actual = driver.getCurrentUrl();
			String expected = data;
			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- URL not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// to verify that certain text contains some specific text
	public String verifyTextContainsByID(String object, String data) {
	
		System.out.println("Verifying that labels contains text");
		try {
			String actual = driver.findElement(By.id(object)).getText();
			String expected = data;
			if (actual.contains(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " --not contain text " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// to verify the src of an image by id
	public String verifyImageSrc(String object, String data) {
	
		System.out.println("Verifying the src of image");
		try {
			String actual = driver.findElement(By.id(object)).getAttribute("src");
			String expected = data;
			if (actual.contains(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- src not match--" + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// To verify the exact text of an element by id
	public String verifyExactTextByID(String object, String data) {
	
		System.out.println("Verifying the text");
		try {
			String actual = driver.findElement(By.id(object)).getText();
			String expected = data;
			if (actual.equals(expected))
				return Constants.KEYWORD_PASS + actual;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified-- " + expected + " -- " + actual;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// To verify that text is not matched
	public String verifyTextNotMatchByID(String object, String data) {
	
		System.out.println("Verifying the text");
		try {
			String actual = driver.findElement(By.id(object)).getText();
			String expected = data;
			if (!actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text matched" + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// To write text in the text box by id
	public String writeTextByID(String object, String data) {
	
		System.out.println("Writing in text box");
		try {
			driver.findElement(By.id(object)).click();
			driver.findElement(By.id(object)).clear();
			driver.findElement(By.id(object)).sendKeys(data);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To write Some string in a text box by xpath
	public String writeTextByXpath(String object, String data) {
	
		System.out.println("Writing in text box");
		try {
			driver.findElement(By.xpath(object)).click();
			driver.findElement(By.xpath(object)).clear();
			driver.findElement(By.xpath(object)).sendKeys(data);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To write some integer in text box by xpath, Suffix k
	public String writeIntegerByXpath(String object, String data) {
	
		System.out.println("Writing in text box");
		try {
			driver.findElement(By.xpath(object)).clear();
			driver.findElement(By.xpath(object)).sendKeys(data.split("k")[0]);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To write some integer in text box by id, Suffix k
	public String writeIntegerByID(String object, String data) {
	
		System.out.println("Writing in text box");
		try {
			driver.findElement(By.id(object)).click();
			driver.findElement(By.id(object)).clear();
			driver.findElement(By.id(object)).sendKeys(data.split("k")[0]);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To type unique text in the text box everytime, it append a number within
	// 10000 by Xpath
	public String typeUniqueTextbyXpath(String object, String data) {
	
		System.out.println("Typing a unique value" + object);
		try {
			// generate unique number using Random class
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(10000);
			driver.findElement(By.xpath(object)).click();
			driver.findElement(By.xpath(object)).clear();
			// (data+randomInt) generates unique value using static value of
			// data and unique value of randomInt
			driver.findElement(By.xpath(object)).sendKeys(data + randomInt);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to Type unique value " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To type unique text in the text box everytime, it append a number within
	// 10000 by id
	public String typeUniqueTextByID(String object, String data) {
	
		System.out.println("Typing a unique value");
		try {
			// generate unique number using Random class
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(10000);
			driver.findElement(By.id(object)).click();
			driver.findElement(By.id(object)).clear();
			// (data+randomInt) generates unique value using static value of
			// data and unique value of randomInt
			driver.findElement(By.id(object)).sendKeys(data + randomInt);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to Type unique value " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// method to wait till an element is present and clickable
	public String waitTillElementIsVisible(String object, String data) {
	
		System.out.println("Waiting till the presence of element" + object);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			if (data.equalsIgnoreCase("id"))
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(object)));
			else if (data.equalsIgnoreCase("css"))
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(object)));
			else if (data.equalsIgnoreCase("xpath"))
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)));
			else if (data.equalsIgnoreCase("className"))
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(object)));
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// to verify the text of a text box by ID
	public String verifyTextinInputByID(String object, String data) {
	
		System.out.println("Verifying the text in input box");
		try {
			String actual = driver.findElement(By.id(object)).getAttribute("value");
			String expected = data;
			if (actual.equals(expected)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " Not matching the text-" + actual + "--" + expected;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();
		}
	}
	
	// To click an image by its xpath
	public String clickImage(String object, String data) {
	
		System.out.println("Clicking the image");
		try {
			driver.findElement(By.xpath("//img[@src='" + data + "']")).click();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "object not found" + e.getMessage();
		}
	}
	
	// to verify the title of web page
	public String verifyTitle(String object, String data) {
	
		System.out.println("Verifying title");
		try {
			String actualTitle = driver.getTitle();
			String expectedTitle = data;
			if (actualTitle.equals(expectedTitle))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + "--Title not verified--" + expectedTitle + " -- " + actualTitle;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Error in retrieving title";
		}
	}
	
	// To verify an element is exist on a web page based on id
	public String verifyExistByID(String object, String data) {
	
		System.out.println("Checking existance of element");
		try {
			if (driver.findElements(By.id(object)).size() != 0)
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To verify an element is not exist on a web page based on id
	public String verifyNotExistByID(String object, String data) {
	
		System.out.println("Checking non-existance of element");
		try {
			if (driver.findElements(By.id(object)).size() == 0)
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object exist in page";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// Method to close the browser
	public String closeBrowser(String object, String data) {
	
		System.out.println("Closing the browser");
		try {
			driver.quit();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to close browser. Check if its open" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// temporary function to upload but not reliable, its not that optimum to
	// use it has a Script of AutoIT
	public String uploadFile(String object, String data) throws IOException {
	
		System.out.println("Executing AutoIT script");
		try {
			Runtime.getRuntime().exec(CONFIG.getProperty(data));
			Thread.sleep(5000L);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "no script found" + e.getMessage();
		}
	}
	
	// to switch to iframe but its not reliable,ID of frame should be given in
	// object
	public String switchToFrameByID(String object, String data) throws IOException {
	
		System.out.println("switching in iframe");
		try {
			String str[] = object.split(Constants.DATA_SPLIT);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			for (int i = 0; i < str.length; i++) {
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(str[i]));
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "not able to switch" + e.getMessage();
		}
	}
	
	// use to switch control back to window which was on iframe before
	public String switchBackToWindow(String object, String data) throws IOException {
	
		System.out.println("switching back to main window");
		try {
			driver.switchTo().defaultContent();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "not able to switch to window back" + e.getMessage();
		}
	}
	
	// store first window id then click on a link, switch to new window extract
	// title
	// verify the title then switch to previous window
	public String clickSwitchVerifyTitleSwitchBack(String object, String data) {
	
		System.out.println("switching to second tab");
		try {
			String expectedVal = data;
			// Store the current window handle
			String winHandleBefore = driver.getWindowHandle();
			// Perform the click operation that opens new window
			System.out.println("clicking on link which open new window");
			driver.findElement(By.id(object)).click();
			Thread.sleep(5000L);
			System.out.println("clicked successfully");
			// Switch to new window opened
			System.out.println("switching control to new window");
			for (String winHandle : driver.getWindowHandles()) {
				if (!winHandle.equals(winHandleBefore)) { // so that the actions
					                                      // are performed
					                                      // only on the new
					                                      // window
					driver.switchTo().window(winHandle);
				}
			}
			System.out.println("control switched");
			// Perform the actions on new window
			String actualVal = driver.getTitle();
			System.out.println("verifying the title");
			// Close the new window, if that window no more required
			driver.close();
			Thread.sleep(5000L);
			// Switch back to original browser (first window)
			driver.switchTo().window(winHandleBefore);
			if (expectedVal.equals(actualVal)) {
				System.out.println("title verified");
			}
			return Constants.KEYWORD_PASS;
		}
		// continue with original browser (first window)
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + "some problem" + e.getMessage();
		}
		// return Constants.KEYWORD_FAIL +"not able to switch" +e.getMessage();
	}
	
	// method to switch control to new window(use previoud method
	// (clickSwitchNavigateNewWindow) in case it did'nt work)
	public String clickSwitchToNewWindow(String object, String data) {
	
		System.out.println("switching to second tab");
		try {
			// Store the current window handle
			String winHandleBefore = driver.getWindowHandle();
			// Perform the click operation that opens new window
			System.out.println("clicking on link which open new window");
			driver.findElement(By.id(object)).click();
			System.out.println("clicked successfully");
			Thread.sleep(3000L);
			for (String winHandle : driver.getWindowHandles()) {
				if (winHandle != winHandleBefore) {
					driver.switchTo().window(winHandle);
				}
			}
			System.out.println("Control switched");
			return Constants.KEYWORD_PASS;
		}
		// continue with original browser (first window)
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Control is not switched" + e.getMessage();
		}
	}
	
	// method to switch control to new window(use previoud method
	// (clickSwitchToNewWindow) in case it did'nt work)
	public String clickSwitchNavigateNewWindow(String object, String data) {
	
		System.out.println("switching to second tab");
		try {
			// Perform the click operation that opens new window
			System.out.println("clicking on link which open new window");
			driver.findElement(By.id(object)).click();
			System.out.println("clicked successfully");
			Thread.sleep(6000L);
			Set<String> handles = driver.getWindowHandles();
			String winHandleBefore = driver.getWindowHandle();
			handles.remove(winHandleBefore);
			String winHandle = handles.iterator().next();
			if (winHandle != winHandleBefore) {
				driver.switchTo().window(winHandle);
			}
			System.out.println("Control switched");
			return Constants.KEYWORD_PASS;
		}
		// continue with original browser (first window)
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Control is not switched" + e.getMessage();
		}
	}
	
	// it is for closing the child
	public String clickAndSwitchtoMainWindow(String object, String data) {
	
		System.out.println("switching to main window");
		try {
			// Perform the click operation that opens new window
			System.out.println("storing the windowhandles");
			Set<String> handles = driver.getWindowHandles();
			String winHandleBefore = driver.getWindowHandle();
			handles.remove(winHandleBefore);
			String winHandle = handles.iterator().next();
			driver.findElement(By.id(object)).click();
			if (winHandle != winHandleBefore) {
				driver.switchTo().window(winHandle);
			}
			System.out.println("Control switched");
			return Constants.KEYWORD_PASS;
		}
		// continue with original browser (first window)
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Control is not switched" + e.getMessage();
		}
	}
	
	// to verify searched text by ID, the id should be contain Id of table and
	// class name splited by a "|"
	public String verifySearchTextbyID(String object, String data) {
	
		System.out.println("Extracting values of all webelement of table");
		String actual, result;
		try {
			result = Constants.KEYWORD_FAIL;
			WebElement elementList = driver.findElement(By.id(object.split(Constants.DATA_SPLIT)[0]));
			List<WebElement> List_contents = elementList.findElements(By.className(object.split(Constants.DATA_SPLIT)[1]));
			System.out.println("Comparing results of each value of table");
			for (int i = 0; i < List_contents.size(); i++) {
				actual = List_contents.get(i).getText();
				if (actual.contains(data)) {
					result = Constants.KEYWORD_PASS;
				} else {
					result = Constants.KEYWORD_FAIL + "-" + i + 1 + "th value of table does not match--" + actual + "--" + data;
					break;
				}
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to compare search results " + e.getMessage();
		}
	}
	
	/***************************************** Functions having failure chances *******************************************/
	// To hover on a list and select a value
	public String hoverSelect(String object, String data) {
	
		System.out.println("hovering and selecting a value ");
		try {
			WebElement sourceLink = driver.findElement(By.xpath(object));
			WebElement destLink = driver.findElement(By.linkText("Switch Project"));
			Actions builder = new Actions(driver);
			// Move cursor to the Main Menu Element
			builder.clickAndHold(sourceLink).perform();
			// Giving 5 Secs for submenu to be displayed
			Thread.sleep(5000L);
			// Clicking on the Hidden SubMenu
			destLink.click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/***************************************** Functions having failure chances *******************************************/
	// The method waits till an element list is loaded properly
	/*
	 * @SuppressWarnings("deprecation") public String waitTillListLoaded(String object, String data) {
	 * System.out.println("Verifying all the list elements"); try { WebElement droplist =
	 * driver.findElement(By.xpath(object)); WebDriverWait wait = new WebDriverWait(driver, 30);
	 * wait.until(ExpectedConditions.textToBePresentInElement(By.xpath(object), data)); return
	 * Constants.KEYWORD_PASS; } catch (Exception e) { return Constants.KEYWORD_FAIL +
	 * " - Could not find list. " + e.getMessage(); } }
	 */
	// The method waits for an element to be visible by XPath
	public String waitForElementVisibility(String object, String data) {
	
		System.out.println("Waiting for an element to be visible");
		int start = 0;
		int time = (int) Double.parseDouble(data);
		try {
			while (time == start) {
				if (driver.findElements(By.xpath((object))).size() == 0) {
					Thread.sleep(1000L);
					start++;
				} else {
					break;
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to close browser. Check if its open" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method maximizes the browser window
	public String maximize(String object, String data) {
	
		System.out.println("Maximizing the  window");
		try {
			driver.manage().window().maximize();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method uploads a file by providing the file path in data sheet
	public String uploadFileByID(String object, String data) {
	
		System.out.println("Writing in text box");
		try {
			driver.findElement(By.id(object)).sendKeys(System.getProperty("user.dir") + data);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method waits till an alert is present and switch to it
	public String switchToAlert(String object, String data) {
	
		System.out.println("Switching into alert");
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To check whether button is enabled or not - Anirudh
	public String buttonEnabled(String object, String data) {
	
		System.out.println("Waiting for an element to be visible");
		try {
			WebElement button = driver.findElement(By.id(object));
			if (button.isEnabled()) {
				return Constants.KEYWORD_PASS;
			} else {
			}
			return Constants.KEYWORD_FAIL + " unable to click ";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to click button" + e.getMessage();
		}
	}
	
	// To verify the title of a page by Class
	public String verifyPageTitleByClass(String object, String data) {
	
		System.out.println("Verifying the page name");
		try {
			List<WebElement> pagenames = driver.findElements(By.className(object));
			waitTillVisibilityOfElementList(pagenames);
			String str[] = data.split(Constants.DATA_SPLIT);
			int index = Integer.parseInt(str[1]);
			String actual = pagenames.get(index).getText();
			String expected = str[0];
			if (actual.trim().equalsIgnoreCase(expected.trim()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " --Page Title is not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// to click on tabel cell, the id should be container Id of table and row
	// which needs to be clicked.
	public String clickTabelCellByID(String object, String data) {
	
		System.out.println("extracting values of all rows of table");
		String result;
		try {
			result = Constants.KEYWORD_FAIL;
			String str[] = object.split(Constants.DATA_SPLIT);
			WebElement elementList = driver.findElement(By.id(str[0]));
			List<WebElement> List_contents = elementList.findElements(By.xpath("//tr[starts-with(@id,'" + str[1] + "')]"));
			System.out.println("Comparing results of table");
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				if (actual.startsWith(data)) {
					String elementID = List_contents.get(i).getAttribute("id");
					driver.findElement(By.id(elementID)).click();
					return Constants.KEYWORD_PASS;
				} else {
					continue;
				}
			}
			result = Constants.KEYWORD_FAIL + "Text does not found";
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// method to wait till an elem is present and clickable
	public String waitTillFrameIsFound(String object, String data) {
	
		System.out.println("Waiting till the presence of element" + object);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try {
			if (data.equalsIgnoreCase("id"))
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(object));
			else if (data.equalsIgnoreCase("css"))
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(object)));
			else if (data.equalsIgnoreCase("xpath"))
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)));
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// Method will retrieve text from multiple textfields after completing edit
	// operation into an Array List
	public String retrieveTextByID(String object, String data) {
	
		System.out.println("Retrieving the text from input box and storing it to an arraylist");
		try {
			retriever.add(driver.findElement(By.id(object)).getAttribute("value"));
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();
		}
	}
	
	// Method will verify Notification text received in Inbox
	public String verifyNotificationTextContainsByID(String object, String data) {
	
		System.out.println("Verifying that notification text contains expected text");
		try {
			String actual = driver.findElement(By.id(object)).getText();
			String expected = data;
			if (actual.contains(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " --not contain text " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// Method will verify Notification text received in Inbox
	public String verifyEnabledStateByID(String object, String data) {
	
		System.out.println("Verifying that state is enabled");
		try {
			WebElement element = driver.findElement(By.id(object));
			if (element.isEnabled()) {
				System.out.println("state is enabled");
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + "State is not Enabled";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// Method will verify text based on class
	public String verifySearchResultByClass(String object, String data) {
	
		System.out.println("Verifying the matched search result");
		try {
			String actual = driver.findElement(By.className(object)).getText();
			String expected = data;
			if (actual.equals(expected)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " Not matching Search Result Text-" + actual + "--" + expected;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to match Search Result Text " + e.getMessage();
		}
	}
	
	// Method will verify a particular text from search results
	public String verifyStatusListByID(String object, String data) {
	
		System.out.println("Verifying all the list elements");
		try {
			String expectedVal = data;
			List<WebElement> droplist_cotents = driver.findElements(By.id(object));
			for (int i = 0; i < droplist_cotents.size(); i++) {
				String selected_status = droplist_cotents.get(i).getText();
				if (selected_status.equals(expectedVal)) {
					continue;
				} else
					return Constants.KEYWORD_FAIL + "Text is not matched - " + selected_status + "--" + expectedVal;
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find list. " + e.getMessage();
		}
	}
	
	// Method to refresh page
	public String refresh(String object, String data) {
	
		System.out.println("Refreshing page");
		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to refresh" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// Method will select a link based on an attribute. Object contains
	// attribute name and data contains attribute value
	public String selectLinkByAttribute(String object, String data) {
	
		System.out.println("Selecting Link");
		try {
			driver.findElement(By.xpath("//*[contains(@" + object + ", '" + data + "')]")).click();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to select Link " + e.getMessage();
		}
	}
	
	// Method will verify text by class
	public String verifyTextByClass(String object, String data) {
	
		System.out.println("Verifying the text");
		try {
			String expected = data;
			List<WebElement> pagenames = driver.findElements(By.className(object));
			for (int i = 0; i < pagenames.size(); i++) {
				String actual = pagenames.get(i).getText();
				if (actual.trim().equals(expected)) {
					break;
				} else {
					if (i == pagenames.size()) {
						return Constants.KEYWORD_FAIL + " Unable to verify Text";
					} else {
						continue;
					}
				}
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to verify Text " + e.getMessage();
		}
	}
	
	// Method will perform click operation based on JS
	public String clickUsingJSByAttribute(String object, String data) {
	
		System.out.println("Clicking on any element");
		try {
			WebElement ele = driver.findElement(By.xpath("//*[contains(@" + object + ", '" + data + "')]"));
			JavascriptExecutor executr = (JavascriptExecutor) driver;
			executr.executeScript("arguments[0].click();", ele);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method clicks on an element and switches to a newly opened window after clicking
	 * @param object - ID of element
	 * @param data - Optional Variable
	 * @return - execution result
	 */
	public String switchToWindowID(String object, String data) {
	
		System.out.println("Switching to new window");
		try {
			String Parenthandle = driver.getWindowHandle();
			Set<String> HandlesBeforeClick = driver.getWindowHandles();
			int CurrentNoOfHandle = HandlesBeforeClick.size();
			driver.findElement(By.id((object))).click();
			waitTillNoOfWindows(CurrentNoOfHandle + 1);
			Set<String> handle = driver.getWindowHandles();
			if (handle.contains(Parenthandle))
				handle.remove(Parenthandle);
			for (String winHandle : handle) {
				driver.switchTo().window(winHandle);
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("exception caught :  " + e);
			return Constants.KEYWORD_FAIL;
		}
	}
	
	// The method waits till number of windows increases by one than the current
	// number of browser windows
	public String waitTillNoOfWindows(final int numberOfWindows) {
	
		System.out.println("Waiting till number of windows becomes 2");
		try {
			new WebDriverWait(driver, 30) {
			}.until(new ExpectedCondition<Boolean>() {
				
				public Boolean apply(WebDriver driver) {
				
					return (driver.getWindowHandles().size() == numberOfWindows);
				}
			});
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Number of windows is not equal to 2" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method waits till element is clickable
	public String waitTillElementIsClickable(String object, String data) {
	
		System.out.println("Waiting till element is clickable" + object);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			if (data.equalsIgnoreCase("id"))
				wait.until(ExpectedConditions.elementToBeClickable(By.id(object)));
			else if (data.equalsIgnoreCase("css"))
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(object)));
			else if (data.equalsIgnoreCase("xpath"))
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(object)));
			else if (data.equalsIgnoreCase("className"))
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(object)));
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method verifies the URL of the active window
	public String verifyContainsUrl(String object, String data) {
	
		System.out.println("Verifying the text");
		try {
			String actual = driver.getCurrentUrl();
			String expected = data;
			if (actual.contains(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- URL not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// The method waits till element list becomes visible
	public String waitTillVisibilityOfElementList(List<WebElement> ElementList) {
	
		System.out.println("Waiting for all elements to be visible");
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfAllElements(ElementList));
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "unable to wait for elements" + e.getMessage();
		}
	}
	
	// The method clicks on an element by text (provided in datasheet)
	public String clickElementByText(String object, String data) {
	
		System.out.println("Clicking on element by its text ");
		try {
			driver.findElement(By.xpath(".//*[@text()='" + data + "']")).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method verifies if an element is present in the DOM or not
	public String verifyElementPresence(String object, String data) {
	
		System.out.println("Checking existance of element");
		try {
			String str[] = object.split(Constants.DATA_SPLIT);
			if (driver.findElements(By.id(str[0])).size() != 0 || driver.findElements(By.id(str[1])).size() != 0)
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method switches to a window handle saved in constant-- WINDOW_HANDLE
	public String switchToWinHandleSavedInConst(String object, String data) {
	
		System.out.println("Changing the focus to top window ");
		try {
			driver.switchTo().window(Constants.WINDOW_HANDLE);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to changefocus - " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method verifies the table row values corresponding to a particular
	// table row heading and saves revision number to a constant
	public String traverseInTable(String object, String data) {
	
		System.out.println("Traversing in table ");
		try {
			List<WebElement> TableHeadData = driver.findElements(By.xpath("//*[@id='fakeRadioCheckboxes']/thead/tr/th"));
			List<WebElement> TableRowData = driver.findElements(By.xpath("//*[@id='fakeRadioCheckboxes']/tbody/tr/td"));
			List<WebElement> TableRowNum = driver.findElements(By.xpath("//*[@id='fakeRadioCheckboxes']/tbody/tr"));
			ArrayList<String> TableHeadList = new ArrayList<String>(TableHeadData.size());
			ArrayList<String> TableRowList = new ArrayList<String>(TableRowData.size());
			String TableRowId = null;
			for (int i = 0; i < TableHeadData.size(); i++) {
				TableHeadList.add(TableHeadData.get(i).getAttribute("title"));
			}
			for (int i = 0; i < TableRowData.size(); i++) {
				TableRowList.add(TableRowData.get(i).getText());
			}
			for (int tableRowNum = 0; tableRowNum < TableRowNum.size(); tableRowNum++) {
				for (int i = 0; i < TableHeadData.size(); i++) {
					if (TableHeadList.get(i).contentEquals("Doc Name")) {
						if (TableRowList.get(i).contains(data)) {
							for (int j = 0; j < TableHeadData.size(); j++) {
								if (TableHeadList.get(j).contentEquals("Rev #")) {
									Constants.Revision_Num = TableRowList.get(j);
									TableRowId = TableRowNum.get(tableRowNum).getAttribute("id");
									driver.findElement(By.id(TableRowId)).click();
									break;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to traverse - " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method calculate a particular date by providing the number of days
	// from the current date in data sheet
	public String writeNewPublishDate(String object, String data) {
	
		System.out.println("Writing new publish date ");
		try {
			String[] p = data.split(",");
			String pattern = "dd MM yyyy";
			new SimpleDateFormat(pattern);
			// formatting
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, Integer.parseInt(p[0]));
			cal.getTime();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to changefocus - " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method waits till an alert is present and switches to alert
	public String acceptAlertButtonByID(String object, String data) {
	
		System.out.println("Clicking on button and acccepting the alert");
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to accept alert";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method verifies an attribute value by providing attribute and its
	// corresponding value in data sheet separated by pipeline
	public String verifyAttribuleValueContainsByID(String object, String data) {
	
		System.out.println("Verifying that labels contains text");
		try {
			String actual = driver.findElement(By.id(object)).getAttribute(data.split(Constants.DATA_SPLIT)[0]);
			String expected = data.split(Constants.DATA_SPLIT)[1];
			if (actual.contains(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " --not contain text " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// The method clicks on a particular row by providing table row contents in
	// data sheet separated by pipeline
	public String clickTabelCellContainsCellById(String object, String data) {
	
		System.out.println("Clikcing on the table cell");
		String result;
		try {
			result = Constants.KEYWORD_FAIL;
			String str[] = object.split(Constants.DATA_SPLIT);
			WebElement elementList = driver.findElement(By.id(str[0]));
			List<WebElement> List_contents = elementList.findElements(By.xpath("//tr[starts-with(@id,'" + str[1] + "')]"));
			System.out.println("Comparing results of table");
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				if (actual.contains(data)) {
					String elementID = List_contents.get(i).getAttribute("id");
					driver.findElement(By.id(elementID)).click();
					return Constants.KEYWORD_PASS;
				} else {
					continue;
				}
			}
			result = Constants.KEYWORD_FAIL + "Text does not found";
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// Method will verify a text in tabel based on a unique text of row. Pass
	// unique text and text to be verified from test data.
	public String verifyTextInTabelByID(String object, String data) {
	
		System.out.println("Verifying the text");
		int flag = 0;
		try {
			String str[] = object.split(Constants.DATA_SPLIT);
			Thread.sleep(5000L);
			WebElement elementList = driver.findElement(By.id(str[0]));
			List<WebElement> List_contents = elementList.findElements(By.xpath("//tr[starts-with(@id,'" + str[1] + "')]"));
			System.out.println("Comparing results of table");
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				for (int j = 0; j < container.size(); j++) {
					if (actual.contains(container.get(j)) && actual.startsWith(data)) {
						flag = 1;
						continue;
					} else {
						flag = 0;
					}
				}
				if (flag == 1) {
					String elementID = List_contents.get(i).getAttribute("id");
					driver.findElement(By.id(elementID)).click();
					return Constants.KEYWORD_PASS;
				} else {
					continue;
				}
			}
			return Constants.KEYWORD_FAIL + " Unable to find text ";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The method waits till an alert appears
	public String waitTillAlertIsPresent(String object, String data) {
	
		System.out.println("Waiting till the presence of element");
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.alertIsPresent());
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method load Config properties (properties file)
	public Properties LoadProperties() {
	
		System.out.println("Loading the properties");
		String path = System.getProperty("user.dir") + "\\src\\com\\automation\\config\\or.properties";
		FileReader reader;
		Properties properties = new Properties();
		try {
			reader = new FileReader(path);
			properties.load(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	// Method will verify whether the element is disabled or not
	public String verifyDisabledStateByID(String object, String data) {
	
		System.out.println("Verifying that state is disabled");
		try {
			WebElement element = driver.findElement(By.id(object));
			if (!(element.isEnabled())) {
				System.out.println("state is disabled");
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + "State is Enabled";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// The method switches to a window after clicking on an element
	public String SwitchToMultipleWindows(String object, String data) {
	
		System.out.println("Switching to new window");
		try {
			if (!(windowHandleListPrimary.contains(driver.getWindowHandle())))
				windowHandleListPrimary.add(driver.getWindowHandle());
			int CurrentNoOfHandle = windowHandleListPrimary.size();
			driver.findElement(By.id((object))).click();
			waitTillNoOfWindows(CurrentNoOfHandle + 1);
			ArrayList<String> windowHandleListSecondary = new ArrayList<String>(driver.getWindowHandles());
			String HandleToSwitch = null;
			for (int SecondaryListIndex = 0; SecondaryListIndex < windowHandleListSecondary.size(); SecondaryListIndex++) {
				if (!(windowHandleListPrimary.contains(windowHandleListSecondary.get(SecondaryListIndex)))) {
					HandleToSwitch = windowHandleListSecondary.get(SecondaryListIndex);
					windowHandleListPrimary.add(HandleToSwitch);
					break;
				}
			}
			driver.switchTo().window(HandleToSwitch);
		} catch (Exception e) {
			System.out.println("exception caught :  " + e);
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method switches back to a particular window by proving window number
	// (e.g. 2k) in data sheet
	public String SwitchBackToMultipleWindows(String object, String data) {
	
		System.out.println("Switching back to previous window");
		try {
			int WindowNumToSwitch = Integer.parseInt(data.split("k")[0]);
			driver.switchTo().window(windowHandleListPrimary.get(WindowNumToSwitch - 1));
		} catch (Exception e) {
			System.out.println("exception caught :  " + e);
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method clears an arraylist--- windowHandleListPrimary
	public String clearWindowHandleArrayList(String object, String data) {
	
		System.out.println("Emptying the given arrayList...");
		try {
			windowHandleListPrimary.clear();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find arrayList " + e.getMessage();
		}
	}
	
	// The method clicks on a table row by providing table row values in data
	// sheet separated by pipeline
	public String clickTableRowText(String object, String data) {
	
		System.out.println("Clicking on the table row");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			int flag = 0;
			List<WebElement> List_contents = driver.findElements(By.xpath(".//*[@id='" + object + "']/tbody/tr"));
			waitTillVisibilityOfElementList(List_contents);
			System.out.println("Comparing results of table");
			for (int i = 0; i < List_contents.size(); i++) {
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					String arr[] = str[expectedArr].split(";");
					String expectedresult = List_contents.get(i).findElement(By.xpath("td[" + arr[1] + "]")).getText();
					if (expectedresult.trim().toLowerCase().equals(arr[0].trim().toLowerCase())) {
						flag += 1;
					} else
						flag = -1;
					if (flag == (str.length)) {
						String elementID = List_contents.get(i - 1).getAttribute("id");
						driver.findElement(By.id(elementID)).click();
						result = Constants.KEYWORD_PASS;
						break;
					} else if (flag == -1) {
						result = Constants.KEYWORD_FAIL + "Text does not found";
					}
				}
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The method if a table row with the spacified value is present in table or
	// not (provide table row value to be searched in data sheet separated by
	// pipeline)
	public String verifyTextNotInTabelByID(String object, String data) {
	
		System.out.println("Verifying the text");
		int flag = 0;
		try {
			String str[] = object.split(Constants.DATA_SPLIT);
			WebElement elementList = driver.findElement(By.id(str[0]));
			List<WebElement> List_contents = elementList.findElements(By.xpath("//tr[starts-with(@id,'" + str[1] + "')]"));
			System.out.println("Comparing results of table");
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				for (int j = 0; j < container.size(); j++) {
					if (!(actual.contains(container.get(j)) && actual.startsWith(data))) {
						flag = 1;
						continue;
					} else {
						flag = 0;
					}
				}
				if (flag == 1) {
					return Constants.KEYWORD_PASS;
				} else {
					continue;
				}
			}
			return Constants.KEYWORD_FAIL + " Unable to find text ";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The method writes a specific date in a specific pattern by providing
	// format and number of days from current days in data sheet (separated by
	// pipeline)
	public String calculateNewDate(String object, String data) {
	
		System.out.println("Calculating new date");
		try {
			String pattern = data.split(Constants.DATA_SPLIT)[0];
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			// formatting
			format.format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, Integer.parseInt(data.split(Constants.DATA_SPLIT)[1]));
			Date NewDate = cal.getTime();
			String ModifiedDate = format.format(NewDate);
			driver.findElement(By.id(object)).sendKeys(ModifiedDate);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The method check checkbox of a row by providing row data in data sheet
	public String checkCheckboxByXpath(String object, String data) {
	
		System.out.println("Clicking on the check box");
		try {
			String result = null;
			String arr[] = data.split(",");
			String str[] = arr[0].split(Constants.DATA_SPLIT);
			List<WebElement> List_contents = null;
			int flag = 0;
			List<WebElement> tdList = driver.findElements(By.xpath(object + "/td[9]"));
			waitTillVisibilityOfElementList(tdList);
			System.out.println("Comparing results of table");
			for (int j = 0; j < tdList.size(); j++) {
				if (tdList.get(j).getText().equals(arr[1])) {
					List_contents = driver.findElements(By.xpath("//table/tbody/tr[contains(@id,'tr_ckbx_" + (j + 1) + "')]"));
				}
			}
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					if (actual.trim().toLowerCase().contains(str[expectedArr].trim().toLowerCase())) {
						flag = expectedArr;
					} else
						flag = -1;
				}
				if (flag == (str.length - 1)) {
					String elementID = List_contents.get(i).getAttribute("id");
					driver.findElement(By.xpath("//input[contains(@id, '" + elementID.substring(3) + "')]")).click();
					result = Constants.KEYWORD_PASS;
					break;
				} else if (flag == -1) {
					result = Constants.KEYWORD_FAIL + "Text does not found";
				}
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// To Click an element on a page by its text (Integer)
	public String clickIntegerLink(String object, String data) {
	
		System.out.println("Clicking on link by its text ");
		try {
			driver.findElement(By.linkText(data.split("k")[0])).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To Click an element on a page by custom Xpath
	public String clickElementByCustomXpath(String object, String data) {
	
		System.out.println("Clicking on link by its custom Xpath ");
		try {
			String str[] = object.split(Constants.DATA_SPLIT);
			driver.findElement(By.xpath("//" + str[0] + "[" + str[1] + "(@" + str[2] + ", '" + str[3] + "')]")).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method switches to a window that appears after clicking on an element
	public String SwitchToWindowByCustomXpath(String object, String data) {
	
		System.out.println("Switching to new window");
		try {
			if (!(windowHandleListPrimary.contains(driver.getWindowHandle())))
				windowHandleListPrimary.add(driver.getWindowHandle());
			int CurrentNoOfHandle = windowHandleListPrimary.size();
			clickElementByCustomXpath(object, data);
			waitTillNoOfWindows(CurrentNoOfHandle + 1);
			ArrayList<String> windowHandleListSecondary = new ArrayList<String>(driver.getWindowHandles());
			String HandleToSwitch = null;
			for (int SecondaryListIndex = 0; SecondaryListIndex < windowHandleListSecondary.size(); SecondaryListIndex++) {
				if (!(windowHandleListPrimary.contains(windowHandleListSecondary.get(SecondaryListIndex)))) {
					HandleToSwitch = windowHandleListSecondary.get(SecondaryListIndex);
					windowHandleListPrimary.add(HandleToSwitch);
					break;
				}
			}
			driver.switchTo().window(HandleToSwitch);
		} catch (Exception e) {
			System.out.println("exception caught :  " + e);
		}
		return Constants.KEYWORD_PASS;
	}
	
	// Method will verify a text in table based on attribute value
	public String verifyTextInTabelByAttribute(String object, String data) {
	
		System.out.println("Verifying the text");
		try {
			String str[] = object.split(Constants.DATA_SPLIT);
			WebElement elementList = driver.findElement(By.id(str[0]));
			System.out.println("Finding in tabel");
			if (elementList.findElements(By.xpath("//tr[contains(@" + str[1] + ",'" + str[2] + "')]")).size() > 0) {
				driver.findElement(By.xpath("//tr[contains(@" + str[1] + ",'" + str[2] + "')]")).click();
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + "Unable to find text in tabel";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The method clicks on a particular row by providing row data in data sheet
	// as well as in an arraylist
	public String verifyRowByTextAndConstant(String object, String data) {
	
		System.out.println("Verifying the table row text");
		try {
			String elementID = searchTableRowByDataAndArrayList(object, data);
			WebElement expectedElement = driver.findElement(By.id(elementID));
			waitTillElementIsClickable(elementID, "id");
			expectedElement.click();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The keyword below clicks on row by providing row content only
	public String clickTabelRowByText(String object, String data) {
	
		System.out.println("Cliking on the table row");
		try {
			String elementID = searchTableRowByData(object, data);
			waitTillElementIsClickable(elementID, "id");
			WebElement ele = driver.findElement(By.id(elementID));
			JavascriptExecutor executr = (JavascriptExecutor) driver;
			executr.executeScript("arguments[0].click();", ele);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The method searches for a row by comparing data provided in excel sheet
	// only and returns id of the row.
	// This method can be called by another method to perform actions with id of
	// the row returned
	public String searchTableRowByData(String object, String data) {
	
		System.out.println("Searching in the table");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			List<WebElement> List_contents = driver.findElements(By.xpath("//*[@id='" + object + "']/tbody/tr"));
			waitTillVisibilityOfElementList(List_contents);
			for (int i = 0; i < List_contents.size(); i++) {
				List<WebElement> Column = List_contents.get(i).findElements(By.tagName("td"));
				int flagStr = 0;
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					for (int columnListIndex = 0; columnListIndex < Column.size(); columnListIndex++) {
						if (Column.get(columnListIndex).getText().trim().toLowerCase().contentEquals(str[expectedArr].trim().toLowerCase())) {
							flagStr += 1;
							break;
						}
					}
				}
				if (flagStr == str.length) {
					String elementID = List_contents.get(i).getAttribute("id");
					result = elementID;
					break;
				} else
					result = Constants.KEYWORD_FAIL + "Matching row does not found";
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The method searches for a filtered row by comparing data provided in
	// excel sheet only and returns id of the row.
	// This method can be called by another method to perform actions with id of
	// the row returned
	public String searchFilteredTableRowByData(String object, String data) {
	
		System.out.println("Searching in the table");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			WebElement Table = driver.findElement(By.xpath(".//*[@id='" + object + "']"));
			List<WebElement> List_contents = Table.findElements(By.xpath("//tr[contains(@style,'block')]"));
			waitTillVisibilityOfElementList(List_contents);
			for (int i = 0; i < List_contents.size(); i++) {
				List<WebElement> Column = List_contents.get(i).findElements(By.tagName("td"));
				int flagStr = 0;
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					for (int columnListIndex = 0; columnListIndex < Column.size(); columnListIndex++) {
						if (Column.get(columnListIndex).getText().trim().toLowerCase().contentEquals(str[expectedArr].trim().toLowerCase())) {
							flagStr += 1;
							break;
						}
					}
				}
				if (flagStr == str.length) {
					String elementID = List_contents.get(i).getAttribute("id");
					result = elementID;
					break;
				} else
					result = Constants.KEYWORD_FAIL + "Matching row does not found";
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The keyword below clicks on flitered row by providing row content only
	public String clickFilteredTabelRowByText(String object, String data) {
	
		System.out.println("Clicking the filtered table row");
		try {
			String elementID = searchFilteredTableRowByData(object, data);
			WebElement expectedElement = driver.findElement(By.id(elementID));
			waitTillElementIsClickable(elementID, "id");
			expectedElement.click();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The keyword below clicks on flitered row by providing row content and
	// arraylist
	public String verifyFilteredRowByTextAndConstant(String object, String data) {
	
		System.out.println("Verifying the sorted rows");
		try {
			String elementID = searchFilteredTableRowByDataAndArrayList(object, data);
			WebElement expectedElement = driver.findElement(By.id(elementID));
			waitTillElementIsClickable(elementID, "id");
			expectedElement.click();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The method searches for a row by comparing data provided in excel sheet
	// only (contains)and returns id of the row.
	// This method can be called by another method to perform actions with id of
	// the row returned
	public String searchTableRowContainsByData(String object, String data) {
	
		System.out.println("Searching in the table");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			System.out.println("extracting");
			List<WebElement> List_contents = driver.findElements(By.xpath("//*[@id='" + object + "']/tbody/tr"));
			waitTillVisibilityOfElementList(List_contents);
			for (int i = 0; i < List_contents.size(); i++) {
				int flagStr = 0;
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					if (str.length == 0)
						break;
					else {
						if (actual.trim().toLowerCase().contains(str[expectedArr].trim().toLowerCase()))
							flagStr += 1;
					}
				}
				if (flagStr == str.length) {
					String elementID = List_contents.get(i).getAttribute("id");
					result = elementID;
					break;
				} else
					result = Constants.KEYWORD_FAIL + "Matching row does not found";
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The keyword below clicks on row (contains)by providing row content only
	public String clickTabelRowContainedByText(String object, String data) {
	
		System.out.println("Clicking the table row containing data");
		try {
			String elementID = searchTableRowContainsByData(object, data);
			WebElement expectedElement = driver.findElement(By.id(elementID));
			waitTillElementIsClickable(elementID, "id");
			JavascriptExecutor executr = (JavascriptExecutor) driver;
			executr.executeScript("arguments[0].click();", expectedElement);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The keyword below clicks on row (contains) by providing row content and
	// arraylist
	public String verifyRowContainsByTextAndConstant(String object, String data) {
	
		System.out.println("Verifying the table rows containing data");
		try {
			String elementID = searchTableRowContainsByDataAndArrayList(object, data);
			WebElement expectedElement = driver.findElement(By.id(elementID));
			waitTillElementIsClickable(elementID, "id");
			expectedElement.click();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The method takes arraylist variable from datasheet and save it to a
	// global variable
	public String saveArrayListTypeToGlobalVar(String object, String data) {
	
		System.out.println("Saving the array list into a global variable");
		try {
			ExpectedArrayList = data;
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// Method will store text from multiple textfields while editing a text into
	// an Array List
	public String storeTextByID(String object, String data) {
	
		System.out.println("Storing the text from input box into arraylist");
		try {
			returnChosenArrayList().add(driver.findElement(By.id(object)).getAttribute("value"));
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();
		}
	}
	
	// The method clicks on a row by providing row data in data sheet as well as
	// in an arraylist
	public String clickMyInboxRowByText(String object, String data) {
	
		System.out.println("Clickin in My Inbox content");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			List<WebElement> List_contents = driver.findElements(By.xpath(".//*[@id='" + object + "']/tbody/tr"));
			waitTillVisibilityOfElementList(List_contents);
			System.out.println("Comparing results of table");
			System.out.println("size" + List_contents.size());
			for (int i = 0; i < List_contents.size(); i++) {
				int flagContainer = 0;
				int flagStr = 0;
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < returnChosenArrayList().size(); expectedArr++) {
					System.out.println("actual" + actual);
					System.out.println("arraylist" + returnChosenArrayList());
					if (actual.trim().toLowerCase().contains(returnChosenArrayList().get(expectedArr).trim().toLowerCase()))
						flagContainer += 1;
				}
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					System.out.println("expectd" + str[expectedArr]);
					if (actual.trim().toLowerCase().contains(str[expectedArr].trim().toLowerCase()))
						flagStr += 1;
				}
				if (flagContainer == (returnChosenArrayList().size()) && flagStr == str.length) {
					String elementID = List_contents.get(i).getAttribute("id");
					String expectedElementXpath = ".//*[@id='" + elementID + "']/td[3]/a";
					WebElement expectedElement = driver.findElement(By.xpath(expectedElementXpath));
					waitTillElementIsClickable(expectedElementXpath, "xpath");
					expectedElement.click();
					result = Constants.KEYWORD_PASS;
					break;
				} else
					result = Constants.KEYWORD_FAIL + "Text does not found";
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// Clear a given arrayList(String). Pass the name of arrayList in object(OR
	// properties)
	public String clearExpectedArrayList(String object, String data) {
	
		System.out.println("Emptying the given arrayList...");
		try {
			returnChosenArrayList().clear();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find arrayList " + e.getMessage();
		}
	}
	
	// Method will verify a link text in tabel based on a unique text of row.
	// Pass unique text and text to be verified from test data.
	public String verifyLinkTextInTabelByID(String object, String data) {
	
		System.out.println("Verifying the link text");
		int flag = 0;
		try {
			String str[] = object.split(Constants.DATA_SPLIT);
			WebElement elementList = driver.findElement(By.id(str[0]));
			List<WebElement> List_contents = elementList.findElements(By.xpath("//a[starts-with(@id,'" + str[1] + "')]"));
			System.out.println("Comparing results of table");
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				for (int j = 0; j < returnChosenArrayList().size(); j++) {
					// System.out.println("arraylist"
					// + returnChosenArrayList().get(j));
					// System.out.println("data" + data);
					// System.out.println("actual" + actual);
					if (actual.contains(returnChosenArrayList().get(j)) && actual.contains(data)) {
						flag = flag + 1;
						// System.out.println("I am in if");
					} else {
						flag = 0;
						// System.out.println("I am in else");
					}
				}
				if (flag == returnChosenArrayList().size()) {
					String elementID = List_contents.get(i).getAttribute("id");
					driver.findElement(By.id(elementID)).click();
					return Constants.KEYWORD_PASS;
				} else {
					continue;
				}
			}
			return Constants.KEYWORD_FAIL + " Unable to find link text ";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find link " + e.getMessage();
		}
	}
	
	// The method writes a particular value saved in arrayList by providing row
	// number in data
	public String writeParticularValueFromArrayList(String object, String data) {
	
		System.out.println("Writing particular value saved in arrayList");
		try {
			String str[] = data.split(Constants.DATA_SPLIT);
			for (int strIndex = 0; strIndex < str.length; strIndex++) {
				driver.findElement(By.id(object)).sendKeys(returnChosenArrayList().get(Integer.parseInt(str[strIndex])) + " ");
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method searches for a row by comparing data provided in excel sheet
	// and container arrayList and returns id of the row.
	// This method can be called by another method to perform actions with id of
	// the row returned
	public String searchTableRowByDataAndArrayList(String object, String data) {
	
		System.out.println("Searching in the table rows");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			List<WebElement> List_contents = driver.findElements(By.xpath("//*[@id='" + object + "']/tbody/tr"));
			waitTillVisibilityOfElementList(List_contents);
			for (int i = 0; i < List_contents.size(); i++) {
				List<WebElement> Column = List_contents.get(i).findElements(By.tagName("td"));
				int flagStr = 0;
				int flagContainer = 0;
				for (int expectedArr = 0; expectedArr < returnChosenArrayList().size(); expectedArr++) {
					if (returnChosenArrayList().size() == 0)
						break;
					else {
						for (int columnListIndex = 0; columnListIndex < Column.size(); columnListIndex++) {
							if (Column.get(columnListIndex).getText().trim().toLowerCase()
							        .contentEquals(returnChosenArrayList().get(expectedArr).trim().toLowerCase())) {
								flagContainer += 1;
								break;
							}
						}
					}
				}
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					if (str.length == 0)
						break;
					else {
						for (int columnListIndex = 0; columnListIndex < Column.size(); columnListIndex++) {
							if (Column.get(columnListIndex).getText().trim().toLowerCase().contentEquals(str[expectedArr].trim().toLowerCase())) {
								flagStr += 1;
								break;
							}
						}
					}
				}
				if (flagContainer == returnChosenArrayList().size() && flagStr == str.length) {
					String elementID = List_contents.get(i).getAttribute("id");
					result = elementID;
					break;
				} else
					result = Constants.KEYWORD_FAIL + "Matching row does not found";
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The method searches for a filtered row by comparing data provided in
	// excel sheet and container arrayList and returns id of the row.
	// This method can be called by another method to perform actions with id of
	// the row returned
	public String searchFilteredTableRowByDataAndArrayList(String object, String data) {
	
		System.out.println("Searching in the filtered table");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			WebElement Table = driver.findElement(By.xpath(".//*[@id='" + object + "']"));
			List<WebElement> List_contents = Table.findElements(By.xpath("//tr[contains(@style,'block')]"));
			waitTillVisibilityOfElementList(List_contents);
			for (int i = 0; i < List_contents.size(); i++) {
				List<WebElement> Column = List_contents.get(i).findElements(By.tagName("td"));
				int flagStr = 0;
				int flagContainer = 0;
				for (int expectedArr = 0; expectedArr < returnChosenArrayList().size(); expectedArr++) {
					if (returnChosenArrayList().size() == 0)
						break;
					else {
						for (int columnListIndex = 0; columnListIndex < Column.size(); columnListIndex++) {
							if (Column.get(columnListIndex).getText().trim().toLowerCase()
							        .contentEquals(returnChosenArrayList().get(expectedArr).trim().toLowerCase())) {
								flagContainer += 1;
								break;
							}
						}
					}
				}
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					if (str.length == 0)
						break;
					else {
						for (int columnListIndex = 0; columnListIndex < Column.size(); columnListIndex++) {
							if (Column.get(columnListIndex).getText().trim().toLowerCase().contentEquals(str[expectedArr].trim().toLowerCase())) {
								flagStr += 1;
								break;
							}
						}
					}
				}
				if (flagContainer == returnChosenArrayList().size() && flagStr == str.length) {
					String elementID = List_contents.get(i).getAttribute("id");
					result = elementID;
					break;
				} else
					result = Constants.KEYWORD_FAIL + "Matching row does not found";
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The method searches for a row by comparing data provided in excel sheet
	// and container arrayList (contains)and returns id of the row.
	// This method can be called by another method to perform actions with id of
	// the row returned
	public String searchTableRowContainsByDataAndArrayList(String object, String data) {
	
		System.out.println("Searching the table contents");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			Thread.sleep(5000L);
			List<WebElement> List_contents = driver.findElements(By.xpath("//*[@id='" + object + "']/tbody/tr"));
			waitTillVisibilityOfElementList(List_contents);
			for (int i = 0; i < List_contents.size(); i++) {
				int flagContainer = 0;
				int flagStr = 0;
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < returnChosenArrayList().size(); expectedArr++) {
					if (returnChosenArrayList().size() == 0)
						break;
					else {
						if (actual.trim().toLowerCase().contains(returnChosenArrayList().get(expectedArr).trim().toLowerCase()))
							flagContainer += 1;
					}
				}
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					if (str.length == 0)
						break;
					else {
						if (actual.trim().toLowerCase().contains(str[expectedArr].trim().toLowerCase()))
							flagStr += 1;
					}
				}
				if (flagContainer == returnChosenArrayList().size() && flagStr == str.length) {
					String elementID = List_contents.get(i).getAttribute("id");
					result = elementID;
					break;
				}
				result = Constants.KEYWORD_FAIL + "Matching row does not found";
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The method clears a global variable--ExpectedArrayList
	public String clearExpectedArrayListGlobalVar(String object, String data) {
	
		System.out.println("Clearing values of global variable");
		try {
			ExpectedArrayList = null;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to clear " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The keyword below clicks on row (contains)by providing row content only
	public String storeColumnValuesOfRowInArrayList(String object, String data) {
	
		System.out.println("Storing values of the specified row in arraylist");
		try {
			String rowIdentifier = data.split(",")[0];
			String[] ColumnIdentifier = data.split(",")[1].split(Constants.DATA_SPLIT);
			String elementID = searchTableRowContainsByData(object, rowIdentifier);
			WebElement expectedElement = driver.findElement(By.id(elementID));
			waitTillElementIsClickable(elementID, "id");
			List<WebElement> ColumnList = expectedElement.findElements(By.tagName("td"));
			for (int DataColumnIndex = 0; DataColumnIndex < ColumnIdentifier.length; DataColumnIndex++) {
				if (ColumnIdentifier.length == 0)
					break;
				else {
					String DataToAdd = ColumnList.get(Integer.parseInt(ColumnIdentifier[DataColumnIndex]) - 1).getText();
					returnChosenArrayList().add(DataToAdd);
				}
			}
			expectedElement.click();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The method verifies if a row is present in a table by providing row data
	// in data shet
	public String verifyMultipleTextByID(String object, String data) throws IOException {
	
		System.out.println("Verifying Text");
		try {
			int flag = 0;
			String str[] = data.split(Constants.DATA_SPLIT);
			String expected = null;
			String actual = null;
			List<WebElement> title = driver.findElements(By.id(object));
			System.out.println("Actual ElementList Size: " + title.size());
			System.out.println("Expected ElementList Size: " + str.length);
			for (int i = 0; i < title.size(); i++) {
				// int flagFail = 1;
				for (int j = 0; j < str.length; j++) {
					expected = str[j];
					actual = title.get(i).getText();
					if (actual.trim().toLowerCase().contains(expected.trim().toLowerCase())) {
						// System.out.println("expected"
						// + expected.trim().toLowerCase());
						// System.out.println("actual"
						// + actual.trim().toLowerCase());
						flag += 1;
						// flagFail = 0;
						break;
					} else {
						// System.out.println(expected.trim().toLowerCase());
						// System.out.println(actual.trim().toLowerCase());
					}
				}
			}
			if (flag == str.length)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + actual + " does not match - " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " not able to match" + e.getMessage();
		}
	}
	
	// Method will expand tree upto given level. Provide fragment type dropdown
	// id in object and pass title, main node name and level name up to which
	// you want to expand in test data
	public String expandTreeNode(String object, String data) {
	
		System.out.println("Expanding tree");
		try {
			frgtype = "ABC";
			String arr[] = data.split(Constants.DATA_SPLIT);
			String str = driver.findElement(By.xpath("//*[contains(@" + arr[0] + ", '" + arr[1] + "')]")).getAttribute("href");
			nodeID = Long.parseLong(str.replaceAll("\\D+", ""));
			String tocFrame = "documentContainer|documentContent|authoringtab|addNewARauthContent|toc";
			while (!(frgtype.equals(arr[2]))) {
				if (frgtype == "Level Not Found") {
					return Constants.KEYWORD_FAIL + " Not able to Find Level";
				} else {
					switchBackToWindow(object, data);
					switchToFrameByID(tocFrame, data);
					frgtype = clickTreeNode(object, data);
				}
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to expand tree" + e.getMessage();
		}
	}
	
	// The method clicks on a particular tree node by providing source and
	// destination fragment title in data sheet separated by pipeline
	public String clickTreeNode(String object, String data) {
	
		System.out.println("Clicking nodes");
		try {
			if (driver.findElements(By.xpath(".//*[@id='" + nodeID + "']/div/img")).size() == 0) {
				frgtype = "Level Not Found";
			} else {
				driver.findElement(By.xpath(".//*[@id='" + nodeID + "']/div/img")).click();
				String name = driver.findElement(By.xpath(".//*[@id='" + nodeID + "']/div/a")).getAttribute("title");
				WebElement ele = driver.findElement(By.xpath("//*[contains(@title,'" + name + "')]"));
				JavascriptExecutor executr = (JavascriptExecutor) driver;
				executr.executeScript("arguments[0].click();", ele);
				frgtype = getFragmentType(object, data);
			}
			return frgtype;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click on nodes" + e.getMessage();
		}
	}
	
	// The fragment is used to get the fragment type and switches to a
	// particular frame
	public String getFragmentType(String object, String data) {
	
		System.out.println("Finding Fragment Type");
		try {
			String airlineRevisionFrame = "documentContainer|documentContent|authoringtab|addNewARauthContent";
			switchBackToWindow(object, data);
			switchToFrameByID(airlineRevisionFrame, data);
			frgtype = driver.findElement(By.id(object)).getText();
			nodeID = nodeID + 10;
			return frgtype;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find fragment type " + e.getMessage();
		}
	}
	
	// To Accept a simple alert
	public String acceptAlertAndVerifyAlertText(String object, String data) {
	
		System.out.println("Acccepting the browser alert");
		try {
			Alert alert = driver.switchTo().alert();
			String actual = alert.getText();
			alert.accept();
			if (actual.contains(data)) {
				return Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to accept alert";
		}
		return Constants.KEYWORD_FAIL;
	}
	
	// to click on tabel cell, the id should be container Id of table and row
	// which needs to be clicked.
	public String clickTabelContainsCellByID(String object, String data) {
	
		System.out.println("Extracting values of all rows of table");
		String result;
		try {
			result = Constants.KEYWORD_FAIL;
			String str[] = object.split(Constants.DATA_SPLIT);
			WebElement elementList = driver.findElement(By.id(str[0]));
			List<WebElement> List_contents = elementList.findElements(By.xpath("//tr[starts-with(@id,'" + str[1] + "')]"));
			System.out.println("Comparing results of table");
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				if (actual.contains(data)) {
					String elementID = List_contents.get(i).getAttribute("id");
					driver.findElement(By.id(elementID)).click();
					return Constants.KEYWORD_PASS;
				} else {
					continue;
				}
			}
			result = Constants.KEYWORD_FAIL + "Text does not found";
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// To verify an element is exist on a web page based on id
	public String verifyExistByXpath(String object, String data) {
	
		System.out.println("Checking existance of element");
		try {
			if (driver.findElements(By.xpath(object)).size() != 0)
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object does not exist";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To verify an element is not exist on a web page based on id
	public String verifyNotExistByXpath(String object, String data) {
	
		System.out.println("Checking non-existance of element");
		try {
			if (driver.findElements(By.xpath(object)).size() == 0)
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object exist in page";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To get all elements of a page
	public String getAllElements(String object, String data) {
	
		System.out.println("Checking non-existance of element");
		try {
			List<WebElement> el = driver.findElements(By.xpath("*"));
			for (int i = 0; i < el.size(); i++) {
				driver.findElement(By.tagName("input")).sendKeys(System.getProperty("user.dir") + data);
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object exist in page";
		}
	}
	
	// Clear a given arrayList(String). Pass the name of arrayList in object(OR
	// properties)
	public String clearContainerArrayList(String object, String data) {
	
		System.out.println("Emptying the given arrayList...");
		try {
			container.clear();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find arrayList " + e.getMessage();
		}
	}
	
	// The method aits till element state is enabled
	public String waitTillElementStateIsEnabled(String object, String data) {
	
		System.out.println("Waiting till element is clickable" + object);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			if (data.equalsIgnoreCase("id"))
				wait.until(ExpectedConditions.elementSelectionStateToBe(By.id(object), true));
			else if (data.equalsIgnoreCase("css"))
				wait.until(ExpectedConditions.elementSelectionStateToBe(By.cssSelector(object), true));
			else if (data.equalsIgnoreCase("xpath"))
				wait.until(ExpectedConditions.elementSelectionStateToBe(By.xpath(object), true));
			else if (data.equalsIgnoreCase("className"))
				wait.until(ExpectedConditions.elementSelectionStateToBe(By.className(object), true));
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To verify the exact text of an element by xpath
	public String verifyExactTextByXpath(String object, String data) {
	
		System.out.println("Verifying the text");
		try {
			String actual = driver.findElement(By.xpath(object)).getText();
			String expected = data;
			if (actual.trim().equalsIgnoreCase(expected.trim()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified-- " + expected + " -- " + actual;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// Function checking the presence of the alert
	public boolean isAlertPresent(String object, String data) {
	
		System.out.println("Checking the presence of an alert");
		try {
			driver.switchTo().alert();
			System.out.println("Alert is present");
			return true;
		} catch (NotFoundException e) {
			System.out.println("Alert is absent");
			return false;
		}
	}
	
	// Function for checking the content of revision info box
	public String getRevisionInfoContentAndCompare(String object, String data) {
	
		System.out.println("Switching to Information Window");
		try {
			Actions builder = new Actions(driver);
			List<WebElement> rows = driver.findElements(By.xpath("//table[@id='fakeRadioCheckboxes']/tbody/tr"));
			for (int i = 0; i < rows.size(); i++) {
				if (rows.get(i).getText().contains(data)) {
					WebElement row = rows.get(i);
					builder.moveToElement(row.findElement(By.xpath("//table[@id='fakeRadioCheckboxes']/tbody/tr/td[8]/a"))).click();
					Action selMultiple = builder.build();
					selMultiple.perform();
					return Constants.KEYWORD_PASS;
				}
			}
			return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}
	}
	
	// Clear a given arrayList(String). Pass the name of arrayList in object(OR
	// properties)
	public String clearRetrieverArrayList(String object, String data) {
	
		System.out.println("Emptying the given arrayList...");
		try {
			retriever.clear();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find arrayList " + e.getMessage();
		}
	}
	
	// The method stores text of an element to a global variable--storeVar
	public String storeTextIntoVarByXPath(String object, String data) {
	
		System.out.println("Storing the text from input box into arraylist");
		try {
			storeVar = driver.findElement(By.xpath(object)).getText();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();
		}
	}
	
	// To write text in the text box by id
	public String writeTextStoreInGlobalVarByID(String object, String data) {
	
		System.out.println("Writing in text box");
		try {
			driver.findElement(By.id(object)).click();
			driver.findElement(By.id(object)).clear();
			driver.findElement(By.id(object)).sendKeys(storeVar);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To Click an element on a page by its text
	public String clickByLinkTextStoredInGlobalVar(String object, String data) {
	
		System.out.println("Clicking on link by its text ");
		try {
			driver.findElement(By.linkText(storeVar)).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method verifies Data in table of content
	public String verifyARDataStoredInGlobarVarInTOC(String object, String data) {
	
		System.out.println("Verifying the Global Variable contents");
		try {
			String arr[] = storeVar.split(Constants.DATA_SPLIT);
			Long nodeID1 = 0L;
			String result = null;
			String xp = "//*[contains(@title, '" + arr[0] + "')]";
			waitTillElementIsVisible(xp, "xpath");
			List<WebElement> ElementList = driver.findElements(By.xpath("//*[contains(@title, '" + arr[0] + "')]"));
			for (int ElementIndex = 0; ElementIndex < ElementList.size(); ElementIndex++) {
				String str = ElementList.get(ElementIndex).getAttribute("href");
				nodeID1 = Long.parseLong(str.replaceAll("\\D+", ""));
				String ElementSrc = driver.findElement(By.xpath(".//*[@id='" + nodeID1 + "']/div/img[3]")).getAttribute("src");
				if (ElementSrc.contains("ar")) {
					driver.findElement(By.xpath(".//*[@id='" + nodeID1 + "']/div/img[3]")).click();
					String color = ElementList.get(ElementIndex).getAttribute("style");
					if (color.contains("yellow")) {
						result += Constants.KEYWORD_PASS;
					} else
						result += Constants.KEYWORD_FAIL;
				} else
					result += Constants.KEYWORD_FAIL;
			}
			if (result.contains(Constants.KEYWORD_FAIL))
				return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The mehtod clicks on a checkbox corresponding to a particular row by
	// proving row data and column number in data sheet
	public String checkDocCheckboxByXpathNew(String object, String data) {
	
		System.out.println("Clicking on the check box");
		try {
			String result = null;
			String arr[] = data.split(";");
			String str[] = arr[0].split(Constants.DATA_SPLIT);
			Thread.sleep(5000L);
			List<WebElement> List_contents = driver.findElements(By.xpath(object + "/td[" + arr[2] + "]"));
			List_contents.clear();
			List<WebElement> tdList = driver.findElements(By.xpath(object + "/td[" + arr[2] + "]"));
			waitTillVisibilityOfElementList(tdList);
			System.out.println("Comparing results of table");
			for (int j = 0; j < tdList.size(); j++) {
				if (tdList.get(j).getText().equals(arr[1])) {
					System.out.println(tdList.size() + "Arr" + arr[1]);
					List_contents.add(driver.findElement(By.xpath("//table/tbody/tr[" + (j + 1) + "]")));
					System.out.println("Got the element" + (j + 1));
				}
			}
			for (int i = 0; i < List_contents.size(); i++) {
				int flag = 0;
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					System.out.println("lENGTH:" + actual.trim().toLowerCase());
					if (actual.trim().toLowerCase().contains(str[expectedArr].trim().toLowerCase())) {
						flag += 1;
					}
				}
				System.out.println("lENGTH:" + str.length + "fLAG:" + flag);
				if (flag == (str.length)) {
					System.out.println("Inside the if block" + str.length);
					String elementID = List_contents.get(i).getAttribute("id");
					System.out.println("Goth the id" + elementID);
					WebElement ele = driver.findElement(By.xpath("//input[contains(@id, '" + elementID.substring(3) + "')]"));
					JavascriptExecutor executr = (JavascriptExecutor) driver;
					executr.executeScript("arguments[0].click();", ele);
					result = Constants.KEYWORD_PASS;
					break;
				} else if (!(flag == str.length)) {
					result = Constants.KEYWORD_FAIL + "Text does not found";
				}
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	public String checkDocCheckboxByXpath(String object, String data) {
	
		System.out.println("Clicking on the check box");
		try {
			String result = null;
			String arr[] = data.split(";");
			String str[] = arr[0].split(Constants.DATA_SPLIT);
			Thread.sleep(5000L);
			List<WebElement> List_contents = driver.findElements(By.xpath(object + "/td[" + arr[2] + "]"));
			List_contents.clear();
			List<WebElement> tdList = driver.findElements(By.xpath(object + "/td[" + arr[2] + "]"));
			waitTillVisibilityOfElementList(tdList);
			System.out.println("Comparing results of table");
			for (int j = 0; j < tdList.size(); j++) {
				if (tdList.get(j).getText().equals(arr[1])) {
					System.out.println(tdList.size() + "Arr" + arr[1]);
					List_contents.add(driver.findElement(By.xpath("//table/tbody/tr[contains(@id,'tr_ckbx_" + (j + 1) + "')]")));
					System.out.println("Got the element" + (j + 1));
				}
			}
			for (int i = 0; i < List_contents.size(); i++) {
				int flag = 0;
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					System.out.println("lENGTH:" + actual.trim().toLowerCase());
					if (actual.trim().toLowerCase().contains(str[expectedArr].trim().toLowerCase())) {
						flag += 1;
					}
				}
				System.out.println("lENGTH:" + str.length + "fLAG:" + flag);
				if (flag == (str.length)) {
					System.out.println("Inside the if block" + str.length);
					String elementID = List_contents.get(i).getAttribute("id");
					System.out.println("Goth the id" + elementID);
					WebElement ele = driver.findElement(By.xpath("//input[contains(@id, '" + elementID.substring(3) + "')]"));
					JavascriptExecutor executr = (JavascriptExecutor) driver;
					executr.executeScript("arguments[0].click();", ele);
					result = Constants.KEYWORD_PASS;
					break;
				} else if (!(flag == str.length)) {
					result = Constants.KEYWORD_FAIL + "Text does not found";
				}
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// function for selecting a category from the list by pasiing the category
	// title as input
	public String selectCategoryByTitle(String object, String data) {
	
		System.out.println("Selecting the category by given title");
		try {
			WebElement ele = driver.findElement(By.xpath("//*[contains(@" + object + ", '" + data + "')]"));
			String var = ele.getAttribute("href");
			String categoryID[] = var.split(",");
			Long nodeID1 = Long.parseLong(categoryID[1].replaceAll("\\D+", ""));
			JavascriptExecutor executr = (JavascriptExecutor) driver;
			executr.executeScript("openDocList('/toolbox_arc/library/libraryOpenDocList.html?categoryId="
			        + nodeID1
			        + "&amp;docType=-docType&amp;docNum=-docNum&amp;owner=-owner&amp;models=-models&amp;revDate=-revDate&amp;revNum=-revNum&amp;src=-src', '"
			        + nodeID1 + "', '0', 'false');");
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method searches for a row by comparing data provided in excel sheet
	// and container arrayList (contains)and returns id of the row.
	// This method can be called by another method to perform actions with id of
	// the row returned
	public String verifyTableRowContainsByArrayList(String object, String data) {
	
		System.out.println("Extracting values of all rows of table");
		try {
			List<WebElement> List_contents = driver.findElements(By.xpath("//*[@id='" + object + "']/tbody/tr"));
			waitTillVisibilityOfElementList(List_contents);
			int flagContainer = 0;
			int flagStr = 0;
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < returnChosenArrayList().size(); expectedArr++) {
					if (returnChosenArrayList().size() == 0)
						break;
					else {
						if (actual.trim().toLowerCase().contains(returnChosenArrayList().get(expectedArr).trim().toLowerCase()))
							flagContainer += 1;
						else
							flagContainer = 0;
					}
				}
				flagStr += 1;
			}
			if (flagContainer > 0 && flagStr == List_contents.size()) {
				return Constants.KEYWORD_PASS;
			} else
				return Constants.KEYWORD_FAIL + "Matching row does not found";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// function for selecting an option from the drop down list
	public String selectDataListByID(String object, String data) {
	
		System.out.println("Selecting from list");
		try {
			String arr[] = data.split(Constants.DATA_SPLIT);
			Select droplist = new Select(driver.findElement(By.id(object)));
			droplist.selectByVisibleText(arr[0]);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To verify the title of a page by Class
	public String verifyPageTitleByID(String object, String data) {
	
		System.out.println("Verifying the page name");
		try {
			List<WebElement> pagenames = driver.findElements(By.id(object));
			waitTillVisibilityOfElementList(pagenames);
			String str[] = data.split(Constants.DATA_SPLIT);
			int index = Integer.parseInt(str[1]);
			String actual = pagenames.get(index).getText();
			String expected = str[0];
			if (actual.trim().toLowerCase().equals(expected.trim().toLowerCase()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " --Window name not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// To do a simple click on an element by its xpath
	public String verifyContainsTextStoredInGlobalVarByTagName(String object, String data) {
	
		System.out.println("Verifying the global variable contents");
		try {
			String fragmentTitle = storeVar.split(" ")[0];
			String actual = driver.findElement(By.tagName(object)).getText();
			if (actual.toLowerCase().trim().contains(fragmentTitle.toLowerCase().trim())) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " Text does not match";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to find text" + e.getMessage();
		}
	}
	
	// To do a simple click on an element by its xpath
	public String clickByClass(String object, String data) {
	
		System.out.println("Clicking on the element");
		try {
			driver.findElement(By.className(object)).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// function for checking the fragment status of airline revision
	public String checkNotification(String object, String data) {
	
		System.out.println("Checking the Notification content");
		try {
			String[] temp_arr = data.split(Constants.DATA_SPLIT);
			String text = temp_arr[0];
			String check = temp_arr[1];
			String rowText = driver.findElement(By.xpath(object)).getText();
			if (check.equalsIgnoreCase("true")) {
				if (rowText.contains(text)) {
					System.out.println("Text is present");
					return Constants.KEYWORD_PASS;
				} else {
					System.out.println("Text is absent");
					return Constants.KEYWORD_FAIL;
				}
			} else {
				if (!(rowText.contains(text))) {
					System.out.println("Text is absent");
					return Constants.KEYWORD_PASS;
				} else {
					System.out.println("Text is present");
					return Constants.KEYWORD_FAIL;
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
	}
	
	// function for storing the text into a global variable
	public String storeTextIntoVarByXpath(String object, String data) {
	
		try {
			toc = driver.findElement(By.xpath(object)).getText();;
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("TOC Not found : " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	// Function checking whether the provided text is highlighted or not
	public String checkHighlightedTextByXpath(String object, String data) {
	
		try {
			System.out.println("Checking the Text status");
			object = object + data + "']";
			System.out.println("Finding the element with xpath" + object);
			if (driver.findElement(By.xpath(object)).getAttribute("style").contains("BACKGROUND-COLOR: yellow")) {
				System.out.println("Text is highlighted");
				return Constants.KEYWORD_PASS;
			}
			System.out.println("Checking Failed with data " + data + " " + object);
			return Constants.KEYWORD_FAIL + "Text not Highlighted";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}
	
	// Function clicking on the link text provided by java script executer
	public String clickUsingJSByLinkText(String object, String data) {
	
		System.out.println("Clicking on the link :" + data);
		try {
			WebElement element = driver.findElement(By.linkText(data));
			JavascriptExecutor executr = (JavascriptExecutor) driver;
			executr.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// Function verifying the absence of the document
	public String verifyAbsenceOfRevision(String data, String object) {
	
		try {
			System.out.println("Checking the absence");
			String message = verifyTextInTabelByID(data, object);
			if (message.contains(Constants.KEYWORD_FAIL)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			System.out.println("Verification Error");
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}
	
	// function for passing an integer into another function
	public String passIntToFunction(String data, String object) {
	
		System.out.println("Passing integer to the function");
		try {
			data = "";
			String message = writeIntegerByID(data, object);
			if (message.contains(Constants.KEYWORD_PASS)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}
	}
	
	// function for verifying the absence of a document
	public String verifyAbsenceOfDocument(String data, String object) {
	
		System.out.println("Verifying the document");
		try {
			String message = clickIntegerLink(data, object);
			if (message.contains(Constants.KEYWORD_FAIL)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}
	}
	
	// The method verifies if DS is present corresponding to a particular
	// fragment in table of content
	public String verifyListItemsDSInTOC(String object, String data) {
	
		System.out.println("Extracting values of all rows of table");
		try {
			long nodeID = 0L;
			String result = null;
			WebElement Tree = driver.findElement(By.id(object));
			List<WebElement> ElementList = null;
			if (!(data.isEmpty())) {
				ElementList = Tree.findElements(By.xpath("//*[contains(@title, '" + data + "')]"));
			}
			for (int ElementIndex = 0; ElementIndex < ElementList.size(); ElementIndex++) {
				String str = ElementList.get(ElementIndex).getAttribute("href");
				nodeID = Long.parseLong(str.replaceAll("\\D+", ""));
				if (driver.findElement(By.xpath(".//*[@id='" + nodeID + "']/div/a")).getAttribute("style").contains("yellow")) {
					String ElementSrc1 = driver.findElement(By.xpath(".//*[@id='" + nodeID + "']/div/img[3]")).getAttribute("src");
					if (ElementSrc1.contains("ds")) {
						result = Constants.KEYWORD_PASS;
						break;
					}
				}
			}
			if (result.equals(Constants.KEYWORD_PASS))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " No such element found";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// function for clicking a particular tree node
	public String clickParticularTreeNode(String object, String data) {
	
		System.out.println("Expanding tree");
		try {
			String arr[] = data.split(Constants.DATA_SPLIT);
			long nodeID = 0L;
			WebElement Element = driver.findElement(By.xpath("//*[contains(@" + arr[0] + ", '" + arr[1] + "')]"));
			String str = Element.getAttribute("href");
			nodeID = Long.parseLong(str.replaceAll("\\D+", ""));
			if (Element.getAttribute("title").contains(arr[2])) {
				driver.findElement(By.xpath(".//*[@id='" + nodeID + "']/div/img[2]")).click();
			} else
				clickTreeNodeDS(nodeID, arr[2]);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to expand tree" + e.getMessage();
		}
	}
	
	// function for selecting a tree node in DS section
	public String clickTreeNodeDS(long nodeID, String data) {
	
		System.out.println("Clicking nodes");
		try {
			long BaseNodeID = nodeID;
			JavascriptExecutor executr = (JavascriptExecutor) driver;
			do {
				driver.findElement(By.xpath(".//*[@id='" + BaseNodeID + "']/div/img[1]")).click();
				BaseNodeID += 10;
				String name = driver.findElement(By.xpath(".//*[@id='" + BaseNodeID + "']/div/a")).getAttribute("title");
				if (name.trim().contains(data))
					executr.executeScript("arguments[0].click();", driver.findElement(By.xpath(".//*[@id='" + BaseNodeID + "']/div/img[2]")));
			} while (!(driver.findElement(By.xpath(".//*[@id='" + BaseNodeID + "']/div/a")).getAttribute("title").trim().contains(data)));
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click on nodes" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method calculates effectivity range and write at the specified
	// location by id
	public String calculateEffectiveRange(String object, String data) {
	
		System.out.println("Writing effective code");
		try {
			String EffectivityCode = null;
			String preText = null;
			int FirstNum = (int) (100 + (Math.random() * 99));
			String str[] = data.split(Constants.DATA_SPLIT);
			if (str[1].equals(null))
				preText = "";
			else
				preText = str[1];
			if (str[0].equalsIgnoreCase("Single Range"))
				EffectivityCode = preText + Integer.toString(FirstNum) + preText + Integer.toString(FirstNum + 10);
			else if (str[0].equalsIgnoreCase("Multiple Range"))
				EffectivityCode = preText + Integer.toString(FirstNum) + preText + Integer.toString(FirstNum + 10) + " " + preText
				        + Integer.toString(FirstNum + 20) + preText + Integer.toString(FirstNum + 30);
			driver.findElement(By.id(object)).clear();
			driver.findElement(By.id(object)).sendKeys(EffectivityCode);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The keyword presses one of the following key from keyboard- TAB-(Enter 1
	// in datasheet), ALT(Enter 2 in datasheet), SAPCE(Enter 3 in datasheet),
	// ENTER(Enter 4 in datasheet), CTRL(Enter 5 in datasheet).
	// Specifiy the number of times you wish to enter the key seperated by
	// Pipeline in data sheet.
	public String clickReleaseParticularKeyUsingRobot(String nodeID, String data) {
	
		System.out.println("Clicking the specified key");
		try {
			int DesiredKey = Integer.parseInt(data.split(Constants.DATA_SPLIT)[0]);
			int KeyTimesPress = Integer.parseInt(data.split(Constants.DATA_SPLIT)[1]);
			Robot robot = new Robot();
			for (int KeyPressIteration = 0; KeyPressIteration < KeyTimesPress; KeyPressIteration++) {
				switch (DesiredKey) {
					case 1:
						robot.keyPress(java.awt.event.KeyEvent.VK_TAB);
						robot.keyRelease(java.awt.event.KeyEvent.VK_TAB);
						break;
					case 2:
						robot.keyPress(java.awt.event.KeyEvent.ALT_DOWN_MASK);
						robot.keyRelease(java.awt.event.KeyEvent.ALT_DOWN_MASK);
						break;
					case 3:
						robot.keyPress(java.awt.event.KeyEvent.VK_SPACE);
						robot.keyRelease(java.awt.event.KeyEvent.VK_SPACE);
						break;
					case 4:
						robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);
						robot.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
						break;
					case 5:
						robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
						robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
						break;
					default:
						System.out.println("Wrong choice");
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The keyword below clicks on a partial link text contained in
	public String clickSpecificPartialLinkTextContainedInArrayList(String object, String data) {
	
		System.out.println("Clicking on partial link contained in arrayList");
		try {
			String str = data.split("k")[0];
			driver.findElement(By.partialLinkText(returnChosenArrayList().get(Integer.parseInt(str)))).click();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The method clicks on a element using Javascript by xpath
	public String clickUsingJSByXpath(String object, String data) {
	
		System.out.println("Clicking on any element");
		try {
			WebElement ele = driver.findElement(By.xpath(object));
			JavascriptExecutor executr = (JavascriptExecutor) driver;
			executr.executeScript("arguments[0].click();", ele);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// This method switches to a window particular window with title passed in
	// data
	public String switchToParticularWindow(String object, String data) {
	
		System.out.println("Switching to new window");
		try {
			int CurrentNumberOfHandles = driver.getWindowHandles().size();
			if (CurrentNumberOfHandles > InitialNumberOfHandles) {
				ArrayList<String> windowHandleList = new ArrayList<String>(driver.getWindowHandles());
				for (int ListIndex = 0; ListIndex < windowHandleList.size(); ListIndex++) {
					if (windowHandleList.get(ListIndex).equals(Constants.WINDOW_HANDLE))
						windowHandleList.remove(ListIndex);
				}
				String WindowTitleToSwitch = data;
				String WindowHandleToSwitch = null;
				for (int ListIndex = 0; ListIndex < windowHandleList.size(); ListIndex++) {
					WebDriver IntermediateWindow = driver.switchTo().window(windowHandleList.get(ListIndex));
					if (IntermediateWindow.getCurrentUrl().contains(WindowTitleToSwitch))
						WindowHandleToSwitch = IntermediateWindow.getWindowHandle();
					else
						IntermediateWindow.close();
				}
				driver.switchTo().window(WindowHandleToSwitch);
			}
		} catch (Exception e) {
			System.out.println("exception caught :  " + e);
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method saves current window handle to to constants- WinHandle
	public String saveWindowHandleToConstant(String object, String data) {
	
		System.out.println("Changing the focus to top window ");
		try {
			String Handle = driver.getWindowHandle();
			Constants.WINDOW_HANDLE = Handle;
			InitialNumberOfHandles = driver.getWindowHandles().size();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to changefocus - " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// Method to close the window
	public String closeWindow(String object, String data) {
	
		System.out.println("Closing the browser");
		try {
			driver.close();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to close window. Check if its open" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// This method click on a link text saved in arrayList by providing row
	// number in data
	public String clickLinkTextValueFromArrayList(String object, String data) {
	
		System.out.println("Clicking on link saved in arrayList ");
		try {
			String str[] = data.split(Constants.DATA_SPLIT);
			for (int strIndex = 0; strIndex < str.length; strIndex++) {
				WebElement ele = driver.findElement(By.linkText(returnChosenArrayList().get(Integer.parseInt(str[strIndex]))));
				JavascriptExecutor executr = (JavascriptExecutor) driver;
				executr.executeScript("arguments[0].click();", ele);
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// function for clicking on a check box by given xpath
	public String clickCheckboxSearchDocByXpath(String object, String data) {
	
		System.out.println("Clicking on check box");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			String arr[] = object.split(Constants.DATA_SPLIT);
			int flag = 0;
			List<WebElement> List_contents = driver.findElements(By.xpath("//" + arr[0] + "[" + arr[1] + "(@" + arr[2] + ", '" + arr[3]
			        + "')]/table/tbody/tr"));
			waitTillVisibilityOfElementList(List_contents);
			System.out.println("Comparing results of table");
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					if (actual.trim().toLowerCase().contains(str[expectedArr].trim().toLowerCase()))
						flag = expectedArr;
					else
						flag = -1;
				}
				if (flag == (str.length - 1)) {
					String elementID = List_contents.get(i).getAttribute("id");
					WebElement ele = driver.findElement(By.xpath("//input[contains(@id, '" + elementID.substring(3) + "')]"));
					JavascriptExecutor executr = (JavascriptExecutor) driver;
					executr.executeScript("arguments[0].click();", ele);
					result = Constants.KEYWORD_PASS;
					break;
				} else if (flag == -1) {
					result = Constants.KEYWORD_FAIL + "Text does not found";
				}
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// Method to close the window
	public String closeOtherWindow(String object, String data) {
	
		System.out.println("Closing the browser");
		try {
			if (!(driver.getWindowHandle().equals(Constants.WINDOW_HANDLE)))
				driver.close();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to close window. Check if its open" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// Method to close the window
	public String getCurrentURL(String object, String data) {
	
		System.out.println("Closing the browser");
		try {
			System.out.println("curremnt URL : " + driver.getCurrentUrl());
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to close window. Check if its open" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// This method click on a link text saved in arrayList by providing row
	// number in data
	public String verifyTextStoredInArrayListOnPageSource(String object, String data) {
	
		System.out.println("Clicking on link saved in arrayList ");
		try {
			int flag = 0;
			String str[] = data.split(Constants.DATA_SPLIT);
			for (int strIndex = 0; strIndex < str.length; strIndex++) {
				String ArrayListContent = returnChosenArrayList().get(Integer.parseInt(str[strIndex]));
				if (driver.getPageSource().contains(ArrayListContent))
					flag += 1;
			}
			if (flag == str.length)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
	}
	
	// function for storing a text into a global variable by given xpath
	public String storeTextByxPath(String object, String data) {
	
		System.out.println("Storing the text from input box into arraylist");
		try {
			returnChosenArrayList().add(driver.findElement(By.xpath(object)).getText());
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/*
	 * //function for verifying the AR in the DS Section public String verifyAR_DSInTOCFromArrayList(String
	 * object, String data) { System.out.println("Verifying the contents"); try { long nodeID = 0L; String
	 * result = null; WebElement Tree = driver.findElement(By.id(object)); String TitleText = ""; for (int
	 * arrayListIndex = 0; arrayListIndex < returnChosenArrayList().size(); arrayListIndex++) TitleText += " "
	 * + returnChosenArrayList().get(arrayListIndex); List < WebElement > ElementList =
	 * Tree.findElements(By.xpath("//*[contains(@title, '" + TitleText.trim())); for (int ElementIndex = 0;
	 * ElementIndex < ElementList.size(); ElementIndex++) { String str =
	 * ElementList.get(ElementIndex).getAttribute("href"); nodeID = Long.parseLong(str.replaceAll("\\D+",
	 * "")); if (driver.findElement(By.xpath(".//*[@id='" + nodeID +
	 * "']/div/a")).getAttribute("style").contains("yellow")) { String ElementSrc1 =
	 * driver.findElement(By.xpath(".//*[@id='" + nodeID + "']/div/img[3]")).getAttribute("src"); String
	 * ElementSrc2 = driver.findElement(By.xpath(".//*[@id='" + nodeID + "']/div/img4]")).getAttribute("src");
	 * if (ElementSrc1.contains("ar") && ElementSrc2.contains("ds")) { result = Constants.KEYWORD_PASS; break;
	 * } } } if (result.equals(Constants.KEYWORD_PASS)) return Constants.KEYWORD_PASS; else return
	 * Constants.KEYWORD_FAIL + " No such element found"; } catch (Exception e) { return
	 * Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage(); } }
	 */
	// function for verifying the given element by given xpath
	public String verifyListElementsByXpath(String object, String data) {
	
		System.out.println("Verifying the list of elements");
		try {
			int flag = 0;
			List<WebElement> tdList = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(tdList);
			System.out.println("Comparing results of table");
			for (int j = 0; j < tdList.size(); j++) {
				if (data.contains(tdList.get(j).getText())) {
					flag = flag + 1;
				} else {
					flag = 0;
				}
			}
			if (flag == tdList.size())
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// function for transfering the selected data into a global variable
	public String transferDataIntoGlobalVar(String object, String data) {
	
		System.out.println("Storing the text from input box into arraylist");
		try {
			storeVar = data;
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();
		}
	}
	
	// to verify that certain text contains some specific text
	public String verifyTextContainsByXpath(String object, String data) {
	
		System.out.println("Verifying that labels contains text");
		try {
			String actual = driver.findElement(By.xpath(object)).getText();
			String expected = data;
			System.out.println("expected" + expected);
			System.out.println("actual" + actual);
			if (actual.trim().toLowerCase().contains(expected.trim().toLowerCase())) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " --not contain text " + actual + " -- " + expected;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// The method returns Pass if element on the specified Xpath does not
	// returns any value
	public String verifyTextNotContainsByXpath(String object, String data) {
	
		System.out.println("Verifying that textfied is empty");
		try {
			String result = "";
			if (driver.findElement(By.xpath(object)).getText().isEmpty()) {
				result = Constants.KEYWORD_PASS;
			} else
				result = Constants.KEYWORD_FAIL + " --contains some contain text ";
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// Function clicking on the partial link text provided by java script
	// executer
	public String clickUsingJSByPartialLinkText(String object, String data) {
	
		System.out.println("Clicking on the link :" + data);
		try {
			WebElement element = driver.findElement(By.partialLinkText(data));
			JavascriptExecutor executr = (JavascriptExecutor) driver;
			executr.executeScript("arguments[0].click();", element);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
	}
	
	// function for verifying the search result by given xpath
	public String verifySearchResultsByXpath(String object, String data) {
	
		System.out.println("Checking existance of element");
		try {
			if (driver.findElement(By.xpath(object)).getText().equals(data) || driver.findElement(By.xpath(object)).getText().equals(null))
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To click an image by its xpath
	public String clickImageByXpath(String object, String data) {
	
		System.out.println("Clicking the image");
		try {
			driver.findElement(By.xpath("//img[@src='" + object + "']")).click();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "object not found" + e.getMessage();
		}
	}
	
	// To click an image by its xpath
	public String mouseHover(String object, String data) {
	
		System.out.println("Clicking the image");
		try {
			WebElement element = driver.findElement(By.id(object));
			Actions builder = new Actions(driver);
			// Move cursor to the Main Menu Element
			builder.moveToElement(element).perform();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "object not found" + e.getMessage();
		}
	}
	
	// To verify the exact text of an element by id
	public String verifyExactTextByAttribute(String object, String data) {
	
		System.out.println("Verifying the text");
		try {
			String actual = driver.findElement(By.id(object)).getAttribute("title");
			String expected = data;
			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified-- " + expected + " -- " + actual;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// The method will verify if a particular attribute is present in the
	// element
	public String verifyAttributePresentInElementByID(String object, String data) {
	
		System.out.println("Clicking on the link");
		try {
			String result = "";
			String value = driver.findElement(By.id(object)).getAttribute(data);
			if (value != null) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
	}
	
	// method to wait till an element is present
	public String waitTillElementIsPresent(String object, String data) {
	
		System.out.println("Waiting till the presence of element" + object);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			if (data.equalsIgnoreCase("id"))
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(object)));
			else if (data.equalsIgnoreCase("css"))
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(object)));
			else if (data.equalsIgnoreCase("xpath"))
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(object)));
			else if (data.equalsIgnoreCase("className"))
				wait.until(ExpectedConditions.presenceOfElementLocated(By.className(object)));
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// function for clicking a link by using title and JS
	public String clickByTextUsingJS(String object, String data) {
	
		System.out.println("Clicking on link by its text ");
		try {
			WebElement ele = driver.findElement(By.partialLinkText(data));
			JavascriptExecutor executr = (JavascriptExecutor) driver;
			executr.executeScript("arguments[0].click();", ele);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// To Click an element on a page by its text
	public String clickByExtractedLinkTextStoredInGlobalVar(String object, String data) {
	
		System.out.println("Clicking on link by its text ");
		try {
			List<WebElement> ElementList = driver.findElements(By.xpath(object));
			storeVar = storeVar + ", " + "Fig " + ElementList.size();
			driver.findElement(By.partialLinkText(storeVar)).click();
			String arr[] = storeVar.split(",");
			storeVar = arr[0] + "-" + ElementList.size();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// function for verifying the absence of an element in the givern table
	public String verifyTextNotInTabelByXpath(String object, String data) {
	
		System.out.println("Verifying the absence of an element in the table");
		String result;
		int flag = 0;
		try {
			result = Constants.KEYWORD_FAIL;
			String str[] = object.split(Constants.DATA_SPLIT);
			WebElement elementList = driver.findElement(By.xpath("//tbody[@id='" + str[0] + "']"));
			List<WebElement> List_contents = elementList.findElements(By.xpath("//tr[contains(@class,'" + str[1] + "')]/td[1]"));
			System.out.println("Comparing results of table");
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				if (!(actual.contains(data))) {
					flag = 1;
				} else {
					break;
				}
			}
			if (flag == 1) {
				result = Constants.KEYWORD_PASS + "Text does not found";
			} else {
				result = Constants.KEYWORD_FAIL + "Text found";
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Text found";
		}
	}
	
	// function for verifying the presence of an element in a table by given
	// table column
	public String searchInTableXpathAndColumn(String object, String data) {
	
		System.out.println("Searching in the table by xpath and column");
		try {
			List<WebElement> rows = driver.findElements(By.xpath(object));
			for (int i = 0; i < rows.size(); i++) {
				if (rows.get(i).getText().contains(data)) {
					System.out.println("Data present");
					return Constants.KEYWORD_FAIL;
				}
			}
			System.out.println("Data absent");
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}
	
	// The method clicks on '+' (revision number) in 3rd column of table
	public String clickOnPlusButton(String object, String data) {
	
		System.out.println("Clikcing on the plus button");
		try {
			String elementID = "";
			String str[] = data.split(Constants.DATA_SPLIT);
			WebElement elementList = driver.findElement(By.id(object));
			List<WebElement> List_contents = elementList.findElements(By.xpath("//tr[contains(@" + str[0] + ", '" + str[1] + "')]"));
			System.out.println("Comparing results of table");
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				if (actual.contains(str[2])) {
					elementID = List_contents.get(i).getAttribute("id");
					driver.findElement(By.id(elementID)).click();
					String expectedElementXpath = ".//*[@id='" + elementID + "']/td[3]/div/ul/li/img[1]";
					WebElement expectedElement = driver.findElement(By.xpath(expectedElementXpath));
					waitTillElementIsClickable(expectedElementXpath, "xpath");
					expectedElement.click();
					return Constants.KEYWORD_PASS;
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
		return Constants.KEYWORD_FAIL;
	}
	
	// function for verifying the element presence in the table - duplicated
	public String searchInTableXpathAndColumn2(String object, String data) {
	
		System.out.println("Searching in the table by xpath and column");
		try {
			List<WebElement> rows = driver.findElements(By.xpath(object));
			for (int i = 0; i < rows.size(); i++) {
				if (rows.get(i).getText().contains(data)) {
					System.out.println("Data present");
					return Constants.KEYWORD_PASS;
				}
			}
			System.out.println("Data absent");
			return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}
	
	// function for verifying the absence of an element
	public String verifyAbsenceOfAnElement(String object, String data) {
	
		System.out.println("Verifying the absence of the element");
		try {
			if (driver.findElement(By.id(object)) == null) {
				return Constants.KEYWORD_PASS;
			}
			return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}
	
	// The method verifies if multiple linkText are present(provide in data
	// sepaated by pipeline)
	public String verifyMultipleTextByPartialLinkText(String object, String data) {
	
		System.out.println("Clicking on the link :" + data);
		try {
			String[] LinkText = data.split(Constants.DATA_SPLIT);
			String result = "";
			WebDriverWait wait = new WebDriverWait(driver, 30);
			for (int DataIndex = 0; DataIndex < LinkText.length; DataIndex++) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(LinkText[DataIndex])));
				if (driver.findElement(By.partialLinkText(LinkText[DataIndex])) != null)
					result += Constants.KEYWORD_PASS;
				else
					result += Constants.KEYWORD_FAIL;
			}
			if (result.contains(Constants.KEYWORD_FAIL))
				return Constants.KEYWORD_FAIL;
			else
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
	}
	
	// The method clicks on Plus / Minus sign before a TR by providing title
	// path in object(provide plus.gif/ minus.gif in data)
	public String clickPlusImageByXpath(String object, String data) {
	
		System.out.println("Clicking on the image :" + data);
		try {
			int flag = -1;
			List<WebElement> ElementList = driver.findElements(By.xpath(object));
			for (int DataIndex = 0; DataIndex < ElementList.size(); DataIndex++) {
				if (ElementList.get(DataIndex).getText().contains(data)) {
					flag = DataIndex + 1;
					break;
				} else
					flag = -1;
			}
			if (flag == -1)
				return Constants.KEYWORD_FAIL;
			else {
				ListID = Long.parseLong(driver.findElement(By.xpath(".//div[@id='Tree']/ul/li[" + flag + "]")).getAttribute("id"));
				String ImageXpath = ".//div[@id='Tree']/ul/li[" + flag + "]/nobr/img[1]";
				WebElement ImageToClick = driver.findElement(By.xpath(ImageXpath));
				JavascriptExecutor executr = (JavascriptExecutor) driver;
				executr.executeScript("arguments[0].click();", ImageToClick);
				return Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
	}
	
	// The method verifies if multiple linkText are not present(provide in data
	// sepaated by pipeline)
	public String verifyMultipleTextByPartialLinkTextNotPresent(String object, String data) {
	
		System.out.println("Verifying if link text are present or not :" + data);
		try {
			String[] LinkText = data.split(Constants.DATA_SPLIT);
			String result = "";
			for (int DataIndex = 0; DataIndex < LinkText.length; DataIndex++) {
				if (driver.findElement(By.partialLinkText(LinkText[DataIndex])) == null)
					result += Constants.KEYWORD_PASS;
				else
					result += Constants.KEYWORD_FAIL;
			}
			if (result.contains(Constants.KEYWORD_FAIL))
				return Constants.KEYWORD_FAIL;
			else
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
	}
	
	// The method verifies if multiple linkText are present(provide in data
	// sepaated by pipeline)
	public String verifyMultipleTextByLinkText(String object, String data) {
	
		System.out.println("Clicking on the link :" + data);
		try {
			String[] LinkText = data.split(Constants.DATA_SPLIT);
			String result = "";
			for (int DataIndex = 0; DataIndex < LinkText.length; DataIndex++) {
				if (driver.findElement(By.linkText(LinkText[DataIndex])) != null)
					result += Constants.KEYWORD_PASS;
				else
					result += Constants.KEYWORD_FAIL;
			}
			if (result.contains(Constants.KEYWORD_FAIL))
				return Constants.KEYWORD_FAIL;
			else
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
	}
	
	// function for verifying the list of elements
	public String verifyMultipleTextNotVisibleByText(String object, String data) {
	
		System.out.println("Verifying if link text are visible or not :" + data);
		try {
			String[] Text = data.split(Constants.DATA_SPLIT);
			String result = "";
			for (int DataIndex = 0; DataIndex < Text.length; DataIndex++) {
				if (!(driver.findElement(By.xpath("//*[contains(@title, '" + Text[DataIndex] + "')]")).isDisplayed()))
					result += Constants.KEYWORD_PASS;
				else
					result += Constants.KEYWORD_FAIL;
			}
			if (result.contains(Constants.KEYWORD_FAIL))
				return Constants.KEYWORD_FAIL;
			else
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
	}
	
	// function for verifying the list of elements - duplicated
	public String verifyMultipleTextVissibleByText(String object, String data) {
	
		System.out.println("Verifying if link text are visible or not :" + data);
		try {
			String[] Text = data.split(Constants.DATA_SPLIT);
			String result = "";
			WebDriverWait wait = new WebDriverWait(driver, 30);
			for (int DataIndex = 0; DataIndex < Text.length; DataIndex++) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@title, '" + Text[DataIndex] + "')]")));
				if (driver.findElement(By.xpath("//*[contains(@title, '" + Text[DataIndex] + "')]")) != null)
					result += Constants.KEYWORD_PASS;
				else
					result += Constants.KEYWORD_FAIL;
			}
			if (result.contains(Constants.KEYWORD_FAIL))
				return Constants.KEYWORD_FAIL;
			else
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
	}
	
	// function for verifying an element by given id
	public String verifyElementPresentByID(String object, String data) {
	
		System.out.println("Verifying if link text are present or not :" + data);
		try {
			if (driver.findElement(By.id(object)) != null)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
	}
	
	// The method searches for a row by comparing data provided in excel sheet
	// only (contains)and returns id of the row.
	// This method can be called by another method to perform actions with id of
	// the row returned
	public String searchTableRowContainsByDataXpath(String object, String data) {
	
		System.out.println("Searching in the table");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			for (int i = 0; i < List_contents.size(); i++) {
				int flagStr = 0;
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					if (str.length == 0)
						break;
					else {
						if (actual.trim().toLowerCase().contains(str[expectedArr].trim().toLowerCase()))
							flagStr += 1;
					}
				}
				if (flagStr == str.length) {
					String elementID = List_contents.get(i).getAttribute("id");
					result = elementID;
					break;
				} else
					result = Constants.KEYWORD_FAIL + "Matching row does not found";
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// The keyword below clicks on row (contains)by providing row content only
	public String clickTabelRowContainedByTextXpath(String object, String data) {
	
		System.out.println("Clicking in the table row");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			for (int i = 0; i < List_contents.size(); i++) {
				int flagStr = 0;
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					if (actual.trim().toLowerCase().contains(str[expectedArr].trim().toLowerCase()))
						flagStr += 1;
				}
				if (flagStr == str.length) {
					List_contents.get(i).click();
					result = Constants.KEYWORD_PASS;
					break;
				} else
					result = Constants.KEYWORD_FAIL + "Matching row does not found";
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	// function chechking whether the given text is existing or not by id
	public String verifyIfTextPresentByID(String object, String data) {
	
		System.out.println("Verifying if text is present or not");
		try {
			if (!(driver.findElement(By.id(object)).getText() == null))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
	}
	
	// function for verifying the text in the page source
	public String verifyMultipleTextFromPageSource(String object, String data) {
	
		System.out.println("Verifying if link text are present or not :" + data);
		try {
			String[] Text = data.split(Constants.DATA_SPLIT);
			String result = "";
			for (int DataIndex = 0; DataIndex < Text.length; DataIndex++) {
				if (driver.getPageSource().contains(Text[DataIndex]))
					result += Constants.KEYWORD_PASS;
				else
					result += Constants.KEYWORD_FAIL;
			}
			if (result.contains(Constants.KEYWORD_FAIL))
				return Constants.KEYWORD_FAIL;
			else
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to find text" + e.getMessage();
		}
	}
	
	// To check whether button is not enabled
	public String buttonNotEnabled(String object, String data) {
	
		System.out.println("Waiting for an element to be visible");
		try {
			WebElement button = driver.findElement(By.id(object));
			if (!(button.isEnabled())) {
				return Constants.KEYWORD_PASS;
			} else
				return Constants.KEYWORD_FAIL + " button is enabled ";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to find button" + e.getMessage();
		}
	}
	
	// To verify Alert text (contains)
	public String verifyAlertTextContains(String object, String data) {
	
		System.out.println("Acccepting the browser alert");
		try {
			String actual = driver.switchTo().alert().getText();
			String expected = data;
			if (actual.contains(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + "text not match" + expected + "--" + actual;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to find alert" + e.getMessage();
		}
	}
	
	// To Click an element on a page by custom Xpath
	public String clickElementByJSCustomXpathAndData(String object, String data) {
	
		System.out.println("Clicking on link by its custom Xpath ");
		try {
			String str[] = object.split(Constants.DATA_SPLIT);
			String CustomXpath = "//" + str[0] + "[" + str[1] + "(@" + str[2] + ", '" + data + "')]";
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(CustomXpath)));
			WebElement Element = driver.findElement(By.xpath(CustomXpath));
			JavascriptExecutor executr = (JavascriptExecutor) driver;
			executr.executeScript("arguments[0].click();", Element);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// The method verify first seelected option from the dropdown
	public String verifyFirstSelectedOptionByID(String object, String data) {
	
		System.out.println("Verifying value of first selected option ");
		try {
			Select DropDown = new Select(driver.findElement(By.id(object)));
			String SelectedText = DropDown.getFirstSelectedOption().getText();
			if (SelectedText.contains(data))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
	}
	
	// function for verifying the attribute value
	public String verifyAttribuleValueContainsByXpath(String object, String data) {
	
		System.out.println("Verifying that labels contains text");
		try {
			String actual = driver.findElement(By.xpath(object)).getAttribute(data.split(Constants.DATA_SPLIT)[0]);
			String expected = data.split(Constants.DATA_SPLIT)[1];
			if (actual.contains(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " --not contain text " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// The method saves current window handle to to
	// arraylist-windowHandleListPrimary
	public String saveWindowHandleToArrayList(String object, String data) {
	
		System.out.println("Changing the focus to top window ");
		try {
			String Handle = driver.getWindowHandle();
			windowHandleListPrimary.add(Handle);
			InitialNumberOfHandles = driver.getWindowHandles().size();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to changefocus - " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// Function checking the existence of document with the type provided
	public String confirmSortingByRowContent(String object, String data) {
	
		int colNum;
		int counter;
		String[] temp_arr;
		String colData;
		try {
			System.out.println("Verifying the result of the sorting");
			WebElement webTable = driver.findElement(By.id(object));
			temp_arr = data.split(Constants.DATA_SPLIT);
			colNum = Integer.parseInt(temp_arr[1]);
			colData = temp_arr[0];
			List<WebElement> allRows = webTable.findElements(By.xpath("//*[@id='" + object + "']/tbody/tr[contains(@style,'block')]/td[" + colNum
			        + "]"));
			for (counter = 0; counter < allRows.size(); counter++) {
				if (!((allRows.get(counter).getText().contains(colData)) || (allRows.get(counter).getText().isEmpty()))) {
					return Constants.KEYWORD_FAIL + " MIS-MATCH found ";
				}
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find " + e.getMessage();
		}
	}
	
	// to verify that certain text contains some specific text
	public String verifyTextByXpath(String object, String data) {
	
		System.out.println("Verifying that labels contains text");
		try {
			String actual = driver.findElement(By.xpath(object)).getText();
			System.out.println("actual" + actual + "actual.trim--" + actual.trim());
			String expected = data;
			System.out.println("expected" + expected + "expected.trim--" + expected.trim());
			if (actual.trim().equalsIgnoreCase(expected.trim()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " --not contain text " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// To verify the exact text of an element by xpath
	public String verifyTextContainsStoredInGlobalVarByXpath(String object, String data) {
	
		System.out.println("Verifying the text");
		try {
			Thread.sleep(5000L);
			String actual = driver.findElement(By.xpath(object)).getText();
			String expected = storeVar;
			if (actual.trim().contains(expected.trim()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified-- " + expected + " -- " + actual;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	// function for verifying the multiple data by given id
	public String verifyMultipleDataByID(String object, String data) throws IOException {
	
		System.out.println("Navigating to Text");
		try {
			String str[] = data.split(Constants.DATA_SPLIT);
			List<WebElement> title = driver.findElements(By.id(object));
			waitTillVisibilityOfElementList(title);
			String expected = null;
			String actual = null;
			for (int ElementListIndex = 0; ElementListIndex < title.size(); ElementListIndex++) {
				int flag = 0;
				for (int ExpectedArrList = 0; ExpectedArrList < str.length; ExpectedArrList++) {
					expected = str[ExpectedArrList];
					actual = title.get(ElementListIndex).getText();
					if (actual != null)
						if (actual.contains(expected))
							flag += 1;
				}
				if (flag == str.length)
					return Constants.KEYWORD_PASS;
			}
			return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " not able to match" + e.getMessage();
		}
	}
	
	// function for verifying the absence of the element by given xpath
	public String verifyElementNotPresentByxpath(String object, String data) {
	
		System.out.println("Checking the existance of element");
		try {
			if (driver.findElements(By.xpath(object)).size() == 0)
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
		return Constants.KEYWORD_PASS;
	}
	
	public String acceptAlertIfPresent(String object, String data) throws TimeoutException {
	
		System.out.println("Checking if alert is present");
		try {
			WebDriverWait wait = new WebDriverWait(driver, 25);
			if (wait.until(ExpectedConditions.alertIsPresent()) != null) {
				System.out.println("Alert Present.");
				System.out.println("Switching to alert...");
				Alert al = driver.switchTo().alert();
				al.accept();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL + " -- Exception Caught: " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	public String verifyElementNotPresentByData(String object, String data) {
	
		System.out.println("Verifying if link text are visible or not :" + data);
		try {
			String[] Text = data.split(Constants.DATA_SPLIT);
			String result = "";
			for (int DataIndex = 0; DataIndex < Text.length; DataIndex++) {
				if (driver.findElements(By.xpath("//*[contains(@title, '" + Text[DataIndex] + "')]")).size() == 0)
					result += Constants.KEYWORD_PASS;
				else
					result += Constants.KEYWORD_FAIL;
			}
			if (result.contains(Constants.KEYWORD_FAIL))
				return Constants.KEYWORD_FAIL;
			else
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " " + e.getMessage();
		}
	}
	
	// Method will verify a text in tabel based on a unique text of row. Pass
	// unique text and text to be verified from test data.
	public String verifyTextInTabelByIDList(String object, String data) {
	
		System.out.println("Verifying the text");
		int flag = 0;
		try {
			String str[] = object.split(Constants.DATA_SPLIT);
			Thread.sleep(5000L);
			WebElement elementList = driver.findElement(By.id(str[0]));
			List<WebElement> List_contents = elementList.findElements(By.xpath("//tr[starts-with(@id,'" + str[1] + "')]"));
			System.out.println("Comparing results of table");
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				for (int j = 0; j < returnChosenArrayList().size(); j++) {
					if (actual.contains(returnChosenArrayList().get(j)) && actual.startsWith(data)) {
						flag = 1;
						continue;
					} else {
						flag = 0;
					}
				}
				if (flag == 1) {
					String elementID = List_contents.get(i).getAttribute("id");
					driver.findElement(By.id(elementID)).click();
					return Constants.KEYWORD_PASS;
				} else {
					continue;
				}
			}
			return Constants.KEYWORD_FAIL + " Unable to find text ";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	public String killParticularTaskByName(String object, String data) {
	
		System.out.println("Verifying the contents");
		try {
			WindowsUtils.tryToKillByName(data.trim());
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/************************************************* PHASE-2 STARTING ***************************************************/
	/**
	 * @description - Function will print page source code
	 * @param object - Optional
	 * @param data - Optional
	 * @return - Execution Result
	 */
	public String getPageSource(String object, String data) {
	
		System.out.println(driver.getPageSource());
		try {
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - To write text stored into a global var in the text box by Xpath
	 * @param object - Xpath of element
	 * @param data - Optional
	 * @return - Execution Result
	 */
	public String writeTextStoreInGlobalVarByXpath(String object, String data) {
	
		System.out.println("Writing in text box");
		try {
			driver.findElement(By.xpath(object)).click();
			driver.findElement(By.xpath(object)).clear();
			driver.findElement(By.xpath(object)).sendKeys(storeVar);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - Method will retrieve text from textfield and store it into an arrayList
	 * @param object - Xpath of element
	 * @param data - Optional
	 * @return - Execution result
	 */
	public String retrieveTextByXpath(String object, String data) {
	
		System.out.println("Retrieving the text from input box and storing it to an arraylist");
		try {
			retriever.add(driver.findElement(By.xpath(object)).getAttribute("value"));
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();
		}
	}
	
	/**
	 * @description - Select a random or a particular value from drop down list based on a xpath
	 * @param object - Xpath of element
	 * @param data - Random OR Particular value
	 * @return - Execution Result
	 */
	public String selectValueFromListByXpath(String object, String data) {
	
		System.out.println("Selecting from list");
		WebElement droplist = driver.findElement(By.xpath(object));
		List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
		int index;
		try {
			if (data.equals(Constants.RANDOM_VALUE)) {
				System.out.println("Selecting Random Value from list");
				Random num = new Random();
				index = num.nextInt(droplist_cotents.size());
				if (index == 0) {
					index = index + 1;
				}
			} else {
				System.out.println("Selecting given Value from list");
				index = 0;
				for (int i = 0; i < droplist_cotents.size(); i++) {
					if (data.equals(droplist_cotents.get(i).getText())) {
						index = i;
						break;
					}
				}
			}
			String selectedVal = droplist_cotents.get(index).getText();
			driver.findElement(By.xpath(object)).sendKeys(selectedVal);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - Select a particular value from dropdown based on visibility of text
	 * @param object - Xpath of dropdown
	 * @param data - Value needs to be selected
	 * @return - Execution Result
	 */
	public String selectFromListByXpath(String object, String data) {
	
		System.out.println("Selecting from list");
		try {
			Select droplist = new Select(driver.findElement(By.xpath(object)));
			droplist.selectByVisibleText(data);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method verify a particular value saved in arrayList
	 * @param object - Xpath of element
	 * @param data - Index of arrayList at which value was saved
	 * @return - Execution Result
	 */
	public String verifyParticularValueFromArrayListByXpath(String object, String data) {
	
		System.out.println("Verify particular value saved in arrayList");
		String expected, actual;
		try {
			String str[] = data.split(Constants.DATA_SPLIT);
			for (int strIndex = 0; strIndex < str.length; strIndex++) {
				expected = returnChosenArrayList().get(Integer.parseInt(str[strIndex]));
				actual = driver.findElement(By.xpath(object)).getText();
				if (actual.trim().equalsIgnoreCase(expected.trim())) {
					return Constants.KEYWORD_PASS;
				} else {
					return Constants.KEYWORD_FAIL + " -- text not verified-- " + expected + " -- " + actual;
				}
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
	}
	
	/**
	 * @description - Function is verifying selection of radio button based on xpath
	 * @param object - Xpath of Element
	 * @param data - Optional
	 * @return - Execution Result
	 */
	public String verifyRadioSelectedByXpath(String object, String data) {
	
		System.out.println("Verifying Radio button is Selected");
		String checked = null;
		try {
			checked = driver.findElement(By.xpath(object)).getAttribute("checked");
			if (!checked.equals(null)) {
				return Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button" + e.getMessage();
		}
		return Constants.KEYWORD_FAIL + "- Radio not selected" + checked;
	}
	
	/**
	 * @description - Function is clicking on an element based on CSS
	 * @param object - CSS of element
	 * @param data - Optional
	 * @return - execution result
	 */
	public String clickByCssSelector(String object, String data) {
	
		System.out.println("Trying to click");
		try {
			driver.findElement(By.cssSelector(object)).click();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find element" + e.getMessage();
		}
	}
	
	/**
	 * @description - Verify Radio button is not selected
	 * @param object - Xpath of element
	 * @param data - Optional
	 * @return - execution result
	 */
	public String verifyRadioNotSelectedByXpath(String object, String data) {
	
		System.out.println("Verifying Radio button is not Selected");
		try {
			if (!(driver.findElement(By.xpath(object)).isSelected())) {
				return Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button" + e.getMessage();
		}
		return Constants.KEYWORD_FAIL + "- Radio not selected";
	}
	
	/**
	 * @description - The method writes a random text by Xpath (uses generateRandomValues(object, data) to
	 *              generate random text)
	 * @param object - string (xpath of object)
	 * @param data - the type of generating string (NUMBER for numeric, STRING for string,ALPHA_NUMERIC for
	 *            alpha numeric) and ASCII for ASCII value) and the character limit of the generating value
	 *            (Example : NUMBER|45).
	 * @return - PASS, if driver is able to write random text
	 */
	public String writeRandomTextByXpath(String object, String data) {
	
		System.out.println("Writing random text");
		try {
			String textToWrite = generateRandomValues(object, data);
			driver.findElement(By.xpath(object)).click();
			driver.findElement(By.xpath(object)).clear();
			driver.findElement(By.xpath(object)).sendKeys(textToWrite);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -Not able to find element " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method writes a random text by ID (uses generateRandomValues(object, data) to
	 *              generate random text)
	 * @param object - string (xpath of object)
	 * @param data - the type of generating string (NUMBER for numeric, STRING for string,ALPHA_NUMERIC for
	 *            alpha numeric) and ASCII for ASCII value) and the character limit of the generating value
	 *            (Example : NUMBER|45).
	 * @return - PASS, if driver is able to write random text
	 */
	public String writeRandomTextByID(String object, String data) {
	
		System.out.println("Writing random text");
		try {
			String textToWrite = generateRandomValues(object, data);
			driver.findElement(By.id(object)).clear();
			driver.findElement(By.id(object)).sendKeys(textToWrite);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -Not able to find element " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method writes a random text by ID if object is enabled (uses
	 *              generateRandomValues(object, data) to generate random text)
	 * @param object - string (xpath of object)
	 * @param data - the type of generating string (NUMBER for numeric, STRING for string,ALPHA_NUMERIC for
	 *            alpha numeric) and ASCII for ASCII value) and the character limit of the generating value
	 *            (Example : NUMBER|45).
	 * @return - PASS, if driver is able to write random text
	 */
	public String writeRandomTextifElementEnabled(String object, String data) {
	
		System.out.println("Writing random text");
		try {
			String textToWrite = generateRandomValues(object, data);
			if (driver.findElement(By.id(object)) != null) {
				if (driver.findElements(By.xpath("//input[@id='" + object + "'and @disabled]")).size() == 0) {
					driver.findElement(By.id(object)).clear();
					driver.findElement(By.id(object)).sendKeys(textToWrite);
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -Not able to find element " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method stores selected option fron drop down inthe specified arraylist
	 * @param object - string (ID of dropdown)
	 * @param data - string
	 * @return - PASS, if driver is able to find selected dropdown and able to store it in the arraylist
	 */
	public String storeSelectedOptionByID(String object, String data) {
	
		System.out.println("Storing value of first selected option ");
		try {
			Select DropDown = new Select(driver.findElement(By.id(object)));
			String SelectedText = DropDown.getFirstSelectedOption().getText();
			returnChosenArrayList().add(SelectedText);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to find dropdown" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method stores selected option fron drop down inthe specified arraylist
	 * @param object - string (Xpath of dropdown)
	 * @param data - string
	 * @return - PASS, if driver is able to find selected dropdown and able to store it in the arraylist
	 */
	public String storeSelectedOptionByXpath(String object, String data) {
	
		System.out.println("Storing value of first selected option ");
		try {
			Select DropDown = new Select(driver.findElement(By.xpath(object)));
			String SelectedText = DropDown.getFirstSelectedOption().getText();
			returnChosenArrayList().add(SelectedText);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to find dropdown" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method compares two arraylist nameleuy- container and retriever
	 * @param object - optional
	 * @param data - optional
	 * @return - PASS, if two arraylist are equal
	 */
	public String verifyTextFromTwoArrayListByID(String object, String data) {
	
		System.out.println("Verify the text from input box");
		try {
			for (int i = 0; i < container.size(); i++) {
				if (container.get(i).trim().toUpperCase().equals(retriever.get(i).trim().toUpperCase())) {
					continue;
				} else {
					return Constants.KEYWORD_FAIL + " Not matching the text" + container.get(i).trim().toUpperCase()
					        + retriever.get(i).trim().toUpperCase();
				}
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to verify text " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method returns arraylist as mentioned in datasheet
	 * @return - ArrayList
	 */
	ArrayList<String> returnChosenArrayList() {
	
		System.out.println("Returning the array list from the data sheet");
		if (ExpectedArrayList == null)
			return container;
		else {
			if (ExpectedArrayList.trim().equalsIgnoreCase("Container"))
				return container;
			else if (ExpectedArrayList.trim().equalsIgnoreCase("ArrayList1"))
				return ArrayList1;
			else if (ExpectedArrayList.trim().equalsIgnoreCase("ArrayList2"))
				return ArrayList2;
			else if (ExpectedArrayList.trim().equalsIgnoreCase("retriever"))
				return retriever;
			else if (ExpectedArrayList.trim().equalsIgnoreCase("ArrayList3"))
				return ArrayList3;
			else if (ExpectedArrayList.trim().equalsIgnoreCase("ArrayList4"))
				return ArrayList4;
			else if (ExpectedArrayList.trim().equalsIgnoreCase("ArrayList5"))
				return ArrayList5;
			else if (ExpectedArrayList.trim().equalsIgnoreCase("ArrayList6"))
				return ArrayList6;
		}
		return container;
	}
	
	/**
	 * @description - The method selects an option from a dropdown by Xpath
	 * @param object - string
	 * @param data - option from the drop down list or Random if random value is to be selected
	 * @return - PASS, if dropdown is able to select the value
	 */
	public String selectListByXpath(String object, String data) {
	
		System.out.println("Selecting from list");
		Select dropDown = new Select(driver.findElement(By.xpath(object)));
		List<WebElement> droplist_cotents = dropDown.getOptions();
		int index = -1;
		try {
			if (data.equals(Constants.RANDOM_VALUE)) {
				System.out.println("Selecting Random Value from list");
				Random num = new Random();
				index = num.nextInt(droplist_cotents.size());
				if (index == 0)
					index = index + 1;
			} else {
				System.out.println("Selecting given Value from list");
				for (int i = 0; i < droplist_cotents.size(); i++) {
					if (data.equalsIgnoreCase(droplist_cotents.get(i).getText())) {
						index = i;
						System.out.println("index" + index);
						break;
					}
				}
			}
			String selectedVal = droplist_cotents.get(index).getText();
			System.out.println(selectedVal + "selected value");
			dropDown.selectByVisibleText(selectedVal);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method selects a random value from a drop down if Random_Value is given in data or
	 *              selects a particular value specified in datasheet
	 * @param object - string
	 * @param data - string
	 * @return - PASS, if object exists and value is selected in the drop down
	 */
	public String selectListIncludingFirstByXpath(String object, String data) {
	
		System.out.println("Selecting from list");
		Select dropDown = new Select(driver.findElement(By.xpath(object)));
		List<WebElement> droplist_cotents = dropDown.getOptions();
		int index = -1;
		try {
			if (data.equals(Constants.RANDOM_VALUE)) {
				System.out.println("Selecting Random Value from list");
				Random num = new Random();
				index = num.nextInt(droplist_cotents.size());
			} else {
				System.out.println("Selecting given Value from list");
				for (int i = 0; i < droplist_cotents.size(); i++) {
					if (data.equalsIgnoreCase(droplist_cotents.get(i).getText())) {
						index = i;
						break;
					}
				}
			}
			String selectedVal = droplist_cotents.get(index).getText();
			dropDown.selectByVisibleText(selectedVal);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method searches for a number of occurence of a particular string in the given
	 *              arraylist
	 * @param GivenString - string to search in arraylist
	 * @param GivenList - arraylist of string
	 * @return - int, number of occurence of string in arraylist
	 */
	public int SearchOccurenceOfWordInArrayList(ArrayList<String> GivenList, String GivenString) {
	
		System.out.println("Searching occurence of word in the given arraylist:");
		int MatchCounter = 0;
		for (int ListIndex = 0; ListIndex < GivenList.size(); ListIndex++) {
			if (GivenList.get(ListIndex) != null) {
				if (GivenList.get(ListIndex).trim().toLowerCase().contains(GivenString.trim().toLowerCase())) {
					MatchCounter++;
				}
			}
		}
		if (MatchCounter == 0) {
			System.out.println("Given word does not found in the arraylist");
		} else {
			System.out.println("Occurence of--" + GivenString + "--in the arraylist: " + MatchCounter);
		}
		return MatchCounter;
	}
	
	/**
	 * @description - The method searches if a row in the table contains the expected data
	 * @param object - xpath of row (tr) of table
	 * @param data - particulars of row data to search separated by pipeline
	 * @return - Execution Result
	 */
	public String verifyTableRowContainsByData(String object, String data) {
	
		System.out.println("Searching in the table");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			System.out.println("extracting");
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			for (int i = 0; i < List_contents.size(); i++) {
				int flagStr = 0;
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					if (str.length == 0) {
						break;
					} else {
						if (actual.trim().toLowerCase().contains(str[expectedArr].trim().toLowerCase()))
							flagStr += 1;
					}
				}
				if (flagStr == str.length) {
					result = Constants.KEYWORD_PASS;
					break;
				} else
					result = Constants.KEYWORD_FAIL + "Matching row does not found";
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method searches for a row by comparing data provided in excel sheet and arrayList
	 *              (contains).
	 * @param object - xpath of row (tr) of table
	 * @param data - particulars of row data to search separated by pipeline
	 * @return - Execution Result
	 */
	public String VerifyTableRowContainsByDataAndArrayList(String object, String data) {
	
		System.out.println("Searching the table contents");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			Thread.sleep(5000L);
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			for (int i = 0; i < List_contents.size(); i++) {
				int flagContainer = 0;
				int flagStr = 0;
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < returnChosenArrayList().size(); expectedArr++) {
					if (returnChosenArrayList().size() == 0)
						break;
					else {
						if (actual.trim().toLowerCase().contains(returnChosenArrayList().get(expectedArr).trim().toLowerCase()))
							flagContainer += 1;
					}
				}
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					if (str.length == 0)
						break;
					else {
						if (actual.trim().toLowerCase().contains(str[expectedArr].trim().toLowerCase()))
							flagStr += 1;
					}
				}
				if (flagContainer == returnChosenArrayList().size() && flagStr == str.length) {
					result = Constants.KEYWORD_PASS;;
					break;
				}
				result = Constants.KEYWORD_FAIL + "Matching row does not found";
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	/**
	 * @description - function entering multiple lines into a text area
	 * @param object - xpath of the text area
	 * @param data - number of lines with the type of data seperated by : (Example : 5:STRING)
	 * @return - result of the execution
	 */
	public String writeMultipleLinesIntoTextArea(String object, String data) {
	
		String text = null;
		int counter = Integer.parseInt(data.split(":")[0]);
		try {
			System.out.println("Writing multiple lines into text area");
			System.out.println();
			text = generateRandomValues(object, data.split(":")[1] + ":20") + "\n";
			counter--;
			while (counter > 0) {
				text = text + generateRandomValues(object, data.split(":")[1] + ":20") + "\n";
				counter--;
			}
			writeTextByXpath(object, text);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Exception caught " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - function calling other functions dynamically
	 * @param object - object paasing from feeder function
	 * @param data - funtionName:arguments
	 * @return - the result of calling functions
	 */
	public String callMethodDynamically(String object, String data) {
	
		System.out.println(data);
		String methodName = data.split("-")[0]; // Storing the function name
		String arguments[] = { object, data.substring(data.indexOf('-') + 1) }; // Storing the
		// arguments
		try {
			System.out.println("Calling " + methodName + " method dynamically");
			Keywords dc = new Keywords(); // Creating the object of loading
			                              // class
			Class cls = dc.getClass(); // Loading the class dynamically
			Object obj = cls.newInstance(); // Creating the loaded class
			                                // instance with Object type
			Class[] params = new Class[2]; // Setting the parameter variable
			Class[] param = new Class[1];
			param[0] = WebDriver.class;
			params[0] = String.class;
			params[1] = String.class;
			Method method = cls.getDeclaredMethod("initializeDriver", param);
			method.invoke(obj, driver);
			method = cls.getDeclaredMethod(methodName, params);
			String returnValue = (String) method.invoke(obj, arguments);
			return returnValue;
		} catch (Exception e) {
			System.out.println("Exception caught" + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - function initialising the driver dynamically
	 * @param initDriver - current driver object
	 */
	public void initializeDriver(WebDriver initDriver) {
	
		driver = initDriver;
	}
	
	/**
	 * @description - function feeding the the return value of A into B and perform the actions (where A and B
	 *              are two functions)
	 * @param object - object of A
	 * @param data -data input of B
	 */
	public String feederFunction(String object, String data) {
	
		try {
			String srcFunction = data.split("-")[0]; // Storing the the first
			                                         // function name
			String destFunction = data.split("-")[1]; // Storing the second
			                                          // function name
			int index = nthOccurrence(data, '-', 1);
			String arguments = data.substring(index + 1); // Storing the arguments
			// forthe first function
			System.out.println("Startig the feeding from " + srcFunction + " to " + destFunction);
			String returnVal = callMethodDynamically(object, srcFunction + "-" + arguments); // Calling the
			                                                                                 // first
			                                                                                 // function
			returnVal = callMethodDynamically(object, destFunction + "-" + returnVal); // Calling the second
			                                                                           // function
			if (returnVal.equalsIgnoreCase("PASS")) {
				System.out.println("Feeding Successfull");
				return Constants.KEYWORD_PASS;
			} else {
				System.out.println("Feeding Failure");
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			System.out.println("Exception caught in feederFunction " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - function storing the object containing test into global storage by name
	 * @param object - xpath of the element
	 * @param data - key name
	 * @return - execution result
	 */
	public String addGlobalVarByName(String object, String data) {
	
		try {
			String value = driver.findElement(By.xpath(object)).getAttribute("value");
			System.out.println("Storing the value: " + value + "into global storage by key " + data);
			globalStorage.put(data, value);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Exception caught " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - function retrieving value from the global storage by matching the key provided in the
	 *              key
	 * @param object - optional varibale
	 * @param data - key name
	 * @return - the retrieved value
	 */
	public String getGlobalVarByName(String object, String data) {
	
		try {
			System.out.println("Retrieving the value from the global storage by key " + data);
			String rertievedVlue = globalStorage.get(data);
			if (rertievedVlue.isEmpty()) {
				System.out.println("Value not found");
			}
			System.out.println("Value found");
			return rertievedVlue;
		} catch (Exception e) {
			System.out.println("Exception caught " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - The method verifies any CSS attribute of an element located by xpath
	 * @param object - String (xpath of element)
	 * @param data - CSS Attribute to be verified|Value
	 * @return - Pass if attribute value is as expected
	 */
	public String verifyCSSAttributeByXpath(String object, String data) {
	
		System.out.println("Verifying the attribute");
		try {
			String actual = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			System.out.println(str[0]);
			System.out.println(str[1]);
			actual = driver.findElement(By.xpath(object)).getCssValue(str[0]);
			System.out.println(actual);
			if (actual.contains(str[1])) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + "CSS attribute is not as expected " + "Actual--> " + actual + "Expected--> " + str[1];
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to verify css attribute " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verifies version value displayed on Export Requirement page
	 * @param object - String (xpath of element)
	 * @param data - Expected String
	 * @return - Pass if text is as expected
	 */
	public String verifyVersionValueByXpath(String object, String data) {
	
		System.out.println("Verifying the attribute");
		try {
			String actual = null;
			String expected = null;
			String pattern = "dd MMM yyyy";
			actual = driver.findElement(By.xpath(object)).getText();
			System.out.println(actual);
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Calendar cal = Calendar.getInstance();
			Date currentDate = cal.getTime();
			expected = "1   (" + format.format(currentDate) + ")";
			System.out.println(expected);
			if (actual.equalsIgnoreCase(expected)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + "Version no. is not as expected " + "Actual--> " + actual + "Expected--> " + expected;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to verify version no. " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verifies selected option in dropdown
	 * @param object - String (xpath of element)
	 * @param data - Expected option
	 * @return - Pass if selected option is as expected
	 */
	public String verifyFirstSelectedOptionByXpath(String object, String data) {
	
		System.out.println("Verifying value of first selected option ");
		try {
			Select DropDown = new Select(driver.findElement(By.xpath(object)));
			String SelectedText = DropDown.getFirstSelectedOption().getText();
			if (SelectedText.contains(data)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verifies state of an element for all values of the dropdown
	 * @param object - String (xpath of dropdown)
	 * @param data - String(Xpath of element|State)
	 * @return - Pass if state of the element is as expected
	 */
	public String verifyStateForDropdownValuesByXpath(String object, String data) {
	
		System.out.println("Selecting from list");
		try {
			Properties properties = LoadProperties();
			String[] str = data.split(Constants.DATA_SPLIT);
			String webelement = properties.getProperty(str[0]);
			String stateResult = null;
			String droplistLength = null;
			boolean flagCheck = false;
			Select droplist = new Select(driver.findElement(By.xpath(object)));
			List<WebElement> droplist_cotents = droplist.getOptions();
			for (int i = 0; i < droplist_cotents.size(); i++) {
				droplist.selectByIndex(i);
				if (str[1].contains("isEnabled")) {
					stateResult = verifyEnabledStateByXpath(webelement, data);
					if (stateResult.equalsIgnoreCase(Constants.KEYWORD_FAIL)) {
						System.out.println("Element is not enabled for the dropdown value  " + droplist_cotents.get(i).getText());
						flagCheck = true;
					}
				} else if (str[1].contains("isDisabled")) {
					stateResult = verifyDisabledStateByXpath(webelement, data);
					if (stateResult.equalsIgnoreCase(Constants.KEYWORD_FAIL)) {
						System.out.println("Element is not disabled for the dropdown value  " + droplist_cotents.get(i).getText());
						flagCheck = true;
					}
				}
				if (flagCheck == true) {
					return Constants.KEYWORD_FAIL + "The element was not in expected state for all dropdown values";
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method verifies enabled state of an element
	 * @param object - String (xpath of element)
	 * @param data - optional
	 * @return - Pass if state of the element is enabled
	 */
	public String verifyEnabledStateByXpath(String object, String data) {
	
		System.out.println("Verifying that state is enabled");
		try {
			WebElement element = driver.findElement(By.xpath(object));
			if (element.isEnabled()) {
				System.out.println("state is enabled");
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + "State is not Enabled";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verifies disabled state of an element
	 * @param object - String (xpath of element)
	 * @param data -
	 * @return Pass if state of the element is disabled
	 */
	public String verifyDisabledStateByXpath(String object, String data) {
	
		System.out.println("Verifying that state is disabled");
		try {
			WebElement element = driver.findElement(By.xpath(object));
			if (!(element.isEnabled())) {
				System.out.println("state is disabled");
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + "State is Enabled";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verifies state of an element for particular value of the dropdown
	 * @param object - String (xpath of dropdown)
	 * @param data - String(Xpath of element|State|Dropdown value)
	 * @return - Pass if state of the element is as expected
	 */
	public String verifyStateForSingleDropdownValueByXpath(String object, String data) {
	
		System.out.println("Selecting from list");
		try {
			Properties properties = LoadProperties();
			String[] str = data.split(Constants.DATA_SPLIT);
			String webelement = properties.getProperty(str[0]);
			String stateResult = null;
			boolean flagCheck = false;
			Select droplist = new Select(driver.findElement(By.xpath(object)));
			List<WebElement> droplist_cotents = droplist.getOptions();
			if (str[1].contains("isEnabled")) {
				for (int i = 0; i < droplist_cotents.size(); i++) {
					droplist.selectByIndex(i);
					if (droplist_cotents.get(i).getText().contains(str[2])) {
						stateResult = verifyEnabledStateByXpath(webelement, data);
						if (stateResult.equalsIgnoreCase(Constants.KEYWORD_FAIL)) {
							System.out.println("Element is not enabled for the dropdown value  " + droplist_cotents.get(i).getText());
							flagCheck = true;
						}
					} else {
						stateResult = verifyDisabledStateByXpath(webelement, data);
						if (stateResult.equalsIgnoreCase(Constants.KEYWORD_FAIL)) {
							System.out.println("Element is not disabled for the dropdown value  " + droplist_cotents.get(i).getText());
							flagCheck = true;
						}
					}
				}
			} else if (str[1].contains("isDisabled")) {
				for (int i = 0; i < droplist_cotents.size(); i++) {
					droplist.selectByIndex(i);
					if (droplist_cotents.get(i).getText().contains(str[2])) {
						stateResult = verifyDisabledStateByXpath(webelement, data);
						if (stateResult.equalsIgnoreCase(Constants.KEYWORD_FAIL)) {
							System.out.println("Element is not disabled for the dropdown value  " + droplist_cotents.get(i).getText());
							flagCheck = true;
						}
					} else {
						stateResult = verifyEnabledStateByXpath(webelement, data);
						if (stateResult.equalsIgnoreCase(Constants.KEYWORD_FAIL)) {
							System.out.println("Element is not enabled for the dropdown value  " + droplist_cotents.get(i).getText());
							flagCheck = true;
						}
					}
				}
			}
			if (flagCheck == true) {
				return Constants.KEYWORD_FAIL + "The element was not in expected state for all dropdown values";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method emulates the Control + Click action
	 * @param object - String (xpath of element)
	 * @param data - optional
	 * @return - Pass if the action is performed
	 */
	public String virtualControlPressAndClick(String object, String data) {
	
		System.out.println("Pressing a key virtually  ");
		try {
			Actions builder = new Actions(driver);
			builder.keyDown(Keys.CONTROL).click(driver.findElement(By.xpath(object)));
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method verifies that multiple options can be selected in dropdown
	 * @param object - String (xpath of dropdown)
	 * @param data - optional
	 * @return - Pass if dropdown supports multiple selections
	 */
	public String verifySelectSupportsMultipleByXpath(String object, String data) {
	
		System.out.println("Verifying that dropdown supports multiple options select ");
		try {
			WebElement dropdown = driver.findElement(By.xpath(object));
			Select se = new Select(dropdown);
			if (se.isMultiple()) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + "Dropdown is not multiselect";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description- Method will store a textfield value into arraylist
	 * @param object - Xpath of textfield
	 * @param data - Optional
	 * @return- Execution Result
	 */
	public String storeTextByValuexPath(String object, String data) {
	
		System.out.println("Storing the text from input box into arraylist");
		try {
			returnChosenArrayList().add(driver.findElement(By.xpath(object)).getAttribute("value"));
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - Function will write a value into a textfield stored at a particular index in arraylist
	 * @param object - Xpath of textfield
	 * @param data - Index of arraylist
	 * @return-- Execution Result
	 */
	public String writeFromArrayListByXpath(String object, String data) {
	
		System.out.println("Storing the text from input box into arraylist");
		try {
			String str[] = data.split(Constants.DATA_SPLIT);
			for (int strIndex = 0; strIndex < str.length; strIndex++) {
				String text = returnChosenArrayList().get(Integer.parseInt(str[strIndex]));
				driver.findElement(By.xpath(object)).sendKeys(text);
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method searches if element list is present or not.
	 * @param object - xpath of arraylist
	 * @param data - string to find in the element list
	 * @return - Execution Result
	 */
	public String checkAndVerifyElementNotPresent(String object, String data) {
	
		System.out.println("Verifying element is not found...:");
		try {
			String IsElementListPresent = verifyElementPresencexpath(object, data);
			if (IsElementListPresent.equals(Constants.KEYWORD_PASS)) {
				System.out.println("Results Found");
				String IsRowNotPresent = verifyListItemNotPresentByXpath(object, data);
				if (IsRowNotPresent.equals(Constants.KEYWORD_PASS)) {
					return Constants.KEYWORD_PASS;
				}
			} else {
				System.out.println("No result found");
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to find element" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - Method will verify element is not present
	 * @param object - Xpath of element
	 * @param data - Optional
	 * @return - Execution Result
	 */
	public String verifyElementPresencexpath(String object, String data) {
	
		System.out.println("Verifying the element presence");
		try {
			if (driver.findElements(By.xpath(object)).size() != 0) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
	}
	
	/**
	 * @description- Method will verify an element stored in arraylist is not present in tabel
	 * @param object - Xpath of element
	 * @param data - Optional
	 * @return - Execution Result
	 */
	public String verifyListItemNotPresentByXpath(String object, String data) {
	
		System.out.println("Verifying element is not found...:");
		try {
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			int flagContainer = 0;
			int flagStr = 0;
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < returnChosenArrayList().size(); expectedArr++) {
					if (returnChosenArrayList().size() == 0) {
						break;
					} else {
						if (actual.trim().toLowerCase().contentEquals(returnChosenArrayList().get(expectedArr).trim().toLowerCase())) {
							flagContainer = 0;
							break;
						} else
							flagContainer += 1;
					}
				}
				flagStr += 1;
			}
			if (flagContainer > 0 && flagStr == List_contents.size()) {
				return Constants.KEYWORD_PASS;
			} else
				return Constants.KEYWORD_FAIL + "Matching row is found";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to find element" + e.getMessage();
		}
	}
	
	/**
	 * @description - The method searches if a row in the table does not contain the expected data
	 * @param object - xpath of row (tr) of table
	 * @param data - particulars of row data to search separated by pipeline
	 * @return - Execution Result
	 */
	public String verifyListItemNotPresentByData(String object, String data) {
	
		System.out.println("Searching in the table");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			System.out.println("extracting");
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			int flagStr = 0;
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					if (str.length == 0) {
						break;
					} else {
						if (!(actual.trim().toLowerCase().contains(str[expectedArr].trim().toLowerCase()))) {
							flagStr += 1;
						}
					}
				}
				if (flagStr == List_contents.size()) {
					result = Constants.KEYWORD_PASS;
				} else {
					result = Constants.KEYWORD_FAIL + "Matching row is found";
				}
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method writes a particular value to input field saved in arrayList by providing
	 *              index number of element in arraylist
	 * @param object - xpath of element
	 * @param data - Index number of element in arraylist
	 * @return - Execution Result
	 */
	public String writeParticularValueFromArrayListXpath(String object, String data) {
	
		System.out.println("Writing particular value saved in arrayList");
		try {
			String ValueToWrite = returnChosenArrayList().get((int) Float.parseFloat(data));
			System.out.println("Value to write: " + ValueToWrite);
			driver.findElement(By.xpath(object)).click();
			driver.findElement(By.xpath(object)).clear();
			driver.findElement(By.xpath(object)).sendKeys(ValueToWrite);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to find element" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method selects a particular value from drop down saved in arrayList by providing
	 *              index number of element in arraylist
	 * @param object - string
	 * @param data - String, index number of element in arraylist
	 * @return - execution result
	 */
	public String selectParticularInDrpDownValueFromArrayList(String object, String data) {
	
		System.out.println("Selecting particular value in drop down saved in arrayList");
		try {
			String ValueToSelect = returnChosenArrayList().get((int) Float.parseFloat(data));
			System.out.println("Value to select :" + ValueToSelect);
			selectListByXpath(object, ValueToSelect);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to find drop down " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method function stores a text into a global variable by given xpath
	 * @param object - xpath of object
	 * @param data - optional variable
	 * @return - Execution Result
	 */
	public String storeTextByValueXpath(String object, String data) {
	
		System.out.println("Storing the text from input box into arraylist");
		try {
			returnChosenArrayList().add(driver.findElement(By.xpath(object)).getAttribute("value"));
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method searches if element list is present or not. If present then it will calculate
	 *              the number of occurence of given string in the element list and return PASS if search
	 *              result is equal to element list size
	 * @param object - xpath of arraylist
	 * @param data - string to find in the element list
	 * @return - Execution Result
	 */
	public String checkAndVerifyElementListContent(String object, String data) {
	
		System.out.println("Verifying if results are found...:");
		try {
			String Result = null;
			String IsElementListPresent = verifyElementPresencexpath(object, data);
			if (IsElementListPresent.equals(Constants.KEYWORD_PASS)) {
				System.out.println("Results Found");
				Result = verifyNumOfElementListContent(object, data);
				return Result;
			} else {
				System.out.println("No result found");
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to find element" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method deselects all selected options from a dropdown (if present) by Xpath
	 * @param object - xpath of dropdown
	 * @param data - optional variable
	 * @return - execution result
	 */
	public String deselectListByXpath(String object, String data) {
	
		try {
			System.out.println("Deselecting all list elements");
			Select dropDown = new Select(driver.findElement(By.xpath(object)));
			if (dropDown.getAllSelectedOptions().size() == 0) {
				System.out.println("No option selected.");
			} else if (dropDown.getAllSelectedOptions().size() != 0 && dropDown.isMultiple()) {
				dropDown.deselectAll();
				System.out.println("All selected options deselected.");
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not deselect from list. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method clicks on a random row from a list of row elements
	 * @param object - xpath of row elements
	 * @param data - optional variable
	 * @return - Execution result
	 */
	public String clickRandomRowIfPresent(String object, String data) {
	
		System.out.println("Clicking on a random row");
		try {
			String ElementText = null;
			String IsElementListPresent = verifyElementPresencexpath(object, data);
			if (IsElementListPresent.equals(Constants.KEYWORD_PASS)) {
				System.out.println("Results Found");
				String ElementXpath = returnRandomRowXpath(object, data);
				ElementText = driver.findElement(By.xpath(ElementXpath)).getText();
				clickByTextUsingJS(object, ElementText);
			} else {
				return Constants.KEYWORD_FAIL + "No such elements found";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find element. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method returns xpath of a random row from a list of row elements
	 * @param object - xpath of row eleemnts
	 * @param data - optional variable
	 * @return - string, xpath of a random row from list of row elements
	 */
	public String returnRandomRowXpath(String object, String data) {
	
		System.out.println("Selecting a random row");
		try {
			int randomInt = -1;
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(object)));
			List<WebElement> ElementList = driver.findElements(By.xpath(object));
			Random randomGenerator = new Random();
			randomInt = randomGenerator.nextInt(ElementList.size());
			String ModifiedRowXpath = object.replace("tr", "tr[" + randomInt + 1 + "]");
			System.out.println("Modified xpath is: " + ModifiedRowXpath);
			return ModifiedRowXpath;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find element. " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method writes integer text in an input field by Xpath
	 * @param object - xpath of element
	 * @param data - integer data to write
	 * @return - Execution result
	 */
	public String writeIntegerTextByXpath(String object, String data) {
	
		System.out.println("Writing in text box");
		try {
			driver.findElement(By.xpath(object)).click();
			driver.findElement(By.xpath(object)).clear();
			driver.findElement(By.xpath(object)).sendKeys(String.valueOf((int) Float.parseFloat(data)));
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method verifies that element located by xpath is not present
	 * @param object - String (ID of element)
	 * @param data - optional
	 * @return - Pass ifelement not present
	 */
	public String verifyElementNotPresentByID(String object, String data) {
	
		System.out.println("Checking the existance of element");
		try {
			if (driver.findElements(By.xpath(object)).size() == 0) {
				return Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method verifies that element located by xpath is not present
	 * @param object - String (ID of element)
	 * @param data - optional
	 * @return - Pass ifelement not present
	 */
	public String clearTextByXpath(String object, String data) {
	
		System.out.println("Checking the existance of element");
		try {
			driver.findElement(By.xpath(object)).clear();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
	}
	
	/**
	 * @description - The method searches for a row by comparing data provided in excel sheet and arrayList
	 *              (contains).
	 * @param object - xpath of row (tr) of table
	 * @param data - particulars of row data to search separated by pipeline
	 * @return - Pass if the row does not contain the data
	 */
	public String VerifyTableRowNotContainsByDataAndArrayList(String object, String data) {
	
		System.out.println("Searching the table contents");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			for (int i = 0; i < str.length; i++)
				System.out.println(str[i]);
			Thread.sleep(5000L);
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			for (int i = 0; i < List_contents.size(); i++) {
				int flagContainer = 0;
				int flagStr = 0;
				String actual = List_contents.get(i).getText();
				System.out.println("Actual is=  " + actual);
				for (int expectedArr = 0; expectedArr < returnChosenArrayList().size(); expectedArr++) {
					if (returnChosenArrayList().size() == 0) {
						break;
					} else {
						if (actual.trim().toLowerCase().contains(returnChosenArrayList().get(expectedArr).trim().toLowerCase()))
							flagContainer += 1;
					}
				}
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					if (str.length == 0) {
						break;
					} else {
						if (actual.trim().toLowerCase().contains(str[expectedArr].trim().toLowerCase()))
							flagStr += 1;
					}
				}
				if (flagContainer == returnChosenArrayList().size() && flagStr == str.length) {
					result = Constants.KEYWORD_FAIL;
					break;
				}
				result = Constants.KEYWORD_PASS + "Matching row does not found";
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	/**
	 * @description- To Clear the text in the text box
	 * @param object - Xpath of element
	 * @param data - Optional
	 * @return- Execution Result
	 */
	public String clearTextByxpath(String object, String data) {
	
		System.out.println("Clear the text box");
		try {
			driver.findElement(By.xpath(object)).click();
			driver.findElement(By.xpath(object)).clear();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to clear " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method verify a particular value saved in arrayList
	 * @param object - Xpath of element
	 * @param data - Index of arrayList at which value was saved
	 * @return - Execution Result
	 */
	public String verifyParticularFromArrayListByXpath(String object, String data) {
	
		System.out.println("Verify particular value saved in arrayList");
		String expected, actual;
		try {
			expected = returnChosenArrayList().get((int) Float.parseFloat(data));
			actual = driver.findElement(By.xpath(object)).getText();
			if (actual.trim().equalsIgnoreCase(expected.trim())) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " -- text not verified-- " + expected + " -- " + actual;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
	}
	
	/**
	 * @description- To verify focus of current element
	 * @param object - Xpath of element
	 * @param data - Optional
	 * @return- Execution Result
	 */
	public String verifyFocusByXpath(String object, String data) {
	
		System.out.println("Verifying focus");
		try {
			WebElement expectedElement = driver.findElement(By.xpath(object));
			if (expectedElement.equals(driver.switchTo().activeElement())) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to verify focus " + e.getMessage();
		}
	}
	
	/**
	 * @description - Method will verify element is enabled
	 * @param object - Xpath of element
	 * @param data - Optional
	 * @return - Execution Result
	 */
	public String verifyElementEnabledxpath(String object, String data) {
	
		System.out.println("Verifying the element presence");
		try {
			List<WebElement> droplist_cotents = driver.findElements(By.xpath(object));
			for (int i = 0; i < droplist_cotents.size(); i++) {
				boolean enabled = droplist_cotents.get(i).isEnabled();
				if (enabled) {
					String elementid = droplist_cotents.get(i).getAttribute("id");
					driver.findElement(By.id(elementid)).click();
					return Constants.KEYWORD_PASS;
				}
			}
			return Constants.KEYWORD_FAIL + "Unable to find Enabled Element";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
	}
	
	/**
	 * @description - The method selects an option from a dropdown by its attribute value
	 * @param object - string
	 * @param data - option from the drop down list or Random if random value is to be selected
	 * @return - PASS, if dropdown is able to select the value
	 */
	public String selectListByValueXpath(String object, String data) {
	
		System.out.println("Selecting from list");
		Select dropDown = new Select(driver.findElement(By.xpath(object)));
		List<WebElement> droplist_cotents = dropDown.getOptions();
		int index = -1;
		try {
			if (data.equals(Constants.RANDOM_VALUE)) {
				System.out.println("Selecting Random Value from list");
				Random num = new Random();
				index = num.nextInt(droplist_cotents.size());
				if (index == 0)
					index = index + 1;
			} else {
				System.out.println("Selecting given Value from list");
				for (int i = 0; i < droplist_cotents.size(); i++) {
					if (data.equalsIgnoreCase(droplist_cotents.get(i).getAttribute("value"))) {
						index = i;
						break;
					}
				}
			}
			String selectedVal = droplist_cotents.get(index).getAttribute("value");
			dropDown.selectByValue(selectedVal);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method selects a particular value from drop down using attribute "value" saved in
	 *              arrayList by providing index number of element in arraylist
	 * @param object - string
	 * @param data - String, index number of element in arraylist
	 * @return - PASS, if dropdown is able to select the value
	 */
	public String selectParticularValueUsingAttributeFromArrayList(String object, String data) {
	
		System.out.println("Selecting particular value in drop down saved in arrayList");
		try {
			String ValueToSelect = returnChosenArrayList().get((int) Float.parseFloat(data));
			System.out.println("Value to select :" + ValueToSelect);
			selectListByValueXpath(object, ValueToSelect);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to find drop down " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method verifies if the occurence of given string is equal to element list size
	 * @param object - xpath of element
	 * @param data - String to verify or Random_Value in case of random selection from object
	 * @return - Execution Result
	 */
	public String verifyNumOfElementListContent(String object, String data) {
	
		System.out.println("Waiting till visibility of element list  ");
		try {
			int MatchedStringNum = -1;
			int ArrayListSize = -1;
			int Flag = -1;
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(object)));
			List<WebElement> ElementList = driver.findElements(By.xpath(object));
			ArrayList<String> StringList = new ArrayList<String>();
			ArrayListSize = ElementList.size();
			for (int i = 0; i < ElementList.size(); i++) {
				if (ElementList.get(i).getText() != null)
					StringList.add(ElementList.get(i).getText());
			}
			if (StringList.size() == ArrayListSize && !(data.equals(Constants.RANDOM_VALUE))) {
				MatchedStringNum = SearchOccurenceOfWordInArrayList(StringList, data);
				Flag = 1;
			}
			if (StringList.size() == ArrayListSize && data.equals(Constants.RANDOM_VALUE)) {
				MatchedStringNum = SearchOccurenceOfWordInArrayList(StringList, returnChosenArrayList().get(0));
				Flag = 1;
			}
			if (Flag == -1) {
				return Constants.KEYWORD_FAIL + "ArrayList size is not equal to number of elements";
			}
			if (MatchedStringNum == ArrayListSize) {
				System.out.println("Number of occurence of string to be searched in arraylist : " + MatchedStringNum);
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + "Number of occurence of given word is not equal to element list size";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to find element" + e.getMessage();
		}
	}
	
	/**
	 * @description - Method waits for 30 seconds for all elements to be visible and verify if elements are
	 *              present or not
	 * @param object - Xpath of Element List
	 * @param data - Optional
	 * @return - Execution Result
	 */
	public String WaitAndVerifyElementPresencexpath(String object, String data) {
	
		System.out.println("Verifying the element presence");
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(object)));
			if (driver.findElements(By.xpath(object)).size() == 0) {
				System.out.println("Element list is empty. Size: " + driver.findElements(By.xpath(object)).size());
				return Constants.KEYWORD_FAIL;
			} else {
				System.out.println("Element list size: " + driver.findElements(By.xpath(object)).size());
				return Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
	}
	
	/**
	 * @description - funtion concatinating two strings
	 * @param object - optional variable
	 * @param data - data sheet input and global storage key seperated by |(Example : Req_|code)
	 * @return - glued variable (Example :Req_getGlobalVarByName(object,code))
	 */
	public String glueFunction(String object, String data) {
	
		String glueResult = null;
		System.out.println("Calling glue function");
		try {
			glueResult = (data.split(Constants.DATA_SPLIT)[0]) + " ".concat(getGlobalVarByName(object, data.split(Constants.DATA_SPLIT)[1]));
			return glueResult;
		} catch (Exception e) {
			System.out.println("Exception Caught in Glue funtion " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - function clicking on multiple elements based on theircommon xapth
	 * @param object - common xpath of the elements
	 * @param data - optional variable
	 * @return - execution result
	 */
	public String multiCheck(String object, String data) {
	
		try {
			List<WebElement> elements = driver.findElements(By.xpath(object));
			System.out.println("Clicking multiple elements");
			for (WebElement webElement : elements) {
				JavascriptExecutor executr = (JavascriptExecutor) driver;
				executr.executeScript("arguments[0].click();", webElement);
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Exception caught in multli click " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - The method verify a particular value saved in arrayList
	 * @param object - Xpath of element
	 * @param data - Index of arrayList at which value was saved
	 * @return - Execution Result
	 */
	public String verifyParticularContainsFromArrayListByXpath(String object, String data) {
	
		System.out.println("Verify particular value saved in arrayList");
		String expected, actual;
		try {
			expected = returnChosenArrayList().get((int) Float.parseFloat(data));
			actual = driver.findElement(By.xpath(object)).getText();
			if (actual.trim().contains(expected.trim())) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " -- text not verified-- " + expected + " -- " + actual;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
	}
	
	/**
	 * @description - Function will write a value into a textfield stored at a particular index in arraylist
	 * @param object - Xpath of textfield
	 * @param data - Index of arraylist without pipeline
	 * @return- Execution Result
	 */
	public String writeFromArrayListIndexByXpath(String object, String data) {
	
		System.out.println("Storing the text from input box into arraylist");
		try {
			String str[] = data.split(Constants.DATA_SPLIT);
			for (int strIndex = 0; strIndex < str.length; strIndex++) {
				String text = returnChosenArrayList().get((int) Float.parseFloat(str[strIndex]));
				System.out.println(text + "text");
				driver.findElement(By.xpath(object)).click();
				driver.findElement(By.xpath(object)).clear();
				driver.findElement(By.xpath(object)).sendKeys(text);
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description- Method will verify whether actual data is starting with expected data
	 * @param object - Xpath of element
	 * @param data - expected data
	 * @return- Execution Result
	 */
	public String verifyIntTextStartsByXpath(String object, String data) {
	
		System.out.println("Verifying that labels starting with text");
		try {
			String actual = driver.findElement(By.xpath(object)).getText();
			String expected = String.valueOf((int) Float.parseFloat(data));
			if (actual.trim().toLowerCase().startsWith(expected.trim().toLowerCase())) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " --not starting with " + actual + " -- " + expected;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verifies any CSS attribute of an element located by id
	 * @param object - String (xpath of element)
	 * @param data - CSS Attribute to be verified|Value
	 * @return - Pass if attribute value is as expected
	 */
	public String verifyCSSAttributeByID(String object, String data) {
	
		System.out.println("Verifying the attribute");
		try {
			String actual = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			System.out.println(str[0]);
			System.out.println(str[1]);
			actual = driver.findElement(By.id(object)).getCssValue(str[0]);
			System.out.println(actual);
			if (actual.contains(str[1])) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + "CSS attribute is not as expected " + "Actual--> " + actual + "Expected--> " + str[1];
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to verify css attribute " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verifies links present on a page
	 * @param object - optional
	 * @param data - Links separated by '|'
	 * @return - Pass if all links are present
	 */
	public String verifyLinksPresence(String object, String data) {
	
		System.out.println("Verifying that links are present");
		try {
			List<WebElement> Links_present = driver.findElements(By.tagName("a"));
			String str[] = data.split(Constants.DATA_SPLIT);
			for (int j = 0; j < Links_present.size(); j++) {
				System.out.println(Links_present.get(j).getText());
			}
			for (int i = 0; i < str.length; i++) {
				boolean flagCheck = false;
				for (int j = 0; j < Links_present.size(); j++) {
					if (Links_present.get(j).getText().contains(str[i])) {
						flagCheck = true;
						break;
					}
				}
				if (flagCheck == false) {
					return Constants.KEYWORD_FAIL + "Unable to find link -->" + str[i];
				}
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to verify links presence " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verifies the column contents are sorted in ascending order
	 * @param object - optional
	 * @param data - optional
	 * @return - Pass if column is sorted in ascending order
	 */
	public String verifyListIsSortedAscending(String object, String data) {
	
		try {
			System.out.println("Verifying sorting");
			int result = 0;
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			if (List_contents.size() < 2) {
				return Constants.KEYWORD_PASS + "Column contains less than 2 rows";
			}
			List<String> array = new ArrayList();
			for (int i = 0; i < List_contents.size(); i++) {
				array.add(List_contents.get(i).getText());
			}
			for (int i = 0; i < array.size() - 1; i++) {
				result = array.get(i).compareTo(array.get(i + 1));
				if (result > 0) {
					return Constants.KEYWORD_FAIL;
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to verify sorting " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method compares the contents of column with data provided in excel sheet and
	 *              arrayList (contains).
	 * @param object - xpath of col (td) of table
	 * @param data - particulars of col data to search
	 * @return - Execution Result
	 */
	public String VerifyTableColContainsByDataAndArrayList(String object, String data) {
	
		System.out.println("Searching the table contents");
		try {
			Thread.sleep(5000L);
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			if (returnChosenArrayList().size() == 0 && data == null) {
				return Constants.KEYWORD_FAIL;
			}
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				if (returnChosenArrayList().size() != 0) {
					if (!actual.trim().toLowerCase().contains(returnChosenArrayList().get(0).trim().toLowerCase())) {
						return Constants.KEYWORD_FAIL;
					}
				}
				if (data != null) {
					if (!actual.trim().toLowerCase().contains(data.trim().toLowerCase()))
						return Constants.KEYWORD_FAIL;
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method verifies the column contents are sorted in descending order
	 * @param object - optional
	 * @param data - optional
	 * @return - Pass if column is sorted in descending order
	 */
	public String verifyListIsSortedDescending(String object, String data) {
	
		try {
			System.out.println("Verifying that links are present");
			int result = 0;
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			if (List_contents.size() < 2) {
				return Constants.KEYWORD_PASS + "Column contains less than 2 rows";
			}
			List<String> array = new ArrayList();
			for (int i = 0; i < List_contents.size(); i++) {
				array.add(List_contents.get(i).getText());
			}
			for (int i = 0; i < array.size() - 1; i++) {
				System.out.println(array.get(i + 1));
				result = array.get(i).compareTo(array.get(i + 1));
				if (result < 0) {
					return Constants.KEYWORD_FAIL;
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to verify sorting " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method clicks on an element located by xpath using action builder class
	 * @param object - optional variable
	 * @param data - optional variable
	 * @return - Pass if element is clicked
	 */
	public String clickUsingActionByXpath(String object, String data) {
	
		try {
			Actions builder = new Actions(driver);
			builder.moveToElement(driver.findElement(By.xpath(object))).click().perform();;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to perform action " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description-This method click on a link text saved in arrayList by providing row number in data
	 * @param object - Optional
	 * @param data - Index of arraylist
	 * @return- Execution Result
	 */
	public String clickLinkTextValueFromIntegerIndexArrayList(String object, String data) {
	
		System.out.println("Clicking on link saved in arrayList ");
		try {
			WebElement ele = driver.findElement(By.linkText(returnChosenArrayList().get((int) Float.parseFloat(data))));
			JavascriptExecutor executr = (JavascriptExecutor) driver;
			executr.executeScript("arguments[0].click();", ele);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
	}
	
	/**
	 * @description- Method is saving current date into global var
	 * @param object - Optional
	 * @param data - Optional
	 * @return - Execution Result
	 */
	public String ReturnCurrentDate(String object, String data) {
	
		System.out.println("Calculating new date");
		try {
			String pattern = data;
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			format.format(new Date());
			Calendar cal = Calendar.getInstance();
			Date NewDate = cal.getTime();
			storeVar = format.format(NewDate);
			globalStorage.put("date", storeVar);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	/**
	 * @description- Method is concatenating given data with global vaariable
	 * @param object -Optional
	 * @param data - String needs to be concatenated
	 * @return-Execution Result
	 */
	public String concatenate(String object, String data) {
	
		System.out.println("concatenating two strings");
		try {
			String concatenated = data + storeVar;
			return concatenated;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to concatenate " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verify a particular value saved in arrayList
	 * @param object - Xpath of element
	 * @param data - Index of arrayList without pipeline at which value was saved
	 * @return - Execution Result
	 */
	public String verifyParticularValueFromArrayListWithIntegerIndexByXpath(String object, String data) {
	
		System.out.println("Verify particular value saved in arrayList");
		String expected, actual;
		try {
			expected = returnChosenArrayList().get((int) Float.parseFloat(data));
			System.out.println("expected" + expected);
			actual = driver.findElement(By.xpath(object)).getAttribute("value");
			System.out.println("actual" + actual);
			if (actual.trim().equalsIgnoreCase(expected.trim())) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " -- text not verified-- " + expected + " -- " + actual;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verify a text is not matched
	 * @param object - Xpath of element
	 * @param data - expected result
	 * @return - Execution Result
	 */
	public String verifyTextNotMatchByXpath(String object, String data) {
	
		System.out.println("Verifying the text");
		try {
			String actual = driver.findElement(By.xpath(object)).getText();
			String expected = data;
			if (!actual.trim().equalsIgnoreCase(expected)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " -- text matched" + actual + " -- " + expected;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description- Method will get a value from element and compare it with expected value
	 * @param object - Xpath of element
	 * @param data - data needs to be verified
	 * @return - Execution Result
	 */
	public String verifyTextContainsByValueXpath(String object, String data) {
	
		System.out.println("Verifying that labels contains text");
		try {
			String actual = driver.findElement(By.xpath(object)).getAttribute("value");
			String expected = data;
			System.out.println("expected" + expected);
			System.out.println("actual" + actual);
			if (actual.trim().toLowerCase().contains(expected.trim().toLowerCase())) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " --not contain text " + actual + " -- " + expected;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - Method will verify element is displayed
	 * @param object - Xpath of element
	 * @param data - Optional
	 * @return - Execution Result
	 */
	public String verifyElementDisplayedXpath(String object, String data) {
	
		System.out.println("Verifying if element is displayed");
		try {
			System.out.println("Size of element: " + driver.findElements(By.xpath(object)).size() + "  Text: "
			        + driver.findElement(By.xpath(object)).getText());
			if (driver.findElement(By.xpath(object)).isDisplayed()) {
				System.out.println("Element is displayed");
				return Constants.KEYWORD_PASS;
			} else {
				System.out.println("Element is not displayed");
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
	}
	
	/**
	 * @description - Method will verify element is not displayed
	 * @param object - Xpath of element
	 * @param data - Optional
	 * @return - Execution Result
	 */
	public String verifyElementNotDisplayedXpath(String object, String data) {
	
		System.out.println("Verifying if element is not displayed");
		try {
			if (!(driver.findElement(By.xpath(object)).isDisplayed())) {
				System.out.println("Element is not displayed");
				return Constants.KEYWORD_PASS;
			} else {
				System.out.println("Element is displayed");
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
	}
	
	/**
	 * @description - funtion creating the specified file if it is not allready existing
	 * @param object - optional variable
	 * @param data - file name with the extension and the relative path
	 * @return - execution result
	 */
	public String createFile(String object, String data) {
	
		try {
			File file = new File(data);
			System.out.println("Creating new file " + System.getProperty("user.dir") + data);
			if (!file.exists()) {
				if (file.createNewFile() == true) {
					System.out.println("File created successfully");
				} else {
					System.out.println("File creatinn error occured");
					return Constants.KEYWORD_FAIL;
				}
			} else {
				System.out.println("File not created because of allready existing");
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Exeption caught in creating new file " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - The method prints the ID of an element
	 * @param object - Xpath of element
	 * @param data - Optional Variable
	 * @return- Execution Result
	 */
	public String verifyAttribute(String object, String data) {
	
		try {
			System.out.println(driver.findElement(By.xpath(object)).getAttribute("id"));
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Exception caught in verification of substring " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description-This method click on a link text saved in arrayList by providing row number in data and
	 *                   without javascript
	 * @param object - Optional
	 * @param data - Index of arraylist
	 * @return- Execution Result
	 */
	public String clickLinkTextValueWithIntegerIndexArrayList(String object, String data) {
	
		System.out.println("Clicking on link saved in arrayList ");
		try {
			driver.findElement(By.linkText(returnChosenArrayList().get((int) Float.parseFloat(data)))).click();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
	}
	
	/**
	 * @description - Method will save index of webelements that have first occurence of consecutive numbers
	 *              in global arrayList "ConsecutiveElementListIndex"
	 * @param object - Xpath of elementlist
	 * @param data - Optional variable
	 * @return - Execution Result
	 */
	public String elementIndexWithFirstConsecutiveNum(String object, String data) {
	
		System.out.println("Calculating elements with first consecutive numbers");
		try {
			List<WebElement> ElementList = driver.findElements(By.xpath(object));
			ConsecutiveElementListIndex.clear();
			System.out.println("Size of element: " + ElementList.size());
			for (int ElementListIndex = 0; ElementListIndex < ElementList.size(); ElementListIndex++) {
				System.out.println("Text: " + ElementList.get(ElementListIndex).getText());
				if (ElementListIndex < ElementList.size() - 1) {
					int CurrentInt = Integer.parseInt(ElementList.get(ElementListIndex).getText());
					int NextInt = Integer.parseInt(ElementList.get(ElementListIndex + 1).getText());
					System.out.println("Current Int: " + CurrentInt);
					System.out.println("Next Int: " + NextInt);
					if (NextInt - CurrentInt == 1) {
						System.out.println("Numbers are consecutive..");
						if (ConsecutiveElementListIndex.contains(ElementListIndex)) {
							ConsecutiveElementListIndex.add(ElementListIndex + 1);
							System.out.println("Size of ConsecutiveElementListIndex after addition:  " + ConsecutiveElementListIndex.size());
						} else {
							ConsecutiveElementListIndex.add(ElementListIndex);
							ConsecutiveElementListIndex.add(ElementListIndex + 1);
							System.out.println("Size of ConsecutiveElementListIndex after addition:  " + ConsecutiveElementListIndex.size());
						}
					}
				}
			}
			System.out.println("Number of first consecutive number elements: " + ConsecutiveElementListIndex.size());
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - Method will click on each element of elementlist (index stored in arraylist
	 *              "ConsecutiveElementListIndex")
	 * @param object - Xpath of element
	 * @param data - Optional variable
	 * @return - Execution Result
	 */
	public String clickEachElementInArrayLIst(String object, String data) {
	
		System.out.println("Verifying if element is displayed");
		try {
			List<WebElement> ElementList = driver.findElements(By.xpath(object));
			for (int ElementListIndex = 0; ElementListIndex < ConsecutiveElementListIndex.size(); ElementListIndex++) {
				WebElement ele = ElementList.get(ConsecutiveElementListIndex.get(ElementListIndex));
				JavascriptExecutor executr = (JavascriptExecutor) driver;
				executr.executeScript("arguments[0].click();", ele);
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - Method will verify if first number of string is consecutive
	 * @param object - Xpath of element
	 * @param data - Optional
	 * @return - Execution Result
	 */
	public String verifyIfFirstConsectiveNumPresent(String object, String data) {
	
		System.out.println("Verifying if consecutive numbers are present in string");
		try {
			String ObtainedString = driver.findElement(By.xpath(object)).getText();
			String str = ObtainedString.split(" ")[0];
			int FirstInt = Integer.parseInt(str.substring(0, (str.length() / 2)));
			int SecondInt = Integer.parseInt(str.substring((str.length() / 2), str.length()));
			System.out.println("First Int: " + FirstInt + "  Second Int: " + SecondInt);
			if (SecondInt - FirstInt == 1) {
				System.out.println("Pattern Matched");
				return Constants.KEYWORD_PASS;
			} else {
				System.out.println("Pattern Not Matched");
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
	}
	
	/**
	 * @description - Method will click on number of element of elementlist specified in datasheet (index
	 *              stored in arraylist "ConsecutiveElementListIndex")
	 * @param object - Xpath of element
	 * @param data - Optional variable
	 * @return - Execution Result
	 */
	public String clickParticularElementInArrayLIst(String object, String data) {
	
		System.out.println("Verifying if element is displayed");
		try {
			List<WebElement> ElementList = driver.findElements(By.xpath(object));
			int CheckBoxesToCheck = (int) Float.parseFloat(data);
			for (int ElementListIndex = 0; ElementListIndex < CheckBoxesToCheck; ElementListIndex++) {
				WebElement ele = ElementList.get(ConsecutiveElementListIndex.get(ElementListIndex));
				JavascriptExecutor executr = (JavascriptExecutor) driver;
				executr.executeScript("arguments[0].click();", ele);
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - Method will save index of webelements that have first occurence of non consecutive
	 *              numbers in global arrayList "ConsecutiveElementListIndex"
	 * @param object - Xpath of elementlist
	 * @param data - Optional variable
	 * @return - Execution Result
	 */
	public String elementIndexWithNonConsecutiveNum(String object, String data) {
	
		System.out.println("Calculating elements with first non consecutive numbers");
		try {
			List<WebElement> ElementList = driver.findElements(By.xpath(object));
			ConsecutiveElementListIndex.clear();
			System.out.println("Size of element: " + ElementList.size());
			for (int ElementListIndex = 0; ElementListIndex < ElementList.size(); ElementListIndex++) {
				System.out.println("Text: " + ElementList.get(ElementListIndex).getText());
				if (ElementListIndex < ElementList.size() - 1) {
					int CurrentInt = Integer.parseInt(ElementList.get(ElementListIndex).getText());
					int NextInt = Integer.parseInt(ElementList.get(ElementListIndex + 1).getText());
					System.out.println("Current Int: " + CurrentInt);
					System.out.println("Next Int: " + NextInt);
					if (NextInt - CurrentInt != 1) {
						System.out.println("Numbers are non consecutive..");
						if (ConsecutiveElementListIndex.contains(ElementListIndex)) {
							ConsecutiveElementListIndex.add(ElementListIndex + 1);
							System.out.println("Size of ConsecutiveElementListIndex after addition:  " + ConsecutiveElementListIndex.size());
						} else {
							ConsecutiveElementListIndex.add(ElementListIndex);
							ConsecutiveElementListIndex.add(ElementListIndex + 1);
							System.out.println("Size of ConsecutiveElementListIndex after addition:  " + ConsecutiveElementListIndex.size());
						}
					}
				}
			}
			System.out.println("Number of first non consecutive number elements: " + ConsecutiveElementListIndex.size());
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - Method will verify if first number of string is non consecutive
	 * @param object - Xpath of element
	 * @param data - Optional variable
	 * @return - Execution Result
	 */
	public String verifyIfFirstNonConsectiveNumPresent(String object, String data) {
	
		System.out.println("Verifying if non consecutive numbers are present in string");
		try {
			String ObtainedString = driver.findElement(By.xpath(object)).getText();
			String str = ObtainedString.split(" ")[0];
			int FirstInt = Integer.parseInt(str.substring(0, (str.length() / 2)));
			int SecondInt = Integer.parseInt(str.substring((str.length() / 2), str.length()));
			System.out.println("First Int: " + FirstInt + "  Second Int: " + SecondInt);
			if (SecondInt == FirstInt) {
				System.out.println("Pattern Matched");
				return Constants.KEYWORD_PASS;
			} else {
				System.out.println("Pattern Not Matched");
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
	}
	
	/**
	 * @description - The method presses one of the following key from keyboard- TAB-(Enter 1 in datasheet),
	 *              ALT(Enter 2 in datasheet), SAPCE(Enter 3 in datasheet), ENTER(Enter 4 in datasheet),
	 *              CTRL(Enter 5 in datasheet). Specifiy the number of times you wish to enter the key
	 *              seperated by Pipeline in data sheet.
	 * @param object - Optional
	 * @param data - Keys to be pressed and iteration number
	 * @return - Execution Result
	 */
	public String clickParticularKeyUsingAction(String object, String data) {
	
		System.out.println("Verifying if element is displayed");
		action = new Actions(driver);
		try {
			int DesiredKey = Integer.parseInt(data.split(Constants.DATA_SPLIT)[0]);
			int KeyTimesPress = Integer.parseInt(data.split(Constants.DATA_SPLIT)[1]);
			for (int KeyPressIteration = 0; KeyPressIteration < KeyTimesPress; KeyPressIteration++) {
				switch (DesiredKey) {
					case 1:
						action.keyDown(Keys.TAB);
						action.keyUp(Keys.TAB);
						break;
					case 2:
						action.keyDown(Keys.ALT);
						action.keyUp(Keys.ALT);
						break;
					case 3:
						action.keyDown(Keys.SPACE);
						action.keyUp(Keys.SPACE);
						break;
					case 4:
						action.keyDown(Keys.ENTER);
						action.keyUp(Keys.ENTER);
						break;
					case 5:
						action.keyDown(Keys.CONTROL);
						action.keyUp(Keys.CONTROL);
						break;
					default:
						System.out.println("Wrong choice");
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - Method will select AMP Report from download drop down on Manage Job page
	 * @param object - Optional
	 * @param data - Value from drop down
	 * @return - Execution Result
	 */
	public String selectAmpReport(String object, String data) {
	
		System.out.println("Selecting AMP Report");
		try {
			String Xpath = "//td[contains(.,'" + returnChosenArrayList().get(0)
			        + "')]/following-sibling::td[contains(.,'Completed') and position()=5]/following-sibling::td[1]/select[@id='ddList']";
			String JobXpath = "//td[contains(.,'" + returnChosenArrayList().get(0)
			        + "')]/following-sibling::td[contains(.,'Completed') and position()=5]/preceding-sibling::td[6]";
			System.out.println("Job ID : " + driver.findElement(By.xpath(JobXpath)).getText());
			toc = driver.findElement(By.xpath(JobXpath)).getText();
			selectListByXpath(Xpath, data);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - Method will select AMP Report from download drop down on Manage Job page
	 * @param object - Optional
	 * @param data - Value from drop down
	 * @return - Execution Result
	 */
	public String verifyFileExist(String object, String data) {
	
		System.out.println("Navigating to the desired folder");
		try {
			String filePath = System.getProperty("user.home") + "\\Downloads\\" + toc.trim() + "_AmpReport.zip";
			File file = new File(filePath);
			if (file.exists()) {
				System.out.println("File exists");
				return Constants.KEYWORD_PASS;
			} else {
				System.out.println("File does not exists");
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
	}
	
	/**
	 * @description - Method will click keys from keyboard (virtual) ALT + S
	 * @param object - Optional variable
	 * @param data - Optional variable
	 * @return - Execution Result
	 */
	public String clickReleaseMultipleKeysUsingRobot(String nodeID, String data) {
	
		System.out.println("Clicking the specified key");
		try {
			Robot robot = new Robot();
			robot.keyPress(java.awt.event.KeyEvent.VK_ALT);
			Thread.sleep(1000L);
			robot.keyPress(java.awt.event.KeyEvent.VK_S);
			Thread.sleep(1000L);
			robot.keyRelease(java.awt.event.KeyEvent.VK_S);
			Thread.sleep(1000L);
			robot.keyRelease(java.awt.event.KeyEvent.VK_ALT);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to click" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - Method will verify element is not displayed
	 * @param object - ID of element
	 * @param data - Optional variable
	 * @return - Execution Result
	 */
	public String verifyElementNotDisplayedByID(String object, String data) {
	
		System.out.println("Verifying if element is not displayed");
		try {
			if (!(driver.findElement(By.id(object)).isDisplayed())) {
				System.out.println("Element is not displayed");
				return Constants.KEYWORD_PASS;
			} else {
				System.out.println("Element is displayed");
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object does not exist";
		}
	}
	
	/**
	 * @description- Method will verify element text is null
	 * @param object - Xpath of element
	 * @param data - Optional variable
	 * @return-Execution Result
	 */
	public String verifyElementAbsenceByXpath(String object, String data) {
	
		System.out.println("Verifying element text is not present");
		try {
			System.out.println("empty" + driver.findElement(By.xpath(object)).getText());
			if (driver.findElement(By.xpath(object)).getText().trim().equals("")) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to find element" + e.getMessage();
		}
	}
	
	/**
	 * @description - The method compares the contents of column with data provided in excel sheet and
	 *              arrayList (contains).
	 * @param object - xpath of col (td) of table
	 * @param data - particulars of col data to search
	 * @return - Execution Result
	 */
	public String VerifyTableColContainsIntegerByDataAndArrayList(String object, String data) {
	
		System.out.println("Searching the table contents");
		try {
			String str[] = data.split(Constants.DATA_SPLIT);
			Thread.sleep(5000L);
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				if (returnChosenArrayList().size() != 0) {
					if (!actual.trim().toLowerCase().contains(returnChosenArrayList().get(0).trim().toLowerCase())) {
						return Constants.KEYWORD_FAIL;
					}
				}
				if (data != null) {
					if (!actual.trim().toLowerCase().contains(str[0].trim().toLowerCase())) {
						return Constants.KEYWORD_FAIL;
					}
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method switches to multiple windows
	 * @param object - Xpath of element
	 * @param data - optional variable
	 * @return - Execution Result
	 */
	public String SwitchToMultipleWindowsByXpath(String object, String data) {
	
		System.out.println("Switching to new window");
		try {
			if (!(windowHandleListPrimary.contains(driver.getWindowHandle()))) {
				windowHandleListPrimary.add(driver.getWindowHandle());
			}
			int CurrentNoOfHandle = windowHandleListPrimary.size();
			clickUsingJSByXpath(object, data);
			waitTillNoOfWindows(CurrentNoOfHandle + 1);
			ArrayList<String> windowHandleListSecondary = new ArrayList<String>(driver.getWindowHandles());
			String HandleToSwitch = null;
			for (int SecondaryListIndex = 0; SecondaryListIndex < windowHandleListSecondary.size(); SecondaryListIndex++) {
				if (!(windowHandleListPrimary.contains(windowHandleListSecondary.get(SecondaryListIndex)))) {
					HandleToSwitch = windowHandleListSecondary.get(SecondaryListIndex);
					windowHandleListPrimary.add(HandleToSwitch);
					break;
				}
			}
			driver.switchTo().window(HandleToSwitch);
		} catch (Exception e) {
			System.out.println("exception caught :  " + e);
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method verifies that all selected options from a dropdown (if present) by Xpath does
	 *              not have null values
	 * @param object - xpath of dropdown
	 * @param data - optional variable
	 * @return - execution result
	 */
	public String verifyOptionsValueIsNotNullByXpath(String object, String data) {
	
		try {
			int index = 0;
			System.out.println("Verifying values of all list elements");
			Select dropDown = new Select(driver.findElement(By.xpath(object)));
			while (index < dropDown.getOptions().size()) {
				if (dropDown.getOptions().get(index).getAttribute("value") == null)
					return Constants.KEYWORD_FAIL + " All dropdown options does not have values";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not deselect from list. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method verifies that the text of an element does not contain particular string
	 * @param object - xpath of element
	 * @param data - string that should not be displayed
	 * @return - Execution Result
	 */
	public String verifyExpectedTextNotContainsByXpath(String object, String data) {
	
		System.out.println("Verifying that textfied is empty");
		try {
			String result = "";
			if (driver.findElement(By.xpath(object)).getText().contains(data)) {
				result = Constants.KEYWORD_FAIL;
			} else
				result = Constants.KEYWORD_PASS + " --contains some contain text ";
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method stores the col contents into an arrayList.
	 * @param object - xpath of col (td) of table
	 * @param data - optional
	 * @return - Execution Result
	 */
	public String storeTextIntoArraylist(String object, String data) {
	
		System.out.println("Storing the text from a list of webelements into an array list ");
		try {
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			for (int i = 0; i < List_contents.size(); i++) {
				returnChosenArrayList().add(List_contents.get(i).getText());
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method compares the contents of column with data stored in arrayList (contains)
	 * @param object - xpath of col (td) of table
	 * @param data - optional variable
	 * @return - Execution Result
	 */
	public String verifyTextInArraylistIsDisplayed(String object, String data) {
	
		System.out.println("Verifying the list contents are displayed on the page ");
		try {
			retriever.clear();
			if (returnChosenArrayList().size() == 0) {
				return Constants.KEYWORD_PASS + "The arraylist does not contains text";
			}
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			for (int i = 0; i < List_contents.size(); i++) {
				retriever.add(List_contents.get(i).getText());
			}
			if (!(returnChosenArrayList().size() == retriever.size())) {
				return Constants.KEYWORD_FAIL;
			}
			for (int i = 0; i < retriever.size(); i++) {
				if (returnChosenArrayList().get(i).trim().contains(retriever.get(i).trim())) {
					continue;
				} else {
					return Constants.KEYWORD_FAIL + " Not matching the text";
				}
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method compares the contents of column with data provided in excel sheet and
	 *              arrayList (contains)
	 * @param object - xpath of col (td) of table
	 * @param data - particulars of col data to search
	 * @return - Execution Result
	 */
	public String VerifyTableColNotContainsByDataAndArrayList(String object, String data) {
	
		System.out.println("Searching the table contents");
		try {
			String colContent = "";
			Thread.sleep(5000L);
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			if (List_contents.size() == 0) {
				return Constants.KEYWORD_PASS + " Table does not exist";
			}
			if (returnChosenArrayList().size() == 0 && data == null) {
				return Constants.KEYWORD_PASS + " The resources never existed";
			}
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				colContent = colContent + " " + actual;
			}
			System.out.println(colContent);
			for (int i = 0; i < returnChosenArrayList().size(); i++) {
				if (returnChosenArrayList().size() != 0) {
					if (colContent.trim().toLowerCase().contains(returnChosenArrayList().get(0).trim().toLowerCase())) {
						return Constants.KEYWORD_FAIL;
					}
				}
				if (data != null) {
					if (colContent.trim().toLowerCase().contains(data.trim().toLowerCase())) {
						return Constants.KEYWORD_FAIL;
					}
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - Method will click on element using xpath (and text as reference stored in arraylist)
	 * @param object - Xpath of element
	 * @param data - Index number of text in arraylist starting from zero
	 * @return - Execution Result
	 */
	public String clickUsingXpathArrayList(String object, String data) {
	
		System.out.println("Clicking on the element");
		try {
			System.out.println("Original data: " + data);
			System.out.println("data: " + (int) Float.parseFloat(data));
			System.out.println("Text:" + returnChosenArrayList().get((int) Float.parseFloat(data)));
			String ElementXpath = "//*[contains(.,'" + returnChosenArrayList().get((int) Float.parseFloat(data)).trim() + "')]" + object;
			System.out.println("Element Xpath: " + ElementXpath);
			WebElement ele = driver.findElement(By.xpath(ElementXpath));
			JavascriptExecutor executr = (JavascriptExecutor) driver;
			executr.executeScript("arguments[0].click();", ele);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - function to verify group of elemenets with same content
	 * @param object - xpath of the element group
	 * @param data - content of the element group
	 * @return - execution result
	 */
	public String multiVerify(String object, String data) {
	
		try {
			List<WebElement> elementGroup = driver.findElements(By.xpath(object));
			System.out.println("Verifying Multiple elements");
			for (WebElement element : elementGroup) {
				if (!(element.getText().contains(data))) {
					System.out.println("Miss match found");
					return Constants.KEYWORD_FAIL;
				}
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Exception caught in multiple element verification " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - The method verifies that a list has all element define in sheet by ID
	 * @param object - ID of element
	 * @param data - Optional Variable
	 * @return - execution result
	 */
	//
	public String verifyAllListElementsByXpath(String object, String data) {
	
		System.out.println("Verifying the selection of the list");
		try {
			WebElement droplist = driver.findElement(By.xpath(object));
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
			int flag = 1;
			int drpdwnSize = returnChosenArrayList().size() + 1;
			if (drpdwnSize != droplist_cotents.size()) {
				return Constants.KEYWORD_FAIL + "- size of lists do not match";
			}
			for (int i = 1; i < droplist_cotents.size(); i++) {
				if (droplist_cotents.get(i).getText().contains(returnChosenArrayList().get(i - 1))) {
					flag += 1;
					System.out.println("r" + flag);
				} else
					flag = 0;
				System.out.println("s" + flag);
			}
			if (flag == droplist_cotents.size()) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + "- Element not found - ";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
	}
	
	/**
	 * @description-Method will select dropdown value based on index
	 * @param object - xpath of dropdown
	 * @param data - optional
	 * @return-Execution Result
	 */
	public String selectList2ByIndex(String object, String data) {
	
		System.out.println("Selecting from list");
		try {
			Select droplist = new Select(driver.findElement(By.xpath(object)));
			droplist.selectByIndex((int) Float.parseFloat(data));
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method scrolls to the bottom of the page.
	 * @param object - optional varible
	 * @param data - optional variable
	 * @return - Execution Result
	 */
	public String scrollToBottom(String object, String data) {
	
		System.out.println(" Scrolling");
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to scroll. Check if its open " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method unchecks the checkbox and stores contents of first row into an arrayList
	 * @param object - xpath of first row (tr) of table
	 * @param data - optional variable
	 * @return - Execution Result
	 */
	public String uncheckAndStoreResourceInArrayList(String object, String data) {
	
		System.out.println("Storing the unchecked resource into an arraylist ");
		try {
			String typeXpath = null;
			String checkBoxXpath = null;
			String descXpath = null;
			String result = null;
			if (driver.findElement(By.xpath(object)) == null) {
				return Constants.KEYWORD_PASS + " No resources are attached with the procedure to be removed";
			}
			checkBoxXpath = object + "/td[1]/input";
			result = clickUsingJSByXpath(checkBoxXpath, data);
			if (!result.equalsIgnoreCase("Pass")) {
				return result;
			}
			typeXpath = object + "/td[2]";
			result = storeTextByxPath(typeXpath, data);
			if (!result.equalsIgnoreCase("Pass")) {
				return result;
			}
			descXpath = object + "/td[3]/b";
			result = storeTextByxPath(descXpath, data);
			if (!result.equalsIgnoreCase("Pass")) {
				return result;
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verifies that the contents of column does not match with data stored in
	 *              arrayList (contains)
	 * @param object - xpath of col (td) of table
	 * @param data - optional variable
	 * @return - Execution Result
	 */
	public String verifyTextInArraylistIsNotDisplayed(String object, String data) {
	
		System.out.println("Verifying the list contents are not displayed on the page ");
		try {
			retriever.clear();
			if (returnChosenArrayList().size() == 0) {
				return Constants.KEYWORD_PASS + "The arraylist does not contains text";
			}
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			if (List_contents.size() == 0) {
				return Constants.KEYWORD_PASS + " The table does not exist";
			}
			for (int i = 0; i < List_contents.size(); i++) {
				retriever.add(List_contents.get(i).getText());
				System.out.println(List_contents.get(i).getText());
			}
			System.out.println(returnChosenArrayList().get(1));
			for (int i = 0; i < retriever.size(); i++) {
				if (returnChosenArrayList().get(1).trim().contains(retriever.get(i).trim())) {
					return Constants.KEYWORD_FAIL + " The resource is not removed from the task card";
				}
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method enters the text into the input field
	 * @param object - Xpath of the element
	 * @param data - Text to be entered
	 * @return - Execution Result
	 */
	public String writeFileNameByXpath(String object, String data) {
	
		System.out.println("Writing in text box");
		try {
			driver.findElement(By.xpath(object)).sendKeys(System.getProperty("user.dir") + data);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method switches to a newly opened indow after clicking on an element
	 * @param object - Xpath of element
	 * @param data - Optional Variable
	 * @return - execution result
	 */
	public String switchToWindowByXpath(String object, String data) {
	
		System.out.println("Switching to new window");
		try {
			String Parenthandle = driver.getWindowHandle();
			Set<String> HandlesBeforeClick = driver.getWindowHandles();
			int CurrentNoOfHandle = HandlesBeforeClick.size();
			driver.findElement(By.xpath((object))).click();
			waitTillNoOfWindows(CurrentNoOfHandle + 1);
			Set<String> handle = driver.getWindowHandles();
			if (handle.contains(Parenthandle))
				handle.remove(Parenthandle);
			for (String winHandle : handle) {
				driver.switchTo().window(winHandle);
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - switching to a newly opened window
	 * @param object - Optional variable
	 * @param data - Optional Variable
	 * @return - execution result
	 */
	public String SwitchToWindow(String object, String data) {
	
		System.out.println("Switching to new window");
		try {
			String last_opened = null;
			Set<String> windows = driver.getWindowHandles();
			Iterator<String> iter = windows.iterator();
			while (iter.hasNext()) {
				last_opened = iter.next();
			}
			driver.switchTo().window(last_opened);
		} catch (Exception e) {
			System.out.println("exception caught :  " + e);
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - uploading a file based on the xpath of the element provided
	 * @param object - xpath of the file input
	 * @param data - path of the file
	 * @return - execution result
	 */
	public String uploadFileByXpath(String object, String data) {
	
		System.out.println("Writing in text box");
		try {
			driver.findElement(By.xpath(object)).sendKeys(System.getProperty("user.dir") + data);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method searches if a row in the table does not contain the expected data
	 * @param object - xpath of row (tr) of table
	 * @param data - particulars of row data to search separated by pipeline
	 * @return - Execution Result
	 */
	public String verifyListItemPresentByData(String object, String data) {
	
		System.out.println("Searching in the table");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			System.out.println("extracting");
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			int flagStr = 0;
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < str.length; expectedArr++) {
					if (str.length == 0) {
						break;
					} else {
						if ((actual.trim().toLowerCase().contains(str[expectedArr].trim().toLowerCase()))) {
							flagStr += 1;
						}
					}
				}
				if (flagStr == List_contents.size()) {
					result = Constants.KEYWORD_PASS;
				} else
					result = Constants.KEYWORD_FAIL + "Matching item is not found";
			}
			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	/**
	 * @description - function storing the object containing test into global storage by name
	 * @param object - xpath of the element
	 * @param data - key name
	 * @return - execution result
	 */
	public String addGlobalVarFromDrpdwn(String object, String data) {
	
		try {
			Select droplist = new Select(driver.findElement(By.xpath(object)));
			String value = droplist.getFirstSelectedOption().getText();
			System.out.println("Storing the value: " + value + "into global storage by key " + data);
			globalStorage.put(data, value);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Exception caught " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - The method verifies that no. of rows should not be greater than expected and verifies
	 *              the title of search results in that case
	 * @param object - Xpath of the column (td)
	 * @param data - Expected number of rows|Xpath of the title
	 * @return - Execution Result
	 */
	public String verifyNumberOfRowsNotGreaterThan(String object, String data) {
	
		System.out.println("Verifying the table rows are less than expected  ");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			String TitleXpath = "//span[@id='chromeTitle']";
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			int Expected = (int) Float.parseFloat(str[0]);
			waitTillVisibilityOfElementList(List_contents);
			if (List_contents.size() > Expected) {
				return Constants.KEYWORD_FAIL + " The table consists of rows that are greater in number than expected";
			} else if (List_contents.size() >= Expected) {
				result = verifyMultipleTextByXpath(TitleXpath, str[1]);
				if (result == Constants.KEYWORD_PASS) {
					return Constants.KEYWORD_PASS;
				}
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verifies the javascript variable value
	 * @param object - optional variable
	 * @param data - Variable name|Expected value of the variable
	 * @return - Execution Result
	 */
	public String verifyJavascriptVariableValue(String object, String data) {
	
		System.out.println(" Verifying variable value ");
		try {
			String result = null;
			String str[] = data.split(Constants.DATA_SPLIT);
			String varName = str[0];
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Object val = js.executeScript("var getValue=function(){return " + varName + "};return getValue() ;");
			System.out.println("Result: " + val.toString());
			if (!val.toString().equalsIgnoreCase(str[1])) {
				return Constants.KEYWORD_FAIL + " Variable value is not as expected ";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to verify variable value " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method stores selected option fron drop down in the specified arraylist
	 * @param object - string (Xpath of dropdown)
	 * @param data - string
	 * @return - PASS, if driver is able to find selected dropdown and able to store it in the arraylist
	 */
	public String storeSelectedOptionValueByXpath(String object, String data) {
	
		System.out.println("Storing value of first selected option ");
		try {
			Select DropDown = new Select(driver.findElement(By.xpath(object)));
			String SelectedText = DropDown.getFirstSelectedOption().getAttribute("value");
			returnChosenArrayList().add(SelectedText);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to find dropdown" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method enters the text into the input field
	 * @param object - Xpath of the element
	 * @param data - Text to be entered
	 * @return - Execution Result
	 */
	public String sendTextByXpath(String object, String data) {
	
		System.out.println("Writing in text box");
		try {
			driver.findElement(By.xpath(object)).sendKeys(data);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - function for verifying the presence of text in the given xpath containing dom element
	 * @param object - optional variable
	 * @param data - words to be verified separated by pipeline e.g. storedkeyname|occurence|newkeyname
	 * @return - execution result
	 */
	public String verifyMultipleTextByXpath(String object, String data) throws IOException {
	
		System.out.println("Navigating to Text");
		try {
			int flag = 0;
			String str[] = data.split(Constants.DATA_SPLIT);
			List<WebElement> title = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(title);
			String expected = null;
			String actual = null;
			System.out.println("Actual ElementList Size: " + title.size());
			System.out.println("Expected ElementList Size: " + str.length);
			for (int i = 0; i < title.size(); i++) {
				int flagFail = 1;
				for (int j = 0; j < str.length; j++) {
					expected = str[j];
					actual = title.get(i).getText();
					if (actual.trim().toLowerCase().contains(expected.trim().toLowerCase())) {
						System.out.println("expected" + expected);
						System.out.println("actual" + actual);
						flag += 1;
						flagFail = 0;
						break;
					}
				}
				// if (flagFail == 1)
				// break;
			}
			if (flag == str.length)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + actual + " does not match - " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " not able to match" + e.getMessage();
		}
	}
	
	/**
	 * @description - Method will verify whether actual data is as expected
	 * @param object - Xpath of element
	 * @param data - expected data (integer)
	 * @return - Execution Result
	 */
	public String verifyIntTextByXpath(String object, String data) {
	
		System.out.println("Verifying the text");
		try {
			String actual = driver.findElement(By.xpath(object)).getText();
			String expected = String.valueOf((int) Float.parseFloat(data));
			if (actual.trim().equals(expected.trim())) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " --not starting with " + actual + " -- " + expected;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description- Method will verify substring of actual string before a string mentioned in data
	 * @param object - Xpath of element
	 * @param data - expected data
	 * @return- Execution Result
	 */
	public String verifyTextUsingSubstringBeforeByXpath(String object, String data) {
	
		System.out.println("Verifying the text");
		try {
			String actual = driver.findElement(By.xpath(object)).getText();
			System.out.println("Actual String: " + actual);
			String actualToCompare = actual.substring(0, actual.indexOf(data.split(Constants.DATA_SPLIT)[0]) - 1);
			System.out.println("Actual String To Compare: " + actualToCompare);
			String expected = data.split(Constants.DATA_SPLIT)[1];
			System.out.println("Expected String To Compare: " + expected);
			if (actualToCompare.trim().equals(expected.trim())) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " --not starting with " + actualToCompare + " -- " + expected;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description- Method will verify that a checkBox is unchecked by Xpath
	 * @param object - Xpath of element
	 * @param data - Optional variable
	 * @return - Execution Result
	 */
	//
	public String verifyCheckBoxUncheckedByXpath(String object, String data) {
	
		System.out.println("Verifying checkbox unchecked");
		try {
			String checked = driver.findElement(By.xpath(object)).getAttribute("checked");
			if (checked == null)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " - Not selected";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find checkbox";
		}
	}
	
	/**
	 * @description- Method will verify whether the element is disabled or not
	 * @param object - Id of element
	 * @param data - Optional variable
	 * @return - Execution Result
	 */
	public String verifyDisabledAttributeByID(String object, String data) {
	
		System.out.println("Verifying that state is disabled");
		try {
			WebElement element = driver.findElement(By.id(object));
			if (!(element.getAttribute("disabled") == null)) {
				System.out.println("state is disabled");
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + "State is Enabled";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description- Method is saving current date into global var
	 * @param object - Optional variable
	 * @param data - Optional variable
	 * @return - Execution Result
	 */
	public String addGlobalVarintoArrayList(String object, String data) {
	
		System.out.println("Calculating new date");
		try {
			returnChosenArrayList().add(storeVar);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	/**
	 * @description- Method will verify an element stored in arraylist is not present in tabel
	 * @param object - Xpath of element
	 * @param data - Optional
	 * @return - Execution Result
	 */
	public String selectTaskCard(String object, String data) {
	
		System.out.println("Verifying element is found...:");
		try {
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(List_contents);
			int flagContainer = 0;
			int flagStr = 0;
			for (int i = 0; i < List_contents.size(); i++) {
				String actual = List_contents.get(i).getText();
				for (int expectedArr = 0; expectedArr < returnChosenArrayList().size(); expectedArr++) {
					if (returnChosenArrayList().size() == 0) {
						break;
					} else {
						System.out.println("actual" + actual);
						System.out.println("arraylist" + returnChosenArrayList().get(expectedArr).trim().toLowerCase());
						if (actual.trim().toLowerCase().equals(returnChosenArrayList().get(expectedArr).trim().toLowerCase())) {
							flagContainer = 0;
							i = i + 1;
							driver.findElement(By.xpath("//table[@id='docResults']/tbody/tr[" + i + "]/td[1]/input")).click();
							break;
						} else
							flagContainer += 1;
					}
				}
				flagStr += 1;
			}
			if (flagContainer > 0 && flagStr == List_contents.size()) {
				return Constants.KEYWORD_FAIL + "Matching row is not found";
			} else
				return Constants.KEYWORD_PASS + "Matching row is found";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to find element" + e.getMessage();
		}
	}
	
	/**
	 * @description - Method will select AMP Report from download drop down on Manage Job page
	 * @param object - Optional
	 * @param data - Value from drop down
	 * @return - Execution Result
	 */
	public String verifyExportFileExist(String object, String data) {
	
		System.out.println("Navigating to the desired folder");
		try {
			String filePath = System.getProperty("user.home") + "\\Downloads\\" + toc.trim() + data;
			System.out.println(filePath);
			File file = new File(filePath);
			if (file.exists()) {
				System.out.println("File exists");
				return Constants.KEYWORD_PASS;
			} else {
				System.out.println("File does not exists");
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verifies that multiple options cannot be selected in dropdown
	 * @param object - String (xpath of dropdown)
	 * @param data
	 * @return - Pass if dropdown does not support multiple selections
	 */
	public String verifySelectNotSupportsMultipleByXpath(String object, String data) {
	
		System.out.println("Verifying that dropdown supports multiple options select ");
		try {
			WebElement dropdown = driver.findElement(By.xpath(object));
			Select se = new Select(dropdown);
			if (se.isMultiple()) {
				return Constants.KEYWORD_FAIL + "Dropdown is multiselect";
			} else {
				return Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verify a particular value saved in arrayList
	 * @param object - Xpath of element
	 * @param data - Index of arrayList at which value was saved separated by pipeline
	 * @return - Execution Result
	 */
	public String verifyParticularValueFromArrayListContainsByXpath(String object, String data) {
	
		System.out.println("Verify particular value saved in arrayList");
		String expected, actual;
		try {
			int flag = 0;
			List<WebElement> elementsList = driver.findElements(By.xpath(object));
			String str[] = data.split(Constants.DATA_SPLIT);
			for (int strIndex = 1; strIndex <= str.length; strIndex++) {
				expected = returnChosenArrayList().get(Integer.parseInt(str[strIndex - 1]));
				for (int elementListIndex = 1; elementListIndex <= elementsList.size(); elementListIndex++) {
					actual = elementsList.get(elementListIndex - 1).getText();
					if (actual.trim().contains(expected.trim())) {
						flag += 1;
					}
				}
				if (flag != strIndex) {
					return Constants.KEYWORD_FAIL + " -- text not verified-- " + expected + " -- " + elementsList.get(0).getText();
				}
			}
			if (flag == str.length) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to verify text" + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verify if a particular value saved in arrayList is not present
	 * @param object - Xpath of element
	 * @param data - Index of arrayList at which value was saved separated by pipeline
	 * @return - Execution Result
	 */
	public String verifyParticularValueFromArrayListNotContainsByXpath(String object, String data) {
	
		System.out.println("Verify if particular value saved in arrayList is not present");
		String expected, actual;
		try {
			int flag = 0;
			List<WebElement> elementsList = driver.findElements(By.xpath(object));
			String str[] = data.split(Constants.DATA_SPLIT);
			for (int strIndex = 1; strIndex <= str.length; strIndex++) {
				expected = returnChosenArrayList().get(Integer.parseInt(str[strIndex - 1]));
				for (int elementListIndex = 1; elementListIndex <= elementsList.size(); elementListIndex++) {
					actual = elementsList.get(elementListIndex - 1).getText();
					if (actual.trim().contains(expected.trim())) {
						flag += 1;
					}
				}
				if (flag == strIndex) {
					return Constants.KEYWORD_FAIL + " -- text is present-- ";
				}
			}
			if (flag == 0) {
				return Constants.KEYWORD_PASS;
			} else
				return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to verify text" + e.getMessage();
		}
	}
	
	/**
	 * @description - function checking the sorting functionality of a search result
	 * @param object - xpath of the element group
	 * @param data - type of the of the element group (Example : INTIGER,STRING)
	 * @return - execution result
	 */
	@SuppressWarnings("unchecked")
	public String validateSorting(String object, String data) {
	
		try {
			System.out.println("Validating the Sorting");
			List<WebElement> list = driver.findElements(By.xpath(object));
			ArrayList<String> taskCodes = new ArrayList<String>();
			ArrayList<String> unSortedCodes = new ArrayList<String>();
			for (WebElement element : list) {
				taskCodes.add(element.getText());
			}
			unSortedCodes = (ArrayList<String>) taskCodes.clone();
			Collections.sort(taskCodes);
			if (unSortedCodes.equals(taskCodes)) {
				System.out.println("Sorting Validated Successfully");
				return Constants.KEYWORD_PASS;
			}
			System.out.println("Sorting Validation failed");
			return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			System.out.println("Exception caught in the validation of the sorting " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @descriptio - storing the selected drop down value into the map by key provided
	 * @param object - xpath of the dropdown
	 * @param data - key to the value
	 * @return - execution result
	 */
	public String storeDropDownValueToMap(String object, String data) {
	
		try {
			Select dropDown = new Select(driver.findElement(By.xpath(object)));
			System.out.println("Storing the Drop down value");
			String value = dropDown.getFirstSelectedOption().getText();
			globalStorage.put(data, value);
			if (!value.isEmpty()) {
				System.out.println("Value " + value + " Stored into map by key " + data);
				return Constants.KEYWORD_PASS;
			}
			return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			System.out.println("Exception caught in storing " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - function splitting by character occurence and storing the splitted value into map
	 * @param object - optional variable
	 * @param data - storedkeyname|occurence|newkeyname
	 * @return - execution result
	 */
	public String splitByNthOccurence(String object, String data) {
	
		try {
			System.out.println("Splitting the String");
			// int occurence =
			// Integer.parseInt(data.split(Constants.DATA_SPLIT)[1]);
			String originalVal = globalStorage.get(data.split(Constants.DATA_SPLIT)[0]);
			String splitVal = originalVal.substring(originalVal.indexOf('-') + 1);
			System.out.println("Split Value " + splitVal.trim());
			globalStorage.put(data.split(Constants.DATA_SPLIT)[2], splitVal.trim());
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Exception caught in splitting of the string " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	public String verifyNumberOfRowsIsAsExpectedArrayList(String object, String data) {
	
		System.out.println("Verifying the table rows are less than expected  ");
		try {
			String result = null;
			// String str[] = data.split(Constants.DATA_SPLIT);
			// String TitleXpath = "//span[@id='chromeTitle']";
			List<WebElement> List_contents = driver.findElements(By.xpath(object));
			int Expected = (int) Float.parseFloat(returnChosenArrayList().get(0));
			// waitTillVisibilityOfElementList(List_contents);
			if (List_contents.size() != Expected) {
				return Constants.KEYWORD_FAIL + " The number of search results obtained are not as expected";
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verifies that no. of rows should be equal to the value stored in the array
	 *              list
	 * @param object - Xpath of the row (tr)
	 * @param data - optional variable
	 * @return - Execution Result
	 */
	public String verifyTitleContainsArrayList(String object, String data) {
	
		System.out.println("Verifying the title  ");
		try {
			String result = null;
			if ((int) Float.parseFloat(returnChosenArrayList().get(0)) != 0) {
				result = verifyParticularValueFromArrayListContainsByXpath(object, data);
				if (result == Constants.KEYWORD_PASS) {
					return Constants.KEYWORD_PASS;
				} else {
					return result;
				}
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verifies that no. of rows should be equal to the value stored in the array
	 *              list
	 * @param object - Xpath of the row (tr)
	 * @param data - optional variable
	 * @return - Execution Result
	 */
	public String verifyUpdatedWorkSummaryByXpath(String object, String data) {
	
		System.out.println("Verifying that labels contains text");
		try {
			String actual = driver.findElement(By.xpath(object)).getText();
			int value = ((int) Float.parseFloat(returnChosenArrayList().get((int) Float.parseFloat(data))));
			value = value + 1;
			String expected = Integer.toString(value);
			System.out.println("expected" + expected + "expected.trim--" + expected.trim());
			if (actual.trim().equalsIgnoreCase(expected.trim())) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " --not contain text " + actual + " -- " + expected;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method selects an option from a dropdown by its attribute value * @param object -
	 *              string
	 * @param data - option from the drop down list or Random if random value is to be selected
	 * @return - PASS, if dropdown is able to select the value
	 */
	public String selectListByValueFirstAlsoXpath(String object, String data) {
	
		System.out.println("Selecting from list");
		Select dropDown = new Select(driver.findElement(By.xpath(object)));
		List<WebElement> droplist_cotents = dropDown.getOptions();
		int index = -1;
		try {
			if (data.equals(Constants.RANDOM_VALUE)) {
				// logic to find a random value in list
				System.out.println("Selecting Random Value from list");
				Random num = new Random();
				// always create index excluding zero,
				// not select a blank value
				index = num.nextInt(droplist_cotents.size());
			} else {
				System.out.println("Selecting given Value from list");
				for (int i = 0; i < droplist_cotents.size(); i++) {
					if (data.equalsIgnoreCase(droplist_cotents.get(i).getAttribute("value"))) {
						index = i;
						break;
					}
				}
			}
			String selectedVal = droplist_cotents.get(index).getAttribute("value");
			dropDown.selectByValue(selectedVal);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - Method will select AMP Report from download drop down on Manage Job page
	 * @param object - Optional
	 * @param data - Value from drop down
	 * @return - Execution Result
	 */
	public String verifyFileExistInDownloads(String object, String data) {
	
		System.out.println("Navigating to the desired folder");
		try {
			System.out.println("data is::" + data);
			String filePath = System.getProperty("user.home") + "\\Downloads\\" + toc.trim() + data;
			System.out.println("filepath is::" + filePath);
			File file = new File(filePath);
			if (file.exists()) {
				System.out.println("File exists");
				return Constants.KEYWORD_PASS;
			} else {
				System.out.println("File does not exists");
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
	}
	
	/**
	 * @description - Method will verify .txt file name in downloads folder
	 * @param object - Optional
	 * @param data - partial name of file
	 * @return - Execution Result
	 */
	public String verifyTextFileInDownloads(String object, String data) {
	
		System.out.println("Navigating to the desired folder");
		try {
			System.out.println("data is::" + data);
			String filePath = System.getProperty("user.home") + "\\Downloads\\" + data + storeVar + ".txt";
			System.out.println("filepath is::" + filePath);
			File file = new File(filePath);
			if (file.exists()) {
				System.out.println("File exists");
				return Constants.KEYWORD_PASS;
			} else {
				System.out.println("File does not exists");
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
	}
	
	/**
	 * @description- Method will verify whether actual data is as expected
	 * @param object - Xpath of element
	 * @param data - expected data (integer)
	 * @return- Execution Result
	 */
	public String verifyIntTextByValueXpath(String object, String data) {
	
		System.out.println("Verifying the text");
		try {
			String actual = driver.findElement(By.xpath(object)).getAttribute("value");
			String expected = String.valueOf((int) Float.parseFloat(data));
			if (actual.trim().equals(expected.trim()))
				return Constants.KEYWORD_PASS;
			else {
				return Constants.KEYWORD_FAIL + " --not starting with " + actual + " -- " + expected;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - function generating random values
	 * @param object - optional variable
	 * @param data - the type of generating stringand the character limit of the generating value (Example :
	 *            NUMBER:45)
	 * @return - randomVlaue the random generated string
	 */
	public String generateRandomValues(String object, String data) {
	
		String randomValue = null;
		int randomValueLimit = Integer.parseInt(data.split(":")[1]);
		try {
			System.out.println("Gnerating the random values");
			switch (data.split(":")[0]) {
				case "NUMBER":
					randomValue = (String) RandomStringUtils.random(randomValueLimit, false, true);
					break;
				case "STRING":
					randomValue = RandomStringUtils.random(randomValueLimit, true, false);
					break;
				case "ALPHA_NUMERIC":
					randomValue = RandomStringUtils.random((randomValueLimit + 1) / 2, false, true);
					randomValue = randomValue + RandomStringUtils.random(randomValueLimit / 2, true, false);
					break;
				case "ASCII":
					randomValue = RandomStringUtils.randomAscii(randomValueLimit);
					break;
			}
		} catch (Exception e) {
			System.out.println("Random value generating error " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
		System.out.println("The random value is generated successfully" + randomValue);
		return randomValue;
	}
	
	/**
	 * @description - function for verifying the presence of multiple text(saved in arraylist) in the given
	 *              xpath
	 * @param object - xpath of element list
	 * @param data - Optional String
	 * @return - execution result
	 */
	public String verifyMultipleTextByXpathArrayList(String object, String data) throws IOException {
	
		System.out.println("Navigating to Text");
		try {
			int flag = 0;
			List<WebElement> title = driver.findElements(By.xpath(object));
			waitTillVisibilityOfElementList(title);
			String expected = null;
			String actual = null;
			System.out.println("Actual ElementList Size: " + title.size());
			System.out.println("Expected ElementList Size: " + returnChosenArrayList().size());
			for (int j = 0; j < returnChosenArrayList().size(); j++) {
				boolean flagFail = true;
				for (int i = 0; i < title.size(); i++) {
					expected = returnChosenArrayList().get(j);
					actual = title.get(i).getText();
					if (actual.trim().toLowerCase().contains(expected.trim().toLowerCase())) {
						System.out.println("expected" + expected);
						System.out.println("actual" + actual);
						flag += 1;
						flagFail = false;
						break;
					}
				}
				if (flagFail == true)
					break;
			}
			if (flag == returnChosenArrayList().size())
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + actual + " does not match - " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Exception Caught:" + e.getMessage();
		}
	}
	
	/**
	 * @description - Method will verify if element is displayed or not
	 * @param object - Xpath of element
	 * @param data - Index number of text in arraylist starting from zero
	 * @return - Execution Result
	 */
	public String verifyElementDisplayedByXpathArrayList(String object, String data) {
	
		System.out.println("Clicking on the element");
		try {
			System.out.println("Original data: " + data);
			System.out.println("data: " + (int) Float.parseFloat(data));
			System.out.println("Text:" + returnChosenArrayList().get((int) Float.parseFloat(data)));
			String elementXpath = "//*[contains(.,'" + returnChosenArrayList().get((int) Float.parseFloat(data)).trim() + "')]" + object;
			waitTillElementIsVisible(elementXpath, "xpath");
			System.out.println("Element Xpath: " + elementXpath);
			WebElement ele = driver.findElement(By.xpath(elementXpath));
			if (ele.isDisplayed()) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
	}
	
	/**
	 * @description - adding content to an allready existing file
	 * @param object - optional variable
	 * @param data - filename|content
	 * @return - executional result
	 */
	public static String addContentToFile(String object, String data) {
	
		try {
			String fileName = System.getProperty("user.dir") + globalStorage.get("fileName");
			String content = data;
			File file = new File(fileName);
			if (!file.exists()) {
				System.out.println("File not exist !!!");
				return Constants.KEYWORD_FAIL;
			}
			System.out.println("Adding content to the file " + fileName);
			FileWriter fileWritter = new FileWriter(fileName, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(content);
			bufferWritter.newLine();
			bufferWritter.close();
			System.out.println("Content added successfully");
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Exception caught in content adding " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - clearing the content of the file
	 * @param object - optional variable
	 * @param data - name of the file
	 * @return - execution result
	 */
	public static String clearFileContent(String object, String data) {
	
		try {
			System.out.println("Cleaning the file " + data);
			File file = new File(System.getProperty("user.dir") + data);
			if (!file.exists()) {
				System.out.println("File not exist !!!");
				return Constants.KEYWORD_FAIL;
			}
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.close();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Exception caught in clearing the file content " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - function saving the data into the global variable
	 * @param object - optional varible
	 * @param data - variable|value
	 * @return - execution result
	 */
	public static String setGlobalVar(String object, String data) {
	
		try {
			String variable = data.split(Constants.DATA_SPLIT)[0];
			String value = data.split(Constants.DATA_SPLIT)[1];
			System.out.println("Setting the global variable " + variable);
			globalStorage.put(variable, value);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Exception caught in setting the global variable " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - function switching to the frame by index number
	 * @param object - index number
	 * @param data - optional variable
	 * @return - execution result
	 */
	public String switchToFrameByIndex(String object, String data) {
	
		int index = Integer.parseInt(object);
		try {
			driver.switchTo().frame(index);
			System.out.println("Navigated to frame with index " + index);
			return Constants.KEYWORD_PASS;
		} catch (NoSuchFrameException e) {
			System.out.println("Unable to locate frame with id " + index + e.getStackTrace());
			return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			System.out.println("Unable to navigate to frame with id " + index + e.getStackTrace());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - The method verifies the length of the string is as expected
	 * @param object - xpath of the input field
	 * @param data - expected lengthof the string
	 * @return - exeution result
	 */
	public String verifyTextLengthByXpath(String object, String data) {
	
		System.out.println("Verifying that labels contains text");
		try {
			String actual = driver.findElement(By.xpath(object)).getAttribute("value");
			System.out.println("actual" + actual + "actual.trim--" + actual.trim());
			int expected = (int) Float.parseFloat(data);
			System.out.println("expected" + expected + "expected.trim--" + expected);
			if (actual.length() == expected)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " --not contain expected number of characters " + actual.length() + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @param object -@description-The method writes a specific date in a specific pattern by providing format
	 *            and number of days from current days in data sheet (separated by pipeline)
	 * @object-Xpath of element
	 * @param data -pattern of date
	 * @return- execution result
	 */
	public String calculateNewDateByXpath(String object, String data) {
	
		System.out.println("Calculating new date");
		try {
			String pattern = data.split(Constants.DATA_SPLIT)[0];
			System.out.println(pattern);
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			// formatting
			format.format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, Integer.parseInt(data.split(Constants.DATA_SPLIT)[1]));
			Date newDate = cal.getTime();
			String modifiedDate = format.format(newDate);
			storeVar = modifiedDate;
			driver.findElement(By.xpath(object)).sendKeys(modifiedDate);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	/**
	 * @description - function selecting a different option comapring to allready selected option from a
	 *              dropdown
	 * @param object - xpath of the dropdown
	 * @param data - allready selected option
	 * @return - execution result
	 */
	public String selectDifferentOption(String object, String data) {
	
		int attempts = 0;
		try {
			Select dropDown = new Select(driver.findElement(By.xpath(object)));
			List<WebElement> allOptions = dropDown.getOptions();
			System.out.println("Seletcing different option than " + data + " from the drop down");
			while (attempts < 2) {
				try {
					for (WebElement option : allOptions) {
						if (!(option.getText().contentEquals(data) || option.getAttribute("value").isEmpty() || option.getText().isEmpty())) {
							globalStorage.put("currentSelectedOption", option.getText());
							System.out.println("Selecting the option " + option.getText());
							dropDown.selectByVisibleText(option.getText());
							System.out.println(globalStorage.get("currentSelectedOption"));
							return Constants.KEYWORD_PASS;
						}
					}
					System.out.println("No different Element found !!!");
					return Constants.KEYWORD_FAIL;
				} catch (StaleElementReferenceException e) {
				}
				attempts++;
			}
			return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			System.out.println("Exception caught in different selection : " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description- Method will expand the tree node by given perticular level number
	 * @param object - Optional Variable
	 * @param data - Partial/complete fragment number of each level of fragment path separated by colon e.g.
	 *            12|12-00|12-00-001 for the fragment-- 12-00-001
	 * @return - Execution result
	 */
	public String expandTreeNodeByNumber(String object, String data) {
	
		String mainXpath = "//div[@id='Tree']/ul";
		System.out.println("expanding tree");
		try {
			String fragNumber[] = data.split(Constants.DATA_SPLIT);
			int plusCount = fragNumber.length;
			int i = 0;
			int j = 0;
			for (i = 0; i < plusCount; i++) {
				List<WebElement> listLi = driver.findElements(By.xpath(mainXpath + "/li"));
				int countLi = listLi.size();
				for (j = 0; j < countLi; j++) {
					int liNumber = j + 1;
					WebElement currentLi = driver.findElement(By.xpath(mainXpath + "/li[" + liNumber + "]/div/a"));
					String actual = currentLi.getText();
					if (actual.contains(fragNumber[i])) {
						WebElement clickplus = driver.findElement(By.xpath(mainXpath + "/li[" + liNumber + "]/div/img[@src='"
						        + CONFIG.getProperty("partialURL") + "/library/images/tree/plus.gif']"));
						JavascriptExecutor executr = (JavascriptExecutor) driver;
						executr.executeScript("arguments[0].click();", currentLi);
						if (!(i + 1 == plusCount)) {
							clickplus.click();
						}
						mainXpath = mainXpath + "/li[" + liNumber + "]/ul";
						break;
					}
				}
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to find Check if its open" + e.getMessage();
		}
	}
	
	/**
	 * @description- Method will expand the tree and check the checkbox next to the searched fragment
	 * @param object - Optional Variable
	 * @param data - Partial/complete fragment number of each level of fragment path separated by colon e.g.
	 *            12|12-00|12-00-001 for the fragment-- 12-00-001
	 * @return - Execution result
	 */
	public String expandTreeNodeByNumberDS(String object, String data) {
	
		String mainXpath = "//div[@id='Tree']/ul";
		System.out.println("expanding tree");
		try {
			String fragNumber[] = data.split(Constants.DATA_SPLIT);
			int plusCount = fragNumber.length;
			int i = 0;
			int j = 0;
			for (i = 0; i < plusCount; i++) {
				List<WebElement> listLi = driver.findElements(By.xpath(mainXpath + "/li"));
				int countli = listLi.size();
				for (j = 0; j < countli; j++) {
					int linumber = j + 1;
					WebElement currentli = driver.findElement(By.xpath(mainXpath + "/li[" + linumber + "]/div/a"));
					String actual = currentli.getText();
					if (actual.contains(fragNumber[i])) {
						if (!(i + 1 == plusCount)) {
							WebElement clickplus = driver.findElement(By.xpath(mainXpath + "/li[" + linumber + "]/div/img[@src='"
							        + CONFIG.getProperty("partialURL") + "/library/images/tree/plus.gif']"));
							clickplus.click();
						} else {
							JavascriptExecutor executr = (JavascriptExecutor) driver;
							executr.executeScript("arguments[0].click();", currentli);
						}
						mainXpath = mainXpath + "/li[" + linumber + "]/ul";
						break;
					}
				}
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to find Check if its open" + e.getMessage();
		}
	}
	
	/**
	 * @description- Method will verify whether actual data is as expected
	 * @param object - Xpath of element
	 * @param data - expected data (integer)
	 * @return- Execution Result
	 */
	public String verifyIntValuetByXpath(String object, String data) {
	
		System.out.println("Verifying the text");
		try {
			String actual = driver.findElement(By.xpath(object)).getAttribute("value");
			String expected = String.valueOf((int) Float.parseFloat(data));
			if (actual.trim().equals(expected.trim()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " --not equals to " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * @description - The method verifies the column contents are sorted in ascending order
	 * @param object -
	 * @param data -
	 * @return - Pass if column is sorted in ascending order
	 */
	public String verifyListIsSortedSelectAscending(String object, String data) {
	
		try {
			System.out.println("Verifying sorting");
			int result = 0;
			List<String> array = new ArrayList();
			for (int i = 0; i < returnChosenArrayList().size(); i++) {
				array.add(returnChosenArrayList().get(i));
			}
			for (int i = 0; i < array.size() - 1; i++) {
				result = array.get(i).compareTo(array.get(i + 1));
				if (result > 0)
					return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to verify sorting " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - function enter same data to a group of elements
	 * @param object - common xpath of the elements
	 * @param data - input data
	 * @return - execution result
	 */
	public String multiWriteByXpath(String object, String data) {
	
		try {
			List<WebElement> elements = driver.findElements(By.xpath(object));
			System.out.println("Entering data to Multiple elements");
			for (WebElement webElement : elements) {
				webElement.click();
				webElement.clear();
				webElement.sendKeys(data);
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Exception caught in entering multiple elements " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - Method will read the files inside the zip file
	 * @param object - Xpath of element
	 * @param data - Index number of text in arraylist starting from zero
	 * @return - Execution Result
	 */
	public String readingContentsOfZipFile(String object, String data) {
	
		System.out.println("Accessing the contents of Zip file");
		try {
			int flag = 0;
			String fileName, fileExtension = null;
			String folderZip = data.split(Constants.DATA_SPLIT)[0];
			System.out.println("data is::" + data);
			System.out.println("folder is::" + folderZip);
			String[] fileDetail = new String[data.split(Constants.DATA_SPLIT).length - 1];
			int Index = 0;
			for (int i = 1; i < data.split(Constants.DATA_SPLIT).length; i++) {
				fileDetail[Index] = data.split(Constants.DATA_SPLIT)[i];
				Index++;
			}
			String filePath = System.getProperty("user.home") + "\\Downloads\\" + toc.trim() + folderZip;
			System.out.println("filepath is::" + filePath);
			ZipFile zipFile = new ZipFile(filePath);
			for (int i = 0; i < fileDetail.length; i++) {
				Enumeration<? extends ZipEntry> entries = zipFile.entries();
				System.out.println("FileDetails: " + fileDetail[i]);
				fileExtension = fileDetail[i].split(";")[1];
				if (returnChosenArrayList().size() != 0) {
					fileName = returnChosenArrayList().get(Integer.parseInt(fileDetail[i].split(";")[0]));
				} else {
					fileName = fileDetail[i].split(";")[0];
				}
				System.out.println("FileName: " + fileName);
				while (entries.hasMoreElements()) {
					ZipEntry entry = entries.nextElement();
					System.out.println("Name of file: " + entry.getName());
					if (entry.getName().contains(fileName))
						if (entry.getName().contains(fileExtension)) {
							flag += 1;
							System.out.println("Flag: " + flag);
							break;
						}
				}
			}
			System.out.println("Flag Value: " + flag);
			System.out.println("File Detail Length: " + fileDetail.length);
			zipFile.close();
			if (flag == fileDetail.length)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
	}
	
	/**
	 * @description - Function to pause teh current thread for the specified time
	 * @param object - Optional Variable
	 * @param data - Integer value (Time to pause)
	 * @return - execution result
	 */
	public String pause(String object, String data) throws NumberFormatException, InterruptedException {
	
		System.out.println("Waiting for the specified amount of time...");
		try {
			long time = (long) Double.parseDouble(object);
			Thread.sleep(time * 1000L);
		} catch (Exception E) {
			return Constants.KEYWORD_FAIL + E.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// Function checking the alert presence and comparing the alert text with
	// input data
	public String alertPresenceAndCompareText(String object, String data) {
	
		System.out.println("Checking the alert presence and comparing the alert text");
		try {
			String[] temp_arr = data.split(Constants.DATA_SPLIT);
			String text = "";
			if (temp_arr[0].equalsIgnoreCase("true")) {
				if (isAlertPresent(object, data) == true) {
					System.out.println("Checking the alert text");
					text = temp_arr[1];
					Alert alt = driver.switchTo().alert();
					if (temp_arr[4].equalsIgnoreCase("DRAFT") || temp_arr[4].equalsIgnoreCase("PUBLISHED")) {
						text = text + ": " + temp_arr[2] + ", " + temp_arr[3] + ", " + temp_arr[4];
					} else {
						text = "";
					}
					if (alt.getText().contains(text)) {
						return Constants.KEYWORD_PASS;
					} else {
						System.out.println("Alert text mis-match");
						return Constants.KEYWORD_FAIL;
					}
				} else {
					return Constants.KEYWORD_FAIL;
				}
			} else {
				if (!(isAlertPresent(object, data) == true)) {
					return Constants.KEYWORD_PASS;
				} else {
					return Constants.KEYWORD_FAIL;
				}
			}
		} catch (Exception E) {
			return Constants.KEYWORD_FAIL + E.getMessage();
		}
	}
	
	/**
	 * @description - function matching with the substring of an element
	 * @param object - XPATH of the element
	 * @param data - starting_index-ending_index:match string (example : 0-2:2)
	 * @return - execution result
	 */
	public String verifySubString(String object, String data) {
	
		try {
			int startLimit = Integer.parseInt((data.split(":")[0]).split("-")[0]);
			int endLimit = Integer.parseInt((data.split(":")[0]).split("-")[1]);
			String subString = null;
			object = driver.findElement(By.xpath(object)).getText();
			System.out.println("Getting the substring from " + object + " with Start and End Limits " + startLimit + " " + endLimit);
			subString = object.substring(startLimit, endLimit);
			if (subString.contains(data.split(":")[1].trim())) {
				System.out.println("Verification Success");
				return Constants.KEYWORD_PASS;
			}
			System.out.println("Verification failure");
			return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			System.out.println("Exception caught in verification of substring " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - The method emulates the enter action
	 * @param object - String (xpath of element)
	 * @param data -
	 * @return - Pass if the action is performed
	 */
	public String virtualEnterPress(String object, String data) {
	
		System.out.println("Pressing a key virtually  ");
		try {
			// Actions builder = new Actions(driver);
			driver.findElement(By.xpath(object)).sendKeys(Keys.ENTER);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/******************************************************** OMP Keywords ***********************************************************/
	/**
	 * @description - function checking the order of the drop down options
	 * @param object - xpath of the drop down
	 * @param data - drop down options by preserving the order
	 * @return - execution result
	 */
	public String verifyDropdownOrder(String object, String data) {
	
		try {
			System.out.println("Verifying the Dropdown order");
			Select dropDown = new Select(driver.findElement(By.xpath(object)));
			List<WebElement> options = dropDown.getOptions();
			ArrayList<String> actualOptions = new ArrayList<String>();
			String[] expectedOptions = data.split(Constants.DATA_SPLIT);
			for (WebElement currentOption : options) {
				actualOptions.add(currentOption.getText());
			}
			if (Arrays.equals(expectedOptions, actualOptions.toArray())) {
				return Constants.KEYWORD_PASS;
			} else {
				System.out.println("Incorrect Option order !!!");
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			System.out.println("Exception caught " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - function verify the content of an input field
	 * @param object - xpath of the input field
	 * @param data - expected data in the inout field
	 * @return - execution result
	 */
	public String verifyTextinInputByXpath(String object, String data) {
	
		System.out.println("Verifying the text in input box");
		try {
			String actual = driver.findElement(By.xpath(object)).getAttribute("value");
			String expected = data;
			if (actual.equals(expected)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " Not matching the text-" + actual + "--" + expected;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();
		}
	}
	
	/**
	 * @description - function checking the absence of an attribute in an element
	 * @param object - xpath of the element
	 * @param data - attribute name
	 * @return - execution result
	 */
	public String verifyAttributeAbsenceByXpath(String object, String data) {
	
		System.out.println("Verifying the Attribute absence in an element");
		try {
			if (driver.findElement(By.xpath(object)).getAttribute(data) == null) {
				return Constants.KEYWORD_PASS;
			} else {
				System.out.println("Attribute presents !!!");
				return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			System.out.println("Exception caught :" + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - creating Projects, Anlysis, and KB
	 * @param object - optional variable
	 * @param data - type of the application feature with the state
	 * @return - execution result
	 */
	public String createIfNotExist(String object, String data) {
	
		try {
			String caseString = data.split(Constants.DATA_SPLIT)[0];
			String type = data.split(Constants.DATA_SPLIT)[1];
			switch (caseString) {
				case "PROJECT":
					return Constants.KEYWORD_FAIL;
				case "ANALYSIS":
					return Constants.KEYWORD_FAIL;
				case "KB_DRAFT":
					System.out.println("Selecting the KB tab");
					pause("3", null);
					clickByXpath("//a[@href='#/kb']", null);
					System.out.println("Page Loading Delay");
					pause("3", null);
					waitTillElementIsVisible("//select[@name='mpdModel' and @ng-model='mpdModelCode']", "xpath");
					selectListByXpath("//select[@name='mpdModel' and @ng-model='mpdModelCode']", "All");
					selectListByXpath("//select[@ng-model='kbStateCode' and  @name='mpdModel']", "Draft");
					writeTextByXpath("//input[@name='query']", "type=" + type);
					clickByXpath("//button[@ng-click='search()']", null);
					pause("4", null);
					if (verifyTextContainsByXpath("//span[@class='ng-scope' and @ng-if='!searching && kbs != null && kbs.length < 1 ']",
					        "No records found").equals(Constants.KEYWORD_PASS)) {
						System.out.println("Selecting the KB Type");
						clickByID("dropdownMenu1", null);
						clickUsingJSByLinkText(null, type);
						System.out.println("Page Loading Delay");
						waitTillElementIsVisible("//button[@ng-click='submit()']", "xpath");
						System.out.println("Entering data into the mandatory fields");
						writeTextByXpath("//input[@name='kbtitle']", "Test Automation KB");
						writeTextByXpath("//input[@name='description']", "This is a KB for Test Automation purpose");
						selectDifferentOption("//select[@name='mpdModel']", "");
						scrollToBottom(null, null);
						System.out.println("Saving the KB");
						clickByXpath("//button[@ng-click='save()']", null);
						System.out.println("Page Loading Delay");
						waitTillElementIsVisible("//button[@ng-click='edit()']", "xpath");
						System.out.println("Going back to the KB front page");
						clickByXpath("//a[@href='#/kb']", null);
						System.out.println("Page Loading Delay");
						pause("5", null);
						System.out.println("KB created successfully");
					} else {
						System.out.println("KB Present in the system");
						pause("5", null);
					}
					return Constants.KEYWORD_PASS;
				default:
					System.out.println("Invalid Application feature passed !!!");
					return Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			System.out.println("Execption caught in creation :" + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - function storing text of a label
	 * @param object - xpath of the label
	 * @param data - map key
	 * @return - execution result
	 */
	public String storeLabelValueIntoMap(String object, String data) {
	
		try {
			String value = driver.findElement(By.xpath(object)).getText();
			System.out.println("Storing label text " + value + "into the map key " + data);
			globalStorage.put(data, value);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Execption caught in creation :" + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - function executing an AutoIT script
	 * @param object - optional
	 * @param data - path of the script
	 * @return - execution result
	 */
	public String runScript(String object, String data) {
	
		System.out.println("Executing AutoIT script");
		try {
			System.out.println(System.getProperty("user.dir"));
			Runtime.getRuntime().exec(System.getProperty("user.dir") + data);
			Thread.sleep(5000L);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "no script found" + e.getMessage();
		}
	}
	
	/**
	 * @description - function seacrhing for a project with analysis
	 * @param object - optional
	 * @param data - optional
	 * @return - execution result
	 */
	public String searchProjectAnalysis(String object, String data) {
	
		try {
			String projectDropDown = "//select[@ng-model='selectedProject']";
			System.out.println("Selecting the Projects");
			waitTillElementIsVisible("//select[@ng-model='selectedProject']/option[2]", "xpath");
			List<WebElement> projectElements = new Select(driver.findElement(By.xpath(projectDropDown))).getOptions();
			System.out.println("Iterating through " + projectElements.size() + " Projects");
			for (WebElement project : projectElements) {
				if (selectListByXpath(projectDropDown, project.getText()).equals(Constants.KEYWORD_PASS)) {
					clickByXpath("//button[@ng-click='search()']", null);
					pause("5", null);
					if (verifyTextContainsByXpath(".//*[@id='wrap']/div[2]/div[7]/span[2]", "No records found").contains(Constants.KEYWORD_FAIL)) {
						System.out.println("Checking for the AMP Tasks in the Analysis");
						List<WebElement> analyses = driver.findElements(By.xpath("//div[@class='col-sm-12']/table/tbody/tr"));
						for (int row = 0; row < analyses.size(); row++) {
							if (!driver.findElement(By.xpath("//div[@class='col-sm-12']/table/tbody/tr[" + (row + 1) + "]/td[3]")).getText()
							        .isEmpty()
							        && driver.findElement(By.xpath("//div[@class='col-sm-12']/table/tbody/tr[" + (row + 1) + "]/td[8]")).getText()
							                .equalsIgnoreCase("Initial")) {
								System.out.println("Store the Analsis Details into map");
								globalStorage.put("projectID", project.getText());
								System.out.println("Selected Project ID : " + globalStorage.get("projectID"));
								globalStorage.put("analysisID",
								        driver.findElement(By.xpath("//div[@class='col-sm-12']/table/tbody/tr[" + (row + 1) + "]/td[2]")).getText());
								System.out.println("Selected Analysis ID : " + globalStorage.get("analysisID"));
								globalStorage.put("analysisCount", "" + analyses.size() + "");
								System.out.println("Total Number of Analyses present : " + globalStorage.get("analysisCount"));
								return Constants.KEYWORD_PASS;
							} else {
								System.out.println("AMP Task is absent in the Analysis or Analysis not in Initial state");
								continue;
							}
						}
					} else {
						System.out.println("Analysis are absent in this project ");
						continue;
					}
				} else {
					System.out.println("Couldn't select the project");
					return Constants.KEYWORD_FAIL;
				}
			}
			return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			System.out.println("Exception caught in Project Analysis :" + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - function splitting the string by given char and storing the values into the map
	 * @param object - optional
	 * @param data - input string|split char (provide split char as 'SPACE' for space)
	 * @return - execution result
	 */
	public String splitByChar(String object, String data) {
	
		try {
			String sourceString = globalStorage.get(data.split(Constants.DATA_SPLIT)[0]);
			String splitChar = data.split(Constants.DATA_SPLIT)[1];
			String[] splitVal;
			if (splitChar.equals("SPACE")) {
				splitVal = sourceString.split(" ");
			} else {
				splitVal = sourceString.split(splitChar);
			}
			for (int count = 0; count < splitVal.length; count++) {
				globalStorage.put("splitVal_" + (count + 1), splitVal[count]);
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Exception caught  :" + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - function storing the path of the last modified file into a map key
	 * @param object - optional
	 * @param data - folder path
	 * @return - execution result
	 */
	public static String lastFileModified(String object, String data) {
	
		try {
			System.out.println("Getting last modified file of the Test files folder");
			String defaultFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
			defaultFolder = defaultFolder.replace("Documents", "Downloads\\");
			File fl = new File(defaultFolder);
			File[] files = fl.listFiles(new FileFilter() {
				
				public boolean accept(File file) {
				
					return file.isFile();
				}
			});
			long lastMod = Long.MIN_VALUE;
			File choice = null;
			for (File file : files) {
				if (file.lastModified() > lastMod) {
					choice = file;
					lastMod = file.lastModified();
				}
			}
			globalStorage.put("lastModifiedFile", choice.toString());
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}
	
	/**
	 * @description - function retrieving the value of an xl sheet cell
	 * @param object - xl sheet path
	 * @param data - column name|row number|data
	 * @return - execution result
	 * @throws IOException
	 */
	public String verifyCellData(String object, String data) throws IOException {
	
		FileInputStream fis = new FileInputStream(globalStorage.get(object));
		try {
			System.out.println("Retrieving the XL sheet value of " + globalStorage.get(object));
			String value = "blank";
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowNum = Integer.parseInt(data.split(Constants.DATA_SPLIT)[1]);
			String colName = data.split(Constants.DATA_SPLIT)[0];
			XSSFRow baseRow = sheet.getRow(0);
			int colNum = -1;
			XSSFCell cell = null;
			for (int i = 0; i < baseRow.getLastCellNum(); i++) {
				if (baseRow.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					colNum = i;
			}
			if (colNum == -1) {
				System.out.println(" column not found");
				return Constants.KEYWORD_FAIL + " column not found";
			}
			baseRow = sheet.getRow(rowNum - 1);
			cell = baseRow.getCell(colNum);
			if (cell == null) {
				System.out.println(" cell not found");
				return Constants.KEYWORD_FAIL + " cell not found";
			}
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				value = cell.getStringCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				value = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					double d = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					value = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					value = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + value;
				}
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				System.out.println("cell is blank");
				return Constants.KEYWORD_FAIL + " cell is blank";
			} else {
				value = String.valueOf(cell.getBooleanCellValue());
			}
			if (value.equalsIgnoreCase(data.split(Constants.DATA_SPLIT)[2])) {
				System.out.println("Value of the selected cell is " + value);
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + "Miss-Match found" + "Actual :" + value + ", Expected:" + data.split(Constants.DATA_SPLIT)[2];
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Constants.KEYWORD_FAIL + e.getMessage();
		} finally {
			fis.close();
		}
	}
	
	/**
	 * @description - funtion concatinating two strings
	 * @param object - optional variable
	 * @param data - map key|data string
	 * @return - glued variable
	 */
	public String glueFunctionOpp(String object, String data) {
	
		String glueResult = null;
		System.out.println("Calling glue function");
		try {
			glueResult = (getGlobalVarByName(object, data.split(Constants.DATA_SPLIT)[1]).concat(" ") + data.split(Constants.DATA_SPLIT)[0]);
			return glueResult;
		} catch (Exception e) {
			System.out.println("Exception Caught in Glue funtion " + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	/**
	 * @description - function returning the position of nth ocuurence of a char
	 * @param str - input string
	 * @param c - char
	 * @param n - nth ocuurence
	 * @return - index of nth occurence
	 */
	public static int nthOccurrence(String str, char c, int n) {
	
		int pos = str.indexOf(c, 0);
		while (n-- > 0 && pos != -1)
			pos = str.indexOf(c, pos + 1);
		return pos;
	}
	
	/**
	 * @desscription - function returning the calculated date
	 * @param object - optional
	 * @param data - date format|number of days want to increment
	 * @return - execution result
	 */
	public String returnNewDate(String object, String data) {
	
		System.out.println("Calculating new date");
		try {
			String pattern = data.split(Constants.DATA_SPLIT)[0];
			System.out.println(pattern);
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			// formatting
			format.format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, Integer.parseInt(data.split(Constants.DATA_SPLIT)[1]));
			Date newDate = cal.getTime();
			String modifiedDate = format.format(newDate);
			return modifiedDate;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find text " + e.getMessage();
		}
	}
	
	/**
	 * @description - Method will store element's text using xpath (and text as reference stored in arraylist)
	 *              in a global variable
	 * @param object - Xpath of element
	 * @param data - Index number of text in arraylist starting from zero
	 * @return - Execution Result
	 */
	public String storeTextInArrayListUsingXpath(String object, String data) {
	
		System.out.println("Storing element's text in arrayList chosen");
		try {
			System.out.println("Original data: " + data);
			System.out.println("data: " + (int) Float.parseFloat(data));
			System.out.println("Text:" + returnChosenArrayList().get((int) Float.parseFloat(data)));
			String ElementXpath = "//*[contains(.,'" + returnChosenArrayList().get((int) Float.parseFloat(data)).trim() + "')]" + object;
			System.out.println("Element Xpath: " + ElementXpath);
			returnChosenArrayList().add(driver.findElement(By.xpath(ElementXpath)).getText());
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method clicks on the element using CSS selector
	 * @param object - CSS expression of element
	 * @param data - optional variable
	 * @return - Execution Result
	 */
	public String clickUsingCss(String object, String data) {
	
		System.out.println("Clicking on the element");
		try {
			driver.findElement(By.cssSelector(object)).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method verifies link text on page that is stored in arraylist
	 * @param object - Optional variable
	 * @param data - Index of linktext in arraylist
	 * @return - Execution resultS
	 */
	public String verifyLinkTextValueFromArrayList(String object, String data) {
	
		System.out.println("verify link text saved in arrayList ");
		try {
			String str[] = data.split(Constants.DATA_SPLIT);
			int flag = 0;
			for (int strIndex = 0; strIndex < str.length; strIndex++) {
				if (driver.findElement(By.linkText(returnChosenArrayList().get((int) Float.parseFloat(str[strIndex])).trim())).isDisplayed())
					flag += 1;
			}
			if (flag == str.length)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link by text" + e.getMessage();
		}
	}
	
	/**
	 * @description - The method writes a random text by Xpath (uses generateRandomValues(object, data) to
	 *              generate random text) and stores the generated text into arraylist
	 * @param object - string (xpath of object)
	 * @param data - the type of generating string (NUMBER for numeric, STRING for string,ALPHA_NUMERIC for
	 *            alpha numeric) and ASCII for ASCII value) and the character limit of the generating value
	 *            (Example : NUMBER|45).
	 * @return - PASS, if driver is able to write random text
	 */
	public String writeAndStoreRandomTextByXpath(String object, String data) {
	
		System.out.println("Writing random text");
		try {
			String textToWrite = generateRandomValues(object, data);
			driver.findElement(By.xpath(object)).click();
			driver.findElement(By.xpath(object)).clear();
			driver.findElement(By.xpath(object)).sendKeys(textToWrite);
			returnChosenArrayList().add(textToWrite);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -Not able to find element " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method writes a particular value to input field saved in arrayList by providing
	 *              index number of element in arraylist
	 * @param object - xpath of element
	 * @param data - Index number of element in arraylist
	 * @return - Execution Result
	 */
	public String writeParticularFromArrayListWithoutClearXpath(String object, String data) {
	
		System.out.println("Writing particular value saved in arrayList");
		try {
			String ValueToWrite = returnChosenArrayList().get((int) Float.parseFloat(data));
			System.out.println("Value to write: " + ValueToWrite);
			driver.findElement(By.xpath(object)).click();
			driver.findElement(By.xpath(object)).sendKeys(ValueToWrite);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to find element" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method writes a text in a text field
	 * @param object - xpath of element
	 * @param data - data to write (alphanumeric/text)
	 * @return - Execution Result
	 */
	public String writeTextWithoutClearByXpath(String object, String data) {
	
		System.out.println("Writing " + data + " in text box");
		try {
			driver.findElement(By.xpath(object)).click();
			driver.findElement(By.xpath(object)).sendKeys(data);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	/**
	 * @description - The method verify that link is not present on the page
	 * @param object -
	 * @param data - Index of the arraylist
	 * @return - Execution Result
	 */
	public String verifyElementNotPresentFromArraylistxpath(String object, String data) {
	
		System.out.println("Checking the existance of element");
		try {
			if (driver.findElements(By.linkText(returnChosenArrayList().get((int) Float.parseFloat(data)))).size() == 0)
				return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
		return Constants.KEYWORD_FAIL;
	}
	
	/**
	 * @description - The method clicks on an element if it is present
	 * @param object - xpath of element
	 * @param data - optional variable
	 * @return - Execution Result
	 */
	public String clickElementIfPresentXpath(String object, String data) {
	
		System.out.println("Checking if element is present...");
		try {
			if (driver.findElements(By.xpath(object)).size() != 0) {
				System.out.println("Element present. Clicking on the element...");
				driver.findElement(By.xpath(object)).click();
				return Constants.KEYWORD_PASS;
			} else {
				System.out.println("Element not present");
				return Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			System.out.println("Exception caught: " + e.getMessage());
			return Constants.KEYWORD_FAIL + " Exception caught: " + e.getMessage();
		}
	}
	
	public String navigateToSideBar(String object, String data) {
	
		try {
			System.out.println("Etering the number");
			waitTillElementIsPresent("//input[@id='serviceNumber']", "xpath");
			writeTextByXpath("//input[@id='serviceNumber']", "9898989898");
			System.out.println("Clicking on the next button");
			clickByXpath("//button[@id='nextBtn']", null);
			clickByXpath("//button[@id='nextBtn']", null);
			clickByXpath("//button[@id='nextBtn']", null);
			waitTillElementIsPresent("//span[@class='btn red']", "xpath");
			clickByXpath("//span[@class='btn red']", null);
			waitTillElementIsPresent("//a[@data-plan-type='recommended']", "xpath");
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	public String test1(String object, String data) {
	
		try {
			clickByXpath("//a[@data-plan-type='recommended']", "");
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.plan3g.planrecommended.plan-amount")));
			List<WebElement> options = driver.findElements(By.cssSelector("li[data-plandescription*='Full Talktime'][style*='display: list-item;']"));
			int count = 0;
			int minValue = Integer.parseInt(options.get(0).getAttribute("data-planamount"));
			WebElement current = options.get(0);
			System.out.println("Total number of full talk times = " + options.size());
			for (WebElement option : options) {
				if (Integer.parseInt(option.getAttribute("data-planamount")) < minValue) {
					current = option;
				}
			}
			System.out.println("Most efficient option = " + current.getAttribute("data-planamount"));
			current.click();
			// System.out.println(driver.findElement(By.cssSelector("div.plan3g.planrecommended.plan-amount+div>p")).getText());
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Excecption:" + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	public String test2(String object, String data) {
	
		try {
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
	
	public String textArear(String object, String data) {
	
		try {
			driver = new FirefoxDriver();
			driver.navigate().to("http://www.optimusinfo.com/contact-us/");
			driver.manage().window().maximize();
			
			
			WebElement textArea = driver.findElement(By.cssSelector("textarea#avia_message_1"));
			textArea.sendKeys("The text in text are");
			Thread.sleep(4000L);
			
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			String messageText = jse.executeScript("return document.getElementById('avia_message_1').value", textArea).toString();
			
			System.out.println("Text area content using text = "+messageText);
			
			
			
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Exception caught"+e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}
}
