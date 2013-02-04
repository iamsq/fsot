package org.fsot.report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.fsot.report.data.CoverableItem;
import org.fsot.report.data.Coverage;
import org.fsot.report.data.CoverageList;
import org.fsot.report.data.CoveragePrefix;
import org.fsot.report.data.SrcFile;
import org.fsot.report.data.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * CodeCover Test Session Container XML Parser. This class provides to tools to
 * parse a CodeCover generated Test Session Container XML file and get a 
 * <Code>CoverageList</Code> object of it.
 * 
 * @author  Shahriar Tajbakhsh
 * @version	13/12/2012
 */
public class TestSessionContainerXMLParser {

	private static final String SRC_FILE_LIST = "SrcFileList";
	private static final String SRC_FILE = "SrcFile";
	private static final String INTERNAL_ID = "Intrnl_Id";
	private static final String FILENAME = "Filename";
	private static final String CONTENT = "Content";
	private static final String NAME = "Name";
	private static final String COMMENT = "Comment";
	
	private static final String TEST_SESSION = "TestSession";
	private static final String TEST_CASE = "TestCase";
	private static final String COV_LIST = "CovList";
	private static final String COV_PREFIX = "CovPrefix";
	private static final String COV = "Cov";
	private static final String COV_ITEM_PREFIX = "CovItemPrefix";
	private static final String COV_ITEM_ID = "CovItemId";
	private static final String VALUE = "Value";
	private static final String LOC_LIST = "LocList";
	private static final String LOC = "Loc";
	private static final String END_OFFSET = "EndOffset";
	private static final String START_OFFSET = "StartOffset";
	private static final String SRC_FILE_ID = "SrcFileId";

	private TestSessionContainerXMLParser() {
	}

	public static List<CoverableItem> getCoverableItemListFromXml(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(xmlFile);

		return getCoverableItemListFromXml(document);
	}

	public static List<CoverableItem> getCoverableItemListFromXml(Document dom) {
		List<CoverableItem> result = new ArrayList<CoverableItem>();
		/**
		 * Select every node in the document and iterate through them
		 */
		NodeList nodeList = dom.getElementsByTagName("*");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			if (currentNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element currentElement = (Element) currentNode;
			/**
			 * Find all non-COV elements with CovItemId attribute
			 */
			if (currentElement.hasAttribute(COV_ITEM_ID) && !currentElement.getNodeName().equals(COV)) {
				/**
				 * Iterate through all the child nodes to see if any of them
				 * are LocList
				 */
				NodeList currentChildNodes = currentElement.getChildNodes();
				for (int j = 0; j < currentChildNodes.getLength(); j++) {
					if (currentChildNodes.item(j).getNodeName().equals(LOC_LIST)) {
						Element currentChildElement = (Element) currentChildNodes.item(j);
						/**
						 * Iterate through all the LocList children to find
						 * the Loc element
						 */
						NodeList currentGrandChildNodes = currentChildElement.getChildNodes();
						for (int k = 0; k < currentGrandChildNodes.getLength(); k++) {
							if (currentGrandChildNodes.item(k).getNodeName().equals(LOC)) {
								Element currentGrandChildElement = (Element) currentGrandChildNodes.item(k);
								String sourceFileId = currentGrandChildElement.getAttribute(SRC_FILE_ID);
								String covItemId = currentElement.getAttribute(COV_ITEM_ID);
								long endOffset = Long.parseLong(currentGrandChildElement.getAttribute(END_OFFSET));
								long startOffset = Long.parseLong(currentGrandChildElement.getAttribute(START_OFFSET));
								
								result.add(new CoverableItem(sourceFileId, covItemId, endOffset, startOffset));
//								System.out.println(sourceFileId + " " + covItemId + " " + endOffset + " " + startOffset);
							}
						}
					}
				}
			}
		}
		return Collections.unmodifiableList(result);
	}
	
	public static List<TestCase> getTestCaseListFromXml(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(xmlFile);

		return getTestCaseListFromXml(document);
	}
	
	public static List<TestCase> getTestCaseListFromXml(Document dom) {
		List<TestCase> result = new ArrayList<TestCase>();
		// Get the root element
		Element rootElement = dom.getDocumentElement();
		NodeList testSessionElements = rootElement
				.getElementsByTagName(TestSessionContainerXMLParser.TEST_SESSION);
		// Fifty Shades of Grey will always have CodeCover produce 1 TestSession element in its XML output
		Element testSessionElement = (Element) testSessionElements.item(0);
		
		// Get all the TestSession tags
		NodeList testCaseElements = testSessionElement.getElementsByTagName(TEST_CASE);
		
		// Iterate through each TestCase
		for (int i = 0; i < testCaseElements.getLength(); i++) {
			Element testSession = (Element) testCaseElements.item(i);
			TestCase testCase = new TestCase(testSession.getAttribute(NAME), testSession.getAttribute(COMMENT));
			
			// Get all the CovList elements
			NodeList coverageListElements = testSession.getElementsByTagName(COV_LIST);
			for (int j = 0; j < coverageListElements.getLength(); j++) {
				CoverageList coverageList = new CoverageList();
				
				Element covList = (Element) coverageListElements.item(j);
				NodeList coveragePrefixElements = covList.getElementsByTagName(COV_PREFIX);
				
				for (int k = 0; k < coveragePrefixElements.getLength(); k++) {
					Element covElementPrefix = (Element) coveragePrefixElements.item(k);
					CoveragePrefix coveragePrefix = new CoveragePrefix(covElementPrefix.getAttribute(COV_ITEM_PREFIX));
					
					NodeList coverageElements = covElementPrefix.getElementsByTagName(COV);
					
					for (int l = 0; l < coverageElements.getLength(); l++) {
						Element cov = (Element) coverageElements.item(l);
						
						String covItemId = cov.getAttribute(COV_ITEM_ID);
						int value = Integer.parseInt(cov.getAttribute(VALUE));
						Coverage coverage = new Coverage(covItemId, value);
						coveragePrefix.addCoverage(coverage);
					}
					coverageList.addCoveragePrefix(coveragePrefix);
				}
				testCase.addCoverageList(coverageList);
			}
			result.add(testCase);
		}
		return Collections.unmodifiableList(result);
	}

