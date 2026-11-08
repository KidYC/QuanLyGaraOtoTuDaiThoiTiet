package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.ChiTietBaoHanh_DTO;
import Entity.BaoHanhHienThi_DTO;
public class BaoHanh_DAO {

    public boolean daKichHoat(String maPhieu) throws SQLException {
        String sql = "SELECT COUNT(*) FROM BAO_HANH WHERE maPhieuSuaChua = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieu);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public int kichHoatBaoHanh(String maPhieu, String dieuKhoan) throws SQLException {
        String sql =
                "INSERT INTO BAO_HANH (maPhieuSuaChua, maDichVu, ngayKichHoat, ngayHetHan, dieuKhoan, trangThai) " +
                        "SELECT ct.maPhieuSuaChua, ct.maDichVu, GETDATE(), " +
                        "       DATEADD(MONTH, dv.thoiGianBaoHanh, GETDATE()), ?, N'Đang hiệu lực' " +
                        "FROM CHI_TIET_DICH_VU ct " +
                        "JOIN DICH_VU dv ON ct.maDichVu = dv.maDichVu " +
                        "WHERE ct.maPhieuSuaChua = ? AND ISNULL(dv.thoiGianBaoHanh, 0) > 0 " +
                        "AND NOT EXISTS (SELECT 1 FROM BAO_HANH b " +
                        "                WHERE b.maPhieuSuaChua = ct.maPhieuSuaChua AND b.maDichVu = ct.maDichVu)";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dieuKhoan);
            ps.setString(2, maPhieu);
            return ps.executeUpdate();
        }
    }

    public List<ChiTietBaoHanh_DTO> getChiTietBaoHanh(String maPhieu) throws SQLException {
        String sql =
                "SELECT b.maDichVu, dv.tenDichVu, dv.thoiGianBaoHanh, b.ngayHetHan " +
                        "FROM BAO_HANH b JOIN DICH_VU dv ON b.maDichVu = dv.maDichVu " +
                        "WHERE b.maPhieuSuaChua = ?";
        List<ChiTietBaoHanh_DTO> list = new ArrayList<>();
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieu);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp het = rs.getTimestamp("ngayHetHan");
                    String tt = het.before(new Timestamp(System.currentTimeMillis()))
                            ? "Hết hạn" : "Đang hiệu lực";
                    list.add(new ChiTietBaoHanh_DTO(
                            rs.getString("maDichVu"),
                            rs.getString("tenDichVu"),
                            rs.getInt("thoiGianBaoHanh"),
                            new java.util.Date(het.getTime()),
                            tt));
                }
            }
        }
        return list;
    }

    public List<ChiTietBaoHanh_DTO> getChiTietBaoHanhDuKien(String maPhieu) throws SQLException {
        String sql =
                "SELECT ct.maDichVu, dv.tenDichVu, dv.thoiGianBaoHanh, " +
                        "       DATEADD(MONTH, dv.thoiGianBaoHanh, GETDATE()) AS ngayHetHan " +
                        "FROM CHI_TIET_DICH_VU ct JOIN DICH_VU dv ON ct.maDichVu = dv.maDichVu " +
                        "WHERE ct.maPhieuSuaChua = ? AND ISNULL(dv.thoiGianBaoHanh, 0) > 0";
        List<ChiTietBaoHanh_DTO> list = new ArrayList<>();
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieu);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp het = rs.getTimestamp("ngayHetHan");
                    list.add(new ChiTietBaoHanh_DTO(
                            rs.getString("maDichVu"),
                            rs.getString("tenDichVu"),
                            rs.getInt("thoiGianBaoHanh"),
                            new java.util.Date(het.getTime()),
                            "Chưa kích hoạt"));
                }
            }
        }
        return list;
    }

    public Timestamp getNgayKichHoat(String maPhieu) throws SQLException {
        String sql = "SELECT MIN(ngayKichHoat) FROM BAO_HANH WHERE maPhieuSuaChua = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieu);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getTimestamp(1) : null;
            }
        }
    }

    public String getDieuKhoan(String maPhieu) throws SQLException {
        String sql = "SELECT TOP 1 dieuKhoan FROM BAO_HANH WHERE maPhieuSuaChua = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieu);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString(1) : null;
            }
        }
    }

    public List<BaoHanhHienThi_DTO> getDanhSachBaoHanhHienThi(String tuKhoa, String trangThai) throws SQLException {
        String sql =
                "SELECT * FROM ( " +
                        "  SELECT b.maPhieuSuaChua, k.hoTen, p.bienSo, dv.tenDichVu, b.ngayKichHoat, b.ngayHetHan, " +
                        "         CASE WHEN b.ngayHetHan < GETDATE() THEN N'Hết hạn' ELSE N'Đang hiệu lực' END AS trangThai " +
                        "  FROM BAO_HANH b " +
                        "  JOIN DICH_VU dv ON b.maDichVu = dv.maDichVu " +
                        "  JOIN PHIEU_SUA_CHUA p ON b.maPhieuSuaChua = p.maPhieuSuaChua " +
                        "  JOIN XE x ON p.bienSo = x.bienSo " +
                        "  JOIN KHACH_HANG k ON x.maKhachHang = k.maKhachHang " +
                        ") t " +
                        "WHERE (t.maPhieuSuaChua LIKE ? OR t.hoTen LIKE ? OR t.bienSo LIKE ? OR t.tenDichVu LIKE ?) " +
                        "AND (? = N'Tất cả' OR t.trangThai = ?) " +
                        "ORDER BY t.ngayKichHoat DESC";

        List<BaoHanhHienThi_DTO> list = new ArrayList<>();
        String kw = "%" + (tuKhoa == null ? "" : tuKhoa.trim()) + "%";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ps.setString(4, kw);
            ps.setString(5, trangThai);
            ps.setString(6, trangThai);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new BaoHanhHienThi_DTO(
                            rs.getString("maPhieuSuaChua"),
                            rs.getString("hoTen"),
                            rs.getString("bienSo"),
                            rs.getString("tenDichVu"),
                            rs.getTimestamp("ngayKichHoat"),
                            rs.getTimestamp("ngayHetHan"),
                            rs.getString("trangThai")));
                }
            }
        }
        return list;
    }

}