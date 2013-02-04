package org.fsot.report.anttask;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FsotTaskTest {
	
	private FsotTask ftask;
	private CSVReportCommand crc;
	private RunJUnitCommand rjc;
	private File file;
	private File dest;

	@Before
	public void setUp() throws Exception {
		file = new File("filename");
		dest = new File("destname");
		crc = new CSVReportCommand();
		crc.setFilename(file);
		crc.setDestination(dest);
		rjc = new RunJUnitCommand();
		ftask = new FsotTask();
	}

	@After
	public void tearDown() throws Exception {
		file = null;
		dest = null;
		crc = null;
		rjc = null;
		ftask = null;
	}

	@Test
	public void testAddConfiguredCSVReport() {
		ftask.addConfiguredCSVReport(crc);
		assertTrue(ftask.getCommands().contains(crc));
	}

	@Test
	public void testAddConfiguredRunJUnit() {
		ftask.addConfiguredRunJUnit(rjc);
		assertTrue(ftask.getCommands().contains(rjc));
	}
	
	@Test(expected = BuildException.class)
	public void testExecuteThrowsException() {
		ftask.addConfiguredCSVReport(crc);
		ftask.execute();
	}

}
