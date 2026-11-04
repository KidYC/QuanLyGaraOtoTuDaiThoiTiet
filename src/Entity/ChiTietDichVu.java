package Entity;

public class ChiTietDichVu {
    private PhieuSuaChua phieuSuaChua;
    private DichVu dichVu;
    private int soLuong;
    private double donGia;

    public ChiTietDichVu() {}

    public ChiTietDichVu(PhieuSuaChua phieuSuaChua, DichVu dichVu, int soLuong, double donGia) {
        this.phieuSuaChua = phieuSuaChua;
        this.dichVu = dichVu;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public PhieuSuaChua getPhieuSuaChua() {
        return phieuSuaChua;
    }

    public void setPhieuSuaChua(PhieuSuaChua phieuSuaChua) {
        this.phieuSuaChua = phieuSuaChua;
    }

    public DichVu getDichVu() {
        return dichVu;
    }

    public void setDichVu(DichVu dichVu) {
        this.dichVu = dichVu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getMaPhieuSuaChua() {
        return (this.phieuSuaChua != null) ? this.phieuSuaChua.getMaPhieuSuaChua() : "";
    }

    public String getMaDichVu() {
        return (this.dichVu != null) ? this.dichVu.getMaDichVu() : "";
    }
}