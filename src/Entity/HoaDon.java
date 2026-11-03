package Entity;

import java.time.LocalDateTime;

public class HoaDon {
    private String maHoaDon;
    private LocalDateTime ngayLap;
    private String maPhuongThuc;
    private String maPhieuSuaChua;
    private String maNhanVien;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, LocalDateTime ngayLap,
                  String maPhuongThuc, String maPhieuSuaChua,
                  String maNhanVien) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.maPhuongThuc = maPhuongThuc;
        this.maPhieuSuaChua = maPhieuSuaChua;
        this.maNhanVien = maNhanVien;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public LocalDateTime getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDateTime ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getMaPhuongThuc() {
        return maPhuongThuc;
    }

    public void setMaPhuongThuc(String maPhuongThuc) {
        this.maPhuongThuc = maPhuongThuc;
    }

    public String getMaPhieuSuaChua() {
        return maPhieuSuaChua;
    }

    public void setMaPhieuSuaChua(String maPhieuSuaChua) {
        this.maPhieuSuaChua = maPhieuSuaChua;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
}