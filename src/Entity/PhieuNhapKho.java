package Entity;

import java.util.Date;

public class PhieuNhapKho {

    private ThuKho thuKho;
    private String maPhieuNhap;
    private Date ngayNhap;
    private Double tongTienNhap;
    private String maNhanVien;
    private NhaCungCap nhaCungCap;

    public PhieuNhapKho() {}
    public PhieuNhapKho(ThuKho thuKho, String maPhieuNhap, Date ngayNhap, Double tongTienNhap,String maCungCap, NhaCungCap nhaCungCap) {
        this.thuKho = thuKho;
        this.maPhieuNhap = maPhieuNhap;
        this.ngayNhap = ngayNhap;
        this.tongTienNhap = tongTienNhap;
        this.maNhanVien = maNhanVien;
        this.nhaCungCap = nhaCungCap;
    }

    public ThuKho getThuKho() {return thuKho;}
    public void setThuKho(ThuKho thuKho) {this.thuKho = thuKho;}
    public String getMaPhieuNhap() {return maPhieuNhap;}
    public void setMaPhieuNhap(String maPhieuNhap) {this.maPhieuNhap = maPhieuNhap;}
    public Date getNgayNhap() {return ngayNhap;}
    public void setNgayNhap(Date ngayNhap) {this.ngayNhap = ngayNhap;}
    public Double getTongTienNhap() {return tongTienNhap;}
    public void setTongTienNhap(Double tongTienNhap) {this.tongTienNhap = tongTienNhap;}
    public String getMaNhanVien() {return maNhanVien;}
    public void setMaNhanVien(String maNhanVien) {this.maNhanVien = maNhanVien;}
    public NhaCungCap getNhaCungCap() {return nhaCungCap;}
    public void setNhaCungCap(NhaCungCap nhaCungCap) {this.nhaCungCap = nhaCungCap;}
}
