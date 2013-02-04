package org.fsot.report.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class UtilsTest {

	Document testDoc;
	File dest;
	
	@Before
	public void setUp()
			throws ParserConfigurationException, TransformerException {
		//create document
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		testDoc = docBuilder.newDocument();
		Element root = testDoc.createElement("root");
		testDoc.appendChild(root);
		//create dest
		dest = new File("./temp.xml");
		Utils.generateXml(testDoc, dest);
	}
	
	@After
	public void tearDown() {
		dest.delete();
		testDoc = null;
		dest = null;
	}
	
	@Test
	public void testFileIsCreated() {
		assertTrue(dest.exists());
	}
	
	@Test
	public void testFileContents()
			throws IOException, FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(dest));
		String line;
		StringBuilder sb = new StringBuilder();

		while((line=br.readLine())!= null){
		    sb.append(line.trim());
		}
		br.close();
		
		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><root/>", sb.toString());
	}

}
