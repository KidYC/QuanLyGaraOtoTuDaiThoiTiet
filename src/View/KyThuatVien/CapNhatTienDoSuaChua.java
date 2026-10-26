package View.KyThuatVien;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
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

public class CapNhatTienDoSuaChua extends JFrame implements ActionListener, MouseListener {
    private JPanel pnlHeaderPanel;
    private JSplitPane splitPane;
    private JPanel pnlLeftPanel;
    private JTextField txtSearch;
    private JButton btnTim;
    private JTable tblLenhSC;
    private DefaultTableModel tableModelLenhSC;
    private JPanel pnlRightPanel;
    private JTextField txtMaLenh;
    private JTextField txtBienSo;
    private JTextField txtKhachHang;
    private JComboBox<String> cmbTrangThai;
    private JComboBox<String> cmbTienDo;
    private JProgressBar prgBar;
    private JTextArea txaGhiChu;
    private JButton btnLuuTienDo;
    private JButton btnLamMoi;
    private JButton btnQuayLai;

    private final Color maunen = new Color(244, 246, 248);
    private final Color maumenu = new Color(226, 232, 240);
    private final Color btnchinh = new Color(186, 230, 253);
    private final Color btnphu = new Color(219, 234, 254);
    private final Color chuchinh = new Color(17, 24, 39);
    private final Color maucotbang = new Color(186, 230, 253);

    private final PhieuSuaChua_DAO phieuSuaChuaDAO = new PhieuSuaChua_DAO();
    private final Xe_DAO xeDAO = new Xe_DAO();
    private final KhachHang_DAO khachHangDAO = new KhachHang_DAO();
    private List<PhieuSuaChua> dsPhieuSuaChua = new ArrayList<>();

