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

public class ChiTietKiemTraChanDoan extends JFrame implements ActionListener, MouseListener {
    private JPanel pnlHeaderPanel;
    private JPanel pnlLeftPanel;
    private JPanel pnlSearchPanel;
    private JPanel pnlRightPanel;
    private JPanel pnlInfoPanel;
    private JPanel pnlTextInputContainer;
    private JPanel pnlLoiPhatHien;
    private JPanel pnlNguyenNhan;
    private JPanel pnlDeXuat;
    private JPanel pnlActionPanel;
    private JSplitPane splitPane;
    private JTextField txtSearch;
    private JTextField txtBienSo;
    private JTextField txtKhachHang;
    private JTextField txtYeuCau;
    private JTextArea txaLoiPhatHien;
    private JTextArea txaNguyenNhan;
    private JTextArea txaDeXuat;
    private JButton btnTim;
    private JButton btnXacNhan;
    private JButton btnLamMoi;
    private JButton btnQuayLai;
    private JTable tblXe;
    private DefaultTableModel tableModelXe;
    private JScrollPane scrScrollPane;
    private JLabel lblTitle;
    private JLabel lblBienSo;
    private JLabel lblKhachHang;
    private JLabel lblYeuCau;
    private JLabel lblLoiPhatHien;
    private JLabel lblNguyenNhan;
    private JLabel lblDeXuat;

    private final PhieuTiepNhan_DAO phieuTiepNhanDAO = new PhieuTiepNhan_DAO();
    private final Xe_DAO xeDAO = new Xe_DAO();
    private final KhachHang_DAO khachHangDAO = new KhachHang_DAO();
    private List<PhieuTiepNhan> dsPhieuTiepNhan = new ArrayList<>();

    public ChiTietKiemTraChanDoan() {
        setTitle("Kiểm tra & Chẩn đoán xe - Kỹ thuật viên");
        setSize(1200, 780);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(244, 246, 248));
        setLayout(new BorderLayout(0, 10));

        pnlHeaderPanel = new JPanel(new BorderLayout());
        pnlHeaderPanel.setBackground(Color.WHITE);
        pnlHeaderPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        pnlHeaderPanel.setPreferredSize(new Dimension(1200, 75));

        lblTitle = new JLabel("KIỂM TRA & CHẨN ĐOÁN TÌNH TRẠNG XE");
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

        pnlLeftPanel = new JPanel(new BorderLayout(0, 12));
        pnlLeftPanel.setBackground(new Color(244, 246, 248));

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

        String[] columns = {"Mã tiếp nhận", "Biển số", "Khách hàng", "Trạng thái"};
        tableModelXe = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblXe = new JTable(tableModelXe);
        tblXe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblXe.setRowHeight(40);
        tblXe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblXe.setSelectionBackground(new Color(219, 234, 254));
        tblXe.setSelectionForeground(new Color(17, 24, 39));
        tblXe.setShowVerticalLines(false);
        tblXe.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblXe.getTableHeader().setForeground(new Color(17, 24, 39));
        tblXe.getTableHeader().setBackground(new Color(186, 230, 253));
        tblXe.getTableHeader().setPreferredSize(new Dimension(0, 42));

        scrScrollPane = new JScrollPane(tblXe);
        scrScrollPane.setBorder(BorderFactory.createLineBorder(new Color(186, 230, 253)));
        scrScrollPane.getViewport().setBackground(Color.WHITE);

        pnlLeftPanel.add(pnlSearchPanel, BorderLayout.NORTH);
        pnlLeftPanel.add(scrScrollPane, BorderLayout.CENTER);

