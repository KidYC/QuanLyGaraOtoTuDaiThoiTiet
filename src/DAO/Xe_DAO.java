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
}