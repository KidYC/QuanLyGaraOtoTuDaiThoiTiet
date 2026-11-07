
package Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ThanhToan {
    private String maThanhToan;
    private HoaDon maHoaDon;
    private LocalDateTime ngayThanhToan;
    private BigDecimal soTienThanhToan;
    private PhuongThucThanhToan maPhuongThuc;
    private KeToan maNhanVien;
    private String trangThai;
    private String ghiChu;

    public ThanhToan() {
    }

    public ThanhToan(String maThanhToan, HoaDon maHoaDon,
                     LocalDateTime ngayThanhToan,
                     BigDecimal soTienThanhToan,
                     PhuongThucThanhToan maPhuongThuc, KeToan maNhanVien,
                     String trangThai, String ghiChu) {
        this.maThanhToan = maThanhToan;
        this.maHoaDon = maHoaDon;
        this.ngayThanhToan = ngayThanhToan;
        this.soTienThanhToan = soTienThanhToan;
        this.maPhuongThuc = maPhuongThuc;
        this.maNhanVien = maNhanVien;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    public String getMaThanhToan() {
        return maThanhToan;
    }

    public void setMaThanhToan(String maThanhToan) {
        this.maThanhToan = maThanhToan;
    }

    public HoaDon getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(HoaDon maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public LocalDateTime getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(LocalDateTime ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public BigDecimal getSoTienThanhToan() {
        return soTienThanhToan;
    }

    public void setSoTienThanhToan(BigDecimal soTienThanhToan) {
        this.soTienThanhToan = soTienThanhToan;
    }

    public PhuongThucThanhToan getMaPhuongThuc() {
        return maPhuongThuc;
    }

    public void setMaPhuongThuc(PhuongThucThanhToan maPhuongThuc) {
        this.maPhuongThuc = maPhuongThuc;
    }

    public KeToan getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(KeToan maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
