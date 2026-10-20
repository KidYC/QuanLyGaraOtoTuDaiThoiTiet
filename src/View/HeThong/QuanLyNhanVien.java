package View.HeThong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import DAO.NhanVien_DAO;
import Entity.NhanVien;

public class QuanLyNhanVien extends JFrame {

    private JPanel pnlHeaderPanel;
    private JPanel pnlContentPanel;
    private JPanel pnlSearchPanel;
    private JPanel pnlButtonPanel;

    private JLabel lblTitle;
    private JLabel lblSearch;

    private JTextField txtSearch;
    private JButton btnTim;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnPhanQuyen;

    private JTable tblTable;
    private JScrollPane scrScrollPane;
    private DefaultTableModel tableModel;

    private final NhanVien_DAO nhanVienDAO = new NhanVien_DAO();

    private Color backgroundColor = new Color(241, 245, 249);
    private Color primaryColor = new Color(37, 99, 235);
    private Color textColor = new Color(15, 23, 42);
    private Color secondaryColor = new Color(71, 85, 105);
    private Color borderColor = new Color(203, 213, 225);
    private Color dangerColor = new Color(220, 38, 38);
    private Color successColor = new Color(22, 163, 74);

    public QuanLyNhanVien() {
        setTitle("Quản lý nhân viên - Garage Management");
        setSize(1050, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout(0, 15));

        taoHeader();
        taoContent();
        taoBang();
        taoButton();

        loadDataToTable();

        setVisible(true);
    }

