package Entity;

import java.sql.Timestamp;

public class BaoHanhHienThi_DTO {
    private String maPhieu;
    private String tenKhachHang;
    private String bienSo;
    private String tenDichVu;
    private Timestamp ngayKichHoat;
    private Timestamp ngayHetHan;
    private String trangThai;

    public BaoHanhHienThi_DTO(String maPhieu, String tenKhachHang, String bienSo, String tenDichVu,
                              Timestamp ngayKichHoat, Timestamp ngayHetHan, String trangThai) {
        this.maPhieu = maPhieu;
        this.tenKhachHang = tenKhachHang;
        this.bienSo = bienSo;
        this.tenDichVu = tenDichVu;
        this.ngayKichHoat = ngayKichHoat;
        this.ngayHetHan = ngayHetHan;
        this.trangThai = trangThai;
    }

    public String getMaPhieu() { return maPhieu; }
    public String getTenKhachHang() { return tenKhachHang; }
    public String getBienSo() { return bienSo; }
    public String getTenDichVu() { return tenDichVu; }
    public Timestamp getNgayKichHoat() { return ngayKichHoat; }
    public Timestamp getNgayHetHan() { return ngayHetHan; }
    public String getTrangThai() { return trangThai; }
}