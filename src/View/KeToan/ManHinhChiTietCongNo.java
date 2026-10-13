package View.KeToan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManHinhChiTietCongNo extends JFrame implements ActionListener {
    private final JPanel pNorth;
    private final JLabel lbltieude;
    private final JPanel pCenter;
    private final JLabel thongtin;
    private final JPanel pCenter1;
    private final JLabel tenkhachhang;
    private final JTextField txttenkhachhang;
    private final JLabel biensoxe;
    private final JTextField txtbiensoxe;
    private final JLabel sdt;
    private final JTextField txtsdt;
    private final JTextField txtdiachi;
    private final JLabel diachi;
    private final JLabel hanmuc;
    private final JTextField txthanmuc;
    private final JLabel tongno;
    private final JTextField txttongno;
    private final JLabel lichsu;
    private final JPanel pCenter2;
    private final JLabel tungay;
    private final JTextField txttungay;
    private final JLabel denngay;
    private final JTextField txtdenngay;
    private final JButton loc;
    private final JButton xuatexcel;
    private final JPanel pCenter3;
    private final DefaultTableModel model;
    private final JTable table;
    private final JScrollPane srcoll;
    private JButton quaylai;
    public ManHinhChiTietCongNo(){
        super("CHI TIẾT CÔNG NỢ KHÁCH HÀNG");
        setSize(650,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0,10));
        pNorth = new JPanel();
        lbltieude = new JLabel("CHI TIẾT CÔNG NỢ KHÁCH HÀNG");
        lbltieude.setFont(new Font("Arial",Font.BOLD,30));
        lbltieude.setForeground(Color.BLUE);
        pNorth.add(lbltieude);
        add(pNorth,BorderLayout.NORTH);
        //pCenter
        pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter,BoxLayout.Y_AXIS));
        thongtin = new JLabel("THÔNG TIN KHÁCH HÀNG & TRẠNG THÁI NỢ");
        thongtin.setFont(new Font("Arial",Font.BOLD,20));
        thongtin.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter.add(thongtin);
        pCenter.add(Box.createVerticalStrut(20));
        //pCenter1
        pCenter1 = new JPanel();
        pCenter1.setLayout(new GridLayout(6,2,10,10));
        //Hang1
        tenkhachhang = new JLabel("Tên Khách Hàng: ");
        txttenkhachhang = new JTextField("Công ty Vận tải A");
        //Hang2
        biensoxe = new JLabel("Biển số xe: ");
        txtbiensoxe  = new JTextField("29H-123.45, 29H-456.78");
        //Hang3
        sdt = new JLabel("Số Điện Thoại: ");
        txtsdt = new JTextField("0901112222");
        //Hang4
        diachi = new JLabel("Địa chỉ: ");
        txtdiachi = new JTextField("Quận Cầu Giấy, Hà Nội");
        //Hang5
        hanmuc = new JLabel("Hạn mức nợ cho phép: ");
        txthanmuc= new JTextField("50.000.000 VNĐ");
        //hang6
        tongno = new JLabel("TỔNG NỢ HIỆN TẠI: ");
        txttongno= new JTextField("25.000.000 VNĐ");
        pCenter1.add(tenkhachhang);
        pCenter1.add(txttenkhachhang);
        pCenter1.add(biensoxe);
        pCenter1.add(txtbiensoxe);
        pCenter1.add(sdt);
        pCenter1.add(txtsdt);
        pCenter1.add(diachi);
        pCenter1.add(txtdiachi);
        pCenter1.add(hanmuc);
        pCenter1.add(txthanmuc);
        pCenter1.add(tongno);
        pCenter1.add(txttongno);
        pCenter1.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter1.setMaximumSize(new Dimension(600,200));
        pCenter1.setPreferredSize(new Dimension(600,200));
        pCenter.add(pCenter1);
        lichsu = new JLabel("LỊCH SỬ PHÁT SINH CÔNG NỢ");
        lichsu.setFont(new Font("Arial",Font.BOLD,20));
        lichsu.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter.add(Box.createVerticalStrut(10));
        pCenter.add(lichsu);
        pCenter.add(Box.createVerticalStrut(20));
        //pCenter2
        pCenter2 = new JPanel();
        pCenter2.setLayout(new GridLayout(3,2,5,5));
        //Hang1
        tungay = new JLabel("Từ ngày: ");
        txttungay = new JTextField("01/09/2023");
        //Hang2
        denngay = new JLabel("Đến ngày: ");
        txtdenngay  = new JTextField("26/10/2023");
        //Hang3
        loc = new JButton("Lọc");
        xuatexcel = new JButton("Xuất Excel");
        loc.setMaximumSize(new Dimension(170,35));
        xuatexcel.setMaximumSize(new Dimension(170,35));
        pCenter2.add(tungay);
        pCenter2.add(txttungay);
        pCenter2.add(denngay);
        pCenter2.add(txtdenngay);
        pCenter2.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter2.setMaximumSize(new Dimension(600,100));
        pCenter2.setPreferredSize(new Dimension(600,100));
        pCenter2.add(loc);
        pCenter2.add(xuatexcel);
        pCenter.add(pCenter2);
        pCenter.add(Box.createVerticalStrut(15));
        //pCenter3
        pCenter3 = new JPanel();
        pCenter3.setLayout(new BorderLayout());
        String[] columns = {"Ngày Giao Dịch","Nội Dung","Ghi Nợ (Tăng)","Ghi Có (Giảm)","Dư Nợ Cuối"};
        Object[][] data = {
                {"26/10/2023","Thu tiền mặt KH","-","10.000.000 VNĐ","25.000.000 VNĐ"},
                {"15/10/2023","Sửa xe 29H-456.78","15.000.000 VNĐ","-","35.000.000 VNĐ"},
                {"01/10/2023","Sửa xe 29H-123.45","20.000.000 VNĐ","-","20.000.000 VNĐ"},
                {"01/10/2023","Số dư đầu kỳ","-","-","0 VNĐ"}
        };
        model = new DefaultTableModel(data,columns);
        table = new JTable(model);
        srcoll = new JScrollPane(table);
        pCenter3.add(srcoll);
        pCenter.add(pCenter3);
        quaylai = new JButton("Quay lại danh sách khách hàng nợ");
        quaylai.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter.add(quaylai);
        add(pCenter,BorderLayout.CENTER);

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
        thongtin.setForeground(chuchinh);
        tenkhachhang.setForeground(chuchinh);
        biensoxe.setForeground(chuchinh);
        sdt.setForeground(chuchinh);
        diachi.setForeground(chuchinh);
        hanmuc.setForeground(chuchinh);
        tongno.setForeground(chuchinh);
        lichsu.setForeground(chuchinh);
        tungay.setForeground(chuchinh);
        denngay.setForeground(chuchinh);
        loc.setBackground(btnchinh);
        xuatexcel.setBackground(btnchinh);
        table.getTableHeader().setBackground(maucotbang);
        table.getTableHeader().setForeground(chuchinh);
        quaylai.setBackground(btnchinh);
        
        


    }
    public static void main(String[] args) {
        ManHinhChiTietCongNo UI = new ManHinhChiTietCongNo();
        UI.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o == quaylai){
            ManHinhDanhSachKHNo UI = new ManHinhDanhSachKHNo();
            UI.setVisible(true);
            dispose();
        }

    }
}
