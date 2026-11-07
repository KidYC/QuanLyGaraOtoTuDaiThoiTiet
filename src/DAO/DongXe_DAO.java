package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.DongXe;
import Entity.HangXe;

public class DongXe_DAO {

    // Lấy tất cả dòng xe kèm thông tin Hãng xe (JOIN)
    public List<DongXe> getAllDongXe() throws SQLException {
        List<DongXe> ds = new ArrayList<>();
        String sql = "SELECT d.maDongXe, d.tenDongXe, h.maHangXe, h.tenHangXe, h.xuatXu " +
                "FROM DONG_XE d LEFT JOIN HANG_XE h ON d.maHangXe = h.maHangXe";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HangXe hx = new HangXe(
                        rs.getString("maHangXe"),
                        rs.getString("tenHangXe"),
                        rs.getString("xuatXu")
                );
                ds.add(new DongXe(
                        rs.getString("maDongXe"),
                        rs.getString("tenDongXe"),
                        hx
                ));
            }
        }
        return ds;
    }

    // Lấy danh sách dòng xe theo mã hãng xe (Load ComboBox phụ thuộc)
    public List<DongXe> getDongXeByMaHangXe(String maHangXe) throws SQLException {
        List<DongXe> ds = new ArrayList<>();
        String sql = "SELECT d.maDongXe, d.tenDongXe, h.maHangXe, h.tenHangXe, h.xuatXu " +
                "FROM DONG_XE d LEFT JOIN HANG_XE h ON d.maHangXe = h.maHangXe " +
                "WHERE d.maHangXe = ?";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maHangXe);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HangXe hx = new HangXe(
                            rs.getString("maHangXe"),
                            rs.getString("tenHangXe"),
                            rs.getString("xuatXu")
                    );
                    ds.add(new DongXe(
                            rs.getString("maDongXe"),
                            rs.getString("tenDongXe"),
                            hx
                    ));
                }
            }
        }
        return ds;
    }

    // Lấy dòng xe theo mã dòng xe
    public DongXe getDongXeByMa(String maDongXe) throws SQLException {
        String sql = "SELECT d.maDongXe, d.tenDongXe, h.maHangXe, h.tenHangXe, h.xuatXu " +
                "FROM DONG_XE d LEFT JOIN HANG_XE h ON d.maHangXe = h.maHangXe " +
                "WHERE d.maDongXe = ?";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maDongXe);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HangXe hx = new HangXe(
                            rs.getString("maHangXe"),
                            rs.getString("tenHangXe"),
                            rs.getString("xuatXu")
                    );
                    return new DongXe(
                            rs.getString("maDongXe"),
                            rs.getString("tenDongXe"),
                            hx
                    );
                }
            }
        }
        return null;
    }
}