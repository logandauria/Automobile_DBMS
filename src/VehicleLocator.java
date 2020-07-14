import java.sql.*;
import java.util.Scanner;

public class VehicleLocator {

    private Connection conn;

    public VehicleLocator(Connection conn){
        this.conn = conn;
    }

    public void locate(Scanner scanner, int dealer_id, String state) {
        boolean x = true;
        while (x) {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from dealer_view where dealer_id = " +
                    + dealer_id);

            System.out.println("Enter 1 to see all inventory for this dealer (sold and unsold)\n" +
                    "Enter 2 to filter your search\n" +
                     "Enter 0 to return");
            int op = scanner.nextInt();
            scanner.nextLine();
            if (op == 1){
                // query built already will do this, no change needed
            }
            else if (op == 2){

                StaticMethods.displayFilterOptionsDealer(sb, scanner);

            }
            else{
                x = false;
                break;
            }
            sb.append(" order by brand, model, color, year, dealer_cost"); // 49 chars
//            System.out.println(sb);

            StaticMethods.runAndDisplay(sb.toString(),conn);

            System.out.println("Would you like to see your search results for nearby dealers? (y/n): ");
            String check = scanner.nextLine();
            if (check.equals("y")){
                // get state of the local dealer
                System.out.println("Displaying vehicles from current dealer and any dealer in the same state.");

                String sql_nearby = "select * from dealer_view where state = \'" + state + "\'";

                sb.delete(sb.toString().length() - 48, sb.toString().length());
                // assuming no dealer_id greater than 99
                String temp;
                if (dealer_id < 10){
                     temp = sb.toString().substring(45, sb.toString().length());
                }
                else{
                     temp = sb.toString().substring(46, sb.toString().length());
                }

//                System.out.println("Query: " + sb.toString());
                sb.append(" UNION " + sql_nearby + temp + " order by dealer_id");

                //select * from dealer_view where dealer_id = 11 AND BRAND = 'Nissan' union select * from dealer_view where state = 'California' order by dealer_id;

                sql_nearby = sb.toString();



                StaticMethods.runAndDisplay(sql_nearby, conn);
            }

            System.out.println("Would you like to do another look up? (y/n): ");
            check = scanner.nextLine();
            if (check.equals("n")){
                x = false;
            }
        }
    }

    public void customerLocator(Scanner scanner)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from customer_view where price != 0");
        StaticMethods.displayFilterOptions(sb, scanner);
//        System.out.println(sb.toString());
        StaticMethods.runAndDisplay(sb.toString(), conn);
    }




    /*
    The vehicle locator service needs a lookup application to check
    inventory both locally and at nearby dealers. This service allows
    a dealer to find a vehicle match the desires of a potential customer.
    Marketing may want to review these inquiries to do future product planning.
     */
}
