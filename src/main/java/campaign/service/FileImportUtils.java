package campaign.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class FileImportUtils {
    private static final String CSV_FILE_DELIMITER = ",";

    public static Iterable<CSVRecord> readCsvFile(InputStream inputStream) {

        // Getting CSVReader object by passing specified Delimiter
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            return csvParser.getRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static XSSFSheet readFile(InputStream file, String ...sheetName) {
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
}
