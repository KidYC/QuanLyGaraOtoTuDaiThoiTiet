package Entity;

public class ThongTinHoaDon {
    private HoaDon maHoaDon;
    private String ngayLap;
    private PhieuSuaChua maPhieuSuaChua;
    private String hoTen;
    private Xe bienSo;
    private String soDienThoai;
    private String phuongThuc;
    private String trangThai;

    public ThongTinHoaDon() {
    }

    public ThongTinHoaDon(HoaDon maHoaDon, String ngayLap,
                          PhieuSuaChua maPhieuSuaChua, String hoTen,
                          Xe bienSo, String soDienThoai,
                          String phuongThuc, String trangThai) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.maPhieuSuaChua = maPhieuSuaChua;
        this.hoTen = hoTen;
        this.bienSo = bienSo;
        this.soDienThoai = soDienThoai;
        this.phuongThuc = phuongThuc;
        this.trangThai = trangThai;
    }

    public HoaDon getMaHoaDon() {
        return maHoaDon;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public PhieuSuaChua getMaPhieuSuaChua() {
        return maPhieuSuaChua;
    }

    public String getHoTen() {
        return hoTen;
    }

    public Xe getBienSo() {
        return bienSo;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }
}