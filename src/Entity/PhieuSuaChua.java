
package Entity;

import java.sql.Date;

public class PhieuSuaChua {
    private String maPhieuSuaChua;
    private Date ngayLap;
    private Date ngayIn;
    private String trangThai;
    private String bienSo;
    private String maNhanVien;
    private String maPhieuTiepNhan;

    public PhieuSuaChua() {}
    public PhieuSuaChua(String maPhieuSuaChua, Date ngayLap, Date ngayIn, String trangThai, String bienSo, String maNhanVien, String maPhieuTiepNhan) {
        this.maPhieuSuaChua = maPhieuSuaChua;
        this.ngayLap = ngayLap;
        this.ngayIn = ngayIn;
        this.trangThai = trangThai;
        this.bienSo = bienSo;
        this.maNhanVien = maNhanVien;
        this.maPhieuTiepNhan = maPhieuTiepNhan;
    }


    public String getMaPhieuSuaChua() {
        return maPhieuSuaChua;
    }
    public void setMaPhieuSuaChua(String maPhieuSuaChua) {
        this.maPhieuSuaChua = maPhieuSuaChua;
    }
    public Date getNgayLap() {
        return ngayLap;
    }
    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }
    public Date getNgayIn() {
        return ngayIn;
    }
    public void setNgayIn(Date ngayIn) {
        this.ngayIn = ngayIn;
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
    public String getMaNhanVien() {
        return maNhanVien;
    }
    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
    public String getMaPhieuTiepNhan() {
        return maPhieuTiepNhan;
    }
    public void setMaPhieuTiepNhan(String maPhieuTiepNhan) {
        this.maPhieuTiepNhan = maPhieuTiepNhan;
    }
}