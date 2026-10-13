package View.LeTan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManHinhDanhSachXeDaHoanThanhSuaChua extends JFrame implements ActionListener {

    private JLabel lblTitle;
    private JTable tblDanhSachXe;
    private DefaultTableModel modelXe;

    private JButton btnQuayLai;
    private JButton btnChiTietBanGiao;

    public ManHinhDanhSachXeDaHoanThanhSuaChua() {
        setTitle("DANH SÁCH XE ĐÃ HOÀN THÀNH SỬA CHỮA");
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
        //pNorth
        lblTitle = new JLabel("DANH SÁCH XE ĐÃ HOÀN THÀNH SỬA CHỮA");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        //pCenter
        JPanel pCenter = new JPanel(new BorderLayout());
        pCenter.setBackground(new Color(245, 245, 245));
        pCenter.setBorder(new EmptyBorder(0, 20, 0, 20));

        String[] cols = {"Mã Phiếu", "Tên Khách Hàng", "Biển Số", "Loại Xe", "Ngày Hoàn Thành", "Trạng Thái"};
        modelXe = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDanhSachXe = new JTable(modelXe);
        tblDanhSachXe.setRowHeight(28);
        tblDanhSachXe.setFont(new Font("Arial", Font.PLAIN, 13));
        tblDanhSachXe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Tạo Bảng
        JTableHeader header = tblDanhSachXe.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tblDanhSachXe);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        pCenter.add(scrollPane, BorderLayout.CENTER);
        add(pCenter, BorderLayout.CENTER);

        //pSouth
        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pSouth.setBackground(new Color(245, 245, 245));

        btnQuayLai = new JButton("Quay Lại");
        btnQuayLai.setPreferredSize(new Dimension(110, 35));
        btnQuayLai.setBackground(new Color(149, 165, 166));
        btnQuayLai.setForeground(Color.WHITE);
        btnQuayLai.setFont(new Font("Arial", Font.BOLD, 12));
        btnQuayLai.setFocusPainted(false);

        btnChiTietBanGiao = new JButton("Xem Chi Tiết Bàn Giao");
        btnChiTietBanGiao.setPreferredSize(new Dimension(180, 35));
        btnChiTietBanGiao.setBackground(new Color(46, 204, 113));
        btnChiTietBanGiao.setForeground(Color.WHITE);
        btnChiTietBanGiao.setFont(new Font("Arial", Font.BOLD, 12));
        btnChiTietBanGiao.setFocusPainted(false);

        pSouth.add(btnQuayLai);
        pSouth.add(btnChiTietBanGiao);
        add(pSouth, BorderLayout.SOUTH);

        initListener();
    }

    private void initListener() {
        btnQuayLai.addActionListener(this);
        btnChiTietBanGiao.addActionListener(this);
    }

    private void loadData() {
        modelXe.addRow(new Object[]{"KH005", "Phạm Văn D", "51H-234.56", "Ford Ranger", "06/10/2026 14:30", "Chờ bàn giao"});
        modelXe.addRow(new Object[]{"KH006", "Nguyễn Thị E", "59K-789.01", "Kia Seltos", "06/10/2026 15:00", "Chờ bàn giao"});
        modelXe.addRow(new Object[]{"KH008", "Trần Văn F", "60C-444.55", "Hyundai Accent", "06/10/2026 16:15", "Chờ bàn giao"});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnQuayLai) {
            xuLyQuayLai();
        } else if (source == btnChiTietBanGiao) {
            xuLyBanGiao();
        }
    }

    private void xuLyQuayLai() {
        TrangChuLeTan Home = new TrangChuLeTan();
        Home.setVisible(true);
        this.dispose();
    }

    private void xuLyBanGiao() {
        int selectedRow = tblDanhSachXe.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn một xe trong danh sách để tiến hành bàn giao!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maPhieu = modelXe.getValueAt(selectedRow, 0).toString();
        String bienSo = modelXe.getValueAt(selectedRow, 2).toString();

        int opt = JOptionPane.showConfirmDialog(this,
                "Chuyển sang màn hình Chi tiết Bàn giao cho xe biển số: " + bienSo + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opt == JOptionPane.YES_OPTION) {
             ManHinhChiTietBanGiao formBanGiao = new ManHinhChiTietBanGiao(maPhieu, bienSo);
             formBanGiao.setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        ManHinhDanhSachXeDaHoanThanhSuaChua form = new ManHinhDanhSachXeDaHoanThanhSuaChua();
        form.setVisible(true);
    }
}