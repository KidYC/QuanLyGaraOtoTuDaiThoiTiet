package View.ThuKho;

import DAO.PhuTung_DAO;
import Entity.PhuTung;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;

public class ManHinhQuanLyDanhMucPhuTung extends JFrame implements ActionListener {

    private JLabel lbltieude;

    private JTable tblphutung;
    private DefaultTableModel modelphutung;

    private JButton btnthem;
    private JButton btnsua;
    private JButton btnxoa;
    private JButton btnquaylai;
    private final PhuTung_DAO phuTungDAO = new PhuTung_DAO();

    public ManHinhQuanLyDanhMucPhuTung() {
        setTitle("QUẢN LÝ DANH MỤC PHỤ TÙNG");
        setSize(950, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();
        napBang();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel("DANH MỤC PHỤ TÙNG");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 22));
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbltieude.setForeground(new Color(44, 62, 80));
        lbltieude.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lbltieude, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel(new BorderLayout(15, 0));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        String[] column = {"Mã Phụ Tùng", "Tên Phụ Tùng", "Đơn Vị", "Số Lượng"};
        Object[][] data = {};
        modelphutung = new DefaultTableModel(data, column) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblphutung = new JTable(modelphutung);
        tblphutung.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tblphutung);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phụ tùng"));
        pCenter.add(scroll, BorderLayout.CENTER);


        add(pCenter, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnquaylai = new JButton("Quay Lại");
        btnquaylai.setPreferredSize(new Dimension(110, 35));
        btnquaylai.setBackground(new Color(149, 165, 166));
        btnquaylai.setForeground(Color.WHITE);
        btnquaylai.setFocusPainted(false);

        btnxoa = new JButton("Xóa");
        btnxoa.setPreferredSize(new Dimension(110, 35));
        btnxoa.setBackground(new Color(231, 76, 60));
        btnxoa.setForeground(Color.WHITE);
        btnxoa.setFocusPainted(false);

        btnsua = new JButton("Sửa");
        btnsua.setPreferredSize(new Dimension(110, 35));
        btnsua.setBackground(new Color(52, 152, 219));
        btnsua.setForeground(Color.WHITE);
        btnsua.setFocusPainted(false);

        btnthem = new JButton("Thêm Phụ Tùng");
        btnthem.setPreferredSize(new Dimension(150, 35));
        btnthem.setBackground(new Color(46, 204, 113));
        btnthem.setForeground(Color.WHITE);
        btnthem.setFont(new Font("Arial", Font.BOLD, 12));
        btnthem.setFocusPainted(false);

        pSouth.add(btnquaylai);
        pSouth.add(btnxoa);
        pSouth.add(btnsua);
        pSouth.add(btnthem);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btnthem.addActionListener(this);
        btnsua.addActionListener(this);
        btnxoa.addActionListener(this);
        btnquaylai.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnthem) {
            xuLyThem();
        } else if (o == btnsua) {
            xuLySua();
        } else if (o == btnxoa) {
            xuLyXoa();
        } else if (o == btnquaylai) {
            xuLyQuayLai();
        }
    }

    private void napBang() {
        modelphutung.setRowCount(0);
        try {
            for (PhuTung pt : phuTungDAO.getAllPhuTung()) {
                modelphutung.addRow(new Object[]{pt.getMaPhuTung(), pt.getTenPhuTung(),
                        pt.getDonViTinh(), pt.getSoLuongTon()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    public boolean tonTaiMa(String ma) {
        try { return phuTungDAO.timPhuTungTheoMa(ma) != null; } catch (SQLException e) { return false; }
    }

    public void themPhuTung(String ma, String ten, String donvi, int soluong) {
        try {
            phuTungDAO.themPhuTung(new PhuTung(ma, ten, donvi, 0, 0, soluong, "K01"));
            napBang();
        } catch (SQLException e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
    }

    public void capNhatPhuTung(String ma, String ten, String donvi, int soluong) {
        try {
            PhuTung pt = phuTungDAO.timPhuTungTheoMa(ma);
            pt.setTenPhuTung(ten); pt.setDonViTinh(donvi); pt.setSoLuongTon(soluong);
            phuTungDAO.capNhatPhuTung(pt);
            napBang();
        } catch (SQLException e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
    }

    private void xuLyThem() {
        FormThongTinPhuTung UI = new FormThongTinPhuTung(this);
        UI.setVisible(true);
    }

    private void xuLySua() {
        int dong = tblphutung.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn phụ tùng cần sửa!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        FormThongTinPhuTung UI = new  FormThongTinPhuTung(this,
                modelphutung.getValueAt(dong, 0).toString(),
                modelphutung.getValueAt(dong, 1).toString(),
                modelphutung.getValueAt(dong, 2).toString(),
                modelphutung.getValueAt(dong, 3).toString());
        UI.setVisible(true);
    }

    private void xuLyXoa() {
        int dong = tblphutung.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn phụ tùng cần xóa!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int chon = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa phụ tùng " + modelphutung.getValueAt(dong, 0) + " không?",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (chon == JOptionPane.YES_OPTION) {
            String ma = modelphutung.getValueAt(dong, 0).toString();
            try {
                if (phuTungDAO.xoaPhuTung(ma)) {
                    napBang();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Không xóa được phụ tùng " + ma + " (có thể đang được dùng trong phiếu nhập/xuất/kiểm kê).",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xuLyQuayLai() {
        this.dispose();
    }

    public static void main(String[] args) {
        ManHinhQuanLyDanhMucPhuTung form = new ManHinhQuanLyDanhMucPhuTung();
        form.setVisible(true);
    }
}