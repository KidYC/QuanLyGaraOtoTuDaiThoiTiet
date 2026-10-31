
package View.KyThuatVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import DAO.KhachHang_DAO;
import DAO.PhieuSuaChua_DAO;
import DAO.Xe_DAO;
import Entity.KhachHang;
import Entity.PhieuSuaChua;
import Entity.Xe;

public class CapNhatTienDoSuaChua extends JFrame implements ActionListener, ListSelectionListener {

    private JPanel pnlHeaderPanel;
    private JPanel pnlLeftPanel;
    private JPanel pnlSearchPanel;
    private JPanel pnlRightPanel;
    private JPanel pnlInfoPanel;
    private JPanel pnlUpdateFormPanel;
    private JPanel pnlBtnPanel;
    private JPanel pnlBottomContainer;

    private JSplitPane splitPane;

    private JTextField txtSearch;
    private JTextField txtMaLenh;
    private JTextField txtBienSo;
    private JTextField txtKhachHang;

    private JTextArea txaGhiChu;

    private JButton btnTim;
    private JButton btnLuuTienDo;
    private JButton btnLamMoi;
    private JButton btnQuayLai;

    private JTable tblLenhSC;
    private DefaultTableModel tableModelLenhSC;

    private JComboBox<String> cmbTrangThai;
    private JComboBox<String> cmbTienDo;

    private JProgressBar prgBar;
    private JDateChooser ngayDuKienChooser;

    private JLabel lblTitle;
    private JLabel lblMaLenh;
    private JLabel lblBienSo;
    private JLabel lblKhachHang;
    private JLabel lblTrangThai;
    private JLabel lblTienDo;
    private JLabel lblThanhTienDo;
    private JLabel lblNgayDuKien;

    private JScrollPane scrLenhSC;
    private JScrollPane scrGhiChu;

    private final PhieuSuaChua_DAO phieuSuaChuaDAO = new PhieuSuaChua_DAO();
    private final Xe_DAO xeDAO = new Xe_DAO();
    private final KhachHang_DAO khachHangDAO = new KhachHang_DAO();

    private List<PhieuSuaChua> dsPhieuSuaChua = new ArrayList<>();

