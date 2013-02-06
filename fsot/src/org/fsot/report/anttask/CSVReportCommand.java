package org.fsot.report.anttask;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.fsot.report.CSVReportGenerator;
import org.fsot.report.TestSessionContainerXMLParser;
import org.fsot.report.data.CoverableItem;
import org.fsot.report.data.SrcFile;
import org.fsot.report.data.TestCase;
import org.fsot.report.utils.ReportMapping;
import org.fsot.report.utils.Utils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;



public class CSVReportCommand extends Command {

	File filename;
	File destination;
	File mapping;

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
	 * Sets the destination of the CSV output
	 * 
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(File destination) {
		this.destination = destination;
	}
	
	/**
	 * Sets the destination of the CSV output
	 * 
	 * @param destination
	 *            the destination to set
	 */
	public void setMapping(File mapping) {
		this.mapping = mapping;
	}

	public void run(){
		if (filename.exists() == false) {
			throw new BuildException("Container file " + filename
					+ "does not exist");
		}
		List<TestCase> testCases;
		List<SrcFile> srcFileList;
		List<CoverableItem> coverableItemList;
		try {
			//Generate CSV output
			log("Getting Test case list from Xml...");
			testCases = TestSessionContainerXMLParser.getTestCaseListFromXml(filename);
			log("Generating CSV Report...");
			CSVReportGenerator.generateCSVReport(testCases, destination);
			log("CSV Report generated: "+destination.getCanonicalPath());
			
			
			//Generate Mapping output
			log("Generating mapping file...");
			srcFileList = 
					TestSessionContainerXMLParser.getSrcFileListFromXml(filename);
			coverableItemList = 
					TestSessionContainerXMLParser.getCoverableItemListFromXml(filename);
			Document document = ReportMapping.getMappingInXml(srcFileList, coverableItemList);
			Utils.generateXml(document, mapping);
			log("XML mapping generated: "+mapping.getCanonicalPath());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e.getMessage());
		}

		
	}

}
