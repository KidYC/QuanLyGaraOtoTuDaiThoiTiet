
package View.KeToan;

import DAO.DAO_KeToan;
import Entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ManHinhLapHoaDon extends JFrame {

    // Màu giao diện kế toán
    private final Color mauNen = new Color(244, 246, 248);
    private final Color mauTieuDe = new Color(186, 230, 253);
    private final Color mauChu = new Color(17, 24, 39);
    private final Color mauNut = new Color(37, 99, 235);

    // Thông tin hóa đơn
    private JTextField txtMaHoaDon;
    private JTextField txtNgayLap;
    private JTextField txtNhanVien;

    private JComboBox<String> cboPhuongThuc;
    private JComboBox<String> cboPhieuSuaChua;

    // Chi tiết hóa đơn
    private JTable tblChiTiet;
    private DefaultTableModel modelChiTiet;

    private JLabel lblTongTien;

    // Danh sách Entity
    private HoaDon hoaDon;
    private List<ChiTietHoaDon> dsChiTiet;


    public ManHinhLapHoaDon() {
        dsChiTiet = new ArrayList<>();

        setTitle("Lập hóa đơn - Quản lý gara ô tô");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        getContentPane().setBackground(mauNen);

        // Khu vực chính
        JPanel pnlChinh = new JPanel(new BorderLayout(10, 10));
        pnlChinh.setBackground(mauNen);
        pnlChinh.setBorder(BorderFactory.createEmptyBorder(
                10, 15, 10, 15
        ));

// Thông tin hóa đơn ở phía trên
        pnlChinh.add(formThongTin(), BorderLayout.NORTH);

// Chi tiết hóa đơn ở giữa
        pnlChinh.add(formChiTietVaThanhToan(), BorderLayout.CENTER);

// Thêm vào JFrame
        add(tieuDe(), BorderLayout.NORTH);
        add(pnlChinh, BorderLayout.CENTER);

        lamMoiForm();
    }

    // ================= TIÊU ĐỀ =================

    private JPanel tieuDe() {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(mauTieuDe);
        pnl.setBorder(BorderFactory.createEmptyBorder(
                15, 20, 15, 20));

        JLabel lbl = new JLabel("LẬP HÓA ĐƠN");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(mauChu);

        JLabel lblPhu = new JLabel(
                "Quản lý hóa đơn và thanh toán sửa chữa ô tô");

        lblPhu.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        pnl.add(lbl, BorderLayout.NORTH);
        pnl.add(lblPhu, BorderLayout.SOUTH);

        return pnl;
    }

    // ================= THÔNG TIN HÓA ĐƠN =================

    private JPanel formThongTin() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createTitledBorder(
                "Thông tin hóa đơn"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaHoaDon = new JTextField();
        txtNgayLap = new JTextField();
        txtNhanVien = new JTextField();

        cboPhuongThuc = new JComboBox<>(new String[]{
                "Tiền mặt",
                "Chuyển khoản",
        });

        cboPhieuSuaChua = new JComboBox<>();
        cboPhieuSuaChua.addItem("Chọn phiếu sửa chữa");

        txtMaHoaDon.setEditable(false);
        txtNgayLap.setEditable(false);
        txtNhanVien.setEditable(false);

        // Dòng 1
        themTruong(pnl, gbc, 0, 0,
                "Mã hóa đơn:", txtMaHoaDon);

        themTruong(pnl, gbc, 2, 0,
                "Ngày lập:", txtNgayLap);

        // Dòng 2
        themTruong(pnl, gbc, 0, 1,
                "Nhân viên lập:", txtNhanVien);

        themTruong(pnl, gbc, 2, 1,
                "Phương thức:", cboPhuongThuc);

        // Dòng 3
        themTruong(pnl, gbc, 0, 2,
                "Phiếu sửa chữa:", cboPhieuSuaChua);

        return pnl;
    }

    private void themTruong(JPanel pnl,
                            GridBagConstraints gbc,
                            int x, int y,
                            String ten,
                            Component component) {

        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 0;

        JLabel lbl = new JLabel(ten);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));

        pnl.add(lbl, gbc);

        gbc.gridx = x + 1;
        gbc.weightx = 1;

        component.setPreferredSize(new Dimension(200, 30));

        pnl.add(component, gbc);
    }

    // ================= CHI TIẾT HÓA ĐƠN =================

    private JPanel formChiTietVaThanhToan() {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));
        pnl.setBackground(mauNen);
        pnl.setBorder(BorderFactory.createEmptyBorder(
                0, 15, 15, 15));
        String[] columns = {
                "STT",
                "Nội dung",
                "Số lượng",
                "Đơn giá",
                "Thành tiền"
        };

        modelChiTiet = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblChiTiet = new JTable(modelChiTiet);
        tblChiTiet.setRowHeight(28);
        tblChiTiet.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        tblChiTiet.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scroll = new JScrollPane(tblChiTiet);
        scroll.setBorder(BorderFactory.createTitledBorder(
                "Chi tiết hóa đơn"));

        pnl.add(scroll, BorderLayout.CENTER);

        // Tổng tiền
        JPanel pnlTongTien = new JPanel(
                new FlowLayout(FlowLayout.RIGHT));

        pnlTongTien.setBackground(Color.WHITE);
        pnlTongTien.setBorder(BorderFactory.createEmptyBorder(
                10, 10, 10, 10));

        JLabel lbl = new JLabel("TỔNG TIỀN:");

        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));

        lblTongTien = new JLabel("0 VNĐ");
        lblTongTien.setFont(
                new Font("Segoe UI", Font.BOLD, 22));

        lblTongTien.setForeground(new Color(220, 38, 38));

        pnlTongTien.add(lbl);
        pnlTongTien.add(lblTongTien);

        // Nút chức năng
        JPanel pnlNutChucNang = new JPanel(
                new FlowLayout(FlowLayout.RIGHT));

        pnlNutChucNang.setBackground(mauNen);

        JButton btnLapHoaDon = taoNut(
                "Lập hóa đơn", new Color(22, 163, 74));

        JButton btnLamMoi = taoNut(
                "Làm mới", new Color(100, 116, 139));

        JButton btnDong = taoNut(
                "Đóng", new Color(71, 85, 105));

        btnLapHoaDon.addActionListener(
                e -> lapHoaDon());

        btnLamMoi.addActionListener(
                e -> lamMoiForm());

        btnDong.addActionListener(
                e -> dispose());

        pnlNutChucNang.add(btnLapHoaDon);
        pnlNutChucNang.add(btnLamMoi);
        pnlNutChucNang.add(btnDong);

        // Khu vực dưới
        // Khu vực dưới
        JPanel pnlDuoi = new JPanel(new BorderLayout());

        pnlDuoi.add(pnlTongTien, BorderLayout.NORTH);
        pnlDuoi.add(pnlNutChucNang, BorderLayout.SOUTH);

        pnl.add(pnlDuoi, BorderLayout.SOUTH);

