package View.ThuKho;

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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ManHinhCanhBaoTonKho extends JFrame implements ActionListener {

    private JLabel lbltieude;
    private JLabel lblsosaphet;
    private JLabel lblsohethang;

    private JTextField txttim;
    private JComboBox<String> cbmucdo;
    private JButton btntim;
    private JButton btnlammoi;

    private JTable tblcanhbao;
    private DefaultTableModel modelcanhbao;

    private JButton btnlapphieu;
    private JButton btnxuat;
    private JButton btnquaylai;

    private ArrayList<Object[]> dsphutung = new ArrayList<>();

    public ManHinhCanhBaoTonKho() {
        setTitle("CẢNH BÁO TỒN KHO");
        setSize(1000, 620);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        taoDuLieuMau();
        buildUI();
        napBang(dsphutung);
        capNhatThongKe();
    }

    private String layMucCanhBao(int soluong, int toithieu) {
        if (soluong == 0) return "Hết hàng";
        if (soluong < toithieu) return "Sắp hết hàng";
        return "Bình thường";
    }

    private void themPhuTung(String ma, String ten, String donvi, int soluong, int toithieu) {
        dsphutung.add(new Object[]{ma, ten, donvi, soluong, toithieu, layMucCanhBao(soluong, toithieu)});
    }

    private void taoDuLieuMau() {
        themPhuTung("PT003", "Bugi", "Cái", 5, 10);
        themPhuTung("PT004", "Má phanh trước", "Bộ", 3, 8);
        themPhuTung("PT005", "Nước làm mát", "Chai", 0, 10);
        themPhuTung("PT006", "Dây curoa", "Cái", 0, 5);
        themPhuTung("PT007", "Lọc dầu", "Cái", 4, 10);
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel("CẢNH BÁO TỒN KHO");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 22));
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbltieude.setForeground(new Color(44, 62, 80));
        lbltieude.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lbltieude, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel(new BorderLayout(0, 10));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pCard = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        pCard.setBackground(new Color(245, 245, 245));

        JPanel pCard1 = new JPanel(new GridLayout(2, 1));
        pCard1.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        pCard1.setBackground(new Color(255, 248, 225));
        pCard1.setPreferredSize(new Dimension(200, 80));
        JLabel lblsaphet = new JLabel("SẮP HẾT HÀNG", SwingConstants.CENTER);
        lblsaphet.setFont(new Font("Arial", Font.BOLD, 16));
        lblsosaphet = new JLabel("0", SwingConstants.CENTER);
        lblsosaphet.setFont(new Font("Arial", Font.BOLD, 20));
        lblsosaphet.setForeground(new Color(230, 126, 0));
        pCard1.add(lblsaphet);
        pCard1.add(lblsosaphet);

        JPanel pCard2 = new JPanel(new GridLayout(2, 1));
        pCard2.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        pCard2.setBackground(new Color(255, 235, 235));
        pCard2.setPreferredSize(new Dimension(200, 80));
        JLabel lblhethang = new JLabel("HẾT HÀNG", SwingConstants.CENTER);
        lblhethang.setFont(new Font("Arial", Font.BOLD, 16));
        lblsohethang = new JLabel("0", SwingConstants.CENTER);
        lblsohethang.setFont(new Font("Arial", Font.BOLD, 20));
        lblsohethang.setForeground(Color.RED);
        pCard2.add(lblhethang);
        pCard2.add(lblsohethang);

        pCard.add(pCard1);
        pCard.add(pCard2);

        JPanel pLoc = new JPanel(new GridLayout(1, 6, 10, 10));
        pLoc.setBackground(Color.WHITE);
        pLoc.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Tìm kiếm",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        txttim = new JTextField();
        cbmucdo = new JComboBox<>(new String[]{"Tất cả", "Sắp hết hàng", "Hết hàng"});

        btntim = new JButton("Tìm");
        btntim.setBackground(new Color(52, 152, 219));
        btntim.setForeground(Color.WHITE);
        btntim.setFocusPainted(false);

        btnlammoi = new JButton("Làm Mới");
        btnlammoi.setBackground(new Color(149, 165, 166));
        btnlammoi.setForeground(Color.WHITE);
        btnlammoi.setFocusPainted(false);

        pLoc.add(new JLabel("Mã/Tên Phụ Tùng:"));
        pLoc.add(txttim);
        pLoc.add(new JLabel("Mức Cảnh Báo:"));
        pLoc.add(cbmucdo);
        pLoc.add(btntim);
        pLoc.add(btnlammoi);

        JPanel pTren = new JPanel(new BorderLayout(0, 10));
        pTren.setBackground(new Color(245, 245, 245));
        pTren.add(pCard, BorderLayout.NORTH);
        pTren.add(pLoc, BorderLayout.CENTER);
        pCenter.add(pTren, BorderLayout.NORTH);

        String[] column = {"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị", "Số Lượng Tồn", "Mức Tồn Tối Thiểu", "Mức Cảnh Báo"};
        modelcanhbao = new DefaultTableModel(column, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblcanhbao = new JTable(modelcanhbao);
        tblcanhbao.setRowHeight(25);

        tblcanhbao.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
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

        JScrollPane scroll = new JScrollPane(tblcanhbao);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phụ tùng cần nhập thêm"));
        pCenter.add(scroll, BorderLayout.CENTER);

        add(pCenter, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnquaylai = new JButton("Quay Lại");
        btnquaylai.setPreferredSize(new Dimension(110, 35));
        btnquaylai.setBackground(new Color(149, 165, 166));
        btnquaylai.setForeground(Color.WHITE);
        btnquaylai.setFocusPainted(false);

        btnxuat = new JButton("Xuất Danh Sách");
        btnxuat.setPreferredSize(new Dimension(150, 35));
        btnxuat.setBackground(new Color(52, 152, 219));
        btnxuat.setForeground(Color.WHITE);
        btnxuat.setFocusPainted(false);

        btnlapphieu = new JButton("Lập Phiếu Nhập Kho");
        btnlapphieu.setPreferredSize(new Dimension(170, 35));
        btnlapphieu.setBackground(new Color(46, 204, 113));
        btnlapphieu.setForeground(Color.WHITE);
        btnlapphieu.setFont(new Font("Arial", Font.BOLD, 12));
        btnlapphieu.setFocusPainted(false);

        pSouth.add(btnquaylai);
        pSouth.add(btnxuat);
        pSouth.add(btnlapphieu);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btntim.addActionListener(this);
        btnlammoi.addActionListener(this);
        btnlapphieu.addActionListener(this);
        btnxuat.addActionListener(this);
        btnquaylai.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btntim) {
            xuLyTim();
        } else if (o == btnlammoi) {
            xuLyLamMoi();
        } else if (o == btnlapphieu) {
            xuLyLapPhieu();
        } else if (o == btnxuat) {

        } else if (o == btnquaylai) {
            xuLyQuayLai();
        }
    }

    private void napBang(ArrayList<Object[]> ds) {
        modelcanhbao.setRowCount(0);
        for (Object[] p : ds) {
            modelcanhbao.addRow(p);
        }
    }

    private void capNhatThongKe() {
        int saphet = 0;
        int hethang = 0;
        for (Object[] p : dsphutung) {
            if (p[5].equals("Sắp hết hàng")) saphet++;
            if (p[5].equals("Hết hàng")) hethang++;
        }
        lblsosaphet.setText(String.valueOf(saphet));
        lblsohethang.setText(String.valueOf(hethang));
    }

    private void xuLyTim() {
        String tukhoa = txttim.getText().trim().toLowerCase();
        String mucdo = cbmucdo.getSelectedItem().toString();

        ArrayList<Object[]> ketqua = new ArrayList<>();
        for (Object[] p : dsphutung) {
            boolean khopten = p[0].toString().toLowerCase().contains(tukhoa)
                    || p[1].toString().toLowerCase().contains(tukhoa);
            boolean khopmuc = mucdo.equals("Tất cả") || p[5].equals(mucdo);
            if (khopten && khopmuc) {
                ketqua.add(p);
            }
        }

        napBang(ketqua);
        if (ketqua.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phụ tùng nào phù hợp!");
        }
    }

    private void xuLyLamMoi() {
        txttim.setText("");
        cbmucdo.setSelectedIndex(0);
        napBang(dsphutung);
    }

    private void xuLyLapPhieu() {
        ManHinhLapPhieuNhapKho UI = new ManHinhLapPhieuNhapKho();
        UI.setVisible(true);
    }


    private void xuLyQuayLai() {
        this.dispose();
    }

    public static void main(String[] args) {
        ManHinhCanhBaoTonKho form = new ManHinhCanhBaoTonKho();
        form.setVisible(true);
    }
}