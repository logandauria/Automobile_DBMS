import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AutoMain {


    final static String LOCATION = "./Database/AutoDb";
    final static String USER = "me";
    final static String PASSWORD = "password";
    final static String COMPANYCSV = "./Datafiles/company-table.csv";
    final static String CUSTOMERCSV = "./Datafiles/customer-table.csv";
    final static String DEALERCSV = "./Datafiles/dealer-table.csv";
    final static String INDIVIDUALCSV = "./Datafiles/individual-table.csv";
    final static String INVENTORYCSV = "./Datafiles/inventory-table.csv";
    final static String SALECSV = "./Datafiles/sales-table.csv";
    final static String SOLDCSV = "./Datafiles/sold-table.csv";
    final static String VEHICLECSV = "./Datafiles/vehicle-table.csv";




    // The connection to the database
    private Connection conn;

    /**
     * Create a database connection with the given params
     * @param location: path of where to place the database
     * @param user: user name for the owner of the database
     * @param password: password of the database owner
     */
    public void createConnection(String location,
                                 String user,
                                 String password){
        try {

            //This needs to be on the front of your location
            String url = "jdbc:h2:" + location;

            //This tells it to use the h2 driver
            Class.forName("org.h2.Driver");

            //creates the connection
            conn = DriverManager.getConnection(url,
                    user,
                    password);
        } catch (SQLException | ClassNotFoundException e) {
            //You should handle this better
            e.printStackTrace();
        }
    }

    /**
     * just returns the connection
     * @return: returns class level connection
     */
    public Connection getConnection(){
        return conn;
    }

    /**
     * When your database program exits
     * you should close the connection
     */
    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Starts and runs the database
     * @param args: not used but you can use them
     */
    public static void main(String[] args) throws IOException {

        AutoMain database = new AutoMain();

        // This just creates a database and establishes a connection with it
        database.createConnection(LOCATION, USER, PASSWORD);


        File tempFile = new File("./Database/AutoDb/AutoDb.mv.db");
        System.out.println("Enter 0 if the H2 database object hasn't been created yet.\n Enter 1 otherwise");
        Scanner s = new Scanner(System.in);
        int exists = s.nextInt();
        boolean check = false;
        if (exists == 0)  check = true;

        if(check) {
            // creating tables
            CompanyTable.createCompanyTable(database.getConnection());
            CustomerTable.createCustomerTable(database.getConnection());
            DealerTable.createDealerTable(database.getConnection());
            IndividualTable.createIndividualTable(database.getConnection());
            InventoryTable.createInventoryTable(database.getConnection());
            SaleTable.createSaleTable(database.getConnection());
            SoldTable.createSoldTable(database.getConnection());
            VehicleTable.createVehicleTable(database.getConnection());

            try {
                //populating tables
                CompanyTable.populateCompanyTableFromCsv(database.getConnection(), COMPANYCSV);
                CustomerTable.populateCustomerTableFromCsv(database.getConnection(), CUSTOMERCSV);
                DealerTable.populateDealerTableFromCSV(database.getConnection(), DEALERCSV);
                IndividualTable.populateIndividualTableFromCSV(database.getConnection(), INDIVIDUALCSV);
                InventoryTable.populateInventoryTableFromCSV(database.getConnection(), INVENTORYCSV);
                SaleTable.populateSaleTableFromCSV(database.getConnection(), SALECSV);
                SoldTable.populateSoldTableFromCSV(database.getConnection(), SOLDCSV);
                VehicleTable.populateVehicleTableFromCSV(database.getConnection(), VEHICLECSV);

                CreateViews.createDealersView(database.getConnection());
                CreateViews.createCustomersView(database.getConnection());
                CreateViews.createMarketingView(database.getConnection());
            }
            catch(SQLIntegrityConstraintViolationException e)
            {
                System.out.println("Data is already populated");
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Application app = new Application(database.conn);
        app.run();
    }
}
