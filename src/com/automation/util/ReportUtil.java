package com.automation.util;

import java.io.*;
import java.util.Date;
import java.util.Properties;

import com.automation.read.Xls_Reader;
import com.automation.test.Constants;

/**
 * @version - MHR_v.6(Removed ZIP function)
 * @description - Class generating HTML report after the execution of the Web driver - Best result with
 *              FireFox browser
 * @required -The web driver should be completed the execution and the XL should contain contains the result
 * @author MIH
 */
public class ReportUtil {
	// Set the Configurations
	public static String indexPageHeading = "Automation Reports"; // Set the index page heading
	public static String resultFolderName = "Automarion_Reports"; // Set the result folder name
	public static int totalTestPassedCount = 0;
	public static int totalTestFailedCount = 0;
	public static String chartTitle = "Report Overview";

	/**
	 * @param passedCount - number of test cases passed
	 * @param failedCount number of test cases failed
	 * @return script - JS function
	 * @required - JS library https://www.google.com/jsapi
	 */
	public static String makePiGraph(int passedCount, int failedCount) {

		String script = "<script type='text/javascript' src='https://www.google.com/jsapi'></script>"
				+ "<script type='text/javascript'>"
				+ "google.load('visualization', '1', {packages:['corechart']});"
				+ "google.setOnLoadCallback(drawChart);" + "function drawChart() {"
				+ "var data = google.visualization.arrayToDataTable([" + "['Status', 'Test Case Count'],"
				+ "['PASS',     " + passedCount + "]," + "['FAIL',      " + failedCount + "]" + "]);"
				+ "var options = {" + "title: 'Test Case Report',"
				+ "slices: {0: {color: 'green'}, 2: {color: 'red'}}" + "};"
				+ "var chart = new google.visualization.PieChart(document.getElementById('piechart'));" + ""
				+ "chart.draw(data, options);" + "}" + "</script>";
		return script;
	}

	/**
	 * @param passedCount - number of test cases passed
	 * @param failedCount number of test cases failed
	 * @return script - JS function
	 * @required - JS library canvasjs.min.js
	 */
	public static String makePiChartOffline(int passedCount, int failedCount, String chartTitle) {

		String script = "<script type='text/javascript' src='../../libs/pi_graph/canvasjs.min.js'></script>"
				+ "<script type='text/javascript'>" + "window.onload = function () {"
				+ "CanvasJS.addColorSet('testResult_color'," + "[" + "'#009925'," + "'#DD4B39'" + "]);"
				+ "var chart = new CanvasJS.Chart('chartContainer'," + "{" + "title:{" + "text: '"
				+ chartTitle
				+ "',"
				+ "fontSize: 20,"
				+ "fontFamily: 'arial',"
				+ "fontColor: '#660066'"
				+ "},"
				+ "colorSet: 'testResult_color',"
				+ "exportFileName: 'Pie Chart',"
				+ "exportEnabled: false,"
				+ "animationEnabled: true,"
				+ "legend:{"
				+ "verticalAlign: 'bottom',"
				+ "horizontalAlign: 'center'"
				+ "},"
				+ "fontFamily: 'arial',"
				+ "data: ["
				+ "{"
				+ "type: 'pie',"
				+ "showInLegend: false,"
				+ "toolTipContent: '{legendText}: <strong>{y} (#percent%)</strong>',"
				+ "indexLabel: ' {label} #percent%',"
				+ "percentFormatString: '#0.##',"
				+ "dataPoints: ["
				+ "{  y: "
				+ passedCount
				+ ", legendText: 'PASS', exploded: true, label: 'PASS' },"
				+ "{  y: "
				+ failedCount
				+ ", legendText: 'FAIL', label: 'FAIL' }"
				+ "]"
				+ "}"
				+ "]"
				+ "});"
				+ "chart.render();" + "}" + "</script>";
		return script;
	}

