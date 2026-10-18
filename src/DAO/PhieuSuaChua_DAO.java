package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.PhieuSuaChua;

public class PhieuSuaChua_DAO {

    public List<PhieuSuaChua> getAllPhieuSuaChua() throws SQLException {
        List<PhieuSuaChua> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHIEU_SUA_CHUA";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new PhieuSuaChua(
                        rs.getString("maPhieuSuaChua"),
                        rs.getDate("ngayLap"),
                        rs.getDate("ngayIn"),
                        rs.getString("trangThai"),
                        rs.getString("bienSo"),
                        rs.getString("maKTV"),
                        rs.getString("maPhieuTiepNhan")
                ));
            }
        }
        return ds;
    }

    // Lập phiếu sửa chữa mới
    public boolean themPhieuSuaChua(PhieuSuaChua psc) throws SQLException {

        String sql = "INSERT INTO PHIEU_SUA_CHUA (maPhieuSuaChua, ngayLap, ngayIn, trangThai, bienSo, maKTV, maPhieuTiepNhan) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, psc.getMaPhieuSuaChua());
            ps.setDate(2, psc.getNgayLap());
            ps.setDate(3, psc.getNgayIn());
            ps.setString(4, psc.getTrangThai());
            ps.setString(5, psc.getBienSo());
            ps.setString(6, psc.getMaNhanVien());
            ps.setString(7, psc.getMaPhieuTiepNhan());
            return ps.executeUpdate() > 0;
        }
    }

    // Cập nhật trạng thái tiến độ hoặc kết quả KCS
    public boolean capNhatTrangThai(String maPhieuSC, String trangThaiMoi) throws SQLException {
        String sql = "UPDATE PHIEU_SUA_CHUA SET trangThai = ? WHERE maPhieuSuaChua = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangThaiMoi);
            ps.setString(2, maPhieuSC);
            return ps.executeUpdate() > 0;
        }
    }
}