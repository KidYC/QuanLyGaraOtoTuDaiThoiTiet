package View.KyThuatVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class KiemTraChatLuong extends JFrame {

    private JPanel headerPanel;
    private JSplitPane splitPane;

    // Panel bên trái (Danh sách xe chờ KCS)
    private JPanel leftPanel;
    private JTextField searchTxt;
    private JButton timBtn;
    private JTable tableXeKCS;
    private DefaultTableModel tableModelXeKCS;

    // Panel bên phải (Chi tiết & Đánh giá chất lượng)
    private JPanel rightPanel;
    private JTextField maPhieuTxt;
    private JTextField bienSoTxt;
    private JTextField khachHangTxt;
    private JTextField ktvThucHienTxt;

    private JTable tableChecklist;
    private DefaultTableModel tableModelChecklist;

    private JRadioButton datRbtn;
    private JRadioButton khongDatRbtn;
    private ButtonGroup danhGiaGroup;
    private JTextArea ghiChuTa;

    private JButton luuKcsBtn;
    private JButton lamMoiBtn;

    // Bảng màu chuẩn
    private Color backgroundColor = new Color(241, 245, 249);
    private Color primaryColor = new Color(37, 99, 235);
    private Color textColor = new Color(15, 23, 42);
    private Color secondaryColor = new Color(71, 85, 105);
    private Color borderColor = new Color(203, 213, 225);
    private Color dangerColor = new Color(220, 38, 38);
    private Color successColor = new Color(22, 163, 74);
    private Color purpleColor = new Color(124, 58, 237);

    public KiemTraChatLuong() {

        setTitle("Kiểm tra chất lượng (KCS) - Garage Management");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout(0, 10));

        taoHeader();
        taoContent();

        setVisible(true);
    }

    private void taoHeader() {

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        headerPanel.setPreferredSize(new Dimension(1200, 70));

        JLabel titleLb = new JLabel("KIỂM TRA CHẤT LƯỢNG XE SAU SỬA CHỮA (KCS)");
        titleLb.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLb.setForeground(textColor);

        headerPanel.add(titleLb, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void taoContent() {

        taoDanhSachXeKCSPanel();
        taoFormDanhGiaPanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(460);
        splitPane.setDividerSize(6);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));
        splitPane.setBackground(backgroundColor);

        add(splitPane, BorderLayout.CENTER);
    }

    private void taoDanhSachXeKCSPanel() {

        leftPanel = new JPanel(new BorderLayout(0, 12));
        leftPanel.setBackground(backgroundColor);

        // Ô tìm kiếm
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(backgroundColor);

        searchTxt = new JTextField();
        searchTxt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchTxt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        searchTxt.setPreferredSize(new Dimension(250, 40));

        timBtn = taoButton("Tìm xe", primaryColor);

        searchPanel.add(searchTxt, BorderLayout.CENTER);
        searchPanel.add(timBtn, BorderLayout.EAST);

        // Bảng danh sách xe chờ kiểm tra chất lượng
        String[] columns = {"Biển số", "Mã phiếu", "KTV phụ trách", "Trạng thái KCS"};
        Object[][] data = {
            {"51A-123.45", "PSC001", "Nguyễn Văn An", "Chờ kiểm tra"},
            {"59A-456.78", "PSC002", "Trần Văn Bình", "Chờ kiểm tra"},
            {"60A-111.11", "PSC003", "Lê Minh Hoàng", "Đang nghiệm thu"},
            {"61B-999.88", "PSC004", "Phạm Văn Nam", "Chờ kiểm tra"}
        };

        tableModelXeKCS = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableXeKCS = taoTable(tableModelXeKCS);
        JScrollPane scrollPane = new JScrollPane(tableXeKCS);
        scrollPane.setBorder(BorderFactory.createLineBorder(borderColor));
        scrollPane.getViewport().setBackground(Color.WHITE);

        leftPanel.add(searchPanel, BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        // Chọn xe từ bảng
        tableXeKCS.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hienThiChiTietXeDuocChon();
            }
        });
    }

    private void taoFormDanhGiaPanel() {

        rightPanel = new JPanel(new BorderLayout(0, 12));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(15, 18, 15, 18)
        ));

        // 1. Thông tin phiếu & xe nghiệm thu
        JPanel infoPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderColor),
                "Thông tin phương tiện nghiệm thu",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                secondaryColor
        ));

        maPhieuTxt = taoTextFieldReadOnly();
        bienSoTxt = taoTextFieldReadOnly();
        khachHangTxt = taoTextFieldReadOnly();
        ktvThucHienTxt = taoTextFieldReadOnly();

        infoPanel.add(taoLabel("Mã phiếu SC:"));
        infoPanel.add(maPhieuTxt);
        infoPanel.add(taoLabel("Biển số xe:"));
        infoPanel.add(bienSoTxt);

        infoPanel.add(taoLabel("Khách hàng:"));
        infoPanel.add(khachHangTxt);
        infoPanel.add(taoLabel("KTV thi công:"));
        infoPanel.add(ktvThucHienTxt);

        // 2. Bảng Checklist danh sách hạng mục đã sửa
        JPanel checklistPanel = new JPanel(new BorderLayout(0, 8));
        checklistPanel.setBackground(Color.WHITE);

        JLabel checklistTitle = taoLabel("Danh sách hạng mục công việc / vật tư cần KCS:");

        String[] colChecklist = {"STT", "Hạng mục sửa chữa", "Kết quả KTV", "Đánh giá KCS"};
        Object[][] dataChecklist = {
            {"1", "Thay nhớt động cơ & Lọc dầu", "Hoàn thành", "Đạt"},
            {"2", "Vệ sinh & cân chỉnh phanh 4 bánh", "Hoàn thành", "Đạt"},
            {"3", "Kiểm tra áp suất lốp & chạy thử", "Hoàn thành", "Đạt"}
        };

        tableModelChecklist = new DefaultTableModel(dataChecklist, colChecklist) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableChecklist = taoTable(tableModelChecklist);
        JScrollPane scrollChecklist = new JScrollPane(tableChecklist);
        scrollChecklist.setBorder(BorderFactory.createLineBorder(borderColor));
        scrollChecklist.setPreferredSize(new Dimension(0, 140));

        checklistPanel.add(checklistTitle, BorderLayout.NORTH);
        checklistPanel.add(scrollChecklist, BorderLayout.CENTER);

        // 3. Khối Đánh giá KCS & Ghi chú kết quả
        JPanel evaluationPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        evaluationPanel.setBackground(Color.WHITE);
        evaluationPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderColor),
                "Kết luận nghiệm thu chất lượng",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                secondaryColor
        ));

        datRbtn = new JRadioButton("ĐẠT (Chuyển chờ bàn giao)");
        datRbtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        datRbtn.setForeground(successColor);
        datRbtn.setBackground(Color.WHITE);
        datRbtn.setSelected(true);

        khongDatRbtn = new JRadioButton("KHÔNG ĐẠT (Yêu cầu sửa lại)");
        khongDatRbtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        khongDatRbtn.setForeground(dangerColor);
        khongDatRbtn.setBackground(Color.WHITE);

        danhGiaGroup = new ButtonGroup();
        danhGiaGroup.add(datRbtn);
        danhGiaGroup.add(khongDatRbtn);

        evaluationPanel.add(datRbtn);
        evaluationPanel.add(khongDatRbtn);

        // Ghi chú KCS
        JPanel notePanel = new JPanel(new BorderLayout(0, 5));
        notePanel.setBackground(Color.WHITE);

        ghiChuTa = taoTextArea();
        JScrollPane scrollNote = new JScrollPane(ghiChuTa);
        scrollNote.setBorder(BorderFactory.createLineBorder(borderColor));
        scrollNote.setPreferredSize(new Dimension(0, 80));

        notePanel.add(taoLabel("Ghi chú nghiệm thu / Lý do nếu Không đạt:"), BorderLayout.NORTH);
        notePanel.add(scrollNote, BorderLayout.CENTER);

        // Nút Thao tác
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setBackground(Color.WHITE);

        lamMoiBtn = taoButton("Làm mới", secondaryColor);
        luuKcsBtn = taoButton("Lưu kết quả KCS", successColor);
        luuKcsBtn.setPreferredSize(new Dimension(190, 40));

        btnPanel.add(lamMoiBtn);
        btnPanel.add(luuKcsBtn);

        // Ghép các Container
        JPanel centerContainer = new JPanel(new BorderLayout(0, 10));
        centerContainer.setBackground(Color.WHITE);
        centerContainer.add(infoPanel, BorderLayout.NORTH);
        centerContainer.add(checklistPanel, BorderLayout.CENTER);

        JPanel bottomContainer = new JPanel(new BorderLayout(0, 10));
        bottomContainer.setBackground(Color.WHITE);
        bottomContainer.add(evaluationPanel, BorderLayout.NORTH);
        bottomContainer.add(notePanel, BorderLayout.CENTER);
        bottomContainer.add(btnPanel, BorderLayout.SOUTH);

        rightPanel.add(centerContainer, BorderLayout.CENTER);
        rightPanel.add(bottomContainer, BorderLayout.SOUTH);

        luuKcsBtn.addActionListener(e -> luuKetQuaKCS());
        lamMoiBtn.addActionListener(e -> xoaForm());
    }

    private void hienThiChiTietXeDuocChon() {

        int row = tableXeKCS.getSelectedRow();

        if (row != -1) {
            String bienSo = tableModelXeKCS.getValueAt(row, 0).toString();
            String maPhieu = tableModelXeKCS.getValueAt(row, 1).toString();
            String ktv = tableModelXeKCS.getValueAt(row, 2).toString();

            bienSoTxt.setText(bienSo);
            maPhieuTxt.setText(maPhieu);
            ktvThucHienTxt.setText(ktv);

            if (bienSo.equals("51A-123.45")) {
                khachHangTxt.setText("Nguyễn Văn An");
            } else if (bienSo.equals("59A-456.78")) {
                khachHangTxt.setText("Trần Thị Bình");
            } else {
                khachHangTxt.setText("Lê Hoàng Nam");
            }
        }
    }

    private void luuKetQuaKCS() {

        int row = tableXeKCS.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn xe cần kiểm tra chất lượng từ danh sách!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (datRbtn.isSelected()) {
            tableModelXeKCS.setValueAt("Đạt - Chờ bàn giao", row, 3);
            JOptionPane.showMessageDialog(
                    this,
                    "N nghiệm thu KCS thành công!\nTrạng thái xe đã chuyển sang: ĐẠT (Đủ điều kiện bàn giao xe).",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            if (ghiChuTa.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng nhập lý do KHÔNG ĐẠT vào ô ghi chú nghiệm thu!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            tableModelXeKCS.setValueAt("Không đạt - Sửa lại", row, 3);
            JOptionPane.showMessageDialog(
                    this,
                    "Đã ghi nhận kết quả KHÔNG ĐẠT!\nYêu cầu sửa chữa đã được trả về cho KTV xử lý lại.",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        xoaForm();
    }

    private void xoaForm() {

        maPhieuTxt.setText("");
        bienSoTxt.setText("");
        khachHangTxt.setText("");
        ktvThucHienTxt.setText("");
        ghiChuTa.setText("");
        datRbtn.setSelected(true);
        tableXeKCS.clearSelection();
    }

    private JTable taoTable(DefaultTableModel model) {

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(36);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(textColor);
        table.setShowVerticalLines(false);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(30, 41, 59));
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));

        return table;
    }

    private JLabel taoLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(secondaryColor);

        return label;
    }

    private JTextField taoTextFieldReadOnly() {

        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setEditable(false);
        textField.setBackground(new Color(248, 250, 252));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(0, 8, 0, 8)
        ));

        return textField;
    }

    private JTextArea taoTextArea() {

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));

        return textArea;
    }

    private JButton taoButton(String text, Color color) {

        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    public static void main(String[] args) {
        new KiemTraChatLuong();
    }
}