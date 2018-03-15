package com.example.ui.service;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pdf")
public class PDFReader {

	@RequestMapping("/read")
	public String readDoc() throws Exception {
		// Loading an existing document
		File file = new File("///Users/Srini/Downloads/AWS Summit - San Francisco - Confirmation.pdf");
		file = new File("///Users/Srini/Documents/Munny/Cloud_Storage/Private Company Inv/EquityZen/Company Profiles/23andMe/23andMe Details _ EquityZen_20180208.pdf");
		
		PDDocument document = PDDocument.load(file);

		// Instantiate PDFTextStripper class
		PDFTextStripper pdfStripper = new PDFTextStripper();

		// Retrieving text from PDF document
		String text = pdfStripper.getText(document);
		System.out.println(text);

		// Closing the document
		document.close();
		
		return text;
	}

}
