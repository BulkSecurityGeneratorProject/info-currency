package pl.lodz.p.zzpj.model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "table",
    "currency",
    "code",
    "rates"
})
@XmlRootElement(name = "ExchangeRatesSeries")
public class ExchangeRatesSeries {

    @XmlElement(name = "Table", required = true)
    protected String table;
    @XmlElement(name = "Currency", required = true)
    protected String currency;
    @XmlElement(name = "Code", required = true)
    protected String code;
    @XmlElement(name = "Rates", required = true)
    protected Rates rates;

    public String getTable() {
        return table;
    }

    public void setTable(String value) {
        this.table = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String value) {
        this.currency = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates value) {
        this.rates = value;
    }

    @Override
    public String toString() {
        return "ExchangeRatesSeries{" +
                "table='" + table + '\'' +
                ", currency='" + currency + '\'' +
                ", code='" + code + '\'' +
                ", rates=" + rates +
                '}';
    }
}
