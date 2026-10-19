package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.KhachHang;

public class KhachHang_DAO {
    private String keyword;

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

    public KhachHang getKhachHangByMa(String maKH) throws SQLException {
        String sql = "SELECT * FROM KHACH_HANG WHERE maKhachHang = ?";
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
    public List<KhachHang> getKhachHangBangTen(String tenKH) throws SQLException {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KHACH_HANG WHERE hoTen = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new KhachHang
                            (rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5)));
                }
            }
            return list;
        }
    }
}