
package View.KeToan;

import DAO.DAO_KeToan;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ManHinhDanhSachKHNo extends JFrame implements ActionListener {

    // Bảng màu giao diện
    private static final Color MAU_NEN = new Color(244, 246, 248);
    private static final Color MAU_THE = Color.WHITE;
    private static final Color MAU_SIDEBAR = new Color(226, 232, 240);
    private static final Color MAU_XANH = new Color(186, 230, 253);
    private static final Color MAU_XANH_PHU = new Color(219, 234, 254);
    private static final Color MAU_CHU = new Color(17, 24, 39);
    private static final Color MAU_PHU = new Color(107, 114, 128);
    private static final Color MAU_VIEN = new Color(226, 232, 240);
    private static final Color MAU_DO = new Color(254, 226, 226);

    // Font
    private static final Font FONT_TIEU_DE =
            new Font("Segoe UI", Font.BOLD, 24);
    private static final Font FONT_MUC =
            new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_NHAN =
            new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_DAM =
            new Font("Segoe UI", Font.BOLD, 14);

    private final JPanel pCenter;
    private final JPanel pNorth;
    private final JLabel lbltieude;
    private final JLabel thongtin;
    private final JPanel pCenter1;
    private final JLabel tongsokhachno;
    private final JTextField txttongsokhachno;
    private final JLabel tongduno;
    private final JTextField txttongduno;
    private final JLabel timkiem;
    private final JPanel pCenter2;
    private final JLabel tenkhachhang;
    private final JTextField txttenkhachhang;
    private final JTextField txtsdt;
    private final JLabel sdt;
    private final JComboBox<String> txtmucno;
    private final JLabel mucno;
    private final JButton btntimkiem;
    private final JLabel ds;
    private final JPanel pCenter3;
    private final DefaultTableModel model;
    private final JTable table;
    private final JScrollPane srcoll;
    private JButton btnchitietcongno;

    public ManHinhDanhSachKHNo() {
        super("DANH SÁCH KHÁCH HÀNG NỢ");

        setSize(1200, 900);
        setMinimumSize(new Dimension(950, 650));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(MAU_NEN);


        pNorth = new JPanel();
        pNorth.setLayout(new BoxLayout(pNorth, BoxLayout.Y_AXIS));
        pNorth.setBackground(MAU_NEN);
        pNorth.setBorder(
                BorderFactory.createEmptyBorder(24, 28, 18, 28)
        );

        lbltieude = new JLabel("DANH SÁCH KHÁCH HÀNG NỢ");
        lbltieude.setFont(FONT_TIEU_DE);
        lbltieude.setForeground(MAU_CHU);
        lbltieude.setAlignmentX(Component.LEFT_ALIGNMENT);

        pNorth.add(lbltieude);
        add(pNorth, BorderLayout.NORTH);


        pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
        pCenter.setBackground(MAU_NEN);
        pCenter.setBorder(
                BorderFactory.createEmptyBorder(0, 28, 24, 28)
        );
        pCenter.setAlignmentX(Component.LEFT_ALIGNMENT);


        thongtin = taoTieuDeMuc("TỔNG QUAN");

        pCenter1 = new JPanel(
                new GridLayout(1, 4, 15, 0)
        );
        pCenter1.setBackground(MAU_THE);
        pCenter1.setBorder(
                BorderFactory.createEmptyBorder(20, 22, 20, 22)
        );
        pCenter1.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter1.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 100)
        );

        tongsokhachno = taoNhan("Tổng số khách hàng nợ");
        txttongsokhachno = taoTextField("");
        txttongsokhachno.setFont(
                new Font("Segoe UI", Font.BOLD, 17)
        );
        txttongsokhachno.setForeground(new Color(3, 105, 161));

        tongduno = taoNhan("Tổng dư nợ toàn hệ thống");
        txttongduno = taoTextField("");
        txttongduno.setFont(
                new Font("Segoe UI", Font.BOLD, 17)
        );
        txttongduno.setForeground(new Color(3, 105, 161));

        pCenter1.add(tongsokhachno);
        pCenter1.add(txttongsokhachno);
        pCenter1.add(tongduno);
        pCenter1.add(txttongduno);


        timkiem = taoTieuDeMuc("TÌM KIẾM & BỘ LỌC");

        pCenter2 = new JPanel(
                new GridBagLayout()
        );
        pCenter2.setBackground(MAU_THE);
        pCenter2.setBorder(
                BorderFactory.createEmptyBorder(20, 22, 20, 22)
        );
        pCenter2.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        tenkhachhang = taoNhan("Tên khách hàng:");
        txttenkhachhang = taoTextField("");
        txttenkhachhang.setPreferredSize(new Dimension(190, 38));

        sdt = taoNhan("Số điện thoại:");
        txtsdt = taoTextField("");
        txtsdt.setPreferredSize(new Dimension(170, 38));

        mucno = taoNhan("Mức nợ:");
        txtmucno = new JComboBox<>(new String[]{
                "Lớn hơn 10000000 VNĐ",
                "Lớn hơn 30000000 VNĐ",
                "Lớn hơn 50000000 VNĐ"
        });
        txtmucno.setFont(FONT_NHAN);
        txtmucno.setBackground(Color.WHITE);
        txtmucno.setForeground(MAU_CHU);
        txtmucno.setPreferredSize(new Dimension(220, 38));
        txtmucno.setBorder(
                BorderFactory.createLineBorder(MAU_VIEN)
        );

        btntimkiem = taoNutBoGoc("Tìm kiếm", MAU_XANH);
        btntimkiem.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        pCenter2.add(tenkhachhang, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        pCenter2.add(txttenkhachhang, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        pCenter2.add(sdt, gbc);

        gbc.gridx = 3;
        gbc.weightx = 1;
        pCenter2.add(txtsdt, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        pCenter2.add(mucno, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        pCenter2.add(txtmucno, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        pCenter2.add(btntimkiem, gbc);


        ds = taoTieuDeMuc("DANH SÁCH");

        pCenter3 = new JPanel(new BorderLayout());
        pCenter3.setBackground(MAU_THE);
        pCenter3.setBorder(
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        );
        pCenter3.setPreferredSize(new Dimension(1100, 300));
        pCenter3.setMinimumSize(new Dimension(500, 220));
        pCenter3.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 350)
        );
        pCenter3.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] columns = {
                "STT",
                "Mã khách hàng",
                "Tên Khách Hàng",
                "Số Điện Thoại",
                "Biển Số Xe",
                "Hạn Mức Nợ",
                "Tổng Nợ Hiện Tại",
                "Trạng Thái"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(36);
        table.setFont(FONT_NHAN);
        table.setForeground(MAU_CHU);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(MAU_XANH_PHU);
        table.setSelectionForeground(MAU_CHU);
        table.setGridColor(MAU_VIEN);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        table.getTableHeader().setFont(FONT_DAM);
        table.getTableHeader().setBackground(MAU_XANH);
        table.getTableHeader().setForeground(MAU_CHU);
        table.getTableHeader().setPreferredSize(
                new Dimension(0, 42)
        );
        table.getTableHeader().setReorderingAllowed(false);

        int[] doRongCot = {
                55, 125, 190, 140, 120, 145, 165, 135
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
                .getColumn(1)
                .setCellRenderer(canGiua);
        table.getColumnModel()
                .getColumn(3)
                .setCellRenderer(canGiua);
        table.getColumnModel()
                .getColumn(4)
                .setCellRenderer(canGiua);
        table.getColumnModel()
                .getColumn(7)
                .setCellRenderer(canGiua);

        srcoll = new JScrollPane(table);
        srcoll.setBorder(BorderFactory.createLineBorder(MAU_VIEN));
        srcoll.getViewport().setBackground(Color.WHITE);
        srcoll.getVerticalScrollBar().setUnitIncrement(16);

        pCenter3.add(srcoll, BorderLayout.CENTER);


        btnchitietcongno = taoNutBoGoc(
                "Chi tiết công nợ", MAU_XANH
        );
        btnchitietcongno.setPreferredSize(
                new Dimension(200, 46)
        );
        btnchitietcongno.addActionListener(this);

        JPanel pNut = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 0, 0)
        );
        pNut.setBackground(MAU_NEN);
        pNut.setAlignmentX(Component.LEFT_ALIGNMENT);
        pNut.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 55)
        );
        pNut.add(btnchitietcongno);

        pCenter.add(thongtin);
        pCenter.add(Box.createVerticalStrut(12));
        pCenter.add(taoThe(pCenter1));
        pCenter.add(Box.createVerticalStrut(22));

        pCenter.add(timkiem);
        pCenter.add(Box.createVerticalStrut(12));
        pCenter.add(taoThe(pCenter2));
        pCenter.add(Box.createVerticalStrut(22));

        pCenter.add(ds);
        pCenter.add(Box.createVerticalStrut(12));
        pCenter.add(taoThe(pCenter3));
        pCenter.add(Box.createVerticalStrut(16));

        pCenter.add(pNut);

        // Thanh cuộn ngoài cho nội dung
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

        chinhmau();


        laydulieu();
        tongnohethong();

        int tongKH = table.getRowCount();
        txttongsokhachno.setText(String.valueOf(tongKH));
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
                        0, 0, getWidth(), getHeight(), 14, 14
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

        return btn;
    }


    private void chinhmau() {
        getContentPane().setBackground(MAU_NEN);
        pNorth.setBackground(MAU_NEN);
        pCenter.setBackground(MAU_NEN);

        lbltieude.setForeground(MAU_CHU);
        thongtin.setForeground(MAU_CHU);
        timkiem.setForeground(MAU_CHU);
        ds.setForeground(MAU_CHU);

        tongsokhachno.setForeground(MAU_PHU);
        tongduno.setForeground(MAU_PHU);
        tenkhachhang.setForeground(MAU_PHU);
        sdt.setForeground(MAU_PHU);
        mucno.setForeground(MAU_PHU);

        table.getTableHeader().setBackground(MAU_XANH);
        table.getTableHeader().setForeground(MAU_CHU);
    }



    public void laydulieu() {
        DAO_KeToan dao = new DAO_KeToan();

        try {
            List<Object[]> danhsach = dao.laydulieuKHno();
            model.setRowCount(0);

            int i = 1;

            for (Object[] dong : danhsach) {
                model.addRow(new Object[]{
                        i++,
                        dong[0],
                        dong[1],
                        dong[2],
                        dong[3],
                        dong[4],
                        dong[5],
                        dong[6]
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi lấy dữ liệu từ SQL: " + e.getMessage()
            );
        }
    }


    public void tongnohethong() {
        long tongnohethong = 0;
        long tongno = 0;

        for (int i = 0; i < table.getRowCount(); i++) {
            tongno = Long.parseLong(
                    table.getValueAt(i, 6)
                            .toString()
                            .replace(".", "")
            );

            tongnohethong = tongnohethong + tongno;
        }

        txttongduno.setText(
                String.format("%,d", tongnohethong).replace(",", ".")
        );
    }


    public void timkiem() {
        String tenkhach = txttenkhachhang.getText().trim();
        String sdt = txtsdt.getText().trim();
        String mucno = txtmucno.getSelectedItem().toString();

        int mucno1 = 0;
        int mucno2 = 0;
        int mucno3 = 0;

        if (mucno.equals("Lớn hơn 10000000 VNĐ")) {
            mucno1 = Integer.parseInt(
                    mucno.replaceAll("[^0-9]", "")
            );
        } else if (mucno.equals("Lớn hơn 30000000 VNĐ")) {
            mucno2 = Integer.parseInt(
                    mucno.replaceAll("[^0-9]", "")
            );
        } else if (mucno.equals("Lớn hơn 50000000 VNĐ")) {
            mucno3 = Integer.parseInt(
                    mucno.replaceAll("[^0-9]", "")
            );
        }

        for (int i = 0; i < table.getRowCount(); i++) {
            String ten = table.getValueAt(i, 2).toString();
            String dt = table.getValueAt(i, 3).toString();
            String tongno = table.getValueAt(i, 6).toString();
            int TongNo = Integer.parseInt(tongno);

            if (ten.equalsIgnoreCase(tenkhach)
                    && dt.equalsIgnoreCase(sdt)
                    && TongNo > mucno1) {
                table.setRowSelectionInterval(i, i);
                return;

            } else if (ten.equalsIgnoreCase(tenkhach)
                    && dt.equalsIgnoreCase(sdt)
                    && TongNo > mucno2) {
                table.setRowSelectionInterval(i, i);
                return;

            } else if (ten.equalsIgnoreCase(tenkhach)
                    && dt.equalsIgnoreCase(sdt)
                    && TongNo > mucno3) {
                table.setRowSelectionInterval(i, i);
                return;
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == btnchitietcongno) {
            int dong = table.getSelectedRow();

            if (dong == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng chọn khách hàng cần xem chi tiết!"
                );
                return;
            }

            String maKH = table.getValueAt(dong, 1).toString();

            new ManHinhChiTietCongNo(maKH).setVisible(true);

        } else if (o == btntimkiem) {
            timkiem();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManHinhDanhSachKHNo ui = new ManHinhDanhSachKHNo();
            ui.setVisible(true);
        });
    }
}
