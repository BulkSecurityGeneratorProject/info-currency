package pl.lodz.p.zzpj.controllers.vm;

public class ConverterVM {

    private String currency1;
    private String currency2;
    private String moneyValue;

    public String getCurrency1() {
        return currency1;
    }

    public void setCurrency1(String currency1) {
        this.currency1 = currency1;
    }

    public String getCurrency2() {
        return currency2;
    }

    public void setCurrency2(String currency2) {
        this.currency2 = currency2;
    }

    public String getMoneyValue() {
        return moneyValue;
    }

    public void setMoneyValue(String moneyValue) {
        this.moneyValue = moneyValue;
    }

    @Override
    public String toString() {
        return "ConverterVM{" +
                "currency1='" + currency1 + '\'' +
                ", currency2='" + currency2 + '\'' +
                ", moneyValue='" + moneyValue + '\'' +
                '}';
    }
}
