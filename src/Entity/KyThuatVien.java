package Entity;

import java.sql.Date;

public class KyThuatVien {
    private String maKTV;
    private String hoTen;
    private String diaChi;
    private Date ngayVaoLam;
    private String trangThai;

    public KyThuatVien() {
    }

    public KyThuatVien(String maKTV, String hoTen, String diaChi, Date ngayVaoLam, String trangThai) {
        this.maKTV = maKTV;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.ngayVaoLam = ngayVaoLam;
        this.trangThai = trangThai;
    }

    public String getMaKTV() {
        return maKTV;
    }

    public void setMaKTV(String maKTV) {
        this.maKTV = maKTV;
    }

    // Giữ phương thức alias này để tránh lỗi nếu code giao diện cũ có gọi getMaNhanVien()
    public String getMaNhanVien() {
        return maKTV;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maKTV = maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Date getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(Date ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}