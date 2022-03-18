package com.subash.web.scraper.respository;

import com.subash.web.scraper.document.PhoneNumbersDocuments;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhoneNumbersRepository extends MongoRepository<PhoneNumbersDocuments, String> {

}
