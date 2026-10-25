package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.DanhSachPhuTung;
import Entity.PhieuSuaChua;

public class PhieuSuaChua_DAO {

    public List<PhieuSuaChua> getAllPhieuSuaChua() throws SQLException {
        List<PhieuSuaChua> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHIEU_SUA_CHUA";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new PhieuSuaChua(
                        rs.getString("maPhieuSuaChua"),
                        rs.getDate("ngayLap"),
                        rs.getDate("ngayIn"),
                        rs.getString("trangThai"),
                        rs.getString("bienSo"),
                        rs.getString("maKTV"),
                        rs.getString("maPhieuTiepNhan")
                ));
            }
        }
        return ds;
    }

    // Lập phiếu sửa chữa mới (Tự động thêm maKTV + hoTen nếu tài khoản chưa có trong KY_THUAT_VIEN)
    public boolean themPhieuSuaChua(PhieuSuaChua psc) throws SQLException {
        // ĐÃ SỬA: Bổ sung cột hoTen = N'Kỹ thuật viên / Quản lý' để tránh lỗi NOT NULL
        String sqlCheckKTV = "IF NOT EXISTS (SELECT 1 FROM KY_THUAT_VIEN WHERE maKTV = ?) " +
                "INSERT INTO KY_THUAT_VIEN (maKTV, hoTen) VALUES (?, N'Kỹ thuật viên / Quản lý')";

        String sqlInsertPSC = "INSERT INTO PHIEU_SUA_CHUA (maPhieuSuaChua, ngayLap, ngayIn, trangThai, bienSo, maKTV, maPhieuTiepNhan) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connectSQL.getConnection()) {
            // Bước 1: Tự động đăng ký mã KTV/Admin nếu chưa có trong bảng KY_THUAT_VIEN
            try (PreparedStatement psKTV = conn.prepareStatement(sqlCheckKTV)) {
                psKTV.setString(1, psc.getMaNhanVien());
                psKTV.setString(2, psc.getMaNhanVien());
                psKTV.executeUpdate();
            }

            // Bước 2: Thêm Phiếu sửa chữa vào CSDL
            try (PreparedStatement ps = conn.prepareStatement(sqlInsertPSC)) {
                ps.setString(1, psc.getMaPhieuSuaChua());
                ps.setDate(2, psc.getNgayLap());
                ps.setDate(3, psc.getNgayIn());
                ps.setString(4, psc.getTrangThai());
                ps.setString(5, psc.getBienSo());
                ps.setString(6, psc.getMaNhanVien());
                ps.setString(7, psc.getMaPhieuTiepNhan());
                return ps.executeUpdate() > 0;
            }
        }
    }

    // Cập nhật trạng thái tiến độ hoặc kết quả KCS
    public boolean capNhatTrangThai(String maPhieuSC, String trangThaiMoi) throws SQLException {
        String sql = "UPDATE PHIEU_SUA_CHUA SET trangThai = ? WHERE maPhieuSuaChua = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangThaiMoi);
            ps.setString(2, maPhieuSC);
            return ps.executeUpdate() > 0;
        }
    }

    // Lấy danh sách phiếu đang ở trạng thái "Chờ xuất kho" cho Thủ kho xem
    public List<PhieuSuaChua> getDSPhieuChoXuatKho() throws SQLException {
        List<PhieuSuaChua> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHIEU_SUA_CHUA WHERE trangThai = N'Chờ xuất kho' ORDER BY ngayLap DESC";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new PhieuSuaChua(
                        rs.getString("maPhieuSuaChua"),
                        rs.getDate("ngayLap"),
                        rs.getDate("ngayIn"),
                        rs.getString("trangThai"),
                        rs.getString("bienSo"),
                        rs.getString("maKTV"),
                        rs.getString("maPhieuTiepNhan")
                ));
            }
        }
        return ds;
    }

    // Thủ kho bấm duyệt xuất kho -> Trừ số lượng tồn kho phụ tùng và đổi trạng thái phiếu sang "Đang sửa chữa"
    public boolean xacNhanXuatKho(String maPhieuSC) throws SQLException {
        Connection conn = connectSQL.getConnection();
        DanhSachPhuTung_DAO dsPhuTungDAO = new DanhSachPhuTung_DAO();
        PhuTung_DAO phuTungDAO = new PhuTung_DAO();

        try {
            conn.setAutoCommit(false); // Bắt đầu Transaction

            List<DanhSachPhuTung> dsPT = dsPhuTungDAO.getDSPhuTungByMaPhieu(maPhieuSC);
            for (DanhSachPhuTung pt : dsPT) {
                boolean checkTon = phuTungDAO.giamSoLuongTon(pt.getMaPhuTung(), pt.getSoLuongPhuTung(), conn);
                if (!checkTon) {
                    conn.rollback();
                    return false;
                }
            }

            String sqlUpdate = "UPDATE PHIEU_SUA_CHUA SET trangThai = N'Đang sửa chữa' WHERE maPhieuSuaChua = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setString(1, maPhieuSC);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}