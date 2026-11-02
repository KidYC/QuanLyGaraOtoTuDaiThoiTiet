package View.ThuKho;

import DAO.PhieuKiemKe_DAO;
import Entity.ChiTietKiemKe;
import Entity.PhieuKiemKe;

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
import java.text.SimpleDateFormat;

public class ManHinhChiTietPhieuKiemKe extends JFrame implements ActionListener {

    private String maphieu;

    private JLabel lbltieude;

    private JTextField txtmaphieu;
    private JTextField txtngaykiemke;
    private JTextField txtnguoikiemke;
    private JTextField txttrangthai;

    private JTable tblchitiet;
    private DefaultTableModel modelchitiet;

    private JLabel lbltongket;
    private JButton btndong;

    private final PhieuKiemKe_DAO phieuKiemKeDAO = new PhieuKiemKe_DAO();

    public ManHinhChiTietPhieuKiemKe(String maphieu) {
        this.maphieu = maphieu;

        setTitle("CHI TIẾT PHIẾU KIỂM KÊ");
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
        lbltieude = new JLabel("CHI TIẾT PHIẾU KIỂM KÊ");
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
                "Thông tin phiếu kiểm kê",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        txtmaphieu = new JTextField();
        txtngaykiemke = new JTextField();
        txtnguoikiemke = new JTextField();
        txttrangthai = new JTextField();

        // các ô chỉ để xem, không cho sửa
        JTextField[] cacO = {txtmaphieu, txtngaykiemke, txtnguoikiemke, txttrangthai};
        for (JTextField o : cacO) {
            o.setEditable(false);
            o.setBackground(new Color(236, 240, 241));
        }

        pThongTin.add(new JLabel("Mã Phiếu:"));
        pThongTin.add(txtmaphieu);
        pThongTin.add(new JLabel("Ngày Kiểm Kê:"));
        pThongTin.add(txtngaykiemke);
        pThongTin.add(new JLabel("Người Kiểm Kê:"));
        pThongTin.add(txtnguoikiemke);
        pThongTin.add(new JLabel("Trạng Thái:"));
        pThongTin.add(txttrangthai);

        pCenter.add(pThongTin, BorderLayout.NORTH);

        String[] column = {"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị", "SL Hệ Thống", "SL Thực Tế", "Chênh Lệch", "Lý Do Chênh Lệch"};
        modelchitiet = new DefaultTableModel(column, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblchitiet = new JTable(modelchitiet);
        tblchitiet.setRowHeight(25);

        // chênh lệch âm (thiếu hàng) tô đỏ, dương (dư hàng) tô xanh
        tblchitiet.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    int chenhlech = Integer.parseInt(value.toString());
                    if (chenhlech < 0) {
                        c.setForeground(Color.RED);
                    } else if (chenhlech > 0) {
                        c.setForeground(new Color(39, 174, 96));
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tblchitiet);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phụ tùng đã kiểm kê"));
        pCenter.add(scroll, BorderLayout.CENTER);

        add(pCenter, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel(new BorderLayout());
        pSouth.setBackground(new Color(245, 245, 245));
        pSouth.setBorder(new EmptyBorder(5, 20, 12, 20));

        lbltongket = new JLabel("Số mặt hàng lệch: 0");
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

    // Lấy thông tin chung của phiếu từ SQL
    private void napThongTin() {
        txtmaphieu.setText(maphieu);
        try {
            PhieuKiemKe pk = phieuKiemKeDAO.timPhieuKiemKeTheoMa(maphieu);
            if (pk == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu kiểm kê " + maphieu + "!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (pk.getNgayKiemKe() != null) {
                txtngaykiemke.setText(new SimpleDateFormat("dd/MM/yyyy").format(pk.getNgayKiemKe()));
            }
            String hoten = pk.getThuKho().getHoTen();
            txtnguoikiemke.setText(pk.getThuKho().getMaNhanVien() + (hoten == null ? "" : " - " + hoten));
            txttrangthai.setText(pk.getTrangThai());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải thông tin phiếu kiểm kê: " + e.getMessage());
        }
    }

    private void napChiTiet() {
        modelchitiet.setRowCount(0);
        int somatlech = 0;
        int tongchenhlech = 0;
        try {
            for (ChiTietKiemKe ct : phieuKiemKeDAO.getChiTietKiemKe(maphieu)) {
                modelchitiet.addRow(new Object[]{
                        ct.getMaPhuTung(),
                        ct.getTenPhuTung(),
                        ct.getDonViTinh(),
                        ct.getSoLuongHeThong(),
                        ct.getSoLuongThucTe(),
                        ct.getChenhLech(),
                        ct.getLyDoChenhLech() == null ? "" : ct.getLyDoChenhLech()});
                if (ct.getChenhLech() != 0) {
                    somatlech++;
                }
                tongchenhlech += ct.getChenhLech();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải chi tiết phiếu kiểm kê: " + e.getMessage());
        }
        lbltongket.setText("Số mặt hàng lệch: " + somatlech + "    Tổng chênh lệch: " + tongchenhlech);
    }

    private void xuLyDong() {
        this.dispose();
    }

    public static void main(String[] args) {
        ManHinhChiTietPhieuKiemKe form = new ManHinhChiTietPhieuKiemKe("KK001");
        form.setVisible(true);
    }
}