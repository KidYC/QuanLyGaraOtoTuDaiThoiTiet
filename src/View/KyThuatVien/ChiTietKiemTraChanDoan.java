package View.KyThuatVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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
        setSize(1150, 720);
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
        headerPanel.setPreferredSize(new Dimension(1150, 75));

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
        rightPanel = new JPanel(new BorderLayout(0, 15));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

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

        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        inputPanel.setBackground(Color.WHITE);

        loiPhatHienTa = taoTextArea();
        nguyenNhanTa = taoTextArea();
        deXuatTa = taoTextArea();

        inputPanel.add(taoBoxTextArea("Lỗi phát hiện:", loiPhatHienTa));
        inputPanel.add(taoBoxTextArea("Nguyên nhân:", nguyenNhanTa));
        inputPanel.add(taoBoxTextArea("Đề xuất phương án sửa chữa:", deXuatTa));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setBackground(Color.WHITE);

        lamMoiBtn = taoButton("Nhập lại", maumenu);
        xacNhanBtn = taoButton("Xác nhận hoàn thành", btnchinh);

        xacNhanBtn.setPreferredSize(new Dimension(200, 40));

        actionPanel.add(lamMoiBtn);
        actionPanel.add(xacNhanBtn);

        JPanel centerContainer = new JPanel(new BorderLayout(0, 15));
        centerContainer.setBackground(Color.WHITE);

        centerContainer.add(infoPanel, BorderLayout.NORTH);
        centerContainer.add(inputPanel, BorderLayout.CENTER);

        rightPanel.add(centerContainer, BorderLayout.CENTER);
        rightPanel.add(actionPanel, BorderLayout.SOUTH);

        xacNhanBtn.addActionListener(e -> xacNhanKiemTra());
        lamMoiBtn.addActionListener(e -> xoaRongForm());
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

        JOptionPane.showMessageDialog(
                this,
                "Lưu kết quả kiểm tra & chẩn đoán thành công!\nPhiếu đã sẵn sàng để Lập phiếu sửa chữa.",
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
        tableXe.clearSelection();
    }

    private JPanel taoBoxTextArea(String labelText, JTextArea textArea) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
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
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
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
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        return textArea;
    }

    private JButton taoButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(chuchinh);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }

    private void quayLaiTrangChu() {
        dispose();
        new TrangChuKyThuatVien();
    }

    public static void main(String[] args) {
        new ChiTietKiemTraChanDoan();
    }
}