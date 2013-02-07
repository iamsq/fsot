package org.fsot.model.ant;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.UnknownElement;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Path;

/**
 * BuildListener for events, triggered by Test-related tasks. It is disabled by
 * default, until test target is first called (after its dependency targets has
 * been executed)
 * 
 * @author SQ
 * 
 */
public class TestTargetListener implements BuildListener {

	private ClassLoader classloader = null;
	private Path classpath = null;
	private boolean isEnabled = false;

	/**
	 * Enable or disable this listener
	 * @param isEnabled 
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * @return the classpath for test or null if none's found (due to absence of
	 *         junit or java task within target?)
	 */
	public Path getClasspath() {
		return classpath;
	}

	/**
	 * @return the classloader or null if none's found (due to absence of junit
	 *         or java task within target?)
	 */
	public ClassLoader getClassLoader() {
		
		return classloader;
	}
	

	/**
	 * When enabled, it listens for taskStarted event and process (Custom)JUnit
	 * task or Java (assuming which calls JUnit) task accordingly.
	 */
	public void taskStarted(BuildEvent be) {
		if (isEnabled == false) {
			return;
		}
		if (be.getTask() instanceof UnknownElement) {
			UnknownElement ue = (UnknownElement) be.getTask();
			ue.maybeConfigure();
			Task task = ue.getTask();
			if (task instanceof CustomJUnitTask) {
				configureClassloader((CustomJUnitTask) ue.getTask());
			} else if (task instanceof Java) {
				configureClassloader((Java) ue.getTask());
			}
		}
	}

	private void configureClassloader(CustomJUnitTask task) {

		this.classpath = task.getClasspath();
		this.classloader = task.getClassLoader();
	}

	private void configureClassloader(Java task) {
		// Nice public function to clear java args, so it is as though no test
		// has been specified
		task.log("Java arguments cleared to prevent test from running - FSOT");
		task.getCommandLine().clearJavaArgs();
		
		this.classpath = (Path) task.getCommandLine().getClasspath().clone();		
		this.classloader = createClassLoader(task.getProject(),this.classpath);
	}

	private AntClassLoader createClassLoader(Project project, Path classpath){
		AntClassLoader classLoader ; 
		classLoader = project.createClassLoader(classpath);

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
		return classLoader;
	}
	@Override
	public void taskFinished(BuildEvent be) {
		// does nothing
	}

	
	@Override
	public void targetStarted(BuildEvent be) {				
	}

	@Override
	public void buildStarted(BuildEvent event) {
		// does nothing
	}

	@Override
	public void buildFinished(BuildEvent event) {
		// does nothing
	}

	@Override
	public void targetFinished(BuildEvent event) {
		// does nothing
	}

	@Override
	public void messageLogged(BuildEvent event) {
		// does nothing
	}

}
