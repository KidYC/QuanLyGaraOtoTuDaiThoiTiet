package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.KhachHang;

public class KhachHang_DAO {
    public List<KhachHang> getAllKhachHang() throws SQLException {
        List<KhachHang> ds = new ArrayList<>();
        String sql = "SELECT * FROM KHACH_HANG";
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
}