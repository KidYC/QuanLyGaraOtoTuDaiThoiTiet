package View.KeToan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManHinhChiTietHDVaThanhToan extends JFrame {
    public ManHinhChiTietHDVaThanhToan(){
        super("CHI TIẾT HOÁ ĐƠN VÀ THANH TOÁN");
        setSize(700,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5,20));

        //pNorth
        JPanel pNorth = new JPanel();
        pNorth.setLayout(new FlowLayout());
        JLabel lbltieude = new JLabel("CHI TIẾT HÓA ĐƠN VÀ THANH TOÁN");
        lbltieude.setFont(new Font("Arial",Font.BOLD,24));
        lbltieude.setForeground(Color.BLUE);
        lbltieude.setAlignmentX(CENTER_ALIGNMENT);
        pNorth.add(lbltieude);
        add(pNorth,BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter,BoxLayout.Y_AXIS));
        JLabel ttchung = new JLabel("THÔNG TIN CHUNG");
        ttchung.setFont(new Font("Arial",Font.BOLD,20));
        ttchung.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel pCenter1 = new JPanel();
        pCenter1.setLayout(new GridLayout(3,4,5,15));
        //Hang1
        JLabel mahd = new JLabel("Mã Hóa đơn: ");
        JTextField txtmahd = new JTextField("HD-20231026-001");
        JLabel ngaylap = new JLabel("Ngày lập: ");
        JTextField txtngaylap = new JTextField("26/10/2023");
        //Hang2
        JLabel maphieusc = new JLabel("Mã Phiếu sửa chữa: ");
        JTextField txtmaphieu = new JTextField("PSC-20231026-001");
        JLabel khachhang = new JLabel("Khách hàng: ");
        JTextField txtkhachhang = new JTextField("Trần Thanh Liêm");
        //Hang3
        JLabel bienso = new JLabel("Biển số xe: ");
        JTextField txtbienso = new JTextField("29A-12345");
        JLabel sdt =  new JLabel("Số điện thoại: ");
        JTextField txtsdt = new JTextField("0123456789");
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


        JLabel chitietsuachuavaphutung = new JLabel("CHI TIẾT SỬA CHỮA & PHỤ TÙNG");
        chitietsuachuavaphutung.setFont(new Font("Arial",Font.BOLD,20));
        chitietsuachuavaphutung.setAlignmentX(Component.LEFT_ALIGNMENT);

        //pCenter2
        JPanel pCenter2 = new JPanel();
        pCenter2.setLayout(new BorderLayout());
        String [] columns = {"STT","Mã Phụ tùng","Tên Phụ tùng/Dịch vụ","Số lượng","Đơn giá","Thành tiền"};
        Object[][] data = {
                {"1","PT000001","Nhớt Castrol","1","1.000.000 VNĐ","1,000,000 VNĐ"},
                {"2","PT000002","Công thay nhớt","1","200.000 VNĐ","200.000 VNĐ"},
                {"3","PT000003","Lọc nhớt","1","300.000 VNĐ","300.000 VNĐ"},
        };
        DefaultTableModel model = new DefaultTableModel(data,columns);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        JScrollPane srcoll = new JScrollPane(table);
        pCenter2.setPreferredSize(new Dimension(850,150));
        pCenter2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        pCenter2.setAlignmentX(Component.LEFT_ALIGNMENT);





        JLabel tomtat = new JLabel("TÓM TẮT & THANH TOÁN");
        tomtat.setFont(new Font("Arial",Font.BOLD,20));
        tomtat.setAlignmentX(Component.LEFT_ALIGNMENT);

        //pCenter3
        JPanel pCenter3 = new JPanel();
        pCenter3.setLayout(new GridLayout(5,2,0,5));
          //Hang1
          JLabel tongtienphutung = new JLabel("Tổng tiền phụ tùng: ");
          JTextField txttongtienphutung = new JTextField("1.300.000 VNĐ");
          //Hang2
          JLabel tongtiendichvu = new JLabel("Tổng tiền dịch vụ: ");
          JTextField txttongtiendichvu = new JTextField("200.000 VNĐ");
          //Hang3
          JLabel khuyenmai = new JLabel("Khuyến mãi: ");
          JTextField txtkhuyenmai = new JTextField("0 VNĐ");
          //Hang4
          JLabel tongcong = new JLabel("TỔNG CỘNG: ");
          JTextField txttongcong = new JTextField("1.500.000 VNĐ");
          //Hang5
          JLabel trangthai = new JLabel("Trạng thái: ");
          JTextField txttrangthai= new JTextField("Chờ thanh toán");
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
        JPanel pCenter4 = new JPanel();
        pCenter4.setLayout(new FlowLayout());
        JButton thanhtoan = new JButton("Thanh toán");
        JButton huybo = new JButton("Huỷ bỏ");
        JButton quaylai = new JButton("Quay lại danh sách");
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

    }

    public static void main(String[] args) {
        ManHinhChiTietHDVaThanhToan UI = new ManHinhChiTietHDVaThanhToan();
        UI.setVisible(true);
    }


}
