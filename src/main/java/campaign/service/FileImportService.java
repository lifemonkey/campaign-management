package campaign.service;

import campaign.domain.TransactionType;
import campaign.domain.Voucher;
import campaign.repository.TransactionTypeRepository;
import campaign.repository.VoucherRepository;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FileImportService {

    private final TransactionTypeRepository transactionTypeRepository;

    private final VoucherRepository voucherRepository;

    public FileImportService(TransactionTypeRepository transactionTypeRepository,VoucherRepository voucherRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
        this.voucherRepository = voucherRepository;
    }

    public boolean importCsvFile(MultipartFile multipartFile, boolean overwrite) {
        try {
            List<TransactionType> transactionTypes = convertTransTypeFromCsv(multipartFile.getInputStream());
            if (!transactionTypes.isEmpty()) {
                // if overwrite, delete all records then saveAll
                if (overwrite) {
                    List<TransactionType> toBeDeleted = transactionTypeRepository.findAll();
                    transactionTypeRepository.saveAll(
                        toBeDeleted.stream().map(TransactionType::clearRuleList).collect(Collectors.toList()));
                    transactionTypeRepository.deleteAll();
                }
                // else just saveAll
                transactionTypeRepository.saveAll(transactionTypes);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean importExcelFile(MultipartFile multipartFile, boolean overwrite) {
        try {
            if (!validateExcel(multipartFile.getInputStream())) return false;
            List<Voucher> voucherList = convertVoucherFromWorkBook(multipartFile.getInputStream());

            if (!voucherList.isEmpty()) {
                // if overwrite, delete all records then saveAll
                if (overwrite) {
                    List<Voucher> toBeDeleted = voucherRepository.findAll();
                    voucherRepository.saveAll(toBeDeleted.stream().map(Voucher::removeReward).collect(Collectors.toList()));
                    voucherRepository.deleteAll();
                }
                // else just saveAll
                voucherRepository.saveAll(voucherList);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<TransactionType> convertTransTypeFromCsv(InputStream inputStream) {
        try {
            Iterable<CSVRecord> csvRecords = FileImportUtils.readCsvFile(inputStream);
            List<TransactionType> transactionTypes = new ArrayList<>();
            // Till there is an element condition holds true
            for (CSVRecord csvRecord : csvRecords) {
                transactionTypes.add(new TransactionType()
                        .externalId(csvRecord.get("ID"))
                        .name(csvRecord.get("NAME"))
                        .description(csvRecord.get("DESCRIPTIONS"))
                        .status(Integer.parseInt(csvRecord.get("STATUS")))
                        .transTypeEN(csvRecord.get("TRANS_TYPE_ENGLISH"))
                        .transTypeSW(csvRecord.get("TRANS_TYPE_SWAHILI"))
                );
            }

            return transactionTypes;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean validateExcel(InputStream inputStream) {
        boolean isValidContent = true;
        try {
            // Reading file from local directory
            XSSFSheet sheet = FileImportUtils.readFile(inputStream);

            // Iterate through each row one by one
            Iterator<Row> rowIterator = sheet.iterator();
            // ignore first row
            if (rowIterator.hasNext()) rowIterator.next();
            // Till there is an element condition holds true
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Voucher voucher = new Voucher();
                // For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                if (FileImportUtils.isCellValueInValid(cellIterator)) {
                    isValidContent = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValidContent;
    }

    public List<Voucher> convertVoucherFromWorkBook(InputStream inputStream) {

        try {
            // Reading file from local directory
            XSSFSheet sheet = FileImportUtils.readFile(inputStream);

            // Iterate through each row one by one
            Iterator<Row> rowIterator = sheet.iterator();
            List<Voucher> voucherList = new ArrayList<>();
            // ignore first row
            if (rowIterator.hasNext()) rowIterator.next();
            // Till there is an element condition holds true
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Voucher voucher = new Voucher();
                // For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            // ignore first column ID
                            break;
                        case 1:
                            voucher.setVoucherCode(cell.getStringCellValue());
                            break;
                        case 2:
                            voucher.setStartDate(cell.getLocalDateTimeCellValue());
                            break;
                        case 3:
                            voucher.setExpiredDate(cell.getLocalDateTimeCellValue());
                            break;
                        case 4:
                            // ignore description columns
                            break;
                        default:
                            break;
                    }
                }
                voucherList.add(voucher);
            }

            return voucherList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
