package com.sunkara.inv.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.ContentHandler;

@Component
public class PDFReaderTika {

	public String readDoc(String fileName) throws Exception {
		String fileContents = null;
		InputStream is = null;
	    try {
	      is = new FileInputStream(fileName);
	      ContentHandler contenthandler = new BodyContentHandler();
	      Metadata metadata = new Metadata();
	      PDFParser pdfparser = new PDFParser();
	      pdfparser.parse(is, contenthandler, metadata, new ParseContext());
	      fileContents = contenthandler.toString();
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    finally {
	        if (is != null) is.close();
	    }
		
		return fileContents;
	}
	
	public List<String> readDocAsStringList(String fileName) throws Exception {
		String fileContents = readDoc(fileName);
		
		List<String> lines = Arrays.asList(fileContents.split("\n"));

		return lines;
	}

}
