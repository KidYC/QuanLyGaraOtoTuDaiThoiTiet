package View.KeToan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManHinhChiTietHDVaThanhToan extends JFrame implements ActionListener {
    private final JPanel pCenter;
    private final JPanel pNorth;
    private final JLabel lbltieude;
    private final JLabel ttchung;
    private final JLabel mahd;
    private final JPanel pCenter1;
    private final JTextField txtmahd;
    private final JLabel ngaylap;
    private final JTextField txtngaylap;
    private final JLabel maphieusc;
    private final JTextField txtmaphieu;
    private final JLabel khachhang;
    private final JTextField txtkhachhang;
    private final JLabel bienso;
    private final JTextField txtbienso;
    private final JLabel sdt;
    private final JTextField txtsdt;
    private final JLabel chitietsuachuavaphutung;
    private final JPanel pCenter2;
    private final DefaultTableModel model;
    private final JTable table;
    private final JScrollPane srcoll;
    private final JLabel tomtat;
    private final JPanel pCenter3;
    private final JLabel tongtienphutung;
    private final JTextField txttongtienphutung;
    private final JLabel tongtiendichvu;
    private final JTextField txttongtiendichvu;
    private final JLabel khuyenmai;
    private final JTextField txtkhuyenmai;
    private final JLabel tongcong;
    private final JTextField txttongcong;
    private final JLabel trangthai;
    private final JTextField txttrangthai;
    private final JPanel pCenter4;
    private JButton thanhtoan;
    private JButton huybo;
    private JButton quaylai;
    public ManHinhChiTietHDVaThanhToan(){
        super("CHI TIẾT HOÁ ĐƠN VÀ THANH TOÁN");
        setSize(700,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5,20));

        //pNorth
        pNorth = new JPanel();
        pNorth.setLayout(new FlowLayout());
        lbltieude = new JLabel("CHI TIẾT HÓA ĐƠN VÀ THANH TOÁN");
        lbltieude.setFont(new Font("Arial",Font.BOLD,24));
        lbltieude.setForeground(Color.BLUE);
        lbltieude.setAlignmentX(CENTER_ALIGNMENT);
        pNorth.add(lbltieude);
        add(pNorth,BorderLayout.NORTH);

        //pCenter
        pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter,BoxLayout.Y_AXIS));
        ttchung = new JLabel("THÔNG TIN CHUNG");
        ttchung.setFont(new Font("Arial",Font.BOLD,20));
        ttchung.setAlignmentX(Component.LEFT_ALIGNMENT);

        pCenter1 = new JPanel();
        pCenter1.setLayout(new GridLayout(3,4,5,15));
        //Hang1
        mahd = new JLabel("Mã Hóa đơn: ");
        txtmahd = new JTextField("HD-20231026-001");
        ngaylap = new JLabel("Ngày lập: ");
        txtngaylap = new JTextField("26/10/2023");
        //Hang2
        maphieusc = new JLabel("Mã Phiếu sửa chữa: ");
        txtmaphieu = new JTextField("PSC-20231026-001");
        khachhang = new JLabel("Khách hàng: ");
        txtkhachhang = new JTextField("Trần Thanh Liêm");
        //Hang3
        bienso = new JLabel("Biển số xe: ");
        txtbienso = new JTextField("29A-12345");
        sdt =  new JLabel("Số điện thoại: ");
        txtsdt = new JTextField("0123456789");
        pCenter1.add(mahd);
        pCenter1.add(txtmahd);
        pCenter1.add(ngaylap);
        pCenter1.add(txtngaylap);
        pCenter1.add(maphieusc);
        pCenter1.add(txtmaphieu);
        pCenter1.add(khachhang);
        pCenter1.add(txtkhachhang);
        pCenter1.add(bienso);
        pCenter1.add(txtbienso);
        pCenter1.add(sdt);
        pCenter1.add(txtsdt);
        pCenter1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        pCenter1.setAlignmentX(Component.LEFT_ALIGNMENT);


        chitietsuachuavaphutung = new JLabel("CHI TIẾT SỬA CHỮA & PHỤ TÙNG");
        chitietsuachuavaphutung.setFont(new Font("Arial",Font.BOLD,20));
        chitietsuachuavaphutung.setAlignmentX(Component.LEFT_ALIGNMENT);

        //pCenter2
        pCenter2 = new JPanel();
        pCenter2.setLayout(new BorderLayout());
        String [] columns = {"STT","Mã Phụ tùng","Tên Phụ tùng/Dịch vụ","Số lượng","Đơn giá","Thành tiền"};
        Object[][] data = {
                {"1","PT000001","Nhớt Castrol","1","1.000.000 VNĐ","1,000,000 VNĐ"},
                {"2","PT000002","Công thay nhớt","1","200.000 VNĐ","200.000 VNĐ"},
                {"3","PT000003","Lọc nhớt","1","300.000 VNĐ","300.000 VNĐ"},
        };
        model = new DefaultTableModel(data,columns);
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        srcoll = new JScrollPane(table);
        pCenter2.setPreferredSize(new Dimension(850,150));
        pCenter2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        pCenter2.setAlignmentX(Component.LEFT_ALIGNMENT);





        tomtat = new JLabel("TÓM TẮT & THANH TOÁN");
        tomtat.setFont(new Font("Arial",Font.BOLD,20));
        tomtat.setAlignmentX(Component.LEFT_ALIGNMENT);

        //pCenter3
        pCenter3 = new JPanel();
        pCenter3.setLayout(new GridLayout(5,2,0,5));
          //Hang1
        tongtienphutung = new JLabel("Tổng tiền phụ tùng: ");
        txttongtienphutung = new JTextField("1.300.000 VNĐ");
          //Hang2
        tongtiendichvu = new JLabel("Tổng tiền dịch vụ: ");
        txttongtiendichvu = new JTextField("200.000 VNĐ");
          //Hang3
        khuyenmai = new JLabel("Khuyến mãi: ");
        txtkhuyenmai = new JTextField("0 VNĐ");
          //Hang4
        tongcong = new JLabel("TỔNG CỘNG: ");
        txttongcong = new JTextField("1.500.000 VNĐ");
          //Hang5
        trangthai = new JLabel("Trạng thái: ");
        txttrangthai= new JTextField("Chờ thanh toán");
        pCenter3.add(tongtienphutung);
        pCenter3.add(txttongtienphutung);
        pCenter3.add(tongtiendichvu);
        pCenter3.add(txttongtiendichvu);
        pCenter3.add(khuyenmai);
        pCenter3.add(txtkhuyenmai);
        pCenter3.add(tongcong);
        pCenter3.add(txttongcong);
        pCenter3.add(trangthai);
        pCenter3.add(txttrangthai);
        pCenter3.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter3.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 200)
        );
        //pCenter4
        pCenter4 = new JPanel();
        pCenter4.setLayout(new FlowLayout());
        thanhtoan = new JButton("Thanh toán");
        huybo = new JButton("Huỷ bỏ");
        quaylai = new JButton("Quay lại danh sách");
        thanhtoan.setMaximumSize(new Dimension(170,50));
        huybo.setMaximumSize(new Dimension(170,50));
        quaylai.setMaximumSize(new Dimension(170,50));

        pCenter4.add(thanhtoan);
        pCenter4.add(huybo);
        pCenter4.add(quaylai);
        pCenter4.setAlignmentX(Component.LEFT_ALIGNMENT);



        pCenter.add(ttchung);
        pCenter.add(Box.createVerticalStrut(15));
        pCenter.add(pCenter1);
        pCenter.add(Box.createVerticalStrut(15));
        pCenter.add(chitietsuachuavaphutung);
        pCenter.add(Box.createVerticalStrut(15));
        pCenter2.add(srcoll,BorderLayout.CENTER);
        pCenter.add(pCenter2);
        pCenter.add(Box.createVerticalStrut(15));
        pCenter.add(tomtat);
        pCenter.add(pCenter3);
        pCenter.add(Box.createVerticalStrut(15));
        pCenter.add(pCenter4);

        add(pCenter,BorderLayout.CENTER);
        thanhtoan.addActionListener(this);
        huybo.addActionListener(this);
        quaylai.addActionListener(this);
        chinhmau();

    }
    private void chinhmau (){
        Color maunen = new Color(244, 246, 248);
        Color maumenu = new Color(226, 232, 240);
        Color btnchinh =  new Color(186, 230, 253);
        Color chuchinh = new Color(17, 24, 39);
        Color maucotbang = new Color(186,230,253);

        lbltieude.setForeground(chuchinh);
        ttchung.setForeground(chuchinh);
        mahd.setForeground(chuchinh);
        maphieusc.setForeground(chuchinh);
        bienso.setForeground(chuchinh);
        ngaylap.setForeground(chuchinh);
        khachhang.setForeground(chuchinh);
        sdt.setForeground(chuchinh);
        chitietsuachuavaphutung.setForeground(chuchinh);

        table.getTableHeader().setBackground(maucotbang);
        table.getTableHeader().setForeground(chuchinh);
        tomtat.setForeground(chuchinh);
        tongtienphutung.setForeground(chuchinh);
        tongtiendichvu.setForeground(chuchinh);
        khuyenmai.setForeground(chuchinh);
        tongcong.setForeground(chuchinh);
        trangthai.setForeground(chuchinh);
        thanhtoan.setBackground(btnchinh);
        huybo.setBackground(btnchinh);
        quaylai.setBackground(btnchinh);
    }
    public static void main(String[] args) {
        ManHinhChiTietHDVaThanhToan UI = new ManHinhChiTietHDVaThanhToan();
        UI.setVisible(true);
    }



    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == quaylai){
            ManHinhDanhSachPhieuSuaChuaDaHoanThanh UI = new ManHinhDanhSachPhieuSuaChuaDaHoanThanh();
            UI.setVisible(true);
            dispose();

        }
    }
}
