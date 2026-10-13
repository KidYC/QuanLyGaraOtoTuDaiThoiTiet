package View.ThuKho;

import javax.swing.*;
import java.awt.*;

public class ManHinhDanhSachYeuCauXuatKho extends JFrame {

    public ManHinhDanhSachYeuCauXuatKho(){
        super("DANH SÁCH YÊU CẦU XUẤT KHO");
        setSize(1300,750);
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

        JLabel lbltieudeman = new JLabel("DANH SÁCH YÊU CẦU XUẤT KHO");
        lbltieudeman.setFont(new Font("Arial",Font.BOLD,20));
        lbltieudeman.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel pTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
        pTimKiem.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        JTextField txtmayeucau = new JTextField(7);
        JTextField txtnguoiyeucau = new JTextField(10);
        JComboBox<String> cbtrangthai = new JComboBox<>(new String[]{"Tất cả","Chờ duyệt","Đã duyệt","Từ chối","Đã xuất kho"});
        JTextField txttungay = new JTextField(8);
        JTextField txtdenngay = new JTextField(8);
        JButton btntim = new JButton("Tìm");
        JButton btnlammoi = new JButton("Làm mới");

        pTimKiem.add(new JLabel("Mã yêu cầu:"));
        pTimKiem.add(txtmayeucau);
        pTimKiem.add(new JLabel("Người yêu cầu:"));
        pTimKiem.add(txtnguoiyeucau);
        pTimKiem.add(new JLabel("Trạng thái:"));
        pTimKiem.add(cbtrangthai);
        pTimKiem.add(new JLabel("Từ ngày:"));
        pTimKiem.add(txttungay);
        pTimKiem.add(new JLabel("Đến ngày:"));
        pTimKiem.add(txtdenngay);
        pTimKiem.add(btntim);
        pTimKiem.add(btnlammoi);

        pCenterNorth.add(lbltieudeman);
        pCenterNorth.add(Box.createVerticalStrut(10));
        pCenterNorth.add(pTimKiem);
        pCenter.add(pCenterNorth, BorderLayout.NORTH);

        JPanel pBang = new JPanel(new GridLayout(2,1,0,10));

        String[] columnYC = {"Mã Yêu Cầu","Ngày Yêu Cầu","Người Yêu Cầu","Biển Số Xe","Trạng Thái"};
        Object[][] dataYC = {
                {"YC001","02/10/2026","Lê Văn C","51A-123.45","Chờ duyệt"},
                {"YC002","03/10/2026","Phạm Văn D","59B-678.90","Đã duyệt"},
                {"YC003","04/10/2026","Lê Văn C","60C-111.22","Đã xuất kho"}
        };
        JTable tableYC = new JTable(dataYC, columnYC);
        tableYC.setRowHeight(25);
        JScrollPane scrollYC = new JScrollPane(tableYC);
        scrollYC.setBorder(BorderFactory.createTitledBorder("Danh sách yêu cầu xuất kho"));

        String[] columnCT = {"Mã Phụ Tùng","Tên Phụ Tùng","Đơn Vị","SL Yêu Cầu","SL Tồn Kho"};
        Object[][] dataCT = {
                {"PT002","Lọc gió","Cái",2,15},
                {"PT003","Bugi","Cái",4,5}
        };
        JTable tableCT = new JTable(dataCT, columnCT);
        tableCT.setRowHeight(25);
        JScrollPane scrollCT = new JScrollPane(tableCT);
        scrollCT.setBorder(BorderFactory.createTitledBorder("Chi tiết yêu cầu"));

        pBang.add(scrollYC);
        pBang.add(scrollCT);
        pCenter.add(pBang, BorderLayout.CENTER);

        JPanel pCRUD = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,5));
        JButton btnxem = new JButton("Xem chi tiết");
        JButton btnduyet = new JButton("Duyệt yêu cầu");
        JButton btntuchoi = new JButton("Từ chối");
        JButton btnlapphieu = new JButton("Lập phiếu xuất kho");
        JButton btnquaylai = new JButton("Quay lại");
        pCRUD.add(btnxem);
        pCRUD.add(btnduyet);
        pCRUD.add(btntuchoi);
        pCRUD.add(btnlapphieu);
        pCRUD.add(btnquaylai);
        pCenter.add(pCRUD, BorderLayout.SOUTH);

        add(pCenter, BorderLayout.CENTER);
    }

    public static void main(String[] args){
        View.ThuKho.ManHinhDanhSachYeuCauXuatKho UI = new View.ThuKho.ManHinhDanhSachYeuCauXuatKho();
        UI.setVisible(true);
    }
}