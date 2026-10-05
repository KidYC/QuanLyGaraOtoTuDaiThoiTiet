package View.LeTan;

import javax.swing.*;
import java.awt.*;

public class DanhSachXeChoBaoGiaPanel extends JPanel {
    public DanhSachXeChoBaoGiaPanel(){
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Danh Sách Xe Chờ Báo Giá");
        add(lblTitle, BorderLayout.NORTH);

        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(44, 62, 80));
    }

}

