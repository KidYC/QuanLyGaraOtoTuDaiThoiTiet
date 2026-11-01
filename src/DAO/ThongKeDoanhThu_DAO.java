package DAO;

import DB.connectSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDoanhThu_DAO {

    public static class ThongKeDoanhThuModel {
        private int thang;
        private int nam;
        private int tongSoHoaDon;
        private double doanhThu;

        public ThongKeDoanhThuModel(int thang, int nam, int tongSoHoaDon, double doanhThu) {
            this.thang = thang;
            this.nam = nam;
            this.tongSoHoaDon = tongSoHoaDon;
            this.doanhThu = doanhThu;
        }

        public int getThang() { return thang; }
        public int getNam() { return nam; }
        public int getTongSoHoaDon() { return tongSoHoaDon; }
        public double getDoanhThu() { return doanhThu; }
    }

    public List<ThongKeDoanhThuModel> layDanhSachDoanhThu() throws SQLException {
        List<ThongKeDoanhThuModel> list = new ArrayList<>();
        String sql = "SELECT thang, nam, tongSoHoaDon, doanhThu FROM v_ThongKeDoanhThu ORDER BY nam DESC, thang DESC";

        try (Connection con = connectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ThongKeDoanhThuModel(
                        rs.getInt("thang"),
                        rs.getInt("nam"),
                        rs.getInt("tongSoHoaDon"),
                        rs.getDouble("doanhThu")
                ));
            }
        }
        return list;
    }
}