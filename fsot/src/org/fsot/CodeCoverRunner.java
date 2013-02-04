package org.fsot;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.fsot.JUnitHelper.JunitVersion;
import org.fsot.model.ProjectModel;

/**
 * This class supplies the necessary parameters to the ant script,
 * ant-codecover.xml, then runs it. The script calls CodeCover, which
 * instruments and compiles the instrumented code. It then runs the user-given
 * test case(s) on the instrumented code and creates a report.
 * 
 * 
 * @author SQ
 * 
 */
public class CodeCoverRunner {

	public final static String ANT_SCRIPT = "ant-codecover.xml";
	public final static String CODECOVER_DIR = "codecover";
	public static final String IDENTIFY_TEST_FAIL = "Unable to identify the following tests: ";

	public enum ReportTypes {
		CSV, HTML
	};

	private final Object sourceRootDir;
	private final Object compileClasspath;
	private final Object testClasspath;
	private final File baseDir;
	private Project project;
	private File antScriptFile;
	private String codecoverDir;

	private JUnitHelper.JunitVersion junitVersion;

	private String[] testClassesToUse;

	/**
	 * Constructor.
	 * 
	 * @param
	 */
	public CodeCoverRunner(ProjectModel projectModel) {

		this.baseDir = projectModel.getBaseDir();
		this.sourceRootDir = projectModel.getSourceRoot();
		this.compileClasspath = projectModel.getCompileClasspath();
		this.testClasspath = projectModel.getTestClasspath();
		this.junitVersion = projectModel.getJunitVersion();
		
	}

	/**
	 * This method removes the tests listed in the exclusion list from the list
	 * of all identified tests
	 */
	public void configureRunAll(String[] allTests, String[] exclusionList) {
		if (exclusionList == null) {
			testClassesToUse = allTests;
			return;
		}

		Set<String> all = new HashSet<String>();
		Set<String> exclude = new HashSet<String>();

		all.addAll(Arrays.asList(allTests));
		exclude.addAll(Arrays.asList(exclusionList));
		all.removeAll(exclude);

		testClassesToUse = all.toArray(new String[0]);
	}

	/**
	 * This method checks that all the selected tests appear in the project
	 */
	public void configureRunSome(String[] selectedTests, String[] allTests)
			throws Exception {
		Set<String> all = new HashSet<String>();
		Set<String> incorrectlySelectedTests = new HashSet<String>();

		all.addAll(Arrays.asList(allTests));
		incorrectlySelectedTests.addAll(Arrays.asList(selectedTests));

		// Removing all identified tests from the list of selected tests
		incorrectlySelectedTests.removeAll(all);

		// Checking to see if any unidentified tests exist
		if (incorrectlySelectedTests.size() > 0) {
			StringBuilder errorMessage = new StringBuilder();

			errorMessage.append(IDENTIFY_TEST_FAIL);

			for (String testName : incorrectlySelectedTests) {
				errorMessage.append("[");
				errorMessage.append(testName);
				errorMessage.append("]");
			}
			
			throw new Exception(errorMessage.toString());
		} else {
			testClassesToUse = selectedTests;
		}
	}

	public void initProject() {
		antScriptFile = new File(ANT_SCRIPT);
		project = new Project();
		project.setBaseDir(baseDir);
		

		// passing parameters to our ant-codecover.xml build script.
		project.setProperty("codecoverDir", codecoverDir);
		project.addReference("fsot-src", sourceRootDir);
		project.addReference("classpathCompile", compileClasspath);
		project.addReference("classpathTest", testClasspath);

		DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(System.out);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);

		project.addBuildListener(consoleLogger);
	}

	public void setupCodecoverDir() throws Exception {
		
		//System.out.println(System.getenv("FSOTHome"));
		
		codecoverDir = System.getenv("CODECOVER_DIR");
		
		System.out.println("Codecover directory is: "+codecoverDir);
		if (new File(codecoverDir).exists() == false) {			
			throw new Exception("Codecover directory " + codecoverDir
					+ " not found.");
		}
	}

	public void execute(ReportTypes... reportTypes) {
		try {
			System.out.println("Starting build script: ant-codecover.xml");
			project.fireBuildStarted();
			project.init();
			ProjectHelper helper = ProjectHelper.getProjectHelper();
			project.addReference("ant.projectHelper", helper);
			helper.parse(project, antScriptFile);

			// Updating config reference to point to correct Junit CodeCover
			// runner
			setJunitCodeCoverRunner();
			// generate test coverage and report
			executeTests();

			// merge sessions
			project.executeTarget("merge-sessions");

			generateReports(reportTypes);

			project.fireBuildFinished(null);
		} catch (BuildException e) {
			project.fireBuildFinished(e);
		}
	}

	/**
	 * Executes one or more tests by calling respective ant targets.
	 * Instrumentation and Compilation are called only called once before the
	 * running of first test.
	 */
	private void executeTests() {
		System.out.println("Running Test 1: " + testClassesToUse[0]);
		project.setProperty("mainClassName", testClassesToUse[0]);
		project.executeTarget(project.getDefaultTarget());

		if (testClassesToUse.length > 1) {
			for (int i = 1; i < testClassesToUse.length; i++) {
				System.out.println("Running Test " + (i + 1) + ": "
						+ testClassesToUse[i]);
				project.setProperty("mainClassName", testClassesToUse[i]);
				project.executeTarget("create-log");
			}
		}
	}

	/**
	 * Sets the correct CodeCover Runner to use in the ant script
	 */
	private void setJunitCodeCoverRunner() {

		if (junitVersion == JunitVersion.JUnit3) {
			project.setProperty("junitRunnerToUse",
					project.getProperty("junit3Runner"));
		}

		if (junitVersion == JunitVersion.JUnit4) {
			project.setProperty("junitRunnerToUse",
					project.getProperty("junit4Runner"));
		}
	}

	/**
	 * Executes report according to the types requested.
	 * 
	 * @param reportTypes
	 */
	private void generateReports(ReportTypes... reportTypes) {

		// keep extending as more report types become available

		for (ReportTypes report : reportTypes) {

			switch (report) {

			case CSV:
				project.executeTarget("generate-csv-report");
				break;

			case HTML:
				project.executeTarget("generate-html-report");
				break;

			default:
				break;
			}
		}

	}

}
