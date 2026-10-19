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
import java.net.URL;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import DAO.TaiKhoan_DAO;
import View.HeThong.TrangChuHeThong;
import Entity.TaiKhoan;

public class DangNhap extends JFrame {
    private JPanel pnlNo;
    private JLabel lblLogo;
    private JPanel pnlCen;
    private JPanel pnlCard;
    private JLabel lblTitle;
    private JTextField txtTen;
    private JPasswordField txtMk;
    private JLabel lblUserIcon;
    private JLabel lblLockIcon;
    private JPanel pnlUserPanel;
    private JPanel pnlPassPanel;
    private JLabel lblTen;
    private JLabel lblMk;
    private JButton btnDn;
    private final TaiKhoan_DAO dangNhapDAO = new TaiKhoan_DAO();

    public DangNhap() {
        setTitle("Đăng nhập");
        Color backgroundColor = new Color(241, 245, 249);
        Color cardColor = Color.WHITE;
        Color textColor = new Color(15, 23, 42);
        Color secondaryColor = new Color(71, 85, 105);
        Color borderColor = new Color(203, 213, 225);
        Color buttonColor = new Color(37, 99, 235);
        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout());

        pnlNo = new JPanel(new BorderLayout());
        pnlNo.setBackground(backgroundColor);
        lblLogo = new JLabel(loadIcon("/resources/icons/logo.png", 230, 210));
        lblLogo.setHorizontalAlignment(JLabel.CENTER);
        pnlNo.add(lblLogo, BorderLayout.CENTER);
        add(pnlNo, BorderLayout.NORTH);

