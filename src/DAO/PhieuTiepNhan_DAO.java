package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.PhieuTiepNhan;

public class PhieuTiepNhan_DAO {


    public List<PhieuTiepNhan> getAllPhieuTiepNhan() throws SQLException {
        List<PhieuTiepNhan> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHIEU_TIEP_NHAN";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new PhieuTiepNhan(
                        rs.getString("maPhieuTiepNhan"),
                        rs.getDate("ngayTiepNhan"),
                        rs.getString("tinhTrangXe"),
                        rs.getString("yeuCauKH"),
                        rs.getString("trangThai"),
                        rs.getString("bienSo")
                ));
            }
        }
        return ds;
    }

    public boolean capNhatChanDoan(String maPhieu, String tinhTrangMoi, String trangThai) throws SQLException {
        String sql = "UPDATE PHIEU_TIEP_NHAN SET tinhTrangXe = ?, trangThai = ? WHERE maPhieuTiepNhan = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tinhTrangMoi);
            ps.setString(2, trangThai);
            ps.setString(3, maPhieu);
            return ps.executeUpdate() > 0;
        }
    }

    // Hàm cập nhật trạng thái cho Phiếu Tiếp Nhận
    public boolean capNhatTrangThai(String maPhieuTN, String trangThaiMoi) throws SQLException {
        String sql = "UPDATE PHIEU_TIEP_NHAN SET trangThai = ? WHERE maPhieuTiepNhan = ?";

        try (Connection conn = DB.connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trangThaiMoi);
            ps.setString(2, maPhieuTN);

            return ps.executeUpdate() > 0;
        }
    }
}