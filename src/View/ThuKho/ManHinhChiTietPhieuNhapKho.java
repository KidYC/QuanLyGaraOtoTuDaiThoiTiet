package View.ThuKho;

import DAO.PhieuNhapKho_DAO;
import Entity.ChiTietPhieuNhap;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class ManHinhChiTietPhieuNhapKho extends JFrame implements ActionListener {

    private String maphieu;
    private String ngaynhap;
    private String nhacungcap;
    private String nhanvien;

    private JLabel lbltieude;

    private JTextField txtmaphieu;
    private JTextField txtngaynhap;
    private JTextField txtnhacungcap;
    private JTextField txtnhanvien;

    private JTable tblchitiet;
    private DefaultTableModel modelchitiet;

    private JLabel lbltongtien;
    private JButton btndong;

    private final PhieuNhapKho_DAO phieuNhapDAO = new PhieuNhapKho_DAO();
    private final DecimalFormat dinhdangtien = new DecimalFormat("#,###");

    public ManHinhChiTietPhieuNhapKho(String maphieu, String ngaynhap, String nhacungcap, String nhanvien) {
        this.maphieu = maphieu;
        this.ngaynhap = ngaynhap;
        this.nhacungcap = nhacungcap;
        this.nhanvien = nhanvien;

        setTitle("CHI TIẾT PHIẾU NHẬP KHO");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();
        napThongTin();
        napChiTiet();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel("CHI TIẾT PHIẾU NHẬP KHO");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 22));
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbltieude.setForeground(new Color(44, 62, 80));
        lbltieude.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lbltieude, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel(new BorderLayout(0, 10));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pThongTin = new JPanel(new GridLayout(2, 4, 10, 10));
        pThongTin.setBackground(Color.WHITE);
        pThongTin.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông tin phiếu nhập",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        txtmaphieu = new JTextField();
        txtngaynhap = new JTextField();
        txtnhacungcap = new JTextField();
        txtnhanvien = new JTextField();


        JTextField[] cacO = {txtmaphieu, txtngaynhap, txtnhacungcap, txtnhanvien};
        for (JTextField o : cacO) {
            o.setEditable(false);
            o.setBackground(new Color(236, 240, 241));
        }

        pThongTin.add(new JLabel("Mã Phiếu:"));
        pThongTin.add(txtmaphieu);
        pThongTin.add(new JLabel("Ngày Nhập:"));
        pThongTin.add(txtngaynhap);
        pThongTin.add(new JLabel("Nhà Cung Cấp:"));
        pThongTin.add(txtnhacungcap);
        pThongTin.add(new JLabel("Nhân Viên Lập:"));
        pThongTin.add(txtnhanvien);

        pCenter.add(pThongTin, BorderLayout.NORTH);

        String[] column = {"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị", "Số Lượng", "Đơn Giá", "Thành Tiền"};
        modelchitiet = new DefaultTableModel(column, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblchitiet = new JTable(modelchitiet);
        tblchitiet.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tblchitiet);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phụ tùng đã nhập"));
        pCenter.add(scroll, BorderLayout.CENTER);

        add(pCenter, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel(new BorderLayout());
        pSouth.setBackground(new Color(245, 245, 245));
        pSouth.setBorder(new EmptyBorder(5, 20, 12, 20));

        lbltongtien = new JLabel("Tổng tiền: 0 VNĐ");
        lbltongtien.setFont(new Font("Arial", Font.BOLD, 16));
        lbltongtien.setForeground(new Color(192, 57, 43));

        btndong = new JButton("Đóng");
        btndong.setPreferredSize(new Dimension(110, 35));
        btndong.setBackground(new Color(149, 165, 166));
        btndong.setForeground(Color.WHITE);
        btndong.setFocusPainted(false);

        pSouth.add(lbltongtien, BorderLayout.WEST);
        pSouth.add(btndong, BorderLayout.EAST);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btndong.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btndong) {
            xuLyDong();
        }
    }

    private void napThongTin() {
        txtmaphieu.setText(maphieu);
        txtngaynhap.setText(ngaynhap);
        txtnhacungcap.setText(nhacungcap);
        txtnhanvien.setText(nhanvien);
    }

    private void napChiTiet() {
        modelchitiet.setRowCount(0);
        double tong = 0;
        try {
            for (ChiTietPhieuNhap ct : phieuNhapDAO.getChiTietPhieuNhap(maphieu)) {
                modelchitiet.addRow(new Object[]{
                        ct.getMaPhuTung(),
                        ct.getTenPhuTung(),
                        ct.getDonViTinh(),
                        ct.getSoLuongNhap(),
                        dinhdangtien.format(ct.getDonGiaNhap()),
                        dinhdangtien.format(ct.tinhTongTien())});
                tong += ct.tinhTongTien();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải chi tiết phiếu nhập: " + e.getMessage());
        }
        lbltongtien.setText("Tổng tiền: " + dinhdangtien.format(tong) + " VNĐ");
    }

    private void xuLyDong() {
        this.dispose();
    }

    public static void main(String[] args) {
        ManHinhChiTietPhieuNhapKho form = new ManHinhChiTietPhieuNhapKho("", "", "", "");
        form.setVisible(true);
    }
}