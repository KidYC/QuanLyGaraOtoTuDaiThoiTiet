package Entity;

import java.math.BigDecimal;

public class PhieuSuaChuaDaHoanThanh {

    private PhieuSuaChua maPhieuSuaChua;
    private Xe bienSo;
    private String soDienThoai;
    private String hoTenKTV;
    private BigDecimal tongTien;
    private String trangThai;

    // Constructor mặc định
    public PhieuSuaChuaDaHoanThanh() {
    }

    // Constructor đầy đủ
    public PhieuSuaChuaDaHoanThanh(
            PhieuSuaChua maPhieuSuaChua,
            Xe bienSo,
            String soDienThoai,
            String hoTenKTV,
            BigDecimal tongTien,
            String trangThai
    ) {
        this.maPhieuSuaChua = maPhieuSuaChua;
        this.bienSo = bienSo;
        this.soDienThoai = soDienThoai;
        this.hoTenKTV = hoTenKTV;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    // Getter và Setter
    public PhieuSuaChua getMaPhieuSuaChua() {
        return maPhieuSuaChua;
    }

    public void setMaPhieuSuaChua(PhieuSuaChua maPhieuSuaChua) {
        this.maPhieuSuaChua = maPhieuSuaChua;
    }

    public Xe getBienSo() {
        return bienSo;
    }

    public void setBienSo(Xe bienSo) {
        this.bienSo = bienSo;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getHoTenKTV() {
        return hoTenKTV;
    }

    public void setHoTenKTV(String hoTenKTV) {
        this.hoTenKTV = hoTenKTV;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}