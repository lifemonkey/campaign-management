package campaign.web.rest.util;

import campaign.config.Constants;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Utility class for HTTP headers creation.
 */
public final class HeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    private static final String APPLICATION_NAME = "campaign-management";

    private HeaderUtil() {
    }

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-campaign-management-alert", message);
        headers.add("X-campaign-management-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".deleted", param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        log.error("Entity processing failed, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-campaign-management-error", "error." + errorKey);
        headers.add("X-campaign-management-params", entityName);
        return headers;
    }

    public static MediaType getImageMediaType(String fileName) {
        String fileExt = FilenameUtils. getExtension(fileName);
        if (Constants.IMAGE_TYPE_JPEGS.contains(fileExt)) {
            return MediaType.IMAGE_JPEG;
        } else if (Constants.IMAGE_TYPE_PNG.contains(fileExt)) {
            return MediaType.IMAGE_PNG;
        } else if (Constants.IMAGE_TYPE_GIF.contains(fileExt)) {
            return MediaType.IMAGE_GIF;
        }

        return MediaType.IMAGE_PNG;
    }
}
