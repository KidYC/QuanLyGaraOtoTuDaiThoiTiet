
package View.KeToan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import DAO.DAO_KeToan;
import java.sql.SQLException;

public class ManHinhChiTietCongNo extends JFrame implements ActionListener {

    private final JPanel pNorth;
    private final JLabel lbltieude;
    private final JPanel pCenter;
    private final JLabel thongtin;
    private final JPanel pCenter1;
    private final JLabel tenkhachhang;
    private final JTextField txttenkhachhang;
    private final JLabel biensoxe;
    private final JTextField txtbiensoxe;
    private final JLabel sdt;
    private final JTextField txtsdt;
    private final JTextField txtdiachi;
    private final JLabel diachi;
    private final JLabel hanmuc;
    private final JTextField txthanmuc;
    private final JLabel tongno;
    private final JTextField txttongno;
    private final JLabel lichsu;
    private final JPanel pCenter2;
    private final JLabel tungay;
    private final JTextField txttungay;
    private final JLabel denngay;
    private final JTextField txtdenngay;
    private final JButton loc;
    private final JButton xuatexcel;
    private final JPanel pCenter3;
    private final DefaultTableModel model;
    private final JTable table;
    private final JScrollPane srcoll;
    private JButton quaylai;



    private static final Color MAU_NEN =
            new Color(244, 246, 248);

    private static final Color MAU_SIDEBAR =
            new Color(226, 232, 240);

    private static final Color MAU_SIDEBAR_CHON =
            new Color(186, 230, 253);

    private static final Color MAU_CHU_SIDEBAR =
            new Color(17, 24, 39);

    private static final Color MAU_TEXT =
            new Color(17, 24, 39);

    private static final Color MAU_PHU =
            new Color(107, 114, 128);

    private static final Color MAU_VIEN =
            new Color(226, 232, 240);

    private static final Color MAU_XANH =
            new Color(186, 230, 253);

    private static final Color MAU_XANH_LA =
            new Color(34, 197, 94);

    private static final Color MAU_CAM =
            new Color(245, 158, 11);

    private static final Color MAU_DO =
            new Color(220, 38, 38);

    private static final Color MAU_TIM =
            new Color(219, 234, 254);

    private static final Font FONT_TIEU_DE =
            new Font("Segoe UI", Font.BOLD, 22);

    private static final Font FONT_NHAN =
            new Font("Segoe UI", Font.PLAIN, 14);

    private static final Font FONT_NHAN_DAM =
            new Font("Segoe UI", Font.BOLD, 14);

