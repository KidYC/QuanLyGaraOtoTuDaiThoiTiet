package View.KeToan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManHinhChiTietCongNo extends JFrame implements ActionListener {
    private JButton quaylai;
    public ManHinhChiTietCongNo(){
        super("CHI TIẾT CÔNG NỢ KHÁCH HÀNG");
        setSize(650,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0,10));
        JPanel pNorth = new JPanel();
        JLabel lbltieude = new JLabel("CHI TIẾT CÔNG NỢ KHÁCH HÀNG");
        lbltieude.setFont(new Font("Arial",Font.BOLD,30));
        lbltieude.setForeground(Color.BLUE);
        pNorth.add(lbltieude);
        add(pNorth,BorderLayout.NORTH);
        //pCenter
        JPanel pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter,BoxLayout.Y_AXIS));
        JLabel thongtin = new JLabel("THÔNG TIN KHÁCH HÀNG & TRẠNG THÁI NỢ");
        thongtin.setFont(new Font("Arial",Font.BOLD,20));
        thongtin.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter.add(thongtin);
        pCenter.add(Box.createVerticalStrut(20));
        //pCenter1
        JPanel pCenter1 = new JPanel();
        pCenter1.setLayout(new GridLayout(6,2,10,10));
        //Hang1
        JLabel tenkhachhang = new JLabel("Tên Khách Hàng: ");
        JTextField txttenkhachhang = new JTextField("Công ty Vận tải A");
        //Hang2
        JLabel biensoxe = new JLabel("Biển số xe: ");
        JTextField txtbiensoxe  = new JTextField("29H-123.45, 29H-456.78");
        //Hang3
        JLabel sdt = new JLabel("Số Điện Thoại: ");
        JTextField txtsdt = new JTextField("0901112222");
        //Hang4
        JLabel diachi = new JLabel("Địa chỉ: ");
        JTextField txtdiachi = new JTextField("Quận Cầu Giấy, Hà Nội");
        //Hang5
        JLabel hanmuc = new JLabel("Hạn mức nợ cho phép: ");
        JTextField txthanmuc= new JTextField("50.000.000 VNĐ");
        //hang6
        JLabel tongno = new JLabel("TỔNG NỢ HIỆN TẠI: ");
        JTextField txttongno= new JTextField("25.000.000 VNĐ");
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
        JLabel lichsu = new JLabel("LỊCH SỬ PHÁT SINH CÔNG NỢ");
        lichsu.setFont(new Font("Arial",Font.BOLD,20));
        lichsu.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter.add(Box.createVerticalStrut(10));
        pCenter.add(lichsu);
        pCenter.add(Box.createVerticalStrut(20));
        //pCenter2
        JPanel pCenter2 = new JPanel();
        pCenter2.setLayout(new GridLayout(3,2,5,5));
        //Hang1
        JLabel tungay = new JLabel("Từ ngày: ");
        JTextField txttungay = new JTextField("01/09/2023");
        //Hang2
        JLabel denngay = new JLabel("Đến ngày: ");
        JTextField txtdenngay  = new JTextField("26/10/2023");
        //Hang3
        JButton loc = new JButton("Lọc");
        JButton xuatexcel = new JButton("Xuất Excel");
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
        JPanel pCenter3 = new JPanel();
        pCenter3.setLayout(new BorderLayout());
        String[] columns = {"Ngày Giao Dịch","Nội Dung","Ghi Nợ (Tăng)","Ghi Có (Giảm)","Dư Nợ Cuối"};
        Object[][] data = {
                {"26/10/2023","Thu tiền mặt KH","-","10.000.000 VNĐ","25.000.000 VNĐ"},
                {"15/10/2023","Sửa xe 29H-456.78","15.000.000 VNĐ","-","35.000.000 VNĐ"},
                {"01/10/2023","Sửa xe 29H-123.45","20.000.000 VNĐ","-","20.000.000 VNĐ"},
                {"01/10/2023","Số dư đầu kỳ","-","-","0 VNĐ"}
        };
        DefaultTableModel model = new DefaultTableModel(data,columns);
        JTable table = new JTable(model);
        JScrollPane srcoll = new JScrollPane(table);
        pCenter3.add(srcoll);
        pCenter.add(pCenter3);
        quaylai = new JButton("Quay lại danh sách khách hàng nợ");
        quaylai.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter.add(quaylai);
        add(pCenter,BorderLayout.CENTER);

        quaylai.addActionListener(this);
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
