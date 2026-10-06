package View.LeTan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class ManHinhQuanLyKhachHang extends JFrame implements ActionListener, MouseListener {

    private DefaultTableModel tableModel;
    private JTable table;
    private TableRowSorter<DefaultTableModel> rowSorter;

    private JTextField txtTimKH;
    private JButton btnTimKH;
    private JButton btnLamMoi;
    private JButton btnThemKH;
    private JButton btnQuayLai;

    public ManHinhQuanLyKhachHang() {
        setTitle("QUẢN LÝ KHÁCH HÀNG VÀ TIẾP NHẬN XE");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();
        loadData();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));
        //pNorth
        JLabel lblTitle = new JLabel("QUẢN LÝ KHÁCH HÀNG VÀ TIẾP NHẬN XE");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        //pCen
        JPanel pCenter = new JPanel(new BorderLayout());
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        String[] columnNames = {"Mã KH", "Họ Tên", "Số Điện Thoại", "Biển Số Xe", "Trạng Thái"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);

        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        pCenter.add(scrollPane, BorderLayout.CENTER);
        add(pCenter, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel();
        pSouth.setLayout(new BoxLayout(pSouth, BoxLayout.Y_AXIS));
        pSouth.setBackground(new Color(245, 245, 245));
        pSouth.setBorder(new EmptyBorder(10, 20, 20, 20));

        JPanel pTimKiem = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pTimKiem.setBackground(new Color(245, 245, 245));

        pTimKiem.add(new JLabel("Tìm Kiếm (Mã KH, Tên, SĐT, Biển số):"));
        txtTimKH = new JTextField(20);
        txtTimKH.setFont(new Font("Arial", Font.PLAIN, 14));
        pTimKiem.add(txtTimKH);

        btnTimKH = new JButton("Tìm");
        btnTimKH.setBackground(new Color(52, 152, 219));
        btnTimKH.setForeground(Color.WHITE);
        btnTimKH.setFocusPainted(false);
        pTimKiem.add(btnTimKH);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(new Color(149, 165, 166));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setFocusPainted(false);
        pTimKiem.add(btnLamMoi);

        JPanel pChucNang = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pChucNang.setBackground(new Color(245, 245, 245));

        btnThemKH = new JButton("Thêm Khách Hàng / Thêm Xe");
        btnThemKH.setPreferredSize(new Dimension(220, 35));
        btnThemKH.setBackground(new Color(46, 204, 113));
        btnThemKH.setForeground(Color.WHITE);
        btnThemKH.setFont(new Font("Arial", Font.BOLD, 12));
        btnThemKH.setFocusPainted(false);

        btnQuayLai = new JButton("Quay Lại");
        btnQuayLai.setPreferredSize(new Dimension(120, 35));
        btnQuayLai.setBackground(new Color(231, 76, 60));
        btnQuayLai.setForeground(Color.WHITE);
        btnQuayLai.setFont(new Font("Arial", Font.BOLD, 12));
        btnQuayLai.setFocusPainted(false);

        pChucNang.add(btnThemKH);
        pChucNang.add(btnQuayLai);

        pSouth.add(pTimKiem);
        pSouth.add(pChucNang);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void loadData() {
        tableModel.addRow(new Object[]{"KH001", "Trần Thanh Liêm", "0901234567", "51H-123.45", "Chờ tiếp nhận"});
        tableModel.addRow(new Object[]{"KH002", "Lê Duy Minh", "0912345678", "59G1-678.90", "Đang sửa chữa"});
        tableModel.addRow(new Object[]{"KH003", "Nguyễn Phi Hùng", "0987654321", "60A-111.22", "Đã bàn giao"});
    }

    private void initListener() {
        btnTimKH.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnThemKH.addActionListener(this);
        btnQuayLai.addActionListener(this);
        table.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnTimKH) {
            xuLyTimKiem();
        } else if (source == btnLamMoi) {
            xuLyLamMoi();
        } else if (source == btnThemKH) {
             FormThemKHXe UI = new FormThemKHXe();
             UI.setVisible(true);
            this.dispose();
        } else if (source == btnQuayLai) {
             TrangChuLeTan Home = new TrangChuLeTan();
             Home.setVisible(true);
            this.dispose();
        }
    }

    private void xuLyTimKiem() {
        String keyword = txtTimKH.getText().trim();
        if (keyword.isEmpty()) {
            rowSorter.setRowFilter(null);
            return;
        }
        try {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(keyword)));
            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy khách hàng phù hợp với từ khóa: " + keyword,
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyLamMoi() {
        txtTimKH.setText("");
        rowSorter.setRowFilter(null);
    }

    private void moUIDanhSachXeKhachHang(String maKH, String hoTen, String sdt, String bienSo, String trangThai) {
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
        ManHinhQuanLyKhachHang ui = new ManHinhQuanLyKhachHang();
        ui.setVisible(true);
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
                String bienSo = tableModel.getValueAt(modelRow, 3).toString();
                String trangThai = tableModel.getValueAt(modelRow, 4).toString();

                moUIDanhSachXeKhachHang(maKH, hoTen, sdt, bienSo, trangThai);
            }
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
}