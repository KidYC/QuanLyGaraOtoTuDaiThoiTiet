package Entity;

import java.time.LocalDateTime;

public class HoaDon {
    private String maHoaDon;
    private LocalDateTime ngayLap;
    private PhuongThucThanhToan maPhuongThuc;
    private PhieuSuaChua maPhieuSuaChua;
    private KeToan maNhanVien;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, LocalDateTime ngayLap,
                  PhuongThucThanhToan maPhuongThuc, PhieuSuaChua maPhieuSuaChua,
                  KeToan maNhanVien) {
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

    public PhuongThucThanhToan getMaPhuongThuc() {
        return maPhuongThuc;
    }

    public void setMaPhuongThuc(PhuongThucThanhToan maPhuongThuc) {
        this.maPhuongThuc = maPhuongThuc;
    }

    public PhieuSuaChua getMaPhieuSuaChua() {
        return maPhieuSuaChua;
    }

    public void setMaPhieuSuaChua(PhieuSuaChua maPhieuSuaChua) {
        this.maPhieuSuaChua = maPhieuSuaChua;
    }

    public KeToan getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(KeToan maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
}