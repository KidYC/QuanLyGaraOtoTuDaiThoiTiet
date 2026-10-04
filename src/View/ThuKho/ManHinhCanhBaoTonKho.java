package View.ThuKho;

import javax.swing.*;
import java.awt.*;

public class ManHinhCanhBaoTonKho extends JFrame {

    public ManHinhCanhBaoTonKho(){
        super("CẢNH BÁO TỒN KHO");
        setSize(1000,650);
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
        pWest.setBackground(new Color(0, 70, 70));
        pWest.setPreferredSize(new Dimension(180,0));

        JLabel lblmenu = new JLabel("MENU");
        lblmenu.setForeground(Color.WHITE);
        lblmenu.setFont(new Font("Arial",Font.BOLD,20));
        lblmenu.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        JPanel pCenter = new JPanel(new BorderLayout(10,10));
        pCenter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        JPanel pCenterNorth = new JPanel();
        pCenterNorth.setLayout(new BoxLayout(pCenterNorth, BoxLayout.Y_AXIS));

        JLabel lbltieudeman = new JLabel("CẢNH BÁO TỒN KHO");
        lbltieudeman.setFont(new Font("Arial",Font.BOLD,20));
        lbltieudeman.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel pCard = new JPanel(new FlowLayout(FlowLayout.CENTER,40,0));

        JPanel pCard1 = new JPanel();
        pCard1.setLayout(new BoxLayout(pCard1, BoxLayout.Y_AXIS));
        pCard1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pCard1.setBackground(new Color(255, 248, 225));
        JLabel lblsaphet = new JLabel("SẮP HẾT HÀNG");
        lblsaphet.setFont(new Font("Arial", Font.BOLD, 16));
        lblsaphet.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel slsaphet = new JLabel("5");
        slsaphet.setFont(new Font("Arial", Font.BOLD, 18));
        slsaphet.setForeground(new Color(230, 126, 0));
        slsaphet.setAlignmentX(Component.CENTER_ALIGNMENT);
        pCard1.add(Box.createVerticalStrut(15));
        pCard1.add(lblsaphet);
        pCard1.add(Box.createVerticalStrut(10));
        pCard1.add(slsaphet);
        pCard1.setPreferredSize(new Dimension(180, 90));

        JPanel pCard2 = new JPanel();
        pCard2.setLayout(new BoxLayout(pCard2, BoxLayout.Y_AXIS));
        pCard2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pCard2.setBackground(new Color(255, 235, 235));
        JLabel lblhethang = new JLabel("HẾT HÀNG");
        lblhethang.setFont(new Font("Arial", Font.BOLD, 16));
        lblhethang.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel slhethang = new JLabel("2");
        slhethang.setFont(new Font("Arial", Font.BOLD, 18));
        slhethang.setForeground(Color.RED);
        slhethang.setAlignmentX(Component.CENTER_ALIGNMENT);
        pCard2.add(Box.createVerticalStrut(15));
        pCard2.add(lblhethang);
        pCard2.add(Box.createVerticalStrut(10));
        pCard2.add(slhethang);
        pCard2.setPreferredSize(new Dimension(180, 90));

        pCard.add(pCard1);
        pCard.add(pCard2);

        JPanel pLoc = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
        pLoc.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        JTextField txttim = new JTextField(12);
        JComboBox<String> cbmucdo = new JComboBox<>(new String[]{"Tất cả","Sắp hết hàng","Hết hàng"});
        JButton btntim = new JButton("Tìm");
        JButton btnlammoi = new JButton("Làm mới");
        pLoc.add(new JLabel("Mã/Tên phụ tùng:"));
        pLoc.add(txttim);
        pLoc.add(new JLabel("Mức cảnh báo:"));
        pLoc.add(cbmucdo);
        pLoc.add(btntim);
        pLoc.add(btnlammoi);

        pCenterNorth.add(lbltieudeman);
        pCenterNorth.add(Box.createVerticalStrut(10));
        pCenterNorth.add(pCard);
        pCenterNorth.add(Box.createVerticalStrut(10));
        pCenterNorth.add(pLoc);
        pCenter.add(pCenterNorth, BorderLayout.NORTH);

        String[] column = {"Mã Phụ Tùng","Tên Phụ Tùng","Đơn Vị","Số Lượng Tồn","Mức Tồn Tối Thiểu","Mức Cảnh Báo"};
        Object[][] data = {
                {"PT003","Bugi","Cái",5,10,"Sắp hết hàng"},
                {"PT004","Má phanh trước","Bộ",3,8,"Sắp hết hàng"},
                {"PT005","Nước làm mát","Chai",0,10,"Hết hàng"},
                {"PT006","Dây curoa","Cái",0,5,"Hết hàng"},
                {"PT007","Lọc dầu","Cái",4,10,"Sắp hết hàng"}
        };
        JTable table = new JTable(data, column);
        table.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phụ tùng cần nhập thêm"));
        pCenter.add(scroll, BorderLayout.CENTER);

        JPanel pCRUD = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,5));
        JButton btnlapphieu = new JButton("Lập phiếu nhập kho");
        JButton btnxuat = new JButton("Xuất danh sách");
        JButton btnquaylai = new JButton("Quay lại");
        pCRUD.add(btnlapphieu);
        pCRUD.add(btnxuat);
        pCRUD.add(btnquaylai);
        pCenter.add(pCRUD, BorderLayout.SOUTH);

        add(pCenter, BorderLayout.CENTER);
    }

    public static void main(String[] args){
        View.ThuKho.ManHinhCanhBaoTonKho UI = new View.ThuKho.ManHinhCanhBaoTonKho();
        UI.setVisible(true);
    }
}