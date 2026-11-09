
package View.KeToan;

import DAO.DAO_KeToan;
import Entity.ChiTietHoaDon;
import Entity.ThongTinThanhToan;
import Entity.ThongTinHoaDon;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;

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
    private final JLabel phuongthuc;
    private final JTextField txtphuongthuc;
    private final JLabel tongcong;
    private final JTextField txttongcong;
    private final JLabel trangthai;
    private final JTextField txttrangthai;
    private final JPanel pCenter4;
    private JButton thanhtoan;
    private JButton huybo;
    private JButton quaylai;
    private JButton inhoadon;
    private String maHoaDonThanhToanGanNhat = null;
    private String[] thongTinHoaDonDaThanhToanGanNhat;
    private List<Object[]> chiTietHoaDonDaThanhToanGanNhat =
            new ArrayList<>();


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
                "Mã dịch vụ/phụ tùng",
                "Tên dịch vụ/phụ tùng",
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

        phuongthuc = taoNhan("Phương thức");
        txtphuongthuc = taoTextField("");

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

        pCenter3.add(phuongthuc);
        pCenter3.add(txtphuongthuc);

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
        inhoadon = taoNutBoGoc("In hóa đơn", new Color(187, 247, 208));
        huybo = taoNutBoGoc("Hủy bỏ", MAU_DO);
        quaylai = taoNutBoGoc("Quay lại danh sách",new Color(226, 232, 240));

        thanhtoan.addActionListener(this);
        huybo.addActionListener(this);
        quaylai.addActionListener(this);
        inhoadon.addActionListener(this);

        pCenter4.add(thanhtoan);
        pCenter4.add(inhoadon);
        pCenter4.add(huybo);
        pCenter4.add(quaylai);


        pCenter.add(ttchung);
        pCenter.add(Box.createVerticalStrut(12));
        pCenter.add(theThongTin);
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
    public ManHinhChiTietHDVaThanhToan(String maHoaDon) {
        this();

        if (maHoaDon != null && !maHoaDon.trim().isEmpty()) {
            loadThongTinTheoHoaDon(maHoaDon.trim());
        }
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
    private void inHoaDon() {

        // Kiểm tra đã có hóa đơn được thanh toán hay chưa
        if (maHoaDonThanhToanGanNhat == null
                || thongTinHoaDonDaThanhToanGanNhat == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Chưa có hóa đơn nào được thanh toán!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Lấy thông tin hóa đơn đã lưu
        String maHDIn = maHoaDonThanhToanGanNhat;

        String[] thongTin = thongTinHoaDonDaThanhToanGanNhat;

        if (chiTietHoaDonDaThanhToanGanNhat.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không có chi tiết hóa đơn để in!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        PrinterJob printerJob = PrinterJob.getPrinterJob();

        printerJob.setJobName("Hoa don " + maHDIn);

        printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {

            if (pageIndex > 0) {
                return java.awt.print.Printable.NO_SUCH_PAGE;
            }

            Graphics2D g2 = (Graphics2D) graphics;

            g2.translate(
                    pageFormat.getImageableX(),
                    pageFormat.getImageableY()
            );

            double tiLe = Math.min(
                    pageFormat.getImageableWidth() / 550,
                    pageFormat.getImageableHeight() / 750
            );

            g2.scale(tiLe, tiLe);

            int y = 30;

            g2.setColor(Color.BLACK);

            // Tiêu đề
            g2.setFont(new Font("Arial", Font.BOLD, 22));
            g2.drawString("HOA DON THANH TOAN", 150, y);

            y += 35;

            g2.setFont(new Font("Arial", Font.PLAIN, 12));

            // Thông tin hóa đơn
            g2.drawString("Ma hoa don: " + maHDIn, 20, y);
            y += 22;

            g2.drawString("Ngay lap: " + thongTin[0], 20, y);
            y += 22;

            g2.drawString("Ma phieu sua chua: " + thongTin[1], 20, y);
            y += 22;

            g2.drawString("Khach hang: " + thongTin[2], 20, y);
            y += 22;

            g2.drawString("Bien so xe: " + thongTin[3], 20, y);
            y += 22;

            g2.drawString("So dien thoai: " + thongTin[4], 20, y);
            y += 35;

            // Tiêu đề bảng
            g2.setFont(new Font("Arial", Font.BOLD, 10));

            int[] x = {20, 55, 155, 330, 375, 445};

            String[] cot = {
                    "STT",
                    "Ma hang",
                    "Ten dich vu/phu tung",
                    "SL",
                    "Don gia",
                    "Thanh tien"
            };

            for (int i = 0; i < cot.length; i++) {
                g2.drawString(cot[i], x[i], y);
            }

            y += 12;

            g2.drawLine(20, y, 530, y);

            y += 20;

            // Chi tiết hóa đơn đã lưu
            g2.setFont(new Font("Arial", Font.PLAIN, 9));

            int stt = 1;

            for (Object[] dong : chiTietHoaDonDaThanhToanGanNhat) {

                g2.drawString(
                        String.valueOf(stt++),
                        x[0],
                        y
                );

                g2.drawString(
                        String.valueOf(dong[4]),
                        x[1],
                        y
                );

                String ten = String.valueOf(dong[5]);

                if (ten.length() > 28) {
                    ten = ten.substring(0, 28);
                }

                g2.drawString(ten, x[2], y);

                g2.drawString(
                        String.valueOf(dong[6]),
                        x[3],
                        y
                );

                g2.drawString(
                        String.valueOf(dong[7]),
                        x[4],
                        y
                );

                g2.drawString(
                        String.valueOf(dong[8]),
                        x[5],
                        y
                );

                y += 22;
            }

            y += 20;

            g2.drawLine(20, y, 530, y);

            y += 25;

            // Tổng tiền
            g2.setFont(new Font("Arial", Font.BOLD, 12));

            g2.drawString(
                    "Tong tien phu tung: " + thongTin[5] + " VNĐ",
                    300,
                    y
            );

            y += 22;

            g2.drawString(
                    "Tong tien dich vu: " + thongTin[6] + " VNĐ",
                    300,
                    y
            );

            y += 25;

            // Tổng cộng
            g2.setFont(new Font("Arial", Font.BOLD, 15));

            g2.drawString(
                    "TONG CONG: " + thongTin[8] + " VNĐ",
                    300,
                    y
            );

            y += 30;

            // Phương thức và trạng thái
            g2.setFont(new Font("Arial", Font.PLAIN, 11));

            g2.drawString(
                    "Phuong thuc: " + thongTin[7],
                    300,
                    y
            );

            y += 22;

            g2.drawString(
                    "Trang thai: " + thongTin[9],
                    300,
                    y
            );

            y += 50;

            g2.drawString(
                    "Cam on quy khach da su dung dich vu!",
                    150,
                    y
            );

            return java.awt.print.Printable.PAGE_EXISTS;
        });

        // Hiển thị hộp thoại chọn máy in
        if (printerJob.printDialog()) {

            try {

                printerJob.print();

            } catch (PrinterException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Lỗi in hóa đơn: " + e.getMessage(),
                        "Lỗi in",
                        JOptionPane.ERROR_MESSAGE
                );

                e.printStackTrace();
            }
        }
    }
    private void loadThongTinTheoHoaDon(String maHoaDon) {
        DAO_KeToan dao = new DAO_KeToan();
        try {
            ThongTinHoaDon data =
                    dao.layThongTinChungTheoHoaDon(maHoaDon);
            if (data == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Không tìm thấy thông tin hóa đơn!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }
            txtmahd.setText(
                    data.getMaHoaDon() == null
                            ? ""
                            : data.getMaHoaDon().getMaHoaDon()
            );
            txtngaylap.setText(data.getNgayLap());
            txtmaphieu.setText(
                    data.getMaPhieuSuaChua() == null
                            ? ""
                            : data.getMaPhieuSuaChua().getMaPhieuSuaChua()
            );
            txtkhachhang.setText(data.getHoTen());
            txtbienso.setText(
                    data.getBienSo() == null
                            ? ""
                            : data.getBienSo().getBienSo()
            );
            txtsdt.setText(data.getSoDienThoai());
            txtphuongthuc.setText(data.getPhuongThuc());
            txttrangthai.setText(data.getTrangThai());
            List<ChiTietHoaDon> danhSach =
                    dao.layChiTietTheoMaHoaDon(maHoaDon);
            model.setRowCount(0);
            int stt = 1;
            for (ChiTietHoaDon ct : danhSach) {
                model.addRow(new Object[]{
                        stt++,
                        ct.getMaHoaDon().getMaHoaDon(),
                        ct.getMaPhieuSuaChua().getMaPhieuSuaChua(),
                        ct.getBienSo().getBienSo(),
                        ct.getMaPhuTung() == null
                                ? ""
                                : ct.getMaPhuTung().getMaPhuTung(),
                        ct.getTenPhuTung(),
                        ct.getSoLuong(),
                        dinhDangTien(ct.getDonGia()),
                        dinhDangTien(ct.getThanhTien())
                });
            }
            ThongTinThanhToan thanhToan =
                    dao.layThongTinThanhToanTheoHoaDon(maHoaDon);
            if (thanhToan != null) {
                txttongtienphutung.setText(
                        dinhDangTien(thanhToan.getTongTienPhuTung())
                );
                txttongtiendichvu.setText(
                        dinhDangTien(thanhToan.getTongTienDichVu())
                );
                txttongcong.setText(
                        dinhDangTien(thanhToan.getTongCong())
                );
            } else {
                txttongtienphutung.setText("0");
                txttongtiendichvu.setText("0");
                txttongcong.setText("0");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi tải thông tin hóa đơn: " + e.getMessage(),
                    "Lỗi dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
    private String dinhDangTien(BigDecimal tien) {
        if (tien == null) {
            tien = BigDecimal.ZERO;
        }
        return String.format("%,.0f", tien);
    }
    private void capNhatThongTinThanhToan() {
        try {
            DAO_KeToan dao = new DAO_KeToan();
            ThongTinThanhToan ketQua = dao.laythongtinthanhtoan();
            if (ketQua != null) {
                txttongtienphutung.setText(
                        ketQua.getTongTienPhuTung() == null
                                ? "0"
                                : ketQua.getTongTienPhuTung().toString()
                );
                txttongtiendichvu.setText(
                        ketQua.getTongTienDichVu() == null
                                ? "0"
                                : ketQua.getTongTienDichVu().toString()
                );
                txttongcong.setText(
                        ketQua.getTongCong() == null
                                ? "0"
                                : ketQua.getTongCong().toString()
                );
            } else {
                txttongtienphutung.setText("0");
                txttongtiendichvu.setText("0");
                txttongcong.setText("0");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi lấy thông tin thanh toán: " + e.getMessage(),
                    "Lỗi dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
    public void laydulieu() {
        DAO_KeToan dao = new DAO_KeToan();
        try {
            List<ChiTietHoaDon> danhSach =
                    dao.laydulieuchitiethoadonvathanhtoan();
            model.setRowCount(0);
            int stt = 1;
            for (ChiTietHoaDon dong : danhSach) {
                model.addRow(new Object[]{
                        stt++,
                        dong.getMaHoaDon().getMaHoaDon(),
                        dong.getMaPhieuSuaChua().getMaPhieuSuaChua(),
                        dong.getBienSo().getBienSo(),
                        dong.getMaPhuTung().getMaPhuTung(),
                        dong.getTenPhuTung(),
                        dong.getSoLuong(),
                        dong.getDonGia(),
                        dong.getThanhTien()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi lấy dữ liệu: " + e.getMessage(),
                    "Lỗi dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
    private void xuLyThanhToan() {

        int dongChon = table.getSelectedRow();

        if (dongChon == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn hóa đơn cần thanh toán!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int dongModel = table.convertRowIndexToModel(dongChon);

        String maHoaDon = String.valueOf(
                model.getValueAt(dongModel, 1)
        );

        if (maHoaDon.isEmpty() || maHoaDon.equals("null")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không lấy được mã hóa đơn!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Đảm bảo thông tin đang hiển thị đúng hóa đơn được chọn
        if (!maHoaDon.equals(txtmahd.getText().trim())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Thông tin hóa đơn đang hiển thị không khớp với hóa đơn được chọn!\n"
                            + "Vui lòng tải lại thông tin hóa đơn trước khi thanh toán.",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );

            loadThongTinTheoHoaDon(maHoaDon);
            return;
        }

        BigDecimal soTien;

        try {

            String tongTienText = txttongcong.getText()
                    .trim()
                    .replace(",", "");

            soTien = new BigDecimal(tongTienText);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Tổng tiền không hợp lệ! Vui lòng kiểm tra lại hóa đơn.",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (soTien.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tổng tiền phải lớn hơn 0!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int xacNhan = JOptionPane.showConfirmDialog(
                this,
                "Mã hóa đơn: " + maHoaDon
                        + "\nTổng tiền: " + soTien
                        + "\nPhương thức: PT01"
                        + "\n\nBạn có chắc chắn muốn thanh toán?",
                "Xác nhận thanh toán",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (xacNhan != JOptionPane.YES_OPTION) {
            return;
        }

        DAO_KeToan dao = new DAO_KeToan();

        try {

            boolean ketQua = dao.thanhToanHoaDon(
                    maHoaDon,
                    soTien,
                    "PT01",
                    "KT01"
            );

            if (ketQua) {

                // LƯU HÓA ĐƠN VỪA THANH TOÁN GẦN NHẤT
                // Phải thực hiện trước khi xóa dữ liệu bảng và JTextField
                luuHoaDonVuaThanhToan(maHoaDon);

                JOptionPane.showMessageDialog(
                        this,
                        "Thanh toán hóa đơn " + maHoaDon
                                + " thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Xóa tất cả dòng chi tiết thuộc hóa đơn vừa thanh toán
                for (int i = model.getRowCount() - 1; i >= 0; i--) {

                    String maHDTrongBang = String.valueOf(
                            model.getValueAt(i, 1)
                    );

                    if (maHoaDon.equals(maHDTrongBang)) {
                        model.removeRow(i);
                    }
                }

                // Làm trống thông tin trên giao diện
                txtmahd.setText("");
                txtngaylap.setText("");
                txtmaphieu.setText("");
                txtkhachhang.setText("");
                txtbienso.setText("");
                txtsdt.setText("");

                txttongtienphutung.setText("0");
                txttongtiendichvu.setText("0");
                txtphuongthuc.setText("");
                txttongcong.setText("0");

                txttrangthai.setText("Chờ thanh toán");

                table.clearSelection();
                table.revalidate();
                table.repaint();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Thanh toán không thành công! Vui lòng kiểm tra lại hóa đơn.",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Thanh toán thất bại: " + ex.getMessage(),
                    "Lỗi thanh toán",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }
    private void luuHoaDonVuaThanhToan(String maHoaDon) {
        maHoaDonThanhToanGanNhat = maHoaDon;
        thongTinHoaDonDaThanhToanGanNhat = new String[]{
                txtngaylap.getText(),
                txtmaphieu.getText(),
                txtkhachhang.getText(),
                txtbienso.getText(),
                txtsdt.getText(),
                txttongtienphutung.getText(),
                txttongtiendichvu.getText(),
                txtphuongthuc.getText(),
                txttongcong.getText(),
                "Đã thanh toán"
        };
        chiTietHoaDonDaThanhToanGanNhat.clear();
        for (int i = 0; i < model.getRowCount(); i++) {
            String maHDTrongBang = String.valueOf(
                    model.getValueAt(i, 1)
            );
            if (maHoaDon.equals(maHDTrongBang)) {
                Object[] dong = new Object[model.getColumnCount()];
                for (int j = 0; j < model.getColumnCount(); j++) {
                    dong[j] = model.getValueAt(i, j);
                }
                chiTietHoaDonDaThanhToanGanNhat.add(dong);
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        Object o = e.getSource();

        if (o == quaylai) {

            ManHinhDanhSachPhieuSuaChuaDaHoanThanh ui =
                    new ManHinhDanhSachPhieuSuaChuaDaHoanThanh();

            ui.setVisible(true);
            dispose();

        } else if (o == thanhtoan) {

            xuLyThanhToan();

        } else if (o == inhoadon) {

            inHoaDon();
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
