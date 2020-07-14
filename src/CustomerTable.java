/**
 * @author: Anthony Cianfrocco
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerTable {
    public static void populateCustomerTableFromCsv(Connection conn,
                                                    String fileName)
            throws SQLException {
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Customer> customers = new ArrayList<Customer>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine();//flushing names
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                customers.add(new Customer(split));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Creates the SQL query to do a bulk add of all people
         * that were read in. This is more efficent then adding one
         * at a time
         */
        String sql = createCustomerInsertSQL(customers);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    /**
     * Create the customer table with the given attributes
     *
     * @param conn: the database connection to work with
     */
    public static void createCustomerTable(Connection conn) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS customer("
                    + "ID INT PRIMARY KEY,"
                    + "FIRST_NAME VARCHAR(255),"
                    + "LAST_NAME VARCHAR(255),"
                    + "STREET VARCHAR (255),"
                    + "CITY VARCHAR (255),"
                    + "STATE VARCHAR (255),"
                    + "ZIPCODE VARCHAR (255)"
                    + ");";

            /**
             * Create a query and execute
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a single customer to the database
     *
     * @param conn
     * @param id
     * @param fName
     * @param lName
     * @param street
     * @param city
     * @param state
     * @param zip
     */
    public static void addCustomer(Connection conn,
                                   String id,
                                   String fName,
                                   String lName,
                                   String street,
                                   String city,
                                   String state,
                                   String zip) {

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO customer "
                        + "VALUES(\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\');",
                id, fName, lName, street, city, state, zip);
        try {
            /**
             * create and execute the query
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This creates an sql statement to do a bulk add of people
     *
     * @param customers: list of Customer objects to add
     * @return
     */
    public static String createCustomerInsertSQL(ArrayList<Customer> customers) {
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO customer (id, FIRST_NAME, LAST_NAME, STREET, CITY, STATE, ZIPCODE) VALUES");

        /**
         * For each customer append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last customer add a comma to seperate
         *
         * If it is the last person add a semi-colon to end the statement
         */
        for (int i = 0; i < customers.size(); i++) {
            Customer p = customers.get(i);
            sb.append(String.format("(\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\')",
                    p.getId(), p.getFirstName(), p.getLastName(), p.getStreet(), p.getCity(), p.getState(),
                    p.getZipcode()));
            if (i != customers.size() - 1) {
                sb.append(",");
            } else {
                sb.append(";");
            }
        }
        return sb.toString();
    }

    /**
     * Makes a query to the person table
     * with given columns and conditions
     *
     * @param conn
     * @param columns:      columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static ResultSet queryCustomerTable(Connection conn,
                                             ArrayList<String> columns,
                                             ArrayList<String> whereClauses) {
        StringBuilder sb = new StringBuilder();

        /**
         * Start the select query
         */
        sb.append("SELECT ");

        /**
         * If we gave no columns just give them all to us
         *
         * other wise add the columns to the query
         * adding a comma top seperate
         */
        if (columns.isEmpty()) {
            sb.append("* ");
        } else {
            for (int i = 0; i < columns.size(); i++) {
                if (i != columns.size() - 1) {
                    sb.append(columns.get(i) + ", ");
                } else {
                    sb.append(columns.get(i) + " ");
                }
            }
        }

        /**
         * Tells it which table to get the data from
         */
        sb.append("FROM customer ");

        /**
         * If we gave it conditions append them
         * place an AND between them
         */
        if (!whereClauses.isEmpty()) {
            sb.append("WHERE ");
            for (int i = 0; i < whereClauses.size(); i++) {
                if (i != whereClauses.size() - 1) {
                    sb.append(whereClauses.get(i) + " AND ");
                } else {
                    sb.append(whereClauses.get(i));
                }
            }
        }

        /**
         * close with semi-colon
         */
        sb.append(";");

        //Print it out to verify it made it right
        System.out.println("Query: " + sb.toString());
        try {
            /**
             * Execute the query and return the result set
             */
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Queries and print the table
     *
     * @param conn
     */
    public static void printCustomerTable(Connection conn) {
        String query = "SELECT * FROM customer;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {
                System.out.printf("Customer %d: %s %s %s %s %s %s\n",
                        result.getInt(1),
                        result.getString(2),
                        result.getString(4),
                        result.getString(3),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

