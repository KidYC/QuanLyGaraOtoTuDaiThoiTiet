package View.KeToan;

import DAO.ThongKeDoanhThu_DAO;
import DAO.ThongKeDoanhThu_DAO.ThongKeDoanhThuModel;

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

public class ManHinhThongKeDoanhThu extends JFrame {
    private JPanel pnlHeader, pnlCenter, pnlKPI, pnlCardTongHD, pnlCardTongDoanhThu, pnlBody, pnlFilter;
    private JLabel lblTieuDe, lblTongHD, lblTongDoanhThu, lblTitleTongHD, lblTitleTongDoanhThu, lblThang, lblNam;
    private JComboBox<String> cbThang, cbNam;
    private JButton btnLoc, btnLamMoi, btnXuatBaoCao;
    private JTable tblThongKe;
    private DefaultTableModel modelTable;
    private JScrollPane scrollPane;

    private ThongKeDoanhThu_DAO dao;

    public ManHinhThongKeDoanhThu() {
        dao = new ThongKeDoanhThu_DAO();

        setTitle("Thống Kê Doanh Thu Hệ Thống");
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBorder(new EmptyBorder(15, 20, 10, 20));
        pnlHeader.setBackground(Color.WHITE);

        lblTieuDe = new JLabel("THỐNG KÊ BÁO CÁO DOANH THU");
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

        lblTongHD = new JLabel("0", JLabel.CENTER);
        lblTongDoanhThu = new JLabel("0 đ", JLabel.CENTER);

        pnlCardTongHD = new JPanel(new BorderLayout(5, 5));
        pnlCardTongHD.setBackground(Color.WHITE);
        pnlCardTongHD.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)), new EmptyBorder(12, 15, 12, 15)));
        lblTitleTongHD = new JLabel("Tổng số hóa đơn");
        lblTitleTongHD.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitleTongHD.setForeground(new Color(107, 114, 128));
        lblTongHD.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTongHD.setForeground(new Color(59, 130, 246));
        pnlCardTongHD.add(lblTitleTongHD, BorderLayout.NORTH);
        pnlCardTongHD.add(lblTongHD, BorderLayout.CENTER);

        pnlCardTongDoanhThu = new JPanel(new BorderLayout(5, 5));
        pnlCardTongDoanhThu.setBackground(Color.WHITE);
        pnlCardTongDoanhThu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)), new EmptyBorder(12, 15, 12, 15)));
        lblTitleTongDoanhThu = new JLabel("Tổng doanh thu ghi nhận");
        lblTitleTongDoanhThu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitleTongDoanhThu.setForeground(new Color(107, 114, 128));
        lblTongDoanhThu.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTongDoanhThu.setForeground(new Color(34, 197, 94));
        pnlCardTongDoanhThu.add(lblTitleTongDoanhThu, BorderLayout.NORTH);
        pnlCardTongDoanhThu.add(lblTongDoanhThu, BorderLayout.CENTER);

        pnlKPI.add(pnlCardTongHD);
        pnlKPI.add(pnlCardTongDoanhThu);
        pnlCenter.add(pnlKPI, BorderLayout.NORTH);

        pnlBody = new JPanel(new BorderLayout(0, 10));
        pnlBody.setOpaque(false);

        pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        pnlFilter.setBackground(Color.WHITE);
        pnlFilter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)), new EmptyBorder(8, 10, 8, 10)));

        lblThang = new JLabel("Tháng:");
        cbThang = new JComboBox<>(new String[]{"Tất cả", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
        lblNam = new JLabel("Năm:");
        cbNam = new JComboBox<>(new String[]{"Tất cả", "2024", "2025", "2026"});

        btnLoc = new JButton("Lọc dữ liệu");
        btnLoc.setBackground(new Color(37, 99, 235));
        btnLoc.setForeground(Color.WHITE);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(new Color(107, 114, 128));
        btnLamMoi.setForeground(Color.WHITE);

        btnXuatBaoCao = new JButton("Xuất Excel");
        btnXuatBaoCao.setBackground(new Color(16, 185, 129));
        btnXuatBaoCao.setForeground(Color.WHITE);

        pnlFilter.add(lblThang); pnlFilter.add(cbThang);
        pnlFilter.add(lblNam); pnlFilter.add(cbNam);
        pnlFilter.add(btnLoc); pnlFilter.add(btnLamMoi); pnlFilter.add(btnXuatBaoCao);

        pnlBody.add(pnlFilter, BorderLayout.NORTH);

        String[] tenCot = {"Tháng", "Năm", "Số Lượng Hóa Đơn", "Doanh Thu (VNĐ)"};
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
            cbThang.setSelectedIndex(0); cbNam.setSelectedIndex(0);
            loadData();
        });
        btnXuatBaoCao.addActionListener(e -> xuatFileCSV());

        loadData();
    }

    private void loadData() {
        modelTable.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###");
        int tongHD = 0;
        double tongDoanhThu = 0;

        try {
            List<ThongKeDoanhThuModel> ds = dao.layDanhSachDoanhThu();
            String thangChon = cbThang.getSelectedItem().toString();
            String namChon = cbNam.getSelectedItem().toString();

            for (ThongKeDoanhThuModel item : ds) {
                boolean matchThang = thangChon.equals("Tất cả") || item.getThang() == Integer.parseInt(thangChon);
                boolean matchNam = namChon.equals("Tất cả") || item.getNam() == Integer.parseInt(namChon);

                if (matchThang && matchNam) {
                    modelTable.addRow(new Object[]{
                            "Tháng " + item.getThang(),
                            item.getNam(),
                            item.getTongSoHoaDon(),
                            df.format(item.getDoanhThu())
                    });
                    tongHD += item.getTongSoHoaDon();
                    tongDoanhThu += item.getDoanhThu();
                }
            }
            lblTongHD.setText(String.valueOf(tongHD));
            lblTongDoanhThu.setText(df.format(tongDoanhThu) + " đ");
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