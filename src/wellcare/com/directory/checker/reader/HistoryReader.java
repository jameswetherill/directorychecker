package wellcare.com.directory.checker.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import wellcare.com.directory.checker.data.FileAttributes;
import wellcare.com.directory.checker.exception.PathNotFoundException;

public class HistoryReader {

	private String startPath;
	private Map<String, FileAttributes> values;

	public HistoryReader(String startPath)
			throws  IOException {
		super();
		this.startPath = startPath;
		checkStartPath(startPath);
		values= new HashMap<String, FileAttributes>();
		readHistory();
	}

	/**
	 * @param path
	 * @throws PathNotFoundException
	 * @throws IOException 
	 */
	private void checkStartPath(String path) throws IOException {
		File file = new File(path);
		if (!file.exists())
			file.createNewFile();
	}
	
	private void readHistory(){
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(startPath));
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(",");
				values.put(line[0], new FileAttributes(line[0], Long.parseLong(line[1]), Integer.parseInt(line[2])));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
		}
	}
	
	/**
	 * Looks to see if the object name is in the map.
	 * @param name of the object to look for.
	 * @return true if name is found
	 */
	public boolean hasName(String name){
		if(name != null){
			if(values.containsKey(name)){
				return true;
			} else {
				for(String key:values.keySet()){
					if(key.endsWith(name)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * This updates or adds a new value to the history if the object is not null.
	 * @param fa the object to update if not null.
	 */
	public void updateHistory(FileAttributes fa){
		if(fa != null){
			values.put(fa.getPath(), fa);
		}
	}
	
	/**
	 * Checks to see if the file has changed any thing since the last time
	 * @param fa the object to compare against
	 * @return true if there is a change in the file or if fa is null
	 */
	public boolean isChanged(FileAttributes fa){
		if(fa != null){
			FileAttributes obj = values.get(fa.getPath());			
			return !fa.equals(obj);			
		}
		return true;
	}
	
	public Map<String, FileAttributes> getValues(){
		return values;
	}
	
	public String getPath(){
		return startPath;
	}

}
