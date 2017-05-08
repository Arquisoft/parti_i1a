package uniovi.asw.citizensloader.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import uniovi.asw.citizensloader.letter.PasswordGenerator;
import uniovi.asw.persistence.model.User;

public class ReaderImpl implements Reader {

	List<User> users;

	@Override
	public List<User> readFile(String path) {
		try {

			//
			users = new ArrayList<User>();
			// Load the .xlsx
			FileInputStream file = new FileInputStream(
					new File(path));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//DataFormatter formatter = new DataFormatter();
			int columnCount = 0;

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			int rowCount = 0;
			while (rowIterator.hasNext()) {
				String[] values = new String[7];
				columnCount = 0;
				Row row = rowIterator.next();

				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				for (; columnCount < 3; columnCount++) {
					values[columnCount] = readStr(cellIterator,
							rowCount, columnCount);
				}

				Date birthDate = readDate(cellIterator, rowCount,
						columnCount);
				columnCount++;

				for (; columnCount < 7; columnCount++) {
					values[columnCount] = readStr(cellIterator,
							rowCount, columnCount);
				}
				User user = new User(values[0], values[1], PasswordGenerator.generatePassword(), 
						values[2], values[5], values[6], values[4],  birthDate);

				users.add(user);

				System.out.println("");

				rowCount++;
			}
			workbook.close();
			file.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return users;
	}

	private Date readDate(Iterator<Cell> cellIterator, int rowCount,
			int columnCount) {
		checkNextAvailable(cellIterator, rowCount, columnCount);
		Cell cell = cellIterator.next();

		Instant instant = cell.getDateCellValue().toInstant();

		ZoneId zoneId = ZoneId.of("Europe/Madrid");
		ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zoneId);
		LocalDate localDate = zdt.toLocalDate();

		java.sql.Date date = java.sql.Date.valueOf(localDate);
		return date;
	}

	private String readStr(Iterator<Cell> cellIterator, int rowCount,
			int columnCount) {
		checkNextAvailable(cellIterator, rowCount, columnCount);
		Cell cell = cellIterator.next();
		return cell.getStringCellValue();
	}

	private void checkNextAvailable(Iterator<Cell> cellIterator,
			int rowCount, int columnCount) {
		if (!cellIterator.hasNext()) {
			String message = String.format(
					"Insufficient information. Lacking column nº %d for user number %d",
					columnCount, rowCount);
			throw new IllegalArgumentException(message);
		}
	}
}
