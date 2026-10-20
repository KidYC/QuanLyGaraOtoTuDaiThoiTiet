package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.KhachHang;

public class KhachHang_DAO {

    // 1. READ: Lấy toàn bộ danh sách khách hàng
    public List<KhachHang> getAllKhachHang() throws SQLException {
        List<KhachHang> ds = new ArrayList<>();
        String sql = "SELECT maKhachHang, hoTen, soDienThoai, diaChi, email FROM KHACH_HANG";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new KhachHang(
                        rs.getString("maKhachHang"),
                        rs.getString("hoTen"),
                        rs.getString("soDienThoai"),
                        rs.getString("diaChi"),
                        rs.getString("email")
                ));
            }
        }
        return ds;
    }

    // 2. READ: Tìm khách hàng theo Mã KH
    public KhachHang getKhachHangByMa(String maKH) throws SQLException {
        String sql = "SELECT maKhachHang, hoTen, soDienThoai, diaChi, email FROM KHACH_HANG WHERE maKhachHang = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKH);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new KhachHang(
                            rs.getString("maKhachHang"),
                            rs.getString("hoTen"),
                            rs.getString("soDienThoai"),
                            rs.getString("diaChi"),
                            rs.getString("email")
                    );
                }
            }
        }
        return null;
    }

    // 3. READ: Tìm chính xác khách hàng theo Số điện thoại (Rất quan trọng cho màn hình Tiếp Nhận Xe)
    public KhachHang getKhachHangBySdt(String sdt) throws SQLException {
        String sql = "SELECT maKhachHang, hoTen, soDienThoai, diaChi, email FROM KHACH_HANG WHERE soDienThoai = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sdt);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new KhachHang(
                            rs.getString("maKhachHang"),
                            rs.getString("hoTen"),
                            rs.getString("soDienThoai"),
                            rs.getString("diaChi"),
                            rs.getString("email")
                    );
                }
            }
        }
        return null;
    }

    // 4. READ: Tìm kiếm linh hoạt theo Từ khóa (Mã KH, Họ tên hoặc Số điện thoại)
    public List<KhachHang> timKiemKhachHang(String keyword) throws SQLException {
        List<KhachHang> ds = new ArrayList<>();
        String sql = "SELECT maKhachHang, hoTen, soDienThoai, diaChi, email FROM KHACH_HANG "
                + "WHERE maKhachHang LIKE ? OR hoTen LIKE ? OR soDienThoai LIKE ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ds.add(new KhachHang(
                            rs.getString("maKhachHang"),
                            rs.getString("hoTen"),
                            rs.getString("soDienThoai"),
                            rs.getString("diaChi"),
                            rs.getString("email")
                    ));
                }
            }
        }
        return ds;
    }

    // 5. HELPER: Tự động sinh Mã khách hàng tiếp theo (KH01, KH02,...)
    public String taoMaKhachHang() throws SQLException {
        String sql = "SELECT TOP 1 maKhachHang FROM KHACH_HANG ORDER BY maKhachHang DESC";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String maCuoi = rs.getString(1);
                if (maCuoi != null && maCuoi.startsWith("KH")) {
                    int so = Integer.parseInt(maCuoi.substring(2));
                    return String.format("KH%02d", so + 1);
                }
            }
        }
        return "KH01";
    }

    // 6. CREATE: Thêm khách hàng mới
    public boolean themKhachHang(KhachHang kh) throws SQLException {
        // Tự sinh mã nếu chưa có mã
        if (kh.getMaKhachHang() == null || kh.getMaKhachHang().trim().isEmpty()) {
            kh.setMaKhachHang(taoMaKhachHang());
        }

        String sql = "INSERT INTO KHACH_HANG (maKhachHang, hoTen, soDienThoai, diaChi, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kh.getMaKhachHang());
            ps.setString(2, kh.getHoTen());
            ps.setString(3, kh.getSoDienThoai());
            ps.setString(4, kh.getDiaChi());
            ps.setString(5, kh.getEmail());
            return ps.executeUpdate() > 0;
        }
    }

    // 7. UPDATE: Cập nhật thông tin khách hàng
    public boolean capNhatKhachHang(KhachHang kh) throws SQLException {
        String sql = "UPDATE KHACH_HANG SET hoTen = ?, soDienThoai = ?, diaChi = ?, email = ? WHERE maKhachHang = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSoDienThoai());
            ps.setString(3, kh.getDiaChi());
            ps.setString(4, kh.getEmail());
            ps.setString(5, kh.getMaKhachHang());
            return ps.executeUpdate() > 0;
        }
    }

    // 8. DELETE: Xóa khách hàng theo Mã KH
    public boolean xoaKhachHang(String maKH) throws SQLException {
        String sql = "DELETE FROM KHACH_HANG WHERE maKhachHang = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKH);
            return ps.executeUpdate() > 0;
        }
    }
}