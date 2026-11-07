package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.HangXe;

public class HangXe_DAO {

    public List<HangXe> getAllHangXe() throws SQLException {
        List<HangXe> ds = new ArrayList<>();
        String sql = "SELECT maHangXe, tenHangXe, xuatXu FROM HANG_XE";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ds.add(new HangXe(
                        rs.getString("maHangXe"),
                        rs.getString("tenHangXe"),
                        rs.getString("xuatXu")
                ));
            }
        }
        return ds;
    }

    public HangXe getHangXeByMa(String maHangXe) throws SQLException {
        String sql = "SELECT maHangXe, tenHangXe, xuatXu FROM HANG_XE WHERE maHangXe = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHangXe);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new HangXe(
                            rs.getString("maHangXe"),
                            rs.getString("tenHangXe"),
                            rs.getString("xuatXu")
                    );
                }
            }
        }
        return null;
    }
}