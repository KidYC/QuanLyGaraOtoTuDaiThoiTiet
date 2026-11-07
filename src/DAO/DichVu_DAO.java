
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
    // Thêm dịch vụ mới
    public boolean themDichVu(DichVu dv) throws SQLException {
        String sql = "INSERT INTO DICH_VU (maDichVu, tenDichVu, donGiaHienTai, moTa, thoiGianBaoHanh) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dv.getMaDichVu());
            ps.setString(2, dv.getTenDichVu());
            ps.setDouble(3, dv.getDonGiaHienTai());
            ps.setString(4, dv.getMoTa());
            ps.setInt(5, dv.getThoiGianBaoHanh());
            return ps.executeUpdate() > 0;
        }
    }

    // Cập nhật dịch vụ
    public boolean suaDichVu(DichVu dv) throws SQLException {
        String sql = "UPDATE DICH_VU SET tenDichVu = ?, donGiaHienTai = ?, moTa = ?, thoiGianBaoHanh = ? WHERE maDichVu = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dv.getTenDichVu());
            ps.setDouble(2, dv.getDonGiaHienTai());
            ps.setString(3, dv.getMoTa());
            ps.setInt(4, dv.getThoiGianBaoHanh());
            ps.setString(5, dv.getMaDichVu());
            return ps.executeUpdate() > 0;
        }
    }

    // Xóa dịch vụ
    public boolean xoaDichVu(String maDV) throws SQLException {
        String sql = "DELETE FROM DICH_VU WHERE maDichVu = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDV);
            return ps.executeUpdate() > 0;
        }
    }

    // Tự động sinh mã dịch vụ tiếp theo (Ví dụ: DV001 -> DV002)
    public String getMaDichVuTuDong() throws SQLException {
        String maMoi = "DV001";
        String sql = "SELECT TOP 1 maDichVu FROM DICH_VU ORDER BY maDichVu DESC";
        try (Connection conn = connectSQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String maCu = rs.getString(1);
                int so = Integer.parseInt(maCu.replaceAll("\\D+", "")) + 1;
                maMoi = String.format("DV%02d", so);
            }
        }
        return maMoi;
    }
}