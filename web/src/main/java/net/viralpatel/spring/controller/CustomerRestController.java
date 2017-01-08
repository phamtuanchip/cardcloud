package net.viralpatel.spring.controller;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.viralpatel.spring.dao.CustomerDAO;
import net.viralpatel.spring.model.Customer;
import storage.OCRService;

@RestController
public class CustomerRestController {

	@Autowired
	private CustomerDAO customerDAO;
	private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/tmpFiles";

	@GetMapping("/customers")
	public List getCustomers() {
		return customerDAO.list();
	}

	private final static String getDateTime() {
		DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		df.setTimeZone(TimeZone.getTimeZone("PST"));
		return df.format(new Date());
	}

	// java(controller)
	@PostMapping("ajaxupload")
	public Map<String, Object> saveCanvasImage(
			@RequestParam(value = "imageBase64", defaultValue = "") String imageBase64) {
		Map<String, Object> res = new HashMap<String, Object>();
		String name = getDateTime() + ".png";
		String message = "You successfully uploaded file";

		try {
			// System.out.println(imageBase64);
			String rootPath = System.getProperty("catalina.home");
			File dir = new File(rootPath + File.separator + "tmpFiles");
			if (!dir.exists())
				dir.mkdirs();

			File file = new File(dir.getAbsolutePath() + File.separator + name);

			//System.out.println(file.getAbsolutePath());

			String base64Image = imageBase64.split(",")[1];
			//Base64.getMimeDecoder().
			//byte[] decodedImg = Base64.getMimeDecoder().decode(base64Image);
			//byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(imageBase64);
			//byte[] decodedImg = new Base64Security().decodeBase64ToBytes(base64Image.getBytes());
			
			//Path destinationFile = Paths.get(dir.getAbsolutePath(), name);
			//Files.write(destinationFile, decodedImg);
			//String convereted = Base64.getEncoder().withoutPadding().encodeToString(base64Image.getBytes());
			byte[] imageBytes = Base64.getMimeDecoder().decode(base64Image);
			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
			
			//File imageFile = new File("Image.png");

			ImageIO.write(bufferedImage, "png", file);
			
			
			
			res.put("ret", 0);
			res.put("msg", message);

		} catch (Exception e) {
			e.printStackTrace();
			res.put("ret", -1);
			res.put("msg", e.getMessage());
			return res;
		}

		return res;
	}

	public Optional<ByteArrayInputStream> Base64InputStream(Optional<String> base64String) throws IOException {
		if (base64String.isPresent()) {
			return Optional
					.ofNullable(new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(base64String.get())));
		}

		return Optional.empty();
	}

	public Optional<String> InputStreamToBase64(Optional<InputStream> inputStream) throws IOException {
		if (inputStream.isPresent()) {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			FileCopyUtils.copy(inputStream.get(), output);
			// TODO retrieve content type from file, & replace png below with it
			return Optional
					.ofNullable("data:image/png;base64," + DatatypeConverter.printBase64Binary(output.toByteArray()));
		}

		return Optional.empty();
	}

	@PostMapping("/image/upload")
	public String saveImage(@RequestParam("file") MultipartFile file, Model model) {
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

	@GetMapping("/image/{filename}.{fileext}")
	public void getImage(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("filename") String filename, @PathVariable("fileext") String fileext) throws IOException {

		String rootPath = System.getProperty("catalina.home");
		File dir = new File(rootPath + File.separator + "tmpFiles");

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		String filePath = dir.getAbsolutePath() + File.separator + filename + "." + fileext;
		System.out.println("===>>> " + filePath);
		File file = new File(filePath);

		if (!file.exists()) {
			String errorMessage = "Sorry. The file you are looking for does not exist";
			System.out.println(errorMessage);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}

		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			System.out.println("mimetype is not detectable, will take default");
			mimeType = "application/octet-stream";
		}

		System.out.println("mimetype : " + mimeType);

		response.setContentType(mimeType);

		/*
		 * "Content-Disposition : inline" will show viewable types [like
		 * images/text/pdf/anything viewable by browser] right on browser while
		 * others(zip e.g) will be directly downloaded [may provide save as
		 * popup, based on your browser setting.]
		 */
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

		/*
		 * "Content-Disposition : attachment" will be directly download, may
		 * provide save as popup, based on your browser setting
		 */
		// response.setHeader("Content-Disposition", String.format("attachment;
		// filename=\"%s\"", file.getName()));

		response.setContentLength((int) file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		// Copy bytes from source to destination(outputstream in this example),
		// closes both streams.
		FileCopyUtils.copy(inputStream, response.getOutputStream());

	}

	@GetMapping("/customers/{id}")
	public ResponseEntity getCustomer(@PathVariable("id") Long id) {

		Customer customer = customerDAO.get(id);
		if (customer == null) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(customer, HttpStatus.OK);
	}

	@PostMapping(value = "/customers")
	public ResponseEntity createCustomer(@RequestBody Customer customer) {

		customerDAO.create(customer);

		return new ResponseEntity(customer, HttpStatus.OK);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity deleteCustomer(@PathVariable Long id) {

		if (null == customerDAO.delete(id)) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(id, HttpStatus.OK);

	}

	@PutMapping("/customers/{id}")
	public ResponseEntity updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {

		customer = customerDAO.update(id, customer);

		if (null == customer) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(customer, HttpStatus.OK);
	}

}