package org.fsot.report.anttask;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class FsotTask extends Task {
	private List<Command> commands = new ArrayList<Command>();
	
	public void addConfiguredCSVReport(CSVReportCommand command) {
        this.commands.add(command);
    }
	
	public void addConfiguredRunJUnit(RunJUnitCommand command) {
		//TODO: allow multiple Junit element?
        this.commands.add(command);
    }
	
	public List<Command> getCommands() {
		return commands;
	}
	
	public void execute() throws BuildException {
		for (Command command : this.commands){
			command.run();
		}
	}
}
