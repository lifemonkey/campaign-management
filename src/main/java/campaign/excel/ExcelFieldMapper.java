package campaign.excel;

import campaign.config.Constants;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExcelFieldMapper {

    final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_DD_MM_YYY);

    public static <T> List<T> getPojos(List<ExcelField[]> excelFields, Class<T> clazz) {

        List<T> list = new ArrayList<>();
        excelFields.forEach(evc -> {

            T t = null;

            try {
                t = clazz.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                     | InvocationTargetException | NoSuchMethodException | SecurityException e1) {
                e1.printStackTrace();
            }

            Class<? extends Object> classz = t.getClass();

            for (int i = 0; i < evc.length; i++) {

                for (Field field : classz.getDeclaredFields()) {
                    field.setAccessible(true);

                    if (evc[i].getPojoAttribute().equalsIgnoreCase(field.getName())) {

                        try {
                            if (FieldType.STRING.getValue().equalsIgnoreCase(evc[i].getExcelColType())) {
                                field.set(t, evc[i].getExcelValue());
                            } else if (FieldType.DOUBLE.getValue().equalsIgnoreCase(evc[i].getExcelColType())) {
                                field.set(t, Double.valueOf(evc[i].getExcelValue()));
                            } else if (FieldType.INTEGER.getValue().equalsIgnoreCase(evc[i].getExcelColType())) {
                                field.set(t, Double.valueOf(evc[i].getExcelValue()).longValue());
                            } else if (FieldType.DATETIME.getValue().equalsIgnoreCase(evc[i].getExcelColType())) {
                                field.set(t, LocalDate.parse(evc[i].getExcelValue(), dtf).atStartOfDay());
                            }
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }

            list.add(t);
        });

        return list;
    }

}
