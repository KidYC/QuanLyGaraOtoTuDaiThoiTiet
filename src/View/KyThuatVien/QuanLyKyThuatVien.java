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

	private JPanel pnlHeaderPanel;
	private JPanel pnlContentPanel;
	private JPanel pnlSearchPanel;
	private JPanel pnlButtonPanel;

	private JLabel lblTitle;
	private JLabel lblSearch;

	private JTextField txtSearch;
	private JButton btnTim;
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnPhanQuyen;

	private JTable tblTable;
	private JScrollPane scrScrollPane;
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		pnlHeaderPanel = new JPanel(new BorderLayout());
		pnlHeaderPanel.setBackground(Color.WHITE);
		pnlHeaderPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));
		pnlHeaderPanel.setPreferredSize(new Dimension(1000, 75));

		lblTitle = new JLabel("QUẢN LÝ KỸ THUẬT VIÊN");
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
		lblTitle.setForeground(textColor);

		pnlHeaderPanel.add(lblTitle, BorderLayout.WEST);

		add(pnlHeaderPanel, BorderLayout.NORTH);
	}

	private void taoContent() {

		pnlContentPanel = new JPanel(new BorderLayout(0, 15));
		pnlContentPanel.setBackground(backgroundColor);
		pnlContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));

		taoTimKiem();

		add(pnlContentPanel, BorderLayout.CENTER);
	}

	private void taoTimKiem() {

		pnlSearchPanel = new JPanel(new BorderLayout(10, 0));
		pnlSearchPanel.setBackground(backgroundColor);
		pnlSearchPanel.setPreferredSize(new Dimension(950, 45));

		JPanel pnlLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		pnlLeftPanel.setBackground(backgroundColor);

		lblSearch = new JLabel("Tìm kiếm:");
		lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblSearch.setForeground(secondaryColor);
		lblSearch.setPreferredSize(new Dimension(75, 40));

		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtSearch.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(borderColor, 1),
				BorderFactory.createEmptyBorder(0, 10, 0, 10)
		));
		txtSearch.setPreferredSize(new Dimension(280, 40));

		btnTim = taoButton("Tìm kiếm", primaryColor);

		pnlLeftPanel.add(lblSearch);
		pnlLeftPanel.add(txtSearch);
		pnlLeftPanel.add(btnTim);

		pnlSearchPanel.add(pnlLeftPanel, BorderLayout.WEST);

		pnlContentPanel.add(pnlSearchPanel, BorderLayout.NORTH);
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

		tblTable = new JTable(tableModel);
		tblTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		tblTable.setRowHeight(42);
		tblTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblTable.setSelectionBackground(new Color(219, 234, 254));
		tblTable.setSelectionForeground(textColor);
		tblTable.setGridColor(new Color(226, 232, 240));
		tblTable.setShowVerticalLines(false);

		tblTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
		tblTable.getTableHeader().setForeground(Color.WHITE);
		tblTable.getTableHeader().setBackground(new Color(30, 41, 59));
		tblTable.getTableHeader().setPreferredSize(new Dimension(0, 45));

		tblTable.getColumnModel().getColumn(0).setPreferredWidth(80);
		tblTable.getColumnModel().getColumn(1).setPreferredWidth(180);
		tblTable.getColumnModel().getColumn(2).setPreferredWidth(130);
		tblTable.getColumnModel().getColumn(3).setPreferredWidth(160);
		tblTable.getColumnModel().getColumn(4).setPreferredWidth(150);
		tblTable.getColumnModel().getColumn(5).setPreferredWidth(120);

		scrScrollPane = new JScrollPane(tblTable);
		scrScrollPane.setBorder(BorderFactory.createLineBorder(borderColor));
		scrScrollPane.getViewport().setBackground(Color.WHITE);

		pnlContentPanel.add(scrScrollPane, BorderLayout.CENTER);
	}

	private void taoButton() {

		pnlButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		pnlButtonPanel.setBackground(backgroundColor);
		pnlButtonPanel.setPreferredSize(new Dimension(950, 50));

		btnThem = taoButton("Thêm", successColor);
		btnSua = taoButton("Sửa", primaryColor);
		btnXoa = taoButton("Xóa", dangerColor);
		btnPhanQuyen = taoButton("Phân quyền", new Color(124, 58, 237));

		pnlButtonPanel.add(btnThem);
		pnlButtonPanel.add(btnSua);
		pnlButtonPanel.add(btnXoa);
		pnlButtonPanel.add(btnPhanQuyen);

		pnlContentPanel.add(pnlButtonPanel, BorderLayout.SOUTH);

		btnThem.addActionListener(e -> moFormThem());

		btnSua.addActionListener(e -> moFormSua());

		btnXoa.addActionListener(e -> xoaKyThuatVien());

		btnPhanQuyen.addActionListener(e -> moFormPhanQuyen());

		btnTim.addActionListener(e -> timKiem());
	}

	private JButton taoButton(String text, Color color) {

		JButton btnButton = new JButton(text);

		btnButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnButton.setForeground(Color.WHITE);
		btnButton.setBackground(color);
		btnButton.setFocusPainted(false);
		btnButton.setBorderPainted(false);
		btnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnButton.setPreferredSize(new Dimension(120, 40));

		return btnButton;
	}

	private void moFormThem() {

		JDialog winDialog = taoDialog("Thêm kỹ thuật viên");

		JPanel pnlPanel = taoFormKyThuatVien();

		JPanel pnlButtonPanelLocal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlButtonPanelLocal.setBackground(Color.WHITE);

		JButton btnLuu = taoButton("Lưu", successColor);
		JButton btnHuy = taoButton("Hủy", secondaryColor);

		pnlButtonPanelLocal.add(btnHuy);
		pnlButtonPanelLocal.add(btnLuu);

		btnHuy.addActionListener(e -> winDialog.dispose());

		btnLuu.addActionListener(e -> {
			JOptionPane.showMessageDialog(
					winDialog,
					"Thêm kỹ thuật viên thành công!",
					"Thông báo",
					JOptionPane.INFORMATION_MESSAGE
			);
			winDialog.dispose();
		});

		winDialog.add(pnlPanel, BorderLayout.CENTER);
		winDialog.add(pnlButtonPanelLocal, BorderLayout.SOUTH);

		winDialog.setVisible(true);
	}

	private void moFormSua() {

		int row = tblTable.getSelectedRow();

		if (row == -1) {
			JOptionPane.showMessageDialog(
					this,
					"Vui lòng chọn kỹ thuật viên cần sửa!",
					"Thông báo",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}

		JDialog winDialog = taoDialog("Cập nhật kỹ thuật viên");

		JPanel pnlPanel = taoFormKyThuatVien();

		JPanel pnlButtonPanelLocal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlButtonPanelLocal.setBackground(Color.WHITE);

		JButton btnLuu = taoButton("Lưu", primaryColor);
		JButton btnHuy = taoButton("Hủy", secondaryColor);

		pnlButtonPanelLocal.add(btnHuy);
		pnlButtonPanelLocal.add(btnLuu);

		btnHuy.addActionListener(e -> winDialog.dispose());

		btnLuu.addActionListener(e -> {
			JOptionPane.showMessageDialog(
					winDialog,
					"Cập nhật kỹ thuật viên thành công!",
					"Thông báo",
					JOptionPane.INFORMATION_MESSAGE
			);
			winDialog.dispose();
		});

		winDialog.add(pnlPanel, BorderLayout.CENTER);
		winDialog.add(pnlButtonPanelLocal, BorderLayout.SOUTH);

		winDialog.setVisible(true);
	}

	private JPanel taoFormKyThuatVien() {

		JPanel pnlPanel = new JPanel(new GridLayout(5, 2, 15, 15));
		pnlPanel.setBackground(Color.WHITE);
		pnlPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 20, 25));

		JTextField txtMa = taoTextField();
		JTextField txtHoTen = taoTextField();
		JTextField txtSdt = taoTextField();
		JTextField txtTaiKhoan = taoTextField();

		JComboBox<String> cmbTrangThai = new JComboBox<>(
				new String[]{"Hoạt động", "Tạm khóa"}
		);

		pnlPanel.add(taoLabel("Mã KTV"));
		pnlPanel.add(txtMa);

		pnlPanel.add(taoLabel("Họ và tên"));
		pnlPanel.add(txtHoTen);

		pnlPanel.add(taoLabel("Số điện thoại"));
		pnlPanel.add(txtSdt);

		pnlPanel.add(taoLabel("Tài khoản"));
		pnlPanel.add(txtTaiKhoan);

		pnlPanel.add(taoLabel("Trạng thái"));
		pnlPanel.add(cmbTrangThai);

		return pnlPanel;
	}

	private void moFormPhanQuyen() {

		int row = tblTable.getSelectedRow();

		if (row == -1) {
			JOptionPane.showMessageDialog(
					this,
					"Vui lòng chọn kỹ thuật viên cần phân quyền!",
					"Thông báo",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}

		String maKtv = tblTable.getValueAt(row, 0).toString();
		String hoTen = tblTable.getValueAt(row, 1).toString();

		JDialog winDialog = taoDialog("Phân quyền kỹ thuật viên");

		JPanel pnlMainPanel = new JPanel(new BorderLayout(0, 15));
		pnlMainPanel.setBackground(Color.WHITE);
		pnlMainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

		JLabel lblInfo = new JLabel(
				"Kỹ thuật viên: " + maKtv + " - " + hoTen
		);
		lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblInfo.setForeground(textColor);

		JPanel pnlPermissionPanel = new JPanel(new GridLayout(5, 1, 0, 10));
		pnlPermissionPanel.setBackground(Color.WHITE);

		javax.swing.JCheckBox chkKiemTra =
				new javax.swing.JCheckBox("Kiểm tra và chẩn đoán xe");

		javax.swing.JCheckBox chkLapPhieu =
				new javax.swing.JCheckBox("Lập phiếu sửa chữa");

		javax.swing.JCheckBox chkTienDo =
				new javax.swing.JCheckBox("Cập nhật tiến độ sửa chữa");

		javax.swing.JCheckBox chkChatLuong =
				new javax.swing.JCheckBox("Kiểm tra chất lượng");

		javax.swing.JCheckBox chkXem =
				new javax.swing.JCheckBox("Xem thông tin sửa chữa");

		javax.swing.JCheckBox[] chkCheckBoxes = {
				chkKiemTra,
				chkLapPhieu,
				chkTienDo,
				chkChatLuong,
				chkXem
		};

		for (javax.swing.JCheckBox chkCheckBox : chkCheckBoxes) {
			chkCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			chkCheckBox.setBackground(Color.WHITE);
			chkCheckBox.setForeground(secondaryColor);
			pnlPermissionPanel.add(chkCheckBox);
		}

		chkKiemTra.setSelected(true);
		chkLapPhieu.setSelected(true);
		chkTienDo.setSelected(true);

		pnlMainPanel.add(lblInfo, BorderLayout.NORTH);
		pnlMainPanel.add(pnlPermissionPanel, BorderLayout.CENTER);

		JPanel pnlButtonPanelLocal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlButtonPanelLocal.setBackground(Color.WHITE);

		JButton btnLuu = taoButton("Lưu phân quyền", primaryColor);
		JButton btnHuy = taoButton("Hủy", secondaryColor);

		pnlButtonPanelLocal.add(btnHuy);
		pnlButtonPanelLocal.add(btnLuu);

		btnHuy.addActionListener(e -> winDialog.dispose());

		btnLuu.addActionListener(e -> {
			JOptionPane.showMessageDialog(
					winDialog,
					"Phân quyền đã được cập nhật!",
					"Thông báo",
					JOptionPane.INFORMATION_MESSAGE
			);
			winDialog.dispose();
		});

		winDialog.add(pnlMainPanel, BorderLayout.CENTER);
		winDialog.add(pnlButtonPanelLocal, BorderLayout.SOUTH);

		winDialog.setSize(500, 400);
		winDialog.setLocationRelativeTo(this);
		winDialog.setVisible(true);
	}

	private void xoaKyThuatVien() {

		int row = tblTable.getSelectedRow();

		if (row == -1) {
			JOptionPane.showMessageDialog(
					this,
					"Vui lòng chọn kỹ thuật viên cần xóa!",
					"Thông báo",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}

		String hoTen = tblTable.getValueAt(row, 1).toString();

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

		String keyword = txtSearch.getText().trim().toLowerCase();

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

				tblTable.setRowSelectionInterval(i, i);
				tblTable.scrollRectToVisible(tblTable.getCellRect(i, 0, true));
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

		JDialog winDialog = new JDialog(this, title, true);

		winDialog.setLayout(new BorderLayout());
		winDialog.setSize(550, 400);
		winDialog.setLocationRelativeTo(this);

		return winDialog;
	}

	private JLabel taoLabel(String text) {

		JLabel lblLabel = new JLabel(text);
		lblLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblLabel.setForeground(secondaryColor);

		return lblLabel;
	}

	private JTextField taoTextField() {

		JTextField txtField = new JTextField();

		txtField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(borderColor, 1),
				BorderFactory.createEmptyBorder(0, 8, 0, 8)
		));

		return txtField;
	}

	public static void main(String[] args) {
		new QuanLyKyThuatVien();
	}
}