
package View.KeToan;

import DAO.DAO_KeToan;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ManHinhChiTietHDVaThanhToan extends JFrame
        implements ActionListener {

    // Màu giao diện
    private static final Color MAU_NEN = new Color(244, 246, 248);
    private static final Color MAU_THE = Color.WHITE;
    private static final Color MAU_XANH = new Color(186, 230, 253);
    private static final Color MAU_CHU = new Color(17, 24, 39);
    private static final Color MAU_PHU = new Color(107, 114, 128);
    private static final Color MAU_VIEN = new Color(226, 232, 240);
    private static final Color MAU_DO = new Color(254, 226, 226);

    // Font giao diện
    private static final Font FONT_TIEU_DE =
            new Font("Segoe UI", Font.BOLD, 24);
    private static final Font FONT_MUC =
            new Font("Segoe UI", Font.BOLD, 17);
    private static final Font FONT_NHAN =
            new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_DAM =
            new Font("Segoe UI", Font.BOLD, 14);

    private final JPanel pCenter;
    private final JPanel pNorth;
    private final JLabel lbltieude;
    private final JLabel ttchung;
    private final JLabel mahd;
    private final JPanel pCenter1;
    private final JTextField txtmahd;
    private final JLabel ngaylap;
    private final JTextField txtngaylap;
    private final JLabel maphieusc;
    private final JTextField txtmaphieu;
    private final JLabel khachhang;
    private final JTextField txtkhachhang;
    private final JLabel bienso;
    private final JTextField txtbienso;
    private final JLabel sdt;
    private final JTextField txtsdt;
    private final JLabel chitietsuachuavaphutung;
    private final JPanel pCenter2;
    private final DefaultTableModel model;
    private final JTable table;
    private final JScrollPane srcoll;
    private final JLabel tomtat;
    private final JPanel pCenter3;
    private final JLabel tongtienphutung;
    private final JTextField txttongtienphutung;
    private final JLabel tongtiendichvu;
    private final JTextField txttongtiendichvu;
    private final JLabel khuyenmai;
    private final JTextField txtkhuyenmai;
    private final JLabel tongcong;
    private final JTextField txttongcong;
    private final JLabel trangthai;
    private final JTextField txttrangthai;
    private final JPanel pCenter4;

    private JButton btntim;
    private JButton thanhtoan;
    private JButton huybo;
    private JButton quaylai;

    public ManHinhChiTietHDVaThanhToan() {
        super("Chi tiết hóa đơn và thanh toán");

        setSize(1200, 850);
        setMinimumSize(new Dimension(950, 700));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));

        getContentPane().setBackground(MAU_NEN);


        pNorth = new JPanel();
        pNorth.setLayout(new BoxLayout(pNorth, BoxLayout.Y_AXIS));
        pNorth.setBackground(MAU_NEN);
        pNorth.setBorder(
                BorderFactory.createEmptyBorder(22, 28, 18, 28)
        );
        pNorth.setAlignmentX(Component.LEFT_ALIGNMENT);

        lbltieude = new JLabel("CHI TIẾT HÓA ĐƠN");
        lbltieude.setFont(FONT_TIEU_DE);
        lbltieude.setForeground(MAU_CHU);
        lbltieude.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblMoTa = new JLabel(
                "Thông tin hóa đơn, chi tiết phụ tùng và thanh toán"
        );
        lblMoTa.setFont(FONT_NHAN);
        lblMoTa.setForeground(MAU_PHU);
        lblMoTa.setAlignmentX(Component.LEFT_ALIGNMENT);

        pNorth.add(lbltieude);
        pNorth.add(Box.createVerticalStrut(5));
        pNorth.add(lblMoTa);

        add(pNorth, BorderLayout.NORTH);


        pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
        pCenter.setBackground(MAU_NEN);
        pCenter.setBorder(
                BorderFactory.createEmptyBorder(0, 28, 24, 28)
        );
        pCenter.setAlignmentX(Component.LEFT_ALIGNMENT);

        ttchung = taoTieuDeMuc("THÔNG TIN CHUNG");

        pCenter1 = new JPanel(new GridLayout(3, 4, 18, 15));
        pCenter1.setBackground(MAU_THE);
        pCenter1.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
        pCenter1.setAlignmentX(Component.LEFT_ALIGNMENT);

        mahd = taoNhan("Mã hóa đơn");
        txtmahd = taoTextField("");

        ngaylap = taoNhan("Ngày lập");
        txtngaylap = taoTextField("");

        maphieusc = taoNhan("Mã phiếu sửa chữa");
        txtmaphieu = taoTextField("");

        khachhang = taoNhan("Khách hàng");
        txtkhachhang = taoTextField("");

        bienso = taoNhan("Biển số xe");
        txtbienso = taoTextField("");

        sdt = taoNhan("Số điện thoại");
        txtsdt = taoTextField("");

        pCenter1.add(mahd);
        pCenter1.add(txtmahd);
        pCenter1.add(ngaylap);
        pCenter1.add(txtngaylap);

        pCenter1.add(maphieusc);
        pCenter1.add(txtmaphieu);
        pCenter1.add(khachhang);
        pCenter1.add(txtkhachhang);

        pCenter1.add(bienso);
        pCenter1.add(txtbienso);
        pCenter1.add(sdt);
        pCenter1.add(txtsdt);

        JPanel theThongTin = taoThe(pCenter1);

        // Nút tìm kiếm
        btntim = taoNutBoGoc("Tìm hóa đơn", MAU_XANH);
        btntim.addActionListener(this);

        JPanel pTimKiem = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 0, 0)
        );
        pTimKiem.setBackground(MAU_NEN);
        pTimKiem.setAlignmentX(Component.LEFT_ALIGNMENT);
        pTimKiem.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 52)
        );
        pTimKiem.add(btntim);


        chitietsuachuavaphutung =
                taoTieuDeMuc("CHI TIẾT SỬA CHỮA & PHỤ TÙNG");

        pCenter2 = new JPanel(new BorderLayout());
        pCenter2.setBackground(MAU_THE);
        pCenter2.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        pCenter2.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] columns = {
                "STT",
                "Mã hóa đơn",
                "Mã phiếu sửa chữa",
                "Biển số xe",
                "Mã phụ tùng",
                "Tên phụ tùng",
                "Số lượng",
                "Đơn giá",
                "Thành tiền"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(34);
        table.setFont(FONT_NHAN);
        table.setForeground(MAU_CHU);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(MAU_CHU);
        table.setGridColor(MAU_VIEN);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getTableHeader().setFont(FONT_DAM);
        table.getTableHeader().setBackground(MAU_XANH);
        table.getTableHeader().setForeground(MAU_CHU);
        table.getTableHeader().setPreferredSize(
                new Dimension(0, 40)
        );
        table.getTableHeader().setReorderingAllowed(false);

        int[] doRongCot = {
                55, 110, 155, 110, 110, 180, 80, 120, 140
        };

        for (int i = 0; i < doRongCot.length; i++) {
            table.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(doRongCot[i]);
        }

        DefaultTableCellRenderer canGiua =
                new DefaultTableCellRenderer();
        canGiua.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel()
                .getColumn(0)
                .setCellRenderer(canGiua);
        table.getColumnModel()
                .getColumn(6)
                .setCellRenderer(canGiua);

        DefaultTableCellRenderer canPhai =
                new DefaultTableCellRenderer();
        canPhai.setHorizontalAlignment(SwingConstants.RIGHT);
        canPhai.setBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 12)
        );

        table.getColumnModel()
                .getColumn(7)
                .setCellRenderer(canPhai);
        table.getColumnModel()
                .getColumn(8)
                .setCellRenderer(canPhai);

        srcoll = new JScrollPane(table);
        srcoll.setBorder(BorderFactory.createLineBorder(MAU_VIEN));
        srcoll.getViewport().setBackground(Color.WHITE);
        srcoll.setAlignmentX(Component.LEFT_ALIGNMENT);

        pCenter2.add(srcoll, BorderLayout.CENTER);
        pCenter2.setPreferredSize(new Dimension(1000, 250));
        pCenter2.setMinimumSize(new Dimension(500, 200));
        pCenter2.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 280)
        );

        JPanel theBang = taoThe(pCenter2);


        tomtat = taoTieuDeMuc("TÓM TẮT & THANH TOÁN");

        pCenter3 = new JPanel(new GridLayout(5, 2, 18, 10));
        pCenter3.setBackground(MAU_THE);
        pCenter3.setBorder(
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        );
        pCenter3.setAlignmentX(Component.LEFT_ALIGNMENT);

        tongtienphutung = taoNhan("Tổng tiền phụ tùng");
        txttongtienphutung = taoTextField("");

        tongtiendichvu = taoNhan("Tổng tiền dịch vụ");
        txttongtiendichvu = taoTextField("");

        khuyenmai = taoNhan("Khuyến mãi");
        txtkhuyenmai = taoTextField("0");

        tongcong = taoNhan("TỔNG CỘNG");
        txttongcong = taoTextField("");
        txttongcong.setFont(new Font("Segoe UI", Font.BOLD, 17));
        txttongcong.setForeground(new Color(3, 105, 161));

        trangthai = taoNhan("Trạng thái");
        txttrangthai = taoTextField("Chờ thanh toán");

        pCenter3.add(tongtienphutung);
        pCenter3.add(txttongtienphutung);

        pCenter3.add(tongtiendichvu);
        pCenter3.add(txttongtiendichvu);

        pCenter3.add(khuyenmai);
        pCenter3.add(txtkhuyenmai);

        pCenter3.add(tongcong);
        pCenter3.add(txttongcong);

        pCenter3.add(trangthai);
        pCenter3.add(txttrangthai);

        JPanel theThanhToan = taoThe(pCenter3);


        pCenter4 = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 12, 0)
        );
        pCenter4.setBackground(MAU_NEN);
        pCenter4.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter4.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 55)
        );

        thanhtoan = taoNutBoGoc("Thanh toán", MAU_XANH);
        huybo = taoNutBoGoc("Hủy bỏ", MAU_DO);
        quaylai = taoNutBoGoc(
                "Quay lại danh sách",
                new Color(226, 232, 240)
        );

        thanhtoan.addActionListener(this);
        huybo.addActionListener(this);
        quaylai.addActionListener(this);

        pCenter4.add(thanhtoan);
        pCenter4.add(huybo);
        pCenter4.add(quaylai);


        pCenter.add(ttchung);
        pCenter.add(Box.createVerticalStrut(12));
        pCenter.add(theThongTin);
        pCenter.add(Box.createVerticalStrut(10));
        pCenter.add(pTimKiem);
        pCenter.add(Box.createVerticalStrut(18));

        pCenter.add(chitietsuachuavaphutung);
        pCenter.add(Box.createVerticalStrut(12));
        pCenter.add(theBang);
        pCenter.add(Box.createVerticalStrut(18));

        pCenter.add(tomtat);
        pCenter.add(Box.createVerticalStrut(12));
        pCenter.add(theThanhToan);
        pCenter.add(Box.createVerticalStrut(18));
        pCenter.add(pCenter4);

        JScrollPane scrollNoiDung = new JScrollPane(pCenter);
        scrollNoiDung.setBorder(null);
        scrollNoiDung.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollNoiDung.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );
        scrollNoiDung.getVerticalScrollBar().setUnitIncrement(18);
        scrollNoiDung.getViewport().setBackground(MAU_NEN);

        add(scrollNoiDung, BorderLayout.CENTER);

        chinhMau();
        laydulieu();
    }


    private JLabel taoTieuDeMuc(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_MUC);
        label.setForeground(MAU_CHU);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel taoNhan(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_NHAN);
        label.setForeground(MAU_PHU);
        return label;
    }

    private JTextField taoTextField(String text) {
        JTextField field = new JTextField(text);
        field.setFont(FONT_NHAN);
        field.setForeground(MAU_CHU);
        field.setBackground(Color.WHITE);
        field.setCaretColor(MAU_CHU);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAU_VIEN),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)
        ));
        field.setPreferredSize(new Dimension(150, 38));
        return field;
    }

    private JPanel taoThe(JComponent noiDung) {
        JPanel the = new JPanel(new BorderLayout());
        the.setBackground(MAU_THE);
        the.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAU_VIEN),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));

        the.setAlignmentX(Component.LEFT_ALIGNMENT);
        the.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE)
        );

        the.add(noiDung, BorderLayout.CENTER);
        return the;
    }

    private JButton taoNutBoGoc(String text, Color mauNen) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                Color mau = getBackground();
                if (getModel().isPressed()) {
                    mau = mau.darker();
                }

                g2.setColor(mau);
                g2.fillRoundRect(
                        0, 0,
                        getWidth(), getHeight(),
                        16, 16
                );

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(FONT_DAM);
        btn.setForeground(MAU_CHU);
        btn.setBackground(mauNen);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(
                BorderFactory.createEmptyBorder(10, 18, 10, 18)
        );
        btn.setPreferredSize(new Dimension(165, 42));

        return btn;
    }

    private void chinhMau() {
        getContentPane().setBackground(MAU_NEN);
        pCenter.setBackground(MAU_NEN);
        pNorth.setBackground(MAU_NEN);

        lbltieude.setForeground(MAU_CHU);
        ttchung.setForeground(MAU_CHU);
        chitietsuachuavaphutung.setForeground(MAU_CHU);
        tomtat.setForeground(MAU_CHU);

        table.getTableHeader().setBackground(MAU_XANH);
        table.getTableHeader().setForeground(MAU_CHU);
    }

    private void capNhatThongTinThanhToan() {
        try {
            DAO_KeToan dao = new DAO_KeToan();

            Object[] ketQua = dao.laythongtinthanhtoan();

            if (ketQua != null) {
                txttongtienphutung.setText(
                        String.valueOf(ketQua[0])
                );

                txttongtiendichvu.setText(
                        String.valueOf(ketQua[1])
                );

                txttongcong.setText(
                        String.valueOf(ketQua[2])
                );
            } else {
                txttongtienphutung.setText("0");
                txttongtiendichvu.setText("0");
                txttongcong.setText("0");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi lấy thông tin thanh toán: " + e.getMessage()
            );
        }
    }

    public void laydulieu() {
        DAO_KeToan dao = new DAO_KeToan();

        try {
            List<Object[]> danhSach =
                    dao.laydulieuchitiethoadonvathanhtoan();

            model.setRowCount(0);
            int stt = 1;

            for (Object[] dong : danhSach) {
                model.addRow(new Object[]{
                        stt++,
                        dong[0],
                        dong[1],
                        dong[2],
                        dong[3],
                        dong[4],
                        dong[5],
                        dong[6],
                        dong[7]
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi lấy dữ liệu từ SQL: " + e.getMessage(),
                    "Lỗi dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }



    public void timkiem() {
        String maHD = txtmahd.getText().trim();
        String maPSC = txtmaphieu.getText().trim();
        String bienSoNhap = txtbienso.getText().trim();

        if (maHD.isEmpty()
                && maPSC.isEmpty()
                && bienSoNhap.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập ít nhất một thông tin để tìm kiếm!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        table.clearSelection();

        for (int i = 0; i < table.getRowCount(); i++) {
            String maHoaDon =
                    String.valueOf(table.getValueAt(i, 1));
            String maPhieu =
                    String.valueOf(table.getValueAt(i, 2));
            String bienSo =
                    String.valueOf(table.getValueAt(i, 3));

            boolean khopMaHD = maHD.isEmpty()
                    || maHoaDon.equalsIgnoreCase(maHD);

            boolean khopMaPSC = maPSC.isEmpty()
                    || maPhieu.equalsIgnoreCase(maPSC);

            boolean khopBienSo = bienSoNhap.isEmpty()
                    || bienSo.equalsIgnoreCase(bienSoNhap);

            if (khopMaHD && khopMaPSC && khopBienSo) {
                table.setRowSelectionInterval(i, i);
                table.scrollRectToVisible(
                        table.getCellRect(i, 0, true)

                );
                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Không tìm thấy hóa đơn phù hợp!",
                "Kết quả tìm kiếm",
                JOptionPane.INFORMATION_MESSAGE
        );
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == quaylai) {
            ManHinhDanhSachPhieuSuaChuaDaHoanThanh ui =
                    new ManHinhDanhSachPhieuSuaChuaDaHoanThanh();

            ui.setVisible(true);
            dispose();

        } else if (o == btntim) {
            timkiem();
            capNhatThongTinThanhToan();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManHinhChiTietHDVaThanhToan ui =
                    new ManHinhChiTietHDVaThanhToan();
            ui.setVisible(true);
        });
    }
}
