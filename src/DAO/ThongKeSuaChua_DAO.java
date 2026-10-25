package DAO;

import Entity.ThongKeSuaChua;
import DB.connectSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThongKeSuaChua_DAO {

    // Lấy toàn bộ danh sách thống kê sửa chữa
    public List<ThongKeSuaChua> layDanhSachThongKe() throws SQLException {
        List<ThongKeSuaChua> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM v_ThongKeSuaChua";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ThongKeSuaChua tk = new ThongKeSuaChua(
                        rs.getString("maPhieuSuaChua"),
                        rs.getDate("ngayLap"),
                        rs.getString("maKTV"),
                        rs.getString("trangThai"), // Đã sửa từ tinhTrang thành trangThai ở đây
                        rs.getInt("tongSoPhuTungDung"),
                        rs.getDouble("tongTienVatTu")
                );
                danhSach.add(tk);
            }
        }
        return danhSach;
    }

    // Lấy thống kê sửa chữa theo mã kỹ thuật viên cụ thể
    public List<ThongKeSuaChua> layThongKeTheoKTV(String maKTV) throws SQLException {
        List<ThongKeSuaChua> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM v_ThongKeSuaChua WHERE maKTV = ?";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maKTV);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThongKeSuaChua tk = new ThongKeSuaChua(
                            rs.getString("maPhieuSuaChua"),
                            rs.getDate("ngayLap"),
                            rs.getString("maKTV"),
                            rs.getString("tinhTrang"),
                            rs.getInt("tongSoPhuTungDung"),
                            rs.getDouble("tongTienVatTu")
                    );
                    danhSach.add(tk);
                }
            }
        }
        return danhSach;
    }
}