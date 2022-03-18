package com.subash.web.scraper.controller;

import com.subash.web.scraper.model.HTMLCrawls;
import com.subash.web.scraper.model.HTMLLink;
import com.subash.web.scraper.model.PhoneNumberCrawls;
import com.subash.web.scraper.rest.controller.WebscraperApi;
import com.subash.web.scraper.service.HTMLFileService;
import com.subash.web.scraper.service.PhoneNumberService;
import com.subash.web.scraper.service.WebCrawlerService;
import com.subash.web.scraper.util.GenericLogger;
import com.subash.web.scraper.util.WebScrapperConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1")
public class WebScraperController implements WebscraperApi {

    private static final Logger logger = LogManager.getLogger(WebScraperController.class);

    @Autowired
    WebCrawlerService webCrawlerService;

    @Autowired
    HTMLFileService htmlFileService;

    @Autowired
    PhoneNumberService phoneNumberService;

    @Override
    public ResponseEntity<Resource> getHtmlFile(String url) {
        String uuid = GenericLogger.getUUID();
        GenericLogger.logRequest(logger, uuid, WebScrapperConstant.HTML_FILE, "GET", url);
        boolean isWriteSuccess = htmlFileService.writeHTMLFile(uuid, url);
        if (isWriteSuccess) {
            return htmlFileService.fetchHTMLFile(uuid);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<List<HTMLLink>> getHtmlLinks(HTMLCrawls htMLCrawls) {
        String uuid = GenericLogger.getUUID();
        GenericLogger.logRequest(logger, uuid, WebScrapperConstant.HTML_CRAWL, "POST", htMLCrawls);
        return  webCrawlerService.webCrawling(uuid, htMLCrawls);
    }

    @Override
    public ResponseEntity<List<String>> getPhoneNumber(String url) {
        String uuid = GenericLogger.getUUID();
        GenericLogger.logRequest(logger, uuid, WebScrapperConstant.GET_PHONE_NUMBER, "GET", url);
        return  phoneNumberService.getPhoneNumber(uuid, url);
    }

    @Override
    public ResponseEntity<List<PhoneNumberCrawls>> getPhoneNumberCrawls(HTMLCrawls htMLCrawls) {
        String uuid = GenericLogger.getUUID();
        GenericLogger.logRequest(logger, uuid, WebScrapperConstant.GET_PHONE_NUMBER_CRAWLS, "POST", htMLCrawls);
        return  phoneNumberService.phoneNumberCrawling(uuid, htMLCrawls);
    }
}
