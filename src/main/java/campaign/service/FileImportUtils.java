package campaign.service;

import org.apache.commons.csv.CSVFormat;

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

}
