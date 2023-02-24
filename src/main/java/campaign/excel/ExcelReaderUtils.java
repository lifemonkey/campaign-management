package campaign.excel;

import campaign.domain.TransactionType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReaderUtils {

    public TransactionType importTransactionType(String fileLocation, String sheetName) {
        // Try block to check for exceptions
        try {
            // Reading file from local directory
            FileInputStream file = new FileInputStream(fileLocation);
            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            // Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            // Iterate through each row one by one
            Iterator<Row> rowIterator = sheet.iterator();
            List<TransactionType> transactionTypes = new ArrayList<>();
            // Till there is an element condition holds true
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                TransactionType transactionType = new TransactionType();
                // For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            transactionType.setExternalId(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case 1:
                            transactionType.setName(cell.getStringCellValue());
                            break;
                        case 2:
                            transactionType.setDescription(cell.getStringCellValue());
                            break;
                        case 3:
                            transactionType.setStatus((int)cell.getNumericCellValue());
                            break;
                        case 4:
                            transactionType.setTransTypeEN(cell.getStringCellValue());
                            break;
                        case 5:
                            transactionType.setTransTypeSW(cell.getStringCellValue());
                            break;
                    }
                }
                transactionTypes.add(transactionType);
            }

            // Closing file output streams
            file.close();
        }
        // Catch block to handle exceptions
        catch (Exception e) {
            // Display the exception along with line number using printStackTrace() method
            e.printStackTrace();
        }
    }
}
