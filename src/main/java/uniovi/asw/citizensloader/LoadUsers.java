package uniovi.asw.citizensloader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniovi.asw.citizensloader.letter.Letter;
import uniovi.asw.citizensloader.letter.TxtLetter;
import uniovi.asw.citizensloader.reader.Reader;
import uniovi.asw.persistence.model.User;
import uniovi.asw.persistence.repositories.UserRepository;

@Service
public class LoadUsers {

    @Autowired
    private UserRepository db;
    
    @Autowired
    private Reader r;

    public void load(InputStream stream) throws IOException {
    	List<User> list = r.readStream(stream);
    	save(list);
    }
    
//    Previous execution entry point (not working in Spring)
//    
//    public void run(String... args) throws IOException {
//		if (args.length == 0) {
//			System.out.println(
//					"Proceeding to parse the default file. " + "If you want to specify other pass it as a parameter");
//			args[0] = "src/main/resources/test.xlsx";
//		} 
//		
//		String filename = args[0];
//		List<User> list = r.readFile(filename);
//		save(list);
//		System.out.println("Los datos del fichero se han procesado.");
//	}

	private void save(List<User> list) {
		Letter txt = new TxtLetter();
		for (User u : list) {
			db.save(u);
			txt.write(u);
		}
	}
}
