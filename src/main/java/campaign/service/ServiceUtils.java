package campaign.service;

import campaign.config.Constants;
import campaign.domain.Reward;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class ServiceUtils {

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }

    public static LocalDate convertToLocalDate(Instant instantToConvert) {
        return instantToConvert
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }

    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }

    public static LocalDateTime convertToLocalDateTime(Instant instantToConvert) {
        return instantToConvert
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }

    public static String clonedFileName(String clonedName, List<String> nameList) {
//        nameList = Arrays.asList("Rule 1-Copy109", "Rule 1", "Rule 1-Copy01", "Rule 1-Copy07", "Rule 1-Copy03", "Rule 1-CopyAA", "Rule 1-Copy06");
        Optional<Integer> nameOpt = nameList.stream()
            .filter(name -> isValidName(clonedName, name))
            .map(name -> Integer.valueOf(name.substring(clonedName.length(), name.length())))
            .sorted(Comparator.comparingInt(name -> (int) name).reversed())
            .findFirst();

        if (nameOpt.isPresent()) {
            if (nameOpt.get() >= 9) {
                return clonedName + (nameOpt.get() + 1);
            } else {
                return clonedName + "0" + (nameOpt.get() + 1);
            }
        }

        return clonedName + "01";
    }

    public static String getFileNameExt(String fileNameWExt) {
        String[] fileNameArr = fileNameWExt.split("\\.");
        if (fileNameArr.length == 2) {
            return "." + fileNameArr[1];
        }

        return "";
    }

    public static String getFileName(String fileNameWExt) {
        String[] fileNameArr = fileNameWExt.split("\\.");
        if (fileNameArr.length == 2) {
            return fileNameArr[0] + Constants.CLONE_POSTFIX;
        }

        return fileNameWExt + Constants.CLONE_POSTFIX;
    }

    public static Boolean isValidName(String clonedName, String value) {
        if (value.startsWith(clonedName)) {
            try {
                String lastCount = value.substring(clonedName.length(), value.length());
                if (Integer.parseInt(lastCount) >= 0) {
                    return true;
                }
            } catch (Exception ex) {
                return false;
            }
        }

        return false;
    }

    public static boolean isImageType(String fileName) {
        String fileExt = FilenameUtils. getExtension(fileName);
        if (fileExt == null && fileExt.isEmpty()) return false;

        return Constants.IMAGE_TYPE_JPEGS.contains(fileExt)
            || Constants.IMAGE_TYPE_PNG.contains(fileExt)
            || Constants.IMAGE_TYPE_GIF.contains(fileExt);

    }

    public static <T> List<T> getPageContent(Pageable pageable, List<T> targetList) {

        // slice result list
        int start = (int)pageable.getOffset();
        int end = (targetList.size() > ((int)pageable.getOffset() + pageable.getPageSize()))
            ? ((int)pageable.getOffset() + pageable.getPageSize())
            : targetList.size();

        return (end > start) ? targetList.subList(start, end) : Collections.emptyList();
    }
}
