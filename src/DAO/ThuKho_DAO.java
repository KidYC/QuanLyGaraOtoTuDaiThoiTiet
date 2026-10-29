package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.ThuKho;

public class ThuKho_DAO {

    // Lấy toàn bộ danh sách Thủ Kho
    public List<ThuKho> getAllThuKho() throws SQLException {
        List<ThuKho> ds = new ArrayList<>();
        String sql = "SELECT maNhanVien, hoTen, soDienThoai, diaChi, ngayVaoLam, trangThai FROM THU_KHO";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new ThuKho(
                        rs.getString("maNhanVien"),
                        rs.getString("hoTen"),
                        rs.getString("soDienThoai"),
                        rs.getString("diaChi"),
                        rs.getDate("ngayVaoLam"),
                        rs.getString("trangThai")
                ));
            }
        }
        return ds;
    }

    // Lấy thông tin Thủ Kho theo mã nhân viên
    public ThuKho getThuKhoByMa(String maNhanVien) throws SQLException {
        String sql = "SELECT maNhanVien, hoTen, soDienThoai, diaChi, ngayVaoLam, trangThai FROM THU_KHO WHERE maNhanVien = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ThuKho(
                            rs.getString("maNhanVien"),
                            rs.getString("hoTen"),
                            rs.getString("soDienThoai"),
                            rs.getString("diaChi"),
                            rs.getDate("ngayVaoLam"),
                            rs.getString("trangThai")
                    );
                }
            }
        }
        return null;
    }

    // 1. Lấy danh sách phiếu sửa chữa đang chờ xuất kho
    public List<Object[]> getDanhSachChoXuatKho() throws SQLException {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT psc.maPhieuSuaChua, psc.bienSo, ktv.hoTen, psc.ngayLap, psc.trangThai " +
                "FROM PHIEU_SUA_CHUA psc " +
                "JOIN KY_THUAT_VIEN ktv ON psc.maKTV = ktv.maKTV " +
                "WHERE psc.trangThai = N'Chờ xuất kho'";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getString(5)
                });
            }
        }
        return ds;
    }

    // 2. Lấy chi tiết danh sách phụ tùng cần xuất của 1 phiếu
    public List<Object[]> getChiTietPhuTungXuat(String maPhieuSC) throws SQLException {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT ct.maPhuTung, pt.tenPhuTung, pt.donViTinh, ct.soLuong, pt.soLuongTon " +
                "FROM CHI_TIET_PHIEU_SUA_CHUA ct " +
                "JOIN PHU_TUNG pt ON ct.maPhuTung = pt.maPhuTung " +
                "WHERE ct.maPhieuSuaChua = ?";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieuSC);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ds.add(new Object[]{
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4),
                            rs.getInt(5)
                    });
                }
            }
        }
        return ds;
    }

    // 3. Duyệt xuất kho: Tự động trừ tồn kho + Đổi trạng thái phiếu
    public boolean xacNhanXuatKho(String maPhieuSC) throws SQLException {
        String sqlUpdateStatus = "UPDATE PHIEU_SUA_CHUA SET trangThai = N'Đã xuất kho' WHERE maPhieuSuaChua = ?";
        String sqlTruTonKho = "UPDATE pt " +
                "SET pt.soLuongTon = pt.soLuongTon - ct.soLuong " +
                "FROM PHU_TUNG pt " +
                "JOIN CHI_TIET_PHIEU_SUA_CHUA ct ON pt.maPhuTung = ct.maPhuTung " +
                "WHERE ct.maPhieuSuaChua = ?";

        Connection conn = null;
        try {
            conn = connectSQL.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(sqlTruTonKho)) {
                ps1.setString(1, maPhieuSC);
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = conn.prepareStatement(sqlUpdateStatus)) {
                ps2.setString(1, maPhieuSC);
                ps2.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }
}