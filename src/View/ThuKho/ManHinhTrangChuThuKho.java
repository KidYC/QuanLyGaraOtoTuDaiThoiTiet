package View.ThuKho;

import javax.swing.*;
import java.awt.*;

public class ManHinhTrangChuThuKho extends JFrame {

    public ManHinhTrangChuThuKho(){
        super("TRANG CHỦ THỦ KHO");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //pnorth
        JPanel pNorth = new JPanel();
        pNorth.setLayout(new FlowLayout());
        JLabel lbltieude = new JLabel("TRANG CHỦ THỦ KHO - HỆ THỐNG QUẢN LÝ");
        lbltieude.setFont(new Font("Arial",Font.BOLD,24));
        lbltieude.setForeground(Color.BLUE);
        pNorth.add(lbltieude);
        add(pNorth, BorderLayout.NORTH);

        //pwest
        JPanel pWest = new JPanel();
        pWest.setLayout(new BoxLayout(pWest, BoxLayout.Y_AXIS));
        pWest.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        JLabel lblmenu = new JLabel("MENU");
        lblmenu.setFont(new Font("Arial",Font.BOLD,20));
        lblmenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        pWest.setBackground(new Color(240, 246, 252));
        pWest.setPreferredSize(new Dimension(180,0));
        pWest.add(lblmenu);

        JButton btntrangchu = new JButton("Trang Chủ");
        btntrangchu.setMaximumSize(new Dimension(150, 35));
        btntrangchu.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btnquanlyphutung = new JButton("Quản Lý Phụ Tùng");
        btnquanlyphutung.setMaximumSize(new Dimension(150,35));
        btnquanlyphutung.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btnnhapkho = new JButton("Nhập Kho");
        btnnhapkho.setMaximumSize(new Dimension(150,35));
        btnnhapkho.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btnxuatkho = new JButton("Xuất Kho");
        btnxuatkho.setMaximumSize(new Dimension(150,35));
        btnxuatkho.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btnkiemke = new JButton("Kiểm Kê Kho");
        btnkiemke.setMaximumSize(new Dimension(150, 35));
        btnkiemke.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btncanhbao = new JButton("Cảnh Báo Tồn Kho");
        btncanhbao.setMaximumSize(new Dimension(150,35));
        btncanhbao.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btndangxuat = new JButton("Đăng Xuất");
        btndangxuat.setMaximumSize(new Dimension(150,35));
        btndangxuat.setAlignmentX(Component.CENTER_ALIGNMENT);


        pWest.add(Box.createVerticalStrut(10));
        pWest.add(lblmenu);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(btntrangchu);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(btnnhapkho);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(btnxuatkho);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(btnquanlyphutung);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(btnkiemke);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(btncanhbao);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(btndangxuat);

        add(pWest, BorderLayout.WEST);

        //pCenter
        JPanel pCenter = new JPanel();
        pCenter.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
        JLabel lbltongquan = new JLabel("TỔNG QUAN KHO");
        lbltongquan.setFont(new Font("Arial",Font.BOLD,20));
        lbltongquan.setAlignmentX(Component.CENTER_ALIGNMENT);
        pCenter.add(lbltongquan);

        //card
        JPanel pCard = new JPanel(new FlowLayout(FlowLayout.CENTER,40,0));

        JPanel pCard1 = new JPanel();
        pCard1.setLayout(new BoxLayout(pCard1, BoxLayout.Y_AXIS));
        pCard1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pCard1.setBackground(new Color(240, 247, 255));

        //phu tung
        JLabel lblphutung = new JLabel("SỐ LƯỢNG PHỤ TÙNG");
        lblphutung.setFont(new Font("Arial", Font.BOLD, 16));
        lblphutung.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel slphutung = new JLabel("200");
        slphutung.setFont(new Font("Arial", Font.BOLD, 18));
        slphutung.setAlignmentX(Component.CENTER_ALIGNMENT);

        pCard1.add(Box.createVerticalStrut(15));
        pCard1.add(lblphutung);
        pCard1.add(Box.createVerticalStrut(10));
        pCard1.add(slphutung);
        pCard1.setPreferredSize(new Dimension(180, 90));

        //phieu nhap kho
        JPanel pCard2 = new JPanel();
        pCard2.setLayout(new BoxLayout(pCard2, BoxLayout.Y_AXIS));
        pCard2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pCard2.setBackground(new Color(240, 247, 255));

        JLabel lblnhapkho = new JLabel("PHIẾU NHẬP KHO");
        lblnhapkho.setFont(new Font("Arial", Font.BOLD, 16));
        lblnhapkho.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel slnhapkho = new JLabel("18");
        slnhapkho.setFont(new Font("Arial", Font.BOLD, 18));
        slnhapkho.setAlignmentX(Component.CENTER_ALIGNMENT);

        pCard2.add(Box.createVerticalStrut(15));
        pCard2.add(lblnhapkho);
        pCard2.add(Box.createVerticalStrut(10));
        pCard2.add(slnhapkho);
        pCard2.setPreferredSize(new Dimension(180, 90));

        //phieu xuat kho
        JPanel pCard3 = new JPanel();
        pCard3.setLayout(new BoxLayout(pCard3, BoxLayout.Y_AXIS));
        pCard3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pCard3.setBackground(new Color(240, 247, 255));
        JLabel lblxuatkho = new JLabel("PHIẾU XUẤT KHO");
        lblxuatkho.setFont(new Font("Arial", Font.BOLD, 16));
        lblxuatkho.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel slxuatkho = new JLabel("12");
        slxuatkho.setFont(new Font("Arial", Font.BOLD, 18));
        slxuatkho.setAlignmentX(Component.CENTER_ALIGNMENT);

        pCard3.add(Box.createVerticalStrut(15));
        pCard3.add(lblxuatkho);
        pCard3.add(Box.createVerticalStrut(10));
        pCard3.add(slxuatkho);
        pCard3.setPreferredSize(new Dimension(180, 90));

        pCard.add(pCard1);
        pCard.add(pCard2);
        pCard.add(pCard3);

        pCenter.add(Box.createVerticalStrut(25));
        pCenter.add(pCard);

        add(pCenter, BorderLayout.CENTER);

        //canh bao
        JPanel pCanhBao = new JPanel();
        pCanhBao.setLayout(new BoxLayout(pCanhBao, BoxLayout.Y_AXIS));
        pCanhBao.setBorder(BorderFactory.createTitledBorder("CẢNH BÁO TỒN KHO"));

        JLabel lblCanhBao = new JLabel("Có 5 phụ tùng đang sắp hết hàng !");
        lblCanhBao.setFont(new Font("Arial", Font.BOLD, 16));
        lblCanhBao.setAlignmentX(Component.CENTER_ALIGNMENT);

        pCanhBao.add(Box.createVerticalStrut(10));
        pCanhBao.add(lblCanhBao);
        pCanhBao.add(Box.createVerticalStrut(10));

        pCenter.add(Box.createVerticalStrut(20));
        pCenter.add(pCanhBao);
    }

    public static void main(String[] args){
        View.ThuKho.ManHinhTrangChuThuKho UI = new View.ThuKho.ManHinhTrangChuThuKho();
        UI.setVisible(true);
    }
}
