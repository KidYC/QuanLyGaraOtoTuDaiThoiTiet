package View.KyThuatVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class KiemTraChatLuong extends JFrame {

    private JPanel pnlHeaderPanel;
    private JSplitPane splitPane;
    private JPanel pnlLeftPanel;
    private JTextField txtSearch;
    private JButton btnTim;
    private JTable tblXeKCS;
    private DefaultTableModel tableModelXeKCS;
    private JPanel pnlRightPanel;
    private JTextField txtMaPhieu;
    private JTextField txtBienSo;
    private JTextField txtKhachHang;
    private JTextField txtKtvThucHien;
    private JTable tblChecklist;
    private DefaultTableModel tableModelChecklist;
    private JRadioButton radDat;
    private JRadioButton radKhongDat;
    private ButtonGroup danhGiaGroup;
    private JTextArea txaGhiChu;
    private JButton btnLuuKcs;
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

    public KiemTraChatLuong() {
        setTitle("Kiểm tra chất lượng (KCS) - Garage Management");
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

        JLabel lblTitle = new JLabel("KIỂM TRA CHẤT LƯỢNG XE SAU SỬA CHỮA (KCS)");
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
        taoDanhSachXeKCSPanel();
        taoFormDanhGiaPanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLeftPanel, pnlRightPanel);
        splitPane.setDividerLocation(460);
        splitPane.setDividerSize(6);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));
        splitPane.setBackground(maunen);

        add(splitPane, BorderLayout.CENTER);
    }

    private void taoDanhSachXeKCSPanel() {
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

        btnTim = taoButton("Tìm xe", btnchinh);

        pnlSearchPanel.add(txtSearch, BorderLayout.CENTER);
        pnlSearchPanel.add(btnTim, BorderLayout.EAST);

        String[] columns = {"Biển số", "Mã phiếu", "KTV phụ trách", "Trạng thái KCS"};
        Object[][] data = {
                {"51A-123.45", "PSC001", "Nguyễn Văn An", "Chờ kiểm tra"},
                {"59A-456.78", "PSC002", "Trần Văn Bình", "Chờ kiểm tra"},
                {"60A-111.11", "PSC003", "Lê Minh Hoàng", "Đang nghiệm thu"},
                {"61B-999.88", "PSC004", "Phạm Văn Nam", "Chờ kiểm tra"}
        };

        tableModelXeKCS = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblXeKCS = taoTable(tableModelXeKCS);

        JScrollPane scrScrollPane = new JScrollPane(tblXeKCS);
        scrScrollPane.setBorder(BorderFactory.createLineBorder(maucotbang));
        scrScrollPane.getViewport().setBackground(Color.WHITE);

        pnlLeftPanel.add(pnlSearchPanel, BorderLayout.NORTH);
        pnlLeftPanel.add(scrScrollPane, BorderLayout.CENTER);

        tblXeKCS.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hienThiChiTietXeDuocChon();
            }
        });
    }

    private void taoFormDanhGiaPanel() {
        pnlRightPanel = new JPanel(new BorderLayout(0, 12));
        pnlRightPanel.setBackground(Color.WHITE);
        pnlRightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(15, 18, 15, 18)
        ));

        JPanel pnlInfoPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        pnlInfoPanel.setBackground(Color.WHITE);
        pnlInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(maucotbang),
                "Thông tin phương tiện nghiệm thu",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                chuchinh
        ));

        txtMaPhieu = taoTextFieldReadOnly();
        txtBienSo = taoTextFieldReadOnly();
        txtKhachHang = taoTextFieldReadOnly();
        txtKtvThucHien = taoTextFieldReadOnly();

        pnlInfoPanel.add(taoLabel("Mã phiếu SC:"));
        pnlInfoPanel.add(txtMaPhieu);
        pnlInfoPanel.add(taoLabel("Biển số xe:"));
        pnlInfoPanel.add(txtBienSo);
        pnlInfoPanel.add(taoLabel("Khách hàng:"));
        pnlInfoPanel.add(txtKhachHang);
        pnlInfoPanel.add(taoLabel("KTV thi công:"));
        pnlInfoPanel.add(txtKtvThucHien);

        JPanel pnlChecklistPanel = new JPanel(new BorderLayout(0, 8));
        pnlChecklistPanel.setBackground(Color.WHITE);

        JLabel lblChecklistTitle = taoLabel("Danh sách hạng mục công việc / vật tư cần KCS:");

        String[] colChecklist = {"STT", "Hạng mục sửa chữa", "Kết quả KTV", "Đánh giá KCS"};
        Object[][] dataChecklist = {
                {"1", "Thay nhớt động cơ & Lọc dầu", "Hoàn thành", "Đạt"},
                {"2", "Vệ sinh & cân chỉnh phanh 4 bánh", "Hoàn thành", "Đạt"},
                {"3", "Kiểm tra áp suất lốp & chạy thử", "Hoàn thành", "Đạt"}
        };

        tableModelChecklist = new DefaultTableModel(dataChecklist, colChecklist) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblChecklist = taoTable(tableModelChecklist);

        JScrollPane scrChecklist = new JScrollPane(tblChecklist);
        scrChecklist.setBorder(BorderFactory.createLineBorder(maucotbang));
        scrChecklist.setPreferredSize(new Dimension(0, 140));

        pnlChecklistPanel.add(lblChecklistTitle, BorderLayout.NORTH);
        pnlChecklistPanel.add(scrChecklist, BorderLayout.CENTER);

        JPanel pnlEvaluationPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlEvaluationPanel.setBackground(Color.WHITE);
        pnlEvaluationPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(maucotbang),
                "Kết luận nghiệm thu chất lượng",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                chuchinh
        ));

        radDat = new JRadioButton("ĐẠT (Chuyển chờ bàn giao)");
        radDat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        radDat.setForeground(mauxanh);
        radDat.setBackground(Color.WHITE);
        radDat.setSelected(true);

        radKhongDat = new JRadioButton("KHÔNG ĐẠT (Yêu cầu sửa lại)");
        radKhongDat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        radKhongDat.setForeground(maudo);
        radKhongDat.setBackground(Color.WHITE);

        danhGiaGroup = new ButtonGroup();
        danhGiaGroup.add(radDat);
        danhGiaGroup.add(radKhongDat);

        pnlEvaluationPanel.add(radDat);
        pnlEvaluationPanel.add(radKhongDat);

        JPanel pnlNotePanel = new JPanel(new BorderLayout(0, 5));
        pnlNotePanel.setBackground(Color.WHITE);

        txaGhiChu = taoTextArea();

        JScrollPane scrNote = new JScrollPane(txaGhiChu);
        scrNote.setBorder(BorderFactory.createLineBorder(maucotbang));
        scrNote.setPreferredSize(new Dimension(0, 80));

        pnlNotePanel.add(taoLabel("Ghi chú nghiệm thu / Lý do nếu Không đạt:"), BorderLayout.NORTH);
        pnlNotePanel.add(scrNote, BorderLayout.CENTER);

        JPanel pnlBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlBtnPanel.setBackground(Color.WHITE);

        btnLamMoi = taoButton("Làm mới", maumenu);
        btnLuuKcs = taoButton("Lưu kết quả KCS", mauxanh);
        btnLuuKcs.setPreferredSize(new Dimension(190, 40));

        pnlBtnPanel.add(btnLamMoi);
        pnlBtnPanel.add(btnLuuKcs);

        JPanel pnlCenterContainer = new JPanel(new BorderLayout(0, 10));
        pnlCenterContainer.setBackground(Color.WHITE);
        pnlCenterContainer.add(pnlInfoPanel, BorderLayout.NORTH);
        pnlCenterContainer.add(pnlChecklistPanel, BorderLayout.CENTER);

        JPanel pnlBottomContainer = new JPanel(new BorderLayout(0, 10));
        pnlBottomContainer.setBackground(Color.WHITE);
        pnlBottomContainer.add(pnlEvaluationPanel, BorderLayout.NORTH);
        pnlBottomContainer.add(pnlNotePanel, BorderLayout.CENTER);
        pnlBottomContainer.add(pnlBtnPanel, BorderLayout.SOUTH);

        pnlRightPanel.add(pnlCenterContainer, BorderLayout.CENTER);
        pnlRightPanel.add(pnlBottomContainer, BorderLayout.SOUTH);

        btnLuuKcs.addActionListener(e -> luuKetQuaKCS());
        btnLamMoi.addActionListener(e -> xoaForm());
    }

    private void hienThiChiTietXeDuocChon() {
        int row = tblXeKCS.getSelectedRow();

        if (row != -1) {
            String bienSo = tableModelXeKCS.getValueAt(row, 0).toString();
            String maPhieu = tableModelXeKCS.getValueAt(row, 1).toString();
            String ktv = tableModelXeKCS.getValueAt(row, 2).toString();

            txtBienSo.setText(bienSo);
            txtMaPhieu.setText(maPhieu);
            txtKtvThucHien.setText(ktv);

            if (bienSo.equals("51A-123.45")) {
                txtKhachHang.setText("Nguyễn Văn An");
            } else if (bienSo.equals("59A-456.78")) {
                txtKhachHang.setText("Trần Thị Bình");
            } else {
                txtKhachHang.setText("Lê Hoàng Nam");
            }
        }
    }

    private void luuKetQuaKCS() {
        int row = tblXeKCS.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn xe cần kiểm tra chất lượng từ danh sách!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (radDat.isSelected()) {
            tableModelXeKCS.setValueAt("Đạt - Chờ bàn giao", row, 3);

            JOptionPane.showMessageDialog(
                    this,
                    "N nghiệm thu KCS thành công!\nTrạng thái xe đã chuyển sang: ĐẠT (Đủ điều kiện bàn giao xe).",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            if (txaGhiChu.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng nhập lý do KHÔNG ĐẠT vào ô ghi chú nghiệm thu!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            tableModelXeKCS.setValueAt("Không đạt - Sửa lại", row, 3);

            JOptionPane.showMessageDialog(
                    this,
                    "Đã ghi nhận kết quả KHÔNG ĐẠT!\nYêu cầu sửa chữa đã được trả về cho KTV xử lý lại.",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        xoaForm();
    }

    private void xoaForm() {
        txtMaPhieu.setText("");
        txtBienSo.setText("");
        txtKhachHang.setText("");
        txtKtvThucHien.setText("");
        txaGhiChu.setText("");
        radDat.setSelected(true);
        tblXeKCS.clearSelection();
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
        new KiemTraChatLuong();
    }
}