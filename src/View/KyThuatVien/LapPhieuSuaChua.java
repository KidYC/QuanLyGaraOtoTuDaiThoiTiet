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

        lblMaPhieuTiepNhan = new JLabel("Mã phiếu tiếp nhận:");
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

        btnTaoPhieu = new JButton("Tạo phiếu sửa chữa");
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
            cmbPhieuTiepNhan.addItem("-- Chọn phiếu tiếp nhận --");
            dsPhieuTiepNhan = phieuTiepNhanDAO.getAllPhieuTiepNhan();
            for (PhieuTiepNhan ptn : dsPhieuTiepNhan) {
                cmbPhieuTiepNhan.addItem(ptn.getMaPhieuTiepNhan() + " - " + ptn.getBienSo());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải phiếu tiếp nhận: " + ex.getMessage());
        }
    }

    private void chonPhieuTiepNhan() {
        int index = cmbPhieuTiepNhan.getSelectedIndex();
        if (index > 0 && index <= dsPhieuTiepNhan.size()) {
            PhieuTiepNhan ptn = dsPhieuTiepNhan.get(index - 1);
            txtBienSo.setText(ptn.getBienSo());
            txtNgayTiepNhan.setText(ptn.getNgayTiepNhan().toString());
            try {
                Xe xe = xeDAO.getXeByBienSo(ptn.getBienSo());
                if (xe != null) {
                    KhachHang kh = khachHangDAO.getKhachHangByMa(xe.getMaKhachHang());
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
        dialogHienTai = new JDialog(this, "Thêm hạng mục công việc", true);
        dialogHienTai.setLayout(new BorderLayout());
        dialogHienTai.setSize(480, 240);
        dialogHienTai.setLocationRelativeTo(this);

        pnlDialogPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        pnlDialogPanel.setBackground(Color.WHITE);
        pnlDialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        cmbCongViecDialog = new JComboBox<>();
        try {
            dsDichVu = dichVuDAO.getAllDichVu();
            for (DichVu dv : dsDichVu) {
                cmbCongViecDialog.addItem(dv.getMaDichVu() + " - " + dv.getTenDichVu());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        lblChonCongViec = new JLabel("Chọn công việc:");
        lblChonCongViec.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblChonCongViec.setForeground(new Color(17, 24, 39));

        pnlDialogPanel.add(lblChonCongViec);
        pnlDialogPanel.add(cmbCongViecDialog);

        pnlDialogButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlDialogButtonPanel.setBackground(Color.WHITE);

        btnHuyCongViecDialog = new JButton("Hủy");
        btnHuyCongViecDialog.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnHuyCongViecDialog.setForeground(new Color(17, 24, 39));
        btnHuyCongViecDialog.setBackground(new Color(226, 232, 240));
        btnHuyCongViecDialog.setFocusPainted(false);
        btnHuyCongViecDialog.setBorderPainted(false);

        btnLuuCongViecDialog = new JButton("Thêm");
        btnLuuCongViecDialog.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLuuCongViecDialog.setForeground(new Color(17, 24, 39));
        btnLuuCongViecDialog.setBackground(new Color(186, 230, 253));
        btnLuuCongViecDialog.setFocusPainted(false);
        btnLuuCongViecDialog.setBorderPainted(false);

        pnlDialogButtonPanel.add(btnHuyCongViecDialog);
        pnlDialogButtonPanel.add(btnLuuCongViecDialog);

        btnHuyCongViecDialog.addActionListener(this);
        btnLuuCongViecDialog.addActionListener(this);

        dialogHienTai.add(pnlDialogPanel, BorderLayout.CENTER);
        dialogHienTai.add(pnlDialogButtonPanel, BorderLayout.SOUTH);
        dialogHienTai.setVisible(true);
    }

    private void moFormThemPhuTung() {
        dialogHienTai = new JDialog(this, "Thêm phụ tùng vào phiếu", true);
        dialogHienTai.setLayout(new BorderLayout());
        dialogHienTai.setSize(480, 240);
        dialogHienTai.setLocationRelativeTo(this);

        pnlDialogPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        pnlDialogPanel.setBackground(Color.WHITE);
        pnlDialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        cmbPhuTungDialog = new JComboBox<>();
        txtSoLuongDialog = new JTextField("1");

        try {
            dsPhuTung = phuTungDAO.getAllPhuTung();
            for (PhuTung pt : dsPhuTung) {
                cmbPhuTungDialog.addItem(pt.getMaPhuTung() + " - " + pt.getTenPhuTung());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        lblChonPhuTung = new JLabel("Chọn phụ tùng:");
        lblChonPhuTung.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblChonPhuTung.setForeground(new Color(17, 24, 39));

        lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSoLuong.setForeground(new Color(17, 24, 39));

        pnlDialogPanel.add(lblChonPhuTung);
        pnlDialogPanel.add(cmbPhuTungDialog);
        pnlDialogPanel.add(lblSoLuong);
        pnlDialogPanel.add(txtSoLuongDialog);

        pnlDialogButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlDialogButtonPanel.setBackground(Color.WHITE);

        btnHuyPhuTungDialog = new JButton("Hủy");
        btnHuyPhuTungDialog.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnHuyPhuTungDialog.setForeground(new Color(17, 24, 39));
        btnHuyPhuTungDialog.setBackground(new Color(226, 232, 240));
        btnHuyPhuTungDialog.setFocusPainted(false);
        btnHuyPhuTungDialog.setBorderPainted(false);

        btnLuuPhuTungDialog = new JButton("Thêm");
        btnLuuPhuTungDialog.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLuuPhuTungDialog.setForeground(new Color(17, 24, 39));
        btnLuuPhuTungDialog.setBackground(new Color(219, 234, 254));
        btnLuuPhuTungDialog.setFocusPainted(false);
        btnLuuPhuTungDialog.setBorderPainted(false);

        pnlDialogButtonPanel.add(btnHuyPhuTungDialog);
        pnlDialogButtonPanel.add(btnLuuPhuTungDialog);

        btnHuyPhuTungDialog.addActionListener(this);
        btnLuuPhuTungDialog.addActionListener(this);

        dialogHienTai.add(pnlDialogPanel, BorderLayout.CENTER);
        dialogHienTai.add(pnlDialogButtonPanel, BorderLayout.SOUTH);
        dialogHienTai.setVisible(true);
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
        if (cmbPhieuTiepNhan.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu tiếp nhận xe!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maPhieuTiepNhan = dsPhieuTiepNhan.get(cmbPhieuTiepNhan.getSelectedIndex() - 1).getMaPhieuTiepNhan();
        String maPhieuSC = "PSC" + System.currentTimeMillis() % 100000;
        Date ngayHienTai = new Date(System.currentTimeMillis());
        PhieuSuaChua psc = new PhieuSuaChua(maPhieuSC, ngayHienTai, ngayHienTai, "Chờ xuất kho", txtBienSo.getText(), maKTV, maPhieuTiepNhan);

        try {
            boolean taoThanhCong = phieuSuaChuaDAO.themPhieuSuaChua(psc);
            if (taoThanhCong) {
                for (int i = 0; i < tableModelCongViec.getRowCount(); i++) {
                    String maDV = tableModelCongViec.getValueAt(i, 1).toString();
                    double donGia = Double.parseDouble(tableModelCongViec.getValueAt(i, 3).toString().replace(".", "").replace(",", ""));
                    chiTietDichVuDAO.themChiTietDichVu(new ChiTietDichVu(maPhieuSC, maDV, 1, donGia));
                }

                for (int i = 0; i < tableModelPhuTung.getRowCount(); i++) {
                    String maPT = tableModelPhuTung.getValueAt(i, 1).toString();
                    int sl = Integer.parseInt(tableModelPhuTung.getValueAt(i, 3).toString());
                    double donGia = Double.parseDouble(tableModelPhuTung.getValueAt(i, 4).toString().replace(".", "").replace(",", ""));
                    danhSachPhuTungDAO.themDanhSachPhuTung(new DanhSachPhuTung(maPhieuSC, maPT, sl, donGia));
                }

                JOptionPane.showMessageDialog(this,
                        "Tạo phiếu sửa chữa thành công!\nMã phiếu: " + maPhieuSC
                                + "\nĐã gửi yêu cầu vật tư sang bộ phận Thủ kho chờ xuất kho.",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                xoaForm();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tạo phiếu sửa chữa: " + ex.getMessage(), "Lỗi SQL", JOptionPane.ERROR_MESSAGE);
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
            chonPhieuTiepNhan();
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
        } else if (source == btnLuuCongViecDialog) {
            int idx = cmbCongViecDialog.getSelectedIndex();
            if (idx >= 0 && idx < dsDichVu.size()) {
                DichVu dv = dsDichVu.get(idx);
                int stt = tableModelCongViec.getRowCount() + 1;
                tableModelCongViec.addRow(new Object[]{
                        stt, dv.getMaDichVu(), dv.getTenDichVu(), df.format(dv.getDonGia())
                });
                tinhTongTien();
            }
            dialogHienTai.dispose();
        } else if (source == btnLuuPhuTungDialog) {
            try {
                int idx = cmbPhuTungDialog.getSelectedIndex();
                int sl = Integer.parseInt(txtSoLuongDialog.getText().trim());

                if (sl <= 0) {
                    JOptionPane.showMessageDialog(dialogHienTai, "Số lượng phải lớn hơn 0!");
                    return;
                }

                if (idx >= 0 && idx < dsPhuTung.size()) {
                    PhuTung pt = dsPhuTung.get(idx);
                    double thanhTien = sl * pt.getDonGiaBan();
                    int stt = tableModelPhuTung.getRowCount() + 1;

                    tableModelPhuTung.addRow(new Object[]{
                            stt, pt.getMaPhuTung(), pt.getTenPhuTung(), sl,
                            df.format(pt.getDonGiaBan()), df.format(thanhTien)
                    });
                    tinhTongTien();
                }

                dialogHienTai.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogHienTai, "Vui lòng nhập số lượng hợp lệ!");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {
        new LapPhieuSuaChua();
    }
}