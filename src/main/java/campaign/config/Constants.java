package campaign.config;

/**
 * Application constants.
 */
public final class Constants {

    private Constants() {}

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final String APPROVED_STATUS = "Approved";
    public static final String REJECTED_STATUS = "Rejected";
    public static final String RUNNING_STATUS = "Running";
    public static final String PENDING_APPROVE_STATUS = "Pending Approve";
    public static final String INITIALIZATION_STATUS = "Initialization";
    public static final String PAUSE_STATUS = "Paused";
    public static final String CANCELLED_STATUS = "Cancelled";
    public static final String COMPLETED_STATUS = "Completed";

    public static final String PAUSE_CAMPAIGN = "PAUSE";
    public static final String CANCEL_CAMPAIGN = "CANCEL";

}
