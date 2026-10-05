package View.LeTan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuanLyKhachHangPanel extends JPanel implements ActionListener {
    private final JPanel pNorth;
    private final JPanel pCenter;
    private final JPanel pSouth;
    private final JButton btnTimKH;
    private final JButton btnThemKH;
    private final CardLayout cardLayout;
    private final JPanel pDanhSach;

    public QuanLyKhachHangPanel(){
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        pDanhSach = new JPanel(new BorderLayout());

        JLabel lblTitle = new JLabel("QUẢN LÝ KHÁCH HÀNG VÀ TIẾP NHẬN XE");
        add(lblTitle, BorderLayout.NORTH);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(44, 62, 80));
        //PNorth
        pNorth = new JPanel();
        pDanhSach.add(pNorth, BorderLayout.NORTH);
        pNorth.add(lblTitle);
        //PCenter
        pCenter = new JPanel();
        //Tạo Bảng Khách Hàng
        String[] columnNames = {"Mã KH", "Họ Tên", "Biển Số Xe", "Trạng Thái"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        //Dữ liệu mẫu
        tableModel.addRow(new Object[]{"KH001", "Nguyễn Văn A", "51H-123.45", "Chờ tiếp nhận"});
        tableModel.addRow(new Object[]{"KH002", "Trần Thị B", "59G1-678.90", "Đang sửa chữa"});
        tableModel.addRow(new Object[]{"KH003", "Lê Văn C", "60A-111.22", "Đã bàn giao"});
        pDanhSach.add(scrollPane, BorderLayout.CENTER);

        //pSouth
        pSouth = new JPanel();
        pDanhSach.add(pSouth, BorderLayout.SOUTH);
        Box b = Box.createVerticalBox();

        Box b1, b2, b3;
        b.add(b1 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b1.add(new JLabel("Tìm Kiếm Theo Mã Khách Hàng:"));
        b1.add(Box.createHorizontalStrut(10));
        b1.add(new JTextField(15));
        b1.add(Box.createHorizontalStrut(10));
        b1.add(btnTimKH = new JButton("Tìm"));

        b.add(b2 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b2.add(btnThemKH = new JButton("Thêm Khách Hàng "));
        b2.add(Box.createHorizontalStrut(10));
        b2.add(new JButton("Quay lại"));

        pSouth.add(b);


        //Chuyển hướng Button Thêm Khách Hàng
        FormThemKHXePanel pFormThemKHXe = new FormThemKHXePanel();
        pFormThemKHXe.setBackground(Color.WHITE);
        //Thêm Card vào Panel
        add(pDanhSach, "DanhSach");
        add(pFormThemKHXe, "Thêm Khách Hàng");
        initListener();
    }

    private void initListener(){
        btnTimKH.addActionListener(this);
        btnThemKH.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(btnTimKH.equals(e)){

        }
        else if(source == btnThemKH){
            cardLayout.show(this,"Thêm Khách Hàng");
        }
    }
}
