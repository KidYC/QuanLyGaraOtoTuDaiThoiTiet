package Entity;

import java.util.Date;

public class NhanVien {
    private String maNV;
    private String hoTen;
    private String soDienThoai;
    private String diaChi;
    private Date ngayVaoLam;
    private String tenDangNhap;
    private String chucVu;
    private String trangThai;

    public NhanVien() {}

    public NhanVien(String maNV, String hoTen, String soDienThoai, String diaChi, Date ngayVaoLam, String tenDangNhap, String chucVu, String trangThai) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.ngayVaoLam = ngayVaoLam;
        this.tenDangNhap = tenDangNhap;
        this.chucVu = chucVu;
        this.trangThai = trangThai;
    }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public Date getNgayVaoLam() { return ngayVaoLam; }
    public void setNgayVaoLam(Date ngayVaoLam) { this.ngayVaoLam = ngayVaoLam; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}