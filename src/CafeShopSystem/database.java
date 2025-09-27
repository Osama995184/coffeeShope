package CafeShopSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
//        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:cstoredb.db";
            conn = DriverManager.getConnection(url);
            System.out.println("✅ Connected to SQLite database");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e);
        }
        return conn;
    }

    public static void closeAllConnections() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("✅ Connection closed successfully.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing connection: " + e.getMessage());
        }
    }
}