    public ManHinhChiTietCongNo(String maKH) {

        super("Chi tiết công nợ khách hàng");

        setSize(1050, 850);
        setMinimumSize(new Dimension(900, 750));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        pNorth = new JPanel();
        pNorth.setLayout(new BoxLayout(pNorth, BoxLayout.Y_AXIS));
        pNorth.setBackground(MAU_NEN);
        pNorth.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 1, 0, MAU_VIEN
                ),
                new EmptyBorder(22, 30, 22, 30)
        ));

        lbltieude = new JLabel("CHI TIẾT CÔNG NỢ KHÁCH HÀNG");
        lbltieude.setFont(FONT_TIEU_DE);
        lbltieude.setForeground(MAU_TEXT);
        lbltieude.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblmota = new JLabel(
                "Thông tin khách hàng, dư nợ và lịch sử giao dịch"
        );
        lblmota.setFont(FONT_NHAN);
        lblmota.setForeground(MAU_PHU);
        lblmota.setAlignmentX(Component.LEFT_ALIGNMENT);

        pNorth.add(lbltieude);
        pNorth.add(Box.createVerticalStrut(7));
        pNorth.add(lblmota);

        add(pNorth, BorderLayout.NORTH);



        pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
        pCenter.setBackground(MAU_NEN);
        pCenter.setBorder(new EmptyBorder(25, 30, 25, 30));



        thongtin = new JLabel("THÔNG TIN KHÁCH HÀNG");
        thongtin.setFont(FONT_TIEU_DE);
        thongtin.setForeground(MAU_TEXT);
        thongtin.setAlignmentX(Component.LEFT_ALIGNMENT);

        pCenter.add(thongtin);
        pCenter.add(Box.createVerticalStrut(18));

        pCenter1 = new JPanel(new GridLayout(3, 4, 15, 15));
        pCenter1.setBackground(Color.WHITE);
        pCenter1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAU_VIEN),
                new EmptyBorder(20, 20, 20, 20)
        ));
        pCenter1.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter1.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 220)
        );
        pCenter1.setPreferredSize(new Dimension(950, 220));

        // Hàng 1
        tenkhachhang = taoNhan("Tên khách hàng");
        txttenkhachhang = taoOThongTin();

        biensoxe = taoNhan("Biển số xe");
        txtbiensoxe = taoOThongTin();

        // Hàng 2
        sdt = taoNhan("Số điện thoại");
        txtsdt = taoOThongTin();

        diachi = taoNhan("Địa chỉ");
        txtdiachi = taoOThongTin();

        // Hàng 3
        hanmuc = taoNhan("Hạn mức nợ cho phép");
        txthanmuc = taoOThongTin();

        tongno = taoNhan("TỔNG NỢ HIỆN TẠI");
        txttongno = taoOThongTin();

        // Lấy dữ liệu từ DAO
        try {
            DAO_KeToan dao = new DAO_KeToan();
            Object[] kh = dao.layThongTinKhachHangNo(maKH);

            if (kh != null) {
                txttenkhachhang.setText(
                        kh[2] == null ? "" : String.valueOf(kh[2])
                );

                txtbiensoxe.setText(
                        kh[1] == null ? "" : String.valueOf(kh[1])
                );

                txtsdt.setText(
                        kh[3] == null ? "" : String.valueOf(kh[3])
                );

                txtdiachi.setText(
                        kh[4] == null ? "" : String.valueOf(kh[4])
                );

                txthanmuc.setText(
                        kh[5] == null ? "" : String.valueOf(kh[5])
                );

                txttongno.setText(
                        kh[6] == null ? "" : String.valueOf(kh[6])
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Không tìm thấy thông tin khách hàng!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi lấy thông tin khách hàng: " + e.getMessage(),
                    "Lỗi cơ sở dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        pCenter1.add(tenkhachhang);
        pCenter1.add(txttenkhachhang);
        pCenter1.add(biensoxe);
        pCenter1.add(txtbiensoxe);

        pCenter1.add(sdt);
        pCenter1.add(txtsdt);
        pCenter1.add(diachi);
        pCenter1.add(txtdiachi);

        pCenter1.add(hanmuc);
        pCenter1.add(txthanmuc);
        pCenter1.add(tongno);
        pCenter1.add(txttongno);

        // Làm nổi bật tổng nợ
        txttongno.setForeground(MAU_DO);
        txttongno.setFont(FONT_NHAN_DAM);

        pCenter.add(pCenter1);
        pCenter.add(Box.createVerticalStrut(28));

        lichsu = new JLabel("LỊCH SỬ PHÁT SINH CÔNG NỢ");
        lichsu.setFont(FONT_TIEU_DE);
        lichsu.setForeground(MAU_TEXT);
        lichsu.setAlignmentX(Component.LEFT_ALIGNMENT);

        pCenter.add(lichsu);
        pCenter.add(Box.createVerticalStrut(18));



        pCenter2 = new JPanel(new GridBagLayout());
        pCenter2.setBackground(Color.WHITE);
        pCenter2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAU_VIEN),
                new EmptyBorder(15, 18, 15, 18)
        ));
        pCenter2.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter2.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 115)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;

        tungay = taoNhan("Từ ngày");
        txttungay = taoOThongTin();
        txttungay.setEditable(true);
        txttungay.setText("01/09/2023");

        denngay = taoNhan("Đến ngày");
        txtdenngay = taoOThongTin();
        txtdenngay.setEditable(true);
        txtdenngay.setText("26/10/2023");

        loc = taoNutBoGoc("Lọc dữ liệu");
        xuatexcel = taoNutBoGoc("Xuất Excel");

        // Cột 0: Từ ngày
        gbc.gridx = 0;
        gbc.weightx = 0;
        pCenter2.add(tungay, gbc);

        // Cột 1: Ngày bắt đầu
        gbc.gridx = 1;
        gbc.weightx = 1;
        pCenter2.add(txttungay, gbc);

        // Cột 2: Đến ngày
        gbc.gridx = 2;
        gbc.weightx = 0;
        pCenter2.add(denngay, gbc);

        // Cột 3: Ngày kết thúc
        gbc.gridx = 3;
        gbc.weightx = 1;
        pCenter2.add(txtdenngay, gbc);

        // Hàng nút
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        pCenter2.add(loc, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 2;
        pCenter2.add(xuatexcel, gbc);

        pCenter.add(pCenter2);
        pCenter.add(Box.createVerticalStrut(18));



        pCenter3 = new JPanel(new BorderLayout());
        pCenter3.setBackground(Color.WHITE);
        pCenter3.setBorder(BorderFactory.createLineBorder(MAU_VIEN));
        pCenter3.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter3.setPreferredSize(new Dimension(950, 240));
        pCenter3.setMinimumSize(new Dimension(700, 180));
        pCenter3.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 300)
        );

        String[] columns = {
                "Ngày giao dịch",
                "Nội dung",
                "Ghi nợ (tăng)",
                "Ghi có (giảm)",
                "Dư nợ cuối"
        };

        Object[][] data = {
                {
                        "26/10/2023",
                        "Thu tiền mặt KH",
                        "-",
                        "10.000.000 VNĐ",
                        "25.000.000 VNĐ"
                },
                {
                        "15/10/2023",
                        "Sửa xe 29H-456.78",
                        "15.000.000 VNĐ",
                        "-",
                        "35.000.000 VNĐ"
                },
                {
                        "01/10/2023",
                        "Sửa xe 29H-123.45",
                        "20.000.000 VNĐ",
                        "-",
                        "20.000.000 VNĐ"
                },
                {
                        "01/10/2023",
                        "Số dư đầu kỳ",
                        "-",
                        "-",
                        "0 VNĐ"
                }
        };

        model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(FONT_NHAN);
        table.setForeground(MAU_TEXT);
        table.setRowHeight(38);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(MAU_VIEN);
        table.setSelectionBackground(MAU_SIDEBAR_CHON);
        table.setSelectionForeground(MAU_TEXT);
        table.setFillsViewportHeight(true);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setAutoCreateRowSorter(true);

        // Tiêu đề cột
        table.getTableHeader().setFont(FONT_NHAN_DAM);
        table.getTableHeader().setBackground(MAU_XANH);
        table.getTableHeader().setForeground(MAU_TEXT);
        table.getTableHeader().setPreferredSize(
                new Dimension(0, 42)
        );
        table.getTableHeader().setReorderingAllowed(false);

        // Căn giữa ngày giao dịch
        DefaultTableCellRenderer canGiua =
                new DefaultTableCellRenderer();
        canGiua.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0)
                .setCellRenderer(canGiua);

        // Căn phải các cột tiền
        DefaultTableCellRenderer canPhai =
                new DefaultTableCellRenderer();
        canPhai.setHorizontalAlignment(SwingConstants.RIGHT);
        canPhai.setBorder(new EmptyBorder(0, 0, 0, 12));

        for (int i = 2; i < 5; i++) {
            table.getColumnModel().getColumn(i)
                    .setCellRenderer(canPhai);
        }

        // Tô màu xen kẽ các hàng
        DefaultTableCellRenderer rendererXenKe =
                new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column
                    ) {
                        Component c = super.getTableCellRendererComponent(
                                table, value, isSelected,
                                hasFocus, row, column
                        );

                        if (!isSelected) {
                            c.setBackground(
                                    row % 2 == 0
                                            ? Color.WHITE
                                            : MAU_NEN
                            );
                            c.setForeground(MAU_TEXT);
                        }

                        setBorder(new EmptyBorder(0, 8, 0, 8));

                        return c;
                    }
                };

        for (int i = 0; i < table.getColumnCount(); i++) {
            if (i == 0 || i >= 2) {
                continue;
            }

            table.getColumnModel().getColumn(i)
                    .setCellRenderer(rendererXenKe);
        }

        srcoll = new JScrollPane(table);
        srcoll.setBorder(BorderFactory.createEmptyBorder());
        srcoll.getViewport().setBackground(Color.WHITE);

        pCenter3.add(srcoll, BorderLayout.CENTER);

        pCenter.add(pCenter3);
        pCenter.add(Box.createVerticalStrut(22));



        quaylai = taoNutBoGoc("←  Quay lại danh sách khách hàng nợ");
        quaylai.setAlignmentX(Component.LEFT_ALIGNMENT);
        quaylai.setPreferredSize(new Dimension(280, 44));
        quaylai.setMaximumSize(new Dimension(320, 44));

        pCenter.add(quaylai);


        JScrollPane scrollNoiDung = new JScrollPane(pCenter);
        scrollNoiDung.setBorder(BorderFactory.createEmptyBorder());
        scrollNoiDung.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scrollNoiDung.getVerticalScrollBar().setUnitIncrement(16);
        scrollNoiDung.getViewport().setBackground(MAU_NEN);

        add(scrollNoiDung, BorderLayout.CENTER);



        quaylai.addActionListener(this);

        chinhmau();
    }



    private JLabel taoNhan(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_NHAN_DAM);
        lbl.setForeground(MAU_TEXT);
        return lbl;
    }



    private JTextField taoOThongTin() {
        JTextField txt = new JTextField();

        txt.setFont(FONT_NHAN);
        txt.setForeground(MAU_TEXT);
        txt.setBackground(Color.WHITE);
        txt.setCaretColor(MAU_TEXT);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAU_VIEN),
                new EmptyBorder(7, 9, 7, 9)
        ));

        return txt;
    }


    private void chinhmau() {

        getContentPane().setBackground(MAU_NEN);

        pNorth.setBackground(MAU_NEN);
        pCenter.setBackground(MAU_NEN);
        pCenter1.setBackground(Color.WHITE);
        pCenter2.setBackground(Color.WHITE);
        pCenter3.setBackground(Color.WHITE);

        lbltieude.setForeground(MAU_TEXT);
        thongtin.setForeground(MAU_TEXT);
        lichsu.setForeground(MAU_TEXT);

        tenkhachhang.setForeground(MAU_TEXT);
        biensoxe.setForeground(MAU_TEXT);
        sdt.setForeground(MAU_TEXT);
        diachi.setForeground(MAU_TEXT);
        hanmuc.setForeground(MAU_TEXT);
        tongno.setForeground(MAU_TEXT);

        tungay.setForeground(MAU_TEXT);
        denngay.setForeground(MAU_TEXT);

        // Nút lọc
        loc.setBackground(MAU_XANH);
        loc.setForeground(MAU_TEXT);

        // Nút xuất Excel
        xuatexcel.setBackground(MAU_SIDEBAR_CHON);
        xuatexcel.setForeground(MAU_TEXT);

        // Nút quay lại
        quaylai.setBackground(MAU_SIDEBAR);
        quaylai.setForeground(MAU_TEXT);

        // Bảng dữ liệu
        table.getTableHeader().setBackground(MAU_XANH);
        table.getTableHeader().setForeground(MAU_TEXT);

        table.setSelectionBackground(MAU_SIDEBAR_CHON);
        table.setSelectionForeground(MAU_TEXT);
    }



    private JButton taoNutBoGoc(String text) {

        JButton btn = new JButton(text) {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(getBackground());

                g2.fillRoundRect(
                        0, 0,
                        getWidth() - 1,
                        getHeight() - 1,
                        14, 14
                );

                g2.dispose();

                super.paintComponent(g);
            }
        };

        btn.setFont(FONT_NHAN_DAM);
        btn.setForeground(MAU_TEXT);
        btn.setBackground(MAU_XANH);

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);

        btn.setBorder(new EmptyBorder(10, 16, 10, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }



    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            ManHinhChiTietCongNo UI =
                    new ManHinhChiTietCongNo("KH01");

            UI.setVisible(true);
        });
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        Object o = e.getSource();

        if (o == quaylai) {

            ManHinhDanhSachKHNo UI =
                    new ManHinhDanhSachKHNo();

            UI.setVisible(true);
            dispose();
        }
    }
}
