package org.fsot.javacct.common;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.ListSelectionDialog;

public class Views {
	
	public enum JunitVersion {JUnit3, JUnit4};

	private enum Targets {
		COMPILE, TEST
	};

	public static Object[] getCompileTarget(Shell shell, Object[] listOfTargets) {
		return getTargetFromUser(shell, listOfTargets, Targets.COMPILE);
	}

	public static Object[] getTestTarget(Shell shell, Object[] listOfTargets) {
		return getTargetFromUser(shell, listOfTargets, Targets.TEST);
	}

	public static JunitVersion getJUnitVersion(Shell shell) {


		ElementListSelectionDialog junitSelection = new ElementListSelectionDialog(
				shell, new LabelProvider());

		
		Object[] options = new Object[] { JunitVersion.JUnit3.toString(), JunitVersion.JUnit4.toString() };
		junitSelection.setElements(options);

		junitSelection.open();

		if (junitSelection.getReturnCode() == junitSelection.OK) {

			if (junitSelection.getResult()[0] == JunitVersion.JUnit3) {
				return JunitVersion.JUnit3;
			}

			if (junitSelection.getResult()[0] == JunitVersion.JUnit4) {
				return JunitVersion.JUnit4;
			}
		}

		return null;
	}

	private static Object[] getTargetFromUser(Shell shell,
			Object[] listOfTargets, Targets target) {

		ElementListSelectionDialog targetView = new ElementListSelectionDialog(
				shell, new LabelProvider());

		targetView.setElements(listOfTargets);

		if (target == Targets.COMPILE) {
			targetView.setTitle("Compile Target");
			targetView
					.setMessage("Please select the Ant compile target you wish to use:");
			targetView.open();
		}

		if (target == Targets.TEST) {
			targetView.setTitle("Test Target");
			targetView
					.setMessage("Please select the Ant test target you wish to use:");
			targetView.open();
		}

		if (targetView.getReturnCode() == targetView.OK) {
			return targetView.getResult();
		}

		return null;

	}

	public static Object[] listTests(Shell shell, Object[] testNames) {

		ListSelectionDialog selection = new ListSelectionDialog(shell,
				testNames, ArrayContentProvider.getInstance(),
				new LabelProvider(),
				"Please select the test class(es) you wish to use:");
		selection.open();
		if (selection.getReturnCode() == selection.OK) {
			return selection.getResult();
		}

		return null;

	}

	/*
	 * 
	 * ListSelectionDialog compile = new ListSelectionDialog(shell,
	 * listOfTargets, ArrayContentProvider.getInstance(), new LabelProvider(),
	 * "Please select the Ant compile target"); ggg.open(); if
	 * (ggg.getReturnCode() == ggg.OK) { values = ggg.getResult(); }
	 */

	/*
	 * Object[] values;
	 * 
	 * ListSelectionDialog ggg = new ListSelectionDialog(shell, listOfTargets,
	 * ArrayContentProvider.getInstance(), new LabelProvider(),
	 * "Please select the test class(es) you wish to use"); ggg.open(); if
	 * (ggg.getReturnCode() == ggg.OK) { values = ggg.getResult(); }
	 */

	/*
	 * Object[] values;
	 * 
	 * ListSelectionDialog ggg = new ListSelectionDialog(shell, listOfTargets,
	 * ArrayContentProvider.getInstance(), new LabelProvider(),
	 * "Please select the test class(es) you wish to use"); ggg.open(); if
	 * (ggg.getReturnCode() == ggg.OK) { values = ggg.getResult(); }
	 */

}
