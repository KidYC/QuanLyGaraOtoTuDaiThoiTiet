package View.KyThuatVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import DAO.*;
import Entity.*;

public class LapPhieuSuaChua extends JFrame implements ActionListener, MouseListener {
    private JPanel pnlHeaderPanel;
    private JPanel pnlMainContentPanel;
    private JPanel pnlTopInfoPanel;
    private JPanel pnlLeftPanel;
    private JPanel pnlHeaderLeft;
    private JPanel pnlBtnLeftPanel;
    private JPanel pnlRightPanel;
    private JPanel pnlHeaderRight;
    private JPanel pnlBtnRightPanel;
    private JPanel pnlBottomPanel;
    private JPanel pnlActionPanel;
    private JPanel pnlDialogPanel;
    private JPanel pnlDialogButtonPanel;

    private JLabel lblTitle;
    private JLabel lblTitleLeft;
    private JLabel lblTitleRight;
    private JLabel lblMaPhieuTiepNhan;
    private JLabel lblBienSo;
    private JLabel lblKhachHang;
    private JLabel lblNgayTiepNhan;
    private JLabel lblTongTien;
    private JLabel lblChonCongViec;
    private JLabel lblChonPhuTung;
    private JLabel lblSoLuong;

    private JComboBox<String> cmbPhieuTiepNhan;
    private JComboBox<String> cmbCongViecDialog;
    private JComboBox<String> cmbPhuTungDialog;

    private JTextField txtBienSo;
    private JTextField txtKhachHang;
    private JTextField txtNgayTiepNhan;
    private JTextField txtSoLuongDialog;

    private JTable tblCongViec;
    private JTable tblPhuTung;

    private DefaultTableModel tableModelCongViec;
    private DefaultTableModel tableModelPhuTung;

    private JButton btnThemCongViec;
    private JButton btnXoaCongViec;
    private JButton btnThemPhuTung;
    private JButton btnXoaPhuTung;
    private JButton btnTaoPhieu;
    private JButton btnLamMoi;
    private JButton btnQuayLai;
    private JButton btnLuuCongViecDialog;
    private JButton btnHuyCongViecDialog;
    private JButton btnLuuPhuTungDialog;
    private JButton btnHuyPhuTungDialog;

    private JScrollPane scrCongViec;
    private JScrollPane scrPhuTung;
    private JSplitPane splitPane;
    private JDialog dialogHienTai;
    private JTextField txtTimKiemDialog;
    private JTable tblChonDichVuDialog;
    private DefaultTableModel tableModelChonDichVu;

    private String maKTV = "KTV01";

    private final PhieuTiepNhan_DAO phieuTiepNhanDAO = new PhieuTiepNhan_DAO();
    private final Xe_DAO xeDAO = new Xe_DAO();
    private final KhachHang_DAO khachHangDAO = new KhachHang_DAO();
    private final DichVu_DAO dichVuDAO = new DichVu_DAO();
    private final PhuTung_DAO phuTungDAO = new PhuTung_DAO();
    private final PhieuSuaChua_DAO phieuSuaChuaDAO = new PhieuSuaChua_DAO();
    private final ChiTietDichVu_DAO chiTietDichVuDAO = new ChiTietDichVu_DAO();
    private final DanhSachPhuTung_DAO danhSachPhuTungDAO = new DanhSachPhuTung_DAO();

    private List<PhieuTiepNhan> dsPhieuTiepNhan = new ArrayList<>();
    private List<DichVu> dsDichVu = new ArrayList<>();
    private List<PhuTung> dsPhuTung = new ArrayList<>();
    private List<PhieuSuaChua> dsPhieuSuaChuaChoLap = new ArrayList<>();

    private final DecimalFormat df = new DecimalFormat("#,###");

