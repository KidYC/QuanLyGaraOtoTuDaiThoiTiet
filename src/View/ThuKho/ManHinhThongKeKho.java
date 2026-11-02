package View.ThuKho;

import DAO.ThongKeKho_DAO;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManHinhThongKeKho extends JFrame implements ActionListener {

    private static final String TON_KHO = "Tồn kho theo phụ tùng";
    private static final String CANH_BAO = "Cảnh báo tồn kho";
    private static final String THEO_KHO = "Tồn kho theo kho";
    private static final String NHAP_KHO = "Nhập kho";
    private static final String XUAT_KHO = "Xuất kho";

    private static final int MUC_TON_TOI_THIEU = 5;

    private JLabel lbltieude;
    private JLabel lblsomathang;
    private JLabel lblgiatriton;
    private JLabel lblsosaphet;
    private JLabel lblsohethang;

    private JComboBox<String> cbloai;
    private JComboBox<String> cbthang;
    private JTextField txtnam;
    private JTextField txttim;
    private JComboBox<String> cbtrangthai;
    private JButton btnloc;
    private JButton btnlammoi;

    private JTable tblthongke;
    private DefaultTableModel modelthongke;
    private JLabel lbltongket;

    private JButton btnxuat;
    private JButton btnquaylai;

    private final ThongKeKho_DAO thongKeDAO = new ThongKeKho_DAO();
    private final DecimalFormat dinhdang = new DecimalFormat("#,###");

    private List<Object[]> dsketqua = new ArrayList<>();

    public ManHinhThongKeKho() {
        setTitle("THỐNG KÊ KHO");
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();
        capNhatTongQuan();
        xuLyLoc();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel("THỐNG KÊ KHO");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 22));
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbltieude.setForeground(new Color(44, 62, 80));
        lbltieude.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lbltieude, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel(new BorderLayout(0, 10));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pCard = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        pCard.setBackground(new Color(245, 245, 245));

        lblsomathang = new JLabel("0", SwingConstants.CENTER);
        lblgiatriton = new JLabel("0", SwingConstants.CENTER);
        lblsosaphet = new JLabel("0", SwingConstants.CENTER);
        lblsohethang = new JLabel("0", SwingConstants.CENTER);

        pCard.add(taoThe("TỔNG MẶT HÀNG", lblsomathang, new Color(232, 244, 253), new Color(41, 128, 185)));
        pCard.add(taoThe("GIÁ TRỊ TỒN KHO", lblgiatriton, new Color(232, 248, 240), new Color(39, 174, 96)));
        pCard.add(taoThe("SẮP HẾT HÀNG", lblsosaphet, new Color(255, 248, 225), new Color(230, 126, 0)));
        pCard.add(taoThe("HẾT HÀNG", lblsohethang, new Color(255, 235, 235), Color.RED));

        JPanel pLoc = new JPanel(new GridLayout(2, 6, 10, 10));
        pLoc.setBackground(Color.WHITE);
        pLoc.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thống kê",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        cbloai = new JComboBox<>(new String[]{TON_KHO, CANH_BAO, THEO_KHO, NHAP_KHO, XUAT_KHO});

        String[] dsthang = new String[13];
        dsthang[0] = "Cả năm";
        for (int i = 1; i <= 12; i++) {
            dsthang[i] = "Tháng " + i;
        }
        cbthang = new JComboBox<>(dsthang);
        cbthang.setSelectedIndex(LocalDate.now().getMonthValue());
        txtnam = new JTextField(String.valueOf(LocalDate.now().getYear()));

        txttim = new JTextField();
        cbtrangthai = new JComboBox<>(new String[]{"Tất cả", "Bình thường", "Sắp hết hàng", "Hết hàng"});

        btnloc = new JButton("Lọc");
        btnloc.setBackground(new Color(52, 152, 219));
        btnloc.setForeground(Color.WHITE);
        btnloc.setFocusPainted(false);

        btnlammoi = new JButton("Làm Mới");
        btnlammoi.setBackground(new Color(149, 165, 166));
        btnlammoi.setForeground(Color.WHITE);
        btnlammoi.setFocusPainted(false);

        pLoc.add(new JLabel("Loại Thống Kê:"));
        pLoc.add(cbloai);
        pLoc.add(new JLabel("Tháng:"));
        pLoc.add(cbthang);
        pLoc.add(new JLabel("Năm:"));
        pLoc.add(txtnam);
        pLoc.add(new JLabel("Mã/Tên:"));
        pLoc.add(txttim);
        pLoc.add(new JLabel("Trạng Thái Tồn:"));
        pLoc.add(cbtrangthai);
        pLoc.add(btnloc);
        pLoc.add(btnlammoi);

        JPanel pTren = new JPanel(new BorderLayout(0, 10));
        pTren.setBackground(new Color(245, 245, 245));
        pTren.add(pCard, BorderLayout.NORTH);
        pTren.add(pLoc, BorderLayout.CENTER);
        pCenter.add(pTren, BorderLayout.NORTH);

        modelthongke = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblthongke = new JTable(modelthongke);
        tblthongke.setRowHeight(25);


        tblthongke.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    if ("Hết hàng".equals(value)) {
                        c.setForeground(Color.RED);
                    } else if ("Sắp hết hàng".equals(value)) {
                        c.setForeground(new Color(230, 126, 0));
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tblthongke);
        scroll.setBorder(BorderFactory.createTitledBorder("Kết quả thống kê"));
        pCenter.add(scroll, BorderLayout.CENTER);

        add(pCenter, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel(new BorderLayout());
        pSouth.setBackground(new Color(245, 245, 245));
        pSouth.setBorder(new EmptyBorder(0, 20, 0, 5));

        lbltongket = new JLabel("");
        lbltongket.setFont(new Font("Arial", Font.BOLD, 14));
        lbltongket.setForeground(new Color(192, 57, 43));

        JPanel pNut = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pNut.setBackground(new Color(245, 245, 245));

        btnquaylai = new JButton("Quay Lại");
        btnquaylai.setPreferredSize(new Dimension(110, 35));
        btnquaylai.setBackground(new Color(149, 165, 166));
        btnquaylai.setForeground(Color.WHITE);
        btnquaylai.setFocusPainted(false);

        btnxuat = new JButton("Xuất File CSV");
        btnxuat.setPreferredSize(new Dimension(150, 35));
        btnxuat.setBackground(new Color(46, 204, 113));
        btnxuat.setForeground(Color.WHITE);
        btnxuat.setFocusPainted(false);

        pNut.add(btnquaylai);
        pNut.add(btnxuat);

        pSouth.add(lbltongket, BorderLayout.WEST);
        pSouth.add(pNut, BorderLayout.EAST);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
        capNhatOLoc();
    }

    private JPanel taoThe(String tieude, JLabel lblso, Color mauNen, Color mauSo) {
        JPanel pThe = new JPanel(new GridLayout(2, 1));
        pThe.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        pThe.setBackground(mauNen);
        pThe.setPreferredSize(new Dimension(220, 80));
        JLabel lbl = new JLabel(tieude, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        lblso.setFont(new Font("Arial", Font.BOLD, 20));
        lblso.setForeground(mauSo);
        pThe.add(lbl);
        pThe.add(lblso);
        return pThe;
    }

    private void initListener() {
        cbloai.addActionListener(this);
        btnloc.addActionListener(this);
        btnlammoi.addActionListener(this);
        txttim.addActionListener(this);
        btnxuat.addActionListener(this);
        btnquaylai.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == cbloai) {
            capNhatOLoc();
            xuLyLoc();
        } else if (o == btnloc || o == txttim) {
            xuLyLoc();
        } else if (o == btnlammoi) {
            xuLyLamMoi();
        } else if (o == btnxuat) {
            xuLyXuatFile();
        } else if (o == btnquaylai) {
            xuLyQuayLai();
        }
    }

    private void capNhatOLoc() {
        String loai = cbloai.getSelectedItem().toString();
        boolean theoThoiGian = loai.equals(NHAP_KHO) || loai.equals(XUAT_KHO);
        cbthang.setEnabled(theoThoiGian);
        txtnam.setEnabled(theoThoiGian);
        cbtrangthai.setEnabled(loai.equals(TON_KHO) || loai.equals(CANH_BAO));
        txttim.setEnabled(!loai.equals(THEO_KHO));
    }

    private String trangThaiTon(int soluong) {
        if (soluong == 0) return "Hết hàng";
        if (soluong < MUC_TON_TOI_THIEU) return "Sắp hết hàng";
        return "Bình thường";
    }

    private void capNhatTongQuan() {
        int somathang = 0;
        int saphet = 0;
        int hethang = 0;
        double giatri = 0;
        try {
            for (Object[] p : thongKeDAO.thongKeTonKho()) {
                int ton = (int) p[4];
                somathang++;
                giatri += ton * (double) p[5];
                if (trangThaiTon(ton).equals("Sắp hết hàng")) saphet++;
                if (trangThaiTon(ton).equals("Hết hàng")) hethang++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage());
        }
        lblsomathang.setText(String.valueOf(somathang));
        lblgiatriton.setText(dinhdang.format(giatri) + " đ");
        lblsosaphet.setText(String.valueOf(saphet));
        lblsohethang.setText(String.valueOf(hethang));
    }

    private void xuLyLoc() {
        String loai = cbloai.getSelectedItem().toString();
        try {
            if (loai.equals(TON_KHO) || loai.equals(CANH_BAO)) {
                dsketqua = thongKeDAO.thongKeTonKho();
            } else if (loai.equals(THEO_KHO)) {
                dsketqua = thongKeDAO.thongKeTheoKho();
            } else {
                Timestamp[] khoang = layKhoangThoiGian();
                if (khoang == null) return;
                dsketqua = loai.equals(NHAP_KHO)
                        ? thongKeDAO.thongKeNhapKho(khoang[0], khoang[1])
                        : thongKeDAO.thongKeXuatKho(khoang[0], khoang[1]);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi thống kê: " + e.getMessage());
            dsketqua = new ArrayList<>();
        }
        napBang();
    }

    private Timestamp[] layKhoangThoiGian() {
        int nam;
        try {
            nam = Integer.parseInt(txtnam.getText().trim());
            if (nam < 2000 || nam > 2100) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Năm phải là số từ 2000 đến 2100!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtnam.requestFocus();
            return null;
        }
        int thang = cbthang.getSelectedIndex();   // 0 = cả năm
        LocalDate tungay = (thang == 0) ? LocalDate.of(nam, 1, 1) : LocalDate.of(nam, thang, 1);
        LocalDate denngay = (thang == 0) ? tungay.plusYears(1) : tungay.plusMonths(1);
        return new Timestamp[]{
                Timestamp.valueOf(tungay.atStartOfDay()),
                Timestamp.valueOf(denngay.atStartOfDay())};
    }

    private void napBang() {
        String loai = cbloai.getSelectedItem().toString();
        String tukhoa = txttim.getText().trim().toLowerCase();
        String trangthai = cbtrangthai.getSelectedItem().toString();

        modelthongke.setRowCount(0);
        int sodong = 0;
        long tongsoluong = 0;
        double tongtien = 0;

        if (loai.equals(CANH_BAO)) {
            List<Object[]> dscanhbao = new ArrayList<>();
            for (Object[] p : dsketqua) {
                if (!trangThaiTon((int) p[4]).equals("Bình thường")) {
                    dscanhbao.add(p);
                }
            }
            dscanhbao.sort((a, b) -> Integer.compare((int) a[4], (int) b[4]));

            modelthongke.setColumnIdentifiers(new String[]{"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị", "Kho",
                    "SL Tồn", "Mức Tồn Tối Thiểu", "Cần Nhập Thêm", "Mức Cảnh Báo"});
            int sohethang = 0;
            for (Object[] p : dscanhbao) {
                if (!khopTuKhoa(p, tukhoa)) continue;
                int ton = (int) p[4];
                String tt = trangThaiTon(ton);
                if (!trangthai.equals("Tất cả") && !tt.equals(trangthai)) continue;
                modelthongke.addRow(new Object[]{p[0], p[1], p[2], p[3] == null ? "" : p[3],
                        ton, MUC_TON_TOI_THIEU, MUC_TON_TOI_THIEU - ton, tt});
                sodong++;
                if (ton == 0) sohethang++;
            }
            lbltongket.setText(sodong + " mặt hàng cần nhập thêm  |  Hết hàng: " + sohethang
                    + "  |  Sắp hết hàng: " + (sodong - sohethang));

        } else if (loai.equals(TON_KHO)) {
            modelthongke.setColumnIdentifiers(new String[]{"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị", "Kho",
                    "SL Tồn", "Đơn Giá Nhập", "Giá Trị Tồn", "Trạng Thái"});
            for (Object[] p : dsketqua) {
                if (!khopTuKhoa(p, tukhoa)) continue;
                int ton = (int) p[4];
                String tt = trangThaiTon(ton);
                if (!trangthai.equals("Tất cả") && !tt.equals(trangthai)) continue;
                double giatri = ton * (double) p[5];
                modelthongke.addRow(new Object[]{p[0], p[1], p[2], p[3] == null ? "" : p[3],
                        ton, dinhdang.format(p[5]), dinhdang.format(giatri), tt});
                sodong++;
                tongsoluong += ton;
                tongtien += giatri;
            }
            lbltongket.setText(sodong + " mặt hàng  |  Tổng tồn: " + tongsoluong
                    + "  |  Giá trị: " + dinhdang.format(tongtien) + " đ");

        } else if (loai.equals(THEO_KHO)) {
            modelthongke.setColumnIdentifiers(new String[]{"Mã Kho", "Tên Kho", "Số Mặt Hàng",
                    "Tổng SL Tồn", "Giá Trị Tồn"});
            for (Object[] k : dsketqua) {
                modelthongke.addRow(new Object[]{k[0], k[1], k[2], k[3], dinhdang.format(k[4])});
                sodong++;
                tongsoluong += (int) k[3];
                tongtien += (double) k[4];
            }
            lbltongket.setText(sodong + " kho  |  Tổng tồn: " + tongsoluong
                    + "  |  Giá trị: " + dinhdang.format(tongtien) + " đ");

        } else {
            boolean nhap = loai.equals(NHAP_KHO);
            modelthongke.setColumnIdentifiers(nhap
                    ? new String[]{"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị", "Số Phiếu Nhập", "Tổng SL Nhập", "Tổng Tiền Nhập"}
                    : new String[]{"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị", "Số Phiếu Sửa Chữa", "Tổng SL Xuất", "Tổng Tiền Bán"});
            for (Object[] p : dsketqua) {
                if (!khopTuKhoa(p, tukhoa)) continue;
                modelthongke.addRow(new Object[]{p[0], p[1], p[2], p[3], p[4], dinhdang.format(p[5])});
                sodong++;
                tongsoluong += (int) p[4];
                tongtien += (double) p[5];
            }
            String thoigian = (cbthang.getSelectedIndex() == 0 ? "Năm " : cbthang.getSelectedItem() + "/")
                    + txtnam.getText().trim();
            lbltongket.setText(thoigian + ": " + sodong + " mặt hàng  |  Tổng SL " + (nhap ? "nhập" : "xuất") + ": "
                    + tongsoluong + "  |  Tổng tiền: " + dinhdang.format(tongtien) + " đ");
        }
    }

    private boolean khopTuKhoa(Object[] p, String tukhoa) {
        return tukhoa.isEmpty()
                || p[0].toString().toLowerCase().contains(tukhoa)
                || p[1].toString().toLowerCase().contains(tukhoa);
    }

    private void xuLyLamMoi() {
        txttim.setText("");
        cbtrangthai.setSelectedIndex(0);
        cbthang.setSelectedIndex(LocalDate.now().getMonthValue());
        txtnam.setText(String.valueOf(LocalDate.now().getYear()));
        capNhatTongQuan();
        xuLyLoc();
    }

    // Xuất bảng đang hiện ra file CSV (mở được bằng Excel)
    private void xuLyXuatFile() {
        if (modelthongke.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Không có dữ liệu để xuất!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JFileChooser chon = new JFileChooser();
        chon.setSelectedFile(new File("ThongKeKho.csv"));
        if (chon.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File file = chon.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new File(file.getParentFile(), file.getName() + ".csv");
        }

        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            pw.print('\uFEFF');
            pw.println(cbloai.getSelectedItem() + "," + lbltongket.getText().replace(",", ""));
            StringBuilder dong = new StringBuilder();
            for (int c = 0; c < modelthongke.getColumnCount(); c++) {
                if (c > 0) dong.append(",");
                dong.append(modelthongke.getColumnName(c));
            }
            pw.println(dong);
            for (int r = 0; r < modelthongke.getRowCount(); r++) {
                dong = new StringBuilder();
                for (int c = 0; c < modelthongke.getColumnCount(); c++) {
                    if (c > 0) dong.append(",");
                    dong.append("\"").append(String.valueOf(modelthongke.getValueAt(r, c)).replace("\"", "\"\"")).append("\"");
                }
                pw.println(dong);
            }
            JOptionPane.showMessageDialog(this, "Đã xuất file: " + file.getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi xuất file: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyQuayLai() {
        this.dispose();
    }

    public static void main(String[] args) {
        ManHinhThongKeKho form = new ManHinhThongKeKho();
        form.setVisible(true);
    }
}
