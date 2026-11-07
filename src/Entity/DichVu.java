package Entity;

public class DichVu {
    private String maDichVu;
    private String tenDichVu;
    private double donGiaHienTai;
    private String moTa;
    private int thoiGianBaoHanh;

    public DichVu() {}

    public DichVu(String maDichVu, String tenDichVu, double donGiaHienTai) {
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
        this.donGiaHienTai = donGiaHienTai;
        this.moTa = ""; // Mặc định rỗng
        this.thoiGianBaoHanh = 0; // Mặc định 0
    }

    public DichVu(String maDichVu, String tenDichVu, double donGiaHienTai, String moTa, int thoiGianBaoHanh) {
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
        this.donGiaHienTai = donGiaHienTai;
        this.moTa = moTa;
        this.thoiGianBaoHanh = thoiGianBaoHanh;
    }

    public String getMaDichVu() { return maDichVu; }
    public void setMaDichVu(String maDichVu) { this.maDichVu = maDichVu; }

    public String getTenDichVu() { return tenDichVu; }
    public void setTenDichVu(String tenDichVu) { this.tenDichVu = tenDichVu; }

    public double getDonGia() { return donGiaHienTai; }
    public void setDonGia(double donGia) { this.donGiaHienTai = donGia; }

    public double getDonGiaHienTai() { return donGiaHienTai; }
    public void setDonGiaHienTai(double donGiaHienTai) { this.donGiaHienTai = donGiaHienTai; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public int getThoiGianBaoHanh() { return thoiGianBaoHanh; }
    public void setThoiGianBaoHanh(int thoiGianBaoHanh) { this.thoiGianBaoHanh = thoiGianBaoHanh; }
}