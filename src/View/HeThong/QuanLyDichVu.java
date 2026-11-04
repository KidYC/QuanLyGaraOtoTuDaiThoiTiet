package View.HeThong;

import DAO.DichVu_DAO;
import Entity.DichVu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class QuanLyDichVu extends JFrame implements ActionListener, MouseListener {
    private JPanel pnlHeader, pnlCenter, pnlForm, pnlTableContainer, pnlButtons, pnlSearchPanel;
    private JLabel lblTitle, lblMaDV, lblTenDV, lblDonGia, lblMoTa, lblBaoHanh, lblSearch;
    private JTextField txtMaDV, txtTenDV, txtDonGia, txtBaoHanh, txtSearch;
    private JTextArea txtMoTa;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnQuayLai, btnTim;
    private JTable tblDichVu;
    private DefaultTableModel tableModelDichVu;
    private JScrollPane scrTable, scrMoTa;

    private final DichVu_DAO dichVuDAO = new DichVu_DAO();
    private List<DichVu> dsDichVu = new ArrayList<>();
    private final DecimalFormat df = new DecimalFormat("#,###");

    public QuanLyDichVu() {
        setTitle("Quản lý danh mục dịch vụ - Garage Management");
        setSize(1150, 680);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(241, 245, 249));
        setLayout(new BorderLayout(0, 10));

        // Header Panel
        pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        pnlHeader.setPreferredSize(new Dimension(1150, 75));

        lblTitle = new JLabel("QUẢN LÝ DANH MỤC DỊCH VỤ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(15, 23, 42));

        btnQuayLai = new JButton("Quay lại");
        btnQuayLai.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnQuayLai.setBackground(new Color(219, 234, 254));
        btnQuayLai.setFocusPainted(false);
        btnQuayLai.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(btnQuayLai, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // Center Panel (Chia 2 phần: Trái là Form nhập, Phải là Bảng + Tìm kiếm)
        pnlCenter = new JPanel(new BorderLayout(15, 0));
        pnlCenter.setBackground(new Color(241, 245, 249));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

        // 1. Form Panel (Bên trái)
        pnlForm = new JPanel(new GridLayout(6, 2, 10, 12));
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        pnlForm.setPreferredSize(new Dimension(380, 0));

        lblMaDV = new JLabel("Mã dịch vụ:");
        lblMaDV.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtMaDV = new JTextField();
        txtMaDV.setEditable(false);
        txtMaDV.setBackground(new Color(236, 240, 241));

        lblTenDV = new JLabel("Tên dịch vụ:");
        lblTenDV.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtTenDV = new JTextField();

        lblDonGia = new JLabel("Đơn giá chuẩn (VNĐ):");
        lblDonGia.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtDonGia = new JTextField();

        lblBaoHanh = new JLabel("Bảo hành (tháng):");
        lblBaoHanh.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtBaoHanh = new JTextField();

        lblMoTa = new JLabel("Mô tả chi tiết:");
        lblMoTa.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtMoTa = new JTextArea(3, 20);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        scrMoTa = new JScrollPane(txtMoTa);

        pnlButtons = new JPanel(new GridLayout(2, 2, 8, 8));
        pnlButtons.setBackground(Color.WHITE);

        btnThem = new JButton("Thêm mới");
        btnThem.setBackground(new Color(22, 163, 74));
        btnThem.setForeground(Color.WHITE);
        btnThem.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnThem.setFocusPainted(false);

        btnSua = new JButton("Cập nhật");
        btnSua.setBackground(new Color(37, 99, 235));
        btnSua.setForeground(Color.WHITE);
        btnSua.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSua.setFocusPainted(false);

        btnXoa = new JButton("Xóa");
        btnXoa.setBackground(new Color(220, 38, 38));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnXoa.setFocusPainted(false);

        btnLamMoi = new JButton("Làm mới form");
        btnLamMoi.setBackground(new Color(226, 232, 240));
        btnLamMoi.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLamMoi.setFocusPainted(false);

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);

        pnlForm.add(lblMaDV); pnlForm.add(txtMaDV);
        pnlForm.add(lblTenDV); pnlForm.add(txtTenDV);
        pnlForm.add(lblDonGia); pnlForm.add(txtDonGia);
        pnlForm.add(lblBaoHanh); pnlForm.add(txtBaoHanh);
        pnlForm.add(lblMoTa); pnlForm.add(scrMoTa);
        pnlForm.add(new JLabel()); pnlForm.add(pnlButtons);

        // 2. Table & Search Panel (Bên phải)
        pnlTableContainer = new JPanel(new BorderLayout(0, 10));
        pnlTableContainer.setBackground(new Color(241, 245, 249));

        pnlSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlSearchPanel.setBackground(new Color(241, 245, 249));

        lblSearch = new JLabel("Tìm kiếm:");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(250, 35));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        btnTim = new JButton("Tìm");
        btnTim.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnTim.setForeground(Color.WHITE);
        btnTim.setBackground(new Color(37, 99, 235));
        btnTim.setFocusPainted(false);

        pnlSearchPanel.add(lblSearch);
        pnlSearchPanel.add(txtSearch);
        pnlSearchPanel.add(btnTim);

        // Cập nhật đủ 5 cột khớp với cơ sở dữ liệu
        String[] columns = {"Mã DV", "Tên dịch vụ", "Đơn giá (VNĐ)", "Mô tả", "Bảo hành (tháng)"};
        tableModelDichVu = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tblDichVu = new JTable(tableModelDichVu);
        tblDichVu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblDichVu.setRowHeight(36);
        tblDichVu.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblDichVu.getTableHeader().setBackground(new Color(30, 41, 59));
        tblDichVu.getTableHeader().setForeground(Color.WHITE);

        scrTable = new JScrollPane(tblDichVu);
        scrTable.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)));

        pnlTableContainer.add(pnlSearchPanel, BorderLayout.NORTH);
        pnlTableContainer.add(scrTable, BorderLayout.CENTER);

        pnlCenter.add(pnlForm, BorderLayout.WEST);
        pnlCenter.add(pnlTableContainer, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // Đăng ký sự kiện
        btnQuayLai.addActionListener(this);
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnTim.addActionListener(this);
        tblDichVu.addMouseListener(this);

        loadTableData();
        taoMaTuDong();
        setVisible(true);
    }

    private void loadTableData() {
        try {
            tableModelDichVu.setRowCount(0);
            dsDichVu = dichVuDAO.getAllDichVu();
            for (DichVu dv : dsDichVu) {
                tableModelDichVu.addRow(new Object[]{
                        dv.getMaDichVu(),
                        dv.getTenDichVu(),
                        df.format(dv.getDonGiaHienTai()),
                        dv.getMoTa(),
                        dv.getThoiGianBaoHanh()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu dịch vụ: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void taoMaTuDong() {
        try {
            txtMaDV.setText(dichVuDAO.getMaDichVuTuDong());
        } catch (SQLException ex) {
            txtMaDV.setText("DV001");
        }
    }

    private void xoaTrangForm() {
        taoMaTuDong();
        txtTenDV.setText("");
        txtDonGia.setText("");
        txtBaoHanh.setText("");
        txtMoTa.setText("");
        tblDichVu.clearSelection();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnQuayLai) {
            dispose();
        } else if (source == btnLamMoi) {
            xoaTrangForm();
            loadTableData();
        } else if (source == btnThem) {
            xuLyThem();
        } else if (source == btnSua) {
            xuLySua();
        } else if (source == btnXoa) {
            xuLyXoa();
        } else if (source == btnTim) {
            xuLyTimKiem();
        }
    }

    private void xuLyThem() {
        String ma = txtMaDV.getText().trim();
        String ten = txtTenDV.getText().trim();
        String giaStr = txtDonGia.getText().trim().replace(",", "").replace(".", "");
        String bhStr = txtBaoHanh.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (ten.isEmpty() || giaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên dịch vụ và đơn giá!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double gia = Double.parseDouble(giaStr);
            int baoHanh = bhStr.isEmpty() ? 0 : Integer.parseInt(bhStr);

            if (gia < 0 || baoHanh < 0) {
                JOptionPane.showMessageDialog(this, "Đơn giá hoặc thời gian bảo hành không được âm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            DichVu dv = new DichVu(ma, ten, gia, moTa, baoHanh);
            if (dichVuDAO.themDichVu(dv)) {
                JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công!");
                loadTableData();
                xoaTrangForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Đơn giá và bảo hành phải là số hợp lệ!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLySua() {
        int row = tblDichVu.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần sửa trên bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ma = txtMaDV.getText().trim();
        String ten = txtTenDV.getText().trim();
        String giaStr = txtDonGia.getText().trim().replace(",", "").replace(".", "");
        String bhStr = txtBaoHanh.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (ten.isEmpty() || giaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống tên và đơn giá!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double gia = Double.parseDouble(giaStr);
            int baoHanh = bhStr.isEmpty() ? 0 : Integer.parseInt(bhStr);

            DichVu dv = new DichVu(ma, ten, gia, moTa, baoHanh);
            if (dichVuDAO.suaDichVu(dv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật dịch vụ thành công!");
                loadTableData();
                xoaTrangForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Đơn giá và bảo hành phải là số hợp lệ!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyXoa() {
        int row = tblDichVu.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần xóa trên bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ma = txtMaDV.getText().trim();
        int opt = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa dịch vụ [" + ma + "] không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            try {
                if (dichVuDAO.xoaDichVu(ma)) {
                    JOptionPane.showMessageDialog(this, "Xóa dịch vụ thành công!");
                    loadTableData();
                    xoaTrangForm();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Không thể xóa vì dịch vụ này đã phát sinh trong các phiếu sửa chữa trước đây!", "Lỗi ràng buộc", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xuLyTimKiem() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            loadTableData();
            return;
        }

        for (int i = 0; i < tableModelDichVu.getRowCount(); i++) {
            String ma = tableModelDichVu.getValueAt(i, 0).toString().toLowerCase();
            String ten = tableModelDichVu.getValueAt(i, 1).toString().toLowerCase();

            if (ma.contains(keyword) || ten.contains(keyword)) {
                tblDichVu.setRowSelectionInterval(i, i);
                tblDichVu.scrollRectToVisible(tblDichVu.getCellRect(i, 0, true));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Không tìm thấy dịch vụ phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblDichVu.getSelectedRow();
        if (row != -1 && row < dsDichVu.size()) {
            DichVu dv = dsDichVu.get(row);
            txtMaDV.setText(dv.getMaDichVu());
            txtTenDV.setText(dv.getTenDichVu());
            txtDonGia.setText(String.valueOf((long) dv.getDonGiaHienTai()));
            txtBaoHanh.setText(String.valueOf(dv.getThoiGianBaoHanh()));
            txtMoTa.setText(dv.getMoTa() != null ? dv.getMoTa() : "");
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        new QuanLyDichVu();
    }
}