package View.LeTan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DanhSachXeKhachHang extends JFrame implements ActionListener {
    private final String maKH;
    private final String hoTen;
    private final String soDienThoai;

    private JTable tableXe;
    private DefaultTableModel tableModelXe;
    private JButton btnTaoPhieuTiepNhan;
    private JButton btnThemXeMoi;
    private JButton btnQuayLai;

    public DanhSachXeKhachHang(String maKH, String hoTen, String soDienThoai) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;

        initUI();
    }

    private void initUI() {
        setTitle("Danh Sách Xe Của Khách Hàng - " + hoTen);
        setSize(850, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel pNorth = new JPanel(new BorderLayout(5, 10));
        pNorth.setBorder(new EmptyBorder(10, 15, 5, 15));

        JLabel lblTitle = new JLabel("DANH SÁCH XE CỦA KHÁCH HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(44, 62, 80));
        pNorth.add(lblTitle, BorderLayout.NORTH);

        JPanel pInfoKH = new JPanel(new GridLayout(1, 3, 20, 5));
        pInfoKH.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Thông Tin Khách Hàng",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13),
                new Color(52, 73, 94)
        ));

        JLabel lblMaKH = new JLabel("Mã KH: " + maKH);
        JLabel lblHoTen = new JLabel("Họ Tên: " + hoTen);
        JLabel lblSDT = new JLabel("Số ĐT: " + soDienThoai);

        lblMaKH.setFont(new Font("Arial", Font.PLAIN, 14));
        lblHoTen.setFont(new Font("Arial", Font.BOLD, 14));
        lblSDT.setFont(new Font("Arial", Font.PLAIN, 14));

        pInfoKH.add(lblMaKH);
        pInfoKH.add(lblHoTen);
        pInfoKH.add(lblSDT);

        pNorth.add(pInfoKH, BorderLayout.CENTER);
        add(pNorth, BorderLayout.NORTH);

        JPanel pCenter = new JPanel(new BorderLayout());
        pCenter.setBorder(new EmptyBorder(0, 15, 0, 15));

        String[] columnNames = {"STT", "Biển Số Xe", "Hãng Xe", "Dòng Xe (Model)", "Màu Sắc", "Lần Bảo Dưỡng Gần Nhất"};
        tableModelXe = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableXe = new JTable(tableModelXe);
        tableXe.setRowHeight(30);
        tableXe.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableXe.setFont(new Font("Arial", Font.PLAIN, 13));
        tableXe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadDataMau();

        JScrollPane scrollPane = new JScrollPane(tableXe);
        pCenter.add(scrollPane, BorderLayout.CENTER);
        add(pCenter, BorderLayout.CENTER);

        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));

        btnThemXeMoi = new JButton("Thêm Xe Cho Khách");
        btnTaoPhieuTiepNhan = new JButton("Tạo Phiếu Tiếp Nhận");
        btnQuayLai = new JButton("Quay Lại");


        btnThemXeMoi.setFont(new Font("Arial", Font.PLAIN, 13));
        btnQuayLai.setFont(new Font("Arial", Font.PLAIN, 13));

        pSouth.add(btnThemXeMoi);
        pSouth.add(btnTaoPhieuTiepNhan);
        pSouth.add(btnQuayLai);
        add(pSouth, BorderLayout.SOUTH);

        btnThemXeMoi.addActionListener(this);
        btnTaoPhieuTiepNhan.addActionListener(this);
        btnQuayLai.addActionListener(this);
    }

    private void loadDataMau() {
        tableModelXe.addRow(new Object[]{"1", "51H-123.45", "Toyota", "Vios 2020", "Trắng", "15/08/2026"});
        tableModelXe.addRow(new Object[]{"2", "59G1-999.88", "Honda", "City 2022", "Đen", "Chưa có"});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnTaoPhieuTiepNhan) {
            int selectedRow = tableXe.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn một xe trong danh sách để tạo phiếu tiếp nhận!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String bienSo = tableModelXe.getValueAt(selectedRow, 1).toString();
            String hangXe = tableModelXe.getValueAt(selectedRow, 2).toString();

            ManHinhLapPhieuTiepNhan formPhieu = new ManHinhLapPhieuTiepNhan(hoTen, soDienThoai, bienSo, hangXe);
            formPhieu.setVisible(true);
            this.dispose();

            JOptionPane.showMessageDialog(this,
                    "Chuyển sang Màn hình Lập Phiếu Tiếp Nhận cho xe:\n" +
                            "Biển số: " + bienSo + " (" + hangXe + " " + ")",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

        } else if (source == btnThemXeMoi) {
            JOptionPane.showMessageDialog(this, "Mở Form thêm xe mới cho khách hàng: " + hoTen);
        } else if (source == btnQuayLai) {
            ManHinhQuanLyKhachHang UI = new ManHinhQuanLyKhachHang();
            UI.setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
            new DanhSachXeKhachHang("KH001", "Nguyễn Văn A", "0901234567").setVisible(true);
    }
}