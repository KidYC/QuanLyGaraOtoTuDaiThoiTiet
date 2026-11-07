package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.PhieuTiepNhan;
import Entity.PhieuTiepNhan_DTO;
import Entity.Xe;

public class PhieuTiepNhan_DAO {

    public List<PhieuTiepNhan> getAllPhieuTiepNhan() throws SQLException {
        List<PhieuTiepNhan> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHIEU_TIEP_NHAN";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Xe xe = new Xe();
                xe.setBienSo(rs.getString("bienSo"));

                ds.add(new PhieuTiepNhan(
                        rs.getString("maPhieuTiepNhan"),
                        rs.getDate("ngayTiepNhan"),
                        rs.getString("tinhTrangXe"),
                        rs.getString("yeuCauKH"),
                        rs.getString("trangThai"),
                        xe
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

    public boolean capNhatTrangThai(String maPhieuTN, String trangThaiMoi) throws SQLException {
        String sql = "UPDATE PHIEU_TIEP_NHAN SET trangThai = ? WHERE maPhieuTiepNhan = ?";

        try (Connection conn = DB.connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trangThaiMoi);
            ps.setString(2, maPhieuTN);

            return ps.executeUpdate() > 0;
        }
    }

    public List<PhieuTiepNhan_DTO> getDanhSachXeChoBaoGia() throws SQLException {
        List<PhieuTiepNhan_DTO> list = new ArrayList<>();

        String sql = "SELECT p.maPhieuSuaChua, k.hoTen, x.bienSo, (h.tenHangXe + ' ' + d.tenDongXe) AS loaiXe, p.ngayLap, p.trangThai \n" +
                "FROM PHIEU_SUA_CHUA p \n" +
                "JOIN XE x ON p.bienSo = x.bienSo \n" +
                "LEFT JOIN DONG_XE d ON x.maDongXe = d.maDongXe \n" +
                "LEFT JOIN HANG_XE h ON d.maHangXe = h.maHangXe \n" +
                "JOIN KHACH_HANG k ON x.maKhachHang = k.maKhachHang \n" +
                "WHERE p.trangThai = N'Chờ xác nhận báo giá'";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PhieuTiepNhan_DTO dto = new PhieuTiepNhan_DTO(
                        rs.getString("maPhieuSuaChua"),
                        rs.getString("hoTen"),
                        rs.getString("bienSo"),
                        rs.getString("loaiXe"),
                        rs.getTimestamp("ngayLap"),
                        rs.getString("trangThai")
                );
                list.add(dto);
            }
        }
        return list;
    }

    public boolean themPhieuTiepNhan(PhieuTiepNhan ptn) throws SQLException {
        String sql = "INSERT INTO PHIEU_TIEP_NHAN (maPhieuTiepNhan, ngayTiepNhan, tinhTrangXe, yeuCauKH, trangThai, bienSo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ptn.getMaPhieuTiepNhan());
            ps.setDate(2, ptn.getNgayTiepNhan());
            ps.setString(3, ptn.getTinhTrangXe());
            ps.setString(4, ptn.getYeuCauKH());
            ps.setString(5, ptn.getTrangThai());
            ps.setString(6, ptn.getBienSo());

            return ps.executeUpdate() > 0;
        }
    }

    public String getMaPhieuTiepNhanTuDong() {
        String prefix = "PTN";
        int nextId = 1;
        String sql = "SELECT MAX(maPhieuTiepNhan) FROM PHIEU_TIEP_NHAN";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String maxMa = rs.getString(1);
                if (maxMa != null && maxMa.startsWith(prefix)) {
                    String numPart = maxMa.substring(prefix.length());
                    nextId = Integer.parseInt(numPart) + 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.format("%s%02d", prefix, nextId);
    }
}