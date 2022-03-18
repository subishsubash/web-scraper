package com.subash.web.scraper.service;

import com.subash.web.scraper.util.GenericLogger;
import com.subash.web.scraper.util.WebScrapperConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.subash.web.scraper.util.WebScrapperConstant.OUTPUT_FILE;

@Component
public class HTMLFileService {
    private static final Logger logger = LogManager.getLogger(HTMLFileService.class);

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
     * Get HTML file
     *
     * @param uuid
     * @param url
     * @return
     */
    public boolean writeHTMLFile(String uuid, String url) {
        Document document = readHTMLDocumentFromURL(uuid, url);
        boolean isWriteSuccess = false;
        // Write into file
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT_FILE), "UTF-8"));
            if (document != null) {
                // write into file
                bufferedWriter.write(document.toString());
                isWriteSuccess = true;
            } else {
                bufferedWriter.write("");
            }
        } catch (IOException e) {
            GenericLogger.logResponse(logger, uuid, "ERROR", WebScrapperConstant.ERROR_WRITE_HTML_FILE);
            logger.error("ERROR : ", e);
        }
        return isWriteSuccess;
    }

    /**
     * Read the HTML file
     *
     * @param uuid
     * @return
     */
    public ResponseEntity<Resource> fetchHTMLFile(String uuid) {
        try {
            HttpHeaders responseHeader = new HttpHeaders();
            Resource htmlFile = new FileSystemResource(OUTPUT_FILE);
            responseHeader.setContentType(MediaType.APPLICATION_XHTML_XML);
            responseHeader.setContentLength(htmlFile.getFile().length());
            responseHeader.setContentDispositionFormData("attachment", htmlFile.getFilename());
            return new ResponseEntity<>(htmlFile, responseHeader, HttpStatus.OK);
        } catch (IOException e) {
            GenericLogger.logResponse(logger, uuid, "ERROR", WebScrapperConstant.ERROR_READ_HTML_FILE);
            logger.error("ERROR : ", e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
