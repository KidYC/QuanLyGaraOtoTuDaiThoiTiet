package Entity;

public class Xe {
    private String bienSo;
    private DongXe dongXe;
    private int namSanXuat;
    private KhachHang maKhachHang;

    public Xe() {}

    public Xe(String bienSo, DongXe dongXe, int namSanXuat, KhachHang maKhachHang) {
        this.bienSo = bienSo;
        this.dongXe = dongXe;
        this.namSanXuat = namSanXuat;
        this.maKhachHang = maKhachHang;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public DongXe getDongXe() {
        return dongXe;
    }

    public void setDongXe(DongXe dongXe) {
        this.dongXe = dongXe;
    }

    public int getNamSanXuat() {
        return namSanXuat;
    }

    public void setNamSanXuat(int namSanXuat) {
        this.namSanXuat = namSanXuat;
    }

    public KhachHang getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(KhachHang maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public HangXe getHangXe() {
        return (this.dongXe != null) ? this.dongXe.getHangXe() : null;
    }

    public String getMaKhachHangStr() {
        return (this.maKhachHang != null) ? this.maKhachHang.getMaKhachHang() : "";
    }

    public String getTenHangXeStr() {
        return (getHangXe() != null) ? getHangXe().getTenHangXe() : "";
    }

    public String getTenDongXeStr() {
        return (this.dongXe != null) ? this.dongXe.getTenDongXe() : "";
    }
}