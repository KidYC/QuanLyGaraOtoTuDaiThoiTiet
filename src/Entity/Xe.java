package Entity;

public class Xe {
    private String bienSo;
    private String hangXe;
    private String dongXe;
    private int namSanXuat;
    private KhachHang maKhachHang;

    public Xe() {}

    public Xe(String bienSo, String hangXe, String dongXe, int namSanXuat, KhachHang maKhachHang) {
        this.bienSo = bienSo;
        this.hangXe = hangXe;
        this.dongXe = dongXe;
        this.namSanXuat = namSanXuat; // hoặc namSanXuat
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

    // Getter trả về đối tượng KhachHang chuẩn hướng đối tượng
    public KhachHang getMaKhachHang() {
        return maKhachHang;
    }
    public void setMaKhachHang(KhachHang maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    // --- THÊM PHƯƠNG THỨC HỖ TRỢ NÀY VÀO XE.JAVA ---
    // Giúp lấy nhanh chuỗi mã khách hàng để tương thích ngược với code cũ ở các DAO/View
    public String getMaKhachHangStr() {
        return (this.maKhachHang != null) ? this.maKhachHang.getMaKhachHang() : "";
    }
}