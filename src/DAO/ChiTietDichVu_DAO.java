
package DAO;

import java.sql.*;
import DB.connectSQL;
import Entity.ChiTietDichVu;

public class ChiTietDichVu_DAO {
    public boolean themChiTietDichVu(ChiTietDichVu ct) throws SQLException {
        String sql = "INSERT INTO CHI_TIET_DICH_VU (maPhieuSuaChua, maDichVu, soLuong, donGia) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ct.getMaPhieuSuaChua());
            ps.setString(2, ct.getMaDichVu());
            ps.setInt(3, ct.getSoLuong());
            ps.setDouble(4, ct.getDonGia());
            return ps.executeUpdate() > 0;
        }
    }
}