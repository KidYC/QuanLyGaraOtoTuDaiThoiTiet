package View.ThuKho;

import javax.swing.*;
import java.awt.*;

public class ManHinhLapPhieuNhapKho extends JFrame {

    public ManHinhLapPhieuNhapKho(){
        super("LẬP PHIẾU NHẬP KHO");
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

        JLabel lbltieudeman = new JLabel("LẬP PHIẾU NHẬP KHO");
        lbltieudeman.setFont(new Font("Arial",Font.BOLD,20));
        lbltieudeman.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel pThongTin = new JPanel(new BorderLayout(0,10));
        pThongTin.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu nhập"));

        JPanel pThongTinTren = new JPanel(new GridLayout(2,4,10,10));
        JTextField txtmaphieu = new JTextField("PN004");
        JTextField txtngaynhap = new JTextField("04/10/2026");
        JComboBox<String> cbnhacungcap = new JComboBox<>(new String[]{"Công ty Castrol VN","Đại lý Hòa Phát","Bosch Việt Nam"});
        JTextField txtnhanvien = new JTextField("Nguyễn Văn A");

        pThongTinTren.add(new JLabel("Mã phiếu:"));
        pThongTinTren.add(txtmaphieu);
        pThongTinTren.add(new JLabel("Ngày nhập:"));
        pThongTinTren.add(txtngaynhap);
        pThongTinTren.add(new JLabel("Nhà cung cấp:"));
        pThongTinTren.add(cbnhacungcap);
        pThongTinTren.add(new JLabel("Nhân viên lập:"));
        pThongTinTren.add(txtnhanvien);

        JPanel pGhiChu = new JPanel(new BorderLayout(10,0));
        JTextField txtghichu = new JTextField();
        JLabel lblghichu = new JLabel("Ghi chú:");
        lblghichu.setPreferredSize(new Dimension(80, 25));
        pGhiChu.add(lblghichu, BorderLayout.WEST);
        pGhiChu.add(txtghichu, BorderLayout.CENTER);

        pThongTin.add(pThongTinTren, BorderLayout.CENTER);
        pThongTin.add(pGhiChu, BorderLayout.SOUTH);

        JPanel pChonPT = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
        pChonPT.setBorder(BorderFactory.createTitledBorder("Thêm phụ tùng vào phiếu"));
        JComboBox<String> cbphutung = new JComboBox<>(new String[]{"PT001 - Dầu nhớt Castrol","PT002 - Lọc gió","PT003 - Bugi"});
        JTextField txtsoluong = new JTextField(6);
        JTextField txtdongia = new JTextField(10);
        JButton btnthemphutung = new JButton("Thêm vào phiếu");
        pChonPT.add(new JLabel("Phụ tùng:"));
        pChonPT.add(cbphutung);
        pChonPT.add(new JLabel("Số lượng:"));
        pChonPT.add(txtsoluong);
        pChonPT.add(new JLabel("Đơn giá:"));
        pChonPT.add(txtdongia);
        pChonPT.add(btnthemphutung);

        pCenterNorth.add(lbltieudeman);
        pCenterNorth.add(Box.createVerticalStrut(10));
        pCenterNorth.add(pThongTin);
        pCenterNorth.add(Box.createVerticalStrut(10));
        pCenterNorth.add(pChonPT);
        pCenter.add(pCenterNorth, BorderLayout.NORTH);

        String[] column = {"Mã Phụ Tùng","Tên Phụ Tùng","Đơn Vị","Số Lượng","Đơn Giá","Thành Tiền"};
        Object[][] data = {
                {"PT001","Dầu nhớt Castrol","Chai",20,"125,000","2,500,000"},
                {"PT002","Lọc gió","Cái",15,"70,000","1,050,000"}
        };
        JTable table = new JTable(data, column);
        table.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Chi tiết phiếu nhập"));
        pCenter.add(scroll, BorderLayout.CENTER);

        JPanel pSouth = new JPanel(new BorderLayout());

        JLabel lbltongtien = new JLabel("Tổng tiền: 3,550,000 VNĐ");
        lbltongtien.setFont(new Font("Arial", Font.BOLD, 16));
        lbltongtien.setForeground(Color.RED);
        pSouth.add(lbltongtien, BorderLayout.WEST);

        JPanel pCRUD = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,5));
        JButton btnxoadong = new JButton("Xóa dòng");
        JButton btnluu = new JButton("Lưu phiếu");
        JButton btnhuy = new JButton("Hủy");
        JButton btnquaylai = new JButton("Quay lại");
        pCRUD.add(btnxoadong);
        pCRUD.add(btnluu);
        pCRUD.add(btnhuy);
        pCRUD.add(btnquaylai);
        pSouth.add(pCRUD, BorderLayout.EAST);

        pCenter.add(pSouth, BorderLayout.SOUTH);

        add(pCenter, BorderLayout.CENTER);
    }

    public static void main(String[] args){
        View.ThuKho.ManHinhLapPhieuNhapKho UI = new View.ThuKho.ManHinhLapPhieuNhapKho();
        UI.setVisible(true);
    }
}