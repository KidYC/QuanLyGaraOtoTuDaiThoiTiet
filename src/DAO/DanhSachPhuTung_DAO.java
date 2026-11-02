package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    public List<DanhSachPhuTung> getDSPhuTungByMaPhieu(String maPhieuSC) throws SQLException {
        List<DanhSachPhuTung> list = new ArrayList<>();
        String sql = "SELECT * FROM DANH_SACH_PHU_TUNG WHERE maPhieuSuaChua = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieuSC);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new DanhSachPhuTung(
                            rs.getString("maPhieuSuaChua"),
                            rs.getString("maPhuTung"),
                            rs.getInt("soLuongPhuTung"),
                            rs.getDouble("donGiaBan")
                    ));
                }
            }
        }
        return list;
    }

    public List<Object[]> getDSPhuTungBaoGiaByMaPhieu(String maPhieuSC) throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT ds.maPhuTung, pt.tenPhuTung, ds.soLuongPhuTung, ds.donGiaBan " +
                "FROM DANH_SACH_PHU_TUNG ds " +
                "JOIN PHU_TUNG pt ON ds.maPhuTung = pt.maPhuTung " +
                "WHERE ds.maPhieuSuaChua = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieuSC);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{
                            rs.getString("maPhuTung"),
                            rs.getString("tenPhuTung"),
                            rs.getInt("soLuongPhuTung"),
                            rs.getDouble("donGiaBan")
                    });
                }
            }
        }
        return list;
    }
}