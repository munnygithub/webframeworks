package com.sunkara.inv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunkara.inv.document.EZCompanyOfferProfile;
import com.sunkara.inv.repository.mongodb.EZCompanyOfferProfileRepository;

@RestController
@RequestMapping("/company")
public class EZCompanyOfferProfileReaderService {
	@Autowired
	EZCompanyOfferProfileReaderServiceController controller;
	@Autowired
	EZCompanyOfferProfileRepository companyOffersRepo;

	@GetMapping("/getAll")
    public List<EZCompanyOfferProfile> getAll() {
        return companyOffersRepo.findAll();
    }
	
	@PostMapping("/deleteAll")
    public void deleteAll() {
        companyOffersRepo.deleteAll();
    }
	
	@GetMapping("/load")
	public EZCompanyOfferProfile loadCompany(@RequestParam(name="name") String name) throws Exception {
		
		return controller.loadCopmanyOfferDetailsByFileName(name);
		
	}

	@GetMapping("/loadAll")
	public EZCompanyOfferProfileServiceResponse loadAllCompany() throws Exception {
		
		return controller.loadAllCopmanyOfferDetails();
		
	}

}
