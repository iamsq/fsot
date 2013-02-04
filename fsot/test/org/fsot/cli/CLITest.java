package org.fsot.cli;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.fsot.BaseTest;
import org.fsot.CodeCoverRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CLITest extends BaseTest {
	
	private static final String TEST_1 = "myset.WhenASetIsEmpty";
	private static final String TEST_2 = "WhenAddThrowsAnException";

	private static final String MISSING_OPTS_PREFIX = "Missing required options: ";

	private static final String EOL = System.getProperty("line.separator");
	private PrintStream console, consoleErr;
	private ByteArrayOutputStream consoleTextOut, consoleTextErr;

	@Before
	public void setUp() {
		consoleTextOut = new ByteArrayOutputStream();
		consoleTextErr = new ByteArrayOutputStream();
		console = System.out;
		consoleErr = System.err;
		System.setOut(new PrintStream(consoleTextOut));
		System.setErr(new PrintStream(consoleTextErr));

	}

	@After
	public void tearDown() {
		System.setOut(console);
		System.setErr(consoleErr);

	}

	private void assertParsingError(String expectedMessage) {
		assertError(CLI.PARSING_FAILED_PREFIX + expectedMessage);
	}

	private void assertError(String expectedMessage) {
		assertEquals(expectedMessage + EOL, consoleTextErr.toString());
	}

	@Test
	public void testWhenNoArgs() {
		String args[] = {};
		CLI.main(args);
		assertParsingError(MISSING_OPTS_PREFIX + "c, j, f");
	}

	@Test
	public void testWhenBuildFileDoesNotExist() {
		String args[] = "-f /non/existing/file -c compile -j test".split(" ");
		CLI.main(args);
		assertParsingError(CLI.MISSING_BUILD_FILE);
	}

	@Test
	public void testWhenJUnitVersionDoesNotExist() {
		String args[] = ("-f " + BUILDFILE_J4 + " -c compile -j test -l")
				.split(" ");
		CLI.main(args);
		assertParsingError(CLI.MISSING_JUNIT_VERSION);
	}

	@Test
	public void testWhenNotListingNorExecutingTest() {
		String args[] = ("-f " + BUILDFILE_J4 + " -c compile -j test -v3")
				.split(" ");
		CLI.main(args);
		assertParsingError(CLI.MISSING_TESTS_OPT);
	}

	@Test
	public void testWhenGivenIncorrectTestName() {
		String args[] = ("-f " + BUILDFILE_J4 + " -c compile -j test -t WRONG.TEST.NAME -v4")
				.split(" ");
		CLI.main(args);
		assertError(CodeCoverRunner.IDENTIFY_TEST_FAIL + "[WRONG.TEST.NAME]");
	}

	@Test
	public void testRunListTestsOK() {
		String args[] = ("-f " + BUILDFILE_J4 + " -c compile -j test -v4 -l")
				.split(" ");
		CLI.main(args);
	}

	@Test
	public void testRunAllTestsOk() {
		String args[] = ("-f " + BUILDFILE_J4 + " -c compile -j test -a -x "+TEST_1+","+TEST_2+" -v4 -html")
				.split(" ");
		CLI.main(args);
	}

	@Test
	public void testRunSomeTestsOk() {
		String args[] = ("-f " + BUILDFILE_J4 + " -c compile -j test -t "+TEST_1+","+TEST_2+" -v4 -html")
				.split(" ");
		CLI.main(args);
	}

}
