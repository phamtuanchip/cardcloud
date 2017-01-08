package storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRService {

	private static void loadLib(String path, String name) {
		name = System.mapLibraryName(name); // extends name with .dll, .so or
											// .dylib
		try {
			InputStream in = Thread.class.getResourceAsStream("/" + path + name);
			File fileOut = new File("your lib path");
			OutputStream out = FileUtils.openOutputStream(fileOut);
			IOUtils.copy(in, out);
			in.close();
			out.close();
			System.load(fileOut.toString());// loading goes here
		} catch (Exception e) {
			// handle
		}
	}

	public static void main(String[] args) {
		// TODO code application logic here
		String lang = "eng";
		String fileParth = "D:\\projects\\cardcloud\\ocr\\card-visit-vietcombank.jpg";
		try {
			ReadImage(fileParth, lang);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String ReadImage(String fileParth, String langguage) throws Exception {

		File imageFile = new File(fileParth);
		Tesseract instance = Tesseract.getInstance();
		instance.setDatapath("src/main/resources");
		// ITesseract instance = new Tesseract(); // JNA Interface Mapping
		// ITesseract instance = new Tesseract1(); // JNA Direct Mapping
		// File tessDataFolder = LoadLibs.extractTessResources("tessdata"); //
		// Maven build bundles English data
		// instance.setDatapath(tessDataFolder.getParent());
		String result = "";
		try {
			String lang = langguage;
			instance.setLanguage(lang);

			result = instance.doOCR(imageFile);
			System.out.println(result);

			// write in a file
			try {
				File file = new File(fileParth.replace(".jpg", ".txt"));
				BufferedWriter out = new BufferedWriter(new FileWriter(file));
				out.write(result);
				out.close();
			} catch (IOException ex) {
				throw ex;
			}

		} catch (TesseractException ep) {
			throw ep;
			// System.err.println(ep.getMessage());
		}
		return result;
	}
}
