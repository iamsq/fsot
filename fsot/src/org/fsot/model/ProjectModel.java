package org.fsot.model;

import java.io.File;

import org.fsot.JUnitHelper;
import org.fsot.JUnitHelper.JunitVersion;

/**
 * Abstract representation of an ProjectModel (System-Under-Test).
 * Implementations are build-tool specific.
 * @author SQ
 *
 */
public abstract class ProjectModel {

	protected final File buildFile;	
	private JUnitHelper.JunitVersion junitVersion;
	
	/**
	 * Constructor 
	 * @param buildfile path to build file
	 */
	public ProjectModel(File buildfile){
		this.buildFile = buildfile;
	}
	
	/**
	 * Get the base directory containing the build file.
	 * @return base directory
	 */
	public File getBaseDir(){
		return buildFile.getAbsoluteFile().getParentFile();
	}
	
	public abstract Object getCompileClasspath();
	
	/** Get the root directory of the source defined by this ProjectModel  
	 * 
	 * @return source root directory 
	 */
	public abstract Object getSourceRoot();
	
	/**
	 * Get the test classpath used by this ProjectModel
	 * @return casspath
	 */
	public abstract Object getTestClasspath();

	/**
	 * Get version of JUnit used by this ProjectModel
	 * @return JUnit version
	 */
	public JUnitHelper.JunitVersion getJunitVersion()
	{
		return junitVersion;
	}
	/**
	 * Set version of JUnit used by this ProjectModel
	 * @param junitVersion JUnit Version
	 */
	public void setJunitVersion(JUnitHelper.JunitVersion junitVersion)
	{
		this.junitVersion = junitVersion;
	}
	
	
}
