package View.KeToan;

import DB.connectSQL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManHinhQuanLyCa extends JFrame implements ActionListener {

    private static final String MA_NHAN_VIEN = "KT01";

    private static final Color MAU_NEN = new Color(244, 246, 248);
    private static final Color MAU_THE = Color.WHITE;
    private static final Color MAU_TEXT = new Color(17, 24, 39);
    private static final Color MAU_PHU = new Color(107, 114, 128);
    private static final Color MAU_VIEN = new Color(226, 232, 240);
    private static final Color MAU_XANH = new Color(37, 99, 235);
    private static final Color MAU_XANH_LA = new Color(22, 163, 74);
    private static final Color MAU_DO = new Color(220, 38, 38);

    private static final Font FONT_TIEU_DE =
            new Font("Segoe UI", Font.BOLD, 24);

    private static final Font FONT_NHAN =
            new Font("Segoe UI", Font.PLAIN, 14);

    private static final Font FONT_NHAN_DAM =
            new Font("Segoe UI", Font.BOLD, 14);

    private JLabel lblTrangThai;
    private JLabel lblNhanVien;
    private JLabel lblThoiGianMo;
    private JLabel lblTienDauCa;
    private JLabel lblThoiGianDong;
    private JLabel lblTienCuoiCa;
    private JLabel lblChenhLech;

    private JButton btnMoCa;
    private JButton btnDongCa;
    private JButton btnTaiLai;

    private String maCaDangMo;

    public ManHinhQuanLyCa() {
        setTitle("Quản lý ca làm việc");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(MAU_NEN);

        taoGiaoDien();
        taiThongTinCa();
    }

    private void taoGiaoDien() {
        // Tiêu đề
        JPanel pTieuDe = new JPanel(new BorderLayout());
        pTieuDe.setBackground(MAU_NEN);
        pTieuDe.setBorder(new EmptyBorder(25, 30, 15, 30));

        JLabel lblTieuDe = new JLabel("QUẢN LÝ CA LÀM VIỆC");
        lblTieuDe.setFont(FONT_TIEU_DE);
        lblTieuDe.setForeground(MAU_TEXT);

        JLabel lblMoTa = new JLabel(
                "Theo dõi và quản lý ca làm việc của nhân viên kế toán"
        );
        lblMoTa.setFont(FONT_NHAN);
        lblMoTa.setForeground(MAU_PHU);

        JPanel pTieuDeNoiDung = new JPanel();
        pTieuDeNoiDung.setLayout(new BoxLayout(pTieuDeNoiDung, BoxLayout.Y_AXIS));
        pTieuDeNoiDung.setBackground(MAU_NEN);
        pTieuDeNoiDung.add(lblTieuDe);
        pTieuDeNoiDung.add(Box.createVerticalStrut(8));
        pTieuDeNoiDung.add(lblMoTa);

        pTieuDe.add(pTieuDeNoiDung, BorderLayout.WEST);
        add(pTieuDe, BorderLayout.NORTH);

        JPanel pNoiDung = new JPanel();
        pNoiDung.setLayout(new BoxLayout(pNoiDung, BoxLayout.Y_AXIS));
        pNoiDung.setBackground(MAU_NEN);
        pNoiDung.setBorder(new EmptyBorder(10, 30, 30, 30));

        JPanel pThongTinCa = taoThe();
        pThongTinCa.setLayout(new BorderLayout(10, 15));
        pThongTinCa.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel lblTieuDeCa = new JLabel("THÔNG TIN CA HIỆN TẠI");
        lblTieuDeCa.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTieuDeCa.setForeground(MAU_TEXT);

        lblTrangThai = new JLabel("Đang kiểm tra...");
        lblTrangThai.setFont(FONT_NHAN_DAM);
        lblTrangThai.setForeground(MAU_PHU);

        JPanel pDauCa = new JPanel(new GridLayout(4, 1, 8, 8));
        pDauCa.setBackground(MAU_THE);

        lblNhanVien = taoLabelThongTin("Nhân viên: --");
        lblThoiGianMo = taoLabelThongTin("Thời gian mở ca: --");
        lblTienDauCa = taoLabelThongTin("Tiền đầu ca: --");
        lblThoiGianDong = taoLabelThongTin("Thời gian đóng ca: --");

        pDauCa.add(lblNhanVien);
        pDauCa.add(lblThoiGianMo);
        pDauCa.add(lblTienDauCa);
        pDauCa.add(lblThoiGianDong);

        JPanel pCuoiCa = new JPanel(new GridLayout(2, 1, 8, 8));
        pCuoiCa.setBackground(MAU_THE);

        lblTienCuoiCa = taoLabelThongTin("Tiền cuối ca: --");
        lblChenhLech = taoLabelThongTin("Chênh lệch: --");

        pCuoiCa.add(lblTienCuoiCa);
        pCuoiCa.add(lblChenhLech);

        JPanel pThongTinChiTiet = new JPanel();
        pThongTinChiTiet.setLayout(new BoxLayout(pThongTinChiTiet, BoxLayout.Y_AXIS));
        pThongTinChiTiet.setBackground(MAU_THE);
        pThongTinChiTiet.add(pDauCa);
        pThongTinChiTiet.add(Box.createVerticalStrut(15));
        pThongTinChiTiet.add(pCuoiCa);

        JPanel pThongTinTren = new JPanel(new BorderLayout());
        pThongTinTren.setBackground(MAU_THE);
        pThongTinTren.add(lblTieuDeCa, BorderLayout.WEST);
        pThongTinTren.add(lblTrangThai, BorderLayout.EAST);

        pThongTinCa.add(pThongTinTren, BorderLayout.NORTH);
        pThongTinCa.add(pThongTinChiTiet, BorderLayout.CENTER);

        JPanel pChucNang = taoThe();
        pChucNang.setLayout(new BorderLayout(10, 20));
        pChucNang.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel lblTieuDeChucNang = new JLabel("CHỨC NĂNG CA LÀM VIỆC");
        lblTieuDeChucNang.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTieuDeChucNang.setForeground(MAU_TEXT);

        JLabel lblHuongDan = new JLabel(
                "Mở ca để bắt đầu làm việc hoặc đóng ca khi kết thúc."
        );
        lblHuongDan.setFont(FONT_NHAN);
        lblHuongDan.setForeground(MAU_PHU);

        JPanel pMoTaChucNang = new JPanel();
        pMoTaChucNang.setLayout(new BoxLayout(pMoTaChucNang, BoxLayout.Y_AXIS));
        pMoTaChucNang.setBackground(MAU_THE);
        pMoTaChucNang.add(lblTieuDeChucNang);
        pMoTaChucNang.add(Box.createVerticalStrut(8));
        pMoTaChucNang.add(lblHuongDan);

        btnMoCa = taoButton("MỞ CA", MAU_XANH_LA);
        btnDongCa = taoButton("ĐÓNG CA", MAU_DO);
        btnTaiLai = taoButton("TẢI LẠI", MAU_XANH);

        btnMoCa.addActionListener(this);
        btnDongCa.addActionListener(this);
        btnTaiLai.addActionListener(this);

        JPanel pNut = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pNut.setBackground(MAU_THE);
        pNut.add(btnMoCa);
        pNut.add(btnDongCa);
        pNut.add(btnTaiLai);

        pChucNang.add(pMoTaChucNang, BorderLayout.NORTH);
        pChucNang.add(pNut, BorderLayout.CENTER);

        pNoiDung.add(pThongTinCa);
        pNoiDung.add(Box.createVerticalStrut(20));
        pNoiDung.add(pChucNang);

        add(pNoiDung, BorderLayout.CENTER);
    }

    private JPanel taoThe() {
        JPanel panel = new JPanel();
        panel.setBackground(MAU_THE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAU_VIEN),
                new EmptyBorder(10, 10, 10, 10)
        ));
        return panel;
    }

    private JLabel taoLabelThongTin(String noiDung) {
        JLabel label = new JLabel(noiDung);
        label.setFont(FONT_NHAN);
        label.setForeground(MAU_TEXT);
        return label;
    }
    private JButton taoButton(String noiDung, Color mauNen) {
        JButton button = new JButton(noiDung);
        button.setFont(FONT_NHAN_DAM);
        button.setForeground(Color.WHITE);
        button.setBackground(mauNen);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 45));
        return button;
    }
    private void taiThongTinCa() {
        String sql = """
                SELECT TOP 1
                    C.maCa,
                    C.thoiGianMoCa,
                    C.thoiGianDongCa,
                    C.tienDauCa,
                    C.tienCuoiCa,
                    C.chenhLech,
                    C.trangThai,
                    K.hoTen
                FROM CA_LAM_VIEC C
                INNER JOIN KE_TOAN K
                    ON C.maNhanVien = K.maNhanVien
                WHERE C.maNhanVien = ?
                    AND C.trangThai = N'Đang mở'
                ORDER BY C.thoiGianMoCa DESC
                """;

        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, MA_NHAN_VIEN);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    maCaDangMo = rs.getString("maCa");

                    String hoTen = rs.getString("hoTen");
                    Date thoiGianMo = rs.getTimestamp("thoiGianMoCa");
                    BigDecimal tienDau = rs.getBigDecimal("tienDauCa");

                    lblTrangThai.setText("ĐANG MỞ CA");
                    lblTrangThai.setForeground(MAU_XANH_LA);

                    lblNhanVien.setText("Nhân viên: " + hoTen + " (" + MA_NHAN_VIEN + ")");
                    lblThoiGianMo.setText("Thời gian mở ca: " + dinhDangNgay(thoiGianMo));
                    lblTienDauCa.setText("Tiền đầu ca: " + dinhDangTien(tienDau));

                    lblThoiGianDong.setText("Thời gian đóng ca: Chưa đóng");
                    lblTienCuoiCa.setText("Tiền cuối ca: Chưa có");
                    lblChenhLech.setText("Chênh lệch: Chưa có");

                    btnMoCa.setEnabled(false);
                    btnDongCa.setEnabled(true);
                } else {
                    maCaDangMo = null;

                    lblTrangThai.setText("CHƯA MỞ CA");
                    lblTrangThai.setForeground(MAU_PHU);

                    lblNhanVien.setText("Nhân viên: " + MA_NHAN_VIEN);
                    lblThoiGianMo.setText("Thời gian mở ca: --");
                    lblTienDauCa.setText("Tiền đầu ca: --");
                    lblThoiGianDong.setText("Thời gian đóng ca: --");
                    lblTienCuoiCa.setText("Tiền cuối ca: --");
                    lblChenhLech.setText("Chênh lệch: --");

                    btnMoCa.setEnabled(true);
                    btnDongCa.setEnabled(false);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không thể tải thông tin ca làm việc!\n" + e.getMessage(),
                    "Lỗi cơ sở dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void moCa() {
        if (maCaDangMo != null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nhân viên đang có một ca chưa đóng!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String input = JOptionPane.showInputDialog(
                this,
                "Nhập số tiền đầu ca:",
                "Mở ca",
                JOptionPane.QUESTION_MESSAGE
        );

        if (input == null) {
            return;
        }

        BigDecimal tienDauCa = chuyenSangTien(input);

        if (tienDauCa == null || tienDauCa.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Số tiền không hợp lệ. Vui lòng nhập số tiền không âm.",
                    "Dữ liệu không hợp lệ",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String maCa = "CA" + System.currentTimeMillis();

        String sql = """
                INSERT INTO CA_LAM_VIEC
                    (maCa, maNhanVien, thoiGianMoCa, tienDauCa, trangThai)
                VALUES
                    (?, ?, GETDATE(), ?, N'Đang mở')
                """;

        try (Connection conn = connectSQL.getConnection()) {
            // Kiểm tra nhân viên đã có ca đang mở hay chưa
            String sqlKiemTra = """
                    SELECT COUNT(*)
                    FROM CA_LAM_VIEC
                    WHERE maNhanVien = ?
                        AND trangThai = N'Đang mở'
                    """;

            try (PreparedStatement psKiemTra = conn.prepareStatement(sqlKiemTra)) {
                psKiemTra.setString(1, MA_NHAN_VIEN);

                try (ResultSet rs = psKiemTra.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Nhân viên đã có ca đang mở!",
                                "Không thể mở ca",
                                JOptionPane.WARNING_MESSAGE
                        );
                        taiThongTinCa();
                        return;
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, maCa);
                ps.setString(2, MA_NHAN_VIEN);
                ps.setBigDecimal(3, tienDauCa);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Mở ca thành công!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    taiThongTinCa();
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không thể mở ca!\n" + e.getMessage(),
                    "Lỗi cơ sở dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void dongCa() {
        if (maCaDangMo == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Hiện tại không có ca nào đang mở!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String input = JOptionPane.showInputDialog(
                this,
                "Nhập số tiền cuối ca:",
                "Đóng ca",
                JOptionPane.QUESTION_MESSAGE
        );

        if (input == null) {
            return;
        }

        BigDecimal tienCuoiCa = chuyenSangTien(input);

        if (tienCuoiCa == null || tienCuoiCa.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Số tiền không hợp lệ. Vui lòng nhập số tiền không âm.",
                    "Dữ liệu không hợp lệ",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int xacNhan = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn đóng ca?",
                "Xác nhận đóng ca",
                JOptionPane.YES_NO_OPTION
        );

        if (xacNhan != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = """
                UPDATE CA_LAM_VIEC
                SET
                    thoiGianDongCa = GETDATE(),
                    tienCuoiCa = ?,
                    chenhLech = ? - tienDauCa,
                    trangThai = N'Đã đóng'
                WHERE maCa = ?
                    AND maNhanVien = ?
                    AND trangThai = N'Đang mở'
                """;

        try (
                Connection conn = connectSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setBigDecimal(1, tienCuoiCa);
            ps.setBigDecimal(2, tienCuoiCa);
            ps.setString(3, maCaDangMo);
            ps.setString(4, MA_NHAN_VIEN);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Đóng ca thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );

                taiThongTinCa();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Không tìm thấy ca đang mở để đóng!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );

                taiThongTinCa();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không thể đóng ca!\n" + e.getMessage(),
                    "Lỗi cơ sở dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private BigDecimal chuyenSangTien(String input) {
        try {
            String soTien = input.trim()
                    .replace(".", "")
                    .replace(",", "")
                    .replace(" ", "");

            if (soTien.isEmpty()) {
                return null;
            }

            return new BigDecimal(soTien);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    private String dinhDangTien(BigDecimal tien) {
        if (tien == null) {
            return "--";
        }

        DecimalFormat df = new DecimalFormat("#,##0");
        return df.format(tien) + " VNĐ";
    }
    private String dinhDangNgay(Date ngay) {
        if (ngay == null) {
            return "--";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return sdf.format(ngay);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnMoCa) {
            moCa();
        } else if (source == btnDongCa) {
            dongCa();
        } else if (source == btnTaiLai) {
            taiThongTinCa();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ManHinhQuanLyCa().setVisible(true);
        });
    }
}