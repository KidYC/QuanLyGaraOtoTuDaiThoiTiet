package View.LeTan;

import DAO.BaoHanh_DAO;
import Entity.BaoHanhHienThi_DTO;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ManHinhDanhSachBaoHanh extends JFrame implements ActionListener {

    private JLabel lblTitle;

    private JTextField txtTimKiem;
    private JComboBox<String> cboTrangThai;
    private JButton btnTim;
    private JButton btnLamMoi;

    private JTable tblBaoHanh;
    private DefaultTableModel modelBaoHanh;
    private JLabel lblTongSo;

    private JButton btnQuayLai;
    private JButton btnXemChiTiet;

    public ManHinhDanhSachBaoHanh() {
        setTitle("DANH SÁCH BẢO HÀNH");
        setSize(1000, 520);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();
        loadData();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        // pNorth
        lblTitle = new JLabel("DANH SÁCH BẢO HÀNH");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // pCenter
        JPanel pCenter = new JPanel(new BorderLayout(10, 10));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        // Thanh tìm kiếm
        JPanel pTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pTimKiem.setBackground(Color.WHITE);
        pTimKiem.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Tìm kiếm & Lọc",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(5, 10, 5, 10)
        ));

        pTimKiem.add(new JLabel("Từ khóa:"));
        txtTimKiem = new JTextField(22);
        txtTimKiem.setToolTipText("Mã phiếu, tên khách hàng, biển số hoặc dịch vụ");
        pTimKiem.add(txtTimKiem);

        pTimKiem.add(new JLabel("Trạng thái:"));
        cboTrangThai = new JComboBox<>(new String[]{"Tất cả", "Đang hiệu lực", "Hết hạn"});
        pTimKiem.add(cboTrangThai);

        btnTim = new JButton("Tìm Kiếm");
        btnTim.setPreferredSize(new Dimension(110, 30));
        btnTim.setBackground(new Color(52, 152, 219));
        btnTim.setForeground(Color.WHITE);
        btnTim.setFocusPainted(false);
        btnTim.setFont(new Font("Arial", Font.BOLD, 12));
        pTimKiem.add(btnTim);

        btnLamMoi = new JButton("Làm Mới");
        btnLamMoi.setPreferredSize(new Dimension(100, 30));
        btnLamMoi.setBackground(new Color(243, 156, 18));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 12));
        pTimKiem.add(btnLamMoi);

        pCenter.add(pTimKiem, BorderLayout.NORTH);

        // Bảng
        String[] cols = {"STT", "Mã Phiếu", "Tên Khách Hàng", "Biển Số", "Dịch Vụ Bảo Hành",
                "Ngày Kích Hoạt", "Ngày Hết Hạn", "Trạng Thái"};
        modelBaoHanh = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblBaoHanh = new JTable(modelBaoHanh);
        tblBaoHanh.setRowHeight(25);
        tblBaoHanh.setFont(new Font("Arial", Font.PLAIN, 13));
        tblBaoHanh.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tblBaoHanh.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblBaoHanh.getColumnModel().getColumn(1).setPreferredWidth(80);
        tblBaoHanh.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblBaoHanh.getColumnModel().getColumn(3).setPreferredWidth(90);
        tblBaoHanh.getColumnModel().getColumn(4).setPreferredWidth(220);

        // Tô màu cột trạng thái
        tblBaoHanh.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    if ("Hết hạn".equals(String.valueOf(value))) {
                        c.setForeground(new Color(192, 57, 43));
                    } else {
                        c.setForeground(new Color(39, 174, 96));
                    }
                }
                c.setFont(new Font("Arial", Font.BOLD, 13));
                return c;
            }
        });

        JTableHeader header = tblBaoHanh.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tblBaoHanh);
        scrollPane.getViewport().setBackground(Color.WHITE);
        pCenter.add(scrollPane, BorderLayout.CENTER);

        lblTongSo = new JLabel("Tổng số: 0 hạng mục bảo hành");
        lblTongSo.setFont(new Font("Arial", Font.BOLD, 13));
        lblTongSo.setForeground(new Color(44, 62, 80));
        pCenter.add(lblTongSo, BorderLayout.SOUTH);

        add(pCenter, BorderLayout.CENTER);

        // pSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnQuayLai = new JButton("Quay Lại");
        btnQuayLai.setPreferredSize(new Dimension(110, 35));
        btnQuayLai.setBackground(new Color(149, 165, 166));
        btnQuayLai.setForeground(Color.WHITE);
        btnQuayLai.setFocusPainted(false);
        btnQuayLai.setFont(new Font("Arial", Font.BOLD, 12));

        btnXemChiTiet = new JButton("Xem Chi Tiết");
        btnXemChiTiet.setPreferredSize(new Dimension(130, 35));
        btnXemChiTiet.setBackground(new Color(46, 204, 113));
        btnXemChiTiet.setForeground(Color.WHITE);
        btnXemChiTiet.setFocusPainted(false);
        btnXemChiTiet.setFont(new Font("Arial", Font.BOLD, 12));

        pSouth.add(btnQuayLai);
        pSouth.add(btnXemChiTiet);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btnQuayLai.addActionListener(this);
        btnXemChiTiet.addActionListener(this);
        btnTim.addActionListener(this);
        btnLamMoi.addActionListener(this);
        txtTimKiem.addActionListener(this);          // Enter để tìm
        cboTrangThai.addActionListener(this);        // đổi trạng thái là lọc luôn

        // Double-click một dòng để xem chi tiết
        tblBaoHanh.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    xuLyXemChiTiet();
                }
            }
        });
    }

    private void loadData() {
        modelBaoHanh.setRowCount(0);
        BaoHanh_DAO dao = new BaoHanh_DAO();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            List<BaoHanhHienThi_DTO> list = dao.getDanhSachBaoHanhHienThi(
                    txtTimKiem.getText().trim(),
                    cboTrangThai.getSelectedItem().toString());

            int stt = 1;
            for (BaoHanhHienThi_DTO bh : list) {
                modelBaoHanh.addRow(new Object[]{
                        stt++,
                        bh.getMaPhieu(),
                        bh.getTenKhachHang(),
                        bh.getBienSo(),
                        bh.getTenDichVu(),
                        sdf.format(bh.getNgayKichHoat()),
                        sdf.format(bh.getNgayHetHan()),
                        bh.getTrangThai()
                });
            }
            lblTongSo.setText("Tổng số: " + list.size() + " hạng mục bảo hành");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải danh sách bảo hành từ CSDL: " + ex.getMessage(),
                    "Lỗi Cơ Sở Dữ Liệu", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnQuayLai) {
            xuLyQuayLai();
        } else if (source == btnXemChiTiet) {
            xuLyXemChiTiet();
        } else if (source == btnTim || source == txtTimKiem || source == cboTrangThai) {
            loadData();
        } else if (source == btnLamMoi) {
            txtTimKiem.setText("");
            cboTrangThai.setSelectedIndex(0);   // kích hoạt loadData qua cboTrangThai nếu đổi mục
            loadData();
        }
    }

    private void xuLyQuayLai() {
        this.dispose();
    }

    private void xuLyXemChiTiet() {
        int selectedRow = tblBaoHanh.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn một dòng trong danh sách để xem chi tiết bảo hành!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maPhieu = modelBaoHanh.getValueAt(selectedRow, 1).toString();
        String tenKH = modelBaoHanh.getValueAt(selectedRow, 2).toString();
        String bienSo = modelBaoHanh.getValueAt(selectedRow, 3).toString();

        FormQuanLyBaoHanh form = new FormQuanLyBaoHanh(maPhieu, bienSo, tenKH);
        form.setVisible(true);
    }

    public static void main(String[] args) {
        ManHinhDanhSachBaoHanh form = new ManHinhDanhSachBaoHanh();
        form.setVisible(true);
    }
}