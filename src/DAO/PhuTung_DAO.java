
package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.PhuTung;

public class PhuTung_DAO {
    public List<PhuTung> getAllPhuTung() throws SQLException {
        List<PhuTung> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHU_TUNG";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new PhuTung(
                        rs.getString("maPhuTung"),
                        rs.getString("tenPhuTung"),
                        rs.getString("donViTinh"),
                        rs.getDouble("donGiaNhap"),
                        rs.getDouble("donGiaBan"),
                        rs.getInt("soLuongTon"),
                        rs.getString("maKho")
                ));
            }
        }
        return ds;
    }
    public boolean giamSoLuongTon(String maPhuTung, int soLuongXuat, Connection conn) throws SQLException {
        String sql = "UPDATE PHU_TUNG SET soLuongTon = soLuongTon - ? WHERE maPhuTung = ? AND soLuongTon >= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuongXuat);
            ps.setString(2, maPhuTung);
            ps.setInt(3, soLuongXuat);
            return ps.executeUpdate() > 0;
        }
    }
}