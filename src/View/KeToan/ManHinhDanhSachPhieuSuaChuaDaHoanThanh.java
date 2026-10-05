package View.KeToan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManHinhDanhSachPhieuSuaChuaDaHoanThanh extends JFrame implements ActionListener {
    private final JPanel pCenter;
    private final JPanel pNorth;
    private final JLabel lbltieude;
    private final JPanel pWest;
    private final JLabel lblmenu;
    private final JPanel card;
    private final JLabel trangthai;
    private final JLabel timkiem;
    private final JTextField txttimkiem;
    private final JComboBox<String> txttrangthai;
    private final DefaultTableModel model;
    private final JTable table;
    private final JScrollPane scroll;
    private JButton trangchu;
    private JButton qlca;
    private JButton dsphieusuachua;
    private JButton quanlycongno;
    private JButton baocaodoanhthu;
    private JButton quaylai;
    public ManHinhDanhSachPhieuSuaChuaDaHoanThanh(){
        setSize(1000,480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0,10));

        pNorth = new JPanel();

        lbltieude = new JLabel("DANH SÁCH PHIẾU SỬA CHỮA ĐÃ HOÀN THÀNH");
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
        pCenter.setLayout(new BorderLayout());

        card = new JPanel();
        card.setLayout(new GridLayout(2,2,5,20));


        card.setMaximumSize(new Dimension(700, 120));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        timkiem = new JLabel("Ô nhập tìm kiếm (Mã Phiếu,Tên KH): ");
        timkiem.setFont(new Font("Arial",Font.BOLD,15));
        txttimkiem = new JTextField();
        trangthai = new JLabel("Trạng thái: ");
        trangthai.setFont(new Font("Arial",Font.BOLD,15));
        txttrangthai = new JComboBox<>(new String []{"Tất cả","Đang xử lý","Hoàn thành"});


        card.add(timkiem);
        card.add(txttimkiem);
        card.add(trangthai);
        card.add(txttrangthai);
        pCenter.add(card,BorderLayout.NORTH);

        String[] columns = {"STT","Mã Phiếu","Biển Số Xe","Tên Khách Hàng","Số Điện Thoại","Ngày Hoàn Thành","Kỹ Thuật Viên","Tổng Tiền","Trạng Thái"};
        Object[][] data = {{"1","PSC20231026001","29-12345","Trần Thanh Liêm","0123456789","26/10/2023","Hùng Nguyễn","1,500,000 VNĐ","Chờ thanh toán"},
                {"2","PSC20231026002","29-44444","Phan Xuân Phụng","0123456789","26/10/2023","Hùng Nguyễn","5,500,000 VNĐ","Đã lập hoá đơn"},
                {"3","PSC20231026003","29-99999","Nguyễn Quan Huy","0123456789","26/10/2023","Hùng Nguyễn","10,000,000 VNĐ","Chờ thanh toán"},
                {"4","PSC20231026004","29-03979","Lê Duy Minh","0123456789","26/10/2023","Hùng Nguyễn","15,500,000 VNĐ","Đã lập hoá đơn"}};
        model = new DefaultTableModel(data, columns);
        table = new JTable(model);
        scroll = new JScrollPane(table);
        pCenter.add(scroll,BorderLayout.CENTER);
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
        timkiem.setForeground(chuchinh);
        trangthai.setForeground(chuchinh);
        pNorth.setBackground(maunen);
        pWest.setBackground(maumenu);
        pCenter.setBackground(maunen);
        table.getTableHeader().setBackground(new Color(186,230,253));
    }
    public static void main(String[] args) {
        ManHinhDanhSachPhieuSuaChuaDaHoanThanh UI = new ManHinhDanhSachPhieuSuaChuaDaHoanThanh();
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
        }else if (o == quaylai) {
            ManHinhTrangChuKeToan UI = new ManHinhTrangChuKeToan();
            UI.setVisible(true);
            dispose();
        }
    }
}
