package campaign.excel;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import campaign.domain.Voucher;
import org.apache.poi.EncryptedDocumentException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelService {

    public static List<Voucher> readVoucher(InputStream inputStream, String sheetName) throws InvalidFormatException {
        try {
            XSSFSheet sheet = ExcelReader.readInputStream(inputStream, sheetName);
            Map<String, List<ExcelField[]>> excelRowValuesMap = ExcelReader.getExcelRowValues(sheet);
            List<ExcelField[]> excelFields = excelRowValuesMap.get(ExcelSection.VOUCHERS.getValue());
            if (validateResults(excelFields)) {
                return ExcelFieldMapper.getPojos(excelFields, Voucher.class);
            }

        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        }

        return Arrays.asList();
    }

    private static boolean validateResults(List<ExcelField[]> excelFields){
        return !excelFields.stream()
            .filter(efs -> Arrays.stream(efs)
                .filter(ef -> ef.isRequired() && ef.getExcelValue().isEmpty())
                .findAny().isPresent())
            .findAny().isPresent();
    }

    public static void write(String filePath, String sheetName) {}
}
