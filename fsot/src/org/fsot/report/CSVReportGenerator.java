package org.fsot.report;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.fsot.report.data.Coverage;
import org.fsot.report.data.CoverageList;
import org.fsot.report.data.CoveragePrefix;
import org.fsot.report.data.TestCase;

public class CSVReportGenerator {

	private static final String TEST_NAME = "Test Name";
	private static final String PASS_FAIL_ERROR = "Pass/Fail/Error";
	private static final String DELIMITER = ",";
	private static final String NEW_LINE = "\n";
	private static final String IGNORE = "-1";
	private static final String NO_OCCURENCE = "0";
	private static final String FAILURE = "Failure";
	private static final String PASS = "Pass";
	private static final String FAIL = "Fail";
	private static final String ERROR = "Error";

	private CSVReportGenerator() {
	}

	public static void generateCSVReport(List<TestCase> testCaseList, File outputFile) throws FileNotFoundException {
		PrintWriter output = new PrintWriter(new BufferedOutputStream(new FileOutputStream(outputFile)));
		putOutHeader(testCaseList, output);
		output.close();
	}
	
	private static void putOutHeader(List<TestCase> testCaseList, PrintWriter output) {
		// Put out header
		output.print(TEST_NAME);
		output.print(DELIMITER);
		output.print(PASS_FAIL_ERROR);
		output.print(DELIMITER);
		
		Map<CoveragePrefix, TreeSet<String>> statements = getStatements(testCaseList);
		Iterator<Map.Entry<CoveragePrefix,TreeSet<String>>> it = statements.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<CoveragePrefix, TreeSet<String>> pairs = (Map.Entry<CoveragePrefix, TreeSet<String>>) it.next();
//	        System.out.println(pairs.getKey().getCovItemPrefix());
	        output.print(pairs.getKey().getCovItemPrefix() + DELIMITER);
	        for (String covItemId : pairs.getValue()) {
	        	output.print(covItemId + DELIMITER);
	        }
	        it.remove();	// avoids a ConcurrentModificationException
	    }
	    
		// Put out body
	    statements = getStatements(testCaseList);
	    for (TestCase testCase : testCaseList) {
			output.print(NEW_LINE);
			output.print(testCase.getName() + DELIMITER);
			String comment = testCase.getComment();
			if (comment.contains(FAILURE)) {
				output.print(FAIL);
			} else if (comment.contains(ERROR)) {
				output.print(ERROR);
			} else {
				output.print(PASS);
			}
			output.print(DELIMITER);
			
			List<CoverageList> coverageLists = testCase.getCoverageLists();
			// There is only one CoverageList, so:
			List<CoveragePrefix> coveragePrefixes = Arrays.asList(coverageLists.get(0).getCoveragePrefix());
			
			it = statements.entrySet().iterator();
		    while (it.hasNext()) {
		    	output.print(IGNORE + DELIMITER);
		        Map.Entry<CoveragePrefix, TreeSet<String>> pairs = (Map.Entry<CoveragePrefix, TreeSet<String>>) it.next();
		        int tempIndex = coveragePrefixes.indexOf(pairs.getKey());
		        if (tempIndex != -1) {
		        	List<Coverage> coverages = Arrays.asList(coveragePrefixes.get(tempIndex).getCoverages());
		        	for (String coverage : pairs.getValue()) {
						if (coverages.contains(new Coverage(coverage))) {
							int index = coverages.indexOf(new Coverage(coverage));
							int value = coverages.get(index).getValue();
							output.print(value + DELIMITER);
						} else {
							output.print(NO_OCCURENCE + DELIMITER);
						}
					}
		        } else {
		        	for (int i = 0; i < pairs.getValue().size(); i++) {
		        		output.print(NO_OCCURENCE + DELIMITER);
		        	}
		        }
		    }
	    }
	}
	
	private static Map<CoveragePrefix, TreeSet<String>> getStatements(List<TestCase> testCaseList) {
		Map<CoveragePrefix, TreeSet<String>> result = new HashMap<CoveragePrefix, TreeSet<String>>();
		for (TestCase testCase : testCaseList) {
			List<CoverageList> coverageListList = testCase.getCoverageLists();
			for (CoverageList coverageList : coverageListList) {
				CoveragePrefix[] coveragePrefixList = coverageList.getCoveragePrefix();
				for (CoveragePrefix coveragePrefix : coveragePrefixList) {
					if (result.get(coveragePrefix) == null) {
						result.put(coveragePrefix, new TreeSet<String>());
					}
					Coverage[] covList = coveragePrefix.getCoverages();
					for (Coverage coverage : covList) {
						result.get(coveragePrefix).add(coverage.getCovItemId());
					}
				}
			}
		}
		return result;
	}
}