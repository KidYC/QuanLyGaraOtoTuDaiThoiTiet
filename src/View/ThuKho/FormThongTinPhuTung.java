package View.ThuKho;

import DAO.Kho_DAO;
import DAO.PhuTung_DAO;
import Entity.Kho;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class FormThongTinPhuTung extends JDialog implements ActionListener {

    private ManHinhQuanLyDanhMucPhuTung cha;
    private boolean chedosua;

    private String magoc = "";
    private String tengoc = "";
    private String donvigoc = "";
    private String soluonggoc = "";
    private String dongianhapgoc = "";
    private String dongiabangoc = "";

    private JLabel lbltieude;

    private JTextField txtmaphutung;
    private JTextField txttenphutung;
    private JTextField txtdonvi;
    private JTextField txtsoluong;
    private JTextField txtdongianhap;
    private JTextField txtdongiaban;
    private JComboBox<String> cbkho;

    private JButton btnquaylai;
    private JButton btnlammoi;
    private JButton btnluu;

    public FormThongTinPhuTung(ManHinhQuanLyDanhMucPhuTung cha) {
        super(cha, "THÊM PHỤ TÙNG", true);
        this.cha = cha;
        this.chedosua = false;
        khoiTao();
    }

    public FormThongTinPhuTung(ManHinhQuanLyDanhMucPhuTung cha, String ma, String ten, String donvi, String soluong,
                               String dongianhap, String dongiaban) {
        super(cha, "SỬA PHỤ TÙNG", true);
        this.cha = cha;
        this.chedosua = true;
        this.magoc = ma;
        this.tengoc = ten;
        this.donvigoc = donvi;
        this.soluonggoc = soluong;
        this.dongianhapgoc = dongianhap;
        this.dongiabangoc = dongiaban;
        khoiTao();
    }

    private void khoiTao() {
        setSize(580, 470);
        setLocationRelativeTo(cha);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();
        napDuLieu();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel(chedosua ? "SỬA PHỤ TÙNG" : "THÊM PHỤ TÙNG");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 22));
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbltieude.setForeground(new Color(44, 62, 80));
        lbltieude.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lbltieude, BorderLayout.NORTH);

        //pCenter
        JPanel pThongTin = new JPanel(new GridLayout(7, 2, 10, 10));
        pThongTin.setBackground(Color.WHITE);
        pThongTin.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông tin phụ tùng",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        txtmaphutung = new JTextField();
        txttenphutung = new JTextField();
        txtdonvi = new JTextField();
        txtsoluong = new JTextField();
        txtdongianhap = new JTextField();
        txtdongiaban = new JTextField();
        cbkho = new JComboBox<>();
        try {
            for (Kho k : new Kho_DAO().getAllKho()) {
                cbkho.addItem(k.getMaKho() + " - " + k.getTenKho());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách kho: " + e.getMessage());
        }

        pThongTin.add(new JLabel("Mã Phụ Tùng:"));
        pThongTin.add(txtmaphutung);
        pThongTin.add(new JLabel("Tên Phụ Tùng:"));
        pThongTin.add(txttenphutung);
        pThongTin.add(new JLabel("Đơn Vị:"));
        pThongTin.add(txtdonvi);
        pThongTin.add(new JLabel("Số Lượng:"));
        pThongTin.add(txtsoluong);
        pThongTin.add(new JLabel("Đơn Giá Nhập:"));
        pThongTin.add(txtdongianhap);
        pThongTin.add(new JLabel("Đơn Giá Bán:"));
        pThongTin.add(txtdongiaban);
        pThongTin.add(new JLabel("Kho"));
        pThongTin.add(cbkho);

        JPanel pGiua = new JPanel(new BorderLayout());
        pGiua.setBackground(new Color(245, 245, 245));
        pGiua.setBorder(new EmptyBorder(0, 20, 0, 20));
        pGiua.add(pThongTin, BorderLayout.NORTH);
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

        btnluu = new JButton("Lưu");
        btnluu.setPreferredSize(new Dimension(110, 35));
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnluu) {
            xuLyLuu();
        } else if (o == btnquaylai) {
            xuLyQuayLai();
        } else if (o == btnlammoi) {
            napDuLieu();
        }
    }

    // Thêm mới thì tự sinh mã phụ tùng, sửa thì giữ mã cũ
    private String taoMaPhuTung() {
        try {
            return new PhuTung_DAO().sinhMaPhuTung();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tạo mã phụ tùng: " + e.getMessage());
            return "";
        }
    }

    private void napDuLieu() {
        txtmaphutung.setText(chedosua ? magoc : taoMaPhuTung());
        txttenphutung.setText(tengoc);
        txtdonvi.setText(donvigoc);
        txtsoluong.setText(chedosua ? soluonggoc : "0");
        txtdongianhap.setText(dongianhapgoc);
        txtdongiaban.setText(dongiabangoc);
        txtsoluong.setEditable(false);
        txtsoluong.setBackground(new Color(236, 240, 241));

        // mã phụ tùng luôn tự sinh
        txtmaphutung.setEditable(false);
        txtmaphutung.setBackground(new Color(236, 240, 241));
        cbkho.setEnabled(!chedosua);
    }

    private void xuLyLuu() {
        String ma = txtmaphutung.getText().trim();
        String ten = txttenphutung.getText().trim();
        String donvi = txtdonvi.getText().trim();

        if (ma.isEmpty() || ten.isEmpty() || donvi.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ Mã, Tên và Đơn vị!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int soluong;
        try {
            soluong = Integer.parseInt(txtsoluong.getText().trim());
            if (soluong < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Số lượng phải là số nguyên không âm!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtsoluong.requestFocus();
            return;
        }

        double dongianhap;
        double dongiaban;
        try {
            dongianhap = Double.parseDouble(txtdongianhap.getText().replace(",", "").trim());
            dongiaban = Double.parseDouble(txtdongiaban.getText().replace(",", "").trim());
            if (dongianhap < 0 || dongiaban <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Đơn giá nhập phải là số không âm, đơn giá bán phải là số lớn hơn 0!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtdongiaban.requestFocus();
            return;
        }

        if (chedosua) {
            if (cha.capNhatPhuTung(ma, ten, donvi, soluong, dongianhap, dongiaban)) {
                JOptionPane.showMessageDialog(this, "Cập nhật phụ tùng thành công!");
                this.dispose();
            }
        } else {
            if (cha.tonTaiMa(ma)) {
                // có người vừa thêm phụ tùng khác cùng mã -> sinh lại mã mới
                txtmaphutung.setText(taoMaPhuTung());
                JOptionPane.showMessageDialog(this,
                        "Mã phụ tùng " + ma + " vừa được dùng! Đã đổi sang mã mới " + txtmaphutung.getText() + ", vui lòng bấm Lưu lại.",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (cbkho.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this,
                        "Chưa có kho nào trong hệ thống!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maKho = cbkho.getSelectedItem().toString().split(" - ")[0];
            if (cha.themPhuTung(ma, ten, donvi, soluong, maKho, dongianhap, dongiaban)) {
                JOptionPane.showMessageDialog(this, "Thêm phụ tùng thành công!");
                int chon = JOptionPane.showConfirmDialog(this,
                        "Bạn có muốn lập phiếu nhập kho cho phụ tùng này không?",
                        "Nhập kho", JOptionPane.YES_NO_OPTION);
                this.dispose();
                if (chon == JOptionPane.YES_OPTION) {
                    new ManHinhLapPhieuNhapKho(cha.getMaNhanVien(), ma).setVisible(true);
                }
            }
        }
    }

        private void xuLyQuayLai () {
            this.dispose();
        }
    }