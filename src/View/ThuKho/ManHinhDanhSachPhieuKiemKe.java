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

public class ManHinhDanhSachPhieuKiemKe extends JFrame implements ActionListener {

    private JLabel lbltieude;

    private JTextField txtmaphieu;
    private JComboBox<String> cbtrangthai;
    private JTextField txttungay;
    private JTextField txtdenngay;
    private JButton btntim;
    private JButton btnlammoi;

    private JTable tblphieu;
    private DefaultTableModel modelphieu;
    private JTable tblchitiet;
    private DefaultTableModel modelchitiet;

    private JButton btnthem;
    private JButton btnsua;
    private JButton btnxoa;
    private JButton btnin;
    private JButton btnquaylai;

    private ArrayList<String[]> dsphieu = new ArrayList<>();
    private HashMap<String, Object[][]> dschitiet = new HashMap<>();

    private DateTimeFormatter dinhdangngay = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ManHinhDanhSachPhieuKiemKe() {
        setTitle("DANH SÁCH PHIẾU KIỂM KÊ");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        taoDuLieuMau();
        buildUI();
        napBangPhieu(dsphieu);
    }

    private void taoDuLieuMau() {
        dschitiet.put("KK001", new Object[][]{
                {"PT001", "Dầu nhớt Castrol", "Chai", 20, 20, 0},
                {"PT002", "Lọc gió", "Cái", 15, 15, 0},
                {"PT003", "Bugi", "Cái", 5, 5, 0}
        });
        dschitiet.put("KK002", new Object[][]{
                {"PT002", "Lọc gió", "Cái", 15, 14, -1},
                {"PT001", "Dầu nhớt Castrol", "Chai", 20, 20, 0}
        });
        dschitiet.put("KK003", new Object[][]{
                {"PT003", "Bugi", "Cái", 5, 6, 1},
                {"PT002", "Lọc gió", "Cái", 14, 14, 0}
        });

        dsphieu.add(new String[]{"KK001", "28/09/2026", String.valueOf(dschitiet.get("KK001").length), "Hoàn thành"});
        dsphieu.add(new String[]{"KK002", "01/10/2026", String.valueOf(dschitiet.get("KK002").length), "Hoàn thành"});
        dsphieu.add(new String[]{"KK003", "04/10/2026", String.valueOf(dschitiet.get("KK003").length), "Đang kiểm kê"});
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel("DANH SÁCH PHIẾU KIỂM KÊ");
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

        txtmaphieu = new JTextField();
        cbtrangthai = new JComboBox<>(new String[]{"Tất cả", "Đang kiểm kê", "Hoàn thành"});
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

        pTimKiem.add(new JLabel("Mã Phiếu:"));
        pTimKiem.add(txtmaphieu);
        pTimKiem.add(new JLabel("Trạng Thái:"));
        pTimKiem.add(cbtrangthai);
        pTimKiem.add(new JLabel(""));
        pTimKiem.add(new JLabel(""));
        pTimKiem.add(new JLabel("Từ Ngày (dd/MM/yyyy):"));
        pTimKiem.add(txttungay);
        pTimKiem.add(new JLabel("Đến Ngày (dd/MM/yyyy):"));
        pTimKiem.add(txtdenngay);
        pTimKiem.add(btntim);
        pTimKiem.add(btnlammoi);

        pCenter.add(pTimKiem, BorderLayout.NORTH);

        JPanel pBang = new JPanel(new GridLayout(2, 1, 0, 10));
        pBang.setBackground(new Color(245, 245, 245));

        String[] columnphieu = {"Mã Phiếu", "Ngày Kiểm Kê", "Số Mặt Hàng", "Trạng Thái"};
        modelphieu = new DefaultTableModel(columnphieu, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblphieu = new JTable(modelphieu);
        tblphieu.setRowHeight(25);
        JScrollPane scrollphieu = new JScrollPane(tblphieu);
        scrollphieu.setBorder(BorderFactory.createTitledBorder("Danh sách phiếu kiểm kê"));

        String[] columnct = {"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị", "SL Hệ Thống", "SL Thực Tế", "Chênh Lệch"};
        modelchitiet = new DefaultTableModel(columnct, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblchitiet = new JTable(modelchitiet);
        tblchitiet.setRowHeight(25);
        JScrollPane scrollct = new JScrollPane(tblchitiet);
        scrollct.setBorder(BorderFactory.createTitledBorder("Chi tiết phiếu kiểm kê"));

        pBang.add(scrollphieu);
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

        btnin = new JButton("In Phiếu");
        btnin.setPreferredSize(new Dimension(110, 35));
        btnin.setBackground(new Color(52, 152, 219));
        btnin.setForeground(Color.WHITE);
        btnin.setFocusPainted(false);

        btnxoa = new JButton("Xóa");
        btnxoa.setPreferredSize(new Dimension(110, 35));
        btnxoa.setBackground(new Color(231, 76, 60));
        btnxoa.setForeground(Color.WHITE);
        btnxoa.setFocusPainted(false);

        btnsua = new JButton("Sửa");
        btnsua.setPreferredSize(new Dimension(110, 35));
        btnsua.setBackground(new Color(52, 152, 219));
        btnsua.setForeground(Color.WHITE);
        btnsua.setFocusPainted(false);

        btnthem = new JButton("Tạo Phiếu Kiểm Kê");
        btnthem.setPreferredSize(new Dimension(170, 35));
        btnthem.setBackground(new Color(46, 204, 113));
        btnthem.setForeground(Color.WHITE);
        btnthem.setFont(new Font("Arial", Font.BOLD, 12));
        btnthem.setFocusPainted(false);

        pSouth.add(btnquaylai);
        pSouth.add(btnin);
        pSouth.add(btnxoa);
        pSouth.add(btnsua);
        pSouth.add(btnthem);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btntim.addActionListener(this);
        btnlammoi.addActionListener(this);
        btnthem.addActionListener(this);
        btnsua.addActionListener(this);
        btnxoa.addActionListener(this);
        btnin.addActionListener(this);
        btnquaylai.addActionListener(this);

        tblphieu.addMouseListener(new MouseAdapter() {
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
        } else if (o == btnthem) {
            xuLyTaoPhieu();
        } else if (o == btnsua) {
            xuLySua();
        } else if (o == btnxoa) {
            xuLyXoa();
        } else if (o == btnin) {
            xuLyIn();
        } else if (o == btnquaylai) {
            xuLyQuayLai();
        }
    }

    private void napBangPhieu(ArrayList<String[]> ds) {
        modelphieu.setRowCount(0);
        modelchitiet.setRowCount(0);
        for (String[] p : ds) {
            modelphieu.addRow(p);
        }
    }

    private void xuLyChonDong() {
        int dong = tblphieu.getSelectedRow();
        if (dong == -1) return;

        String ma = modelphieu.getValueAt(dong, 0).toString();
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
                    "Ngày phải nhập đúng dạng dd/MM/yyyy (ví dụ 01/10/2026)!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ma = txtmaphieu.getText().trim().toLowerCase();
        String trangthai = cbtrangthai.getSelectedItem().toString();

        ArrayList<String[]> ketqua = new ArrayList<>();
        for (String[] p : dsphieu) {
            if (!p[0].toLowerCase().contains(ma)) continue;
            if (!trangthai.equals("Tất cả") && !p[3].equals(trangthai)) continue;

            LocalDate ngay = LocalDate.parse(p[1], dinhdangngay);
            if (tungay != null && ngay.isBefore(tungay)) continue;
            if (denngay != null && ngay.isAfter(denngay)) continue;

            ketqua.add(p);
        }

        napBangPhieu(ketqua);
        if (ketqua.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu kiểm kê nào phù hợp!");
        }
    }

    private void xuLyLamMoi() {
        txtmaphieu.setText("");
        cbtrangthai.setSelectedIndex(0);
        txttungay.setText("");
        txtdenngay.setText("");
        napBangPhieu(dsphieu);
    }

    private void xuLyTaoPhieu() {
        String mamoi = String.format("KK%03d", dsphieu.size() + 1);
        ManHinhLapPhieuKiemKe UI = new ManHinhLapPhieuKiemKe(mamoi, "Nguyễn Văn A");
        UI.setVisible(true);
    }

    private void xuLySua() {
        int dong = tblphieu.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn phiếu kiểm kê cần sửa!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!modelphieu.getValueAt(dong, 3).toString().equals("Đang kiểm kê")) {
            JOptionPane.showMessageDialog(this,
                    "Chỉ sửa được phiếu đang ở trạng thái 'Đang kiểm kê'!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ma = modelphieu.getValueAt(dong, 0).toString();
        ManHinhLapPhieuKiemKe UI = new ManHinhLapPhieuKiemKe(ma, "Nguyễn Văn A");
        UI.setVisible(true);
    }

    private void xuLyXoa() {
        int dong = tblphieu.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn phiếu kiểm kê cần xóa!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!modelphieu.getValueAt(dong, 3).toString().equals("Đang kiểm kê")) {
            JOptionPane.showMessageDialog(this,
                    "Chỉ xóa được phiếu đang ở trạng thái 'Đang kiểm kê'!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ma = modelphieu.getValueAt(dong, 0).toString();
        int chon = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa phiếu kiểm kê " + ma + " không?",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (chon != JOptionPane.YES_OPTION) return;

        for (int i = 0; i < dsphieu.size(); i++) {
            if (dsphieu.get(i)[0].equals(ma)) {
                dsphieu.remove(i);
                break;
            }
        }
        dschitiet.remove(ma);
        modelphieu.removeRow(dong);
        modelchitiet.setRowCount(0);
    }

    private void xuLyIn() {
        int dong = tblphieu.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn phiếu kiểm kê cần in!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this,
                "Chức năng in phiếu " + modelphieu.getValueAt(dong, 0) + " chưa được cài đặt.");
    }

    private void xuLyQuayLai() {
        this.dispose();
    }

    public static void main(String[] args) {
        ManHinhDanhSachPhieuKiemKe form = new ManHinhDanhSachPhieuKiemKe();
        form.setVisible(true);
    }
}