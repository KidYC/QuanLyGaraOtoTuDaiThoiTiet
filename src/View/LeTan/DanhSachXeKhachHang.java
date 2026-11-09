package View.LeTan;

import DAO.DongXe_DAO;
import DAO.HangXe_DAO;
import DAO.Xe_DAO;
import Entity.DongXe;
import Entity.HangXe;
import Entity.KhachHang;
import Entity.Xe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class DanhSachXeKhachHang extends JFrame implements ActionListener {
    private final String maKH;
    private final String hoTen;
    private final String soDienThoai;

    private JButton btnTaoPhieuTiepNhan;
    private JButton btnThemXeMoi;
    private JButton btnQuayLai;
    private JTable table;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private DefaultTableModel tableModel;

    public DanhSachXeKhachHang(String maKH, String hoTen, String soDienThoai) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;

        initUI();
    }

    public DanhSachXeKhachHang(){
        this.maKH = "";
        this.hoTen = "";
        this.soDienThoai = "";

        initUI();
    }

    private void initUI() {
        setTitle("Danh Sách Xe Của Khách Hàng - " + hoTen);
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel pNorth = new JPanel(new BorderLayout(5, 10));
        pNorth.setBorder(new EmptyBorder(10, 15, 5, 15));

        JLabel lblTitle = new JLabel("DANH SÁCH XE CỦA KHÁCH HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(44, 62, 80));
        pNorth.add(lblTitle, BorderLayout.NORTH);

        JPanel pInfoKH = new JPanel(new GridLayout(1, 3, 20, 5));

        try {
            if (maKH != null && !maKH.trim().isEmpty()) {
                pInfoKH.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(189, 195, 199)),
                        "Thông Tin Khách Hàng",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 13),
                        new Color(52, 73, 94)
                ));

                JLabel lblMaKH = new JLabel("Mã KH: " + maKH);
                JLabel lblHoTen = new JLabel("Họ Tên: " + hoTen);
                JLabel lblSDT = new JLabel("Số ĐT: " + soDienThoai);

                lblMaKH.setFont(new Font("Arial", Font.PLAIN, 14));
                lblHoTen.setFont(new Font("Arial", Font.BOLD, 14));
                lblSDT.setFont(new Font("Arial", Font.PLAIN, 14));

                pInfoKH.add(lblMaKH);
                pInfoKH.add(lblHoTen);
                pInfoKH.add(lblSDT);

                tableModel = new DefaultTableModel(
                        new String[]{"Biển Số Xe", "Hãng Xe", "Dòng Xe", "Năm Sản Xuất"}, 0){
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
            } else {
                tableModel = new DefaultTableModel(
                        new String[]{"Biển Số Xe", "Hãng Xe", "Dòng Xe", "Năm Sản Xuất", "Mã Khách Hàng"}, 0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo giao diện");
        }

        pNorth.add(pInfoKH, BorderLayout.CENTER);
        add(pNorth, BorderLayout.NORTH);

        JPanel pCenter = new JPanel(new BorderLayout());
        pCenter.setBorder(new EmptyBorder(0, 15, 0, 15));

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

        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));

        btnThemXeMoi = new JButton("Thêm Xe Cho Khách");
        btnTaoPhieuTiepNhan = new JButton("Tạo Phiếu Tiếp Nhận");
        btnQuayLai = new JButton("Quay Lại");

        btnThemXeMoi.setFont(new Font("Arial", Font.PLAIN, 13));
        btnTaoPhieuTiepNhan.setFont(new Font("Arial", Font.PLAIN, 13));
        btnQuayLai.setFont(new Font("Arial", Font.PLAIN, 13));

        pSouth.add(btnThemXeMoi);
        pSouth.add(btnTaoPhieuTiepNhan);
        pSouth.add(btnQuayLai);
        add(pSouth, BorderLayout.SOUTH);

        btnThemXeMoi.addActionListener(this);
        btnTaoPhieuTiepNhan.addActionListener(this);
        btnQuayLai.addActionListener(this);

        loadDataXe();
    }

    private void loadDataXe() {
        tableModel.setRowCount(0);
        try {
            Xe_DAO dao = new Xe_DAO();
            if (maKH != null && !maKH.trim().isEmpty()) {
                List<Xe> list = dao.getXeByMaKH(maKH);
                for (Xe xe : list) {
                    tableModel.addRow(new Object[]{
                            xe.getBienSo(),
                            xe.getTenHangXeStr(),
                            xe.getTenDongXeStr(),
                            xe.getNamSanXuat()
                    });
                }
            } else {
                List<Xe> list = dao.getAllXe();
                for (Xe xe : list) {
                    tableModel.addRow(new Object[]{
                            xe.getBienSo(),
                            xe.getTenHangXeStr(),
                            xe.getTenDongXeStr(),
                            xe.getNamSanXuat(),
                            xe.getMaKhachHangStr()
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu xe: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void XuLyThemXeMoi() {
        JDialog dialog = new JDialog(this, "Thêm Xe Cho Khách Hàng", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel pForm = new JPanel(new GridLayout(5, 2, 10, 10));
        pForm.setBorder(new EmptyBorder(15, 20, 15, 20));

        JTextField txtBienSo = new JTextField();
        JComboBox<HangXe> cbHangXe = new JComboBox<>();
        JComboBox<DongXe> cbDongXe = new JComboBox<>();

        // Nạp danh sách Hãng xe vào cbHangXe
        try {
            HangXe_DAO hangXeDao = new HangXe_DAO();
            List<HangXe> listHangXe = hangXeDao.getAllHangXe();
            for (HangXe hx : listHangXe) {
                cbHangXe.addItem(hx);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Sự kiện khi chọn Hãng xe -> tự động cập nhật danh sách Dòng xe trong cbDongXe
        cbHangXe.addActionListener(e -> {
            HangXe selectedHang = (HangXe) cbHangXe.getSelectedItem();
            cbDongXe.removeAllItems();
            if (selectedHang != null) {
                try {
                    DongXe_DAO dongXeDao = new DongXe_DAO();
                    List<DongXe> listDongXe = dongXeDao.getDongXeByMaHangXe(selectedHang.getMaHangXe());
                    for (DongXe dx : listDongXe) {
                        cbDongXe.addItem(dx);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Trigger lặp đầu tiên để load Dòng xe của Hãng xe mặc định đầu tiên
        if (cbHangXe.getItemCount() > 0) {
            cbHangXe.setSelectedIndex(0);
        }

        JTextField txtNamSX = new JTextField();
        JTextField txtMaKHForm = new JTextField(this.maKH);

        if (maKH != null && !maKH.isEmpty()) {
            txtMaKHForm.setEditable(false);
        }

        pForm.add(new JLabel("Biển Số Xe:"));
        pForm.add(txtBienSo);
        pForm.add(new JLabel("Hãng Xe:"));
        pForm.add(cbHangXe);
        pForm.add(new JLabel("Dòng Xe:"));
        pForm.add(cbDongXe);
        pForm.add(new JLabel("Năm Sản Xuất:"));
        pForm.add(txtNamSX);
        pForm.add(new JLabel("Mã Khách Hàng:"));
        pForm.add(txtMaKHForm);

        dialog.add(pForm, BorderLayout.CENTER);

        JPanel pButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnLuu = new JButton("Lưu Xe");
        JButton btnHuy = new JButton("Hủy");

        btnLuu.setBackground(new Color(46, 204, 113));
        btnLuu.setForeground(Color.WHITE);
        btnHuy.setBackground(new Color(149, 165, 166));
        btnHuy.setForeground(Color.WHITE);

        pButtons.add(btnHuy);
        pButtons.add(btnLuu);
        dialog.add(pButtons, BorderLayout.SOUTH);

        btnHuy.addActionListener(e -> dialog.dispose());

        btnLuu.addActionListener(e -> {
            String bienSoStr = txtBienSo.getText().trim();
            HangXe selectedHangXe = (HangXe) cbHangXe.getSelectedItem();
            DongXe selectedDongXe = (DongXe) cbDongXe.getSelectedItem();
            String namSXStr = txtNamSX.getText().trim();
            String maKHStr = txtMaKHForm.getText().trim();

            if (bienSoStr.isEmpty() || selectedDongXe == null || maKHStr.isEmpty() || selectedHangXe == null) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng chọn đầy đủ các trường thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int namSanXuat = 0;
            try {
                namSanXuat = Integer.parseInt(namSXStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Năm sản xuất phải là số nguyên hợp lệ (VD: 2022)!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            KhachHang kh = new KhachHang();
            kh.setMaKhachHang(maKHStr);

            Xe xeMoi = new Xe(bienSoStr, selectedDongXe, namSanXuat, kh);
            try {
                Xe_DAO dao = new Xe_DAO();
                if (dao.themXe(xeMoi)) {
                    JOptionPane.showMessageDialog(dialog, "Thêm xe mới thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    loadDataXe();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Không thể thêm xe mới!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi CSDL (Kiểm tra xem Biển số đã tồn tại hoặc Mã KH có hợp lệ hay không):\n" + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnTaoPhieuTiepNhan) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn một xe trong danh sách để tạo phiếu tiếp nhận!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String bienSo = table.getValueAt(selectedRow, 0).toString();
            String hangXe = table.getValueAt(selectedRow, 1).toString();

            ManHinhLapPhieuTiepNhan formPhieu = new ManHinhLapPhieuTiepNhan(hoTen, soDienThoai, bienSo, hangXe);
            formPhieu.setVisible(true);
            this.dispose();

        } else if (source == btnThemXeMoi) {
            XuLyThemXeMoi();
        } else if (source == btnQuayLai) {
            ManHinhQuanLyKhachHang UI = new ManHinhQuanLyKhachHang();
            UI.setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new DanhSachXeKhachHang().setVisible(true);
    }
}