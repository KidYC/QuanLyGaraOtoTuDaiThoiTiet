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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import DAO.PhieuSuaChua_DAO;
import DAO.ThuKho_DAO;
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
    private JButton btnchitiet;
    private JButton btnquaylai;
    private final PhieuSuaChua_DAO phieuSuaChuaDAO = new PhieuSuaChua_DAO();
    private final ThuKho_DAO thuKhoDAO = new ThuKho_DAO();

    private List<PhieuSuaChua> dsPhieuFull = new ArrayList<>();


    // các trạng thái dưới đây là phiếu chưa tới kho nên không hiện trong danh sách của thủ kho.
    private final String[] TRANG_THAI_CHUA_TOI_KHO = {"Đang lập vật tư", "Chờ xác nhận báo giá", "Chờ báo giá", "Từ chối"};
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
        cbtrangthai = new JComboBox<>(new String[]{"Chờ xuất kho", "Đã xuất kho", "Từ chối xuất kho", "Tất cả"});
        cbtrangthai.setSelectedItem("Chờ xuất kho"); // mở lên là thấy ngay các yêu cầu cần duyệt
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
        btnduyet.setBackground(new Color(46, 204, 113));
        btnduyet.setForeground(Color.WHITE);
        btnduyet.setFocusPainted(false);

        btnchitiet = new JButton("Xem Chi Tiết");
        btnchitiet.setPreferredSize(new Dimension(130, 35));
        btnchitiet.setBackground(new Color(243, 156, 18));
        btnchitiet.setForeground(Color.WHITE);
        btnchitiet.setFocusPainted(false);

        pSouth.add(btnquaylai);
        pSouth.add(btntuchoi);
        pSouth.add(btnchitiet);
        pSouth.add(btnduyet);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btntim.addActionListener(this);
        btnlammoi.addActionListener(this);
        btnduyet.addActionListener(this);
        btntuchoi.addActionListener(this);
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
            dsPhieuFull = new ArrayList<>();
            for (PhieuSuaChua psc : phieuSuaChuaDAO.getAllPhieuSuaChua()) {
                if (daToiKho(psc.getTrangThai())) {
                    dsPhieuFull.add(psc);
                }
            }
            List<PhieuSuaChua> ds = locDanhSach();
            napBangYeuCau(ds == null ? dsPhieuFull : ds);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu từ CSDL: " + e.getMessage(), "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean daToiKho(String trangThai) {
        if (trangThai == null || trangThai.trim().isEmpty()) return false;
        for (String tt : TRANG_THAI_CHUA_TOI_KHO) {
            if (tt.equalsIgnoreCase(trangThai.trim())) return false;
        }
        return true;
    }

    private boolean dungTrangThai(String trangThai, String chon) {
        if (chon.equals("Tất cả")) return true;
        if (chon.equals("Đã xuất kho")) {
            return !trangThai.equalsIgnoreCase("Chờ xuất kho") && !trangThai.equalsIgnoreCase("Từ chối xuất kho");
        }
        return trangThai.equalsIgnoreCase(chon);
    }

    private void napBangYeuCau(List<PhieuSuaChua> ds) {
        modelyeucau.setRowCount(0);
        if (ds == null) return;

        for (PhieuSuaChua psc : ds) {
            String ngayFormat = (psc.getNgayLap() != null) ? sdf.format(psc.getNgayLap()) : "";

            String maNhanVien = psc.getMaNhanVien() == null ? "" : psc.getMaNhanVien();

            modelyeucau.addRow(new Object[]{
                    psc.getMaPhieuSuaChua(),
                    ngayFormat,
                    maNhanVien,
                    psc.getBienSo(),
                    psc.getTrangThai() == null ? "" : psc.getTrangThai().trim()
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
        } else if (o == btnchitiet) {
            xuLyXemChiTiet();
        } else if (o == btnquaylai) {
            this.dispose();
        }
    }

    private void xuLyTim() {
        List<PhieuSuaChua> ketqua = locDanhSach();
        if (ketqua == null) return;

        napBangYeuCau(ketqua);
        if (ketqua.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy yêu cầu nào phù hợp!");
        }
    }

    private List<PhieuSuaChua> locDanhSach() {
        String ngay = txtngayyeucau.getText().trim();
        if (!ngay.isEmpty()) {
            try {
                sdf.setLenient(false);
                ngay = sdf.format(sdf.parse(ngay));   // chuẩn hóa, ví dụ 1/10/2026 -> 01/10/2026
            } catch (java.text.ParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Ngày phải nhập đúng dạng dd/MM/yyyy (ví dụ 01/10/2026)!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return null;
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
            String tt = psc.getTrangThai() == null ? "" : psc.getTrangThai().trim();
            if (!dungTrangThai(tt, trangthai)) continue;
            if (!ngay.isEmpty()) {
                String ngaylap = (psc.getNgayLap() != null) ? sdf.format(psc.getNgayLap()) : "";
                if (!ngaylap.equals(ngay)) continue;
            }

            ketqua.add(psc);
        }
        return ketqua;
    }

    private void xuLyLamMoi() {
        txtmayeucau.setText("");
        txtnguoiyeucau.setText("");
        cbtrangthai.setSelectedItem("Chờ xuất kho");
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
            boolean thanhCong = thuKhoDAO.xacNhanXuatKho(maPhieuSC);
            if (thanhCong) {
                JOptionPane.showMessageDialog(this, "Duyệt xuất kho thành công! Xe đã được chuyển sang trạng thái Đang sửa chữa.");
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
        if (!modelyeucau.getValueAt(dong, 4).toString().equalsIgnoreCase("Chờ xuất kho")) {
            JOptionPane.showMessageDialog(this, "Chỉ từ chối được các phiếu đang ở trạng thái 'Chờ xuất kho'!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int chon = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn từ chối yêu cầu xuất kho cho phiếu " + maPhieuSC + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (chon != JOptionPane.YES_OPTION) return;

        try {
            boolean thanhCong = phieuSuaChuaDAO.capNhatTrangThai(maPhieuSC, "Từ chối xuất kho");
            if (thanhCong) {
                JOptionPane.showMessageDialog(this, "Đã chuyển trạng thái phiếu sang 'Từ chối xuất kho'.");
                loadDataFromDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi xử lý: " + ex.getMessage(), "Lỗi SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        ManHinhDanhSachYeuCauXuatKho form = new ManHinhDanhSachYeuCauXuatKho();
        form.setVisible(true);
    }
}
