package com.subash.web.scraper.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class GenericLogger {

    private final static String COMMA = ", ";

    /**
     * Method helps to log the API requests
     *
     * @param logger
     * @param operationId
     * @param method
     * @param requestBody
     */
    public static void logRequest(Logger logger, String UUID, String operationId, String method, Object requestBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String requestBodyString = mapper.writeValueAsString(requestBody);
            StringBuffer logMsg = new StringBuffer(UUID);
            logMsg.append(COMMA).append(WebScrapperConstant.LOG_APP).append(WebScrapperConstant.LOG_APP_NAME).append(COMMA).append(WebScrapperConstant.LOG_OPERATION_ID)
                    .append(operationId).append(COMMA).append(WebScrapperConstant.LOG_METHOD).append(method)
                    .append(COMMA).append(WebScrapperConstant.LOG_REQUEST)
                    .append(requestBodyString);
            logger.info(logMsg.toString());
        } catch (Exception e) {
            logger.info(UUID + COMMA + WebScrapperConstant.LOG_FAILURE_MSG + e.getMessage());
        }
    }

    /**
     * @param logger
     * @param UUID
     * @param status
     * @param responseObject
     */
    public static void logResponse(Logger logger, String UUID, String status, Object responseObject) {
        try {
            StringBuffer logMsg = new StringBuffer(UUID);
            ObjectMapper mapper = new ObjectMapper();
            String responseObjectString = mapper.writeValueAsString(responseObject);
            logMsg.append(COMMA).append(WebScrapperConstant.LOG_APP).append(WebScrapperConstant.LOG_APP_NAME).append(COMMA).append(WebScrapperConstant.LOG_STATUS)
                    .append(status).append(COMMA).append(WebScrapperConstant.LOG_RESPONSE).append(responseObjectString);
            logger.info(logMsg.toString());
        } catch (Exception e) {
            logger.info(UUID + COMMA + WebScrapperConstant.LOG_FAILURE_MSG + e.getMessage());
        }
    }

    /**
     * Method helps to generate the UUID for logger
     *
     * @return
     */
    public static String getUUID() {
        StringBuffer UUIDString = new StringBuffer(WebScrapperConstant.LOG_UUID);
        UUID uuid = UUID.randomUUID();
        UUIDString.append(uuid);
        return UUIDString.toString();
    }


}
