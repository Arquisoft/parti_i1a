package citloader.reader;

import uniovi.asw.citizensloader.reader.Reader;
import uniovi.asw.citizensloader.reader.ReaderImpl;

public class ReaderFactory {

	public static Reader getReader() {
		return new ReaderImpl();
	}

}
