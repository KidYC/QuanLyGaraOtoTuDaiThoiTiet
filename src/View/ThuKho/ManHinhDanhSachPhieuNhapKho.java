package View.ThuKho;

import DAO.NhaCungCap_DAO;
import DAO.PhieuNhapKho_DAO;
import Entity.NhaCungCap;
import Entity.PhieuNhapKho;
import com.toedter.calendar.JDateChooser;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ManHinhDanhSachPhieuNhapKho extends JFrame implements ActionListener {

    private JLabel lbltieude;

    private JTextField txtmaphieu;
    private JComboBox<String> cbnhacungcap;
    private JTextField txtnhanvien;
    private JDateChooser chonngaynhap;
    private JButton btntim;
    private JButton btnlammoi;

    private JTable tblphieu;
    private DefaultTableModel modelphieu;

    private JButton btnthem;
    private JButton btnsua;
    private JButton btnxoa;
    private JButton btnquaylai;
    private JButton btnchitiet;

    private ArrayList<String[]> dsphieu = new ArrayList<>();

    private final PhieuNhapKho_DAO phieuNhapDAO = new PhieuNhapKho_DAO();
    private final NhaCungCap_DAO nhaCungCapDAO = new NhaCungCap_DAO();

    private String maNhanVien = "";

    public ManHinhDanhSachPhieuNhapKho(String maNhanVien) {
        this();
        this.maNhanVien = maNhanVien == null ? "" : maNhanVien;
    }

    public ManHinhDanhSachPhieuNhapKho() {
        setTitle("DANH SÁCH PHIẾU NHẬP KHO");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();
        taiDuLieu();
        napBangPhieu(dsphieu);
    }


    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel("DANH SÁCH PHIẾU NHẬP KHO");
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
        cbnhacungcap = new JComboBox<>();
        napNhaCungCap();
        txtnhanvien = new JTextField();
        chonngaynhap = new JDateChooser();
        chonngaynhap.setDateFormatString("dd/MM/yyyy");
        chonngaynhap.setLocale(Locale.forLanguageTag("vi-VN"));

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
        pTimKiem.add(new JLabel("Nhà Cung Cấp:"));
        pTimKiem.add(cbnhacungcap);
        pTimKiem.add(new JLabel(""));
        pTimKiem.add(new JLabel(""));
        pTimKiem.add(new JLabel("Ngày Nhập :"));
        pTimKiem.add(chonngaynhap);
        pTimKiem.add(new JLabel("Nhân Viên:"));
        pTimKiem.add(txtnhanvien);
        pTimKiem.add(btntim);
        pTimKiem.add(btnlammoi);

        pCenter.add(pTimKiem, BorderLayout.NORTH);

        JPanel pBang = new JPanel(new GridLayout(1, 1));
        pBang.setBackground(new Color(245, 245, 245));

        String[] columnphieu = {"Mã Phiếu", "Ngày Nhập", "Nhà Cung Cấp", "Nhân Viên", "Tổng Tiền"};
        modelphieu = new DefaultTableModel(columnphieu, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblphieu = new JTable(modelphieu);
        tblphieu.setRowHeight(25);

        tblphieu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    xuLyXemChiTiet();
                }
            }
        });
        JScrollPane scrollphieu = new JScrollPane(tblphieu);
        scrollphieu.setBorder(BorderFactory.createTitledBorder("Danh sách phiếu nhập"));

        pBang.add(scrollphieu);
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

        btnchitiet = new JButton("Xem Chi Tiết");
        btnchitiet.setPreferredSize(new Dimension(130, 35));
        btnchitiet.setBackground(new Color(243, 156, 18));
        btnchitiet.setForeground(Color.WHITE);
        btnchitiet.setFocusPainted(false);

        btnthem = new JButton("Tạo Phiếu Nhập");
        btnthem.setPreferredSize(new Dimension(150, 35));
        btnthem.setBackground(new Color(46, 204, 113));
        btnthem.setForeground(Color.WHITE);
        btnthem.setFont(new Font("Arial", Font.BOLD, 12));
        btnthem.setFocusPainted(false);

        pSouth.add(btnquaylai);
        pSouth.add(btnxoa);
        pSouth.add(btnsua);
        pSouth.add(btnchitiet);
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
        btnquaylai.addActionListener(this);
        btnchitiet.addActionListener(this);
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
        } else if (o == btnquaylai) {
            xuLyQuayLai();
        } else if (o == btnchitiet) {
            xuLyXemChiTiet();
        }
    }

    private void taiDuLieu() {
        dsphieu.clear();
        SimpleDateFormat dinhdangdate = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat dinhdangtien = new DecimalFormat("#,###");
        try {
            for (PhieuNhapKho p : phieuNhapDAO.getAllPhieuNhap()) {
                String ngay = p.getNgayNhap() == null ? "" : dinhdangdate.format(p.getNgayNhap());
                double tong = p.getTongTienNhap() == null ? 0 : p.getTongTienNhap();
                dsphieu.add(new String[]{
                        p.getMaPhieuNhap(),
                        ngay,
                        p.getNhaCungCap().getTenNhaCungCap(),
                        p.getThuKho().getHoTen(),
                        dinhdangtien.format(tong)});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }


    private void napNhaCungCap() {
        cbnhacungcap.removeAllItems();
        cbnhacungcap.addItem("Tất cả");
        try {
            for (NhaCungCap ncc : nhaCungCapDAO.getAllNhaCungCap()) {
                cbnhacungcap.addItem(ncc.getTenNhaCungCap());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải nhà cung cấp: " + e.getMessage());
        }
    }

    private void napBangPhieu(ArrayList<String[]> ds) {
        modelphieu.setRowCount(0);
        for (String[] p : ds) {
            modelphieu.addRow(p);
        }
    }

    private void xuLyTim() {
        String ngaynhap = "";
        if (chonngaynhap.getDate() != null) {
            ngaynhap = new SimpleDateFormat("dd/MM/yyyy").format(chonngaynhap.getDate());
        }

        String ma = txtmaphieu.getText().trim().toLowerCase();
        String ncc = cbnhacungcap.getSelectedItem() == null ? "Tất cả" : cbnhacungcap.getSelectedItem().toString();
        String nv = txtnhanvien.getText().trim().toLowerCase();

        ArrayList<String[]> ketqua = new ArrayList<>();
        for (String[] p : dsphieu) {
            if (!p[0].toLowerCase().contains(ma)) continue;
            if (!ncc.equals("Tất cả") && !ncc.equals(p[2])) continue;
            if (!p[3].toLowerCase().contains(nv)) continue;

            if (!ngaynhap.isEmpty() && !p[1].equals(ngaynhap)) continue;

            ketqua.add(p);
        }

        napBangPhieu(ketqua);
        if (ketqua.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập nào phù hợp!");
        }
    }

    private void xuLyLamMoi() {
        txtmaphieu.setText("");
        napNhaCungCap();
        txtnhanvien.setText("");
        chonngaynhap.setDate(null);
        taiDuLieu();
        napBangPhieu(dsphieu);
    }

    private void xuLyTaoPhieu() {
        moManHinhCon(new ManHinhLapPhieuNhapKho(maNhanVien, null));
    }

    private void xuLySua() {
        int dong = tblphieu.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn phiếu nhập cần sửa!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String ma = modelphieu.getValueAt(dong, 0).toString();
        moManHinhCon(new ManHinhLapPhieuNhapKho(ma));
    }

    private void xuLyXoa() {
        int dong = tblphieu.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn phiếu nhập cần xóa!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ma = modelphieu.getValueAt(dong, 0).toString();
        int chon = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa phiếu nhập " + ma + " không?\n"
                        + "Số lượng đã nhập sẽ được trừ lại khỏi tồn kho.",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (chon != JOptionPane.YES_OPTION) return;

        try {
            if (phieuNhapDAO.xoaPhieuNhap(ma)) {
                taiDuLieu();
                napBangPhieu(dsphieu);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Không xóa được: tồn kho hiện tại không đủ để trừ lại số lượng đã nhập!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa phiếu nhập: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyXemChiTiet() {
        int dong = tblphieu.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn phiếu nhập cần xem!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ManHinhChiTietPhieuNhapKho UI = new ManHinhChiTietPhieuNhapKho(
                modelphieu.getValueAt(dong, 0).toString(),
                modelphieu.getValueAt(dong, 1).toString(),
                modelphieu.getValueAt(dong, 2).toString(),
                modelphieu.getValueAt(dong, 3).toString());
        UI.setVisible(true);
    }

    private void moManHinhCon(JFrame UI) {
        UI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                taiDuLieu();
                napBangPhieu(dsphieu);
            }
        });
        UI.setVisible(true);
    }

    private void xuLyQuayLai() {
        this.dispose();
    }

    public static void main(String[] args) {
        ManHinhDanhSachPhieuNhapKho form = new ManHinhDanhSachPhieuNhapKho();
        form.setVisible(true);
    }
}