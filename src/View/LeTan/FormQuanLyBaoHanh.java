package View.LeTan;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormQuanLyBaoHanh extends JFrame implements ActionListener {

    private JLabel lblTitle;

    private JTextField txtMaPhieu;
    private JTextField txtBienSo;
    private JTextField txtTenKH;
    private JTextField txtNgayKichHoat;

    private JTable tblBaoHanh;
    private DefaultTableModel modelBaoHanh;

    private JTextArea txtDieuKhoan;

    private JButton btnQuayLai;
    private JButton btnHoanTat;

    public FormQuanLyBaoHanh(String maPhieu, String bienSo, String tenKH) {
        setTitle("QUẢN LÝ BẢO HÀNH");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();

        txtMaPhieu.setText(maPhieu != null ? maPhieu : "KH005");
        txtBienSo.setText(bienSo != null ? bienSo : "51H-234.56");
        txtTenKH.setText(tenKH != null ? tenKH : "Phạm Văn D");

        loadData();
    }

    public FormQuanLyBaoHanh() {
        this("KH005", "51H-234.56", "Phạm Văn D");
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lblTitle = new JLabel("KÍCH HOẠT & QUẢN LÝ BẢO HÀNH");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 10, 20));

        //Thông tin Khách Hàng
        JPanel pThongTin = new JPanel(new GridBagLayout());
        pThongTin.setBackground(Color.WHITE);
        pThongTin.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông tin Hồ sơ",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 0; gbc.gridx = 0;
        pThongTin.add(new JLabel("Mã Phiếu:"), gbc);
        gbc.gridx = 1;
        txtMaPhieu = new JTextField(15);
        txtMaPhieu.setEditable(false);
        txtMaPhieu.setBackground(new Color(236, 240, 241));
        pThongTin.add(txtMaPhieu, gbc);

        gbc.gridx = 2;
        pThongTin.add(new JLabel("Biển Số Xe:"), gbc);
        gbc.gridx = 3;
        txtBienSo = new JTextField(15);
        txtBienSo.setEditable(false);
        pThongTin.add(txtBienSo, gbc);

        gbc.gridy = 1; gbc.gridx = 0;
        pThongTin.add(new JLabel("Tên Khách Hàng:"), gbc);
        gbc.gridx = 1;
        txtTenKH = new JTextField(15);
        txtTenKH.setEditable(false);
        pThongTin.add(txtTenKH, gbc);

        gbc.gridx = 2;
        pThongTin.add(new JLabel("Ngày Kích Hoạt:"), gbc);
        gbc.gridx = 3;
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        txtNgayKichHoat = new JTextField(now, 15);
        txtNgayKichHoat.setEditable(false);
        txtNgayKichHoat.setBackground(new Color(255, 250, 205));
        pThongTin.add(txtNgayKichHoat, gbc);

        pCenter.add(pThongTin);
        pCenter.add(Box.createVerticalStrut(15));

        // Tạo Bảng
        JPanel pBang = new JPanel(new BorderLayout(5, 5));
        pBang.setBackground(Color.WHITE);
        pBang.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Danh sách Phụ tùng / Dịch vụ áp dụng bảo hành",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(5, 5, 5, 5)
        ));

        String[] cols = {"STT", "Mã PT/DV", "Tên Hạng Mục", "Thời Hạn", "Ngày Hết Hạn", "Trạng Thái"};
        modelBaoHanh = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tblBaoHanh = new JTable(modelBaoHanh);
        tblBaoHanh.setRowHeight(25);
        tblBaoHanh.setFont(new Font("Arial", Font.PLAIN, 13));

        JTableHeader header = tblBaoHanh.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tblBaoHanh);
        scrollPane.setPreferredSize(new Dimension(800, 120));
        pBang.add(scrollPane, BorderLayout.CENTER);

        pCenter.add(pBang);
        pCenter.add(Box.createVerticalStrut(15));

        //Phần Ghi Chú
        JPanel pGhiChu = new JPanel(new BorderLayout(5, 5));
        pGhiChu.setBackground(new Color(245, 245, 245));
        pGhiChu.add(new JLabel("Điều khoản / Ghi chú bảo hành:"), BorderLayout.NORTH);

        txtDieuKhoan = new JTextArea(3, 20);
        txtDieuKhoan.setText("Bảo hành các lỗi do nhà sản xuất hoặc do quá trình lắp ráp tại xưởng.\nKhông bảo hành đối với các trường hợp hao mòn tự nhiên, tai nạn hoặc do khách hàng tự ý can thiệp.");
        txtDieuKhoan.setLineWrap(true);
        txtDieuKhoan.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        pGhiChu.add(new JScrollPane(txtDieuKhoan), BorderLayout.CENTER);

        pCenter.add(pGhiChu);
        add(pCenter, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnQuayLai = new JButton("Quay Lại");
        btnQuayLai.setPreferredSize(new Dimension(110, 38));
        btnQuayLai.setBackground(new Color(149, 165, 166));
        btnQuayLai.setForeground(Color.WHITE);
        btnQuayLai.setFont(new Font("Arial", Font.BOLD, 13));
        btnQuayLai.setFocusPainted(false);

        btnHoanTat = new JButton("Kích Hoạt Bảo Hành & Về Trang Chủ");
        btnHoanTat.setPreferredSize(new Dimension(270, 38));
        btnHoanTat.setBackground(new Color(46, 204, 113));
        btnHoanTat.setForeground(Color.WHITE);
        btnHoanTat.setFont(new Font("Arial", Font.BOLD, 13));
        btnHoanTat.setFocusPainted(false);

        pSouth.add(btnQuayLai);
        pSouth.add(btnHoanTat);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btnQuayLai.addActionListener(this);
        btnHoanTat.addActionListener(this);
    }

    private void loadData() {
        modelBaoHanh.addRow(new Object[]{1, "PT015", "Bố thắng trước Toyota", "6 Tháng", "06/04/2027", "Đang hiệu lực"});
        modelBaoHanh.addRow(new Object[]{2, "PT088", "Bình ắc quy GS", "12 Tháng", "06/10/2027", "Đang hiệu lực"});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnQuayLai) {
            xuLyQuayLai();
        } else if (source == btnHoanTat) {
            xuLyHoanTat();
        }
    }

    private void xuLyQuayLai() {
        this.dispose();
        ManHinhChiTietBanGiao UI = new ManHinhChiTietBanGiao(txtMaPhieu.getText(), txtBienSo.getText());
        UI.setVisible(true);
    }

    private void xuLyHoanTat() {
        JOptionPane.showMessageDialog(this,
                "Đã kích hoạt bảo hành thành công cho xe " + txtBienSo.getText() + "!\nQuy trình dịch vụ đã hoàn tất.",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);

         TrangChuLeTan Home = new TrangChuLeTan();
         Home.setVisible(true);
        this.dispose();

    }

    public static void main(String[] args) {
        FormQuanLyBaoHanh form = new FormQuanLyBaoHanh();
        form.setVisible(true);
    }
}