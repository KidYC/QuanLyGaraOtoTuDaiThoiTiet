package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.NhaCungCap;

public class NhaCungCap_DAO {

    public List<NhaCungCap> getAllNhaCungCap() throws SQLException {
        List<NhaCungCap> ds = new ArrayList<>();
        String sql = "SELECT maNhaCungCap, tenNhaCungCap, soDienThoai, diaChi, email FROM NHA_CUNG_CAP";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new NhaCungCap(
                        rs.getString("maNhaCungCap"),
                        rs.getString("tenNhaCungCap"),
                        rs.getString("soDienThoai"),
                        rs.getString("diaChi"),
                        rs.getString("email")
                ));
            }
        }
        return ds;
    }

    public NhaCungCap timNhaCungCapTheoMa(String ma) throws SQLException {
        String sql = "SELECT maNhaCungCap, tenNhaCungCap, soDienThoai, diaChi, email FROM NHA_CUNG_CAP WHERE maNhaCungCap = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ma);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new NhaCungCap(
                            rs.getString("maNhaCungCap"),
                            rs.getString("tenNhaCungCap"),
                            rs.getString("soDienThoai"),
                            rs.getString("diaChi"),
                            rs.getString("email")
                    );
                }
            }
        }
        return null;
    }

    public boolean themNhaCungCap(NhaCungCap ncc) throws SQLException {
        String sql = "INSERT INTO NHA_CUNG_CAP (maNhaCungCap, tenNhaCungCap, soDienThoai, diaChi, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ncc.getMaNhaCungCap());
            ps.setString(2, ncc.getTenNhaCungCap());
            ps.setString(3, ncc.getSoDienThoai());
            ps.setString(4, ncc.getDiaChi());
            ps.setString(5, ncc.getEmail());
            return ps.executeUpdate() > 0;
        }
    }
    public boolean capNhatNhaCungCap(NhaCungCap ncc) throws SQLException {
        String sql = "UPDATE NHA_CUNG_CAP SET tenNhaCungCap = ?, soDienThoai = ?, diaChi = ?, email = ? WHERE maNhaCungCap = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ncc.getTenNhaCungCap());
            ps.setString(2, ncc.getSoDienThoai());
            ps.setString(3, ncc.getDiaChi());
            ps.setString(4, ncc.getEmail());
            ps.setString(5, ncc.getMaNhaCungCap());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean xoaNhaCungCap(String ma) throws SQLException {
        String sql = "DELETE FROM NHA_CUNG_CAP WHERE maNhaCungCap = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ma);
            return ps.executeUpdate() > 0;
        }
    }
}
