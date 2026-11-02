package DAO;

import DB.connectSQL;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO_KeToan {
     public List<Object[]> layDanhSachPhieuSuaChuaDaHoanThanh() throws SQLException{
         List<Object[]> danhSach = new ArrayList<>();
        String sql = """
                select PSC.maPhieuSuaChua,PSC.bienSo,KH.soDienThoai,KTV.hoTen,(CTHD.soLuong*CTHD.donGia)as tongtien,PSC.trangThai
                from PHIEU_SUA_CHUA PSC
                join KHACH_HANG KH on KH.bienSo=PSC.bienSo
                join KY_THUAT_VIEN KTV on KTV.maKTV= PSC.maKTV
                join HOA_DON HD on HD.maPhieuSuaChua=PSC.maPhieuSuaChua
                join CHI_TIET_HOA_DON CTHD on CTHD.maHD = HD.maHoaDon
                where PSC.trangThai = N'Hoàn thành'
                """;
        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ){
            while (rs.next()){
                Object[] dong = {
                        rs.getString("maPhieuSuaChua"),
                        rs.getString("bienSo"),
                        rs.getString("soDienThoai"),
                        rs.getString("hoTen"),
                        rs.getBigDecimal("tongtien"),
                        rs.getString("trangThai")
                };
                danhSach.add(dong);
            }
        }
        return danhSach;
     }
     public List<Object[]> laydulieuchitiethoadonvathanhtoan() throws SQLException{
         List<Object[]> danhsach = new ArrayList<>();
         String sql = """
                 SELECT
                 HD.maHoaDon,
                 PSC.maPhieuSuaChua,
                 PSC.bienSo,
                 CTPSC.maPhuTung,
                 PT.tenPhuTung,
                 CTPSC.soLuong,
                 CTPSC.donGia AS donGia,
                 CTPSC.soLuong * CTPSC.donGia AS thanhTien
                 FROM HOA_DON HD
                 INNER JOIN PHIEU_SUA_CHUA PSC
                 ON HD.maPhieuSuaChua = PSC.maPhieuSuaChua
                 INNER JOIN CHI_TIET_PHIEU_SUA_CHUA CTPSC
                 ON PSC.maPhieuSuaChua = CTPSC.maPhieuSuaChua
                 INNER JOIN PHU_TUNG PT
                 ON CTPSC.maPhuTung = PT.maPhuTung
                 ORDER BY HD.maHoaDon
                 """;
         try(
                 Connection conn = connectSQL.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery();
         ){
             while (rs.next()){
                 Object[] dong = {
                         rs.getString("maHoaDon"),
                         rs.getString("maPhieuSuaChua"),
                         rs.getString("bienSo"),
                         rs.getString("maPhuTung"),
                         rs.getString("tenPhuTung"),
                         rs.getString("soLuong"),
                         rs.getBigDecimal("donGia"),
                         rs.getBigDecimal("thanhTien")
                 };
                 danhsach.add(dong);
             }
         }
         return danhsach;
     }
    public List<Object[]> laydulieucongno(
            String maKH,
            java.sql.Date tuNgay,
            java.sql.Date denNgay
    ) throws SQLException {

        List<Object[]> danhsach = new ArrayList<>();

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
                ngayGiaoDich,
                noiDung,
                ghiNo,
                ghiCo,
                duNoCuoi
                FROM CongNo
                WHERE ngayGiaoDich >= ?
                AND ngayGiaoDich < DATEADD(DAY, 1, ?)
                ORDER BY ngayGiaoDich DESC, maGiaoDich DESC;
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
                    Object[] dong = {
                            rs.getString("ngayGiaoDich"),
                            rs.getString("noiDung"),
                            rs.getBigDecimal("ghiNo"),
                            rs.getBigDecimal("ghiCo"),
                            rs.getBigDecimal("duNoCuoi")
                    };

                    danhsach.add(dong);
                }
            }
        }

        return danhsach;
    }
     public List<Object[]> laydulieuKHno() throws SQLException{
         List<Object[]> danhsach = new ArrayList<>();
         String sql = """
                 select
                 KHN.maKhachHang,
                 KHN.hoTen,
                 KHN.soDienThoai,
                 X.bienSo,
                 KHN.hanMucNo,
                 KHN.tongNo,
                 KHN.trangThai
                 from danhsachKHno KHN
                 join XE X
                 on KHN.maKhachHang=X.maKhachHang
                 """;
         try(
                 Connection conn = connectSQL.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery();
                 ){

             while (rs.next()){
                 Object[] dong = {
                         rs.getString("maKhachHang"),
                         rs.getString("hoTen"),
                         rs.getString("soDienThoai"),
                         rs.getString("bienSo"),
                         rs.getBigDecimal("hanMucNo"),
                         rs.getBigDecimal("tongNo"),
                         rs.getString("trangThai")
                 };
                 danhsach.add(dong);
             }
             return danhsach;
         }
     }
    public Object[] layThongTinKhachHangNo(String maKH) throws SQLException {
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
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maKH);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Object[]{
                            rs.getString("maKhachHang"),
                            rs.getString("bienSo"),
                            rs.getString("hoTen"),
                            rs.getString("soDienThoai"),
                            rs.getString("diaChi"),
                            rs.getBigDecimal("hanMucNo"),
                            rs.getBigDecimal("tongNo")
                    };
                }
            }
        }
        return null;
    }
    public Object[] laythongtinthanhtoan () throws SQLException{
         String sql = """
                 select 
                 sum(PT.donGiaBan) as tongtienphutung,
                 CTDV.thanhTien,
                 sum(PT.donGiaBan) + CTDV.thanhTien as tongcong
                 from CHI_TIET_PHIEU_SUA_CHUA CTPSC
                 join CHI_TIET_DICH_VU CTDV 
                 on CTPSC.maPhieuSuaChua=CTDV.maPhieuSuaChua
                 join PHU_TUNG PT
                 on PT.maPhuTung=CTPSC.maPhuTung
                 group by CTDV.thanhTien
                 """;
         try (
                 Connection conn = connectSQL.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)){
             try(ResultSet rs = ps.executeQuery()){
                 if (rs.next()){
                     return new Object[]{
                             rs.getBigDecimal("tongtienphutung"),
                             rs.getBigDecimal("thanhTien"),
                             rs.getBigDecimal("tongcong")
                     };
                 }
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
            (maThanhToan, maHoaDon, ngayThanhToan,
             soTienThanhToan, maPhuongThuc, maNhanVien, trangThai)
        VALUES (?, ?, GETDATE(), ?, ?, ?, N'Đã thanh toán')
        """;

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "TT" + System.currentTimeMillis());
            ps.setString(2, maHoaDon);
            ps.setBigDecimal(3, soTien);
            ps.setString(4, maPhuongThuc);
            ps.setString(5, maNhanVien);

            return ps.executeUpdate() > 0;
        }
    }


}
