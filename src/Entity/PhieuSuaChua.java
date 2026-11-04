package Entity;

import java.sql.Date;

public class PhieuSuaChua {
    private String maPhieuSuaChua;
    private Date ngayLap;
    private Date ngayDuKienHoanThanh;
    private Date ngayIn;
    private String trangThai;
    private Xe xe;
    private KyThuatVien kyThuatVien; // Thay NhanVien thành KyThuatVien
    private PhieuTiepNhan phieuTiepNhan;
    private String loiPhatHien;
    private String nguyenNhan;
    private String deXuatSuaChua;
    private int tienDo;
    private String ghiChu;
    private String ketQuaKCS;
    private String ghiChuKCS;
    private Date ngayKiemTraKCS;

    public PhieuSuaChua() {}

    public PhieuSuaChua(String maPhieuSuaChua, Date ngayLap, Date ngayIn, String trangThai, Xe xe, KyThuatVien kyThuatVien, PhieuTiepNhan phieuTiepNhan) {
        this(maPhieuSuaChua, ngayLap, ngayIn, trangThai, xe, kyThuatVien, phieuTiepNhan, null, null, null);
    }

    public PhieuSuaChua(String maPhieuSuaChua, Date ngayLap, Date ngayIn, String trangThai, Xe xe, KyThuatVien kyThuatVien, PhieuTiepNhan phieuTiepNhan, String loiPhatHien, String nguyenNhan, String deXuatSuaChua) {
        this.maPhieuSuaChua = maPhieuSuaChua;
        this.ngayLap = ngayLap;
        this.ngayIn = ngayIn;
        this.trangThai = trangThai;
        this.xe = xe;
        this.kyThuatVien = kyThuatVien;
        this.phieuTiepNhan = phieuTiepNhan;
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

    public Xe getXe() {
        return xe;
    }

    public void setXe(Xe xe) {
        this.xe = xe;
    }

    public KyThuatVien getKyThuatVien() {
        return kyThuatVien;
    }

    public void setKyThuatVien(KyThuatVien kyThuatVien) {
        this.kyThuatVien = kyThuatVien;
    }

    public PhieuTiepNhan getPhieuTiepNhan() {
        return phieuTiepNhan;
    }

    public void setPhieuTiepNhan(PhieuTiepNhan phieuTiepNhan) {
        this.phieuTiepNhan = phieuTiepNhan;
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

    // --- Các phương thức hỗ trợ tương thích ngược (Helper Methods) ---
    public String getBienSo() {
        return (this.xe != null) ? this.xe.getBienSo() : "";
    }

    public String getMaNhanVien() {
        return (this.kyThuatVien != null) ? this.kyThuatVien.getMaKTV(): ""; // Hoặc getMaKTV() tùy vào getter của class KyThuatVien
    }

    public String getMaPhieuTiepNhan() {
        return (this.phieuTiepNhan != null) ? this.phieuTiepNhan.getMaPhieuTiepNhan() : "";
    }
}