    public LapPhieuSuaChua(String maKTV) {
        if (maKTV != null && !maKTV.trim().isEmpty()) {
            this.maKTV = maKTV;
        }

        setTitle("Lập phiếu sửa chữa - Garage Management");
        setSize(1180, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(244, 246, 248));
        setLayout(new BorderLayout(0, 10));

        pnlHeaderPanel = new JPanel(new BorderLayout());
        pnlHeaderPanel.setBackground(Color.WHITE);
        pnlHeaderPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        pnlHeaderPanel.setPreferredSize(new Dimension(1180, 75));

        lblTitle = new JLabel("LẬP PHIẾU SỬA CHỮA & CHỈ ĐỊNH VẬT TƯ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(17, 24, 39));

        btnQuayLai = new JButton("Quay lại");
        btnQuayLai.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnQuayLai.setForeground(new Color(17, 24, 39));
        btnQuayLai.setBackground(new Color(219, 234, 254));
        btnQuayLai.setFocusPainted(false);
        btnQuayLai.setBorderPainted(false);
        btnQuayLai.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnQuayLai.setPreferredSize(new Dimension(110, 40));

        pnlHeaderPanel.add(lblTitle, BorderLayout.WEST);
        pnlHeaderPanel.add(btnQuayLai, BorderLayout.EAST);
        add(pnlHeaderPanel, BorderLayout.NORTH);

        pnlMainContentPanel = new JPanel(new BorderLayout(0, 12));
        pnlMainContentPanel.setBackground(new Color(244, 246, 248));
        pnlMainContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));

        pnlTopInfoPanel = new JPanel(new GridLayout(2, 4, 15, 10));
        pnlTopInfoPanel.setBackground(Color.WHITE);
        pnlTopInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(186, 230, 253), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        cmbPhieuTiepNhan = new JComboBox<>();
        cmbPhieuTiepNhan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbPhieuTiepNhan.setBackground(Color.WHITE);

        txtBienSo = new JTextField();
        txtBienSo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBienSo.setEditable(false);
        txtBienSo.setBackground(new Color(244, 246, 248));

        txtKhachHang = new JTextField();
        txtKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtKhachHang.setEditable(false);
        txtKhachHang.setBackground(new Color(244, 246, 248));

        txtNgayTiepNhan = new JTextField();
        txtNgayTiepNhan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNgayTiepNhan.setEditable(false);
        txtNgayTiepNhan.setBackground(new Color(244, 246, 248));

        lblMaPhieuTiepNhan = new JLabel("Mã phiếu sửa chữa:");
        lblMaPhieuTiepNhan.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMaPhieuTiepNhan.setForeground(new Color(17, 24, 39));

        lblBienSo = new JLabel("Biển số xe:");
        lblBienSo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblBienSo.setForeground(new Color(17, 24, 39));

        lblKhachHang = new JLabel("Tên khách hàng:");
        lblKhachHang.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblKhachHang.setForeground(new Color(17, 24, 39));

        lblNgayTiepNhan = new JLabel("Ngày tiếp nhận:");
        lblNgayTiepNhan.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNgayTiepNhan.setForeground(new Color(17, 24, 39));

        pnlTopInfoPanel.add(lblMaPhieuTiepNhan);
        pnlTopInfoPanel.add(cmbPhieuTiepNhan);
        pnlTopInfoPanel.add(lblBienSo);
        pnlTopInfoPanel.add(txtBienSo);
        pnlTopInfoPanel.add(lblKhachHang);
        pnlTopInfoPanel.add(txtKhachHang);
        pnlTopInfoPanel.add(lblNgayTiepNhan);
        pnlTopInfoPanel.add(txtNgayTiepNhan);

        pnlMainContentPanel.add(pnlTopInfoPanel, BorderLayout.NORTH);

        pnlLeftPanel = new JPanel(new BorderLayout(0, 8));
        pnlLeftPanel.setBackground(Color.WHITE);
        pnlLeftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(186, 230, 253), 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        pnlHeaderLeft = new JPanel(new BorderLayout());
        pnlHeaderLeft.setBackground(Color.WHITE);

        lblTitleLeft = new JLabel("1. HẠNG MỤC CÔNG VIỆC SỬA CHỮA");
        lblTitleLeft.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitleLeft.setForeground(new Color(17, 24, 39));

        pnlBtnLeftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pnlBtnLeftPanel.setBackground(Color.WHITE);

        btnThemCongViec = new JButton("Thêm CV");
        btnThemCongViec.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnThemCongViec.setForeground(new Color(17, 24, 39));
        btnThemCongViec.setBackground(new Color(186, 230, 253));
        btnThemCongViec.setFocusPainted(false);
        btnThemCongViec.setBorderPainted(false);
        btnThemCongViec.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnThemCongViec.setPreferredSize(new Dimension(105, 32));

