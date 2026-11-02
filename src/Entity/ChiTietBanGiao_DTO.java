package Entity;

public class ChiTietBanGiao_DTO {
    private String loai;
    private String tenHangMuc;
    private int soLuong;
    private double thanhTien;
    private String baoHanh;

    public ChiTietBanGiao_DTO(String loai, String tenHangMuc, int soLuong, double thanhTien, String baoHanh) {
        this.loai = loai;
        this.tenHangMuc = tenHangMuc;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
        this.baoHanh = baoHanh;
    }

    public ChiTietBanGiao_DTO() {}

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getTenHangMuc() {
        return tenHangMuc;
    }

    public void setTenHangMuc(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getBaoHanh() {
        return baoHanh;
    }

    public void setBaoHanh(String baoHanh) {
        this.baoHanh = baoHanh;
    }
}