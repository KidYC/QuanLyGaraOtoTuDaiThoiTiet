
package View.KyThuatVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import DAO.KhachHang_DAO;
import DAO.PhieuTiepNhan_DAO;
import DAO.Xe_DAO;
import Entity.KhachHang;
import Entity.PhieuTiepNhan;
import Entity.Xe;

public class ChiTietKiemTraChanDoan extends JFrame {
    private JPanel pnlHeaderPanel;
    private JSplitPane splitPane;
    private JPanel pnlLeftPanel;
    private JTextField txtSearch;
    private JButton btnTim;
    private JTable tblXe;
    private DefaultTableModel tableModelXe;
    private JPanel pnlRightPanel;
    private JTextField txtBienSo;
    private JTextField txtKhachHang;
    private JTextField txtYeuCau;
    private JTextArea txaLoiPhatHien;
    private JTextArea txaNguyenNhan;
    private JTextArea txaDeXuat;

    private JButton btnXacNhan;
    private JButton btnLamMoi;
    private JButton btnQuayLai;

    private final Color maunen = new Color(244, 246, 248);
    private final Color maumenu = new Color(226, 232, 240);
    private final Color btnchinh = new Color(186, 230, 253);
    private final Color btnphu = new Color(219, 234, 254);
    private final Color chuchinh = new Color(17, 24, 39);
    private final Color maucotbang = new Color(186, 230, 253);

    // Khai báo DAO
    private final PhieuTiepNhan_DAO phieuTiepNhanDAO = new PhieuTiepNhan_DAO();
    private final Xe_DAO xeDAO = new Xe_DAO();
    private final KhachHang_DAO khachHangDAO = new KhachHang_DAO();
    private List<PhieuTiepNhan> dsPhieuTiepNhan = new ArrayList<>();

    public ChiTietKiemTraChanDoan() {
        setTitle("Kiểm tra & Chẩn đoán xe - Kỹ thuật viên");
        setSize(1200, 780);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(maunen);
        setLayout(new BorderLayout(0, 10));

        taoHeader();
        taoContent();
        loadDataToTable();
        setVisible(true);
    }

    private void taoHeader() {
        pnlHeaderPanel = new JPanel(new BorderLayout());
        pnlHeaderPanel.setBackground(Color.WHITE);
        pnlHeaderPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        pnlHeaderPanel.setPreferredSize(new Dimension(1200, 75));

        JLabel lblTitle = new JLabel("KIỂM TRA & CHẨN ĐOÁN TÌNH TRẠNG XE");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(chuchinh);

        btnQuayLai = taoButton("Quay lại", btnphu);
        btnQuayLai.setPreferredSize(new Dimension(110, 40));
        btnQuayLai.addActionListener(e -> dispose());

        pnlHeaderPanel.add(lblTitle, BorderLayout.WEST);
        pnlHeaderPanel.add(btnQuayLai, BorderLayout.EAST);
        add(pnlHeaderPanel, BorderLayout.NORTH);
    }

    private void taoContent() {
        taoDanhSachXePanel();
        taoFormKiemTraPanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLeftPanel, pnlRightPanel);
        splitPane.setDividerLocation(480);
        splitPane.setDividerSize(6);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        splitPane.setBackground(maunen);

