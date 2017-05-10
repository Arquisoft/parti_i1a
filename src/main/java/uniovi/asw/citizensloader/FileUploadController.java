package uniovi.asw.citizensloader;

import java.io.IOException;

import org.apache.poi.UnsupportedFileFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import uniovi.asw.citizensloader.storage.StorageFileNotFoundException;
//import uniovi.asw.citizensloader.storage.StorageService;

@Controller
public class FileUploadController {

	private LoadUsers loader;

	@Autowired
	public FileUploadController(LoadUsers loader) {
		this.loader = loader;
	}

	@GetMapping("/upload")
	public String listUploadedFiles(Model model) throws IOException {

		return "upload_form";
	}

	@PostMapping("/upload")
	public String handleFileUpload(
			@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		try {
			loader.load(file.getInputStream());

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded "
							+ file.getOriginalFilename() + "!");

		} catch (IOException e) {
			System.out.println("Error processing uploaded file: "
					+ e.getMessage());

			redirectAttributes.addFlashAttribute("message",
					"The uploaded file " + file.getOriginalFilename()
							+ " could not be opened.");

		} catch (OpenXML4JRuntimeException
				| UnsupportedFileFormatException e) {

			System.out.println("Error processing uploaded file: "
					+ e.getMessage());

			redirectAttributes.addFlashAttribute("message",
					"The uploaded file " + file.getOriginalFilename()
							+ " could not be processed.\n"
							+ e.getMessage());

		} catch (DataAccessException e) {

			System.out.println("Error processing uploaded file: "
					+ e.getMessage());

			redirectAttributes.addFlashAttribute("message",
					"The uploaded file " + file.getOriginalFilename()
							+ " could not be processed. "
							+ "Please check that the data is valid and "
							+ "has not been already uploaded.");
		}
		return "redirect:/upload";
	}
}