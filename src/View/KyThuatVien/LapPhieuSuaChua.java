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
import javax.swing.table.DefaultTableModel;

public class LapPhieuSuaChua extends JFrame {
    private JPanel pnlHeaderPanel;
    private JPanel pnlMainContentPanel;
    private JPanel pnlTopInfoPanel;
    private JComboBox cmbPhieuTiepNhan;
    private JTextField txtBienSo;
    private JTextField txtKhachHang;
    private JTextField txtNgayTiepNhan;
    private JTable tblCongViec;
    private DefaultTableModel tableModelCongViec;
    private JButton btnThemCongViec;
    private JButton btnXoaCongViec;
    private JTable tblPhuTung;
    private DefaultTableModel tableModelPhuTung;
    private JButton btnThemPhuTung;
    private JButton btnXoaPhuTung;
    private JLabel lblTongTien;
    private JButton btnTaoPhieu;
    private JButton btnLamMoi;
    private JButton btnQuayLai;

    private Color maunen = new Color(244, 246, 248);
    private Color maumenu = new Color(226, 232, 240);
    private Color btnchinh = new Color(186, 230, 253);
    private Color btnphu = new Color(219, 234, 254);
    private Color chuchinh = new Color(17, 24, 39);
    private Color maucotbang = new Color(186, 230, 253);
    private Color mauphu = new Color(107, 114, 128);
    private Color maudo = new Color(220, 38, 38);
    private Color mauxanh = new Color(34, 197, 94);

