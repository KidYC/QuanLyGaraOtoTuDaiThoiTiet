package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;

public class ThongKeKho_DAO {

    public List<Object[]> thongKeTonKho() throws SQLException {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT pt.maPhuTung, pt.tenPhuTung, pt.donViTinh, k.tenKho, pt.soLuongTon, pt.donGiaNhap "
                + "FROM PHU_TUNG pt "
                + "LEFT JOIN KHO k ON pt.maKho = k.maKho "
                + "ORDER BY pt.maPhuTung";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new Object[]{
                        rs.getString("maPhuTung"),
                        rs.getString("tenPhuTung"),
                        rs.getString("donViTinh"),
                        rs.getString("tenKho"),
                        rs.getInt("soLuongTon"),
                        rs.getDouble("donGiaNhap")
                });
            }
        }
        return ds;
    }

    public List<Object[]> thongKeTheoKho() throws SQLException {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT k.maKho, k.tenKho, COUNT(pt.maPhuTung) AS soMatHang, "
                + "COALESCE(SUM(pt.soLuongTon), 0) AS tongTon, "
                + "COALESCE(SUM(pt.soLuongTon * pt.donGiaNhap), 0) AS giaTriTon "
                + "FROM KHO k "
                + "LEFT JOIN PHU_TUNG pt ON pt.maKho = k.maKho "
                + "GROUP BY k.maKho, k.tenKho "
                + "ORDER BY k.maKho";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new Object[]{
                        rs.getString("maKho"),
                        rs.getString("tenKho"),
                        rs.getInt("soMatHang"),
                        rs.getInt("tongTon"),
                        rs.getDouble("giaTriTon")
                });
            }
        }
        return ds;
    }

    public List<Object[]> thongKeNhapKho(Timestamp tuNgay, Timestamp denNgay) throws SQLException {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT ct.maPhuTung, pt.tenPhuTung, pt.donViTinh, "
                + "COUNT(DISTINCT pn.maPhieuNhap) AS soPhieu, "
                + "SUM(ct.soLuongNhap) AS tongSoLuong, "
                + "SUM(ct.soLuongNhap * ct.donGiaNhap) AS tongTien "
                + "FROM CHI_TIET_PHIEU_NHAP ct "
                + "JOIN PHIEU_NHAP_KHO pn ON ct.maPhieuNhap = pn.maPhieuNhap "
                + "JOIN PHU_TUNG pt ON ct.maPhuTung = pt.maPhuTung "
                + "WHERE pn.ngayNhap >= ? AND pn.ngayNhap < ? "
                + "GROUP BY ct.maPhuTung, pt.tenPhuTung, pt.donViTinh "
                + "ORDER BY tongSoLuong DESC";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, tuNgay);
            ps.setTimestamp(2, denNgay);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ds.add(new Object[]{
                            rs.getString("maPhuTung"),
                            rs.getString("tenPhuTung"),
                            rs.getString("donViTinh"),
                            rs.getInt("soPhieu"),
                            rs.getInt("tongSoLuong"),
                            rs.getDouble("tongTien")
                    });
                }
            }
        }
        return ds;
    }

    public List<Object[]> thongKeXuatKho(Timestamp tuNgay, Timestamp denNgay) throws SQLException {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT dspt.maPhuTung, pt.tenPhuTung, pt.donViTinh, "
                + "COUNT(DISTINCT psc.maPhieuSuaChua) AS soPhieu, "
                + "SUM(dspt.soLuong) AS tongSoLuong, "
                + "SUM(dspt.soLuong * dspt.donGia) AS tongTien "
                + "FROM CHI_TIET_PHIEU_SUA_CHUA dspt "
                + "JOIN PHIEU_SUA_CHUA psc ON dspt.maPhieuSuaChua = psc.maPhieuSuaChua "
                + "JOIN PHU_TUNG pt ON dspt.maPhuTung = pt.maPhuTung "
                // Chỉ tính phiếu đã qua bước xuất kho: bỏ phiếu còn chờ xác nhận báo giá, chờ xuất kho, bị từ chối
                + "WHERE psc.trangThai NOT IN (N'Chờ xác nhận báo giá', N'Chờ xuất kho', N'Từ chối', N'Từ chối xuất kho') "
                + "AND psc.ngayLap >= ? AND psc.ngayLap < ? "
                + "GROUP BY dspt.maPhuTung, pt.tenPhuTung, pt.donViTinh "
                + "ORDER BY tongSoLuong DESC";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, tuNgay);
            ps.setTimestamp(2, denNgay);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ds.add(new Object[]{
                            rs.getString("maPhuTung"),
                            rs.getString("tenPhuTung"),
                            rs.getString("donViTinh"),
                            rs.getInt("soPhieu"),
                            rs.getInt("tongSoLuong"),
                            rs.getDouble("tongTien")
                    });
                }
            }
        }
        return ds;
    }
}
