package edu.washington.cs.dt.tools.explain;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class TestExplainer extends TestCase {

	//all cases in crystal
	public void testCrystal_1() {
		String traceFolder = "E:\\testisolation\\dt-instrument-folder\\crystal\\dt-output-folder";
		String depTest = "crystal.model.DataSourceTest.testSetKind";
		List<String> depSeqs = Arrays.asList("crystal.model.DataSourceTest.testSetField");
		DTExplainer explainer = new DTExplainer(traceFolder, depTest, depSeqs);
		explainer.identifyConflictAccess();
	}
	
	public void testCrystal_2() {
		String traceFolder = "E:\\testisolation\\dt-instrument-folder\\crystal\\dt-output-folder";
		String depTest = "crystal.model.LocalStateResultTest.testToString";
		List<String> depSeqs = Arrays.asList("crystal.model.LocalStateResultTest.testLocalStateResult");
		DTExplainer explainer = new DTExplainer(traceFolder, depTest, depSeqs);
		explainer.identifyConflictAccess();
	}
	
	
	//test xml security
	public void testXMLSecurity() {
		String traceFolder = "E:\\testisolation\\dt-instrument-folder\\xml-security";
		String depTest = "";
		List<String> depSeqs = Arrays.asList("");
		DTExplainer explainer = new DTExplainer(traceFolder, depTest, depSeqs);
		explainer.identifyConflictAccess();
	}
	
	//E:\testisolation\dt-instrument-folder\jfreechart\dt-output-folder
	public void testJFreechart_1() {
		String traceFolder = "E:\\testisolation\\dt-instrument-folder\\jfreechart\\dt-output-folder\\";
		String depTest = "org.jfree.chart.axis.junit.SegmentedTimelineTests.testMondayThroughFridaySegmentedTimeline";
		List<String> depSeqs = Arrays.asList("org.jfree.chart.axis.junit.SegmentedTimelineTests2.test6");
		DTExplainer explainer = new DTExplainer(traceFolder, depTest, depSeqs);
		explainer.identifyConflictAccess();
	}

	public void testJFreechart_2() {
		String traceFolder = "E:\\testisolation\\dt-instrument-folder\\jfreechart\\dt-output-folder\\";
		String depTest = "org.jfree.data.time.junit.DayTests.testParseDay";
		List<String> depSeqs = Arrays.asList("org.jfree.data.time.junit.HourTests.testGetLastMillisecond");
		DTExplainer explainer = new DTExplainer(traceFolder, depTest, depSeqs);
		explainer.identifyConflictAccess();
	}
	
	public void testJFreechart_3() {
		String traceFolder = "E:\\testisolation\\dt-instrument-folder\\jfreechart\\dt-output-folder\\";
		String depTest = "org.jfree.data.time.junit.MonthTests.testParseMonth";
		List<String> depSeqs = Arrays.asList("org.jfree.data.time.junit.MillisecondTests.testGetStart");
		DTExplainer explainer = new DTExplainer(traceFolder, depTest, depSeqs);
		explainer.identifyConflictAccess();
	}
	
	//
}
