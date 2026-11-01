package View.ThuKho;

import DAO.NhaCungCap_DAO;
import DAO.PhieuNhapKho_DAO;
import DAO.PhuTung_DAO;
import Entity.ChiTietPhieuNhap;
import Entity.NhaCungCap;
import Entity.PhieuNhapKho;
import Entity.PhuTung;
import Entity.ThuKho;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ManHinhLapPhieuNhapKho extends JFrame implements ActionListener {

    private JLabel lbltieude;

    private JTextField txtmaphieu;
    private JTextField txtngaynhap;
    private JComboBox<String> cbnhacungcap;
    private JTextField txtnhanvien;
    private JTextField txtghichu;

    private JComboBox<String> cbphutung;
    private JTextField txtdonvi;
    private JTextField txtsoluong;
    private JTextField txtdongia;
    private JTextField txtthanhtien;

    private JButton btnquaylai;
    private JButton btnlammoi;
    private JButton btnluu;

    private final PhuTung_DAO phuTungDAO = new PhuTung_DAO();
    private final NhaCungCap_DAO nhaCungCapDAO = new NhaCungCap_DAO();
    private final PhieuNhapKho_DAO phieuNhapDAO = new PhieuNhapKho_DAO();

    private List<PhuTung> dsphutung = new ArrayList<>();
    private List<NhaCungCap> dsnhacungcap = new ArrayList<>();
    private final DecimalFormat dinhdang = new DecimalFormat("#,###");
    private boolean chedosua = false;
    private String maphieugoc = "";

    public ManHinhLapPhieuNhapKho() {
        khoiTao();
        txtmaphieu.setText(taoMaPhieu());
    }

    public ManHinhLapPhieuNhapKho(String maphieu) {
        this.chedosua = true;
        this.maphieugoc = maphieu;
        khoiTao();
        napDuLieuSua();
    }

    private void khoiTao() {
        setTitle(chedosua ? "SỬA PHIẾU NHẬP KHO" : "LẬP PHIẾU NHẬP KHO");
        setSize(850, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        taiDanhMuc();
        buildUI();
    }

    private void napDuLieuSua() {
        try {
            PhieuNhapKho pn = phieuNhapDAO.timPhieuNhapTheoMa(maphieugoc);
            List<ChiTietPhieuNhap> dsct = phieuNhapDAO.getChiTietPhieuNhap(maphieugoc);
            if (pn == null || dsct.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu của phiếu " + maphieugoc + "!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                btnluu.setEnabled(false);
                return;
            }

            txtmaphieu.setText(pn.getMaPhieuNhap());
            if (pn.getNgayNhap() != null) {
                txtngaynhap.setText(new SimpleDateFormat("dd/MM/yyyy").format(pn.getNgayNhap()));
            }
            txtnhanvien.setText(pn.getThuKho().getMaNhanVien());

            String mancc = pn.getNhaCungCap().getMaNhaCungCap();
            for (int i = 0; i < dsnhacungcap.size(); i++) {
                if (dsnhacungcap.get(i).getMaNhaCungCap().equals(mancc)) {
                    cbnhacungcap.setSelectedIndex(i);
                }
            }

            ChiTietPhieuNhap ct = dsct.get(0);
            for (int i = 0; i < dsphutung.size(); i++) {
                if (dsphutung.get(i).getMaPhuTung().equals(ct.getMaPhuTung())) {
                    cbphutung.setSelectedIndex(i);
                }
            }
            txtsoluong.setText(String.valueOf(ct.getSoLuongNhap()));
            txtdongia.setText(String.valueOf((long) ct.getDonGiaNhap()));
            tinhThanhTien();

            if (dsct.size() > 1) {
                JOptionPane.showMessageDialog(this,
                        "Phiếu này có nhiều phụ tùng, màn hình chỉ sửa được phiếu có 1 phụ tùng!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                btnluu.setEnabled(false);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    private void taiDanhMuc() {
        try {
            dsphutung = phuTungDAO.getAllPhuTung();
            dsnhacungcap = nhaCungCapDAO.getAllNhaCungCap();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    private String taoMaPhieu() {
        try {
            return phieuNhapDAO.sinhMaPhieuNhap();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tạo mã phiếu: " + e.getMessage());
            return "";
        }
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel(chedosua ? "SỬA PHIẾU NHẬP KHO" : "LẬP PHIẾU NHẬP KHO");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 22));
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbltieude.setForeground(new Color(44, 62, 80));
        lbltieude.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lbltieude, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel(new BorderLayout(0, 10));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pThongTin = new JPanel(new GridLayout(3, 4, 10, 10));
        pThongTin.setBackground(Color.WHITE);
        pThongTin.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông tin phiếu nhập",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        txtmaphieu = new JTextField();
        txtmaphieu.setEditable(false);
        txtmaphieu.setBackground(new Color(236, 240, 241));

        String homnay = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        txtngaynhap = new JTextField(homnay);
        txtngaynhap.setEditable(false);
        txtngaynhap.setBackground(new Color(236, 240, 241));

        cbnhacungcap = new JComboBox<>();
        for (NhaCungCap ncc : dsnhacungcap) {
            cbnhacungcap.addItem(ncc.getTenNhaCungCap());
        }

        txtnhanvien = new JTextField();

        txtghichu = new JTextField();

        pThongTin.add(new JLabel("Mã Phiếu:"));
        pThongTin.add(txtmaphieu);
        pThongTin.add(new JLabel("Ngày Nhập:"));
        pThongTin.add(txtngaynhap);
        pThongTin.add(new JLabel("Nhà Cung Cấp:"));
        pThongTin.add(cbnhacungcap);
        pThongTin.add(new JLabel("Mã Nhân Viên Lập:"));
        pThongTin.add(txtnhanvien);
        pThongTin.add(new JLabel("Ghi Chú:"));
        pThongTin.add(txtghichu);
        pThongTin.add(new JLabel(""));
        pThongTin.add(new JLabel(""));

        JPanel pChiTiet = new JPanel(new GridLayout(3, 4, 10, 10));
        pChiTiet.setBackground(Color.WHITE);
        pChiTiet.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Phụ tùng nhập kho",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        cbphutung = new JComboBox<>();
        for (PhuTung pt : dsphutung) {
            cbphutung.addItem(pt.getMaPhuTung() + " - " + pt.getTenPhuTung());
        }

        txtdonvi = new JTextField();
        if (!dsphutung.isEmpty()) {
            txtdonvi.setText(dsphutung.get(0).getDonViTinh());
        }
        txtdonvi.setEditable(false);
        txtdonvi.setBackground(new Color(236, 240, 241));

        txtsoluong = new JTextField();
        txtdongia = new JTextField();

        txtthanhtien = new JTextField();
        txtthanhtien.setEditable(false);
        txtthanhtien.setBackground(new Color(236, 240, 241));

        pChiTiet.add(new JLabel("Phụ Tùng:"));
        pChiTiet.add(cbphutung);
        pChiTiet.add(new JLabel("Đơn Vị:"));
        pChiTiet.add(txtdonvi);
        pChiTiet.add(new JLabel("Số Lượng:"));
        pChiTiet.add(txtsoluong);
        pChiTiet.add(new JLabel("Đơn Giá:"));
        pChiTiet.add(txtdongia);
        pChiTiet.add(new JLabel("Thành Tiền:"));
        pChiTiet.add(txtthanhtien);
        pChiTiet.add(new JLabel(""));
        pChiTiet.add(new JLabel(""));

        pCenter.add(pThongTin, BorderLayout.NORTH);
        pCenter.add(pChiTiet, BorderLayout.CENTER);

        JPanel pGiua = new JPanel(new BorderLayout());
        pGiua.setBackground(new Color(245, 245, 245));
        pGiua.add(pCenter, BorderLayout.NORTH);
        add(pGiua, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnquaylai = new JButton("Quay Lại");
        btnquaylai.setPreferredSize(new Dimension(110, 35));
        btnquaylai.setBackground(new Color(149, 165, 166));
        btnquaylai.setForeground(Color.WHITE);
        btnquaylai.setFocusPainted(false);

        btnlammoi = new JButton("Làm Mới");
        btnlammoi.setPreferredSize(new Dimension(110, 35));
        btnlammoi.setBackground(new Color(52, 152, 219));
        btnlammoi.setForeground(Color.WHITE);
        btnlammoi.setFocusPainted(false);

        btnluu = new JButton("Lưu Phiếu");
        btnluu.setPreferredSize(new Dimension(120, 35));
        btnluu.setBackground(new Color(46, 204, 113));
        btnluu.setForeground(Color.WHITE);
        btnluu.setFont(new Font("Arial", Font.BOLD, 12));
        btnluu.setFocusPainted(false);

        pSouth.add(btnquaylai);
        pSouth.add(btnlammoi);
        pSouth.add(btnluu);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btnluu.addActionListener(this);
        btnquaylai.addActionListener(this);
        btnlammoi.addActionListener(this);
        cbphutung.addActionListener(this);
        txtsoluong.addActionListener(this);
        txtdongia.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnluu) {
            xuLyLuuPhieu();
        } else if (o == btnquaylai) {
            xuLyQuayLai();
        } else if (o == btnlammoi) {
            xuLyLamMoi();
        } else if (o == cbphutung) {
            xuLyDoiPhuTung();
        } else if (o == txtsoluong || o == txtdongia) {
            tinhThanhTien();
        }
    }

    private void xuLyDoiPhuTung() {
        int vitri = cbphutung.getSelectedIndex();
        if (vitri >= 0) {
            txtdonvi.setText(dsphutung.get(vitri).getDonViTinh());
        }
    }

    private boolean tinhThanhTien() {
        try {
            int soluong = Integer.parseInt(txtsoluong.getText().trim());
            long dongia = Long.parseLong(txtdongia.getText().replace(",", "").trim());
            if (soluong <= 0 || dongia <= 0) throw new NumberFormatException();
            txtthanhtien.setText(dinhdang.format(soluong * dongia));
            return true;
        } catch (NumberFormatException ex) {
            txtthanhtien.setText("");
            return false;
        }
    }

    private void xuLyLuuPhieu() {
        String manv = txtnhanvien.getText().trim();
        if (manv.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập mã nhân viên lập phiếu!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtnhanvien.requestFocus();
            return;
        }

        if (dsphutung.isEmpty() || dsnhacungcap.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Hệ thống chưa có phụ tùng hoặc nhà cung cấp!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!tinhThanhTien()) {
            JOptionPane.showMessageDialog(this,
                    "Số lượng và đơn giá phải là số lớn hơn 0!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtsoluong.requestFocus();
            return;
        }

        PhuTung pt = dsphutung.get(cbphutung.getSelectedIndex());
        NhaCungCap ncc = dsnhacungcap.get(cbnhacungcap.getSelectedIndex());
        int soluong = Integer.parseInt(txtsoluong.getText().trim());
        double dongia = Double.parseDouble(txtdongia.getText().replace(",", "").trim());

        PhieuNhapKho pn = new PhieuNhapKho();
        pn.setThuKho(new ThuKho(manv, null, null));
        pn.setMaPhieuNhap(txtmaphieu.getText());
        pn.setNgayNhap(new java.util.Date());
        pn.setTongTienNhap(0.0);
        pn.setNhaCungCap(ncc);
        List<ChiTietPhieuNhap> dsChiTiet = new ArrayList<>();
        dsChiTiet.add(new ChiTietPhieuNhap(pn.getMaPhieuNhap(), pt.getMaPhuTung(), soluong, dongia));

        try {
            boolean thanhcong = chedosua
                    ? phieuNhapDAO.capNhatPhieuNhap(pn, dsChiTiet)
                    : phieuNhapDAO.taoPhieuNhap(pn, dsChiTiet);
            if (!thanhcong) {
                JOptionPane.showMessageDialog(this,
                        chedosua ? "Sửa phiếu nhập thất bại! Tồn kho không đủ để điều chỉnh." : "Lập phiếu nhập thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("FOREIGN KEY")) {
                JOptionPane.showMessageDialog(this,
                        "Mã nhân viên " + manv + " không tồn tại trong danh sách thủ kho!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtnhanvien.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi lưu phiếu nhập: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        JOptionPane.showMessageDialog(this,
                (chedosua ? "Sửa" : "Lập") + " phiếu nhập " + txtmaphieu.getText() + " thành công!");
        this.dispose();
    }

    private void xuLyLamMoi() {
        if (chedosua) {
            napDuLieuSua();
            return;
        }
        txtghichu.setText("");
        if (cbnhacungcap.getItemCount() > 0) cbnhacungcap.setSelectedIndex(0);
        if (cbphutung.getItemCount() > 0) {
            cbphutung.setSelectedIndex(0);
            txtdonvi.setText(dsphutung.get(0).getDonViTinh());
        }
        txtsoluong.setText("");
        txtdongia.setText("");
        txtthanhtien.setText("");
    }

    private void xuLyQuayLai() {
        this.dispose();
    }

    public static void main(String[] args) {
        ManHinhLapPhieuNhapKho form = new ManHinhLapPhieuNhapKho();
        form.setVisible(true);
    }
}