package View.ThuKho;

import javax.swing.*;
import java.awt.*;

public class ManHinhDanhSachPhieuNhapKho extends JFrame {

    public ManHinhDanhSachPhieuNhapKho(){
        super("DANH SÁCH PHIẾU NHẬP KHO");
        setSize(1150,650);
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

        JLabel lbltieudeman = new JLabel("DANH SÁCH PHIẾU NHẬP KHO");
        lbltieudeman.setFont(new Font("Arial",Font.BOLD,20));
        lbltieudeman.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel pTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
        pTimKiem.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        JTextField txtmaphieu = new JTextField(7);
        JTextField txtnhacungcap = new JTextField(12);
        JTextField txttungay = new JTextField(8);
        JTextField txtdenngay = new JTextField(8);
        JButton btntim = new JButton("Tìm");
        JButton btnlammoi = new JButton("Làm mới");

        pTimKiem.add(new JLabel("Mã phiếu:"));
        pTimKiem.add(txtmaphieu);
        pTimKiem.add(new JLabel("Nhà cung cấp:"));
        pTimKiem.add(txtnhacungcap);
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

        String[] columnPhieu = {"Mã Phiếu","Ngày Nhập","Nhà Cung Cấp","Nhân Viên","Tổng Tiền"};
        Object[][] dataPhieu = {
                {"PN001","01/10/2026","Công ty Castrol VN","Nguyễn Văn A","2,500,000"},
                {"PN002","03/10/2026","Đại lý Hòa Phát","Trần Thị B","1,350,000"},
                {"PN003","04/10/2026","Bosch Việt Nam","Nguyễn Văn A","900,000"}
        };
        JTable tablePhieu = new JTable(dataPhieu, columnPhieu);
        tablePhieu.setRowHeight(25);
        JScrollPane scrollPhieu = new JScrollPane(tablePhieu);
        scrollPhieu.setBorder(BorderFactory.createTitledBorder("Danh sách phiếu nhập"));

        String[] columnChiTiet = {"Mã Phụ Tùng","Tên Phụ Tùng","Đơn Vị","Số Lượng","Đơn Giá","Thành Tiền"};
        Object[][] dataChiTiet = {
                {"PT001","Dầu nhớt Castrol","Chai",20,"125,000","2,500,000"}
        };
        JTable tableChiTiet = new JTable(dataChiTiet, columnChiTiet);
        tableChiTiet.setRowHeight(25);
        JScrollPane scrollChiTiet = new JScrollPane(tableChiTiet);
        scrollChiTiet.setBorder(BorderFactory.createTitledBorder("Chi tiết phiếu nhập"));

        pBang.add(scrollPhieu);
        pBang.add(scrollChiTiet);
        pCenter.add(pBang, BorderLayout.CENTER);

        JPanel pCRUD = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,5));
        JButton btnthem = new JButton("Tạo phiếu nhập");
        JButton btnsua = new JButton("Sửa");
        JButton btnxoa = new JButton("Xóa");
        JButton btnin = new JButton("In phiếu");
        JButton btnquaylai = new JButton("Quay lại");
        pCRUD.add(btnthem);
        pCRUD.add(btnsua);
        pCRUD.add(btnxoa);
        pCRUD.add(btnin);
        pCRUD.add(btnquaylai);
        pCenter.add(pCRUD, BorderLayout.SOUTH);

        add(pCenter, BorderLayout.CENTER);
    }

    public static void main(String[] args){
        View.ThuKho.ManHinhDanhSachPhieuNhapKho UI = new View.ThuKho.ManHinhDanhSachPhieuNhapKho();
        UI.setVisible(true);
    }
}