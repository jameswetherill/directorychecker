/**
 * 
 */
package jw.com.directory.checker.exception;

/**
 * jw.com.directory.checker.exception.PathNotFoundException
 */
public class PathNotFoundException extends Throwable {
	private static final long serialVersionUID = 3738482253969235688L;
	private static final String MESSAGE = "The Path that was specified was Not Found! Path:";
	
	/**
	 * 
	 */
	public PathNotFoundException() {
		super(MESSAGE);
	}
	
	/**
	 * @param arg0
	 */
	public PathNotFoundException(String arg0) {
		super(MESSAGE + arg0);
	}
	
	
}
