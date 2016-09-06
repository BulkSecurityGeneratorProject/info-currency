package pl.lodz.p.zzpj.domain.util;

import pl.lodz.p.zzpj.controllers.vm.CurrencyVM;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by piotr on 05.09.16.
 */
@Entity
@Table(name = "country")
public class Search implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "currency")
    private String currency;

    @Column(name = "up_to_date_rates")
    private boolean upToDateRates;

    @Column(name = "historical_date")
    private String historicalDate;

    @Column(name = "low_hist_date")
    private String lowHistDate;

    @Column(name = "high_hist_date")
    private String highHistDate;

    public Search(CurrencyVM request, int userId) {
        this.userId = userId;
        this.currency = request.getCurrency();
        this.upToDateRates = request.isUpToDateRates();
        this.historicalDate = request.getHistoricalDate();
        this.lowHistDate = request.getLowHistDate();
        this.highHistDate = request.getHighHistDate();
    }

    public Search() {}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isUpToDateRates() {
        return upToDateRates;
    }

    public void setUpToDateRates(boolean upToDateRates) {
        this.upToDateRates = upToDateRates;
    }

    public String getHistoricalDate() {
        return historicalDate;
    }

    public void setHistoricalDate(String historicalDate) {
        this.historicalDate = historicalDate;
    }

    public String getLowHistDate() {
        return lowHistDate;
    }

    public void setLowHistDate(String lowHistDate) {
        this.lowHistDate = lowHistDate;
    }

    public String getHighHistDate() {
        return highHistDate;
    }

    public void setHighHistDate(String highHistDate) {
        this.highHistDate = highHistDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Search search = (Search) o;

        if (upToDateRates != search.upToDateRates) return false;
        if (id != null ? !id.equals(search.id) : search.id != null) return false;
        if (currency != null ? !currency.equals(search.currency) : search.currency != null) return false;
        if (historicalDate != null ? !historicalDate.equals(search.historicalDate) : search.historicalDate != null)
            return false;
        if (lowHistDate != null ? !lowHistDate.equals(search.lowHistDate) : search.lowHistDate != null) return false;
        return highHistDate != null ? highHistDate.equals(search.highHistDate) : search.highHistDate == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (upToDateRates ? 1 : 0);
        result = 31 * result + (historicalDate != null ? historicalDate.hashCode() : 0);
        result = 31 * result + (lowHistDate != null ? lowHistDate.hashCode() : 0);
        result = 31 * result + (highHistDate != null ? highHistDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Search{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", upToDateRates=" + upToDateRates +
                ", historicalDate='" + historicalDate + '\'' +
                ", lowHistDate='" + lowHistDate + '\'' +
                ", highHistDate='" + highHistDate + '\'' +
                '}';
    }
}
