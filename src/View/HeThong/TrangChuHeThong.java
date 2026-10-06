package View.HeThong;

import View.KyThuatVien.ChiTietKiemTraChanDoan;
import View.KyThuatVien.LapPhieuSuaChua;
import View.KyThuatVien.CapNhatTienDoSuaChua;
import View.KyThuatVien.KiemTraChatLuong;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class TrangChuHeThong extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContentPanel;

    private JPanel headerPanel;
    private JPanel sidebarPanel;
    private JScrollPane sidebarScrollPane;

    private JLabel logo;
    private JLabel systemTitleLabel;
    private JLabel userInfoLabel;
    private JButton logoutBtn;

    private Color backgroundColor = new Color(244, 246, 248);
    private Color primaryColor = new Color(186, 230, 253);
    private Color textColor = new Color(17, 24, 39);
    private Color secondaryColor = new Color(107, 114, 128);
    private Color borderColor = new Color(226, 232, 240);
    private Color menuBgColor = new Color(226, 232, 240);
    private Color submenuBgColor = new Color(244, 246, 248);

    private Color successColor = new Color(34, 197, 94);
    private Color greenColor = new Color(34, 197, 94);
    private Color warningColor = new Color(245, 158, 11);
    private Color buttonSecondaryColor = new Color(219, 234, 254);

    private String currentUser = "Nguyễn Văn An";
    private String vaiTro = "ADMIN";

    public TrangChuHeThong() {
        this("ADMIN", "Nguyễn Văn An (Admin)");
    }

    public TrangChuHeThong(String vaiTro, String tenNguoiDung) {

        this.vaiTro = vaiTro != null ? vaiTro.toUpperCase() : "ADMIN";

        if (tenNguoiDung != null && !tenNguoiDung.isEmpty()) {
            this.currentUser = tenNguoiDung;
        }

        setTitle("Hệ Thống Quản Lý Gara Ô Tô - " + this.vaiTro);
        setSize(1300, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        taoHeader();
        taoSidebarTheoActor();
        taoMainContent();

        setVisible(true);
    }

    private void taoHeader() {

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, borderColor),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));

        headerPanel.setPreferredSize(new Dimension(1300, 65));

        systemTitleLabel = new JLabel("HỆ THỐNG QUẢN LÝ GARA Ô TÔ");
        systemTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        systemTitleLabel.setForeground(textColor);

        JPanel rightHeader = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 15, 0)
        );

        rightHeader.setBackground(Color.WHITE);

        userInfoLabel = new JLabel(
                "Xin chào: " + currentUser + " (" + layTenVaiTroHienThi(vaiTro) + ")"
        );

        userInfoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userInfoLabel.setForeground(secondaryColor);

        logoutBtn = new JButton("Đăng xuất");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logoutBtn.setForeground(textColor);
        logoutBtn.setBackground(buttonSecondaryColor);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setPreferredSize(new Dimension(100, 35));
        logoutBtn.addActionListener(e -> dangXuat());

        rightHeader.add(userInfoLabel);
        rightHeader.add(logoutBtn);

        headerPanel.add(systemTitleLabel, BorderLayout.WEST);
        headerPanel.add(rightHeader, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void taoSidebarTheoActor() {

        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(menuBgColor);
        sidebarPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 10, 15, 10)
        );

        logo = new JLabel(
                loadIcon("/resources/icons/logo.png", 180, 150)
        );

        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebarPanel.add(logo);
        sidebarPanel.add(
                Box.createRigidArea(new Dimension(0, 15))
        );

        addActorGroup(
                "TỔNG QUAN HỆ THỐNG",
                new String[][]{
                        {"Màn hình Dashboard tổng quan", "CARD_OVERVIEW"}
                }
        );

        if (vaiTro.equals("ADMIN") || vaiTro.equals("LETAN")) {

            addActorGroup(
                    "LỄ TÂN & TIẾP NHẬN",
                    new String[][]{
                            {"Tiếp nhận xe & Đặt lịch", "ACTOR_LETAN_1"},
                            {"Lập phiếu dịch vụ", "ACTOR_LETAN_2"},
                            {"Bàn giao xe cho khách", "ACTOR_LETAN_3"}
                    }
            );
        }

        if (vaiTro.equals("ADMIN") || vaiTro.equals("KTV")) {

            addActorGroup(
                    "KỸ THUẬT VIÊN",
                    new String[][]{
                            {"Kiểm tra & Chẩn đoán xe", "CARD_KTV_KIEMTRA"},
                            {"Lập phiếu sửa chữa", "CARD_KTV_LAPPHIEU"},
                            {"Cập nhật tiến độ sửa chữa", "CARD_KTV_TIENDO"},
                            {"Kiểm tra chất lượng (KCS)", "CARD_KTV_KCS"}
                    }
            );
        }

        if (vaiTro.equals("ADMIN") || vaiTro.equals("KHO")) {

            addActorGroup(
                    "THỦ KHO",
                    new String[][]{
                            {"Tra cứu phụ tùng & Vật tư", "ACTOR_KHO_1"},
                            {"Nhập kho phụ tùng", "ACTOR_KHO_2"},
                            {"Xuất kho sửa chữa", "ACTOR_KHO_3"}
                    }
            );
        }

        if (vaiTro.equals("ADMIN") || vaiTro.equals("KETOAN")) {

            addActorGroup(
                    "KẾ TOÁN",
                    new String[][]{
                            {"Lập hóa đơn thanh toán", "ACTOR_KETOAN_1"},
                            {"Quản lý thu chi & Công nợ", "ACTOR_KETOAN_2"}
                    }
            );
        }

        if (vaiTro.equals("ADMIN")) {

            addActorGroup(
                    "CHỦ GARA & QUẢN TRỊ",
                    new String[][]{
                            {"Quản lý nhân viên & Phân quyền", "ACTOR_ADMIN_1"},
                            {"Báo cáo doanh thu & Hiệu suất", "ACTOR_ADMIN_2"}
                    }
            );
        }

        sidebarScrollPane = new JScrollPane(sidebarPanel);

        sidebarScrollPane.setPreferredSize(
                new Dimension(280, 0)
        );

        sidebarScrollPane.setBorder(null);

        sidebarScrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        sidebarScrollPane.getVerticalScrollBar()
                .setUnitIncrement(12);

        sidebarScrollPane.getViewport()
                .setBackground(menuBgColor);

        add(sidebarScrollPane, BorderLayout.WEST);
    }

    private void addActorGroup(
            String actorTitle,
            String[][] submenus
    ) {

        JButton actorBtn = new JButton(actorTitle);

        actorBtn.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        actorBtn.setForeground(textColor);
        actorBtn.setBackground(menuBgColor);

        actorBtn.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        actorBtn.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        actorBtn.setMaximumSize(
                new Dimension(255, 42)
        );

        actorBtn.setPreferredSize(
                new Dimension(255, 42)
        );

        actorBtn.setMargin(
                new Insets(0, 0, 0, 0)
        );

        actorBtn.setFocusPainted(false);
        actorBtn.setBorderPainted(false);

        actorBtn.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        JPanel subMenuPanel = new JPanel();

        subMenuPanel.setLayout(
                new BoxLayout(
                        subMenuPanel,
                        BoxLayout.Y_AXIS
                )
        );

        subMenuPanel.setBackground(
                submenuBgColor
        );

        subMenuPanel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        subMenuPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        3, 0, 3, 0
                )
        );

        subMenuPanel.setVisible(false);

        for (String[] sub : submenus) {

            String label = sub[0];
            String cardKey = sub[1];

            JButton subBtn = new JButton(label);

            subBtn.setFont(
                    new Font("Segoe UI", Font.PLAIN, 12)
            );

            subBtn.setForeground(
                    secondaryColor
            );

            subBtn.setBackground(
                    submenuBgColor
            );

            subBtn.setHorizontalAlignment(
                    SwingConstants.LEFT
            );

            subBtn.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            subBtn.setMaximumSize(
                    new Dimension(255, 35)
            );

            subBtn.setPreferredSize(
                    new Dimension(255, 35)
            );

            subBtn.setMargin(
                    new Insets(0, 20, 0, 0)
            );

            subBtn.setFocusPainted(false);
            subBtn.setBorderPainted(false);

            subBtn.setCursor(
                    new Cursor(Cursor.HAND_CURSOR)
            );

            subBtn.addMouseListener(
                    new MouseAdapter() {

                        @Override
                        public void mouseEntered(MouseEvent e) {

                            subBtn.setForeground(textColor);
                            subBtn.setBackground(
                                    buttonSecondaryColor
                            );
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                            subBtn.setForeground(
                                    secondaryColor
                            );

                            subBtn.setBackground(
                                    submenuBgColor
                            );
                        }
                    }
            );

            subBtn.addActionListener(
                    e -> moChucNang(
                            cardKey,
                            sub[0]
                    )
            );

            subMenuPanel.add(subBtn);
        }

        if (subMenuPanel.getComponentCount() > 0) {

            actorBtn.addActionListener(e -> {

                boolean currentState =
                        subMenuPanel.isVisible();

                subMenuPanel.setVisible(
                        !currentState
                );

                sidebarPanel.revalidate();
                sidebarPanel.repaint();
            });

            sidebarPanel.add(actorBtn);
            sidebarPanel.add(subMenuPanel);

            sidebarPanel.add(
                    Box.createRigidArea(
                            new Dimension(0, 4)
                    )
            );
        }
    }

    private void taoMainContent() {

        cardLayout = new CardLayout();

        mainContentPanel = new JPanel(cardLayout) {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                ImageIcon bgIcon = loadIcon(
                        "/resources/icons/bg_system.png",
                        getWidth(),
                        getHeight()
                );

                if (bgIcon != null) {

                    g.drawImage(
                            bgIcon.getImage(),
                            0,
                            0,
                            getWidth(),
                            getHeight(),
                            this
                    );

                    g.setColor(
                            new Color(
                                    244,
                                    246,
                                    248,
                                    220
                            )
                    );

                    g.fillRect(
                            0,
                            0,
                            getWidth(),
                            getHeight()
                    );

                } else {

                    g.setColor(
                            backgroundColor
                    );

                    g.fillRect(
                            0,
                            0,
                            getWidth(),
                            getHeight()
                    );
                }
            }
        };

        mainContentPanel.add(
                taoPanelDashboardMaster(),
                "CARD_OVERVIEW"
        );

        add(
                mainContentPanel,
                BorderLayout.CENTER
        );
    }

    private JPanel taoPanelDashboardMaster() {

        JPanel contentPanel = new JPanel(
                new BorderLayout(0, 15)
        );

        contentPanel.setBackground(
                backgroundColor
        );

        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        JPanel welcomePanel = new JPanel(
                new GridLayout(2, 1, 0, 5)
        );

        welcomePanel.setBackground(
                backgroundColor
        );

        welcomePanel.setPreferredSize(
                new Dimension(700, 55)
        );

        JLabel welcomeLb = new JLabel(
                "TỔNG QUAN HOẠT ĐỘNG GARA Ô TÔ"
        );

        welcomeLb.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        welcomeLb.setForeground(textColor);

        JLabel subWelcomeLb = new JLabel(
                "Theo dõi chỉ số KPI trọng yếu của các phân hệ và biểu đồ năng suất dịch vụ"
        );

        subWelcomeLb.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        subWelcomeLb.setForeground(
                secondaryColor
        );

        welcomePanel.add(welcomeLb);
        welcomePanel.add(subWelcomeLb);

        contentPanel.add(
                welcomePanel,
                BorderLayout.NORTH
        );

        JPanel mainDashboardPanel = new JPanel(
                new BorderLayout(0, 15)
        );

        mainDashboardPanel.setBackground(
                backgroundColor
        );

        JPanel cardPanel = new JPanel(
                new GridLayout(2, 2, 15, 15)
        );

        cardPanel.setBackground(
                backgroundColor
        );

        JButton btn1 = taoButton(
                "Xem chi tiết",
                greenColor
        );

        JButton btn2 = taoButton(
                "Xem chi tiết",
                primaryColor
        );

        JButton btn3 = taoButton(
                "Xem chi tiết",
                warningColor
        );

        JButton btn4 = taoButton(
                "Xem chi tiết",
                successColor
        );

        btn1.addActionListener(
                e -> hienThongBaoChuaPhatTrien(
                        "Lễ tân"
                )
        );

        btn2.addActionListener(
                e -> new ChiTietKiemTraChanDoan()
        );

        btn3.addActionListener(
                e -> hienThongBaoChuaPhatTrien(
                        "Thủ kho"
                )
        );

        btn4.addActionListener(
                e -> hienThongBaoChuaPhatTrien(
                        "Kế toán"
                )
        );

        cardPanel.add(
                taoCardItem(
                        "Lễ tân: Xe chờ tiếp nhận",
                        "14",
                        greenColor,
                        btn1
                )
        );

        cardPanel.add(
                taoCardItem(
                        "Kỹ thuật: Lệnh sửa chữa đang làm",
                        "5",
                        primaryColor,
                        btn2
                )
        );

        cardPanel.add(
                taoCardItem(
                        "Thủ kho: Cảnh báo phụ tùng sắp hết",
                        "3",
                        warningColor,
                        btn3
                )
        );

        cardPanel.add(
                taoCardItem(
                        "Kế toán: Doanh thu dịch vụ hôm nay",
                        "25.5 Tr",
                        successColor,
                        btn4
                )
        );

        JPanel chartPanel = taoChartSystem();

        mainDashboardPanel.add(
                cardPanel,
                BorderLayout.NORTH
        );

        mainDashboardPanel.add(
                chartPanel,
                BorderLayout.CENTER
        );

        JScrollPane scrollPane = new JScrollPane(
                mainDashboardPanel
        );

        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(12);

        scrollPane.setBackground(
                backgroundColor
        );

        scrollPane.getViewport()
                .setBackground(backgroundColor);

        contentPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return contentPanel;
    }

    private JPanel taoCardItem(
            String title,
            String soLuong,
            Color color,
            JButton button
    ) {

        JPanel card = new JPanel(
                new BorderLayout(0, 10)
        );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                borderColor,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                20,
                                15,
                                20
                        )
                )
        );

        JLabel titleCardLb = new JLabel(title);

        titleCardLb.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        titleCardLb.setForeground(
                secondaryColor
        );

        JLabel soLuongLb = new JLabel(
                soLuong,
                SwingConstants.LEFT
        );

        soLuongLb.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        38
                )
        );

        soLuongLb.setForeground(textColor);

        JPanel bottomPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        0,
                        0
                )
        );

        bottomPanel.setBackground(
                Color.WHITE
        );

        bottomPanel.add(button);

        card.add(
                titleCardLb,
                BorderLayout.NORTH
        );

        card.add(
                soLuongLb,
                BorderLayout.CENTER
        );

        card.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        return card;
    }

    private JPanel taoChartSystem() {

        JPanel chartPanel = new JPanel(
                new BorderLayout(0, 10)
        ) {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 =
                        (Graphics2D) g;

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                String[] days = {
                        "T2",
                        "T3",
                        "T4",
                        "T5",
                        "T6",
                        "T7",
                        "CN"
                };

                int[] values = {
                        12,
                        18,
                        15,
                        22,
                        20,
                        28,
                        10
                };

                int maxValue = 30;

                int width = getWidth();
                int height = getHeight();

                int paddingTop = 45;
                int paddingBottom = 35;
                int paddingLeftRight = 40;

                int chartHeight =
                        height
                        - paddingTop
                        - paddingBottom;

                int chartWidth =
                        width
                        - paddingLeftRight * 2;

                int barWidth =
                        Math.min(
                                45,
                                chartWidth / days.length - 20
                        );

                int gap =
                        (chartWidth
                        - (barWidth * days.length))
                        / (days.length + 1);

                g2.setColor(
                        new Color(
                                226,
                                232,
                                240
                        )
                );

                for (int i = 0; i <= 4; i++) {

                    int y =
                            paddingTop
                            + (chartHeight * i / 4);

                    g2.drawLine(
                            paddingLeftRight,
                            y,
                            width - paddingLeftRight,
                            y
                    );
                }

                for (int i = 0;
                     i < days.length;
                     i++) {

                    int barHeight =
                            (int) (
                                    ((double) values[i]
                                    / maxValue)
                                    * chartHeight
                            );

                    int x =
                            paddingLeftRight
                            + gap
                            + i * (barWidth + gap);

                    int y =
                            paddingTop
                            + (chartHeight - barHeight);

                    g2.setColor(primaryColor);

                    g2.fillRoundRect(
                            x,
                            y,
                            barWidth,
                            barHeight,
                            8,
                            8
                    );

                    g2.setColor(textColor);

                    g2.setFont(
                            new Font(
                                    "Segoe UI",
                                    Font.BOLD,
                                    12
                            )
                    );

                    String valStr =
                            values[i] + " xe";

                    int strWidth =
                            g2.getFontMetrics()
                                    .stringWidth(valStr);

                    g2.drawString(
                            valStr,
                            x + (barWidth - strWidth) / 2,
                            y - 6
                    );

                    g2.setColor(
                            secondaryColor
                    );

                    g2.setFont(
                            new Font(
                                    "Segoe UI",
                                    Font.PLAIN,
                                    12
                            )
                    );

                    int dayWidth =
                            g2.getFontMetrics()
                                    .stringWidth(days[i]);

                    g2.drawString(
                            days[i],
                            x + (barWidth - dayWidth) / 2,
                            height - 12
                    );
                }
            }
        };

        chartPanel.setBackground(
                Color.WHITE
        );

        chartPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                borderColor,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                20,
                                15,
                                20
                        )
                )
        );

        chartPanel.setPreferredSize(
                new Dimension(700, 240)
        );

        JLabel chartTitle = new JLabel(
                "Thống kê năng suất toàn hệ thống: Số lượng xe tiếp nhận & sửa chữa 7 ngày gần nhất"
        );

        chartTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        chartTitle.setForeground(
                textColor
        );

        chartPanel.add(
                chartTitle,
                BorderLayout.NORTH
        );

        return chartPanel;
    }

    private JButton taoButton(
            String text,
            Color color
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(textColor);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(135, 38)
        );

        return button;
    }

    private ImageIcon loadIcon(
            String path,
            int w,
            int h
    ) {

        try {

            URL url =
                    getClass().getResource(path);

            if (url != null) {

                ImageIcon icon =
                        new ImageIcon(url);

                Image img =
                        icon.getImage()
                                .getScaledInstance(
                                        w,
                                        h,
                                        Image.SCALE_SMOOTH
                                );

                return new ImageIcon(img);
            }

        } catch (Exception ignored) {
        }

        return null;
    }

    private String layTenVaiTroHienThi(
            String code
    ) {

        switch (code) {

            case "ADMIN":
                return "Quản trị viên / Chủ Gara";

            case "KTV":
                return "Kỹ thuật viên";

            case "LETAN":
                return "Lễ tân";

            case "KHO":
                return "Thủ kho";

            case "KETOAN":
                return "Kế toán";

            default:
                return "Nhân viên";
        }
    }

    private void moChucNang(
            String cardKey,
            String tenChucNang
    ) {

        if (cardKey.equals("CARD_OVERVIEW")) {

            cardLayout.show(
                    mainContentPanel,
                    cardKey
            );

        } else if (cardKey.equals("CARD_KTV_KIEMTRA")) {

            new ChiTietKiemTraChanDoan();

        } else if (cardKey.equals("CARD_KTV_LAPPHIEU")) {

            new LapPhieuSuaChua();

        } else if (cardKey.equals("CARD_KTV_TIENDO")) {

            new CapNhatTienDoSuaChua();

        } else if (cardKey.equals("CARD_KTV_KCS")) {

            new KiemTraChatLuong();

        } else {

            hienManHinhChucNang(
                    tenChucNang
            );
        }
    }

    private void hienManHinhChucNang(
            String tenChucNang
    ) {

        JFrame frame = new JFrame(
                tenChucNang
        );

        frame.setSize(900, 600);
        frame.setLocationRelativeTo(this);
        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        JPanel panel = new JPanel(
                new BorderLayout()
        );

        panel.setBackground(
                backgroundColor
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        40,
                        30,
                        40
                )
        );

        JLabel title = new JLabel(
                tenChucNang
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        title.setForeground(
                textColor
        );

        title.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        JLabel roleLabel = new JLabel(
                "Vai trò: " +
                layTenVaiTroHienThi(vaiTro)
        );

        roleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        roleLabel.setForeground(
                secondaryColor
        );

        roleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        JPanel centerPanel = new JPanel(
                new GridLayout(2, 1, 0, 10)
        );

        centerPanel.setBackground(
                Color.WHITE
        );

        centerPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                borderColor,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                30,
                                30,
                                30,
                                30
                        )
                )
        );

        JLabel functionLabel = new JLabel(
                "Màn hình chức năng: " +
                tenChucNang
        );

        functionLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        functionLabel.setForeground(
                textColor
        );

        functionLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        JLabel statusLabel = new JLabel(
                "Giao diện chức năng đang được phát triển."
        );

        statusLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        statusLabel.setForeground(
                secondaryColor
        );

        statusLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        centerPanel.add(functionLabel);
        centerPanel.add(statusLabel);

        panel.add(
                title,
                BorderLayout.NORTH
        );

        panel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        panel.add(
                roleLabel,
                BorderLayout.SOUTH
        );

        frame.add(panel);
        frame.setVisible(true);
    }

    private void hienThongBaoChuaPhatTrien(
            String tenChucNang
    ) {

        JOptionPane.showMessageDialog(
                this,
                "Chức năng '" +
                tenChucNang +
                "' thuộc phân hệ phát triển của thành viên khác trong nhóm!",
                "Thông báo Phân quyền Nghiệp vụ",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void dangXuat() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Bạn có chắc muốn đăng xuất khỏi hệ thống?",
                        "Xác nhận đăng xuất",
                        JOptionPane.YES_NO_OPTION
                );

        if (result ==
                JOptionPane.YES_OPTION) {

            dispose();

            try {

                Class<?> dangNhapClass =
                        Class.forName(
                                "View.DangNhap"
                        );

                JFrame dangNhapFrame =
                        (JFrame) dangNhapClass
                                .getDeclaredConstructor()
                                .newInstance();

                dangNhapFrame.setVisible(true);

            } catch (Exception ignored) {
            }
        }
    }

    public static void main(String[] args) {

        new TrangChuHeThong(
                "KETOAN",
                "Trần Văn Quản Lý"
        );
    }
}