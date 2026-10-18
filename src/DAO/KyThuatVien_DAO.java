package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.KyThuatVien;

public class KyThuatVien_DAO {

    public List<KyThuatVien> getAllKyThuatVien() throws SQLException {
        List<KyThuatVien> ds = new ArrayList<>();
        String sql = "SELECT maKTV, hoTen, diaChi, ngayVaoLam, trangThai FROM KY_THUAT_VIEN";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new KyThuatVien(
                        rs.getString("maKTV"),
                        rs.getString("hoTen"),
                        rs.getString("diaChi"),
                        rs.getDate("ngayVaoLam"),
                        rs.getString("trangThai")
                ));
            }
        }
        return ds;
    }

    public KyThuatVien getKyThuatVienByMa(String maKTV) throws SQLException {
        String sql = "SELECT maKTV, hoTen, diaChi, ngayVaoLam, trangThai FROM KY_THUAT_VIEN WHERE maKTV = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKTV);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new KyThuatVien(
                            rs.getString("maKTV"),
                            rs.getString("hoTen"),
                            rs.getString("diaChi"),
                            rs.getDate("ngayVaoLam"),
                            rs.getString("trangThai")
                    );
                }
            }
        }
        return null;
    }
}