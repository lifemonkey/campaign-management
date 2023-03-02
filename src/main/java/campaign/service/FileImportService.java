package campaign.service;

import campaign.domain.TransactionType;
import campaign.domain.Voucher;
import campaign.excel.ExcelService;
import campaign.repository.TransactionTypeRepository;
import campaign.repository.VoucherRepository;
import campaign.service.dto.VoucherDTO;
import org.apache.commons.csv.CSVRecord;
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
                // if overwritten, delete all records then saveAll
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

    public List<VoucherDTO> readExcelFile(MultipartFile multipartFile) {
        try {
            return ExcelService.readVoucher(multipartFile.getInputStream(), null)
                .stream().map(VoucherDTO::new)
                .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public boolean importExcelFile(MultipartFile multipartFile, boolean overwrite) {
        try {
            List<Voucher> voucherList = ExcelService.readVoucher(multipartFile.getInputStream(), null);
            if (voucherList.isEmpty()) return false;

            // if overwritten, delete all records then saveAll
            if (overwrite) {
                List<Voucher> toBeDeleted = voucherRepository.findAll();
                voucherRepository.saveAll(toBeDeleted.stream().map(Voucher::removeReward).collect(Collectors.toList()));
                voucherRepository.deleteAll();
            }
            // else just saveAll
            voucherRepository.saveAll(voucherList);
            return true;

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
}
