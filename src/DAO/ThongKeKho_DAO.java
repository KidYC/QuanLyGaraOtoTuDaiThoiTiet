package DAO;

import DB.connectSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThongKeKho_DAO {

    public static class ThongKeKhoModel {
        private String maPhuTung;
        private String tenPhuTung;
        private String donViTinh;
        private int soLuongTon;
        private double donGiaNhap;
        private double donGiaBan;
        private String trangThaiTon;

        public ThongKeKhoModel(String maPhuTung, String tenPhuTung, String donViTinh, int soLuongTon, double donGiaNhap, double donGiaBan, String trangThaiTon) {
            this.maPhuTung = maPhuTung;
            this.tenPhuTung = tenPhuTung;
            this.donViTinh = donViTinh;
            this.soLuongTon = soLuongTon;
            this.donGiaNhap = donGiaNhap;
            this.donGiaBan = donGiaBan;
            this.trangThaiTon = trangThaiTon;
        }

        public String getMaPhuTung() { return maPhuTung; }
        public String getTenPhuTung() { return tenPhuTung; }
        public String getDonViTinh() { return donViTinh; }
        public int getSoLuongTon() { return soLuongTon; }
        public double getDonGiaNhap() { return donGiaNhap; }
        public double getDonGiaBan() { return donGiaBan; }
        public String getTrangThaiTon() { return trangThaiTon; }
    }

    public List<ThongKeKhoModel> layDanhSachThongKeKho() throws SQLException {
        List<ThongKeKhoModel> list = new ArrayList<>();
        String sql = "SELECT maPhuTung, tenPhuTung, donViTinh, soLuongTon, donGiaNhap, donGiaBan, trangThaiTon FROM v_ThongKeKho";

        try (Connection con = connectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ThongKeKhoModel(
                        rs.getString("maPhuTung"),
                        rs.getString("tenPhuTung"),
                        rs.getString("donViTinh"),
                        rs.getInt("soLuongTon"),
                        rs.getDouble("donGiaNhap"),
                        rs.getDouble("donGiaBan"),
                        rs.getString("trangThaiTon")
                ));
            }
        }
        return list;
    }
}