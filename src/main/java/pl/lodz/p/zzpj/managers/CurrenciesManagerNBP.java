package pl.lodz.p.zzpj.managers;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.zzpj.domain.util.Search;
import pl.lodz.p.zzpj.model.*;
import pl.lodz.p.zzpj.repository.SearchRepository;
import pl.lodz.p.zzpj.utils.DateUtil;
import pl.lodz.p.zzpj.vm.CurrencyVM;
import pl.lodz.p.zzpj.xml.XMLparser;
import pl.lodz.p.zzpj.xml.XMLparserJAXB;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

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
    private List<String> notWorkingDates;

    private XMLparser parser;

    private SearchRepository repository;

    private SearchManager searchManager;

    @Autowired
    public CurrenciesManagerNBP(SearchRepository repository) {
        this.repository = repository;
        searchManager = new SearchManager(repository);
        setUpNotWorkingDates();
    }

    private void setUpNotWorkingDates() {
        notWorkingDates = new ArrayList<>();
        notWorkingDates.add("2016-01-01");
        notWorkingDates.add("2016-01-06");
        notWorkingDates.add("2016-03-25");
        notWorkingDates.add("2016-03-28");
        notWorkingDates.add("2016-05-03");
        notWorkingDates.add("2016-05-26");
        notWorkingDates.add("2016-08-15");
        notWorkingDates.add("2016-11-01");
        notWorkingDates.add("2016-11-11");
        notWorkingDates.add("2016-12-26");
        notWorkingDates.add("2017-01-06");
        notWorkingDates.add("2017-04-14");
        notWorkingDates.add("2017-04-17");
        notWorkingDates.add("2017-05-01");
        notWorkingDates.add("2017-05-03");
        notWorkingDates.add("2017-06-15");
        notWorkingDates.add("2017-08-15");
        notWorkingDates.add("2017-11-01");
        notWorkingDates.add("2017-12-25");
        notWorkingDates.add("2017-12-26");
    }

    @Override
    public String getLastCurrenciesXMLFromWebsite() {
        logger.info("getLastCurrenciesXMLFromWebsite invoked");
        String content = "";
        URL url;
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
    public ExchangeRatesSeries getCurrencyRate(CurrencyVM request) {
        logger.info("getCurrencyRate invoked" );
        parser = new XMLparserJAXB();
        String url;

        if(request.isUpToDateRates() && (request.getCurrency() != null)) {
            makeSureDateIsValid(request);
            url = defineURL(request.getCurrency(), request.getHistoricalDate());
        } else if(request.getHistoricalDate() != null && (request.getCurrency() != null)) {
            url = defineURL(request.getCurrency(), validateDate(request.getHistoricalDate()));
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
    public CurrencyResponse getRatesDependsOnParams(CurrencyVM request, Long userId) {
        logger.info("getRatesDependsOnParams invoked" );
        CurrencyResponse currencyResponse = new CurrencyResponse();
        searchManager.saveSearchHistoryItem(new Search(request, userId));
        if(request.getLowHistDate() != null && request.getHighHistDate() != null) {
            ArrayList<ExchangeRatesSeries> rangeRates = getRangeRates(request);
            currencyResponse.setData(rangeRates);
            currencyResponse.setAverageAsk(getAverageRate(rangeRates, "ask"));
            currencyResponse.setAverageBid(getAverageRate(rangeRates, "bid"));
        } else {
            currencyResponse.setData(getCurrencyRate(request));
        }
        return currencyResponse;
    }

    private double getAverageRate(List<ExchangeRatesSeries> rangeRates, String type) {
        Function<ExchangeRatesSeries, BigDecimal> function;
        if(type.equals("ask")) {
            function = p -> p.getRates().getRate().getAsk();
        } else {
            function = p -> p.getRates().getRate().getBid();
        }
        return rangeRates.parallelStream()
                .map(function)
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .getAsDouble();
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
                    request.setHistoricalDate(DateUtil.getInstance().parseDateToString(date, DATE_FORMAT));
                }
            }
        }
    }

    private String defineURL(String currency, String historicalDate) {
        String url;
        if(historicalDate == null) {
            url = CURRENCY_URL_PREFIX + currency + FORMAT_SUFFIX;
        } else {
            url = CURRENCY_URL_PREFIX + currency + "/" + historicalDate + FORMAT_SUFFIX;
        }
        return url;
    }

    private String validateDate(String currentDate) {
        logger.info("validateDate invoked");
        Calendar calendar = Calendar.getInstance();
        Date currDate = null;
        boolean isValid = false;
        try {
            currDate = DateUtil.getInstance().parseStringToDate(currentDate, DATE_FORMAT);
            calendar.setTime(currDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while(!isValid) {
            if(notWorkingDates.contains(currentDate)) {
                calendar.add(Calendar.DATE, -1);
                currDate = calendar.getTime();
            }
            while(currDate.getDay() == 6 || currDate.getDay() == 0) {
                calendar.add(Calendar.DATE, -1);
                currDate = calendar.getTime();
                currentDate = DateUtil.getInstance().parseDateToString(currDate, DATE_FORMAT);
            }
            if(!notWorkingDates.contains(DateUtil.getInstance().parseDateToString(currDate, DATE_FORMAT))) {
                isValid = true;
            } else {
                isValid = false;
            }
        }

        return DateUtil.getInstance().parseDateToString(currDate, DATE_FORMAT);
    }

    @Override
    public ArrayList<ExchangeRatesSeries> getRangeRates(CurrencyVM request) {
        logger.info("getRangeRates invoked");
        String currentDate = request.getLowHistDate();
        String tempWeekDate;
        ExchangeRatesSeries rateForCurrentDate;
        ArrayList<ExchangeRatesSeries> ratesForDateRange = new ArrayList<>();
        long howManyDays = calculateHowManyDays(currentDate, request.getHighHistDate());
        parser = new XMLparserJAXB();
        String url;

        for(int i = 0; i <= howManyDays; i++) {
            tempWeekDate = validateDate(currentDate);
            if(currentDate.compareTo(tempWeekDate) == 0) {
                url = defineURL(request.getCurrency(), currentDate);
            } else {
                url = defineURL(request.getCurrency(), tempWeekDate);
            }

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
        Date parsedDate;
        try {
            parsedDate = DateUtil.getInstance().parseStringToDate(date, DATE_FORMAT);
            if(rate.getRates().getRate().getEffectiveDate().compareTo(parsedDate.toString()) != 0) {
                GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
                gc.setTime(parsedDate);
                rate.getRates().getRate().setEffectiveDate(DatatypeFactory.newInstance()
                        .newXMLGregorianCalendarDate(gc.get(Calendar.YEAR),
                                                 gc.get(Calendar.MONTH) + 1,
                                                 gc.get(Calendar.DAY_OF_MONTH),
                                                 DatatypeConstants.FIELD_UNDEFINED));
            }
        } catch (ParseException | DatatypeConfigurationException e) {
            e.printStackTrace();
        }

    }

    private String setCurrentDate(String currentDate) {
        Calendar calendar = Calendar.getInstance();
        Date currDate = null;
            try {
                currDate = DateUtil.getInstance().parseStringToDate(currentDate, DATE_FORMAT);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendar.setTime(currDate);
            calendar.add(Calendar.DATE, 1);
            return DateUtil.getInstance().parseDateToString(calendar.getTime(), DATE_FORMAT);
    }

    private long calculateHowManyDays(String lowDate, String highDate) {
        Date minDate = null;
        Date maxDate = null;
        try {
            minDate = DateUtil.getInstance().parseStringToDate(lowDate, DATE_FORMAT);
            maxDate = DateUtil.getInstance().parseStringToDate(highDate, DATE_FORMAT);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return TimeUnit.DAYS.convert((maxDate.getTime() - minDate.getTime()), TimeUnit.MILLISECONDS);
    }

}
