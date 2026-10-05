package View.KyThuatVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import View.DangNhap;

public class TrangChuKyThuatVien extends JFrame {
    private JPanel headerPanel;
    private JPanel bodyPanel;
    private JPanel menuPanel;
    private JPanel contentPanel;
    private JPanel cardPanel;
    private JPanel chartPanel;
    private JLabel logo;
    private JLabel titleLb;
    private JLabel userLb;
    private JLabel welcomeLb;
    private JLabel subWelcomeLb;
    private JButton trangChuBtn;
    private JButton kiemTraXeBtn;
    private JButton lapPhieuBtn;
    private JButton tienDoBtn;
    private JButton chatLuongBtn;
    private JButton moKiemTraBtn;
    private JButton moLapPhieuBtn;
    private JButton moTienDoBtn;
    private JButton moChatLuongBtn;
    private Color backgroundColor = new Color(244, 246, 248);
    private Color primaryColor = new Color(249, 115, 22);
    private Color textColor = new Color(17, 24, 39);
    private Color secondaryColor = new Color(107, 114, 128);
    private Color borderColor = new Color(229, 231, 235);
    private Color dangerColor = new Color(220, 38, 38);
    private Color successColor = new Color(37, 99, 235);
    private Color purpleColor = new Color(37, 99, 235);
    private Color menuColor = new Color(31, 41, 55);
    private String tenKyThuatVien = "Nguyễn Văn An";

    public TrangChuKyThuatVien() {
        setTitle("Trang chủ kỹ thuật viên - Garage Management");
        setSize(1100, 820);
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
        headerPanel.setPreferredSize(new Dimension(1100, 75));
        titleLb = new JLabel("TRANG CHỦ KỸ THUẬT VIÊN");
        titleLb.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLb.setForeground(textColor);
        headerPanel.add(titleLb, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void taoBody() {
        bodyPanel = new JPanel(new BorderLayout(15, 0));
        bodyPanel.setBackground(backgroundColor);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));
        add(bodyPanel, BorderLayout.CENTER);
    }

    private void taoMenu() {
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(menuColor);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        menuPanel.setPreferredSize(new Dimension(230, 0));

        JPanel menuTopPanel = new JPanel(new BorderLayout(0, 15));
        menuTopPanel.setBackground(menuColor);

        logo = new JLabel(loadIcon("/resources/icons/logo.png", 180, 165));
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        menuTopPanel.add(logo, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 0, 15));
        buttonPanel.setBackground(menuColor);

        trangChuBtn = taoMenuButton("Trang chủ", primaryColor, true);
        kiemTraXeBtn = taoMenuButton("Kiểm tra xe", menuColor, false);
        lapPhieuBtn = taoMenuButton("Lập phiếu sửa chữa", menuColor, false);
        tienDoBtn = taoMenuButton("Tiến độ sửa chữa", menuColor, false);
        chatLuongBtn = taoMenuButton("Kiểm tra chất lượng", menuColor, false);

        buttonPanel.add(trangChuBtn);
        buttonPanel.add(kiemTraXeBtn);
        buttonPanel.add(lapPhieuBtn);
        buttonPanel.add(tienDoBtn);
        buttonPanel.add(chatLuongBtn);

        menuTopPanel.add(buttonPanel, BorderLayout.CENTER);

        menuPanel.add(menuTopPanel, BorderLayout.NORTH);
        menuPanel.add(taoThongTinKTV(), BorderLayout.SOUTH);

