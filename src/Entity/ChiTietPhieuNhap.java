package Entity;

public class ChiTietPhieuNhap {
    private String maPhieuNhap;
    private String maPhuTung;
    private int soLuongNhap;
    private double donGiaNhap;
    private String tenPhuTung;
    private String donViTinh;

    public ChiTietPhieuNhap() {}

    public ChiTietPhieuNhap(String maPhieuNhap, String maPhuTung, int soLuongNhap, double donGiaNhap) {
        this.maPhieuNhap = maPhieuNhap;
        this.maPhuTung = maPhuTung;
        this.soLuongNhap = soLuongNhap;
        this.donGiaNhap = donGiaNhap;
    }

    public String getMaPhieuNhap() {return maPhieuNhap;}
    public void setMaPhieuNhap(String maPhieuNhap) {this.maPhieuNhap = maPhieuNhap;}
    public String getMaPhuTung() {return maPhuTung;}
    public void setMaPhuTung(String maPhuTung) {this.maPhuTung = maPhuTung;}
    public int getSoLuongNhap() {return soLuongNhap;}
    public void setSoLuongNhap(int soLuongNhap) {this.soLuongNhap = soLuongNhap;}
    public double getDonGiaNhap() {return donGiaNhap;}
    public void setDonGiaNhap(double donGiaNhap) {this.donGiaNhap = donGiaNhap;}
    public String getTenPhuTung() {return tenPhuTung;}
    public void setTenPhuTung(String tenPhuTung) {this.tenPhuTung = tenPhuTung;}
    public String getDonViTinh() {return donViTinh;}
    public void setDonViTinh(String donViTinh) {this.donViTinh = donViTinh;}

    public double tinhTongTien() {
        return soLuongNhap * donGiaNhap;
    }
}
