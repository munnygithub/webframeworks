package com.sunkara.inv.utils;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
public class PDFReader {

	public String readDoc(String fileName) throws Exception {
		// Loading an existing document
		File file = new File(fileName);
		
		PDDocument document = PDDocument.load(file);

		// Instantiate PDFTextStripper class
		PDFTextStripper pdfStripper = new PDFTextStripper();

		// Retrieving text from PDF document
		String fileContents = pdfStripper.getText(document);
//		System.out.println(fileContents);
		
//		System.out.println("Word Separator: " + pdfStripper.getWordSeparator());
//		System.out.println("Line Separator: " + pdfStripper.getLineSeparator());
//		System.out.println("Text Line Matrix: " + pdfStripper.getTextLineMatrix());

		// Closing the document
		document.close();
		
		return fileContents;
	}

}
