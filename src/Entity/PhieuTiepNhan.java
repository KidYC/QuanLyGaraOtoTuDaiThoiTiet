// File: Entity/PhieuTiepNhan.java
package Entity;

import java.sql.Date;

public class PhieuTiepNhan {
    private String maPhieuTiepNhan;
    private Date ngayTiepNhan;
    private String tinhTrangXe;
    private String yeuCauKH;
    private String trangThai;
    private String bienSo;

    public PhieuTiepNhan() {}
    public PhieuTiepNhan(String maPhieuTiepNhan, Date ngayTiepNhan, String tinhTrangXe, String yeuCauKH, String trangThai, String bienSo) {
        this.maPhieuTiepNhan = maPhieuTiepNhan;
        this.ngayTiepNhan = ngayTiepNhan;
        this.tinhTrangXe = tinhTrangXe;
        this.yeuCauKH = yeuCauKH;
        this.trangThai = trangThai;
        this.bienSo = bienSo;
    }


    public String getMaPhieuTiepNhan() {
        return maPhieuTiepNhan;
    }
    public void setMaPhieuTiepNhan(String maPhieuTiepNhan) {
        this.maPhieuTiepNhan = maPhieuTiepNhan;
    }
    public Date getNgayTiepNhan() {
        return ngayTiepNhan;
    }
    public void setNgayTiepNhan(Date ngayTiepNhan) {
        this.ngayTiepNhan = ngayTiepNhan;
    }
    public String getTinhTrangXe() {
        return tinhTrangXe;
    }
    public void setTinhTrangXe(String tinhTrangXe) {
        this.tinhTrangXe = tinhTrangXe;
    }
    public String getYeuCauKH() {
        return yeuCauKH;
    }
    public void setYeuCauKH(String yeuCauKH) {
        this.yeuCauKH = yeuCauKH;
    }
    public String getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    public String getBienSo() {
        return bienSo;
    }
    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }
}