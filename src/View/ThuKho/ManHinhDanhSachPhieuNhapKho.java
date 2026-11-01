package View.ThuKho;

import DAO.PhieuNhapKho_DAO;
import Entity.PhieuNhapKho;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ManHinhDanhSachPhieuNhapKho extends JFrame implements ActionListener {

    private JLabel lbltieude;

    private JTextField txtmaphieu;
    private JTextField txtnhacungcap;
    private JTextField txtnhanvien;
    private JTextField txtngaynhap;
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

    private DateTimeFormatter dinhdangngay = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
        txtnhacungcap = new JTextField();
        txtnhanvien = new JTextField();
        txtngaynhap = new JTextField();

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
        pTimKiem.add(txtnhacungcap);
        pTimKiem.add(new JLabel(""));
        pTimKiem.add(new JLabel(""));
        pTimKiem.add(new JLabel("Ngày Nhập (dd/MM/yyyy):"));
        pTimKiem.add(txtngaynhap);
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
        // nhấp đúp vào một dòng để xem chi tiết
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

    private void napBangPhieu(ArrayList<String[]> ds) {
        modelphieu.setRowCount(0);
        for (String[] p : ds) {
            modelphieu.addRow(p);
        }
    }

    private void xuLyTim() {
        LocalDate ngaynhap = null;
        try {
            if (!txtngaynhap.getText().trim().isEmpty()) {
                ngaynhap = LocalDate.parse(txtngaynhap.getText().trim(), dinhdangngay);
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ngày phải nhập đúng dạng dd/MM/yyyy (ví dụ 03/10/2026)!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ma = txtmaphieu.getText().trim().toLowerCase();
        String ncc = txtnhacungcap.getText().trim().toLowerCase();
        String nv = txtnhanvien.getText().trim().toLowerCase();

        ArrayList<String[]> ketqua = new ArrayList<>();
        for (String[] p : dsphieu) {
            if (!p[0].toLowerCase().contains(ma)) continue;
            if (!p[2].toLowerCase().contains(ncc)) continue;
            if (!p[3].toLowerCase().contains(nv)) continue;

            if (ngaynhap != null) {
                if (p[1].isEmpty()) continue;
                LocalDate ngay = LocalDate.parse(p[1], dinhdangngay);
                if (!ngay.equals(ngaynhap)) continue;
            }

            ketqua.add(p);
        }

        napBangPhieu(ketqua);
        if (ketqua.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập nào phù hợp!");
        }
    }

    private void xuLyLamMoi() {
        txtmaphieu.setText("");
        txtnhacungcap.setText("");
        txtnhanvien.setText("");
        txtngaynhap.setText("");
        taiDuLieu();
        napBangPhieu(dsphieu);
    }

    private void xuLyTaoPhieu() {
        moManHinhCon(new ManHinhLapPhieuNhapKho());
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