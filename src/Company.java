public class Company {

    static final int CUSTOMERID = 0;
    static final int COMPANYNAME = 1;

    private String customerId;
    private String companyName;

    public Company( String customerId, String companyName){
        this.customerId= customerId;
        this.companyName = companyName;
    }

    public Company(String[] data)
    {
        this.customerId = data[CUSTOMERID];
        this.companyName = data[COMPANYNAME];
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
