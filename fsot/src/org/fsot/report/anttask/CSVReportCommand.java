package org.fsot.report.anttask;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.fsot.report.CSVReportGenerator;
import org.fsot.report.TestSessionContainerXMLParser;
import org.fsot.report.data.TestCase;
import org.xml.sax.SAXException;



public class CSVReportCommand extends Command {

	File filename;
	File destination;

	/**
	 * Sets the filename of the xml container.
	 * 
	 * @param destination
	 *            the filename to set
	 */
	public void setFilename(File filename) {
		this.filename = filename;
	}

	/**
	 * Sets the destination.
	 * 
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(File destination) {
		this.destination = destination;
	}

	public void run(){
		if (filename.exists() == false) {
			throw new BuildException("Container file " + filename
					+ "does not exist");
		}
		List<TestCase> testCases;
		try {
			testCases = TestSessionContainerXMLParser.getTestCaseListFromXml(filename);

			CSVReportGenerator.generateCSVReport(testCases, destination);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e.getMessage());
		}

		
	}

}
