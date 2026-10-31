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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import DAO.KhachHang_DAO;
import DAO.PhieuSuaChua_DAO;
import DAO.Xe_DAO;
import Entity.KhachHang;
import Entity.PhieuSuaChua;
import Entity.Xe;

public class KiemTraChatLuong extends JFrame implements ActionListener, MouseListener {
    private JPanel pnlHeaderPanel;
    private JPanel pnlLeftPanel;
    private JPanel pnlSearchPanel;
    private JPanel pnlRightPanel;
    private JPanel pnlInfoPanel;
    private JPanel pnlEvaluationPanel;
    private JPanel pnlCenterContainer;
    private JPanel pnlBtnPanel;
    private JPanel pnlBottomContainer;
    private JSplitPane splitPane;
    private JTextField txtSearch;
    private JTextField txtMaPhieu;
    private JTextField txtBienSo;
    private JTextField txtKhachHang;
    private JTextField txtKtvThucHien;
    private JRadioButton radDat;
    private JRadioButton radKhongDat;
    private ButtonGroup danhGiaGroup;
    private JTextArea txaGhiChu;
    private JButton btnTim;
    private JButton btnLuuKcs;
    private JButton btnLamMoi;
    private JButton btnQuayLai;
    private JTable tblXeKCS;
    private DefaultTableModel tableModelXeKCS;
    private JScrollPane scrXeKCS;
    private JScrollPane scrGhiChu;
    private JLabel lblTitle;
    private JLabel lblMaPhieu;
    private JLabel lblBienSo;
    private JLabel lblKhachHang;
    private JLabel lblKtvThucHien;

    private final PhieuSuaChua_DAO phieuSuaChuaDAO = new PhieuSuaChua_DAO();
    private final Xe_DAO xeDAO = new Xe_DAO();
    private final KhachHang_DAO khachHangDAO = new KhachHang_DAO();
    private List<PhieuSuaChua> dsPhieuSuaChua = new ArrayList<>();

    public KiemTraChatLuong() {
        setTitle("Kiểm tra chất lượng (KCS) - Garage Management");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(244, 246, 248));
        setLayout(new BorderLayout(0, 10));

