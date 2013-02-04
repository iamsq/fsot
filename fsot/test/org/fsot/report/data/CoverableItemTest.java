package org.fsot.report.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CoverableItemTest {

	private CoverableItem testItem;
	private CoverableItem nullItem;
	
	@Before
	public void setUp() throws Exception {
		testItem = new CoverableItem("sourceFileId", "coverableItemId", 123456, 987654);
		nullItem = new CoverableItem("nullSrcFileId", "nullCovItemId");
	}

	@After
	public void tearDown() throws Exception {
		testItem = null;
		nullItem = null;
	}

	@Test
	public void testGetSourceFileId() {
		assertEquals("sourceFileId", testItem.getSourceFileId());
	}

	@Test
	public void testGetCovItemId() {
		assertEquals("coverableItemId", testItem.getCovItemId());
	}

	@Test
	public void testGetEndOffset() {
		assertEquals(123456, testItem.getEndOffset());
	}

	@Test
	public void testGetStartOffset() {
		assertEquals(987654, testItem.getStartOffset());
	}

	@Test
	public void testSetSourceFileId() {
		testItem.setSourceFileId("newInternalId");
		assertEquals("newInternalId", testItem.getSourceFileId());
	}

	@Test
	public void testSetCovItemId() {
		testItem.setCovItemId("newCovItemId");
		assertEquals("newCovItemId", testItem.getCovItemId());
	}

	@Test
	public void testSetEndOffset() {
		testItem.setEndOffset(13579);
		assertEquals(13579, testItem.getEndOffset());
	}

	@Test
	public void testSetStartOffset() {
		testItem.setStartOffset(97531);
		assertEquals(97531, testItem.getStartOffset());
	}
	
	@Test
	public void testNullCoverableItemSetsSourceFileId() {
		assertEquals("nullSrcFileId", nullItem.getSourceFileId());
	}
	
	@Test
	public void testNullCoverableItemSetsCovItemId() {
		assertEquals("nullCovItemId", nullItem.getCovItemId());
	}
	
	@Test
	public void testNullCoverableItemSetsEndOffset() {
		assertEquals(-1, nullItem.getEndOffset());
	}
	
	@Test
	public void testNullCoverableItemSetsStartOffset() {
		assertEquals(-1, nullItem.getStartOffset());
	}

}
