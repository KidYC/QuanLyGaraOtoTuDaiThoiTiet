package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.DanhSachPhuTung;
import Entity.PhieuSuaChua;

public class PhieuSuaChua_DAO {

    private PhieuSuaChua docPhieuSuaChua(ResultSet rs) throws SQLException {
        PhieuSuaChua psc = new PhieuSuaChua(
                rs.getString("maPhieuSuaChua"),
                rs.getDate("ngayLap"),
                rs.getDate("ngayIn"),
                rs.getString("trangThai"),
                rs.getString("bienSo"),
                rs.getString("maKTV"),
                rs.getString("maPhieuTiepNhan"),
                rs.getString("loiPhatHien"),
                rs.getString("nguyenNhan"),
                rs.getString("deXuatSuaChua")
        );

        psc.setTienDo(rs.getInt("tienDo"));
        psc.setGhiChu(rs.getString("ghiChu"));
        psc.setNgayDuKienHoanThanh(rs.getDate("ngayDuKienHoanThanh"));
        psc.setKetQuaKCS(rs.getString("ketQuaKCS"));
        psc.setGhiChuKCS(rs.getString("ghiChuKCS"));
        psc.setNgayKiemTraKCS(rs.getDate("ngayKiemTraKCS"));

        return psc;
    }

    public List<PhieuSuaChua> getAllPhieuSuaChua() throws SQLException {
        List<PhieuSuaChua> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHIEU_SUA_CHUA";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ds.add(docPhieuSuaChua(rs));
            }
        }
        return ds;
    }

    public String phatSinhMaPhieuSuaChuaMoi() throws SQLException {
        String sql = "SELECT MAX(maPhieuSuaChua) FROM PHIEU_SUA_CHUA";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String maxMa = rs.getString(1);
                if (maxMa != null && maxMa.startsWith("PSC")) {
                    int so = Integer.parseInt(maxMa.substring(3));
                    return String.format("PSC%02d", so + 1);
                }
            }
        }
        return "PSC01";
    }

    public boolean themPhieuSuaChua(PhieuSuaChua psc) throws SQLException {
        String sqlCheckKTV = "IF NOT EXISTS (SELECT 1 FROM KY_THUAT_VIEN WHERE maKTV = ?) "
                + "INSERT INTO KY_THUAT_VIEN (maKTV, hoTen) VALUES (?, N'Kỹ thuật viên / Quản lý')";

        String sqlInsertPSC = "INSERT INTO PHIEU_SUA_CHUA "
                + "(maPhieuSuaChua, ngayLap, ngayIn, trangThai, bienSo, maKTV, maPhieuTiepNhan, loiPhatHien, nguyenNhan, deXuatSuaChua) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connectSQL.getConnection()) {
            try (PreparedStatement psKTV = conn.prepareStatement(sqlCheckKTV)) {
                psKTV.setString(1, psc.getMaNhanVien());
                psKTV.setString(2, psc.getMaNhanVien());
                psKTV.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlInsertPSC)) {
                ps.setString(1, psc.getMaPhieuSuaChua());
                ps.setDate(2, psc.getNgayLap());
                ps.setDate(3, psc.getNgayIn());
                ps.setString(4, psc.getTrangThai());
                ps.setString(5, psc.getBienSo());
                ps.setString(6, psc.getMaNhanVien());
                ps.setString(7, psc.getMaPhieuTiepNhan());
                ps.setString(8, psc.getLoiPhatHien());
                ps.setString(9, psc.getNguyenNhan());
                ps.setString(10, psc.getDeXuatSuaChua());
                return ps.executeUpdate() > 0;
            }
        }
    }

    public List<PhieuSuaChua> getDSPhieuChoXacNhanBaoGia() throws SQLException {
        List<PhieuSuaChua> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHIEU_SUA_CHUA WHERE trangThai = N'Chờ xác nhận báo giá' ORDER BY ngayLap DESC";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ds.add(docPhieuSuaChua(rs));
            }
        }
        return ds;
    }

    public boolean capNhatChanDoan(String maPhieuSC, String loiPhatHien, String nguyenNhan, String deXuatSuaChua) throws SQLException {
        String sql = "UPDATE PHIEU_SUA_CHUA SET loiPhatHien = ?, nguyenNhan = ?, deXuatSuaChua = ? WHERE maPhieuSuaChua = ?";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, loiPhatHien);
            ps.setString(2, nguyenNhan);
            ps.setString(3, deXuatSuaChua);
            ps.setString(4, maPhieuSC);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean capNhatTrangThai(String maPhieuSC, String trangThaiMoi) throws SQLException {
        String sql = "UPDATE PHIEU_SUA_CHUA SET trangThai = ? WHERE maPhieuSuaChua = ?";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trangThaiMoi);
            ps.setString(2, maPhieuSC);

            return ps.executeUpdate() > 0;
        }
    }

    public List<PhieuSuaChua> getDSPhieuChoXuatKho() throws SQLException {
        List<PhieuSuaChua> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHIEU_SUA_CHUA WHERE trangThai = N'Chờ xuất kho' ORDER BY ngayLap DESC";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ds.add(docPhieuSuaChua(rs));
            }
        }
        return ds;
    }

    public boolean xacNhanXuatKho(String maPhieuSC) throws SQLException {
        Connection conn = connectSQL.getConnection();
        DanhSachPhuTung_DAO dsPhuTungDAO = new DanhSachPhuTung_DAO();
        PhuTung_DAO phuTungDAO = new PhuTung_DAO();

        try {
            conn.setAutoCommit(false);

            List<DanhSachPhuTung> dsPT = dsPhuTungDAO.getDSPhuTungByMaPhieu(maPhieuSC);

            for (DanhSachPhuTung pt : dsPT) {
                boolean checkTon = phuTungDAO.giamSoLuongTon(
                        pt.getMaPhuTung(),
                        pt.getSoLuongPhuTung(),
                        conn
                );

                if (!checkTon) {
                    conn.rollback();
                    return false;
                }
            }

            String sqlUpdate = "UPDATE PHIEU_SUA_CHUA SET trangThai = N'Đang sửa chữa' WHERE maPhieuSuaChua = ?";

            try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setString(1, maPhieuSC);

                if (ps.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public boolean capNhatTienDoVaTrangThai(
            String maPhieuSC,
            String trangThai,
            int tienDo,
            String ghiChu,
            java.sql.Date ngayDuKienHoanThanh
    ) throws SQLException {
        String sql = "UPDATE PHIEU_SUA_CHUA SET trangThai = ?, tienDo = ?, ghiChu = ?, ngayDuKienHoanThanh = ? WHERE maPhieuSuaChua = ?";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trangThai);
            ps.setInt(2, tienDo);
            ps.setString(3, ghiChu);
            ps.setDate(4, ngayDuKienHoanThanh);
            ps.setString(5, maPhieuSC);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean capNhatKCS(
            String maPhieuSC,
            String ketQuaKCS,
            String ghiChuKCS,
            java.sql.Date ngayKiemTra,
            String trangThaiMoi
    ) throws SQLException {
        String sql = "UPDATE PHIEU_SUA_CHUA SET ketQuaKCS = ?, ghiChuKCS = ?, ngayKiemTraKCS = ?, trangThai = ? WHERE maPhieuSuaChua = ?";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ketQuaKCS);
            ps.setString(2, ghiChuKCS);
            ps.setDate(3, ngayKiemTra);
            ps.setString(4, trangThaiMoi);
            ps.setString(5, maPhieuSC);

            return ps.executeUpdate() > 0;
        }
    }
}