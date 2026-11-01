package Entity;

import java.sql.Date;

public class PhieuSuaChua {
    private String maPhieuSuaChua;
    private Date ngayLap;
    private Date ngayDuKienHoanThanh;
    private Date ngayIn;
    private String trangThai;
    private String bienSo;
    private String maNhanVien;
    private String maPhieuTiepNhan;
    private String loiPhatHien;
    private String nguyenNhan;
    private String deXuatSuaChua;
    private int tienDo;
    private String ghiChu;
    private String ketQuaKCS;
    private String ghiChuKCS;
    private Date ngayKiemTraKCS;

    public PhieuSuaChua() {}

    public PhieuSuaChua(String maPhieuSuaChua, Date ngayLap, Date ngayIn, String trangThai, String bienSo, String maNhanVien, String maPhieuTiepNhan) {
        this(maPhieuSuaChua, ngayLap, ngayIn, trangThai, bienSo, maNhanVien, maPhieuTiepNhan, null, null, null);
    }

    public PhieuSuaChua(String maPhieuSuaChua, Date ngayLap, Date ngayIn, String trangThai, String bienSo, String maNhanVien, String maPhieuTiepNhan, String loiPhatHien, String nguyenNhan, String deXuatSuaChua) {
        this.maPhieuSuaChua = maPhieuSuaChua;
        this.ngayLap = ngayLap;
        this.ngayIn = ngayIn;
        this.trangThai = trangThai;
        this.bienSo = bienSo;
        this.maNhanVien = maNhanVien;
        this.maPhieuTiepNhan = maPhieuTiepNhan;
        this.loiPhatHien = loiPhatHien;
        this.nguyenNhan = nguyenNhan;
        this.deXuatSuaChua = deXuatSuaChua;

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

    public Date getNgayDuKienHoanThanh() {
        return ngayDuKienHoanThanh;
    }

    public void setNgayDuKienHoanThanh(Date ngayDuKienHoanThanh) {
        this.ngayDuKienHoanThanh = ngayDuKienHoanThanh;
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

    public String getLoiPhatHien() {
        return loiPhatHien;
    }

    public void setLoiPhatHien(String loiPhatHien) {
        this.loiPhatHien = loiPhatHien;
    }

    public String getNguyenNhan() {
        return nguyenNhan;
    }

    public void setNguyenNhan(String nguyenNhan) {
        this.nguyenNhan = nguyenNhan;
    }

    public String getDeXuatSuaChua() {
        return deXuatSuaChua;
    }

    public void setDeXuatSuaChua(String deXuatSuaChua) {
        this.deXuatSuaChua = deXuatSuaChua;
    }
    public int getTienDo() {
        return tienDo;
    }

    public void setTienDo(int tienDo) {
        this.tienDo = tienDo;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    public String getKetQuaKCS() {
        return ketQuaKCS;
    }

    public void setKetQuaKCS(String ketQuaKCS) {
        this.ketQuaKCS = ketQuaKCS;
    }

    public String getGhiChuKCS() {
        return ghiChuKCS;
    }

    public void setGhiChuKCS(String ghiChuKCS) {
        this.ghiChuKCS = ghiChuKCS;
    }

    public Date getNgayKiemTraKCS() {
        return ngayKiemTraKCS;
    }

    public void setNgayKiemTraKCS(Date ngayKiemTraKCS) {
        this.ngayKiemTraKCS = ngayKiemTraKCS;
    }
}