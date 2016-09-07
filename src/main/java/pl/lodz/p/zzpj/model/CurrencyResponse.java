package pl.lodz.p.zzpj.model;

public class CurrencyResponse {
    Object data;
    int statusCode;
    double averageBid;
    double averageAsk;

    public Object getData() { return data; }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public double getAverageBid() {
        return averageBid;
    }

    public void setAverageBid(double averageBid) {
        this.averageBid = averageBid;
    }

    public double getAverageAsk() {
        return averageAsk;
    }

    public void setAverageAsk(double averageAsk) {
        this.averageAsk = averageAsk;
    }
}
