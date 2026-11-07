package Entity;

import java.math.BigDecimal;

public class ChiTietHoaDon {

    private String maChiTietHD;
    private String maHD;
    private HoaDon maHoaDon;
    private PhieuSuaChua maPhieuSuaChua;
    private Xe bienSo;
    private PhuTung maPhuTung;
    private String tenPhuTung;
    private String noiDung;
    private int soLuong;
    private BigDecimal donGia;
    private BigDecimal thanhTien;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(
            String maChiTietHD,
            String maHD,
            String noiDung,
            int soLuong,
            BigDecimal donGia
    ) {
        this.maChiTietHD = maChiTietHD;
        this.maHD = maHD;
        this.noiDung = noiDung;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMaChiTietHD() {
        return maChiTietHD;
    }

    public void setMaChiTietHD(String maChiTietHD) {
        this.maChiTietHD = maChiTietHD;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public HoaDon getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(HoaDon maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

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

    public PhuTung getMaPhuTung() {
        return maPhuTung;
    }

    public void setMaPhuTung(PhuTung maPhuTung) {
        this.maPhuTung = maPhuTung;
    }

    public String getTenPhuTung() {
        return tenPhuTung;
    }

    public void setTenPhuTung(String tenPhuTung) {
        this.tenPhuTung = tenPhuTung;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }
}