package com.sunkara.inv.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an example on how to extract text line by line from pdf document
 */
@Component
public class PDFReaderAsLineByLine extends PDFTextStripper {

	static List<String> lines = new ArrayList<String>();

	public PDFReaderAsLineByLine() throws IOException {
	}

	/**
	 * @throws IOException
	 *             If there is an error parsing the document.
	 */
	public List<String> readDoc(String fileName) throws IOException {
		PDDocument document = null;
		
		try {
			lines = new ArrayList<String>();
			document = PDDocument.load(new File(fileName));
			setSortByPosition(true);
			setStartPage(0);
			setEndPage(document.getNumberOfPages());

			Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
			writeText(document, dummy);
		
		} finally {
			if (document != null) {
				document.close();
			}
		}
		
		return lines;
	}

	/**
	 * Override the default functionality of PDFTextStripper.writeString()
	 */
	@Override
	protected void writeString(String str, List<TextPosition> textPositions) throws IOException {
		lines.add(str);
		// you may process the line here itself, as and when it is obtained
//		System.out.println(textPositions);
	}
}