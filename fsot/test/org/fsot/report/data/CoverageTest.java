package org.fsot.report.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CoverageTest {
	
	private Coverage testItem;
	private Coverage nullItem;

	@Before
	public void setUp() throws Exception {
		testItem = new Coverage("CovItemId", 1);
		nullItem = new Coverage("nullCovItemId");
	}

	@After
	public void tearDown() throws Exception {
		testItem = null;
		nullItem = null;
	}

	@Test
	public void testGetCovItemId() {
		assertEquals("CovItemId", testItem.getCovItemId());
	}

	@Test
	public void testGetValue() {
		assertEquals(1, testItem.getValue());
	}
	
	@Test
	public void testNullItemGetCovItemId() {
		assertEquals("nullCovItemId", nullItem.getCovItemId());
	}

	@Test
	public void testNullItemGetValue() {
		assertEquals(-1, nullItem.getValue());
	}

	@Test
	public void testCompareToEquals() {
		Coverage cov = new Coverage("CovItemId", 0);
		assertEquals(0, testItem.compareTo(cov));
	}
	
	@Test
	public void testCompareToBefore() {
		Coverage cov = new Coverage("CovItemIe", 0);
		assertEquals(-1, testItem.compareTo(cov));
	}
	
	@Test
	public void testCompareToAfter() {
		Coverage cov = new Coverage("CovItemIc", 0);
		assertEquals(1, testItem.compareTo(cov));
	}
	
	@Test
	public void testCompareToNullItem() {
		Coverage cov = new Coverage("nullCovItemId");
		assertEquals(0, nullItem.compareTo(cov));
	}

	@Test
	public void testEqualsObjectTrue() {
		Coverage cov = new Coverage("CovItemId");
		assertTrue(testItem.equals(cov));
	}
	
	@Test
	public void testEqualsObjectFalse() {
		Coverage cov = new Coverage("NotCovItemId");
		assertTrue(!testItem.equals(cov));
	}

	@Test
	public void testToString() {
		assertEquals("\"CovItemId\" : \"1\"", testItem.toString());
	}
	
	@Test
	public void testToStringNullItem() {
		assertEquals("\"nullCovItemId\" : \"-1\"", nullItem.toString());
	}

}
