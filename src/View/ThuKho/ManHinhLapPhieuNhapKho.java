package View.ThuKho;

import DAO.NhaCungCap_DAO;
import DAO.PhieuNhapKho_DAO;
import DAO.PhuTung_DAO;
import DAO.ThuKho_DAO;
import Entity.ChiTietPhieuNhap;
import Entity.NhaCungCap;
import Entity.PhieuNhapKho;
import Entity.PhuTung;
import Entity.ThuKho;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ManHinhLapPhieuNhapKho extends JFrame implements ActionListener {

    private JLabel lbltieude;

    private JTextField txtmaphieu;
    private JTextField txtngaynhap;
    private JComboBox<String> cbnhacungcap;
    private JTextField txtnhanvien;
    private JTextField txtghichu;

    private JComboBox<String> cbphutung;
    private JTextField txtdonvi;
    private JTextField txtsoluong;
    private JTextField txtdongia;
    private JTextField txtthanhtien;
    private JButton btnthemdong;
    private JButton btnxoadong;

    private JTable tblchitiet;
    private DefaultTableModel modelchitiet;
    private JLabel lbltongtien;

    private JButton btnquaylai;
    private JButton btnlammoi;
    private JButton btnluu;

    private final PhuTung_DAO phuTungDAO = new PhuTung_DAO();
    private final NhaCungCap_DAO nhaCungCapDAO = new NhaCungCap_DAO();
    private final PhieuNhapKho_DAO phieuNhapDAO = new PhieuNhapKho_DAO();
    private final ThuKho_DAO thuKhoDAO = new ThuKho_DAO();

    private List<PhuTung> dsphutung = new ArrayList<>();
    private List<NhaCungCap> dsnhacungcap = new ArrayList<>();
    private final DecimalFormat dinhdang = new DecimalFormat("#,###");
    private boolean chedosua = false;
    private String maphieugoc = "";

    public ManHinhLapPhieuNhapKho() {
        khoiTao();
        txtmaphieu.setText(taoMaPhieu());
    }

    public ManHinhLapPhieuNhapKho(String maNhanVien, String maPhuTung) {
        khoiTao();
        txtmaphieu.setText(taoMaPhieu());
        dienNhanVien(maNhanVien);
        chonPhuTung(maPhuTung);
    }

    public ManHinhLapPhieuNhapKho(String maphieu) {
        this.chedosua = true;
        this.maphieugoc = maphieu;
        khoiTao();
        napDuLieuSua();
    }

    private void khoiTao() {
        setTitle(chedosua ? "SỬA PHIẾU NHẬP KHO" : "LẬP PHIẾU NHẬP KHO");
        setSize(900, 640);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        taiDanhMuc();
        buildUI();
    }

    private void napDuLieuSua() {
        try {
            PhieuNhapKho pn = phieuNhapDAO.timPhieuNhapTheoMa(maphieugoc);
            List<ChiTietPhieuNhap> dsct = phieuNhapDAO.getChiTietPhieuNhap(maphieugoc);
            if (pn == null || dsct.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu của phiếu " + maphieugoc + "!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                btnluu.setEnabled(false);
                return;
            }

            txtmaphieu.setText(pn.getMaPhieuNhap());
            if (pn.getNgayNhap() != null) {
                txtngaynhap.setText(new SimpleDateFormat("dd/MM/yyyy").format(pn.getNgayNhap()));
            }
            txtnhanvien.setText(pn.getThuKho().getMaNhanVien());

            String mancc = pn.getNhaCungCap().getMaNhaCungCap();
            for (int i = 0; i < dsnhacungcap.size(); i++) {
                if (dsnhacungcap.get(i).getMaNhaCungCap().equals(mancc)) {
                    cbnhacungcap.setSelectedIndex(i);
                }
            }

            modelchitiet.setRowCount(0);
            for (ChiTietPhieuNhap ct : dsct) {
                modelchitiet.addRow(new Object[]{
                        ct.getMaPhuTung(),
                        ct.getTenPhuTung(),
                        ct.getDonViTinh(),
                        ct.getSoLuongNhap(),
                        dinhdang.format(ct.getDonGiaNhap()),
                        dinhdang.format(ct.tinhTongTien())});
            }
            capNhatTongTien();
            xoaONhap();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    private void dienNhanVien(String maNhanVien) {
        if (maNhanVien == null || maNhanVien.trim().isEmpty()) return;

        if (maNhanVien.trim().equalsIgnoreCase("ADMIN")) {
            txtnhanvien.setText("ADMIN");
            txtnhanvien.setToolTipText("Quản trị viên");
            txtnhanvien.setEditable(false);
            txtnhanvien.setBackground(new Color(236, 240, 241));
            return;
        }

        try {
            ThuKho tk = thuKhoDAO.timThuKhoTheoMa(maNhanVien.trim());
            if (tk != null) {
                txtnhanvien.setText(tk.getMaNhanVien());
                txtnhanvien.setToolTipText(tk.getHoTen());
                txtnhanvien.setEditable(false);
                txtnhanvien.setBackground(new Color(236, 240, 241));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải thông tin nhân viên: " + e.getMessage());
        }
    }

    private void chonPhuTung(String maPhuTung) {
        if (maPhuTung == null) return;
        for (int i = 0; i < dsphutung.size(); i++) {
            if (dsphutung.get(i).getMaPhuTung().equalsIgnoreCase(maPhuTung)) {
                cbphutung.setSelectedIndex(i);
                break;
            }
        }
    }

    private void taiDanhMuc() {
        try {
            dsphutung = phuTungDAO.getAllPhuTung();
            dsnhacungcap = nhaCungCapDAO.getAllNhaCungCap();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    private String taoMaPhieu() {
        try {
            return phieuNhapDAO.sinhMaPhieuNhap();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tạo mã phiếu: " + e.getMessage());
            return "";
        }
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel(chedosua ? "SỬA PHIẾU NHẬP KHO" : "LẬP PHIẾU NHẬP KHO");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 22));
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbltieude.setForeground(new Color(44, 62, 80));
        lbltieude.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lbltieude, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel(new BorderLayout(0, 10));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pThongTin = new JPanel(new GridLayout(3, 4, 10, 10));
        pThongTin.setBackground(Color.WHITE);
        pThongTin.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông tin phiếu nhập",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        txtmaphieu = new JTextField();
        txtmaphieu.setEditable(false);
        txtmaphieu.setBackground(new Color(236, 240, 241));

        String homnay = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        txtngaynhap = new JTextField(homnay);
        txtngaynhap.setEditable(false);
        txtngaynhap.setBackground(new Color(236, 240, 241));

        cbnhacungcap = new JComboBox<>();
        for (NhaCungCap ncc : dsnhacungcap) {
            cbnhacungcap.addItem(ncc.getTenNhaCungCap());
        }

        // Mã nhân viên lập lấy theo tài khoản đăng nhập hoặc theo phiếu khi sửa không cho sửa tay
        txtnhanvien = new JTextField();
        txtnhanvien.setEditable(false);
        txtnhanvien.setBackground(new Color(236, 240, 241));

        txtghichu = new JTextField();

        pThongTin.add(new JLabel("Mã Phiếu:"));
        pThongTin.add(txtmaphieu);
        pThongTin.add(new JLabel("Ngày Nhập:"));
        pThongTin.add(txtngaynhap);
        pThongTin.add(new JLabel("Nhà Cung Cấp:"));
        pThongTin.add(cbnhacungcap);
        pThongTin.add(new JLabel("Mã Nhân Viên Lập:"));
        pThongTin.add(txtnhanvien);
        pThongTin.add(new JLabel("Ghi Chú:"));
        pThongTin.add(txtghichu);
        pThongTin.add(new JLabel(""));
        pThongTin.add(new JLabel(""));

        JPanel pChiTiet = new JPanel(new GridLayout(3, 4, 10, 10));
        pChiTiet.setBackground(Color.WHITE);
        pChiTiet.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Phụ tùng nhập kho",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        cbphutung = new JComboBox<>();
        for (PhuTung pt : dsphutung) {
            cbphutung.addItem(pt.getMaPhuTung() + " - " + pt.getTenPhuTung());
        }

        txtdonvi = new JTextField();
        txtdonvi.setEditable(false);
        txtdonvi.setBackground(new Color(236, 240, 241));

        txtsoluong = new JTextField();
        txtdongia = new JTextField();
        txtdongia.setEditable(false);
        txtdongia.setBackground(new Color(236, 240, 241));

        txtthanhtien = new JTextField();
        txtthanhtien.setEditable(false);
        txtthanhtien.setBackground(new Color(236, 240, 241));

        pChiTiet.add(new JLabel("Phụ Tùng:"));
        pChiTiet.add(cbphutung);
        pChiTiet.add(new JLabel("Đơn Vị:"));
        pChiTiet.add(txtdonvi);
        pChiTiet.add(new JLabel("Số Lượng:"));
        pChiTiet.add(txtsoluong);
        pChiTiet.add(new JLabel("Đơn Giá:"));
        pChiTiet.add(txtdongia);
        btnthemdong = new JButton("Thêm Vào Danh Sách");
        btnthemdong.setBackground(new Color(46, 204, 113));
        btnthemdong.setForeground(Color.WHITE);
        btnthemdong.setFocusPainted(false);

        btnxoadong = new JButton("Xóa Khỏi Danh Sách");
        btnxoadong.setBackground(new Color(231, 76, 60));
        btnxoadong.setForeground(Color.WHITE);
        btnxoadong.setFocusPainted(false);

        pChiTiet.add(new JLabel("Thành Tiền:"));
        pChiTiet.add(txtthanhtien);
        pChiTiet.add(btnthemdong);
        pChiTiet.add(btnxoadong);

        pCenter.add(pThongTin, BorderLayout.NORTH);
        pCenter.add(pChiTiet, BorderLayout.CENTER);

        String[] column = {"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị", "Số Lượng", "Đơn Giá", "Thành Tiền"};
        modelchitiet = new DefaultTableModel(column, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblchitiet = new JTable(modelchitiet);
        tblchitiet.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tblchitiet);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phụ tùng nhập"));

        JPanel pBang = new JPanel(new BorderLayout());
        pBang.setBackground(new Color(245, 245, 245));
        pBang.setBorder(new EmptyBorder(10, 20, 0, 20));
        pBang.add(scroll, BorderLayout.CENTER);

        JPanel pGiua = new JPanel(new BorderLayout());
        pGiua.setBackground(new Color(245, 245, 245));
        pGiua.add(pCenter, BorderLayout.NORTH);
        pGiua.add(pBang, BorderLayout.CENTER);
        add(pGiua, BorderLayout.CENTER);

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

        lbltongtien = new JLabel("Tổng tiền: 0 VNĐ");
        lbltongtien.setFont(new Font("Arial", Font.BOLD, 16));
        lbltongtien.setForeground(new Color(192, 57, 43));

        JPanel pDuoi = new JPanel(new BorderLayout());
        pDuoi.setBackground(new Color(245, 245, 245));
        pDuoi.setBorder(new EmptyBorder(0, 20, 0, 5));
        pDuoi.add(lbltongtien, BorderLayout.WEST);
        pDuoi.add(pSouth, BorderLayout.EAST);
        add(pDuoi, BorderLayout.SOUTH);

        initListener();
        xuLyDoiPhuTung();
    }

    private void initListener() {
        btnluu.addActionListener(this);
        btnquaylai.addActionListener(this);
        btnlammoi.addActionListener(this);
        cbphutung.addActionListener(this);
        txtsoluong.addActionListener(this);
        btnthemdong.addActionListener(this);
        btnxoadong.addActionListener(this);

        tblchitiet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyChonDong();
            }
        });

        // gõ số lượng tới đâu thì tính thành tiền tới đó
        txtsoluong.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                tinhThanhTien();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tinhThanhTien();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                tinhThanhTien();
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
        } else if (o == txtsoluong) {
            tinhThanhTien();
        } else if (o == btnthemdong) {
            xuLyThemDong();
        } else if (o == btnxoadong) {
            xuLyXoaDong();
        }
    }

    private void xuLyDoiPhuTung() {
        int vitri = cbphutung.getSelectedIndex();
        if (vitri >= 0) {
            PhuTung pt = dsphutung.get(vitri);
            txtdonvi.setText(pt.getDonViTinh());
            txtdongia.setText(String.valueOf((long) pt.getDonGiaNhap()));
            tinhThanhTien();
        }
    }

    private boolean tinhThanhTien() {
        try {
            int soluong = Integer.parseInt(txtsoluong.getText().trim());
            long dongia = Long.parseLong(txtdongia.getText().replace(",", "").trim());
            if (soluong <= 0 || dongia <= 0) throw new NumberFormatException();
            txtthanhtien.setText(dinhdang.format(soluong * dongia));
            return true;
        } catch (NumberFormatException ex) {
            txtthanhtien.setText("");
            return false;
        }
    }

    // Thêm phụ tùng đang chọn vào bảng; nếu phụ tùng đã có trong bảng thì cập nhật lại dòng đó
    private void xuLyThemDong() {
        if (dsphutung.isEmpty() || cbphutung.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this,
                    "Hệ thống chưa có phụ tùng nào!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (layDonGia() <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Phụ tùng này chưa có đơn giá nhập! Hãy cập nhật đơn giá trong Danh mục phụ tùng trước.",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!tinhThanhTien()) {
            JOptionPane.showMessageDialog(this,
                    "Số lượng phải là số nguyên lớn hơn 0!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtsoluong.requestFocus();
            return;
        }

        PhuTung pt = dsphutung.get(cbphutung.getSelectedIndex());
        int soluong = Integer.parseInt(txtsoluong.getText().trim());
        long dongia = layDonGia();
        Object[] dong = {
                pt.getMaPhuTung(),
                pt.getTenPhuTung(),
                pt.getDonViTinh(),
                soluong,
                dinhdang.format(dongia),
                dinhdang.format(soluong * dongia)};

        for (int i = 0; i < modelchitiet.getRowCount(); i++) {
            if (modelchitiet.getValueAt(i, 0).equals(pt.getMaPhuTung())) {
                for (int cot = 0; cot < dong.length; cot++) {
                    modelchitiet.setValueAt(dong[cot], i, cot);
                }
                capNhatTongTien();
                xoaONhap();
                return;
            }
        }
        modelchitiet.addRow(dong);
        capNhatTongTien();
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
        capNhatTongTien();
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
        txtdongia.setText(modelchitiet.getValueAt(dong, 4).toString().replaceAll("[^0-9]", ""));
        txtsoluong.setText(modelchitiet.getValueAt(dong, 3).toString());
        tinhThanhTien();
    }

    private void xoaONhap() {
        txtsoluong.setText("");
        txtthanhtien.setText("");
        xuLyDoiPhuTung();
    }

    private long layDonGia() {
        try {
            return Long.parseLong(txtdongia.getText().replaceAll("[^0-9]", ""));
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private void capNhatTongTien() {
        long tong = 0;
        for (int i = 0; i < modelchitiet.getRowCount(); i++) {
            tong += Long.parseLong(modelchitiet.getValueAt(i, 5).toString().replaceAll("[^0-9]", ""));
        }
        lbltongtien.setText(modelchitiet.getRowCount() + " phụ tùng  |  Tổng tiền: " + dinhdang.format(tong) + " VNĐ");
    }

    private void xuLyLuuPhieu() {
        String manv = txtnhanvien.getText().trim();
        if (manv.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Không xác định được nhân viên lập phiếu! Vui lòng đăng nhập bằng tài khoản thủ kho.",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (dsphutung.isEmpty() || dsnhacungcap.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Hệ thống chưa có phụ tùng hoặc nhà cung cấp!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (modelchitiet.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng thêm ít nhất 1 phụ tùng vào danh sách!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        NhaCungCap ncc = dsnhacungcap.get(cbnhacungcap.getSelectedIndex());

        PhieuNhapKho pn = new PhieuNhapKho();
        pn.setThuKho(new ThuKho(manv, null, null));
        pn.setMaPhieuNhap(txtmaphieu.getText());
        pn.setNgayNhap(new java.util.Date());
        pn.setTongTienNhap(0.0);
        pn.setNhaCungCap(ncc);
        List<ChiTietPhieuNhap> dsChiTiet = new ArrayList<>();
        for (int i = 0; i < modelchitiet.getRowCount(); i++) {
            PhuTung pt = new PhuTung();
            pt.setMaPhuTung(modelchitiet.getValueAt(i, 0).toString());
            int soluong = Integer.parseInt(modelchitiet.getValueAt(i, 3).toString());
            double dongia = Double.parseDouble(modelchitiet.getValueAt(i, 4).toString().replaceAll("[^0-9]", ""));
            dsChiTiet.add(new ChiTietPhieuNhap(pn, pt, soluong, dongia));
        }

        try {
            boolean thanhcong = chedosua
                    ? phieuNhapDAO.capNhatPhieuNhap(pn, dsChiTiet)
                    : phieuNhapDAO.taoPhieuNhap(pn, dsChiTiet);
            if (!thanhcong) {
                JOptionPane.showMessageDialog(this,
                        chedosua ? "Sửa phiếu nhập thất bại! Tồn kho không đủ để điều chỉnh." : "Lập phiếu nhập thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("FOREIGN KEY")) {
                JOptionPane.showMessageDialog(this,
                        "Mã nhân viên " + manv + " không tồn tại trong danh sách thủ kho!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi lưu phiếu nhập: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        JOptionPane.showMessageDialog(this,
                (chedosua ? "Sửa" : "Lập") + " phiếu nhập " + txtmaphieu.getText() + " thành công!");
        this.dispose();
    }

    private void xuLyLamMoi() {
        if (chedosua) {
            napDuLieuSua();
            return;
        }
        txtghichu.setText("");
        if (cbnhacungcap.getItemCount() > 0) cbnhacungcap.setSelectedIndex(0);
        modelchitiet.setRowCount(0);
        capNhatTongTien();
        if (cbphutung.getItemCount() > 0) cbphutung.setSelectedIndex(0);
        xoaONhap();
    }

    private void xuLyQuayLai() {
        this.dispose();
    }

    public static void main(String[] args) {
        ManHinhLapPhieuNhapKho form = new ManHinhLapPhieuNhapKho();
        form.setVisible(true);
    }
}