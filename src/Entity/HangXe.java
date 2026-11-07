package Entity;

public class HangXe {
    private String maHangXe;
    private String tenHangXe;
    private String xuatXu;

    public HangXe() {}

    public HangXe(String maHangXe, String tenHangXe, String xuatXu) {
        this.maHangXe = maHangXe;
        this.tenHangXe = tenHangXe;
        this.xuatXu = xuatXu;
    }

    public String getMaHangXe() {
        return maHangXe;
    }

    public void setMaHangXe(String maHangXe) {
        this.maHangXe = maHangXe;
    }

    public String getTenHangXe() {
        return tenHangXe;
    }

    public void setTenHangXe(String tenHangXe) {
        this.tenHangXe = tenHangXe;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    @Override
    public String toString() {
        // Giúp khi đưa trực tiếp đối tượng HangXe vào JComboBox nó sẽ hiển thị tên hãng
        return this.tenHangXe;
    }
}