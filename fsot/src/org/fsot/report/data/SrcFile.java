package org.fsot.report.data;

/*
 * A class for representing a single source file as specified by CodeCover. Each
 * source file instance will contain the source file's content, filename and internal
 * ID as specified by CodeCover.
 * 
 * @author  Shahriar Tajbakhsh
 * @version	15/01/2013
 */
public class SrcFile {
	private String internalId;
	private String filename;
	private String content;

	/**
	 * 
	 * Class constructor specifying a source file's internal ID, filename and
	 * content as specified by CodeCover
	 * 
	 * @param internalId
	 *            The internal ID of the source file as specified in the
	 *            CodeCover Test Session XML output
	 * @param filename
	 *            The filename of the source file
	 * @param content
	 *            The content of the source file. The content is basically all
	 *            the text in the source file represented as one line
	 */
	public SrcFile(String internalId, String filename, String content) {
		this.internalId = internalId;
		this.filename = filename;
		this.content = content;
	}

	/**
	 * 
	 * @return The internal ID of the source file
	 */
	public String getInternalId() {
		return this.internalId;
	}

	/**
	 * 
	 * @return The filename of the source file
	 */
	public String getFilename() {
		return this.filename;
	}

	/**
	 * 
	 * @return The content of the source file. The whole content is represented
	 *         in one line.
	 */
	public String getContent() {
		return this.content;
	}

	@Override
	public boolean equals(Object srcFile) {
		if ((srcFile != null) && srcFile instanceof SrcFile) {
			return this.internalId.equals(((SrcFile) srcFile).getInternalId())
					&& this.filename.equals(((SrcFile) srcFile).getFilename())
					&& this.content.equals(((SrcFile) srcFile).getContent());
		}

		return false;
	}

	@Override
	public String toString() {
		return filename + "(" + internalId + ")";
	}
}
