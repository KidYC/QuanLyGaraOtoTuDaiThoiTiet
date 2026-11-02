package Entity;

import java.sql.Timestamp;

public class XeDaHoanThanh_DTO {
    private String maPhieu;
    private String tenKhachHang;
    private String bienSo;
    private String loaiXe;
    private Timestamp ngayHoanThanh;
    private String trangThai;

    public XeDaHoanThanh_DTO(String maPhieu, String tenKhachHang, String bienSo, String loaiXe, Timestamp ngayHoanThanh, String trangThai) {
        this.maPhieu = maPhieu;
        this.tenKhachHang = tenKhachHang;
        this.bienSo = bienSo;
        this.loaiXe = loaiXe;
        this.ngayHoanThanh = ngayHoanThanh;
        this.trangThai = trangThai;
    }

    public String getMaPhieu() { return maPhieu; }
    public String getTenKhachHang() { return tenKhachHang; }
    public String getBienSo() { return bienSo; }
    public String getLoaiXe() { return loaiXe; }
    public Timestamp getNgayHoanThanh() { return ngayHoanThanh; }
    public String getTrangThai() { return trangThai; }
}