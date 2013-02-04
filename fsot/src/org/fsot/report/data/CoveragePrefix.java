package org.fsot.report.data;

import java.util.ArrayList;
import java.util.Collection;

/*
 * Class for representing a list of coverages and their coverage prefix
 * as specified in CodeCover.
 * 
 * @author  Shahriar Tajbakhsh
 * @version	13/12/2012
 */
public class CoveragePrefix {

	private String covItemPrefix;
	private Collection<Coverage> coverages;

	/**
	 * Class constructor for creating a <Code>CoveragePrefix</Code> object with
	 * a coverage prefix and a null <Code>Collection</Code> of coverages.
	 * 
	 * @param covItemPrefix
	 *            the coverage item prefix.
	 */
	public CoveragePrefix(String covItemPrefix) {
		this(covItemPrefix, new ArrayList<Coverage>());
	}

	/**
	 * Class constructor for creating a <Code>CoveragePrefix</Code> object with
	 * a coverage prefix and a <Code>Collection</Code> of coverages.
	 * 
	 * @param covItemPrefix
	 *            the coverage item prefix.
	 * @param coverages
	 *            a <Code>Collection</Code> of <Code>Coverage</Code> objects
	 * @see Coverage
	 * @see Collection
	 */
	public CoveragePrefix(String covItemPrefix, Collection<Coverage> coverages) {
		this.covItemPrefix = covItemPrefix;
		this.coverages = coverages;
	}

	/**
	 * 
	 * @return the coverage item prefix
	 */
	public String getCovItemPrefix() {
		return this.covItemPrefix;
	}

	/**
	 * 
	 * @return array of <Code>Coverage</Code> objects in this
	 *         <Code>CoveragePrefix</Code>
	 * @see Coverage
	 */
	public Coverage[] getCoverages() {
		return coverages.toArray(new Coverage[0]);
	}

	/**
	 * 
	 * @param coverage
	 *            <Code>Coverage</Code> object to be added to this
	 *            <Code>CoveragePrefix</Code>
	 * @see Coverage
	 */
	public void addCoverage(Coverage coverage) {
		coverages.add(coverage);
	}

	@Override
	public boolean equals(Object coveragePrefix) {
		if (coveragePrefix != null
				&& coveragePrefix instanceof CoveragePrefix) {
			if (this.getCovItemPrefix().equals(
					((CoveragePrefix) coveragePrefix).getCovItemPrefix())) {
//				if (this.getCoverages().length == ((CoveragePrefix) coveragePrefix)
//						.getCoverages().length) {
//					Coverage[] thatCoverages = ((CoveragePrefix) coveragePrefix)
//							.getCoverages();
//					for (int i = 0; i < thatCoverages.length; i++) {
//						if (!this.coverages.contains(thatCoverages[i])) {
//							return false;
//						}
//					}
					return true;
//				}
			}
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();

		result.append("\"" + this.covItemPrefix + "\"");
//		result.append("{ ");
//
//		int size = this.coverages.size();
//		int count = 0;
//		for (Coverage coverage : this.coverages) {
//			count++;
//			result.append(coverage.toString() + ((count == size) ? "" : ","));
//		}
//		result.append(" }");

		return result.toString();
	}
}
