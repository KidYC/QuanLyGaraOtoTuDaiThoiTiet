package Entity;

public class DongXe {
    private String maDongXe;
    private String tenDongXe;
    private HangXe hangXe;

    public DongXe() {}

    public DongXe(String maDongXe, String tenDongXe, HangXe hangXe) {
        this.maDongXe = maDongXe;
        this.tenDongXe = tenDongXe;
        this.hangXe = hangXe;
    }

    public String getMaDongXe() {
        return maDongXe;
    }

    public void setMaDongXe(String maDongXe) {
        this.maDongXe = maDongXe;
    }

    public String getTenDongXe() {
        return tenDongXe;
    }

    public void setTenDongXe(String tenDongXe) {
        this.tenDongXe = tenDongXe;
    }

    public HangXe getHangXe() {
        return hangXe;
    }

    public void setHangXe(HangXe hangXe) {
        this.hangXe = hangXe;
    }

    @Override
    public String toString() {
        return this.tenDongXe;
    }
}