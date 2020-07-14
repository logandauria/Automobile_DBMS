import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Marketing {

    private Connection conn;

    public Marketing(Connection conn) {
        this.conn = conn;
    }

    /**
     * Runs the marketing query system. Provides multiple options/suboptions for the
     * user to select depending on what information they wish to see
     * @param scanner: input scanner
     */
    public void run(Scanner scanner) {
        boolean flag = true;
        while(flag) {
            System.out.println("\nEnter 1 to do a filtered sales search\n" +
                    "Enter 2 to see sales info by dealer\n" +
                    "Enter 3 to see sales info by time range\n" +
                    "Enter 4 to see sales info by location\n" +
                    "Enter 0 to exit");
            int input;
            int info = scanner.nextInt();
            scanner.nextLine();
            switch (info) {
                case 0:
                    flag = false;
                    break;
                case 2:
                    System.out.println("\nEnter 1 to view specific dealers\n" +
                            "Enter 2 to see top performing dealers\n" +
                            "Enter 3 to see worst performing dealers");
                    input = scanner.nextInt();
                    scanner.nextLine();
                    if (input == 1) {
                        System.out.println("Here are the available dealers:\n");
                        execQuery("select dealership, address, state, city, zip from dealer");
                        System.out.println("\nEnter all dealership names you would like to see, each separated by a comma:\n" +
                                "(i.e. Corkery LLC, Muller Group, etc");
                        StringBuilder sb = new StringBuilder();
                        sb.append("select dealership, count(list_price) as Total_Sales, sum(profit) " +
                                "as Total_Profit from marketing_view group by dealership having dealership in (");
                        sb.append(getList(scanner));
                        sb.append(")");
                        execQuery(sb.toString());
                    } else if (input == 2) {
                        System.out.println("\nEnter 1 to compare by total profit\n" +
                                "Enter 2 to compare by total sales");
                        input = scanner.nextInt();
                        scanner.nextLine();
                        if (input == 1)
                            execQuery("select dealership, count(list_price) as Total_Sales, sum(profit) " +
                                    "as Total_Profit from marketing_view group by dealership order by sum(profit) desc");
                        else
                            execQuery("select dealership, count(list_price) as Total_Sales, sum(profit) " +
                                    "as Total_Profit from marketing_view group by dealership order by count(list_price) desc");
                    } else {
                        System.out.println("Enter 1 to compare by total profit\n" +
                                "Enter 2 to compare by total sales");
                        input = scanner.nextInt();
                        scanner.nextLine();
                        if (input == 1)
                            execQuery("select dealership, count(list_price) as Total_Sales, sum(profit) " +
                                    "as Total_Profit from marketing_view group by dealership order by sum(profit)");
                        else
                            execQuery("select dealership, count(list_price) as Total_Sales, sum(profit) " +
                                    "as Total_Profit from marketing_view group by dealership order by count(list_price)");
                    }
                    break;
                case 1:
                    StringBuilder sb = new StringBuilder();
                    sb.append("select * from marketing_view where year != 0");
                    displayFilterOptions(sb, scanner);
                    execQuery(sb.toString());
                    break;
                case 3:
                    System.out.println("Enter start date(yyyy-mm-dd): ");
                    String date1 = scanner.next();
                    scanner.nextLine();
                    System.out.println("Enter end date(yyyy-mm-dd): ");
                    String date2 = scanner.next();
                    execQuery("select date, count(list_price) as Total_Sales, sum(profit) " +
                            "as Total_Profit from marketing_view where date between \'" +
                            date1 + "\' and \'" + date2 + "\' group by date order by date");
                    break;
                case 4:
                    System.out.println("\nEnter 1 to look by state\n" +
                            "Enter 2 to look by city");
                    input = scanner.nextInt();
                    scanner.nextLine();
                    if (input == 1) {
                        System.out.println("\nEnter 1 to look at all states\n" +
                                "Enter 2 to look up a state");
                        input = scanner.nextInt();
                        scanner.nextLine();
                        if (input == 1) {
                            String[] s = {"state", "sum(profit)", "count(list_price)"};
                            System.out.println("\nEnter 1 to sort in alphabetical order\n" +
                                    "Enter 2 to sort by profit\n" +
                                    "Enter 3 to sort by sales");
                            input = scanner.nextInt();
                            scanner.nextLine();
                            execQuery("select state, count(list_price) as Total_Sales, sum(profit) " +
                                    "as Total_Profit from marketing_view group by state order by " + s[input - 1]);
                        } else {
                            System.out.println("\nEnter states, each separated by a comma: ");
                            execQuery("select state, count(list_price) as Total_Sales, sum(profit) " +
                                    "as Total_Profit from marketing_view group by state having state in (" +
                                    getList(scanner) + ")");
                        }
                    } else {
                        System.out.println("\nEnter 1 to look at all cities\n" +
                                "Enter 2 to look up a city");
                        input = scanner.nextInt();
                        scanner.nextLine();
                        if (input == 1) {
                            String[] s = {"city", "sum(profit)", "count(list_price)"};
                            System.out.println("\nEnter 1 to sort in alphabetical order\n" +
                                    "Enter 2 to sort by profit\n" +
                                    "Enter 3 to sort by sales");
                            input = scanner.nextInt();
                            scanner.nextLine();
                            execQuery("select city, count(list_price) as Total_Sales, sum(profit) " +
                                    "as Total_Profit from marketing_view group by city order by " + s[input - 1]);
                        } else {
                            System.out.println("\nEnter cities, each separated by a comma: ");
                            execQuery("select city, count(list_price) as Total_Sales, sum(profit) " +
                                    "as Total_Profit from marketing_view group by city having city in (" +
                                    getList(scanner) + ")");
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Builds a string based on selected filter options selected by the user. Uses methods in the
     * StaticMethods class to convert the desired filters into SQL
     * @param sb:
     * @param scanner:
     */
    private void displayFilterOptions(StringBuilder sb, Scanner scanner)
    {
        boolean flag = true;
        while(flag) {
            System.out.println("\nEnter 1: To filter dealerships\n" +
                    "Enter 2: To filter City\n" +
                    "Enter 3: To filter State\n" +
                    "Enter 4: To filter Brand\n" +
                    "Enter 5: To filter Model\n" +
                    "Enter 6: To filter Color\n" +
                    "Enter 7: To filter Year\n" +
                    "Enter 8: To filter Dealer Cost\n" +
                    "Enter 9: To filter Price\n" +
                    "Enter 10: To filter Profit\n" +
                    "Enter 11: To filter Date Range\n" +
                    "Enter 12: To get Ordered Results\n" +
                    "Enter 0: To return results\n\n"
            );
            int filter = scanner.nextInt();

            switch (filter) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    StaticMethods.searchByDealer(sb, scanner);
                    break;
                case 2:
                    StaticMethods.searchByCity(sb, scanner);
                    break;
                case 3:
                    StaticMethods.searchByState(sb, scanner);
                    break;
                case 4:
                    StaticMethods.searchByBrand(sb, scanner);
                    break;
                case 5:
                    StaticMethods.searchByModel(sb, scanner);
                    break;
                case 6:
                    StaticMethods.searchByColor(sb, scanner);
                    break;
                case 7:
                    StaticMethods.searchByYear(sb, scanner);
                    break;
                case 8:
                    StaticMethods.searchByDealerCostRange(sb, scanner);
                    break;
                case 9:
                    StaticMethods.searchByListPriceRange(sb, scanner);
                    break;
                case 10:
                    StaticMethods.searchByProfitRange(sb, scanner);
                    break;
                case 11:
                    StaticMethods.searchByDateRange(sb, scanner);
                    break;
                case 12:
                    StaticMethods.orderBY(sb, scanner);
                    flag = false;
                    break;
            }
        }
    }


    /**
     * Executes a query and prints the results of it into a formatted table
     * Entries can fit into a max of 30 characters, as that is how much spacing there is
     * @param q: input query that the function executes
     */
    public void execQuery(String q){
        try {
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(q);
            ResultSetMetaData rsmd = results.getMetaData();

            System.out.println("Displaying: ");
            int columnsNumber = rsmd.getColumnCount();
            for(int x = 1; x <= columnsNumber; x++){
                System.out.print(rsmd.getColumnName(x));
                for(int y = 0; y < 20 - rsmd.getColumnName(x).length(); y++){
                    System.out.print(" ");
                }
            }
            System.out.println();
            while (results.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = results.getString(i);
                    System.out.printf("%.20s", columnValue);
                    for(int y = 0; y < 20 - columnValue.length(); y++){
                        System.out.print(" ");
                    }
                }
                System.out.println("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets input from user and turns it into a list of strings for SQL
     * @param scanner: input scanner
     * @return: a list of strings based on input
     */
    public StringBuilder getList(Scanner scanner) {
        StringBuilder sb = new StringBuilder();
        String temp = scanner.nextLine();
        if (!temp.isEmpty()) {
            String[] strings = temp.split(",");
            for (int i = 0; i < strings.length; i++) {
                String b = strings[i].trim();
                if (i == strings.length - 1) {
                    sb.append("'" + b + "'");
                } else {
                    sb.append("'" + b + "', ");
                }
            }
        }
        return sb;
    }
}