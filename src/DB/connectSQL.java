package DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class connectSQL {
    public static Connection getConnection() throws SQLException {
        String serverName = "localhost";
        String port = "1433";
        String databaseName = "QuanLyGaraOToTuDaiThoiTiet";
        String user = "sa";
        String password = "sapassword";
        String url = "jdbc:sqlserver://" + serverName + ":" + port + ";"
                + "databaseName=" + databaseName + ";"
                + "user=" + user + ";"
                + "password=" + password + ";"
                + "encrypt=true;"
                + "trustServerCertificate=true;";
        return DriverManager.getConnection(url);
    }
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Đã kết nối SQL Server thành công.");
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
        }
    }
}
