package View.ThuKho;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class ManHinhDanhSachYeuCauXuatKho extends JFrame implements ActionListener {

    private JLabel lbltieude;

    private JTextField txtmayeucau;
    private JTextField txtnguoiyeucau;
    private JComboBox<String> cbtrangthai;
    private JTextField txttungay;
    private JTextField txtdenngay;
    private JButton btntim;
    private JButton btnlammoi;

    private JTable tblyeucau;
    private DefaultTableModel modelyeucau;
    private JTable tblchitiet;
    private DefaultTableModel modelchitiet;

    private JButton btnduyet;
    private JButton btntuchoi;
    private JButton btnlapphieu;
    private JButton btnquaylai;

    private ArrayList<String[]> dsyeucau = new ArrayList<>();
    private HashMap<String, Object[][]> dschitiet = new HashMap<>();

    private DateTimeFormatter dinhdangngay = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ManHinhDanhSachYeuCauXuatKho() {
        setTitle("DANH SÁCH YÊU CẦU XUẤT KHO");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        taoDuLieuMau();
        buildUI();
        napBangYeuCau(dsyeucau);
    }

    private void taoDuLieuMau() {
        dsyeucau.add(new String[]{"YC001", "02/10/2026", "Lê Văn C", "51A-123.45", "Chờ duyệt"});
        dsyeucau.add(new String[]{"YC002", "03/10/2026", "Phạm Văn D", "59B-678.90", "Đã duyệt"});
        dsyeucau.add(new String[]{"YC003", "04/10/2026", "Lê Văn C", "60C-111.22", "Đã xuất kho"});

        dschitiet.put("YC001", new Object[][]{
                {"PT002", "Lọc gió", "Cái", 2, 15},
                {"PT003", "Bugi", "Cái", 4, 5}
        });
        dschitiet.put("YC002", new Object[][]{
                {"PT001", "Dầu nhớt Castrol", "Chai", 1, 20}
        });
        dschitiet.put("YC003", new Object[][]{
                {"PT003", "Bugi", "Cái", 2, 5}
        });
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel("DANH SÁCH YÊU CẦU XUẤT KHO");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 22));
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbltieude.setForeground(new Color(44, 62, 80));
        lbltieude.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lbltieude, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel(new BorderLayout(0, 10));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pTimKiem = new JPanel(new GridLayout(2, 6, 10, 10));
        pTimKiem.setBackground(Color.WHITE);
        pTimKiem.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Tìm kiếm",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        txtmayeucau = new JTextField();
        txtnguoiyeucau = new JTextField();
        cbtrangthai = new JComboBox<>(new String[]{"Tất cả", "Chờ duyệt", "Đã duyệt", "Từ chối", "Đã xuất kho"});
        txttungay = new JTextField();
        txtdenngay = new JTextField();

        btntim = new JButton("Tìm");
        btntim.setBackground(new Color(52, 152, 219));
        btntim.setForeground(Color.WHITE);
        btntim.setFocusPainted(false);

        btnlammoi = new JButton("Làm Mới");
        btnlammoi.setBackground(new Color(149, 165, 166));
        btnlammoi.setForeground(Color.WHITE);
        btnlammoi.setFocusPainted(false);

        pTimKiem.add(new JLabel("Mã Yêu Cầu:"));
        pTimKiem.add(txtmayeucau);
        pTimKiem.add(new JLabel("Người Yêu Cầu:"));
        pTimKiem.add(txtnguoiyeucau);
        pTimKiem.add(new JLabel("Trạng Thái:"));
        pTimKiem.add(cbtrangthai);
        pTimKiem.add(new JLabel("Từ Ngày (dd/MM/yyyy):"));
        pTimKiem.add(txttungay);
        pTimKiem.add(new JLabel("Đến Ngày (dd/MM/yyyy):"));
        pTimKiem.add(txtdenngay);
        pTimKiem.add(btntim);
        pTimKiem.add(btnlammoi);

        pCenter.add(pTimKiem, BorderLayout.NORTH);

        JPanel pBang = new JPanel(new GridLayout(2, 1, 0, 10));
        pBang.setBackground(new Color(245, 245, 245));

        String[] columnyc = {"Mã Yêu Cầu", "Ngày Yêu Cầu", "Người Yêu Cầu", "Biển Số Xe", "Trạng Thái"};
        modelyeucau = new DefaultTableModel(columnyc, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblyeucau = new JTable(modelyeucau);
        tblyeucau.setRowHeight(25);
        JScrollPane scrollyc = new JScrollPane(tblyeucau);
        scrollyc.setBorder(BorderFactory.createTitledBorder("Danh sách yêu cầu xuất kho"));

        String[] columnct = {"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị", "SL Yêu Cầu", "SL Tồn Kho"};
        modelchitiet = new DefaultTableModel(columnct, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblchitiet = new JTable(modelchitiet);
        tblchitiet.setRowHeight(25);
        JScrollPane scrollct = new JScrollPane(tblchitiet);
        scrollct.setBorder(BorderFactory.createTitledBorder("Chi tiết yêu cầu"));

        pBang.add(scrollyc);
        pBang.add(scrollct);
        pCenter.add(pBang, BorderLayout.CENTER);

        add(pCenter, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnquaylai = new JButton("Quay Lại");
        btnquaylai.setPreferredSize(new Dimension(110, 35));
        btnquaylai.setBackground(new Color(149, 165, 166));
        btnquaylai.setForeground(Color.WHITE);
        btnquaylai.setFocusPainted(false);

        btntuchoi = new JButton("Từ Chối");
        btntuchoi.setPreferredSize(new Dimension(110, 35));
        btntuchoi.setBackground(new Color(231, 76, 60));
        btntuchoi.setForeground(Color.WHITE);
        btntuchoi.setFocusPainted(false);

        btnduyet = new JButton("Duyệt Yêu Cầu");
        btnduyet.setPreferredSize(new Dimension(140, 35));
        btnduyet.setBackground(new Color(52, 152, 219));
        btnduyet.setForeground(Color.WHITE);
        btnduyet.setFocusPainted(false);

        btnlapphieu = new JButton("Lập Phiếu Xuất Kho");
        btnlapphieu.setPreferredSize(new Dimension(170, 35));
        btnlapphieu.setBackground(new Color(46, 204, 113));
        btnlapphieu.setForeground(Color.WHITE);
        btnlapphieu.setFont(new Font("Arial", Font.BOLD, 12));
        btnlapphieu.setFocusPainted(false);

        pSouth.add(btnquaylai);
        pSouth.add(btntuchoi);
        pSouth.add(btnduyet);
        pSouth.add(btnlapphieu);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btntim.addActionListener(this);
        btnlammoi.addActionListener(this);
        btnduyet.addActionListener(this);
        btntuchoi.addActionListener(this);
        btnlapphieu.addActionListener(this);
        btnquaylai.addActionListener(this);

        // bấm vào một yêu cầu thì hiện chi tiết bên dưới
        tblyeucau.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyChonDong();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btntim) {
            xuLyTim();
        } else if (o == btnlammoi) {
            xuLyLamMoi();
        } else if (o == btnduyet) {
            xuLyDoiTrangThai("Đã duyệt");
        } else if (o == btntuchoi) {
            xuLyDoiTrangThai("Từ chối");
        } else if (o == btnlapphieu) {
            xuLyLapPhieu();
        } else if (o == btnquaylai) {
            xuLyQuayLai();
        }
    }

    private void napBangYeuCau(ArrayList<String[]> ds) {
        modelyeucau.setRowCount(0);
        modelchitiet.setRowCount(0);
        for (String[] yc : ds) {
            modelyeucau.addRow(yc);
        }
    }

    private void xuLyChonDong() {
        int dong = tblyeucau.getSelectedRow();
        if (dong == -1) return;

        String ma = modelyeucau.getValueAt(dong, 0).toString();
        modelchitiet.setRowCount(0);
        Object[][] ct = dschitiet.get(ma);
        if (ct != null) {
            for (Object[] dongct : ct) {
                modelchitiet.addRow(dongct);
            }
        }
    }

    private void xuLyTim() {
        LocalDate tungay = null;
        LocalDate denngay = null;
        try {
            if (!txttungay.getText().trim().isEmpty()) {
                tungay = LocalDate.parse(txttungay.getText().trim(), dinhdangngay);
            }
            if (!txtdenngay.getText().trim().isEmpty()) {
                denngay = LocalDate.parse(txtdenngay.getText().trim(), dinhdangngay);
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ngày phải nhập đúng dạng dd/MM/yyyy (ví dụ 03/10/2026)!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ma = txtmayeucau.getText().trim().toLowerCase();
        String nguoi = txtnguoiyeucau.getText().trim().toLowerCase();
        String trangthai = cbtrangthai.getSelectedItem().toString();

        ArrayList<String[]> ketqua = new ArrayList<>();
        for (String[] yc : dsyeucau) {
            if (!yc[0].toLowerCase().contains(ma)) continue;
            if (!yc[2].toLowerCase().contains(nguoi)) continue;
            if (!trangthai.equals("Tất cả") && !yc[4].equals(trangthai)) continue;

            LocalDate ngay = LocalDate.parse(yc[1], dinhdangngay);
            if (tungay != null && ngay.isBefore(tungay)) continue;
            if (denngay != null && ngay.isAfter(denngay)) continue;

            ketqua.add(yc);
        }

        napBangYeuCau(ketqua);
        if (ketqua.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy yêu cầu nào phù hợp!");
        }
    }

    private void xuLyLamMoi() {
        txtmayeucau.setText("");
        txtnguoiyeucau.setText("");
        cbtrangthai.setSelectedIndex(0);
        txttungay.setText("");
        txtdenngay.setText("");
        napBangYeuCau(dsyeucau);
    }

    private void xuLyDoiTrangThai(String trangthaimoi) {
        int dong = tblyeucau.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn yêu cầu cần xử lý!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ma = modelyeucau.getValueAt(dong, 0).toString();
        String hientai = modelyeucau.getValueAt(dong, 4).toString();
        if (!hientai.equals("Chờ duyệt")) {
            JOptionPane.showMessageDialog(this,
                    "Chỉ xử lý được yêu cầu đang ở trạng thái 'Chờ duyệt'!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int chon = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn chuyển yêu cầu " + ma + " sang '" + trangthaimoi + "' không?",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (chon != JOptionPane.YES_OPTION) return;

        for (String[] yc : dsyeucau) {
            if (yc[0].equals(ma)) {
                yc[4] = trangthaimoi;
            }
        }
        modelyeucau.setValueAt(trangthaimoi, dong, 4);
    }

    private void xuLyLapPhieu() {
        int dong = tblyeucau.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn yêu cầu cần lập phiếu xuất!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!modelyeucau.getValueAt(dong, 4).toString().equals("Đã duyệt")) {
            JOptionPane.showMessageDialog(this,
                    "Chỉ lập được phiếu xuất cho yêu cầu đã duyệt!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ManHinhLapPhieuXuatKho UI = new ManHinhLapPhieuXuatKho();
        UI.setVisible(true);
    }

    private void xuLyQuayLai() {
        this.dispose();
    }

    public static void main(String[] args) {
        ManHinhDanhSachYeuCauXuatKho form = new ManHinhDanhSachYeuCauXuatKho();
        form.setVisible(true);
    }
}