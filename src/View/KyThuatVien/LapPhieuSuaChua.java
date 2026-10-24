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
    private JComboBox<String> cmbPhieuTiepNhan;
    private JTextField txtBienSo;
    private JTextField txtKhachHang;
    private JTextField txtNgayTiepNhan;
    private JTable tblCongViec;
    private DefaultTableModel tableModelCongViec;
    private JButton btnThemCongViec;
    private JButton btnXoaCongViec;
    private JTable tblPhuTung;
    private DefaultTableModel tableModelPhuTung;
    private JButton btnThemPhuTung;
    private JButton btnXoaPhuTung;
    private JLabel lblTongTien;
    private JButton btnTaoPhieu;
    private JButton btnLamMoi;
    private JButton btnQuayLai;
    private String maKTV = "KTV01";

    private final Color maunen = new Color(244, 246, 248);
    private final Color maumenu = new Color(226, 232, 240);
    private final Color btnchinh = new Color(186, 230, 253);
    private final Color btnphu = new Color(219, 234, 254);
    private final Color chuchinh = new Color(17, 24, 39);
    private final Color maucotbang = new Color(186, 230, 253);
    private final Color maudo = new Color(220, 38, 38);
    private final Color mauxanh = new Color(34, 197, 94);

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

    private JDialog dialogHienTai;
    private JComboBox<String> cmbCongViecDialog;
    private JComboBox<String> cmbPhuTungDialog;
    private JTextField txtSoLuongDialog;
    private JButton btnLuuCongViecDialog;
    private JButton btnHuyCongViecDialog;
    private JButton btnLuuPhuTungDialog;
    private JButton btnHuyPhuTungDialog;

    public LapPhieuSuaChua(String maKTV) {
        if (maKTV != null && !maKTV.trim().isEmpty()) {
            this.maKTV = maKTV;
        }
        setTitle("Lập phiếu sửa chữa - Garage Management");
        setSize(1180, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(maunen);
        setLayout(new BorderLayout(0, 10));

        taoHeader();
        taoContent();

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

    private void taoHeader() {
        pnlHeaderPanel = new JPanel(new BorderLayout());
        pnlHeaderPanel.setBackground(Color.WHITE);
        pnlHeaderPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        pnlHeaderPanel.setPreferredSize(new Dimension(1180, 75));

        JLabel lblTitle = new JLabel("LẬP PHIẾU SỬA CHỮA & CHỈ ĐỊNH VẬT TƯ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(chuchinh);

        btnQuayLai = taoButton("Quay lại", btnphu);
        btnQuayLai.setPreferredSize(new Dimension(110, 40));

        pnlHeaderPanel.add(lblTitle, BorderLayout.WEST);
        pnlHeaderPanel.add(btnQuayLai, BorderLayout.EAST);
        add(pnlHeaderPanel, BorderLayout.NORTH);
    }

    private void taoContent() {
        pnlMainContentPanel = new JPanel(new BorderLayout(0, 12));
        pnlMainContentPanel.setBackground(maunen);
        pnlMainContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));

        taoThongTinChungPanel();
        taoPanelHaiBang();
        taoBottomPanel();

        add(pnlMainContentPanel, BorderLayout.CENTER);
    }

    private void taoThongTinChungPanel() {
        pnlTopInfoPanel = new JPanel(new GridLayout(2, 4, 15, 10));
        pnlTopInfoPanel.setBackground(Color.WHITE);
        pnlTopInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        cmbPhieuTiepNhan = new JComboBox<>();
        cmbPhieuTiepNhan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbPhieuTiepNhan.setBackground(Color.WHITE);

        txtBienSo = taoTextFieldReadOnly();
        txtKhachHang = taoTextFieldReadOnly();
        txtNgayTiepNhan = taoTextFieldReadOnly();

        pnlTopInfoPanel.add(taoLabel("Mã phiếu tiếp nhận:"));
        pnlTopInfoPanel.add(cmbPhieuTiepNhan);
        pnlTopInfoPanel.add(taoLabel("Biển số xe:"));
        pnlTopInfoPanel.add(txtBienSo);
        pnlTopInfoPanel.add(taoLabel("Tên khách hàng:"));
        pnlTopInfoPanel.add(txtKhachHang);
        pnlTopInfoPanel.add(taoLabel("Ngày tiếp nhận:"));
        pnlTopInfoPanel.add(txtNgayTiepNhan);

        pnlMainContentPanel.add(pnlTopInfoPanel, BorderLayout.NORTH);
    }

    private void taoPanelHaiBang() {
        JPanel pnlLeftPanel = new JPanel(new BorderLayout(0, 8));
        pnlLeftPanel.setBackground(Color.WHITE);
        pnlLeftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        JPanel pnlHeaderLeft = new JPanel(new BorderLayout());
        pnlHeaderLeft.setBackground(Color.WHITE);

        JLabel lblTitleLeft = new JLabel("1. HẠNG MỤC CÔNG VIỆC SỬA CHỮA");
        lblTitleLeft.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitleLeft.setForeground(chuchinh);

        JPanel pnlBtnLeftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pnlBtnLeftPanel.setBackground(Color.WHITE);

        btnThemCongViec = taoButton("Thêm CV", btnchinh);
        btnThemCongViec.setPreferredSize(new Dimension(105, 32));
        btnXoaCongViec = taoButton("Xóa", maudo);
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

        tblCongViec = taoTable(tableModelCongViec);
        pnlLeftPanel.add(pnlHeaderLeft, BorderLayout.NORTH);
        pnlLeftPanel.add(new JScrollPane(tblCongViec), BorderLayout.CENTER);

        JPanel pnlRightPanel = new JPanel(new BorderLayout(0, 8));
        pnlRightPanel.setBackground(Color.WHITE);
        pnlRightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        JPanel pnlHeaderRight = new JPanel(new BorderLayout());
        pnlHeaderRight.setBackground(Color.WHITE);
        JLabel lblTitleRight = new JLabel("2. DANH SÁCH PHỤ TÙNG SỬ DỤNG");
        lblTitleRight.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitleRight.setForeground(chuchinh);

        JPanel pnlBtnRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pnlBtnRightPanel.setBackground(Color.WHITE);
        btnThemPhuTung = taoButton("Thêm PT", btnphu);
        btnThemPhuTung.setPreferredSize(new Dimension(105, 32));
        btnXoaPhuTung = taoButton("Xóa", maudo);
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

        tblPhuTung = taoTable(tableModelPhuTung);
        pnlRightPanel.add(pnlHeaderRight, BorderLayout.NORTH);
        pnlRightPanel.add(new JScrollPane(tblPhuTung), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLeftPanel, pnlRightPanel);
        splitPane.setDividerLocation(560);
        splitPane.setDividerSize(6);
        splitPane.setBorder(null);
        pnlMainContentPanel.add(splitPane, BorderLayout.CENTER);
    }

    private void taoBottomPanel() {
        JPanel pnlBottomPanel = new JPanel(new BorderLayout());
        pnlBottomPanel.setBackground(Color.WHITE);
        pnlBottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)));

        lblTongTien = new JLabel("Tổng chi phí ước tính: 0 VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTongTien.setForeground(maudo);

        JPanel pnlActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlActionPanel.setBackground(Color.WHITE);
        btnLamMoi = taoButton("Làm mới", maumenu);
        btnTaoPhieu = taoButton("Tạo phiếu sửa chữa", mauxanh);
        btnTaoPhieu.setPreferredSize(new Dimension(200, 40));
        pnlActionPanel.add(btnLamMoi);
        pnlActionPanel.add(btnTaoPhieu);

        pnlBottomPanel.add(lblTongTien, BorderLayout.WEST);
        pnlBottomPanel.add(pnlActionPanel, BorderLayout.EAST);
        pnlMainContentPanel.add(pnlBottomPanel, BorderLayout.SOUTH);
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
        dialogHienTai = taoDialog("Thêm hạng mục công việc");
        JPanel pnlPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        pnlPanel.setBackground(Color.WHITE);
        pnlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        cmbCongViecDialog = new JComboBox<>();
        try {
            dsDichVu = dichVuDAO.getAllDichVu();
            for (DichVu dv : dsDichVu) {
                cmbCongViecDialog.addItem(dv.getMaDichVu() + " - " + dv.getTenDichVu());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        pnlPanel.add(taoLabel("Chọn công việc:"));
        pnlPanel.add(cmbCongViecDialog);

        JPanel pnlBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBtnPanel.setBackground(Color.WHITE);
        btnHuyCongViecDialog = taoButton("Hủy", maumenu);
        btnLuuCongViecDialog = taoButton("Thêm", btnchinh);
        pnlBtnPanel.add(btnHuyCongViecDialog);
        pnlBtnPanel.add(btnLuuCongViecDialog);
        btnHuyCongViecDialog.addActionListener(this);
        btnLuuCongViecDialog.addActionListener(this);

        dialogHienTai.add(pnlPanel, BorderLayout.CENTER);
        dialogHienTai.add(pnlBtnPanel, BorderLayout.SOUTH);
        dialogHienTai.setVisible(true);
    }

    private void moFormThemPhuTung() {
        dialogHienTai = taoDialog("Thêm phụ tùng vào phiếu");
        JPanel pnlPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        pnlPanel.setBackground(Color.WHITE);
        pnlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

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
        pnlPanel.add(taoLabel("Chọn phụ tùng:"));
        pnlPanel.add(cmbPhuTungDialog);
        pnlPanel.add(taoLabel("Số lượng:"));
        pnlPanel.add(txtSoLuongDialog);

        JPanel pnlBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBtnPanel.setBackground(Color.WHITE);
        btnHuyPhuTungDialog = taoButton("Hủy", maumenu);
        btnLuuPhuTungDialog = taoButton("Thêm", btnphu);
        pnlBtnPanel.add(btnHuyPhuTungDialog);
        pnlBtnPanel.add(btnLuuPhuTungDialog);
        btnHuyPhuTungDialog.addActionListener(this);
        btnLuuPhuTungDialog.addActionListener(this);

        dialogHienTai.add(pnlPanel, BorderLayout.CENTER);
        dialogHienTai.add(pnlBtnPanel, BorderLayout.SOUTH);
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
        PhieuSuaChua psc = new PhieuSuaChua(maPhieuSC, ngayHienTai, ngayHienTai, "Chờ xuất kho", txtBienSo.getText(), this.maKTV, maPhieuTiepNhan);

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
                        "Tạo phiếu sửa chữa thành công!\nMã phiếu: " + maPhieuSC +
                                "\nĐã gửi yêu cầu vật tư sang bộ phận Thủ kho chờ xuất kho.",
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

    private JTable taoTable(DefaultTableModel model) {
        JTable tblTable = new JTable(model);
        tblTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblTable.setRowHeight(36);
        tblTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblTable.setSelectionBackground(btnphu);
        tblTable.setSelectionForeground(chuchinh);
        tblTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblTable.getTableHeader().setBackground(btnchinh);
        return tblTable;
    }

    private JDialog taoDialog(String title) {
        JDialog winDialog = new JDialog(this, title, true);
        winDialog.setLayout(new BorderLayout());
        winDialog.setSize(480, 240);
        winDialog.setLocationRelativeTo(this);
        return winDialog;
    }

    private JLabel taoLabel(String text) {
        JLabel lblLabel = new JLabel(text);
        lblLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblLabel.setForeground(chuchinh);
        return lblLabel;
    }

    private JTextField taoTextFieldReadOnly() {
        JTextField txtField = new JTextField();
        txtField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtField.setEditable(false);
        txtField.setBackground(maunen);
        return txtField;
    }

    private JButton taoButton(String text, Color color) {
        JButton btnButton = new JButton(text);
        btnButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnButton.setForeground(chuchinh);
        btnButton.setBackground(color);
        btnButton.setFocusPainted(false);
        btnButton.setBorderPainted(false);
        btnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btnButton;
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
                tableModelCongViec.addRow(new Object[]{stt, dv.getMaDichVu(), dv.getTenDichVu(), df.format(dv.getDonGia())});
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
                    tableModelPhuTung.addRow(new Object[]{stt, pt.getMaPhuTung(), pt.getTenPhuTung(), sl, df.format(pt.getDonGiaBan()), df.format(thanhTien)});
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
