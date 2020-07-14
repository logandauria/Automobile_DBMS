
public class Individual {
    private String customerID;
    private String gender;
    private String annualIncome;



    static final int CUSTOMERID = 0;
    static final int GENDER = 1;
    static final int SALARY = 2;


    public Individual(String customerID, String gender,String annualIncome)
    {
        this.customerID = customerID;
        this.gender = gender;
        this.annualIncome = annualIncome;
    }




    public Individual(String[] data)
    {
        customerID = data[CUSTOMERID];
        gender = data[GENDER];
        annualIncome = data[SALARY];

    }

    public String getCustomerID() {
        return customerID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }
}