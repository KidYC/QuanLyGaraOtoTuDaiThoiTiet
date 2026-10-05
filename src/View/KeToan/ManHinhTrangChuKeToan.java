package View.KeToan;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ManHinhTrangChuKeToan extends JFrame implements ActionListener {
    private final JPanel pNorth;
    private final JLabel lbltieude;
    private final JPanel pThongTin;
    private final JPanel pWest;
    private final JLabel lblmenu;
    private final JPanel pCenter;
    private final JLabel lbltrangchu;
    private final JLabel hello;
    private final JLabel lblthaotac;
    private final JPanel pCenter1;
    private final JLabel lbltongquantrongngay;
    private final JPanel pThongKe;
    private final JPanel card1;
    private final JLabel title1;
    private final JPanel card2;
    private final JLabel title2;
    private final JLabel value2;
    private final JPanel card3;
    private final JLabel title3;
    private final JLabel nd2;
    private final JLabel value3;
    private final JLabel lblthongtin;
    private final JLabel nd1;
    private final JLabel nd3;
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
        setSize(1000, 600);
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
        trangchu = new JButton("Trang chủ");
        qlca = new JButton("Quản lý ca");
        qlcongno = new JButton("Quản lý công nợ");
        hoadonvatt = new JButton("Hóa đơn & Thanh toán");
        dangxuat = new JButton("Đăng xuất");
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
        // Panel chứa các thẻ thống kê
        pThongKe = new JPanel(new GridLayout(1, 3, 15, 0));
        pThongKe.setMaximumSize(new Dimension(700, 110));
        pThongKe.setBackground(Color.WHITE);
        // Thẻ 1
        card1 = new JPanel();
        card1.setLayout(new BoxLayout(card1, BoxLayout.Y_AXIS));
        card1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        title1 = new JLabel("CA ĐANG MỞ");
        title1.setFont(new Font("Arial", Font.BOLD, 14));
        title1.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel value1 = new JLabel("1");
        value1.setFont(new Font("Arial", Font.BOLD, 30));
        value1.setAlignmentX(Component.CENTER_ALIGNMENT);
        card1.add(Box.createVerticalStrut(12));
        card1.add(title1);
        card1.add(Box.createVerticalStrut(5));
        card1.add(value1);
        // Thẻ 2
        card2 = new JPanel();
        card2.setLayout(new BoxLayout(card2, BoxLayout.Y_AXIS));
        card2.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        title2 = new JLabel("HÓA ĐƠN CHỜ THU");
        title2.setFont(new Font("Arial", Font.BOLD, 14));
        title2.setAlignmentX(Component.CENTER_ALIGNMENT);
        value2 = new JLabel("5");
        value2.setFont(new Font("Arial", Font.BOLD, 30));
        value2.setAlignmentX(Component.CENTER_ALIGNMENT);
        card2.add(Box.createVerticalStrut(12));
        card2.add(title2);
        card2.add(Box.createVerticalStrut(5));
        card2.add(value2);
        // Thẻ 3
        card3 = new JPanel();
        card3.setLayout(new BoxLayout(card3, BoxLayout.Y_AXIS));
        card3.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        title3 = new JLabel("KH ĐẾN HẠN NỢ");
        title3.setFont(new Font("Arial", Font.BOLD, 14));
        title3.setAlignmentX(Component.CENTER_ALIGNMENT);
        value3 = new JLabel("2");
        value3.setFont(new Font("Arial", Font.BOLD, 30));
        value3.setAlignmentX(Component.CENTER_ALIGNMENT);
        card3.add(Box.createVerticalStrut(12));
        card3.add(title3);
        card3.add(Box.createVerticalStrut(5));
        card3.add(value3);
        pThongKe.add(card1);
        pThongKe.add(card2);
        pThongKe.add(card3);
        pCenter.add(pThongKe);
        //THÔNG TIN NHANH
        pThongTin = new JPanel();
        pThongTin.setLayout(new BoxLayout(pThongTin, BoxLayout.Y_AXIS));
        pThongTin.setMaximumSize(new Dimension(700, 90));
        pThongTin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        lblthongtin = new JLabel("THÔNG TIN NHANH");
        lblthongtin.setFont(new Font("Arial", Font.BOLD, 15));
        lblthongtin.setAlignmentX(Component.LEFT_ALIGNMENT);
        nd1 = new JLabel("• Hệ thống đang hoạt động bình thường.");
        nd2 = new JLabel("• Có 5 hóa đơn đang chờ thu tiền.");
        nd3 = new JLabel("• Có 2 khách hàng đến hạn thanh toán công nợ.");
        nd1.setFont(new Font("Arial", Font.PLAIN, 13));
        nd2.setFont(new Font("Arial", Font.PLAIN, 13));
        nd3.setFont(new Font("Arial", Font.PLAIN, 13));
        pThongTin.add(lblthongtin);
        pThongTin.add(Box.createVerticalStrut(5));
        pThongTin.add(nd1);
        pThongTin.add(nd2);
        pThongTin.add(nd3);
        pThongTin.setAlignmentX(Component.CENTER_ALIGNMENT);
        pCenter.add(Box.createVerticalStrut(15));
        pCenter.add(pThongTin);
        pCenter.add(Box.createVerticalGlue());
        add(pCenter, BorderLayout.CENTER);
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
    public static void main(String[] args) {
        ManHinhTrangChuKeToan UI = new ManHinhTrangChuKeToan();
        UI.setVisible(true);
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
