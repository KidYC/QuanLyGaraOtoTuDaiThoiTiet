package View.KyThuatVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
public class TrangChuKyThuatVien extends JFrame {
    private JPanel headerPanel;
    private JPanel bodyPanel;
    private JPanel menuPanel;
    private JPanel contentPanel;
    private JPanel cardPanel;
    private JLabel titleLb;
    private JLabel userLb;
    private JLabel welcomeLb;
    private JLabel subWelcomeLb;
    private JButton trangChuBtn;
    private JButton kiemTraXeBtn;
    private JButton lapPhieuBtn;
    private JButton tienDoBtn;
    private JButton chatLuongBtn;
    private JButton dangXuatBtn;
    private JButton moKiemTraBtn;
    private JButton moLapPhieuBtn;
    private JButton moTienDoBtn;
    private JButton moChatLuongBtn;
    private Color backgroundColor = new Color(241, 245, 249);
    private Color primaryColor = new Color(37, 99, 235);
    private Color textColor = new Color(15, 23, 42);
    private Color secondaryColor = new Color(71, 85, 105);
    private Color borderColor = new Color(203, 213, 225);
    private Color dangerColor = new Color(220, 38, 38);
    private Color successColor = new Color(22, 163, 74);
    private Color purpleColor = new Color(124, 58, 237);
    private Color menuColor = new Color(30, 41, 59);
    private String tenKyThuatVien = "Nguyễn Văn An";

    public TrangChuKyThuatVien() {

        setTitle("Trang chủ kỹ thuật viên - Garage Management");
        setSize(1050, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout(0, 15));

        taoHeader();
        taoBody();
        taoMenu();
        taoContent();
        taoSuKien();

        setVisible(true);
    }

