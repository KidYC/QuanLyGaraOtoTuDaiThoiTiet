
package View.KeToan;

import DAO.ThongKeDoanhThu_DAO;
import DAO.ThongKeDoanhThu_DAO.ThongKeDoanhThuModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class ManHinhThongKeDoanhThu extends JFrame {

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

    private JPanel pnlHeader;
    private JPanel pnlCenter;
    private JPanel pnlKPI;

    private JPanel pnlCardTongHD;
    private JPanel pnlCardTongDoanhThu;

    private JPanel pnlBody;
    private JPanel pnlFilter;

    private JLabel lblTieuDe;
    private JLabel lblMoTa;

    private JLabel lblTongHD;
    private JLabel lblTongDoanhThu;

    private JLabel lblTitleTongHD;
    private JLabel lblTitleTongDoanhThu;

    private JLabel lblThang;
    private JLabel lblNam;

    private JComboBox<String> cbThang;
    private JComboBox<String> cbNam;

    private JButton btnLoc;
    private JButton btnLamMoi;
    private JButton btnXuatBaoCao;

    private JTable tblThongKe;
    private DefaultTableModel modelTable;

    private JScrollPane scrollPane;

    private ThongKeDoanhThu_DAO dao;

    public ManHinhThongKeDoanhThu() {

        dao = new ThongKeDoanhThu_DAO();

        setTitle("Thống kê doanh thu");
        setSize(1100, 680);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(MAU_NEN);
        setLayout(new BorderLayout());

        pnlHeader = new JPanel(new BorderLayout());

        pnlHeader.setBackground(MAU_NEN);

        pnlHeader.setBorder(
                new EmptyBorder(25, 25, 15, 25)
        );

        lblTieuDe = new JLabel(
                "THỐNG KÊ DOANH THU"
        );

        lblTieuDe.setFont(FONT_TIEU_DE);
        lblTieuDe.setForeground(MAU_TEXT);

        lblMoTa = new JLabel(
                "Theo dõi và tổng hợp doanh thu của gara"
        );

        lblMoTa.setFont(FONT_NHAN);
        lblMoTa.setForeground(MAU_PHU);

        JPanel pnlTitle = new JPanel();

        pnlTitle.setLayout(
                new BoxLayout(
                        pnlTitle,
                        BoxLayout.Y_AXIS
                )
        );

        pnlTitle.setOpaque(false);

        lblTieuDe.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        lblMoTa.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        pnlTitle.add(lblTieuDe);

        pnlTitle.add(
                Box.createVerticalStrut(5)
        );

        pnlTitle.add(lblMoTa);

        pnlHeader.add(
                pnlTitle,
                BorderLayout.WEST
        );

        add(pnlHeader, BorderLayout.NORTH);


        pnlCenter = new JPanel(
                new BorderLayout(0, 20)
        );

        pnlCenter.setBackground(MAU_NEN);

        pnlCenter.setBorder(
                new EmptyBorder(0, 25, 25, 25)
        );


        pnlKPI = new JPanel(
                new GridLayout(1, 2, 20, 0)
        );

        pnlKPI.setOpaque(false);

        pnlKPI.setPreferredSize(
                new Dimension(0, 115)
        );


        pnlCardTongHD = taoCardKPI(
                "TỔNG SỐ HÓA ĐƠN",
                "0",
                MAU_XANH
        );

        lblTitleTongHD =
                (JLabel) pnlCardTongHD.getComponent(0);

        lblTongHD =
                (JLabel) pnlCardTongHD.getComponent(1);

        pnlCardTongDoanhThu = taoCardKPI(
                "TỔNG DOANH THU GHI NHẬN",
                "0 đ",
                MAU_XANH_LA
        );

        lblTitleTongDoanhThu =
                (JLabel) pnlCardTongDoanhThu.getComponent(0);

        lblTongDoanhThu =
                (JLabel) pnlCardTongDoanhThu.getComponent(1);


        pnlKPI.add(pnlCardTongHD);
        pnlKPI.add(pnlCardTongDoanhThu);

        pnlCenter.add(
                pnlKPI,
                BorderLayout.NORTH
        );

        pnlBody = new JPanel(
                new BorderLayout(0, 15)
        );

        pnlBody.setBackground(MAU_NEN);


        pnlFilter = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        12,
                        15
                )
        );

        pnlFilter.setBackground(Color.WHITE);

        pnlFilter.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(MAU_VIEN),
                        new EmptyBorder(5, 10, 5, 10)
                )
        );


        lblThang = new JLabel("Tháng:");

        lblThang.setFont(FONT_NHAN_DAM);
        lblThang.setForeground(MAU_TEXT);


        cbThang = new JComboBox<>(
                new String[]{
                        "Tất cả",
                        "1", "2", "3", "4",
                        "5", "6", "7", "8",
                        "9", "10", "11", "12"
                }
        );

        lblNam = new JLabel("Năm:");

        lblNam.setFont(FONT_NHAN_DAM);
        lblNam.setForeground(MAU_TEXT);


        cbNam = new JComboBox<>(
                new String[]{
                        "Tất cả",
                        "2024",
                        "2025",
                        "2026"
                }
        );


        dinhDangComboBox(cbThang);
        dinhDangComboBox(cbNam);

        btnLoc = taoButton(
                "Lọc dữ liệu",
                MAU_XANH
        );

        btnLamMoi = taoButton(
                "Làm mới",
                MAU_PHU
        );

        btnXuatBaoCao = taoButton(
                "Xuất CSV",
                MAU_XANH_LA
        );

        pnlFilter.add(lblThang);
        pnlFilter.add(cbThang);

        pnlFilter.add(lblNam);
        pnlFilter.add(cbNam);

        pnlFilter.add(btnLoc);
        pnlFilter.add(btnLamMoi);
        pnlFilter.add(btnXuatBaoCao);


        pnlBody.add(
                pnlFilter,
                BorderLayout.NORTH
        );

        String[] tenCot = {
                "Tháng",
                "Năm",
                "Số lượng hóa đơn",
                "Doanh thu (VNĐ)"
        };


        modelTable = new DefaultTableModel(
                tenCot,
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };


        tblThongKe = new JTable(modelTable);

        tblThongKe.setFont(FONT_NHAN);

        tblThongKe.setRowHeight(35);

        tblThongKe.setBackground(Color.WHITE);

        tblThongKe.setForeground(MAU_TEXT);

        tblThongKe.setGridColor(MAU_VIEN);

        tblThongKe.setShowGrid(true);

        tblThongKe.setSelectionBackground(
                MAU_SIDEBAR_CHON
        );

        tblThongKe.setSelectionForeground(
                MAU_CHU_SIDEBAR
        );

        tblThongKe.setFillsViewportHeight(true);

        tblThongKe.setAutoCreateRowSorter(true);
        tblThongKe.getTableHeader().setFont(
                FONT_NHAN_DAM
        );

        tblThongKe.getTableHeader().setBackground(
                MAU_SIDEBAR
        );

        tblThongKe.getTableHeader().setForeground(
                MAU_CHU_SIDEBAR
        );

        tblThongKe.getTableHeader().setPreferredSize(
                new Dimension(0, 40)
        );


        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                JLabel.CENTER
        );


        tblThongKe.getColumnModel()
                .getColumn(0)
                .setCellRenderer(centerRenderer);

        tblThongKe.getColumnModel()
                .getColumn(1)
                .setCellRenderer(centerRenderer);

        tblThongKe.getColumnModel()
                .getColumn(2)
                .setCellRenderer(centerRenderer);
        DefaultTableCellRenderer rightRenderer =
                new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(
                JLabel.RIGHT
        );
        rightRenderer.setBorder(
                new EmptyBorder(0, 0, 0, 15)
        );
        tblThongKe.getColumnModel()
                .getColumn(3)
                .setCellRenderer(rightRenderer);
        scrollPane = new JScrollPane(tblThongKe);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(MAU_VIEN)
        );

        scrollPane.getViewport().setBackground(
                Color.WHITE
        );
        pnlBody.add(
                scrollPane,
                BorderLayout.CENTER
        );
        pnlCenter.add(
                pnlBody,
                BorderLayout.CENTER
        );
        add(
                pnlCenter,
                BorderLayout.CENTER
        );
        btnLoc.addActionListener(e -> loadData());
        btnLamMoi.addActionListener(e -> {
            cbThang.setSelectedIndex(0);
            cbNam.setSelectedIndex(0);
            loadData();
        });
        btnXuatBaoCao.addActionListener(
                e -> xuatFileCSV()
        );
        loadData();
    }
    private JPanel taoCardKPI(
            String title,
            String value,
            Color valueColor
    ) {
        JPanel card = new JPanel(
                new BorderLayout(5, 10)
        );
        card.setBackground(Color.WHITE);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(MAU_VIEN),
                        new EmptyBorder(15, 20, 15, 20)
                )
        );
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(FONT_NHAN_DAM);
        lblTitle.setForeground(MAU_PHU);
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(
                new Font("Segoe UI", Font.BOLD, 26)
        );
        lblValue.setForeground(valueColor);
        card.add(
                lblTitle,
                BorderLayout.NORTH
        );
        card.add(
                lblValue,
                BorderLayout.CENTER
        );
        return card;
    }
    private JButton taoButton(
            String text,
            Color background
    ) {
        JButton button = new JButton(text);
        button.setFont(FONT_NHAN_DAM);
        button.setBackground(background);
        if (background.equals(MAU_XANH)) {
            button.setForeground(MAU_TEXT);
        } else {
            button.setForeground(Color.WHITE);
        }
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        button.setPreferredSize(
                new Dimension(125, 35)
        );
        return button;
    }
    private void dinhDangComboBox(
            JComboBox<String> comboBox
    ) {
        comboBox.setFont(FONT_NHAN);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(MAU_TEXT);
        comboBox.setPreferredSize(
                new Dimension(110, 35)
        );
    }
    private void loadData() {
        modelTable.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###");
        int tongHD = 0;
        double tongDoanhThu = 0;
        try {
            List<ThongKeDoanhThuModel> ds =
                    dao.layDanhSachDoanhThu();
            String thangChon =
                    cbThang.getSelectedItem().toString();
            String namChon =
                    cbNam.getSelectedItem().toString();
            for (ThongKeDoanhThuModel item : ds) {
                boolean matchThang =
                        thangChon.equals("Tất cả")
                                || item.getThang()
                                == Integer.parseInt(thangChon);
                boolean matchNam =
                        namChon.equals("Tất cả")
                                || item.getNam()
                                == Integer.parseInt(namChon);
                if (matchThang && matchNam) {
                    modelTable.addRow(
                            new Object[]{
                                    "Tháng " + item.getThang(),
                                    item.getNam(),
                                    item.getTongSoHoaDon(),
                                    df.format(item.getDoanhThu())
                            }
                    );
                    tongHD += item.getTongSoHoaDon();
                    tongDoanhThu += item.getDoanhThu();
                }
            }
            lblTongHD.setText(
                    String.valueOf(tongHD)
            );
            lblTongDoanhThu.setText(
                    df.format(tongDoanhThu) + " đ"
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi tải dữ liệu: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void xuatFileCSV() {
        if (modelTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không có dữ liệu để xuất!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(
                "Xuất báo cáo doanh thu"
        );
        fc.setFileFilter(
                new FileNameExtensionFilter(
                        "CSV Files (*.csv)",
                        "csv"
                )
        );
        if (fc.showSaveDialog(this)
                == JFileChooser.APPROVE_OPTION) {
            String path =
                    fc.getSelectedFile().getAbsolutePath();
            if (!path.toLowerCase().endsWith(".csv")) {

                path += ".csv";
            }
            try (FileWriter writer = new FileWriter(
                    path,
                    java.nio.charset.StandardCharsets.UTF_8
            )) {
                writer.write("\ufeff");
                for (int i = 0;
                     i < modelTable.getColumnCount();
                     i++) {
                    writer.write(
                            "\"" + modelTable.getColumnName(i)
                                    + "\""
                                    + (
                                    i < modelTable.getColumnCount() - 1
                                            ? ","
                                            : "\n"
                            )
                    );
                }
                for (int r = 0;
                     r < modelTable.getRowCount();
                     r++) {
                    for (int c = 0;
                         c < modelTable.getColumnCount();
                         c++) {
                        writer.write(
                                "\"" + modelTable.getValueAt(r, c)
                                        + "\""
                                        + (
                                        c < modelTable.getColumnCount() - 1
                                                ? ","
                                                : "\n"
                                )
                        );
                    }
                }
                JOptionPane.showMessageDialog(
                        this,
                        "Xuất báo cáo thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Lỗi xuất file: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManHinhThongKeDoanhThu frame =
                    new ManHinhThongKeDoanhThu();
            frame.setVisible(true);
        });
    }
}

