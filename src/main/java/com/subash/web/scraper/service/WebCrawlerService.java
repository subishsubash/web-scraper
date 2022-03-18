package com.subash.web.scraper.service;

import com.subash.web.scraper.model.HTMLCrawls;
import com.subash.web.scraper.model.HTMLLink;
import com.subash.web.scraper.util.GenericLogger;
import com.subash.web.scraper.util.WebScrapperConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class WebCrawlerService {


    private static final Logger logger = LogManager.getLogger(WebCrawlerService.class);

    List<HTMLLink> response;

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
     * @param htmlCrawls
     * @return
     */
    public ResponseEntity<List<HTMLLink>> webCrawling(String uuid, HTMLCrawls htmlCrawls) {
        response = new ArrayList<>();
        Document document = readHTMLDocumentFromURL(uuid, htmlCrawls.getUrl());

        HashSet<String> links = new HashSet<>();
        try {
            if (document != null) {
                getLinks(links, htmlCrawls.getUrl(), 0, htmlCrawls.getRequiredDepth());
            }
        } catch (Exception e) {
            GenericLogger.logResponse(logger, uuid, "ERROR", WebScrapperConstant.ERROR_WEB_CRAWLING);
            logger.error("ERROR : ", e);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * Method to get All Links
     *
     * @param url
     * @param depth
     */
    public void getLinks(HashSet<String> links, String url, int depth, int maxDepth) {
        if ((!links.contains(url) && (depth < maxDepth))) {
            logger.info(" Depth : " + depth + " URL : " + url);

            // Set response
            HTMLLink htmlLink = new HTMLLink();
            htmlLink.setDepth(depth);
            htmlLink.setLink(url);
            response.add(htmlLink);
            try {
                links.add(url);
                Document document = Jsoup.connect(url).get();
                Elements linksOnPage = document.select("a[href]");
                depth++;
                for (Element page : linksOnPage) {
                    getLinks(links, page.attr("abs:href"), depth, maxDepth);
                }
            } catch (IOException e) {
                logger.error("URL : " + url + "ERROR : ", e.getMessage());
            }
        }
    }
}














