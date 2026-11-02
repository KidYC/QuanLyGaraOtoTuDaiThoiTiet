package View.ThuKho;

import DAO.PhieuKiemKe_DAO;
import Entity.PhieuKiemKe;

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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ManHinhDanhSachPhieuKiemKe extends JFrame implements ActionListener {

    private JLabel lbltieude;

    private JTextField txtmaphieu;
    private JComboBox<String> cbtrangthai;
    private JTextField txtngaykiemke;
    private JButton btntim;
    private JButton btnlammoi;

    private JTable tblphieu;
    private DefaultTableModel modelphieu;

    private JButton btnthem;
    private JButton btnsua;
    private JButton btnxoa;
    private JButton btnin;
    private JButton btnxacnhan;
    private JButton btnchitiet;
    private JButton btnquaylai;

    private ArrayList<String[]> dsphieu = new ArrayList<>();

    private final PhieuKiemKe_DAO phieuKiemKeDAO = new PhieuKiemKe_DAO();

    private DateTimeFormatter dinhdangngay = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ManHinhDanhSachPhieuKiemKe() {
        setTitle("DANH SÁCH PHIẾU KIỂM KÊ");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        taiDuLieu();
        buildUI();
        napBangPhieu(dsphieu);
    }

    private void taiDuLieu() {
        dsphieu.clear();
        SimpleDateFormat dinhdangdate = new SimpleDateFormat("dd/MM/yyyy");
        try {
            for (PhieuKiemKe pk : phieuKiemKeDAO.getAllPhieuKiemKe()) {
                String ngay = pk.getNgayKiemKe() == null ? "" : dinhdangdate.format(pk.getNgayKiemKe());
                int somathang = phieuKiemKeDAO.getChiTietKiemKe(pk.getMaPhieuKiemKe()).size();
                dsphieu.add(new String[]{pk.getMaPhieuKiemKe(), ngay, String.valueOf(somathang), pk.getTrangThai()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage());
        }
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
        cbtrangthai = new JComboBox<>(new String[]{"Tất cả", "Đang kiểm kê", "Đã hoàn thành"});
        txtngaykiemke = new JTextField();

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
        pTimKiem.add(new JLabel("Ngày Kiểm Kê (dd/MM/yyyy):"));
        pTimKiem.add(txtngaykiemke);
        pTimKiem.add(new JLabel(""));
        pTimKiem.add(new JLabel(""));
        pTimKiem.add(btntim);
        pTimKiem.add(btnlammoi);

        pCenter.add(pTimKiem, BorderLayout.NORTH);

        JPanel pBang = new JPanel(new GridLayout(1, 1));
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

        btnxacnhan = new JButton("Xác Nhận");
        btnxacnhan.setPreferredSize(new Dimension(110, 35));
        btnxacnhan.setBackground(new Color(46, 204, 113));
        btnxacnhan.setForeground(Color.WHITE);
        btnxacnhan.setFocusPainted(false);

        btnthem = new JButton("Tạo Phiếu Kiểm Kê");
        btnthem.setPreferredSize(new Dimension(170, 35));
        btnthem.setBackground(new Color(46, 204, 113));
        btnthem.setForeground(Color.WHITE);
        btnthem.setFont(new Font("Arial", Font.BOLD, 12));
        btnthem.setFocusPainted(false);

        pSouth.add(btnquaylai);
        pSouth.add(btnxoa);
        pSouth.add(btnsua);
        pSouth.add(btnchitiet);
        pSouth.add(btnxacnhan);
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
        btnxacnhan.addActionListener(this);
        btnchitiet.addActionListener(this);
        btnquaylai.addActionListener(this);

        tblphieu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    xuLyXemChiTiet();
                }
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
        } else if (o == btnxacnhan) {
            xuLyXacNhan();
        } else if (o == btnchitiet) {
            xuLyXemChiTiet();
        } else if (o == btnquaylai) {
            xuLyQuayLai();
        }
    }

    private void napBangPhieu(ArrayList<String[]> ds) {
        modelphieu.setRowCount(0);
        for (String[] p : ds) {
            modelphieu.addRow(p);
        }
    }

    private void xuLyXemChiTiet() {
        int dong = tblphieu.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn phiếu kiểm kê cần xem!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ManHinhChiTietPhieuKiemKe UI = new ManHinhChiTietPhieuKiemKe(modelphieu.getValueAt(dong, 0).toString());
        UI.setVisible(true);
    }

    private void xuLyTim() {
        LocalDate ngaykiemke = null;
        try {
            if (!txtngaykiemke.getText().trim().isEmpty()) {
                ngaykiemke = LocalDate.parse(txtngaykiemke.getText().trim(), dinhdangngay);
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

            if (ngaykiemke != null) {
                if (p[1].isEmpty()) continue;
                LocalDate ngay = LocalDate.parse(p[1], dinhdangngay);
                if (!ngay.equals(ngaykiemke)) continue;
            }

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
        txtngaykiemke.setText("");
        taiDuLieu();
        napBangPhieu(dsphieu);
    }

    private void xuLyTaoPhieu() {
        moManHinhCon(new ManHinhLapPhieuKiemKe());
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
        moManHinhCon(new ManHinhLapPhieuKiemKe(ma));
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

        try {
            if (phieuKiemKeDAO.xoaPhieuKiemKe(ma)) {
                taiDuLieu();
                napBangPhieu(dsphieu);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Không xóa được: phiếu " + ma + " không còn ở trạng thái 'Đang kiểm kê'!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa phiếu kiểm kê: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyXacNhan() {
        int dong = tblphieu.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn phiếu kiểm kê cần xác nhận!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!modelphieu.getValueAt(dong, 3).toString().equals("Đang kiểm kê")) {
            JOptionPane.showMessageDialog(this,
                    "Chỉ xác nhận được phiếu đang ở trạng thái 'Đang kiểm kê'!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ma = modelphieu.getValueAt(dong, 0).toString();
        int chon = JOptionPane.showConfirmDialog(this,
                "Xác nhận phiếu kiểm kê " + ma + "?\n"
                        + "Tồn kho sẽ được điều chỉnh theo chênh lệch (thực tế - hệ thống).",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (chon != JOptionPane.YES_OPTION) return;

        try {
            if (phieuKiemKeDAO.xacNhanKiemKe(ma)) {
                JOptionPane.showMessageDialog(this, "Xác nhận phiếu kiểm kê " + ma + " thành công!");
                taiDuLieu();
                napBangPhieu(dsphieu);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Không xác nhận được phiếu " + ma + "!\n"
                                + "Phiếu không còn ở trạng thái 'Đang kiểm kê' hoặc tồn kho hiện tại không đủ để trừ phần thiếu.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi xác nhận phiếu kiểm kê: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
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
        ManHinhDanhSachPhieuKiemKe form = new ManHinhDanhSachPhieuKiemKe();
        form.setVisible(true);
    }
}
