
package View.KyThuatVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

import DAO.KhachHang_DAO;
import DAO.PhieuSuaChua_DAO;
import DAO.Xe_DAO;
import Entity.KhachHang;
import Entity.PhieuSuaChua;
import Entity.Xe;

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
    private JComboBox<String> cmbTrangThai;
    private JComboBox<String> cmbTienDo;
    private JProgressBar prgBar;
    private JTextArea txaGhiChu;
    private JButton btnLuuTienDo;
    private JButton btnLamMoi;
    private JButton btnQuayLai;

    private final Color maunen = new Color(244, 246, 248);
    private final Color maumenu = new Color(226, 232, 240);
    private final Color btnchinh = new Color(186, 230, 253);
    private final Color btnphu = new Color(219, 234, 254);
    private final Color chuchinh = new Color(17, 24, 39);
    private final Color maucotbang = new Color(186, 230, 253);

    private final PhieuSuaChua_DAO phieuSuaChuaDAO = new PhieuSuaChua_DAO();
    private final Xe_DAO xeDAO = new Xe_DAO();
    private final KhachHang_DAO khachHangDAO = new KhachHang_DAO();
    private List<PhieuSuaChua> dsPhieuSuaChua = new ArrayList<>();

    public CapNhatTienDoSuaChua() {
        setTitle("Công việc được phân công & Tiến độ - Garage Management");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(maunen);
        setLayout(new BorderLayout(0, 10));

        taoHeader();
        taoContent();
        loadDanhSachLenh();
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
        btnQuayLai.addActionListener(e -> dispose());

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

        add(splitPane, BorderLayout.CENTER);
    }

    private void taoDanhSachLenhPanel() {
        pnlLeftPanel = new JPanel(new BorderLayout(0, 12));
        pnlLeftPanel.setBackground(maunen);

        JPanel pnlSearchPanel = new JPanel(new BorderLayout(10, 0));
        pnlSearchPanel.setBackground(maunen);

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnTim = taoButton("Tìm lệnh", btnchinh);
        btnTim.addActionListener(e -> loadDanhSachLenh());

        pnlSearchPanel.add(txtSearch, BorderLayout.CENTER);
        pnlSearchPanel.add(btnTim, BorderLayout.EAST);

        String[] columns = {"Mã lệnh", "Biển số", "Trạng thái"};
        tableModelLenhSC = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tblLenhSC = taoTable(tableModelLenhSC);
        pnlLeftPanel.add(pnlSearchPanel, BorderLayout.NORTH);
        pnlLeftPanel.add(new JScrollPane(tblLenhSC), BorderLayout.CENTER);

        tblLenhSC.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hienThiChiTietLenhDuocChon();
            }
        });
    }

    private void taoChiTietTienDoPanel() {
        pnlRightPanel = new JPanel(new BorderLayout(0, 12));
        pnlRightPanel.setBackground(Color.WHITE);
        pnlRightPanel.setBorder(BorderFactory.createEmptyBorder(15, 18, 15, 18));

        JPanel pnlInfoPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        pnlInfoPanel.setBackground(Color.WHITE);
        pnlInfoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin lệnh sửa chữa"));

        txtMaLenh = taoTextFieldReadOnly();
        txtBienSo = taoTextFieldReadOnly();
        txtKhachHang = taoTextFieldReadOnly();

        pnlInfoPanel.add(taoLabel("Mã lệnh SC:"));
        pnlInfoPanel.add(taoLabel("Biển số xe:"));
        pnlInfoPanel.add(taoLabel("Khách hàng:"));
        pnlInfoPanel.add(txtMaLenh);
        pnlInfoPanel.add(txtBienSo);
        pnlInfoPanel.add(txtKhachHang);

        JPanel pnlUpdateFormPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlUpdateFormPanel.setBackground(Color.WHITE);
        pnlUpdateFormPanel.setBorder(BorderFactory.createTitledBorder("Cập nhật tiến độ"));

        cmbTrangThai = new JComboBox<>(new String[]{"Đang sửa chữa", "Tạm dừng sửa chữa", "Hoàn thành"});
        cmbTienDo = new JComboBox<>(new String[]{"0%", "20%", "40%", "60%", "80%", "100%"});

        prgBar = new JProgressBar(0, 100);
        prgBar.setValue(0);
        prgBar.setStringPainted(true);

        pnlUpdateFormPanel.add(taoLabel("Trạng thái chung:"));
        pnlUpdateFormPanel.add(cmbTrangThai);
        pnlUpdateFormPanel.add(taoLabel("Tiến độ tổng thể (%):"));
        pnlUpdateFormPanel.add(cmbTienDo);
        pnlUpdateFormPanel.add(taoLabel("Thanh tiến độ:"));
        pnlUpdateFormPanel.add(prgBar);

        txaGhiChu = new JTextArea(4, 20);

        JPanel pnlBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlBtnPanel.setBackground(Color.WHITE);

        btnLamMoi = taoButton("Làm mới", maumenu);
        btnLuuTienDo = taoButton("Lưu tiến độ", btnchinh);
        pnlBtnPanel.add(btnLamMoi);
        pnlBtnPanel.add(btnLuuTienDo);

        JPanel pnlBottomContainer = new JPanel(new BorderLayout(0, 10));
        pnlBottomContainer.setBackground(Color.WHITE);
        pnlBottomContainer.add(pnlUpdateFormPanel, BorderLayout.NORTH);
        pnlBottomContainer.add(new JScrollPane(txaGhiChu), BorderLayout.CENTER);
        pnlBottomContainer.add(pnlBtnPanel, BorderLayout.SOUTH);

        pnlRightPanel.add(pnlInfoPanel, BorderLayout.NORTH);
        pnlRightPanel.add(pnlBottomContainer, BorderLayout.CENTER);

        cmbTienDo.addActionListener(e -> {
            String val = cmbTienDo.getSelectedItem().toString().replace("%", "");
            prgBar.setValue(Integer.parseInt(val));
        });

        btnLuuTienDo.addActionListener(e -> luuTienDo());
        btnLamMoi.addActionListener(e -> xoaForm());
    }

    private void loadDanhSachLenh() {
        try {
            tableModelLenhSC.setRowCount(0);
            dsPhieuSuaChua = phieuSuaChuaDAO.getAllPhieuSuaChua();
            for (PhieuSuaChua psc : dsPhieuSuaChua) {
                tableModelLenhSC.addRow(new Object[]{psc.getMaPhieuSuaChua(), psc.getBienSo(), psc.getTrangThai()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải lệnh sửa chữa: " + ex.getMessage());
        }
    }

    private void hienThiChiTietLenhDuocChon() {
        int row = tblLenhSC.getSelectedRow();
        if (row != -1 && row < dsPhieuSuaChua.size()) {
            PhieuSuaChua psc = dsPhieuSuaChua.get(row);
            txtMaLenh.setText(psc.getMaPhieuSuaChua());
            txtBienSo.setText(psc.getBienSo());
            cmbTrangThai.setSelectedItem(psc.getTrangThai());

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

    private void luuTienDo() {
        int row = tblLenhSC.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lệnh sửa chữa cần cập nhật!");
            return;
        }

        String maLenh = txtMaLenh.getText();
        String trangThaiMoi = cmbTrangThai.getSelectedItem().toString();

        try {
            boolean ok = phieuSuaChuaDAO.capNhatTrangThai(maLenh, trangThaiMoi);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Cập nhật tiến độ thành công!");
                loadDanhSachLenh();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi lưu CSDL: " + ex.getMessage());
        }
    }

    private void xoaForm() {
        txtMaLenh.setText("");
        txtBienSo.setText("");
        txtKhachHang.setText("");
        txaGhiChu.setText("");
        prgBar.setValue(0);
        tblLenhSC.clearSelection();
    }

    private JTable taoTable(DefaultTableModel model) {
        JTable tblTable = new JTable(model);
        tblTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblTable.setRowHeight(36);
        tblTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return tblTable;
    }

    private JLabel taoLabel(String text) {
        JLabel lblLabel = new JLabel(text);
        lblLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return lblLabel;
    }

    private JTextField taoTextFieldReadOnly() {
        JTextField txtField = new JTextField();
        txtField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtField.setEditable(false);
        return txtField;
    }

    private JButton taoButton(String text, Color color) {
        JButton btnButton = new JButton(text);
        btnButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnButton.setBackground(color);
        return btnButton;
    }

    public static void main(String[] args) {
        new CapNhatTienDoSuaChua();
    }
}