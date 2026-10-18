package View.KyThuatVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class ChiTietKiemTraChanDoan extends JFrame {
    private JPanel pnlHeaderPanel;
    private JSplitPane splitPane;
    private JPanel pnlLeftPanel;
    private JTextField txtSearch;
    private JButton btnTim;
    private JTable tblXe;
    private DefaultTableModel tableModelXe;
    private JPanel pnlRightPanel;
    private JTextField txtBienSo;
    private JTextField txtKhachHang;
    private JTextField txtYeuCau;
    private JTextArea txaLoiPhatHien;
    private JTextArea txaNguyenNhan;
    private JTextArea txaDeXuat;

    // Thành phần quản lý và tải hình ảnh thực tế
    private JButton btnChonAnh;
    private JLabel lblPreviewAnh;
    private String duongDanAnh = "";

    private JButton btnXacNhan;
    private JButton btnLamMoi;
    private JButton btnQuayLai;

    private Color maunen = new Color(244, 246, 248);
    private Color maumenu = new Color(226, 232, 240);
    private Color btnchinh = new Color(186, 230, 253);
    private Color btnphu = new Color(219, 234, 254);
    private Color chuchinh = new Color(17, 24, 39);
    private Color maucotbang = new Color(186, 230, 253);
    private Color mauphu = new Color(107, 114, 128);

    public ChiTietKiemTraChanDoan() {
        setTitle("Kiểm tra & Chẩn đoán xe - Kỹ thuật viên");
        setSize(1200, 780);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(maunen);
        setLayout(new BorderLayout(0, 10));
        taoHeader();
        taoContent();
        setVisible(true);
    }

    private void taoHeader() {
        pnlHeaderPanel = new JPanel(new BorderLayout());
        pnlHeaderPanel.setBackground(Color.WHITE);
        pnlHeaderPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        pnlHeaderPanel.setPreferredSize(new Dimension(1200, 75));

        JLabel lblTitle = new JLabel("KIỂM TRA & CHẨN ĐOÁN TÌNH TRẠNG XE");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(chuchinh);

        btnQuayLai = taoButton("Quay lại", btnphu);
        btnQuayLai.setPreferredSize(new Dimension(110, 40));
        btnQuayLai.addActionListener(e -> quayLaiTrangChu());

        pnlHeaderPanel.add(lblTitle, BorderLayout.WEST);
        pnlHeaderPanel.add(btnQuayLai, BorderLayout.EAST);

        add(pnlHeaderPanel, BorderLayout.NORTH);
    }

    private void taoContent() {
        taoDanhSachXePanel();
        taoFormKiemTraPanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLeftPanel, pnlRightPanel);
        splitPane.setDividerLocation(480);
        splitPane.setDividerSize(6);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        splitPane.setBackground(maunen);

        add(splitPane, BorderLayout.CENTER);
    }

    private void taoDanhSachXePanel() {
        pnlLeftPanel = new JPanel(new BorderLayout(0, 12));
        pnlLeftPanel.setBackground(maunen);

        JPanel pnlSearchPanel = new JPanel(new BorderLayout(10, 0));
        pnlSearchPanel.setBackground(maunen);

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        txtSearch.setPreferredSize(new Dimension(260, 40));

        btnTim = taoButton("Tìm xe", btnchinh);

        pnlSearchPanel.add(txtSearch, BorderLayout.CENTER);
        pnlSearchPanel.add(btnTim, BorderLayout.EAST);

        String[] columns = {"Biển số", "Tên xe", "Khách hàng", "Trạng thái"};
        Object[][] data = {
                {"51A-123.45", "Toyota Camry 2.5Q", "Nguyễn Văn An", "Chờ kiểm tra"},
                {"59A-456.78", "Honda CR-V 2021", "Trần Thị Bình", "Chờ kiểm tra"},
                {"60A-111.11", "Mazda 3 Luxury", "Lê Hoàng Nam", "Đang kiểm tra"},
                {"61B-999.88", "Ford Ranger Wildtrak", "Phạm Quốc Hùng", "Chờ kiểm tra"}
        };

        tableModelXe = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblXe = new JTable(tableModelXe);
        tblXe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblXe.setRowHeight(40);
        tblXe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblXe.setSelectionBackground(btnphu);
        tblXe.setSelectionForeground(chuchinh);
        tblXe.setShowVerticalLines(false);
        tblXe.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblXe.getTableHeader().setForeground(chuchinh);
        tblXe.getTableHeader().setBackground(btnchinh);
        tblXe.getTableHeader().setPreferredSize(new Dimension(0, 42));

        JScrollPane scrScrollPane = new JScrollPane(tblXe);
        scrScrollPane.setBorder(BorderFactory.createLineBorder(maucotbang));
        scrScrollPane.getViewport().setBackground(Color.WHITE);

        pnlLeftPanel.add(pnlSearchPanel, BorderLayout.NORTH);
        pnlLeftPanel.add(scrScrollPane, BorderLayout.CENTER);

        tblXe.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hienThiThongTinXeDuocChon();
            }
        });
    }

    private void taoFormKiemTraPanel() {
        pnlRightPanel = new JPanel(new BorderLayout(0, 12));
        pnlRightPanel.setBackground(Color.WHITE);
        pnlRightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        // 1. Thông tin xe & Yêu cầu của KH
        JPanel pnlInfoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlInfoPanel.setBackground(Color.WHITE);
        pnlInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(maucotbang),
                "Thông tin xe & Yêu cầu của khách hàng",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                chuchinh
        ));

        txtBienSo = taoTextFieldReadOnly();
        txtKhachHang = taoTextFieldReadOnly();
        txtYeuCau = taoTextFieldReadOnly();

        pnlInfoPanel.add(taoLabel("Biển số xe:"));
        pnlInfoPanel.add(txtBienSo);
        pnlInfoPanel.add(taoLabel("Khách hàng:"));
        pnlInfoPanel.add(txtKhachHang);
        pnlInfoPanel.add(taoLabel("Yêu cầu KH:"));
        pnlInfoPanel.add(txtYeuCau);

        // 2. Các ô nhập liệu nội dung chẩn đoán
        JPanel pnlTextInputContainer = new JPanel(new GridLayout(3, 1, 0, 8));
        pnlTextInputContainer.setBackground(Color.WHITE);

        txaLoiPhatHien = taoTextArea();
        txaNguyenNhan = taoTextArea();
        txaDeXuat = taoTextArea();

        pnlTextInputContainer.add(taoBoxTextArea("Lỗi phát hiện:", txaLoiPhatHien));
        pnlTextInputContainer.add(taoBoxTextArea("Nguyên nhân:", txaNguyenNhan));
        pnlTextInputContainer.add(taoBoxTextArea("Đề xuất phương án sửa chữa:", txaDeXuat));

        // 3. Khối tải & hiển thị hình ảnh linh kiện hư hỏng thực tế
        JPanel pnlImageSectionPanel = new JPanel(new BorderLayout(10, 5));
        pnlImageSectionPanel.setBackground(Color.WHITE);
        pnlImageSectionPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(maucotbang),
                "Hình ảnh thực tế hư hỏng (KTV đính kèm)",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                chuchinh
        ));

        JPanel pnlImgBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        pnlImgBoxPanel.setBackground(Color.WHITE);

        lblPreviewAnh = new JLabel("Chưa có ảnh hư hỏng", SwingConstants.CENTER);
        lblPreviewAnh.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblPreviewAnh.setForeground(mauphu);
        lblPreviewAnh.setPreferredSize(new Dimension(150, 95));
        lblPreviewAnh.setBorder(BorderFactory.createLineBorder(maucotbang, 1));

        btnChonAnh = taoButton("Tải/Chọn ảnh lỗi", btnphu);
        btnChonAnh.setPreferredSize(new Dimension(170, 38));

        pnlImgBoxPanel.add(lblPreviewAnh);
        pnlImgBoxPanel.add(btnChonAnh);
        pnlImageSectionPanel.add(pnlImgBoxPanel, BorderLayout.CENTER);

        // 4. Thanh nút bấm xác nhận / làm mới
        JPanel pnlActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlActionPanel.setBackground(Color.WHITE);

        btnLamMoi = taoButton("Nhập lại", maumenu);
        btnXacNhan = taoButton("Xác nhận hoàn thành", btnchinh);
        btnXacNhan.setPreferredSize(new Dimension(200, 40));

        pnlActionPanel.add(btnLamMoi);
        pnlActionPanel.add(btnXacNhan);

        // Ghép các khối vào container trung tâm
        JPanel pnlCenterContainer = new JPanel(new BorderLayout(0, 10));
        pnlCenterContainer.setBackground(Color.WHITE);
        pnlCenterContainer.add(pnlInfoPanel, BorderLayout.NORTH);
        pnlCenterContainer.add(pnlTextInputContainer, BorderLayout.CENTER);
        pnlCenterContainer.add(pnlImageSectionPanel, BorderLayout.SOUTH);

        pnlRightPanel.add(pnlCenterContainer, BorderLayout.CENTER);
        pnlRightPanel.add(pnlActionPanel, BorderLayout.SOUTH);

        // Bắt sự kiện
        btnChonAnh.addActionListener(e -> chonAnhHuHong());
        btnXacNhan.addActionListener(e -> xacNhanKiemTra());
        btnLamMoi.addActionListener(e -> xoaRongForm());
    }

    private void chonAnhHuHong() {
        JFileChooser fchFileChooser = new JFileChooser();
        fchFileChooser.setDialogTitle("Chọn hình ảnh linh kiện / bộ phận hư hỏng");
        fchFileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh (*.jpg, *.png, *.jpeg)", "jpg", "png", "jpeg"));

        int result = fchFileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fchFileChooser.getSelectedFile();
            duongDanAnh = selectedFile.getAbsolutePath();

            // Co giãn hình ảnh vừa chọn để hiển thị vừa vặn trong khung Preview
            ImageIcon icon = new ImageIcon(new ImageIcon(duongDanAnh).getImage().getScaledInstance(150, 95, Image.SCALE_SMOOTH));
            lblPreviewAnh.setIcon(icon);
            lblPreviewAnh.setText(""); // Xóa dòng chữ thông báo tạm
        }
    }

    private void hienThiThongTinXeDuocChon() {
        int row = tblXe.getSelectedRow();

        if (row != -1) {
            txtBienSo.setText(tableModelXe.getValueAt(row, 0).toString());
            txtKhachHang.setText(tableModelXe.getValueAt(row, 2).toString());

            String bienSo = tableModelXe.getValueAt(row, 0).toString();

            if (bienSo.equals("51A-123.45")) {
                txtYeuCau.setText("Động cơ bị rung giật khi tăng tốc, có tiếng kêu khoang máy.");
            } else if (bienSo.equals("59A-456.78")) {
                txtYeuCau.setText("Phanh không ăn, bàn đạp phanh bị sâu.");
            } else {
                txtYeuCau.setText("Bảo dưỡng định kỳ 40.000 km, kiểm tra hệ thống điều hòa.");
            }
        }
    }

    private void xacNhanKiemTra() {
        int row = tblXe.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn xe cần kiểm tra từ danh sách bên trái!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (txaLoiPhatHien.getText().trim().isEmpty() || txaDeXuat.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ thông tin Lỗi phát hiện và Đề xuất phương án!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        tableModelXe.setValueAt("Đã kiểm tra", row, 3);

        String thongBaoAnh = duongDanAnh.isEmpty() ? "\n(Chưa đính kèm ảnh lỗi thực tế)" : "\n(Đã đính kèm hình ảnh bằng chứng hỏng hóc)";

        JOptionPane.showMessageDialog(
                this,
                "Lưu kết quả kiểm tra & chẩn đoán thành công!" + thongBaoAnh + "\nPhiếu đã sẵn sàng để Lập phiếu sửa chữa.",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE
        );

        xoaRongForm();
    }

    private void xoaRongForm() {
        txtBienSo.setText("");
        txtKhachHang.setText("");
        txtYeuCau.setText("");
        txaLoiPhatHien.setText("");
        txaNguyenNhan.setText("");
        txaDeXuat.setText("");

        duongDanAnh = "";
        lblPreviewAnh.setIcon(null);
        lblPreviewAnh.setText("Chưa có ảnh hư hỏng");

        tblXe.clearSelection();
    }

    private JPanel taoBoxTextArea(String labelText, JTextArea textArea) {
        JPanel pnlPanel = new JPanel(new BorderLayout(0, 4));
        pnlPanel.setBackground(Color.WHITE);

        JLabel lblLabel = taoLabel(labelText);

        JScrollPane scrScrollPane = new JScrollPane(textArea);
        scrScrollPane.setBorder(BorderFactory.createLineBorder(maucotbang));

        pnlPanel.add(lblLabel, BorderLayout.NORTH);
        pnlPanel.add(scrScrollPane, BorderLayout.CENTER);

        return pnlPanel;
    }

    private JLabel taoLabel(String text) {
        JLabel lblLabel = new JLabel(text);
        lblLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblLabel.setForeground(chuchinh);
        return lblLabel;
    }

    private JTextField taoTextFieldReadOnly() {
        JTextField txtField = new JTextField();
        txtField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtField.setEditable(false);
        txtField.setBackground(maunen);
        txtField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(0, 8, 0, 8)
        ));
        return txtField;
    }

    private JTextArea taoTextArea() {
        JTextArea txaArea = new JTextArea();
        txaArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txaArea.setLineWrap(true);
        txaArea.setWrapStyleWord(true);
        txaArea.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        return txaArea;
    }

    private JButton taoButton(String text, Color color) {
        JButton btnButton = new JButton(text);
        btnButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnButton.setForeground(chuchinh);
        btnButton.setBackground(color);
        btnButton.setFocusPainted(false);
        btnButton.setBorderPainted(false);
        btnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnButton.setPreferredSize(new Dimension(120, 38));
        return btnButton;
    }

    private void quayLaiTrangChu() {
        dispose();
    }

    public static void main(String[] args) {
        new ChiTietKiemTraChanDoan();
    }
}