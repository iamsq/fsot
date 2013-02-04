package org.fsot.report.utils;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.fsot.report.data.CoverableItem;
import org.fsot.report.data.SrcFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;




public class ReportMapping {

	/**
	 * Tag names
	 */
	private static final String ROOT_ELEMENT = "FSOTReportMapping";
	private static final String SOURCE_FILES_ELEMENT = "SourceFiles";
	private static final String SOURCE_FILE_ELEMENT = "SourceFile";
	private static final String COVERABLE_ITEM_ELEMENT = "CoverableItem";

	/**
	 * Attributes
	 */
	private static final String DATE = "Date";
	private static final String COUNT_ATTR = "Count";
	private static final String ID = "Id";
	private static final String NAME = "Name";
	private static final String START_OFFSET = "StartOffset";
	private static final String END_OFFSET = "EndOffset";

	/**
	 * An instance of this class must not be created
	 */
	private ReportMapping() {
	}

	/**
	 * Returns the <code>Document</code> object representing the mapping of the
	 * Fifty Shades of Testing output report in XML. This mapping specifies to
	 * which source file does a particular <code>CoverableItem</code> belong and
	 * at what offset can it be found.
	 * 
	 * @param srcFileList
	 *            The list of source files as returned by
	 *            {@link TestSessionContainerXMLParser#getSrcFileListFromXml(File)}
	 * @param coverableItemList
	 *            The list of coverable items as returned by
	 *            {@link TestSessionContainerXMLParser#getCoverableItemListFromXml(File)}
	 * @return A {@link Document} object containing the mapping of the Fifty
	 *         Shades of Testing output report in XML
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static Document getMappingInXml(List<SrcFile> srcFileList,
			List<CoverableItem> coverableItemList)
			throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.newDocument();

		// Create the root element
		Element rootElement = document.createElement(ROOT_ELEMENT);
		document.appendChild(rootElement);
		rootElement.setAttribute(DATE, String.valueOf(new Date().getTime()));

		// Create the SourceFiles element
		Element srcFilesElement = document.createElement(SOURCE_FILES_ELEMENT);
		rootElement.appendChild(srcFilesElement);
		// Set the Count attribute of SourceFiles element
		srcFilesElement.setAttribute(COUNT_ATTR,
				String.valueOf(srcFileList.size()));

		// Create SourceFile elements and append them to SourceFiles parent
		Element srcFileElement = null;
		for (SrcFile srcFile : srcFileList) {
			srcFileElement = document.createElement(SOURCE_FILE_ELEMENT);
			srcFilesElement.appendChild(srcFileElement);

			String srcFileInternalId = srcFile.getInternalId();
			srcFileElement.setAttribute(ID, String.valueOf(srcFileInternalId));
			srcFileElement.setAttribute(NAME, srcFile.getFilename());

			// Create and append all the CoverableItem elements for this source
			// file
			Element covItemElement = null;
			for (CoverableItem covItem : coverableItemList) {
				if (covItem.getSourceFileId().equals(srcFileInternalId)) {
					covItemElement = document
							.createElement(COVERABLE_ITEM_ELEMENT);
					srcFileElement.appendChild(covItemElement);

					covItemElement.setAttribute(ID,
							String.valueOf(covItem.getCovItemId()));
					covItemElement.setAttribute(END_OFFSET,
							String.valueOf(covItem.getEndOffset()));
					covItemElement.setAttribute(START_OFFSET,
							String.valueOf(covItem.getStartOffset()));
				}
			}
		}

		return document;
	}

}
