package org.fsot.model;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.Field;

import org.apache.tools.ant.BuildException;
import org.fsot.BaseTest;
import org.fsot.JUnitHelper.JunitVersion;
import org.fsot.cli.CLITest;
import org.fsot.model.AntProjectModel;
import org.junit.Before;
import org.junit.Test;


/**This class tests the functionality of the class org.fsot.AntSUT*/
public class AntProjectModelTest extends BaseTest {
	private AntProjectModel antProjectModel;
	private File buildfile = new File(BUILDFILE_J4);

	@Before
	public void setUp() {
		antProjectModel = new AntProjectModel(buildfile);
	}
	
	
	@Test
	public void testSetTargetJavac() throws SecurityException, NoSuchFieldException, 
	IllegalArgumentException, IllegalAccessException {
	
		antProjectModel.setTargetJavac("compile");
		Field field = AntProjectModel.class.getDeclaredField("javacTarget");
		field.setAccessible(true);
		assertEquals(field.get(antProjectModel), "compile");
	}


	@Test
	public void testSetTargetJunit() throws SecurityException, NoSuchFieldException, 
	IllegalArgumentException, IllegalAccessException {
	
		antProjectModel.setTargetJUnit("test");
		Field field = AntProjectModel.class.getDeclaredField("testTarget");
		field.setAccessible(true);
		assertEquals(field.get(antProjectModel), "test");
		
	}

	
    @Test
    public void testGetJUnitClassloaderNull() {
    	//happens when given a valid test target but without
    	//detectable java or junit task
    	
    	antProjectModel.setTargetJavac("emptyCompile");
    	antProjectModel.setTargetJUnit("emptyTest");
    	antProjectModel.setJunitVersion(JunitVersion.JUnit4);
    	antProjectModel.initAntProject();
    	
    	assertNull(antProjectModel.getJUnitClassloader());    	
    }
    
    @Test 
    public void testGetTestClasspathNull(){
    	//happens when given a valid test target but without
    	//detectable java or junit task
    	
    	antProjectModel.setTargetJavac("emptyCompile");
    	antProjectModel.setTargetJUnit("emptyTest");
    	antProjectModel.setJunitVersion(JunitVersion.JUnit4);
    	antProjectModel.initAntProject();
    	
    	assertNull(antProjectModel.getTestClasspath());
    }
    
    
    @Test (expected = BuildException.class)
    public void testExecuteTargetsInvalidCompile(){
    	antProjectModel.setTargetJUnit("test");
    	antProjectModel.setTargetJavac("InvalidCompile");  
    	antProjectModel.initAntProject();
    	antProjectModel.executeTargets();
    }
    
    @Test (expected = BuildException.class)
    public void testExecuteTargetsInvalidTest(){    	
    	antProjectModel.setTargetJUnit("InvalidTest");
    	antProjectModel.setTargetJavac("compile");
    	antProjectModel.initAntProject();
    	antProjectModel.executeTargets();
    }
    
    @Test 
    public void testExecuteTargetsOK(){    	
    	antProjectModel.setTargetJavac("compile");
    	antProjectModel.setTargetJUnit("test");
    	antProjectModel.setJunitVersion(JunitVersion.JUnit4);
    	antProjectModel.initAntProject();
    	antProjectModel.executeTargets();
    	assertNotNull(antProjectModel.getJUnitClassloader()); //to check for correctness  
    	
    	assertNotNull(antProjectModel.getTestClasspath()); //to check for correctness
    	
    	assertNotNull(antProjectModel.getSourceRoot()); //to check for correctness
    	
    }
}
