package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.Xe;

public class Xe_DAO {
    public List<Xe> getAllXe() throws SQLException {
        List<Xe> ds = new ArrayList<>();
        String sql = "SELECT * FROM XE";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new Xe(
                        rs.getString("bienSo"),
                        rs.getString("hangXe"),
                        rs.getString("dongXe"),
                        rs.getInt("namSanXuat"),
                        rs.getString("maKhachHang")
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
                    return new Xe(
                            rs.getString("bienSo"),
                            rs.getString("hangXe"),
                            rs.getString("dongXe"),
                            rs.getInt("namSanXuat"),
                            rs.getString("maKhachHang")
                    );
                }
            }
        }
        return null;
    }

    public List<Xe> getXeByMaKH(String maKH) throws SQLException{
        List<Xe> ds = new ArrayList<>();
        String sql = "SELECT bienSo, hangXe, dongXe, namSanXuat FROM XE WHERE maKhachHang = ?";
        try(Connection conn = connectSQL.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, maKH);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    ds.add( new Xe(
                            rs.getString("bienSo"),
                            rs.getString("hangXe"),
                            rs.getString("dongXe"),
                            rs.getInt("namSanXuat"),
                            maKH
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
            ps.setString(5, xe.getMaKhachHang());

            return ps.executeUpdate() > 0;
        }
    }
}