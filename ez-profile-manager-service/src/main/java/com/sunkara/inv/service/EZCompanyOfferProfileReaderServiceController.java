package com.sunkara.inv.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sunkara.inv.document.EZCompanyOfferProfile;
import com.sunkara.inv.repository.mongodb.EZCompanyOfferProfileRepository;
import com.sunkara.inv.utils.DateUtility;
import com.sunkara.inv.utils.FileList;
import com.sunkara.inv.utils.NumberUtility;
import com.sunkara.inv.utils.PDFReader;
import com.sunkara.inv.utils.PDFReaderAsLineByLine;
import com.sunkara.inv.utils.PDFReaderTika;

@Component
public class EZCompanyOfferProfileReaderServiceController {
	@Autowired
	PDFReader pdfReader;
	@Autowired
	PDFReaderAsLineByLine pdfReaderAsLineByLine;
	@Autowired
	PDFReaderTika pdfReaderTika;
	@Autowired
	EZCompanyOfferProfileRepository companyOffersRepository;
	@Autowired
	DateUtility dateUtility;
	@Autowired
	NumberUtility numberUtility;
	@Autowired
	FileList fileList;

	public EZCompanyOfferProfile loadCopmanyOfferDetailsByFileName(String name) throws Exception {
		
		// Loading an existing document
		File file = new File("///Users/Srini/Downloads/AWS Summit - San Francisco - Confirmation.pdf");
		file = new File("///Users/Srini/Documents/Munny/Cloud_Storage/Private Company Inv/EquityZen/Company Profiles/"
				+ name);
		
		String filePath = file.getAbsolutePath();
		
		return loadCopmanyOfferDetailsByFolder(filePath);

	}

	public EZCompanyOfferProfileServiceResponse loadAllCopmanyOfferDetails() {
		
		EZCompanyOfferProfileServiceResponse resp = new EZCompanyOfferProfileServiceResponse();
		resp.setAsOfDate(new Date());
		
		String baseFolderName = "///Users/Srini/Documents/Munny/Cloud_Storage/Private Company Inv/EquityZen/Company Profiles/";
		
		List<File> files = fileList.listFiles(baseFolderName, true);
		if (files != null) {
			resp.setProcessCt(files.size());	
		}
		List<String> respMsgs = new ArrayList<>();
		String respMsg = null;
		for(File file : files) {
			respMsg = file.getAbsolutePath();
			
			try {
				System.out.println("Processing -- " + file.getAbsolutePath());
				loadCopmanyOfferDetailsByFolder(file.getAbsolutePath());
				
				respMsg = "OK" + "," + respMsg;
			} catch (Exception e) {
				respMsg = "ERROR" + "," + respMsg + "," + e.getMessage();
				System.out.println(e.getMessage());
//				e.printStackTrace();
			}
			respMsgs.add(respMsg);
		}
		resp.setStatusMsgs(respMsgs);
		
		return resp;
	}

