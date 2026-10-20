package View.LeTan;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

public class FormatMau {

    public static final Color COLOR_BLUE_DARK  = Color.decode("#6C7EE1"); // Xanh tím đậm
    public static final Color COLOR_BLUE_LIGHT = Color.decode("#92B9E3"); // Xanh dương nhạt
    public static final Color COLOR_PEACH = Color.decode("#FFC4A4"); // Cam đào (Peach)
    public static final Color COLOR_PINK = Color.decode("#FBA2D0"); // Hồng pastel
    public static final Color COLOR_PURPLE = Color.decode("#C688EB"); // Tím pastel

    public static final Color MAU_NEN_CHUNG = new Color(250, 252, 255); // Trắng pha chút xanh rất nhẹ
    public static final Color CHU_TRANG = Color.WHITE;
    public static final Color CHU_DEN = new Color(40, 40, 40);

    public static void formatPanel(JPanel panel) {
        panel.setBackground(MAU_NEN_CHUNG);
    }

    public static void formatTitleLabel(JLabel label) {
        label.setForeground(COLOR_BLUE_DARK);
    }

    public static void formatLabel(JLabel label) {
        label.setForeground(CHU_DEN);
    }

    public static void formatPrimaryButton(JButton button) {
        button.setBackground(COLOR_BLUE_DARK);
        button.setForeground(CHU_TRANG);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }

    public static void formatSecondaryButton(JButton button) {
        button.setBackground(COLOR_BLUE_LIGHT);
        button.setForeground(CHU_DEN);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }

    public static void formatHighlightButton(JButton button) {
        button.setBackground(COLOR_PURPLE);
        button.setForeground(CHU_TRANG);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }
    public static void formatWarningButton(JButton button) {
        button.setBackground(COLOR_PEACH);
        button.setForeground(CHU_DEN);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }
    public static void formatTable(JTable table) {
        table.setBackground(Color.WHITE);
        table.setForeground(CHU_DEN);
        table.setRowHeight(30);
        table.setSelectionBackground(COLOR_BLUE_LIGHT);
        table.setSelectionForeground(CHU_DEN);

        JTableHeader header = table.getTableHeader();
        header.setBackground(COLOR_PINK);
        header.setForeground(CHU_DEN); // Chữ đen cho dễ đọc trên nền hồng
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setOpaque(true);
    }
}