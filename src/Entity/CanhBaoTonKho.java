package Entity;

import java.util.Date;

public class CanhBaoTonKho {
    private String maCanhBao;
    private Date ngayCanhBao;
    private String loaiCanhBao;
    private Boolean trangThaiDaXem;
    private PhuTung phuTung;

    public CanhBaoTonKho() {}
    public CanhBaoTonKho(String maCanhBao, Date ngayCanhBao, String loaiCanhBao, Boolean trangThaiDaXem, PhuTung phuTung) {
        this.maCanhBao = maCanhBao;
        this.ngayCanhBao = ngayCanhBao;
        this.loaiCanhBao = loaiCanhBao;
        this.trangThaiDaXem = trangThaiDaXem;
        this.phuTung = phuTung;
    }

    public String getMaCanhBao() {return maCanhBao;}
    public void setMaCanhBao(String maCanhBao) {this.maCanhBao = maCanhBao;}
    public Date getNgayCanhBao() {return ngayCanhBao;}
    public void setNgayCanhBao(Date ngayCanhBao) {this.ngayCanhBao = ngayCanhBao;}
    public String getLoaiCanhBao() {return loaiCanhBao;}
    public void setLoaiCanhBao(String loaiCanhBao) {this.loaiCanhBao = loaiCanhBao;}
    public Boolean getTrangThaiDaXem() {return trangThaiDaXem;}
    public void setTrangThaiDaXem(Boolean trangThaiDaXem) {this.trangThaiDaXem = trangThaiDaXem;}
    public PhuTung getPhuTung() {return phuTung;}
    public void setPhuTung(PhuTung phuTung) {this.phuTung = phuTung;}
}
