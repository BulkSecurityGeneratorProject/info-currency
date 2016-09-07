package pl.lodz.p.zzpj.utils;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private Logger logger = Logger.getLogger(DateUtil.class);

    private static DateUtil instance;

    private DateUtil() {

    }

    public static synchronized DateUtil getInstance() {
        if(instance == null) {
            instance = new DateUtil();
        }
        return instance;
    }

    public String parseDateToString(Date date, String format) {
        logger.info("parseDateToString invoked");
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public Date parseStringToDate(String date, String format) throws ParseException {
        logger.info("parseStringToDate invoked");
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }

}
