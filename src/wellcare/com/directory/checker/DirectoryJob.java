/**
 * 
 */
package wellcare.com.directory.checker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.TimerTask;

import wellcare.com.directory.checker.exception.PathNotFoundException;
import wellcare.com.directory.checker.reader.DirectoryReader;

/**
 * wellcare.com.directory.checker.DirectoryJob
 */
public class DirectoryJob extends TimerTask {

	public static final long DAY = 10000l;// 86400000000l;

	/**
	 * Constructor
	 *
	 */
	public DirectoryJob() {
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		try {
			// DirectoryReader reader = new DirectoryReader("C:/STS", null);
			String[] ini = readFile(new File("C:/temp/scan.ini"));
			if (ini != null) {
				for (String value : ini) {
					DirectoryReader reader = new DirectoryReader(value.split(",")[0],value.split(",")[1], "\\|", false);
					reader.execute();
				}
			}

		} catch (PathNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
				buff.append(scanner.nextLine() + "@");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
		}

		if (buff.length() > 0) {
			return buff.toString().split("@");
		}
		return null;
	}

}
