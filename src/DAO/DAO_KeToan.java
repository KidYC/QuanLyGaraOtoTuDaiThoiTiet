package DAO;
import DB.connectSQL;
import Entity.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DAO_KeToan {

    public List<PhieuSuaChuaDaHoanThanh> layDanhSachPhieuSuaChuaDaHoanThanh()
            throws SQLException {

        List<PhieuSuaChuaDaHoanThanh> danhSach = new ArrayList<>();

        String sql = """
                SELECT
                    PSC.maPhieuSuaChua,
                    PSC.bienSo,
                    KH.soDienThoai,
                    KTV.hoTen,
                    COALESCE(SUM(CTHD.soLuong * CTHD.donGia), 0) AS tongTien,
                    PSC.trangThai
                FROM PHIEU_SUA_CHUA PSC
                JOIN KHACH_HANG KH
                    ON KH.bienSo = PSC.bienSo
                JOIN KY_THUAT_VIEN KTV
                    ON KTV.maKTV = PSC.maKTV
                LEFT JOIN HOA_DON HD
                    ON HD.maPhieuSuaChua = PSC.maPhieuSuaChua
                LEFT JOIN CHI_TIET_HOA_DON CTHD
                    ON CTHD.maHD = HD.maHoaDon
                WHERE PSC.trangThai = N'Hoàn thành'
                GROUP BY
                    PSC.maPhieuSuaChua,
                    PSC.bienSo,
                    KH.soDienThoai,
                    KTV.hoTen,
                    PSC.trangThai
                """;

        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                PhieuSuaChuaDaHoanThanh p = new PhieuSuaChuaDaHoanThanh();

                PhieuSuaChua psc = new PhieuSuaChua();
                psc.setMaPhieuSuaChua(rs.getString("maPhieuSuaChua"));
                p.setMaPhieuSuaChua(psc);
                Xe xe = new Xe();
                xe.setBienSo(rs.getString("bienSo"));
                p.setBienSo(xe);
                p.setSoDienThoai(rs.getString("soDienThoai"));
                p.setHoTenKTV(rs.getString("hoTen"));
                p.setTongTien(rs.getBigDecimal("tongTien"));
                p.setTrangThai(rs.getString("trangThai"));

                danhSach.add(p);
            }
        }

        return danhSach;
    }

    public List<ChiTietHoaDon> laydulieuchitiethoadonvathanhtoan() {
        List<ChiTietHoaDon> ds = new ArrayList<>();

        String sql = """
        
                SELECT
                                         HD.maHoaDon,
                                         PSC.maPhieuSuaChua,
                                         PSC.bienSo,
                                         CTPSC.maPhuTung,
                                         PT.tenPhuTung,
                                         CTPSC.soLuong,
                                         CTPSC.donGia,
                                         CTPSC.soLuong * CTPSC.donGia AS thanhTien
                                     FROM HOA_DON HD
                                     INNER JOIN PHIEU_SUA_CHUA PSC
                                         ON HD.maPhieuSuaChua = PSC.maPhieuSuaChua
                                     LEFT JOIN CHI_TIET_PHIEU_SUA_CHUA CTPSC
                                         ON PSC.maPhieuSuaChua = CTPSC.maPhieuSuaChua
                                     LEFT JOIN PHU_TUNG PT
                                         ON CTPSC.maPhuTung = PT.maPhuTung
                                     ORDER BY HD.maHoaDon DESC
        """;

        try (Connection con = DB.connectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ChiTietHoaDon ct = new ChiTietHoaDon();

                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString("maHoaDon"));
                ct.setMaHoaDon(hd);

                PhieuSuaChua psc = new PhieuSuaChua();
                psc.setMaPhieuSuaChua(rs.getString("maPhieuSuaChua"));
                ct.setMaPhieuSuaChua(psc);

                Xe xe = new Xe();
                xe.setBienSo(rs.getString("bienSo"));
                ct.setBienSo(xe);

                PhuTung pt = new PhuTung();
                pt.setMaPhuTung(rs.getString("maPhuTung"));
                ct.setMaPhuTung(pt);
                ct.setTenPhuTung(rs.getString("tenPhuTung"));
                ct.setSoLuong(rs.getInt("soLuong"));
                ct.setDonGia(rs.getBigDecimal("donGia"));
                ct.setThanhTien(rs.getBigDecimal("thanhTien"));

                ds.add(ct);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds;
    }

    public List<LichSuCongNo> laydulieucongno(
            String maKH,
            java.sql.Date tuNgay,
            java.sql.Date denNgay
    ) throws SQLException {

        List<LichSuCongNo> danhSach = new ArrayList<>();

        String sql = """
                WITH CongNo AS (
                    SELECT
                        maGiaoDich,
                        maKhachHang,
                        ngayGiaoDich,
                        noiDung,
                        ghiNo,
                        ghiCo,
                        SUM(ghiNo - ghiCo) OVER (
                            PARTITION BY maKhachHang
                            ORDER BY ngayGiaoDich, maGiaoDich
                            ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                        ) AS duNoCuoi
                    FROM LICH_SU_CONG_NO
                    WHERE maKhachHang = ?
                )
                SELECT
                    maGiaoDich,
                    maKhachHang,
                    ngayGiaoDich,
                    noiDung,
                    ghiNo,
                    ghiCo,
                    duNoCuoi
                FROM CongNo
                WHERE ngayGiaoDich >= ?
                  AND ngayGiaoDich < DATEADD(DAY, 1, ?)
                ORDER BY ngayGiaoDich DESC, maGiaoDich DESC
                """;

        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, maKH);
            ps.setDate(2, tuNgay);
            ps.setDate(3, denNgay);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LichSuCongNo cn = new LichSuCongNo();

                    cn.setNgayGiaoDich(rs.getTimestamp("ngayGiaoDich").toLocalDateTime());
                    cn.setNoiDung(rs.getString("noiDung"));
                    cn.setGhiNo(rs.getBigDecimal("ghiNo"));
                    cn.setGhiCo(rs.getBigDecimal("ghiCo"));
                    cn.setDuNoCuoi(rs.getBigDecimal("duNoCuoi"));

                    danhSach.add(cn);
                }
            }
        }

        return danhSach;
    }

    public List<DanhSachKHNo> laydulieuKHno() throws SQLException {

        List<DanhSachKHNo> danhSach = new ArrayList<>();

        String sql = """
                SELECT
                    KHN.maKhachHang,
                    KHN.hoTen,
                    KHN.soDienThoai,
                    X.bienSo,
                    KHN.hanMucNo,
                    KHN.tongNo,
                    KHN.trangThai
                FROM danhsachKHno KHN
                JOIN XE X
                    ON KHN.maKhachHang = X.maKhachHang
                """;

        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                DanhSachKHNo khNo = new DanhSachKHNo();

                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                khNo.setMaKhachHang(kh);
                khNo.setHoTen(rs.getString("hoTen"));
                khNo.setSoDienThoai(rs.getString("soDienThoai"));
                khNo.setBienSo(rs.getString("bienSo"));
                khNo.setHanMucNo(rs.getBigDecimal("hanMucNo"));
                khNo.setTongNo(rs.getBigDecimal("tongNo"));
                khNo.setTrangThai(rs.getString("trangThai"));

                danhSach.add(khNo);
            }
        }

        return danhSach;
    }

    public DanhSachKHNo layThongTinKhachHangNo(String maKH)
            throws SQLException {

        String sql = """
            SELECT
                KH.maKhachHang,
                K.hoTen,
                X.bienSo,
                K.soDienThoai,
                K.diaChi,
                KH.hanMucNo,
                KH.tongNo
            FROM danhsachKHno KH
            JOIN KHACH_HANG K
                ON KH.maKhachHang = K.maKhachHang
            JOIN XE X
                ON KH.maKhachHang = X.maKhachHang
            WHERE KH.maKhachHang = ?
            """;

        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, maKH);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    DanhSachKHNo khNo = new DanhSachKHNo();

                    KhachHang kh = new KhachHang();
                    kh.setMaKhachHang(rs.getString("maKhachHang"));

                    khNo.setMaKhachHang(kh);

                    khNo.setHoTen(rs.getString("hoTen"));
                    khNo.setBienSo(rs.getString("bienSo"));
                    khNo.setSoDienThoai(rs.getString("soDienThoai"));
                    khNo.setDiaChi(rs.getString("diaChi"));

                    khNo.setHanMucNo(rs.getBigDecimal("hanMucNo"));
                    khNo.setTongNo(rs.getBigDecimal("tongNo"));

                    return khNo;
                }
            }
        }

        return null;
    }

    public ThongTinThanhToan laythongtinthanhtoan()
            throws SQLException {

        String sql = """
                SELECT
                    COALESCE(SUM(CTPSC.soLuong * CTPSC.donGia), 0) AS tongTienPhuTung,
                    COALESCE(SUM(CTDV.thanhTien), 0) AS tongTienDichVu,
                    COALESCE(SUM(CTPSC.soLuong * CTPSC.donGia), 0)
                    + COALESCE(SUM(CTDV.thanhTien), 0) AS tongCong
                FROM CHI_TIET_PHIEU_SUA_CHUA CTPSC
                LEFT JOIN CHI_TIET_DICH_VU CTDV
                    ON CTPSC.maPhieuSuaChua = CTDV.maPhieuSuaChua
                """;

        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                ThongTinThanhToan tt = new ThongTinThanhToan();

                tt.setTongTienPhuTung(rs.getBigDecimal("tongTienPhuTung"));
                tt.setTongTienDichVu(rs.getBigDecimal("tongTienDichVu"));
                tt.setTongCong(rs.getBigDecimal("tongCong"));

                return tt;
            }
        }

        return null;
    }

    public boolean thanhToanHoaDon(
            String maHoaDon,
            BigDecimal soTien,
            String maPhuongThuc,
            String maNhanVien
    ) throws SQLException {

        String sql = """
                INSERT INTO THANH_TOAN
                    (maThanhToan, maHoaDon, soTienThanhToan,
                     maPhuongThuc, maNhanVien, trangThai)
                VALUES (?, ?, ?, ?, ?, N'Đã thanh toán')
                """;

        try (Connection conn = connectSQL.getConnection()) {

            conn.setAutoCommit(false);

            try {
                String maThanhToan = taoMaThanhToan(conn);

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, maThanhToan);
                    ps.setString(2, maHoaDon);
                    ps.setBigDecimal(3, soTien);
                    ps.setString(4, maPhuongThuc);
                    ps.setString(5, maNhanVien);

                    ps.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public String taoMaThanhToan(Connection conn) throws SQLException {

        String sql = """
                SELECT ISNULL(
                    MAX(TRY_CAST(SUBSTRING(maThanhToan, 3, LEN(maThanhToan)) AS INT)),
                    0
                ) + 1 AS soTiepTheo
                FROM THANH_TOAN
                WHERE maThanhToan LIKE 'TT%'
                """;

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                int soTiepTheo = rs.getInt("soTiepTheo");
                return String.format("TT%02d", soTiepTheo);
            }
        }

        return "TT01";
    }

    public int laySoHoaDonLonNhat() throws SQLException {

        String sql = """
                SELECT ISNULL(
                    MAX(TRY_CAST(SUBSTRING(maHoaDon, 3, 20) AS INT)),
                    0
                )
                FROM HOA_DON
                WHERE maHoaDon LIKE 'HD%'
                """;

        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    public List<String> layMaPhieuSuaChuaDaHoanThanh()
            throws SQLException {

        List<String> danhSach = new ArrayList<>();

        String sql = """
                SELECT maPhieuSuaChua
                FROM PHIEU_SUA_CHUA
                WHERE trangThai = N'Hoàn thành'
                ORDER BY maPhieuSuaChua
                """;

        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                danhSach.add(rs.getString("maPhieuSuaChua"));
            }
        }

        return danhSach;
    }

    public int laySoChiTietHoaDonLonNhat() throws SQLException {

        String sql = """
                SELECT ISNULL(
                    MAX(TRY_CAST(SUBSTRING(maChiTietHD, 5, 20) AS INT)),
                    0
                )
                FROM CHI_TIET_HOA_DON
                WHERE maChiTietHD LIKE 'CTHD%'
                """;

        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }
    public boolean themHoaDon(HoaDon hoaDon) throws SQLException {

        String sql = """
        INSERT INTO HOA_DON
            (maHoaDon, ngayLap, maPhuongThuc,
             maPhieuSuaChua, maNhanVien)
        VALUES (?, ?, ?, ?, ?)
        """;

        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            // 1. Mã hóa đơn
            ps.setString(1, hoaDon.getMaHoaDon());

            // 2. Ngày lập hóa đơn
            if (hoaDon.getNgayLap() != null) {
                ps.setTimestamp(
                        2,
                        Timestamp.valueOf(hoaDon.getNgayLap())
                );
            } else {
                ps.setNull(2, Types.TIMESTAMP);
            }

            // 3. Mã phương thức thanh toán
            if (hoaDon.getMaPhuongThuc() != null) {
                ps.setString(
                        3,
                        hoaDon.getMaPhuongThuc().getMaPhuongThuc()
                );
            } else {
                ps.setNull(3, Types.VARCHAR);
            }

            // 4. Mã phiếu sửa chữa
            if (hoaDon.getMaPhieuSuaChua() != null) {
                ps.setString(
                        4,
                        hoaDon.getMaPhieuSuaChua().getMaPhieuSuaChua()
                );
            } else {
                ps.setNull(4, Types.VARCHAR);
            }

            // 5. Mã nhân viên
            if (hoaDon.getMaNhanVien() != null) {
                ps.setString(
                        5,
                        hoaDon.getMaNhanVien()
                                .getMaNhanVien()
                                .getMaNhanVien()
                );
            } else {
                ps.setNull(5, Types.VARCHAR);
            }

            return ps.executeUpdate() > 0;
        }
    }
    public List<ChiTietHoaDon> layChiTietTheoPhieuSuaChua(String maPSC)
            throws SQLException {

        List<ChiTietHoaDon> ds = new ArrayList<>();

        String sql = """
            SELECT
                PT.tenPhuTung AS noiDung,
                CTPSC.soLuong,
                CTPSC.donGia,
                CTPSC.soLuong * CTPSC.donGia AS thanhTien
            FROM CHI_TIET_PHIEU_SUA_CHUA CTPSC
            JOIN PHU_TUNG PT
                ON CTPSC.maPhuTung = PT.maPhuTung
            WHERE CTPSC.maPhieuSuaChua = ?

            UNION ALL

            SELECT
                DV.tenDichVu AS noiDung,
                CTDV.soLuong,
                CTDV.donGia,
                CTDV.soLuong * CTDV.donGia AS thanhTien
            FROM CHI_TIET_DICH_VU CTDV
            JOIN DICH_VU DV
                ON CTDV.maDichVu = DV.maDichVu
            WHERE CTDV.maPhieuSuaChua = ?
            """;

        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, maPSC);
            ps.setString(2, maPSC);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    ChiTietHoaDon ct = new ChiTietHoaDon();

                    ct.setNoiDung(rs.getString("noiDung"));
                    ct.setSoLuong(rs.getInt("soLuong"));
                    ct.setDonGia(rs.getBigDecimal("donGia"));
                    ct.setThanhTien(rs.getBigDecimal("thanhTien"));

                    ds.add(ct);
                }
            }
        }

        return ds;
    }
    public String layPhuongThucTheoHoaDon(String maHoaDon)
            throws SQLException {

        String sql = """
        SELECT PT.tenPhuongThuc
        FROM HOA_DON HD
        INNER JOIN PHUONG_THUC_THANH_TOAN PT
            ON HD.maPhuongThuc = PT.maPhuongThuc
        WHERE HD.maHoaDon = ?
        """;

        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, maHoaDon);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("tenPhuongThuc");
                }
            }
        }
        return "";
    }
}