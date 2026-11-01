package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.DanhSachPhuTung;

public class DanhSachPhuTung_DAO {

    // Sửa lại tên bảng thành CHI_TIET_PHIEU_SUA_CHUA và đúng tên cột trong CSDL của bạn
    public boolean themDanhSachPhuTung(DanhSachPhuTung dspt) throws SQLException {
        String sql = "INSERT INTO CHI_TIET_PHIEU_SUA_CHUA (maPhieuSuaChua, maPhuTung, soLuong, donGia) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dspt.getMaPhieuSuaChua());
            ps.setString(2, dspt.getMaPhuTung());
            ps.setInt(3, dspt.getSoLuongPhuTung());
            ps.setDouble(4, dspt.getDonGiaBan());
            return ps.executeUpdate() > 0;
        }
    }

    // Lấy danh sách phụ tùng theo mã phiếu từ bảng CHI_TIET_PHIEU_SUA_CHUA
    public List<DanhSachPhuTung> getDSPhuTungByMaPhieu(String maPhieuSC) throws SQLException {
        List<DanhSachPhuTung> list = new ArrayList<>();
        String sql = "SELECT * FROM CHI_TIET_PHIEU_SUA_CHUA WHERE maPhieuSuaChua = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieuSC);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new DanhSachPhuTung(
                            rs.getString("maPhieuSuaChua"),
                            rs.getString("maPhuTung"),
                            rs.getInt("soLuong"), // Tên cột trong CSDL là soLuong
                            rs.getDouble("donGia") // Tên cột trong CSDL là donGia
                    ));
                }
            }
        }
        return list;
    }
}