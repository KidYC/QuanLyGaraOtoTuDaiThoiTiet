package View.ThuKho;

import DAO.ThongKeKho_DAO;
import DAO.ThongKeKho_DAO.ThongKeKhoModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class ManHinhThongKeKho extends JFrame {
    private JPanel pnlHeader, pnlCenter, pnlKPI, pnlCardTongMatHang, pnlCardTongGiaTri, pnlBody, pnlFilter;
    private JLabel lblTieuDe, lblTongMatHang, lblTongGiaTriTon, lblTitleTongMatHang, lblTitleTongGiaTri, lblTimKiem, lblTrangThai;
    private JTextField txtSearch;
    private JComboBox<String> cbTrangThai;
    private JButton btnLoc, btnLamMoi, btnXuatBaoCao;
    private JTable tblThongKe;
    private DefaultTableModel modelTable;
    private JScrollPane scrollPane;

    private ThongKeKho_DAO dao;

    public ManHinhThongKeKho() {
        dao = new ThongKeKho_DAO();

        setTitle("Thống Kê Tồn Kho Phụ Tùng");
        setSize(1150, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBorder(new EmptyBorder(15, 20, 10, 20));
        pnlHeader.setBackground(Color.WHITE);

        lblTieuDe = new JLabel("THỐNG KÊ BÁO CÁO TỒN KHO PHỤ TÙNG");
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTieuDe.setForeground(new Color(15, 23, 42));
        pnlHeader.add(lblTieuDe, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);

        pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setBorder(new EmptyBorder(0, 20, 20, 20));
        pnlCenter.setOpaque(false);

        pnlKPI = new JPanel(new GridLayout(1, 2, 15, 0));
        pnlKPI.setOpaque(false);
        pnlKPI.setPreferredSize(new Dimension(0, 90));

        lblTongMatHang = new JLabel("0", JLabel.CENTER);
        lblTongGiaTriTon = new JLabel("0 đ", JLabel.CENTER);

        pnlCardTongMatHang = new JPanel(new BorderLayout(5, 5));
        pnlCardTongMatHang.setBackground(Color.WHITE);
        pnlCardTongMatHang.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)), new EmptyBorder(12, 15, 12, 15)));
        lblTitleTongMatHang = new JLabel("Tổng loại phụ tùng trong kho");
        lblTitleTongMatHang.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitleTongMatHang.setForeground(new Color(107, 114, 128));
        lblTongMatHang.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTongMatHang.setForeground(new Color(59, 130, 246));
        pnlCardTongMatHang.add(lblTitleTongMatHang, BorderLayout.NORTH);
        pnlCardTongMatHang.add(lblTongMatHang, BorderLayout.CENTER);

        pnlCardTongGiaTri = new JPanel(new BorderLayout(5, 5));
        pnlCardTongGiaTri.setBackground(Color.WHITE);
        pnlCardTongGiaTri.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)), new EmptyBorder(12, 15, 12, 15)));
        lblTitleTongGiaTri = new JLabel("Tổng giá trị vốn tồn kho");
        lblTitleTongGiaTri.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitleTongGiaTri.setForeground(new Color(107, 114, 128));
        lblTongGiaTriTon.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTongGiaTriTon.setForeground(new Color(245, 158, 11));
        pnlCardTongGiaTri.add(lblTitleTongGiaTri, BorderLayout.NORTH);
        pnlCardTongGiaTri.add(lblTongGiaTriTon, BorderLayout.CENTER);

        pnlKPI.add(pnlCardTongMatHang);
        pnlKPI.add(pnlCardTongGiaTri);
        pnlCenter.add(pnlKPI, BorderLayout.NORTH);

        pnlBody = new JPanel(new BorderLayout(0, 10));
        pnlBody.setOpaque(false);

        pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        pnlFilter.setBackground(Color.WHITE);
        pnlFilter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)), new EmptyBorder(8, 10, 8, 10)));

        lblTimKiem = new JLabel("Từ khóa:");
        txtSearch = new JTextField(12);
        lblTrangThai = new JLabel("Trạng thái:");
        cbTrangThai = new JComboBox<>(new String[]{"Tất cả", "Bình thường", "Sắp hết"});

        btnLoc = new JButton("Lọc dữ liệu");
        btnLoc.setBackground(new Color(37, 99, 235));
        btnLoc.setForeground(Color.WHITE);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(new Color(107, 114, 128));
        btnLamMoi.setForeground(Color.WHITE);

        btnXuatBaoCao = new JButton("Xuất Excel");
        btnXuatBaoCao.setBackground(new Color(16, 185, 129));
        btnXuatBaoCao.setForeground(Color.WHITE);

        pnlFilter.add(lblTimKiem); pnlFilter.add(txtSearch);
        pnlFilter.add(lblTrangThai); pnlFilter.add(cbTrangThai);
        pnlFilter.add(btnLoc); pnlFilter.add(btnLamMoi); pnlFilter.add(btnXuatBaoCao);

        pnlBody.add(pnlFilter, BorderLayout.NORTH);

        String[] tenCot = {"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị Tính", "Số Lượng Tồn", "Đơn Giá Nhập (đ)", "Đơn Giá Bán (đ)", "Trạng Thái"};
        modelTable = new DefaultTableModel(tenCot, 0);
        tblThongKe = new JTable(modelTable);
        tblThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblThongKe.setRowHeight(28);

        scrollPane = new JScrollPane(tblThongKe);
        pnlBody.add(scrollPane, BorderLayout.CENTER);

        pnlCenter.add(pnlBody, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        btnLoc.addActionListener(e -> loadData());
        btnLamMoi.addActionListener(e -> {
            txtSearch.setText(""); cbTrangThai.setSelectedIndex(0);
            loadData();
        });
        btnXuatBaoCao.addActionListener(e -> xuatFileCSV());

        loadData();
    }

    private void loadData() {
        modelTable.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###");
        int tongMatHang = 0;
        double tongGiaTri = 0;

        try {
            List<ThongKeKhoModel> ds = dao.layDanhSachThongKeKho();
            String tuKhoa = txtSearch.getText().trim().toLowerCase();
            String trangThaiChon = cbTrangThai.getSelectedItem().toString();

            for (ThongKeKhoModel item : ds) {
                boolean matchKey = tuKhoa.isEmpty() || item.getMaPhuTung().toLowerCase().contains(tuKhoa) || item.getTenPhuTung().toLowerCase().contains(tuKhoa);
                boolean matchTrangThai = trangThaiChon.equals("Tất cả") || item.getTrangThaiTon().equalsIgnoreCase(trangThaiChon);

                if (matchKey && matchTrangThai) {
                    modelTable.addRow(new Object[]{
                            item.getMaPhuTung(),
                            item.getTenPhuTung(),
                            item.getDonViTinh(),
                            item.getSoLuongTon(),
                            df.format(item.getDonGiaNhap()),
                            df.format(item.getDonGiaBan()),
                            item.getTrangThaiTon()
                    });
                    tongMatHang++;
                    tongGiaTri += (item.getSoLuongTon() * item.getDonGiaNhap());
                }
            }
            lblTongMatHang.setText(String.valueOf(tongMatHang));
            lblTongGiaTriTon.setText(df.format(tongGiaTri) + " đ");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuatFileCSV() {
        if (modelTable.getRowCount() == 0) return;
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("CSV Files (*.csv)", "csv"));
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".csv")) path += ".csv";
            try (FileWriter writer = new FileWriter(path, java.nio.charset.StandardCharsets.UTF_8)) {
                writer.write("\ufeff");
                for (int i = 0; i < modelTable.getColumnCount(); i++) {
                    writer.write("\"" + modelTable.getColumnName(i) + "\"" + (i < modelTable.getColumnCount() - 1 ? "," : "\n"));
                }
                for (int r = 0; r < modelTable.getRowCount(); r++) {
                    for (int c = 0; c < modelTable.getColumnCount(); c++) {
                        writer.write("\"" + modelTable.getValueAt(r, c) + "\"" + (c < modelTable.getColumnCount() - 1 ? "," : "\n"));
                    }
                }
                JOptionPane.showMessageDialog(this, "Xuất file thành công!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}