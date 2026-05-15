import java.sql.*;

public class DatabaseService {
    private static final String URL = "jdbc:mysql://localhost:3306/dev_db";
    private static final String USER = "root";
    private static final String PASS = "your_password";

    // Helper method to get a connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // CREATE / UPDATE / DELETE (Anything that doesn't return rows)
    public static void executeUpdate(String sql, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Update Error: " + e.getMessage());
        }
    }

    // READ (Retrieves data)
    public static void executeQuery(String sql, QueryHandler handler, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                handler.process(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Query Error: " + e.getMessage());
        }
    }

    // Simple interface to handle the results
    public interface QueryHandler {
        void process(ResultSet rs) throws SQLException;
    }
}