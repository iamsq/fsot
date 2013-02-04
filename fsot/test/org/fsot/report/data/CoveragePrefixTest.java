package org.fsot.report.data;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CoveragePrefixTest {
	
	private CoveragePrefix testItem;
	private CoveragePrefix nullItem;
	private Collection<Coverage> covCollection;
	private Coverage cov1;
	private Coverage cov2;
	
	@Before
	public void setUp() throws Exception {
		covCollection = new ArrayList<Coverage>();
		cov1 = new Coverage("CovItemId", 1);
		cov2 = new Coverage("nullCovItemId");
		covCollection.add(cov1);
		covCollection.add(cov2);
		testItem = new CoveragePrefix("CovItemPrefix", covCollection);
		nullItem = new CoveragePrefix("nullCovItemPrefix");
	}

	@After
	public void tearDown() throws Exception {
		testItem = null;
		nullItem = null;
		cov1 = null;
		cov2 = null;
	}

	@Test
	public void testGetCovItemPrefix() {
		assertEquals("CovItemPrefix", testItem.getCovItemPrefix());
	}
	
	@Test
	public void testGetCovItemPrefixNullItem() {
		assertEquals("nullCovItemPrefix", nullItem.getCovItemPrefix());
	}

	@Test
	public void testGetCoverages() {
		Coverage[] covArray = new Coverage[2];
		covArray[0] = cov1;
		covArray[1] = cov2;
		assertTrue(Arrays.deepEquals(covArray, testItem.getCoverages()));
	}

	@Test
	public void testAddCoverage() {
		Coverage cov3 = new Coverage("cov3ItemId", 2);
		testItem.addCoverage(cov3);
		assertTrue(Arrays.asList(testItem.getCoverages()).contains(cov3));
	}

	@Test
	public void testEqualsObject() {
		CoveragePrefix cp = new CoveragePrefix("CovItemPrefix");
		assertTrue(testItem.equals(cp));
	}

	@Test
	public void testToString() {
		assertEquals("\"CovItemPrefix\"", testItem.toString());
	}

}
