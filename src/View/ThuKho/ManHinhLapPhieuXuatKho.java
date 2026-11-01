package View.ThuKho;

import DAO.DanhSachPhuTung_DAO;
import DAO.PhieuSuaChua_DAO;
import DAO.PhuTung_DAO;
import DAO.ThuKho_DAO;
import Entity.DanhSachPhuTung;
import Entity.PhieuSuaChua;
import Entity.PhuTung;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ManHinhLapPhieuXuatKho extends JFrame implements ActionListener {

    private JLabel lbltieude;

    private JTextField txtmaphieu;
    private JTextField txtngayxuat;
    private JComboBox<String> cbyeucau;
    private JTextField txtnhanvien;
    private JTextField txtghichu;

    private JComboBox<String> cbphutung;
    private JTextField txtdonvi;
    private JTextField txtsltonkho;
    private JTextField txtslxuat;

    private JButton btnquaylai;
    private JButton btnlammoi;
    private JButton btnluu;

    // CSDL không có bảng phiếu xuất riêng: phiếu xuất = phiếu sửa chữa đang "Chờ xuất kho".
    // Lưu phiếu sẽ xuất toàn bộ phụ tùng của yêu cầu (PhieuSuaChua_DAO.xacNhanXuatKho).
    private final PhieuSuaChua_DAO phieuSuaChuaDAO = new PhieuSuaChua_DAO();
    private final DanhSachPhuTung_DAO danhSachPhuTungDAO = new DanhSachPhuTung_DAO();
    private final PhuTung_DAO phuTungDAO = new PhuTung_DAO();
    private final ThuKho_DAO thuKhoDAO = new ThuKho_DAO();

    private List<PhieuSuaChua> dsyeucau = new ArrayList<>();
    private List<DanhSachPhuTung> dsphutungxuat = new ArrayList<>();
    private List<PhuTung> dstatcaphutung = new ArrayList<>();

    public ManHinhLapPhieuXuatKho() {
        this(null);
    }

    // maphieusc: mã yêu cầu (phiếu sửa chữa) cần chọn sẵn, null = chọn yêu cầu đầu tiên
    public ManHinhLapPhieuXuatKho(String maphieusc) {
        setTitle("LẬP PHIẾU XUẤT KHO");
        setSize(850, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();
        taiDuLieu();

        if (maphieusc != null) {
            for (int i = 0; i < dsyeucau.size(); i++) {
                if (dsyeucau.get(i).getMaPhieuSuaChua().equals(maphieusc)) {
                    cbyeucau.setSelectedIndex(i);
                }
            }
        }
    }

    // Lấy các yêu cầu "Chờ xuất kho" và danh sách phụ tùng từ SQL
    private void taiDuLieu() {
        try {
            dsyeucau = phieuSuaChuaDAO.getDSPhieuChoXuatKho();
            dstatcaphutung = phuTungDAO.getAllPhuTung();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage());
        }

        cbyeucau.removeAllItems();
        for (PhieuSuaChua psc : dsyeucau) {
            cbyeucau.addItem(psc.getMaPhieuSuaChua() + " - " + psc.getBienSo());
        }
        if (dsyeucau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hiện không có yêu cầu nào đang chờ xuất kho!");
        }
        xuLyDoiYeuCau();
    }

    private PhuTung timPhuTung(String ma) {
        for (PhuTung pt : dstatcaphutung) {
            if (pt.getMaPhuTung().equalsIgnoreCase(ma)) {
                return pt;
            }
        }
        return null;
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel("LẬP PHIẾU XUẤT KHO");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 22));
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbltieude.setForeground(new Color(44, 62, 80));
        lbltieude.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lbltieude, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel(new BorderLayout(0, 10));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pThongTin = new JPanel(new GridLayout(3, 4, 10, 10));
        pThongTin.setBackground(Color.WHITE);
        pThongTin.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông tin phiếu xuất",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        txtmaphieu = new JTextField();
        txtmaphieu.setEditable(false);
        txtmaphieu.setBackground(new Color(236, 240, 241));

        String homnay = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        txtngayxuat = new JTextField(homnay);
        txtngayxuat.setEditable(false);
        txtngayxuat.setBackground(new Color(236, 240, 241));

        cbyeucau = new JComboBox<>();
        txtnhanvien = new JTextField();
        txtghichu = new JTextField();

        pThongTin.add(new JLabel("Mã Phiếu:"));
        pThongTin.add(txtmaphieu);
        pThongTin.add(new JLabel("Ngày Xuất:"));
        pThongTin.add(txtngayxuat);
        pThongTin.add(new JLabel("Yêu Cầu Xuất Kho:"));
        pThongTin.add(cbyeucau);
        pThongTin.add(new JLabel("Mã Nhân Viên Lập:"));
        pThongTin.add(txtnhanvien);
        pThongTin.add(new JLabel("Ghi Chú:"));
        pThongTin.add(txtghichu);
        pThongTin.add(new JLabel(""));
        pThongTin.add(new JLabel(""));

        JPanel pChiTiet = new JPanel(new GridLayout(2, 4, 10, 10));
        pChiTiet.setBackground(Color.WHITE);
        pChiTiet.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Phụ tùng xuất kho",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        cbphutung = new JComboBox<>();

        txtdonvi = new JTextField();
        txtdonvi.setEditable(false);
        txtdonvi.setBackground(new Color(236, 240, 241));

        txtsltonkho = new JTextField();
        txtsltonkho.setEditable(false);
        txtsltonkho.setBackground(new Color(236, 240, 241));

        txtslxuat = new JTextField();
        txtslxuat.setEditable(false); // xuất đúng số lượng của yêu cầu
        txtslxuat.setBackground(new Color(236, 240, 241));

        pChiTiet.add(new JLabel("Phụ Tùng:"));
        pChiTiet.add(cbphutung);
        pChiTiet.add(new JLabel("Đơn Vị:"));
        pChiTiet.add(txtdonvi);
        pChiTiet.add(new JLabel("SL Tồn Kho:"));
        pChiTiet.add(txtsltonkho);
        pChiTiet.add(new JLabel("SL Xuất:"));
        pChiTiet.add(txtslxuat);

        pCenter.add(pThongTin, BorderLayout.NORTH);
        pCenter.add(pChiTiet, BorderLayout.CENTER);

        JPanel pGiua = new JPanel(new BorderLayout());
        pGiua.setBackground(new Color(245, 245, 245));
        pGiua.add(pCenter, BorderLayout.NORTH);
        add(pGiua, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnquaylai = new JButton("Quay Lại");
        btnquaylai.setPreferredSize(new Dimension(110, 35));
        btnquaylai.setBackground(new Color(149, 165, 166));
        btnquaylai.setForeground(Color.WHITE);
        btnquaylai.setFocusPainted(false);

        btnlammoi = new JButton("Làm Mới");
        btnlammoi.setPreferredSize(new Dimension(110, 35));
        btnlammoi.setBackground(new Color(52, 152, 219));
        btnlammoi.setForeground(Color.WHITE);
        btnlammoi.setFocusPainted(false);

        btnluu = new JButton("Lưu Phiếu");
        btnluu.setPreferredSize(new Dimension(120, 35));
        btnluu.setBackground(new Color(46, 204, 113));
        btnluu.setForeground(Color.WHITE);
        btnluu.setFont(new Font("Arial", Font.BOLD, 12));
        btnluu.setFocusPainted(false);

        pSouth.add(btnquaylai);
        pSouth.add(btnlammoi);
        pSouth.add(btnluu);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btnluu.addActionListener(this);
        btnquaylai.addActionListener(this);
        btnlammoi.addActionListener(this);
        cbphutung.addActionListener(this);
        cbyeucau.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnluu) {
            xuLyLuuPhieu();
        } else if (o == btnquaylai) {
            xuLyQuayLai();
        } else if (o == btnlammoi) {
            xuLyLamMoi();
        } else if (o == cbphutung) {
            xuLyDoiPhuTung();
        } else if (o == cbyeucau) {
            xuLyDoiYeuCau();
        }
    }

    // Đổi yêu cầu -> nạp lại các phụ tùng cần xuất của yêu cầu đó
    private void xuLyDoiYeuCau() {
        dsphutungxuat = new ArrayList<>();
        int vitri = cbyeucau.getSelectedIndex();
        if (vitri >= 0) {
            String maphieusc = dsyeucau.get(vitri).getMaPhieuSuaChua();
            txtmaphieu.setText(maphieusc);
            try {
                dsphutungxuat = danhSachPhuTungDAO.getDSPhuTungByMaPhieu(maphieusc);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Lỗi tải phụ tùng của yêu cầu: " + e.getMessage());
            }
        } else {
            txtmaphieu.setText("");
        }

        cbphutung.removeAllItems();
        for (DanhSachPhuTung ct : dsphutungxuat) {
            PhuTung pt = timPhuTung(ct.getMaPhuTung());
            String ten = (pt == null) ? "" : pt.getTenPhuTung();
            cbphutung.addItem(ct.getMaPhuTung() + " - " + ten);
        }
        xuLyDoiPhuTung();
    }

    private void xuLyDoiPhuTung() {
        int vitri = cbphutung.getSelectedIndex();
        if (vitri < 0) {
            txtdonvi.setText("");
            txtsltonkho.setText("");
            txtslxuat.setText("");
            return;
        }
        DanhSachPhuTung ct = dsphutungxuat.get(vitri);
        PhuTung pt = timPhuTung(ct.getMaPhuTung());
        txtdonvi.setText(pt == null ? "" : pt.getDonViTinh());
        txtsltonkho.setText(pt == null ? "0" : String.valueOf(pt.getSoLuongTon()));
        txtslxuat.setText(String.valueOf(ct.getSoLuongPhuTung()));
    }

    private void xuLyLuuPhieu() {
        if (cbyeucau.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this,
                    "Không có yêu cầu xuất kho nào để lập phiếu!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String manv = txtnhanvien.getText().trim();
        if (manv.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập mã nhân viên lập phiếu!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtnhanvien.requestFocus();
            return;
        }
        try {
            if (thuKhoDAO.timThuKhoTheoMa(manv) == null) {
                JOptionPane.showMessageDialog(this,
                        "Mã nhân viên " + manv + " không tồn tại trong danh sách thủ kho!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtnhanvien.requestFocus();
                return;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi kiểm tra nhân viên: " + e.getMessage());
            return;
        }

        if (dsphutungxuat.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Yêu cầu này không có phụ tùng nào cần xuất!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra tồn kho của tất cả phụ tùng trong yêu cầu
        for (DanhSachPhuTung ct : dsphutungxuat) {
            PhuTung pt = timPhuTung(ct.getMaPhuTung());
            int tonkho = (pt == null) ? 0 : pt.getSoLuongTon();
            if (ct.getSoLuongPhuTung() > tonkho) {
                JOptionPane.showMessageDialog(this,
                        "Phụ tùng " + ct.getMaPhuTung() + " cần xuất " + ct.getSoLuongPhuTung()
                                + " nhưng tồn kho chỉ còn " + tonkho + "!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        String maphieusc = txtmaphieu.getText();
        try {
            if (!phieuSuaChuaDAO.xacNhanXuatKho(maphieusc)) {
                JOptionPane.showMessageDialog(this,
                        "Xuất kho thất bại! Tồn kho không đủ hoặc phiếu không còn ở trạng thái 'Chờ xuất kho'.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi lưu phiếu xuất: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Xuất kho cho phiếu " + maphieusc + " thành công!");
        this.dispose();
    }

    private void xuLyLamMoi() {
        txtghichu.setText("");
        taiDuLieu();
    }

    private void xuLyQuayLai() {
        this.dispose();
    }

    public static void main(String[] args) {
        ManHinhLapPhieuXuatKho form = new ManHinhLapPhieuXuatKho();
        form.setVisible(true);
    }
}
