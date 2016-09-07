package pl.lodz.p.zzpj.model;

public enum Currency {
    USD("USD"),
    EUR("EUR"),
    CHF("CHF"),
    JPY("JPY"),
    AUD("AUD"),
    CAD("CAD"),
    DKK("DKK"),
    NOK("NOK"),
    SEK("SEK");

    private String name;

    Currency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
