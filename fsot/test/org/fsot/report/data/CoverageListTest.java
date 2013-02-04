package org.fsot.report.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CoverageListTest {
	
	private CoverageList testItem;
	private CoverageList nullItem;
	
	@Before
	public void setUp() throws Exception {
		testItem = new CoverageList(getCoverageList());
		nullItem = new CoverageList();
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

	@After
	public void tearDown() throws Exception {
		testItem = null;
		nullItem = null;
	}

	@Test
	public void testNullConstructor() {
		assertTrue(nullItem.getCoveragePrefix().length == 0);
	}
	
	@Test
	public void testGetCoveragePrefix() {
		CoveragePrefix[] cp = getCoverageList().toArray(new CoveragePrefix[0]);
		assertTrue(Arrays.deepEquals(cp, testItem.getCoveragePrefix()));
	}

	@Test
	public void testAddCoveragePrefix() {
		Collection<Coverage> covCollection = new ArrayList<Coverage>();
		Coverage cov3 = new Coverage("newCovItemId", 3);
		covCollection.add(cov3);
		CoveragePrefix cp3 = new CoveragePrefix("cp1", covCollection);
		testItem.addCoveragePrefix(cp3);
		assertTrue(Arrays.asList(testItem.getCoveragePrefix()).contains(cp3));
	}

	@Test
	public void testEqualsObject() {
		CoverageList cl = new CoverageList(getCoverageList());
		assertTrue(testItem.equals(cl));
	}

	@Test
	public void testToString() {
		assertEquals("{ \"cp1\",\"cp2\" }", testItem.toString());
	}

}
