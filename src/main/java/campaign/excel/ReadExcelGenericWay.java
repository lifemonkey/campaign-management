package campaign.excel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import campaign.domain.Voucher;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;


public class ReadExcelGenericWay {

    public static void main(String[] args) throws InvalidFormatException {
        try {
            Workbook workbook = ExcelFileReader.readExcel("/Users/hqd/Desktop/template_voucher.xlsx");
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, List<ExcelField[]>> excelRowValuesMap = ExcelFileReader.getExcelRowValues(sheet);
            excelRowValuesMap.forEach((section, rows) -> {
                // System.out.println(section);
                // System.out.println("==============");
                boolean headerPrint = true;
                for (ExcelField[] evc : rows) {
                    if (headerPrint) {
                        for (int j = 0; j < evc.length; j++) {
                            // System.out.print(evc[j].getExcelHeader() + "t");
                        }
                        // System.out.println();
                        // System.out.println(
                        // "------------------------------------------------------------------------------------");
                        // System.out.println();
                        headerPrint = false;
                    }
                    for (int j = 0; j < evc.length; j++) {
                        // System.out.print(evc[j].getExcelValue() + "t");
                    }
                }
            });
            List<Voucher> voucherList = ExcelFieldMapper
                .getPojos(excelRowValuesMap.get(ExcelSection.VOUCHERS.getValue()), Voucher.class);

            voucherList.forEach(voucher -> System.out.println(voucher));

        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
