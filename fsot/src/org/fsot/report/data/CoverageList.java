package org.fsot.report.data;

import java.util.ArrayList;
import java.util.Collection;

/*
 * Class for representing a list of <Code>CoveragePrefix</Code> objects as specified in CodeCover.
 * 
 * @author  Shahriar Tajbakhsh
 * @version	13/12/2012
 */
public class CoverageList {

	private Collection<CoveragePrefix> coveragePrefix;

	/**
	 * Empty class constructor. It will initialize the class with a null list.
	 */
	public CoverageList() {
		this(new ArrayList<CoveragePrefix>());
	}

	/**
	 * Class constructor for initializing the class with a
	 * <Code>Collection</Code> of <Code>CoveragePrefix</Code> objects.
	 * 
	 * @param coveragePrefix
	 *            a <Code>Collection</code> of <Code>CoveragePrefix</Code>
	 *            objects
	 * @see CoveragePrefix
	 * @see Collection
	 */
	public CoverageList(Collection<CoveragePrefix> coveragePrefix) {
		this.coveragePrefix = coveragePrefix;
	}

	/**
	 * 
	 * @return array of <Code>CoveragePrefix</Code> objects in this
	 *         <Code>CoverageList</Code>
	 * @see CoveragePrefix
	 */
	public CoveragePrefix[] getCoveragePrefix() {
		return coveragePrefix.toArray(new CoveragePrefix[0]);
	}

	/**
	 * 
	 * @param coveragePrefix
	 *            <Code>CoveragePrefix</Code> object to be added to this
	 *            <Code>CoverageList</Code>
	 * @see CoveragePrefix
	 */
	public void addCoveragePrefix(CoveragePrefix coveragePrefix) {
		this.coveragePrefix.add(coveragePrefix);
	}

	@Override
	public boolean equals(Object coverageList) {
		if ((coverageList != null) && (coverageList instanceof CoverageList)) {
			if (this.getCoveragePrefix().length == ((CoverageList) coverageList)
					.getCoveragePrefix().length) {
				CoveragePrefix[] thatCoverageList = ((CoverageList) coverageList)
						.getCoveragePrefix();
				for (int i = 0; i < thatCoverageList.length; i++) {
					if (!this.coveragePrefix.contains(thatCoverageList[i])) {
						return false;
					}
				}
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();

		int size = this.coveragePrefix.size();
		int count = 0;
		result.append("{ ");
		for (CoveragePrefix coveragePrefix : this.coveragePrefix) {
			count++;
			result.append(coveragePrefix + ((count == size) ? "" : ","));
		}
		result.append(" }");

		return result.toString();
	}
}
