package pl.lodz.p.zzpj.managers;

import ch.qos.logback.core.util.TimeUtil;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.jni.Time;
import org.h2.mvstore.DataUtils;
import org.springframework.stereotype.Component;
import pl.lodz.p.zzpj.utils.DateUtils;
import pl.lodz.p.zzpj.controllers.vm.CurrencyVM;
import pl.lodz.p.zzpj.model.Currency;
import pl.lodz.p.zzpj.model.ExchangeRatesSeries;
import pl.lodz.p.zzpj.xml.XMLparser;
import pl.lodz.p.zzpj.xml.XMLparserJAXB;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * CurrenciesManager implementation that downloads currencies from Narodowy Bank Polski site.
 */
@Component("currenciesManager")
public class CurrenciesManagerNBP implements CurrenciesManager {
    Logger logger = Logger.getLogger(CurrenciesManagerNBP.class);

    private static final String COURSES_URL_PREFIX = "http://www.nbp.pl/kursy/xml/";
    private static final String LAST_COURSES_URL = "http://www.nbp.pl/kursy/xml/LastA.xml";
    private static final String CURRENCY_URL_PREFIX = "http://api.nbp.pl/api/exchangerates/rates/c/";
    private static final String FORMAT_SUFFIX = "?format=xml";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private XMLparser parser;

    @Override
    public String getLastCurrenciesXMLFromWebsite() {
        logger.info("getLastCurrenciesXMLFromWebsite invoked");
        String content = "";
        URL url = null;
        try {
            url = new URL(LAST_COURSES_URL);
            InputStream input = url.openStream();
            content = IOUtils.toString(input, "utf-8");
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public ExchangeRatesSeries getDailyCurrencyRateFromWebsite(Currency currencySymbol) {
        logger.info("getDailyCurrencyRateFromWebsite invoked");
//        parser = new XMLparserJAXB();
//        try {
//            return parser.parseXMLtoObject(currencySymbol);
//        } catch (JAXBException | IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    public ExchangeRatesSeries getCurrencyRate(CurrencyVM request) {
        logger.info("getCurrencyRate invoked" );
        parser = new XMLparserJAXB();
        String url = null;
        if(request.isUpToDateRates() && (request.getCurrency() != null)) {
            makeSureDateIsValid(request);
            url = CURRENCY_URL_PREFIX + request.getCurrency() + FORMAT_SUFFIX;
        } else if(request.getHistoricalDate() != null && (request.getCurrency() != null)) {
            url = CURRENCY_URL_PREFIX + request.getCurrency() + "/" + request.getHistoricalDate() + FORMAT_SUFFIX;
        } else {
            return null;
        }

        try {
            return parser.parseXMLtoObject(url);
        } catch (JAXBException | IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Object getRatesDependsOnParams(CurrencyVM request) {
        logger.info("getRatesDependsOnParams invoked" );
        if(request.getLowHistDate() != null && request.getHighHistDate() != null) {
            return getRangeRates(request);
        } else {
            return getCurrencyRate(request);
        }
    }

    private void makeSureDateIsValid(CurrencyVM request) {
        logger.info("makeSureDateIsValid invoked");

        if(!request.isUpToDateRates()) return;

        if(request.isUpToDateRates()) {
            Date date = new Date();
            if(date.getDay() != 6 || date.getDay() != 0) {
                return;
            } else {
                request.setUpToDateRates(false);
                while(date.getDay() == 6 || date.getDay() == 0) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, -1);
                    date = calendar.getTime();
                    request.setHistoricalDate(DateUtils.getInstance().parseDateToString(date, DATE_FORMAT));
                }
            }
        }
    }

    private String defineURL(String currency, String historicalDate) {
        String url;
        url = CURRENCY_URL_PREFIX + currency + "/" + historicalDate + FORMAT_SUFFIX;
        return url;
    }

    private String validateDate(String currentDate) {
        Calendar calendar = Calendar.getInstance();
        Date currDate = null;
        try {
            currDate = DateUtils.getInstance().parseStringToDate(currentDate, DATE_FORMAT);
            calendar.setTime(currDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while(currDate.getDay() == 6 || currDate.getDay() == 0) {
            logger.info(currDate.getDay());
            calendar.add(Calendar.DATE, -1);
            currDate = calendar.getTime();
        }
        logger.info(DateUtils.getInstance().parseDateToString(currDate, DATE_FORMAT));
        return DateUtils.getInstance().parseDateToString(currDate, DATE_FORMAT);
    }

    private ArrayList<ExchangeRatesSeries> getRangeRates(CurrencyVM request) {
        logger.info("getRangeRates invoked");
        String maxDate = request.getHighHistDate();
        String currentDate = request.getLowHistDate();
        String tempWeekDate = null;
        ExchangeRatesSeries rateForCurrentDate;
        ArrayList<ExchangeRatesSeries> ratesForDateRange = new ArrayList<>();
        long howManyDays = calculateHowManyDays(currentDate, maxDate);
        parser = new XMLparserJAXB();
        String url = null;

        for(int i = 0; i <= howManyDays; i++) {
            tempWeekDate = validateDate(currentDate);
            logger.info("cur" + currentDate);
            logger.info("temp" + tempWeekDate);
            if(currentDate.compareTo(tempWeekDate) == 0) {
                url = defineURL(request.getCurrency(), currentDate);
            } else {
                url = defineURL(request.getCurrency(), tempWeekDate);
            }
            logger.info(url);

            try {
                rateForCurrentDate = parser.parseXMLtoObject(url);
                switchDate(rateForCurrentDate, currentDate);
                ratesForDateRange.add(rateForCurrentDate);
            } catch (JAXBException | IOException ex) {
                ex.printStackTrace();
                return null;
            }
            currentDate = setCurrentDate(currentDate);
        }
        return ratesForDateRange;
    }

    private void switchDate(ExchangeRatesSeries rate, String date) {
        Date parsedDate = null;
        try {
            parsedDate = DateUtils.getInstance().parseStringToDate(date, DATE_FORMAT);
            if(rate.getRates().getRate().getEffectiveDate().toString().compareTo(parsedDate.toString()) != 0) {
                GregorianCalendar gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
                gregorianCalendar.setTime(parsedDate);
                rate.getRates().getRate().setEffectiveDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                logger.info(rate.getRates().getRate().getEffectiveDate());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

    }

    private String setCurrentDate(String currentDate) {
        Calendar calendar = Calendar.getInstance();
        Date currDate = null;
            try {
                currDate = DateUtils.getInstance().parseStringToDate(currentDate, DATE_FORMAT);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendar.setTime(currDate);
            calendar.add(Calendar.DATE, 1);
            return DateUtils.getInstance().parseDateToString(calendar.getTime(), DATE_FORMAT);
    }

    private long calculateHowManyDays(String lowDate, String highDate) {
        Date minDate = null;
        Date maxDate = null;
        try {
            minDate = DateUtils.getInstance().parseStringToDate(lowDate, DATE_FORMAT);
            maxDate = DateUtils.getInstance().parseStringToDate(highDate, DATE_FORMAT);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return TimeUnit.DAYS.convert((maxDate.getTime() - minDate.getTime()), TimeUnit.MILLISECONDS);
    }

}
