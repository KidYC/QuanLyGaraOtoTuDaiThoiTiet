
package Entity;

public class Xe {
    private String bienSo;
    private String hangXe;
    private String dongXe;
    private int namSanXuat;
    private String maKhachHang;

    public Xe() {}
    public Xe(String bienSo, String hangXe, String dongXe, int namSanXuat, String maKhachHang) {
        this.bienSo = bienSo;
        this.hangXe = hangXe;
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
    public String getHangXe() {
        return hangXe;
    }
    public void setHangXe(String hangXe) {
        this.hangXe = hangXe;
    }
    public String getDongXe() {
        return dongXe;
    }
    public void setDongXe(String dongXe) {
        this.dongXe = dongXe;
    }
    public int getNamSanXuat() {
        return namSanXuat;
    }
    public void setNamSanXuat(int namSanXuat) {
        this.namSanXuat = namSanXuat;
    }
    public String getMaKhachHang() {
        return maKhachHang;
    }
    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
}