package View.KyThuatVien;

import DAO.ThongKeSuaChua_DAO;
import Entity.ThongKeSuaChua;
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
import java.util.Calendar;
import java.util.List;

public class ManHinhThongKeSuaChua extends JFrame {
    private JPanel pnlHeader;
    private JPanel pnlCenter;
    private JPanel pnlKPI;
    private JPanel pnlCardTongPhieu;
    private JPanel pnlCardTongTien;
    private JPanel pnlBody;
    private JPanel pnlFilter;

    private JLabel lblTieuDe;
    private JLabel lblTongPhieu;
    private JLabel lblTongTienVatTu;
    private JLabel lblTitleTongPhieu;
    private JLabel lblTitleTongTien;
    private JLabel lblMaKTV;
    private JLabel lblThang;
    private JLabel lblNam;
    private JLabel lblTrangThai;

    private JTextField txtTimKiemKTV;

    private JComboBox<String> cbThang;
    private JComboBox<String> cbNam;
    private JComboBox<String> cbTrangThai;

    private JButton btnLoc;
    private JButton btnLamMoi;
    private JButton btnXuatBaoCao;

    private JTable tblThongKe;
    private DefaultTableModel modelTable;
    private JScrollPane scrollPane;

    private ThongKeSuaChua_DAO thongKeDAO;

