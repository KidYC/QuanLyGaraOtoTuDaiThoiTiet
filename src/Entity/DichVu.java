
package Entity;

public class DichVu {
    private String maDichVu;
    private String tenDichVu;
    private double donGia;
    private String moTa;
    private int thoiGianBaoHanh;

    public DichVu() {}
    public DichVu(String maDichVu, String tenDichVu, double donGia, String moTa, int thoiGianBaoHanh) {
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
        this.donGia = donGia;
        this.moTa = moTa;
        this.thoiGianBaoHanh = thoiGianBaoHanh;
    }


    public String getMaDichVu() {
        return maDichVu;
    }
    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }
    public String getTenDichVu() {
        return tenDichVu;
    }
    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }
    public double getDonGia() {
        return donGia;
    }
    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
    public String getMoTa() {
        return moTa;
    }
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    public int getThoiGianBaoHanh() {
        return thoiGianBaoHanh;
    }
    public void setThoiGianBaoHanh(int thoiGianBaoHanh) {
        this.thoiGianBaoHanh = thoiGianBaoHanh;
    }
}