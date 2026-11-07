package Entity;

public class ChiTietBaoGia_DTO {
    private String loai;
    private String maHangMuc;
    private String tenHangMuc;
    private int soLuong;
    private double donGia;

    public ChiTietBaoGia_DTO(String loai, String maHangMuc, String tenHangMuc, int soLuong, double donGia) {
        this.loai = loai;
        this.maHangMuc = maHangMuc;
        this.tenHangMuc = tenHangMuc;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getLoai() { return loai; }
    public String getMaHangMuc() { return maHangMuc; }
    public String getTenHangMuc() { return tenHangMuc; }
    public int getSoLuong() { return soLuong; }
    public double getDonGia() { return donGia; }
}