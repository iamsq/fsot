package org.fsot.cli;

import java.util.Collection;
import static org.junit.Assert.*;
import org.apache.commons.cli.*;
import org.junit.Before;
import org.junit.Test;

/**This class tests the functionality of the class org.fsot.cli.CliOptions*/
public class CliOptionsTest {
	private Options options;
	
	@Before
	public void setUp() {
		options = CliOptions.getBuildOptions();
	}
	
   	@Test
	public void testOptionsNotNull() {
		assertNotNull(options);
    }
  

    /**Assert that setUp has initialized the options object to have the
     * correct number of flags and check their names*/
   	
   	@SuppressWarnings("unchecked")
	@Test
	public void testAllOptions() {
		Collection<Options> ops = options.getOptions();
		assertEquals(ops.size(), 10);
		
   		assertTrue(options.hasOption("f"));
		assertTrue(options.hasOption("t"));
		assertTrue(options.hasOption("a"));
		assertTrue(options.hasOption("x"));
		assertTrue(options.hasOption("l"));
		assertTrue(options.hasOption("j"));
		assertTrue(options.hasOption("c"));
		assertTrue(options.hasOption("h"));
		assertTrue(options.hasOption("v3"));
		assertTrue(options.hasOption("v4"));
	}


    /**Assert that option has the correct number of required options, 3, namely
     * the flags which specify the build file, the compile target and the ant test target
     */
   	
	@Test
	public void testRequiredOptions() {
		assertEquals(options.getRequiredOptions().size(), 3);
		assertTrue(options.getOption("c").isRequired());
		assertTrue(options.getOption("j").isRequired());
		assertTrue(options.getOption("f").isRequired());
	}

	
	/**Assert that there are three option groups, for the version, testing and
	 * mutex options*/
	
	@SuppressWarnings("rawtypes")
	@Test 
	public void testOptionGroups() {
		Collection ogTest = options.getOptionGroup(options.getOption("a")).getNames();
		assertEquals(ogTest.size(), 2);
		assertTrue(ogTest.contains("t"));
		assertTrue(ogTest.contains("a"));
				
		Collection ogEx = options.getOptionGroup(options.getOption("x")).getNames();
		assertEquals(ogEx.size(), 2);
		assertTrue(ogEx.contains("x"));
		assertTrue(ogEx.contains("t"));
		
		Collection ogVersion = options.getOptionGroup(options.getOption("v3")).getNames();
		assertEquals(ogEx.size(), 2);
		assertTrue(ogVersion.contains("v3"));
		assertTrue(ogVersion.contains("v4"));
					
		assertNull(options.getOptionGroup(options.getOption("f")));
		assertNull(options.getOptionGroup(options.getOption("l")));
		assertNull(options.getOptionGroup(options.getOption("j")));
		assertNull(options.getOptionGroup(options.getOption("c")));
		assertNull(options.getOptionGroup(options.getOption("h")));
	}
}
	
	
	




