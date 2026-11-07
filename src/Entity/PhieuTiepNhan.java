package Entity;

import java.sql.Date;

public class PhieuTiepNhan {
    private String maPhieuTiepNhan;
    private Date ngayTiepNhan;
    private String tinhTrangXe;
    private String yeuCauKH;
    private String trangThai;
    private Xe xe;

    public PhieuTiepNhan() {}

    public PhieuTiepNhan(String maPhieuTiepNhan, Date ngayTiepNhan, String tinhTrangXe, String yeuCauKH, String trangThai, Xe xe) {
        this.maPhieuTiepNhan = maPhieuTiepNhan;
        this.ngayTiepNhan = ngayTiepNhan;
        this.tinhTrangXe = tinhTrangXe;
        this.yeuCauKH = yeuCauKH;
        this.trangThai = trangThai;
        this.xe = xe;
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

    public Xe getXe() {
        return xe;
    }

    public void setXe(Xe xe) {
        this.xe = xe;
    }

    public String getBienSo() {
        return (this.xe != null) ? this.xe.getBienSo() : "";
    }
}