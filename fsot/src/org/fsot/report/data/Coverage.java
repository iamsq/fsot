package org.fsot.report.data;

/*
 * Class for representing a single coverage item as specified by CodeCover.
 * 
 * @author  Shahriar Tajbakhsh
 * @version	13/12/2012
 */
public class Coverage implements Comparable<Coverage> {

	private String covItemId;
	private int value;

	public Coverage(String covItemId) {
		this(covItemId, -1);
	}
	/**
	 * Class constructor specifying coverage item ID and value as specified by
	 * CodeCover.
	 * 
	 * @param covItemId
	 *            coverage item ID
	 * @param value
	 *            coverage item value specifying how many times the item has
	 *            been executed
	 */
	public Coverage(String covItemId, int value) {
		this.covItemId = covItemId;
		this.value = value;
	}

	/**
	 * 
	 * @return the coverage item ID
	 */
	public String getCovItemId() {
		return this.covItemId;
	}

	/**
	 * 
	 * @return the coverage item value
	 */
	public int getValue() {
		return this.value;
	}
	
	@Override
	public int compareTo(Coverage that) {
		final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;
	    
	    if ( this == that ) {
	    	return EQUAL;
	    }
	    
	    if (this.covItemId.equals(that.getCovItemId())) {
	    	return EQUAL;
	    }
	    
	    if (this.covItemId.compareTo(that.getCovItemId()) < EQUAL) {
	    	return BEFORE;
	    }
	    
	    if (this.covItemId.compareTo(that.getCovItemId()) > EQUAL) {
	    	return AFTER;
	    }
	    
	    // The statements below should never be reached.
	    assert this.equals(that) : "compareTo inconsistent with equals.";
	    return EQUAL;
	}

	@Override
	public boolean equals(Object coverage) {
		if ((coverage != null) && coverage instanceof Coverage) {
			return this.getCovItemId().equals(
					((Coverage) coverage).getCovItemId());
		}

		return false;
	}

	@Override
	public String toString() {
		return "\"" + this.covItemId + "\"" + " : " + "\"" + this.value + "\"";
	}
}