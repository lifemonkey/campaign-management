package campaign.web.rest.vm;

public class ResponseVM {

    private String type;

    private Integer errorCode;

    private String errorMessage;

    public ResponseVM(String type, Integer errorCode, String errorMessage) {
        this.type = type;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getType() {
        return type;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "ResponseVM{" +
            "type='" + type + '\'' +
            ", errorCode='" + errorCode + '\'' +
            ", errorMessage='" + errorMessage + '\'' +
            '}';
    }
}
