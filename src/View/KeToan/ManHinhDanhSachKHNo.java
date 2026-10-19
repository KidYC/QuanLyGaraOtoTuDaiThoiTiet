package View.KeToan;

import DAO.DAO_KeToan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ManHinhDanhSachKHNo extends JFrame implements ActionListener {
    private final JPanel pCenter;
    private final JPanel pNorth;
    private final JLabel lbltieude;
    private final JLabel thongtin;
    private final JPanel pCenter1;
    private final JLabel tongsokhachno;
    private final JTextField txttongsokhachno;
    private final JLabel tongduno;
    private final JTextField txttongduno;
    private final JLabel timkiem;
    private final JPanel pCenter2;
    private final JLabel tenkhachhang;
    private final JTextField txttenkhachhang;
    private final JTextField txtsdt;
    private final JLabel sdt;
    private final JComboBox<String> txtmucno;
    private final JLabel mucno;
    private final JButton btntimkiem;
    private final JLabel ds;
    private final JPanel pCenter3;
    private final DefaultTableModel model;
    private final JTable table;
    private final JScrollPane srcoll;
    private JButton btnquaylai;
    public ManHinhDanhSachKHNo(){
        super("DANH SÁCH KHÁCH HÀNG NỢ");
        setSize(900,600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5,20));

        //pNorth
        pNorth = new JPanel();
        pNorth.setLayout(new FlowLayout());
        lbltieude = new JLabel("DANH SÁCH KHÁCH HÀNG NỢ");
        lbltieude.setFont(new Font("Arial",Font.BOLD,24));
        lbltieude.setForeground(Color.BLUE);
        lbltieude.setAlignmentX(CENTER_ALIGNMENT);
        pNorth.add(lbltieude);
        add(pNorth,BorderLayout.NORTH);

        //pCenter
        pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter,BoxLayout.Y_AXIS));
        pCenter.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        thongtin = new JLabel("TỔNG QUAN");
        thongtin.setFont(new Font("Arial",Font.BOLD,20));
        thongtin.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter.add(thongtin);
        pCenter.add(Box.createVerticalStrut(20));

        //pCenter1
        pCenter1 = new JPanel();
        pCenter1.setLayout(new FlowLayout(FlowLayout.LEFT,15,0));
        pCenter1.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter1.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 50)
        );

        tongsokhachno = new JLabel("Tổng số khách hàng nợ: ");
        txttongsokhachno = new JTextField();
        txttongsokhachno.setPreferredSize(new Dimension(120,25));
        tongduno = new JLabel("Tổng dư nợ toàn hệ thống: ");
        txttongduno = new JTextField("");
        txttongduno.setPreferredSize(new Dimension(120,25));
        pCenter1.add(tongsokhachno);
        pCenter1.add(txttongsokhachno);
        pCenter1.add(tongduno);
        pCenter1.add(txttongduno);


        timkiem = new JLabel("TÌM KIẾM & BỘ LỌC");
        timkiem.setFont(new Font("Arial",Font.BOLD,20));
        timkiem.setAlignmentX(Component.LEFT_ALIGNMENT);



        //pCenter2
        pCenter2 = new JPanel();
        pCenter2.setLayout(new FlowLayout(FlowLayout.LEFT,15,0));
        pCenter2.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCenter2.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 50)
        );

        tenkhachhang = new JLabel("Tên khách hàng: ");
        txttenkhachhang = new JTextField();
        txttenkhachhang.setPreferredSize(new Dimension(120,25));
        sdt = new JLabel("Số điện thoại: ");
        txtsdt = new JTextField();
        txtsdt.setPreferredSize(new Dimension(120,25));
        mucno = new JLabel("Mức nợ: ");
        txtmucno = new JComboBox<>(new String[]{"Lớn hơn 10000000 VNĐ", "Lớn hơn 30000000 VNĐ", "Lớn hơn 50000000 VNĐ"});
        txtmucno.setPreferredSize(new Dimension(120,20));
        btntimkiem = taoNutBoGoc("Tìm kiếm");
        pCenter2.add(tenkhachhang);
        pCenter2.add(txttenkhachhang);
        pCenter2.add(sdt);
        pCenter2.add(txtsdt);
        pCenter2.add(mucno);
        pCenter2.add(txtmucno);
        pCenter2.add(btntimkiem);



        ds = new JLabel("DANH SÁCH");
        ds.setFont(new Font("Arial",Font.BOLD,20));
        ds.setAlignmentX(Component.LEFT_ALIGNMENT);

        //pCenter3
        pCenter3 = new JPanel();
        pCenter3.setLayout(new BorderLayout());
        pCenter3.setPreferredSize(new Dimension(850,250));
        pCenter3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        pCenter3.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] columns ={"STT","Mã khách hàng","Tên Khách Hàng","Số Điện Thoại","Biển Số Xe","Hạn Mức Nợ","Tổng Nợ Hiện Tại","Trạng Thái"};
        model = new DefaultTableModel(columns,0);
        table = new JTable(model);
        srcoll = new JScrollPane(table);
        pCenter3.add(srcoll,BorderLayout.CENTER);

        btnquaylai = taoNutBoGoc("Quay lại Trang chủ Kế toán");
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
        chinhmau();
        laydulieu();
        tongnohethong();
        int tongKH = table.getRowCount();
        txttongsokhachno.setText(String.valueOf(tongKH));
        btntimkiem.addActionListener(this);
    }
    private void chinhmau (){
        Color maunen = new Color(244, 246, 248);
        Color maumenu = new Color(226, 232, 240);
        Color btnchinh =  new Color(186, 230, 253);
        Color btnphu = new Color(219, 234, 254);
        Color chuchinh = new Color(17, 24, 39);
        Color maucotbang = new Color(186,230,253);
        lbltieude.setForeground(chuchinh);
        thongtin.setForeground(chuchinh);
        tongduno.setForeground(chuchinh);
        timkiem.setForeground(chuchinh);
        tenkhachhang.setForeground(chuchinh);
        sdt.setForeground(chuchinh);
        mucno.setForeground(chuchinh);
        btntimkiem.setBackground(btnchinh);
        table.getTableHeader().setBackground(maucotbang);
        table.getTableHeader().setForeground(chuchinh);
        btnquaylai.setBackground(btnchinh);
    }
    public void laydulieu(){
        DAO_KeToan dao = new DAO_KeToan();
        try{
            List<Object[]> danhsach = dao.laydulieuKHno();
            model.setRowCount(0);
            int i = 1;
            for (Object[] dong : danhsach){
                model.addRow(new Object[]{
                        i++,
                        dong[0],
                        dong[1],
                        dong[2],
                        dong[3],
                        dong[4],
                        dong[5],
                        dong[6]
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi lấy dữ liệu từ SQL: " + e.getMessage());
        }
    }
    public void tongnohethong() {
        int tongnohethong = 0;
        int tongno = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            tongno = Integer.parseInt(table.getValueAt(i, 6).toString());
            tongnohethong = tongnohethong + tongno;
        }
        txttongduno.setText(String.valueOf(tongnohethong));
    }

    public void timkiem(){
        String tenkhach = txttenkhachhang.getText().trim();
        String sdt = txtsdt.getText().trim();
        String mucno = txtmucno.getSelectedItem().toString();
        int mucno1 = 0;
        int mucno2 = 0;
        int mucno3 = 0;
        if (mucno.equals("Lớn hơn 10000000 VNĐ")){
            mucno1 = Integer.parseInt(mucno.replaceAll("[^0-9]",""));
        } else if(mucno.equals("Lớn hơn 30000000 VNĐ")){
            mucno2 = Integer.parseInt(mucno.replaceAll("[^0-9]",""));
        } else if(mucno.equals("Lớn hơn 50000000 VNĐ")){
            mucno3 = Integer.parseInt(mucno.replaceAll("[^0-9]",""));
        }
        for (int i = 0 ; i< table.getRowCount();i++){
            String ten = table.getValueAt(i,2).toString();
            String dt = table.getValueAt(i,3).toString();
            String tongno = table.getValueAt(i,6).toString();
            int TongNo = Integer.parseInt(tongno);

            if (ten.equalsIgnoreCase(tenkhach) && dt.equalsIgnoreCase(sdt) && TongNo > mucno1){
                table.setRowSelectionInterval(i,i);
                return;
            } else if (ten.equalsIgnoreCase(tenkhach) && dt.equalsIgnoreCase(sdt) && TongNo > mucno2){
                table.setRowSelectionInterval(i,i);
                return;
            } else if (ten.equalsIgnoreCase(tenkhach) && dt.equalsIgnoreCase(sdt) && TongNo > mucno3){
                table.setRowSelectionInterval(i,i);
                return;
            }
        }
    }
    private JButton taoNutBoGoc(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
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
        if (o == btntimkiem){
            timkiem();
        }
    }
}