// Tải danh sách phiếu sửa chữa
        loadPhieuSuaChua();

// Bắt sự kiện khi chọn phiếu sửa chữa
        cboPhieuSuaChua.addActionListener(e -> {

            if (cboPhieuSuaChua.getSelectedIndex() > 0) {
                loadChiTietTheoPhieu();
            } else {
                dsChiTiet.clear();
                modelChiTiet.setRowCount(0);
                capNhatTongTien();
            }
        });

        return pnl;
    }

    // ================= THÊM CHI TIẾT =================

    private void themChiTiet() {

        JTextField txtNoiDung = new JTextField();
        JTextField txtSoLuong = new JTextField();
        JTextField txtDonGia = new JTextField();

        Object[] fields = {
                "Nội dung:", txtNoiDung,
                "Số lượng:", txtSoLuong,
                "Đơn giá:", txtDonGia
        };

        int result = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Thêm chi tiết hóa đơn",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            String noiDung = txtNoiDung.getText().trim();

            int soLuong = Integer.parseInt(
                    txtSoLuong.getText().trim());

            BigDecimal donGia = new BigDecimal(
                    txtDonGia.getText().trim());

            if (noiDung.isEmpty()
                    || soLuong <= 0
                    || donGia.compareTo(BigDecimal.ZERO) < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng nhập thông tin hợp lệ!");

                return;
            }

            int soChiTiet = dsChiTiet.size() + 1;

            String maChiTiet = String.format("CTHD%02d", soChiTiet);

            // Tính thành tiền
            BigDecimal thanhTien = donGia.multiply(
                    BigDecimal.valueOf(soLuong));

            // Thêm vào bảng
            int stt = modelChiTiet.getRowCount() + 1;

            modelChiTiet.addRow(new Object[]{
                    stt,
                    maChiTiet,
                    noiDung,
                    soLuong,
                    dinhDangTien(donGia),
                    dinhDangTien(thanhTien)
            });

            // Tạo Entity chi tiết hóa đơn
            ChiTietHoaDon chiTiet = new ChiTietHoaDon(
                    maChiTiet,
                    txtMaHoaDon.getText(),
                    noiDung,
                    soLuong,
                    donGia
            );

            dsChiTiet.add(chiTiet);

            capNhatTongTien();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Số lượng hoặc đơn giá không hợp lệ!");
        }
    }

    // ================= XÓA CHI TIẾT =================

    private void xoaChiTiet() {

        int selectedRow = tblChiTiet.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn chi tiết cần xóa!");

            return;
        }

        dsChiTiet.remove(selectedRow);
        modelChiTiet.removeRow(selectedRow);

        // Cập nhật lại STT
        for (int i = 0; i < modelChiTiet.getRowCount(); i++) {
            modelChiTiet.setValueAt(i + 1, i, 0);
        }

        capNhatTongTien();
    }
    private void loadChiTietTheoPhieu() {

        String maPSC = cboPhieuSuaChua.getSelectedItem().toString();

        dsChiTiet.clear();
        modelChiTiet.setRowCount(0);

        try {
            DAO_KeToan dao = new DAO_KeToan();

            List<ChiTietHoaDon> danhSach =
                    dao.layChiTietTheoPhieuSuaChua(maPSC);

            int stt = 1;

            for (ChiTietHoaDon ct : danhSach) {

                dsChiTiet.add(ct);

                modelChiTiet.addRow(new Object[]{
                        stt++,
                        ct.getNoiDung(),
                        ct.getSoLuong(),
                        dinhDangTien(ct.getDonGia()),
                        dinhDangTien(ct.getThanhTien())
                });
            }

            capNhatTongTien();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi tải chi tiết: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }



    private void capNhatTongTien() {

        BigDecimal tongTien = BigDecimal.ZERO;

        for (ChiTietHoaDon ct : dsChiTiet) {

            BigDecimal thanhTien = ct.getDonGia()
                    .multiply(BigDecimal.valueOf(
                            ct.getSoLuong()));

            tongTien = tongTien.add(thanhTien);
        }

        lblTongTien.setText(dinhDangTien(tongTien) + " VNĐ");
    }

    // ================= LẬP HÓA ĐƠN =================
    private void lapHoaDon() {

        if (cboPhieuSuaChua.getSelectedItem() == null
                || cboPhieuSuaChua.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn phiếu sửa chữa!"
            );
            return;
        }

        if (dsChiTiet.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Hóa đơn chưa có chi tiết!"
            );
            return;
        }

        try {
            String phuongThucDuocChon =
                    cboPhuongThuc.getSelectedItem().toString();

            String maPhuongThuc;

            switch (phuongThucDuocChon) {
                case "Tiền mặt":
                    maPhuongThuc = "PT01";
                    break;
                case "Chuyển khoản":
                    maPhuongThuc = "PT02";
                    break;
                default:
                    JOptionPane.showMessageDialog(this,
                            "Vui lòng chọn phương thức thanh toán!");
                    return;
            }

            PhuongThucThanhToan pttt = new PhuongThucThanhToan();
            pttt.setMaPhuongThuc(maPhuongThuc);

            String maPSC = cboPhieuSuaChua.getSelectedItem().toString();

            PhieuSuaChua psc = new PhieuSuaChua();
            psc.setMaPhieuSuaChua(maPSC);

            String maNV = txtNhanVien.getText().trim();

            NhanVien nv = new NhanVien();
            nv.setMaNhanVien(maNV);

            KeToan kt = new KeToan();
            kt.setMaNhanVien(nv);

            hoaDon = new HoaDon(
                    txtMaHoaDon.getText().trim(),
                    LocalDateTime.now(),
                    pttt,
                    psc,
                    kt
            );

            DAO_KeToan dao = new DAO_KeToan();

            // Lưu hóa đơn vào SQL Server
            boolean ketQua = dao.themHoaDon(hoaDon);

            if (ketQua) {
                JOptionPane.showMessageDialog(
                        this,
                        "Lập hóa đơn thành công!\n"
                                + "Mã hóa đơn: " + hoaDon.getMaHoaDon()
                                + "\nTổng tiền: " + lblTongTien.getText()
                );

                // Mở màn hình chi tiết hóa đơn
                ManHinhChiTietHDVaThanhToan ui =
                        new ManHinhChiTietHDVaThanhToan();

                ui.setVisible(true);

                // Đóng màn hình lập hóa đơn
                dispose();

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Không thể lưu hóa đơn!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi lưu hóa đơn vào SQL Server:\n" + e.getMessage(),
                    "Lỗi cơ sở dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }


    private void lamMoiForm() {
        txtMaHoaDon.setText(taoMaHoaDon());
        txtNgayLap.setText(
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern(
                                "dd/MM/yyyy HH:mm:ss"
                        )
                )
        );

        txtNhanVien.setText("KT01");

        cboPhuongThuc.setSelectedIndex(0);

        loadPhieuSuaChua();

        if (cboPhieuSuaChua.getItemCount() > 0) {
            cboPhieuSuaChua.setSelectedIndex(0);
        }

        dsChiTiet.clear();
        modelChiTiet.setRowCount(0);

        lblTongTien.setText("0 VNĐ");

        modelChiTiet.fireTableDataChanged();

        tblChiTiet.clearSelection();

        revalidate();
        repaint();
    }

    private JButton taoNut(String ten, Color mau) {

        JButton btn = new JButton(ten);

        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(mau);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(140, 35));

        return btn;
    }
    private void loadPhieuSuaChua() {

        cboPhieuSuaChua.removeAllItems();

        // Thêm lựa chọn mặc định
        cboPhieuSuaChua.addItem("Chọn phiếu sửa chữa");

        try {

            DAO_KeToan dao = new DAO_KeToan();

            List<String> danhSach =
                    dao.layMaPhieuSuaChuaDaHoanThanh();

            for (String maPSC : danhSach) {
                cboPhieuSuaChua.addItem(maPSC);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi tải danh sách phiếu sửa chữa: "
                            + e.getMessage()
            );
        }
    }

    private String dinhDangTien(BigDecimal tien) {

        return String.format(
                "%,.0f",
                tien
        );
    }
    private String taoMaHoaDon() {
        DAO_KeToan dao = new DAO_KeToan();

        try {
            int soHoaDon = dao.laySoHoaDonLonNhat() + 1;

            return String.format("HD%02d", soHoaDon);

        } catch (SQLException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Không thể tạo mã hóa đơn!"
            );

            return "";
        }
    }

    // ================= CHẠY GIAO DIỆN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ManHinhLapHoaDon form = new ManHinhLapHoaDon();

            form.setVisible(true);
        });
    }
}