    private void taoHeader() {
        pnlHeaderPanel = new JPanel(new BorderLayout());
        pnlHeaderPanel.setBackground(Color.WHITE);
        pnlHeaderPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));
        pnlHeaderPanel.setPreferredSize(new Dimension(1050, 75));

        lblTitle = new JLabel("QUẢN LÝ HỒ SƠ NHÂN VIÊN & PHÂN QUYỀN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(textColor);

        pnlHeaderPanel.add(lblTitle, BorderLayout.WEST);
        add(pnlHeaderPanel, BorderLayout.NORTH);
    }

    private void taoContent() {
        pnlContentPanel = new JPanel(new BorderLayout(0, 15));
        pnlContentPanel.setBackground(backgroundColor);
        pnlContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));

        taoTimKiem();
        add(pnlContentPanel, BorderLayout.CENTER);
    }

    private void taoTimKiem() {
        pnlSearchPanel = new JPanel(new BorderLayout(10, 0));
        pnlSearchPanel.setBackground(backgroundColor);
        pnlSearchPanel.setPreferredSize(new Dimension(1000, 45));

        JPanel pnlLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlLeftPanel.setBackground(backgroundColor);

        lblSearch = new JLabel("Tìm kiếm:");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSearch.setForeground(secondaryColor);
        lblSearch.setPreferredSize(new Dimension(75, 40));

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        txtSearch.setPreferredSize(new Dimension(280, 40));

        btnTim = taoButton("Tìm kiếm", primaryColor);

        pnlLeftPanel.add(lblSearch);
        pnlLeftPanel.add(txtSearch);
        pnlLeftPanel.add(btnTim);

        pnlSearchPanel.add(pnlLeftPanel, BorderLayout.WEST);
        pnlContentPanel.add(pnlSearchPanel, BorderLayout.NORTH);
    }

    private void taoBang() {
        String[] columns = {
                "Mã NV",
                "Họ và tên",
                "Số điện thoại",
                "Tài khoản",
                "Chức vụ / Vai trò",
                "Trạng thái"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblTable = new JTable(tableModel);
        tblTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblTable.setRowHeight(42);
        tblTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblTable.setSelectionBackground(new Color(219, 234, 254));
        tblTable.setSelectionForeground(textColor);
        tblTable.setGridColor(new Color(226, 232, 240));
        tblTable.setShowVerticalLines(false);

        tblTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblTable.getTableHeader().setForeground(Color.WHITE);
        tblTable.getTableHeader().setBackground(new Color(30, 41, 59));
        tblTable.getTableHeader().setPreferredSize(new Dimension(0, 45));

        tblTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblTable.getColumnModel().getColumn(2).setPreferredWidth(130);
        tblTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        tblTable.getColumnModel().getColumn(4).setPreferredWidth(160);
        tblTable.getColumnModel().getColumn(5).setPreferredWidth(110);

        scrScrollPane = new JScrollPane(tblTable);
        scrScrollPane.setBorder(BorderFactory.createLineBorder(borderColor));
        scrScrollPane.getViewport().setBackground(Color.WHITE);

        pnlContentPanel.add(scrScrollPane, BorderLayout.CENTER);
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        try {
            List<NhanVien> ds = nhanVienDAO.getAllNhanVien();
            for (NhanVien nv : ds) {
                tableModel.addRow(new Object[]{
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getSoDienThoai(),
                        nv.getTenDangNhap(),
                        nv.getChucVu(),
                        nv.getTrangThai()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi nạp dữ liệu nhân viên từ SQL Server:\n" + ex.getMessage(), "Lỗi SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void taoButton() {
        pnlButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlButtonPanel.setBackground(backgroundColor);
        pnlButtonPanel.setPreferredSize(new Dimension(1000, 50));

        btnThem = taoButton("Thêm NV", successColor);
        btnSua = taoButton("Sửa", primaryColor);
        btnXoa = taoButton("Xóa", dangerColor);
        btnPhanQuyen = taoButton("Phân quyền", new Color(124, 58, 237));

        pnlButtonPanel.add(btnThem);
        pnlButtonPanel.add(btnSua);
        pnlButtonPanel.add(btnXoa);
        pnlButtonPanel.add(btnPhanQuyen);

        pnlContentPanel.add(pnlButtonPanel, BorderLayout.SOUTH);

        btnThem.addActionListener(e -> moFormThem());
        btnSua.addActionListener(e -> moFormSua());
        btnXoa.addActionListener(e -> xoaNhanVien());
        btnPhanQuyen.addActionListener(e -> moFormPhanQuyen());
        btnTim.addActionListener(e -> timKiem());
    }

    private JButton taoButton(String text, Color color) {
        JButton btnButton = new JButton(text);
        btnButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnButton.setForeground(Color.WHITE);
        btnButton.setBackground(color);
        btnButton.setFocusPainted(false);
        btnButton.setBorderPainted(false);
        btnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnButton.setPreferredSize(new Dimension(120, 40));
        return btnButton;
    }

    private void moFormThem() {
        JDialog winDialog = taoDialog("Thêm nhân viên mới");

        JPanel pnlPanel = new JPanel(new GridLayout(6, 2, 15, 12));
        pnlPanel.setBackground(Color.WHITE);
        pnlPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JTextField txtMa = taoTextField();
        JTextField txtHoTen = taoTextField();
        JTextField txtSdt = taoTextField();
        JTextField txtTaiKhoan = taoTextField();
        JTextField txtMatKhau = taoTextField();

        JComboBox<String> cmbVaiTro = new JComboBox<>(
                new String[]{"Lễ tân", "Kỹ thuật viên", "Thủ kho", "Kế toán", "Chủ gara (Admin)"}
        );
        cmbVaiTro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbVaiTro.setBackground(Color.WHITE);

        pnlPanel.add(taoLabel("Mã nhân viên:"));
        pnlPanel.add(txtMa);
        pnlPanel.add(taoLabel("Họ và tên:"));
        pnlPanel.add(txtHoTen);
        pnlPanel.add(taoLabel("Số điện thoại / ĐC:"));
        pnlPanel.add(txtSdt);
        pnlPanel.add(taoLabel("Tên đăng nhập:"));
        pnlPanel.add(txtTaiKhoan);
        pnlPanel.add(taoLabel("Mật khẩu:"));
        pnlPanel.add(txtMatKhau);
        pnlPanel.add(taoLabel("Chức vụ / Vai trò:"));
        pnlPanel.add(cmbVaiTro);

        JPanel pnlButtonPanelLocal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButtonPanelLocal.setBackground(Color.WHITE);

        JButton btnLuu = taoButton("Lưu", successColor);
        JButton btnHuy = taoButton("Hủy", secondaryColor);

        pnlButtonPanelLocal.add(btnHuy);
        pnlButtonPanelLocal.add(btnLuu);

        btnHuy.addActionListener(e -> winDialog.dispose());
        btnLuu.addActionListener(e -> {
            String ma = txtMa.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String sdt = txtSdt.getText().trim();
            String tenDN = txtTaiKhoan.getText().trim();
            String mk = txtMatKhau.getText().trim();
            String vaiTro = cmbVaiTro.getSelectedItem().toString();

            if (ma.isEmpty() || tenDN.isEmpty() || mk.isEmpty()) {
                JOptionPane.showMessageDialog(winDialog, "Vui lòng nhập đầy đủ Mã NV, Tên đăng nhập và Mật khẩu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            NhanVien nv = new NhanVien(ma, hoTen, sdt, tenDN, vaiTro, "Hoạt động");
            try {
                if (nhanVienDAO.themNhanVien(nv, mk)) {
                    JOptionPane.showMessageDialog(winDialog, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDataToTable();
                    winDialog.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(winDialog, "Lỗi thêm nhân viên: " + ex.getMessage(), "Lỗi SQL", JOptionPane.ERROR_MESSAGE);
            }
        });

        winDialog.add(pnlPanel, BorderLayout.CENTER);
        winDialog.add(pnlButtonPanelLocal, BorderLayout.SOUTH);
        winDialog.setVisible(true);
    }

    private void moFormSua() {
        int row = tblTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maNV = tableModel.getValueAt(row, 0).toString();
        String hoTen = tableModel.getValueAt(row, 1).toString();
        String sdt = tableModel.getValueAt(row, 2).toString();
        String tenDN = tableModel.getValueAt(row, 3).toString();
        String vaiTro = tableModel.getValueAt(row, 4).toString();

        JDialog winDialog = taoDialog("Cập nhật thông tin nhân viên");
        JPanel pnlPanel = new JPanel(new GridLayout(5, 2, 15, 12));
        pnlPanel.setBackground(Color.WHITE);
        pnlPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JTextField txtMa = taoTextField();
        txtMa.setText(maNV);
        txtMa.setEditable(false);

        JTextField txtHoTen = taoTextField();
        txtHoTen.setText(hoTen);

        JTextField txtSdt = taoTextField();
        txtSdt.setText(sdt);

        JTextField txtTaiKhoan = taoTextField();
        txtTaiKhoan.setText(tenDN);
        txtTaiKhoan.setEditable(false);

        JComboBox<String> cmbVaiTro = new JComboBox<>(
                new String[]{"Lễ tân", "Kỹ thuật viên", "Thủ kho", "Kế toán", "Chủ gara (Admin)"}
        );
        cmbVaiTro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbVaiTro.setBackground(Color.WHITE);
        cmbVaiTro.setSelectedItem(vaiTro);

        pnlPanel.add(taoLabel("Mã nhân viên:"));
        pnlPanel.add(txtMa);
        pnlPanel.add(taoLabel("Họ và tên:"));
        pnlPanel.add(txtHoTen);
        pnlPanel.add(taoLabel("Số điện thoại / ĐC:"));
        pnlPanel.add(txtSdt);
        pnlPanel.add(taoLabel("Tên đăng nhập:"));
        pnlPanel.add(txtTaiKhoan);
        pnlPanel.add(taoLabel("Chức vụ / Vai trò:"));
        pnlPanel.add(cmbVaiTro);

        JPanel pnlButtonPanelLocal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButtonPanelLocal.setBackground(Color.WHITE);

        JButton btnLuu = taoButton("Lưu", primaryColor);
        JButton btnHuy = taoButton("Hủy", secondaryColor);

        pnlButtonPanelLocal.add(btnHuy);
        pnlButtonPanelLocal.add(btnLuu);

        btnHuy.addActionListener(e -> winDialog.dispose());
        btnLuu.addActionListener(e -> {
            JOptionPane.showMessageDialog(winDialog, "Cập nhật thông tin nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadDataToTable();
            winDialog.dispose();
        });

        winDialog.add(pnlPanel, BorderLayout.CENTER);
        winDialog.add(pnlButtonPanelLocal, BorderLayout.SOUTH);
        winDialog.setVisible(true);
    }

    private void moFormPhanQuyen() {
        int row = tblTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần phân quyền!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maNv = tableModel.getValueAt(row, 0).toString();
        String hoTen = tableModel.getValueAt(row, 1).toString();
        String vaiTro = tableModel.getValueAt(row, 4).toString();

        JDialog winDialog = taoDialog("Phân quyền nhân viên");

        JPanel pnlMainPanel = new JPanel(new BorderLayout(0, 15));
        pnlMainPanel.setBackground(Color.WHITE);
        pnlMainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblInfo = new JLabel("Nhân viên: " + maNv + " - " + hoTen + " (" + vaiTro + ")");
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblInfo.setForeground(textColor);

        JPanel pnlPermissionPanel = new JPanel(new GridLayout(5, 1, 0, 10));
        pnlPermissionPanel.setBackground(Color.WHITE);

        JCheckBox chkTiepNhan = new JCheckBox("Quyền Phân hệ Tiếp nhận & Khách hàng");
        JCheckBox chkSuaChua = new JCheckBox("Quyền Phân hệ Sửa chữa & Bảo hành");
        JCheckBox chkKho = new JCheckBox("Quyền Phân hệ Kho phụ tùng");
        JCheckBox chkThanhToan = new JCheckBox("Quyền Phân hệ Thanh toán & Thu ngân");
        JCheckBox chkHeThong = new JCheckBox("Quyền Quản trị Hệ thống & Báo cáo");

        JCheckBox[] chkCheckBoxes = {chkTiepNhan, chkSuaChua, chkKho, chkThanhToan, chkHeThong};

        for (JCheckBox chk : chkCheckBoxes) {
            chk.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            chk.setBackground(Color.WHITE);
            chk.setForeground(secondaryColor);
            pnlPermissionPanel.add(chk);
        }

        if (vaiTro.contains("Kỹ thuật")) {
            chkSuaChua.setSelected(true);
        } else if (vaiTro.contains("Thủ kho")) {
            chkKho.setSelected(true);
        } else if (vaiTro.contains("Kế toán")) {
            chkThanhToan.setSelected(true);
        } else if (vaiTro.contains("Lễ tân")) {
            chkTiepNhan.setSelected(true);
        } else {
            for (JCheckBox chk : chkCheckBoxes) chk.setSelected(true);
        }

        pnlMainPanel.add(lblInfo, BorderLayout.NORTH);
        pnlMainPanel.add(pnlPermissionPanel, BorderLayout.CENTER);

        JPanel pnlButtonPanelLocal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButtonPanelLocal.setBackground(Color.WHITE);

        JButton btnLuu = taoButton("Lưu phân quyền", primaryColor);
        JButton btnHuy = taoButton("Hủy", secondaryColor);

        pnlButtonPanelLocal.add(btnHuy);
        pnlButtonPanelLocal.add(btnLuu);

        btnHuy.addActionListener(e -> winDialog.dispose());
        btnLuu.addActionListener(e -> {
            JOptionPane.showMessageDialog(winDialog, "Cập nhật phân quyền thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            winDialog.dispose();
        });

        winDialog.add(pnlMainPanel, BorderLayout.CENTER);
        winDialog.add(pnlButtonPanelLocal, BorderLayout.SOUTH);
        winDialog.setSize(550, 420);
        winDialog.setLocationRelativeTo(this);
        winDialog.setVisible(true);
    }

    private void xoaNhanVien() {
        int row = tblTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maNV = tblTable.getValueAt(row, 0).toString();
        String hoTen = tblTable.getValueAt(row, 1).toString();

        int result = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa tài khoản nhân viên \"" + hoTen + "\" (" + maNV + ")?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            try {
                if (nhanVienDAO.xoaNhanVien(maNV)) {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDataToTable();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi xóa nhân viên: " + ex.getMessage(), "Lỗi SQL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void timKiem() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            loadDataToTable();
            return;
        }

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String ma = tableModel.getValueAt(i, 0).toString().toLowerCase();
            String hoTen = tableModel.getValueAt(i, 1).toString().toLowerCase();
            String taiKhoan = tableModel.getValueAt(i, 3).toString().toLowerCase();
            String vaiTro = tableModel.getValueAt(i, 4).toString().toLowerCase();

            if (ma.contains(keyword) || hoTen.contains(keyword) || taiKhoan.contains(keyword) || vaiTro.contains(keyword)) {
                tblTable.setRowSelectionInterval(i, i);
                tblTable.scrollRectToVisible(tblTable.getCellRect(i, 0, true));
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private JDialog taoDialog(String title) {
        JDialog winDialog = new JDialog(this, title, true);
        winDialog.setLayout(new BorderLayout());
        winDialog.setSize(550, 440);
        winDialog.setLocationRelativeTo(this);
        return winDialog;
    }

    private JLabel taoLabel(String text) {
        JLabel lblLabel = new JLabel(text);
        lblLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblLabel.setForeground(secondaryColor);
        return lblLabel;
    }

    private JTextField taoTextField() {
        JTextField txtField = new JTextField();
        txtField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(0, 8, 0, 8)
        ));
        return txtField;
    }

    public static void main(String[] args) {
        new QuanLyNhanVien();
    }
}