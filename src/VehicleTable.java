import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VehicleTable {
    /**
     * Reads a cvs file for data and adds them to the vehicle table
     *
     * Does not create the table. It must already be created
     *
     * @param conn: database connection to work with
     * @param fileName
     * @throws SQLException
     */
    public static void populateVehicleTableFromCSV(Connection conn,
                                                  String fileName)
            throws SQLException{
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine();// flushing the column names
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                vehicles.add(new Vehicle(split));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Creates the SQL query to do a bulk add of all vehicles
         * that were read in. This is more efficent then adding one
         * at a time
         */
        String sql = createVehicleInsertSQL(vehicles);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    /**
     * Create the vehicle table with the given attributes
     *
     * @param conn: the database connection to work with
     */
    public static void createVehicleTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS vehicle("
                    + "VIN VARCHAR(255) PRIMARY KEY,"
                    + "BRAND VARCHAR(255),"
                    + "MODEL VARCHAR(255),"
                    + "COLOR VARCHAR(255),"
                    + "YEAR VARCHAR(4),"
                    + "DEALER_COST NUMERIC(7, 2),"
                    + "LIST_PRICE NUMERIC(7, 2)"
                    + ");" ;

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
     * Adds a single vehicle to the database
     *
     */
    public static void addVehicle(Connection conn,
                                  String vin,
                                  String brand,
                                  String model,
                                  String color,
                                  String year,
                                  String dealer_cost,
                                  String list_price){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO vehicle "
                        + "VALUES(\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\');",
                vin, brand, model, color, year, dealer_cost, list_price);
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
     * This creates an sql statement to do a bulk add of vehicles
     *
     * @param vehicles: list of Vehicle objects to add
     *
     * @return
     */
    public static String createVehicleInsertSQL(ArrayList<Vehicle> vehicles){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO vehicle (VIN, BRAND, MODEL, COLOR, YEAR, DEALER_COST, LIST_PRICE) VALUES");

        /**
         * For each vehicle append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last vehicle add a comma to seperate
         *
         * If it is the last vehicle add a semi-colon to end the statement
         */
        for(int i = 0; i < vehicles.size(); i++){
            Vehicle p = vehicles.get(i);
            sb.append(String.format("(\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s',\'%s\')",
                    p.getVin(), p.getBrand(), p.getModel(), p.getColor(), p.getYear(), p.getDealer_cost(), p.getList_price()));
            if( i != vehicles.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }
        return sb.toString();
    }

    /**
     * Makes a query to the vehicle table
     * with given columns and conditions
     *
     * @param conn
     * @param columns: columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static ResultSet queryVehicleTable(Connection conn,
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
        sb.append("FROM vehicle ");

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
    public static void printVehicleTable(Connection conn){
        String query = "SELECT * FROM vehicle;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("Vehicle %s: %s %s %s %s %s %s\n",
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