	public EZCompanyOfferProfile loadCopmanyOfferDetailsByFolder(String filePath) throws Exception {
		
		String watermark = "sunkarsr@yahoo.com";

		File file = new File(filePath);
		String fileName = file.getName();
		
//		System.out.println(fileName + " ---- " + filePath);
		String asOfDate = fileName.replaceAll(".pdf", "");
		asOfDate = asOfDate.substring(asOfDate.length() - 8);
		Date asOfDt = dateUtility.convertStringToDate(asOfDate, "yyyyMMdd");
//		System.out.println(asOfDate + " ----- " + asOfDt);
		
		
		StringBuffer outContents = new StringBuffer();
		List<String> lines = null;
		
		// PDFReader - Read whole document as String
//		String fileContents = pdfReader.readDoc(file.getAbsolutePath());
		
		// PDFReaderAsLineByLine - Read whole document as List<String> using single line parser
//		lines = pdfReaderAsLineByLine.readDoc(file.getAbsolutePath());

		// PDFReaderTika - Read whole document as List<String> using Tika package
		lines = pdfReaderTika.readDocAsStringList(file.getAbsolutePath());
		

		/*
		 * Clean up lines
		 */
		lines = lines.stream().filter(line -> (!watermark.toUpperCase().contains(line.trim().toUpperCase()) && line.trim().length() > 0)).collect(Collectors.toList());

		// print lines
//		for (String line : lines) {
//			System.out.println(line);
//		}
		
		int maxLines = lines.size();
		String line = null;
		String companyName = null;
		String fundStrategy = null;
		for (int i=0; i<maxLines; i++) {			
			line = lines.get(i).toUpperCase();
			
			// (/company/addepar/)
			if (companyName == null && line.indexOf("(/COMPANY/") != -1) {
				companyName = line.replace("(/COMPANY/", "");
				companyName = companyName.replace(")", "");
				companyName = companyName.replace("/", "");
			} else if (line.indexOf("FUND STRATEGY") != -1) {
				fundStrategy = lines.get(i + 1);
			}

		}
		companyName = (companyName != null)?companyName.trim().toUpperCase():null;
		fundStrategy = (fundStrategy != null)?fundStrategy.trim().toUpperCase():null;
		
		if (companyName == null || companyName.trim().length() == 0) {
			companyName = fundStrategy;
		}
		
		System.out.println("Company Name: " + companyName);
		if (companyName == null) {
			throw new Exception("Company Name can't be null");
		}
		companyName = companyName.toUpperCase().replace("COMMON", "");
		companyName = companyName.toUpperCase().replace("STOCK", "");
		companyName = companyName.toUpperCase().replace(",", "");
		
		EZCompanyOfferProfile entity = companyOffersRepository.findByAsOfDateAndCompanyName(asOfDt, companyName);
		if (entity == null) {
			entity = companyOffersRepository.findByFileName(fileName);
		} else {
			if (entity.getFileName() != null && entity.getFileName().trim().equalsIgnoreCase(fileName)) {
				// Entity is found by file name while the As of Date and Company Name are different.
				System.out.println("Company is found by file name and not by date & company name.");
				System.out.println("As of Date: " + asOfDate);
				System.out.println("Company Name: " + companyName);
				System.out.println(entity);
				
				throw new Exception("Company is found by file name and not by date & company name. companyName=" + companyName + ", asOfDt=" + asOfDt);
			}
		}
		
		if (entity == null) {
			entity = new EZCompanyOfferProfile();
			entity.setCompanyName(companyName);
			entity.setId(dateUtility.getCurrentTimestampUTC());
			entity.setAsOfDate(asOfDt);
			entity.setCreatedTime(new Date());
		}

		entity.setFileName(fileName);
		entity.setFundStrategy(fundStrategy);
		entity.setSourceId("FILE");
		entity.setIssuedBy("EQUITYZEN");
		entity.setUpdatedTime(new Date());
				
		String prevLine = null;
		
		for (int i=0; i<maxLines; i++) {			
			prevLine = "";
			if (i > 0) {
				prevLine = lines.get(i - 1).toUpperCase();
			}
			line = lines.get(i).toUpperCase();
			
			if (prevLine != null && prevLine.indexOf("INVESTMENT OPPORTUNITY") != -1) {
				outContents.append(prevLine + "\n");
				outContents.append(lines.get(i) + "\n");
				entity.setAddlDtls(lines.get(i));
			} else if (line.indexOf("IMPLIED VALUATION") != -1) {
				outContents.append(lines.get(i) + "\n");
				outContents.append(lines.get(i + 1) + "\n");
				entity.setImpliedValuation(convertNumberStringToDouble(lines.get(i + 1)));
			} else if (line.indexOf("TOTAL FUNDING") != -1) {
				entity.setTotalFunding(convertNumberStringToDouble(lines.get(i + 1)));
			} else if (line.indexOf("WHAT THEY DO") != -1) {
				entity.setCompanyIndustry(lines.get(i + 1));
			} else if (line.indexOf("FOUNDED IN") != -1) {
				entity.setFoundedYear(numberUtility.convertStringToInteger(lines.get(i + 1), null));
			} else if (line.startsWith("TOTALS")) {
				
				line = line.replace("TOTALS", "");
				
				line = line.replace(",", "");
				
				String[] items = line.split(" ");
				if (items != null && items.length > 0) {
					entity.setTotalShares(convertNumberStringToDouble(items[0]));
				}
			} else if (line.startsWith("VALUATION")) {
				line = line.replace("VALUATION", "");
				entity.setLastValuations(convertNumberStringToDouble(line));
			} else if (prevLine.indexOf("EXPECTED") != -1 && prevLine.endsWith("SHARE PRICE") && line.indexOf("INCL. FEES") != -1) {
				outContents.append(lines.get(i - 1) + "\n");
				outContents.append(lines.get(i) + "\n");
				// 11.00 (incl. fees, 11.55)
				String offerPriceStr = lines.get(i);
				offerPriceStr = offerPriceStr.replace("incl. fees", "");
				offerPriceStr = offerPriceStr.replace("(", "");
				offerPriceStr = offerPriceStr.replace(")", "");
//				System.out.println("Price -- " + offerPriceStr);
				String[] dtls = offerPriceStr.split(",");
//				for(String item : dtls) {
//					System.out.println(item);
//				}
				entity.setOfferPrice(numberUtility.convertStringToDouble(dtls[0].trim(), null));
				entity.setSalesFeeRate(numberUtility.convertStringToDouble(dtls[1].trim(), null));
				
			}
			
		}
		
		entity.setFileContents(lines);
		
//		System.out.println("Entity:\n" + entity + "\n\n\n");
		companyOffersRepository.save(entity);
		
		return entity;
	}

	private Double convertNumberStringToDouble(String inValue) {
		
		inValue = (inValue == null)?"":inValue;
		
		if (inValue.trim().isEmpty()) {
			return null;
		}
		
		inValue = inValue.replace("$", "");
		inValue = inValue.replace(",", "");
		
		boolean billionValue = false;
		boolean millionValue = false;
		if (inValue.toUpperCase().indexOf("BILLION") != -1) {
			billionValue = true;
			inValue = inValue.toUpperCase().replaceAll("BILLION", "");
		} else if (inValue.toUpperCase().indexOf("M") != -1) {
			millionValue = true;
			inValue = inValue.toUpperCase().replaceAll("M", "");
		}
		
		Double val = numberUtility.convertStringToDouble(inValue.trim(), null);
		
		if (val != null) {
			if (billionValue) {
				val = val * 1000000000;
			} else if (millionValue) {
				val = val * 1000000;
			}
		}
		
		return val;
	}
}
