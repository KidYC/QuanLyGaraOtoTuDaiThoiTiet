package Entity;

public class ChiTietPhieuNhap {
    private PhieuNhapKho phieuNhapKho;
    private PhuTung phuTung;
    private int soLuongNhap;
    private double donGiaNhap;

    public ChiTietPhieuNhap() {}

    public ChiTietPhieuNhap(PhieuNhapKho phieuNhapKho, PhuTung phuTung, int soLuongNhap, double donGiaNhap) {
        this.phieuNhapKho = phieuNhapKho;
        this.phuTung = phuTung;
        this.soLuongNhap = soLuongNhap;
        this.donGiaNhap = donGiaNhap;
    }

    public PhieuNhapKho getPhieuNhapKho() {return phieuNhapKho;}
    public void setPhieuNhapKho(PhieuNhapKho phieuNhapKho) {this.phieuNhapKho = phieuNhapKho;}
    public PhuTung getPhuTung() {return phuTung;}
    public void setPhuTung(PhuTung phuTung) {this.phuTung = phuTung;}
    public int getSoLuongNhap() {return soLuongNhap;}
    public void setSoLuongNhap(int soLuongNhap) {this.soLuongNhap = soLuongNhap;}
    public double getDonGiaNhap() {return donGiaNhap;}
    public void setDonGiaNhap(double donGiaNhap) {this.donGiaNhap = donGiaNhap;}

    public String getMaPhieuNhap() {return phieuNhapKho != null ? phieuNhapKho.getMaPhieuNhap() : "";}
    public String getMaPhuTung() {return phuTung != null ? phuTung.getMaPhuTung() : "";}
    public String getTenPhuTung() {return phuTung != null ? phuTung.getTenPhuTung() : "";}
    public String getDonViTinh() {return phuTung != null ? phuTung.getDonViTinh() : "";}

    public double tinhTongTien() {
        return soLuongNhap * donGiaNhap;
    }
}
