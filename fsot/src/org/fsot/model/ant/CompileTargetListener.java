package org.fsot.model.ant;

import java.util.ArrayList;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.RuntimeConfigurable;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.UnknownElement;
import org.apache.tools.ant.property.LocalProperties;
import org.apache.tools.ant.taskdefs.Classloader;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.taskdefs.MacroInstance;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask;
import org.apache.tools.ant.types.Path;

/**
 * This class listens for taskStarted and taskFinished event of Javac tasks.
 * 
 * @author SQ
 * 
 */
public class CompileTargetListener implements BuildListener {

	private ArrayList<Task> tasks = new ArrayList<Task>();

	public ArrayList<Task> getJavacTasks() {
		return tasks;
	}

	/**
	 * Listens for javac tasks and force it to run in forked mode if it isn't
	 * already set.
	 */
	@Override
	public void taskStarted(BuildEvent be) {
		if (be.getTask() instanceof UnknownElement) {
			UnknownElement ue = (UnknownElement) be.getTask();
			ue.maybeConfigure();

			if (ue.getTask() instanceof Javac) {
				Javac task = (Javac) ue.getTask();
				if (task.isForkedJavac() == false) {
					task.log("FSOT - overriding javac to run in forked mode.");
					task.setFork(true);
				}
			}
		}

	}

	/**
	 * Listens for javac task finished, and adds the javac task to arraylist.
	 */
	@Override
	public void taskFinished(BuildEvent be) {

		if (be.getTask() instanceof UnknownElement) {
			UnknownElement ue = (UnknownElement) be.getTask();
			ue.maybeConfigure();

			if (ue.getTask() instanceof Javac) {
				Javac task = (Javac) ue.getTask();
				tasks.add(task);
			}
		}
	}

	@Override
	public void buildStarted(BuildEvent event) {
		// Does nothing

	}

	@Override
	public void buildFinished(BuildEvent event) {
		// Does nothing

	}

	@Override
	public void targetStarted(BuildEvent event) {
		// Does nothing

	}

	@Override
	public void targetFinished(BuildEvent event) {
		// Does nothing

	}

	@Override
	public void messageLogged(BuildEvent event) {
		// Does nothing

	}

}
