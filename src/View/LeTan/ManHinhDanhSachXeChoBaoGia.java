package View.LeTan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManHinhDanhSachXeChoBaoGia extends JFrame implements ActionListener {

    private JLabel lblTitle;
    private JTable tblDanhSachXe;
    private DefaultTableModel modelXe;

    private JButton btnQuayLai;
    private JButton btnLapBaoGia;

    public ManHinhDanhSachXeChoBaoGia() {
        setTitle("DANH SÁCH XE CHỜ BÁO GIÁ");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();
        loadData();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        lblTitle = new JLabel("DANH SÁCH XE CHỜ BÁO GIÁ");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pCenter = new JPanel(new BorderLayout());
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        String[] cols = {"Mã Phiếu", "Tên Khách Hàng", "Biển Số", "Loại Xe", "Ngày Tiếp Nhận", "Trạng Thái"};
        modelXe = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDanhSachXe = new JTable(modelXe);
        tblDanhSachXe.setRowHeight(25);
        tblDanhSachXe.setFont(new Font("Arial", Font.PLAIN, 13));
        tblDanhSachXe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = tblDanhSachXe.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tblDanhSachXe);
        scrollPane.getViewport().setBackground(Color.WHITE);
        pCenter.add(scrollPane, BorderLayout.CENTER);
        add(pCenter, BorderLayout.CENTER);

        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnQuayLai = new JButton("Quay Lại");
        btnQuayLai.setPreferredSize(new Dimension(110, 35));
        btnQuayLai.setBackground(new Color(149, 165, 166));
        btnQuayLai.setForeground(Color.WHITE);
        btnQuayLai.setFocusPainted(false);
        btnQuayLai.setFont(new Font("Arial", Font.BOLD, 12));

        btnLapBaoGia = new JButton("Lập Báo Giá");
        btnLapBaoGia.setPreferredSize(new Dimension(130, 35));
        btnLapBaoGia.setBackground(new Color(46, 204, 113));
        btnLapBaoGia.setForeground(Color.WHITE);
        btnLapBaoGia.setFocusPainted(false);
        btnLapBaoGia.setFont(new Font("Arial", Font.BOLD, 12));

        pSouth.add(btnQuayLai);
        pSouth.add(btnLapBaoGia);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btnQuayLai.addActionListener(this);
        btnLapBaoGia.addActionListener(this);
    }

    private void loadData() {
        modelXe.addRow(new Object[]{"KH001", "Nguyễn Văn A", "51F-123.45", "Toyota Vios", "06/10/2026 08:30", "Chờ báo giá"});
        modelXe.addRow(new Object[]{"KH002", "Trần Thị B", "59G-987.65", "Honda CR-V", "06/10/2026 09:15", "Chờ báo giá"});
        modelXe.addRow(new Object[]{"KH003", "Lê Văn C", "60A-111.22", "Mazda 3", "06/10/2026 10:00", "Chờ báo giá"});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnQuayLai) {
            xuLyQuayLai();
        } else if (source == btnLapBaoGia) {
            xuLyLapBaoGia();
        }
    }

    private void xuLyQuayLai() {
        this.dispose();
        TrangChuLeTan Home = new TrangChuLeTan();
        Home.setVisible(true);
    }

    private void xuLyLapBaoGia() {
        int selectedRow = tblDanhSachXe.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn một xe trong danh sách để lập báo giá!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maPhieu = modelXe.getValueAt(selectedRow, 0).toString();
        String bienSo = modelXe.getValueAt(selectedRow, 2).toString();
        String tenKH = modelXe.getValueAt(selectedRow, 1).toString();
        int opt = JOptionPane.showConfirmDialog(this,
                "Chuyển sang màn hình Lập báo giá cho xe biển số: " + bienSo + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opt == JOptionPane.YES_OPTION) {
            ManHinhLapBaoGia formLapBaoGia = new ManHinhLapBaoGia(maPhieu, bienSo, tenKH);
            formLapBaoGia.setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        ManHinhDanhSachXeChoBaoGia form = new ManHinhDanhSachXeChoBaoGia();
        form.setVisible(true);
    }
}