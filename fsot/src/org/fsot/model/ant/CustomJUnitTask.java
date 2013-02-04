package org.fsot.model.ant;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.junit.BatchTest;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.apache.tools.ant.types.Path;

/**
 * Inherits almost everything from JUnitTask except that it exposes the
 * classloader and it runs no tests, bypassing all tests added.
 * 
 * This custom task is mainly used by the TestTargetListner class for it
 * to mainly access the classloader and not run any specific test.
 *
 * 
 * @author SQ
 * 
 */
public class CustomJUnitTask extends JUnitTask {

	/**
	 * Constructor that just calls super()
	 * 
	 * @throws Exception
	 */
	public CustomJUnitTask() throws Exception {
		super();
	}

	/**
	 * Overridden method to expose class loader. The class loader is set to be
	 * isolated
	 * 
	 * @return class loader
	 * @author SQ
	 */
	public AntClassLoader getClassLoader() {
		Path userClasspath;
		AntClassLoader classLoader = null;
		userClasspath = getCommandline().getClasspath();
		if (userClasspath != null) {
			Path classpath = (Path) userClasspath.clone();
			classLoader = getProject().createClassLoader(classpath);

			classLoader.setParentFirst(false);
			classLoader.addJavaLibraries();

			// make sure the test will be accepted as a TestCase
			classLoader.addSystemPackageRoot("junit");
			// make sure the test annotation are accepted
			classLoader.addSystemPackageRoot("org.junit");
			// will cause trouble in JDK 1.1 if omitted
			classLoader.addSystemPackageRoot("org.apache.tools.ant");
			// make sure do not include classes from current project
			classLoader.setIsolated(true);

		}
		return classLoader;
	}
	
	/**
	 * Expose method to get classpath used the JUnit Task
	 * @return
	 */
	public Path getClasspath(){
		return getCommandline().getClasspath();
	}

	/**
	 * Overridden method to make it prevent it from adding any test
	 * No-op
	 */
	public void addTest(JUnitTest test) {
		//Does nothing.
		log("Bypassing test. Test is not added. - [FSOT - CustomJunitTask]");
	}

	/**
	 * Overridden method to make it prevent it from adding any test
	 * 
	 * @return empty BatchTest instance
	 */
	public BatchTest createBatchTest() {		
		BatchTest test = new BatchTest(getProject());
		log("Bypassing test. Batchtest is not added. [FSOT - CustomJunitTask]");
		return test;
	}
	
	
	 
}
