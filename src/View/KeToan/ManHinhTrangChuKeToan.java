package View.KeToan;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ManHinhTrangChuKeToan extends JFrame implements ActionListener {
    private final JPanel pNorth;
    private final JLabel lbltieude;
    private final JPanel pWest;
    private final JLabel lblmenu;
    private final JPanel pCenter;
    private final JLabel lbltrangchu;
    private final JLabel hello;
    private final JLabel lblthaotac;
    private final JPanel pCenter1;
    private final JLabel lbltongquantrongngay;
    private JButton trangchu;
    private JButton qlca;
    private JButton qlcongno;
    private JButton hoadonvatt;
    private JButton dangxuat;
    private JButton moca;
    private JButton laphoadonvatt;
    private JButton qlCongno;
    public ManHinhTrangChuKeToan() {
        super("TRANG CHỦ KẾ TOÁN");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        //pNorth
         pNorth = new JPanel(new BorderLayout());
        pNorth.setPreferredSize(new Dimension(0, 75));
        lbltieude = new JLabel("TRANG CHỦ KẾ TOÁN - HỆ THỐNG QUẢN LÝ");
        lbltieude.setFont(new Font("Arial", Font.BOLD, 24));
        lbltieude.setForeground(Color.WHITE);
        lbltieude.setHorizontalAlignment(SwingConstants.CENTER);
        pNorth.add(lbltieude, BorderLayout.CENTER);
        add(pNorth, BorderLayout.NORTH);
        //pWest
        pWest = new JPanel();
        pWest.setLayout(new BoxLayout(pWest, BoxLayout.Y_AXIS));
        pWest.setPreferredSize(new Dimension(250, 0));
        lblmenu = new JLabel("MENU CHÍNH");
        lblmenu.setFont(new Font("Arial", Font.BOLD, 20));
        lblmenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        trangchu = new ButtonBoTron("Trang chủ");
        qlca = new ButtonBoTron("Quản lý ca");
        qlcongno = new ButtonBoTron("Quản lý công nợ");
        hoadonvatt = new ButtonBoTron("Hóa đơn & Thanh toán");
        dangxuat = new ButtonBoTron("Đăng xuất");
        // Cấu hình các nút menu
        JButton[] menuButtons = {
                trangchu, qlca, qlcongno, hoadonvatt, dangxuat
        };
        for (JButton button : menuButtons) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(200, 40));
            button.setPreferredSize(new Dimension(200, 40));
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.setFocusPainted(false);
        }
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(lblmenu);
        pWest.add(Box.createVerticalStrut(25));
        pWest.add(trangchu);
        pWest.add(Box.createVerticalStrut(15));
        pWest.add(qlca);
        pWest.add(Box.createVerticalStrut(15));
        pWest.add(qlcongno);
        pWest.add(Box.createVerticalStrut(15));
        pWest.add(hoadonvatt);
        pWest.add(Box.createVerticalStrut(15));
        pWest.add(dangxuat);
        add(pWest, BorderLayout.WEST);
        //pCenter
        pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
        pCenter.setBackground(Color.WHITE);
        // Tiêu đề
        lbltrangchu = new JLabel("TRANG CHỦ");
        lbltrangchu.setFont(new Font("Arial", Font.BOLD, 24));
        lbltrangchu.setAlignmentX(Component.CENTER_ALIGNMENT);
        hello = new JLabel(
                "Xin chào, Kế toán viên! Chúc bạn một ngày làm việc hiệu quả."
        );
        hello.setFont(new Font("Arial", Font.PLAIN, 16));
        hello.setAlignmentX(Component.CENTER_ALIGNMENT);
        pCenter.add(Box.createVerticalStrut(20));
        pCenter.add(lbltrangchu);
        pCenter.add(Box.createVerticalStrut(8));
        pCenter.add(hello);
        //THAO TÁC NHANH
        lblthaotac = new JLabel("THAO TÁC NHANH");
        lblthaotac.setFont(new Font("Arial", Font.BOLD, 19));
        lblthaotac.setAlignmentX(Component.CENTER_ALIGNMENT);
        moca = new JButton("Mở ca");
        laphoadonvatt = new JButton("Lập hóa đơn & thanh toán");
        qlCongno = new JButton("Quản lý công nợ");
        JButton[] quickButtons = {
                moca, laphoadonvatt, qlCongno
        };
        for (JButton button : quickButtons) {
            button.setPreferredSize(new Dimension(190, 45));
            button.setMaximumSize(new Dimension(190, 45));
            button.setFont(new Font("Arial", Font.BOLD, 13));
            button.setFocusPainted(false);
        }
        pCenter1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pCenter1.setMaximumSize(new Dimension(700, 85));
        pCenter1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        pCenter1.add(moca);
        pCenter1.add(laphoadonvatt);
        pCenter1.add(qlCongno);
        pCenter.add(Box.createVerticalStrut(25));
        pCenter.add(lblthaotac);
        pCenter.add(Box.createVerticalStrut(12));
        pCenter.add(pCenter1);
        //TỔNG QUAN
        lbltongquantrongngay = new JLabel("TỔNG QUAN TRONG NGÀY");
        lbltongquantrongngay.setFont(new Font("Arial", Font.BOLD, 19));
        lbltongquantrongngay.setAlignmentX(Component.CENTER_ALIGNMENT);
        pCenter.add(Box.createVerticalStrut(25));
        pCenter.add(lbltongquantrongngay);
        pCenter.add(Box.createVerticalStrut(15));
        BieuDoCot bieuDoCot = new BieuDoCot();
        pCenter.add(Box.createVerticalStrut(10));
        pCenter.add(bieuDoCot);
        add(pCenter,BorderLayout.CENTER);
        //ACTION LISTENER
        trangchu.addActionListener(this);
        qlca.addActionListener(this);
        qlcongno.addActionListener(this);
        hoadonvatt.addActionListener(this);
        dangxuat.addActionListener(this);
        moca.addActionListener(this);
        laphoadonvatt.addActionListener(this);
        qlCongno.addActionListener(this);

        chinhmau();
    }
    private void chinhmau (){
        Color maunen = new Color(244, 246, 248);
        Color maumenu = new Color(226, 232, 240);
        Color btnchinh =  new Color(186, 230, 253);
        Color btnphu = new Color(219, 234, 254);
        Color chuchinh = new Color(17, 24, 39);
        Color chuphu = new Color(148, 163, 184);
        lbltieude.setForeground(chuchinh);
        lblthaotac.setForeground(chuchinh);
        lblmenu.setForeground(chuchinh);
        trangchu.setBackground(btnchinh);
        qlca.setBackground(btnchinh);
        qlcongno.setBackground(btnchinh);
        qlCongno.setBackground(btnphu);
        hoadonvatt.setBackground(btnchinh);
        dangxuat.setBackground(btnchinh);
        moca.setBackground(btnphu);
        laphoadonvatt.setBackground(btnphu);
        pNorth.setBackground(maunen);
        pWest.setBackground(maumenu);
        pCenter.setBackground(maunen);
        pCenter1.setBackground(maunen);
    }
    public class ButtonBoTron extends JButton {
        private int radius = 15;
        public ButtonBoTron(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
            g2.dispose();
        }
    }
    public class BieuDoCot extends JPanel {

        private int[] values = {1, 5, 2, 12};

        private String[] labels = {
                "Ca đang mở",
                "Hóa đơn chờ thu",
                "KH đến hạn nợ",
                "Đã thanh toán"
        };

        public BieuDoCot() {
            setPreferredSize(new Dimension(700, 300));
            setMaximumSize(new Dimension(700, 300));
            setBackground(Color.WHITE);

            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220)),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
        }
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int width = getWidth();
            int height = getHeight();

            g2.setColor(new Color(17, 24, 39));

            g2.setFont(
                    new Font("Arial", Font.BOLD, 18)
            );

            g2.drawString(
                    "TỔNG QUAN KẾ TOÁN",
                    25,
                    30
            );
            int xStart = 70;
            int yTop = 55;

            int xEnd = width - 30;
            int yBottom = height - 55;

            int chartHeight = yBottom - yTop;

            int max = 15;

            g2.setFont(
                    new Font("Arial", Font.PLAIN, 11)
            );
            for (int i = 0; i <= max; i += 5) {

                int y =
                        yBottom
                                - (i * chartHeight / max);
                g2.setColor(
                        new Color(225, 230, 235)
                );

                g2.drawLine(
                        xStart,
                        y,
                        xEnd,
                        y
                );
                g2.setColor(
                        new Color(80, 80, 80)
                );

                String value = String.valueOf(i);
                g2.drawString(
                        value,
                        xStart - 25,
                        y + 4
                );
            }
            g2.setColor(
                    new Color(100, 100, 100)
            );

            g2.drawLine(
                    xStart,
                    yTop,
                    xStart,
                    yBottom
            );

            g2.drawLine(
                    xStart,
                    yBottom,
                    xEnd,
                    yBottom
            );
            int barWidth = 75;

            int khoangcach = 55;

            for (int i = 0; i < values.length; i++) {

                int barHeight =
                        values[i] * chartHeight / max;

                int x =
                        xStart
                                + 40
                                + i * (barWidth + khoangcach);

                int y =
                        yBottom - barHeight;

                if (i == 0) {
                    g2.setColor(
                            new Color(186, 230, 253)
                    );
                } else if (i == 1) {
                    g2.setColor(
                            new Color(147, 197, 253)
                    );
                } else if (i == 2) {
                    g2.setColor(
                            new Color(125, 211, 252)
                    );
                } else {
                    g2.setColor(
                            new Color(96, 165, 250)
                    );
                }
                g2.fillRoundRect(
                        x,
                        y,
                        barWidth,
                        barHeight,
                        8,
                        8
                );
                g2.setColor(
                        new Color(80, 120, 150)
                );
                g2.drawRoundRect(
                        x,
                        y,
                        barWidth,
                        barHeight,
                        8,
                        8
                );
                g2.setColor(
                        new Color(17, 24, 39)
                );
                g2.setFont(
                        new Font("Arial", Font.BOLD, 14)
                );
                String value =
                        String.valueOf(values[i]);

                int valueWidth =
                        g2.getFontMetrics()
                                .stringWidth(value);
                g2.drawString(
                        value,
                        x + (barWidth - valueWidth) / 2,
                        y - 8
                );
                g2.setFont(
                        new Font("Arial", Font.PLAIN, 11)
                );

                String label = labels[i];

                int labelWidth =
                        g2.getFontMetrics()
                                .stringWidth(label);

                g2.drawString(
                        label,
                        x + (barWidth - labelWidth) / 2,
                        yBottom + 22
                );
            }
            g2.dispose();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManHinhTrangChuKeToan UI = new ManHinhTrangChuKeToan();
            UI.setVisible(true);
        });
    }
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == qlcongno || o == qlCongno) {
            ManHinhDanhSachKHNo UI = new ManHinhDanhSachKHNo();
            UI.setVisible(true);
            this.dispose();
        } else if (o == qlca || o == moca) {
            ManHinhQuanLyCa UI = new ManHinhQuanLyCa();
            UI.setVisible(true);
            this.dispose();
        } else if (o == hoadonvatt || o == laphoadonvatt) {
            ManHinhChiTietHDVaThanhToan UI = new ManHinhChiTietHDVaThanhToan();
            UI.setVisible(true);
            this.dispose();
        } else if (o == dangxuat) {
            int chon = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn đăng xuất không?",
                    "Xác nhận đăng xuất",
                    JOptionPane.YES_NO_OPTION
            );
            if (chon == JOptionPane.YES_OPTION) {
                this.dispose();
            }
        }
    }
}
