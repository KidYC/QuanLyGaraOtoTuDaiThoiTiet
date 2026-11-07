package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.connectSQL;
import Entity.DongXe;
import Entity.HangXe;
import Entity.KhachHang;
import Entity.Xe;

public class Xe_DAO {

    public List<Xe> getAllXe() throws SQLException {
        List<Xe> ds = new ArrayList<>();
        String sql = "SELECT x.bienSo, x.namSanXuat, " +
                "d.maDongXe, d.tenDongXe, " +
                "h.maHangXe, h.tenHangXe, h.xuatXu, " +
                "k.maKhachHang " +
                "FROM XE x " +
                "LEFT JOIN DONG_XE d ON x.maDongXe = d.maDongXe " +
                "LEFT JOIN HANG_XE h ON d.maHangXe = h.maHangXe " +
                "LEFT JOIN KHACH_HANG k ON x.maKhachHang = k.maKhachHang";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HangXe hx = new HangXe(
                        rs.getString("maHangXe"),
                        rs.getString("tenHangXe"),
                        rs.getString("xuatXu")
                );

                DongXe dx = new DongXe(
                        rs.getString("maDongXe"),
                        rs.getString("tenDongXe"),
                        hx
                );

                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));

                ds.add(new Xe(
                        rs.getString("bienSo"),
                        dx,
                        rs.getInt("namSanXuat"),
                        kh
                ));
            }
        }
        return ds;
    }

    public Xe getXeByBienSo(String bienSo) throws SQLException {
        String sql = "SELECT x.bienSo, x.namSanXuat, " +
                "d.maDongXe, d.tenDongXe, " +
                "h.maHangXe, h.tenHangXe, h.xuatXu, " +
                "k.maKhachHang " +
                "FROM XE x " +
                "LEFT JOIN DONG_XE d ON x.maDongXe = d.maDongXe " +
                "LEFT JOIN HANG_XE h ON d.maHangXe = h.maHangXe " +
                "LEFT JOIN KHACH_HANG k ON x.maKhachHang = k.maKhachHang " +
                "WHERE x.bienSo = ?";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bienSo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HangXe hx = new HangXe(
                            rs.getString("maHangXe"),
                            rs.getString("tenHangXe"),
                            rs.getString("xuatXu")
                    );

                    DongXe dx = new DongXe(
                            rs.getString("maDongXe"),
                            rs.getString("tenDongXe"),
                            hx
                    );

                    KhachHang kh = new KhachHang();
                    kh.setMaKhachHang(rs.getString("maKhachHang"));

                    return new Xe(
                            rs.getString("bienSo"),
                            dx,
                            rs.getInt("namSanXuat"),
                            kh
                    );
                }
            }
        }
        return null;
    }

    public List<Xe> getXeByMaKH(String maKH) throws SQLException {
        List<Xe> ds = new ArrayList<>();
        String sql = "SELECT x.bienSo, x.namSanXuat, " +
                "d.maDongXe, d.tenDongXe, " +
                "h.maHangXe, h.tenHangXe, h.xuatXu, " +
                "k.maKhachHang " +
                "FROM XE x " +
                "LEFT JOIN DONG_XE d ON x.maDongXe = d.maDongXe " +
                "LEFT JOIN HANG_XE h ON d.maHangXe = h.maHangXe " +
                "LEFT JOIN KHACH_HANG k ON x.maKhachHang = k.maKhachHang " +
                "WHERE x.maKhachHang = ?";

        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKH);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HangXe hx = new HangXe(
                            rs.getString("maHangXe"),
                            rs.getString("tenHangXe"),
                            rs.getString("xuatXu")
                    );

                    DongXe dx = new DongXe(
                            rs.getString("maDongXe"),
                            rs.getString("tenDongXe"),
                            hx
                    );

                    KhachHang kh = new KhachHang();
                    kh.setMaKhachHang(rs.getString("maKhachHang"));

                    ds.add(new Xe(
                            rs.getString("bienSo"),
                            dx,
                            rs.getInt("namSanXuat"),
                            kh
                    ));
                }
            }
        }
        return ds;
    }

    public boolean themXe(Xe xe) throws SQLException {
        // CSDL mới chỉ lưu mã dòng xe maDongXe trong bảng XE
        String sql = "INSERT INTO XE (bienSo, maDongXe, namSanXuat, maKhachHang) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, xe.getBienSo());

            String maDongXeStr = (xe.getDongXe() != null) ? xe.getDongXe().getMaDongXe() : null;
            ps.setString(2, maDongXeStr);

            ps.setInt(3, xe.getNamSanXuat());

            String maKhachHangStr = (xe.getMaKhachHang() != null) ? xe.getMaKhachHang().getMaKhachHang() : null;
            ps.setString(4, maKhachHangStr);

            return ps.executeUpdate() > 0;
        }
    }
}