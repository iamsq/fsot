package org.fsot.report.anttask;

import org.apache.tools.ant.types.DataType;

public abstract class Command extends DataType {

	public abstract void run();
}
