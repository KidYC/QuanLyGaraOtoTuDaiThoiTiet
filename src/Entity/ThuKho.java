package Entity;

public class ThuKho {

    private String maNhanVien;
    private String hoTen;
    private String soDienThoai;

    public ThuKho() {}
    public ThuKho(String maNhanVien, String hoTen, String soDienThoai) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
    }

    public String getMaNhanVien() {return maNhanVien;}
    public void setMaNhanVien(String maNhanVien) {this.maNhanVien = maNhanVien;}
    public String getHoTen() {return hoTen;}
    public void setHoTen(String hoTen) {this.hoTen = hoTen;}
    public String getSoDienThoai() {return soDienThoai;}
    public void setSoDienThoai(String soDienThoai) {this.soDienThoai = soDienThoai;}
}
