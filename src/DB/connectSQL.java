package DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class connectSQL {
    public static void main(String[] args) {
        // 1. Khai báo thông tin (Thay đổi cho khớp với database của bạn)
        String serverName = "localhost"; // Hoặc IP nếu server ở máy khác
        String port = "1433"; // Cổng mặc định
        String databaseName = "QuanLyGaraOToTuDaiThoiTiet";
        String user = "sa"; // Tài khoản đăng nhập SQL (hoặc tài khoản bạn tự tạo)
        String password = "sapassword";

        // 2. Tạo chuỗi kết nối
        // Lưu ý: encrypt=true và trustServerCertificate=true rất quan trọng cho driver phiên bản mới
        String url = "jdbc:sqlserver://" + serverName + ":" + port + ";"
                + "databaseName=" + databaseName + ";"
                + "user=" + user + ";"
                + "password=" + password + ";"
                + "encrypt=true;"
                + "trustServerCertificate=true;";

        // 3. Thực hiện kết nối
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Chúc mừng! Đã kết nối SQL Server thành công.");

            // Bạn có thể viết code Truy vấn (SELECT, INSERT) ở đây...

        } catch (SQLException e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
        }
    }
}
