package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class DangNhap extends JFrame {
    private JPanel pNo;
    private JLabel logo;
    private JPanel pCen;
    private JPanel card;
    private JLabel title;
    private JTextField tenTxt;
    private JPasswordField mkTxt;
    private JLabel userIcon;
    private JLabel lockIcon;
    private JPanel userPanel;
    private JPanel passPanel;
    private JLabel tenLb;
    private JLabel mkLb;
    private JButton dnBtn;
    private JCheckBox ghiNhoCb;
    private JLabel quenMkLb;
    public DangNhap() {
        setTitle("Đăng nhập");
        Color backgroundColor = new Color(241, 245, 249);
        Color cardColor = Color.WHITE;
        Color textColor = new Color(15, 23, 42);
        Color secondaryColor = new Color(71, 85, 105);
        Color borderColor = new Color(203, 213, 225);
        Color buttonColor = new Color(37, 99, 235);
        getContentPane().setBackground(backgroundColor);

        pNo = new JPanel(new BorderLayout());
        pNo.setBackground(backgroundColor);

        logo = new JLabel(loadIcon("/resources/icons/logo.png", 230, 210));
        logo.setHorizontalAlignment(JLabel.CENTER);

        pNo.add(logo, BorderLayout.CENTER);
        add(pNo, BorderLayout.NORTH);

        pCen = new JPanel(new GridBagLayout());
        pCen.setBackground(backgroundColor);

        card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardColor);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(25, 15, 25, 15)
        ));

        card.setPreferredSize(new Dimension(570, 350));
        card.setMaximumSize(new Dimension(570, 350));

        title = new JLabel("Đăng nhập");
        title.setFont(new Font("Segoe UI", Font.BOLD, 34));
        title.setForeground(textColor);
        title.setAlignmentX(CENTER_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(30));

        JPanel form = new JPanel(new GridLayout(2, 1, 0, 20));
        form.setBackground(cardColor);
        form.setAlignmentX(CENTER_ALIGNMENT);
        form.setMaximumSize(new Dimension(540, 100));

        JPanel usernameBox = new JPanel(new BorderLayout(15, 0));
        usernameBox.setBackground(cardColor);

        tenLb = new JLabel("Mã nhân viên");
        tenLb.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tenLb.setForeground(secondaryColor);
        tenLb.setPreferredSize(new Dimension(110, 42));

        userPanel = new JPanel(new BorderLayout());
        userPanel.setBackground(Color.WHITE);

        userPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));

        userPanel.setPreferredSize(new Dimension(415, 42));
        userPanel.setMaximumSize(new Dimension(415, 42));

        userIcon = new JLabel(loadIcon("/resources/icons/user.png", 22, 22));
        userIcon.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 10));

        tenTxt = new JTextField();
        tenTxt.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tenTxt.setBorder(null);
        tenTxt.setBackground(Color.WHITE);

        userPanel.add(userIcon, BorderLayout.WEST);
        userPanel.add(tenTxt, BorderLayout.CENTER);

        usernameBox.add(tenLb, BorderLayout.WEST);
        usernameBox.add(userPanel, BorderLayout.CENTER);

        JPanel passwordBox = new JPanel(new BorderLayout(15, 0));
        passwordBox.setBackground(cardColor);

        mkLb = new JLabel("Mật khẩu");
        mkLb.setFont(new Font("Segoe UI", Font.BOLD, 15));
        mkLb.setForeground(secondaryColor);
        mkLb.setPreferredSize(new Dimension(110, 42));

        passPanel = new JPanel(new BorderLayout());
        passPanel.setBackground(Color.WHITE);

        passPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));

        passPanel.setPreferredSize(new Dimension(415, 42));
        passPanel.setMaximumSize(new Dimension(415, 42));

        lockIcon = new JLabel(loadIcon("/resources/icons/lock.png", 22, 22));
        lockIcon.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 10));

        mkTxt = new JPasswordField();
        mkTxt.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        mkTxt.setBorder(null);
        mkTxt.setBackground(Color.WHITE);

        passPanel.add(lockIcon, BorderLayout.WEST);
        passPanel.add(mkTxt, BorderLayout.CENTER);

        passwordBox.add(mkLb, BorderLayout.WEST);
        passwordBox.add(passPanel, BorderLayout.CENTER);

        form.add(usernameBox);
        form.add(passwordBox);

        card.add(form);
        card.add(Box.createVerticalStrut(15));

        JPanel options = new JPanel(new BorderLayout());
        options.setBackground(cardColor);
        options.setAlignmentX(CENTER_ALIGNMENT);
        options.setMaximumSize(new Dimension(540, 30));

        JPanel optionsInput = new JPanel(new BorderLayout());
        optionsInput.setBackground(cardColor);

        ghiNhoCb = new JCheckBox("Ghi nhớ tài khoản");
        ghiNhoCb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ghiNhoCb.setForeground(secondaryColor);
        ghiNhoCb.setBackground(cardColor);
        ghiNhoCb.setFocusPainted(false);
        ghiNhoCb.setBorder(null);

        quenMkLb = new JLabel("Quên mật khẩu?");
        quenMkLb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        quenMkLb.setForeground(buttonColor);
        quenMkLb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        optionsInput.add(ghiNhoCb, BorderLayout.WEST);
        optionsInput.add(quenMkLb, BorderLayout.EAST);
        options.add(Box.createHorizontalStrut(125), BorderLayout.WEST);
        options.add(optionsInput, BorderLayout.CENTER);
        card.add(options);
        card.add(Box.createVerticalStrut(22));

        dnBtn = new JButton("Đăng nhập");
        dnBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        dnBtn.setForeground(Color.WHITE);
        dnBtn.setBackground(buttonColor);
        dnBtn.setFocusPainted(false);
        dnBtn.setBorderPainted(false);
        dnBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dnBtn.setAlignmentX(CENTER_ALIGNMENT);

        dnBtn.setPreferredSize(new Dimension(540, 45));
        dnBtn.setMaximumSize(new Dimension(540, 45));

        card.add(dnBtn);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 0, 20, 0);
        pCen.add(card, gbc);
        add(pCen, BorderLayout.CENTER);
        tenTxt.addActionListener(e -> mkTxt.requestFocus());
        mkTxt.addActionListener(e -> dnBtn.doClick());

        setSize(650, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private ImageIcon loadIcon(String path, int w, int h) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
        new DangNhap().setVisible(true);
    }
}