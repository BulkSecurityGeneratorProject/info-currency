package pl.lodz.p.zzpj;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static DateUtils instance;

    private DateUtils() {

    }

    public static synchronized DateUtils getInstance() {
        if(instance == null) {
            instance = new DateUtils();
        }
        return instance;
    }

    public String parseDateToString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public Date parseStringToDate(String date, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }
}
