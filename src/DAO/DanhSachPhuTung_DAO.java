
package DAO;

import java.sql.*;
import DB.connectSQL;
import Entity.DanhSachPhuTung;

public class DanhSachPhuTung_DAO {
    public boolean themDanhSachPhuTung(DanhSachPhuTung dspt) throws SQLException {
        String sql = "INSERT INTO DANH_SACH_PHU_TUNG (maPhieuSuaChua, maPhuTung, soLuongPhuTung, donGiaBan) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dspt.getMaPhieuSuaChua());
            ps.setString(2, dspt.getMaPhuTung());
            ps.setInt(3, dspt.getSoLuongPhuTung());
            ps.setDouble(4, dspt.getDonGiaBan());
            return ps.executeUpdate() > 0;
        }
    }
}