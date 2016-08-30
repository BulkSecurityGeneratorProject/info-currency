package pl.lodz.p.zzpj.model;

public enum Currency {
    USD("usd"),
    EUR("eur");

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
