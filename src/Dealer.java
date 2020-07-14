
public class Dealer {

    static final int DEALERID =0;
    static final int DEALERSHIP =1;
    static final int ADDRESS = 2;
    static final int CITY = 3;
    static final int STATE = 4;
    static final int ZIP = 5;


    private String dealer_id;
    private String dealership;
    private String address;
    private String city;
    private String state;
    private String zip;

    public Dealer(String dealer_id, String dealership, String address, String city, String state, String zip) {
        this.dealer_id = dealer_id;
        this.dealership = dealership;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public Dealer(String[] data) {
        this.dealer_id = data[DEALERID];
        this.dealership = data[DEALERSHIP];
        this.address = data[ADDRESS];
        this.city = data[CITY];
        this.state = data[STATE];
        this.zip = data[ZIP];
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getDealership() {
        return dealership;
    }

    public void setDealership(String dealership) {
        this.dealership = dealership;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
