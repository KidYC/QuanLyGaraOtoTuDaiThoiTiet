package View.LeTan;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManHinhChiTietBanGiao extends JFrame implements ActionListener {

    private JLabel lblTitle;

    private JTextField txtMaPhieu;
    private JTextField txtTenKH;
    private JTextField txtBienSo;
    private JTextField txtNgaySuaXong;
    private JTextField txtNgayBanGiao;

    private JTable tblChiTietSuaChua;
    private DefaultTableModel modelSuaChua;
    private JLabel lblTongTien;

    private JCheckBox chkDaKiemTra;
    private JTextArea txtGhiChuBanGiao;

    private JButton btnQuayLai;
    private JButton btnXacNhanBanGiao;

    public ManHinhChiTietBanGiao(String maPhieu, String bienSo) {
        setTitle("CHI TIẾT BÀN GIAO XE");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();

        txtMaPhieu.setText(maPhieu != null ? maPhieu : "KH005");
        txtBienSo.setText(bienSo != null ? bienSo : "51H-234.56");

        loadDummyData();
    }

    public ManHinhChiTietBanGiao() {
        this("KH005", "51H-234.56");
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lblTitle = new JLabel("CHI TIẾT BÀN GIAO XE");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(15, 0, 5, 0));
        add(lblTitle, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 10, 20));

        //Khung Phiếu Khách Hàng
        JPanel pThongTin = new JPanel(new GridBagLayout());
        pThongTin.setBackground(Color.WHITE);
        pThongTin.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông tin Giao nhận",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 0; gbc.gridx = 0;
        pThongTin.add(new JLabel("Mã Phiếu:"), gbc);
        gbc.gridx = 1;
        txtMaPhieu = new JTextField(15);
        txtMaPhieu.setEditable(false);
        txtMaPhieu.setBackground(new Color(236, 240, 241));
        pThongTin.add(txtMaPhieu, gbc);

        gbc.gridx = 2;
        pThongTin.add(new JLabel("Tên Khách Hàng:"), gbc);
        gbc.gridx = 3;
        txtTenKH = new JTextField("Phạm Văn D", 15);
        txtTenKH.setEditable(false);
        pThongTin.add(txtTenKH, gbc);

        gbc.gridy = 1; gbc.gridx = 0;
        pThongTin.add(new JLabel("Biển Số Xe:"), gbc);
        gbc.gridx = 1;
        txtBienSo = new JTextField(15);
        txtBienSo.setEditable(false);
        pThongTin.add(txtBienSo, gbc);

        gbc.gridx = 2;
        pThongTin.add(new JLabel("Ngày Hoàn Thành:"), gbc);
        gbc.gridx = 3;
        txtNgaySuaXong = new JTextField("06/10/2026 14:30", 15);
        txtNgaySuaXong.setEditable(false);
        pThongTin.add(txtNgaySuaXong, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        pThongTin.add(new JLabel("Ngày Bàn Giao:"), gbc);
        gbc.gridx = 1;
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        txtNgayBanGiao = new JTextField(now, 15);
        txtNgayBanGiao.setEditable(false);
        txtNgayBanGiao.setBackground(new Color(255, 250, 205)); // Highlight ngày hiện tại
        pThongTin.add(txtNgayBanGiao, gbc);

        pCenter.add(pThongTin);
        pCenter.add(Box.createVerticalStrut(15));

        //Tạo Bảng
        JPanel pBang = new JPanel(new BorderLayout(5, 5));
        pBang.setBackground(Color.WHITE);
        pBang.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Chi tiết các hạng mục đã hoàn thành",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(5, 5, 5, 5)
        ));

        String[] cols = {"STT", "Loại", "Tên Hạng Mục", "Số Lượng", "Thành Tiền", "Bảo Hành"};
        modelSuaChua = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tblChiTietSuaChua = new JTable(modelSuaChua);
        tblChiTietSuaChua.setRowHeight(25);
        tblChiTietSuaChua.setFont(new Font("Arial", Font.PLAIN, 13));

        JTableHeader header = tblChiTietSuaChua.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tblChiTietSuaChua);
        scrollPane.setPreferredSize(new Dimension(800, 150));
        pBang.add(scrollPane, BorderLayout.CENTER);

        JPanel pTongTien = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pTongTien.setBackground(Color.WHITE);
        pTongTien.add(new JLabel("Tổng Thanh Toán: "));
        lblTongTien = new JLabel("0 VNĐ");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 16));
        lblTongTien.setForeground(new Color(192, 57, 43));
        pTongTien.add(lblTongTien);
        pBang.add(pTongTien, BorderLayout.SOUTH);

        pCenter.add(pBang);
        pCenter.add(Box.createVerticalStrut(15));

        JPanel pXacNhan = new JPanel(new BorderLayout(10, 5));
        pXacNhan.setBackground(new Color(245, 245, 245));

        chkDaKiemTra = new JCheckBox("Khách hàng đã kiểm tra xe, nhận đủ giấy tờ và đồ cá nhân.");
        chkDaKiemTra.setFont(new Font("Arial", Font.BOLD, 13));
        chkDaKiemTra.setBackground(new Color(245, 245, 245));
        pXacNhan.add(chkDaKiemTra, BorderLayout.NORTH);

        JPanel pGhiChu = new JPanel(new BorderLayout(5, 5));
        pGhiChu.setBackground(new Color(245, 245, 245));
        pGhiChu.add(new JLabel("Ghi chú bàn giao:"), BorderLayout.NORTH);
        txtGhiChuBanGiao = new JTextArea(3, 20);
        txtGhiChuBanGiao.setLineWrap(true);
        txtGhiChuBanGiao.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        pGhiChu.add(new JScrollPane(txtGhiChuBanGiao), BorderLayout.CENTER);

        pXacNhan.add(pGhiChu, BorderLayout.CENTER);

        pCenter.add(pXacNhan);
        add(pCenter, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnQuayLai = new JButton("Quay Lại");
        btnQuayLai.setPreferredSize(new Dimension(110, 38));
        btnQuayLai.setBackground(new Color(149, 165, 166));
        btnQuayLai.setForeground(Color.WHITE);
        btnQuayLai.setFont(new Font("Arial", Font.BOLD, 13));
        btnQuayLai.setFocusPainted(false);

        btnXacNhanBanGiao = new JButton("Xác Nhận Bàn Giao & In Phiếu");
        btnXacNhanBanGiao.setPreferredSize(new Dimension(250, 38));
        btnXacNhanBanGiao.setBackground(new Color(46, 204, 113));
        btnXacNhanBanGiao.setForeground(Color.WHITE);
        btnXacNhanBanGiao.setFont(new Font("Arial", Font.BOLD, 13));
        btnXacNhanBanGiao.setFocusPainted(false);

        pSouth.add(btnQuayLai);
        pSouth.add(btnXacNhanBanGiao);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btnQuayLai.addActionListener(this);
        btnXacNhanBanGiao.addActionListener(this);
    }

    private void loadDummyData() {
        modelSuaChua.addRow(new Object[]{1, "Dịch vụ", "Thay nhớt & Lọc nhớt", 1, "780,000", "Không"});
        modelSuaChua.addRow(new Object[]{2, "Phụ tùng", "Bố thắng trước", 1, "1,200,000", "6 tháng"});
        modelSuaChua.addRow(new Object[]{3, "Dịch vụ", "Vệ sinh buồng đốt", 1, "450,000", "Không"});

        lblTongTien.setText("2,430,000 VNĐ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnQuayLai) {
            xuLyQuayLai();
        } else if (source == btnXacNhanBanGiao) {
            xuLyXacNhanBanGiao();
        }
    }

    private void xuLyQuayLai() {
        ManHinhDanhSachXeDaHoanThanhSuaChua UI = new ManHinhDanhSachXeDaHoanThanhSuaChua();
        UI.setVisible(true);
        this.dispose();
    }

    private void xuLyXacNhanBanGiao() {
        if (!chkDaKiemTra.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng tích xác nhận khách hàng đã kiểm tra xe và nhận đủ đồ cá nhân trước khi bàn giao!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Xác nhận bàn giao xe " + txtBienSo.getText() + " thành công!\n"
                        + "Hệ thống đang tự động in Biên bản bàn giao...",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);

         FormQuanLyBaoHanh formBaoHanh = new FormQuanLyBaoHanh();
         formBaoHanh.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        ManHinhChiTietBanGiao form = new ManHinhChiTietBanGiao();
        form.setVisible(true);
    }
}