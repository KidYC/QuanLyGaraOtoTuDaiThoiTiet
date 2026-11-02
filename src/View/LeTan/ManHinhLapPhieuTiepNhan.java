package View.LeTan;

import DAO.PhieuTiepNhan_DAO;
import Entity.PhieuTiepNhan;
import View.HeThong.TrangChuHeThong;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManHinhLapPhieuTiepNhan extends JFrame implements ActionListener {

    private JLabel lblTitle;

    private JTextField txtMaPhieu;
    private JTextField txtNgayTiepNhan;
    private JTextField txtBienSo;
    private JComboBox<String> cboTrangThai;
    private JTextArea txtTinhTrangXe;
    private JTextArea txtYeuCauKhach;

    private JButton btnLuuPhieu;
    private JButton btnQuayLai;
    private JButton btnLamMoi;

    public ManHinhLapPhieuTiepNhan() {
        this("Nguyễn Văn A", "0901234567", "51F-123.45", "Toyota Vios");
    }

    public ManHinhLapPhieuTiepNhan(String tenKH, String sdt, String bienSo, String loaiXe) {
        setTitle("LẬP PHIẾU TIẾP NHẬN XE");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();

        if (bienSo != null) {
            txtBienSo.setText(bienSo);
        }
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
        JPanel pCenter = new JPanel(new GridBagLayout());
        pCenter.setBackground(Color.WHITE);
        pCenter.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông Tin Phiếu Tiếp Nhận",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Hang 1
        gbc.gridy = 0; gbc.gridx = 0;
        pCenter.add(new JLabel("Mã Phiếu:"), gbc);
        gbc.gridx = 1;
        DAO.PhieuTiepNhan_DAO ptnDao = new DAO.PhieuTiepNhan_DAO();
        txtMaPhieu = new JTextField(ptnDao.getMaPhieuTiepNhanTuDong(), 15);
        txtMaPhieu.setEditable(false);
        txtMaPhieu.setBackground(new Color(236, 240, 241));
        pCenter.add(txtMaPhieu, gbc);

        gbc.gridx = 2;
        pCenter.add(new JLabel("Ngày Tiếp Nhận:"), gbc);
        gbc.gridx = 3;
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        txtNgayTiepNhan = new JTextField(now, 15);
        txtNgayTiepNhan.setEditable(false);
        txtNgayTiepNhan.setBackground(new Color(236, 240, 241));
        pCenter.add(txtNgayTiepNhan, gbc);
        // Hang 2
        gbc.gridy = 1; gbc.gridx = 0;
        pCenter.add(new JLabel("Biển Số Xe:"), gbc);
        gbc.gridx = 1;
        txtBienSo = new JTextField(15);
        pCenter.add(txtBienSo, gbc);

        gbc.gridx = 2;
        pCenter.add(new JLabel("Trạng Thái:"), gbc);
        gbc.gridx = 3;
        cboTrangThai = new JComboBox<>(new String[]{"Đang xử lý"});
        pCenter.add(cboTrangThai, gbc);
        // Hang 3
        gbc.gridy = 2; gbc.gridx = 0;
        pCenter.add(new JLabel("Tình Trạng Xe:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        txtTinhTrangXe = new JTextArea(3, 40);
        txtTinhTrangXe.setLineWrap(true);
        txtTinhTrangXe.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        pCenter.add(new JScrollPane(txtTinhTrangXe), gbc);
        // Hang 4
        gbc.gridy = 3; gbc.gridx = 0;
        gbc.gridwidth = 1;
        pCenter.add(new JLabel("Yêu Cầu KH:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        txtYeuCauKhach = new JTextArea(3, 40);
        txtYeuCauKhach.setLineWrap(true);
        txtYeuCauKhach.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        pCenter.add(new JScrollPane(txtYeuCauKhach), gbc);
        // Bao bọc pCenter để căn lề đẹp hơn
        JPanel pCenterWrapper = new JPanel(new BorderLayout());
        pCenterWrapper.setBorder(new EmptyBorder(0, 20, 0, 20));
        pCenterWrapper.add(pCenter, BorderLayout.NORTH);
        add(pCenterWrapper, BorderLayout.CENTER);

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
        String bienSo = txtBienSo.getText().trim();
        String tinhTrang = txtTinhTrangXe.getText().trim();
        String yeuCau = txtYeuCauKhach.getText().trim();
        String trangThai = cboTrangThai.getSelectedItem().toString();
        String maPhieu = txtMaPhieu.getText();

        if (bienSo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập Biển số xe!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.sql.Date ngayTiepNhan = new java.sql.Date(System.currentTimeMillis());

        PhieuTiepNhan ptn = new PhieuTiepNhan(
                maPhieu,
                ngayTiepNhan,
                tinhTrang,
                yeuCau,
                trangThai,
                bienSo
        );

        PhieuTiepNhan_DAO dao = new PhieuTiepNhan_DAO();
        try {
            boolean success = dao.themPhieuTiepNhan(ptn);
            if (success) {
                int opt = JOptionPane.showConfirmDialog(this,
                        "Lập phiếu tiếp nhận " + maPhieu + " thành công!\n"
                                + "Bạn có muốn mở 'Màn hình Danh sách xe chờ báo giá' ngay không?",
                        "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (opt == JOptionPane.YES_OPTION) {
                    ManHinhDanhSachXeChoBaoGia UI = new ManHinhDanhSachXeChoBaoGia();
                    UI.setVisible(true);
                    this.dispose();
                } else {
                    TrangChuHeThong Home = new TrangChuHeThong();
                    Home.setVisible(true);
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không thể lưu phiếu tiếp nhận!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi lưu vào CSDL (Kiểm tra lại Biển số xe có trong bảng XE chưa):\n" + ex.getMessage(),
                    "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyQuayLai() {
        this.dispose();
    }

    private void xuLyLamMoi() {
        txtBienSo.setText("");
        cboTrangThai.setSelectedIndex(0);
        txtTinhTrangXe.setText("");
        txtYeuCauKhach.setText("");
    }

    public static void main(String[] args) {
            ManHinhLapPhieuTiepNhan form = new ManHinhLapPhieuTiepNhan();
            form.setVisible(true);
    }
}