        btnXoaCongViec = new JButton("Xóa");
        btnXoaCongViec.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnXoaCongViec.setForeground(new Color(17, 24, 39));
        btnXoaCongViec.setBackground(new Color(220, 38, 38));
        btnXoaCongViec.setFocusPainted(false);
        btnXoaCongViec.setBorderPainted(false);
        btnXoaCongViec.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnXoaCongViec.setPreferredSize(new Dimension(75, 32));

        pnlBtnLeftPanel.add(btnThemCongViec);
        pnlBtnLeftPanel.add(btnXoaCongViec);
        pnlHeaderLeft.add(lblTitleLeft, BorderLayout.WEST);
        pnlHeaderLeft.add(pnlBtnLeftPanel, BorderLayout.EAST);

        String[] colCongViec = {"STT", "Mã CV", "Tên công việc", "Tiền công (VNĐ)"};
        tableModelCongViec = new DefaultTableModel(colCongViec, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblCongViec = new JTable(tableModelCongViec);
        tblCongViec.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblCongViec.setRowHeight(36);
        tblCongViec.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCongViec.setSelectionBackground(new Color(219, 234, 254));
        tblCongViec.setSelectionForeground(new Color(17, 24, 39));
        tblCongViec.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblCongViec.getTableHeader().setBackground(new Color(186, 230, 253));

        scrCongViec = new JScrollPane(tblCongViec);

        pnlLeftPanel.add(pnlHeaderLeft, BorderLayout.NORTH);
        pnlLeftPanel.add(scrCongViec, BorderLayout.CENTER);

        pnlRightPanel = new JPanel(new BorderLayout(0, 8));
        pnlRightPanel.setBackground(Color.WHITE);
        pnlRightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(186, 230, 253), 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        pnlHeaderRight = new JPanel(new BorderLayout());
        pnlHeaderRight.setBackground(Color.WHITE);

        lblTitleRight = new JLabel("2. DANH SÁCH PHỤ TÙNG SỬ DỤNG");
        lblTitleRight.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitleRight.setForeground(new Color(17, 24, 39));

        pnlBtnRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pnlBtnRightPanel.setBackground(Color.WHITE);

        btnThemPhuTung = new JButton("Thêm PT");
        btnThemPhuTung.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnThemPhuTung.setForeground(new Color(17, 24, 39));
        btnThemPhuTung.setBackground(new Color(219, 234, 254));
        btnThemPhuTung.setFocusPainted(false);
        btnThemPhuTung.setBorderPainted(false);
        btnThemPhuTung.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnThemPhuTung.setPreferredSize(new Dimension(105, 32));

        btnXoaPhuTung = new JButton("Xóa");
        btnXoaPhuTung.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnXoaPhuTung.setForeground(new Color(17, 24, 39));
        btnXoaPhuTung.setBackground(new Color(220, 38, 38));
        btnXoaPhuTung.setFocusPainted(false);
        btnXoaPhuTung.setBorderPainted(false);
        btnXoaPhuTung.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnXoaPhuTung.setPreferredSize(new Dimension(75, 32));

        pnlBtnRightPanel.add(btnThemPhuTung);
        pnlBtnRightPanel.add(btnXoaPhuTung);
        pnlHeaderRight.add(lblTitleRight, BorderLayout.WEST);
        pnlHeaderRight.add(pnlBtnRightPanel, BorderLayout.EAST);

        String[] colPhuTung = {"STT", "Mã PT", "Tên phụ tùng", "SL", "Đơn giá", "Thành tiền"};
        tableModelPhuTung = new DefaultTableModel(colPhuTung, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblPhuTung = new JTable(tableModelPhuTung);
        tblPhuTung.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblPhuTung.setRowHeight(36);
        tblPhuTung.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblPhuTung.setSelectionBackground(new Color(219, 234, 254));
        tblPhuTung.setSelectionForeground(new Color(17, 24, 39));
        tblPhuTung.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblPhuTung.getTableHeader().setBackground(new Color(186, 230, 253));

        scrPhuTung = new JScrollPane(tblPhuTung);

        pnlRightPanel.add(pnlHeaderRight, BorderLayout.NORTH);
        pnlRightPanel.add(scrPhuTung, BorderLayout.CENTER);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLeftPanel, pnlRightPanel);
        splitPane.setDividerLocation(560);
        splitPane.setDividerSize(6);
        splitPane.setBorder(null);

        pnlMainContentPanel.add(splitPane, BorderLayout.CENTER);

        pnlBottomPanel = new JPanel(new BorderLayout());
        pnlBottomPanel.setBackground(Color.WHITE);
        pnlBottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(186, 230, 253), 1),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)));

        lblTongTien = new JLabel("Tổng chi phí ước tính: 0 VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTongTien.setForeground(new Color(220, 38, 38));

        pnlActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlActionPanel.setBackground(Color.WHITE);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLamMoi.setForeground(new Color(17, 24, 39));
        btnLamMoi.setBackground(new Color(226, 232, 240));
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnTaoPhieu = new JButton("Hoàn tất & Chuyển báo giá");
        btnTaoPhieu.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnTaoPhieu.setForeground(new Color(17, 24, 39));
        btnTaoPhieu.setBackground(new Color(34, 197, 94));
        btnTaoPhieu.setFocusPainted(false);
        btnTaoPhieu.setBorderPainted(false);
        btnTaoPhieu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTaoPhieu.setPreferredSize(new Dimension(200, 40));

        pnlActionPanel.add(btnLamMoi);
        pnlActionPanel.add(btnTaoPhieu);

        pnlBottomPanel.add(lblTongTien, BorderLayout.WEST);
        pnlBottomPanel.add(pnlActionPanel, BorderLayout.EAST);
        pnlMainContentPanel.add(pnlBottomPanel, BorderLayout.SOUTH);

        add(pnlMainContentPanel, BorderLayout.CENTER);

        btnQuayLai.addActionListener(this);
        cmbPhieuTiepNhan.addActionListener(this);
        btnThemCongViec.addActionListener(this);
        btnXoaCongViec.addActionListener(this);
        btnThemPhuTung.addActionListener(this);
        btnXoaPhuTung.addActionListener(this);
        btnTaoPhieu.addActionListener(this);
        btnLamMoi.addActionListener(this);
        tblCongViec.addMouseListener(this);
        tblPhuTung.addMouseListener(this);

        loadPhieuTiepNhanCombobox();
        setVisible(true);
    }

    public LapPhieuSuaChua() {
        this("KTV01");
    }

    private void loadPhieuTiepNhanCombobox() {
        try {
            cmbPhieuTiepNhan.removeAllItems();
            cmbPhieuTiepNhan.addItem("-- Chọn phiếu đang lập vật tư --");

            List<PhieuSuaChua> allPSC = phieuSuaChuaDAO.getAllPhieuSuaChua();
            for (PhieuSuaChua psc : allPSC) {
                if ("Đang lập vật tư".equals(psc.getTrangThai())) {
                    dsPhieuSuaChuaChoLap.add(psc);
                    cmbPhieuTiepNhan.addItem(psc.getMaPhieuSuaChua() + " - " + psc.getBienSo());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void chonPhieuSuaChua() {
        int index = cmbPhieuTiepNhan.getSelectedIndex();
        if (index > 0 && index <= dsPhieuSuaChuaChoLap.size()) {
            PhieuSuaChua psc = dsPhieuSuaChuaChoLap.get(index - 1);

            txtBienSo.setText(psc.getBienSo() != null ? psc.getBienSo() : "");
            txtNgayTiepNhan.setText(psc.getNgayLap() != null ? psc.getNgayLap().toString() : "");

            try {
                Xe xe = xeDAO.getXeByBienSo(psc.getBienSo());
                if (xe != null && xe.getKhachHang() != null) {
                    // Lấy mã khách hàng (String) từ đối tượng KhachHang bên trong xe
                    String maKH = xe.getKhachHang().getMaKhachHang();

                    KhachHang kh = khachHangDAO.getKhachHangByMa(maKH);
                    txtKhachHang.setText(kh != null ? kh.getHoTen() : "");
                } else {
                    txtKhachHang.setText("");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            txtBienSo.setText("");
            txtKhachHang.setText("");
            txtNgayTiepNhan.setText("");
        }
    }

    private void moFormThemCongViec() {
        dialogHienTai = new JDialog(this, "Chọn công việc sửa chữa", true);
        dialogHienTai.setLayout(new BorderLayout(0, 10));
        dialogHienTai.setSize(700, 450);
        dialogHienTai.setLocationRelativeTo(this);

        JPanel pnlTopDialog = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlTopDialog.setBackground(Color.WHITE);
        JLabel lblTim = new JLabel("Nhập tên hoặc mã dịch vụ cần tìm:");
        lblTim.setFont(new Font("Segoe UI", Font.BOLD, 13));

        txtTimKiemDialog = new JTextField(25);
        txtTimKiemDialog.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlTopDialog.add(lblTim);
        pnlTopDialog.add(txtTimKiemDialog);

        String[] cols = {"Mã DV", "Tên dịch vụ", "Đơn giá (VNĐ)", "Mô tả", "Bảo hành"};
        tableModelChonDichVu = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblChonDichVuDialog = new JTable(tableModelChonDichVu);
        tblChonDichVuDialog.setRowHeight(30);
        tblChonDichVuDialog.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblChonDichVuDialog.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrDialog = new JScrollPane(tblChonDichVuDialog);

        JPanel pnlBottomDialog = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlBottomDialog.setBackground(Color.WHITE);

        JButton btnHuy = new JButton("Hủy");
        btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnHuy.setBackground(new Color(226, 232, 240));

        JButton btnChon = new JButton("Thêm vào phiếu");
        btnChon.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnChon.setBackground(new Color(186, 230, 253));

        pnlBottomDialog.add(btnHuy);
        pnlBottomDialog.add(btnChon);

        dialogHienTai.add(pnlTopDialog, BorderLayout.NORTH);
        dialogHienTai.add(scrDialog, BorderLayout.CENTER);
        dialogHienTai.add(pnlBottomDialog, BorderLayout.SOUTH);
        loadDataLenBảngDialog("");

        txtTimKiemDialog.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { locDuLieu(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { locDuLieu(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { locDuLieu(); }

            private void locDuLieu() {
                String keyword = txtTimKiemDialog.getText().trim();
                loadDataLenBảngDialog(keyword);
            }
        });
        btnHuy.addActionListener(e -> dialogHienTai.dispose());
        btnChon.addActionListener(e -> chonDichVuTuBangDialog());
        tblChonDichVuDialog.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    chonDichVuTuBangDialog();
                }
            }
        });

        dialogHienTai.setVisible(true);
    }

    private void loadDataLenBảngDialog(String keyword) {
        try {
            tableModelChonDichVu.setRowCount(0);
            dsDichVu = dichVuDAO.getAllDichVu();
            String kwLower = keyword.toLowerCase();

            for (DichVu dv : dsDichVu) {
                if (kwLower.isEmpty() ||
                        dv.getMaDichVu().toLowerCase().contains(kwLower) ||
                        dv.getTenDichVu().toLowerCase().contains(kwLower) ||
                        (dv.getMoTa() != null && dv.getMoTa().toLowerCase().contains(kwLower))) {

                    tableModelChonDichVu.addRow(new Object[]{
                            dv.getMaDichVu(),
                            dv.getTenDichVu(),
                            df.format(dv.getDonGiaHienTai()),
                            dv.getMoTa(),
                            dv.getThoiGianBaoHanh() + " tháng"
                    });
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void chonDichVuTuBangDialog() {
        int row = tblChonDichVuDialog.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(dialogHienTai, "Vui lòng chọn một dịch vụ từ danh sách!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maDV = tableModelChonDichVu.getValueAt(row, 0).toString();
        String tenDV = tableModelChonDichVu.getValueAt(row, 1).toString();
        String donGiaStr = tableModelChonDichVu.getValueAt(row, 2).toString();

        int stt = tableModelCongViec.getRowCount() + 1;
        tableModelCongViec.addRow(new Object[]{
                stt, maDV, tenDV, donGiaStr
        });
        tinhTongTien();
        dialogHienTai.dispose();
    }

    private void moFormThemPhuTung() {
        dialogHienTai = new JDialog(this, "Chọn phụ tùng sửa chữa", true);
        dialogHienTai.setLayout(new BorderLayout(0, 10));
        dialogHienTai.setSize(750, 500);
        dialogHienTai.setLocationRelativeTo(this);

        JPanel pnlTopDialog = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlTopDialog.setBackground(Color.WHITE);
        JLabel lblTim = new JLabel("Nhập tên hoặc mã phụ tùng:");
        lblTim.setFont(new Font("Segoe UI", Font.BOLD, 13));

        txtTimKiemDialog = new JTextField(20);
        txtTimKiemDialog.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlTopDialog.add(lblTim);
        pnlTopDialog.add(txtTimKiemDialog);

        String[] cols = {"Mã PT", "Tên phụ tùng", "ĐVT", "Đơn giá bán (VNĐ)", "Tồn kho"};
        DefaultTableModel tableModelChonPhuTung = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblChonDichVuDialog = new JTable(tableModelChonPhuTung);
        tblChonDichVuDialog.setRowHeight(30);
        tblChonDichVuDialog.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblChonDichVuDialog.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrDialog = new JScrollPane(tblChonDichVuDialog);

        JPanel pnlBottomDialog = new JPanel(new BorderLayout());
        pnlBottomDialog.setBackground(Color.WHITE);
        pnlBottomDialog.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel pnlSL = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlSL.setBackground(Color.WHITE);
        JLabel lblSL = new JLabel("Số lượng:");
        lblSL.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtSoLuongDialog = new JTextField("1", 8);
        txtSoLuongDialog.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlSL.add(lblSL);
        pnlSL.add(txtSoLuongDialog);

        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlBtns.setBackground(Color.WHITE);
        JButton btnHuy = new JButton("Hủy");
        btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnHuy.setBackground(new Color(226, 232, 240));

        JButton btnChon = new JButton("Thêm vào phiếu");
        btnChon.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnChon.setBackground(new Color(186, 230, 253));

        pnlBtns.add(btnHuy);
        pnlBtns.add(btnChon);

        pnlBottomDialog.add(pnlSL, BorderLayout.WEST);
        pnlBottomDialog.add(pnlBtns, BorderLayout.EAST);

        dialogHienTai.add(pnlTopDialog, BorderLayout.NORTH);
        dialogHienTai.add(scrDialog, BorderLayout.CENTER);
        dialogHienTai.add(pnlBottomDialog, BorderLayout.SOUTH);

        loadDataLenBangPhuTungDialog("", tableModelChonPhuTung);

        txtTimKiemDialog.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { locDuLieu(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { locDuLieu(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { locDuLieu(); }

            private void locDuLieu() {
                String keyword = txtTimKiemDialog.getText().trim();
                loadDataLenBangPhuTungDialog(keyword, tableModelChonPhuTung);
            }
        });

        btnHuy.addActionListener(e -> dialogHienTai.dispose());
        btnChon.addActionListener(e -> chonPhuTungTuBangDialog(tableModelChonPhuTung));

        tblChonDichVuDialog.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    chonPhuTungTuBangDialog(tableModelChonPhuTung);
                }
            }
        });

        dialogHienTai.setVisible(true);
    }
    private void loadDataLenBangPhuTungDialog(String keyword, DefaultTableModel model) {
        try {
            model.setRowCount(0);
            dsPhuTung = phuTungDAO.getAllPhuTung();

            String kwLower = (keyword == null) ? "" : keyword.trim().toLowerCase();

            for (PhuTung pt : dsPhuTung) {
                if (kwLower.isEmpty() ||
                        pt.getMaPhuTung().toLowerCase().contains(kwLower) ||
                        pt.getTenPhuTung().toLowerCase().contains(kwLower)) {

                    model.addRow(new Object[]{
                            pt.getMaPhuTung(),
                            pt.getTenPhuTung(),
                            pt.getDonViTinh(),
                            df.format(pt.getDonGiaBan()),
                            pt.getSoLuongTon()
                    });
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void chonPhuTungTuBangDialog(DefaultTableModel model) {
        int row = tblChonDichVuDialog.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(dialogHienTai, "Vui lòng chọn một phụ tùng từ danh sách!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int sl = Integer.parseInt(txtSoLuongDialog.getText().trim());
            if (sl <= 0) {
                JOptionPane.showMessageDialog(dialogHienTai, "Số lượng phải lớn hơn 0!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maPT = model.getValueAt(row, 0).toString();
            String tenPT = model.getValueAt(row, 1).toString();
            double donGia = Double.parseDouble(model.getValueAt(row, 3).toString().replace(",", "").replace(".", ""));
            double thanhTien = sl * donGia;

            int stt = tableModelPhuTung.getRowCount() + 1;
            tableModelPhuTung.addRow(new Object[]{
                    stt, maPT, tenPT, sl, df.format(donGia), df.format(thanhTien)
            });
            tinhTongTien();
            dialogHienTai.dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialogHienTai, "Vui lòng nhập số lượng hợp lệ!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaDongTable(JTable tblTable, DefaultTableModel model) {
        int row = tblTable.getSelectedRow();
        if (row != -1) {
            model.removeRow(row);
            capNhatSTT(tableModelCongViec);
            capNhatSTT(tableModelPhuTung);
            tinhTongTien();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
        }
    }

    private void capNhatSTT(DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(i + 1, i, 0);
        }
    }

    private void tinhTongTien() {
        double tong = 0;
        for (int i = 0; i < tableModelCongViec.getRowCount(); i++) {
            tong += Double.parseDouble(tableModelCongViec.getValueAt(i, 3).toString().replace(",", "").replace(".", ""));
        }
        for (int i = 0; i < tableModelPhuTung.getRowCount(); i++) {
            tong += Double.parseDouble(tableModelPhuTung.getValueAt(i, 5).toString().replace(",", "").replace(".", ""));
        }
        lblTongTien.setText("Tổng chi phí ước tính: " + df.format(tong) + " VNĐ");
    }

    private void xacNhanTaoPhieu() {
        int index = cmbPhieuTiepNhan.getSelectedIndex();
        if (index <= 0) return;

        String maPhieuSC = dsPhieuSuaChuaChoLap.get(index - 1).getMaPhieuSuaChua();

        try {

            PhieuSuaChua psc = new PhieuSuaChua();
            psc.setMaPhieuSuaChua(maPhieuSC);

            for (int i = 0; i < tableModelCongViec.getRowCount(); i++) {
                String maDV = tableModelCongViec.getValueAt(i, 1).toString();
                double donGia = Double.parseDouble(tableModelCongViec.getValueAt(i, 3).toString().replace(".", "").replace(",", ""));


                DichVu dv = new DichVu();
                dv.setMaDichVu(maDV);

                chiTietDichVuDAO.themChiTietDichVu(new ChiTietDichVu(psc, dv, 1, donGia));
            }

            for (int i = 0; i < tableModelPhuTung.getRowCount(); i++) {
                String maPT = tableModelPhuTung.getValueAt(i, 1).toString();
                int sl = Integer.parseInt(tableModelPhuTung.getValueAt(i, 3).toString());
                double donGia = Double.parseDouble(tableModelPhuTung.getValueAt(i, 4).toString().replace(".", "").replace(",", ""));


                PhuTung pt = new PhuTung();
                pt.setMaPhuTung(maPT);

                danhSachPhuTungDAO.themDanhSachPhuTung(new DanhSachPhuTung(psc, pt, sl, donGia));
            }

            phieuSuaChuaDAO.capNhatTrangThai(maPhieuSC, "Chờ xác nhận báo giá");

            JOptionPane.showMessageDialog(this, "Lập phiếu thành công! Đã chuyển sang bộ phận Lễ tân chờ báo giá.");
            xoaForm();
            loadPhieuTiepNhanCombobox();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu phụ tùng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaForm() {
        cmbPhieuTiepNhan.setSelectedIndex(0);
        txtBienSo.setText("");
        txtKhachHang.setText("");
        txtNgayTiepNhan.setText("");
        tableModelCongViec.setRowCount(0);
        tableModelPhuTung.setRowCount(0);
        lblTongTien.setText("Tổng chi phí ước tính: 0 VNĐ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnQuayLai) {
            dispose();
        } else if (source == cmbPhieuTiepNhan) {
            chonPhieuSuaChua();
        } else if (source == btnThemCongViec) {
            moFormThemCongViec();
        } else if (source == btnXoaCongViec) {
            xoaDongTable(tblCongViec, tableModelCongViec);
        } else if (source == btnThemPhuTung) {
            moFormThemPhuTung();
        } else if (source == btnXoaPhuTung) {
            xoaDongTable(tblPhuTung, tableModelPhuTung);
        } else if (source == btnTaoPhieu) {
            xacNhanTaoPhieu();
        } else if (source == btnLamMoi) {
            xoaForm();
        } else if (source == btnHuyCongViecDialog || source == btnHuyPhuTungDialog) {
            if (dialogHienTai != null) {
                dialogHienTai.dispose();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        new LapPhieuSuaChua();
    }
}