        add(splitPane, BorderLayout.CENTER);
    }

    private void taoDanhSachXePanel() {
        pnlLeftPanel = new JPanel(new BorderLayout(0, 12));
        pnlLeftPanel.setBackground(maunen);

        JPanel pnlSearchPanel = new JPanel(new BorderLayout(10, 0));
        pnlSearchPanel.setBackground(maunen);

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        txtSearch.setPreferredSize(new Dimension(260, 40));

        btnTim = taoButton("Tìm xe", btnchinh);
        btnTim.addActionListener(e -> loadDataToTable());

        pnlSearchPanel.add(txtSearch, BorderLayout.CENTER);
        pnlSearchPanel.add(btnTim, BorderLayout.EAST);

        String[] columns = {"Mã tiếp nhận", "Biển số", "Khách hàng", "Trạng thái"};
        tableModelXe = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tblXe = new JTable(tableModelXe);
        tblXe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblXe.setRowHeight(40);
        tblXe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblXe.setSelectionBackground(btnphu);
        tblXe.setSelectionForeground(chuchinh);
        tblXe.setShowVerticalLines(false);
        tblXe.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblXe.getTableHeader().setForeground(chuchinh);
        tblXe.getTableHeader().setBackground(btnchinh);
        tblXe.getTableHeader().setPreferredSize(new Dimension(0, 42));

        JScrollPane scrScrollPane = new JScrollPane(tblXe);
        scrScrollPane.setBorder(BorderFactory.createLineBorder(maucotbang));
        scrScrollPane.getViewport().setBackground(Color.WHITE);

        pnlLeftPanel.add(pnlSearchPanel, BorderLayout.NORTH);
        pnlLeftPanel.add(scrScrollPane, BorderLayout.CENTER);

        tblXe.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hienThiThongTinXeDuocChon();
            }
        });
    }

    private void taoFormKiemTraPanel() {
        pnlRightPanel = new JPanel(new BorderLayout(0, 12));
        pnlRightPanel.setBackground(Color.WHITE);
        pnlRightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        JPanel pnlInfoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlInfoPanel.setBackground(Color.WHITE);
        pnlInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(maucotbang),
                "Thông tin xe & Yêu cầu của khách hàng",
                0, 0, new Font("Segoe UI", Font.BOLD, 14), chuchinh
        ));

        txtBienSo = taoTextFieldReadOnly();
        txtKhachHang = taoTextFieldReadOnly();
        txtYeuCau = taoTextFieldReadOnly();

        pnlInfoPanel.add(taoLabel("Biển số xe:"));
        pnlInfoPanel.add(txtBienSo);
        pnlInfoPanel.add(taoLabel("Khách hàng:"));
        pnlInfoPanel.add(txtKhachHang);
        pnlInfoPanel.add(taoLabel("Yêu cầu KH:"));
        pnlInfoPanel.add(txtYeuCau);

        JPanel pnlTextInputContainer = new JPanel(new GridLayout(3, 1, 0, 8));
        pnlTextInputContainer.setBackground(Color.WHITE);

        txaLoiPhatHien = taoTextArea();
        txaNguyenNhan = taoTextArea();
        txaDeXuat = taoTextArea();

        pnlTextInputContainer.add(taoBoxTextArea("Lỗi phát hiện:", txaLoiPhatHien));
        pnlTextInputContainer.add(taoBoxTextArea("Nguyên nhân:", txaNguyenNhan));
        pnlTextInputContainer.add(taoBoxTextArea("Đề xuất phương án sửa chữa:", txaDeXuat));

        JPanel pnlActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlActionPanel.setBackground(Color.WHITE);

        btnLamMoi = taoButton("Nhập lại", maumenu);
        btnXacNhan = taoButton("Xác nhận hoàn thành", btnchinh);
        btnXacNhan.setPreferredSize(new Dimension(200, 40));

        pnlActionPanel.add(btnLamMoi);
        pnlActionPanel.add(btnXacNhan);

        pnlRightPanel.add(pnlInfoPanel, BorderLayout.NORTH);
        pnlRightPanel.add(pnlTextInputContainer, BorderLayout.CENTER);
        pnlRightPanel.add(pnlActionPanel, BorderLayout.SOUTH);

        btnXacNhan.addActionListener(e -> xacNhanKiemTra());
        btnLamMoi.addActionListener(e -> xoaRongForm());
    }

    private void loadDataToTable() {
        try {
            tableModelXe.setRowCount(0);
            dsPhieuTiepNhan = phieuTiepNhanDAO.getAllPhieuTiepNhan();
            String tuKhoa = txtSearch.getText().trim().toLowerCase();

            for (PhieuTiepNhan ptn : dsPhieuTiepNhan) {
                if (!tuKhoa.isEmpty() && !ptn.getBienSo().toLowerCase().contains(tuKhoa) && !ptn.getMaPhieuTiepNhan().toLowerCase().contains(tuKhoa)) {
                    continue;
                }
                Xe xe = xeDAO.getXeByBienSo(ptn.getBienSo());
                KhachHang kh = (xe != null) ? khachHangDAO.getKhachHangByMa(xe.getMaKhachHang()) : null;

                tableModelXe.addRow(new Object[]{
                        ptn.getMaPhieuTiepNhan(),
                        ptn.getBienSo(),
                        (kh != null) ? kh.getHoTen() : "N/A",
                        ptn.getTrangThai()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu từ SQL: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hienThiThongTinXeDuocChon() {
        int row = tblXe.getSelectedRow();
        if (row != -1 && row < dsPhieuTiepNhan.size()) {
            PhieuTiepNhan ptn = dsPhieuTiepNhan.get(row);
            txtBienSo.setText(ptn.getBienSo());
            txtYeuCau.setText(ptn.getYeuCauKH());

            try {
                Xe xe = xeDAO.getXeByBienSo(ptn.getBienSo());
                if (xe != null) {
                    KhachHang kh = khachHangDAO.getKhachHangByMa(xe.getMaKhachHang());
                    txtKhachHang.setText((kh != null) ? kh.getHoTen() : "");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void xacNhanKiemTra() {
        int row = tblXe.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn xe cần kiểm tra từ danh sách!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txaLoiPhatHien.getText().trim().isEmpty() || txaDeXuat.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin Lỗi phát hiện và Đề xuất phương án!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maPhieu = tableModelXe.getValueAt(row, 0).toString();
        String noiDungChanDoan = txaLoiPhatHien.getText().trim() + " | Nguyên nhân: " + txaNguyenNhan.getText().trim() + " | Đề xuất: " + txaDeXuat.getText().trim();

        try {
            boolean thanhCong = phieuTiepNhanDAO.capNhatChanDoan(maPhieu, noiDungChanDoan, "Đã chẩn đoán");
            if (thanhCong) {
                JOptionPane.showMessageDialog(this, "Lưu kết quả kiểm tra & chẩn đoán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadDataToTable();
                xoaRongForm();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật CSDL: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaRongForm() {
        txtBienSo.setText("");
        txtKhachHang.setText("");
        txtYeuCau.setText("");
        txaLoiPhatHien.setText("");
        txaNguyenNhan.setText("");
        txaDeXuat.setText("");
        tblXe.clearSelection();
    }

    private JPanel taoBoxTextArea(String labelText, JTextArea textArea) {
        JPanel pnlPanel = new JPanel(new BorderLayout(0, 4));
        pnlPanel.setBackground(Color.WHITE);
        pnlPanel.add(taoLabel(labelText), BorderLayout.NORTH);
        pnlPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return pnlPanel;
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
        txtField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(maucotbang, 1),
                BorderFactory.createEmptyBorder(0, 8, 0, 8)
        ));
        return txtField;
    }

    private JTextArea taoTextArea() {
        JTextArea txaArea = new JTextArea();
        txaArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txaArea.setLineWrap(true);
        txaArea.setWrapStyleWord(true);
        return txaArea;
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

//    public static void main(String[] args) {
//        new ChiTietKiemTraChanDoan();
//    }
}