    public CapNhatTienDoSuaChua() {
        setTitle("Công việc được phân công & Tiến độ - Garage Management");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(maunen);
        setLayout(new BorderLayout(0, 10));

        pnlHeaderPanel = new JPanel(new BorderLayout());
        pnlHeaderPanel.setBackground(Color.WHITE);
        pnlHeaderPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        pnlHeaderPanel.setPreferredSize(new Dimension(1200, 75));

        JLabel lblTitle = new JLabel("CÔNG VIỆC ĐƯỢC PHÂN CÔNG & CẬP NHẬT TIẾN ĐỘ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(chuchinh);

        btnQuayLai = new JButton("Quay lại");
        btnQuayLai.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnQuayLai.setBackground(btnphu);
        btnQuayLai.setPreferredSize(new Dimension(110, 40));

        pnlHeaderPanel.add(lblTitle, BorderLayout.WEST);
        pnlHeaderPanel.add(btnQuayLai, BorderLayout.EAST);
        add(pnlHeaderPanel, BorderLayout.NORTH);

        pnlLeftPanel = new JPanel(new BorderLayout(0, 12));
        pnlLeftPanel.setBackground(maunen);

        JPanel pnlSearchPanel = new JPanel(new BorderLayout(10, 0));
        pnlSearchPanel.setBackground(maunen);

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btnTim = new JButton("Tìm lệnh");
        btnTim.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnTim.setBackground(btnchinh);

        pnlSearchPanel.add(txtSearch, BorderLayout.CENTER);
        pnlSearchPanel.add(btnTim, BorderLayout.EAST);

        String[] columns = {"Mã lệnh", "Biển số", "Trạng thái"};
        tableModelLenhSC = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLenhSC = new JTable(tableModelLenhSC);
        tblLenhSC.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblLenhSC.setRowHeight(36);
        tblLenhSC.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        pnlLeftPanel.add(pnlSearchPanel, BorderLayout.NORTH);
        pnlLeftPanel.add(new JScrollPane(tblLenhSC), BorderLayout.CENTER);

        pnlRightPanel = new JPanel(new BorderLayout(0, 12));
        pnlRightPanel.setBackground(Color.WHITE);
        pnlRightPanel.setBorder(BorderFactory.createEmptyBorder(15, 18, 15, 18));

        JPanel pnlInfoPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        pnlInfoPanel.setBackground(Color.WHITE);
        pnlInfoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin lệnh sửa chữa"));

        txtMaLenh = new JTextField();
        txtMaLenh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMaLenh.setEditable(false);

        txtBienSo = new JTextField();
        txtBienSo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBienSo.setEditable(false);

        txtKhachHang = new JTextField();
        txtKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtKhachHang.setEditable(false);

        JLabel lblMaLenh = new JLabel("Mã lệnh SC:");
        lblMaLenh.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel lblBienSo = new JLabel("Biển số xe:");
        lblBienSo.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel lblKhachHang = new JLabel("Khách hàng:");
        lblKhachHang.setFont(new Font("Segoe UI", Font.BOLD, 13));

        pnlInfoPanel.add(lblMaLenh);
        pnlInfoPanel.add(lblBienSo);
        pnlInfoPanel.add(lblKhachHang);
        pnlInfoPanel.add(txtMaLenh);
        pnlInfoPanel.add(txtBienSo);
        pnlInfoPanel.add(txtKhachHang);

        JPanel pnlUpdateFormPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlUpdateFormPanel.setBackground(Color.WHITE);
        pnlUpdateFormPanel.setBorder(BorderFactory.createTitledBorder("Cập nhật tiến độ"));

        cmbTrangThai = new JComboBox<>(new String[]{"Đang sửa chữa", "Tạm dừng sửa chữa", "Hoàn thành"});
        cmbTienDo = new JComboBox<>(new String[]{"0%", "20%", "40%", "60%", "80%", "100%"});

        prgBar = new JProgressBar(0, 100);
        prgBar.setValue(0);
        prgBar.setStringPainted(true);
        JLabel lblTrangThai = new JLabel("Trạng thái chung:");
        lblTrangThai.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel lblTienDo = new JLabel("Tiến độ tổng thể (%):");
        lblTienDo.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel lblThanhTienDo = new JLabel("Thanh tiến độ:");
        lblThanhTienDo.setFont(new Font("Segoe UI", Font.BOLD, 13));

        pnlUpdateFormPanel.add(lblTrangThai);
        pnlUpdateFormPanel.add(cmbTrangThai);
        pnlUpdateFormPanel.add(lblTienDo);
        pnlUpdateFormPanel.add(cmbTienDo);
        pnlUpdateFormPanel.add(lblThanhTienDo);
        pnlUpdateFormPanel.add(prgBar);

        txaGhiChu = new JTextArea(4, 20);

        JPanel pnlBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlBtnPanel.setBackground(Color.WHITE);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLamMoi.setBackground(maumenu);

        btnLuuTienDo = new JButton("Lưu tiến độ");
        btnLuuTienDo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLuuTienDo.setBackground(btnchinh);

        pnlBtnPanel.add(btnLamMoi);
        pnlBtnPanel.add(btnLuuTienDo);

        JPanel pnlBottomContainer = new JPanel(new BorderLayout(0, 10));
        pnlBottomContainer.setBackground(Color.WHITE);
        pnlBottomContainer.add(pnlUpdateFormPanel, BorderLayout.NORTH);
        pnlBottomContainer.add(new JScrollPane(txaGhiChu), BorderLayout.CENTER);
        pnlBottomContainer.add(pnlBtnPanel, BorderLayout.SOUTH);

        pnlRightPanel.add(pnlInfoPanel, BorderLayout.NORTH);
        pnlRightPanel.add(pnlBottomContainer, BorderLayout.CENTER);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLeftPanel, pnlRightPanel);
        splitPane.setDividerLocation(460);
        splitPane.setDividerSize(6);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));

        add(splitPane, BorderLayout.CENTER);

        btnQuayLai.addActionListener(this);
        btnTim.addActionListener(this);
        cmbTienDo.addActionListener(this);
        btnLuuTienDo.addActionListener(this);
        btnLamMoi.addActionListener(this);
        tblLenhSC.addMouseListener(this);

        loadDanhSachLenh();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnQuayLai) {
            dispose();
        } else if (e.getSource() == btnTim) {
            loadDanhSachLenh();
        } else if (e.getSource() == cmbTienDo) {
            String val = cmbTienDo.getSelectedItem().toString().replace("%", "");
            prgBar.setValue(Integer.parseInt(val));
        } else if (e.getSource() == btnLuuTienDo) {
            luuTienDo();
        } else if (e.getSource() == btnLamMoi) {
            xoaForm();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == tblLenhSC) {
            hienThiChiTietLenhDuocChon();
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

    private void loadDanhSachLenh() {
        try {
            tableModelLenhSC.setRowCount(0);
            dsPhieuSuaChua = phieuSuaChuaDAO.getAllPhieuSuaChua();
            for (PhieuSuaChua psc : dsPhieuSuaChua) {
                tableModelLenhSC.addRow(new Object[]{psc.getMaPhieuSuaChua(), psc.getBienSo(), psc.getTrangThai()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải lệnh sửa chữa: " + ex.getMessage());
        }
    }

    private void hienThiChiTietLenhDuocChon() {
        int row = tblLenhSC.getSelectedRow();
        if (row != -1 && row < dsPhieuSuaChua.size()) {
            PhieuSuaChua psc = dsPhieuSuaChua.get(row);
            txtMaLenh.setText(psc.getMaPhieuSuaChua());
            txtBienSo.setText(psc.getBienSo());
            cmbTrangThai.setSelectedItem(psc.getTrangThai());
            try {
                Xe xe = xeDAO.getXeByBienSo(psc.getBienSo());
                if (xe != null) {
                    KhachHang kh = khachHangDAO.getKhachHangByMa(xe.getMaKhachHang());
                    txtKhachHang.setText(kh != null ? kh.getHoTen() : "");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void luuTienDo() {
        int row = tblLenhSC.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lệnh sửa chữa cần cập nhật!");
            return;
        }

        String maLenh = txtMaLenh.getText();
        String trangThaiMoi = cmbTrangThai.getSelectedItem().toString();

        try {
            boolean ok = phieuSuaChuaDAO.capNhatTrangThai(maLenh, trangThaiMoi);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Cập nhật tiến độ thành công!");
                loadDanhSachLenh();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi lưu CSDL: " + ex.getMessage());
        }
    }

    private void xoaForm() {
        txtMaLenh.setText("");
        txtBienSo.setText("");
        txtKhachHang.setText("");
        txaGhiChu.setText("");
        prgBar.setValue(0);
        tblLenhSC.clearSelection();
    }

    public static void main(String[] args) {
        new CapNhatTienDoSuaChua();
    }
}