
package Entity;

public class DanhSachPhuTung {
    private String maPhieuSuaChua;
    private String maPhuTung;
    private int soLuongPhuTung;
    private double donGiaBan;

    public DanhSachPhuTung() {}
    public DanhSachPhuTung(String maPhieuSuaChua, String maPhuTung, int soLuongPhuTung, double donGiaBan) {
        this.maPhieuSuaChua = maPhieuSuaChua;
        this.maPhuTung = maPhuTung;
        this.soLuongPhuTung = soLuongPhuTung;
        this.donGiaBan = donGiaBan;
    }


    public String getMaPhieuSuaChua() {
        return maPhieuSuaChua;
    }
    public void setMaPhieuSuaChua(String maPhieuSuaChua) {
        this.maPhieuSuaChua = maPhieuSuaChua;
    }
    public String getMaPhuTung() {
        return maPhuTung;
    }
    public void setMaPhuTung(String maPhuTung) {
        this.maPhuTung = maPhuTung;
    }
    public int getSoLuongPhuTung() {
        return soLuongPhuTung;
    }
    public void setSoLuongPhuTung(int soLuongPhuTung) {
        this.soLuongPhuTung = soLuongPhuTung;
    }
    public double getDonGiaBan() {
        return donGiaBan;
    }
    public void setDonGiaBan(double donGiaBan) {
        this.donGiaBan = donGiaBan;
    }
}