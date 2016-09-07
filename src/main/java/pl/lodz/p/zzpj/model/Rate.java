package pl.lodz.p.zzpj.model;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "no",
        "effectiveDate",
        "bid",
        "ask"
})
public class Rate {

    @XmlElement(name = "No", required = true)
    protected String no;
    @XmlElement(name = "EffectiveDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveDate;
    @XmlElement(name = "Bid", required = true)
    protected BigDecimal bid;
    @XmlElement(name = "Ask", required = true)
    protected BigDecimal ask;

    public String getNo() {
        return no;
    }

    public void setNo(String value) {
        this.no = value;
    }

    public String getEffectiveDate() {
        return effectiveDate.toString();
    }

    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public BigDecimal getMid() {
        return bid.add(ask).divide(new BigDecimal(2));
    }

    @Override
    public String toString() {
        return "Rate{" +
                "no='" + no + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", bid=" + bid +
                ", ask=" + ask +
                '}';
    }
}