package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DB.connectSQL;
import Entity.TaiKhoan;

public class TaiKhoan_DAO {
    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) throws SQLException {
        String sql = "SELECT tk.maTaiKhoan, tk.tenDangNhap, tk.matKhau, tk.loaiTaiKhoan, tk.maNhanVien, "
                + "COALESCE(kho.hoTen, kt.hoTen, ktv.hoTen, N'Quản trị viên') AS hoTen "
                + "FROM TAI_KHOAN tk "
                + "LEFT JOIN THU_KHO kho ON tk.maNhanVien = kho.maNhanVien "
                + "LEFT JOIN KE_TOAN kt ON tk.maNhanVien = kt.maNhanVien "
                + "LEFT JOIN KY_THUAT_VIEN ktv ON tk.maNhanVien = ktv.maKTV "
                + "WHERE tk.tenDangNhap = ? AND tk.matKhau = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TaiKhoan(
                            rs.getString("maTaiKhoan"),
                            rs.getString("tenDangNhap"),
                            rs.getString("matKhau"),
                            rs.getString("loaiTaiKhoan"),
                            rs.getString("maNhanVien"),
                            rs.getString("hoTen"));
                }
            }
        }
        return null;
    }
}