        pnlHeaderPanel = new JPanel(new BorderLayout());
        pnlHeaderPanel.setBackground(Color.WHITE);
        pnlHeaderPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        lblTitle = new JLabel("KIỂM TRA CHẤT LƯỢNG XE SAU SỬA CHỮA (KCS)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(17, 24, 39));

        btnQuayLai = new JButton("Quay lại");
        btnQuayLai.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnQuayLai.setForeground(new Color(17, 24, 39));
        btnQuayLai.setBackground(new Color(219, 234, 254));
        btnQuayLai.setFocusPainted(false);
        btnQuayLai.setBorderPainted(false);
        btnQuayLai.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlHeaderPanel.add(lblTitle, BorderLayout.WEST);
        pnlHeaderPanel.add(btnQuayLai, BorderLayout.EAST);
        add(pnlHeaderPanel, BorderLayout.NORTH);

        pnlLeftPanel = new JPanel(new BorderLayout(0, 12));
        pnlLeftPanel.setBackground(new Color(244, 246, 248));
        pnlLeftPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 10));

        pnlSearchPanel = new JPanel(new BorderLayout(10, 0));
        pnlSearchPanel.setBackground(new Color(244, 246, 248));

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(186, 230, 253), 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        txtSearch.setPreferredSize(new Dimension(260, 40));

        btnTim = new JButton("Tìm xe");
        btnTim.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnTim.setForeground(new Color(17, 24, 39));
        btnTim.setBackground(new Color(186, 230, 253));
        btnTim.setFocusPainted(false);
        btnTim.setBorderPainted(false);
        btnTim.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlSearchPanel.add(txtSearch, BorderLayout.CENTER);
        pnlSearchPanel.add(btnTim, BorderLayout.EAST);

        String[] columns = {"Biển số", "Mã phiếu", "KTV phụ trách", "Trạng thái KCS"};
        tableModelXeKCS = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblXeKCS = new JTable(tableModelXeKCS);
        tblXeKCS.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblXeKCS.setRowHeight(36);
        tblXeKCS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblXeKCS.setSelectionBackground(new Color(219, 234, 254));
        tblXeKCS.setSelectionForeground(new Color(17, 24, 39));
        tblXeKCS.setShowVerticalLines(false);
        tblXeKCS.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblXeKCS.getTableHeader().setBackground(new Color(186, 230, 253));
        tblXeKCS.getTableHeader().setForeground(new Color(17, 24, 39));
        tblXeKCS.getTableHeader().setPreferredSize(new Dimension(0, 40));

        scrXeKCS = new JScrollPane(tblXeKCS);
        scrXeKCS.setBorder(BorderFactory.createLineBorder(new Color(186, 230, 253)));

        pnlLeftPanel.add(pnlSearchPanel, BorderLayout.NORTH);
        pnlLeftPanel.add(scrXeKCS, BorderLayout.CENTER);

        pnlRightPanel = new JPanel(new BorderLayout(0, 12));
        pnlRightPanel.setBackground(Color.WHITE);
        pnlRightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(186, 230, 253), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        pnlInfoPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        pnlInfoPanel.setBackground(Color.WHITE);

        txtMaPhieu = new JTextField();
        txtMaPhieu.setEditable(false);
        txtMaPhieu.setBackground(new Color(244, 246, 248));

        txtBienSo = new JTextField();
        txtBienSo.setEditable(false);
        txtBienSo.setBackground(new Color(244, 246, 248));

        txtKhachHang = new JTextField();
        txtKhachHang.setEditable(false);
        txtKhachHang.setBackground(new Color(244, 246, 248));

        txtKtvThucHien = new JTextField();
        txtKtvThucHien.setEditable(false);
        txtKtvThucHien.setBackground(new Color(244, 246, 248));

        txtMaPhieu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBienSo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtKtvThucHien.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        lblMaPhieu = new JLabel("Mã phiếu SC:");
        lblBienSo = new JLabel("Biển số xe:");
        lblKhachHang = new JLabel("Khách hàng:");
        lblKtvThucHien = new JLabel("KTV thi công:");

        lblMaPhieu.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblBienSo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblKhachHang.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblKtvThucHien.setFont(new Font("Segoe UI", Font.BOLD, 13));

        pnlInfoPanel.add(lblMaPhieu);
        pnlInfoPanel.add(txtMaPhieu);
        pnlInfoPanel.add(lblBienSo);
        pnlInfoPanel.add(txtBienSo);
        pnlInfoPanel.add(lblKhachHang);
        pnlInfoPanel.add(txtKhachHang);
        pnlInfoPanel.add(lblKtvThucHien);
        pnlInfoPanel.add(txtKtvThucHien);

        pnlEvaluationPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlEvaluationPanel.setBackground(Color.WHITE);

        radDat = new JRadioButton("ĐẠT (Chuyển chờ bàn giao)");
        radDat.setForeground(new Color(34, 197, 94));
        radDat.setBackground(Color.WHITE);
        radDat.setFont(new Font("Segoe UI", Font.BOLD, 13));
        radDat.setSelected(true);

        radKhongDat = new JRadioButton("KHÔNG ĐẠT (Yêu cầu sửa lại)");
        radKhongDat.setForeground(new Color(220, 38, 38));
        radKhongDat.setBackground(Color.WHITE);
        radKhongDat.setFont(new Font("Segoe UI", Font.BOLD, 13));

        danhGiaGroup = new ButtonGroup();
        danhGiaGroup.add(radDat);
        danhGiaGroup.add(radKhongDat);

        pnlEvaluationPanel.add(radDat);
        pnlEvaluationPanel.add(radKhongDat);

        pnlCenterContainer = new JPanel(new BorderLayout(0, 10));
        pnlCenterContainer.setBackground(Color.WHITE);
        pnlCenterContainer.add(pnlInfoPanel, BorderLayout.NORTH);
        pnlCenterContainer.add(pnlEvaluationPanel, BorderLayout.CENTER);

        txaGhiChu = new JTextArea(5, 20);
        txaGhiChu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txaGhiChu.setLineWrap(true);
        txaGhiChu.setWrapStyleWord(true);
        txaGhiChu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Ghi chú kiểm tra"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        scrGhiChu = new JScrollPane(txaGhiChu);

        pnlBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlBtnPanel.setBackground(Color.WHITE);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLamMoi.setForeground(new Color(17, 24, 39));
        btnLamMoi.setBackground(new Color(226, 232, 240));
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLuuKcs = new JButton("Lưu kết quả KCS");
        btnLuuKcs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLuuKcs.setForeground(Color.WHITE);
        btnLuuKcs.setBackground(new Color(34, 197, 94));
        btnLuuKcs.setFocusPainted(false);
        btnLuuKcs.setBorderPainted(false);
        btnLuuKcs.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLuuKcs.setPreferredSize(new Dimension(170, 40));

        pnlBtnPanel.add(btnLamMoi);
        pnlBtnPanel.add(btnLuuKcs);

        pnlBottomContainer = new JPanel(new BorderLayout(0, 10));
        pnlBottomContainer.setBackground(Color.WHITE);
        pnlBottomContainer.add(scrGhiChu, BorderLayout.CENTER);
        pnlBottomContainer.add(pnlBtnPanel, BorderLayout.SOUTH);

        pnlRightPanel.add(pnlCenterContainer, BorderLayout.NORTH);
        pnlRightPanel.add(pnlBottomContainer, BorderLayout.CENTER);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLeftPanel, pnlRightPanel);
        splitPane.setDividerLocation(460);
        splitPane.setDividerSize(6);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setBackground(new Color(244, 246, 248));

        add(splitPane, BorderLayout.CENTER);

        btnQuayLai.addActionListener(this);
        btnTim.addActionListener(this);
        btnLuuKcs.addActionListener(this);
        btnLamMoi.addActionListener(this);
        tblXeKCS.addMouseListener(this);

        loadDanhSachKCS();
        setVisible(true);
    }

    private void loadDanhSachKCS() {
        try {
            tableModelXeKCS.setRowCount(0);
            List<PhieuSuaChua> dsTatCa = phieuSuaChuaDAO.getAllPhieuSuaChua();
            String tuKhoa = txtSearch.getText().trim().toLowerCase();
            dsPhieuSuaChua = new ArrayList<>();

            for (PhieuSuaChua psc : dsTatCa) {
                String bienSo = psc.getBienSo() == null ? "" : psc.getBienSo().toLowerCase();
                String maPhieu = psc.getMaPhieuSuaChua() == null ? "" : psc.getMaPhieuSuaChua().toLowerCase();
                String maKtv = psc.getMaNhanVien() == null ? "" : psc.getMaNhanVien().toLowerCase();
                String trangThai = psc.getTrangThai() == null ? "" : psc.getTrangThai().toLowerCase();

                if (bienSo.contains(tuKhoa) || maPhieu.contains(tuKhoa) || maKtv.contains(tuKhoa) || trangThai.contains(tuKhoa)) {
                    dsPhieuSuaChua.add(psc);
                    tableModelXeKCS.addRow(new Object[]{
                            psc.getBienSo(),
                            psc.getMaPhieuSuaChua(),
                            psc.getMaNhanVien(),
                            psc.getTrangThai()
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách KCS: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hienThiChiTietXeDuocChon() {
        int row = tblXeKCS.getSelectedRow();

        if (row != -1 && row < dsPhieuSuaChua.size()) {
            PhieuSuaChua psc = dsPhieuSuaChua.get(row);
            txtMaPhieu.setText(psc.getMaPhieuSuaChua());
            txtBienSo.setText(psc.getBienSo());
            txtKtvThucHien.setText(psc.getMaNhanVien());
            txaGhiChu.setText(psc.getGhiChuKCS() != null ? psc.getGhiChuKCS() : "");
            if ("Không đạt".equals(psc.getKetQuaKCS())) {
                radKhongDat.setSelected(true);
            } else {
                radDat.setSelected(true);
            }
            try {
                Xe xe = xeDAO.getXeByBienSo(psc.getBienSo());

                if (xe != null) {
                    KhachHang kh = khachHangDAO.getKhachHangByMa(xe.getMaKhachHang());
                    txtKhachHang.setText(kh != null ? kh.getHoTen() : "");
                } else {
                    txtKhachHang.setText("");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi tải thông tin khách hàng: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void luuKetQuaKCS() {
        int row = tblXeKCS.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn xe cần kiểm tra chất lượng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maPhieu = txtMaPhieu.getText().trim();
        if (maPhieu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu sửa chữa hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Xác định kết quả và trạng thái dựa trên RadioButton
        String ketQuaKCS = radDat.isSelected() ? "Đạt" : "Không đạt";
        String trangThaiMoi = radDat.isSelected() ? "Chờ bàn giao" : "Đang sửa chữa"; // Hoặc trạng thái tùy logic nhóm bạn
        String ghiChuKCS = txaGhiChu.getText().trim();

        // Lấy ngày hiện tại làm ngày kiểm tra KCS
        java.sql.Date ngayKiemTra = new java.sql.Date(System.currentTimeMillis());

        try {
            boolean ok = phieuSuaChuaDAO.capNhatKCS(maPhieu, ketQuaKCS, ghiChuKCS, ngayKiemTra, trangThaiMoi);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Lưu kết quả KCS thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadDanhSachKCS();
                xoaForm();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể cập nhật kết quả KCS!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi lưu CSDL: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaForm() {
        txtMaPhieu.setText("");
        txtBienSo.setText("");
        txtKhachHang.setText("");
        txtKtvThucHien.setText("");
        txaGhiChu.setText("");
        radDat.setSelected(true);
        tblXeKCS.clearSelection();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnQuayLai) {
            dispose();
        } else if (e.getSource() == btnTim) {
            loadDanhSachKCS();
        } else if (e.getSource() == btnLuuKcs) {
            luuKetQuaKCS();
        } else if (e.getSource() == btnLamMoi) {
            xoaForm();
            txtSearch.setText("");
            loadDanhSachKCS();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == tblXeKCS) {
            hienThiChiTietXeDuocChon();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        new KiemTraChatLuong();
    }
}