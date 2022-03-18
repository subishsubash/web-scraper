package com.subash.web.scraper.service;

import com.subash.web.scraper.document.PhoneNumbersDocuments;
import com.subash.web.scraper.model.HTMLCrawls;
import com.subash.web.scraper.model.HTMLLink;
import com.subash.web.scraper.model.PhoneNumberCrawls;
import com.subash.web.scraper.respository.PhoneNumbersRepository;
import com.subash.web.scraper.util.GenericLogger;
import com.subash.web.scraper.util.WebScrapperConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PhoneNumberService {

    private static final Logger logger = LogManager.getLogger(PhoneNumberService.class);

    @Autowired
    WebCrawlerService webCrawlerService;

    @Autowired
    PhoneNumbersRepository repository;

    /**
     * Read HTML Document
     *
     * @param url
     * @return
     */
    private Document readHTMLDocumentFromURL(String uuid, String url) {
        Document document = null;
        try {
            // Get HTML Document for the URL
            document = Jsoup.connect(url).get();
        } catch (Exception e) {
            logger.error("ERROR : ", e);
            GenericLogger.logResponse(logger, uuid, "ERROR", WebScrapperConstant.ERROR_IN_READ_DOCUMENT);
        }
        return document;
    }


    /**
     * @param uuid
     * @param url
     * @return
     */
    public ResponseEntity<List<String>> getPhoneNumber(String uuid, String url) {
        List<String> response = new ArrayList<>();
        Document document = readHTMLDocumentFromURL(uuid, url);
        try {
            // Phone Number pattern
            // TODO  Regex should be improved.
            String regex_num = "\\+?[0-9. ()-]{10,25}";
            Pattern pattern = Pattern.compile(regex_num);

            Elements phoneNumberElements = document.getElementsMatchingOwnText(pattern);
            if (!phoneNumberElements.isEmpty()) {
                for (Element element : phoneNumberElements) {
                    Matcher matcher = pattern.matcher(element.text());
                    if (matcher.find()) {
                        logger.info("Phone Number : " + matcher.group(0).trim());
                        response.add(matcher.group(0).trim());
                    }
                }
            }
            GenericLogger.logResponse(logger, uuid, "SUCCESS", response);
        } catch (Exception e) {
            GenericLogger.logResponse(logger, uuid, "ERROR", WebScrapperConstant.ERROR_FETCHING_PHONE_NUMBER);
            logger.error("ERROR : ", e);
        }
        return ResponseEntity.ok(response);
    }


    /**
     * Get Phone numbers from all sub sites by crawling
     *
     * @param uuid
     * @param htMLCrawls
     * @return
     */
    public ResponseEntity<List<PhoneNumberCrawls>> phoneNumberCrawling(String uuid, HTMLCrawls htMLCrawls) {
        List<PhoneNumberCrawls> response = new ArrayList<>();

        try {
            List<HTMLLink> HTMLLinkList = webCrawlerService.webCrawling(uuid, htMLCrawls).getBody();
            HTMLLinkList.stream().forEach(htmlLink -> {
                List<String> phoneNumbers = getPhoneNumber(uuid, htmlLink.getLink()).getBody();
                if (phoneNumbers != null && !phoneNumbers.isEmpty()) {
                    PhoneNumberCrawls phoneNumberCrawls = new PhoneNumberCrawls();
                    phoneNumberCrawls.setUrl(htmlLink.getLink());
                    phoneNumberCrawls.setPhoneNumber(phoneNumbers);
                    response.add(phoneNumberCrawls);
                }
            });

        } catch (Exception e) {
            GenericLogger.logResponse(logger, uuid, "ERROR", WebScrapperConstant.ERROR_FETCHING_PHONE_NUMBER);
            logger.error("ERROR : ", e);
        }

        // Save PhoneNumbers
        try {
            if (!response.isEmpty()) {
                PhoneNumbersDocuments phoneNumbersDocuments = new PhoneNumbersDocuments();
                phoneNumbersDocuments.setUuid(uuid);
                phoneNumbersDocuments.setPhoneNumberCrawls(response);
                repository.save(phoneNumbersDocuments);
            }
        } catch (Exception e) {
            GenericLogger.logResponse(logger, uuid, "ERROR", WebScrapperConstant.ERROR_SAVING_PHONE_NUMBER);
            logger.error("ERROR : ", e);
        }
        return ResponseEntity.ok(response);
    }
}
