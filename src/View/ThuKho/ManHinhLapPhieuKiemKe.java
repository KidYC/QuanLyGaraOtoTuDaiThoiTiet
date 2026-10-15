package View.ThuKho;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ManHinhLapPhieuKiemKe extends JFrame implements ActionListener {

    private JLabel lbltieude;

    private JTextField txtmaphieu;
    private JTextField txtngaykiemke;
    private JTextField txtnguoikiemke;
    private JTextField txtghichu;

    private JComboBox<String> cbphutung;
    private JTextField txtslhethong;
    private JTextField txtslthucte;
    private JTextField txtchenhlech;
    private JTextField txtlydo;

    private JButton btnluu;
    private JButton btnquaylai;
    private JButton btnlammoi;

    private final String[] dsphutung = {"PT001 - Dầu nhớt Castrol", "PT002 - Lọc gió", "PT003 - Bugi"};
    private final int[] dssoluong = {20, 15, 5};

    public ManHinhLapPhieuKiemKe() {
        this("KK004", "Nguyễn Văn A");
    }

    public ManHinhLapPhieuKiemKe(String maphieu, String nguoikiemke) {
        setTitle("LẬP PHIẾU KIỂM KÊ");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();

        if (maphieu != null) txtmaphieu.setText(maphieu);
        if (nguoikiemke != null) txtnguoikiemke.setText(nguoikiemke);
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lbltieude = new JLabel("LẬP PHIẾU KIỂM KÊ");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 22));
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbltieude.setForeground(new Color(44, 62, 80));
        lbltieude.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lbltieude, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel(new BorderLayout(0, 10));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pThongTin = new JPanel(new GridLayout(2, 4, 10, 10));
        pThongTin.setBackground(Color.WHITE);
        pThongTin.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông tin phiếu kiểm kê",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        txtmaphieu = new JTextField("KK004");
        txtmaphieu.setEditable(false);
        txtmaphieu.setBackground(new Color(236, 240, 241));

        String homnay = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        txtngaykiemke = new JTextField(homnay);
        txtngaykiemke.setEditable(false);
        txtngaykiemke.setBackground(new Color(236, 240, 241));

        txtnguoikiemke = new JTextField();
        txtghichu = new JTextField();

        pThongTin.add(new JLabel("Mã Phiếu:"));
        pThongTin.add(txtmaphieu);
        pThongTin.add(new JLabel("Ngày Kiểm Kê:"));
        pThongTin.add(txtngaykiemke);
        pThongTin.add(new JLabel("Người Kiểm Kê:"));
        pThongTin.add(txtnguoikiemke);
        pThongTin.add(new JLabel("Ghi Chú:"));
        pThongTin.add(txtghichu);

        JPanel pChiTiet = new JPanel(new GridLayout(3, 4, 10, 10));
        pChiTiet.setBackground(Color.WHITE);
        pChiTiet.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Phụ tùng kiểm kê",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        cbphutung = new JComboBox<>(dsphutung);

        txtslhethong = new JTextField(String.valueOf(dssoluong[0]));
        txtslhethong.setEditable(false);
        txtslhethong.setBackground(new Color(236, 240, 241));

        txtslthucte = new JTextField();

        txtchenhlech = new JTextField();
        txtchenhlech.setEditable(false);
        txtchenhlech.setBackground(new Color(236, 240, 241));

        txtlydo = new JTextField();

        pChiTiet.add(new JLabel("Phụ Tùng:"));
        pChiTiet.add(cbphutung);
        pChiTiet.add(new JLabel("SL Hệ Thống:"));
        pChiTiet.add(txtslhethong);
        pChiTiet.add(new JLabel("SL Thực Tế:"));
        pChiTiet.add(txtslthucte);
        pChiTiet.add(new JLabel("Chênh Lệch:"));
        pChiTiet.add(txtchenhlech);
        pChiTiet.add(new JLabel("Lý Do Chênh Lệch:"));
        pChiTiet.add(txtlydo);

        pCenter.add(pThongTin, BorderLayout.NORTH);
        pCenter.add(pChiTiet, BorderLayout.CENTER);

        JPanel pCenter2 = new JPanel(new BorderLayout());
        pCenter2.setBackground(new Color(245, 245, 245));
        pCenter2.add(pCenter, BorderLayout.NORTH);
        add(pCenter2, BorderLayout.CENTER);

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
        txtslthucte.addActionListener(this); // nhấn Enter để tính chênh lệch
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
        } else if (o == txtslthucte) {
            tinhChenhLech();
        }
    }

    private void xuLyDoiPhuTung() {
        txtslhethong.setText(String.valueOf(dssoluong[cbphutung.getSelectedIndex()]));
        txtslthucte.setText("");
        txtchenhlech.setText("");
    }

    private boolean tinhChenhLech() {
        try {
            int thucte = Integer.parseInt(txtslthucte.getText().trim());
            if (thucte < 0) throw new NumberFormatException();
            int hethong = Integer.parseInt(txtslhethong.getText().trim());
            txtchenhlech.setText(String.valueOf(thucte - hethong));
            return true;
        } catch (NumberFormatException ex) {
            txtchenhlech.setText("");
            return false;
        }
    }

    private void xuLyLuuPhieu() {
        if (txtnguoikiemke.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập Người kiểm kê!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtnguoikiemke.requestFocus();
            return;
        }

        if (!tinhChenhLech()) {
            JOptionPane.showMessageDialog(this,
                    "Số lượng thực tế phải là số nguyên không âm!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtslthucte.requestFocus();
            return;
        }

        if (!txtchenhlech.getText().equals("0") && txtlydo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Số lượng có chênh lệch, vui lòng nhập lý do!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtlydo.requestFocus();
            return;
        }

        int chon = JOptionPane.showConfirmDialog(this,
                "Lập phiếu kiểm kê " + txtmaphieu.getText() + " thành công!\n"
                        + "Bạn có muốn mở 'Màn hình Danh sách phiếu kiểm kê' ngay không?",
                "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (chon == JOptionPane.YES_OPTION) {
            ManHinhDanhSachPhieuKiemKe UI = new ManHinhDanhSachPhieuKiemKe();
            UI.setVisible(true);
        }
        this.dispose();
    }

    private void xuLyQuayLai() {
        ManHinhDanhSachPhieuKiemKe UI = new ManHinhDanhSachPhieuKiemKe();
        UI.setVisible(true);
        this.dispose();
    }

    private void xuLyLamMoi() {
        txtghichu.setText("");
        cbphutung.setSelectedIndex(0);
        txtslhethong.setText(String.valueOf(dssoluong[0]));
        txtslthucte.setText("");
        txtchenhlech.setText("");
        txtlydo.setText("");
    }

    public static void main(String[] args) {
        ManHinhLapPhieuKiemKe form = new ManHinhLapPhieuKiemKe();
        form.setVisible(true);
    }
}