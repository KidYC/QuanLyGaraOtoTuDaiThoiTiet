package Entity;

import java.util.Date;

public class PhieuKiemKe {
    private ThuKho thuKho;
    private String maPhieuKiemKe;
    private Date ngayKiemKe;
    private String trangThai;

    public PhieuKiemKe() {}
    public PhieuKiemKe(ThuKho thuKho, String maPhieuKiemKe, Date ngayKiemKe, String trangThai) {
        this.thuKho = thuKho;
        this.maPhieuKiemKe = maPhieuKiemKe;
        this.ngayKiemKe = ngayKiemKe;
        this.trangThai = trangThai;
    }

    public ThuKho getThuKho() {return thuKho;}
    public void setThuKho(ThuKho thuKho) {this.thuKho = thuKho;}
    public String getMaPhieuKiemKe() {return maPhieuKiemKe;}
    public void setMaPhieuKiemKe(String maPhieuKiemKe) {this.maPhieuKiemKe = maPhieuKiemKe;}
    public Date getNgayKiemKe() {return ngayKiemKe;}
    public void setNgayKiemKe(Date ngayKiemKe) {this.ngayKiemKe = ngayKiemKe;}
    public String getTrangThai() {return trangThai;}
    public void setTrangThai(String trangThai) {this.trangThai = trangThai;}

}
