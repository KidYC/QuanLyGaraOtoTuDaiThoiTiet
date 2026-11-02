package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.ChiTietKiemKe;
import Entity.PhieuKiemKe;
import Entity.ThuKho;

public class PhieuKiemKe_DAO {

    public List<PhieuKiemKe> getAllPhieuKiemKe() throws SQLException {
        List<PhieuKiemKe> ds = new ArrayList<>();
        String sql = "SELECT kk.maPhieuKiemKe, kk.ngayKiemKe, kk.trangThai, "
                + "tk.maNhanVien, tk.hoTen, tk.soDienThoai "
                + "FROM PHIEU_KIEM_KE kk "
                + "LEFT JOIN THU_KHO tk ON kk.maNhanVien = tk.maNhanVien "
                + "ORDER BY kk.ngayKiemKe DESC";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PhieuKiemKe pk = new PhieuKiemKe();
                pk.setMaPhieuKiemKe(rs.getString("maPhieuKiemKe"));
                pk.setNgayKiemKe(rs.getTimestamp("ngayKiemKe"));
                pk.setTrangThai(rs.getString("trangThai"));
                pk.setThuKho(new ThuKho(rs.getString("maNhanVien"), rs.getString("hoTen"), rs.getString("soDienThoai")));
                ds.add(pk);
            }
        }
        return ds;
    }

    public PhieuKiemKe timPhieuKiemKeTheoMa(String maPhieuKiemKe) throws SQLException {
        String sql = "SELECT kk.maPhieuKiemKe, kk.ngayKiemKe, kk.trangThai, "
                + "tk.maNhanVien, tk.hoTen, tk.soDienThoai "
                + "FROM PHIEU_KIEM_KE kk "
                + "LEFT JOIN THU_KHO tk ON kk.maNhanVien = tk.maNhanVien "
                + "WHERE kk.maPhieuKiemKe = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieuKiemKe);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PhieuKiemKe pk = new PhieuKiemKe();
                    pk.setMaPhieuKiemKe(rs.getString("maPhieuKiemKe"));
                    pk.setNgayKiemKe(rs.getTimestamp("ngayKiemKe"));
                    pk.setTrangThai(rs.getString("trangThai"));
                    pk.setThuKho(new ThuKho(rs.getString("maNhanVien"), rs.getString("hoTen"), rs.getString("soDienThoai")));
                    return pk;
                }
            }
        }
        return null;
    }

    public List<ChiTietKiemKe> getChiTietKiemKe(String maPhieuKiemKe) throws SQLException {
        List<ChiTietKiemKe> ds = new ArrayList<>();
        String sql = "SELECT ct.maPhieuKiemKe, ct.maPhuTung, pt.tenPhuTung, pt.donViTinh, "
                + "ct.soLuongHeThong, ct.soLuongThucTe, ct.chenhLech, ct.lyDoChenhLech "
                + "FROM CHI_TIET_KIEM_KE ct JOIN PHU_TUNG pt ON ct.maPhuTung = pt.maPhuTung "
                + "WHERE ct.maPhieuKiemKe = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieuKiemKe);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietKiemKe ct = new ChiTietKiemKe(
                            rs.getString("maPhieuKiemKe"),
                            rs.getString("maPhuTung"),
                            rs.getInt("soLuongHeThong"),
                            rs.getInt("soLuongThucTe"),
                            rs.getString("lyDoChenhLech"));
                    ct.setChenhLech(rs.getInt("chenhLech"));
                    ct.setTenPhuTung(rs.getString("tenPhuTung"));
                    ct.setDonViTinh(rs.getString("donViTinh"));
                    ds.add(ct);
                }
            }
        }
        return ds;
    }

    // Sinh mã mới: KK01, KK02, ... Lấy số lớn nhất theo giá trị số (không so chuỗi)
    // để sau KK99 ra KK100 chứ không bị trùng.
    public String sinhMaPhieuKiemKe() throws SQLException {
        String sql = "SELECT maPhieuKiemKe FROM PHIEU_KIEM_KE WHERE maPhieuKiemKe LIKE 'KK%'";
        int max = 0;
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                try {
                    int so = Integer.parseInt(rs.getString(1).substring(2).trim());
                    if (so > max) max = so;
                } catch (NumberFormatException e) {
                    // bỏ qua mã không đúng dạng KK + số
                }
            }
        }
        return String.format("KK%02d", max + 1);
    }

    public boolean taoPhieuKiemKe(PhieuKiemKe pk, List<ChiTietKiemKe> dsChiTiet) throws SQLException {
        Connection conn = connectSQL.getConnection();
        try {
            conn.setAutoCommit(false);

            java.util.Date ngay = pk.getNgayKiemKe();
            if (ngay == null) {
                ngay = new java.util.Date();
            }

            String sqlPhieu = "INSERT INTO PHIEU_KIEM_KE (maPhieuKiemKe, ngayKiemKe, trangThai, maNhanVien) VALUES (?, ?, N'Đang kiểm kê', ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlPhieu)) {
                ps.setString(1, pk.getMaPhieuKiemKe());
                ps.setTimestamp(2, new Timestamp(ngay.getTime()));
                ps.setString(3, pk.getThuKho().getMaNhanVien());
                ps.executeUpdate();
            }

            String sqlCT = "INSERT INTO CHI_TIET_KIEM_KE (maPhieuKiemKe, maPhuTung, soLuongHeThong, soLuongThucTe, chenhLech, lyDoChenhLech) VALUES (?, ?, ?, ?, ?, ?)";
            for (ChiTietKiemKe ct : dsChiTiet) {
                try (PreparedStatement ps = conn.prepareStatement(sqlCT)) {
                    ps.setString(1, pk.getMaPhieuKiemKe());
                    ps.setString(2, ct.getMaPhuTung());
                    ps.setInt(3, ct.getSoLuongHeThong());
                    ps.setInt(4, ct.getSoLuongThucTe());
                    ps.setInt(5, ct.tinhChenhLech());
                    ps.setString(6, ct.getLyDoChenhLech());
                    ps.executeUpdate();
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

    public boolean capNhatPhieuKiemKe(PhieuKiemKe pk, List<ChiTietKiemKe> dsChiTiet) throws SQLException {
        Connection conn = connectSQL.getConnection();
        try {
            conn.setAutoCommit(false);

            String sqlPhieu = "UPDATE PHIEU_KIEM_KE SET maNhanVien = ? WHERE maPhieuKiemKe = ? AND trangThai = N'Đang kiểm kê'";
            int soDong;
            try (PreparedStatement ps = conn.prepareStatement(sqlPhieu)) {
                ps.setString(1, pk.getThuKho().getMaNhanVien());
                ps.setString(2, pk.getMaPhieuKiemKe());
                soDong = ps.executeUpdate();
            }
            if (soDong == 0) {
                conn.rollback();
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM CHI_TIET_KIEM_KE WHERE maPhieuKiemKe = ?")) {
                ps.setString(1, pk.getMaPhieuKiemKe());
                ps.executeUpdate();
            }

            String sqlCT = "INSERT INTO CHI_TIET_KIEM_KE (maPhieuKiemKe, maPhuTung, soLuongHeThong, soLuongThucTe, chenhLech, lyDoChenhLech) VALUES (?, ?, ?, ?, ?, ?)";
            for (ChiTietKiemKe ct : dsChiTiet) {
                try (PreparedStatement ps = conn.prepareStatement(sqlCT)) {
                    ps.setString(1, pk.getMaPhieuKiemKe());
                    ps.setString(2, ct.getMaPhuTung());
                    ps.setInt(3, ct.getSoLuongHeThong());
                    ps.setInt(4, ct.getSoLuongThucTe());
                    ps.setInt(5, ct.tinhChenhLech());
                    ps.setString(6, ct.getLyDoChenhLech());
                    ps.executeUpdate();
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

    public boolean xacNhanKiemKe(String maPhieuKiemKe) throws SQLException {
        PhuTung_DAO phuTungDAO = new PhuTung_DAO();
        List<ChiTietKiemKe> dsChiTiet = getChiTietKiemKe(maPhieuKiemKe);

        Connection conn = connectSQL.getConnection();
        try {
            conn.setAutoCommit(false);

            String sql = "UPDATE PHIEU_KIEM_KE SET trangThai = N'Hoàn thành' WHERE maPhieuKiemKe = ? AND trangThai = N'Đang kiểm kê'";
            int soDong;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, maPhieuKiemKe);
                soDong = ps.executeUpdate();
            }
            if (soDong == 0) {
                conn.rollback();
                return false;
            }

            // Điều chỉnh tồn kho theo CHÊNH LỆCH (thực tế - hệ thống lúc kiểm kê), không ghi đè,
            // để các lần nhập/xuất xảy ra sau khi lập phiếu không bị mất.
            for (ChiTietKiemKe ct : dsChiTiet) {
                int chenhLech = ct.getSoLuongThucTe() - ct.getSoLuongHeThong();
                boolean ok = true;
                if (chenhLech > 0) {
                    ok = phuTungDAO.tangSoLuongTon(ct.getMaPhuTung(), chenhLech, conn);
                } else if (chenhLech < 0) {
                    ok = phuTungDAO.giamSoLuongTon(ct.getMaPhuTung(), -chenhLech, conn); // tồn không đủ để trừ -> false
                }
                if (!ok) {
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

    public boolean xoaPhieuKiemKe(String maPhieuKiemKe) throws SQLException {
        Connection conn = connectSQL.getConnection();
        try {
            conn.setAutoCommit(false);

            int soDong;
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM PHIEU_KIEM_KE WHERE maPhieuKiemKe = ? AND trangThai = N'Đang kiểm kê'")) {
                ps.setString(1, maPhieuKiemKe);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    soDong = rs.getInt(1);
                }
            }
            if (soDong == 0) {
                conn.rollback();
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM CHI_TIET_KIEM_KE WHERE maPhieuKiemKe = ?")) {
                ps.setString(1, maPhieuKiemKe);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM PHIEU_KIEM_KE WHERE maPhieuKiemKe = ?")) {
                ps.setString(1, maPhieuKiemKe);
                ps.executeUpdate();
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
}
