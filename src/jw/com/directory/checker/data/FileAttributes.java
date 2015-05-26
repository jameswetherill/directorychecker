package jw.com.directory.checker.data;

public class FileAttributes {
	
	private long lastModified;
	private String path;
	private int hash;
	
	
	/**
	 * Constructor
	 *
	 * @param lastModified
	 * @param path
	 * @param hash
	 */
	public FileAttributes(String path,long lastModified, int hash) {
		super();
		this.lastModified = lastModified;
		this.path = path;
		this.hash = hash;
	}
	
	public long getLastModified() {
		return lastModified;
	}
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getHash() {
		return hash;
	}
	public void setHash(int hash) {
		this.hash = hash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hash;
		result = prime * result + (int) (lastModified ^ (lastModified >>> 32));
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FileAttributes)) {
			return false;
		}
		FileAttributes other = (FileAttributes) obj;
		if (hash != other.hash) {
			return false;
		}
		if (lastModified != other.lastModified) {
			return false;
		}
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!path.equals(other.path)) {
			return false;
		}
		return true;
	}
	
}