    private void taoHeader() {

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));
        headerPanel.setPreferredSize(new Dimension(1050, 75));

        titleLb = new JLabel("TRANG CHỦ KỸ THUẬT VIÊN");
        titleLb.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLb.setForeground(textColor);

        userLb = new JLabel("KTV: " + tenKyThuatVien);
        userLb.setFont(new Font("Segoe UI", Font.BOLD, 15));
        userLb.setForeground(secondaryColor);

        headerPanel.add(titleLb, BorderLayout.WEST);
        headerPanel.add(userLb, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void taoBody() {

        bodyPanel = new JPanel(new BorderLayout(15, 0));
        bodyPanel.setBackground(backgroundColor);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));

        add(bodyPanel, BorderLayout.CENTER);
    }

    private void taoMenu() {

        menuPanel = new JPanel(new GridLayout(6, 1, 0, 10));
        menuPanel.setBackground(menuColor);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        menuPanel.setPreferredSize(new Dimension(230, 0));

        trangChuBtn = taoMenuButton("Trang chủ", primaryColor, true);
        kiemTraXeBtn = taoMenuButton("Kiểm tra xe", menuColor, false);
        lapPhieuBtn = taoMenuButton("Lập phiếu sửa chữa", menuColor, false);
        tienDoBtn = taoMenuButton("Tiến độ sửa chữa", menuColor, false);
        chatLuongBtn = taoMenuButton("Kiểm tra chất lượng", menuColor, false);
        dangXuatBtn = taoMenuButton("Đăng xuất", dangerColor, false);

        menuPanel.add(trangChuBtn);
        menuPanel.add(kiemTraXeBtn);
        menuPanel.add(lapPhieuBtn);
        menuPanel.add(tienDoBtn);
        menuPanel.add(chatLuongBtn);
        menuPanel.add(dangXuatBtn);

        bodyPanel.add(menuPanel, BorderLayout.WEST);
    }

    private void taoContent() {

        contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(backgroundColor);

        JPanel welcomePanel = new JPanel(new GridLayout(2, 1, 0, 5));
        welcomePanel.setBackground(backgroundColor);
        welcomePanel.setPreferredSize(new Dimension(700, 70));

        welcomeLb = new JLabel("XIN CHÀO KỸ THUẬT VIÊN");
        welcomeLb.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLb.setForeground(textColor);

        subWelcomeLb = new JLabel("Chọn chức năng bên dưới hoặc từ thanh menu để bắt đầu làm việc");
        subWelcomeLb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subWelcomeLb.setForeground(secondaryColor);

        welcomePanel.add(welcomeLb);
        welcomePanel.add(subWelcomeLb);

        contentPanel.add(welcomePanel, BorderLayout.NORTH);

        taoCard();

        bodyPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void taoCard() {

        cardPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        cardPanel.setBackground(backgroundColor);

        moKiemTraBtn = taoButton("Mở chức năng", primaryColor);
        moLapPhieuBtn = taoButton("Mở chức năng", successColor);
        moTienDoBtn = taoButton("Mở chức năng", purpleColor);
        moChatLuongBtn = taoButton("Mở chức năng", new Color(234, 88, 12));

        cardPanel.add(taoCardItem("Xe cần kiểm tra", "5", primaryColor, moKiemTraBtn));
        cardPanel.add(taoCardItem("Phiếu tiếp nhận chờ lập", "3", successColor, moLapPhieuBtn));
        cardPanel.add(taoCardItem("Lệnh sửa chữa đang làm", "4", purpleColor, moTienDoBtn));
        cardPanel.add(taoCardItem("Xe chờ kiểm tra KCS", "2", new Color(234, 88, 12), moChatLuongBtn));

        contentPanel.add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel taoCardItem(String title, String soLuong, Color color, JButton button) {

        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(20, 25, 18, 25)
        ));

        JLabel titleCardLb = new JLabel(title);
        titleCardLb.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleCardLb.setForeground(secondaryColor);

        JLabel soLuongLb = new JLabel(soLuong, SwingConstants.LEFT);
        soLuongLb.setFont(new Font("Segoe UI", Font.BOLD, 48));
        soLuongLb.setForeground(color);

        // Căn phải cho nút bấm ở chân card
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(button);

        card.add(titleCardLb, BorderLayout.NORTH);
        card.add(soLuongLb, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

    private void taoSuKien() {

        kiemTraXeBtn.addActionListener(e -> new ChiTietKiemTraChanDoan());
        moKiemTraBtn.addActionListener(e -> new ChiTietKiemTraChanDoan());

        lapPhieuBtn.addActionListener(e -> new LapPhieuSuaChua());
        moLapPhieuBtn.addActionListener(e -> new LapPhieuSuaChua());

        tienDoBtn.addActionListener(e -> new CapNhatTienDoSuaChua());
        moTienDoBtn.addActionListener(e -> new CapNhatTienDoSuaChua());

        chatLuongBtn.addActionListener(e -> new KiemTraChatLuong());
        moChatLuongBtn.addActionListener(e -> new KiemTraChatLuong());

        dangXuatBtn.addActionListener(e -> dangXuat());
    }

    private JButton taoMenuButton(String text, Color color, boolean isActive) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Thêm hiệu ứng Hover di chuột cho Menu
        if (!isActive && color.equals(menuColor)) {
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(new Color(51, 65, 85));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(menuColor);
                }
            });
        }

        return button;
    }

    private JButton taoButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(135, 38));

        return button;
    }

    private void dangXuat() {

        int result = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn đăng xuất khỏi hệ thống?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public void setQuyen(boolean kiemTra, boolean lapPhieu, boolean tienDo, boolean chatLuong) {

        kiemTraXeBtn.setEnabled(kiemTra);
        moKiemTraBtn.setEnabled(kiemTra);

        lapPhieuBtn.setEnabled(lapPhieu);
        moLapPhieuBtn.setEnabled(lapPhieu);

        tienDoBtn.setEnabled(tienDo);
        moTienDoBtn.setEnabled(tienDo);

        chatLuongBtn.setEnabled(chatLuong);
        moChatLuongBtn.setEnabled(chatLuong);
    }

    public static void main(String[] args) {
        new TrangChuKyThuatVien();
    }
}