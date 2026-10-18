package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.PhieuTiepNhan;

public class PhieuTiepNhan_DAO {

    // Lấy danh sách phiếu tiếp nhận phục vụ chẩn đoán & lập phiếu sửa chữa
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

    // Cập nhật kết quả chẩn đoán lỗi thực tế của KTV
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
}