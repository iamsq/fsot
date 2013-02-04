package org.fsot.cli;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.fsot.CodeCoverRunner;
import org.fsot.JUnitHelper;
import org.fsot.model.AntProjectModel;
import org.springframework.expression.spel.SpelMessage;

public class CLI {

	private Options options;
	private AntProjectModel antProjectModel;
	private CodeCoverRunner runner;

	private File buildfile = null;
	private String[] testList = null;
	private String[] exclusionList = null;
	private String antTestTarget = null;
	private String antCompileTarget = null;
	private boolean doListTests = false;
	private boolean runAllTests = false;
	private boolean generateHtml = false;
	private JUnitHelper.JunitVersion junitVersion = null;

	public static String USAGE = "fsot -f <path> -c <name> -j <name> -v3|-v4 [options]";
	public static final String PARSING_FAILED_PREFIX = "Parsing failed.  Reason: ";
	public static String MISSING_BUILD_FILE = "-f is missing or path specified does not exist";
	public static final String MISSING_JUNIT_VERSION = "JUnit version not specified - please use v3 / v4 flags";
	public static final String MISSING_TESTS_OPT = "-t / -a is missing";

	/**
	 * Private constructor
	 */
	private CLI() {

	}

	private boolean parseArguments(String[] args) {

		options = CliOptions.getBuildOptions();

		CommandLineParser parser = new GnuParser();

		try {

			CommandLine line = parser.parse(options, args);

			extractAttributes(line);

			checkAttributes();

		} catch (ParseException e) {
			System.err.println(PARSING_FAILED_PREFIX + e.getMessage());

			HelpFormatter formatter = new HelpFormatter();
			formatter.setOptionComparator(CliOptions.getComparator());
			formatter.printHelp(USAGE, options, false);
			return false;
		}
		return true;
	}

	/**
	 * This method sets up ProjectModel using the given -f buildfile It then executes ant
	 * (target -c and -j) to collect ProjectModel-specific parameters, such as
	 * classpaths, source directories, for the use of subsequent listing of
	 * tests and/or instrumentation for coverage report.
	 */
	private void setupSUT() {
		antProjectModel = new AntProjectModel(buildfile);
		antProjectModel.setTargetJavac(antCompileTarget);
		antProjectModel.setTargetJUnit(antTestTarget);
		antProjectModel.setJunitVersion(junitVersion);
		antProjectModel.initAntProject();
		antProjectModel.executeTargets();
	}

	private void run() throws Exception {

		setupSUT();

		if (doListTests) {
			printTests();
			return;
		}

		runner = new CodeCoverRunner(antProjectModel);
		runner.setupCodecoverDir();
		runner.initProject();

		String[] allTests = getTests();

		if (runAllTests) {
			runner.configureRunAll(allTests, exclusionList);
		} else {

			runner.configureRunSome(testList, allTests);

		}

		if (generateHtml) {
			runner.execute(CodeCoverRunner.ReportTypes.CSV,
					CodeCoverRunner.ReportTypes.HTML);
		} else {

			// CSV generation by default
			runner.execute(CodeCoverRunner.ReportTypes.CSV);
		}

	}

	private void extractAttributes(CommandLine line) {

		for (Option option : line.getOptions()) {

			switch (option.getId()) {

			case 'f':
				buildfile = new File(option.getValue());
				break;

			case 'l':
				doListTests = true;
				break;

			case 'h':
				generateHtml = true;
				break;

			case 'c':
				antCompileTarget = option.getValue();
				break;

			case 'j':
				antTestTarget = option.getValue();
				break;

			case 't':
				testList = option.getValue().split(",");
				break;
			case 'a':
				runAllTests = true;
				break;

			case 'x':
				exclusionList = option.getValue().split(",");
				break;

			default:
				break;
			}

			if (option.getLongOpt() == "JUnit3") {
				junitVersion = JUnitHelper.JunitVersion.JUnit3;
			}

			if (option.getLongOpt() == "JUnit4") {
				junitVersion = JUnitHelper.JunitVersion.JUnit4;
			}
		}
	}

	private void checkAttributes() throws ParseException {
		if (buildfile == null || buildfile.exists() == false) {
			throw new ParseException(MISSING_BUILD_FILE);
		}

		if (junitVersion == null) {
			throw new ParseException(MISSING_JUNIT_VERSION);
		}

		// would have caught by CommandLineParser.pase
		if (antCompileTarget == null) {
			throw new ParseException("-c is missing");
		}

		// would have caught by CommandLineParser.pase
		if (antTestTarget == null) {
			throw new ParseException("-j is missing");
		}

		if (doListTests == false) {
			if (testList == null && runAllTests == false) {
				throw new ParseException(MISSING_TESTS_OPT);
			}

		}
	}

	private void printTests() {
		try {
			JUnitHelper.printTests(antProjectModel.getJUnitClassloader(), junitVersion,
					false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String[] getTests() {
		return JUnitHelper.getTestClassNames(antProjectModel.getJUnitClassloader(),
				junitVersion);
	}

	/**
	 * Main entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		CLI cli;
		cli = new CLI();
		try {
			if (cli.parseArguments(args)) {
				cli.run();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

}
