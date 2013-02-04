package org.fsot;

import static org.junit.Assert.*;
import java.io.File;
import java.lang.reflect.Field;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.fsot.CodeCoverRunner;
import org.fsot.model.AntProjectModel;
import org.junit.Before;
import org.junit.Test;


/**This class tests the functionality of the class org.fsot.CodeCoverRunner*/
public class CodeCoverRunnerTest extends BaseTest{
	private File buildfile = new File(BUILDFILE_DUMMY);
	private String antCompileTarget = "compile";
	private String antTestTarget = "test";
	private CodeCoverRunner codeCoverRunner;
	private JUnitHelper.JunitVersion junitVersion = JUnitHelper.JunitVersion.JUnit3;

	
	@Before
	public void setUp() throws Exception {
		AntProjectModel sut = new AntProjectModel(buildfile);
		sut.setTargetJavac(antCompileTarget);
		sut.setTargetJUnit(antTestTarget);
		sut.setJunitVersion(junitVersion);
		sut.initAntProject();
		sut.executeTargets();
		codeCoverRunner = new CodeCoverRunner(sut);
		codeCoverRunner.setupCodecoverDir();
		codeCoverRunner.initProject();
	}
	
	
	@Test
	public void testRunnerIntialization() {
		assertNotNull(codeCoverRunner);
	}
	
	
    @Test
	public void testConfigureRunAllNoX() throws SecurityException, NoSuchFieldException, 
	IllegalArgumentException, IllegalAccessException {
    	
		String[] allTests = {"test1", "test2", "test3"};
		codeCoverRunner.configureRunAll(allTests, null);
			Field field = CodeCoverRunner.class.getDeclaredField("testClassesToUse");
			field.setAccessible(true);
			String[] testClassesToUse = (String[]) field.get(codeCoverRunner);
			assertNotNull(testClassesToUse);
			assertTrue(allTests.equals(testClassesToUse));
	}

    
    @Test
	public void testConfigureRunAllWithX() throws SecurityException, NoSuchFieldException, 
	IllegalArgumentException, IllegalAccessException {
		
    	String[] allTests = {"test1", "test2", "test3"};
		String[] exclusionList = {"test2"};
		codeCoverRunner.configureRunAll(allTests, exclusionList);
		Field field = CodeCoverRunner.class.getDeclaredField("testClassesToUse");
		field.setAccessible(true);
		String[] testClassesToUse = (String[]) field.get(codeCoverRunner);
		assertNotNull(testClassesToUse);
		assertEquals(testClassesToUse.length, 2);
		assertTrue(testClassesToUse[0].equals("test1"));
		assertTrue(testClassesToUse[1].equals("test3"));
	}
    

    @Test(expected=Exception.class)
    public void testConfigureRunSomeWithExistingTests() throws Exception {
    	String[] allTests = {"test1", "test2", "test3"};
    	String[] selectedTests = {"test4"};
 		codeCoverRunner.configureRunSome(selectedTests, allTests);   
   }
}


