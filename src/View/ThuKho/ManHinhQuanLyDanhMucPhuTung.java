package View.ThuKho;

import javax.swing.*;
import java.awt.*;

public class ManHinhQuanLyDanhMucPhuTung extends JFrame {

    public ManHinhQuanLyDanhMucPhuTung(){

        super("QUẢN LÝ DANH MỤC PHỤ TÙNG");
        setSize(1000,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //north
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

        //center
        JPanel pCenter = new JPanel(new BorderLayout(15,15));
        pCenter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        JLabel lbltieudeman = new JLabel("DANH MỤC PHỤ TÙNG", SwingConstants.CENTER);
        lbltieudeman.setFont(new Font("Arial", Font.BOLD, 20));
        pCenter.add(lbltieudeman, BorderLayout.NORTH);

        String[] column = {"Mã Phụ Tùng","Tên Phụ Tùng","Đơn Vị","Số Lượng"};
        Object[][] data = {
                {"PT001", "Dầu nhớt Castrol", "Chai", 20},
                {"PT002", "Lọc gió", "Cái", 15},
                {"PT003", "Bugi", "Cái", 5}
        };
        JTable table = new JTable(data, column);
        table.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(table);
        pCenter.add(scroll, BorderLayout.CENTER);

        //pright
        JPanel pRight = new JPanel();
        pRight.setLayout(new BoxLayout(pRight, BoxLayout.Y_AXIS));
        pRight.setPreferredSize(new Dimension(230, 0));

        JLabel lblanh = new JLabel();
        ImageIcon icon = new ImageIcon("C:\\PTUD\\QuanLyGaraOtoTuDaiThoiTiet\\src\\IMG\\daunhot.png");
        Image img = icon.getImage().getScaledInstance(200,200, Image.SCALE_SMOOTH);
        lblanh.setPreferredSize(new Dimension(200, 200));
        lblanh.setMaximumSize(new Dimension(200, 200));
        lblanh.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblanh.setHorizontalAlignment(SwingConstants.CENTER);
        lblanh.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblanh.setIcon(new ImageIcon(img));

        pRight.add(lblanh);
        pRight.add(Box.createVerticalStrut(15));

        JPanel pCRUD = new JPanel(new GridLayout(2,2,10,10));
        JButton btnThem = new JButton("Thêm");
        JButton btnXoa = new JButton("Xóa");
        JButton btnSua = new JButton("Sửa");
        JButton btnLuu = new JButton("Lưu");
        pCRUD.add(btnThem);
        pCRUD.add(btnXoa);
        pCRUD.add(btnSua);
        pCRUD.add(btnLuu);
        pCRUD.setMaximumSize(new Dimension(200, 80));
        pCRUD.setAlignmentX(Component.CENTER_ALIGNMENT);

        pRight.add(pCRUD);

        pCenter.add(pRight, BorderLayout.EAST);
        add(pCenter, BorderLayout.CENTER);
    }
    public static void main(String[] args){
        View.ThuKho.ManHinhQuanLyDanhMucPhuTung UI = new View.ThuKho.ManHinhQuanLyDanhMucPhuTung();
        UI.setVisible(true);
    }

}