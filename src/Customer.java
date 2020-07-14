public class Customer {
    static final int ID = 0;
    static final int FNAME = 1;
    static final int LNAME = 2;
    static final int STREET = 3;
    static final int CITY = 4;
    static final int STATE = 5;
    static final int ZIPCODE = 6;
    static final int PHONE = 7;

    private String id;
    private String first_name;
    private String last_name;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String phone;

    public Customer()
    {}



    public Customer(String id, String first, String last, String street, String city, String state,
                    String zipcode, String phone)
    {
        this.id = id;
        this.first_name = first;
        this.last_name = last;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.phone = phone;
    }

    public Customer(String[] data)
    {
        id = data[ID];
        first_name = data[FNAME];
        last_name = data[LNAME];
        street = data[STREET];
        city = data[CITY];
        state = data[STATE];
        zipcode = data[ZIPCODE];
        phone = data[PHONE];
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName(){
        return last_name;
    }

    public void setName(String first, String last) {
        this.first_name = first;
        this.last_name = last;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet(){
        return this.street;
    }

    public String getCity() {
        return city;
    }

    public String getState(){
        return this.state;
    }

    public String getZipcode(){
        return this.zipcode;
    }

    public void setAddress(String street, String city, String state,
                           String zipcode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
