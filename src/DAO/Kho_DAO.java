package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.Kho;

public class Kho_DAO {

    public List<Kho> getAllKho() throws SQLException {
        List<Kho> ds = new ArrayList<>();
        String sql = "SELECT maKho, tenKho, diaChi FROM KHO";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new Kho(rs.getString("maKho"), rs.getString("tenKho"), rs.getString("diaChi")));
            }
        }
        return ds;
    }

    public Kho timKhoTheoMa(String maKho) throws SQLException {
        String sql = "SELECT maKho, tenKho, diaChi FROM KHO WHERE maKho = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKho);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Kho(rs.getString("maKho"), rs.getString("tenKho"), rs.getString("diaChi"));
                }
            }
        }
        return null;
    }

    public boolean themKho(Kho kho) throws SQLException {
        String sql = "INSERT INTO KHO (maKho, tenKho, diaChi) VALUES (?, ?, ?)";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kho.getMaKho());
            ps.setString(2, kho.getTenKho());
            ps.setString(3, kho.getDiaChi());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean capNhatKho(Kho kho) throws SQLException {
        String sql = "UPDATE KHO SET tenKho = ?, diaChi = ? WHERE maKho = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kho.getTenKho());
            ps.setString(2, kho.getDiaChi());
            ps.setString(3, kho.getMaKho());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean xoaKho(String maKho) throws SQLException {
        String sql = "DELETE FROM KHO WHERE maKho = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKho);
            return ps.executeUpdate() > 0;
        }
    }
}