        bodyPanel.add(menuPanel, BorderLayout.WEST);
    }

    private JPanel taoThongTinKTV() {
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(new Color(30, 41, 59));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(15, 5, 0, 5));

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(71, 85, 105));
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        JLabel tenLb = new JLabel(tenKyThuatVien);
        tenLb.setFont(new Font("Segoe UI", Font.BOLD, 22));
        tenLb.setForeground(Color.WHITE);
        tenLb.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel chucVuLb = new JLabel("Kỹ thuật viên");
        chucVuLb.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        chucVuLb.setForeground(new Color(148, 163, 184));
        chucVuLb.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        logoutPanel.setBackground(new Color(30, 41, 59));
        logoutPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel logoutLabel = new JLabel("Đăng xuất");
        logoutLabel.setIcon(loadIcon("/resources/icons/logout.png", 18, 18));
        logoutLabel.setIconTextGap(7);
        logoutLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        logoutLabel.setForeground(dangerColor);
        logoutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        logoutPanel.add(logoutLabel);

        logoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dangXuat();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                logoutLabel.setForeground(new Color(248, 113, 113));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutLabel.setForeground(dangerColor);
            }
        });

        profilePanel.add(separator);
        profilePanel.add(Box.createVerticalStrut(12));
        profilePanel.add(tenLb);
        profilePanel.add(Box.createVerticalStrut(2));
        profilePanel.add(chucVuLb);
        profilePanel.add(Box.createVerticalStrut(8));
        profilePanel.add(logoutPanel);

        return profilePanel;
    }

    private void taoContent() {
        contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setBackground(backgroundColor);

        JPanel welcomePanel = new JPanel(new GridLayout(2, 1, 0, 5));
        welcomePanel.setBackground(backgroundColor);
        welcomePanel.setPreferredSize(new Dimension(700, 60));

        welcomeLb = new JLabel("XIN CHÀO KỸ THUẬT VIÊN");
        welcomeLb.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLb.setForeground(textColor);

        subWelcomeLb = new JLabel("Chọn chức năng bên dưới hoặc từ thanh menu để bắt đầu làm việc");
        subWelcomeLb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subWelcomeLb.setForeground(secondaryColor);

        welcomePanel.add(welcomeLb);
        welcomePanel.add(subWelcomeLb);
        contentPanel.add(welcomePanel, BorderLayout.NORTH);

        JPanel mainDashboardPanel = new JPanel(new BorderLayout(0, 15));
        mainDashboardPanel.setBackground(backgroundColor);

        taoCard();
        taoChart();

        mainDashboardPanel.add(cardPanel, BorderLayout.NORTH);
        mainDashboardPanel.add(chartPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(mainDashboardPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        scrollPane.setBackground(backgroundColor);
        scrollPane.getViewport().setBackground(backgroundColor);

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        bodyPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void taoCard() {
        cardPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        cardPanel.setBackground(backgroundColor);

        moKiemTraBtn = taoButton("Xem chi tiết", primaryColor);
        moLapPhieuBtn = taoButton("Xem chi tiết", successColor);
        moTienDoBtn = taoButton("Xem chi tiết", purpleColor);
        moChatLuongBtn = taoButton("Xem chi tiết", new Color(245, 158, 11));

        cardPanel.add(taoCardItem("Xe cần kiểm tra", "5", primaryColor, moKiemTraBtn));
        cardPanel.add(taoCardItem("Phiếu tiếp nhận chờ lập", "3", successColor, moLapPhieuBtn));
        cardPanel.add(taoCardItem("Xe đang sửa chữa", "4", purpleColor, moTienDoBtn));
        cardPanel.add(taoCardItem("Xe chờ kiểm tra KCS", "2", new Color(245, 158, 11), moChatLuongBtn));
    }

    private JPanel taoCardItem(String title, String soLuong, Color color, JButton button) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel titleCardLb = new JLabel(title);
        titleCardLb.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleCardLb.setForeground(secondaryColor);

        JLabel soLuongLb = new JLabel(soLuong, SwingConstants.LEFT);
        soLuongLb.setFont(new Font("Segoe UI", Font.BOLD, 42));
        soLuongLb.setForeground(color);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(button);

        card.add(titleCardLb, BorderLayout.NORTH);
        card.add(soLuongLb, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

    private void taoChart() {
        chartPanel = new JPanel(new BorderLayout(0, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                String[] days = {"T2", "T3", "T4", "T5", "T6", "T7", "CN"};
                int[] values = {3, 5, 4, 7, 6, 8, 2};
                int maxValue = 10;
                int width = getWidth();
                int height = getHeight();
                int paddingTop = 45;
                int paddingBottom = 35;
                int paddingLeftRight = 40;
                int chartHeight = height - paddingTop - paddingBottom;
                int chartWidth = width - paddingLeftRight * 2;
                int barWidth = Math.min(42, chartWidth / days.length - 20);
                int gap = (chartWidth - (barWidth * days.length)) / (days.length + 1);

                g2.setColor(new Color(241, 245, 249));

                for (int i = 0; i <= 4; i++) {
                    int y = paddingTop + (chartHeight * i / 4);
                    g2.drawLine(paddingLeftRight, y, width - paddingLeftRight, y);
                }

                for (int i = 0; i < days.length; i++) {
                    int barHeight = (int) (((double) values[i] / maxValue) * chartHeight);
                    int x = paddingLeftRight + gap + i * (barWidth + gap);
                    int y = paddingTop + (chartHeight - barHeight);

                    g2.setColor(primaryColor);
                    g2.fillRoundRect(x, y, barWidth, barHeight, 8, 8);

                    g2.setColor(textColor);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 12));

                    String valStr = String.valueOf(values[i]);
                    int strWidth = g2.getFontMetrics().stringWidth(valStr);
                    g2.drawString(valStr, x + (barWidth - strWidth) / 2, y - 6);

                    g2.setColor(secondaryColor);
                    g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));

                    int dayWidth = g2.getFontMetrics().stringWidth(days[i]);
                    g2.drawString(days[i], x + (barWidth - dayWidth) / 2, height - 12);
                }
            }
        };

        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        chartPanel.setPreferredSize(new Dimension(700, 220));

        JLabel chartTitle = new JLabel("Thống kê năng suất: Số xe hoàn thành sửa chữa 7 ngày gần nhất");
        chartTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        chartTitle.setForeground(textColor);

        chartPanel.add(chartTitle, BorderLayout.NORTH);
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

        if (!isActive && color.equals(menuColor)) {
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(new Color(55, 65, 81));
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

    private ImageIcon loadIcon(String path, int w, int h) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
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
            new DangNhap().setVisible(true);
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