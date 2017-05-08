package uniovi.asw.citizensloader.reader;

// This class would be useful for creating swapping readers with ease.
//
// I suppose this won't happen, making extra Readers is extra work, 
// but I have made it just in case.
//
/**
 * Instantiates and returns readers
 * 
 * @author Pabloski
 */
public class ReaderFactory {
	
	
	/**
	 * This method returns the default implementation of the reader
	 * @return default implementation of the reader
	 */
	public static Reader getReader() {
		return new ReaderImpl();
	}
}
