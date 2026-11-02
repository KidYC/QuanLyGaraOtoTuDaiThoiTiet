package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.ThuKho;

// Bảng THU_KHO(maNhanVien, hoTen, soDienThoai, diaChi, ngayVaoLam, trangThai)
public class ThuKho_DAO {

    // 1. READ: Lấy toàn bộ danh sách Thủ Kho
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

    // 2. READ: Lấy thông tin Thủ Kho theo mã nhân viên
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

    // Tìm thủ kho theo mã (dùng để kiểm tra mã nhập tay trên phiếu)
    public ThuKho timThuKhoTheoMa(String maNhanVien) throws SQLException {
        return getThuKhoByMa(maNhanVien);
    }

    // 3. UPDATE: Sửa họ tên, số điện thoại của thủ kho
    public boolean capNhatThuKho(ThuKho tk) throws SQLException {
        String sql = "UPDATE THU_KHO SET hoTen = ?, soDienThoai = ? WHERE maNhanVien = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tk.getHoTen());
            ps.setString(2, tk.getSoDienThoai());
            ps.setString(3, tk.getMaNhanVien());
            return ps.executeUpdate() > 0;
        }
    }

    // 4. Lấy danh sách phiếu sửa chữa đang chờ xuất kho
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

    // 5. Duyệt xuất kho: dùng chung 1 hàm bên PhieuSuaChua_DAO
    // (chỉ duyệt phiếu còn "Chờ xuất kho", trừ tồn kho theo CHI_TIET_PHIEU_SUA_CHUA, lỗi thì rollback)
    public boolean xacNhanXuatKho(String maPhieuSC) throws SQLException {
        return new PhieuSuaChua_DAO().xacNhanXuatKho(maPhieuSC);
    }
}
