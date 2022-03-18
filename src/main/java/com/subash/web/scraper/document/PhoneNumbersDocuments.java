package com.subash.web.scraper.document;

import com.subash.web.scraper.model.PhoneNumberCrawls;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("PhoneNumbers")
public class PhoneNumbersDocuments {

    @Id
    String uuid;
    List<PhoneNumberCrawls> phoneNumberCrawls;

}
