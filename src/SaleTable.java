import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SaleTable {
    /**
     * Reads a cvs file for data and adds them to the sale table
     *
     * Does not create the table. It must already be created
     *
     * @param conn: database connection to work with
     * @param fileName
     * @throws SQLException
     */
    public static void populateSaleTableFromCSV(Connection conn,
                                                  String fileName)
            throws SQLException{
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Sale> sales = new ArrayList<Sale>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine(); // flushing out the column names
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                sales.add(new Sale(split));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Creates the SQL query to do a bulk add of all sales
         * that were read in. This is more efficent then adding one
         * at a time
         */
        String sql = createSaleInsertSQL(sales);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    /**
     * Create the sale table with the given attributes
     *
     * @param conn: the database connection to work with
     */
    public static void createSaleTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS sale("
                    + "SALE_ID INT PRIMARY KEY,"
                    + "DATE VARCHAR(10),"
                    + "DEALER_ID INT,"
                    + "CUSTOMER_ID INT"
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
     * Adds a single sale to the database
     */
    public static void addSale(Connection conn,
                               String sale_id,
                               String date,
                               String dealer_id,
                               String customer_id){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO sale "
                        + "VALUES(\'%s\',\'%s\',\'%s\', \'%s\');",
                sale_id, date, dealer_id, customer_id);
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
     * This creates an sql statement to do a bulk add of sales
     */
    public static String createSaleInsertSQL(ArrayList<Sale> sales){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO sale (SALE_ID, DATE, DEALER_ID, CUSTOMER_ID) VALUES");

        /**
         * For each sale append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last sale add a comma to seperate
         *
         * If it is the last sale add a semi-colon to end the statement
         */
        for(int i = 0; i < sales.size(); i++){
            Sale p = sales.get(i);
            sb.append(String.format("(\'%s\',\'%s\',\'%s\',\'%s\')",
                    p.getSale_id(), p.getDate(), p.getDealer_id(), p.getCustomer_id()));
            if( i != sales.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }
        return sb.toString();
    }

    /**
     * Makes a query to the sale table
     * with given columns and conditions
     *
     * @param conn
     * @param columns: columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static ResultSet querySaleTable(Connection conn,
                                             ArrayList<String> columns,
                                             ArrayList<String> whereClauses){
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
        if(columns.isEmpty()){
            sb.append("* ");
        }
        else{
            for(int i = 0; i < columns.size(); i++){
                if(i != columns.size() - 1){
                    sb.append(columns.get(i) + ", ");
                }
                else{
                    sb.append(columns.get(i) + " ");
                }
            }
        }

        /**
         * Tells it which table to get the data from
         */
        sb.append("FROM sale ");

        /**
         * If we gave it conditions append them
         * place an AND between them
         */
        if(!whereClauses.isEmpty()){
            sb.append("WHERE ");
            for(int i = 0; i < whereClauses.size(); i++){
                if(i != whereClauses.size() -1){
                    sb.append(whereClauses.get(i) + " AND ");
                }
                else{
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
     * @param conn
     */
    public static void printSaleTable(Connection conn){
        String query = "SELECT * FROM sale;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("Sale %s: %s %s %s \n",
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
