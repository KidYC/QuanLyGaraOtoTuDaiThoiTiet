package Entity;

import java.sql.Timestamp;

public class BaoHanh {
    private PhieuSuaChua phieuSuaChua;   // FK maPhieuSuaChua
    private DichVu dichVu;               // FK maDichVu
    private Timestamp ngayKichHoat;
    private Timestamp ngayHetHan;
    private String dieuKhoan;
    private String trangThai;

    public BaoHanh() {}

    public BaoHanh(PhieuSuaChua phieuSuaChua, DichVu dichVu, Timestamp ngayKichHoat,
                   Timestamp ngayHetHan, String dieuKhoan, String trangThai) {
        this.phieuSuaChua = phieuSuaChua;
        this.dichVu = dichVu;
        this.ngayKichHoat = ngayKichHoat;
        this.ngayHetHan = ngayHetHan;
        this.dieuKhoan = dieuKhoan;
        this.trangThai = trangThai;
    }

    public PhieuSuaChua getPhieuSuaChua() { return phieuSuaChua; }
    public void setPhieuSuaChua(PhieuSuaChua phieuSuaChua) { this.phieuSuaChua = phieuSuaChua; }

    public DichVu getDichVu() { return dichVu; }
    public void setDichVu(DichVu dichVu) { this.dichVu = dichVu; }

    public Timestamp getNgayKichHoat() { return ngayKichHoat; }
    public void setNgayKichHoat(Timestamp ngayKichHoat) { this.ngayKichHoat = ngayKichHoat; }

    public Timestamp getNgayHetHan() { return ngayHetHan; }
    public void setNgayHetHan(Timestamp ngayHetHan) { this.ngayHetHan = ngayHetHan; }

    public String getDieuKhoan() { return dieuKhoan; }
    public void setDieuKhoan(String dieuKhoan) { this.dieuKhoan = dieuKhoan; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    // --- Helper ---
    public String getMaPhieuSuaChua() {
        return (phieuSuaChua != null) ? phieuSuaChua.getMaPhieuSuaChua() : "";
    }

    public String getMaDichVu() {
        return (dichVu != null) ? dichVu.getMaDichVu() : "";
    }

    public String getTenDichVu() {
        return (dichVu != null) ? dichVu.getTenDichVu() : "";
    }

    public int getThoiGianBaoHanhThang() {
        return (dichVu != null) ? dichVu.getThoiGianBaoHanh() : 0;
    }

    // Trạng thái thực tế: tự chuyển "Hết hạn" khi quá hạn
    public String getTrangThaiHienTai() {
        if (ngayHetHan != null && ngayHetHan.before(new Timestamp(System.currentTimeMillis()))) {
            return "Hết hạn";
        }
        return trangThai;
    }
}