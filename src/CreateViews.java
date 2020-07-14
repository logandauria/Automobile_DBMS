import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateViews {

    public static void createCustomersView(Connection conn)throws SQLException {
        String sql = "CREATE VIEW Customer_view AS select Brand, Model, Color, year, list_price as Price, dealership, city, state from ((dealer join inventory on dealer.dealer_id = inventory.dealer_id ) join vehicle on inventory.vin = vehicle.vin) where inventory.vin not in (select vin from sold) order by dealership;";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static void createDealersView(Connection conn)throws SQLException {
        String sql = "create view dealer_view as select dealer.dealer_id, vehicle.vin, Brand, Model, Color, year, list_price as Price, dealer_cost, dealership, city, state from (((dealer join inventory on dealer.dealer_id = inventory.dealer_id ) join vehicle on inventory.vin = vehicle.vin)) order by dealership;";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static void createMarketingView(Connection conn)throws SQLException {
        String sql = "create view Marketing_view as select Brand, model, color, year, dealer_cost, list_price, (list_price- dealer_cost) as Profit,dealership,address, city, state,date from (((sale join dealer on sale.dealer_id = dealer.dealer_id) join sold on sold.sale_id = sale.sale_id) join vehicle on vehicle.vin = sold.vin) order by dealership, address;";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

}
