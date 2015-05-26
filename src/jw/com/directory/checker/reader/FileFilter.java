/**
 * 
 */
package jw.com.directory.checker.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * jw.com.directory.checker.reader.FileFilter
 */
public class FileFilter implements Filter {

	String filterPath;
	String[] values = null;

	/**
	 * Constructor
	 * 
	 * @param regex the seperator for the filter string.
	 *
	 * @param filter a File path which will read in a csv file.
	 */
	public FileFilter(String filter) {
		this.filterPath = filter;
		getFilter();
	}

	
	/**
	 * Constructor
	 *
	 * @param filter
	 * @param getFile
	 * @param regex
	 */
	public FileFilter(String filter, boolean getFile, String regex) {
		if (getFile) {
			this.filterPath = filter;
			getFilter();
		} else {
			values = filter.split(regex);
		}
	}

	/**
	 * Get the filter values from the input path.
	 */
	private void getFilter() {
		if (filterPath != null) {
			File file = new File(filterPath);
			if (file.exists()) {
				values = readFile(file);
			} else {
				values = new String[1];
				values[0] = filterPath;
			}
		}
	}

	/**
	 * @param file
	 * @return String[] the values that are read in or null if the file cannot be read.
	 */
	private String[] readFile(File file) {
		StringBuffer buff = new StringBuffer();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(file));
			while (scanner.hasNextLine()) {
				buff.append(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
		}

		if (buff.length() > 0) {
			return buff.toString().split(",");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File pathname) {
		if (values != null) {
			for (String value : values) {
				if (pathname != null && pathname.getAbsolutePath().endsWith(value)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

}
