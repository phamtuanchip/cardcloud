package net.viralpatel.spring.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import storage.OCRService;
import storage.StorageFileNotFoundException;
import storage.StorageService;

@Controller
public class FileUploadController {

	/**
	 * private final StorageService storageService;
	 * 
	 * @Autowired public FileUploadController(StorageService storageService) {
	 *            this.storageService = storageService; }
	 * 
	 * 			@GetMapping("/") public String listUploadedFiles(Model model)
	 *            throws IOException {
	 * 
	 * 
	 *            return "uploadForm"; }
	 * 
	 * 			@GetMapping("/files/{filename:.+}")
	 * @ResponseBody public ResponseEntity<Resource> serveFile(@PathVariable
	 *               String filename) {
	 * 
	 *               Resource file = storageService.loadAsResource(filename);
	 *               return ResponseEntity .ok()
	 *               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;
	 *               filename=\""+file.getFilename()+"\"") .body(file); }
	 * 
	 * 				@PostMapping("/") public String
	 *               handleFileUpload(@RequestParam("file") MultipartFile file,
	 *               RedirectAttributes redirectAttributes) {
	 * 
	 *               storageService.store(file);
	 *               redirectAttributes.addFlashAttribute("message", "You
	 *               successfully uploaded " + file.getOriginalFilename() +
	 *               "!");
	 * 
	 *               return "redirect:/"; }
	 * 
	 * @ExceptionHandler(StorageFileNotFoundException.class) public
	 *                                                       ResponseEntity
	 *                                                       handleStorageFileNotFound(StorageFileNotFoundException
	 *                                                       exc) { return
	 *                                                       ResponseEntity.notFound().build();
	 *                                                       }
	 * 
	 */

	@GetMapping("/upload")
	public String hello(Model model) {

		return "upload";
	}
	private  final static String getDateTime()  
	{  
	    DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");  
	    df.setTimeZone(TimeZone.getTimeZone("PST"));  
	    return df.format(new Date());  
	}  
	/**
	 * Upload single file using Spring Controller
	 */
	// @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@PostMapping("/upload")
	public String uploadFileHandler(@RequestParam("file") MultipartFile file, Model model) {
		// @RequestParam("name") String name,

		String name = getDateTime();
		String message = "You successfully uploaded file";
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				String realfile = file.getOriginalFilename();
				name += realfile.substring(realfile.lastIndexOf("."), realfile.length());
				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				String langguage = "eng";

				try {
					message = OCRService.ReadImage(serverFile.getAbsolutePath(), langguage);
				} catch (Exception e) {
					e.printStackTrace();
					message = e.getMessage();
				}

				model.addAttribute("fileUrl", "image/" + name);
				model.addAttribute("message", message);
				return "upload";

			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}

	/**
	 * Upload multiple file using Spring Controller
	 */
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody String uploadMultipleFileHandler(@RequestParam("name") String[] names,
			@RequestParam("file") MultipartFile[] files) {

		if (files.length != names.length)
			return "Mandatory information missing";

		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		}
		return message;
	}

}