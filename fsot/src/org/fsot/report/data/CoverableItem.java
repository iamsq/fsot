package org.fsot.report.data;

/**
 * As stated in the CodeCover documentation, <code>CoverableItem</code>
 * represents a coverable item which can be covered in a test case.
 * 
 * @author Shahriar Tajbakhsh
 * @version 15/01/2013
 */
public class CoverableItem {
	private String sourceFileId;
	private String covItemId;
	private long endOffset;
	private long startOffset;
	
	public CoverableItem(String sourceFileId, String covItemId) {
		this(sourceFileId, covItemId, -1, -1);
	}	
	
	public CoverableItem(String sourceFileId, String covItemId, long endOffset, long startOffset) {
		this.sourceFileId = sourceFileId;
		this.covItemId = covItemId;
		this.endOffset = endOffset;
		this.startOffset = startOffset;
	}
	
	public String getSourceFileId() {
		return sourceFileId;
	}
	
	public String getCovItemId() {
		return covItemId;
	}
	
	public long getEndOffset() {
		return endOffset;
	}
	
	public long getStartOffset() {
		return startOffset;
	}
	
	public void setSourceFileId(String sourceInternalId) {
		this.sourceFileId = sourceInternalId;
	}
	
	public void setCovItemId(String covItemId) {
		this.covItemId = covItemId;
	}
	
	public void setEndOffset(long endOffset) {
		this.endOffset = endOffset;
	}
	
	public void setStartOffset(long startOffset) {
		this.startOffset = startOffset;
	}
}
