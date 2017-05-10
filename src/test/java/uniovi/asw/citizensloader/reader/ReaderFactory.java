package uniovi.asw.citizensloader.reader;

public class ReaderFactory {

	public static Reader getReader() {
		return new ReaderImpl();
	}

}
