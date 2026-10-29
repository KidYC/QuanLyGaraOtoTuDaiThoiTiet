package View.HeThong;

import DAO.NhanVien_DAO;
import Entity.NhanVien;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class QuanLyNhanVien extends JFrame implements ActionListener, MouseListener {
    private JPanel pnlContentPanel;
    private JPanel pnlHeaderPanel;
    private JPanel pnlSearchPanel;
    private JPanel pnlButtonPanel;
    private JTextField txtSearch;
    private JButton btnTim;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnPhanQuyen;
    private JLabel lblTitle;
    private JLabel lblSearch;
    private JTable tblTable;
    private JScrollPane scrScrollPane;
    private DefaultTableModel tableModel;
    private final NhanVien_DAO nhanVienDAO = new NhanVien_DAO();

    public QuanLyNhanVien() {
        setTitle("Quản lý nhân viên - Garage Management");
        setSize(1100, 680);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(241, 245, 249));
        setLayout(new BorderLayout(0, 15));

        pnlHeaderPanel = new JPanel(new BorderLayout());
        pnlHeaderPanel.setBackground(Color.WHITE);
        pnlHeaderPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));
        pnlHeaderPanel.setPreferredSize(new Dimension(1100, 75));

        lblTitle = new JLabel("QUẢN LÝ HỒ SƠ NHÂN VIÊN & PHÂN QUYỀN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(15, 23, 42));
        pnlHeaderPanel.add(lblTitle, BorderLayout.WEST);
        add(pnlHeaderPanel, BorderLayout.NORTH);

        pnlContentPanel = new JPanel(new BorderLayout(0, 15));
        pnlContentPanel.setBackground(new Color(241, 245, 249));
        pnlContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));

        pnlSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlSearchPanel.setBackground(new Color(241, 245, 249));

        lblSearch = new JLabel("Tìm kiếm:");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setPreferredSize(new Dimension(280, 40));

        btnTim = new JButton("Tìm kiếm");
        btnTim.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnTim.setForeground(Color.WHITE);
        btnTim.setBackground(new Color(37, 99, 235));
        btnTim.setFocusPainted(false);
        btnTim.addActionListener(this);

        pnlSearchPanel.add(lblSearch);
        pnlSearchPanel.add(txtSearch);
        pnlSearchPanel.add(btnTim);
        pnlContentPanel.add(pnlSearchPanel, BorderLayout.NORTH);
        String[] columns = {
                "Mã NV", "Họ tên", "SĐT", "Địa chỉ",
                "Ngày vào làm", "Tài khoản", "Chức vụ", "Trạng thái"
        };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblTable = new JTable(tableModel);
        tblTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblTable.setRowHeight(38);
        tblTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblTable.getTableHeader().setBackground(new Color(30, 41, 59));
        tblTable.getTableHeader().setForeground(Color.WHITE);
        tblTable.addMouseListener(this);

        scrScrollPane = new JScrollPane(tblTable);
        pnlContentPanel.add(scrScrollPane, BorderLayout.CENTER);

        pnlButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlButtonPanel.setBackground(new Color(241, 245, 249));

        btnThem = new JButton("Thêm NV");
        btnThem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnThem.setForeground(Color.WHITE);
        btnThem.setBackground(new Color(22, 163, 74));
        btnThem.setFocusPainted(false);
        btnThem.addActionListener(this);

        btnSua = new JButton("Sửa");
        btnSua.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSua.setForeground(Color.WHITE);
        btnSua.setBackground(new Color(37, 99, 235));
        btnSua.setFocusPainted(false);
        btnSua.addActionListener(this);

        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setBackground(new Color(220, 38, 38));
        btnXoa.setFocusPainted(false);
        btnXoa.addActionListener(this);

        btnPhanQuyen = new JButton("Phân quyền");
        btnPhanQuyen.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnPhanQuyen.setForeground(Color.WHITE);
        btnPhanQuyen.setBackground(new Color(124, 58, 237));
        btnPhanQuyen.setFocusPainted(false);
        btnPhanQuyen.addActionListener(this);

        pnlButtonPanel.add(btnThem);
        pnlButtonPanel.add(btnSua);
        pnlButtonPanel.add(btnXoa);
        pnlButtonPanel.add(btnPhanQuyen);
        pnlContentPanel.add(pnlButtonPanel, BorderLayout.SOUTH);

        add(pnlContentPanel, BorderLayout.CENTER);

        loadDataToTable();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThem) {
            moFormThem();
        } else if (e.getSource() == btnSua) {
            moFormSua();
        } else if (e.getSource() == btnXoa) {
            xoaNhanVien();
        } else if (e.getSource() == btnPhanQuyen) {
            moFormPhanQuyen();
        } else if (e.getSource() == btnTim) {
            timKiem();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == tblTable && e.getClickCount() == 2) {
            moFormSua();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        try {
            List<NhanVien> ds = nhanVienDAO.getAllNhanVien();
            for (NhanVien nv : ds) {
                tableModel.addRow(new Object[]{
                        nv.getMaNV(), nv.getHoTen(), nv.getSoDienThoai(),
                        nv.getDiaChi(), nv.getNgayVaoLam(), nv.getTenDangNhap(),
                        nv.getChucVu(), nv.getTrangThai()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi nạp dữ liệu: " + ex.getMessage(), "Lỗi SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateData(String ma, String hoTen, String sdt, String tenDN, String matKhau, Date ngayVaoLam) {
        if (ma != null && !ma.matches("^(LT|KTV|TK|KT)\\d{2,}$")) {
            JOptionPane.showMessageDialog(this, "Mã NV phải bắt đầu bằng LT, KTV, TK hoặc KT và theo sau bởi số!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (hoTen.trim().isEmpty() || !hoTen.matches("^[\\p{L}\\s]+$")) {
            JOptionPane.showMessageDialog(this, "Họ và tên không hợp lệ (không chứa số hay ký tự đặc biệt)!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!sdt.matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm 10 chữ số và bắt đầu bằng 0!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (tenDN != null && tenDN.length() < 4) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập phải từ 4 ký tự trở lên!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (matKhau != null && matKhau.length() < 6) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải từ 6 ký tự trở lên!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (ngayVaoLam == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày vào làm!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void moFormThem() {
        JDialog winDialog = new JDialog(this, "Thêm nhân viên mới", true);
        winDialog.setSize(500, 480);
        winDialog.setLocationRelativeTo(this);
        winDialog.setLayout(new BorderLayout());

        JPanel pnlPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        pnlPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JTextField txtMa = new JTextField();
        JTextField txtHoTen = new JTextField();
        JTextField txtSdt = new JTextField();
        JTextField txtDiaChi = new JTextField();
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        JTextField txtTaiKhoan = new JTextField();
        JTextField txtMatKhau = new JTextField();
        JComboBox<String> cmbVaiTro = new JComboBox<>(new String[]{
                "Lễ tân", "Kỹ thuật viên", "Thủ kho", "Kế toán"
        });

        pnlPanel.add(new JLabel("Mã nhân viên:"));
        pnlPanel.add(txtMa);
        pnlPanel.add(new JLabel("Họ và tên:"));
        pnlPanel.add(txtHoTen);
        pnlPanel.add(new JLabel("Số điện thoại:"));
        pnlPanel.add(txtSdt);
        pnlPanel.add(new JLabel("Địa chỉ:"));
        pnlPanel.add(txtDiaChi);
        pnlPanel.add(new JLabel("Ngày vào làm:"));
        pnlPanel.add(dateChooser);
        pnlPanel.add(new JLabel("Tên đăng nhập:"));
        pnlPanel.add(txtTaiKhoan);
        pnlPanel.add(new JLabel("Mật khẩu:"));
        pnlPanel.add(txtMatKhau);
        pnlPanel.add(new JLabel("Chức vụ:"));
        pnlPanel.add(cmbVaiTro);

        JButton btnLuu = new JButton("Lưu");
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBackground(new Color(22, 163, 74));
        btnLuu.setFocusPainted(false);

        btnLuu.addActionListener(e -> {
            String ma = txtMa.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String sdt = txtSdt.getText().trim();
            String diaChi = txtDiaChi.getText().trim();
            Date ngayVaoLam = dateChooser.getDate();
            String tenDN = txtTaiKhoan.getText().trim();
            String mk = txtMatKhau.getText().trim();
            String vaiTro = cmbVaiTro.getSelectedItem().toString();

            if (!validateData(ma, hoTen, sdt, tenDN, mk, ngayVaoLam)) {
                return;
            }

            NhanVien nv = new NhanVien(ma, hoTen, sdt, diaChi, ngayVaoLam, tenDN, vaiTro, "Đang làm việc");
            try {
                if (nhanVienDAO.themNhanVien(nv, mk)) {
                    JOptionPane.showMessageDialog(winDialog, "Thêm nhân viên thành công!");
                    loadDataToTable();
                    winDialog.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(winDialog, "Lỗi thêm SQL: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        winDialog.add(pnlPanel, BorderLayout.CENTER);
        winDialog.add(btnLuu, BorderLayout.SOUTH);
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
        String sdt = tableModel.getValueAt(row, 2) != null ? tableModel.getValueAt(row, 2).toString() : "";
        String diaChi = tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "";
        Date ngayVaoLam = (Date) tableModel.getValueAt(row, 4);
        String chucVu = tableModel.getValueAt(row, 6).toString();

        JDialog winDialog = new JDialog(this, "Sửa thông tin nhân viên", true);
        winDialog.setSize(500, 480);
        winDialog.setLocationRelativeTo(this);
        winDialog.setLayout(new BorderLayout());

        JPanel pnlPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        pnlPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JTextField txtMa = new JTextField();
        txtMa.setText(maNV);
        txtMa.setEditable(false);

        JTextField txtHoTen = new JTextField();
        txtHoTen.setText(hoTen);

        JTextField txtSdt = new JTextField();
        txtSdt.setText(sdt);

        JTextField txtDiaChi = new JTextField();
        txtDiaChi.setText(diaChi);

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDate(ngayVaoLam != null ? ngayVaoLam : new Date());

        JTextField txtChucVu = new JTextField();
        txtChucVu.setText(chucVu);
        txtChucVu.setEditable(false);

        pnlPanel.add(new JLabel("Mã nhân viên:"));
        pnlPanel.add(txtMa);
        pnlPanel.add(new JLabel("Họ và tên:"));
        pnlPanel.add(txtHoTen);
        pnlPanel.add(new JLabel("Số điện thoại:"));
        pnlPanel.add(txtSdt);
        pnlPanel.add(new JLabel("Địa chỉ:"));
        pnlPanel.add(txtDiaChi);
        pnlPanel.add(new JLabel("Ngày vào làm:"));
        pnlPanel.add(dateChooser);
        pnlPanel.add(new JLabel("Chức vụ:"));
        pnlPanel.add(txtChucVu);

        JButton btnLuu = new JButton("Lưu cập nhật");
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBackground(new Color(37, 99, 235));
        btnLuu.setFocusPainted(false);

        btnLuu.addActionListener(e -> {
            String hoTenMoi = txtHoTen.getText().trim();
            String sdtMoi = txtSdt.getText().trim();
            String diaChiMoi = txtDiaChi.getText().trim();
            Date ngayVaoLamMoi = dateChooser.getDate();

            if (!validateData(null, hoTenMoi, sdtMoi, null, null, ngayVaoLamMoi)) {
                return;
            }

            NhanVien nv = new NhanVien(maNV, hoTenMoi, sdtMoi, diaChiMoi, ngayVaoLamMoi, "", chucVu, "Đang làm việc");
            try {
                if (nhanVienDAO.capNhatNhanVien(nv)) {
                    JOptionPane.showMessageDialog(winDialog, "Cập nhật nhân viên thành công!");
                    loadDataToTable();
                    winDialog.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(winDialog, "Lỗi cập nhật SQL: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        winDialog.add(pnlPanel, BorderLayout.CENTER);
        winDialog.add(btnLuu, BorderLayout.SOUTH);
        winDialog.setVisible(true);
    }

    private void moFormPhanQuyen() {
        int row = tblTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần phân quyền!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maNV = tableModel.getValueAt(row, 0).toString();
        String hoTen = tableModel.getValueAt(row, 1).toString();
        String chucVuHienTai = tableModel.getValueAt(row, 6).toString();

        JDialog winDialog = new JDialog(this, "Phân quyền nhân viên", true);
        winDialog.setSize(450, 220);
        winDialog.setLocationRelativeTo(this);
        winDialog.setLayout(new BorderLayout());

        JPanel pnlPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JComboBox<String> cmbVaiTro = new JComboBox<>(new String[]{
                "Lễ tân", "Kỹ thuật viên", "Thủ kho", "Kế toán", "Admin"
        });
        cmbVaiTro.setSelectedItem(chucVuHienTai);

        pnlPanel.add(new JLabel("Mã NV - Họ tên:"));
        pnlPanel.add(new JLabel(maNV + " - " + hoTen));
        pnlPanel.add(new JLabel("Loại tài khoản / Vai trò:"));
        pnlPanel.add(cmbVaiTro);

        JButton btnLuu = new JButton("Lưu phân quyền");
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBackground(new Color(124, 58, 237));
        btnLuu.setFocusPainted(false);

        btnLuu.addActionListener(e -> {
            String vaiTroMoi = cmbVaiTro.getSelectedItem().toString();
            try {
                if (nhanVienDAO.capNhatPhanQuyen(maNV, vaiTroMoi)) {
                    JOptionPane.showMessageDialog(winDialog, "Cập nhật phân quyền thành công!");
                    loadDataToTable();
                    winDialog.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(winDialog, "Lỗi phân quyền SQL: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        winDialog.add(pnlPanel, BorderLayout.CENTER);
        winDialog.add(btnLuu, BorderLayout.SOUTH);
        winDialog.setVisible(true);
    }

    private void xoaNhanVien() {
        int row = tblTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
            return;
        }

        String maNV = tableModel.getValueAt(row, 0).toString();
        String chucVu = tableModel.getValueAt(row, 6).toString();

        if (JOptionPane.showConfirmDialog(this, "Xóa nhân viên " + maNV + "?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                if (nhanVienDAO.xoaNhanVien(maNV, chucVu)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadDataToTable();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi xóa SQL: " + ex.getMessage());
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
            String taiKhoan = tableModel.getValueAt(i, 5) != null
                    ? tableModel.getValueAt(i, 5).toString().toLowerCase()
                    : "";

            if (ma.contains(keyword) || hoTen.contains(keyword) || taiKhoan.contains(keyword)) {
                tblTable.setRowSelectionInterval(i, i);
                tblTable.scrollRectToVisible(tblTable.getCellRect(i, 0, true));
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new QuanLyNhanVien();
    }
}