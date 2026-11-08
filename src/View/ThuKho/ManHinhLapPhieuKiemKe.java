package View.ThuKho;

import DAO.PhieuKiemKe_DAO;
import DAO.PhuTung_DAO;
import Entity.ChiTietKiemKe;
import Entity.PhieuKiemKe;
import Entity.PhuTung;
import Entity.ThuKho;

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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ManHinhLapPhieuKiemKe extends JFrame implements ActionListener {

    private JLabel lbltieude;

    private JTextField txtmaphieu;
    private JTextField txtngaykiemke;
    private JTextField txtnguoikiemke;
    private JTextField txtghichu;

    private JComboBox<String> cbphutung;
    private JTextField txtslhethong;
    private JTextField txtslthucte;
    private JTextField txtchenhlech;
    private JTextField txtlydo;
    private JButton btnthemdong;
    private JButton btnxoadong;

    private JTable tblchitiet;
    private DefaultTableModel modelchitiet;

    private JButton btnluu;
    private JButton btnquaylai;
    private JButton btnlammoi;

    private final PhuTung_DAO phuTungDAO = new PhuTung_DAO();
    private final PhieuKiemKe_DAO phieuKiemKeDAO = new PhieuKiemKe_DAO();

    private List<PhuTung> dsphutung = new ArrayList<>();
    private boolean chedosua = false;
    private String maphieugoc = "";

    public ManHinhLapPhieuKiemKe() {
        khoiTao();
        txtmaphieu.setText(taoMaPhieu());
    }

    public ManHinhLapPhieuKiemKe(String maphieu) {
        this.chedosua = true;
        this.maphieugoc = maphieu;
        khoiTao();
        napDuLieuSua();
    }

    private void khoiTao() {
        setTitle(chedosua ? "SỬA PHIẾU KIỂM KÊ" : "LẬP PHIẾU KIỂM KÊ");
        setSize(900, 640);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        taiDanhMuc();
        buildUI();
    }

    private void napDuLieuSua() {
        try {
            PhieuKiemKe pk = phieuKiemKeDAO.timPhieuKiemKeTheoMa(maphieugoc);
            List<ChiTietKiemKe> dsct = phieuKiemKeDAO.getChiTietKiemKe(maphieugoc);
            if (pk == null || dsct.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu của phiếu " + maphieugoc + "!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                btnluu.setEnabled(false);
                return;
            }

            txtmaphieu.setText(pk.getMaPhieuKiemKe());
            if (pk.getNgayKiemKe() != null) {
                txtngaykiemke.setText(new SimpleDateFormat("dd/MM/yyyy").format(pk.getNgayKiemKe()));
            }
            txtnguoikiemke.setText(pk.getThuKho().getMaNhanVien());

            modelchitiet.setRowCount(0);
            for (ChiTietKiemKe ct : dsct) {
                modelchitiet.addRow(new Object[]{
                        ct.getMaPhuTung(),
                        ct.getTenPhuTung(),
                        ct.getDonViTinh(),
                        ct.getSoLuongHeThong(),
                        ct.getSoLuongThucTe(),
                        ct.getChenhLech(),
                        ct.getLyDoChenhLech() == null ? "" : ct.getLyDoChenhLech()});
            }
            xoaONhap();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    private void taiDanhMuc() {
        try {
            dsphutung = phuTungDAO.getAllPhuTung();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    private String taoMaPhieu() {
        try {
            return phieuKiemKeDAO.sinhMaPhieuKiemKe();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tạo mã phiếu: " + e.getMessage());
            return "";
        }
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel(chedosua ? "SỬA PHIẾU KIỂM KÊ" : "LẬP PHIẾU KIỂM KÊ");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 22));
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbltieude.setForeground(new Color(44, 62, 80));
        lbltieude.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lbltieude, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel(new BorderLayout(0, 10));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pThongTin = new JPanel(new GridLayout(2, 4, 10, 10));
        pThongTin.setBackground(Color.WHITE);
        pThongTin.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông tin phiếu kiểm kê",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        txtmaphieu = new JTextField();
        txtmaphieu.setEditable(false);
        txtmaphieu.setBackground(new Color(236, 240, 241));

        String homnay = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        txtngaykiemke = new JTextField(homnay);
        txtngaykiemke.setEditable(false);
        txtngaykiemke.setBackground(new Color(236, 240, 241));

        txtnguoikiemke = new JTextField();
        txtghichu = new JTextField();

        pThongTin.add(new JLabel("Mã Phiếu:"));
        pThongTin.add(txtmaphieu);
        pThongTin.add(new JLabel("Ngày Kiểm Kê:"));
        pThongTin.add(txtngaykiemke);
        pThongTin.add(new JLabel("Mã Người Kiểm Kê:"));
        pThongTin.add(txtnguoikiemke);
        pThongTin.add(new JLabel("Ghi Chú:"));
        pThongTin.add(txtghichu);

        JPanel pChiTiet = new JPanel(new GridLayout(3, 4, 10, 10));
        pChiTiet.setBackground(Color.WHITE);
        pChiTiet.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Phụ tùng kiểm kê",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        cbphutung = new JComboBox<>();
        for (PhuTung pt : dsphutung) {
            cbphutung.addItem(pt.getMaPhuTung() + " - " + pt.getTenPhuTung());
        }

        txtslhethong = new JTextField();
        if (!dsphutung.isEmpty()) {
            txtslhethong.setText(String.valueOf(dsphutung.get(0).getSoLuongTon()));
        }
        txtslhethong.setEditable(false);
        txtslhethong.setBackground(new Color(236, 240, 241));

        txtslthucte = new JTextField();

        txtchenhlech = new JTextField();
        txtchenhlech.setEditable(false);
        txtchenhlech.setBackground(new Color(236, 240, 241));

        txtlydo = new JTextField();

        pChiTiet.add(new JLabel("Phụ Tùng:"));
        pChiTiet.add(cbphutung);
        pChiTiet.add(new JLabel("SL Hệ Thống:"));
        pChiTiet.add(txtslhethong);
        pChiTiet.add(new JLabel("SL Thực Tế:"));
        pChiTiet.add(txtslthucte);
        pChiTiet.add(new JLabel("Chênh Lệch:"));
        pChiTiet.add(txtchenhlech);
        btnthemdong = new JButton("Thêm Vào Danh Sách");
        btnthemdong.setBackground(new Color(46, 204, 113));
        btnthemdong.setForeground(Color.WHITE);
        btnthemdong.setFocusPainted(false);

        btnxoadong = new JButton("Xóa Khỏi Danh Sách");
        btnxoadong.setBackground(new Color(231, 76, 60));
        btnxoadong.setForeground(Color.WHITE);
        btnxoadong.setFocusPainted(false);

        pChiTiet.add(new JLabel("Lý Do Chênh Lệch:"));
        pChiTiet.add(txtlydo);
        pChiTiet.add(btnthemdong);
        pChiTiet.add(btnxoadong);

        pCenter.add(pThongTin, BorderLayout.NORTH);
        pCenter.add(pChiTiet, BorderLayout.CENTER);

        String[] column = {"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị", "SL Hệ Thống", "SL Thực Tế", "Chênh Lệch", "Lý Do Chênh Lệch"};
        modelchitiet = new DefaultTableModel(column, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblchitiet = new JTable(modelchitiet);
        tblchitiet.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tblchitiet);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phụ tùng kiểm kê"));

        JPanel pBang = new JPanel(new BorderLayout());
        pBang.setBackground(new Color(245, 245, 245));
        pBang.setBorder(new EmptyBorder(10, 20, 0, 20));
        pBang.add(scroll, BorderLayout.CENTER);

        JPanel pCenter2 = new JPanel(new BorderLayout());
        pCenter2.setBackground(new Color(245, 245, 245));
        pCenter2.add(pCenter, BorderLayout.NORTH);
        pCenter2.add(pBang, BorderLayout.CENTER);
        add(pCenter2, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnquaylai = new JButton("Quay Lại");
        btnquaylai.setPreferredSize(new Dimension(110, 35));
        btnquaylai.setBackground(new Color(149, 165, 166));
        btnquaylai.setForeground(Color.WHITE);
        btnquaylai.setFocusPainted(false);

        btnlammoi = new JButton("Làm Mới");
        btnlammoi.setPreferredSize(new Dimension(110, 35));
        btnlammoi.setBackground(new Color(52, 152, 219));
        btnlammoi.setForeground(Color.WHITE);
        btnlammoi.setFocusPainted(false);

        btnluu = new JButton("Lưu Phiếu");
        btnluu.setPreferredSize(new Dimension(120, 35));
        btnluu.setBackground(new Color(46, 204, 113));
        btnluu.setForeground(Color.WHITE);
        btnluu.setFont(new Font("Arial", Font.BOLD, 12));
        btnluu.setFocusPainted(false);

        pSouth.add(btnquaylai);
        pSouth.add(btnlammoi);
        pSouth.add(btnluu);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btnluu.addActionListener(this);
        btnquaylai.addActionListener(this);
        btnlammoi.addActionListener(this);
        cbphutung.addActionListener(this);
        txtslthucte.addActionListener(this);
        btnthemdong.addActionListener(this);
        btnxoadong.addActionListener(this);

        tblchitiet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyChonDong();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnluu) {
            xuLyLuuPhieu();
        } else if (o == btnquaylai) {
            xuLyQuayLai();
        } else if (o == btnlammoi) {
            xuLyLamMoi();
        } else if (o == cbphutung) {
            xuLyDoiPhuTung();
        } else if (o == txtslthucte) {
            tinhChenhLech();
        } else if (o == btnthemdong) {
            xuLyThemDong();
        } else if (o == btnxoadong) {
            xuLyXoaDong();
        }
    }

    private void xuLyDoiPhuTung() {
        int vitri = cbphutung.getSelectedIndex();
        if (vitri >= 0) {
            txtslhethong.setText(String.valueOf(dsphutung.get(vitri).getSoLuongTon()));
        }
        txtslthucte.setText("");
        txtchenhlech.setText("");
    }

    private boolean tinhChenhLech() {
        try {
            int thucte = Integer.parseInt(txtslthucte.getText().trim());
            if (thucte < 0) throw new NumberFormatException();
            int hethong = Integer.parseInt(txtslhethong.getText().trim());
            txtchenhlech.setText(String.valueOf(thucte - hethong));
            return true;
        } catch (NumberFormatException ex) {
            txtchenhlech.setText("");
            return false;
        }
    }

    private void xuLyThemDong() {
        if (dsphutung.isEmpty() || cbphutung.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this,
                    "Hệ thống chưa có phụ tùng nào!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!tinhChenhLech()) {
            JOptionPane.showMessageDialog(this,
                    "Số lượng thực tế phải là số nguyên không âm!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtslthucte.requestFocus();
            return;
        }

        if (!txtchenhlech.getText().equals("0") && txtlydo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Số lượng có chênh lệch, vui lòng nhập lý do!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtlydo.requestFocus();
            return;
        }

        PhuTung pt = dsphutung.get(cbphutung.getSelectedIndex());
        Object[] dong = {
                pt.getMaPhuTung(),
                pt.getTenPhuTung(),
                pt.getDonViTinh(),
                Integer.parseInt(txtslhethong.getText().trim()),
                Integer.parseInt(txtslthucte.getText().trim()),
                Integer.parseInt(txtchenhlech.getText().trim()),
                txtlydo.getText().trim()};

        for (int i = 0; i < modelchitiet.getRowCount(); i++) {
            if (modelchitiet.getValueAt(i, 0).equals(pt.getMaPhuTung())) {
                for (int cot = 0; cot < dong.length; cot++) {
                    modelchitiet.setValueAt(dong[cot], i, cot);
                }
                xoaONhap();
                return;
            }
        }
        modelchitiet.addRow(dong);
        xoaONhap();
    }

    private void xuLyXoaDong() {
        int dong = tblchitiet.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn phụ tùng cần xóa khỏi danh sách!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        modelchitiet.removeRow(dong);
        xoaONhap();
    }

    private void xuLyChonDong() {
        int dong = tblchitiet.getSelectedRow();
        if (dong == -1) return;

        String ma = modelchitiet.getValueAt(dong, 0).toString();
        for (int i = 0; i < dsphutung.size(); i++) {
            if (dsphutung.get(i).getMaPhuTung().equals(ma)) {
                cbphutung.setSelectedIndex(i);
            }
        }
        txtslhethong.setText(modelchitiet.getValueAt(dong, 3).toString());
        txtslthucte.setText(modelchitiet.getValueAt(dong, 4).toString());
        txtlydo.setText(modelchitiet.getValueAt(dong, 6).toString());
        tinhChenhLech();
    }

    private void xoaONhap() {
        int vitri = cbphutung.getSelectedIndex();
        if (vitri >= 0) {
            txtslhethong.setText(String.valueOf(dsphutung.get(vitri).getSoLuongTon()));
        }
        txtslthucte.setText("");
        txtchenhlech.setText("");
        txtlydo.setText("");
    }

    private void xuLyLuuPhieu() {
        String manv = txtnguoikiemke.getText().trim();
        if (manv.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập mã người kiểm kê!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtnguoikiemke.requestFocus();
            return;
        }

        if (modelchitiet.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng thêm ít nhất 1 phụ tùng vào danh sách kiểm kê!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PhieuKiemKe pk = new PhieuKiemKe();
        pk.setThuKho(new ThuKho(manv, null, null));
        pk.setMaPhieuKiemKe(txtmaphieu.getText());
        pk.setNgayKiemKe(new java.util.Date());
        pk.setTrangThai("Đang kiểm kê");
        List<ChiTietKiemKe> dsChiTiet = new ArrayList<>();
        for (int i = 0; i < modelchitiet.getRowCount(); i++) {
            PhuTung pt = new PhuTung();
            pt.setMaPhuTung(modelchitiet.getValueAt(i, 0).toString());
            dsChiTiet.add(new ChiTietKiemKe(pk, pt,
                    Integer.parseInt(modelchitiet.getValueAt(i, 3).toString()),
                    Integer.parseInt(modelchitiet.getValueAt(i, 4).toString()),
                    modelchitiet.getValueAt(i, 6).toString()));
        }

        try {
            boolean thanhcong = chedosua
                    ? phieuKiemKeDAO.capNhatPhieuKiemKe(pk, dsChiTiet)
                    : phieuKiemKeDAO.taoPhieuKiemKe(pk, dsChiTiet);
            if (!thanhcong) {
                JOptionPane.showMessageDialog(this,
                        chedosua ? "Sửa phiếu kiểm kê thất bại! Phiếu không còn ở trạng thái 'Đang kiểm kê'." : "Lập phiếu kiểm kê thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("FOREIGN KEY")) {
                JOptionPane.showMessageDialog(this,
                        "Mã nhân viên " + manv + " không tồn tại trong danh sách thủ kho!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtnguoikiemke.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi lưu phiếu kiểm kê: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        JOptionPane.showMessageDialog(this,
                (chedosua ? "Sửa" : "Lập") + " phiếu kiểm kê " + txtmaphieu.getText() + " thành công!");
        this.dispose();
    }

    private void xuLyQuayLai() {
        this.dispose();
    }

    private void xuLyLamMoi() {
        if (chedosua) {
            napDuLieuSua();
            return;
        }
        txtghichu.setText("");
        modelchitiet.setRowCount(0);
        if (cbphutung.getItemCount() > 0) {
            cbphutung.setSelectedIndex(0);
        }
        xoaONhap();
    }

    public static void main(String[] args) {
        ManHinhLapPhieuKiemKe form = new ManHinhLapPhieuKiemKe();
        form.setVisible(true);
    }
}
