package Entity;

import java.util.Date;

public class ChiTietBaoHanh_DTO {
    private String maHangMuc;
    private String tenHangMuc;
    private int thoiGianBaoHanhThang;
    private Date ngayHetHan;
    private String trangThai;

    public ChiTietBaoHanh_DTO(String maHangMuc, String tenHangMuc, int thoiGianBaoHanhThang, Date ngayHetHan, String trangThai) {
        this.maHangMuc = maHangMuc;
        this.tenHangMuc = tenHangMuc;
        this.thoiGianBaoHanhThang = thoiGianBaoHanhThang;
        this.ngayHetHan = ngayHetHan;
        this.trangThai = trangThai;
    }

    public String getMaHangMuc() { return maHangMuc; }
    public String getTenHangMuc() { return tenHangMuc; }
    public int getThoiGianBaoHanhThang() { return thoiGianBaoHanhThang; }
    public Date getNgayHetHan() { return ngayHetHan; }
    public String getTrangThai() { return trangThai; }
}