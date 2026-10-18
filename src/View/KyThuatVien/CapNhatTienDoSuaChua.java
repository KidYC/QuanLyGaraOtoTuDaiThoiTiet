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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class CapNhatTienDoSuaChua extends JFrame {
    private JPanel pnlHeaderPanel;
    private JSplitPane splitPane;
    private JPanel pnlLeftPanel;
    private JTextField txtSearch;
    private JButton btnTim;
    private JTable tblLenhSC;
    private DefaultTableModel tableModelLenhSC;
    private JPanel pnlRightPanel;
    private JTextField txtMaLenh;
    private JTextField txtBienSo;
    private JTextField txtKhachHang;
    private JTable tblChiTietCV;
    private DefaultTableModel tableModelChiTietCV;
    private JComboBox cmbTrangThai;
    private JComboBox cmbTienDo;
    private JProgressBar prgBar;
    private JTextArea txaGhiChu;
    private JButton btnLuuTienDo;
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

    public CapNhatTienDoSuaChua() {
        setTitle("Công việc được phân công & Tiến độ - Garage Management");
        setSize(1200, 750);
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

        JLabel lblTitle = new JLabel("CÔNG VIỆC ĐƯỢC PHÂN CÔNG & CẬP NHẬT TIẾN ĐỘ");
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
        taoDanhSachLenhPanel();
        taoChiTietTienDoPanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLeftPanel, pnlRightPanel);
        splitPane.setDividerLocation(460);
        splitPane.setDividerSize(6);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));
        splitPane.setBackground(maunen);

        add(splitPane, BorderLayout.CENTER);
    }

    private void taoDanhSachLenhPanel() {
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
        txtSearch.setPreferredSize(new Dimension(250, 40));

        btnTim = taoButton("Tìm lệnh", btnchinh);

        pnlSearchPanel.add(txtSearch, BorderLayout.CENTER);
        pnlSearchPanel.add(btnTim, BorderLayout.EAST);

        String[] columns = {"Mã lệnh", "Biển số", "Trạng thái", "Tiến độ"};
        Object[][] data = {
                {"SC001", "51A-123.45", "Đang sửa chữa", "60%"},
                {"SC002", "59A-456.78", "Chờ phụ tùng", "30%"},
                {"SC003", "60A-111.11", "Đang sửa chữa", "90%"},
                {"SC004", "61B-999.88", "Mới phân công", "0%"}
        };

        tableModelLenhSC = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLenhSC = taoTable(tableModelLenhSC);

        JScrollPane scrScrollPane = new JScrollPane(tblLenhSC);
        scrScrollPane.setBorder(BorderFactory.createLineBorder(maucotbang));
        scrScrollPane.getViewport().setBackground(Color.WHITE);

        pnlLeftPanel.add(pnlSearchPanel, BorderLayout.NORTH);
        pnlLeftPanel.add(scrScrollPane, BorderLayout.CENTER);

        tblLenhSC.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hienThiChiTietLenhDuocChon();
            }
        });
    }

    private void taoChiTietTienDoPanel() {
        pnlRightPanel = new JPanel(new BorderLayout(0, 12));
        pnlRightPanel.setBackground(Color.WHITE);
        pnlRightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(15, 18, 15, 18)
        ));

        JPanel pnlInfoPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        pnlInfoPanel.setBackground(Color.WHITE);
        pnlInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(maucotbang),
                "Thông tin lệnh sửa chữa",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                chuchinh
        ));

        txtMaLenh = taoTextFieldReadOnly();
        txtBienSo = taoTextFieldReadOnly();
        txtKhachHang = taoTextFieldReadOnly();

        pnlInfoPanel.add(taoLabel("Mã lệnh SC:"));
        pnlInfoPanel.add(taoLabel("Biển số xe:"));
        pnlInfoPanel.add(taoLabel("Khách hàng:"));
        pnlInfoPanel.add(txtMaLenh);
        pnlInfoPanel.add(txtBienSo);
        pnlInfoPanel.add(txtKhachHang);

        JPanel pnlTaskTablePanel = new JPanel(new BorderLayout(0, 8));
        pnlTaskTablePanel.setBackground(Color.WHITE);

        JLabel lblTaskTitle = taoLabel("Danh sách hạng mục công việc & phụ tùng đi kèm:");

        String[] colCV = {"STT", "Hạng mục công việc / Phụ tùng", "Loại", "Trạng thái CV"};
        Object[][] dataCV = {
                {"1", "Thay nhớt động cơ Castrol Magnatec", "Công việc + PT", "Hoàn thành"},
                {"2", "Thay lọc dầu Toyota chính hãng", "Phụ tùng", "Hoàn thành"},
                {"3", "Vệ sinh & cân chỉnh phanh 4 bánh", "Công việc", "Đang thi công"}
        };

        tableModelChiTietCV = new DefaultTableModel(dataCV, colCV) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblChiTietCV = taoTable(tableModelChiTietCV);

        JScrollPane scrCV = new JScrollPane(tblChiTietCV);
        scrCV.setBorder(BorderFactory.createLineBorder(maucotbang));
        scrCV.setPreferredSize(new Dimension(0, 140));

        pnlTaskTablePanel.add(lblTaskTitle, BorderLayout.NORTH);
        pnlTaskTablePanel.add(scrCV, BorderLayout.CENTER);

        JPanel pnlUpdateFormPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlUpdateFormPanel.setBackground(Color.WHITE);
        pnlUpdateFormPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(maucotbang),
                "Cập nhật tiến độ & Kết quả thực hiện",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                chuchinh
        ));

        cmbTrangThai = new JComboBox<>(new String[]{
                "Đang sửa chữa",
                "Chờ phụ tùng",
                "Tạm dừng sửa chữa",
                "Hoàn thành"
        });
        cmbTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbTrangThai.setBackground(Color.WHITE);

        cmbTienDo = new JComboBox<>(new String[]{
                "0%", "20%", "40%", "60%", "80%", "100%"
        });
        cmbTienDo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbTienDo.setBackground(Color.WHITE);
        cmbTienDo.setSelectedItem("60%");

        prgBar = new JProgressBar(0, 100);
        prgBar.setValue(60);
        prgBar.setStringPainted(true);
        prgBar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        prgBar.setForeground(btnchinh);
        prgBar.setBackground(maumenu);

        pnlUpdateFormPanel.add(taoLabel("Trạng thái chung:"));
        pnlUpdateFormPanel.add(cmbTrangThai);
        pnlUpdateFormPanel.add(taoLabel("Tiến độ tổng thể (%):"));
        pnlUpdateFormPanel.add(cmbTienDo);
        pnlUpdateFormPanel.add(taoLabel("Thanh tiến độ:"));
        pnlUpdateFormPanel.add(prgBar);

        JPanel pnlNotePanel = new JPanel(new BorderLayout(0, 5));
        pnlNotePanel.setBackground(Color.WHITE);

        txaGhiChu = taoTextArea();

        JScrollPane scrNote = new JScrollPane(txaGhiChu);
        scrNote.setBorder(BorderFactory.createLineBorder(maucotbang));
        scrNote.setPreferredSize(new Dimension(0, 75));

        pnlNotePanel.add(taoLabel("Ghi chú kết quả / Báo cáo sự cố phát sinh:"), BorderLayout.NORTH);
        pnlNotePanel.add(scrNote, BorderLayout.CENTER);

        JPanel pnlBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlBtnPanel.setBackground(Color.WHITE);

        btnLamMoi = taoButton("Làm mới", maumenu);
        btnLuuTienDo = taoButton("Lưu tiến độ & Kết quả", btnchinh);
        btnLuuTienDo.setPreferredSize(new Dimension(210, 40));

        pnlBtnPanel.add(btnLamMoi);
        pnlBtnPanel.add(btnLuuTienDo);

        JPanel pnlCenterContainer = new JPanel(new BorderLayout(0, 10));
        pnlCenterContainer.setBackground(Color.WHITE);
        pnlCenterContainer.add(pnlInfoPanel, BorderLayout.NORTH);
        pnlCenterContainer.add(pnlTaskTablePanel, BorderLayout.CENTER);

        JPanel pnlBottomContainer = new JPanel(new BorderLayout(0, 10));
        pnlBottomContainer.setBackground(Color.WHITE);
        pnlBottomContainer.add(pnlUpdateFormPanel, BorderLayout.NORTH);
        pnlBottomContainer.add(pnlNotePanel, BorderLayout.CENTER);
        pnlBottomContainer.add(pnlBtnPanel, BorderLayout.SOUTH);

        pnlRightPanel.add(pnlCenterContainer, BorderLayout.CENTER);
        pnlRightPanel.add(pnlBottomContainer, BorderLayout.SOUTH);

        cmbTienDo.addActionListener(e -> {
            String val = cmbTienDo.getSelectedItem().toString().replace("%", "");
            prgBar.setValue(Integer.parseInt(val));
        });

        btnLuuTienDo.addActionListener(e -> luuTienDo());
        btnLamMoi.addActionListener(e -> xoaForm());
    }

    private void hienThiChiTietLenhDuocChon() {
        int row = tblLenhSC.getSelectedRow();

        if (row != -1) {
            String maLenh = tableModelLenhSC.getValueAt(row, 0).toString();
            String bienSo = tableModelLenhSC.getValueAt(row, 1).toString();
            String trangThai = tableModelLenhSC.getValueAt(row, 2).toString();
            String tienDo = tableModelLenhSC.getValueAt(row, 3).toString();

            txtMaLenh.setText(maLenh);
            txtBienSo.setText(bienSo);

            if (bienSo.equals("51A-123.45")) {
                txtKhachHang.setText("Nguyễn Văn An");
            } else if (bienSo.equals("59A-456.78")) {
                txtKhachHang.setText("Trần Thị Bình");
            } else {
                txtKhachHang.setText("Lê Hoàng Nam");
            }

            cmbTrangThai.setSelectedItem(trangThai);
            cmbTienDo.setSelectedItem(tienDo);
            prgBar.setValue(Integer.parseInt(tienDo.replace("%", "")));
        }
    }

    private void luuTienDo() {
        int row = tblLenhSC.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn lệnh sửa chữa cần cập nhật tiến độ!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String trangThaiMoi = cmbTrangThai.getSelectedItem().toString();
        String tienDoMoi = cmbTienDo.getSelectedItem().toString();

        tableModelLenhSC.setValueAt(trangThaiMoi, row, 2);
        tableModelLenhSC.setValueAt(tienDoMoi, row, 3);

        JOptionPane.showMessageDialog(
                this,
                "Cập nhật tiến độ & trạng thái lệnh sửa chữa thành công!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void xoaForm() {
        txtMaLenh.setText("");
        txtBienSo.setText("");
        txtKhachHang.setText("");
        txaGhiChu.setText("");
        tblLenhSC.clearSelection();
        prgBar.setValue(0);
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
        return btnButton;
    }

    private void quayLaiTrangChu() {
        dispose();
    }

    public static void main(String[] args) {
        new CapNhatTienDoSuaChua();
    }
}