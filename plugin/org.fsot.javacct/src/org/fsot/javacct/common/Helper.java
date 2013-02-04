package org.fsot.javacct.common;


import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.ant.internal.ui.model.AntModelProject;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.ProjectHelper;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;



public class Helper {

	//Use this method to extract the file object from the currently selected file
	public static File getBuildFile(ISelection currentSelection) throws Exception {

		IFile file = null;
				
		if (currentSelection instanceof IStructuredSelection) {
			Object buildFilePath;
			
			IStructuredSelection ssel = (IStructuredSelection) currentSelection;
			buildFilePath = ssel.getFirstElement();
			file = (IFile) Platform.getAdapterManager().getAdapter(
					buildFilePath, IFile.class);
			if (file == null) {
				throw new Exception(
						"Unable to load file, please enure the correct Ant build has been selected");
			}
		}
		
		return file.getRawLocation().makeAbsolute().toFile();

	}

	// Removes empty items from the array
	public static String[] cleanArray(Object[] listOfTargets) {

		Set<String> targetsToUse = new HashSet<String>();

		for (Object target : listOfTargets) {

			if (target.toString().trim() != "") {
				targetsToUse.add(target.toString().trim());
			}

		}

		return targetsToUse.toArray(new String[0]);
	}
	
	
	public static Object[] extractListOfTargets(File buildFile)
	{
		
		Project project;
		project = new Project();
		project.init();

		ProjectHelper.configureProject(project, buildFile);
		
		
		return project.getTargets().values().toArray();
	}

}
