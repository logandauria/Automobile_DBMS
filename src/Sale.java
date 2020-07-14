
public class Sale {

    static final int SALEID = 0;
    static final int DATE = 1;
    static final int DEALERID = 2;
    static final int CUSTOMERID = 3;

    private String sale_id;
    private String date;
    private String dealer_id;
    private String customer_id;

    public Sale(String sale_id, String date, String vin, String dealer_id, String customer_id) {
        this.sale_id = sale_id;
        this.date = date;
        this.dealer_id = dealer_id;
        this.customer_id = customer_id;
    }

    public Sale(String[] data) {
        this.sale_id = data[SALEID];
        this.date = data[DATE];
        this.dealer_id = data[DEALERID];
        this.customer_id = data[CUSTOMERID];
    }

    public String getSale_id() {
        return sale_id;
    }

    public void setSale_id(String sale_id) {
        this.sale_id = sale_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}
