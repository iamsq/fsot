package org.fsot;

import java.io.File;

import org.fsot.JUnitHelper.JunitVersion;
import org.fsot.model.AntProjectModel;
import org.junit.Before;
import org.junit.Test;

/**This class tests the functionality of the class org.fsot.JUnitHelper*/
public class JUnitHelperTest {
	private AntProjectModel projectModel;
	private JunitVersion junitVersion = JUnitHelper.JunitVersion.JUnit3;
	private File buildfile = new File("../JavaCCT/mocks/org/fsot/dummy/build.xml");
	
	
	@Before
	public void setUp() {
		projectModel = new AntProjectModel(buildfile);
		projectModel.setTargetJavac("compile");
		projectModel.setTargetJUnit("test");
		projectModel.setJunitVersion(junitVersion);
		projectModel.initAntProject();
		projectModel.executeTargets();
	}
	
		
	@Test
	public void testPrintTests() throws Exception {
		JUnitHelper.printTests(projectModel.getJUnitClassloader(), junitVersion, false);
		
	}
	
	
	@Test
	public void testGetTestClassNames() {
		
	}
	

	
	
}
