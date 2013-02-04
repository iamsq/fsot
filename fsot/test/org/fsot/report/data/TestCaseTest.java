package org.fsot.report.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCaseTest {

	private TestCase testCase;
	private TestCase nullCase;
	
	@Before
	public void setUp() throws Exception {
		testCase = new TestCase("name", "comment");
		nullCase = new TestCase("nullName");
	}

	@After
	public void tearDown() throws Exception {
		testCase = null;
	}
	
	@Test
	public void testAddCoverageList() {
		CoverageList testList = new CoverageList(getCoverageList());
		testCase.addCoverageList(testList);
		assertTrue(testCase.getCoverageLists().contains(testList));
	}
	
	private ArrayList<CoveragePrefix> getCoverageList() {
		Collection<Coverage> covCollection = new ArrayList<Coverage>();
		Coverage cov1 = new Coverage("CovItemId", 1);
		Coverage cov2 = new Coverage("nullCovItemId");
		covCollection.add(cov1);
		covCollection.add(cov2);
		CoveragePrefix cp1 = new CoveragePrefix("cp1", covCollection);
		CoveragePrefix cp2 = new CoveragePrefix("cp2");
		ArrayList<CoveragePrefix> coverageList = new ArrayList<CoveragePrefix>();
		coverageList.add(cp1);
		coverageList.add(cp2);
		return coverageList;
	}

	@Test
	public void testSetComment() {
		testCase.setComment("newComment");
		assertEquals("newComment", testCase.getComment());
	}

	@Test
	public void testGetName() {
		assertEquals("name", testCase.getName());
	}
	
	@Test
	public void testNullGetName() {
		assertEquals("nullName", nullCase.getName());
	}

	@Test
	public void testGetComment() {
		assertEquals("comment", testCase.getComment());
	}
	
	@Test
	public void testNullGetComment() {
		assertEquals("", nullCase.getComment());
	}

	@Test
	public void testGetCoverageLists() {
		List<CoverageList> emptyList = new ArrayList<CoverageList>();
		assertEquals(emptyList, testCase.getCoverageLists());
	}

}
