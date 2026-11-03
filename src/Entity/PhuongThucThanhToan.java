
package Entity;

public class PhuongThucThanhToan {
    private String maPhuongThuc;
    private String tenPhuongThuc;

    public PhuongThucThanhToan() {
    }

    public PhuongThucThanhToan(String maPhuongThuc,
                               String tenPhuongThuc) {
        this.maPhuongThuc = maPhuongThuc;
        this.tenPhuongThuc = tenPhuongThuc;
    }

    public String getMaPhuongThuc() {
        return maPhuongThuc;
    }

    public void setMaPhuongThuc(String maPhuongThuc) {
        this.maPhuongThuc = maPhuongThuc;
    }

    public String getTenPhuongThuc() {
        return tenPhuongThuc;
    }

    public void setTenPhuongThuc(String tenPhuongThuc) {
        this.tenPhuongThuc = tenPhuongThuc;
    }
}
