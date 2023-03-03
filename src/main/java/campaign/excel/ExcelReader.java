package campaign.excel;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    final static SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy");

    private ExcelReader() {
    }

    public static XSSFSheet readInputStream(InputStream file, String ...sheetName) {
        // Try block to check for exceptions
        try {
            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            // Get first/desired sheet from the workbook
            XSSFSheet sheet = null;

            if (sheetName.length > 0 && sheetName[0] != null) {
                sheet = workbook.getSheet(sheetName[0]);
            }

            if (sheet == null) {
                sheet = workbook.getSheetAt(0);
            }

            return sheet;

        } catch (Exception e) { // Catch block to handle exceptions
            // Display the exception along with line number using printStackTrace() method
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, List<ExcelField[]>> getExcelRowValues(final XSSFSheet sheet) {
        Map<String, List<ExcelField[]>> excelMap = new HashMap<>();
        Map<String, ExcelField[]> excelSectionHeaders = getExcelHeaderSections();
        int totalRows = sheet.getLastRowNum();
        excelSectionHeaders.forEach((section, excelFields) -> {
            List<ExcelField[]> excelFieldList = new ArrayList<>();
            for (int i = 1; i <= totalRows; i++) {
                Row row = sheet.getRow(i);
                ExcelField[] excelFieldArr = new ExcelField[excelFields.length];
                int k = 0;
                for (ExcelField ehc : excelFields) {
                    int cellIndex = ehc.getExcelIndex();
                    String cellType = ehc.getExcelColType();
                    Cell cell = row.getCell(cellIndex);
                    ExcelField excelField = new ExcelField();
                    excelField.setExcelColType(ehc.getExcelColType());
                    excelField.setExcelHeader(ehc.getExcelHeader());
                    excelField.setExcelIndex(ehc.getExcelIndex());
                    excelField.setPojoAttribute(ehc.getPojoAttribute());
                    excelField.setRequired(ehc.isRequired());
                    if (FieldType.STRING.getValue().equalsIgnoreCase(cellType)) {
                        excelField.setExcelValue(cell.getStringCellValue());
                    } else if (FieldType.DOUBLE.getValue().equalsIgnoreCase(cellType)
                        || FieldType.INTEGER.getValue().equalsIgnoreCase(cellType)) {
                        excelField.setExcelValue(String.valueOf(cell.getNumericCellValue()));
                    } else if (DateUtil.isCellDateFormatted(cell)) {
                        excelField.setExcelValue(dtf.format(cell.getDateCellValue()));
                    }
                    excelFieldArr[k++] = excelField;
                }
                // check if entire row is empty, then ignore next row
                boolean rowHasContent = Arrays.stream(excelFieldArr)
                    .filter(excelField -> {
                        if (FieldType.DOUBLE.getValue().equalsIgnoreCase(excelField.getExcelColType())
                            || FieldType.INTEGER.getValue().equalsIgnoreCase(excelField.getExcelColType())
                        ) {
                            return !excelField.getExcelValue().equalsIgnoreCase("0.0");
                        } else {
                            return excelField.getExcelValue() != null && !excelField.getExcelValue().isEmpty();
                        }
                    })
                    .findAny().isPresent();

                if (!rowHasContent) break;
                // add row to list if it is valid
                excelFieldList.add(excelFieldArr);
            }
            excelMap.put(section, excelFieldList);
        });
        return excelMap;
    }

    private static Map<String, ExcelField[]> getExcelHeaderSections() {
        List<Map<String, List<ExcelField>>> jsonConfigMap = getExcelHeaderFieldSections();
        Map<String, ExcelField[]> jsonMap = new HashMap<>();
        jsonConfigMap.forEach(jps -> {
            jps.forEach((section, values) -> {
                ExcelField[] excelFields = new ExcelField[values.size()];
                jsonMap.put(section, values.toArray(excelFields));
            });
        });
        return jsonMap;
    }

    private static List<Map<String, List<ExcelField>>> getExcelHeaderFieldSections() {
        List<Map<String, List<ExcelField>>> jsonMap = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonConfig = new String(
                Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("excel.json").toURI())));

            jsonMap = objectMapper.readValue(jsonConfig, new TypeReference<List<Map<String, List<ExcelField>>>>() {
            });
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return jsonMap;
    }

}
