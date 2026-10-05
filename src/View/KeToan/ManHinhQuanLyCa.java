
package View.KeToan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManHinhQuanLyCa extends JFrame implements ActionListener {
    private final JLabel lbltieude;
    private final JPanel pWest;
    private final JLabel lblmenu;
    private final JLabel tieude;
    private final JPanel pCenter;
    private final JPanel card1;
    private final JLabel dangmo;
    private final JLabel nhanvien;
    private final JLabel giomo;
    private final JLabel sotien;
    private final JPanel card2;
    private final JButton dongca;
    private final JButton moca;
    private final JPanel pNorth;
    private JButton trangchu;
    private JButton qlca;
    private JButton dsphieusuachua;
    private JButton quanlycongno;
    private JButton baocaodoanhthu;
    private JButton quaylai;

    public ManHinhQuanLyCa(){

        setSize(580,480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        pNorth = new JPanel();

        lbltieude = new JLabel("MÀN HÌNH QUẢN LÝ CA");
        lbltieude.setFont(new Font("Arial",Font.BOLD,30));
        lbltieude.setForeground(Color.BLUE);

        pNorth.add(lbltieude);

        add(pNorth,BorderLayout.NORTH);

        //pWest
        pWest = new JPanel();
        pWest.setLayout(new BoxLayout(pWest,BoxLayout.Y_AXIS));
        pWest.setBackground(new Color(240,245,250));
        pWest.setPreferredSize(new Dimension(200,0));

        lblmenu = new JLabel("MENU CHÍNH");
        lblmenu.setFont(new Font("Arial",Font.BOLD,20));
        lblmenu.setAlignmentX(Component.CENTER_ALIGNMENT);

        trangchu = new JButton("Trang chủ kế toán");

        trangchu.setAlignmentX(Component.CENTER_ALIGNMENT);
        trangchu.setMaximumSize(new Dimension(190, 35));

        qlca = new JButton("Quản lý ca");
        qlca.setAlignmentX(Component.CENTER_ALIGNMENT);
        qlca.setMaximumSize(new Dimension(190, 35));

        dsphieusuachua = new JButton("Danh sách phiếu sửa chữa");
        dsphieusuachua.setAlignmentX(Component.CENTER_ALIGNMENT);
        dsphieusuachua.setMaximumSize(new Dimension(190,35));

        quanlycongno = new JButton("Quản lý công nợ");
        quanlycongno.setAlignmentX(Component.CENTER_ALIGNMENT);
        quanlycongno.setMaximumSize(new Dimension(190, 35));

        baocaodoanhthu = new JButton("Báo cáo doanh thu");
        baocaodoanhthu.setAlignmentX(Component.CENTER_ALIGNMENT);
        baocaodoanhthu.setMaximumSize(new Dimension(190, 35));

        quaylai = new JButton("Quay lại trang chủ");
        quaylai.setAlignmentX(Component.CENTER_ALIGNMENT);
        quaylai.setMaximumSize(new Dimension(190,35));


        pWest.add(Box.createVerticalStrut(10));
        pWest.add(lblmenu);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(trangchu);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(qlca);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(dsphieusuachua);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(quanlycongno);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(baocaodoanhthu);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(quaylai);
        add(pWest,BorderLayout.WEST);


        //pCenter
        pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter,BoxLayout.Y_AXIS));
        pCenter.setBorder(BorderFactory.createEmptyBorder(30,20,0,0));
        card1 = new JPanel();
        tieude = new JLabel("Thông tin ca làm");
        tieude.setFont(new Font("Arial",Font.BOLD,24));
        card1.setLayout(new BoxLayout(card1, BoxLayout.Y_AXIS));

        card1.setMaximumSize(new Dimension(250, 120));
        card1.setBackground(Color.WHITE);

        card1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        dangmo = new JLabel("Trạng thái: Đang mở");
        nhanvien = new JLabel("Nhân viên: KT Trần Thanh Liêm");
        giomo = new JLabel("8h30 - 3/10/2026");
        sotien = new JLabel("Số tiền ban đầu: 2,000,000 VNĐ");

        card1.add(dangmo);
        card1.add(nhanvien);
        card1.add(giomo);
        card1.add(sotien);

        card2 = new JPanel();
        card2.setLayout(new BoxLayout(card2, BoxLayout.Y_AXIS));

        card2.setMaximumSize(new Dimension(250, 120));
        card2.setBackground(Color.WHITE);

        card2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        moca = new JButton("Mở ca làm việc");
        dongca = new JButton("Đóng ca hiện tại");
        moca.setMaximumSize(new Dimension(170,50));
        dongca.setMaximumSize(new Dimension(170,50));

        card2.add(moca);
        card2.add(Box.createVerticalStrut(10));
        card2.add(dongca);
        pCenter.add(tieude);
        pCenter.add(Box.createVerticalStrut(20));
        pCenter.add(card1);
        pCenter.add(Box.createVerticalStrut(20));
        pCenter.add(card2);
        add(pCenter,BorderLayout.CENTER);

        trangchu.addActionListener(this);
        qlca.addActionListener(this);
        dsphieusuachua.addActionListener(this);
        quanlycongno.addActionListener(this);
        baocaodoanhthu.addActionListener(this);
        quaylai.addActionListener(this);
        chinhmau();
    }
    private void chinhmau (){
        Color maunen = new Color(244, 246, 248);
        Color maumenu = new Color(226, 232, 240);
        Color btnchinh =  new Color(186, 230, 253);
        Color btnphu = new Color(219, 234, 254);
        Color chuchinh = new Color(17, 24, 39);
        lbltieude.setForeground(chuchinh);
        lblmenu.setForeground(chuchinh);
        trangchu.setBackground(btnchinh);
        qlca.setBackground(btnchinh);
        dsphieusuachua.setBackground(btnchinh);
        quanlycongno.setBackground(btnchinh);
        baocaodoanhthu.setBackground(btnchinh);
        quaylai.setBackground(btnchinh);
        tieude.setForeground(chuchinh);
        dangmo.setForeground(chuchinh);
        nhanvien.setForeground(chuchinh);
        giomo.setForeground(chuchinh);
        sotien.setForeground(chuchinh);
        moca.setBackground(btnphu);
        dongca.setBackground(btnphu);
        pNorth.setBackground(maunen);
        pWest.setBackground(maumenu);
        pCenter.setBackground(maunen);

    }
    public static void main(String[] args){
        ManHinhQuanLyCa UI = new ManHinhQuanLyCa();
        UI.setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == trangchu){
            ManHinhTrangChuKeToan UI = new ManHinhTrangChuKeToan();
            UI.setVisible(true);
            dispose();
        } else if (o == qlca) {
            ManHinhQuanLyCa UI = new ManHinhQuanLyCa();
            UI.setVisible(true);
            dispose();
        } else if (o == dsphieusuachua) {
            ManHinhDanhSachPhieuSuaChuaDaHoanThanh UI = new ManHinhDanhSachPhieuSuaChuaDaHoanThanh();
            UI.setVisible(true);
            dispose();
        }else if (o== quanlycongno){
            ManHinhDanhSachKHNo UI = new ManHinhDanhSachKHNo();
            UI.setVisible(true);
            dispose();
        } else if (o == quaylai) {
            ManHinhTrangChuKeToan UI = new ManHinhTrangChuKeToan();
            UI.setVisible(true);
            dispose();
        }
    }
}