    public ManHinhThongKeSuaChua() {
        thongKeDAO = new ThongKeSuaChua_DAO();

        setTitle("Thống Kê & Báo Cáo Sửa Chữa");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBorder(new EmptyBorder(15, 20, 10, 20));
        pnlHeader.setBackground(Color.WHITE);

        lblTieuDe = new JLabel("THỐNG KÊ BÁO CÁO SỬA CHỮA HỆ THỐNG");
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

        lblTongPhieu = new JLabel("0", JLabel.CENTER);
        lblTongTienVatTu = new JLabel("0 đ", JLabel.CENTER);

        pnlCardTongPhieu = new JPanel(new BorderLayout(5, 5));
        pnlCardTongPhieu.setBackground(Color.WHITE);
        pnlCardTongPhieu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(12, 15, 12, 15)
        ));

        lblTitleTongPhieu = new JLabel("Tổng số phiếu sửa chữa");
        lblTitleTongPhieu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitleTongPhieu.setForeground(new Color(107, 114, 128));

        lblTongPhieu.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTongPhieu.setForeground(new Color(59, 130, 246));

        pnlCardTongPhieu.add(lblTitleTongPhieu, BorderLayout.NORTH);
        pnlCardTongPhieu.add(lblTongPhieu, BorderLayout.CENTER);

        pnlCardTongTien = new JPanel(new BorderLayout(5, 5));
        pnlCardTongTien.setBackground(Color.WHITE);
        pnlCardTongTien.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(12, 15, 12, 15)
        ));

        lblTitleTongTien = new JLabel("Tổng tiền vật tư sử dụng");
        lblTitleTongTien.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitleTongTien.setForeground(new Color(107, 114, 128));

        lblTongTienVatTu.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTongTienVatTu.setForeground(new Color(34, 197, 94));

        pnlCardTongTien.add(lblTitleTongTien, BorderLayout.NORTH);
        pnlCardTongTien.add(lblTongTienVatTu, BorderLayout.CENTER);

        pnlKPI.add(pnlCardTongPhieu);
        pnlKPI.add(pnlCardTongTien);

        pnlCenter.add(pnlKPI, BorderLayout.NORTH);

        pnlBody = new JPanel(new BorderLayout(0, 10));
        pnlBody.setOpaque(false);

        pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        pnlFilter.setBackground(Color.WHITE);
        pnlFilter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(8, 10, 8, 10)
        ));

        lblMaKTV = new JLabel("Mã KTV:");
        txtTimKiemKTV = new JTextField(8);
        txtTimKiemKTV.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        lblThang = new JLabel("Tháng:");
        cbThang = new JComboBox<>(new String[]{
                "Tất cả", "1", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "11", "12"
        });
        cbThang.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        lblNam = new JLabel("Năm:");
        cbNam = new JComboBox<>(new String[]{"Tất cả", "2024", "2025", "2026"});
        cbNam.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        lblTrangThai = new JLabel("Trạng thái:");
        cbTrangThai = new JComboBox<>(new String[]{
                "Tất cả", "Hoàn thành", "Đang xử lý", "Chờ xuất kho"
        });
        cbTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        btnLoc = new JButton("Lọc dữ liệu");
        btnLoc.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLoc.setForeground(Color.WHITE);
        btnLoc.setBackground(new Color(37, 99, 235));
        btnLoc.setFocusPainted(false);
        btnLoc.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLoc.setBorder(new EmptyBorder(6, 12, 6, 12));

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setBackground(new Color(107, 114, 128));
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLamMoi.setBorder(new EmptyBorder(6, 12, 6, 12));

        btnXuatBaoCao = new JButton("Xuất Excel");
        btnXuatBaoCao.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnXuatBaoCao.setForeground(Color.WHITE);
        btnXuatBaoCao.setBackground(new Color(16, 185, 129));
        btnXuatBaoCao.setFocusPainted(false);
        btnXuatBaoCao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnXuatBaoCao.setBorder(new EmptyBorder(6, 12, 6, 12));

        pnlFilter.add(lblMaKTV);
        pnlFilter.add(txtTimKiemKTV);
        pnlFilter.add(lblThang);
        pnlFilter.add(cbThang);
        pnlFilter.add(lblNam);
        pnlFilter.add(cbNam);
        pnlFilter.add(lblTrangThai);
        pnlFilter.add(cbTrangThai);
        pnlFilter.add(btnLoc);
        pnlFilter.add(btnLamMoi);
        pnlFilter.add(btnXuatBaoCao);

        pnlBody.add(pnlFilter, BorderLayout.NORTH);

        String[] tenCot = {
                "Mã Phiếu", "Ngày Lập", "Mã KTV",
                "Trạng Thái", "SL Phụ Tùng", "Tổng Tiền Vật Tư (đ)"
        };

        modelTable = new DefaultTableModel(tenCot, 0);
        tblThongKe = new JTable(modelTable);
        tblThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblThongKe.setRowHeight(28);
        tblThongKe.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        scrollPane = new JScrollPane(tblThongKe);
        pnlBody.add(scrollPane, BorderLayout.CENTER);

        pnlCenter.add(pnlBody, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        btnLoc.addActionListener(e -> loadDataToTable());

        btnLamMoi.addActionListener(e -> {
            txtTimKiemKTV.setText("");
            cbThang.setSelectedIndex(0);
            cbNam.setSelectedIndex(0);
            cbTrangThai.setSelectedIndex(0);
            loadDataToTable();
        });

        btnXuatBaoCao.addActionListener(e -> xuatFileExcel());

        loadDataToTable();
    }

    private void loadDataToTable() {
        modelTable.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###");
        int tongPhieu = 0;
        double tongTien = 0;

        try {
            List<ThongKeSuaChua> ds = thongKeDAO.layDanhSachThongKe();

            String maKTVFilter = txtTimKiemKTV.getText().trim();
            String trangThaiChon = cbTrangThai.getSelectedItem().toString();
            String thangChon = cbThang.getSelectedItem().toString();
            String namChon = cbNam.getSelectedItem().toString();

            for (ThongKeSuaChua tk : ds) {
                boolean matchKTV = maKTVFilter.isEmpty()
                        || tk.getMaKTV().toLowerCase().contains(maKTVFilter.toLowerCase());

                boolean matchTrangThai = trangThaiChon.equals("Tất cả")
                        || tk.getTrangThai().equalsIgnoreCase(trangThaiChon);

                boolean matchThoiGian = true;

                if (tk.getNgayLap() != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(tk.getNgayLap());

                    int thangRow = cal.get(Calendar.MONTH) + 1;
                    int namRow = cal.get(Calendar.YEAR);

                    if (!thangChon.equals("Tất cả")
                            && thangRow != Integer.parseInt(thangChon)) {
                        matchThoiGian = false;
                    }

                    if (!namChon.equals("Tất cả")
                            && namRow != Integer.parseInt(namChon)) {
                        matchThoiGian = false;
                    }
                } else if (!thangChon.equals("Tất cả") || !namChon.equals("Tất cả")) {
                    matchThoiGian = false;
                }

                if (matchKTV && matchTrangThai && matchThoiGian) {
                    Object[] row = {
                            tk.getMaPhieuSuaChua(),
                            tk.getNgayLap(),
                            tk.getMaKTV(),
                            tk.getTrangThai(),
                            tk.getTongSoPhuTungDung(),
                            df.format(tk.getTongTienVatTu())
                    };

                    modelTable.addRow(row);
                    tongPhieu++;
                    tongTien += tk.getTongTienVatTu();
                }
            }

            lblTongPhieu.setText(String.valueOf(tongPhieu));
            lblTongTienVatTu.setText(df.format(tongTien) + " đ");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi tải dữ liệu thống kê: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    private void xuatFileExcel() {
        if (modelTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không có dữ liệu để xuất báo cáo!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn đường dẫn lưu file báo cáo");
        fileChooser.setFileFilter(
                new FileNameExtensionFilter("CSV Files (*.csv)", "csv")
        );

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            if (!filePath.toLowerCase().endsWith(".csv")) {
                filePath += ".csv";
            }

            try (FileWriter writer = new FileWriter(
                    filePath, java.nio.charset.StandardCharsets.UTF_8)) {

                writer.write("\ufeff");

                for (int i = 0; i < modelTable.getColumnCount(); i++) {
                    writer.write("\"" + modelTable.getColumnName(i) + "\"");
                    if (i < modelTable.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }

                writer.write("\n");

                for (int row = 0; row < modelTable.getRowCount(); row++) {
                    for (int col = 0; col < modelTable.getColumnCount(); col++) {
                        Object value = modelTable.getValueAt(row, col);
                        String cellText = value != null ? value.toString() : "";

                        writer.write("\"" + cellText.replace("\"", "\"\"") + "\"");

                        if (col < modelTable.getColumnCount() - 1) {
                            writer.write(",");
                        }
                    }

                    writer.write("\n");
                }

                JOptionPane.showMessageDialog(
                        this,
                        "Xuất báo cáo thành công!\nĐã lưu tại: " + filePath,
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE
                );

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(new File(filePath));
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Lỗi khi ghi file: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        }
    }
}