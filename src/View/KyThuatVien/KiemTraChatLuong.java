
package View.KyThuatVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

import DAO.KhachHang_DAO;
import DAO.PhieuSuaChua_DAO;
import DAO.Xe_DAO;
import Entity.KhachHang;
import Entity.PhieuSuaChua;
import Entity.Xe;

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
    private JRadioButton radDat;
    private JRadioButton radKhongDat;
    private ButtonGroup danhGiaGroup;
    private JTextArea txaGhiChu;
    private JButton btnLuuKcs;
    private JButton btnLamMoi;
    private JButton btnQuayLai;

    private final Color maunen = new Color(244, 246, 248);
    private final Color maumenu = new Color(226, 232, 240);
    private final Color btnchinh = new Color(186, 230, 253);
    private final Color btnphu = new Color(219, 234, 254);
    private final Color chuchinh = new Color(17, 24, 39);
    private final Color maudo = new Color(220, 38, 38);
    private final Color mauxanh = new Color(34, 197, 94);

    private final PhieuSuaChua_DAO phieuSuaChuaDAO = new PhieuSuaChua_DAO();
    private final Xe_DAO xeDAO = new Xe_DAO();
    private final KhachHang_DAO khachHangDAO = new KhachHang_DAO();
    private List<PhieuSuaChua> dsPhieuSuaChua = new ArrayList<>();

    public KiemTraChatLuong() {
        setTitle("Kiểm tra chất lượng (KCS) - Garage Management");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(maunen);
        setLayout(new BorderLayout(0, 10));

        taoHeader();
        taoContent();
        loadDanhSachKCS();
        setVisible(true);
    }

    private void taoHeader() {
        pnlHeaderPanel = new JPanel(new BorderLayout());
        pnlHeaderPanel.setBackground(Color.WHITE);
        pnlHeaderPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JLabel lblTitle = new JLabel("KIỂM TRA CHẤT LƯỢNG XE SAU SỬA CHỮA (KCS)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));

        btnQuayLai = taoButton("Quay lại", btnphu);
        btnQuayLai.addActionListener(e -> dispose());

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

        add(splitPane, BorderLayout.CENTER);
    }

    private void taoDanhSachXeKCSPanel() {
        pnlLeftPanel = new JPanel(new BorderLayout(0, 12));
        pnlLeftPanel.setBackground(maunen);

        JPanel pnlSearchPanel = new JPanel(new BorderLayout(10, 0));
        txtSearch = new JTextField();
        btnTim = taoButton("Tìm xe", btnchinh);
        btnTim.addActionListener(e -> loadDanhSachKCS());

        pnlSearchPanel.add(txtSearch, BorderLayout.CENTER);
        pnlSearchPanel.add(btnTim, BorderLayout.EAST);

        String[] columns = {"Biển số", "Mã phiếu", "KTV phụ trách", "Trạng thái KCS"};
        tableModelXeKCS = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tblXeKCS = taoTable(tableModelXeKCS);
        pnlLeftPanel.add(pnlSearchPanel, BorderLayout.NORTH);
        pnlLeftPanel.add(new JScrollPane(tblXeKCS), BorderLayout.CENTER);

        tblXeKCS.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hienThiChiTietXeDuocChon();
            }
        });
    }

    private void taoFormDanhGiaPanel() {
        pnlRightPanel = new JPanel(new BorderLayout(0, 12));
        pnlRightPanel.setBackground(Color.WHITE);

        JPanel pnlInfoPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        pnlInfoPanel.setBackground(Color.WHITE);

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

        JPanel pnlEvaluationPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlEvaluationPanel.setBackground(Color.WHITE);

        radDat = new JRadioButton("ĐẠT (Chuyển chờ bàn giao)");
        radDat.setForeground(mauxanh);
        radDat.setSelected(true);

        radKhongDat = new JRadioButton("KHÔNG ĐẠT (Yêu cầu sửa lại)");
        radKhongDat.setForeground(maudo);

        danhGiaGroup = new ButtonGroup();
        danhGiaGroup.add(radDat);
        danhGiaGroup.add(radKhongDat);

        pnlEvaluationPanel.add(radDat);
        pnlEvaluationPanel.add(radKhongDat);

        txaGhiChu = new JTextArea(5, 20);

        JPanel pnlBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlBtnPanel.setBackground(Color.WHITE);

        btnLamMoi = taoButton("Làm mới", maumenu);
        btnLuuKcs = taoButton("Lưu kết quả KCS", mauxanh);
        pnlBtnPanel.add(btnLamMoi);
        pnlBtnPanel.add(btnLuuKcs);

        JPanel pnlCenterContainer = new JPanel(new BorderLayout(0, 10));
        pnlCenterContainer.setBackground(Color.WHITE);
        pnlCenterContainer.add(pnlInfoPanel, BorderLayout.NORTH);
        pnlCenterContainer.add(pnlEvaluationPanel, BorderLayout.CENTER);

        JPanel pnlBottomContainer = new JPanel(new BorderLayout(0, 10));
        pnlBottomContainer.setBackground(Color.WHITE);
        pnlBottomContainer.add(new JScrollPane(txaGhiChu), BorderLayout.CENTER);
        pnlBottomContainer.add(pnlBtnPanel, BorderLayout.SOUTH);

        pnlRightPanel.add(pnlCenterContainer, BorderLayout.NORTH);
        pnlRightPanel.add(pnlBottomContainer, BorderLayout.CENTER);

        btnLuuKcs.addActionListener(e -> luuKetQuaKCS());
        btnLamMoi.addActionListener(e -> xoaForm());
    }

    private void loadDanhSachKCS() {
        try {
            tableModelXeKCS.setRowCount(0);
            dsPhieuSuaChua = phieuSuaChuaDAO.getAllPhieuSuaChua();
            for (PhieuSuaChua psc : dsPhieuSuaChua) {
                tableModelXeKCS.addRow(new Object[]{psc.getBienSo(), psc.getMaPhieuSuaChua(), psc.getMaNhanVien(), psc.getTrangThai()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách KCS: " + ex.getMessage());
        }
    }

    private void hienThiChiTietXeDuocChon() {
        int row = tblXeKCS.getSelectedRow();
        if (row != -1 && row < dsPhieuSuaChua.size()) {
            PhieuSuaChua psc = dsPhieuSuaChua.get(row);
            txtMaPhieu.setText(psc.getMaPhieuSuaChua());
            txtBienSo.setText(psc.getBienSo());
            txtKtvThucHien.setText(psc.getMaNhanVien());

            try {
                Xe xe = xeDAO.getXeByBienSo(psc.getBienSo());
                if (xe != null) {
                    KhachHang kh = khachHangDAO.getKhachHangByMa(xe.getMaKhachHang());
                    txtKhachHang.setText((kh != null) ? kh.getHoTen() : "");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void luuKetQuaKCS() {
        int row = tblXeKCS.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn xe cần kiểm tra chất lượng!");
            return;
        }

        String maPhieu = txtMaPhieu.getText();
        String trangThaiKCS = radDat.isSelected() ? "Đạt - Chờ bàn giao" : "Không đạt - Sửa lại";

        try {
            boolean ok = phieuSuaChuaDAO.capNhatTrangThai(maPhieu, trangThaiKCS);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Lưu kết quả KCS thành công!");
                loadDanhSachKCS();
                xoaForm();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi lưu CSDL: " + ex.getMessage());
        }
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
        return tblTable;
    }

    private JLabel taoLabel(String text) {
        return new JLabel(text);
    }

    private JTextField taoTextFieldReadOnly() {
        JTextField txtField = new JTextField();
        txtField.setEditable(false);
        return txtField;
    }

    private JButton taoButton(String text, Color color) {
        JButton btnButton = new JButton(text);
        btnButton.setBackground(color);
        return btnButton;
    }

//    public static void main(String[] args) {
//        new KiemTraChatLuong();
//    }
}