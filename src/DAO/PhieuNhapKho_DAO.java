package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.ChiTietPhieuNhap;
import Entity.NhaCungCap;
import Entity.PhieuNhapKho;
import Entity.ThuKho;

public class PhieuNhapKho_DAO {

    // 1. READ: Lấy danh sách phiếu nhập (kèm thủ kho và nhà cung cấp)
    public List<PhieuNhapKho> getAllPhieuNhap() throws SQLException {
        List<PhieuNhapKho> ds = new ArrayList<>();
        String sql = "SELECT pn.maPhieuNhap, pn.ngayNhap, pn.tongTienNhap, "
                + "tk.maNhanVien, tk.hoTen AS tenThuKho, tk.soDienThoai AS sdtThuKho, "
                + "ncc.maNhaCungCap, ncc.tenNhaCungCap, ncc.soDienThoai AS sdtNCC, ncc.diaChi, ncc.email "
                + "FROM PHIEU_NHAP_KHO pn "
                + "LEFT JOIN THU_KHO tk ON pn.maNhanVien = tk.maNhanVien "
                + "LEFT JOIN NHA_CUNG_CAP ncc ON pn.maNhaCungCap = ncc.maNhaCungCap "
                + "ORDER BY pn.ngayNhap DESC";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ThuKho tk = new ThuKho(rs.getString("maNhanVien"), rs.getString("tenThuKho"), rs.getString("sdtThuKho"));
                NhaCungCap ncc = new NhaCungCap(rs.getString("maNhaCungCap"), rs.getString("tenNhaCungCap"),
                        rs.getString("sdtNCC"), rs.getString("diaChi"), rs.getString("email"));
                PhieuNhapKho pn = new PhieuNhapKho();
                pn.setThuKho(tk);
                pn.setMaPhieuNhap(rs.getString("maPhieuNhap"));
                pn.setNgayNhap(rs.getDate("ngayNhap"));
                pn.setTongTienNhap(rs.getDouble("tongTienNhap"));
                pn.setNhaCungCap(ncc);
                ds.add(pn);
            }
        }
        return ds;
    }

    // 1b. READ: Tìm một phiếu nhập theo mã
    public PhieuNhapKho timPhieuNhapTheoMa(String maPhieuNhap) throws SQLException {
        String sql = "SELECT pn.maPhieuNhap, pn.ngayNhap, pn.tongTienNhap, "
                + "tk.maNhanVien, tk.hoTen AS tenThuKho, tk.soDienThoai AS sdtThuKho, "
                + "ncc.maNhaCungCap, ncc.tenNhaCungCap, ncc.soDienThoai AS sdtNCC, ncc.diaChi, ncc.email "
                + "FROM PHIEU_NHAP_KHO pn "
                + "LEFT JOIN THU_KHO tk ON pn.maNhanVien = tk.maNhanVien "
                + "LEFT JOIN NHA_CUNG_CAP ncc ON pn.maNhaCungCap = ncc.maNhaCungCap "
                + "WHERE pn.maPhieuNhap = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieuNhap);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ThuKho tk = new ThuKho(rs.getString("maNhanVien"), rs.getString("tenThuKho"), rs.getString("sdtThuKho"));
                    NhaCungCap ncc = new NhaCungCap(rs.getString("maNhaCungCap"), rs.getString("tenNhaCungCap"),
                            rs.getString("sdtNCC"), rs.getString("diaChi"), rs.getString("email"));
                    PhieuNhapKho pn = new PhieuNhapKho();
                    pn.setThuKho(tk);
                    pn.setMaPhieuNhap(rs.getString("maPhieuNhap"));
                    pn.setNgayNhap(rs.getDate("ngayNhap"));
                    pn.setTongTienNhap(rs.getDouble("tongTienNhap"));
                    pn.setNhaCungCap(ncc);
                    return pn;
                }
            }
        }
        return null;
    }

    // 2. READ: Lấy chi tiết của một phiếu nhập
    public List<ChiTietPhieuNhap> getChiTietPhieuNhap(String maPhieuNhap) throws SQLException {
        List<ChiTietPhieuNhap> ds = new ArrayList<>();
        String sql = "SELECT ct.maPhieuNhap, ct.maPhuTung, pt.tenPhuTung, pt.donViTinh, ct.soLuongNhap, ct.donGiaNhap "
                + "FROM CHI_TIET_PHIEU_NHAP ct JOIN PHU_TUNG pt ON ct.maPhuTung = pt.maPhuTung "
                + "WHERE ct.maPhieuNhap = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieuNhap);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuNhap ct = new ChiTietPhieuNhap(
                            rs.getString("maPhieuNhap"),
                            rs.getString("maPhuTung"),
                            rs.getInt("soLuongNhap"),
                            rs.getDouble("donGiaNhap"));
                    ct.setTenPhuTung(rs.getString("tenPhuTung"));
                    ct.setDonViTinh(rs.getString("donViTinh"));
                    ds.add(ct);
                }
            }
        }
        return ds;
    }

    // 3. Sinh mã phiếu nhập mới theo dạng của nhóm: PNK01, PNK02, ...
    // Lấy phần số của mọi mã (PNK02 -> 2, PN001 -> 1) rồi tìm số lớn nhất, nên không bị lỗi khi có mã cũ.
    public String sinhMaPhieuNhap() throws SQLException {
        String sql = "SELECT maPhieuNhap FROM PHIEU_NHAP_KHO";
        int max = 0;
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String so = rs.getString(1).replaceAll("[^0-9]", ""); // chỉ giữ chữ số
                if (!so.isEmpty() && Integer.parseInt(so) > max) {
                    max = Integer.parseInt(so);
                }
            }
        }
        return String.format("PNK%02d", max + 1);
    }

    // 4. CREATE: Lập phiếu nhập -> lưu phiếu, lưu chi tiết, cộng tồn kho (1 transaction)
    public boolean taoPhieuNhap(PhieuNhapKho pn, List<ChiTietPhieuNhap> dsChiTiet) throws SQLException {
        PhuTung_DAO phuTungDAO = new PhuTung_DAO();
        Connection conn = connectSQL.getConnection();
        try {
            conn.setAutoCommit(false);

            double tong = 0;
            for (ChiTietPhieuNhap ct : dsChiTiet) {
                tong += ct.tinhTongTien();
            }
            pn.setTongTienNhap(tong);

            java.util.Date ngay = pn.getNgayNhap();
            if (ngay == null) {
                ngay = new java.util.Date();
            }

            String sqlPhieu = "INSERT INTO PHIEU_NHAP_KHO (maPhieuNhap, maNhanVien, ngayNhap, tongTienNhap, maNhaCungCap) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlPhieu)) {
                ps.setString(1, pn.getMaPhieuNhap());
                ps.setString(2, pn.getThuKho().getMaNhanVien());
                ps.setDate(3, new java.sql.Date(ngay.getTime()));
                ps.setDouble(4, tong);
                ps.setString(5, pn.getNhaCungCap().getMaNhaCungCap());
                ps.executeUpdate();
            }

            String sqlCT = "INSERT INTO CHI_TIET_PHIEU_NHAP (maPhieuNhap, maPhuTung, soLuongNhap, donGiaNhap, thanhTien) VALUES (?, ?, ?, ?, ?)";
            for (ChiTietPhieuNhap ct : dsChiTiet) {
                try (PreparedStatement ps = conn.prepareStatement(sqlCT)) {
                    ps.setString(1, pn.getMaPhieuNhap());
                    ps.setString(2, ct.getMaPhuTung());
                    ps.setInt(3, ct.getSoLuongNhap());
                    ps.setDouble(4, ct.getDonGiaNhap());
                    ps.setDouble(5, ct.tinhTongTien());
                    ps.executeUpdate();
                }
                if (!phuTungDAO.tangSoLuongTon(ct.getMaPhuTung(), ct.getSoLuongNhap(), conn)) {
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

    // 4b. UPDATE: Sửa phiếu nhập -> trả lại tồn kho theo chi tiết cũ, ghi chi tiết mới, cộng tồn kho theo chi tiết mới
    // (1 transaction; nếu sửa xong mà có phụ tùng bị âm tồn kho thì hủy hết)
    public boolean capNhatPhieuNhap(PhieuNhapKho pn, List<ChiTietPhieuNhap> dsChiTietMoi) throws SQLException {
        PhuTung_DAO phuTungDAO = new PhuTung_DAO();
        List<ChiTietPhieuNhap> dsChiTietCu = getChiTietPhieuNhap(pn.getMaPhieuNhap());

        Connection conn = connectSQL.getConnection();
        try {
            conn.setAutoCommit(false);

            // 1. Trả lại tồn kho theo chi tiết cũ rồi xóa chi tiết cũ
            for (ChiTietPhieuNhap ct : dsChiTietCu) {
                phuTungDAO.tangSoLuongTon(ct.getMaPhuTung(), -ct.getSoLuongNhap(), conn);
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM CHI_TIET_PHIEU_NHAP WHERE maPhieuNhap = ?")) {
                ps.setString(1, pn.getMaPhieuNhap());
                ps.executeUpdate();
            }

            // 2. Ghi chi tiết mới và cộng tồn kho
            double tong = 0;
            String sqlCT = "INSERT INTO CHI_TIET_PHIEU_NHAP (maPhieuNhap, maPhuTung, soLuongNhap, donGiaNhap, thanhTien) VALUES (?, ?, ?, ?, ?)";
            for (ChiTietPhieuNhap ct : dsChiTietMoi) {
                tong += ct.tinhTongTien();
                try (PreparedStatement ps = conn.prepareStatement(sqlCT)) {
                    ps.setString(1, pn.getMaPhieuNhap());
                    ps.setString(2, ct.getMaPhuTung());
                    ps.setInt(3, ct.getSoLuongNhap());
                    ps.setDouble(4, ct.getDonGiaNhap());
                    ps.setDouble(5, ct.tinhTongTien());
                    ps.executeUpdate();
                }
                if (!phuTungDAO.tangSoLuongTon(ct.getMaPhuTung(), ct.getSoLuongNhap(), conn)) {
                    conn.rollback();
                    return false;
                }
            }

            // 3. Cập nhật thông tin phiếu
            String sqlPhieu = "UPDATE PHIEU_NHAP_KHO SET maNhanVien = ?, maNhaCungCap = ?, tongTienNhap = ? WHERE maPhieuNhap = ?";
            int soDong;
            try (PreparedStatement ps = conn.prepareStatement(sqlPhieu)) {
                ps.setString(1, pn.getThuKho().getMaNhanVien());
                ps.setString(2, pn.getNhaCungCap().getMaNhaCungCap());
                ps.setDouble(3, tong);
                ps.setString(4, pn.getMaPhieuNhap());
                soDong = ps.executeUpdate();
            }
            if (soDong == 0) {
                conn.rollback();
                return false;
            }

            // 4. Kiểm tra không phụ tùng nào bị âm tồn kho sau khi sửa
            List<String> dsMa = new ArrayList<>();
            for (ChiTietPhieuNhap ct : dsChiTietCu) {
                dsMa.add(ct.getMaPhuTung());
            }
            for (ChiTietPhieuNhap ct : dsChiTietMoi) {
                dsMa.add(ct.getMaPhuTung());
            }
            for (String ma : dsMa) {
                try (PreparedStatement ps = conn.prepareStatement("SELECT soLuongTon FROM PHU_TUNG WHERE maPhuTung = ?")) {
                    ps.setString(1, ma);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next() && rs.getInt(1) < 0) {
                            conn.rollback();
                            return false;
                        }
                    }
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

    // 5. DELETE: Xóa phiếu nhập -> trừ lại tồn kho đã cộng (nếu tồn không đủ thì không xóa)
    public boolean xoaPhieuNhap(String maPhieuNhap) throws SQLException {
        PhuTung_DAO phuTungDAO = new PhuTung_DAO();
        List<ChiTietPhieuNhap> dsChiTiet = getChiTietPhieuNhap(maPhieuNhap);

        Connection conn = connectSQL.getConnection();
        try {
            conn.setAutoCommit(false);

            for (ChiTietPhieuNhap ct : dsChiTiet) {
                if (!phuTungDAO.giamSoLuongTon(ct.getMaPhuTung(), ct.getSoLuongNhap(), conn)) {
                    conn.rollback();
                    return false;
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM CHI_TIET_PHIEU_NHAP WHERE maPhieuNhap = ?")) {
                ps.setString(1, maPhieuNhap);
                ps.executeUpdate();
            }
            int soDong;
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM PHIEU_NHAP_KHO WHERE maPhieuNhap = ?")) {
                ps.setString(1, maPhieuNhap);
                soDong = ps.executeUpdate();
            }

            conn.commit();
            return soDong > 0;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}