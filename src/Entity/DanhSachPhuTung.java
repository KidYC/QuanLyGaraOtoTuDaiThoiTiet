package Entity;

public class DanhSachPhuTung {
    private PhieuSuaChua phieuSuaChua;
    private PhuTung phuTung;
    private int soLuongPhuTung;
    private double donGiaBan;

    public DanhSachPhuTung() {}

    public DanhSachPhuTung(PhieuSuaChua phieuSuaChua, PhuTung phuTung, int soLuongPhuTung, double donGiaBan) {
        this.phieuSuaChua = phieuSuaChua;
        this.phuTung = phuTung;
        this.soLuongPhuTung = soLuongPhuTung;
        this.donGiaBan = donGiaBan;
    }

    public PhieuSuaChua getPhieuSuaChua() {
        return phieuSuaChua;
    }

    public void setPhieuSuaChua(PhieuSuaChua phieuSuaChua) {
        this.phieuSuaChua = phieuSuaChua;
    }

    public PhuTung getPhuTung() {
        return phuTung;
    }

    public void setPhuTung(PhuTung phuTung) {
        this.phuTung = phuTung;
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

    public String getMaPhieuSuaChua() {
        return (this.phieuSuaChua != null) ? this.phieuSuaChua.getMaPhieuSuaChua() : "";
    }

    public String getMaPhuTung() {
        return (this.phuTung != null) ? this.phuTung.getMaPhuTung() : "";
    }
}