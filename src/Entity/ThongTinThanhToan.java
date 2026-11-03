package Entity;

import java.math.BigDecimal;

public class ThongTinThanhToan {

    private BigDecimal tongTienPhuTung;
    private BigDecimal tongTienDichVu;
    private BigDecimal tongCong;

    // Constructor không tham số
    public ThongTinThanhToan() {
    }

    // Constructor có tham số
    public ThongTinThanhToan(
            BigDecimal tongTienPhuTung,
            BigDecimal tongTienDichVu,
            BigDecimal tongCong) {

        this.tongTienPhuTung = tongTienPhuTung;
        this.tongTienDichVu = tongTienDichVu;
        this.tongCong = tongCong;
    }

    // Getter và Setter
    public BigDecimal getTongTienPhuTung() {
        return tongTienPhuTung;
    }

    public void setTongTienPhuTung(BigDecimal tongTienPhuTung) {
        this.tongTienPhuTung = tongTienPhuTung;
    }

    public BigDecimal getTongTienDichVu() {
        return tongTienDichVu;
    }

    public void setTongTienDichVu(BigDecimal tongTienDichVu) {
        this.tongTienDichVu = tongTienDichVu;
    }

    public BigDecimal getTongCong() {
        return tongCong;
    }

    public void setTongCong(BigDecimal tongCong) {
        this.tongCong = tongCong;
    }
}