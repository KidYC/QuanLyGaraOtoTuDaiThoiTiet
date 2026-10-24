package View.KyThuatVien;

import DAO.ThongKeSuaChua_DAO;
import Entity.ThongKeSuaChua;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class ManHinhThongKeSuaChua extends JFrame {
    private JTable tblThongKe;
    private DefaultTableModel modelTable;
    private ThongKeSuaChua_DAO thongKeDAO;
    private JTextField txtTimKiemKTV;
    private JComboBox<String> cbTrangThai;
    private JLabel lblTongPhieu, lblTongTienVatTu;

    public ManHinhThongKeSuaChua() {
        thongKeDAO = new ThongKeSuaChua_DAO();
        setTitle("Thống Kê & Báo Cáo Sửa Chữa");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. Tiêu đề
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBorder(new EmptyBorder(15, 20, 10, 20));
        pnlHeader.setBackground(Color.WHITE);
        JLabel lblTieuDe = new JLabel("THỐNG KÊ BÁO CÁO SỬA CHỮA HỆ THỐNG");
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTieuDe.setForeground(new Color(15, 23, 42));
        pnlHeader.add(lblTieuDe, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);

        // 2. Khu vực trung tâm gồm: Thẻ KPI, Bộ lọc và Bảng dữ liệu
        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setBorder(new EmptyBorder(0, 20, 20, 20));
        pnlCenter.setOpaque(false);

        // -- Thẻ KPI Tổng quan (Dashboard Cards) --
        JPanel pnlKPI = new JPanel(new GridLayout(1, 2, 15, 0));
        pnlKPI.setOpaque(false);
        pnlKPI.setPreferredSize(new Dimension(0, 90));

        lblTongPhieu = new JLabel("0", JLabel.CENTER);
        lblTongTienVatTu = new JLabel("0 đ", JLabel.CENTER);

        pnlKPI.add(createCard("Tổng số phiếu sửa chữa", lblTongPhieu, new Color(59, 130, 246)));
        pnlKPI.add(createCard("Tổng tiền vật tư sử dụng", lblTongTienVatTu, new Color(34, 197, 94)));
        pnlCenter.add(pnlKPI, BorderLayout.NORTH);

        // -- Panel chứa bộ lọc và bảng --
        JPanel pnlBody = new JPanel(new BorderLayout(0, 10));
        pnlBody.setOpaque(false);

        // Bộ lọc (Filter Panel)
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlFilter.setBackground(Color.WHITE);
        pnlFilter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(8, 10, 8, 10)
        ));

        pnlFilter.add(new JLabel("Mã KTV:"));
        txtTimKiemKTV = new JTextField(12);
        txtTimKiemKTV.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlFilter.add(txtTimKiemKTV);

        pnlFilter.add(new JLabel("Trạng thái:"));
        cbTrangThai = new JComboBox<>(new String[]{"Tất cả", "Hoàn thành", "Đang xử lý", "Chờ xuất kho"});
        cbTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlFilter.add(cbTrangThai);

        JButton btnLoc = new JButton("Lọc dữ liệu");
        styleButton(btnLoc, new Color(37, 99, 235));
        pnlFilter.add(btnLoc);

        JButton btnLamMoi = new JButton("Làm mới");
        styleButton(btnLamMoi, new Color(107, 114, 128));
        pnlFilter.add(btnLamMoi);

        pnlBody.add(pnlFilter, BorderLayout.NORTH);

        // Bảng dữ liệu (JTable)
        String[] tenCot = {"Mã Phiếu", "Ngày Lập", "Mã KTV", "Trạng Thái", "SL Phụ Tùng", "Tổng Tiền Vật Tư (đ)"};
        modelTable = new DefaultTableModel(tenCot, 0);
        tblThongKe = new JTable(modelTable);
        tblThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblThongKe.setRowHeight(28);
        tblThongKe.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(tblThongKe);
        pnlBody.add(scrollPane, BorderLayout.CENTER);

        pnlCenter.add(pnlBody, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // Sự kiện nút bấm
        btnLoc.addActionListener(e -> xuLyLocDuLieu());
        btnLamMoi.addActionListener(e -> {
            txtTimKiemKTV.setText("");
            cbTrangThai.setSelectedIndex(0);
            loadDataToTable("");
        });

        // Load dữ liệu ban đầu
        loadDataToTable("");
    }

    private JPanel createCard(String title, JLabel lblValue, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(12, 15, 12, 15)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitle.setForeground(new Color(107, 114, 128));

        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblValue.setForeground(accentColor);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        return card;
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(6, 14, 6, 14));
    }

    private void loadDataToTable(String maKTVFilter) {
        modelTable.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###");
        int tongPhieu = 0;
        double tongTien = 0;

        try {
            List<ThongKeSuaChua> ds = thongKeDAO.layDanhSachThongKe();
            String trangThaiChon = cbTrangThai.getSelectedItem().toString();

            for (ThongKeSuaChua tk : ds) {
                // Điều kiện lọc theo KTV và Trạng thái
                boolean matchKTV = maKTVFilter.isEmpty() || tk.getMaKTV().toLowerCase().contains(maKTVFilter.toLowerCase());
                boolean matchTrangThai = trangThaiChon.equals("Tất cả") || tk.getTrangThai().equalsIgnoreCase(trangThaiChon);

                if (matchKTV && matchTrangThai) {
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

            // Cập nhật lại số liệu trên các thẻ KPI
            lblTongPhieu.setText(String.valueOf(tongPhieu));
            lblTongTienVatTu.setText(df.format(tongTien) + " đ");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu thống kê: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void xuLyLocDuLieu() {
        String maKTV = txtTimKiemKTV.getText().trim();
        loadDataToTable(maKTV);
    }
}