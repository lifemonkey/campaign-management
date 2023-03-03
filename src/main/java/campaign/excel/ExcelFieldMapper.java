package campaign.excel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExcelFieldMapper {

    final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:MM:ss");

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
                                field.set(t, Long.valueOf(evc[i].getExcelValue()));
                            } else if (FieldType.INTEGER.getValue().equalsIgnoreCase(evc[i].getExcelColType())) {
                                field.set(t, Integer.valueOf(evc[i].getExcelValue()));
                            } else if (FieldType.DATE.getValue().equalsIgnoreCase(evc[i].getExcelColType())) {
                                field.set(t, LocalDateTime.parse(evc[i].getExcelValue(), dtf));
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