    public LapPhieuSuaChua() {
        setTitle("Lập phiếu sửa chữa - Garage Management");
        setSize(1180, 750);
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
        pnlHeaderPanel.setPreferredSize(new Dimension(1180, 75));

        JLabel lblTitle = new JLabel("LẬP PHIẾU SỬA CHỮA & CHỈ ĐỊNH VẬT TƯ");
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
        pnlMainContentPanel = new JPanel(new BorderLayout(0, 12));
        pnlMainContentPanel.setBackground(maunen);
        pnlMainContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));

        taoThongTinChungPanel();
        taoPanelHaiBang();
        taoBottomPanel();

        add(pnlMainContentPanel, BorderLayout.CENTER);
    }

    private void taoThongTinChungPanel() {
        pnlTopInfoPanel = new JPanel(new GridLayout(2, 4, 15, 10));
        pnlTopInfoPanel.setBackground(Color.WHITE);
        pnlTopInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        cmbPhieuTiepNhan = new JComboBox<>(new String[]{
                "-- Chọn phiếu tiếp nhận --",
                "TN001 - 51A-123.45 (Toyota Camry)",
                "TN002 - 59A-456.78 (Honda CR-V)",
                "TN003 - 60A-111.11 (Mazda 3)"
        });

        cmbPhieuTiepNhan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbPhieuTiepNhan.setBackground(Color.WHITE);

        txtBienSo = taoTextFieldReadOnly();
        txtKhachHang = taoTextFieldReadOnly();
        txtNgayTiepNhan = taoTextFieldReadOnly();

        pnlTopInfoPanel.add(taoLabel("Mã phiếu tiếp nhận:"));
        pnlTopInfoPanel.add(cmbPhieuTiepNhan);
        pnlTopInfoPanel.add(taoLabel("Biển số xe:"));
        pnlTopInfoPanel.add(txtBienSo);
        pnlTopInfoPanel.add(taoLabel("Tên khách hàng:"));
        pnlTopInfoPanel.add(txtKhachHang);
        pnlTopInfoPanel.add(taoLabel("Ngày tiếp nhận:"));
        pnlTopInfoPanel.add(txtNgayTiepNhan);

        pnlMainContentPanel.add(pnlTopInfoPanel, BorderLayout.NORTH);

        cmbPhieuTiepNhan.addActionListener(e -> chonPhieuTiepNhan());
    }

    private void taoPanelHaiBang() {
        JPanel pnlLeftPanel = new JPanel(new BorderLayout(0, 8));
        pnlLeftPanel.setBackground(Color.WHITE);
        pnlLeftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JPanel pnlHeaderLeft = new JPanel(new BorderLayout());
        pnlHeaderLeft.setBackground(Color.WHITE);

        JLabel lblTitleLeft = new JLabel("1. HẠNG MỤC CÔNG VIỆC SỬA CHỮA");
        lblTitleLeft.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitleLeft.setForeground(chuchinh);

        JPanel pnlBtnLeftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pnlBtnLeftPanel.setBackground(Color.WHITE);

        btnThemCongViec = taoButton("Thêm CV", btnchinh);
        btnThemCongViec.setPreferredSize(new Dimension(105, 32));

        btnXoaCongViec = taoButton("Xóa", maudo);
        btnXoaCongViec.setPreferredSize(new Dimension(75, 32));

        pnlBtnLeftPanel.add(btnThemCongViec);
        pnlBtnLeftPanel.add(btnXoaCongViec);

        pnlHeaderLeft.add(lblTitleLeft, BorderLayout.WEST);
        pnlHeaderLeft.add(pnlBtnLeftPanel, BorderLayout.EAST);

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

        tblCongViec = taoTable(tableModelCongViec);

        JScrollPane scrCongViec = new JScrollPane(tblCongViec);
        scrCongViec.setBorder(BorderFactory.createLineBorder(maucotbang));

        pnlLeftPanel.add(pnlHeaderLeft, BorderLayout.NORTH);
        pnlLeftPanel.add(scrCongViec, BorderLayout.CENTER);

        JPanel pnlRightPanel = new JPanel(new BorderLayout(0, 8));
        pnlRightPanel.setBackground(Color.WHITE);
        pnlRightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JPanel pnlHeaderRight = new JPanel(new BorderLayout());
        pnlHeaderRight.setBackground(Color.WHITE);

        JLabel lblTitleRight = new JLabel("2. DANH SÁCH PHỤ TÙNG SỬ DỤNG");
        lblTitleRight.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitleRight.setForeground(chuchinh);

        JPanel pnlBtnRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pnlBtnRightPanel.setBackground(Color.WHITE);

        btnThemPhuTung = taoButton("Thêm PT", btnphu);
        btnThemPhuTung.setPreferredSize(new Dimension(105, 32));

        btnXoaPhuTung = taoButton("Xóa", maudo);
        btnXoaPhuTung.setPreferredSize(new Dimension(75, 32));

        pnlBtnRightPanel.add(btnThemPhuTung);
        pnlBtnRightPanel.add(btnXoaPhuTung);

        pnlHeaderRight.add(lblTitleRight, BorderLayout.WEST);
        pnlHeaderRight.add(pnlBtnRightPanel, BorderLayout.EAST);

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

        tblPhuTung = taoTable(tableModelPhuTung);

        JScrollPane scrPhuTung = new JScrollPane(tblPhuTung);
        scrPhuTung.setBorder(BorderFactory.createLineBorder(maucotbang));

        pnlRightPanel.add(pnlHeaderRight, BorderLayout.NORTH);
        pnlRightPanel.add(scrPhuTung, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLeftPanel, pnlRightPanel);
        splitPane.setDividerLocation(560);
        splitPane.setDividerSize(6);
        splitPane.setBackground(maunen);
        splitPane.setBorder(null);

        pnlMainContentPanel.add(splitPane, BorderLayout.CENTER);

        btnThemCongViec.addActionListener(e -> moFormThemCongViec());
        btnXoaCongViec.addActionListener(e -> xoaDongTable(tblCongViec, tableModelCongViec));
        btnThemPhuTung.addActionListener(e -> moFormThemPhuTung());
        btnXoaPhuTung.addActionListener(e -> xoaDongTable(tblPhuTung, tableModelPhuTung));
    }

    private void taoBottomPanel() {
        JPanel pnlBottomPanel = new JPanel(new BorderLayout());
        pnlBottomPanel.setBackground(Color.WHITE);
        pnlBottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));

        lblTongTien = new JLabel("Tổng chi phí ước tính: 1.330.000 VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTongTien.setForeground(maudo);

        JPanel pnlActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlActionPanel.setBackground(Color.WHITE);

        btnLamMoi = taoButton("Làm mới", maumenu);
        btnTaoPhieu = taoButton("Tạo phiếu sửa chữa", mauxanh);
        btnTaoPhieu.setPreferredSize(new Dimension(200, 40));

        pnlActionPanel.add(btnLamMoi);
        pnlActionPanel.add(btnTaoPhieu);

        pnlBottomPanel.add(lblTongTien, BorderLayout.WEST);
        pnlBottomPanel.add(pnlActionPanel, BorderLayout.EAST);

        pnlMainContentPanel.add(pnlBottomPanel, BorderLayout.SOUTH);

        btnTaoPhieu.addActionListener(e -> xacNhanTaoPhieu());
        btnLamMoi.addActionListener(e -> xoaForm());
    }

    private void chonPhieuTiepNhan() {
        int index = cmbPhieuTiepNhan.getSelectedIndex();

        if (index == 1) {
            txtBienSo.setText("51A-123.45");
            txtKhachHang.setText("Nguyễn Văn An");
            txtNgayTiepNhan.setText("04/10/2026");
        } else if (index == 2) {
            txtBienSo.setText("59A-456.78");
            txtKhachHang.setText("Trần Thị Bình");
            txtNgayTiepNhan.setText("04/10/2026");
        } else if (index == 3) {
            txtBienSo.setText("60A-111.11");
            txtKhachHang.setText("Lê Hoàng Nam");
            txtNgayTiepNhan.setText("03/10/2026");
        } else {
            txtBienSo.setText("");
            txtKhachHang.setText("");
            txtNgayTiepNhan.setText("");
        }
    }

    private void moFormThemCongViec() {
        JDialog winDialog = taoDialog("Thêm hạng mục công việc");

        JPanel pnlPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        pnlPanel.setBackground(Color.WHITE);
        pnlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JComboBox cmbCongViec = new JComboBox<>(new String[]{
                "CV003 - Kiểm tra & cân chỉnh thước lái",
                "CV004 - Vệ sinh lọc gió động cơ & điều hòa",
                "CV005 - Nạp gas điều hòa R134a"
        });

        JTextField txtTienCong = new JTextField("250.000");

        pnlPanel.add(taoLabel("Chọn công việc:"));
        pnlPanel.add(cmbCongViec);
        pnlPanel.add(taoLabel("Tiền công (VNĐ):"));
        pnlPanel.add(txtTienCong);

        JPanel pnlBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBtnPanel.setBackground(Color.WHITE);

        JButton btnLuu = taoButton("Thêm", btnchinh);
        JButton btnHuy = taoButton("Hủy", maumenu);

        pnlBtnPanel.add(btnHuy);
        pnlBtnPanel.add(btnLuu);

        btnHuy.addActionListener(e -> winDialog.dispose());

        btnLuu.addActionListener(e -> {
            int stt = tableModelCongViec.getRowCount() + 1;
            String cvSec = cmbCongViec.getSelectedItem().toString();
            String maCv = cvSec.split(" - ")[0];
            String tenCv = cvSec.split(" - ")[1];

            tableModelCongViec.addRow(new Object[]{
                    stt, maCv, tenCv, txtTienCong.getText()
            });

            winDialog.dispose();
            tinhTongTien();
        });

        winDialog.add(pnlPanel, BorderLayout.CENTER);
        winDialog.add(pnlBtnPanel, BorderLayout.SOUTH);
        winDialog.setVisible(true);
    }

    private void moFormThemPhuTung() {
        JDialog winDialog = taoDialog("Thêm phụ tùng vào phiếu");

        JPanel pnlPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        pnlPanel.setBackground(Color.WHITE);
        pnlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JComboBox cmbPhuTung = new JComboBox<>(new String[]{
                "PT003 - Má phanh trước Toyota Camry",
                "PT004 - Lọc gió động cơ Denso",
                "PT005 - Gạt mưa Bosch AeroTwin"
        });

        JTextField txtSoLuong = new JTextField("1");
        JTextField txtDonGia = new JTextField("850.000");

        pnlPanel.add(taoLabel("Chọn phụ tùng:"));
        pnlPanel.add(cmbPhuTung);
        pnlPanel.add(taoLabel("Số lượng:"));
        pnlPanel.add(txtSoLuong);
        pnlPanel.add(taoLabel("Đơn giá (VNĐ):"));
        pnlPanel.add(txtDonGia);

        JPanel pnlBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBtnPanel.setBackground(Color.WHITE);

        JButton btnLuu = taoButton("Thêm", btnphu);
        JButton btnHuy = taoButton("Hủy", maumenu);

        pnlBtnPanel.add(btnHuy);
        pnlBtnPanel.add(btnLuu);

        btnHuy.addActionListener(e -> winDialog.dispose());

        btnLuu.addActionListener(e -> {
            int stt = tableModelPhuTung.getRowCount() + 1;
            String ptSec = cmbPhuTung.getSelectedItem().toString();
            String maPt = ptSec.split(" - ")[0];
            String tenPt = ptSec.split(" - ")[1];
            int sl = Integer.parseInt(txtSoLuong.getText());
            long donGia = Long.parseLong(txtDonGia.getText().replace(".", ""));
            long thanhTien = sl * donGia;

            tableModelPhuTung.addRow(new Object[]{
                    stt,
                    maPt,
                    tenPt,
                    sl,
                    txtDonGia.getText(),
                    String.format("%,d", thanhTien).replace(",", ".")
            });

            winDialog.dispose();
            tinhTongTien();
        });

        winDialog.add(pnlPanel, BorderLayout.CENTER);
        winDialog.add(pnlBtnPanel, BorderLayout.SOUTH);
        winDialog.setVisible(true);
    }

    private void xoaDongTable(JTable tblTable, DefaultTableModel model) {
        int row = tblTable.getSelectedRow();

        if (row != -1) {
            model.removeRow(row);
            tinhTongTien();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn dòng cần xóa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void tinhTongTien() {
        lblTongTien.setText("Tổng chi phí ước tính: 2.430.000 VNĐ");
    }

    private void xacNhanTaoPhieu() {
        if (cmbPhieuTiepNhan.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn phiếu tiếp nhận xe!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (tableModelCongViec.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chỉ định ít nhất 1 hạng mục công việc!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
            );
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
        cmbPhieuTiepNhan.setSelectedIndex(0);
        txtBienSo.setText("");
        txtKhachHang.setText("");
        txtNgayTiepNhan.setText("");
    }

    private JTable taoTable(DefaultTableModel model) {
        JTable tblTable = new JTable(model);
        tblTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblTable.setRowHeight(36);
        tblTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblTable.setSelectionBackground(btnphu);
        tblTable.setSelectionForeground(chuchinh);
        tblTable.setShowVerticalLines(false);
        tblTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblTable.getTableHeader().setForeground(chuchinh);
        tblTable.getTableHeader().setBackground(btnchinh);
        tblTable.getTableHeader().setPreferredSize(new Dimension(0, 38));
        return tblTable;
    }

    private JDialog taoDialog(String title) {
        JDialog winDialog = new JDialog(this, title, true);
        winDialog.setLayout(new BorderLayout());
        winDialog.setSize(480, 280);
        winDialog.setLocationRelativeTo(this);
        return winDialog;
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

    private JButton taoButton(String text, Color color) {
        JButton btnButton = new JButton(text);
        btnButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnButton.setForeground(chuchinh);
        btnButton.setBackground(color);
        btnButton.setFocusPainted(false);
        btnButton.setBorderPainted(false);
        btnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btnButton;
    }

    private void quayLaiTrangChu() {
        dispose();
    }

    public static void main(String[] args) {
        new LapPhieuSuaChua();
    }
}