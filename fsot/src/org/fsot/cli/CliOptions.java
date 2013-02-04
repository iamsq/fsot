package org.fsot.cli;

import java.util.Comparator;

import org.apache.commons.cli.*;

@SuppressWarnings("static-access")
public final class CliOptions {

	private static final Option buildFile = OptionBuilder.withArgName("path")
			.withLongOpt("build-file").hasArg().isRequired()
			.withDescription("use given buildfile").create('f');

	private static final Option test = OptionBuilder
			.withArgName("name")
			.hasArg()
			.withDescription(
					"specify an individual test class or a list of test classes seperated by commas")
			.create('t');

	private static final Option allTests = OptionBuilder
			.withArgName("allTests")
			.withDescription(
					"execute all identified tests excluding those specified in the -x option")
			.create('a');

	private static final Option exclude = OptionBuilder
			.withArgName("exclude")
			.withLongOpt("exclusion-list")
			.hasArg()
			.withDescription(
					"exclusion list - an individual test class or a list of test classes seperated by commas")
			.create('x');

	private static final Option listTest = OptionBuilder
			.withLongOpt("list-tests").withDescription("list all junit tests")
			.create('l');

	private static final Option antTestTarget = OptionBuilder
			.withArgName("test-target-name").withLongOpt("ant-test").hasArg()
			.withDescription("specify ant test target name").isRequired()
			.create('j');

	private static final Option antCompileTarget = OptionBuilder
			.withArgName("compile-target-name").withLongOpt("ant-compile")
			.hasArg().withDescription("specify ant compile target name")
			.isRequired().create('c');

	private static final Option junitVersion3 = OptionBuilder
			.withDescription("JUnit 3 flag - the selected project is on JUnit3")
			.withLongOpt("JUnit3").create("v3");

	private static final Option junitVersion4 = OptionBuilder
			.withDescription("JUnit 4 flag - the selected project is on JUnit4")
			.withLongOpt("JUnit4").create("v4");

	private static final Option html = OptionBuilder
			.withDescription("generates CodeCover HTML report")
			.withLongOpt("html").create("h");

	/**
	 * Comparator class to list the options in the specified other, otherwise
	 * the options will be listed in alphabetical order
	 * 
	 * @return
	 */
	public static Comparator<Option> getComparator() {
		return new Comparator<Option>() {
			// order of options (represented by short option names) - e.g. f =
			// buildFile
			private static final String OPTS_ORDER = "fcjlvtax";

			/**
			 * Implementation of compare, order defined in
			 * <code>OPTS_ORDER</code> Any undefined order will be ranked last.
			 * 
			 * @param o1
			 * @param o2
			 * @return
			 */
			public int compare(Option o1, Option o2) {
				int i1 = OPTS_ORDER.indexOf(o1.getOpt());
				int i2 = OPTS_ORDER.indexOf(o2.getOpt());
				if (i1 < 0) {
					i1 = Integer.MAX_VALUE;
				}
				if (i2 < 0) {
					i2 = Integer.MAX_VALUE;
				}
				int result = i1 - i2;

				return result;
			}
		};
	}

	public static Options getBuildOptions() {

		Options options;

		// mutually exclusive options
		OptionGroup ogTests = new OptionGroup();
		ogTests.addOption(test);
		ogTests.addOption(allTests);

		// mutually exclusive options
		OptionGroup ogExclusion = new OptionGroup();
		ogExclusion.addOption(test);
		ogExclusion.addOption(exclude);

		OptionGroup version = new OptionGroup();
		version.addOption(junitVersion3);
		version.addOption(junitVersion4);
		version.isRequired();

		options = new Options();
		options.addOption(antCompileTarget);
		options.addOption(antTestTarget);
		options.addOption(buildFile);
		options.addOption(listTest);
		options.addOption(html);
		options.addOptionGroup(version);
		options.addOptionGroup(ogTests);
		options.addOptionGroup(ogExclusion);

		return options;

	}

}
