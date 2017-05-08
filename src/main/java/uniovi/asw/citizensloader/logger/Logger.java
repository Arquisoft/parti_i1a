package uniovi.asw.citizensloader.logger;

import java.io.*;
import java.util.Date;

/**
 * Logger class used to write messages in the files
 * @author Diego
 */
public class Logger {

	BufferedWriter file;
	
	/**
	 * Default constructor for Logger. Creates the errors.log file for future additions
	 */
	public Logger() {
		createLog("generatedFiles/errors.log");
	}
	
	/**
	 * Adds a report message to the log file.
	 * @param filename File's name 
	 * @param message Report to be written in the file
	 */
	public void log(String filename, String message) {
		try {
			Date date = new Date();
			file.append("Filename: " + filename + " ");
			file.append(date.toString() +" "+ message + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a new instance of BufferedWriter type.
	 * @param name
	 */
	public void createLog(String name) {
		try {
			file = new BufferedWriter(new FileWriter(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the file being written currently.
	 */
	public void close() {
		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
