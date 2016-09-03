package pl.lodz.p.zzpj.xml;

import pl.lodz.p.zzpj.model.ExchangeRatesSeries;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URL;

public interface XMLparser {
    ExchangeRatesSeries parseXMLtoObject(String url) throws JAXBException, IOException;
    String parseObjectToXML(ExchangeRatesSeries exchangeRatesSeries) throws JAXBException;
}
