
package Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CaLamViec {
    private String maCa;
    private KeToan maNhanVien;
    private LocalDateTime thoiGianMoCa;
    private LocalDateTime thoiGianDongCa;
    private BigDecimal tienDauCa;
    private BigDecimal tienCuoiCa;
    private BigDecimal chenhLech;
    private String trangThai;
    private String ghiChu;

    public CaLamViec() {
    }

    public CaLamViec(String maCa, KeToan maNhanVien,
                     LocalDateTime thoiGianMoCa,
                     LocalDateTime thoiGianDongCa,
                     BigDecimal tienDauCa, BigDecimal tienCuoiCa,
                     BigDecimal chenhLech, String trangThai,
                     String ghiChu) {
        this.maCa = maCa;
        this.maNhanVien = maNhanVien;
        this.thoiGianMoCa = thoiGianMoCa;
        this.thoiGianDongCa = thoiGianDongCa;
        this.tienDauCa = tienDauCa;
        this.tienCuoiCa = tienCuoiCa;
        this.chenhLech = chenhLech;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    public String getMaCa() {
        return maCa;
    }

    public void setMaCa(String maCa) {
        this.maCa = maCa;
    }

    public KeToan getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(KeToan maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public LocalDateTime getThoiGianMoCa() {
        return thoiGianMoCa;
    }

    public void setThoiGianMoCa(LocalDateTime thoiGianMoCa) {
        this.thoiGianMoCa = thoiGianMoCa;
    }

    public LocalDateTime getThoiGianDongCa() {
        return thoiGianDongCa;
    }

    public void setThoiGianDongCa(LocalDateTime thoiGianDongCa) {
        this.thoiGianDongCa = thoiGianDongCa;
    }

    public BigDecimal getTienDauCa() {
        return tienDauCa;
    }

    public void setTienDauCa(BigDecimal tienDauCa) {
        this.tienDauCa = tienDauCa;
    }

    public BigDecimal getTienCuoiCa() {
        return tienCuoiCa;
    }

    public void setTienCuoiCa(BigDecimal tienCuoiCa) {
        this.tienCuoiCa = tienCuoiCa;
    }

    public BigDecimal getChenhLech() {
        return chenhLech;
    }

    public void setChenhLech(BigDecimal chenhLech) {
        this.chenhLech = chenhLech;
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
