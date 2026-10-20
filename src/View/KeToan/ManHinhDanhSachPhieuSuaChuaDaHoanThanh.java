package View.KeToan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import DAO.DAO_KeToan;
import java.sql.SQLException;
import java.util.List;

public class ManHinhDanhSachPhieuSuaChuaDaHoanThanh extends JFrame implements ActionListener {
    private final JPanel pCenter;
    private final JPanel pNorth;
    private final JLabel lbltieude;
    private final JPanel pWest;
    private final JLabel lblmenu;
    private final JPanel card;
    private final JLabel trangthai;
    private final JLabel timkiem;
    private final JTextField txttimkiem;
    private final JComboBox<String> txttrangthai;
    private final DefaultTableModel model;
    private final JTable table;
    private final JScrollPane scroll;
    private final JButton btnTim;
    private final JLabel khoangtrong;
    private JButton trangchu;
    private JButton qlca;
    private JButton dsphieusuachua;
    private JButton quanlycongno;
    private JButton baocaodoanhthu;
    private JButton quaylai;
    public ManHinhDanhSachPhieuSuaChuaDaHoanThanh(){
        setSize(1000,480);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0,10));

        pNorth = new JPanel();

        lbltieude = new JLabel("DANH SÁCH PHIẾU SỬA CHỮA ĐÃ HOÀN THÀNH");
        lbltieude.setFont(new Font("Arial",Font.BOLD,30));
        lbltieude.setForeground(Color.BLUE);

        pNorth.add(lbltieude);

        add(pNorth,BorderLayout.NORTH);

        //pWest
        pWest = new JPanel();
        pWest.setLayout(new BoxLayout(pWest,BoxLayout.Y_AXIS));
        pWest.setBackground(new Color(240,245,250));
        pWest.setPreferredSize(new Dimension(200,0));

        lblmenu = new JLabel("MENU CHÍNH");
        lblmenu.setFont(new Font("Arial",Font.BOLD,20));
        lblmenu.setAlignmentX(Component.CENTER_ALIGNMENT);

        trangchu = taoNutBoGoc("Trang chủ kế toán");
        trangchu.setAlignmentX(Component.CENTER_ALIGNMENT);
        trangchu.setMaximumSize(new Dimension(190, 35));

        qlca = taoNutBoGoc("Quản lý ca");
        qlca.setAlignmentX(Component.CENTER_ALIGNMENT);
        qlca.setMaximumSize(new Dimension(190, 35));



        quanlycongno = taoNutBoGoc("Quản lý công nợ");
        quanlycongno.setAlignmentX(Component.CENTER_ALIGNMENT);
        quanlycongno.setMaximumSize(new Dimension(190, 35));

        baocaodoanhthu = taoNutBoGoc("Báo cáo doanh thu");
        baocaodoanhthu.setAlignmentX(Component.CENTER_ALIGNMENT);
        baocaodoanhthu.setMaximumSize(new Dimension(190, 35));

        quaylai = taoNutBoGoc("Quay lại trang chủ");
        quaylai.setAlignmentX(Component.CENTER_ALIGNMENT);
        quaylai.setMaximumSize(new Dimension(190,35));


        pWest.add(Box.createVerticalStrut(10));
        pWest.add(lblmenu);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(trangchu);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(qlca);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(quanlycongno);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(baocaodoanhthu);
        pWest.add(Box.createVerticalStrut(20));
        pWest.add(quaylai);
        add(pWest,BorderLayout.WEST);

        //pCenter
        pCenter = new JPanel();
        pCenter.setLayout(new BorderLayout());

        card = new JPanel();
        card.setLayout(new GridLayout(3,2,5,20));


        card.setMaximumSize(new Dimension(700, 120));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        timkiem = new JLabel("Ô nhập tìm kiếm (Mã Phiếu): ");
        timkiem.setFont(new Font("Arial",Font.BOLD,15));
        txttimkiem = new JTextField();
        trangthai = new JLabel("Trạng thái: ");
        trangthai.setFont(new Font("Arial",Font.BOLD,15));
        txttrangthai = new JComboBox<>(new String []{"Tất cả","Đang xử lý","Hoàn thành"});
        btnTim = taoNutBoGoc("Tìm");
        khoangtrong = new JLabel("");


        card.add(timkiem);
        card.add(txttimkiem);
        card.add(trangthai);
        card.add(txttrangthai);
        card.add(khoangtrong);
        card.add(btnTim);
        pCenter.add(card,BorderLayout.NORTH);


        String[] columns = {"STT","Mã Phiếu","Biển Số Xe","Tên Khách Hàng","Số Điện Thoại","Kỹ Thuật Viên","Tổng Tiền","Trạng Thái"};
        model = new DefaultTableModel(columns,0);
        table = new JTable(model);
        scroll = new JScrollPane(table);
        pCenter.add(scroll,BorderLayout.CENTER);
        add(pCenter,BorderLayout.CENTER);
        trangchu.addActionListener(this);
        qlca.addActionListener(this);
        quanlycongno.addActionListener(this);
        baocaodoanhthu.addActionListener(this);
        quaylai.addActionListener(this);
        btnTim.addActionListener(this);
        chinhmau();
        laydulieu();
    }
    private void chinhmau (){
        Color maunen = new Color(244, 246, 248);
        Color maumenu = new Color(226, 232, 240);
        Color btnchinh =  new Color(186, 230, 253);
        Color btnphu = new Color(219, 234, 254);
        Color chuchinh = new Color(17, 24, 39);

        lbltieude.setForeground(chuchinh);
        lblmenu.setForeground(chuchinh);
        trangchu.setBackground(btnchinh);
        qlca.setBackground(btnchinh);
        quanlycongno.setBackground(btnchinh);
        baocaodoanhthu.setBackground(btnchinh);
        quaylai.setBackground(btnchinh);
        timkiem.setForeground(chuchinh);
        trangthai.setForeground(chuchinh);
        btnTim.setBackground(btnchinh);
        pNorth.setBackground(maunen);
        pWest.setBackground(maumenu);
        pCenter.setBackground(maunen);
        table.getTableHeader().setBackground(new Color(186,230,253));
    }

    public void laydulieu(){
        DAO_KeToan dao = new DAO_KeToan();
        try {
            List<Object[]> danhSach = dao.layDanhSachPhieuSuaChuaDaHoanThanh();
            model.setRowCount(0);
            int i = 1;
            for (Object[] dong : danhSach){
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
        } catch (SQLException e){
            JOptionPane.showMessageDialog(this,
                    "Lỗi lấy dữ liệu từ SQL: " + e.getMessage());
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
    public void TimKiem(){
        String maphieu = txttimkiem.getText().trim();
        if (maphieu.isEmpty()){
            JOptionPane.showMessageDialog(this,"Vui lòng nhập thông tin tìm kiếm");
            return;
        }
        for (int i = 0; i < table.getRowCount();i++){
            String ma = table.getValueAt(i,1).toString();
            if (ma.equalsIgnoreCase(maphieu)){
                table.setRowSelectionInterval(i,i);
                return;
            }
            else {
              table.clearSelection();
              JOptionPane.showMessageDialog(this,"Không tìm thấy mã phiếu!");
            }
        }
    }
    public static void main(String[] args) {
        ManHinhDanhSachPhieuSuaChuaDaHoanThanh UI = new ManHinhDanhSachPhieuSuaChuaDaHoanThanh();
        UI.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == trangchu){
            ManHinhTrangChuKeToan UI = new ManHinhTrangChuKeToan();
            UI.setVisible(true);
            dispose();
        } else if (o == qlca) {
            ManHinhQuanLyCa UI = new ManHinhQuanLyCa();
            UI.setVisible(true);
            dispose();
        }else if (o== quanlycongno){
            ManHinhDanhSachKHNo UI = new ManHinhDanhSachKHNo();
            UI.setVisible(true);
            dispose();
        }else if (o == quaylai) {
            ManHinhTrangChuKeToan UI = new ManHinhTrangChuKeToan();
            UI.setVisible(true);
            dispose();
        } else if(o == btnTim){
            TimKiem();
        }
    }
}
