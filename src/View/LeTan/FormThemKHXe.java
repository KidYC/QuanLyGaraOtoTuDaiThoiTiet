package View.LeTan;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormThemKHXe extends JFrame implements ActionListener {


    private JLabel lblTitle;
    private JPanel pCenter;
    private GridBagConstraints gbc;
    private JLabel lblTen;
    private JTextField txtTen;
    private JLabel lblSDT;

    private JTextField txtSDT;
    private JPanel pSouth;
    private JButton btnLuu;

    public FormThemKHXe(){
        setTitle("Form Thêm Khách Hàng Và Xe");
        setSize(700, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildUI();
    }
    public void buildUI(){
        //pNorth
        lblTitle = new JLabel("FORM THÊM KHÁCH HÀNG VÀ THÊM XE");
        add(lblTitle, BorderLayout.NORTH);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(44, 62, 80));
        //pCenter
        pCenter = new JPanel(new GridBagLayout());
        add(pCenter, BorderLayout.CENTER);
        Box b = Box.createVerticalBox();
        pCenter.add(b);
        setPreferredSize(new Dimension(250, 0));
        setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông tin chung & Khách hàng - Xe",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(44, 62, 80)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 0;
        gbc.gridx = 0;
        lblTen = new JLabel("Tên Khách Hàng: ");
        pCenter.add(lblTen, gbc);

        gbc.gridx = 1;
        txtTen = new JTextField(15);
        pCenter.add(txtTen, gbc);

        gbc.gridx = 2;
        lblSDT = new JLabel("Số Điện Thoai: ");
        pCenter.add(lblSDT, gbc);

        gbc.gridx = 3;
        txtSDT = new JTextField(15);
        pCenter.add(txtSDT, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel lblDiaChi = new JLabel("Địa Chỉ: ");
        pCenter.add(lblDiaChi, gbc);

        gbc.gridx = 1;
        JTextField txtDiaChi = new JTextField(15);
        pCenter.add(txtDiaChi, gbc);

        gbc.gridx = 2;
        JLabel lblEmail = new JLabel("Email: ");
        pCenter.add(lblEmail, gbc);

        gbc.gridx = 3;
        JTextField txtEmail = new JTextField(15);
        pCenter.add(txtEmail, gbc);

        gbc.gridy = 2;

        gbc.gridx = 0;
        JLabel lblLoaiXe = new JLabel("Loại Xe:");
        pCenter.add(lblLoaiXe, gbc);

        gbc.gridx = 1;
        JTextField txtLoaiXe = new JTextField(15);
        pCenter.add(txtLoaiXe, gbc);

        gbc.gridx = 2;
        JLabel lblBienSo = new JLabel("Biển Số Xe:");
        pCenter.add(lblBienSo, gbc);

        gbc.gridx = 3;
        JTextField txtBienSo = new JTextField(15);
        pCenter.add(txtBienSo, gbc);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(pCenter, BorderLayout.NORTH);

        add(wrapperPanel, BorderLayout.CENTER);

        pSouth = new JPanel();
        add(pSouth, BorderLayout.SOUTH);
        btnLuu = new JButton("Lưu");
        pSouth.add(btnLuu);

        initListener();
    }
    public void initListener(){
        btnLuu.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(btnLuu == source){
        }
    }

    public static void main() {
        FormThemKHXe UI = new FormThemKHXe();
        UI.setVisible(true);
    }
}
