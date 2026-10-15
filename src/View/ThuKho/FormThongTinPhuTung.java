package View.ThuKho;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FormThongTinPhuTung extends JDialog implements ActionListener {

    private ManHinhQuanLyDanhMucPhuTung cha;
    private boolean chedosua;

    // giá trị ban đầu khi sửa, dùng cho nút Làm Mới
    private String magoc = "";
    private String tengoc = "";
    private String donvigoc = "";
    private String soluonggoc = "";

    private JLabel lbltieude;

    private JTextField txtmaphutung;
    private JTextField txttenphutung;
    private JTextField txtdonvi;
    private JTextField txtsoluong;

    private JButton btnquaylai;
    private JButton btnlammoi;
    private JButton btnluu;

    public FormThongTinPhuTung(ManHinhQuanLyDanhMucPhuTung cha) {
        super(cha, "THÊM PHỤ TÙNG", true);
        this.cha = cha;
        this.chedosua = false;
        khoiTao();
    }

    public FormThongTinPhuTung(ManHinhQuanLyDanhMucPhuTung cha, String ma, String ten, String donvi, String soluong) {
        super(cha, "SỬA PHỤ TÙNG", true);
        this.cha = cha;
        this.chedosua = true;
        this.magoc = ma;
        this.tengoc = ten;
        this.donvigoc = donvi;
        this.soluonggoc = soluong;
        khoiTao();
    }

    private void khoiTao() {
        setSize(520, 380);
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
        JPanel pThongTin = new JPanel(new GridLayout(4, 2, 10, 10));
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

        pThongTin.add(new JLabel("Mã Phụ Tùng:"));
        pThongTin.add(txtmaphutung);
        pThongTin.add(new JLabel("Tên Phụ Tùng:"));
        pThongTin.add(txttenphutung);
        pThongTin.add(new JLabel("Đơn Vị:"));
        pThongTin.add(txtdonvi);
        pThongTin.add(new JLabel("Số Lượng:"));
        pThongTin.add(txtsoluong);

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

    private void napDuLieu() {
        txtmaphutung.setText(magoc);
        txttenphutung.setText(tengoc);
        txtdonvi.setText(donvigoc);
        txtsoluong.setText(soluonggoc);

        txtmaphutung.setEditable(!chedosua);
        txtmaphutung.setBackground(chedosua ? new Color(236, 240, 241) : Color.WHITE);
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

        if (chedosua) {
            cha.capNhatPhuTung(ma, ten, donvi, soluong);
            JOptionPane.showMessageDialog(this, "Cập nhật phụ tùng thành công!");
        } else {
            if (cha.tonTaiMa(ma)) {
                JOptionPane.showMessageDialog(this,
                        "Mã phụ tùng " + ma + " đã tồn tại!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                txtmaphutung.requestFocus();
                return;
            }
            cha.themPhuTung(ma, ten, donvi, soluong);
            JOptionPane.showMessageDialog(this, "Thêm phụ tùng thành công!");
        }
        this.dispose();
    }

    private void xuLyQuayLai() {
        this.dispose();
    }
}