	public static void main(String[] arg) throws Exception {

		System.out.println("HTML Report Rendering...");
		int totalTestSuites; // Total number of different test modules present in the Suite.xl
		int testCaseRows;
		int testCaseCols;
		int testStepRows;
		int testStepCols;
		int testStepRowIndex;
		int lastIndex;
		int terminalCount;
		int testCasePassedCount;
		int testCaseFailedCount;
		String environment = null;
		String release = null;
		String testCaseName = null;
		String currentTestSuite = null;
		String currentTestName = null;
		String testCaseHtmlPath = null; // Path for the individual test module sheets
		String indexHtmlPath = null; // Path for the index.html
		String date = null;
		String data = null;
		boolean resultCol = false;
		boolean testStepResult = true;
		boolean testCaseResult = true;
		boolean skipFound = false;
		boolean resultFound = false;
		Xls_Reader currentSuiteXLS = null; // Name of the current test module
		Xls_Reader suiteXLS = null; // Name of Suite.xls
		Properties CONFIG = null;
		FileInputStream fs = null; // Stream for setting configuration details
		FileWriter fstream = null; // Stream for index.html
		FileWriter fstreamTestCases = null; // Stream for test case level HTML
		BufferedWriter outTestCases = null;
		BufferedWriter out = null;
		FileWriter fstreamTestSteps = null; // Stream for test step level HTML
		BufferedWriter outTestSteps = null;
		try {
			// Creating the reports folder
			date = (((new Date().toString()).replaceAll(" ", "_")).replaceAll(":", "_")).replaceAll("\\+",
					"_");
			resultFolderName = resultFolderName + "//" + resultFolderName + "_" + date;
			new File(resultFolderName).mkdirs();
			// Creating the index.html
			indexHtmlPath = resultFolderName + "//index.html";
			new File(indexHtmlPath).createNewFile();
			// Creating the file streams
			fs = new FileInputStream(System.getProperty("user.dir")
					+ "//src//com//automation//config//config.properties");
			// Setting the configuration details for the report
			CONFIG = new Properties();
			CONFIG.load(fs);
			release = CONFIG.getProperty("release");
			environment = CONFIG.getProperty("environment");
			fstream = new FileWriter(indexHtmlPath);
			out = new BufferedWriter(fstream);
			suiteXLS = new Xls_Reader(System.getProperty("user.dir")
					+ "//src//com//automation//xls//Suite.xlsx");
			// Setting the configuration details in the index page
			out.write("<html><HEAD><TITLE>"
					+ indexPageHeading
					+ "</TITLE></HEAD><body><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> "
					+ indexPageHeading
					+ " Test Results</u></b></h4><table  border=1 cellspacing=1 cellpadding=1 ><tr><h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> <u>Test Details :</u></h4><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Date</b></td><td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
			out.write(new Date().toString());
			out.write("</b></td></tr><tr><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Environment</b></td><td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
			out.write(environment);
			out.write("</b></td></tr><tr><td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Release</b></td><td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>");
			out.write(release);
			out.write("</b></td></tr></table><h4> <FONT COLOR=660000 FACE= Arial  SIZE=4.5> <u>Report :</u></h4><table  border=1 cellspacing=1 cellpadding=1 width=100%><tr><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>SUITE NAME</b></td><td width=40% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>DESCRIPTION</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>EXECUTION RESULT</b></td></tr>");
			totalTestSuites = suiteXLS.getRowCount(Constants.TEST_SUITE_SHEET);
			/**
			 * Suites Loop starts Runs total number of suites times
			 */
			for (int currentSuiteID = 2; currentSuiteID <= totalTestSuites; currentSuiteID++) {
				// Checking the run mode of the suites and skips to the next iteration if the run mode is 'N'
				if (suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE, currentSuiteID)
						.equalsIgnoreCase("N")) {
					continue;
				}
				testCaseResult = true;
				currentTestSuite = suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.SUITE_ID,
						currentSuiteID);
				currentSuiteXLS = new Xls_Reader(System.getProperty("user.dir")
						+ "//src//com//automation//xls//" + currentTestSuite + ".xlsx");
				testCaseRows = currentSuiteXLS.getRowCount(Constants.TEST_CASES_SHEET);
				testCaseCols = currentSuiteXLS.getColumnCount(Constants.TEST_CASES_SHEET);
				// Creating individual test case HTML sheet
				testCaseHtmlPath = resultFolderName + "//" + currentTestSuite + "_cases.html";
				new File(testCaseHtmlPath).createNewFile();
				fstreamTestCases = new FileWriter(testCaseHtmlPath);
				outTestCases = new BufferedWriter(fstreamTestCases);
				outTestCases
						.write("<html><HEAD> <TITLE>"
								+ currentTestSuite
								+ " Test Cases</TITLE></HEAD><body><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u>"
								+ currentTestSuite
								+ "Test Cases</u></b></h4><table width=100% border=1 cellspacing=1 cellpadding=1 >");
				outTestCases
						.write("<tr><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>TCID</b></td><td width=40% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>DESCRIPTION</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>EXECUTION RESULT</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>START TIME</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>END TIME</b></td></tr>");
				outTestCases.write("<tr>");
				outTestCases.write("</b></tr>");
				testCasePassedCount = 0;
				testCaseFailedCount = 0;
				/**
				 * TestCase Loop starts (Test Cases tab) Runs total number of test cases present in the tab
				 */
				for (int currentTestCaseID = 2; currentTestCaseID <= testCaseRows; currentTestCaseID++) {
					currentTestName = currentSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID,
							currentTestCaseID);
					// Checking the run mode of the suites and skips to the next iteration if the run mode is
					// 'N'
					if (currentSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE,
							currentTestCaseID).equalsIgnoreCase("N")
							|| currentSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID,
									currentTestCaseID).length() == 0) {
						continue;
					}
					System.out.println(currentTestSuite + " -- " + currentTestName);
					System.out.println("TCID "
							+ currentSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID,
									currentTestCaseID));
					testCaseName = currentSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID,
							currentTestCaseID);
					// Storing the number of total number of columns and rows
					testStepRows = currentSuiteXLS.getRowCount(Constants.TEST_STEPS_SHEET);
					testStepCols = currentSuiteXLS.getColumnCount(Constants.TEST_STEPS_SHEET);
					testStepRowIndex = 0;
					lastIndex = 0;
					terminalCount = 1;
					// Checking the row index of the first existence of the selected testcase
					for (int innerRow = 0; innerRow < testStepRows; innerRow++) {
						if ((currentSuiteXLS
								.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID, innerRow)
								.equalsIgnoreCase(testCaseName))) {
							testStepRowIndex = innerRow;
							while (currentSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID,
									innerRow).equalsIgnoreCase(testCaseName)
									|| currentSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET,
											Constants.TCID, innerRow).isEmpty()) {
								innerRow++;
								if (currentSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID,
										innerRow).isEmpty()) {
									if (terminalCount > 3) {
										break;
									}
									terminalCount++;
								}
							}
							lastIndex = innerRow - 1;
							break;
						}
					}
					// make the file corresponding to test Steps
					String testSteps_file = resultFolderName + "//" + testCaseName + "_steps.html";
					new File(testSteps_file).createNewFile();
					// Creating the file streams for reporting test steps
					fstreamTestSteps = new FileWriter(testSteps_file);
					outTestSteps = new BufferedWriter(fstreamTestSteps);
					// Detailed Report starting
					outTestSteps
							.write("<html><HEAD> <TITLE>"
									+ testCaseName
									+ " Test Results</TITLE></HEAD><body><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> "
									+ testCaseName
									+ " Detailed Test Results</u></b></h4><table width=100% border=1 cellspacing=1 cellpadding=1 >");
					outTestSteps.write("<tr>");
					for (int colNum = 0; colNum < testStepCols; colNum++) {
						outTestSteps
								.write("<td align= center bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
						outTestSteps
								.write(currentSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1));
					}
					outTestSteps.write("</b></tr>");
					testStepResult = true;
					skipFound = false;
					resultFound = false;
					for (int subInnerRows = testStepRowIndex; subInnerRows <= lastIndex; subInnerRows++) {
						outTestSteps.write("<tr>");
						for (int subInnerCols = 0; subInnerCols < testStepCols; subInnerCols++) {
							data = currentSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, subInnerCols,
									subInnerRows);
							resultCol = currentSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, subInnerCols,
									1).startsWith(Constants.RESULT);
							if (data.isEmpty()) {
								if (resultCol) {
									data = "  ";
								} else {
									data = " ";
								}
							}
							if ((data.startsWith("Pass") || data.startsWith("PASS")) && resultCol) {
								outTestSteps
										.write("<td align=center bgcolor=#009925><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
								resultFound = true;
							} else if ((data.startsWith("Fail") || data.startsWith("FAIL")) && resultCol) {
								outTestSteps
										.write("<td align=center bgcolor=#DD4B39><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
								testStepResult = false;
								testCaseResult = false;
								resultFound = true;
							} else if ((data.startsWith("Skip") || data.startsWith("SKIP")) && resultCol) {
								outTestSteps
										.write("<td align=center bgcolor=yellow><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
								skipFound = true;
							} else {
								outTestSteps
										.write("<td align= center bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
							}
							outTestSteps.write(data);
						}
						outTestSteps.write("</tr>");
					}
					outTestSteps.write("</tr>");
					outTestSteps.write("</table>");
					outTestSteps.close();
					outTestCases.write("<tr><b>");
					for (int colNum = 0; colNum < (testCaseCols - 1); colNum++) {
						data = currentSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, colNum,
								currentTestCaseID);
						if (currentSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, colNum, 1).contains(
								"Mapped TCID")
								|| currentSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, colNum, 1)
										.contains("Automated TC Count")) {
							continue;
						}
						if (colNum == 0) {
							outTestCases
									.write("<td align= center bgcolor=#ffffff><FONT COLOR=153E7E FACE= Arial  SIZE=2><b>");
							outTestCases.write("<a href=" + testCaseName.replace(" ", "%20") + "_steps.html>"
									+ testCaseName + "</a>");
						} else if (colNum == 2) {
							if (skipFound == true && resultFound == false) {
								outTestCases
										.write("<td align= center bgcolor=yellow><FONT COLOR=153E7E FACE= Arial  SIZE=2><b>");
								outTestCases.write("SKIP");
							} else if (testStepResult == true) {
								outTestCases
										.write("<td align= center bgcolor=#009925><FONT COLOR=153E7E FACE= Arial  SIZE=2><b>");
								outTestCases.write("PASS");
								testCasePassedCount++;
								totalTestPassedCount++;
							} else if (testStepResult == false) {
								outTestCases
										.write("<td align= center bgcolor=#DD4B39><FONT COLOR=153E7E FACE= Arial  SIZE=2><b>");
								outTestCases.write("FAIL");
								testCaseFailedCount++;
								totalTestFailedCount++;
							} else {
								outTestCases
										.write("<td align= center bgcolor=yellow><FONT COLOR=153E7E FACE= Arial  SIZE=2><b>");
								outTestCases.write("FAIL");
							}
						} else {
							outTestCases
									.write("<td align= center bgcolor=#ffffff><FONT COLOR=153E7E FACE= Arial  SIZE=2><b>");
							outTestCases.write(data);
						}
						outTestCases.write("</b></td>");
					}
					outTestCases.write("</tr>");
				}
				outTestCases.write("</b></tr>");
				outTestCases.write("</table>");
				if (!((testCasePassedCount + testCaseFailedCount) == 0)) {
					outTestCases.write(makePiChartOffline(testCasePassedCount, testCaseFailedCount,
							currentTestSuite + " Overview"));
					outTestCases.write("<div id='chartContainer' style='height: 350px; width: 100%;'></div>");
				}
				outTestCases.close();
				// comment
				out.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				// creating the link
				out.write("<a href=" + currentTestSuite.replace(" ", "%20") + "_cases.html>"
						+ currentTestSuite + "</a>");
				out.write("</b></td><td width=40% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				out.write(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.DESCRIPTION,
						currentSuiteID));
				out.write("</b></td><" + "td width=10% align=center  bgcolor=");
				if (suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE, currentSuiteID)
						.equalsIgnoreCase(Constants.RUNMODE_YES)) {
					if ((testCasePassedCount + testCaseFailedCount) == 0) {
						out.write("yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>SKIP</b></td></tr>");
					} else if (testCaseResult == false) {
						out.write("#DD4B39><FONT COLOR=153E7E FACE=Arial SIZE=2><b>FAIL</b></td></tr>");
					} else if (testCaseResult == true) {
						out.write("#009925><FONT COLOR=153E7E FACE=Arial SIZE=2><b>PASS</b></td></tr>");
					} else {
						out.write("yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>SKIP</b></td></tr>");
					}
				}
			}
			// Close the output stream
			out.write("</table>");
			out.write("<table id='chart table' style='width:100%;'><tr><td style='width:50%;'>");
			out.write("<div>");
			out.write("<div>");
			out.write(makePiChartOffline(totalTestPassedCount, totalTestFailedCount, "Overview"));
			out.write("</div>");
			out.write("<div id='chartContainer' style='height: 350px;'></div>");
			out.write("</div>");
			out.write("</td><td><div><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b><p>TOTAL TEST CASE RUN : "
					+ (totalTestFailedCount + totalTestPassedCount) + "</p>");
			out.write("<p><FONT COLOR=#009925>PASSED  : " + totalTestPassedCount + "</p>");
			out.write("<p><FONT COLOR=#DD4B39>FAILED  : " + totalTestFailedCount + "</p>");
			out.write("</b>");
			out.write("</div></td></tr></table>");
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		Zip.zip(System.getProperty("user.dir") + "/" + ReportUtil.resultFolderName,
				CONFIG.getProperty("report_file_name"));
		
		SendMail.execute(CONFIG.getProperty("report_file_name"));
	}
}
