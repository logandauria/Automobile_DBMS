

public class Vehicle {
    private String vin;
    private String brand;
    private String model;
    private String color;
    private String year;
    private String dealer_cost;
    private String list_price;
    
    static final int VIN = 0;
    static final int BRAND = 1;
    static final int MODEL = 2;
    static final int COLOR = 3;
    static final int YEAR = 4;
    static final int DEALER_COST = 5;
    static final int LIST_PRICE = 6;

    
    public Vehicle(String vin, String brand, String model, String color, String year, String dealer_cost, String list_price)
    {
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.year = year;
        this.dealer_cost = dealer_cost;
        this.list_price = list_price;
    }

    /**
     * To use this constructor make sure the data in
     * the array is at the right indices
     * @param data
     */
    public Vehicle(String[] data)
    {
        vin = data[VIN];
        brand = data[BRAND];
        model = data[MODEL];
        color = data[COLOR];
        year = data[YEAR];
        dealer_cost = data[DEALER_COST];
        list_price = data[LIST_PRICE];
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setDealer_cost(String cost){
        this.dealer_cost = cost;
    }

    public String getDealer_cost() {
        return dealer_cost;
    }

    public String getList_price() {
        return list_price;
    }

    public void setList_price(String list_price) {
        this.list_price = list_price;
    }
}
