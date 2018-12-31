package com.sunkara.inv.document;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Document(collection = "company_offers")
public class EZCompanyOfferProfile {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date asOfDate;
	private String companyName;
	private String issueName;
	private Double offerPrice;
	private Double offerPriceWithFee;
	private Double salesFeeRate;
	private Double totalFunding;
	private Double impliedValuation;
	private Double lastValuations;
	private Double totalShares;
	private String fundStrategy;
	private String companyIndustry;
	private Integer foundedYear;
	private String executiveSummary;
	private String investmentOpportunity;
	private String addlDtls;
	//
	private String sourceId;
	private String issuedBy;
	//
	private String fileName;
	private List<String> fileContents;
	//
	private Date createdTime;
	private Date updatedTime;
}