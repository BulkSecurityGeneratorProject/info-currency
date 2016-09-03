package pl.lodz.p.zzpj.xml;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import pl.lodz.p.zzpj.model.Currency;
import pl.lodz.p.zzpj.model.ExchangeRatesSeries;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class XMLparserJAXB implements XMLparser {
    Logger logger = Logger.getLogger(XMLparserJAXB.class);

    private JAXBContext jaxbContext;
    private Unmarshaller unmarshaller;
    private Marshaller marshaller;
    StringWriter stringWriter;

    private static final String CURRENCY_URL_PREFIX = "http://api.nbp.pl/api/exchangerates/rates/c/";
    private static final String CURRENCY_DAILY_SUFFIX = "/today?format=xml";

    @Override
    public ExchangeRatesSeries parseXMLtoObject(String url) throws JAXBException, IOException {
        jaxbContext = JAXBContext.newInstance(ExchangeRatesSeries.class);
        unmarshaller = jaxbContext.createUnmarshaller();
        ExchangeRatesSeries unmarshal = (ExchangeRatesSeries) unmarshaller.unmarshal(new URL(url));
        return unmarshal;
    }

    @Override
    public String parseObjectToXML(ExchangeRatesSeries exchangeRatesSeries) throws JAXBException {
        jaxbContext = JAXBContext.newInstance(ExchangeRatesSeries.class);
        stringWriter = new StringWriter();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(exchangeRatesSeries, stringWriter);
        return stringWriter.toString();
    }

    private String buildURI(String symbol) {
        return CURRENCY_URL_PREFIX + symbol + CURRENCY_DAILY_SUFFIX;
    }

}
