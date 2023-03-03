package campaign.web.rest.vm;

public final class ResponseCode {

    public static final String RESPONSE_NOT_FOUND = "Not Found";
    public static final String RESPONSE_WRONG_PARAM = "Param invalid";
    public static final String RESPONSE_RULE_NAME_IS_DUPLICATED = "Rule name is duplicated. Please enter a new name!";
    public static final String RESPONSE_CAMPAIGN_NAME_IS_DUPLICATED = "Campaign name is duplicated. Please enter a new name!";
    public static final String RESPONSE_REWARD_NAME_IS_DUPLICATED = "Reward name is duplicated. Please enter a new name!";
    public static final String RESPONSE_REWARD_VOUCHER_INVALID = "Voucher code value is invalid!";
    public static final String RESPONSE_REWARD_VOUCHER_IS_DUPLICATED = "Voucher is duplicated!";

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
    public static final Integer ERROR_CODE_RULE_NAME_IS_EMPTY = 1102;
    public static final Integer ERROR_CODE_RULE_NAME_IS_DUPLICATED = 1103;
    public static final Integer ERROR_CODE_RULE_CANNOT_BE_DELETED = 1104;
    public static final Integer ERROR_CODE_RULE_TEMPLATE_CANNOT_BE_CLONED = 1105;
    public static final Integer ERROR_CODE_CAMPAIGN_NOT_FOUND = 1201;
    public static final Integer ERROR_CODE_CAMPAIGN_NAME_IS_EMPTY = 1202;
    public static final Integer ERROR_CODE_CAMPAIGN_NAME_IS_DUPLICATED = 1203;
    public static final Integer ERROR_CODE_TARGET_LIST_NOT_FOUND = 1301;
    public static final Integer ERROR_CODE_TARGET_LIST_NAME_IS_EMPTY = 1302;
    public static final Integer ERROR_CODE_FILE_NOT_FOUND = 1401;
    public static final Integer ERROR_CODE_FILE_EXPORT_FAILED = 1402;
    public static final Integer ERROR_CODE_FILE_EXTENSION_INVALID = 1403;
    public static final Integer ERROR_CODE_FILE_CONTENT_EMPTY = 1404;
    public static final Integer ERROR_CODE_FILE_CONTENT_REQUIRED_FIELD_MISSING = 1405;
    public static final Integer ERROR_CODE_FILE_CONTENT_FIELD_LENGTH_LIMIT = 1406;
    public static final Integer ERROR_CODE_REWARD_NOT_FOUND = 1501;
    public static final Integer ERROR_CODE_REWARD_NAME_IS_EMPTY = 1502;
    public static final Integer ERROR_CODE_REWARD_NAME_IS_DUPLICATED = 1503;
    public static final Integer ERROR_CODE_REWARD_CANNOT_BE_DELETED = 1504;
    public static final Integer ERROR_CODE_REWARD_TEMPLATE_CANNOT_BE_CLONED = 1505;
    public static final Integer ERROR_CODE_REWARD_VOUCHER_INVALID = 1506;
    public static final Integer ERROR_CODE_REWARD_VOUCHER_IS_DUPLICATED = 1507;
    public static final Integer ERROR_CODE_REWARD_CONDITION_NOT_FOUND = 1601;
    public static final Integer ERROR_CODE_GENERATED_TIME_NOT_FOUND = 1701;
    public static final Integer ERROR_CODE_GENERATED_TIME_IS_EMPTY = 1702;
    public static final Integer ERROR_CODE_TRANSACTION_TYPE_NOT_FOUND = 1801;
    public static final Integer ERROR_CODE_TRANSACTION_TYPE_NAME_IS_EMPTY = 1802;
    public static final Integer ERROR_CODE_RECORD_NOT_EXISTED = 1901;

    public static final Integer ERROR_CODE_PASSWORD_LENGTH = 2001;

    public ResponseCode() {
    }
}
