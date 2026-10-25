package Entity;

import java.util.Date;

public class ThongKeSuaChua {
    private String maPhieuSuaChua;
    private Date ngayLap;
    private String maKTV;
    private String trangThai; // Đảm bảo có thuộc tính này
    private int tongSoPhuTungDung;
    private double tongTienVatTu;

    public ThongKeSuaChua() {
    }

    public ThongKeSuaChua(String maPhieuSuaChua, Date ngayLap, String maKTV, String trangThai, int tongSoPhuTungDung, double tongTienVatTu) {
        this.maPhieuSuaChua = maPhieuSuaChua;
        this.ngayLap = ngayLap;
        this.maKTV = maKTV;
        this.trangThai = trangThai;
        this.tongSoPhuTungDung = tongSoPhuTungDung;
        this.tongTienVatTu = tongTienVatTu;
    }

    public String getMaPhieuSuaChua() { return maPhieuSuaChua; }
    public void setMaPhieuSuaChua(String maPhieuSuaChua) { this.maPhieuSuaChua = maPhieuSuaChua; }

    public Date getNgayLap() { return ngayLap; }
    public void setNgayLap(Date ngayLap) { this.ngayLap = ngayLap; }

    public String getMaKTV() { return maKTV; }
    public void setMaKTV(String maKTV) { this.maKTV = maKTV; }

    // Bổ sung Getter và Setter cho trangThai
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public int getTongSoPhuTungDung() { return tongSoPhuTungDung; }
    public void setTongSoPhuTungDung(int tongSoPhuTungDung) { this.tongSoPhuTungDung = tongSoPhuTungDung; }

    public double getTongTienVatTu() { return tongTienVatTu; }
    public void setTongTienVatTu(double tongTienVatTu) { this.tongTienVatTu = tongTienVatTu; }
}