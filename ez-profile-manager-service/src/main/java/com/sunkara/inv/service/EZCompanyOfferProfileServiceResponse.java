package com.sunkara.inv.service;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EZCompanyOfferProfileServiceResponse {
	private Date asOfDate;
	private int processCt;
	private List<String> statusMsgs;
}
