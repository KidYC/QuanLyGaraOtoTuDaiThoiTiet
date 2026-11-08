package Entity;

public class ChiTietKiemKe {
    private PhieuKiemKe phieuKiemKe;
    private PhuTung phuTung;
    private int soLuongHeThong;
    private int soLuongThucTe;
    private int chenhLech;
    private String lyDoChenhLech;

    public ChiTietKiemKe() {}

    public ChiTietKiemKe(PhieuKiemKe phieuKiemKe, PhuTung phuTung, int soLuongHeThong,
                         int soLuongThucTe, String lyDoChenhLech) {
        this.phieuKiemKe = phieuKiemKe;
        this.phuTung = phuTung;
        this.soLuongHeThong = soLuongHeThong;
        this.soLuongThucTe = soLuongThucTe;
        this.chenhLech = soLuongThucTe - soLuongHeThong;
        this.lyDoChenhLech = lyDoChenhLech;
    }

    public PhieuKiemKe getPhieuKiemKe() {return phieuKiemKe;}
    public void setPhieuKiemKe(PhieuKiemKe phieuKiemKe) {this.phieuKiemKe = phieuKiemKe;}
    public PhuTung getPhuTung() {return phuTung;}
    public void setPhuTung(PhuTung phuTung) {this.phuTung = phuTung;}
    public int getSoLuongHeThong() {return soLuongHeThong;}
    public void setSoLuongHeThong(int soLuongHeThong) {this.soLuongHeThong = soLuongHeThong;}
    public int getSoLuongThucTe() {return soLuongThucTe;}
    public void setSoLuongThucTe(int soLuongThucTe) {this.soLuongThucTe = soLuongThucTe;}
    public int getChenhLech() {return chenhLech;}
    public void setChenhLech(int chenhLech) {this.chenhLech = chenhLech;}
    public String getLyDoChenhLech() {return lyDoChenhLech;}
    public void setLyDoChenhLech(String lyDoChenhLech) {this.lyDoChenhLech = lyDoChenhLech;}


    public String getMaPhieuKiemKe() {return phieuKiemKe != null ? phieuKiemKe.getMaPhieuKiemKe() : "";}
    public String getMaPhuTung() {return phuTung != null ? phuTung.getMaPhuTung() : "";}
    public String getTenPhuTung() {return phuTung != null ? phuTung.getTenPhuTung() : "";}
    public String getDonViTinh() {return phuTung != null ? phuTung.getDonViTinh() : "";}

    public int tinhChenhLech() {
        this.chenhLech = soLuongThucTe - soLuongHeThong;
        return chenhLech;
    }
}
