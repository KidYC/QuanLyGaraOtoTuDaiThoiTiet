
package View.KeToan;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import DAO.DAO_KeToan;
import java.sql.SQLException;
import java.util.List;

public class ManHinhDanhSachPhieuSuaChuaDaHoanThanh
        extends JFrame implements ActionListener {

    // Bảng màu thống nhất
    private static final Color MAU_NEN = new Color(244, 246, 248);
    private static final Color MAU_MENU = new Color(226, 232, 240);
    private static final Color MAU_XANH = new Color(186, 230, 253);
    private static final Color MAU_XANH_PHU = new Color(219, 234, 254);
    private static final Color MAU_CHU = new Color(17, 24, 39);
    private static final Color MAU_PHU = new Color(107, 114, 128);
    private static final Color MAU_VIEN = new Color(226, 232, 240);
    private static final Color MAU_DO = new Color(254, 226, 226);
    private static final Color MAU_TRANG = Color.WHITE;

    private static final Font FONT_TIEU_DE =
            new Font("Segoe UI", Font.BOLD, 23);
    private static final Font FONT_MUC =
            new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONT_NHAN =
            new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_DAM =
            new Font("Segoe UI", Font.BOLD, 14);

    private final JPanel pCenter;
    private final JPanel pNorth;
    private final JLabel lbltieude;
    private final JPanel pWest;
    private final JLabel lblmenu;
    private final JPanel card;
    private final JLabel trangthai;
    private final JLabel timkiem;
    private final JTextField txttimkiem;
    private final JComboBox<String> txttrangthai;
    private final DefaultTableModel model;
    private final JTable table;
    private final JScrollPane scroll;
    private final JButton btnTim;
    private final JLabel khoangtrong;

    private JButton trangchu;
    private JButton qlca;
    private JButton dsphieusuachua;
    private JButton quanlycongno;
    private JButton baocaodoanhthu;
    private JButton quaylai;

    public ManHinhDanhSachPhieuSuaChuaDaHoanThanh() {
        super("Danh sách phiếu sửa chữa đã hoàn thành");

        setSize(1200, 750);
        setMinimumSize(new Dimension(950, 600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(MAU_NEN);

        pNorth = new JPanel();
        pNorth.setLayout(new BoxLayout(pNorth, BoxLayout.Y_AXIS));
        pNorth.setBackground(MAU_NEN);
        pNorth.setBorder(
                BorderFactory.createEmptyBorder(24, 28, 20, 28)
        );

        lbltieude = new JLabel(
                "DANH SÁCH PHIẾU SỬA CHỮA ĐÃ HOÀN THÀNH"
        );
        lbltieude.setFont(FONT_TIEU_DE);
        lbltieude.setForeground(MAU_CHU);
        lbltieude.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblMoTa = new JLabel(
                "Tra cứu và theo dõi thông tin các phiếu sửa chữa"
        );
        lblMoTa.setFont(FONT_NHAN);
        lblMoTa.setForeground(MAU_PHU);
        lblMoTa.setAlignmentX(Component.LEFT_ALIGNMENT);

        pNorth.add(lbltieude);
        pNorth.add(Box.createVerticalStrut(6));
        pNorth.add(lblMoTa);

        add(pNorth, BorderLayout.NORTH);

        pWest = new JPanel();
        pWest.setLayout(new BoxLayout(pWest, BoxLayout.Y_AXIS));
        pWest.setBackground(MAU_MENU);
        pWest.setPreferredSize(new Dimension(220, 0));
        pWest.setBorder(
                BorderFactory.createEmptyBorder(22, 12, 20, 12)
        );

        lblmenu = new JLabel("MENU CHÍNH");
        lblmenu.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblmenu.setForeground(MAU_CHU);
        lblmenu.setAlignmentX(Component.CENTER_ALIGNMENT);

        trangchu = taoNutBoGoc("Trang chủ kế toán", MAU_XANH);
        qlca = taoNutBoGoc("Quản lý ca", MAU_XANH);
        quanlycongno = taoNutBoGoc("Quản lý công nợ", MAU_XANH);
        baocaodoanhthu = taoNutBoGoc("Báo cáo doanh thu", MAU_XANH);
        quaylai = taoNutBoGoc(
                "Quay lại trang chủ", MAU_XANH_PHU
        );

        JButton[] cacNutMenu = {
                trangchu, qlca, quanlycongno,
                baocaodoanhthu, quaylai
        };

        pWest.add(lblmenu);
        pWest.add(Box.createVerticalStrut(28));

        for (JButton nut : cacNutMenu) {
            nut.setAlignmentX(Component.CENTER_ALIGNMENT);
            nut.setMaximumSize(new Dimension(196, 44));
            nut.setPreferredSize(new Dimension(196, 44));
            nut.setMinimumSize(new Dimension(170, 40));

            pWest.add(nut);
            pWest.add(Box.createVerticalStrut(14));
        }

        pWest.add(Box.createVerticalGlue());
        add(pWest, BorderLayout.WEST);

        pCenter = new JPanel();
        pCenter.setLayout(new BorderLayout(0, 18));
        pCenter.setBackground(MAU_NEN);
        pCenter.setBorder(
                BorderFactory.createEmptyBorder(4, 24, 24, 24)
        );

        card = new JPanel(new GridBagLayout());
        card.setBackground(MAU_TRANG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAU_VIEN),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        timkiem = new JLabel("Mã phiếu sửa chữa:");
        timkiem.setFont(FONT_DAM);
        timkiem.setForeground(MAU_CHU);

        txttimkiem = new JTextField();
        txttimkiem.setFont(FONT_NHAN);
        txttimkiem.setForeground(MAU_CHU);
        txttimkiem.setBackground(MAU_TRANG);
        txttimkiem.setPreferredSize(new Dimension(220, 38));
        txttimkiem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAU_VIEN),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)
        ));

        trangthai = new JLabel("Trạng thái:");
        trangthai.setFont(FONT_DAM);
        trangthai.setForeground(MAU_CHU);

        txttrangthai = new JComboBox<>(
                new String[]{"Tất cả", "Đang xử lý", "Hoàn thành"}
        );
        txttrangthai.setFont(FONT_NHAN);
        txttrangthai.setForeground(MAU_CHU);
        txttrangthai.setBackground(MAU_TRANG);
        txttrangthai.setPreferredSize(new Dimension(170, 38));

        btnTim = taoNutBoGoc("Tìm kiếm", MAU_XANH);
        btnTim.setPreferredSize(new Dimension(130, 40));

        khoangtrong = new JLabel("");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        card.add(timkiem, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        card.add(txttimkiem, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        card.add(trangthai, gbc);

        gbc.gridx = 3;
        gbc.weightx = 1;
        card.add(txttrangthai, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0;
        card.add(btnTim, gbc);

        JPanel pTimKiem = new JPanel(new BorderLayout(0, 10));
        pTimKiem.setBackground(MAU_NEN);

        JLabel lblTimKiem = new JLabel("TÌM KIẾM & BỘ LỌC");
        lblTimKiem.setFont(FONT_MUC);
        lblTimKiem.setForeground(MAU_CHU);

        pTimKiem.add(lblTimKiem, BorderLayout.NORTH);
        pTimKiem.add(card, BorderLayout.CENTER);

        String[] columns = {
                "STT",
                "Mã Phiếu",
                "Biển Số Xe",
                "Tên Khách Hàng",
                "Số Điện Thoại",
                "Kỹ Thuật Viên",
                "Tổng Tiền",
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
        table.setBackground(MAU_TRANG);
        table.setSelectionBackground(MAU_XANH_PHU);
        table.setSelectionForeground(MAU_CHU);
        table.setGridColor(MAU_VIEN);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
                55, 120, 125, 190, 145, 175, 130, 130
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
                .getColumn(2)
                .setCellRenderer(canGiua);
        table.getColumnModel()
                .getColumn(4)
                .setCellRenderer(canGiua);
        table.getColumnModel()
                .getColumn(7)
                .setCellRenderer(canGiua);

        DefaultTableCellRenderer canPhai =
                new DefaultTableCellRenderer();
        canPhai.setHorizontalAlignment(SwingConstants.RIGHT);
        canPhai.setBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 12)
        );
        table.getColumnModel()
                .getColumn(6)
                .setCellRenderer(canPhai);

        scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(MAU_VIEN));
        scroll.getViewport().setBackground(MAU_TRANG);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JPanel pBang = new JPanel(new BorderLayout(0, 12));
        pBang.setBackground(MAU_NEN);

        JLabel lblDanhSach = new JLabel("DANH SÁCH PHIẾU SỬA CHỮA");
        lblDanhSach.setFont(FONT_MUC);
        lblDanhSach.setForeground(MAU_CHU);

        pBang.add(lblDanhSach, BorderLayout.NORTH);
        pBang.add(scroll, BorderLayout.CENTER);

        pCenter.add(pTimKiem, BorderLayout.NORTH);
        pCenter.add(pBang, BorderLayout.CENTER);

        add(pCenter, BorderLayout.CENTER);

        trangchu.addActionListener(this);
        qlca.addActionListener(this);
        quanlycongno.addActionListener(this);
        baocaodoanhthu.addActionListener(this);
        quaylai.addActionListener(this);
        btnTim.addActionListener(this);

        chinhmau();
        laydulieu();
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
                } else if (getModel().isRollover()) {
                    mau = mau.brighter();
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
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );

        return btn;
    }


    private void chinhmau() {
        getContentPane().setBackground(MAU_NEN);
        pNorth.setBackground(MAU_NEN);
        pWest.setBackground(MAU_MENU);
        pCenter.setBackground(MAU_NEN);
        card.setBackground(MAU_TRANG);

        lbltieude.setForeground(MAU_CHU);
        lblmenu.setForeground(MAU_CHU);
        timkiem.setForeground(MAU_CHU);
        trangthai.setForeground(MAU_CHU);

        table.getTableHeader().setBackground(MAU_XANH);
        table.getTableHeader().setForeground(MAU_CHU);
    }

    public void laydulieu() {
        DAO_KeToan dao = new DAO_KeToan();

        try {
            List<Object[]> danhSach =
                    dao.layDanhSachPhieuSuaChuaDaHoanThanh();

            model.setRowCount(0);
            int i = 1;

            for (Object[] dong : danhSach) {
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
                    "Lỗi lấy dữ liệu từ SQL: " + e.getMessage(),
                    "Lỗi dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    public void TimKiem() {
        String maphieu = txttimkiem.getText().trim();

        if (maphieu.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập thông tin tìm kiếm"
            );
            return;
        }

        table.clearSelection();

        for (int i = 0; i < table.getRowCount(); i++) {
            String ma = table.getValueAt(i, 1).toString();

            if (ma.equalsIgnoreCase(maphieu)) {
                table.setRowSelectionInterval(i, i);
                table.scrollRectToVisible(
                        table.getCellRect(i, 0, true)
                );
                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Không tìm thấy mã phiếu!"
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == trangchu) {
            ManHinhTrangChuKeToan ui =
                    new ManHinhTrangChuKeToan();
            ui.setVisible(true);
            dispose();

        } else if (o == qlca) {
            ManHinhQuanLyCa ui = new ManHinhQuanLyCa();
            ui.setVisible(true);
            dispose();

        } else if (o == quanlycongno) {
            ManHinhDanhSachKHNo ui =
                    new ManHinhDanhSachKHNo();
            ui.setVisible(true);
            dispose();

        } else if (o == quaylai) {
            ManHinhTrangChuKeToan ui =
                    new ManHinhTrangChuKeToan();
            ui.setVisible(true);
            dispose();

        } else if (o == btnTim) {
            TimKiem();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManHinhDanhSachPhieuSuaChuaDaHoanThanh ui =
                    new ManHinhDanhSachPhieuSuaChuaDaHoanThanh();
            ui.setVisible(true);
        });
    }
}
