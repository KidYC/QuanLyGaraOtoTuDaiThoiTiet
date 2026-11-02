package View.ThuKho;

import DAO.PhuTung_DAO;
import Entity.PhuTung;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class ManHinhQuanLyDanhMucPhuTung extends JFrame implements ActionListener {

    private JLabel lbltieude;
    private JTextField txttimkiem;
    private JButton btntimkiem;
    private JButton btnlammoi;

    private JTable tblphutung;
    private DefaultTableModel modelphutung;

    private JButton btnthem;
    private JButton btnsua;
    private JButton btnxoa;
    private JButton btnquaylai;
    private final PhuTung_DAO phuTungDAO = new PhuTung_DAO();
    private final DecimalFormat dinhdang = new DecimalFormat("#,###");

    public ManHinhQuanLyDanhMucPhuTung() {
        setTitle("QUẢN LÝ DANH MỤC PHỤ TÙNG");
        setSize(950, 530);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();
        napBang();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel("DANH MỤC PHỤ TÙNG");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 22));
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbltieude.setForeground(new Color(44, 62, 80));
        lbltieude.setBorder(new EmptyBorder(15, 0, 10, 0));

        JPanel pNorth = new JPanel(new BorderLayout());
        pNorth.setBackground(new Color(245, 245, 245));
        pNorth.add(lbltieude, BorderLayout.NORTH);

        JPanel pTimKiem = new JPanel(new BorderLayout(12, 0));
        pTimKiem.setBackground(new Color(245, 245, 245));
        pTimKiem.setBorder(new EmptyBorder(5, 20, 12, 20));

        JLabel lbltimkiem = new JLabel("Tìm theo mã hoặc tên:");
        lbltimkiem.setFont(new Font("Arial", Font.BOLD, 12));

        txttimkiem = new JTextField();
        txttimkiem.setPreferredSize(new Dimension(250, 32));

        btntimkiem = new JButton("Tìm Kiếm");
        btntimkiem.setPreferredSize(new Dimension(110, 32));
        btntimkiem.setBackground(new Color(52, 152, 219));
        btntimkiem.setForeground(Color.WHITE);
        btntimkiem.setFocusPainted(false);

        btnlammoi = new JButton("Làm Mới");
        btnlammoi.setPreferredSize(new Dimension(110, 32));
        btnlammoi.setBackground(new Color(149, 165, 166));
        btnlammoi.setForeground(Color.WHITE);
        btnlammoi.setFocusPainted(false);

        JPanel pNut = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pNut.setBackground(new Color(245, 245, 245));
        pNut.add(btntimkiem);
        pNut.add(btnlammoi);

        pTimKiem.add(lbltimkiem, BorderLayout.WEST);
        pTimKiem.add(txttimkiem, BorderLayout.CENTER);
        pTimKiem.add(pNut, BorderLayout.EAST);

        pNorth.add(pTimKiem, BorderLayout.CENTER);

        add(pNorth, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel(new BorderLayout(15, 0));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        String[] column = {"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị", "Số Lượng", "Đơn Giá Nhập", "Đơn Giá Bán"};
        Object[][] data = {};
        modelphutung = new DefaultTableModel(data, column) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblphutung = new JTable(modelphutung);
        tblphutung.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tblphutung);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phụ tùng"));
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

        btnthem = new JButton("Thêm Phụ Tùng");
        btnthem.setPreferredSize(new Dimension(150, 35));
        btnthem.setBackground(new Color(46, 204, 113));
        btnthem.setForeground(Color.WHITE);
        btnthem.setFont(new Font("Arial", Font.BOLD, 12));
        btnthem.setFocusPainted(false);

        pSouth.add(btnquaylai);
        pSouth.add(btnxoa);
        pSouth.add(btnsua);
        pSouth.add(btnthem);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btnthem.addActionListener(this);
        btnsua.addActionListener(this);
        btnxoa.addActionListener(this);
        btnquaylai.addActionListener(this);
        btntimkiem.addActionListener(this);
        btnlammoi.addActionListener(this);
        txttimkiem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnthem) {
            xuLyThem();
        } else if (o == btnsua) {
            xuLySua();
        } else if (o == btnxoa) {
            xuLyXoa();
        } else if (o == btnquaylai) {
            xuLyQuayLai();
        } else if (o == btntimkiem || o == txttimkiem) {
        xuLyTimKiem();
        } else if (o == btnlammoi) {
        xuLyLamMoi();
        }
    }

    private void napBang() {
        modelphutung.setRowCount(0);
        String tuKhoa = txttimkiem.getText().trim();
        try {
            List<PhuTung> ds = tuKhoa.isEmpty()
                    ? phuTungDAO.getAllPhuTung()
                    : phuTungDAO.timKiemPhuTung(tuKhoa);
            for (PhuTung pt : ds) {
                modelphutung.addRow(new Object[]{pt.getMaPhuTung(), pt.getTenPhuTung(),
                        pt.getDonViTinh(), pt.getSoLuongTon(),
                        dinhdang.format(pt.getDonGiaNhap()), dinhdang.format(pt.getDonGiaBan())});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    public boolean tonTaiMa(String ma) {
        try { return phuTungDAO.timPhuTungTheoMa(ma) != null; } catch (SQLException e) { return false; }
    }


    public boolean themPhuTung(String ma, String ten, String donvi, int soluong, String maKho,
                               double dongianhap, double dongiaban) {
        try {
            boolean ok = phuTungDAO.themPhuTung(new PhuTung(ma, ten, donvi, dongianhap, dongiaban, soluong, maKho));
            napBang();
            return ok;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            return false;
        }
    }

    public boolean capNhatPhuTung(String ma, String ten, String donvi, int soluong,
                                  double dongianhap, double dongiaban) {
        try {
            PhuTung pt = phuTungDAO.timPhuTungTheoMa(ma);
            if (pt == null) return false;
            pt.setTenPhuTung(ten);
            pt.setDonViTinh(donvi);
            pt.setDonGiaNhap(dongianhap);
            pt.setDonGiaBan(dongiaban);
            boolean ok = phuTungDAO.capNhatPhuTung(pt);
            napBang();
            return ok;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            return false;
        }
    }

    private void xuLyThem() {
        FormThongTinPhuTung UI = new FormThongTinPhuTung(this);
        UI.setVisible(true);
    }

    private void xuLySua() {
        int dong = tblphutung.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn phụ tùng cần sửa!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        FormThongTinPhuTung UI = new  FormThongTinPhuTung(this,
                modelphutung.getValueAt(dong, 0).toString(),
                modelphutung.getValueAt(dong, 1).toString(),
                modelphutung.getValueAt(dong, 2).toString(),
                modelphutung.getValueAt(dong, 3).toString(),
                modelphutung.getValueAt(dong, 4).toString(),
                modelphutung.getValueAt(dong, 5).toString());
        UI.setVisible(true);
    }

    private void xuLyXoa() {
        int dong = tblphutung.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn phụ tùng cần xóa!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String ma = modelphutung.getValueAt(dong, 0).toString();

        // kiểm tra phụ tùng đã có trong phiếu nào thì báo rõ và không cho xóa
        try {
            String dangdung = phuTungDAO.kiemTraPhuTungDangDung(ma);
            if (!dangdung.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không thể xóa phụ tùng " + ma + " vì đang được dùng trong:\n" + dangdung
                                + "\nXóa sẽ làm mất thông tin phụ tùng trong các phiếu trên.",
                        "Không thể xóa", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi kiểm tra phụ tùng: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int chon = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa phụ tùng " + ma + " không?",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (chon == JOptionPane.YES_OPTION) {
            try {
                if (phuTungDAO.xoaPhuTung(ma)) {
                    napBang();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Không xóa được phụ tùng " + ma + " (có thể đang được dùng trong phiếu nhập/xuất/kiểm kê).",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xuLyQuayLai() {
        this.dispose();
    }

    private void xuLyTimKiem() {
        napBang();
        if (modelphutung.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy phụ tùng phù hợp!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void xuLyLamMoi() {
        txttimkiem.setText("");
        napBang();
    }

    public static void main(String[] args) {
        ManHinhQuanLyDanhMucPhuTung form = new ManHinhQuanLyDanhMucPhuTung();
        form.setVisible(true);
    }
}