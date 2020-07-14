import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {

    private Connection conn;
    public Application(Connection conn){
        this.conn = conn;
    }

    private void adminQuery(Scanner scanner){
        boolean x = true;
        while(x){
            System.out.print("Would you like to make a query?\n(y/n) ");
            String temp = scanner.nextLine();
            if (temp.equals("y")){
                System.out.println("Enter query: ");
                String query = scanner.nextLine();
                ResultSet results;
                try {
                    /**
                     * Execute the query and return the result set
                     */
                    Statement stmt = conn.createStatement();
                    results = stmt.executeQuery(query);
                    printResultSet(results);
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
            else{
                x = false;
            }
        }
    }

    private void dealer_sales(Scanner scanner, int dealer_id){
        String sql = "select brand, model, year, sale.date as date_sold, " +
                "vehicle.list_price as sold_price from sale natural join sold natural " +
                "join vehicle where dealer_id = " + dealer_id;

        System.out.println("Displaying Sales.");
        StaticMethods.runAndDisplay(sql, conn);

    }

    private void dealer(Scanner scanner, VehicleLocator vehLocSer){
        boolean x = true;
        String state = "%";

        System.out.println("Enter your dealer ID: ");

        // Check if ID is contained in the dealer table?????

        boolean check_valid_id = false;
        int id = 0;

        while(!check_valid_id) {
            try {
                id = scanner.nextInt();
                Statement stmt = conn.createStatement();
                String q = "select state from dealer_view where dealer_id = " + id;
                ResultSet resultSet = stmt.executeQuery(q);
                if (resultSet.next()){
                    state = resultSet.getString(1);
                }

                if (resultSet.next()) {
                    check_valid_id = true;
                    break;
                }
            }
            catch (InputMismatchException e){
                scanner.nextLine(); // clear the buffer
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Invalid Dealer ID. Please try again: ");
        }
        scanner.nextLine();
        while(x) {
            System.out.println("Enter 1 to go to the Look up Application" +
                    "\nEnter 2 to see your Sales");
            System.out.println("Enter 0 to exit");
            int input = scanner.nextInt();
            if (input == 0){
                break;
            }
            else if (input == 1){
                vehLocSer.locate(scanner, id, state);
            }
            else if (input == 2){
                dealer_sales(scanner, id);
            }
        }
    }



    private void onlineCustomer(Scanner scanner, VehicleLocator vehLocSer){
        boolean flag = true;

        while(flag){
            System.out.println("Enter 1 to see inventory for ALL dealerships.\n" +
                    "Enter 2 To do a filtered search\n" +
                    "Enter 0 to Exit");
            int input = scanner.nextInt();
            scanner.nextLine();
            switch (input){
                case 0:
                    flag = false;
                    break;
                case 1:
                    StaticMethods.runAndDisplay("select * from customer_view", conn);
                    break;
                case 2:
                    vehLocSer.customerLocator(scanner);
                    break;

            }

//            System.out.println("Would you like to make another search? (y/n): ");
//            String temp = scanner.nextLine();
//            if(temp.isEmpty() || temp.equalsIgnoreCase("n"));
//            {
//                flag = false;
//            }
        }
    }

    private void printResultSet(ResultSet results) throws SQLException
    {
        ResultSetMetaData rsmd = results.getMetaData();

        int columnsNumber = rsmd.getColumnCount();
        while (results.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = results.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
    }

    private void marketing(Scanner scanner){
        Marketing m = new Marketing(conn);
        m.run(scanner);
    }

    public void run(){
        VehicleLocator vehLocSer = new VehicleLocator(conn);
        Scanner scanner = new Scanner(System.in);
        boolean x = true;
        while(x) {
            System.out.println("Enter 1 if you are the data admin\n" +
                    "Enter 2 if you are a dealer\n" +
                    "Enter 3 if you are an online customer\n" +
                    "Enter 4 if you are in the marketing dept\n" +
                    "Enter 0 to exit\n");
            int num = scanner.nextInt();
            scanner.nextLine();
            switch (num) {
                case 1:
                    adminQuery(scanner);
                    break;
                case 2:
                    dealer(scanner, vehLocSer);
                    break;
                case 3:
                    onlineCustomer(scanner, vehLocSer);
                    break;
                case 4:
                    marketing(scanner);
                    break;
                default:
                    x = false;
                    break;
            }
        }
    }
}
