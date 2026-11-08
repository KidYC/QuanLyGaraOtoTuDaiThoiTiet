package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.*;

public class PhieuSuaChua_DAO {

    private PhieuSuaChua docPhieuSuaChua(ResultSet rs) throws SQLException {
        // Khởi tạo các đối tượng liên kết từ dữ liệu truy vấn
        Xe xe = new Xe();
        xe.setBienSo(rs.getString("bienSo"));

        KyThuatVien ktv = new KyThuatVien();
        ktv.setMaKTV(rs.getString("maKTV"));

        PhieuTiepNhan ptn = new PhieuTiepNhan();
        ptn.setMaPhieuTiepNhan(rs.getString("maPhieuTiepNhan"));

        PhieuSuaChua psc = new PhieuSuaChua(
                rs.getString("maPhieuSuaChua"),
                rs.getDate("ngayLap"),
                rs.getDate("ngayIn"),
                rs.getString("trangThai"),
                xe,
                ktv,
                ptn,
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
                String maKTV = (psc.getKyThuatVien() != null) ? psc.getKyThuatVien().getMaKTV() : "";
                psKTV.setString(1, maKTV);
                psKTV.setString(2, maKTV);
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

            String sqlUpdate = "UPDATE PHIEU_SUA_CHUA SET trangThai = N'Đang sửa chữa' WHERE maPhieuSuaChua = ? AND trangThai = N'Chờ xuất kho'";
            int soDong;
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setString(1, maPhieuSC);
                soDong = ps.executeUpdate();
            }
            if (soDong == 0) {
                conn.rollback();
                return false;
            }

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

    public String getMaPhieuSuaChuaTuDong() throws SQLException {
        String prefix = "PSC";
        int nextId = 1;
        String sql = "SELECT MAX(maPhieuSuaChua) FROM PHIEU_SUA_CHUA";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String maxMa = rs.getString(1);
                if (maxMa != null && maxMa.startsWith(prefix)) {
                    int currentId = Integer.parseInt(maxMa.substring(prefix.length()));
                    nextId = currentId + 1;
                }
            }
        }
        return String.format("%s%02d", prefix, nextId);
    }

    public boolean luuBaoGiaSuaChua(String maPhieuSuaChua, String bienSo, List<ChiTietBaoGia_DTO> danhSachChiTiet) {
        Connection conn = null;
        try {
            conn = connectSQL.getConnection();
            conn.setAutoCommit(false);

            String sqlCheck = "SELECT COUNT(*) FROM PHIEU_SUA_CHUA WHERE maPhieuSuaChua = ?";
            boolean daTonTai = false;
            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {
                psCheck.setString(1, maPhieuSuaChua);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        daTonTai = true;
                    }
                }
            }

            if (!daTonTai) {
                String maPTN = null;
                String sqlGetPTN = "SELECT TOP 1 maPhieuTiepNhan FROM PHIEU_TIEP_NHAN WHERE bienSo = ? ORDER BY ngayTiepNhan DESC";
                try (PreparedStatement psPTN = conn.prepareStatement(sqlGetPTN)) {
                    psPTN.setString(1, bienSo);
                    try (ResultSet rs = psPTN.executeQuery()) {
                        if (rs.next()) {
                            maPTN = rs.getString(1);
                        }
                    }
                }

                String sqlPhieuSC = "INSERT INTO PHIEU_SUA_CHUA (maPhieuSuaChua, trangThai, bienSo, maPhieuTiepNhan, ngayLap) VALUES (?, N'Chờ xác nhận báo giá', ?, ?, GETDATE())";
                try (PreparedStatement ps1 = conn.prepareStatement(sqlPhieuSC)) {
                    ps1.setString(1, maPhieuSuaChua);
                    ps1.setString(2, bienSo);
                    ps1.setString(3, maPTN);
                    ps1.executeUpdate();
                }
            } else {
                String sqlUpdateSC = "UPDATE PHIEU_SUA_CHUA SET trangThai = N'Chờ xác nhận báo giá' WHERE maPhieuSuaChua = ?";
                try (PreparedStatement psUp = conn.prepareStatement(sqlUpdateSC)) {
                    psUp.setString(1, maPhieuSuaChua);
                    psUp.executeUpdate();
                }
            }

            try (PreparedStatement psDelDV = conn.prepareStatement("DELETE FROM CHI_TIET_DICH_VU WHERE maPhieuSuaChua = ?");
                 PreparedStatement psDelPT = conn.prepareStatement("DELETE FROM DANH_SACH_PHU_TUNG WHERE maPhieuSuaChua = ?")) {
                psDelDV.setString(1, maPhieuSuaChua);
                psDelPT.setString(1, maPhieuSuaChua);
                psDelDV.executeUpdate();
                psDelPT.executeUpdate();
            }

            String sqlDichVu = "INSERT INTO CHI_TIET_DICH_VU (maPhieuSuaChua, maDichVu, soLuong, donGia) VALUES (?, ?, ?, ?)";
            String sqlPhuTung = "INSERT INTO DANH_SACH_PHU_TUNG (maPhieuSuaChua, maPhuTung, soLuongPhuTung, donGiaBan) VALUES (?, ?, ?, ?)";

            try (PreparedStatement psDV = conn.prepareStatement(sqlDichVu);
                 PreparedStatement psPT = conn.prepareStatement(sqlPhuTung)) {

                for (ChiTietBaoGia_DTO ct : danhSachChiTiet) {
                    if (ct.getLoai().equalsIgnoreCase("Dịch vụ")) {
                        psDV.setString(1, maPhieuSuaChua);
                        psDV.setString(2, ct.getMaHangMuc());
                        psDV.setInt(3, ct.getSoLuong());
                        psDV.setDouble(4, ct.getDonGia());
                        psDV.addBatch();
                    } else if (ct.getLoai().equalsIgnoreCase("Phụ tùng")) {
                        psPT.setString(1, maPhieuSuaChua);
                        psPT.setString(2, ct.getMaHangMuc());
                        psPT.setInt(3, ct.getSoLuong());
                        psPT.setDouble(4, ct.getDonGia());
                        psPT.addBatch();
                    }
                }
                psDV.executeBatch();
                psPT.executeBatch();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
    }

    public List<XeDaHoanThanh_DTO> getDanhSachXeDaHoanThanh() throws SQLException {
        List<XeDaHoanThanh_DTO> list = new ArrayList<>();
        String sql = "SELECT p.maPhieuSuaChua, k.hoTen, x.bienSo, (h.tenHangXe + ' ' + d.tenDongXe) AS loaiXe, \n" +
                "p.ngayDuKienHoanThanh, p.trangThai \n" +
                "FROM PHIEU_SUA_CHUA p \n" +
                "JOIN XE x ON p.bienSo = x.bienSo \n" +
                "LEFT JOIN DONG_XE d ON x.maDongXe = d.maDongXe \n" +
                "LEFT JOIN HANG_XE h ON d.maHangXe = h.maHangXe \n" +
                "JOIN KHACH_HANG k ON x.maKhachHang = k.maKhachHang \n" +
                "WHERE p.trangThai = N'Hoàn thành' OR p.trangThai = N'Chờ bàn giao'";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                XeDaHoanThanh_DTO dto = new XeDaHoanThanh_DTO(
                        rs.getString("maPhieuSuaChua"),
                        rs.getString("hoTen"),
                        rs.getString("bienSo"),
                        rs.getString("loaiXe"),
                        rs.getTimestamp("ngayDuKienHoanThanh"),
                        rs.getString("trangThai")
                );
                list.add(dto);
            }
        }
        return list;
    }

    public List<ChiTietBaoGia_DTO> getChiTietBaoGiaByMaPhieu(String maPhieuSuaChua) throws SQLException {
        List<ChiTietBaoGia_DTO> list = new ArrayList<>();

        String sql = "SELECT N'Dịch vụ' AS loai, ctdv.maDichVu AS maHangMuc, dv.tenDichVu AS tenHangMuc, " +
                "ctdv.soLuong AS soLuong, ctdv.donGia AS donGia " +
                "FROM CHI_TIET_DICH_VU ctdv " +
                "JOIN DICH_VU dv ON ctdv.maDichVu = dv.maDichVu " +
                "WHERE ctdv.maPhieuSuaChua = ? " +
                "UNION ALL " +
                "SELECT N'Phụ tùng' AS loai, dspt.maPhuTung AS maHangMuc, pt.tenPhuTung AS tenHangMuc, " +
                "dspt.soLuongPhuTung AS soLuong, dspt.donGiaBan AS donGia " +
                "FROM DANH_SACH_PHU_TUNG dspt " +
                "JOIN PHU_TUNG pt ON dspt.maPhuTung = pt.maPhuTung " +
                "WHERE dspt.maPhieuSuaChua = ?";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPhieuSuaChua);
            ps.setString(2, maPhieuSuaChua);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietBaoGia_DTO item = new ChiTietBaoGia_DTO(
                            rs.getString("loai"),
                            rs.getString("maHangMuc"),
                            rs.getString("tenHangMuc"),
                            rs.getInt("soLuong"),
                            rs.getDouble("donGia")
                    );
                    list.add(item);
                }
            }
        }
        return list;
    }

    public boolean xoaHangMucBaoGia(String maPhieuSuaChua, String loai, String maHangMuc) throws SQLException {
        String sql = "";
        if (loai.equalsIgnoreCase("Dịch vụ")) {
            sql = "DELETE FROM CHI_TIET_DICH_VU WHERE maPhieuSuaChua = ? AND maDichVu = ?";
        } else if (loai.equalsIgnoreCase("Phụ tùng")) {
            sql = "DELETE FROM CHI_TIET_PHIEU_SUA_CHUA WHERE maPhieuSuaChua = ? AND maPhuTung = ?";
        } else {
            return false;
        }

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieuSuaChua);
            ps.setString(2, maHangMuc);
            return ps.executeUpdate() > 0;
        }
    }

    public List<ChiTietBaoHanh_DTO> getChiTietBaoHanhByMaPhieu(String maPhieuSuaChua) throws SQLException {
        List<ChiTietBaoHanh_DTO> list = new ArrayList<>();

        String sql = "SELECT ctdv.maDichVu AS maHangMuc, dv.tenDichVu AS tenHangMuc, " +
                "dv.thoiGianBaoHanh, p.ngayLap " +
                "FROM CHI_TIET_DICH_VU ctdv " +
                "JOIN DICH_VU dv ON ctdv.maDichVu = dv.maDichVu " +
                "JOIN PHIEU_SUA_CHUA p ON ctdv.maPhieuSuaChua = p.maPhieuSuaChua " +
                "WHERE ctdv.maPhieuSuaChua = ? AND dv.thoiGianBaoHanh > 0";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPhieuSuaChua);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String ma = rs.getString("maHangMuc");
                    String ten = rs.getString("tenHangMuc");
                    int thoiGianBaoHanh = rs.getInt("thoiGianBaoHanh");

                    java.sql.Timestamp ngayLap = rs.getTimestamp("ngayLap");
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    if (ngayLap != null) {
                        cal.setTimeInMillis(ngayLap.getTime());
                    }
                    cal.add(java.util.Calendar.MONTH, thoiGianBaoHanh);
                    java.util.Date ngayHetHan = cal.getTime();

                    String trangThai = "Đang hiệu lực";
                    if (ngayHetHan.before(new java.util.Date())) {
                        trangThai = "Hết hạn";
                    }

                    ChiTietBaoHanh_DTO item = new ChiTietBaoHanh_DTO(
                            ma, ten, thoiGianBaoHanh, ngayHetHan, trangThai
                    );
                    list.add(item);
                }
            }
        }
        return list;
    }

    public ResultSet getThongTinBanGiao(String maPhieuSuaChua) throws SQLException {
        String sql = "SELECT k.hoTen, p.ngayDuKienHoanThanh " +
                "FROM PHIEU_SUA_CHUA p " +
                "JOIN XE x ON p.bienSo = x.bienSo " +
                "JOIN KHACH_HANG k ON x.maKhachHang = k.maKhachHang " +
                "WHERE p.maPhieuSuaChua = ?";
        Connection conn = connectSQL.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, maPhieuSuaChua);
        return ps.executeQuery();
    }

    public List<ChiTietBanGiao_DTO> getChiTietBanGiaoByMaPhieu(String maPhieu) throws SQLException {
        List<ChiTietBanGiao_DTO> list = new ArrayList<>();

        String sql =
                // Dịch vụ
                "SELECT N'Dịch vụ' AS loai, dv.tenDichVu AS tenHangMuc, " +
                        "       ct.soLuong AS soLuong, (ct.soLuong * ct.donGia) AS thanhTien, " +
                        "       CASE WHEN dv.thoiGianBaoHanh > 0 " +
                        "            THEN CAST(dv.thoiGianBaoHanh AS NVARCHAR(10)) + N' tháng' " +
                        "            ELSE N'Không' END AS baoHanh " +
                        "FROM CHI_TIET_DICH_VU ct " +
                        "JOIN DICH_VU dv ON ct.maDichVu = dv.maDichVu " +
                        "WHERE ct.maPhieuSuaChua = ? " +
                        "UNION ALL " +
                        // Phụ tùng
                        "SELECT N'Phụ tùng', pt.tenPhuTung, " +
                        "       ds.soLuongPhuTung, (ds.soLuongPhuTung * ds.donGiaBan), N'Không' " +
                        "FROM DANH_SACH_PHU_TUNG ds " +
                        "JOIN PHU_TUNG pt ON ds.maPhuTung = pt.maPhuTung " +
                        "WHERE ds.maPhieuSuaChua = ?";

        try (PreparedStatement ps = connectSQL.getConnection().prepareStatement(sql)) {
            ps.setString(1, maPhieu);
            ps.setString(2, maPhieu);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ChiTietBanGiao_DTO(
                            rs.getString("loai"),
                            rs.getString("tenHangMuc"),
                            rs.getInt("soLuong"),
                            rs.getDouble("thanhTien"),
                            rs.getString("baoHanh")));
                }
            }
        }
        return list;
    }

    public boolean capNhatTrangThaiDaBanGiao(String maPhieuSuaChua) throws SQLException {
        String sql = "UPDATE PHIEU_SUA_CHUA SET trangThai = N'Đã bàn giao' WHERE maPhieuSuaChua = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieuSuaChua);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean capNhatTrangThaiPhieu(String maPhieuSuaChua, String trangThaiMoi) {
        String sql = "UPDATE PHIEU_SUA_CHUA SET trangThai = ? WHERE maPhieuSuaChua = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangThaiMoi);
            ps.setString(2, maPhieuSuaChua);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatTrangThaiHoanThanh(String maPhieuSuaChua) throws SQLException {
        String sql = "UPDATE PHIEU_SUA_CHUA SET trangThai = N'Hoàn thành' WHERE maPhieuSuaChua = ?";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieuSuaChua);
            return ps.executeUpdate() > 0;
        }
    }
}