        pnlCen = new JPanel(new GridBagLayout());
        pnlCen.setBackground(backgroundColor);
        pnlCard = new JPanel();
        pnlCard.setLayout(new BoxLayout(pnlCard, BoxLayout.Y_AXIS));
        pnlCard.setBackground(cardColor);
        pnlCard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1), BorderFactory.createEmptyBorder(25, 15, 25, 15)));
        pnlCard.setPreferredSize(new Dimension(570, 350));
        pnlCard.setMaximumSize(new Dimension(570, 350));

        lblTitle = new JLabel("Đăng nhập");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lblTitle.setForeground(textColor);
        lblTitle.setAlignmentX(CENTER_ALIGNMENT);
        pnlCard.add(lblTitle);
        pnlCard.add(Box.createVerticalStrut(30));

        JPanel pnlForm = new JPanel(new GridLayout(2, 1, 0, 20));
        pnlForm.setBackground(cardColor);
        pnlForm.setAlignmentX(CENTER_ALIGNMENT);
        pnlForm.setMaximumSize(new Dimension(540, 100));

        JPanel pnlUsernameBox = new JPanel(new BorderLayout(15, 0));
        pnlUsernameBox.setBackground(cardColor);
        lblTen = new JLabel("Tài khoản");
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTen.setForeground(secondaryColor);
        lblTen.setPreferredSize(new Dimension(110, 42));
        pnlUserPanel = taoONhap(borderColor, 415, 42);
        lblUserIcon = new JLabel(loadIcon("/resources/icons/user.png", 22, 22));
        lblUserIcon.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 10));
        txtTen = new JTextField();
        txtTen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtTen.setBorder(null);
        txtTen.setBackground(Color.WHITE);
        pnlUserPanel.add(lblUserIcon, BorderLayout.WEST);
        pnlUserPanel.add(txtTen, BorderLayout.CENTER);
        pnlUsernameBox.add(lblTen, BorderLayout.WEST);
        pnlUsernameBox.add(pnlUserPanel, BorderLayout.CENTER);

        JPanel pnlPasswordBox = new JPanel(new BorderLayout(15, 0));
        pnlPasswordBox.setBackground(cardColor);
        lblMk = new JLabel("Mật khẩu");
        lblMk.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblMk.setForeground(secondaryColor);
        lblMk.setPreferredSize(new Dimension(110, 42));
        pnlPassPanel = taoONhap(borderColor, 415, 42);
        lblLockIcon = new JLabel(loadIcon("/resources/icons/lock.png", 22, 22));
        lblLockIcon.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 10));
        txtMk = new JPasswordField();
        txtMk.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtMk.setBorder(null);
        txtMk.setBackground(Color.WHITE);
        pnlPassPanel.add(lblLockIcon, BorderLayout.WEST);
        pnlPassPanel.add(txtMk, BorderLayout.CENTER);
        pnlPasswordBox.add(lblMk, BorderLayout.WEST);
        pnlPasswordBox.add(pnlPassPanel, BorderLayout.CENTER);
        pnlForm.add(pnlUsernameBox);
        pnlForm.add(pnlPasswordBox);
        pnlCard.add(pnlForm);
        pnlCard.add(Box.createVerticalStrut(15));

        JPanel pnlOptions = new JPanel(new BorderLayout());
        pnlOptions.setBackground(cardColor);
        pnlOptions.setAlignmentX(CENTER_ALIGNMENT);
        pnlOptions.setMaximumSize(new Dimension(540, 30));
        JPanel pnlOptionsInput = new JPanel(new BorderLayout());
        pnlOptionsInput.setBackground(cardColor);
        pnlOptions.add(Box.createHorizontalStrut(125), BorderLayout.WEST);
        pnlOptions.add(pnlOptionsInput, BorderLayout.CENTER);
        pnlCard.add(pnlOptions);
        pnlCard.add(Box.createVerticalStrut(22));

        btnDn = new JButton("Đăng nhập");
        btnDn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnDn.setForeground(Color.WHITE);
        btnDn.setBackground(buttonColor);
        btnDn.setFocusPainted(false);
        btnDn.setBorderPainted(false);
        btnDn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDn.setAlignmentX(CENTER_ALIGNMENT);
        btnDn.setPreferredSize(new Dimension(540, 45));
        btnDn.setMaximumSize(new Dimension(540, 45));
        btnDn.addActionListener(e -> xuLyDangNhap());
        pnlCard.add(btnDn);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 0, 20, 0);
        pnlCen.add(pnlCard, gbc);
        add(pnlCen, BorderLayout.CENTER);
        txtTen.addActionListener(e -> txtMk.requestFocus());
        txtMk.addActionListener(e -> xuLyDangNhap());
        setSize(650, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel taoONhap(Color borderColor, int width, int height) {
        JPanel pnlPanel = new JPanel(new BorderLayout());
        pnlPanel.setBackground(Color.WHITE);
        pnlPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borderColor, 1), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        pnlPanel.setPreferredSize(new Dimension(width, height));
        pnlPanel.setMaximumSize(new Dimension(width, height));
        return pnlPanel;
    }

    private void xuLyDangNhap() {
        String tenDangNhap = txtTen.getText().trim();
        String matKhau = new String(txtMk.getPassword());
        if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }
        btnDn.setEnabled(false);
        try {
            // SỬA LỖI: Đổi DangNhap thành TaiKhoan
            TaiKhoan taiKhoan = dangNhapDAO.dangNhap(tenDangNhap, matKhau);

            if (taiKhoan == null) {
                JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu không chính xác.", "Đăng nhập thất bại", JOptionPane.ERROR_MESSAGE);
                txtMk.setText("");
                txtMk.requestFocus();
                return;
            }
            TrangChuHeThong.VaiTro vaiTro = TrangChuHeThong.chuyenVaiTro(taiKhoan.getLoaiTaiKhoan());
            if (vaiTro == null) {
                JOptionPane.showMessageDialog(this, "Loại tài khoản chưa được hỗ trợ: " + taiKhoan.getLoaiTaiKhoan(), "Không thể đăng nhập", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String maNV = taiKhoan.getMaNhanVien();
            if (maNV == null || maNV.trim().isEmpty() || "NULL".equalsIgnoreCase(maNV)) maNV = taiKhoan.getMaTaiKhoan();
            dispose();
            new TrangChuHeThong(maNV, taiKhoan.getHoTen(), vaiTro).setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối hoặc truy vấn cơ sở dữ liệu.\n" + ex.getMessage(), "Lỗi cơ sở dữ liệu", JOptionPane.ERROR_MESSAGE);
        } finally {
            btnDn.setEnabled(true);
        }
    }

    private ImageIcon loadIcon(String path, int w, int h) {
        URL url = getClass().getResource(path);
        if (url == null) return null;
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new DangNhap().setVisible(true));
    }
}