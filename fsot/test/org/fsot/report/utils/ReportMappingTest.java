package org.fsot.report.utils;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.fsot.report.data.CoverableItem;
import org.fsot.report.data.SrcFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ReportMappingTest {
	
	private Document xmlTestDoc;
	private File tempXml; 

	@Before
	public void setUp() throws Exception {
		tempXml = new File("./temp.xml");
		//create the list of srcFile
		SrcFile sf1 = new SrcFile("id1", "filename1", "content1");
		ArrayList<SrcFile> srcFileList = new ArrayList<SrcFile>();
		srcFileList.add(sf1);		
		//create the list of coverableItems
		CoverableItem ci1 = new CoverableItem("id1", "ciId1", 12345, 98765);
		ArrayList<CoverableItem> coverableItemList = new ArrayList<CoverableItem>();
		coverableItemList.add(ci1);
		xmlTestDoc = ReportMapping.getMappingInXml(srcFileList, coverableItemList);
		Element root = (Element) xmlTestDoc.getElementsByTagName("FSOTReportMapping").item(0);
		root.setAttribute("Date", "Foo");
		Utils.generateXml(xmlTestDoc, tempXml);
	}

	@After
	public void tearDown() throws Exception {
		tempXml.delete();
		xmlTestDoc = null;
	}

	@Test
	public void testGetMappingInXml()
			throws IOException, FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(tempXml));
		String line;
		StringBuilder sb = new StringBuilder();
		while((line=br.readLine())!= null){
		    sb.append(line.trim());
		}
		br.close();
		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><FSOTReportMapping Date=\"Foo\"><SourceFiles Count=\"1\"><SourceFile Id=\"id1\" Name=\"filename1\"><CoverableItem EndOffset=\"12345\" Id=\"ciId1\" StartOffset=\"98765\"/></SourceFile></SourceFiles></FSOTReportMapping>", sb.toString());
	}

}
