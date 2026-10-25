
package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.PhuTung;

public class PhuTung_DAO {
    public List<PhuTung> getAllPhuTung() throws SQLException {
        List<PhuTung> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHU_TUNG";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new PhuTung(
                        rs.getString("maPhuTung"),
                        rs.getString("tenPhuTung"),
                        rs.getString("donViTinh"),
                        rs.getDouble("donGiaNhap"),
                        rs.getDouble("donGiaBan"),
                        rs.getInt("soLuongTon"),
                        rs.getString("maKho")
                ));
            }
        }
        return ds;
    }

    public boolean giamSoLuongTon(String maPhuTung, int soLuongXuat, Connection conn) throws SQLException {
        String sql = "UPDATE PHU_TUNG SET soLuongTon = soLuongTon - ? WHERE maPhuTung = ? AND soLuongTon >= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuongXuat);
            ps.setString(2, maPhuTung);
            ps.setInt(3, soLuongXuat);
            return ps.executeUpdate() > 0;
        }
    }

    private PhuTung docPhuTung(ResultSet rs) throws SQLException {
        return new PhuTung(
                rs.getString("maPhuTung"),
                rs.getString("tenPhuTung"),
                rs.getString("donViTinh"),
                rs.getDouble("donGiaNhap"),
                rs.getDouble("donGiaBan"),
                rs.getInt("soLuongTon"),
                rs.getString("maKho"));
    }

    public PhuTung timPhuTungTheoMa(String maPhuTung) throws SQLException {
        String sql = "SELECT * FROM PHU_TUNG WHERE maPhuTung = ?";
        try (Connection conn = connectSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, maPhuTung);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return docPhuTung(rs);
                }
            }
        }
        return null;
    }

    public List<PhuTung> timKiemPhuTung(String tuKhoa) throws SQLException {
        List<PhuTung> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHU_TUNG WHERE maPhuTung LIKE ? OR tenPhuTung LIKE ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String kq = "%" + tuKhoa.trim() + "%";
            ps.setString(1, kq);
            ps.setString(2, kq);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ds.add(docPhuTung(rs));
                }
            }
        }
        return ds;
    }

    public List<PhuTung> timPhuTungTheoMaKho(String maKho) throws SQLException{
        List<PhuTung> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHU_TUNG WHERE maKho = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKho);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    ds.add(docPhuTung(rs));
                }
            }
        }
        return ds;
    }

    public List<PhuTung> timPhuTungSapHet(int nguongToiThieu) throws SQLException{
        List<PhuTung> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHU_TUNG WHERE soLuongTon <= ? ORDER BY soLuongTon ASC";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, nguongToiThieu);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    ds.add(docPhuTung(rs));
                }
            }
        }
        return ds;
    }

    public int xemSoLuongTon(String maPhuTung) throws SQLException{
        String sql = "SELECT soLuongTon FROM PHU_TUNG WHERE maPhuTung = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, maPhuTung);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public boolean themPhuTung(PhuTung pt) throws SQLException {
        String sql = "INSERT INTO PHU_TUNG (maPhuTung, tenPhuTung, donViTinh, donGiaNhap, donGiaBan, soLuongTon, maKho) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pt.getMaPhuTung());
            ps.setString(2, pt.getTenPhuTung());
            ps.setString(3, pt.getDonViTinh());
            ps.setDouble(4, pt.getDonGiaNhap());
            ps.setDouble(5, pt.getDonGiaBan());
            ps.setInt(6, pt.getSoLuongTon());
            ps.setString(7, pt.getMaKho());
            return ps.executeUpdate() > 0 ;
        }
    }

    public boolean capNhatPhuTung(PhuTung pt) throws SQLException {
        String sql = "UPDATE PHU_TUNG SET tenPhuTung = ?, donViTinh = ?, donGiaNhap = ?, donGiaBan = ?, soLuongTon = ?, maKho = ? WHERE maPhuTung = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pt.getTenPhuTung());
            ps.setString(2, pt.getDonViTinh());
            ps.setDouble(3, pt.getDonGiaNhap());
            ps.setDouble(4, pt.getDonGiaBan());
            ps.setInt(5, pt.getSoLuongTon());
            ps.setString(6, pt.getMaKho());
            ps.setString(7, pt.getMaPhuTung());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean xoaPhuTung (String maPhuTung) throws SQLException {
        String sql = "DELETE FROM PHU_TUNG WHERE maPhuTung = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhuTung);
            return ps.executeUpdate() > 0 ;
        }
    }

    public boolean tangSoLuongTon (String maPhuTung, int soLuongNhap, Connection conn) throws SQLException {
        String sql = "UPDATE PHU_TUNG SET soLuongTon = soLuongTon + ? WHERE maPhuTung = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuongNhap);
            ps.setString(2, maPhuTung);
            return ps.executeUpdate() > 0 ;
        }
    }

    public boolean capNhatSoLuongTon(String maPhuTung, int soLuongMoi, Connection conn) throws SQLException {
        String sql = "UPDATE PHU_TUNG SET soLuongTon = ? WHERE maPhuTung = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuongMoi);
            ps.setString(2, maPhuTung);
            return ps.executeUpdate() > 0;
        }
    }

}