        pnlRightPanel = new JPanel(new BorderLayout(0, 12));
        pnlRightPanel.setBackground(Color.WHITE);
        pnlRightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(186, 230, 253), 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        pnlInfoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlInfoPanel.setBackground(Color.WHITE);
        pnlInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(186, 230, 253)),
                "Thông tin xe & Yêu cầu của khách hàng",
                0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(17, 24, 39)
        ));

        txtBienSo = new JTextField();
        txtBienSo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBienSo.setEditable(false);
        txtBienSo.setBackground(new Color(244, 246, 248));
        txtBienSo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(186, 230, 253), 1),
                BorderFactory.createEmptyBorder(0, 8, 0, 8)
        ));

        txtKhachHang = new JTextField();
        txtKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtKhachHang.setEditable(false);
        txtKhachHang.setBackground(new Color(244, 246, 248));
        txtKhachHang.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(186, 230, 253), 1),
                BorderFactory.createEmptyBorder(0, 8, 0, 8)
        ));

        txtYeuCau = new JTextField();
        txtYeuCau.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtYeuCau.setEditable(false);
        txtYeuCau.setBackground(new Color(244, 246, 248));
        txtYeuCau.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(186, 230, 253), 1),
                BorderFactory.createEmptyBorder(0, 8, 0, 8)
        ));

        lblBienSo = new JLabel("Biển số xe:");
        lblBienSo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblBienSo.setForeground(new Color(17, 24, 39));

        lblKhachHang = new JLabel("Khách hàng:");
        lblKhachHang.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblKhachHang.setForeground(new Color(17, 24, 39));

        lblYeuCau = new JLabel("Yêu cầu KH:");
        lblYeuCau.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblYeuCau.setForeground(new Color(17, 24, 39));

        pnlInfoPanel.add(lblBienSo);
        pnlInfoPanel.add(txtBienSo);
        pnlInfoPanel.add(lblKhachHang);
        pnlInfoPanel.add(txtKhachHang);
        pnlInfoPanel.add(lblYeuCau);
        pnlInfoPanel.add(txtYeuCau);

        pnlTextInputContainer = new JPanel(new GridLayout(3, 1, 0, 8));
        pnlTextInputContainer.setBackground(Color.WHITE);

        txaLoiPhatHien = new JTextArea();
        txaLoiPhatHien.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txaLoiPhatHien.setLineWrap(true);
        txaLoiPhatHien.setWrapStyleWord(true);

        txaNguyenNhan = new JTextArea();
        txaNguyenNhan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txaNguyenNhan.setLineWrap(true);
        txaNguyenNhan.setWrapStyleWord(true);

        txaDeXuat = new JTextArea();
        txaDeXuat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txaDeXuat.setLineWrap(true);
        txaDeXuat.setWrapStyleWord(true);

        pnlLoiPhatHien = new JPanel(new BorderLayout(0, 4));
        pnlLoiPhatHien.setBackground(Color.WHITE);
        lblLoiPhatHien = new JLabel("Lỗi phát hiện:");
        lblLoiPhatHien.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblLoiPhatHien.setForeground(new Color(17, 24, 39));
        pnlLoiPhatHien.add(lblLoiPhatHien, BorderLayout.NORTH);
        pnlLoiPhatHien.add(new JScrollPane(txaLoiPhatHien), BorderLayout.CENTER);

        pnlNguyenNhan = new JPanel(new BorderLayout(0, 4));
        pnlNguyenNhan.setBackground(Color.WHITE);
        lblNguyenNhan = new JLabel("Nguyên nhân:");
        lblNguyenNhan.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNguyenNhan.setForeground(new Color(17, 24, 39));
        pnlNguyenNhan.add(lblNguyenNhan, BorderLayout.NORTH);
        pnlNguyenNhan.add(new JScrollPane(txaNguyenNhan), BorderLayout.CENTER);

        pnlDeXuat = new JPanel(new BorderLayout(0, 4));
        pnlDeXuat.setBackground(Color.WHITE);
        lblDeXuat = new JLabel("Đề xuất phương án sửa chữa:");
        lblDeXuat.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDeXuat.setForeground(new Color(17, 24, 39));
        pnlDeXuat.add(lblDeXuat, BorderLayout.NORTH);
        pnlDeXuat.add(new JScrollPane(txaDeXuat), BorderLayout.CENTER);

        pnlTextInputContainer.add(pnlLoiPhatHien);
        pnlTextInputContainer.add(pnlNguyenNhan);
        pnlTextInputContainer.add(pnlDeXuat);

        pnlActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlActionPanel.setBackground(Color.WHITE);

        btnLamMoi = new JButton("Nhập lại");
        btnLamMoi.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLamMoi.setForeground(new Color(17, 24, 39));
        btnLamMoi.setBackground(new Color(226, 232, 240));
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnXacNhan = new JButton("Xác nhận hoàn thành");
        btnXacNhan.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnXacNhan.setForeground(new Color(17, 24, 39));
        btnXacNhan.setBackground(new Color(186, 230, 253));
        btnXacNhan.setFocusPainted(false);
        btnXacNhan.setBorderPainted(false);
        btnXacNhan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnXacNhan.setPreferredSize(new Dimension(200, 40));

        pnlActionPanel.add(btnLamMoi);
        pnlActionPanel.add(btnXacNhan);

        pnlRightPanel.add(pnlInfoPanel, BorderLayout.NORTH);
        pnlRightPanel.add(pnlTextInputContainer, BorderLayout.CENTER);
        pnlRightPanel.add(pnlActionPanel, BorderLayout.SOUTH);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLeftPanel, pnlRightPanel);
        splitPane.setDividerLocation(480);
        splitPane.setDividerSize(6);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        splitPane.setBackground(new Color(244, 246, 248));

        add(splitPane, BorderLayout.CENTER);

        btnQuayLai.addActionListener(this);
        btnTim.addActionListener(this);
        btnXacNhan.addActionListener(this);
        btnLamMoi.addActionListener(this);
        tblXe.addMouseListener(this);

        loadDataToTable();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnQuayLai) {
            dispose();
        } else if (e.getSource() == btnTim) {
            loadDataToTable();
        } else if (e.getSource() == btnXacNhan) {
            xacNhanKiemTra();
        } else if (e.getSource() == btnLamMoi) {
            xoaRongForm();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == tblXe) {
            hienThiThongTinXeDuocChon();
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

    private void loadDataToTable() {
        try {
            tableModelXe.setRowCount(0);
            dsPhieuTiepNhan = phieuTiepNhanDAO.getAllPhieuTiepNhan();
            String tuKhoa = txtSearch.getText().trim().toLowerCase();
            List<PhieuTiepNhan> dsLoc = new ArrayList<>();

            for (PhieuTiepNhan ptn : dsPhieuTiepNhan) {
                if (!tuKhoa.isEmpty()
                        && !ptn.getBienSo().toLowerCase().contains(tuKhoa)
                        && !ptn.getMaPhieuTiepNhan().toLowerCase().contains(tuKhoa)) {
                    continue;
                }

                Xe xe = xeDAO.getXeByBienSo(ptn.getBienSo());
                KhachHang kh = xe != null ? khachHangDAO.getKhachHangByMa(xe.getMaKhachHang()) : null;

                tableModelXe.addRow(new Object[]{
                        ptn.getMaPhieuTiepNhan(),
                        ptn.getBienSo(),
                        kh != null ? kh.getHoTen() : "N/A",
                        ptn.getTrangThai()
                });
                dsLoc.add(ptn);
            }

            dsPhieuTiepNhan = dsLoc;
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
                    txtKhachHang.setText(kh != null ? kh.getHoTen() : "");
                } else {
                    txtKhachHang.setText("");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi tải thông tin khách hàng: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
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
        String noiDungChanDoan = txaLoiPhatHien.getText().trim()
                + " | Nguyên nhân: " + txaNguyenNhan.getText().trim()
                + " | Đề xuất: " + txaDeXuat.getText().trim();

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
}