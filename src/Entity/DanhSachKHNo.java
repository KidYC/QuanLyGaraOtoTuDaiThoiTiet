package Entity;

import java.math.BigDecimal;

public class DanhSachKHNo {
    private KhachHang maKhachHang;
    private String hoTen;
    private String soDienThoai;
    private String bienSo;
    private String diaChi;
    private BigDecimal hanMucNo;
    private BigDecimal tongNo;
    private String trangThai;

    public DanhSachKHNo() {
    }

    public DanhSachKHNo(KhachHang maKhachHang, String hoTen,
                        String soDienThoai, String bienSo, String diaChi,
                        BigDecimal hanMucNo, BigDecimal tongNo,
                        String trangThai) {
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.bienSo = bienSo;
        this.diaChi = diaChi;
        this.hanMucNo = hanMucNo;
        this.tongNo = tongNo;
        this.trangThai = trangThai;
    }

    public KhachHang getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(KhachHang maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public BigDecimal getHanMucNo() {
        return hanMucNo;
    }

    public void setHanMucNo(BigDecimal hanMucNo) {
        this.hanMucNo = hanMucNo;
    }

    public BigDecimal getTongNo() {
        return tongNo;
    }

    public void setTongNo(BigDecimal tongNo) {
        this.tongNo = tongNo;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}