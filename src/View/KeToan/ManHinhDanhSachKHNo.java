package View.KeToan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManHinhDanhSachKHNo extends JFrame implements ActionListener {
    private JButton btnquaylai;
    public ManHinhDanhSachKHNo(){
        super("DANH SÁCH KHÁCH HÀNG NỢ");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5,20));

        //pNorth
        JPanel pNorth = new JPanel();
        pNorth.setLayout(new FlowLayout());
        JLabel lbltieude = new JLabel("DANH SÁCH KHÁCH HÀNG NỢ");
        lbltieude.setFont(new Font("Arial",Font.BOLD,24));
        lbltieude.setForeground(Color.BLUE);
        lbltieude.setAlignmentX(CENTER_ALIGNMENT);
        pNorth.add(lbltieude);
        add(pNorth,BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter,BoxLayout.Y_AXIS));
        pCenter.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JLabel thongtin = new JLabel("TỔNG QUAN");
        thongtin.setFont(new Font("Arial",Font.BOLD,20));
        thongtin.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter.add(thongtin);
        pCenter.add(Box.createVerticalStrut(20));

        //pCenter1
        JPanel pCenter1 = new JPanel();
        pCenter1.setLayout(new FlowLayout(FlowLayout.LEFT,15,0));
        pCenter1.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter1.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 50)
        );

        JLabel tongsokhachno = new JLabel("Tổng số khách hàng nợ: ");
        JTextField txttongsokhachno = new JTextField("45");
        txttongsokhachno.setPreferredSize(new Dimension(120,25));
        JLabel tongduno = new JLabel("Tổng dư nợ toàn hệ thống: ");
        JTextField txttongduno = new JTextField("120.500.000 VNĐ");
        txttongduno.setPreferredSize(new Dimension(120,25));
        pCenter1.add(tongsokhachno);
        pCenter1.add(txttongsokhachno);
        pCenter1.add(tongduno);
        pCenter1.add(txttongduno);


        JLabel timkiem = new JLabel("TÌM KIẾM & BỘ LỌC");
        timkiem.setFont(new Font("Arial",Font.BOLD,20));
        timkiem.setAlignmentX(Component.LEFT_ALIGNMENT);



        //pCenter2
        JPanel pCenter2 = new JPanel();
        pCenter2.setLayout(new FlowLayout(FlowLayout.LEFT,15,0));
        pCenter2.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter2.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 50)
        );

        JLabel tenkhachhang = new JLabel("Tên khách hàng: ");
        JTextField txttenkhachhang = new JTextField("Trần Thanh Liêm");
        txttenkhachhang.setPreferredSize(new Dimension(120,25));
        JLabel sdt = new JLabel("Số điện thoại: ");
        JTextField txtsdt = new JTextField("0123456789");
        txtsdt.setPreferredSize(new Dimension(120,25));
        JLabel mucno = new JLabel("Mức nợ: ");
        JComboBox<Object> txtmucno = new JComboBox<>(new String[]{"Lớn hơn 10.000.000 VNĐ", "Lớn hơn 30.000.000 VNĐ", "Lớn hơn 50.000.000 VNĐ"});
        txtmucno.setPreferredSize(new Dimension(120,20));
        JButton btntimkiem = new JButton("Tìm kiếm");
        pCenter2.add(tenkhachhang);
        pCenter2.add(txttenkhachhang);
        pCenter2.add(sdt);
        pCenter2.add(txtsdt);
        pCenter2.add(mucno);
        pCenter2.add(txtmucno);
        pCenter2.add(btntimkiem);



        JLabel ds = new JLabel("DANH SÁCH");
        ds.setFont(new Font("Arial",Font.BOLD,20));
        ds.setAlignmentX(Component.LEFT_ALIGNMENT);

        //pCenter3
        JPanel pCenter3 = new JPanel();
        pCenter3.setLayout(new BorderLayout());
        pCenter3.setPreferredSize(new Dimension(850,250));
        pCenter3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        pCenter3.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] columns ={"STT","Tên Khách Hàng","Số Điện Thoại","Biển Số Xe","Hạn Mức Nợ","Tổng Nợ Hiện Tại","Trạng Thái"};
        Object[][] data = {
                {"1","Công ty Vận tải A","0901112222","29H-123.45","50.000.000 VNĐ","25.000.000 VNĐ","Chưa hạn mức"},
                {"2","Trần Thanh Liêm","0987654321","30A-999.99","10.000.000 VNĐ","5.000.000 VNĐ","Chưa hạn mức"},
                {"3","Nguyễn Quan Huy","0912345678","51G-888.88","20.000.000 VNĐ","12.000.000 VNĐ","Chưa hạn mức"},
                {"4","Phan Xuân Phụng","0933334444","60C-555.55","5.000.000 VNĐ","6.000.000 VNĐ","Vượt hạn mức"}
        };
        DefaultTableModel model = new DefaultTableModel(data,columns);
        JTable table = new JTable(model);
        JScrollPane srcoll = new JScrollPane(table);
        pCenter3.add(srcoll,BorderLayout.CENTER);

        btnquaylai = new JButton("Quay lại Trang chủ Kế toán");
        btnquaylai.setPreferredSize(new Dimension(200,50));
        btnquaylai.setAlignmentX(Component.LEFT_ALIGNMENT);

        pCenter.add(pCenter1);
        pCenter.add(timkiem);
        pCenter.add(Box.createVerticalStrut(20));
        pCenter.add(pCenter2);
        pCenter.add(Box.createVerticalStrut(20));
        pCenter.add(ds);
        pCenter.add(Box.createVerticalStrut(20));
        pCenter.add(pCenter3);
        pCenter.add(Box.createVerticalStrut(20));
        //pCenter4

        pCenter.add(btnquaylai);
        add(pCenter,BorderLayout.CENTER);

        btnquaylai.addActionListener(this);


    }
    public static void main(String[] args) {
        ManHinhDanhSachKHNo UI = new ManHinhDanhSachKHNo();
        UI.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnquaylai) {
            ManHinhTrangChuKeToan UI = new ManHinhTrangChuKeToan();
            UI.setVisible(true);
            dispose();
        }
    }
}
