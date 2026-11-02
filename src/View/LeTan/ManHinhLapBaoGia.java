package View.LeTan;

import DAO.PhieuSuaChua_DAO;
import Entity.ChiTietBaoGia_DTO;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManHinhLapBaoGia extends JFrame implements ActionListener {

    private JLabel lblTitle;

    private JTextField txtMaPhieu;
    private JTextField txtBienSo;
    private JTextField txtTenKH;

    private JTable tblChiTietBaoGia;
    private DefaultTableModel modelBaoGia;

    private JLabel lblTongTien;
    private JTextField txtChietKhau;
    private JLabel lblThanhTien;

    private JButton btnQuayLai;
    private JButton btnThemHangMuc;
    private JButton btnXoaHangMuc;
    private JButton btnLuuBaoGia;
    private JButton btnChuyenXacNhan;

    // Constructor nhận dữ liệu từ ManHinhDanhSachXeChoBaoGia
    public ManHinhLapBaoGia(String maPhieu, String bienSo, String tenKH) {
        setTitle("LẬP BÁO GIÁ SỬA CHỮA");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();

        // Gán dữ liệu từ màn hình danh sách truyền sang
        txtMaPhieu.setText(maPhieu != null ? maPhieu : "");
        txtBienSo.setText(bienSo != null ? bienSo : "");
        txtTenKH.setText(tenKH != null ? tenKH : "");

        // Tải chi tiết dịch vụ/phụ tùng từ CSDL theo mã phiếu
        if (maPhieu != null && !maPhieu.isEmpty()) {
            loadDataFromDB(maPhieu);
        }
        tinhTongTien();
    }

    public ManHinhLapBaoGia() {
        this("", "", "");
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        // pNorth
        lblTitle = new JLabel("LẬP BÁO GIÁ SỬA CHỮA");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // pCenter
        JPanel pCenter = new JPanel(new BorderLayout(10, 10));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pThongTin = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        pThongTin.setBackground(Color.WHITE);
        pThongTin.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông tin khách hàng & Xe",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(5, 10, 5, 10)
        ));

        pThongTin.add(new JLabel("Mã Phiếu:"));
        txtMaPhieu = new JTextField(12);
        txtMaPhieu.setEditable(false);
        pThongTin.add(txtMaPhieu);

        pThongTin.add(new JLabel("Tên KH:"));
        txtTenKH = new JTextField(15);
        txtTenKH.setEditable(false);
        pThongTin.add(txtTenKH);

        pThongTin.add(new JLabel("Biển Số Xe:"));
        txtBienSo = new JTextField(10);
        txtBienSo.setEditable(false);
        pThongTin.add(txtBienSo);

        pCenter.add(pThongTin, BorderLayout.NORTH);

        JPanel pTableContent = new JPanel(new BorderLayout(5, 5));
        pTableContent.setBackground(new Color(245, 245, 245));

        String[] cols = {"STT", "Loại", "Mã SP/DV", "Tên Hạng Mục", "Số Lượng", "Đơn Giá (VNĐ)", "Thành Tiền (VNĐ)"};
        modelBaoGia = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Chỉ cho phép sửa số lượng
            }
        };

        tblChiTietBaoGia = new JTable(modelBaoGia);
        tblChiTietBaoGia.setRowHeight(28);
        tblChiTietBaoGia.setFont(new Font("Arial", Font.PLAIN, 13));

        JTableHeader header = tblChiTietBaoGia.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tblChiTietBaoGia);
        scrollPane.getViewport().setBackground(Color.WHITE);
        pTableContent.add(scrollPane, BorderLayout.CENTER);

        JPanel pActionTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pActionTable.setBackground(new Color(245, 245, 245));

        // Khởi tạo 2 nút thao tác bảng (Sửa lỗi NullPointerException)

        btnXoaHangMuc = new JButton("- Xóa Đã Chọn");
        btnXoaHangMuc.setBackground(new Color(231, 76, 60));
        btnXoaHangMuc.setForeground(Color.WHITE);
        btnXoaHangMuc.setFocusPainted(false);

        pActionTable.add(btnXoaHangMuc);
        pTableContent.add(pActionTable, BorderLayout.SOUTH);

        pCenter.add(pTableContent, BorderLayout.CENTER);

        JPanel pTongKet = new JPanel(new GridLayout(3, 2, 10, 10));
        pTongKet.setBackground(new Color(245, 245, 245));
        pTongKet.setBorder(new EmptyBorder(10, 300, 10, 0));

        pTongKet.add(new JLabel("Tổng Tiền Trước Thuế:"));
        lblTongTien = new JLabel("0 VNĐ");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));
        pTongKet.add(lblTongTien);

        pTongKet.add(new JLabel("Chiết Khấu (%):"));
        txtChietKhau = new JTextField("0");
        txtChietKhau.setHorizontalAlignment(JTextField.RIGHT);
        pTongKet.add(txtChietKhau);

        pTongKet.add(new JLabel("TỔNG THANH TOÁN:"));
        lblThanhTien = new JLabel("0 VNĐ");
        lblThanhTien.setFont(new Font("Arial", Font.BOLD, 16));
        lblThanhTien.setForeground(new Color(192, 57, 43));
        pTongKet.add(lblThanhTien);

        pCenter.add(pTongKet, BorderLayout.SOUTH);

        add(pCenter, BorderLayout.CENTER);

        // pSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnQuayLai = new JButton("Quay Lại");
        btnQuayLai.setPreferredSize(new Dimension(110, 35));
        btnQuayLai.setBackground(new Color(149, 165, 166));
        btnQuayLai.setForeground(Color.WHITE);
        btnQuayLai.setFocusPainted(false);

        btnLuuBaoGia = new JButton("Lưu Báo Giá (Nháp)");
        btnLuuBaoGia.setPreferredSize(new Dimension(150, 35));
        btnLuuBaoGia.setBackground(new Color(243, 156, 18));
        btnLuuBaoGia.setForeground(Color.WHITE);
        btnLuuBaoGia.setFont(new Font("Arial", Font.BOLD, 12));
        btnLuuBaoGia.setFocusPainted(false);

        btnChuyenXacNhan = new JButton("Lưu & Chuyển Xác Nhận");
        btnChuyenXacNhan.setPreferredSize(new Dimension(190, 35));
        btnChuyenXacNhan.setBackground(new Color(46, 204, 113));
        btnChuyenXacNhan.setForeground(Color.WHITE);
        btnChuyenXacNhan.setFont(new Font("Arial", Font.BOLD, 12));
        btnChuyenXacNhan.setFocusPainted(false);

        pSouth.add(btnQuayLai);
        pSouth.add(btnLuuBaoGia);
        pSouth.add(btnChuyenXacNhan);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btnQuayLai.addActionListener(this);
        btnXoaHangMuc.addActionListener(this);
        btnLuuBaoGia.addActionListener(this);
        btnChuyenXacNhan.addActionListener(this);
        txtChietKhau.addActionListener(e -> tinhTongTien());

        modelBaoGia.addTableModelListener(e -> {
            if (e.getColumn() == 4) { // Khi sửa số lượng
                int row = e.getFirstRow();
                try {
                    int soLuongMoi = Integer.parseInt(modelBaoGia.getValueAt(row, 4).toString());
                    double donGia = Double.parseDouble(modelBaoGia.getValueAt(row, 5).toString());
                    modelBaoGia.setValueAt(soLuongMoi * donGia, row, 6);
                    tinhTongTien();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!");
                }
            }
        });
    }

    private void loadDataFromDB(String maPhieu) {
        modelBaoGia.setRowCount(0);
        PhieuSuaChua_DAO dao = new PhieuSuaChua_DAO();

        try {
            List<ChiTietBaoGia_DTO> list = dao.getChiTietBaoGiaByMaPhieu(maPhieu);
            int stt = 1;
            for (ChiTietBaoGia_DTO item : list) {
                double thanhTien = item.getSoLuong() * item.getDonGia();
                modelBaoGia.addRow(new Object[]{
                        stt++,
                        item.getLoai(),
                        item.getMaHangMuc(),
                        item.getTenHangMuc(),
                        item.getSoLuong(),
                        item.getDonGia(),
                        thanhTien
                });
            }
            tinhTongTien();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi lấy chi tiết báo giá từ CSDL: " + e.getMessage(),
                    "Lỗi CSDL",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tinhTongTien() {
        double tongTien = 0;
        for (int i = 0; i < modelBaoGia.getRowCount(); i++) {
            double thanhTien = Double.parseDouble(modelBaoGia.getValueAt(i, 6).toString());
            tongTien += thanhTien;
        }

        double chietKhau = 0;
        try {
            chietKhau = Double.parseDouble(txtChietKhau.getText().trim());
        } catch (NumberFormatException ignored) {}

        double thanhTienCuoi = tongTien - (tongTien * (chietKhau / 100.0));

        lblTongTien.setText(String.format("%,.0f VNĐ", tongTien));
        lblThanhTien.setText(String.format("%,.0f VNĐ", thanhTienCuoi));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnQuayLai) {
            xuLyQuayLai();
        } else if (source == btnLuuBaoGia) {
            xuLyLuuBaoGia();
        } else if (source == btnChuyenXacNhan) {
            xuLyChuyenXacNhan();
        } else if (source == btnXoaHangMuc) {
            int selectedRow = tblChiTietBaoGia.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hạng mục trong bảng để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maPhieu = txtMaPhieu.getText().trim();
            String loai = modelBaoGia.getValueAt(selectedRow, 1).toString();
            String maHangMuc = modelBaoGia.getValueAt(selectedRow, 2).toString();
            String tenHangMuc = modelBaoGia.getValueAt(selectedRow, 3).toString();

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa hạng mục '" + tenHangMuc + "' khỏi cơ sở dữ liệu?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                PhieuSuaChua_DAO dao = new PhieuSuaChua_DAO();
                try {
                    boolean isDeleted = dao.xoaHangMucBaoGia(maPhieu, loai, maHangMuc);

                    modelBaoGia.removeRow(selectedRow);
                    tinhTongTien();

                    for (int i = 0; i < modelBaoGia.getRowCount(); i++) {
                        modelBaoGia.setValueAt(i + 1, i, 0);
                    }

                    JOptionPane.showMessageDialog(this, "Đã xóa hạng mục thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa hạng mục khỏi CSDL: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                }
            }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!");
            }
    }

    private void xuLyLuuBaoGia() {
        tinhTongTien();
        JOptionPane.showMessageDialog(this,
                "Đã lưu bản nháp Báo Giá cho phiếu " + txtMaPhieu.getText() + " thành công!",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void xuLyChuyenXacNhan() {
        if (modelBaoGia.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Báo giá đang trống. Vui lòng thêm hạng mục!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opt = JOptionPane.showConfirmDialog(this,
                "Lưu Báo giá này vào CSDL và chuyển trạng thái phiếu sang 'Chờ xuất kho'?",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opt == JOptionPane.YES_OPTION) {
            List<ChiTietBaoGia_DTO> danhSachChiTiet = new ArrayList<>();
            for (int i = 0; i < modelBaoGia.getRowCount(); i++) {
                String loai = modelBaoGia.getValueAt(i, 1).toString();
                String maHangMuc = modelBaoGia.getValueAt(i, 2).toString();
                String tenHangMuc = modelBaoGia.getValueAt(i, 3).toString();
                int soLuong = Integer.parseInt(modelBaoGia.getValueAt(i, 4).toString());
                double donGia = Double.parseDouble(modelBaoGia.getValueAt(i, 5).toString());

                danhSachChiTiet.add(new ChiTietBaoGia_DTO(loai, maHangMuc, tenHangMuc, soLuong, donGia));
            }

            PhieuSuaChua_DAO dao = new PhieuSuaChua_DAO();


            boolean successBaoGia = dao.luuBaoGiaSuaChua(txtMaPhieu.getText().trim(), txtBienSo.getText().trim(), danhSachChiTiet);

            if (successBaoGia) {
                boolean successTrangThai = dao.capNhatTrangThaiPhieu(txtMaPhieu.getText().trim(), "Chờ xuất kho");

                if (successTrangThai) {
                    JOptionPane.showMessageDialog(this,
                            "Đã chuyển báo giá cho khách hàng xác nhận!\nTrạng thái phiếu: 'Chờ xuất kho'.",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    ManHinhDanhSachXeChoBaoGia formDanhSach = new ManHinhDanhSachXeChoBaoGia();
                    formDanhSach.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Lưu báo giá thành công nhưng không thể cập nhật trạng thái!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Lưu báo giá thất bại. Vui lòng kiểm tra lại!",
                        "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xuLyQuayLai() {
        this.dispose();
        ManHinhDanhSachXeChoBaoGia formDanhSach = new ManHinhDanhSachXeChoBaoGia();
        formDanhSach.setVisible(true);
    }

    public static void main(String[] args) {
        ManHinhLapBaoGia form = new ManHinhLapBaoGia();
        form.setVisible(true);
    }
}