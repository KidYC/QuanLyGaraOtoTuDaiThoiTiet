package Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LichSuCongNo {
    private long maGiaoDich;
    private KhachHang maKhachHang;
    private HoaDon maHoaDon;
    private LocalDateTime ngayGiaoDich;
    private String noiDung;
    private BigDecimal ghiNo;
    private BigDecimal ghiCo;
    private BigDecimal duNoCuoi;

    public LichSuCongNo() {
    }

    public LichSuCongNo(long maGiaoDich, KhachHang maKhachHang,
                        HoaDon maHoaDon, LocalDateTime ngayGiaoDich,
                        String noiDung, BigDecimal ghiNo,
                        BigDecimal ghiCo, BigDecimal duNoCuoi) {
        this.maGiaoDich = maGiaoDich;
        this.maKhachHang = maKhachHang;
        this.maHoaDon = maHoaDon;
        this.ngayGiaoDich = ngayGiaoDich;
        this.noiDung = noiDung;
        this.ghiNo = ghiNo;
        this.ghiCo = ghiCo;
        this.duNoCuoi = duNoCuoi;
    }

    public long getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(long maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public KhachHang getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(KhachHang maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public HoaDon getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(HoaDon maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public LocalDateTime getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(LocalDateTime ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public BigDecimal getGhiNo() {
        return ghiNo;
    }

    public void setGhiNo(BigDecimal ghiNo) {
        this.ghiNo = ghiNo;
    }

    public BigDecimal getGhiCo() {
        return ghiCo;
    }

    public void setGhiCo(BigDecimal ghiCo) {
        this.ghiCo = ghiCo;
    }

    public BigDecimal getDuNoCuoi() {
        return duNoCuoi;
    }

    public void setDuNoCuoi(BigDecimal duNoCuoi) {
        this.duNoCuoi = duNoCuoi;
    }
}