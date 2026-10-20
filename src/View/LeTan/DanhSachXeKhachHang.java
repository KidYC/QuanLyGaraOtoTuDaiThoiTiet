package View.LeTan;

import DAO.Xe_DAO;
import Entity.Xe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DanhSachXeKhachHang extends JFrame implements ActionListener {
    private final String maKH;
    private final String hoTen;
    private final String soDienThoai;

    private DefaultTableModel tableModelXe;
    private JButton btnTaoPhieuTiepNhan;
    private JButton btnThemXeMoi;
    private JButton btnQuayLai;
    private JTable table;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private DefaultTableModel tableModel;

    public DanhSachXeKhachHang(String maKH, String hoTen, String soDienThoai) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;

        initUI();
    }

    public DanhSachXeKhachHang(){
        this.maKH = "";
        this.hoTen = "";
        this.soDienThoai = "";

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


        try{
            if(maKH != ""){
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

                Xe_DAO dao = new Xe_DAO();
                List<Xe> list = dao.getXeByMaKH(maKH);
                tableModel = new DefaultTableModel(
                        new String[]{"Biển Số Xe", "Hãng Xe","Dòng Xe", "Năm Sản Xuất"}, 0);
                for(Xe xe: list){
                    tableModel.addRow(
                            new Object[]{xe.getBienSo(), xe.getHangXe(), xe.getDongXe(), xe.getNamSanXuat()}
                    );
                }
            }
            else{
                Xe_DAO dao = new Xe_DAO();
                List<Xe> list = dao.getAllXe();
                tableModel = new DefaultTableModel(
                        new String[]{"Biển Số Xe", "Hãng Xe","Dòng Xe", "Năm Sản Xuất", "Mã Khách Hàng"}, 0);
                for(Xe xe: list){
                    tableModel.addRow(
                            new Object[]{xe.getBienSo(), xe.getHangXe(), xe.getDongXe(), xe.getNamSanXuat(), xe.getMaKhachHang()}
                    );
                }
            }

        }catch (SQLException e){
            JOptionPane.showMessageDialog(this, "Lỗi Mã Khách Hàng");
        }

        pNorth.add(pInfoKH, BorderLayout.CENTER);
        add(pNorth, BorderLayout.NORTH);

        JPanel pCenter = new JPanel(new BorderLayout());
        pCenter.setBorder(new EmptyBorder(0, 15, 0, 15));

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.BOLD, 13));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);

        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
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


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnTaoPhieuTiepNhan) {
            int selectedRow = table.getSelectedRow();
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
//            new DanhSachXeKhachHang("KH01", "Nguyễn Văn A", "0901234567").setVisible(true);
        new DanhSachXeKhachHang().setVisible(true);
    }
}