package View.ThuKho;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ManHinhLapPhieuNhapKho extends JFrame implements ActionListener {

    private JLabel lbltieude;

    private JTextField txtmaphieu;
    private JTextField txtngaynhap;
    private JComboBox<String> cbnhacungcap;
    private JTextField txtnhanvien;
    private JTextField txtghichu;

    private JComboBox<String> cbphutung;
    private JTextField txtdonvi;
    private JTextField txtsoluong;
    private JTextField txtdongia;
    private JTextField txtthanhtien;

    private JButton btnquaylai;
    private JButton btnlammoi;
    private JButton btnluu;

    private final String[] dsphutung = {
            "PT001 - Dầu nhớt Castrol", "PT002 - Lọc gió", "PT003 - Bugi"};
    private final String[] dsdonvi = {"Chai", "Cái", "Cái"};

    private DecimalFormat dinhdang = new DecimalFormat("#,###");

    public ManHinhLapPhieuNhapKho() {
        this("PN004", "Nguyễn Văn A");
    }

    public ManHinhLapPhieuNhapKho(String maphieu, String nhanvien) {
        setTitle("LẬP PHIẾU NHẬP KHO");
        setSize(850, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();

        if (maphieu != null) txtmaphieu.setText(maphieu);
        if (nhanvien != null) txtnhanvien.setText(nhanvien);
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel("LẬP PHIẾU NHẬP KHO");
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
                "Thông tin phiếu nhập",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        txtmaphieu = new JTextField("PN004");
        txtmaphieu.setEditable(false);
        txtmaphieu.setBackground(new Color(236, 240, 241));

        String homnay = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        txtngaynhap = new JTextField(homnay);
        txtngaynhap.setEditable(false);
        txtngaynhap.setBackground(new Color(236, 240, 241));

        cbnhacungcap = new JComboBox<>(new String[]{"Công ty Castrol VN", "Đại lý Hòa Phát", "Bosch Việt Nam"});
        txtnhanvien = new JTextField();
        txtghichu = new JTextField();

        pThongTin.add(new JLabel("Mã Phiếu:"));
        pThongTin.add(txtmaphieu);
        pThongTin.add(new JLabel("Ngày Nhập:"));
        pThongTin.add(txtngaynhap);
        pThongTin.add(new JLabel("Nhà Cung Cấp:"));
        pThongTin.add(cbnhacungcap);
        pThongTin.add(new JLabel("Nhân Viên Lập:"));
        pThongTin.add(txtnhanvien);
        pThongTin.add(new JLabel("Ghi Chú:"));
        pThongTin.add(txtghichu);
        pThongTin.add(new JLabel(""));
        pThongTin.add(new JLabel(""));

        JPanel pChiTiet = new JPanel(new GridLayout(3, 4, 10, 10));
        pChiTiet.setBackground(Color.WHITE);
        pChiTiet.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Phụ tùng nhập kho",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        cbphutung = new JComboBox<>(dsphutung);

        txtdonvi = new JTextField(dsdonvi[0]);
        txtdonvi.setEditable(false);
        txtdonvi.setBackground(new Color(236, 240, 241));

        txtsoluong = new JTextField();
        txtdongia = new JTextField();

        txtthanhtien = new JTextField();
        txtthanhtien.setEditable(false);
        txtthanhtien.setBackground(new Color(236, 240, 241));

        pChiTiet.add(new JLabel("Phụ Tùng:"));
        pChiTiet.add(cbphutung);
        pChiTiet.add(new JLabel("Đơn Vị:"));
        pChiTiet.add(txtdonvi);
        pChiTiet.add(new JLabel("Số Lượng:"));
        pChiTiet.add(txtsoluong);
        pChiTiet.add(new JLabel("Đơn Giá:"));
        pChiTiet.add(txtdongia);
        pChiTiet.add(new JLabel("Thành Tiền:"));
        pChiTiet.add(txtthanhtien);
        pChiTiet.add(new JLabel(""));
        pChiTiet.add(new JLabel(""));

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
        txtsoluong.addActionListener(this);  // nhấn Enter để tính thành tiền
        txtdongia.addActionListener(this);
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
        } else if (o == txtsoluong || o == txtdongia) {
            tinhThanhTien();
        }
    }

    private void xuLyDoiPhuTung() {
        txtdonvi.setText(dsdonvi[cbphutung.getSelectedIndex()]);
    }

    private boolean tinhThanhTien() {
        try {
            int soluong = Integer.parseInt(txtsoluong.getText().trim());
            long dongia = Long.parseLong(txtdongia.getText().replace(",", "").trim());
            if (soluong <= 0 || dongia <= 0) throw new NumberFormatException();
            txtthanhtien.setText(dinhdang.format(soluong * dongia));
            return true;
        } catch (NumberFormatException ex) {
            txtthanhtien.setText("");
            return false;
        }
    }

    private void xuLyLuuPhieu() {
        if (txtnhanvien.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập Nhân viên lập phiếu!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtnhanvien.requestFocus();
            return;
        }

        if (!tinhThanhTien()) {
            JOptionPane.showMessageDialog(this,
                    "Số lượng và đơn giá phải là số lớn hơn 0!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtsoluong.requestFocus();
            return;
        }

        int chon = JOptionPane.showConfirmDialog(this,
                "Lập phiếu nhập " + txtmaphieu.getText() + " thành công!\n"
                        + "Bạn có muốn mở 'Màn hình Danh sách phiếu nhập kho' ngay không?",
                "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (chon == JOptionPane.YES_OPTION) {
            ManHinhDanhSachPhieuNhapKho UI = new ManHinhDanhSachPhieuNhapKho();
            UI.setVisible(true);
        }
        this.dispose();
    }

    private void xuLyLamMoi() {
        txtghichu.setText("");
        cbnhacungcap.setSelectedIndex(0);
        cbphutung.setSelectedIndex(0);
        txtdonvi.setText(dsdonvi[0]);
        txtsoluong.setText("");
        txtdongia.setText("");
        txtthanhtien.setText("");
    }

    private void xuLyQuayLai() {
        ManHinhDanhSachPhieuNhapKho UI = new ManHinhDanhSachPhieuNhapKho();
        UI.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        ManHinhLapPhieuNhapKho form = new ManHinhLapPhieuNhapKho();
        form.setVisible(true);
    }
}