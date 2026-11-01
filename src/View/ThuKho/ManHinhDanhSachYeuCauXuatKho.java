package View.ThuKho;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import DAO.PhieuSuaChua_DAO;
import Entity.PhieuSuaChua;

public class ManHinhDanhSachYeuCauXuatKho extends JFrame implements ActionListener {

    private JLabel lbltieude;

    private JTextField txtmayeucau;
    private JTextField txtnguoiyeucau;
    private JComboBox<String> cbtrangthai;
    private JTextField txtngayyeucau;
    private JTextField txtbienso;
    private JButton btntim;
    private JButton btnlammoi;

    private JTable tblyeucau;
    private DefaultTableModel modelyeucau;

    private JButton btnduyet;
    private JButton btntuchoi;
    private JButton btnlapphieu;
    private JButton btnchitiet;
    private JButton btnquaylai;
    private final PhieuSuaChua_DAO phieuSuaChuaDAO = new PhieuSuaChua_DAO();

    private List<PhieuSuaChua> dsPhieuFull = new ArrayList<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ManHinhDanhSachYeuCauXuatKho() {
        setTitle("DANH SÁCH YÊU CẦU XUẤT KHO");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();
        loadDataFromDB();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        // pNorth
        lbltieude = new JLabel("DANH SÁCH YÊU CẦU XUẤT KHO");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 22));
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbltieude.setForeground(new Color(44, 62, 80));
        lbltieude.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lbltieude, BorderLayout.NORTH);

        // pCenter
        JPanel pCenter = new JPanel(new BorderLayout(0, 10));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pTimKiem = new JPanel(new GridLayout(2, 6, 10, 10));
        pTimKiem.setBackground(Color.WHITE);
        pTimKiem.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Tìm kiếm",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        txtmayeucau = new JTextField();
        txtnguoiyeucau = new JTextField();
        cbtrangthai = new JComboBox<>(new String[]{"Tất cả", "Chờ báo giá", "Chờ xuất kho", "Đang sửa chữa", "Tạm dừng sửa chữa",
                "Hoàn thành", "Đạt - Chờ bàn giao", "Không đạt - Sửa lại", "Đang xử lý", "Từ chối"});
        txtngayyeucau = new JTextField();
        txtbienso = new JTextField();

        btntim = new JButton("Tìm");
        btntim.setBackground(new Color(52, 152, 219));
        btntim.setForeground(Color.WHITE);
        btntim.setFocusPainted(false);

        btnlammoi = new JButton("Làm Mới");
        btnlammoi.setBackground(new Color(149, 165, 166));
        btnlammoi.setForeground(Color.WHITE);
        btnlammoi.setFocusPainted(false);

        pTimKiem.add(new JLabel("Mã Yêu Cầu:"));
        pTimKiem.add(txtmayeucau);
        pTimKiem.add(new JLabel("Người Yêu Cầu:"));
        pTimKiem.add(txtnguoiyeucau);
        pTimKiem.add(new JLabel("Trạng Thái:"));
        pTimKiem.add(cbtrangthai);
        pTimKiem.add(new JLabel("Ngày (dd/MM/yyyy):"));
        pTimKiem.add(txtngayyeucau);
        pTimKiem.add(new JLabel("Biển Số Xe:"));
        pTimKiem.add(txtbienso);
        pTimKiem.add(btntim);
        pTimKiem.add(btnlammoi);

        pCenter.add(pTimKiem, BorderLayout.NORTH);

        JPanel pBang = new JPanel(new GridLayout(1, 1));
        pBang.setBackground(new Color(245, 245, 245));

        String[] columnyc = {"Mã Yêu Cầu", "Ngày Yêu Cầu", "Người Yêu Cầu", "Biển Số Xe", "Trạng Thái"};
        modelyeucau = new DefaultTableModel(columnyc, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblyeucau = new JTable(modelyeucau);
        tblyeucau.setRowHeight(25);
        JScrollPane scrollyc = new JScrollPane(tblyeucau);
        scrollyc.setBorder(BorderFactory.createTitledBorder("Danh sách yêu cầu xuất kho"));

        pBang.add(scrollyc);
        pCenter.add(pBang, BorderLayout.CENTER);

        add(pCenter, BorderLayout.CENTER);

        // pSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnquaylai = new JButton("Quay Lại");
        btnquaylai.setPreferredSize(new Dimension(110, 35));
        btnquaylai.setBackground(new Color(149, 165, 166));
        btnquaylai.setForeground(Color.WHITE);
        btnquaylai.setFocusPainted(false);

        btntuchoi = new JButton("Từ Chối");
        btntuchoi.setPreferredSize(new Dimension(110, 35));
        btntuchoi.setBackground(new Color(231, 76, 60));
        btntuchoi.setForeground(Color.WHITE);
        btntuchoi.setFocusPainted(false);

        btnduyet = new JButton("Duyệt & Xuất Kho");
        btnduyet.setPreferredSize(new Dimension(160, 35));
        btnduyet.setBackground(new Color(52, 152, 219));
        btnduyet.setForeground(Color.WHITE);
        btnduyet.setFocusPainted(false);

        btnchitiet = new JButton("Xem Chi Tiết");
        btnchitiet.setPreferredSize(new Dimension(130, 35));
        btnchitiet.setBackground(new Color(243, 156, 18));
        btnchitiet.setForeground(Color.WHITE);
        btnchitiet.setFocusPainted(false);

        btnlapphieu = new JButton("Lập Phiếu Xuất Kho");
        btnlapphieu.setPreferredSize(new Dimension(170, 35));
        btnlapphieu.setBackground(new Color(46, 204, 113));
        btnlapphieu.setForeground(Color.WHITE);
        btnlapphieu.setFont(new Font("Arial", Font.BOLD, 12));
        btnlapphieu.setFocusPainted(false);

        pSouth.add(btnquaylai);
        pSouth.add(btntuchoi);
        pSouth.add(btnchitiet);
        pSouth.add(btnduyet);
        pSouth.add(btnlapphieu);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btntim.addActionListener(this);
        btnlammoi.addActionListener(this);
        btnduyet.addActionListener(this);
        btntuchoi.addActionListener(this);
        btnlapphieu.addActionListener(this);
        btnchitiet.addActionListener(this);
        btnquaylai.addActionListener(this);

        tblyeucau.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    xuLyXemChiTiet(); // nhấp đúp để xem chi tiết
                }
            }
        });
    }

    // Tải dữ liệu từ CSDL
    private void loadDataFromDB() {
        try {
            dsPhieuFull = phieuSuaChuaDAO.getAllPhieuSuaChua();
            napBangYeuCau(dsPhieuFull);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu từ CSDL: " + e.getMessage(), "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void napBangYeuCau(List<PhieuSuaChua> ds) {
        modelyeucau.setRowCount(0);
        if (ds == null) return;

        for (PhieuSuaChua psc : ds) {
            String ngayFormat = (psc.getNgayLap() != null) ? sdf.format(psc.getNgayLap()) : "";

            String maNhanVien = psc.getMaNhanVien();
            if (maNhanVien == null) {
                maNhanVien = psc.getMaNhanVien();
            }

            modelyeucau.addRow(new Object[]{
                    psc.getMaPhieuSuaChua(),
                    ngayFormat,
                    maNhanVien,
                    psc.getBienSo(),
                    psc.getTrangThai()
            });
        }
    }

    private void xuLyXemChiTiet() {
        int dong = tblyeucau.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn yêu cầu cần xem!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ManHinhChiTietYeuCauXuatKho UI = new ManHinhChiTietYeuCauXuatKho(
                modelyeucau.getValueAt(dong, 0).toString(),
                modelyeucau.getValueAt(dong, 1).toString(),
                String.valueOf(modelyeucau.getValueAt(dong, 2)),
                String.valueOf(modelyeucau.getValueAt(dong, 3)),
                String.valueOf(modelyeucau.getValueAt(dong, 4)));
        UI.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btntim) {
            xuLyTim();
        } else if (o == btnlammoi) {
            xuLyLamMoi();
        } else if (o == btnduyet) {
            xuLyDuyetXuatKho();
        } else if (o == btntuchoi) {
            xuLyTuChoi();
        } else if (o == btnlapphieu) {
            xuLyLapPhieu();
        } else if (o == btnchitiet) {
            xuLyXemChiTiet();
        } else if (o == btnquaylai) {
            this.dispose();
        }
    }

    private void xuLyTim() {
        String ngay = txtngayyeucau.getText().trim();
        if (!ngay.isEmpty()) {
            try {
                sdf.setLenient(false);
                ngay = sdf.format(sdf.parse(ngay));   // chuẩn hóa, ví dụ 1/10/2026 -> 01/10/2026
            } catch (java.text.ParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Ngày phải nhập đúng dạng dd/MM/yyyy (ví dụ 01/10/2026)!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        String ma = txtmayeucau.getText().trim().toLowerCase();
        String nguoi = txtnguoiyeucau.getText().trim().toLowerCase();
        String bienso = txtbienso.getText().trim().toLowerCase();
        String trangthai = cbtrangthai.getSelectedItem().toString();

        List<PhieuSuaChua> ketqua = new ArrayList<>();
        for (PhieuSuaChua psc : dsPhieuFull) {
            if (!psc.getMaPhieuSuaChua().toLowerCase().contains(ma)) continue;

            String maNV = psc.getMaNhanVien() == null ? "" : psc.getMaNhanVien();
            if (!maNV.toLowerCase().contains(nguoi)) continue;
            String bs = psc.getBienSo() == null ? "" : psc.getBienSo();
            if (!bs.toLowerCase().contains(bienso)) continue;
            String tt = psc.getTrangThai() == null ? "" : psc.getTrangThai();
            if (!trangthai.equals("Tất cả") && !tt.equalsIgnoreCase(trangthai)) continue;
            if (!ngay.isEmpty()) {
                String ngaylap = (psc.getNgayLap() != null) ? sdf.format(psc.getNgayLap()) : "";
                if (!ngaylap.equals(ngay)) continue;
            }

            ketqua.add(psc);
        }

        napBangYeuCau(ketqua);
        if (ketqua.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy yêu cầu nào phù hợp!");
        }
    }

    private void xuLyLamMoi() {
        txtmayeucau.setText("");
        txtnguoiyeucau.setText("");
        cbtrangthai.setSelectedIndex(0);
        txtngayyeucau.setText("");
        txtbienso.setText("");
        loadDataFromDB();
    }

    private void xuLyDuyetXuatKho() {
        int dong = tblyeucau.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn yêu cầu cần xuất kho!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maPhieuSC = modelyeucau.getValueAt(dong, 0).toString();
        String hientai = modelyeucau.getValueAt(dong, 4).toString();

        if (!hientai.equalsIgnoreCase("Chờ xuất kho")) {
            JOptionPane.showMessageDialog(this, "Chỉ xử lý được các phiếu đang ở trạng thái 'Chờ xuất kho'!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int chon = JOptionPane.showConfirmDialog(this,
                "Xác nhận xuất phụ tùng và trừ số lượng tồn kho cho phiếu " + maPhieuSC + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (chon != JOptionPane.YES_OPTION) return;

        try {
            boolean thanhCong = phieuSuaChuaDAO.xacNhanXuatKho(maPhieuSC);
            if (thanhCong) {
                JOptionPane.showMessageDialog(this, "Duyệt xuất kho thành công! Số lượng phụ tùng trong kho đã được trừ.");
                loadDataFromDB();
            } else {
                JOptionPane.showMessageDialog(this, "Xuất kho thất bại! Tồn kho không đủ hoặc phiếu không còn ở trạng thái 'Chờ xuất kho'.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                loadDataFromDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi xử lý: " + ex.getMessage(), "Lỗi SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyTuChoi() {
        int dong = tblyeucau.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn yêu cầu cần từ chối!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maPhieuSC = modelyeucau.getValueAt(dong, 0).toString();
        int chon = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn từ chối yêu cầu xuất kho cho phiếu " + maPhieuSC + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (chon != JOptionPane.YES_OPTION) return;

        try {
            boolean thanhCong = phieuSuaChuaDAO.capNhatTrangThai(maPhieuSC, "Từ chối");
            if (thanhCong) {
                JOptionPane.showMessageDialog(this, "Đã chuyển trạng thái phiếu sang 'Từ chối'.");
                loadDataFromDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi xử lý: " + ex.getMessage(), "Lỗi SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyLapPhieu() {
        int dong = tblyeucau.getSelectedRow();
        if (dong == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn yêu cầu cần lập phiếu xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!modelyeucau.getValueAt(dong, 4).toString().equalsIgnoreCase("Chờ xuất kho")) {
            JOptionPane.showMessageDialog(this, "Chỉ lập phiếu xuất cho các phiếu đang ở trạng thái 'Chờ xuất kho'!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        moManHinhCon(new ManHinhLapPhieuXuatKho(modelyeucau.getValueAt(dong, 0).toString()));
    }

    private void moManHinhCon(JFrame UI) {
        UI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loadDataFromDB();
            }
        });
        UI.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ManHinhDanhSachYeuCauXuatKho().setVisible(true);
        });
    }
}
