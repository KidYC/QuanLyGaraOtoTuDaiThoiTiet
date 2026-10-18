
package Entity;

public class PhuTung {
    private String maPhuTung;
    private String tenPhuTung;
    private String donViTinh;
    private double donGiaNhap;
    private double donGiaBan;
    private int soLuongTon;
    private String maKho;

    public PhuTung() {}
    public PhuTung(String maPhuTung, String tenPhuTung, String donViTinh, double donGiaNhap, double donGiaBan, int soLuongTon, String maKho) {
        this.maPhuTung = maPhuTung;
        this.tenPhuTung = tenPhuTung;
        this.donViTinh = donViTinh;
        this.donGiaNhap = donGiaNhap;
        this.donGiaBan = donGiaBan;
        this.soLuongTon = soLuongTon;
        this.maKho = maKho;
    }

    public String getMaPhuTung() {
        return maPhuTung;
    }
    public void setMaPhuTung(String maPhuTung) {
        this.maPhuTung = maPhuTung;
    }
    public String getTenPhuTung() {
        return tenPhuTung;
    }
    public void setTenPhuTung(String tenPhuTung) {
        this.tenPhuTung = tenPhuTung;
    }
    public String getDonViTinh() {
        return donViTinh;
    }
    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }
    public double getDonGiaNhap() {
        return donGiaNhap;
    }
    public void setDonGiaNhap(double donGiaNhap) {
        this.donGiaNhap = donGiaNhap;
    }
    public double getDonGiaBan() {
        return donGiaBan;
    }
    public void setDonGiaBan(double donGiaBan) {
        this.donGiaBan = donGiaBan;
    }
    public int getSoLuongTon() {
        return soLuongTon;
    }
    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }
    public String getMaKho() {
        return maKho;
    }
    public void setMaKho(String maKho) {
        this.maKho = maKho;
    }
}