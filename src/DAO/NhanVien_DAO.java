package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.NhanVien;

public class NhanVien_DAO {

    // Lấy tất cả nhân viên từ CSDL
    public List<NhanVien> getAllNhanVien() throws SQLException {
        List<NhanVien> ds = new ArrayList<>();
        String sql = "SELECT tk.maNhanVien, "
                + "COALESCE(ktv.hoTen, kho.hoTen, kt.hoTen, N'Quản trị viên') AS hoTen, "
                + "COALESCE(ktv.diaChi, N'TP.HCM') AS soDienThoai, " // hoặc cột sdt tương ứng trong DB
                + "tk.tenDangNhap, tk.loaiTaiKhoan AS chucVu, "
                + "COALESCE(ktv.trangThai, N'Hoạt động') AS trangThai "
                + "FROM TAI_KHOAN tk "
                + "LEFT JOIN KY_THUAT_VIEN ktv ON tk.maNhanVien = ktv.maKTV "
                + "LEFT JOIN THU_KHO kho ON tk.maNhanVien = kho.maNhanVien "
                + "LEFT JOIN KE_TOAN kt ON tk.maNhanVien = kt.maNhanVien";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new NhanVien(
                        rs.getString("maNhanVien"),
                        rs.getString("hoTen"),
                        rs.getString("soDienThoai"),
                        rs.getString("tenDangNhap"),
                        rs.getString("chucVu"),
                        rs.getString("trangThai")
                ));
            }
        }
        return ds;
    }

    // Thêm nhân viên mới vào TAI_KHOAN
    public boolean themNhanVien(NhanVien nv, String matKhau) throws SQLException {
        String sql = "INSERT INTO TAI_KHOAN (maTaiKhoan, tenDangNhap, matKhau, loaiTaiKhoan, maNhanVien) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String maTK = "TK" + System.currentTimeMillis() % 1000;
            ps.setString(1, maTK);
            ps.setString(2, nv.getTenDangNhap());
            ps.setString(3, matKhau);
            ps.setString(4, nv.getChucVu());
            ps.setString(5, nv.getMaNV());
            return ps.executeUpdate() > 0;
        }
    }

    // Xóa tài khoản nhân viên
    public boolean xoaNhanVien(String maNV) throws SQLException {
        String sql = "DELETE FROM TAI_KHOAN WHERE maNhanVien = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            return ps.executeUpdate() > 0;
        }
    }
}