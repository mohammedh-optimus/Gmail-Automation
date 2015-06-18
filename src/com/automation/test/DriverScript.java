
package com.automation.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.log4j.Logger;

import com.automation.read.Xls_Reader;

/**
 * The main control to the frame work execution
 * @version version (Revised on 8th June 2015)
 * @author vikas.tuli
 */
public class DriverScript {
	
	public static Logger APP_LOGS;
	public Xls_Reader suiteXLS; // Current Suite.xlsx
	public int currentSuiteID; // Row pointer to the Suite.xlsx Test Cases tab
	public String currentTestSuite; // Current test case XL sheet
	public String executingTestSuite;
	public static Xls_Reader currentTestSuiteXLS;
	public static int currentTestCaseID;
	public static String currentTestCaseName;
	public static int currentTestStepID;
	public static String currentKeyword;
	public static int currentTestDataSetID = 2;
	public static Method method[];
	public static Method capturescreenShot_method;
	public static Keywords keywords;
	public static String keyword_execution_result;
	public static ArrayList<String> resultSet;
	public static ArrayList<String> resultSetTC;
	public static String data;
	public static String object;
	public Path FROM;
	public Path TO;
	public CopyOption options[];
	public String myDateString;
	public Date myDate = new Date();
	public String currentXlsBackUpFileName;
	public static int xlsBackupFolderLimit;
	public static String xlsPath = "\\src\\com\\automation\\xls\\";
	public static String patternString;
	public static Pattern pattern;
	public Xls_Reader resultXLS;
	public static String[] coveredScenariosID;
	public static String currentScenario;
	public static String currentScenarioID;
	public static int currentResultTestNameID;
	public String currentResultFileName;
	public static int resultBackupFolderLimit;
	public static String currentTestCaseResult;
	public static String resultSetFile;
	public String resultTemplateFolder;
	public File resultSetFolder;
	public File[] resultSetFiles;
	public static ArrayList<String> resultSetFilesList;
	public static ArrayList<String> resultSetBackupFilesList;
	public static Properties CONFIG;
	public static Properties OR;
	public static String dataSheet; // Consolidated test data XL Sheet
	public static int rowIndex;
	public static int colIndex;
	public static int iteration;
	public HashMap<String, Integer> dataMap = new HashMap<String, Integer>();
	public static boolean consolidatedDataRead = false;
	
	public DriverScript() throws NoSuchMethodException, SecurityException {
	
		keywords = new Keywords();
		method = keywords.getClass().getMethods();
		// capturescreenShot_method = keywords.getClass().getMethod("captureScreenShot", String.class,
		// String.class);
	}
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException,
	        NoSuchMethodException, SecurityException {
	
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir") + "\\src\\com\\automation\\config\\config.properties");
		CONFIG = new Properties();
		CONFIG.load(fs);
		fs = new FileInputStream(System.getProperty("user.dir") + "\\src\\com\\automation\\config\\or.properties");
		OR = new Properties();
		OR.load(fs);
		DriverScript test = new DriverScript();
		test.start();
	}
	
