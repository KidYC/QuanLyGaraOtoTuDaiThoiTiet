package View.LeTan;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public ManHinhLapBaoGia(String maPhieu, String bienSo, String tenKH) {
        setTitle("LẬP BÁO GIÁ SỬA CHỮA");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();

        txtMaPhieu.setText(maPhieu != null ? maPhieu : "KH001");
        txtBienSo.setText(bienSo != null ? bienSo : "51F-123.45");
        txtTenKH.setText(tenKH != null ? tenKH : "Nguyễn Văn A");

        loadDummyData();
        tinhTongTien();
    }

    public ManHinhLapBaoGia() {
        this("KH001", "51F-123.45", "Nguyễn Văn A");
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lblTitle = new JLabel("LẬP BÁO GIÁ SỬA CHỮA");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        //pCen
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
        txtMaPhieu = new JTextField(10);
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
                return column == 4;
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

        btnThemHangMuc = new JButton("+ Thêm Dịch Vụ / Phụ Tùng");
        btnThemHangMuc.setBackground(new Color(52, 152, 219));
        btnThemHangMuc.setForeground(Color.WHITE);
        btnThemHangMuc.setFocusPainted(false);

        btnXoaHangMuc = new JButton("- Xóa Đã Chọn");
        btnXoaHangMuc.setBackground(new Color(231, 76, 60));
        btnXoaHangMuc.setForeground(Color.WHITE);
        btnXoaHangMuc.setFocusPainted(false);

        pActionTable.add(btnThemHangMuc);
        pActionTable.add(btnXoaHangMuc);
        pTableContent.add(pActionTable, BorderLayout.SOUTH);

        pCenter.add(pTableContent, BorderLayout.CENTER);

        JPanel pTongKet = new JPanel(new GridLayout(3, 2, 10, 10));
        pTongKet.setBackground(new Color(245, 245, 245));
        pTongKet.setBorder(new EmptyBorder(10, 300, 10, 0)); // Đẩy sang phải

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

        //pSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnQuayLai = new JButton("Quay Lại");
        btnQuayLai.setPreferredSize(new Dimension(110, 35));
        btnQuayLai.setBackground(new Color(149, 165, 166));
        btnQuayLai.setForeground(Color.WHITE);
        btnQuayLai.setFocusPainted(false);

        btnLuuBaoGia = new JButton("Lưu Báo Giá");
        btnLuuBaoGia.setPreferredSize(new Dimension(130, 35));
        btnLuuBaoGia.setBackground(new Color(243, 156, 18));
        btnLuuBaoGia.setForeground(Color.WHITE);
        btnLuuBaoGia.setFont(new Font("Arial", Font.BOLD, 12));
        btnLuuBaoGia.setFocusPainted(false);

        btnChuyenXacNhan = new JButton("Chuyển Xác Nhận");
        btnChuyenXacNhan.setPreferredSize(new Dimension(150, 35));
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
        btnThemHangMuc.addActionListener(this);
        btnXoaHangMuc.addActionListener(this);
        btnLuuBaoGia.addActionListener(this);
        btnChuyenXacNhan.addActionListener(this);
        txtChietKhau.addActionListener(e -> tinhTongTien());
    }

    private void loadDummyData() {
        modelBaoGia.addRow(new Object[]{1, "Dịch vụ", "DV01", "Kiểm tra tổng quát", 1, 200000, 200000});
        modelBaoGia.addRow(new Object[]{2, "Phụ tùng", "PT15", "Nhớt động cơ Castrol", 4, 150000, 600000});
        modelBaoGia.addRow(new Object[]{3, "Phụ tùng", "PT22", "Lọc nhớt Toyota", 1, 180000, 180000});
        modelBaoGia.addRow(new Object[]{4, "Dịch vụ", "DV05", "Vệ sinh buồng đốt", 1, 450000, 450000});
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
        } else if (source == btnThemHangMuc) {
            JOptionPane.showMessageDialog(this, "Tính năng mở danh sách Phụ tùng/Dịch vụ đang được phát triển!");
        } else if (source == btnXoaHangMuc) {
            int selectedRow = tblChiTietBaoGia.getSelectedRow();
            if (selectedRow != -1) {
                modelBaoGia.removeRow(selectedRow);
                tinhTongTien();
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!");
            }
        }
    }

    private void xuLyLuuBaoGia() {
        tinhTongTien();
        JOptionPane.showMessageDialog(this,
                "Đã lưu bản nháp Báo Giá cho phiếu " + txtMaPhieu.getText() + " thành công!",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void xuLyChuyenXacNhan() {
        int opt = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn chuyển Báo giá này sang trạng thái Chờ Xác Nhận từ khách hàng không?",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opt == JOptionPane.YES_OPTION) {
             ManHinhXacNhanPhuongAn formXacNhan = new ManHinhXacNhanPhuongAn();
             formXacNhan.setVisible(true);
            this.dispose();

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