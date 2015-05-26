/**
 * 
 */
package jw.com.directory.checker.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jw.com.directory.checker.data.FileAttributes;
import jw.com.directory.checker.exception.PathNotFoundException;
import jw.com.directory.checker.writer.FileWriter;

/**
 * jw.com.directory.checker.DirectoryReader
 */
public class DirectoryReader {
	private static final String HISTORYPATH = "c:/temp/history.csv";
	private static final String REPORTPATH = "c:/temp/report.csv";
	private String startPath;
	private String fileFilterPath;
	private FileFilter filter;
	private HistoryReader hsre;
	List<FileAttributes> diffiles;

	/**
	 * Constructor
	 * 
	 * @param startPath the Path of the root directory to work on.
	 * @param fileFilterPath the Path of file filter, Not required. If filter file is present then the files with the
	 *            extentions/names provided will be checked for change.
	 * @throws PathNotFoundException
	 * @throws IOException
	 */
	public DirectoryReader(String startPath, String fileFilterPath) throws PathNotFoundException, IOException {
		super();
		this.startPath = startPath;
		this.fileFilterPath = fileFilterPath;
		init();

	}

	/**
	 * Constructor
	 * 
	 * @param startPath the Path of the root directory to work on.
	 * @throws PathNotFoundException
	 * @throws IOException
	 */
	public DirectoryReader(String startPath, String properties, String regex, boolean useFile)
			throws PathNotFoundException, IOException {
		super();
		this.startPath = startPath;
		checkStartPath(startPath);
		filter = new FileFilter(properties, useFile, regex);
		hsre = new HistoryReader(HISTORYPATH);
		diffiles = new ArrayList<FileAttributes>();
	}

	private void init() throws IOException, PathNotFoundException {
		checkStartPath(startPath);
		filter = new FileFilter(fileFilterPath);
		hsre = new HistoryReader(HISTORYPATH);
		diffiles = new ArrayList<FileAttributes>();
	}

	/**
	 * @param path
	 * @throws PathNotFoundException
	 */
	private void checkStartPath(String path) throws PathNotFoundException {
		File file = new File(path);
		if (!file.exists())
			throw new PathNotFoundException(path);
	}

	public void execute() throws IOException {
		File file = new File(startPath);
		try{
			doSearch(file);
			writeReport();
			FileWriter fw = new FileWriter(HISTORYPATH);
			fw.writeHistory(hsre);
		} catch(Exception ex){
			
		}
	}

	private void writeReport() throws IOException {
		FileWriter fw = new FileWriter(REPORTPATH);
		fw.writeReport(diffiles, startPath);
	}

	private void doCompare(FileAttributes fa) {
		if (hsre.isChanged(fa)) {
			diffiles.add(fa);
			hsre.updateHistory(fa);
		}
	}

	private void doSearch(File file) {
		if (file.isDirectory()) {
			for (File fi : file.listFiles(filter)) {
				doSearch(fi);
			}
		} else {
			if (filter.accept(file)) {
				doCompare(new FileAttributes(file.getAbsolutePath(), file.lastModified(), file.hashCode()));
			}
		}
	}
}
