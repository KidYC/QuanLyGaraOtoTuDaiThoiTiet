package View.LeTan;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManHinhLapPhieuTiepNhan extends JFrame implements ActionListener {

    private JLabel lblTitle;

    private JTextField txtMaPhieu;
    private JTextField txtNgayTiepNhan;
    private JTextField txtNhanVien;

    private JTextField txtTenKH;
    private JTextField txtSDT;
    private JTextField txtBienSo;
    private JTextField txtLoaiXe;
    private JTextField txtSoKM;

    private JComboBox<String> cboMucNhienLieu;
    private JTextArea txtTinhTrangXe;
    private JTextArea txtYeuCauKhach;
    private JTextField txtHenTraXe;
    private JTextArea txtGhiChu;

    private JButton btnLuuPhieu;
    private JButton btnQuayLai;
    private JButton btnLamMoi;

    public ManHinhLapPhieuTiepNhan() {
        this("Nguyễn Văn A", "0901234567", "51F-123.45", "Toyota Vios");
    }

    public ManHinhLapPhieuTiepNhan(String tenKH, String sdt, String bienSo, String loaiXe) {
        setTitle("LẬP PHIẾU TIẾP NHẬN XE");
        setSize(850, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();

        if (tenKH != null) txtTenKH.setText(tenKH);
        if (sdt != null) txtSDT.setText(sdt);
        if (bienSo != null) txtBienSo.setText(bienSo);
        if (loaiXe != null) txtLoaiXe.setText(loaiXe);
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lblTitle = new JLabel("LẬP PHIẾU TIẾP NHẬN XE");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pThongTin = new JPanel(new GridBagLayout());
        pThongTin.setBackground(Color.WHITE);
        pThongTin.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông tin chung & Khách hàng - Xe",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 0; gbc.gridx = 0;
        pThongTin.add(new JLabel("Mã Phiếu:"), gbc);
        gbc.gridx = 1;
        txtMaPhieu = new JTextField("KH001");
        txtMaPhieu.setEditable(false);
        txtMaPhieu.setBackground(new Color(236, 240, 241));
        pThongTin.add(txtMaPhieu, gbc);

        gbc.gridx = 2;
        pThongTin.add(new JLabel("Ngày Tiếp Nhận:"), gbc);
        gbc.gridx = 3;
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        txtNgayTiepNhan = new JTextField(now, 15);
        txtNgayTiepNhan.setEditable(false);
        txtNgayTiepNhan.setBackground(new Color(236, 240, 241));
        pThongTin.add(txtNgayTiepNhan, gbc);

        // Hàng 1: Khách hàng & SĐT
        gbc.gridy = 1; gbc.gridx = 0;
        pThongTin.add(new JLabel("Tên Khách Hàng:"), gbc);
        gbc.gridx = 1;
        txtTenKH = new JTextField(15);
        pThongTin.add(txtTenKH, gbc);

        gbc.gridx = 2;
        pThongTin.add(new JLabel("Số Điện Thoại:"), gbc);
        gbc.gridx = 3;
        txtSDT = new JTextField(15);
        pThongTin.add(txtSDT, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        pThongTin.add(new JLabel("Biển Số Xe:"), gbc);
        gbc.gridx = 1;
        txtBienSo = new JTextField(15);
        pThongTin.add(txtBienSo, gbc);

        gbc.gridx = 2;
        pThongTin.add(new JLabel("Loại Xe:"), gbc);
        gbc.gridx = 3;
        txtLoaiXe = new JTextField(15);
        pThongTin.add(txtLoaiXe, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        pThongTin.add(new JLabel("Số KM Hiện Tại:"), gbc);
        gbc.gridx = 1;
        txtSoKM = new JTextField(15);
        pThongTin.add(txtSoKM, gbc);

        gbc.gridx = 2;
        pThongTin.add(new JLabel("Mức Nhiên Liệu:"), gbc);
        gbc.gridx = 3;
        cboMucNhienLieu = new JComboBox<>(new String[]{"Dưới 1/4", "1/4", "1/2", "3/4", "Đầy bình"});
        pThongTin.add(cboMucNhienLieu, gbc);

        pCenter.add(pThongTin);
        pCenter.add(Box.createVerticalStrut(10));
        add(pCenter, BorderLayout.CENTER);

        //PSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnQuayLai = new JButton("Quay Lại");
        btnQuayLai.setPreferredSize(new Dimension(110, 35));
        btnQuayLai.setBackground(new Color(149, 165, 166));
        btnQuayLai.setForeground(Color.WHITE);
        btnQuayLai.setFocusPainted(false);

        btnLamMoi = new JButton("Làm Mới");
        btnLamMoi.setPreferredSize(new Dimension(110, 35));
        btnLamMoi.setBackground(new Color(52, 152, 219));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setFocusPainted(false);

        btnLuuPhieu = new JButton("Lưu Phiếu");
        btnLuuPhieu.setPreferredSize(new Dimension(120, 35));
        btnLuuPhieu.setBackground(new Color(46, 204, 113));
        btnLuuPhieu.setForeground(Color.WHITE);
        btnLuuPhieu.setFont(new Font("Arial", Font.BOLD, 12));
        btnLuuPhieu.setFocusPainted(false);

        pSouth.add(btnQuayLai);
        pSouth.add(btnLamMoi);
        pSouth.add(btnLuuPhieu);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btnLuuPhieu.addActionListener(this);
        btnQuayLai.addActionListener(this);
        btnLamMoi.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnLuuPhieu) {
            xuLyLuuPhieu();
        } else if (source == btnQuayLai) {
            xuLyQuayLai();
        } else if (source == btnLamMoi) {
            xuLyLamMoi();
        }
    }

    private void xuLyLuuPhieu() {
        if (txtTenKH.getText().trim().isEmpty() || txtBienSo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ Tên khách hàng và Biển số xe!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opt = JOptionPane.showConfirmDialog(this,
                "Lập phiếu tiếp nhận " + txtMaPhieu.getText() + " thành công!\n"
                        + "Xe đã được chuyển sang danh sách chờ báo giá.\n"
                        + "Bạn có muốn mở 'Màn hình Danh sách xe chờ báo giá' ngay không?",
                "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (opt == JOptionPane.YES_OPTION) {
            ManHinhDanhSachXeChoBaoGia UI = new ManHinhDanhSachXeChoBaoGia();
            UI.setVisible(true);
            this.dispose();
        } else {
            TrangChuLeTan Home = new TrangChuLeTan();
            Home.setVisible(true);
            this.dispose();
        }
    }

    private void xuLyQuayLai() {
        this.dispose();
    }

    private void xuLyLamMoi() {
        txtTinhTrangXe.setText("");
        txtYeuCauKhach.setText("");
        txtSoKM.setText("");
        txtHenTraXe.setText("");
        cboMucNhienLieu.setSelectedIndex(0);
    }

    public static void main(String[] args) {
            ManHinhLapPhieuTiepNhan form = new ManHinhLapPhieuTiepNhan();
            form.setVisible(true);
    }
}