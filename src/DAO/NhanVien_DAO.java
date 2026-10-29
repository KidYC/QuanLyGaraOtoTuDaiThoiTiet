package DAO;

import DB.connectSQL;
import Entity.NhanVien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVien_DAO {

    // Lấy toàn bộ danh sách nhân viên gộp từ 4 bảng actor
    public List<NhanVien> getAllNhanVien() throws SQLException {
        List<NhanVien> ds = new ArrayList<>();
        String sql = "SELECT lt.maNhanVien AS maNV, lt.hoTen, lt.soDienThoai, lt.diaChi, lt.ngayVaoLam, tk.tenDangNhap, N'Lễ tân' AS chucVu, lt.trangThai " +
                "FROM LE_TAN lt LEFT JOIN TAI_KHOAN tk ON lt.maNhanVien = tk.maNhanVien " +
                "UNION ALL " +
                "SELECT ktv.maKTV AS maNV, ktv.hoTen, ktv.soDienThoai, ktv.diaChi, ktv.ngayVaoLam, tk.tenDangNhap, N'Kỹ thuật viên' AS chucVu, ktv.trangThai " +
                "FROM KY_THUAT_VIEN ktv LEFT JOIN TAI_KHOAN tk ON ktv.maKTV = tk.maNhanVien " +
                "UNION ALL " +
                "SELECT tkho.maNhanVien AS maNV, tkho.hoTen, tkho.soDienThoai, tkho.diaChi, tkho.ngayVaoLam, tk.tenDangNhap, N'Thủ kho' AS chucVu, tkho.trangThai " +
                "FROM THU_KHO tkho LEFT JOIN TAI_KHOAN tk ON tkho.maNhanVien = tk.maNhanVien " +
                "UNION ALL " +
                "SELECT kt.maNhanVien AS maNV, kt.hoTen, kt.soDienThoai, kt.diaChi, kt.ngayVaoLam, tk.tenDangNhap, N'Kế toán' AS chucVu, kt.trangThai " +
                "FROM KE_TOAN kt LEFT JOIN TAI_KHOAN tk ON kt.maNhanVien = tk.maNhanVien";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new NhanVien(
                        rs.getString("maNV"),
                        rs.getString("hoTen"),
                        rs.getString("soDienThoai"),
                        rs.getString("diaChi"),
                        rs.getDate("ngayVaoLam"),
                        rs.getString("tenDangNhap"),
                        rs.getString("chucVu"),
                        rs.getString("trangThai")
                ));
            }
        }
        return ds;
    }

    // Hàm tự động phát sinh maTaiKhoan dạng ACC01, ACC02, ACC06...
    private String phatSinhMaTaiKhoanTudong(Connection conn) throws SQLException {
        String sql = "SELECT MAX(maTaiKhoan) FROM TAI_KHOAN WHERE maTaiKhoan LIKE 'ACC%' AND LEN(maTaiKhoan) = 5";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getString(1) != null) {
                String maxMa = rs.getString(1); // Lấy mã lớn nhất hiện tại (VD: ACC05)
                int so = Integer.parseInt(maxMa.substring(3)); // Tách lấy phần số: 5
                return String.format("ACC%02d", so + 1); // Tăng lên 1 thành ACC06
            }
        }
        return "ACC01"; // Nếu CSDL chưa có mã nào thì trả về mặc định ACC01
    }

    // Thêm nhân viên mới (Ghi đồng thời vào bảng nhân sự tương ứng và TAI_KHOAN)
    public boolean themNhanVien(NhanVien nv, String matKhau) throws SQLException {
        Connection conn = null;
        try {
            conn = connectSQL.getConnection();
            conn.setAutoCommit(false);

            String tableTarget = switch (nv.getChucVu()) {
                case "Kỹ thuật viên" -> "KY_THUAT_VIEN";
                case "Thủ kho" -> "THU_KHO";
                case "Kế toán" -> "KE_TOAN";
                default -> "LE_TAN";
            };

            String colMa = nv.getChucVu().equals("Kỹ thuật viên") ? "maKTV" : "maNhanVien";

            // 1. Thêm vào bảng Actor tương ứng
            String sqlNv = "INSERT INTO " + tableTarget + " (" + colMa + ", hoTen, soDienThoai, diaChi, ngayVaoLam, trangThai) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps1 = conn.prepareStatement(sqlNv)) {
                ps1.setString(1, nv.getMaNV());
                ps1.setString(2, nv.getHoTen());
                ps1.setString(3, nv.getSoDienThoai());
                ps1.setString(4, nv.getDiaChi());
                ps1.setDate(5, new java.sql.Date(nv.getNgayVaoLam().getTime()));
                ps1.setString(6, nv.getTrangThai());
                ps1.executeUpdate();
            }

            // 2. Thêm vào bảng TAI_KHOAN với mã tài khoản tự động sinh
            String sqlTk = "INSERT INTO TAI_KHOAN (maTaiKhoan, tenDangNhap, matKhau, loaiTaiKhoan, maNhanVien) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps2 = conn.prepareStatement(sqlTk)) {
                String maTKAuto = phatSinhMaTaiKhoanTudong(conn);
                ps2.setString(1, maTKAuto);
                ps2.setString(2, nv.getTenDangNhap());
                ps2.setString(3, matKhau);
                ps2.setString(4, nv.getChucVu());
                ps2.setString(5, nv.getMaNV());
                ps2.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException ex) {
            if (conn != null) conn.rollback();
            throw ex;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    // Cập nhật thông tin cá nhân nhân viên
    public boolean capNhatNhanVien(NhanVien nv) throws SQLException {
        String tableTarget = switch (nv.getChucVu()) {
            case "Kỹ thuật viên" -> "KY_THUAT_VIEN";
            case "Thủ kho" -> "THU_KHO";
            case "Kế toán" -> "KE_TOAN";
            default -> "LE_TAN";
        };
        String colMa = nv.getChucVu().equals("Kỹ thuật viên") ? "maKTV" : "maNhanVien";

        String sql = "UPDATE " + tableTarget + " SET hoTen = ?, soDienThoai = ?, diaChi = ?, ngayVaoLam = ? WHERE " + colMa + " = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getSoDienThoai());
            ps.setString(3, nv.getDiaChi());
            ps.setDate(4, new java.sql.Date(nv.getNgayVaoLam().getTime()));
            ps.setString(5, nv.getMaNV());
            return ps.executeUpdate() > 0;
        }
    }

    // Cập nhật phân quyền / vai trò (loaiTaiKhoan) trong bảng TAI_KHOAN
    public boolean capNhatPhanQuyen(String maNV, String loaiTaiKhoanMoi) throws SQLException {
        String sql = "UPDATE TAI_KHOAN SET loaiTaiKhoan = ? WHERE maNhanVien = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loaiTaiKhoanMoi);
            ps.setString(2, maNV);
            return ps.executeUpdate() > 0;
        }
    }

    // Xóa nhân viên (Xóa tài khoản trước, xóa thông tin nhân sự sau)
    public boolean xoaNhanVien(String maNV, String chucVu) throws SQLException {
        Connection conn = null;
        try {
            conn = connectSQL.getConnection();
            conn.setAutoCommit(false);

            String sqlTk = "DELETE FROM TAI_KHOAN WHERE maNhanVien = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlTk)) {
                ps.setString(1, maNV);
                ps.executeUpdate();
            }

            String tableTarget = switch (chucVu) {
                case "Kỹ thuật viên" -> "KY_THUAT_VIEN";
                case "Thủ kho" -> "THU_KHO";
                case "Kế toán" -> "KE_TOAN";
                default -> "LE_TAN";
            };
            String colMa = chucVu.equals("Kỹ thuật viên") ? "maKTV" : "maNhanVien";

            String sqlNv = "DELETE FROM " + tableTarget + " WHERE " + colMa + " = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlNv)) {
                ps.setString(1, maNV);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException ex) {
            if (conn != null) conn.rollback();
            throw ex;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }
}