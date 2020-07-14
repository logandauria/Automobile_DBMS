import java.sql.*;
import java.util.Scanner;

public class StaticMethods {
    public static void searchByBrand(StringBuilder sb, Scanner scanner){
        String temp="";
        System.out.println("Enter all brands you would like to find or nothing to see all, each seperated by a comma:\n" +
                "(i.e. chevrolet, audi, etc)");
        while((temp = scanner.nextLine()).isEmpty());
        if (!temp.isEmpty()){
            String [] brands = temp.split(",");
            if (brands.length > 1){
                sb.append(" AND brand in (");
                buildInString(sb, brands);
                sb.append(")");
            }

            else {
                sb.append(" AND brand like ");
                sb.append("'%" + temp + "%'");
            }
        }
    }

    public static void searchBySoldVehicles(StringBuilder sb, Scanner scanner){
        String temp="";
        System.out.println("Would you like to see sold cars in your search? (y/n): ");
        while((temp = scanner.nextLine()).isEmpty());
        if (!temp.isEmpty()){
            if (temp.equals("y")){

            }
            else {
                sb.append(" AND brand like ");
                sb.append("'%" + temp + "%'");
            }
        }
    }

    public static void displayFilterOptions(StringBuilder sb, Scanner scanner)
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
                    "Enter 8: To filter Price\n" +
                    "Enter 9: To get ordered result\n" +
                    "Enter 0: To return results\n\n"
            );
            int filter = scanner.nextInt();
            scanner.nextLine();

            switch (filter) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    searchByDealer(sb, scanner);
                    break;
                case 2:
                    searchByCity(sb, scanner);
                    break;
                case 3:
                    searchByState(sb, scanner);
                    break;
                case 4:
                    searchByBrand(sb, scanner);
                    break;
                case 5:
                    searchByModel(sb, scanner);
                    break;
                case 6:
                    searchByColor(sb, scanner);
                    break;
                case 7:
                    searchByYear(sb, scanner);
                    break;
                case 8:
                    searchByPriceRange(sb, scanner);
                    break;
                case 9:
                    orderBY(sb, scanner);
                    flag = false;
                    break;
            }
        }
    }

    public static void displayFilterOptionsDealer(StringBuilder sb, Scanner scanner)
    {
        boolean flag = true;
        while(flag) {
            System.out.println("\nEnter 1: To filter Brand\n" +
                    "Enter 2: To filter Model\n" +
                    "Enter 3: To filter Color\n" +
                    "Enter 4: To filter Year\n" +
                    "Enter 5: To filter Price\n" +
                    "Enter 0: To return results\n\n"
            );
            int filter = scanner.nextInt();
            scanner.nextLine();

            switch (filter) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    searchByBrand(sb, scanner);
                    break;
                case 2:
                    searchByModel(sb, scanner);
                    break;
                case 3:
                    searchByColor(sb, scanner);
                    break;
                case 4:
                    searchByYear(sb, scanner);
                    break;
                case 5:
                    searchByPriceRange(sb, scanner);
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
        System.out.println("Do you want to see only unsold vehicles?");
        String temp = scanner.nextLine();
        if (temp.equals("y")){
            sb.append(" except(select dealer_id, dealer_view.vin, brand, model," +
                    " color, year, price, dealer_cost, dealership, city, state" +
                    " from dealer_view natural join sold)");
        }
    }

    public static void searchByDealer(StringBuilder sb, Scanner scanner){
        String temp="";
        System.out.println("Enter all dealers you would like to find or nothing to see all, each seperated by a comma:\n" +
                "(i.e. Corkery LLC, Conroy-Hirthe, Koepp-Brekke, etc");
        while((temp = scanner.nextLine()).isEmpty());
        if (!temp.isEmpty()){
            String [] dealership = temp.split(",");
            if (dealership.length > 1){
                sb.append(" AND dealership in (");
                buildInString(sb, dealership);
                sb.append(")");
            }
            else {
                sb.append(" AND dealership like ");
                sb.append("'%" + temp + "%'");
            }
        }
    }

    public static void searchByModel(StringBuilder sb, Scanner scanner)
    {
        String temp ="";
        System.out.println("Enter all models you would like to find or nothing to see all, each seperated by a comma\n" +
                "i.e.: edge, camry, etc");
        while((temp = scanner.nextLine()).isEmpty());
        if (!temp.isEmpty()){
            String [] models = temp.split(",");
            if (models.length > 1){
                sb.append(" AND model in (");
                buildInString(sb, models);
                sb.append(")");
            }
            else {
                sb.append(" AND model like ");
                sb.append("'%" + temp + "%'");
            }
        }
    }

    public static void searchByColor(StringBuilder sb, Scanner scanner)
    {
        String temp ="";
        System.out.println("Enter all colors you would like to find or nothing to see all, each seperated by a comma\n" +
                "i.e.: white, blue, etc");
        while((temp = scanner.nextLine()).isEmpty());
        if (!temp.isEmpty()){
            String [] colors = temp.split(",");
            if (colors.length > 1){
                sb.append(" AND color in (");
                buildInString(sb, colors);

                sb.append(")");
            }
            else {
                sb.append(" AND color like ");
                sb.append("'%" + temp + "%'");
            }
        }
    }

    public static void searchByState(StringBuilder sb, Scanner scanner)
    {
        String temp ="";
        System.out.println("Enter all States you would like to find or nothing to see all, each seperated by a comma\n" +
                "i.e.: Louisiana, California,Virginia, etc");
        while((temp = scanner.nextLine()).isEmpty());
        if (!temp.isEmpty()){
            String [] states = temp.split(",");
            if (states.length > 1){
                sb.append(" AND state in (");
                buildInString(sb, states);

                sb.append(")");
            }
            else {
                sb.append(" AND state like ");
                sb.append("'%" + temp + "%'");
            }
        }
    }

    public static void searchByCity(StringBuilder sb, Scanner scanner)
    {
        String temp ="";
        System.out.println("Enter all cities you would like to find or nothing to see all, each seperated by a comma\n" +
                "i.e.: Baton, Fresno,Richmond, etc");
        while((temp = scanner.nextLine()).isEmpty());
        if (!temp.isEmpty()){
            String [] cities = temp.split(",");
            if (cities.length > 1){
                sb.append(" AND city in (");
                buildInString(sb, cities);

                sb.append(")");
            }
            else {
                sb.append(" AND city like ");
                sb.append("'%" + temp + "%'");
            }
        }
    }

    public static void searchByPriceRange(StringBuilder sb, Scanner scanner)
    {
        String temp ="";
        System.out.println("Enter the range of number, each seperated by a comma\n" +
                "i.e.: 100, 10000, etc");

        while((temp = scanner.nextLine()).isEmpty());

        if (!temp.isEmpty()){
            String [] range = temp.split(",");
            if (range.length ==2){
                sb.append(" AND price between "+ range[0] + " and " + range[1].trim());
            }

        }
    }

    public static void searchByDealerCostRange(StringBuilder sb, Scanner scanner)
    {
        String temp ="";
        System.out.println("Enter the range of number, each seperated by a comma\n" +
                "i.e.: 100, 10000, etc");

        while((temp = scanner.nextLine()).isEmpty());

        if (!temp.isEmpty()){
            String [] range = temp.split(",");
            if (range.length ==2){
                sb.append(" AND dealer_cost between "+ range[0] + " and " + range[1]);
            }

        }
    }

    public static void searchByProfitRange(StringBuilder sb, Scanner scanner)
    {
        String temp ="";
        System.out.println("Enter the range of number, each seperated by a comma\n" +
                "i.e.: 100, 10000, etc");

        while((temp = scanner.nextLine()).isEmpty());

        if (!temp.isEmpty()){
            String [] range = temp.split(",");
            if (range.length ==2){
                sb.append(" AND profit between "+ range[0] + " and " + range[1]);
            }

        }
    }

    public static void searchByListPriceRange(StringBuilder sb, Scanner scanner)
    {
        String temp ="";
        System.out.println("Enter the range of number, each seperated by a comma\n" +
                "i.e.: 100, 10000, etc");

        while((temp = scanner.nextLine()).isEmpty());

        if (!temp.isEmpty()){
            String [] range = temp.split(",");
            if (range.length ==2){
                sb.append(" AND list_price between "+ range[0] + " and " + range[1]);
            }

        }
    }




    public static void searchByYear(StringBuilder sb, Scanner scanner)
    {
        String temp="";
        System.out.println("Enter all years you would like to find or nothing to see all, each seperated by a comma\n" +
                "i.e.: 2011, 2009, etc");
        while((temp = scanner.nextLine()).isEmpty());
        if (!temp.isEmpty()){
            String [] years = temp.split(",");
            if (years.length > 1){
                sb.append(" AND year in (");
                buildInString(sb, years);
                sb.append(")");
            }
            else {
                sb.append(" AND year like ");
                sb.append("'" + temp + "'");
            }
        }
    }

    public static void searchByDateRange(StringBuilder sb, Scanner scanner)
    {
        String temp ="";
        System.out.println("Enter the range of date, each seperated by a comma\n" +
                "i.e.: 2018-02-01, 2019-12-23");

        while((temp = scanner.nextLine()).isEmpty());

        if (!temp.isEmpty()){
            String [] range = temp.split(",");
            if (range.length ==2){
                sb.append(" AND date between \'"+ range[0].trim() + "\' and \'" + range[1].trim() + "\'");
            }

        }
    }

    public static void orderBY(StringBuilder sb, Scanner scanner)
    {
        System.out.println("Enter the what you want to order by seperated by a comma\n i.e: price, year, etc");
        String orderByList;
        while((orderByList = scanner.nextLine()).isEmpty());

        sb.append(" order by ("+ orderByList + ")");
    }


    public static void buildInString(StringBuilder sb, String[] values)
    {
        for (int i = 0; i < values.length; i ++) {
            String val = values[i].trim();
            if (i == values.length - 1) {
                sb.append("'" + val + "'");
            }
            else {
                sb.append("'" + val + "', ");
            }
        }
    }

    public static void runAndDisplay(String q, Connection conn){
        try {
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(q);
            ResultSetMetaData rsmd = results.getMetaData();

            System.out.println("Displaying: ");
            int columnsNumber = rsmd.getColumnCount();
            for(int x = 1; x <= columnsNumber; x++){
                System.out.print(rsmd.getColumnName(x));
                for(int y = 0; y < 14 - rsmd.getColumnName(x).length(); y++){
                    System.out.print(" ");
                }
            }
            System.out.println();
            while (results.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = results.getString(i);
                    System.out.printf("%.140s", columnValue);
                    for(int y = 0; y < 14 - columnValue.length(); y++){
                        System.out.print(" ");
                    }
                }
                System.out.println("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
