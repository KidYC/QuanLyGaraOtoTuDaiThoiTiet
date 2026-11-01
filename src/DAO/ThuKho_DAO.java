package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.ThuKho;

// Bảng THU_KHO(maNhanVien, hoTen, soDienThoai, tenDangNhap, matKhau)
// Không đọc cột matKhau ra ngoài; đăng nhập dùng TaiKhoan_DAO.
public class ThuKho_DAO {

    // 1. READ: Lấy danh sách thủ kho
    public List<ThuKho> getAllThuKho() throws SQLException {
        List<ThuKho> ds = new ArrayList<>();
        String sql = "SELECT maNhanVien, hoTen, soDienThoai FROM THU_KHO";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new ThuKho(rs.getString("maNhanVien"), rs.getString("hoTen"), rs.getString("soDienThoai")));
            }
        }
        return ds;
    }

    // 2. READ: Tìm thủ kho theo mã nhân viên (dùng để kiểm tra mã nhập tay trên phiếu)
    public ThuKho timThuKhoTheoMa(String maNhanVien) throws SQLException {
        String sql = "SELECT maNhanVien, hoTen, soDienThoai FROM THU_KHO WHERE maNhanVien = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ThuKho(rs.getString("maNhanVien"), rs.getString("hoTen"), rs.getString("soDienThoai"));
                }
            }
        }
        return null;
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
}
