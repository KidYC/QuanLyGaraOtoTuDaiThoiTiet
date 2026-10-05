package View.LeTan;

import javax.swing.*;
import java.awt.*;

public class FormThemKHXePanel extends JPanel {
    private final JLabel lblTitle;
    private final CardLayout cardLayout;
    private final JPanel pFormThemKHXe;

    public FormThemKHXePanel(){

        cardLayout = new CardLayout();
        setLayout(cardLayout);
        pFormThemKHXe = new JPanel();
        pFormThemKHXe.setLayout(new BorderLayout());

        //pNorth
        lblTitle = new JLabel("FORM THÊM KHÁCH HÀNG VÀ THÊM XE");
        pFormThemKHXe.add(lblTitle, BorderLayout.NORTH);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(44, 62, 80));
        //pCenter


        add(pFormThemKHXe, "FormThemKHXE");
    }
}