	/**
	 * @description - function taking the back up of the XL sheet automatically
	 * @param - Source folder path from where copy is to made
	 * @param - File name whose copy is to done
	 * @param - Destination folder path where copy is made
	 * @param - Folder limit to contain files
	 * @return - Copied file name appended with time stamp
	 **/
	public String autoBackUp(String folderPath, String sheetName, String path, int folderLimit) {
	
		String backUpFile;
		try {
			APP_LOGS.debug("Creating Back up of " + sheetName + ".xlsx");
			System.out.println("Creating Back up of " + sheetName + ".xlsx");
			if (!(Files.exists(Paths.get(System.getProperty("user.dir") + folderPath)))) {
				new File(System.getProperty("user.dir") + folderPath).mkdir();
			}
			File outPutFolder = new File(System.getProperty("user.dir") + folderPath);
			if (outPutFolder.listFiles().length >= folderLimit) {
				APP_LOGS.debug("Back_Up exceeds the limit...Overriding the oldest backup...");
				System.out.println("Back_Up exceeds the limit...Overriding the oldest backup...");
				File[] files = outPutFolder.listFiles();
				File theOldestFile = null;
				Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
				theOldestFile = files[0];
				theOldestFile.delete();
			}
			FastDateFormat fdf = FastDateFormat.getInstance("yyyy_MM_dd_HH_mm_ss");
			myDateString = fdf.format(myDate);
			backUpFile = myDateString + "_" + sheetName + ".xlsx";
			FROM = Paths.get(System.getProperty("user.dir") + path + sheetName + ".xlsx");
			TO = Paths.get(System.getProperty("user.dir") + folderPath + "\\" + backUpFile);
			options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES };
			Files.copy(FROM, TO, options);
			APP_LOGS.debug("Backup created successfully...");
			System.out.println("Backup created successfully...");
			return backUpFile;
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
			return "Exception : " + e.getMessage();
		}
	}
	
	public void start() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException,
	        IOException {
	
		resultSetTC = new ArrayList<String>();
		APP_LOGS = Logger.getLogger("devpinoyLogger");
		APP_LOGS.debug("Properties loaded...");
		System.out.println("Properties loaded...");
		APP_LOGS.debug("Intialize Suite.xlsx...");
		System.out.println("Initialize Suite.xls...");
		suiteXLS = new Xls_Reader(System.getProperty("user.dir") + "\\src\\com\\automation\\xls\\Suite.xlsx");
		resultSetFilesList = new ArrayList<String>();
		resultSetBackupFilesList = new ArrayList<String>();
		resultSetFilesList.add("DummyFile");
		resultSetBackupFilesList.add("DummyBackupFile");
		/*************************************** Suite level execution starts here ***************************************/
		for (currentSuiteID = 2; currentSuiteID <= suiteXLS.getRowCount(Constants.TEST_SUITE_SHEET); currentSuiteID++) {
			APP_LOGS.debug(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID) + " -- "
			        + suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE, currentSuiteID));
			currentTestSuite = suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID);
			if (suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE, currentSuiteID).equals(Constants.RUNMODE_YES)) {
				executingTestSuite = currentTestSuite;
				APP_LOGS.debug("Executing Suite " + suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID));
				System.out
				        .println("Executing the Suite " + suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID));
				currentTestSuiteXLS = new Xls_Reader(System.getProperty("user.dir") + "\\src\\com\\automation\\xls\\" + currentTestSuite + ".xlsx");
				xlsBackupFolderLimit = Integer.parseInt(CONFIG.getProperty("xls_backup_folder_limit"));
				currentXlsBackUpFileName = autoBackUp(CONFIG.getProperty("xls_backup_folder_path"), currentTestSuite, xlsPath, xlsBackupFolderLimit);
				resultTemplateFolder = CONFIG.getProperty("result_template_folder_path");
				resultSetFolder = new File(System.getProperty("user.dir") + resultTemplateFolder);
				resultSetFiles = resultSetFolder.listFiles();
				/********************************* Template Checking for the XL level reporting *********************************/
				/*
				 * for (int templateFilesIndex = 0; templateFilesIndex < resultSetFiles.length;
				 * templateFilesIndex++) { if
				 * (executingTestSuite.contains(FilenameUtils.getBaseName(resultSetFiles
				 * [templateFilesIndex].getName()))) { resultSetFile =
				 * FilenameUtils.getBaseName(resultSetFiles[templateFilesIndex].getName()); break; } }
				 * resultBackupFolderLimit =
				 * Integer.parseInt(CONFIG.getProperty("result_backup_folder_limit")); if
				 * (!resultSetFilesList.contains(resultSetFile)) { currentResultFileName =
				 * autoBackUp(CONFIG.getProperty("result_backup_folder_path"), resultSetFile,
				 * CONFIG.getProperty("result_template_files_path"), resultBackupFolderLimit);
				 * resultSetFilesList.add(resultSetFile); resultSetBackupFilesList.add(currentResultFileName);
				 * } else { String currentResultSetBackupFile = null; for (int resultSetBackupFilesIndex = 0;
				 * resultSetBackupFilesIndex < resultSetBackupFilesList.size(); resultSetBackupFilesIndex++) {
				 * currentResultSetBackupFile = resultSetBackupFilesList.get(resultSetBackupFilesIndex); if
				 * (currentResultSetBackupFile.contains(resultSetFile)) currentResultFileName =
				 * currentResultSetBackupFile; } }
				 */
				dataSheet = currentTestSuiteXLS.getCellData("Pre-Requisites", Constants.CONSOLIDATED_DATA_COL, Constants.CONSOLIDATED_DATA_ROW);
				if (!(dataSheet.equalsIgnoreCase("Pre-Requisites") || dataSheet.isEmpty())) {
					if (new File(Constants.CONSOLIDATED_DATA_FOLDER + dataSheet + ".xlsx").exists()) {
						currentTestSuiteXLS.DataSheet_Reader(Constants.CONSOLIDATED_DATA_FOLDER + dataSheet + ".xlsx");
						int totalDataRows = currentTestSuiteXLS.getDataSheetRowCount(Constants.CONSOLIDATED_DATA_SHEET_NAME);
						consolidatedDataRead = true;
						System.out.println("Consolidated Test Data sheet exists...");
						APP_LOGS.debug("Consolidated Test Data sheet exists...");
						for (int dataRowNum = 0; dataRowNum < totalDataRows; dataRowNum++) {
							String cellData = currentTestSuiteXLS.getDataSheetCellData(Constants.CONSOLIDATED_DATA_SHEET_NAME, 0, dataRowNum);
							if (cellData.contains("Test Case")) {
								String testCaseName = cellData.substring(cellData.indexOf('-') + 1).trim();
								dataMap.put(testCaseName, dataRowNum);
								System.out.println("Test cases are added to the dataMap...");
								APP_LOGS.debug("Test cases are added to the dataMap...");
							}
						}
					}
				} else {
					System.out.println("Consolidated Test Data sheet missing...");
					APP_LOGS.debug("Consolidated Test Data sheet missing...");
					consolidatedDataRead = false;
				}
				/********************************************* Test case level execution starts here ***********************************************/
				for (currentTestCaseID = 2; currentTestCaseID <= currentTestSuiteXLS.getRowCount(Constants.TEST_CASES_SHEET); currentTestCaseID++) {
					APP_LOGS.debug(currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, currentTestCaseID) + " -- "
					        + currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID));
					System.out.println(currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, currentTestCaseID) + " -- "
					        + currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID));
					currentTestCaseName = currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, currentTestCaseID);
					if (currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID).equals(
					        Constants.RUNMODE_YES)) {
						APP_LOGS.debug("Executing the test case -> " + currentTestCaseName);
						System.out.println("Executing the test case -> " + currentTestCaseName);
						resultSetTC.clear();
						starttime(currentTestCaseID);
						if (consolidatedDataRead) {
							File dataWorkbook = new File(Constants.CONSOLIDATED_DATA_FOLDER + dataSheet + ".xlsx");
							if (dataWorkbook.exists()) {
								if (currentTestSuiteXLS.isDataSheetExist(Constants.CONSOLIDATED_DATA_SHEET_NAME)) {
								}
								if (currentTestSuiteXLS.isDataSheetExist(Constants.CONSOLIDATED_DATA_SHEET_NAME)
								        && dataMap.containsKey(currentTestCaseName)) {
									colIndex = dataMap.get(currentTestCaseName) + 1;
									rowIndex = dataMap.get(currentTestCaseName) + 2;
									iteration = 0;
									while (!currentTestSuiteXLS.getDataSheetCellData(Constants.CONSOLIDATED_DATA_SHEET_NAME, 0, rowIndex).isEmpty()) {
										iteration++;
										resultSet = new ArrayList<String>();
										APP_LOGS.debug("Iteration - "
										        + iteration
										        + " Runmode - "
										        + currentTestSuiteXLS.getDataSheetCellData(Constants.CONSOLIDATED_DATA_SHEET_NAME, Constants.RUNMODE,
										                rowIndex, colIndex));
										System.out.println("Iteration - "
										        + iteration
										        + " Runmode - "
										        + currentTestSuiteXLS.getDataSheetCellData(Constants.CONSOLIDATED_DATA_SHEET_NAME, Constants.RUNMODE,
										                rowIndex, colIndex));
										if (currentTestSuiteXLS.getDataSheetCellData(Constants.CONSOLIDATED_DATA_SHEET_NAME, Constants.RUNMODE,
										        rowIndex, colIndex).equals(Constants.RUNMODE_YES)) {
											executeKeywords();
										}
										createXLSReport();
										rowIndex++;
									}
								} else {
									resultSet = new ArrayList<String>();
									executeKeywords();
									createXLSReport();
								}
							}
						} else {
							if (currentTestSuiteXLS.isSheetExist(currentTestCaseName)) {
								for (currentTestDataSetID = 2; currentTestDataSetID <= currentTestSuiteXLS.getRowCount(currentTestCaseName); currentTestDataSetID++) {
									resultSet = new ArrayList<String>();
									APP_LOGS.debug("Iteration number " + (currentTestDataSetID - 1));
									if (currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.RUNMODE, currentTestDataSetID).equals(
									        Constants.RUNMODE_YES)) {
										executeKeywords();
									}
									createXLSReport();
								}
							} else {
								resultSet = new ArrayList<String>();
								executeKeywords();
								createXLSReport();
							}
						}
						endtime(currentTestCaseID);
						// setResultStatusTcSheet(currentTestCaseID);
					}
				}
			}
		}
	}
	
	/**
	 * @description Executing the keywords
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void executeKeywords() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	
		try {
			for (currentTestStepID = 2; currentTestStepID <= currentTestSuiteXLS.getRowCount(Constants.TEST_STEPS_SHEET); currentTestStepID++) {
				int keywordFoundFlag = 0;
				if (currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID, currentTestStepID))) {
					data = currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.DATA, currentTestStepID);
					if (data.startsWith(Constants.DATA_START_COL)) {
						if (consolidatedDataRead) {
							data = currentTestSuiteXLS.getDataSheetCellData(Constants.CONSOLIDATED_DATA_SHEET_NAME,
							        data.split(Constants.DATA_SPLIT)[1], rowIndex, colIndex);
						} else {
							data = currentTestSuiteXLS.getCellData(currentTestCaseName, data.split(Constants.DATA_SPLIT)[1], currentTestDataSetID);
						}
					} else if (data.startsWith(Constants.CONFIG)) {
						data = data.split(Constants.DATA_SPLIT)[1];
					} else {
						data = OR.getProperty(data);
					}
					object = currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.OBJECT, currentTestStepID);
					object = OR.getProperty(object);
					currentKeyword = currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.KEYWORD, currentTestStepID);
					APP_LOGS.debug(currentKeyword);
					System.out.println(currentKeyword);
					if (currentKeyword == "") {
						currentKeyword = "Error!!!  Keyword column is empty";
						resultSet.add(currentKeyword);
						for (int mcount = 0; mcount < method.length; mcount++) {
							if (method[mcount].getName().equals("closeBrowser")) {
								method[mcount].invoke(keywords, object, data);
								break;
							}
						}
						return;
					}
					for (int i = 0; i < method.length; i++) {
						keyword_execution_result = null;
						if (method[i].getName().equals(currentKeyword)) {
							keywordFoundFlag = 1;
							keyword_execution_result = (String) method[i].invoke(keywords, object, data);
							APP_LOGS.debug(keyword_execution_result);
							System.out.println(keyword_execution_result);
							resultSet.add(keyword_execution_result);
							if (CONFIG.getProperty("screenshot_everystep").equals("Y")) {
								keywords.captureScreenShot(currentTestSuite + "_" + currentTestCaseName + "_TS" + (currentTestStepID - 1) + "_"
								        + (currentTestDataSetID - 1));
							} else if (keyword_execution_result.startsWith(Constants.KEYWORD_FAIL)
							        && CONFIG.getProperty("screenshot_error").equals("Y")) {
								keywords.captureScreenShot(currentTestSuite + "_" + currentTestCaseName + "_TS" + (currentTestStepID - 1) + "_"
								        + (currentTestDataSetID - 1));
							}
							break;
						}
					}
					if (keywordFoundFlag == 0) {
						System.out.println("The keyword -- " + currentKeyword + " is not found in Keywords.java");
						resultSet.add("Error!!! Keyword Not Found");
					}
					if (keywordFoundFlag == 0 || keyword_execution_result.startsWith(Constants.KEYWORD_FAIL)) {
						for (int mcount = 0; mcount < method.length; mcount++) {
							if (method[mcount].getName().equals("closeBrowser")) {
								method[mcount].invoke(keywords, object, data);
								break;
							}
						}
						return;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			for (int mcount = 0; mcount < method.length; mcount++) {
				if (method[mcount].getName().equals("closeBrowser")) {
					resultSet.add("Invocation error !!! ");
					method[mcount].invoke(keywords, object, data);
					break;
				}
			}
			return;
		}
	}
	
	public void createXLSReport() {
	
		String colName = Constants.RESULT + (currentTestDataSetID - 1);
		if (consolidatedDataRead) {
			colName = Constants.RESULT + (iteration);
		} else {
			colName = Constants.RESULT + (currentTestDataSetID - 1);
		}
		boolean isColExist = false;
		for (int c = 0; c < currentTestSuiteXLS.getColumnCount(Constants.TEST_STEPS_SHEET); c++) {
			if (currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, c, 1).equals(colName)) {
				isColExist = true;
				break;
			}
		}
		if (!isColExist)
			currentTestSuiteXLS.addColumn(Constants.TEST_STEPS_SHEET, colName);
		int index = 0;
		for (int i = 2; i <= currentTestSuiteXLS.getRowCount(Constants.TEST_STEPS_SHEET); i++) {
			if (currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID, i))) {
				if (resultSet.size() == 0) {
					currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i, Constants.KEYWORD_SKIP);
					resultSetTC.add(Constants.KEYWORD_SKIP);
					break;
				} else {
					if (resultSet.get(index).contains(Constants.KEYWORD_PASS)) {
						currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i, resultSet.get(index));
					}
					if (resultSet.get(index).contains(Constants.KEYWORD_FAIL) || resultSet.get(index).contains("Error!!! Keyword Not Found")
					        || resultSet.get(index).contains("Error!!!  Keyword column is empty")) {
						currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i, resultSet.get(index));
						resultSetTC.add(Constants.KEYWORD_FAIL);
						break;
					}
				}
				index++;
			}
		}
		if (!(resultSet.contains(Constants.KEYWORD_FAIL) || resultSet.contains(Constants.KEYWORD_SKIP)
		        || resultSet.contains("Error!!! Keyword Not Found") || resultSet.contains("Error!!!  Keyword column is empty"))
		        && resultSet.contains(Constants.KEYWORD_PASS)) {
			resultSetTC.add(Constants.KEYWORD_PASS);
		}
	}
	
	public void starttime(int currentrow) {
	
		String colname = "Start Time";
		boolean isColExist = false;
		for (int c = 0; c < currentTestSuiteXLS.getColumnCount(Constants.TEST_CASES_SHEET); c++) {
			if (currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, c, 1).equals(colname)) {
				isColExist = true;
				break;
			}
		}
		if (!isColExist)
			currentTestSuiteXLS.addColumn(Constants.TEST_CASES_SHEET, colname);
		Date date = new Date();
		currentTestSuiteXLS.setCellData(Constants.TEST_CASES_SHEET, colname, currentrow, date.toString());
	}
	
	public void endtime(int currentrow) {
	
		String colname = "End Time";
		boolean isColExist = false;
		for (int c = 0; c < currentTestSuiteXLS.getColumnCount(Constants.TEST_CASES_SHEET); c++) {
			if (currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, c, 1).equals(colname)) {
				isColExist = true;
				break;
			}
		}
		if (!isColExist)
			currentTestSuiteXLS.addColumn(Constants.TEST_CASES_SHEET, colname);
		Date date = new Date();
		currentTestSuiteXLS.setCellData(Constants.TEST_CASES_SHEET, colname, currentrow, date.toString());
	}
	
	/**
	 * @description - The function set result status of executed test case in Test Case sheet
	 * @param - Row number (integer)
	 * @return- Execution Result
	 */
	public void setResultStatusTcSheet(int currentrow) {
	
		boolean isColExist = false;
		for (int c = 0; c < currentTestSuiteXLS.getColumnCount(Constants.TEST_CASES_SHEET); c++) {
			if (currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, c, 1).equals(Constants.RESULT)) {
				isColExist = true;
				break;
			}
		}
		if (!isColExist)
			currentTestSuiteXLS.addColumn(Constants.TEST_CASES_SHEET, Constants.RESULT);
		if (resultSetTC.contains(Constants.KEYWORD_FAIL)) {
			currentTestSuiteXLS.setCellData(Constants.TEST_CASES_SHEET, Constants.RESULT, currentrow, Constants.KEYWORD_FAIL);
		} else if (resultSetTC.contains(Constants.KEYWORD_PASS) && !resultSetTC.contains(Constants.KEYWORD_FAIL)) {
			currentTestSuiteXLS.setCellData(Constants.TEST_CASES_SHEET, Constants.RESULT, currentrow, Constants.KEYWORD_PASS);
		} else if (resultSetTC.contains(Constants.KEYWORD_SKIP) && !resultSetTC.contains(Constants.KEYWORD_PASS)) {
			currentTestSuiteXLS.setCellData(Constants.TEST_CASES_SHEET, Constants.RESULT, currentrow, Constants.KEYWORD_SKIP);
		}
		coveredScenariosID = currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.MAPPED_TEST_CASE_ID, currentrow).split(",");
		if (!currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.MAPPED_TEST_CASE_ID, currentrow).isEmpty()) {
			currentTestCaseResult = currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.RESULT, currentrow);
			setResultStatusProgressSheet(currentTestCaseResult, coveredScenariosID);
		}
	}
	
	/**
	 * @description - Update status of executed test case in Result sheet
	 * @param - Test Case status
	 * @param - Scenarios covered (String array)
	 * @return - Execution result
	 */
	public void setResultStatusProgressSheet(String result, String[] scenariosID) {
	
		boolean isColExist = false;
		String tcResult = result;
		String currentScenarioTC = null;
		int coveredScenarioIndex = 0;
		patternString = "[1-9]\\d*[a-zA-Z]?\\.?\\d*";
		resultXLS = new Xls_Reader(System.getProperty("user.dir") + CONFIG.getProperty("result_backup_folder_path") + "\\" + currentResultFileName);
		for (int c = 0; c < resultXLS.getColumnCount(Constants.TEST_RESULT_SHEET); c++) {
			if (resultXLS.getCellData(Constants.TEST_RESULT_SHEET, c, 1).equals(Constants.RESULT)) {
				isColExist = true;
				break;
			}
		}
		if (!isColExist)
			resultXLS.addColumn(Constants.TEST_RESULT_SHEET, Constants.RESULT);
		pattern = Pattern.compile(patternString);
		for (currentResultTestNameID = 2; currentResultTestNameID <= resultXLS.getRowCount(Constants.TEST_RESULT_SHEET); currentResultTestNameID++) {
			currentScenario = resultXLS.getCellData(Constants.TEST_RESULT_SHEET, Constants.RESULT_SHEET_COLUMN_ID, currentResultTestNameID);
			if (currentScenario.isEmpty())
				continue;
			Matcher matcher = pattern.matcher(currentScenario);
			boolean isMached = matcher.find();
			if (isMached) {
				currentScenario = matcher.group(0);
			}
			for (int coveredScenariosIndex = 0; coveredScenariosIndex < scenariosID.length; coveredScenariosIndex++) {
				matcher = pattern.matcher(scenariosID[coveredScenariosIndex]);
				isMached = matcher.find();
				if (isMached) {
					currentScenarioTC = matcher.group(0);
				}
				if (currentScenario.equals(currentScenarioTC.trim())) {
					resultXLS.setCellData(Constants.TEST_RESULT_SHEET, Constants.RESULT, currentResultTestNameID, tcResult);
					coveredScenarioIndex++;
					break;
				}
			}
			if (coveredScenarioIndex == scenariosID.length)
				break;
		}
	}
}
