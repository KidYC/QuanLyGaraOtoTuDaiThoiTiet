package Entity;

import java.sql.Timestamp;

public class PhieuTiepNhan_DTO {

    private String maPhieuTiepNhan;
    private String tenKhachHang;
    private String bienSo;
    private String loaiXe; // Kết hợp hangXe và dongXe
    private Timestamp ngayTiepNhan;
    private String trangThai;

    public PhieuTiepNhan_DTO(String maPhieuTiepNhan, String tenKhachHang, String bienSo, String loaiXe, Timestamp ngayTiepNhan, String trangThai) {
        this.maPhieuTiepNhan = maPhieuTiepNhan;
        this.tenKhachHang = tenKhachHang;
        this.bienSo = bienSo;
        this.loaiXe = loaiXe;
        this.ngayTiepNhan = ngayTiepNhan;
        this.trangThai = trangThai;
    }

    public String getMaPhieuTiepNhan() { return maPhieuTiepNhan; }
    public String getTenKhachHang() { return tenKhachHang; }
    public String getBienSo() { return bienSo; }
    public String getLoaiXe() { return loaiXe; }
    public Timestamp getNgayTiepNhan() { return ngayTiepNhan; }
    public String getTrangThai() { return trangThai; }
}
