package View.KeToan;

import javax.swing.*;
import java.awt.*;

public class ManHinhTrangChuKeToan extends JFrame {

    public ManHinhTrangChuKeToan(){
        super("TRANG CHỦ KẾ TOÁN");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //pNorth
        JPanel pNorth = new JPanel();
        pNorth.setLayout(new FlowLayout());
        JLabel lbltieude = new JLabel("TRANG CHỦ KẾ TOÁN - HỆ THỐNG QUẢN LÝ");
        lbltieude.setFont(new Font("Arial",Font.BOLD,24));
        lbltieude.setForeground(Color.BLUE);
        lbltieude.setAlignmentX(CENTER_ALIGNMENT);
        pNorth.add(lbltieude);
        add(pNorth,BorderLayout.NORTH);

        //pWest
        JPanel pWest = new JPanel();
        pWest.setLayout(new BoxLayout(pWest,BoxLayout.Y_AXIS));
        JLabel lblmenu = new JLabel("MENU CHÍNH");
        lblmenu.setFont(new Font("Arial",Font.BOLD,20));
        lblmenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        pWest.setBackground(new Color(240,245,250));
        pWest.setPreferredSize(new Dimension(200,0));


        JButton trangchu = new JButton("Trang chủ");
        trangchu.setAlignmentX(Component.CENTER_ALIGNMENT);
        trangchu.setMaximumSize(new Dimension(170, 35));
        JButton qlca = new JButton("Quản lý ca");
        qlca.setAlignmentX(Component.CENTER_ALIGNMENT);
        qlca.setMaximumSize(new Dimension(170, 35));
        JButton qlcongno = new JButton("Quản lý công nợ");
        qlcongno.setAlignmentX(Component.CENTER_ALIGNMENT);
        qlcongno.setMaximumSize(new Dimension(170,35));
        JButton hoadonvatt = new JButton("Hóa đơn & Thanh toán");
        hoadonvatt.setAlignmentX(Component.CENTER_ALIGNMENT);
        hoadonvatt.setMaximumSize(new Dimension(170, 35));
        JButton dangxuat = new JButton("Đăng xuất");
        dangxuat.setAlignmentX(Component.CENTER_ALIGNMENT);
        dangxuat.setMaximumSize(new Dimension(170, 35));

        pWest.add(Box.createVerticalStrut(10));
        pWest.add(lblmenu);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(trangchu);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(qlca);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(qlcongno);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(hoadonvatt);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(dangxuat);

        add(pWest,BorderLayout.WEST);


        //pCenter
        JPanel pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter,BoxLayout.Y_AXIS));
        JLabel lbltrangchu = new JLabel("Trang chủ");
        lbltrangchu.setFont(new Font("Arial",Font.BOLD,20));


        JLabel hello = new JLabel("Xin chào, Kế toán viên! Chúc bạn một ngày làm việc hiệu quả.");
        hello.setFont(new Font("Arial",Font.BOLD,20));
        lbltrangchu.setAlignmentX(Component.CENTER_ALIGNMENT);
        hello.setAlignmentX(Component.CENTER_ALIGNMENT);

        pCenter.add(Box.createVerticalStrut(15));

        pCenter.add(lbltrangchu);
        pCenter.add(Box.createVerticalStrut(20));
        pCenter.add(hello);

        JLabel lblthaotac = new JLabel("THAO TÁC NHANH");
        lblthaotac.setFont(new Font ("Arial",Font.BOLD,20));
        JButton moca = new JButton("Mở ca");
        JButton laphoadonvatt = new JButton("Lập hóa đơn & thanh toán");
        JButton qlCongno = new JButton("Quản lý công nợ");
        moca.setMaximumSize(new Dimension(170,35));
        laphoadonvatt.setMaximumSize(new Dimension(170,35));
        qlCongno.setMaximumSize(new Dimension(170,35));

        lblthaotac.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel pCenter1 = new JPanel();
        pCenter1.add(moca);
        pCenter1.add(Box.createHorizontalStrut(15));
        pCenter1.add(laphoadonvatt);
        pCenter1.add(Box.createHorizontalStrut(15));
        pCenter1.add(qlCongno);

        pCenter.add(Box.createVerticalStrut(20));
        pCenter.add(lblthaotac);
        pCenter.add(Box.createVerticalStrut(20));
        pCenter.add(pCenter1);

        JLabel lbltongquantrongngay = new JLabel("TỔNG QUAN TRONG NGÀY");
        lbltongquantrongngay.setFont(new Font("Arial",Font.BOLD,20));
        lbltongquantrongngay.setAlignmentX(Component.CENTER_ALIGNMENT);
        pCenter.add(lbltongquantrongngay);
        pCenter.add(Box.createVerticalStrut(10));

        JPanel pCenter2 = new JPanel();
        pCenter2.setLayout(new BoxLayout(pCenter2,BoxLayout.Y_AXIS));
        JLabel nd1= new JLabel("- Số ca đang mở: 1");
        nd1.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel nd2= new JLabel("- Hoá đơn chờ thu: 5");
        nd2.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel nd3= new JLabel("- KH đến hạn nợ: 2");
        nd3.setAlignmentX(Component.LEFT_ALIGNMENT);

        pCenter2.add(nd1);
        pCenter2.add(Box.createVerticalStrut(5));
        pCenter2.add(nd2);
        pCenter2.add(Box.createVerticalStrut(5));
        pCenter2.add(nd3);
        pCenter2.add(Box.createVerticalStrut(5));
        pCenter2.setAlignmentX(Component.CENTER_ALIGNMENT);
        pCenter.add(pCenter2);

        pCenter.add(Box.createVerticalGlue());
        add(pCenter,BorderLayout.CENTER);
    }
    public static void main(String[] args){
        ManHinhTrangChuKeToan UI = new ManHinhTrangChuKeToan();
        UI.setVisible(true);
    }
}