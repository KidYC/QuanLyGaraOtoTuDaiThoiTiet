package View.LeTan;

import DAO.KhachHang_DAO;
import Entity.KhachHang;
import View.HeThong.TrangChuHeThong;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class ManHinhQuanLyKhachHang extends JFrame implements ActionListener, MouseListener {

    private DefaultTableModel tableModel;
    private JTable table;
    private TableRowSorter<DefaultTableModel> rowSorter;

    private JTextField txtTimKH;

    private JButton btnLamMoi;
    private JButton btnThemKH;
    private JButton btnSuaKH;
    private JButton btnXoaKH;
    private JButton btnQuayLai;

    private final KhachHang_DAO khachHangDAO = new KhachHang_DAO();

    public ManHinhQuanLyKhachHang() {
        setTitle("QUẢN LÝ KHÁCH HÀNG");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();
        loadDataToTable();
        xuLyTimKiem();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        // pNorth - Tiêu đề
        JLabel lblTitle = new JLabel("QUẢN LÝ THÔNG TIN KHÁCH HÀNG");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // pCenter - Bảng danh sách
        JPanel pCenter = new JPanel(new BorderLayout());
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        tableModel = new DefaultTableModel(
                new String[]{"Mã Khách Hàng", "Họ Tên", "Số Điện Thoại", "Địa Chỉ", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(32);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 35));

        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        pCenter.add(scrollPane, BorderLayout.CENTER);
        add(pCenter, BorderLayout.CENTER);

        // pSouth - Tìm kiếm & Các nút chức năng
        JPanel pSouth = new JPanel();
        pSouth.setLayout(new BoxLayout(pSouth, BoxLayout.Y_AXIS));
        pSouth.setBackground(new Color(245, 245, 245));
        pSouth.setBorder(new EmptyBorder(10, 20, 20, 20));

        // Thanh tìm kiếm
        JPanel pTimKiem = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pTimKiem.setBackground(new Color(245, 245, 245));

        JLabel lblTim = new JLabel("Tìm Kiếm (Mã, Tên, SĐT):");
        lblTim.setFont(new Font("Arial", Font.BOLD, 13));
        pTimKiem.add(lblTim);

        txtTimKH = new JTextField(22);
        txtTimKH.setFont(new Font("Arial", Font.PLAIN, 14));
        pTimKiem.add(txtTimKH);

        btnLamMoi = taoButton("Làm mới", new Color(149, 165, 166), 110, 32);
        pTimKiem.add(btnLamMoi);

        // Thanh nút CRUD
        JPanel pChucNang = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pChucNang.setBackground(new Color(245, 245, 245));

        btnThemKH = taoButton("Thêm Khách Hàng", new Color(46, 204, 113), 160, 38);
        btnSuaKH = taoButton("Sửa Khách Hàng", new Color(52, 152, 219), 160, 38);
        btnXoaKH = taoButton("Xóa Khách Hàng", new Color(231, 76, 60), 160, 38);
        btnQuayLai = taoButton("Quay Lại", new Color(108, 122, 137), 120, 38);

        pChucNang.add(btnThemKH);
        pChucNang.add(btnSuaKH);
        pChucNang.add(btnXoaKH);
        pChucNang.add(btnQuayLai);

        pSouth.add(pTimKiem);
        pSouth.add(pChucNang);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private JButton taoButton(String text, Color bg, int width, int height) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void initListener() {
        btnLamMoi.addActionListener(this);
        btnThemKH.addActionListener(this);
        btnSuaKH.addActionListener(this);
        btnXoaKH.addActionListener(this);
        btnQuayLai.addActionListener(this);
        table.addMouseListener(this);
    }

    // Đổ dữ liệu từ SQL Server vào JTable
    public void loadDataToTable() {
        tableModel.setRowCount(0);
        try {
            List<KhachHang> list = khachHangDAO.getAllKhachHang();
            for (KhachHang kh : list) {
                tableModel.addRow(new Object[]{
                        kh.getMaKhachHang(),
                        kh.getHoTen(),
                        kh.getSoDienThoai(),
                        kh.getDiaChi(),
                        kh.getEmail()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnLamMoi) {
            xuLyLamMoi();
        } else if (source == btnThemKH) {
            moDialogThem();
        } else if (source == btnSuaKH) {
            moDialogSua();
        } else if (source == btnXoaKH) {
            xuLyXoa();
        } else if (source == btnQuayLai) {
//            TrangChuHeThong Home = new TrangChuHeThong();
//            Home.setVisible(true);
            this.dispose();
        }
    }

    // 1. CHỨC NĂNG THÊM KHÁCH HÀNG
    private void moDialogThem() {
        JDialog dlg = new JDialog(this, "Thêm Khách Hàng Mới", true);
        dlg.setSize(420, 320);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout());

        JPanel pForm = new JPanel(new GridLayout(4, 2, 10, 12));
        pForm.setBorder(new EmptyBorder(20, 20, 10, 20));

        JTextField txtHoTen = new JTextField();
        JTextField txtSdt = new JTextField();
        JTextField txtDiaChi = new JTextField();
        JTextField txtEmail = new JTextField();

        pForm.add(new JLabel("Họ và tên (*):"));
        pForm.add(txtHoTen);
        pForm.add(new JLabel("Số điện thoại (*):"));
        pForm.add(txtSdt);
        pForm.add(new JLabel("Địa chỉ:"));
        pForm.add(txtDiaChi);
        pForm.add(new JLabel("Email:"));
        pForm.add(txtEmail);

        JPanel pBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnLuu = taoButton("Lưu", new Color(46, 204, 113), 100, 35);
        JButton btnHuy = taoButton("Hủy", new Color(149, 165, 166), 90, 35);

        pBtns.add(btnHuy);
        pBtns.add(btnLuu);

        btnHuy.addActionListener(ev -> dlg.dispose());
        btnLuu.addActionListener(ev -> {
            String hoTen = txtHoTen.getText().trim();
            String sdt = txtSdt.getText().trim();
            String diaChi = txtDiaChi.getText().trim();
            String email = txtEmail.getText().trim();

            if (hoTen.isEmpty() || sdt.isEmpty()) {
                JOptionPane.showMessageDialog(dlg, "Vui lòng nhập đầy đủ Họ tên và Số điện thoại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            KhachHang kh = new KhachHang(null, hoTen, sdt, diaChi, email);
            try {
                if (khachHangDAO.themKhachHang(kh)) {
                    JOptionPane.showMessageDialog(dlg, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDataToTable();
                    dlg.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dlg, "Lỗi thêm khách hàng: " + ex.getMessage(), "Lỗi SQL", JOptionPane.ERROR_MESSAGE);
            }
        });

        dlg.add(pForm, BorderLayout.CENTER);
        dlg.add(pBtns, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    // 2. CHỨC NĂNG SỬA KHÁCH HÀNG
    private void moDialogSua() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        String maKH = tableModel.getValueAt(modelRow, 0).toString();
        String hoTenCu = tableModel.getValueAt(modelRow, 1).toString();
        String sdtCu = tableModel.getValueAt(modelRow, 2).toString();
        String diaChiCu = tableModel.getValueAt(modelRow, 3) != null ? tableModel.getValueAt(modelRow, 3).toString() : "";
        String emailCu = tableModel.getValueAt(modelRow, 4) != null ? tableModel.getValueAt(modelRow, 4).toString() : "";

        JDialog dlg = new JDialog(this, "Cập Nhật Thông Tin Khách Hàng", true);
        dlg.setSize(420, 350);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout());

        JPanel pForm = new JPanel(new GridLayout(5, 2, 10, 12));
        pForm.setBorder(new EmptyBorder(20, 20, 10, 20));

        JTextField txtMa = new JTextField(maKH);
        txtMa.setEditable(false);
        JTextField txtHoTen = new JTextField(hoTenCu);
        JTextField txtSdt = new JTextField(sdtCu);
        JTextField txtDiaChi = new JTextField(diaChiCu);
        JTextField txtEmail = new JTextField(emailCu);

        pForm.add(new JLabel("Mã khách hàng:"));
        pForm.add(txtMa);
        pForm.add(new JLabel("Họ và tên (*):"));
        pForm.add(txtHoTen);
        pForm.add(new JLabel("Số điện thoại (*):"));
        pForm.add(txtSdt);
        pForm.add(new JLabel("Địa chỉ:"));
        pForm.add(txtDiaChi);
        pForm.add(new JLabel("Email:"));
        pForm.add(txtEmail);

        JPanel pBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnLuu = taoButton("Lưu thay đổi", new Color(52, 152, 219), 120, 35);
        JButton btnHuy = taoButton("Hủy", new Color(149, 165, 166), 90, 35);

        pBtns.add(btnHuy);
        pBtns.add(btnLuu);

        btnHuy.addActionListener(ev -> dlg.dispose());
        btnLuu.addActionListener(ev -> {
            String hoTen = txtHoTen.getText().trim();
            String sdt = txtSdt.getText().trim();
            String diaChi = txtDiaChi.getText().trim();
            String email = txtEmail.getText().trim();

            if (hoTen.isEmpty() || sdt.isEmpty()) {
                JOptionPane.showMessageDialog(dlg, "Vui lòng nhập đầy đủ Họ tên và Số điện thoại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            KhachHang kh = new KhachHang(maKH, hoTen, sdt, diaChi, email);
            try {
                if (khachHangDAO.capNhatKhachHang(kh)) {
                    JOptionPane.showMessageDialog(dlg, "Cập nhật thông tin thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDataToTable();
                    dlg.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dlg, "Lỗi cập nhật khách hàng: " + ex.getMessage(), "Lỗi SQL", JOptionPane.ERROR_MESSAGE);
            }
        });

        dlg.add(pForm, BorderLayout.CENTER);
        dlg.add(pBtns, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    // 3. CHỨC NĂNG XÓA KHÁCH HÀNG
    private void xuLyXoa() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        String maKH = tableModel.getValueAt(modelRow, 0).toString();
        String hoTen = tableModel.getValueAt(modelRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa khách hàng \"" + hoTen + "\" (" + maKH + ") không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (khachHangDAO.xoaKhachHang(maKH)) {
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDataToTable();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Không thể xóa khách hàng này do đã có dữ liệu xe hoặc hóa đơn liên quan!", "Lỗi ràng buộc dữ liệu", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 4. CHỨC NĂNG TÌM KIẾM TỰ ĐỘNG
    private void xuLyTimKiem() {
        txtTimKH.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { timKiem(); }
            @Override
            public void removeUpdate(DocumentEvent e) { timKiem(); }
            @Override
            public void changedUpdate(DocumentEvent e) { timKiem(); }
        });
    }

    public void timKiem() {
        String keyword = txtTimKH.getText().trim();
        if (keyword.isEmpty()) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(keyword), 0, 1, 2));
        }
    }

    // 5. CHỨC NĂNG LÀM MỚI
    private void xuLyLamMoi() {
        txtTimKH.setText("");
        rowSorter.setRowFilter(null);
        loadDataToTable();
    }

    private void moUIDanhSachXeKhachHang(String maKH, String hoTen, String sdt) {
        int opt = JOptionPane.showConfirmDialog(this,
                "Mở danh sách xe của khách hàng:\nMã KH: " + maKH + "\nHọ tên: " + hoTen + "\nSĐT: " + sdt,
                "Xác nhận",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (opt == JOptionPane.YES_OPTION) {
            DanhSachXeKhachHang UI = new DanhSachXeKhachHang(maKH, hoTen, sdt);
            UI.setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManHinhQuanLyKhachHang ui = new ManHinhQuanLyKhachHang();
            ui.setVisible(true);
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int modelRow = table.convertRowIndexToModel(selectedRow);
                String maKH = tableModel.getValueAt(modelRow, 0).toString();
                String hoTen = tableModel.getValueAt(modelRow, 1).toString();
                String sdt = tableModel.getValueAt(modelRow, 2).toString();

                moUIDanhSachXeKhachHang(maKH, hoTen, sdt);
            }
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}