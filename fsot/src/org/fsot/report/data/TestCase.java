package org.fsot.report.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestCase {
	private String name;
	private String comment;
	private List<CoverageList> coverageLists;

	public TestCase(String name) {
		this(name, "");
	}
	
	public TestCase(String name, String comment) {
		this.name = name;
		this.comment = comment;
		this.coverageLists = new ArrayList<CoverageList>();
	}
	
	public void addCoverageList(CoverageList coverageList) {
		coverageLists.add(coverageList);
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getName() {
		return name;
	}
	
	public String getComment() {
		return comment;
	}
	
	public List<CoverageList> getCoverageLists() {
		return Collections.unmodifiableList(coverageLists);
	}
}
