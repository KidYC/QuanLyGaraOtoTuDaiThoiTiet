package View.KeToan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManHinhDanhSachPhieuSuaChuaDaHoanThanh extends JFrame {
    public ManHinhDanhSachPhieuSuaChuaDaHoanThanh(){
        setSize(1000,480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0,10));

        JPanel pNorth = new JPanel();

        JLabel lbltieude = new JLabel("DANH SÁCH PHIẾU SỬA CHỮA ĐÃ HOÀN THÀNH");
        lbltieude.setFont(new Font("Arial",Font.BOLD,30));
        lbltieude.setForeground(Color.BLUE);

        pNorth.add(lbltieude);

        add(pNorth,BorderLayout.NORTH);

        //pWest
        JPanel pWest = new JPanel();
        pWest.setLayout(new BoxLayout(pWest,BoxLayout.Y_AXIS));
        pWest.setBackground(new Color(240,245,250));
        pWest.setPreferredSize(new Dimension(200,0));

        JLabel lblmenu = new JLabel("MENU CHÍNH");
        lblmenu.setFont(new Font("Arial",Font.BOLD,20));
        lblmenu.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton trangchu = new JButton("Trang chủ kế toán");
        trangchu.setAlignmentX(Component.CENTER_ALIGNMENT);
        trangchu.setMaximumSize(new Dimension(190, 35));

        JButton qlca = new JButton("Quản lý ca");
        qlca.setAlignmentX(Component.CENTER_ALIGNMENT);
        qlca.setMaximumSize(new Dimension(190, 35));

        JButton dsphieusuachua = new JButton("Danh sách phiếu sửa chữa");
        dsphieusuachua.setAlignmentX(Component.CENTER_ALIGNMENT);
        dsphieusuachua.setMaximumSize(new Dimension(190,35));

        JButton quanlycongno = new JButton("Quản lý công nợ");
        quanlycongno.setAlignmentX(Component.CENTER_ALIGNMENT);
        quanlycongno.setMaximumSize(new Dimension(190, 35));

        JButton baocaodoanhthu = new JButton("Báo cáo doanh thu");
        baocaodoanhthu.setAlignmentX(Component.CENTER_ALIGNMENT);
        baocaodoanhthu.setMaximumSize(new Dimension(190, 35));

        JButton quaylai = new JButton("Quay lại");
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
        JPanel pCenter = new JPanel();
        pCenter.setLayout(new BorderLayout());

        JPanel card = new JPanel();
        card.setLayout(new GridLayout(2,2,5,20));


        card.setMaximumSize(new Dimension(700, 120));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        JLabel timkiem = new JLabel("Ô nhập tìm kiếm (Mã Phiếu,Tên KH): ");
        timkiem.setFont(new Font("Arial",Font.BOLD,15));
        JTextField txttimkiem = new JTextField();
        JLabel trangthai = new JLabel("Trạng thái: ");
        trangthai.setFont(new Font("Arial",Font.BOLD,15));
        JComboBox<Object> txttrangthai = new JComboBox<>(new String []{"Tất cả","Đang xử lý","Hoàn thành"});


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
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        pCenter.add(scroll,BorderLayout.CENTER);


        add(pCenter,BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        ManHinhDanhSachPhieuSuaChuaDaHoanThanh UI = new ManHinhDanhSachPhieuSuaChuaDaHoanThanh();
        UI.setVisible(true);
    }
}
