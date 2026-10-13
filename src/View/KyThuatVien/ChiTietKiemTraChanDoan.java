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
    private JPanel headerPanel;
    private JSplitPane splitPane;
    private JPanel leftPanel;
    private JTextField searchTxt;
    private JButton timBtn;
    private JTable tableXe;
    private DefaultTableModel tableModelXe;
    private JPanel rightPanel;
    private JTextField bienSoTxt;
    private JTextField khachHangTxt;
    private JTextField yeuCauTxt;
    private JTextArea loiPhatHienTa;
    private JTextArea nguyenNhanTa;
    private JTextArea deXuatTa;

    // Thành phần quản lý và tải hình ảnh thực tế
    private JButton btnChonAnh;
    private JLabel lbPreviewAnh;
    private String duongDanAnh = "";

    private JButton xacNhanBtn;
    private JButton lamMoiBtn;
    private JButton quayLaiBtn;

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
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        headerPanel.setPreferredSize(new Dimension(1200, 75));

        JLabel titleLb = new JLabel("KIỂM TRA & CHẨN ĐOÁN TÌNH TRẠNG XE");
        titleLb.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLb.setForeground(chuchinh);

        quayLaiBtn = taoButton("Quay lại", btnphu);
        quayLaiBtn.setPreferredSize(new Dimension(110, 40));
        quayLaiBtn.addActionListener(e -> quayLaiTrangChu());

        headerPanel.add(titleLb, BorderLayout.WEST);
        headerPanel.add(quayLaiBtn, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void taoContent() {
        taoDanhSachXePanel();
        taoFormKiemTraPanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(480);
        splitPane.setDividerSize(6);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        splitPane.setBackground(maunen);

        add(splitPane, BorderLayout.CENTER);
    }

    private void taoDanhSachXePanel() {
        leftPanel = new JPanel(new BorderLayout(0, 12));
        leftPanel.setBackground(maunen);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(maunen);

        searchTxt = new JTextField();
        searchTxt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchTxt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        searchTxt.setPreferredSize(new Dimension(260, 40));

        timBtn = taoButton("Tìm xe", btnchinh);

        searchPanel.add(searchTxt, BorderLayout.CENTER);
        searchPanel.add(timBtn, BorderLayout.EAST);

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

        tableXe = new JTable(tableModelXe);
        tableXe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableXe.setRowHeight(40);
        tableXe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableXe.setSelectionBackground(btnphu);
        tableXe.setSelectionForeground(chuchinh);
        tableXe.setShowVerticalLines(false);
        tableXe.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableXe.getTableHeader().setForeground(chuchinh);
        tableXe.getTableHeader().setBackground(btnchinh);
        tableXe.getTableHeader().setPreferredSize(new Dimension(0, 42));

        JScrollPane scrollPane = new JScrollPane(tableXe);
        scrollPane.setBorder(BorderFactory.createLineBorder(maucotbang));
        scrollPane.getViewport().setBackground(Color.WHITE);

        leftPanel.add(searchPanel, BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        tableXe.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hienThiThongTinXeDuocChon();
            }
        });
    }

    private void taoFormKiemTraPanel() {
        rightPanel = new JPanel(new BorderLayout(0, 12));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        // 1. Thông tin xe & Yêu cầu của KH
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(maucotbang),
                "Thông tin xe & Yêu cầu của khách hàng",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                chuchinh
        ));

        bienSoTxt = taoTextFieldReadOnly();
        khachHangTxt = taoTextFieldReadOnly();
        yeuCauTxt = taoTextFieldReadOnly();

        infoPanel.add(taoLabel("Biển số xe:"));
        infoPanel.add(bienSoTxt);
        infoPanel.add(taoLabel("Khách hàng:"));
        infoPanel.add(khachHangTxt);
        infoPanel.add(taoLabel("Yêu cầu KH:"));
        infoPanel.add(yeuCauTxt);

        // 2. Các ô nhập liệu nội dung chẩn đoán
        JPanel textInputContainer = new JPanel(new GridLayout(3, 1, 0, 8));
        textInputContainer.setBackground(Color.WHITE);

        loiPhatHienTa = taoTextArea();
        nguyenNhanTa = taoTextArea();
        deXuatTa = taoTextArea();

        textInputContainer.add(taoBoxTextArea("Lỗi phát hiện:", loiPhatHienTa));
        textInputContainer.add(taoBoxTextArea("Nguyên nhân:", nguyenNhanTa));
        textInputContainer.add(taoBoxTextArea("Đề xuất phương án sửa chữa:", deXuatTa));

        // 3. Khối tải & hiển thị hình ảnh linh kiện hư hỏng thực tế
        JPanel imageSectionPanel = new JPanel(new BorderLayout(10, 5));
        imageSectionPanel.setBackground(Color.WHITE);
        imageSectionPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(maucotbang),
                "Hình ảnh thực tế hư hỏng (KTV đính kèm)",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                chuchinh
        ));

        JPanel imgBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        imgBoxPanel.setBackground(Color.WHITE);

        lbPreviewAnh = new JLabel("Chưa có ảnh hư hỏng", SwingConstants.CENTER);
        lbPreviewAnh.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lbPreviewAnh.setForeground(mauphu);
        lbPreviewAnh.setPreferredSize(new Dimension(150, 95));
        lbPreviewAnh.setBorder(BorderFactory.createLineBorder(maucotbang, 1));

        btnChonAnh = taoButton("Tải/Chọn ảnh lỗi", btnphu);
        btnChonAnh.setPreferredSize(new Dimension(170, 38));

        imgBoxPanel.add(lbPreviewAnh);
        imgBoxPanel.add(btnChonAnh);
        imageSectionPanel.add(imgBoxPanel, BorderLayout.CENTER);

        // 4. Thanh nút bấm xác nhận / làm mới
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setBackground(Color.WHITE);

        lamMoiBtn = taoButton("Nhập lại", maumenu);
        xacNhanBtn = taoButton("Xác nhận hoàn thành", btnchinh);
        xacNhanBtn.setPreferredSize(new Dimension(200, 40));

        actionPanel.add(lamMoiBtn);
        actionPanel.add(xacNhanBtn);

        // Ghép các khối vào container trung tâm
        JPanel centerContainer = new JPanel(new BorderLayout(0, 10));
        centerContainer.setBackground(Color.WHITE);
        centerContainer.add(infoPanel, BorderLayout.NORTH);
        centerContainer.add(textInputContainer, BorderLayout.CENTER);
        centerContainer.add(imageSectionPanel, BorderLayout.SOUTH);

        rightPanel.add(centerContainer, BorderLayout.CENTER);
        rightPanel.add(actionPanel, BorderLayout.SOUTH);

        // Bắt sự kiện
        btnChonAnh.addActionListener(e -> chonAnhHuHong());
        xacNhanBtn.addActionListener(e -> xacNhanKiemTra());
        lamMoiBtn.addActionListener(e -> xoaRongForm());
    }

    private void chonAnhHuHong() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn hình ảnh linh kiện / bộ phận hư hỏng");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh (*.jpg, *.png, *.jpeg)", "jpg", "png", "jpeg"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            duongDanAnh = selectedFile.getAbsolutePath();

            // Co giãn hình ảnh vừa chọn để hiển thị vừa vặn trong khung Preview
            ImageIcon icon = new ImageIcon(new ImageIcon(duongDanAnh).getImage().getScaledInstance(150, 95, Image.SCALE_SMOOTH));
            lbPreviewAnh.setIcon(icon);
            lbPreviewAnh.setText(""); // Xóa dòng chữ thông báo tạm
        }
    }

    private void hienThiThongTinXeDuocChon() {
        int row = tableXe.getSelectedRow();

        if (row != -1) {
            bienSoTxt.setText(tableModelXe.getValueAt(row, 0).toString());
            khachHangTxt.setText(tableModelXe.getValueAt(row, 2).toString());

            String bienSo = tableModelXe.getValueAt(row, 0).toString();

            if (bienSo.equals("51A-123.45")) {
                yeuCauTxt.setText("Động cơ bị rung giật khi tăng tốc, có tiếng kêu khoang máy.");
            } else if (bienSo.equals("59A-456.78")) {
                yeuCauTxt.setText("Phanh không ăn, bàn đạp phanh bị sâu.");
            } else {
                yeuCauTxt.setText("Bảo dưỡng định kỳ 40.000 km, kiểm tra hệ thống điều hòa.");
            }
        }
    }

    private void xacNhanKiemTra() {
        int row = tableXe.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn xe cần kiểm tra từ danh sách bên trái!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (loiPhatHienTa.getText().trim().isEmpty() || deXuatTa.getText().trim().isEmpty()) {
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
        bienSoTxt.setText("");
        khachHangTxt.setText("");
        yeuCauTxt.setText("");
        loiPhatHienTa.setText("");
        nguyenNhanTa.setText("");
        deXuatTa.setText("");

        duongDanAnh = "";
        lbPreviewAnh.setIcon(null);
        lbPreviewAnh.setText("Chưa có ảnh hư hỏng");

        tableXe.clearSelection();
    }

    private JPanel taoBoxTextArea(String labelText, JTextArea textArea) {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setBackground(Color.WHITE);

        JLabel label = taoLabel(labelText);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(maucotbang));

        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JLabel taoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(chuchinh);
        return label;
    }

    private JTextField taoTextFieldReadOnly() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setEditable(false);
        textField.setBackground(maunen);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(0, 8, 0, 8)
        ));
        return textField;
    }

    private JTextArea taoTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        return textArea;
    }

    private JButton taoButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(chuchinh);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 38));
        return button;
    }

    private void quayLaiTrangChu() {
        dispose();
    }

    public static void main(String[] args) {
        new ChiTietKiemTraChanDoan();
    }
}