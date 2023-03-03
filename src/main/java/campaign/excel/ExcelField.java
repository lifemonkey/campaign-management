package campaign.excel;

public class ExcelField {

    private String excelHeader;
    private int excelIndex;
    private String excelColType;
    private String excelValue;
    private String pojoAttribute;
    private boolean required;
    private int maxLength;

    public String getExcelHeader() {
        return excelHeader;
    }

    public void setExcelHeader(String excelHeader) {
        this.excelHeader = excelHeader;
    }

    public int getExcelIndex() {
        return excelIndex;
    }

    public void setExcelIndex(int excelIndex) {
        this.excelIndex = excelIndex;
    }

    public String getExcelColType() {
        return excelColType;
    }

    public void setExcelColType(String excelColType) {
        this.excelColType = excelColType;
    }

    public String getExcelValue() {
        return excelValue;
    }

    public void setExcelValue(String excelValue) {
        this.excelValue = excelValue;
    }

    public String getPojoAttribute() {
        return pojoAttribute;
    }

    public void setPojoAttribute(String pojoAttribute) {
        this.pojoAttribute = pojoAttribute;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
