
package Entity;

public class ChiTietDichVu {
    private String maPhieuSuaChua;
    private String maDichVu;
    private int soLuong;
    private double donGia;

    public ChiTietDichVu() {}
    public ChiTietDichVu(String maPhieuSuaChua, String maDichVu, int soLuong, double donGia) {
        this.maPhieuSuaChua = maPhieuSuaChua;
        this.maDichVu = maDichVu;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }


    public String getMaPhieuSuaChua() {
        return maPhieuSuaChua;
    }
    public void setMaPhieuSuaChua(String maPhieuSuaChua) {
        this.maPhieuSuaChua = maPhieuSuaChua;
    }
    public String getMaDichVu() {
        return maDichVu;
    }
    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
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
}