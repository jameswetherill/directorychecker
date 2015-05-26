/**
 * 
 */
package wellcare.com.directory.checker.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

import wellcare.com.directory.checker.data.FileAttributes;
import wellcare.com.directory.checker.reader.HistoryReader;

/**
 * wellcare.com.directory.checker.writer.FileWriter
 */
public class FileWriter {

	private File file;
	private static final String COMMA = ",";
	private static final String ENDLINE = "\n";
	
	/**
	 * Constructor
	 *
	 * @param path
	 * @throws IOException
	 */
	public FileWriter(String path) throws IOException {
		file = new File(path);
		file.createNewFile();
	}

	/**
	 * @param value
	 * @param append
	 */
	public void write(String value, boolean append) {
		try {
			
			BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(
					file, append));
			writer.write(value);
			writer.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

	}

	
	/**
	 * @param reader
	 */
	public void writeHistory(HistoryReader reader){
		StringBuffer buff = new StringBuffer(); 				
		for(String key:reader.getValues().keySet()){
			FileAttributes at = reader.getValues().get(key);
			buff.append(at.getPath()).append(COMMA);
			buff.append(at.getLastModified()).append(COMMA);
			buff.append(at.getHash()).append(COMMA).append(ENDLINE);
		}		
		write(buff.toString(), false);
	}

	/**
	 * @param diffiles
	 * @param rootDir
	 */
	public void writeReport(List<FileAttributes> diffiles, String rootDir) {
		StringBuffer buff = new StringBuffer(); 
		buff.append("These files have either been added or changed in the root directory."+ENDLINE);
		buff.append("RootDir :"+rootDir+ENDLINE);
		for(FileAttributes at:diffiles){
			buff.append(at.getPath()).append(COMMA);
			buff.append(at.getLastModified()).append(COMMA);
			buff.append(at.getHash()).append(COMMA).append(ENDLINE);
		}
		
		write(buff.toString().substring(0,buff.length() - ENDLINE.length()), true);
	}
}
