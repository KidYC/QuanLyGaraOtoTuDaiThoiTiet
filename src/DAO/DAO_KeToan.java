package DAO;

import DB.connectSQL;

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
                select PSC.maPhieuSuaChua,
                PSC.bienSo,
                KH.hoTen AS tenKhachHang,
                KH.soDienThoai,
                KTV.hoTen AS tenKyThuatVien,
                CTPSC.donGia,
                PSC.trangThai 
                from PHIEU_SUA_CHUA PSC
                join KHACH_HANG KH
                on PSC.bienSo = KH.bienSo
                join KY_THUAT_VIEN KTV
                on KTV.maKTV=PSC.maKTV
                join CHI_TIET_PHIEU_SUA_CHUA CTPSC 
                on CTPSC.maPhieuSuaChua = PSC.maPhieuSuaChua""" ;
        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ){
            while (rs.next()){
                Object[] dong = {
                        rs.getString("maPhieuSuaChua"),
                        rs.getString("bienSo"),
                        rs.getString("tenKhachHang"),
                        rs.getString("soDienThoai"),
                        rs.getString("tenKyThuatVien"),
                        rs.getBigDecimal("donGia"),
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
                 select HD.maHoaDon,
                 PSC.maPhieuSuaChua,
                 BS.bienSo,
                 PT.maPhuTung,
                 PT.tenPhuTung,
                 CTPSC.soLuong,
                 PT.donGiaBan,
                 PT.donGiaBan as thanhtien
                 from HOA_DON HD
                 join PHIEU_SUA_CHUA PSC
                 on HD.maPhieuSuaChua=PSC.maPhieuSuaChua
                 join XE BS
                 on PSC.bienSo = BS.bienSo
                 join CHI_TIET_PHIEU_SUA_CHUA CTPSC
                 on CTPSC.maPhieuSuaChua=PSC.maPhieuSuaChua
                 join PHU_TUNG PT
                 on CTPSC.maPhuTung=PT.maPhuTung
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
                         rs.getBigDecimal("donGiaBan"),
                         rs.getBigDecimal("thanhtien")
                 };
                 danhsach.add(dong);
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
                         rs.getString("hanMucNo"),
                         rs.getString("tongNo"),
                         rs.getString("trangThai")
                 };
                 danhsach.add(dong);
             }
             return danhsach;
         }
     }
//     public List<Object[]> laydulieucongno(){
//
//     }

}
