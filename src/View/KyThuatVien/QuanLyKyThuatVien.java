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
	import javax.swing.JDialog;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JScrollPane;
	import javax.swing.JTable;
	import javax.swing.JTextField;
	import javax.swing.ListSelectionModel;
	import javax.swing.SwingConstants;
	import javax.swing.table.DefaultTableModel;
	
	public class QuanLyKyThuatVien extends JFrame {
	
	    private JPanel headerPanel;
	    private JPanel contentPanel;
	    private JPanel searchPanel;
	    private JPanel buttonPanel;
	
	    private JLabel titleLb;
	    private JLabel searchLb;
	
	    private JTextField searchTxt;
	    private JButton timBtn;
	    private JButton themBtn;
	    private JButton suaBtn;
	    private JButton xoaBtn;
	    private JButton phanQuyenBtn;
	
	    private JTable table;
	    private JScrollPane scrollPane;
	    private DefaultTableModel tableModel;
	
	    private Color backgroundColor = new Color(241, 245, 249);
	    private Color primaryColor = new Color(37, 99, 235);
	    private Color textColor = new Color(15, 23, 42);
	    private Color secondaryColor = new Color(71, 85, 105);
	    private Color borderColor = new Color(203, 213, 225);
	    private Color dangerColor = new Color(220, 38, 38);
	    private Color successColor = new Color(22, 163, 74);
	
	    public QuanLyKyThuatVien() {
	
	        setTitle("Quản lý kỹ thuật viên");
	        setSize(1000, 650);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	
	        getContentPane().setBackground(backgroundColor);
	        setLayout(new BorderLayout(0, 15));
	
	        taoHeader();
	        taoContent();
	        taoBang();
	        taoButton();
	
	        setVisible(true);
	    }
	
	    private void taoHeader() {
	
	        headerPanel = new JPanel(new BorderLayout());
	        headerPanel.setBackground(Color.WHITE);
	        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));
	        headerPanel.setPreferredSize(new Dimension(1000, 75));
	
	        titleLb = new JLabel("QUẢN LÝ KỸ THUẬT VIÊN");
	        titleLb.setFont(new Font("Segoe UI", Font.BOLD, 26));
	        titleLb.setForeground(textColor);
	
	        headerPanel.add(titleLb, BorderLayout.WEST);
	
	        add(headerPanel, BorderLayout.NORTH);
	    }
	
	    private void taoContent() {
	
	        contentPanel = new JPanel(new BorderLayout(0, 15));
	        contentPanel.setBackground(backgroundColor);
	        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));
	
	        taoTimKiem();
	
	        add(contentPanel, BorderLayout.CENTER);
	    }
	
	    private void taoTimKiem() {
	
	        searchPanel = new JPanel(new BorderLayout(10, 0));
	        searchPanel.setBackground(backgroundColor);
	        searchPanel.setPreferredSize(new Dimension(950, 45));
	
	        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	        leftPanel.setBackground(backgroundColor);
	
	        searchLb = new JLabel("Tìm kiếm:");
	        searchLb.setFont(new Font("Segoe UI", Font.BOLD, 14));
	        searchLb.setForeground(secondaryColor);
	        searchLb.setPreferredSize(new Dimension(75, 40));
	
	        searchTxt = new JTextField();
	        searchTxt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	        searchTxt.setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(borderColor, 1),
	                BorderFactory.createEmptyBorder(0, 10, 0, 10)
	        ));
	        searchTxt.setPreferredSize(new Dimension(280, 40));
	
	        timBtn = taoButton("Tìm kiếm", primaryColor);
	
	        leftPanel.add(searchLb);
	        leftPanel.add(searchTxt);
	        leftPanel.add(timBtn);
	
	        searchPanel.add(leftPanel, BorderLayout.WEST);
	
	        contentPanel.add(searchPanel, BorderLayout.NORTH);
	    }
	
	    private void taoBang() {
	
	        String[] columns = {
	            "Mã KTV",
	            "Họ và tên",
	            "Số điện thoại",
	            "Tài khoản",
	            "Vai trò",
	            "Trạng thái"
	        };
	
	        Object[][] data = {
	            {"KTV001", "Nguyễn Văn An", "0901234567", "nguyenvanan", "Kỹ thuật viên", "Hoạt động"},
	            {"KTV002", "Trần Văn Bình", "0912345678", "tranvanbinh", "Kỹ thuật viên", "Hoạt động"},
	            {"KTV003", "Lê Minh Hoàng", "0923456789", "leminhhoang", "Kỹ thuật viên", "Tạm khóa"},
	            {"KTV004", "Phạm Văn Nam", "0934567890", "phamvannam", "Kỹ thuật viên", "Hoạt động"}
	        };
	
	        tableModel = new DefaultTableModel(data, columns) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	
	        table = new JTable(tableModel);
	        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	        table.setRowHeight(42);
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        table.setSelectionBackground(new Color(219, 234, 254));
	        table.setSelectionForeground(textColor);
	        table.setGridColor(new Color(226, 232, 240));
	        table.setShowVerticalLines(false);
	
	        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
	        table.getTableHeader().setForeground(Color.WHITE);
	        table.getTableHeader().setBackground(new Color(30, 41, 59));
	        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
	
	        table.getColumnModel().getColumn(0).setPreferredWidth(80);
	        table.getColumnModel().getColumn(1).setPreferredWidth(180);
	        table.getColumnModel().getColumn(2).setPreferredWidth(130);
	        table.getColumnModel().getColumn(3).setPreferredWidth(160);
	        table.getColumnModel().getColumn(4).setPreferredWidth(150);
	        table.getColumnModel().getColumn(5).setPreferredWidth(120);
	
	        scrollPane = new JScrollPane(table);
	        scrollPane.setBorder(BorderFactory.createLineBorder(borderColor));
	        scrollPane.getViewport().setBackground(Color.WHITE);
	
	        contentPanel.add(scrollPane, BorderLayout.CENTER);
	    }
	
	    private void taoButton() {
	
	        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
	        buttonPanel.setBackground(backgroundColor);
	        buttonPanel.setPreferredSize(new Dimension(950, 50));
	
	        themBtn = taoButton("Thêm", successColor);
	        suaBtn = taoButton("Sửa", primaryColor);
	        xoaBtn = taoButton("Xóa", dangerColor);
	        phanQuyenBtn = taoButton("Phân quyền", new Color(124, 58, 237));
	
	        buttonPanel.add(themBtn);
	        buttonPanel.add(suaBtn);
	        buttonPanel.add(xoaBtn);
	        buttonPanel.add(phanQuyenBtn);
	
	        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
	
	        themBtn.addActionListener(e -> moFormThem());
	
	        suaBtn.addActionListener(e -> moFormSua());
	
	        xoaBtn.addActionListener(e -> xoaKyThuatVien());
	
	        phanQuyenBtn.addActionListener(e -> moFormPhanQuyen());
	
	        timBtn.addActionListener(e -> timKiem());
	    }
	
	    private JButton taoButton(String text, Color color) {
	
	        JButton button = new JButton(text);
	
	        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
	        button.setForeground(Color.WHITE);
	        button.setBackground(color);
	        button.setFocusPainted(false);
	        button.setBorderPainted(false);
	        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        button.setPreferredSize(new Dimension(120, 40));
	
	        return button;
	    }
	
	    private void moFormThem() {
	
	        JDialog dialog = taoDialog("Thêm kỹ thuật viên");
	
	        JPanel panel = taoFormKyThuatVien();
	
	        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        buttonPanel.setBackground(Color.WHITE);
	
	        JButton luuBtn = taoButton("Lưu", successColor);
	        JButton huyBtn = taoButton("Hủy", secondaryColor);
	
	        buttonPanel.add(huyBtn);
	        buttonPanel.add(luuBtn);
	
	        huyBtn.addActionListener(e -> dialog.dispose());
	
	        luuBtn.addActionListener(e -> {
	            JOptionPane.showMessageDialog(
	                    dialog,
	                    "Thêm kỹ thuật viên thành công!",
	                    "Thông báo",
	                    JOptionPane.INFORMATION_MESSAGE
	            );
	            dialog.dispose();
	        });
	
	        dialog.add(panel, BorderLayout.CENTER);
	        dialog.add(buttonPanel, BorderLayout.SOUTH);
	
	        dialog.setVisible(true);
	    }
	
	    private void moFormSua() {
	
	        int row = table.getSelectedRow();
	
	        if (row == -1) {
	            JOptionPane.showMessageDialog(
	                    this,
	                    "Vui lòng chọn kỹ thuật viên cần sửa!",
	                    "Thông báo",
	                    JOptionPane.WARNING_MESSAGE
	            );
	            return;
	        }
	
	        JDialog dialog = taoDialog("Cập nhật kỹ thuật viên");
	
	        JPanel panel = taoFormKyThuatVien();
	
	        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        buttonPanel.setBackground(Color.WHITE);
	
	        JButton luuBtn = taoButton("Lưu", primaryColor);
	        JButton huyBtn = taoButton("Hủy", secondaryColor);
	
	        buttonPanel.add(huyBtn);
	        buttonPanel.add(luuBtn);
	
	        huyBtn.addActionListener(e -> dialog.dispose());
	
	        luuBtn.addActionListener(e -> {
	            JOptionPane.showMessageDialog(
	                    dialog,
	                    "Cập nhật kỹ thuật viên thành công!",
	                    "Thông báo",
	                    JOptionPane.INFORMATION_MESSAGE
	            );
	            dialog.dispose();
	        });
	
	        dialog.add(panel, BorderLayout.CENTER);
	        dialog.add(buttonPanel, BorderLayout.SOUTH);
	
	        dialog.setVisible(true);
	    }
	
	    private JPanel taoFormKyThuatVien() {
	
	        JPanel panel = new JPanel(new GridLayout(5, 2, 15, 15));
	        panel.setBackground(Color.WHITE);
	        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 20, 25));
	
	        JTextField maTxt = taoTextField();
	        JTextField hoTenTxt = taoTextField();
	        JTextField sdtTxt = taoTextField();
	        JTextField taiKhoanTxt = taoTextField();
	
	        JComboBox<String> trangThaiCb = new JComboBox<>(
	                new String[]{"Hoạt động", "Tạm khóa"}
	        );
	
	        panel.add(taoLabel("Mã KTV"));
	        panel.add(maTxt);
	
	        panel.add(taoLabel("Họ và tên"));
	        panel.add(hoTenTxt);
	
	        panel.add(taoLabel("Số điện thoại"));
	        panel.add(sdtTxt);
	
	        panel.add(taoLabel("Tài khoản"));
	        panel.add(taiKhoanTxt);
	
	        panel.add(taoLabel("Trạng thái"));
	        panel.add(trangThaiCb);
	
	        return panel;
	    }
	
	    private void moFormPhanQuyen() {
	
	        int row = table.getSelectedRow();
	
	        if (row == -1) {
	            JOptionPane.showMessageDialog(
	                    this,
	                    "Vui lòng chọn kỹ thuật viên cần phân quyền!",
	                    "Thông báo",
	                    JOptionPane.WARNING_MESSAGE
	            );
	            return;
	        }
	
	        String maKtv = table.getValueAt(row, 0).toString();
	        String hoTen = table.getValueAt(row, 1).toString();
	
	        JDialog dialog = taoDialog("Phân quyền kỹ thuật viên");
	
	        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
	        mainPanel.setBackground(Color.WHITE);
	        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
	
	        JLabel infoLb = new JLabel(
	                "Kỹ thuật viên: " + maKtv + " - " + hoTen
	        );
	        infoLb.setFont(new Font("Segoe UI", Font.BOLD, 16));
	        infoLb.setForeground(textColor);
	
	        JPanel permissionPanel = new JPanel(new GridLayout(5, 1, 0, 10));
	        permissionPanel.setBackground(Color.WHITE);
	
	        javax.swing.JCheckBox kiemTraCb =
	                new javax.swing.JCheckBox("Kiểm tra và chẩn đoán xe");
	
	        javax.swing.JCheckBox lapPhieuCb =
	                new javax.swing.JCheckBox("Lập phiếu sửa chữa");
	
	        javax.swing.JCheckBox tienDoCb =
	                new javax.swing.JCheckBox("Cập nhật tiến độ sửa chữa");
	
	        javax.swing.JCheckBox chatLuongCb =
	                new javax.swing.JCheckBox("Kiểm tra chất lượng");
	
	        javax.swing.JCheckBox xemCb =
	                new javax.swing.JCheckBox("Xem thông tin sửa chữa");
	
	        javax.swing.JCheckBox[] checkBoxes = {
	            kiemTraCb,
	            lapPhieuCb,
	            tienDoCb,
	            chatLuongCb,
	            xemCb
	        };
	
	        for (javax.swing.JCheckBox checkBox : checkBoxes) {
	            checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	            checkBox.setBackground(Color.WHITE);
	            checkBox.setForeground(secondaryColor);
	            permissionPanel.add(checkBox);
	        }
	
	        kiemTraCb.setSelected(true);
	        lapPhieuCb.setSelected(true);
	        tienDoCb.setSelected(true);
	
	        mainPanel.add(infoLb, BorderLayout.NORTH);
	        mainPanel.add(permissionPanel, BorderLayout.CENTER);
	
	        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        buttonPanel.setBackground(Color.WHITE);
	
	        JButton luuBtn = taoButton("Lưu phân quyền", primaryColor);
	        JButton huyBtn = taoButton("Hủy", secondaryColor);
	
	        buttonPanel.add(huyBtn);
	        buttonPanel.add(luuBtn);
	
	        huyBtn.addActionListener(e -> dialog.dispose());
	
	        luuBtn.addActionListener(e -> {
	            JOptionPane.showMessageDialog(
	                    dialog,
	                    "Phân quyền đã được cập nhật!",
	                    "Thông báo",
	                    JOptionPane.INFORMATION_MESSAGE
	            );
	            dialog.dispose();
	        });
	
	        dialog.add(mainPanel, BorderLayout.CENTER);
	        dialog.add(buttonPanel, BorderLayout.SOUTH);
	
	        dialog.setSize(500, 400);
	        dialog.setLocationRelativeTo(this);
	        dialog.setVisible(true);
	    }
	
	    private void xoaKyThuatVien() {
	
	        int row = table.getSelectedRow();
	
	        if (row == -1) {
	            JOptionPane.showMessageDialog(
	                    this,
	                    "Vui lòng chọn kỹ thuật viên cần xóa!",
	                    "Thông báo",
	                    JOptionPane.WARNING_MESSAGE
	            );
	            return;
	        }
	
	        String hoTen = table.getValueAt(row, 1).toString();
	
	        int result = JOptionPane.showConfirmDialog(
	                this,
	                "Bạn có chắc muốn xóa kỹ thuật viên \"" + hoTen + "\"?",
	                "Xác nhận xóa",
	                JOptionPane.YES_NO_OPTION,
	                JOptionPane.WARNING_MESSAGE
	        );
	
	        if (result == JOptionPane.YES_OPTION) {
	            tableModel.removeRow(row);
	
	            JOptionPane.showMessageDialog(
	                    this,
	                    "Xóa kỹ thuật viên thành công!",
	                    "Thông báo",
	                    JOptionPane.INFORMATION_MESSAGE
	            );
	        }
	    }
	
	    private void timKiem() {
	
	        String keyword = searchTxt.getText().trim().toLowerCase();
	
	        if (keyword.isEmpty()) {
	            return;
	        }
	
	        for (int i = 0; i < tableModel.getRowCount(); i++) {
	
	            String ma = tableModel.getValueAt(i, 0).toString().toLowerCase();
	            String hoTen = tableModel.getValueAt(i, 1).toString().toLowerCase();
	            String taiKhoan = tableModel.getValueAt(i, 3).toString().toLowerCase();
	
	            if (ma.contains(keyword)
	                    || hoTen.contains(keyword)
	                    || taiKhoan.contains(keyword)) {
	
	                table.setRowSelectionInterval(i, i);
	                table.scrollRectToVisible(table.getCellRect(i, 0, true));
	                return;
	            }
	        }
	
	        JOptionPane.showMessageDialog(
	                this,
	                "Không tìm thấy kỹ thuật viên!",
	                "Thông báo",
	                JOptionPane.INFORMATION_MESSAGE
	        );
	    }
	
	    private JDialog taoDialog(String title) {
	
	        JDialog dialog = new JDialog(this, title, true);
	
	        dialog.setLayout(new BorderLayout());
	        dialog.setSize(550, 400);
	        dialog.setLocationRelativeTo(this);
	
	        return dialog;
	    }
	
	    private JLabel taoLabel(String text) {
	
	        JLabel label = new JLabel(text);
	        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
	        label.setForeground(secondaryColor);
	
	        return label;
	    }
	
	    private JTextField taoTextField() {
	
	        JTextField textField = new JTextField();
	
	        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	        textField.setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(borderColor, 1),
	                BorderFactory.createEmptyBorder(0, 8, 0, 8)
	        ));
	
	        return textField;
	    }
	
	    public static void main(String[] args) {
	        new QuanLyKyThuatVien();
	    }
	}
