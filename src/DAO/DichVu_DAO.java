
package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.DichVu;

public class DichVu_DAO {
    public List<DichVu> getAllDichVu() throws SQLException {
        List<DichVu> ds = new ArrayList<>();
        String sql = "SELECT * FROM DICH_VU";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new DichVu(
                        rs.getString("maDichVu"),
                        rs.getString("tenDichVu"),
                        rs.getDouble("donGiaHienTai"),
                        rs.getString("moTa"),
                        rs.getInt("thoiGianBaoHanh")
                ));
            }
        }
        return ds;
    }
}