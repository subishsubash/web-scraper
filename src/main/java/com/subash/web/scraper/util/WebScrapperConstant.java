package com.subash.web.scraper.util;

public class WebScrapperConstant {

    // Constants for logger
    public final static String LOG_APP_NAME = "Web Scraper";
    public final static String LOG_OPERATION_ID = "[OPERATION ID] : ";
    public final static String LOG_METHOD = "[HTTP METHOD] : ";
    public final static String LOG_REQUEST = "[REQUEST BODY] : ";
    public final static String LOG_RESPONSE = "[RESPONSE BODY] : ";
    public final static String LOG_FAILURE_MSG = "[FAILED TO LOG] : ";
    public final static String LOG_UUID = "[UUID] : ";
    public final static String LOG_STATUS = "[STATUS] : ";
    public final static String LOG_APP = "[APPLICATION] : ";

    // Operation Id
    public final static String HTML_FILE = "getHtmlFile";
    public final static String HTML_CRAWL = "getHtmlLinks";
    public final static String GET_PHONE_NUMBER = "getPhoneNumber";
    public final static String GET_PHONE_NUMBER_CRAWLS = "phonenumbercrawls";


    // API response
    public static final String ERROR_READ_HTML_FILE = "Failed to read HTML File";
    public static final String ERROR_WRITE_HTML_FILE = "Failed to write the HTML File";
    public static final String ERROR_IN_READ_DOCUMENT = "Failed to fetch document content from URL";
    public static final String ERROR_WEB_CRAWLING = "Failed to process the web crawling";
    public static final String ERROR_FETCHING_PHONE_NUMBER = "Failed to fetch the phone numbers";
    public static final String ERROR_SAVING_PHONE_NUMBER = "Failed to save the phone numbers";

    public static final String OUTPUT_FILE = "src/output/index.html";

}
