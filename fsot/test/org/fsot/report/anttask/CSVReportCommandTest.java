package org.fsot.report.anttask;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CSVReportCommandTest {
	
	private CSVReportCommand crc;
	private File file;
	private File dest;

	@Before
	public void setUp() throws Exception {
		crc = new CSVReportCommand();
		file = new File("tempName");
		dest = new File("destName");
	}

	@After
	public void tearDown() throws Exception {
		crc = null;
		file = null;
		dest = null;
	}

	@Test
	public void testSetFilename() {
		crc.setFilename(file);
		assertEquals(file, crc.filename);
	}

	@Test
	public void testSetDestination() {
		crc.setDestination(dest);
		assertEquals(dest, crc.destination);
	}
	
	@Test(expected = BuildException.class)
	public void testRunThrowsException() {
		crc.setFilename(file);
		crc.run();
	}

}
