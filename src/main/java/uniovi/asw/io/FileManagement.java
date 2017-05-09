package uniovi.asw.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class FileManagement {

    public int getSupport(String filename){
        int support = 0;
        FileReader fr = null;
        File file = null;
        BufferedReader br = null;
        try {

            file = new File(filename);
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    support = Integer.parseInt(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } finally {
            try {
                if (null != fr)
                    fr.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
        return support;
    }

	public Set<String> getNotAllowedWords(String filename) {
		Set<String> words = new HashSet<String>();
		FileReader fr = null;
		File file = null;
		BufferedReader br = null;
		try {

			file = new File(filename);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			try {
				String line;
				while ((line = br.readLine()) != null) {
					words.add(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} finally {
			try {
				if (null != fr)
					fr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		return words;
	}
}
