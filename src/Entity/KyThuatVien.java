package Entity;

import java.util.Date;
import java.util.Objects;

public class KyThuatVien {
    private String maKTV;
    private String hoTen;
    private String soDienThoai;
    private String diaChi;
    private Date ngayVaoLam;
    private String trangThai;


    public KyThuatVien() {
    }

    public KyThuatVien(String maKTV, String hoTen, String soDienThoai, String diaChi, Date ngayVaoLam, String trangThai) {
        this.maKTV = maKTV;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
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

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KyThuatVien that = (KyThuatVien) o;
        return Objects.equals(maKTV, that.maKTV);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKTV);
    }

    @Override
    public String toString() {
        return hoTen;
    }
}