package org.fsot.model;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.types.Path;
import org.fsot.model.ant.CompileTargetListener;
import org.fsot.model.ant.CustomJUnitTask;
import org.fsot.model.ant.TestTargetListener;

/**
 * ProjectModel with Ant build file.
 * 
 * @author SQ
 * 
 */
public class AntProjectModel extends ProjectModel {

	private Project project;
	private String javacTarget;
	private String testTarget;
	private Javac javacTask;

	private TestTargetListener testListener;
	private CompileTargetListener compileListener;

	/**
	 * Constructor. Calls super.
	 * 
	 * @param buildFile
	 *            path to Ant build file
	 */
	public AntProjectModel(File buildFile) {
		super(buildFile);
	}

	/**
	 * Set name of Ant target containing javac, specified by CLI -c option
	 * 
	 * @param target
	 *            name of target
	 * 
	 */
	public void setTargetJavac(String target) {
		javacTarget = target;
	}

	/**
	 * Set name of Ant target containing JUnit or equivalent testing task,
	 * specified by CLI -j option
	 * 
	 * @param target
	 *            name of target
	 */
	public void setTargetJUnit(String target) {
		testTarget = target;
	}

	/**
	 * Initialise project by attaching build listeners. Make sure setTargetJavac
	 * and setTargetJUnit are called before calling this method
	 * 
	 */
	public void initAntProject() {

		project = new Project();

		// Add logger
		DefaultLogger consoleLogger = new DefaultLogger() {
			@Override
			protected String getBuildFailedMessage() {
				return "FSOT - ProjectModel Build Failed";
			}

			@Override
			protected String getBuildSuccessfulMessage() {
				return "FSOT - ProjectModel Build Success";
			}
		};
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(System.out);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
		project.addBuildListener(consoleLogger);

		// Add listeners
		if (testTarget != null) {
			testListener = new TestTargetListener();

			testListener.setTargetJUnit(testTarget);
			project.addBuildListener(testListener);
		}

		compileListener = new CompileTargetListener();
		project.addBuildListener(compileListener);

	}

	/**
	 * Check compile and test targets and execute them.
	 * 
	 */
	public void executeTargets() {
		System.out.println("Starting FSOT - ProjectModel ant build...");
		try {

			project.fireBuildStarted();
			project.init();
			ProjectHelper helper = ProjectHelper.getProjectHelper();
			project.addReference("ant.projectHelper", helper);

			project.addTaskDefinition("junit", org.fsot.model.ant.CustomJUnitTask.class);
			helper.parse(project, buildFile);

			checkTargets();

			Target test = (Target) project.getTargets().get(testTarget);

			if (test.dependsOn(javacTarget) == false) {
				project.executeTarget(javacTarget);
			}

			project.executeTarget(testTarget);

			project.fireBuildFinished(null);
		} catch (BuildException e) {
			// convention to allow listeners to handle exceptions happened
			// within this build

			project.fireBuildFinished(e);
			// re-throw exception up, to the caller
			throw e;
		}

	}

	/**
	 * Check that user has input target names correctly
	 * 
	 * @throws BuildException
	 *             if any of the target name is not found
	 */
	private void checkTargets() throws BuildException {
		if (project.getTargets().get(testTarget) == null) {
			throw new BuildException("Target " + testTarget + " not found");
		}
		if (project.getTargets().get(javacTarget) == null) {
			throw new BuildException("Target " + javacTarget + " not found");
		}

	}

	/**
	 * Retrieve javac task from the compilerListener. It is currently returning
	 * the first javac task, as it is assumed the source files are being
	 * compiled first before test and anything else. This is internally used by
	 * getCompileClasspath() and getSourceRoot()
	 * 
	 * @return first javac task found
	 * @throws BuildException
	 *             if initAntProject is not called before this method.
	 */
	private Javac getJavacTask() throws BuildException {
		if (compileListener == null) {
			throw new BuildException("Compile Listener not added");
		}
		// Just get the first
		javacTask = (Javac) compileListener.getJavacTasks().get(0);
		return javacTask;
	}

	/**
	 * Returns the classpath used by javac task.
	 * 
	 * @return classpath or null if javac is not found.
	 */
	@Override
	public Object getCompileClasspath() {
		Javac task = getJavacTask();
		if (task != null) {
			return task.getClasspath();
		}
		return null;
	}

	/**
	 * returns root directory of the java source folder (note: if javac has
	 * multiple <src> elements, only the first one will be used as the source
	 * root directory for instrumentation
	 * 
	 * @return path of the source root directory or null if javac is not found
	 */
	public Object getSourceRoot() {
		Javac task = getJavacTask();
		if (task != null) {
			// BUG-FIX: will only take the first src dir of javac task
			String firstDir = task.getSrcdir().list()[0];
			return new Path(project, firstDir);
		}
		return null;
	}

	/**
	 * testListener must be added (via initAntProject()) before calling this
	 * method
	 * 
	 * @return Classloader where the test classes are "stored"
	 * @throws BuildException
	 *             if initAntProject is not called before this method.
	 */
	public ClassLoader getJUnitClassloader() {
		if (testListener == null) {
			throw new BuildException("Test Listner not added");
		}
		return testListener.getClassLoader();
	}

	/**
	 * testListener must be added (via initAntProject()) before calling this
	 * method
	 * 
	 * @return classpath used by the testing task
	 * @throws BuildException
	 *             if initAntProject is not called before this method.
	 */
	public Object getTestClasspath() {
		if (testListener == null) {
			throw new BuildException("Test Listner not added");
		}
		return testListener.getClasspath();
	}

}
