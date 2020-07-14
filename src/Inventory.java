
public class Inventory {

    private String dealer_id;
    private String vin;

    public Inventory(String vin, String inventory_id) {
        this.dealer_id = inventory_id;
        this.vin = vin;
    }

    public Inventory(String[] data) {
        this.dealer_id = data[1];
        this.vin = data[0];
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
