package campaign.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Iterator;

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

    public static boolean isCellValueInValid(Iterator<Cell> cellIterator) {
        boolean isCellInvalid = true;

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            switch (cell.getColumnIndex()) {
                case 0:
                    // No: maximum 10 characters
                    isCellInvalid = String.valueOf(cell.getNumericCellValue()).length() > 10;
                    break;
                case 1:
                    // Voucher code: Tối đa 10 ký tự
                    isCellInvalid = cell.getStringCellValue().length() > 10;
                    break;
                case 2:
                case 3:
                    // Expired date must have format dd/mm/yyyy
                    // Effective date must have format dd/mm/yyyy
                    isCellInvalid = !isValidDate(cell.getLocalDateTimeCellValue().toString());
                    break;
                case 4:
                    // Description maximum characters are 200
                    isCellInvalid = cell.getStringCellValue().length() > 200;
                    break;
                default:
                    break;
            }

            if (isCellInvalid) break;
        }

        return isCellInvalid;
    }

    public static boolean isValidDate(String date) {
        boolean valid = false;
        try {
            LocalDateTime.parse(date);
            valid = true;
        } catch (Exception e) {
            e.printStackTrace();
            valid = false;
        }

        return valid;
    }
}
