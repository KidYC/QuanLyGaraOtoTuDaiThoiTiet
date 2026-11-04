package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.KhachHang;
import Entity.Xe;

public class Xe_DAO {

    public List<Xe> getAllXe() throws SQLException {
        List<Xe> ds = new ArrayList<>();
        String sql = "SELECT * FROM XE";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Khởi tạo đối tượng KhachHang và gán mã KH từ kết quả truy vấn
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));

                ds.add(new Xe(
                        rs.getString("bienSo"),
                        rs.getString("hangXe"),
                        rs.getString("dongXe"),
                        rs.getInt("namSanXuat"),
                        kh // Truyền đối tượng KhachHang vào Xe
                ));
            }
        }
        return ds;
    }

    public Xe getXeByBienSo(String bienSo) throws SQLException {
        String sql = "SELECT * FROM XE WHERE bienSo = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bienSo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    KhachHang kh = new KhachHang();
                    kh.setMaKhachHang(rs.getString("maKhachHang"));

                    return new Xe(
                            rs.getString("bienSo"),
                            rs.getString("hangXe"),
                            rs.getString("dongXe"),
                            rs.getInt("namSanXuat"),
                            kh
                    );
                }
            }
        }
        return null;
    }

    public List<Xe> getXeByMaKH(String maKH) throws SQLException {
        List<Xe> ds = new ArrayList<>();
        String sql = "SELECT bienSo, hangXe, dongXe, namSanXuat, maKhachHang FROM XE WHERE maKhachHang = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKH);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    KhachHang kh = new KhachHang();
                    kh.setMaKhachHang(rs.getString("maKhachHang"));

                    ds.add(new Xe(
                            rs.getString("bienSo"),
                            rs.getString("hangXe"),
                            rs.getString("dongXe"),
                            rs.getInt("namSanXuat"),
                            kh
                    ));
                }
            }
        }
        return ds;
    }

    public boolean themXe(Xe xe) throws SQLException {
        String sql = "INSERT INTO XE (bienSo, hangXe, dongXe, namSanXuat, maKhachHang) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, xe.getBienSo());
            ps.setString(2, xe.getHangXe());
            ps.setString(3, xe.getDongXe());
            ps.setInt(4, xe.getNamSanXuat());

            // Lấy mã chuỗi bên trong đối tượng KhachHang để lưu xuống CSDL
            String maKhachHangStr = (xe.getMaKhachHang() != null) ? xe.getMaKhachHang().getMaKhachHang() : null;
            ps.setString(5, maKhachHangStr);

            return ps.executeUpdate() > 0;
        }
    }
}