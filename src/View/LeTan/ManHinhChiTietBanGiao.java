package View.LeTan;

import DAO.PhieuSuaChua_DAO;
import Entity.ChiTietBanGiao_DTO;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.print.PrinterException;

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
    private JButton btnInPhieu;

    public ManHinhChiTietBanGiao(String maPhieu, String bienSo) {
        setTitle("CHI TIẾT BÀN GIAO XE");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();

        txtMaPhieu.setText(maPhieu != null ? maPhieu : "KH005");
        txtBienSo.setText(bienSo != null ? bienSo : "51H-234.56");

        loadDataFromDB(maPhieu);
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

        btnXacNhanBanGiao = new JButton("Xác Nhận Bàn Giao");
        btnXacNhanBanGiao.setPreferredSize(new Dimension(250, 38));
        btnXacNhanBanGiao.setBackground(new Color(46, 204, 113));
        btnXacNhanBanGiao.setForeground(Color.WHITE);
        btnXacNhanBanGiao.setFont(new Font("Arial", Font.BOLD, 13));
        btnXacNhanBanGiao.setFocusPainted(false);

        btnInPhieu = new JButton("In Phiếu");
        btnInPhieu.setPreferredSize(new Dimension(130, 38));
        btnInPhieu.setBackground(new Color(52, 152, 219));
        btnInPhieu.setForeground(Color.WHITE);
        btnInPhieu.setFont(new Font("Arial", Font.BOLD, 13));
        btnInPhieu.setFocusPainted(false);

        pSouth.add(btnQuayLai);
        pSouth.add(btnInPhieu);
        pSouth.add(btnXacNhanBanGiao);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btnQuayLai.addActionListener(this);
        btnInPhieu.addActionListener(this);
        btnXacNhanBanGiao.addActionListener(this);
    }

    private void loadDataFromDB(String maPhieu) {
        modelSuaChua.setRowCount(0);
        PhieuSuaChua_DAO dao = new PhieuSuaChua_DAO();
        DecimalFormat df = new DecimalFormat("#,### VNĐ");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {

            try (ResultSet rs = dao.getThongTinBanGiao(maPhieu)) {
                if (rs.next()) {
                    txtTenKH.setText(rs.getString("hoTen"));
                    Timestamp ngayHoanThanh = rs.getTimestamp("ngayDuKienHoanThanh");
                    if (ngayHoanThanh != null) {
                        txtNgaySuaXong.setText(sdf.format(ngayHoanThanh));
                    }
                }
            }


            List<ChiTietBanGiao_DTO> list = dao.getChiTietBanGiaoByMaPhieu(maPhieu);
            int stt = 1;
            double tongTien = 0;

            for (ChiTietBanGiao_DTO item : list) {
                tongTien += item.getThanhTien();
                modelSuaChua.addRow(new Object[]{
                        stt++,
                        item.getLoai(),
                        item.getTenHangMuc(),
                        item.getSoLuong(),
                        df.format(item.getThanhTien()),
                        item.getBaoHanh()
                });
            }

            lblTongTien.setText(df.format(tongTien));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu bàn giao từ CSDL: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnQuayLai) {
            xuLyQuayLai();
        } else if(source == btnInPhieu){
            xuLyInPhieu();
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

        int opt = JOptionPane.showConfirmDialog(this,
                "Xác nhận hoàn tất bàn giao xe cho biển số: " + txtBienSo.getText() + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opt == JOptionPane.YES_OPTION) {
            try {
                PhieuSuaChua_DAO dao = new PhieuSuaChua_DAO();

                // Gọi hàm cập nhật trạng thái thành "Hoàn thành"
                boolean success = dao.capNhatTrangThaiHoanThanh(txtMaPhieu.getText().trim());

                if (success) {
                    JOptionPane.showMessageDialog(this,
                            "Xác nhận bàn giao xe thành công!\nTrạng thái phiếu đã chuyển sang 'Hoàn thành'.",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                    // Chuyển sang màn hình Quản lý bảo hành
                    FormQuanLyBaoHanh formBaoHanh = new FormQuanLyBaoHanh(
                            txtMaPhieu.getText().trim(),
                            txtBienSo.getText().trim(),
                            txtTenKH.getText().trim()
                    );
                    formBaoHanh.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể cập nhật trạng thái bàn giao vào CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi CSDL: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void xuLyInPhieu() {
        StringBuilder sb = new StringBuilder();
        sb.append("            GARA Ô TÔ TỰ ĐẠI THỜI TIẾT\n");
        sb.append("               PHIẾU BÀN GIAO XE\n");
        sb.append("==========================================================\n");
        sb.append("Mã phiếu       : ").append(txtMaPhieu.getText()).append("\n");
        sb.append("Khách hàng     : ").append(txtTenKH.getText()).append("\n");
        sb.append("Biển số xe     : ").append(txtBienSo.getText()).append("\n");
        sb.append("Ngày hoàn thành: ").append(txtNgaySuaXong.getText()).append("\n");
        sb.append("Ngày bàn giao  : ").append(txtNgayBanGiao.getText()).append("\n");
        sb.append("----------------------------------------------------------\n");
        sb.append(String.format("%-4s %-9s %-24s %-4s %-14s %-9s%n",
                "STT", "Loại", "Hạng mục", "SL", "Thành tiền", "Bảo hành"));
        sb.append("----------------------------------------------------------\n");

        for (int i = 0; i < modelSuaChua.getRowCount(); i++) {
            sb.append(String.format("%-4s %-9s %-24s %-4s %-14s %-9s%n",
                    modelSuaChua.getValueAt(i, 0),
                    modelSuaChua.getValueAt(i, 1),
                    modelSuaChua.getValueAt(i, 2),
                    modelSuaChua.getValueAt(i, 3),
                    modelSuaChua.getValueAt(i, 4),
                    modelSuaChua.getValueAt(i, 5)));
        }

        sb.append("----------------------------------------------------------\n");
        sb.append("TỔNG THANH TOÁN: ").append(lblTongTien.getText()).append("\n\n");

        String ghiChu = txtGhiChuBanGiao.getText().trim();
        if (!ghiChu.isEmpty()) {
            sb.append("Ghi chú: ").append(ghiChu).append("\n\n");
        }
        sb.append("   Khách hàng ký tên              Nhân viên bàn giao\n");
        sb.append("\n\n\n");

        JTextArea txtPhieu = new JTextArea(sb.toString());
        txtPhieu.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtPhieu.setEditable(false);
        txtPhieu.setMargin(new Insets(10, 10, 10, 10));

        JDialog dlg = new JDialog(this, "Xem trước phiếu bàn giao", true);
        dlg.setLayout(new BorderLayout(10, 10));
        dlg.add(new JScrollPane(txtPhieu), BorderLayout.CENTER);

        JPanel pBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton btnDong = new JButton("Đóng");
        btnDong.setPreferredSize(new Dimension(100, 32));
        btnDong.setBackground(new Color(149, 165, 166));
        btnDong.setForeground(Color.WHITE);
        btnDong.setFocusPainted(false);
        btnDong.addActionListener(ev -> dlg.dispose());

        JButton btnIn = new JButton("In");
        btnIn.setPreferredSize(new Dimension(100, 32));
        btnIn.setBackground(new Color(52, 152, 219));
        btnIn.setForeground(Color.WHITE);
        btnIn.setFocusPainted(false);
        btnIn.addActionListener(ev -> {
            try {
                if (txtPhieu.print()) {
                    dlg.dispose();
                }
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(dlg, "Lỗi khi in: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        pBtn.add(btnDong);
        pBtn.add(btnIn);
        dlg.add(pBtn, BorderLayout.SOUTH);

        dlg.setSize(620, 560);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    public static void main(String[] args) {
        ManHinhChiTietBanGiao form = new ManHinhChiTietBanGiao();
        form.setVisible(true);
    }
}