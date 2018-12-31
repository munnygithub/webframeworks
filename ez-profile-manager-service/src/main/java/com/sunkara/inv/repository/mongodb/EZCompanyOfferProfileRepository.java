package com.sunkara.inv.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sunkara.inv.document.EZCompanyOfferProfile;

import java.util.Date;
import java.util.List;

public interface EZCompanyOfferProfileRepository extends MongoRepository<EZCompanyOfferProfile, Long> {

	EZCompanyOfferProfile findFirstByCompanyName(String companyName);

	EZCompanyOfferProfile findByAsOfDateAndCompanyName(Date asOfDate, String companyName);

	// Supports native JSON query string
	@Query("{companyName: { $regex: ?0 } })")
	List<EZCompanyOfferProfile> findByCompanyName(String domain);

	@Query("{fileName: { $regex: ?0 } })")
	EZCompanyOfferProfile findByFileName(String fileName);

}