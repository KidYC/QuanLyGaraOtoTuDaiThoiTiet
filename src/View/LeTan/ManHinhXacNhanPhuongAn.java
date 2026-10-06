package View.LeTan;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManHinhXacNhanPhuongAn extends JFrame implements ActionListener {

    private JLabel lblTitle;

    private JTextField txtMaPhieu;
    private JTextField txtTenKH;
    private JTextField txtBienSo;
    private JTextField txtTongTien;

    private JRadioButton radDongY;
    private JRadioButton radTuChoi;
    private JTextArea txtGhiChu;

    private JButton btnQuayLai;
    private JButton btnHuyBo;
    private JButton btnXacNhan;

    public ManHinhXacNhanPhuongAn(String maPhieu, String tenKH, String bienSo, String tongTien) {
        setTitle("XÁC NHẬN PHƯƠNG ÁN SỬA CHỮA");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();

        txtMaPhieu.setText(maPhieu != null ? maPhieu : "KH001");
        txtTenKH.setText(tenKH != null ? tenKH : "Nguyễn Văn A");
        txtBienSo.setText(bienSo != null ? bienSo : "51F-123.45");
        txtTongTien.setText(tongTien != null ? tongTien : "1,430,000 VNĐ");
    }

    public ManHinhXacNhanPhuongAn() {
        this("KH001", "Nguyễn Văn A", "51F-123.45", "1,430,000 VNĐ");
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        //pNorth
        lblTitle = new JLabel("XÁC NHẬN PHƯƠNG ÁN SỬA CHỮA");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        //pSouth
        JPanel pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 30, 10, 30));

        JPanel pThongTin = new JPanel(new GridBagLayout());
        pThongTin.setBackground(Color.WHITE);
        pThongTin.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Tóm tắt Báo giá",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 0; gbc.gridx = 0;
        pThongTin.add(new JLabel("Mã Phiếu:"), gbc);
        gbc.gridx = 1;
        txtMaPhieu = new JTextField(12);
        txtMaPhieu.setEditable(false);
        txtMaPhieu.setBackground(new Color(236, 240, 241));
        pThongTin.add(txtMaPhieu, gbc);

        gbc.gridx = 2;
        pThongTin.add(new JLabel("Tổng Tiền:"), gbc);
        gbc.gridx = 3;
        txtTongTien = new JTextField(12);
        txtTongTien.setEditable(false);
        txtTongTien.setFont(new Font("Arial", Font.BOLD, 14));
        txtTongTien.setForeground(new Color(192, 57, 43));
        txtTongTien.setBackground(new Color(236, 240, 241));
        pThongTin.add(txtTongTien, gbc);

        gbc.gridy = 1; gbc.gridx = 0;
        pThongTin.add(new JLabel("Tên KH:"), gbc);
        gbc.gridx = 1;
        txtTenKH = new JTextField(12);
        txtTenKH.setEditable(false);
        pThongTin.add(txtTenKH, gbc);

        gbc.gridx = 2;
        pThongTin.add(new JLabel("Biển Số Xe:"), gbc);
        gbc.gridx = 3;
        txtBienSo = new JTextField(12);
        txtBienSo.setEditable(false);
        pThongTin.add(txtBienSo, gbc);

        pCenter.add(pThongTin);
        pCenter.add(Box.createVerticalStrut(15));

        JPanel pXacNhan = new JPanel(new BorderLayout(10, 10));
        pXacNhan.setBackground(Color.WHITE);
        pXacNhan.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Phản hồi từ Khách hàng",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        JPanel pRadio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pRadio.setBackground(Color.WHITE);
        radDongY = new JRadioButton("Khách hàng Đồng ý sửa chữa", true);
        radDongY.setFont(new Font("Arial", Font.BOLD, 13));
        radDongY.setBackground(Color.WHITE);
        radTuChoi = new JRadioButton("Khách hàng Từ chối");
        radTuChoi.setFont(new Font("Arial", Font.BOLD, 13));
        radTuChoi.setBackground(Color.WHITE);

        ButtonGroup bg = new ButtonGroup();
        bg.add(radDongY);
        bg.add(radTuChoi);

        pRadio.add(radDongY);
        pRadio.add(Box.createHorizontalStrut(30));
        pRadio.add(radTuChoi);

        pXacNhan.add(pRadio, BorderLayout.NORTH);

        JPanel pGhiChu = new JPanel(new BorderLayout(5, 5));
        pGhiChu.setBackground(Color.WHITE);
        pGhiChu.add(new JLabel("Ghi chú / Lý do (nếu từ chối):"), BorderLayout.NORTH);
        txtGhiChu = new JTextArea(3, 20);
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        pGhiChu.add(new JScrollPane(txtGhiChu), BorderLayout.CENTER);

        pXacNhan.add(pGhiChu, BorderLayout.CENTER);

        pCenter.add(pXacNhan);

        add(pCenter, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnQuayLai = new JButton("Quay Lại");
        btnQuayLai.setPreferredSize(new Dimension(110, 35));
        btnQuayLai.setBackground(new Color(149, 165, 166));
        btnQuayLai.setForeground(Color.WHITE);
        btnQuayLai.setFocusPainted(false);
        btnQuayLai.setFont(new Font("Arial", Font.BOLD, 12));

        btnHuyBo = new JButton("Hủy Phiếu");
        btnHuyBo.setPreferredSize(new Dimension(110, 35));
        btnHuyBo.setBackground(new Color(231, 76, 60));
        btnHuyBo.setForeground(Color.WHITE);
        btnHuyBo.setFocusPainted(false);
        btnHuyBo.setFont(new Font("Arial", Font.BOLD, 12));
        btnHuyBo.setVisible(false); // Sẽ hiện khi chọn Từ chối

        btnXacNhan = new JButton("Xác Nhận Sửa Chữa");
        btnXacNhan.setPreferredSize(new Dimension(160, 35));
        btnXacNhan.setBackground(new Color(46, 204, 113));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setFont(new Font("Arial", Font.BOLD, 12));
        btnXacNhan.setFocusPainted(false);

        pSouth.add(btnQuayLai);
        pSouth.add(btnHuyBo);
        pSouth.add(btnXacNhan);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btnQuayLai.addActionListener(this);
        btnXacNhan.addActionListener(this);
        btnHuyBo.addActionListener(this);

        radDongY.addActionListener(e -> {
            btnXacNhan.setVisible(true);
            btnHuyBo.setVisible(false);
        });

        radTuChoi.addActionListener(e -> {
            btnXacNhan.setVisible(false);
            btnHuyBo.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnQuayLai) {
            xuLyQuayLai();
        } else if (source == btnXacNhan) {
            xuLyXacNhan();
        } else if (source == btnHuyBo) {
            xuLyHuyBo();
        }
    }

    private void xuLyQuayLai() {
        this.dispose();
        ManHinhLapBaoGia formLapBaoGia = new ManHinhLapBaoGia(txtMaPhieu.getText(), txtBienSo.getText(), txtTenKH.getText());
        formLapBaoGia.setVisible(true);
    }

    private void xuLyXacNhan() {
        JOptionPane.showMessageDialog(this,
                "Xác nhận thành công!\nXe " + txtBienSo.getText() + " đã được chuyển sang trạng thái Đang sửa chữa.",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);

         TrangChuLeTan Home = new TrangChuLeTan();
         Home.setVisible(true);
        this.dispose();

    }

    private void xuLyHuyBo() {
        if(txtGhiChu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lý do khách hàng từ chối vào mục Ghi chú!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opt = JOptionPane.showConfirmDialog(this,
                "Khách hàng từ chối sửa chữa. Bạn có chắc chắn muốn Hủy phiếu và trả xe?",
                "Xác nhận Hủy", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if(opt == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Đã hủy phiếu và hoàn tất thủ tục trả xe.");
             TrangChuLeTan Home = new TrangChuLeTan();
             Home.setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        ManHinhXacNhanPhuongAn form = new ManHinhXacNhanPhuongAn();
        form.setVisible(true);
    }
}