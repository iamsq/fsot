package org.fsot.report.data;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class SrcFileTest {

private SrcFile srcFileForTest;
	
	@Before
	public void setUp() {
		srcFileForTest = new SrcFile("internalId", "filename", "content");
	}

	@After
	public void tearDown() {
		srcFileForTest = null;
	}

	@Test
	public void getInternalIdShouldReturnId() {
		assertEquals("internalId", srcFileForTest.getInternalId());
	}

	@Test
	public void getFilenameShouldReturnFilename() {
		assertEquals("filename", srcFileForTest.getFilename());
	}

	@Test
	public void getContentShouldReturnContent() {
		assertEquals("content", srcFileForTest.getContent());
	}

	@Test
	public void equalsShouldBeOverridden() {
		SrcFile duplicateSrcFile = new SrcFile("internalId", "filename", "content");
		assertTrue(srcFileForTest.equals(duplicateSrcFile));
	}

	@Test
	public void toStringShouldBeOverridden() {
		assertEquals("filename(internalId)", srcFileForTest.toString());
	}

}
