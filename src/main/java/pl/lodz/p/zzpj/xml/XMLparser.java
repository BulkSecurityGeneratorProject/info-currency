package pl.lodz.p.zzpj.xml;

import pl.lodz.p.zzpj.model.Currency;
import pl.lodz.p.zzpj.model.ExchangeRatesSeries;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface XMLparser {
    ExchangeRatesSeries parseXMLtoObject(Currency currency) throws JAXBException, IOException;
    String parseObjectToXML(ExchangeRatesSeries exchangeRatesSeries) throws JAXBException;
}