	/**
	 * 
	 * @param xmlFile
	 *            a <Code>File</Code> object of the XML file generated by
	 *            CodeCover
	 * @return the source file list extracted from the XML file; null if there
	 *         is a problem
	 * @see SrcFileList
	 * @see File
	 */
	public static List<SrcFile> getSrcFileListFromXml(File xmlFile) {
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			Document dom = db.parse(xmlFile);
			return getSrcFileListFromXml(dom);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @param xmlFile
	 *            a <Code>Document</Code> object of the XML file generated by
	 *            CodeCover
	 * @return the source file list extracted from the XML file
	 * @see SrcFileList
	 * @see File
	 */
	private static List<SrcFile> getSrcFileListFromXml(Document dom) {

		List<SrcFile> result = new ArrayList<SrcFile>();

		// Get the root element which should be TestSessionContainer
		Element rootElement = dom.getDocumentElement();

		NodeList srcFileListElements = rootElement
				.getElementsByTagName(SRC_FILE_LIST);

		Element srcFileListElement = (Element) srcFileListElements.item(0);
		NodeList srcFileList = srcFileListElement
				.getElementsByTagName(SRC_FILE);

		for (int i = 0; i < srcFileList.getLength(); i++) {
			Element tempSrcFileElement = (Element) srcFileList.item(i);

			String internalId = tempSrcFileElement.getAttribute(INTERNAL_ID);
			String filename = tempSrcFileElement.getAttribute(FILENAME);
			String content = tempSrcFileElement.getAttribute(CONTENT);

			SrcFile srcFile = new SrcFile(internalId, filename, content);
			result.add(srcFile);
		}

		return Collections.unmodifiableList(result);
	}

	/**
	 * 
	 * @param xmlFile
	 *            a <Code>File</Code> object of the XML file generated by
	 *            CodeCover
	 * @return the coverage list extracted from the XML file
	 * @see CoverageList
	 * @see File
	 */
	public static CoverageList getCoverageListFromXML(File xmlFile) {
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			Document dom = db.parse(xmlFile);
			return TestSessionContainerXMLParser.getCoverageListFromXML(dom);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @param xmlFile
	 *            a <Code>Document</Code> object of the XML file generated by
	 *            CodeCover
	 * @return the coverage list extracted from the XML file
	 * @see CoverageList
	 * @see File
	 */
	private static CoverageList getCoverageListFromXML(Document dom) {

		CoverageList coverageList = new CoverageList();

		// Get the root element
		Element rootElement = dom.getDocumentElement();
		NodeList testSessionElements = rootElement
				.getElementsByTagName(TestSessionContainerXMLParser.TEST_SESSION);

		// Only using one of the TestSession elements for now
		Element testSessionElement = (Element) testSessionElements.item(0);
		NodeList testCaseElements = testSessionElement
				.getElementsByTagName(TestSessionContainerXMLParser.TEST_CASE);

		// Get a NodeList of CovList elements
		// There should only be one TestCase element
		Element testCaseElement = (Element) testCaseElements.item(0);
		NodeList coverageListElements = testCaseElement
				.getElementsByTagName(TestSessionContainerXMLParser.COV_LIST);

		// Get a NodeList of CovPrefix elements
		// There should only be one CovList element
		Element coverageListElement = (Element) coverageListElements.item(0);
		NodeList coveragePrefixElements = coverageListElement
				.getElementsByTagName(TestSessionContainerXMLParser.COV_PREFIX);

		for (int i = 0; i < coveragePrefixElements.getLength(); i++) {
			Element coveragePrefixElement = (Element) coveragePrefixElements
					.item(i);
			CoveragePrefix coveragePrefix = new CoveragePrefix(
					coveragePrefixElement
							.getAttribute(TestSessionContainerXMLParser.COV_ITEM_PREFIX));

			NodeList coverageElements = coveragePrefixElement
					.getElementsByTagName(TestSessionContainerXMLParser.COV);
			for (int j = 0; j < coverageElements.getLength(); j++) {
				Element coverageElement = (Element) coverageElements.item(j);

				String covItemId = coverageElement
						.getAttribute(TestSessionContainerXMLParser.COV_ITEM_ID);
				int value = Integer.parseInt(coverageElement
						.getAttribute(TestSessionContainerXMLParser.VALUE));
				Coverage coverage = new Coverage(covItemId, value);

				coveragePrefix.addCoverage(coverage);
			}
			coverageList.addCoveragePrefix(coveragePrefix);
		}

		return coverageList;
	}
}