package campaign.web.rest.vm;

public final class ResponseCode {

    public static final String RESPONSE_NOT_FOUND = "Not Found";
    public static final String RESPONSE_WRONG_PARAM = "Param invalid";

    /**
     * Error code
     * 1XXX: data not found
     *      10XX: User
     *      11XX: Rule
     *      12XX: Campaign
     *      13XX: TargetList
     * 20XX: wrong data
     */
    public static final Integer ERROR_CODE_USER_NOT_FOUND = 1001;
    public static final Integer ERROR_CODE_RULE_NOT_FOUND = 1101;
    public static final Integer ERROR_CODE_CAMPAIGN_NOT_FOUND = 1201;
    public static final Integer ERROR_CODE_TARGET_LIST_NOT_FOUND = 1301;
    public static final Integer ERROR_CODE_FILE_NOT_FOUND = 1401;
    public static final Integer ERROR_CODE_REWARD_NOT_FOUND = 1501;
    public static final Integer ERROR_CODE_REWARD_CONDITION_NOT_FOUND = 1601;
    public static final Integer ERROR_CODE_GENERATED_TIME_NOT_FOUND = 1701;
    public static final Integer ERROR_CODE_TRANSACTION_TYPE_NOT_FOUND = 1801;
    public static final Integer ERROR_CODE_VOUCHER_CODE_ALREADY_EXISTED = 1902;

    public static final Integer ERROR_CODE_PASSWORD_LENGTH = 2001;

    public ResponseCode() {
    }
}
