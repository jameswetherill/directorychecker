package wellcare.com.directory.checker;

import java.util.Timer;

/**
 * @author jwetheri
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Timer timer = new Timer("Directory Reader");
		timer.schedule(new DirectoryJob(), 10000l, DirectoryJob.DAY);
		
	}

}
