package View.LeTan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrangChuLeTan extends JFrame implements ActionListener {
    private JLabel lblTieuDe;
    private JPanel pWest;
    private JButton btnTiepNhanXe;
    private JButton btnLapBaoGia;
    private JButton btnBanGiaoXe;
    private JLabel lblMenuChinh;
    private JLabel lblProfile;

    private JPanel pCenter;

    private CardLayout cardLayout;
    private JLabel lblTieuDeQLKH;
    private JLabel lblTieuDeDSXCBG;
    private JLabel lblTieuDeDSXDSC;

    public TrangChuLeTan(){
        setTitle("Trang Chủ Lễ Tân");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildUI();
    }

    private void buildUI(){
        //PNorth
        JPanel pNorth = new JPanel();
        add(pNorth, BorderLayout.NORTH);
        pNorth.setLayout(new BorderLayout());
        lblTieuDe = new JLabel("TRANG CHỦ LỄ TÂN");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 22));
        lblTieuDe.setForeground(Color.BLUE);
        pNorth.add(lblTieuDe, BorderLayout.WEST);
        pNorth.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        lblProfile = new JLabel("Chào mừng, Trần Thanh Liêm");
        lblProfile.setFont(new Font("Arial", Font.BOLD, 20));
        lblProfile.setForeground(Color.GRAY);
        pNorth.add(lblProfile, BorderLayout.EAST);

        //PWest
        add(pWest = new JPanel(), BorderLayout.WEST);
        pWest.setLayout(new BoxLayout(pWest, BoxLayout.Y_AXIS));
        pWest.setPreferredSize(new Dimension(250, 0));
        pWest.setBackground(new Color(245, 245, 245));
        pWest.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        lblMenuChinh = new JLabel("Menu Chính");
        lblMenuChinh.setFont(new Font("Arial", Font.BOLD, 22));
        lblMenuChinh.setForeground(Color.BLUE);

        Box b = Box.createVerticalBox();
        pWest.add(b);
        Box b1, b2, b3, b4;

        b.add(b1 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        lblMenuChinh.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.add(lblMenuChinh);

        btnTiepNhanXe = createMenuButton("Tiếp Nhận Xe");
        b.add(b2 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b2.add(btnTiepNhanXe);

        btnLapBaoGia = createMenuButton("Lập Báo Giá");
        b.add(b3 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b3.add(btnLapBaoGia);

        btnBanGiaoXe = createMenuButton("Bàn Giao Xe");
        b.add(b4 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b4.add(btnBanGiaoXe);

        //pCenter
        cardLayout = new CardLayout();
        pCenter = new JPanel(cardLayout);
        add(pCenter, BorderLayout.CENTER);

        //Chuyển hướng Button Tiếp Nhận Xe
        QuanLyKhachHangPanel pQuanLyKhachHang = new QuanLyKhachHangPanel();
        pQuanLyKhachHang.setBackground(Color.WHITE);
        //Chuyển hướng Button Lập Báo Giá
        DanhSachXeChoBaoGiaPanel pXeChoBaoGia = new DanhSachXeChoBaoGiaPanel();
        pXeChoBaoGia.setBackground(new Color(240, 248, 255));
        //Chuyển hướng Button Bàn Giao Xe
        DanhSachXeDaHoanThanhSuaChua pDanhSachXeDaSua = new DanhSachXeDaHoanThanhSuaChua();
        pDanhSachXeDaSua.setBackground(new Color(255, 250, 240));
        //Thêm Card vào pCen
        pCenter.add(pQuanLyKhachHang, "CardTiepNhan");
        pCenter.add(pXeChoBaoGia, "CardLapBaoGia");
        pCenter.add(pDanhSachXeDaSua, "CardBanGiaoXe");
        //Thêm các ActionListener
        initListener();
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        Dimension btnSize = new Dimension(180, 35);
        btn.setPreferredSize(btnSize);
        btn.setMaximumSize(btnSize);
        btn.setMinimumSize(btnSize);

        return btn;
    }

    private void initListener(){
        btnTiepNhanXe.addActionListener(this);
        btnLapBaoGia.addActionListener(this);
        btnBanGiaoXe.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(btnTiepNhanXe.equals(source)){
            cardLayout.show(pCenter, "CardTiepNhan");
        }
        else if(btnLapBaoGia.equals(source)){
            cardLayout.show(pCenter, "CardLapBaoGia");
        }
        else if(btnBanGiaoXe.equals(source)){
            cardLayout.show(pCenter, "CardBanGiaoXe");
        }
    }

    public static void main() {
        TrangChuLeTan UI = new TrangChuLeTan();
        UI.setVisible(true);
    }
}
