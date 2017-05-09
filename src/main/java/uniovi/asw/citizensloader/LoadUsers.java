package uniovi.asw.citizensloader;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniovi.asw.citizensloader.letter.Letter;
import uniovi.asw.citizensloader.letter.TxtLetter;
import uniovi.asw.citizensloader.reader.Reader;
import uniovi.asw.citizensloader.reader.ReaderFactory;
import uniovi.asw.persistence.model.User;
import uniovi.asw.persistence.repositories.UserRepository;

@Service
public class LoadUsers {

    @Autowired
    private UserRepository db;

    public void run(String... args) throws IOException {
		if (args.length == 0) {
			System.out.println(
					"Proceeding to parse the default file. " + "If you want to specify other pass it as a parameter");
			args[0] = "src/main/resources/test.xlsx";
		} 
		
		Reader r = ReaderFactory.getReader();
		String filename = args[0];
		Letter txt = new TxtLetter();
		List<User> list = r.readFile(filename);
		for (User u : list) {
			db.save(u);
			txt.write(u);
		}
		System.out.println("Los datos del fichero se han procesado.");
	}
}
