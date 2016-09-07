package pl.lodz.p.zzpj.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"rate"})
public class Rates {

    @XmlElement(name = "Rate", required = true)
    protected Rate rate;

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate value) {
        this.rate = value;
    }

    @Override
    public String toString() {
        return "Rates{" +
                "rate=" + rate +
                '}';
    }
}