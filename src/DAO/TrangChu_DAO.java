package DAO;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import DB.connectSQL;

public class TrangChu_DAO {

    // Lấy toàn bộ các chỉ số KPI động từ View CSDL
    public Map<String, Object> laySoLieuKPI() throws SQLException {
        Map<String, Object> data = new HashMap<>();
        String sql = "SELECT * FROM v_Dashboard_KPI";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                data.put("SoXeDangXuLy", rs.getInt("SoXeDangXuLy"));
                data.put("SoHoaDonThangNay", rs.getInt("SoHoaDonThangNay"));
                data.put("DoanhThuThangNay", rs.getDouble("DoanhThuThangNay"));
                data.put("SoPhuTungSapHet", rs.getInt("SoPhuTungSapHet"));
            }
        }
        return data;
    }

    // Lấy dữ liệu doanh thu 12 tháng thực tế từ Procedure
    public double[] layDoanhThu12Thang(int nam) throws SQLException {
        double[] doanhThu = new double[12];
        String sql = "{call sp_ThongKeDoanhThu12Thang(?)}";
        try (Connection conn = connectSQL.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, nam);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    int thang = rs.getInt("Thang");
                    double dt = rs.getDouble("TongDoanhThu");
                    doanhThu[thang - 1] = dt;
                }
            }
        }
        return doanhThu;
    }
}