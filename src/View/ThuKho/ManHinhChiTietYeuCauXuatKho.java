package View.ThuKho;

import DAO.DanhSachPhuTung_DAO;
import DAO.PhuTung_DAO;
import Entity.DanhSachPhuTung;
import Entity.PhuTung;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class ManHinhChiTietYeuCauXuatKho extends JFrame implements ActionListener {

    private String mayeucau;
    private String ngayyeucau;
    private String nguoiyeucau;
    private String bienso;
    private String trangthai;

    private JLabel lbltieude;

    private JTextField txtmayeucau;
    private JTextField txtngayyeucau;
    private JTextField txtnguoiyeucau;
    private JTextField txtbienso;
    private JTextField txttrangthai;

    private JTable tblchitiet;
    private DefaultTableModel modelchitiet;

    private JLabel lbltongket;
    private JButton btndong;

    private final DanhSachPhuTung_DAO danhSachPhuTungDAO = new DanhSachPhuTung_DAO();
    private final PhuTung_DAO phuTungDAO = new PhuTung_DAO();
    private final DecimalFormat dinhdangtien = new DecimalFormat("#,###");

    public ManHinhChiTietYeuCauXuatKho(String mayeucau, String ngayyeucau, String nguoiyeucau, String bienso, String trangthai) {
        this.mayeucau = mayeucau;
        this.ngayyeucau = ngayyeucau;
        this.nguoiyeucau = nguoiyeucau;
        this.bienso = bienso;
        this.trangthai = trangthai;

        setTitle("CHI TIẾT YÊU CẦU XUẤT KHO");
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
        lbltieude = new JLabel("CHI TIẾT YÊU CẦU XUẤT KHO");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 22));
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbltieude.setForeground(new Color(44, 62, 80));
        lbltieude.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lbltieude, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel(new BorderLayout(0, 10));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pThongTin = new JPanel(new GridLayout(3, 4, 10, 10));
        pThongTin.setBackground(Color.WHITE);
        pThongTin.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông tin yêu cầu xuất kho",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        txtmayeucau = new JTextField();
        txtngayyeucau = new JTextField();
        txtnguoiyeucau = new JTextField();
        txtbienso = new JTextField();
        txttrangthai = new JTextField();

        JTextField[] cacO = {txtmayeucau, txtngayyeucau, txtnguoiyeucau, txtbienso, txttrangthai};
        for (JTextField o : cacO) {
            o.setEditable(false);
            o.setBackground(new Color(236, 240, 241));
        }

        pThongTin.add(new JLabel("Mã Yêu Cầu:"));
        pThongTin.add(txtmayeucau);
        pThongTin.add(new JLabel("Ngày Yêu Cầu:"));
        pThongTin.add(txtngayyeucau);
        pThongTin.add(new JLabel("Người Yêu Cầu:"));
        pThongTin.add(txtnguoiyeucau);
        pThongTin.add(new JLabel("Biển Số Xe:"));
        pThongTin.add(txtbienso);
        pThongTin.add(new JLabel("Trạng Thái:"));
        pThongTin.add(txttrangthai);
        pThongTin.add(new JLabel(""));
        pThongTin.add(new JLabel(""));

        pCenter.add(pThongTin, BorderLayout.NORTH);

        String[] column = {"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị", "SL Yêu Cầu", "SL Tồn Kho", "Đơn Giá Bán", "Thành Tiền", "Tình Trạng Kho"};
        modelchitiet = new DefaultTableModel(column, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblchitiet = new JTable(modelchitiet);
        tblchitiet.setRowHeight(25);

        tblchitiet.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    c.setForeground("Thiếu hàng".equals(value) ? Color.RED : new Color(39, 174, 96));
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tblchitiet);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phụ tùng cần xuất"));
        pCenter.add(scroll, BorderLayout.CENTER);

        add(pCenter, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel(new BorderLayout());
        pSouth.setBackground(new Color(245, 245, 245));
        pSouth.setBorder(new EmptyBorder(5, 20, 12, 20));

        lbltongket = new JLabel("Tổng tiền: 0 VNĐ");
        lbltongket.setFont(new Font("Arial", Font.BOLD, 16));
        lbltongket.setForeground(new Color(192, 57, 43));

        btndong = new JButton("Đóng");
        btndong.setPreferredSize(new Dimension(110, 35));
        btndong.setBackground(new Color(149, 165, 166));
        btndong.setForeground(Color.WHITE);
        btndong.setFocusPainted(false);

        pSouth.add(lbltongket, BorderLayout.WEST);
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
        txtmayeucau.setText(mayeucau);
        txtngayyeucau.setText(ngayyeucau);
        txtnguoiyeucau.setText(nguoiyeucau);
        txtbienso.setText(bienso);
        txttrangthai.setText(trangthai);
    }

    private void napChiTiet() {
        modelchitiet.setRowCount(0);
        double tong = 0;
        int sothieu = 0;
        try {
            List<PhuTung> dsphutung = phuTungDAO.getAllPhuTung();
            List<DanhSachPhuTung> dsct = danhSachPhuTungDAO.getDSPhuTungByMaPhieu(mayeucau);
            for (DanhSachPhuTung ct : dsct) {
                String ten = "";
                String donvi = "";
                int tonkho = 0;
                for (PhuTung pt : dsphutung) {
                    if (pt.getMaPhuTung().equalsIgnoreCase(ct.getMaPhuTung())) {
                        ten = pt.getTenPhuTung();
                        donvi = pt.getDonViTinh();
                        tonkho = pt.getSoLuongTon();
                        break;
                    }
                }
                double thanhtien = ct.getSoLuongPhuTung() * ct.getDonGiaBan();
                String tinhtrang = (ct.getSoLuongPhuTung() <= tonkho) ? "Đủ hàng" : "Thiếu hàng";
                if (tinhtrang.equals("Thiếu hàng")) sothieu++;

                modelchitiet.addRow(new Object[]{
                        ct.getMaPhuTung(),
                        ten,
                        donvi,
                        ct.getSoLuongPhuTung(),
                        tonkho,
                        dinhdangtien.format(ct.getDonGiaBan()),
                        dinhdangtien.format(thanhtien),
                        tinhtrang});
                tong += thanhtien;
            }
            lbltongket.setText(dsct.size() + " phụ tùng  |  Thiếu hàng: " + sothieu
                    + "  |  Tổng tiền: " + dinhdangtien.format(tong) + " VNĐ");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải chi tiết yêu cầu xuất kho: " + e.getMessage());
        }
    }

    private void xuLyDong() {
        this.dispose();
    }

    public static void main(String[] args) {
        ManHinhChiTietYeuCauXuatKho form = new ManHinhChiTietYeuCauXuatKho("", "", "", "", "");
        form.setVisible(true);
    }
}