    public CapNhatTienDoSuaChua() {
        setTitle("Công việc được phân công & Tiến độ - Garage Management");
        setSize(1350, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(244, 246, 248));
        setLayout(new BorderLayout(0, 10));

        pnlHeaderPanel = new JPanel(new BorderLayout());
        pnlHeaderPanel.setBackground(Color.WHITE);
        pnlHeaderPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        pnlHeaderPanel.setPreferredSize(new Dimension(1200, 75));

        lblTitle = new JLabel("CÔNG VIỆC ĐƯỢC PHÂN CÔNG & CẬP NHẬT TIẾN ĐỘ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(17, 24, 39));

        btnQuayLai = new JButton("Quay lại");
        btnQuayLai.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnQuayLai.setBackground(new Color(219, 234, 254));
        btnQuayLai.setPreferredSize(new Dimension(110, 40));
        btnQuayLai.addActionListener(this);

        pnlHeaderPanel.add(lblTitle, BorderLayout.WEST);
        pnlHeaderPanel.add(btnQuayLai, BorderLayout.EAST);
        add(pnlHeaderPanel, BorderLayout.NORTH);

        pnlLeftPanel = new JPanel(new BorderLayout(0, 12));
        pnlLeftPanel.setBackground(new Color(244, 246, 248));

        pnlSearchPanel = new JPanel(new BorderLayout(10, 0));
        pnlSearchPanel.setBackground(new Color(244, 246, 248));

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btnTim = new JButton("Tìm lệnh");
        btnTim.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnTim.setBackground(new Color(186, 230, 253));
        btnTim.addActionListener(this);

        pnlSearchPanel.add(txtSearch, BorderLayout.CENTER);
        pnlSearchPanel.add(btnTim, BorderLayout.EAST);

        tableModelLenhSC = new DefaultTableModel(
                new String[]{"Mã lệnh", "Biển số", "Trạng thái", "Tiến độ", "Ngày dự kiến hoàn thành", "Ghi chú"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLenhSC = new JTable(tableModelLenhSC);
        tblLenhSC.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblLenhSC.setRowHeight(36);
        tblLenhSC.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblLenhSC.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblLenhSC.getSelectionModel().addListSelectionListener(this);

        tblLenhSC.getColumnModel().getColumn(0).setPreferredWidth(90);
        tblLenhSC.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblLenhSC.getColumnModel().getColumn(2).setPreferredWidth(130);
        tblLenhSC.getColumnModel().getColumn(3).setPreferredWidth(75);
        tblLenhSC.getColumnModel().getColumn(4).setPreferredWidth(150);
        tblLenhSC.getColumnModel().getColumn(5).setPreferredWidth(220);

        scrLenhSC = new JScrollPane(tblLenhSC);

        pnlLeftPanel.add(pnlSearchPanel, BorderLayout.NORTH);
        pnlLeftPanel.add(scrLenhSC, BorderLayout.CENTER);

        pnlRightPanel = new JPanel(new BorderLayout(0, 12));
        pnlRightPanel.setBackground(Color.WHITE);
        pnlRightPanel.setBorder(BorderFactory.createEmptyBorder(15, 18, 15, 18));

        pnlInfoPanel = new JPanel(new GridLayout(2, 3, 10, 10));
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

        lblMaLenh = new JLabel("Mã lệnh SC:");
        lblMaLenh.setFont(new Font("Segoe UI", Font.BOLD, 13));

        lblBienSo = new JLabel("Biển số xe:");
        lblBienSo.setFont(new Font("Segoe UI", Font.BOLD, 13));

        lblKhachHang = new JLabel("Khách hàng:");
        lblKhachHang.setFont(new Font("Segoe UI", Font.BOLD, 13));

        pnlInfoPanel.add(lblMaLenh);
        pnlInfoPanel.add(lblBienSo);
        pnlInfoPanel.add(lblKhachHang);
        pnlInfoPanel.add(txtMaLenh);
        pnlInfoPanel.add(txtBienSo);
        pnlInfoPanel.add(txtKhachHang);

        pnlUpdateFormPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlUpdateFormPanel.setBackground(Color.WHITE);
        pnlUpdateFormPanel.setBorder(BorderFactory.createTitledBorder("Cập nhật tiến độ"));

        cmbTrangThai = new JComboBox<>(new String[]{
                "Đang sửa chữa",
                "Tạm dừng sửa chữa",
                "Hoàn thành"
        });

        cmbTienDo = new JComboBox<>(new String[]{
                "0%", "20%", "40%", "60%", "80%", "100%"
        });

        cmbTienDo.addActionListener(this);

        prgBar = new JProgressBar(0, 100);
        prgBar.setValue(0);
        prgBar.setStringPainted(true);

        ngayDuKienChooser = new JDateChooser();
        ngayDuKienChooser.setDateFormatString("dd/MM/yyyy");
        ngayDuKienChooser.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        lblTrangThai = new JLabel("Trạng thái chung:");
        lblTrangThai.setFont(new Font("Segoe UI", Font.BOLD, 13));

        lblTienDo = new JLabel("Tiến độ tổng thể (%):");
        lblTienDo.setFont(new Font("Segoe UI", Font.BOLD, 13));

        lblNgayDuKien = new JLabel("Ngày dự kiến hoàn thành:");
        lblNgayDuKien.setFont(new Font("Segoe UI", Font.BOLD, 13));

        lblThanhTienDo = new JLabel("Thanh tiến độ:");
        lblThanhTienDo.setFont(new Font("Segoe UI", Font.BOLD, 13));

        pnlUpdateFormPanel.add(lblTrangThai);
        pnlUpdateFormPanel.add(cmbTrangThai);
        pnlUpdateFormPanel.add(lblTienDo);
        pnlUpdateFormPanel.add(cmbTienDo);
        pnlUpdateFormPanel.add(lblNgayDuKien);
        pnlUpdateFormPanel.add(ngayDuKienChooser);
        pnlUpdateFormPanel.add(lblThanhTienDo);
        pnlUpdateFormPanel.add(prgBar);

        txaGhiChu = new JTextArea(4, 20);
        txaGhiChu.setLineWrap(true);
        txaGhiChu.setWrapStyleWord(true);

        scrGhiChu = new JScrollPane(txaGhiChu);
        scrGhiChu.setBorder(BorderFactory.createTitledBorder("Ghi chú sửa chữa"));

        pnlBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlBtnPanel.setBackground(Color.WHITE);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLamMoi.setBackground(new Color(226, 232, 240));
        btnLamMoi.addActionListener(this);

        btnLuuTienDo = new JButton("Lưu tiến độ");
        btnLuuTienDo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLuuTienDo.setBackground(new Color(186, 230, 253));
        btnLuuTienDo.addActionListener(this);

        pnlBtnPanel.add(btnLamMoi);
        pnlBtnPanel.add(btnLuuTienDo);

        pnlBottomContainer = new JPanel(new BorderLayout(0, 10));
        pnlBottomContainer.setBackground(Color.WHITE);
        pnlBottomContainer.add(pnlUpdateFormPanel, BorderLayout.NORTH);
        pnlBottomContainer.add(scrGhiChu, BorderLayout.CENTER);
        pnlBottomContainer.add(pnlBtnPanel, BorderLayout.SOUTH);

        pnlRightPanel.add(pnlInfoPanel, BorderLayout.NORTH);
        pnlRightPanel.add(pnlBottomContainer, BorderLayout.CENTER);

        splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                pnlLeftPanel,
                pnlRightPanel
        );

        splitPane.setDividerLocation(650);
        splitPane.setDividerSize(6);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));

        add(splitPane, BorderLayout.CENTER);

        loadDanhSachLenh();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnQuayLai) {
            dispose();
        } else if (e.getSource() == btnTim) {
            timKiemLenh();
        } else if (e.getSource() == cmbTienDo) {
            if (cmbTienDo.getSelectedItem() != null) {
                String val = cmbTienDo.getSelectedItem().toString().replace("%", "");
                prgBar.setValue(Integer.parseInt(val));
            }
        } else if (e.getSource() == btnLuuTienDo) {
            luuTienDo();
        } else if (e.getSource() == btnLamMoi) {
            xoaForm();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            hienThiChiTietLenhDuocChon();
        }
    }

    private void timKiemLenh() {
        String keyword = txtSearch.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {
            loadDanhSachLenh();
            return;
        }

        for (int i = 0; i < tableModelLenhSC.getRowCount(); i++) {
            String maLenh = tableModelLenhSC.getValueAt(i, 0).toString().toLowerCase();
            String bienSo = tableModelLenhSC.getValueAt(i, 1).toString().toLowerCase();

            if (maLenh.contains(keyword) || bienSo.contains(keyword)) {
                tblLenhSC.setRowSelectionInterval(i, i);
                tblLenhSC.scrollRectToVisible(tblLenhSC.getCellRect(i, 0, true));
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Không tìm thấy lệnh sửa chữa phù hợp!");
    }

    private void loadDanhSachLenh() {
        try {
            tableModelLenhSC.setRowCount(0);
            dsPhieuSuaChua = phieuSuaChuaDAO.getAllPhieuSuaChua();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            for (PhieuSuaChua psc : dsPhieuSuaChua) {
                String ngayDuKien = psc.getNgayDuKienHoanThanh() != null
                        ? sdf.format(psc.getNgayDuKienHoanThanh())
                        : "";

                tableModelLenhSC.addRow(new Object[]{
                        psc.getMaPhieuSuaChua(),
                        psc.getBienSo(),
                        psc.getTrangThai(),
                        psc.getTienDo() + "%",
                        ngayDuKien,
                        psc.getGhiChu() != null ? psc.getGhiChu() : ""
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi tải lệnh sửa chữa: " + ex.getMessage()
            );
        }
    }

    private void hienThiChiTietLenhDuocChon() {
        int row = tblLenhSC.getSelectedRow();

        if (row == -1) {
            return;
        }

        int modelRow = tblLenhSC.convertRowIndexToModel(row);
        String maLenh = tableModelLenhSC.getValueAt(modelRow, 0).toString();

        for (PhieuSuaChua psc : dsPhieuSuaChua) {
            if (psc.getMaPhieuSuaChua().equals(maLenh)) {
                txtMaLenh.setText(psc.getMaPhieuSuaChua());
                txtBienSo.setText(psc.getBienSo());
                cmbTrangThai.setSelectedItem(psc.getTrangThai());

                int tienDoCu = psc.getTienDo();
                prgBar.setValue(tienDoCu);

                String tienDoText = tienDoCu + "%";
                boolean tonTai = false;

                for (int i = 0; i < cmbTienDo.getItemCount(); i++) {
                    if (cmbTienDo.getItemAt(i).equals(tienDoText)) {
                        tonTai = true;
                        break;
                    }
                }

                cmbTienDo.setSelectedItem(tonTai ? tienDoText : null);

                txaGhiChu.setText(
                        psc.getGhiChu() != null ? psc.getGhiChu() : ""
                );

                ngayDuKienChooser.setDate(psc.getNgayDuKienHoanThanh());

                try {
                    Xe xe = xeDAO.getXeByBienSo(psc.getBienSo());

                    if (xe != null) {
                        KhachHang kh = khachHangDAO.getKhachHangByMa(
                                xe.getMaKhachHang()
                        );

                        txtKhachHang.setText(
                                kh != null ? kh.getHoTen() : ""
                        );
                    } else {
                        txtKhachHang.setText("");
                    }
                } catch (SQLException ex) {
                    txtKhachHang.setText("");
                    JOptionPane.showMessageDialog(
                            this,
                            "Lỗi tải thông tin khách hàng: " + ex.getMessage()
                    );
                }

                return;
            }
        }
    }

    private void luuTienDo() {
        int row = tblLenhSC.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn lệnh sửa chữa cần cập nhật!"
            );
            return;
        }

        String maLenh = txtMaLenh.getText().trim();

        if (maLenh.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không tìm thấy mã lệnh sửa chữa!"
            );
            return;
        }

        if (ngayDuKienChooser.getDate() == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn ngày dự kiến hoàn thành!"
            );
            return;
        }

        String trangThaiMoi = cmbTrangThai.getSelectedItem().toString();
        int tienDoMoi = prgBar.getValue();
        String ghiChuMoi = txaGhiChu.getText().trim();

        java.sql.Date ngayDuKienMoi = new java.sql.Date(
                ngayDuKienChooser.getDate().getTime()
        );

        try {
            boolean ok = phieuSuaChuaDAO.capNhatTienDoVaTrangThai(
                    maLenh,
                    trangThaiMoi,
                    tienDoMoi,
                    ghiChuMoi,
                    ngayDuKienMoi
            );

            if (ok) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cập nhật tiến độ thành công!"
                );

                loadDanhSachLenh();

                for (int i = 0; i < tableModelLenhSC.getRowCount(); i++) {
                    if (tableModelLenhSC.getValueAt(i, 0).toString().equals(maLenh)) {
                        tblLenhSC.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi lưu CSDL: " + ex.getMessage()
            );
        }
    }

    private void xoaForm() {
        txtMaLenh.setText("");
        txtBienSo.setText("");
        txtKhachHang.setText("");
        txaGhiChu.setText("");
        cmbTrangThai.setSelectedIndex(0);
        cmbTienDo.setSelectedIndex(0);
        prgBar.setValue(0);
        ngayDuKienChooser.setDate(null);
        tblLenhSC.clearSelection();
        loadDanhSachLenh();
    }

    public static void main(String[] args) {
        new CapNhatTienDoSuaChua();
    }
}
