package Entity;

public class ChiTietKiemKe {
    private String maPhieuKiemKe;
    private String maPhuTung;
    private int soLuongHeThong;
    private int soLuongThucTe;
    private int chenhLech;
    private String lyDoChenhLech;
    private String tenPhuTung;
    private String donViTinh;

    public ChiTietKiemKe() {}

    public ChiTietKiemKe(String maPhieuKiemKe, String maPhuTung, int soLuongHeThong,
                         int soLuongThucTe, String lyDoChenhLech) {
        this.maPhieuKiemKe = maPhieuKiemKe;
        this.maPhuTung = maPhuTung;
        this.soLuongHeThong = soLuongHeThong;
        this.soLuongThucTe = soLuongThucTe;
        this.chenhLech = soLuongThucTe - soLuongHeThong;
        this.lyDoChenhLech = lyDoChenhLech;
    }

    public String getMaPhieuKiemKe() {return maPhieuKiemKe;}
    public void setMaPhieuKiemKe(String maPhieuKiemKe) {this.maPhieuKiemKe = maPhieuKiemKe;}
    public String getMaPhuTung() {return maPhuTung;}
    public void setMaPhuTung(String maPhuTung) {this.maPhuTung = maPhuTung;}
    public int getSoLuongHeThong() {return soLuongHeThong;}
    public void setSoLuongHeThong(int soLuongHeThong) {this.soLuongHeThong = soLuongHeThong;}
    public int getSoLuongThucTe() {return soLuongThucTe;}
    public void setSoLuongThucTe(int soLuongThucTe) {this.soLuongThucTe = soLuongThucTe;}
    public int getChenhLech() {return chenhLech;}
    public void setChenhLech(int chenhLech) {this.chenhLech = chenhLech;}
    public String getLyDoChenhLech() {return lyDoChenhLech;}
    public void setLyDoChenhLech(String lyDoChenhLech) {this.lyDoChenhLech = lyDoChenhLech;}
    public String getTenPhuTung() {return tenPhuTung;}
    public void setTenPhuTung(String tenPhuTung) {this.tenPhuTung = tenPhuTung;}
    public String getDonViTinh() {return donViTinh;}
    public void setDonViTinh(String donViTinh) {this.donViTinh = donViTinh;}

    public int tinhChenhLech() {
        this.chenhLech = soLuongThucTe - soLuongHeThong;
        return chenhLech;
    }
}
