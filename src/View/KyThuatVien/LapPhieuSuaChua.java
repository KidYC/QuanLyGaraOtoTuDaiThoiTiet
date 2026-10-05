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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class LapPhieuSuaChua extends JFrame {

    private JPanel headerPanel;
    private JPanel mainContentPanel;
    private JPanel topInfoPanel;

    // Component Thông tin phiếu tiếp nhận
    private JComboBox phieuTiepNhanCb;
    private JTextField bienSoTxt;
    private JTextField khachHangTxt;
    private JTextField ngayTiepNhanTxt;

    // Bảng 1: Hạng mục Công việc (UC015)
    private JTable tableCongViec;
    private DefaultTableModel tableModelCongViec;
    private JButton themCongViecBtn;
    private JButton xoaCongViecBtn;

    // Bảng 2: Danh sách Phụ tùng sử dụng (UC016)
    private JTable tablePhuTung;
    private DefaultTableModel tableModelPhuTung;
    private JButton themPhuTungBtn;
    private JButton xoaPhuTungBtn;

    // Tổng tiền & Nút lưu phiếu
    private JLabel tongTienLb;
    private JButton taoPhieuBtn;
    private JButton lamMoiBtn;

    // Bảng màu chuẩn
    private Color backgroundColor = new Color(241, 245, 249);
    private Color primaryColor = new Color(37, 99, 235);
    private Color textColor = new Color(15, 23, 42);
    private Color secondaryColor = new Color(71, 85, 105);
    private Color borderColor = new Color(203, 213, 225);
    private Color dangerColor = new Color(220, 38, 38);
    private Color successColor = new Color(22, 163, 74);
    private Color purpleColor = new Color(124, 58, 237);

    public LapPhieuSuaChua() {

        setTitle("Lập phiếu sửa chữa - Garage Management");
        setSize(1180, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout(0, 10));

        taoHeader();
        taoContent();

        setVisible(true);
    }

    private void taoHeader() {

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        headerPanel.setPreferredSize(new Dimension(1180, 70));

        JLabel titleLb = new JLabel("LẬP PHIẾU SỬA CHỮA & CHỈ ĐỊNH VẬT TƯ");
        titleLb.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLb.setForeground(textColor);

        headerPanel.add(titleLb, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void taoContent() {

        mainContentPanel = new JPanel(new BorderLayout(0, 12));
        mainContentPanel.setBackground(backgroundColor);
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));

        taoThongTinChungPanel();
        taoPanelHaiBang();
        taoBottomPanel();

        add(mainContentPanel, BorderLayout.CENTER);
    }

    private void taoThongTinChungPanel() {

        topInfoPanel = new JPanel(new GridLayout(2, 4, 15, 10));
        topInfoPanel.setBackground(Color.WHITE);
        topInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        phieuTiepNhanCb = new JComboBox<>(new String[]{
            "-- Chọn phiếu tiếp nhận --",
            "TN001 - 51A-123.45 (Toyota Camry)",
            "TN002 - 59A-456.78 (Honda CR-V)",
            "TN003 - 60A-111.11 (Mazda 3)"
        });
        phieuTiepNhanCb.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        bienSoTxt = taoTextFieldReadOnly();
        khachHangTxt = taoTextFieldReadOnly();
        ngayTiepNhanTxt = taoTextFieldReadOnly();

        topInfoPanel.add(taoLabel("Mã phiếu tiếp nhận:"));
        topInfoPanel.add(phieuTiepNhanCb);
        topInfoPanel.add(taoLabel("Biển số xe:"));
        topInfoPanel.add(bienSoTxt);

        topInfoPanel.add(taoLabel("Tên khách hàng:"));
        topInfoPanel.add(khachHangTxt);
        topInfoPanel.add(taoLabel("Ngày tiếp nhận:"));
        topInfoPanel.add(ngayTiepNhanTxt);

        mainContentPanel.add(topInfoPanel, BorderLayout.NORTH);

        phieuTiepNhanCb.addActionListener(e -> chonPhieuTiepNhan());
    }

    private void taoPanelHaiBang() {

        // 1. Panel Bảng Hạng mục Công việc (Bên trái)
        JPanel leftPanel = new JPanel(new BorderLayout(0, 8));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JPanel headerLeft = new JPanel(new BorderLayout());
        headerLeft.setBackground(Color.WHITE);

        JLabel titleLeft = new JLabel("1. HẠNG MỤC CÔNG VIỆC SỬA CHỮA");
        titleLeft.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLeft.setForeground(primaryColor);

        JPanel btnLeftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        btnLeftPanel.setBackground(Color.WHITE);

        themCongViecBtn = taoButton("Thêm CV", primaryColor);
        themCongViecBtn.setPreferredSize(new Dimension(105, 32));

        xoaCongViecBtn = taoButton("Xóa", dangerColor);
        xoaCongViecBtn.setPreferredSize(new Dimension(75, 32));

        btnLeftPanel.add(themCongViecBtn);
        btnLeftPanel.add(xoaCongViecBtn);

        headerLeft.add(titleLeft, BorderLayout.WEST);
        headerLeft.add(btnLeftPanel, BorderLayout.EAST);

        String[] colCongViec = {"STT", "Mã CV", "Tên công việc", "Tiền công (VNĐ)"};
        Object[][] dataCongViec = {
            {"1", "CV001", "Thay nhớt động cơ & lọc dầu", "150.000"},
            {"2", "CV002", "Vệ sinh & cân chỉnh phanh 4 bánh", "350.000"}
        };

        tableModelCongViec = new DefaultTableModel(dataCongViec, colCongViec) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableCongViec = taoTable(tableModelCongViec);
        JScrollPane scrollCongViec = new JScrollPane(tableCongViec);
        scrollCongViec.setBorder(BorderFactory.createLineBorder(borderColor));

        leftPanel.add(headerLeft, BorderLayout.NORTH);
        leftPanel.add(scrollCongViec, BorderLayout.CENTER);

        // 2. Panel Bảng Danh sách Phụ tùng (Bên phải)
        JPanel rightPanel = new JPanel(new BorderLayout(0, 8));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JPanel headerRight = new JPanel(new BorderLayout());
        headerRight.setBackground(Color.WHITE);

        JLabel titleRight = new JLabel("2. DANH SÁCH PHỤ TÙNG SỬ DỤNG");
        titleRight.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleRight.setForeground(purpleColor);

        JPanel btnRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        btnRightPanel.setBackground(Color.WHITE);

        themPhuTungBtn = taoButton("Thêm PT", purpleColor);
        themPhuTungBtn.setPreferredSize(new Dimension(105, 32));

        xoaPhuTungBtn = taoButton("Xóa", dangerColor);
        xoaPhuTungBtn.setPreferredSize(new Dimension(75, 32));

        btnRightPanel.add(themPhuTungBtn);
        btnRightPanel.add(xoaPhuTungBtn);

        headerRight.add(titleRight, BorderLayout.WEST);
        headerRight.add(btnRightPanel, BorderLayout.EAST);

        String[] colPhuTung = {"STT", "Mã PT", "Tên phụ tùng", "SL", "Đơn giá", "Thành tiền"};
        Object[][] dataPhuTung = {
            {"1", "PT001", "Nhớt Castrol Magnatec 4L", "1", "650.000", "650.000"},
            {"2", "PT002", "Lọc dầu Toyota chính hãng", "1", "180.000", "180.000"}
        };

        tableModelPhuTung = new DefaultTableModel(dataPhuTung, colPhuTung) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablePhuTung = taoTable(tableModelPhuTung);
        JScrollPane scrollPhuTung = new JScrollPane(tablePhuTung);
        scrollPhuTung.setBorder(BorderFactory.createLineBorder(borderColor));

        rightPanel.add(headerRight, BorderLayout.NORTH);
        rightPanel.add(scrollPhuTung, BorderLayout.CENTER);

        // Chia đôi không gian bằng JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(560);
        splitPane.setDividerSize(6);
        splitPane.setBackground(backgroundColor);
        splitPane.setBorder(null);

        mainContentPanel.add(splitPane, BorderLayout.CENTER);

        // Sự kiện các nút thêm/xóa
        themCongViecBtn.addActionListener(e -> moFormThemCongViec());
        xoaCongViecBtn.addActionListener(e -> xoaDongTable(tableCongViec, tableModelCongViec));

        themPhuTungBtn.addActionListener(e -> moFormThemPhuTung());
        xoaPhuTungBtn.addActionListener(e -> xoaDongTable(tablePhuTung, tableModelPhuTung));
    }

    private void taoBottomPanel() {

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));

        // Tổng chi phí ước tính
        tongTienLb = new JLabel("Tổng chi phí ước tính: 1.330.000 VNĐ");
        tongTienLb.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tongTienLb.setForeground(dangerColor);

        // Cụm nút thao tác
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setBackground(Color.WHITE);

        lamMoiBtn = taoButton("Làm mới", secondaryColor);
        taoPhieuBtn = taoButton("Tạo phiếu sửa chữa", successColor);
        taoPhieuBtn.setPreferredSize(new Dimension(200, 40));

        actionPanel.add(lamMoiBtn);
        actionPanel.add(taoPhieuBtn);

        bottomPanel.add(tongTienLb, BorderLayout.WEST);
        bottomPanel.add(actionPanel, BorderLayout.EAST);

        mainContentPanel.add(bottomPanel, BorderLayout.SOUTH);

        taoPhieuBtn.addActionListener(e -> xacNhanTaoPhieu());
        lamMoiBtn.addActionListener(e -> xoaForm());
    }

    private void chonPhieuTiepNhan() {

        int index = phieuTiepNhanCb.getSelectedIndex();
        if (index == 1) {
            bienSoTxt.setText("51A-123.45");
            khachHangTxt.setText("Nguyễn Văn An");
            ngayTiepNhanTxt.setText("04/10/2026");
        } else if (index == 2) {
            bienSoTxt.setText("59A-456.78");
            khachHangTxt.setText("Trần Thị Bình");
            ngayTiepNhanTxt.setText("04/10/2026");
        } else if (index == 3) {
            bienSoTxt.setText("60A-111.11");
            khachHangTxt.setText("Lê Hoàng Nam");
            ngayTiepNhanTxt.setText("03/10/2026");
        } else {
            bienSoTxt.setText("");
            khachHangTxt.setText("");
            ngayTiepNhanTxt.setText("");
        }
    }

    private void moFormThemCongViec() {

        JDialog dialog = taoDialog("Thêm hạng mục công việc");

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JComboBox congViecCb = new JComboBox<>(new String[]{
            "CV003 - Kiểm tra & cân chỉnh thước lái",
            "CV004 - Vệ sinh lọc gió động cơ & điều hòa",
            "CV005 - Nạp gas điều hòa R134a"
        });

        JTextField tienCongTxt = new JTextField("250.000");

        panel.add(taoLabel("Chọn công việc:"));
        panel.add(congViecCb);
        panel.add(taoLabel("Tiền công (VNĐ):"));
        panel.add(tienCongTxt);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Color.WHITE);

        JButton luuBtn = taoButton("Thêm", primaryColor);
        JButton huyBtn = taoButton("Hủy", secondaryColor);

        btnPanel.add(huyBtn);
        btnPanel.add(luuBtn);

        huyBtn.addActionListener(e -> dialog.dispose());

        luuBtn.addActionListener(e -> {
            int stt = tableModelCongViec.getRowCount() + 1;
            String cvSec = congViecCb.getSelectedItem().toString();
            String maCv = cvSec.split(" - ")[0];
            String tenCv = cvSec.split(" - ")[1];

            tableModelCongViec.addRow(new Object[]{stt, maCv, tenCv, tienCongTxt.getText()});
            dialog.dispose();
            tinhTongTien();
        });

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void moFormThemPhuTung() {

        JDialog dialog = taoDialog("Thêm phụ tùng vào phiếu");

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JComboBox phuTungCb = new JComboBox<>(new String[]{
            "PT003 - Má phanh trước Toyota Camry",
            "PT004 - Lọc gió động cơ Denso",
            "PT005 - Gạt mưa Bosch AeroTwin"
        });

        JTextField soLuongTxt = new JTextField("1");
        JTextField donGiaTxt = new JTextField("850.000");

        panel.add(taoLabel("Chọn phụ tùng:"));
        panel.add(phuTungCb);
        panel.add(taoLabel("Số lượng:"));
        panel.add(soLuongTxt);
        panel.add(taoLabel("Đơn giá (VNĐ):"));
        panel.add(donGiaTxt);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Color.WHITE);

        JButton luuBtn = taoButton("Thêm", purpleColor);
        JButton huyBtn = taoButton("Hủy", secondaryColor);

        btnPanel.add(huyBtn);
        btnPanel.add(luuBtn);

        huyBtn.addActionListener(e -> dialog.dispose());

        luuBtn.addActionListener(e -> {
            int stt = tableModelPhuTung.getRowCount() + 1;
            String ptSec = phuTungCb.getSelectedItem().toString();
            String maPt = ptSec.split(" - ")[0];
            String tenPt = ptSec.split(" - ")[1];

            int sl = Integer.parseInt(soLuongTxt.getText());
            long donGia = Long.parseLong(donGiaTxt.getText().replace(".", ""));
            long thanhTien = sl * donGia;

            tableModelPhuTung.addRow(new Object[]{
                stt, maPt, tenPt, sl, donGiaTxt.getText(), String.format("%,d", thanhTien).replace(",", ".")
            });
            dialog.dispose();
            tinhTongTien();
        });

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void xoaDongTable(JTable table, DefaultTableModel model) {

        int row = table.getSelectedRow();
        if (row != -1) {
            model.removeRow(row);
            tinhTongTien();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void tinhTongTien() {

        // Giả lập tính lại tổng tiền
        tongTienLb.setText("Tổng chi phí ước tính: 2.430.000 VNĐ");
    }

    private void xacNhanTaoPhieu() {

        if (phieuTiepNhanCb.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu tiếp nhận xe!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (tableModelCongViec.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chỉ định ít nhất 1 hạng mục công việc!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Tạo phiếu sửa chữa thành công!\nMã phiếu: PSC-20261004-01\nĐã chuyển trạng thái sang Chờ báo giá / Phân công.",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE
        );
        xoaForm();
    }

    private void xoaForm() {

        phieuTiepNhanCb.setSelectedIndex(0);
        bienSoTxt.setText("");
        khachHangTxt.setText("");
        ngayTiepNhanTxt.setText("");
    }

    private JTable taoTable(DefaultTableModel model) {

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(36);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(textColor);
        table.setShowVerticalLines(false);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(30, 41, 59));
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));

        return table;
    }

    private JDialog taoDialog(String title) {

        JDialog dialog = new JDialog(this, title, true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(480, 280);
        dialog.setLocationRelativeTo(this);

        return dialog;
    }

    private JLabel taoLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(secondaryColor);

        return label;
    }

    private JTextField taoTextFieldReadOnly() {

        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setEditable(false);
        textField.setBackground(new Color(248, 250, 252));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(0, 8, 0, 8)
        ));

        return textField;
    }

    private JButton taoButton(String text, Color color) {

        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    public static void main(String[] args) {
        new LapPhieuSuaChua();
    }
}