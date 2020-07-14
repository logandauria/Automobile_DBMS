public class Sold {

    static final int VIN = 0;
    static final int SALE_ID = 1;

    private String vin;
    private String sale_id;

    public Sold(String vin, String sale_id) {
        this.vin = vin;
        this.sale_id = sale_id;
    }

    public Sold(String[] data) {
        this.vin = data[VIN];
        this.sale_id = data[SALE_ID];
    }

    public static int getVIN() {
        return VIN;
    }

    public static int getSaleId() {
        return SALE_ID;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getSale_id() {
        return sale_id;
    }

    public void setSale_id(String sale_id) {
        this.sale_id = sale_id;
    }
}

