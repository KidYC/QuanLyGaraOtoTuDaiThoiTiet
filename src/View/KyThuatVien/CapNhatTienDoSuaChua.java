package View.KyThuatVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class CapNhatTienDoSuaChua extends JFrame {

    private JPanel headerPanel;
    private JSplitPane splitPane;

    // Panel bên trái (Danh sách lệnh sửa chữa)
    private JPanel leftPanel;
    private JTextField searchTxt;
    private JButton timBtn;
    private JTable tableLenhSC;
    private DefaultTableModel tableModelLenhSC;

    // Panel bên phải (Chi tiết & Cập nhật tiến độ)
    private JPanel rightPanel;
    private JTextField maLenhTxt;
    private JTextField bienSoTxt;
    private JTextField khachHangTxt;
    
    private JTable tableChiTietCV;
    private DefaultTableModel tableModelChiTietCV;

    private JComboBox trangThaiCb;
    private JComboBox tienDoCb;
    private JProgressBar progressBar;
    private JTextArea ghiChuTa;

    private JButton luuTienDoBtn;
    private JButton lamMoiBtn;

    // Bảng màu chuẩn
    private Color backgroundColor = new Color(241, 245, 249);
    private Color primaryColor = new Color(37, 99, 235);
    private Color textColor = new Color(15, 23, 42);
    private Color secondaryColor = new Color(71, 85, 105);
    private Color borderColor = new Color(203, 213, 225);
    private Color dangerColor = new Color(220, 38, 38);
    private Color successColor = new Color(22, 163, 74);
    private Color warningColor = new Color(217, 119, 6);

    public CapNhatTienDoSuaChua() {

        setTitle("Công việc được phân công & Tiến độ - Garage Management");
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

        JLabel titleLb = new JLabel("CÔNG VIỆC ĐƯỢC PHÂN CÔNG & CẬP NHẬT TIẾN ĐỘ");
        titleLb.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLb.setForeground(textColor);

        headerPanel.add(titleLb, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void taoContent() {

        taoDanhSachLenhPanel();
        taoChiTietTienDoPanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(460);
        splitPane.setDividerSize(6);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));
        splitPane.setBackground(backgroundColor);

        add(splitPane, BorderLayout.CENTER);
    }

    private void taoDanhSachLenhPanel() {

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

        timBtn = taoButton("Tìm lệnh", primaryColor);

        searchPanel.add(searchTxt, BorderLayout.CENTER);
        searchPanel.add(timBtn, BorderLayout.EAST);

        // Bảng danh sách lệnh sửa chữa được phân công
        String[] columns = {"Mã lệnh", "Biển số", "Trạng thái", "Tiến độ"};
        Object[][] data = {
            {"SC001", "51A-123.45", "Đang sửa chữa", "60%"},
            {"SC002", "59A-456.78", "Chờ phụ tùng", "30%"},
            {"SC003", "60A-111.11", "Đang sửa chữa", "90%"},
            {"SC004", "61B-999.88", "Mới phân công", "0%"}
        };

        tableModelLenhSC = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableLenhSC = taoTable(tableModelLenhSC);
        JScrollPane scrollPane = new JScrollPane(tableLenhSC);
        scrollPane.setBorder(BorderFactory.createLineBorder(borderColor));
        scrollPane.getViewport().setBackground(Color.WHITE);

        leftPanel.add(searchPanel, BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        // Chọn dòng trên bảng để tải thông tin
        tableLenhSC.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hienThiChiTietLenhDuocChon();
            }
        });
    }

    private void taoChiTietTienDoPanel() {

        rightPanel = new JPanel(new BorderLayout(0, 12));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(15, 18, 15, 18)
        ));

        // 1. Thông tin chung về Lệnh sửa chữa
        JPanel infoPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderColor),
                "Thông tin lệnh sửa chữa",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                secondaryColor
        ));

        maLenhTxt = taoTextFieldReadOnly();
        bienSoTxt = taoTextFieldReadOnly();
        khachHangTxt = taoTextFieldReadOnly();

        infoPanel.add(taoLabel("Mã lệnh SC:"));
        infoPanel.add(taoLabel("Biển số xe:"));
        infoPanel.add(taoLabel("Khách hàng:"));

        infoPanel.add(maLenhTxt);
        infoPanel.add(bienSoTxt);
        infoPanel.add(khachHangTxt);

        // 2. Bảng các hạng mục công việc chi tiết
        JPanel taskTablePanel = new JPanel(new BorderLayout(0, 8));
        taskTablePanel.setBackground(Color.WHITE);

        JLabel taskTitle = taoLabel("Danh sách hạng mục công việc & phụ tùng đi kèm:");

        String[] colCV = {"STT", "Hạng mục công việc / Phụ tùng", "Loại", "Trạng thái CV"};
        Object[][] dataCV = {
            {"1", "Thay nhớt động cơ Castrol Magnatec", "Công việc + PT", "Hoàn thành"},
            {"2", "Thay lọc dầu Toyota chính hãng", "Phụ tùng", "Hoàn thành"},
            {"3", "Vệ sinh & cân chỉnh phanh 4 bánh", "Công việc", "Đang thi công"}
        };

        tableModelChiTietCV = new DefaultTableModel(dataCV, colCV) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableChiTietCV = taoTable(tableModelChiTietCV);
        JScrollPane scrollCV = new JScrollPane(tableChiTietCV);
        scrollCV.setBorder(BorderFactory.createLineBorder(borderColor));
        scrollCV.setPreferredSize(new Dimension(0, 140));

        taskTablePanel.add(taskTitle, BorderLayout.NORTH);
        taskTablePanel.add(scrollCV, BorderLayout.CENTER);

        // 3. Khối Cập nhật Trạng thái & Tiến độ
        JPanel updateFormPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        updateFormPanel.setBackground(Color.WHITE);
        updateFormPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderColor),
                "Cập nhật tiến độ & Kết quả thực hiện",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                secondaryColor
        ));

        trangThaiCb = new JComboBox<>(new String[]{
            "Đang sửa chữa",
            "Chờ phụ tùng",
            "Tạm dừng sửa chữa",
            "Hoàn thành"
        });
        trangThaiCb.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tienDoCb = new JComboBox<>(new String[]{
            "0%", "20%", "40%", "60%", "80%", "100%"
        });
        tienDoCb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tienDoCb.setSelectedItem("60%");

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(60);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        progressBar.setForeground(successColor);
        progressBar.setBackground(new Color(226, 232, 240));

        updateFormPanel.add(taoLabel("Trạng thái chung:"));
        updateFormPanel.add(trangThaiCb);

        updateFormPanel.add(taoLabel("Tiến độ tổng thể (%):"));
        updateFormPanel.add(tienDoCb);

        updateFormPanel.add(taoLabel("Thanh tiến độ:"));
        updateFormPanel.add(progressBar);

        // Ghi chú kết quả
        JPanel notePanel = new JPanel(new BorderLayout(0, 5));
        notePanel.setBackground(Color.WHITE);

        ghiChuTa = taoTextArea();
        JScrollPane scrollNote = new JScrollPane(ghiChuTa);
        scrollNote.setBorder(BorderFactory.createLineBorder(borderColor));
        scrollNote.setPreferredSize(new Dimension(0, 75));

        notePanel.add(taoLabel("Ghi chú kết quả / Báo cáo sự cố phát sinh:"), BorderLayout.NORTH);
        notePanel.add(scrollNote, BorderLayout.CENTER);

        // Cụm Nút Thao tác
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setBackground(Color.WHITE);

        lamMoiBtn = taoButton("Làm mới", secondaryColor);
        luuTienDoBtn = taoButton("Lưu tiến độ & Kết quả", successColor);
        luuTienDoBtn.setPreferredSize(new Dimension(210, 40));

        btnPanel.add(lamMoiBtn);
        btnPanel.add(luuTienDoBtn);

        // Gom các khối vào Right Panel
        JPanel centerContainer = new JPanel(new BorderLayout(0, 10));
        centerContainer.setBackground(Color.WHITE);
        centerContainer.add(infoPanel, BorderLayout.NORTH);
        centerContainer.add(taskTablePanel, BorderLayout.CENTER);

        JPanel bottomContainer = new JPanel(new BorderLayout(0, 10));
        bottomContainer.setBackground(Color.WHITE);
        bottomContainer.add(updateFormPanel, BorderLayout.NORTH);
        bottomContainer.add(notePanel, BorderLayout.CENTER);
        bottomContainer.add(btnPanel, BorderLayout.SOUTH);

        rightPanel.add(centerContainer, BorderLayout.CENTER);
        rightPanel.add(bottomContainer, BorderLayout.SOUTH);

        // Lắng nghe sự kiện đổi ComboBox tiến độ để cập nhật ProgressBar
        tienDoCb.addActionListener(e -> {
            String val = tienDoCb.getSelectedItem().toString().replace("%", "");
            progressBar.setValue(Integer.parseInt(val));
        });

        luuTienDoBtn.addActionListener(e -> luuTienDo());
        lamMoiBtn.addActionListener(e -> xoaForm());
    }

    private void hienThiChiTietLenhDuocChon() {

        int row = tableLenhSC.getSelectedRow();

        if (row != -1) {
            String maLenh = tableModelLenhSC.getValueAt(row, 0).toString();
            String bienSo = tableModelLenhSC.getValueAt(row, 1).toString();
            String trangThai = tableModelLenhSC.getValueAt(row, 2).toString();
            String tienDo = tableModelLenhSC.getValueAt(row, 3).toString();

            maLenhTxt.setText(maLenh);
            bienSoTxt.setText(bienSo);

            if (bienSo.equals("51A-123.45")) {
                khachHangTxt.setText("Nguyễn Văn An");
            } else if (bienSo.equals("59A-456.78")) {
                khachHangTxt.setText("Trần Thị Bình");
            } else {
                khachHangTxt.setText("Lê Hoàng Nam");
            }

            trangThaiCb.setSelectedItem(trangThai);
            tienDoCb.setSelectedItem(tienDo);
            progressBar.setValue(Integer.parseInt(tienDo.replace("%", "")));
        }
    }

    private void luuTienDo() {

        int row = tableLenhSC.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn lệnh sửa chữa cần cập nhật tiến độ!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String trangThaiMoi = trangThaiCb.getSelectedItem().toString();
        String tienDoMoi = tienDoCb.getSelectedItem().toString();

        // Cập nhật lên Bảng danh sách bên trái
        tableModelLenhSC.setValueAt(trangThaiMoi, row, 2);
        tableModelLenhSC.setValueAt(tienDoMoi, row, 3);

        JOptionPane.showMessageDialog(
                this,
                "Cập nhật tiến độ & trạng thái lệnh sửa chữa thành công!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void xoaForm() {

        maLenhTxt.setText("");
        bienSoTxt.setText("");
        khachHangTxt.setText("");
        ghiChuTa.setText("");
        tableLenhSC.clearSelection();
        progressBar.setValue(0);
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
        new CapNhatTienDoSuaChua();
    }
}