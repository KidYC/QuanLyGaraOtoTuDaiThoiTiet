package View.HeThong;

import java.awt.Component;
import java.awt.Window;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import View.DangNhap;
import View.KyThuatVien.CapNhatTienDoSuaChua;
import View.KyThuatVien.ChiTietKiemTraChanDoan;
import View.KyThuatVien.KiemTraChatLuong;
import View.KyThuatVien.LapPhieuSuaChua;
import View.KyThuatVien.QuanLyKyThuatVien;
import View.KeToan.ManHinhChiTietHDVaThanhToan;
import View.KeToan.ManHinhDanhSachKHNo;
import View.KeToan.ManHinhQuanLyCa;
import View.LeTan.*;
import View.ThuKho.ManHinhQuanLyDanhMucPhuTung;

public class TrangChuHeThong extends JFrame {
    private static final Color MAU_NEN = new Color(244, 246, 248);
    private static final Color MAU_SIDEBAR = new Color(226, 232, 240);
    private static final Color MAU_SIDEBAR_CHON = new Color(186, 230, 253);
    private static final Color MAU_CHU_SIDEBAR = new Color(17, 24, 39);
    private static final Color MAU_TEXT = new Color(17, 24, 39);
    private static final Color MAU_PHU = new Color(107, 114, 128);
    private static final Color MAU_VIEN = new Color(226, 232, 240);
    private static final Color MAU_XANH = new Color(186, 230, 253);
    private static final Color MAU_XANH_LA = new Color(34, 197, 94);
    private static final Color MAU_CAM = new Color(245, 158, 11);
    private static final Color MAU_DO = new Color(220, 38, 38);
    private static final Color MAU_TIM = new Color(219, 234, 254);
    private static final Font FONT_TIEU_DE = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_NHAN = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_NHAN_DAM = new Font("Segoe UI", Font.BOLD, 14);

    public enum VaiTro {
        ADMIN("Chủ gara (Admin)"), LE_TAN("Lễ tân"), KY_THUAT("Kỹ thuật viên"), THU_KHO("Thủ kho"), KE_TOAN("Kế toán");
        private final String ten;
        VaiTro(String ten) { this.ten = ten; }
        public String getTen() { return ten; }
    }

    public enum NhomChucNang {
        CHUNG("TỔNG QUAN"), TIEP_NHAN("TIẾP NHẬN & KHÁCH HÀNG"), SUA_CHUA("SỬA CHỮA & BẢO HÀNH"), KHO("KHO PHỤ TÙNG"), KE_TOAN("KẾ TOÁN"), THONG_KE("THỐNG KÊ BÁO CÁO"), HE_THONG("HỆ THỐNG");
        private final String ten;
        NhomChucNang(String ten) { this.ten = ten; }
        public String getTen() { return ten; }
    }

    public enum ChucNang {
        TONG_QUAN("Trang chủ", NhomChucNang.CHUNG, "Tổng quan hoạt động của gara", VaiTro.values()),
        QUAN_LY_XE("Quản lý xe", NhomChucNang.TIEP_NHAN, "Hồ sơ xe và thông tin khách hàng", VaiTro.LE_TAN),
        TIEP_NHAN_XE("Tiếp nhận xe", NhomChucNang.TIEP_NHAN, "Lập phiếu tiếp nhận xe vào gara", VaiTro.LE_TAN),
        LAP_BAO_GIA("Lập báo giá", NhomChucNang.TIEP_NHAN, "Báo giá sửa chữa cho khách hàng", VaiTro.LE_TAN),
        XAC_NHAN_PHUONG_AN("Xác nhận phương án", NhomChucNang.TIEP_NHAN, "Ghi nhận quyết định của khách", VaiTro.LE_TAN),
        BAN_GIAO_XE("Bàn giao xe", NhomChucNang.TIEP_NHAN, "Bàn giao xe và chăm sóc khách hàng", VaiTro.LE_TAN),
        CHAN_DOAN("Chẩn đoán xe", NhomChucNang.SUA_CHUA, "Kiểm tra và chẩn đoán lỗi xe", VaiTro.KY_THUAT),
        PHIEU_SUA_CHUA("Phiếu sửa chữa", NhomChucNang.SUA_CHUA, "Lập phiếu, công việc và phụ tùng", VaiTro.KY_THUAT),
        THUC_HIEN_SUA_CHUA("Thực hiện sửa chữa", NhomChucNang.SUA_CHUA, "Cập nhật tiến độ thi công", VaiTro.KY_THUAT),
        KIEM_TRA_CHAT_LUONG("Kiểm tra chất lượng", NhomChucNang.SUA_CHUA, "Kiểm tra xe sau khi sửa chữa", VaiTro.KY_THUAT),
        BAO_HANH("Bảo hành", NhomChucNang.SUA_CHUA, "Tiếp nhận và xử lý bảo hành", VaiTro.LE_TAN),
        LICH_SU_SUA_CHUA("Lịch sử sửa chữa", NhomChucNang.SUA_CHUA, "Tra cứu lịch sử sửa chữa của xe", VaiTro.LE_TAN),
        DANH_MUC_PHU_TUNG("Danh mục phụ tùng", NhomChucNang.KHO, "Thông tin, mã vạch và đơn giá phụ tùng", VaiTro.THU_KHO),
        NHAP_KHO("Nhập kho", NhomChucNang.KHO, "Nhập phụ tùng bằng quét mã vạch", VaiTro.THU_KHO),
        XUAT_KHO("Xuất kho", NhomChucNang.KHO, "Xuất phụ tùng theo phiếu sửa chữa", VaiTro.THU_KHO),
        KIEM_KE("Kiểm kê kho", NhomChucNang.KHO, "Đối chiếu tồn kho thực tế với hệ thống", VaiTro.THU_KHO),
        CANH_BAO_TON("Cảnh báo tồn kho", NhomChucNang.KHO, "Ngưỡng tồn tối thiểu và tối đa", VaiTro.THU_KHO),
        HOA_DON("Hóa đơn", NhomChucNang.KE_TOAN, "Lập hóa đơn từ các phiếu sửa chữa", VaiTro.KE_TOAN),
        THANH_TOAN("Thanh toán", NhomChucNang.KE_TOAN, "Ghi nhận thu tiền nhiều đợt", VaiTro.KE_TOAN),
        CONG_NO("Công nợ", NhomChucNang.KE_TOAN, "Theo dõi công nợ khách thân thiết", VaiTro.KE_TOAN),
        CA_THU_NGAN("Ca thu ngân", NhomChucNang.KE_TOAN, "Mở ca, đóng ca và đối soát doanh thu", VaiTro.KE_TOAN),
        THONG_KE_DOANH_THU("Doanh thu", NhomChucNang.THONG_KE, "Thống kê doanh thu theo kỳ"),
        THONG_KE_SUA_CHUA("Sửa chữa", NhomChucNang.THONG_KE, "Thống kê xe và phiếu sửa chữa"),
        THONG_KE_KHO("Kho", NhomChucNang.THONG_KE, "Tồn kho và nhập xuất theo thời gian", VaiTro.THU_KHO),
        PHAN_QUYEN("Phân quyền", NhomChucNang.HE_THONG, "Cấp và thu hồi quyền người dùng"),
        QUAN_LY_NHAN_VIEN("Nhân viên", NhomChucNang.HE_THONG, "Quản lý hồ sơ nhân sự"),
        BANG_GIA("Bảng giá", NhomChucNang.HE_THONG, "Cấu hình bảng giá và chiết khấu");

        private final String ten;
        private final NhomChucNang nhom;
        private final String moTa;
        private final VaiTro[] cacVaiTro;

        ChucNang(String ten, NhomChucNang nhom, String moTa, VaiTro... cacVaiTro) {
            this.ten = ten;
            this.nhom = nhom;
            this.moTa = moTa;
            this.cacVaiTro = cacVaiTro;
        }

        public boolean choPhep(VaiTro vt) {
            if (vt == VaiTro.ADMIN) return true;
            for (VaiTro r : cacVaiTro) if (r == vt) return true;
            return false;
        }

        public String getTen() { return ten; }
        public NhomChucNang getNhom() { return nhom; }
        public String getMoTa() { return moTa; }
    }

    private final String maNV;
    private final String hoTen;
    private final VaiTro vaiTro;
    private CardLayout cardLayout;
    private NenTrang pnlNoiDung;
    private final Map<ChucNang, JButton> dsNutMenu = new LinkedHashMap<>();
    private JButton btnDangChon;

    public TrangChuHeThong() {
        this("NV001", "Phan Xuân Phụng", VaiTro.KY_THUAT);
    }

    public TrangChuHeThong(String maNV, String hoTen, VaiTro vaiTro) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.vaiTro = vaiTro;
        setTitle("Hệ Thống Quản Lý Gara Ô Tô - " + vaiTro.getTen());
        getContentPane().setBackground(MAU_NEN);
        setLayout(new BorderLayout());
        add(taoHeader(), BorderLayout.NORTH);
        add(taoSidebar(), BorderLayout.WEST);
        add(taoVungNoiDung(), BorderLayout.CENTER);
        chonChucNang(ChucNang.TONG_QUAN, dsNutMenu.get(ChucNang.TONG_QUAN));
        setSize(1280, 800);
        setMinimumSize(new Dimension(1100, 680));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel taoHeader() {
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, MAU_VIEN), BorderFactory.createEmptyBorder(8, 20, 8, 20)));
        pnlHeader.setPreferredSize(new Dimension(0, 70));
        JPanel pnlTrai = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        pnlTrai.setOpaque(false);
        pnlTrai.add(taoLogo("/resources/icons/logo.png", 60, 52, "GO"));
        JLabel lblTieuDe = new JLabel("HỆ THỐNG QUẢN LÝ GARA Ô TÔ");
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTieuDe.setForeground(MAU_TEXT);
        pnlTrai.add(lblTieuDe);
        pnlHeader.add(pnlTrai, BorderLayout.WEST);
        return pnlHeader;
    }

    private JPanel taoSidebar() {
        JPanel pnlSidebar = new JPanel(new BorderLayout());
        pnlSidebar.setBackground(MAU_SIDEBAR);
        pnlSidebar.setPreferredSize(new Dimension(260, 0));
        JPanel pnlDsMenu = new JPanel();
        pnlDsMenu.setLayout(new BoxLayout(pnlDsMenu, BoxLayout.Y_AXIS));
        pnlDsMenu.setBackground(MAU_SIDEBAR);
        pnlDsMenu.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        NhomChucNang nhomTruoc = null;
        for (ChucNang cn : ChucNang.values()) {
            if (!cn.choPhep(vaiTro)) continue;
            if (cn.getNhom() != nhomTruoc) {
                nhomTruoc = cn.getNhom();
                JLabel lblNhom = new JLabel(nhomTruoc.getTen());
                lblNhom.setFont(new Font("Segoe UI", Font.BOLD, 11));
                lblNhom.setForeground(new Color(107, 114, 128));
                lblNhom.setBorder(BorderFactory.createEmptyBorder(14, 10, 4, 0));
                lblNhom.setAlignmentX(LEFT_ALIGNMENT);
                pnlDsMenu.add(lblNhom);
            }
            JButton btnMenu = taoNutMenu(cn);
            pnlDsMenu.add(btnMenu);
            pnlDsMenu.add(Box.createVerticalStrut(4));
            dsNutMenu.put(cn, btnMenu);
        }
        JScrollPane scrMenu = new JScrollPane(pnlDsMenu);
        scrMenu.setBorder(null);
        scrMenu.getViewport().setBackground(MAU_SIDEBAR);
        scrMenu.getVerticalScrollBar().setUnitIncrement(12);
        pnlSidebar.add(scrMenu, BorderLayout.CENTER);
        pnlSidebar.add(taoThongTinUser(), BorderLayout.SOUTH);
        return pnlSidebar;
    }

    private JPanel taoThongTinUser() {
        JPanel pnlProfile = new JPanel();
        pnlProfile.setLayout(new BoxLayout(pnlProfile, BoxLayout.Y_AXIS));
        pnlProfile.setBackground(MAU_SIDEBAR);
        pnlProfile.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JSeparator sepProfile = new JSeparator();
        sepProfile.setForeground(new Color(203, 213, 225));
        sepProfile.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        JLabel lblTen = new JLabel(hoTen);
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTen.setForeground(MAU_TEXT);
        lblTen.setAlignmentX(LEFT_ALIGNMENT);
        JLabel lblChucVu = new JLabel(vaiTro.getTen());
        lblChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblChucVu.setForeground(MAU_PHU);
        lblChucVu.setAlignmentX(LEFT_ALIGNMENT);
        JPanel pnlLogout = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlLogout.setBackground(MAU_SIDEBAR);
        pnlLogout.setAlignmentX(LEFT_ALIGNMENT);
        pnlLogout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        JLabel lblLogout = new JLabel("Đăng xuất");
        ImageIcon icoLogout = loadIcon("/resources/icons/logout.png", 18, 18);
        if (icoLogout != null) lblLogout.setIcon(icoLogout);
        lblLogout.setIconTextGap(7);
        lblLogout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblLogout.setForeground(MAU_DO);
        lblLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlLogout.add(lblLogout);
        lblLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { dangXuat(); }
            @Override
            public void mouseEntered(MouseEvent e) { lblLogout.setForeground(new Color(248, 113, 113)); }
            @Override
            public void mouseExited(MouseEvent e) { lblLogout.setForeground(MAU_DO); }
        });
        pnlProfile.add(sepProfile);
        pnlProfile.add(Box.createVerticalStrut(12));
        pnlProfile.add(lblTen);
        pnlProfile.add(Box.createVerticalStrut(2));
        pnlProfile.add(lblChucVu);
        pnlProfile.add(Box.createVerticalStrut(8));
        pnlProfile.add(pnlLogout);
        return pnlProfile;
    }

    private JButton taoNutMenu(ChucNang cn) {
        JButton btnMenu = new JButton(cn.getTen());
        btnMenu.setFont(FONT_NHAN);
        btnMenu.setForeground(MAU_CHU_SIDEBAR);
        btnMenu.setBackground(MAU_SIDEBAR);
        btnMenu.setHorizontalAlignment(SwingConstants.LEFT);
        btnMenu.setFocusPainted(false);
        btnMenu.setBorderPainted(false);
        btnMenu.setOpaque(true);
        btnMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMenu.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 12));
        btnMenu.setAlignmentX(LEFT_ALIGNMENT);
        btnMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnMenu.addActionListener(e -> chonChucNang(cn, btnMenu));
        return btnMenu;
    }

    private JComponent taoVungNoiDung() {
        cardLayout = new CardLayout();
        pnlNoiDung = new NenTrang(cardLayout, loadImage("/resources/images/background.jpg"));
        pnlNoiDung.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        pnlNoiDung.add(new TongQuanPanel(hoTen, vaiTro), ChucNang.TONG_QUAN.name());
        for (ChucNang cn : ChucNang.values()) {
            if (cn != ChucNang.TONG_QUAN && cn.choPhep(vaiTro)) {
                pnlNoiDung.add(taoTrang(cn), cn.name());
            }
        }
        return pnlNoiDung;
    }

    private void chonChucNang(ChucNang cn, JButton btnMenu) {
        if (btnDangChon != null) {
            btnDangChon.setBackground(MAU_SIDEBAR);
            btnDangChon.setForeground(MAU_CHU_SIDEBAR);
            btnDangChon.setFont(FONT_NHAN);
        }
        if (btnMenu != null) {
            btnMenu.setBackground(MAU_SIDEBAR_CHON);
            btnMenu.setForeground(MAU_TEXT);
            btnMenu.setFont(FONT_NHAN_DAM);
            btnDangChon = btnMenu;
        }
        // Chức năng có màn hình riêng thì mở cửa sổ đó, còn lại hiện trang tạm trong khung chính
        if (!moManHinhNghiepVu(cn) && cardLayout != null) {
            cardLayout.show(pnlNoiDung, cn.name());
        }
    }

    /** Điểm liên kết duy nhất: mở màn hình nghiệp vụ của actor tương ứng. Trả về false nếu chưa có màn hình riêng. */
    static boolean moManHinhNghiepVu(ChucNang cn) {
        switch (cn) {
            // Lễ tân
            case QUAN_LY_XE:            hienThi(new DanhSachXeKhachHang("001","Minh Lo", "0123456789")); return true;
            case TIEP_NHAN_XE:          hienThi(new ManHinhLapPhieuTiepNhan()); return true;
            case LAP_BAO_GIA:           hienThi(new ManHinhLapBaoGia()); return true;
            case XAC_NHAN_PHUONG_AN:    hienThi(new ManHinhXacNhanPhuongAn()); return true;
            case BAN_GIAO_XE:           hienThi(new ManHinhChiTietBanGiao()); return true;
            case BAO_HANH:              hienThi(new FormQuanLyBaoHanh()); return true;
//            case LICH_SU_SUA_CHUA:      hienThi(new DanhSachXeDaHoanThanhSuaChua()); return true;

            // Kỹ thuật viên
            case CHAN_DOAN:             hienThi(new ChiTietKiemTraChanDoan()); return true;
            case PHIEU_SUA_CHUA:        hienThi(new LapPhieuSuaChua()); return true;
            case THUC_HIEN_SUA_CHUA:    hienThi(new CapNhatTienDoSuaChua()); return true;
            case KIEM_TRA_CHAT_LUONG:   hienThi(new KiemTraChatLuong()); return true;

            // Thủ kho
//            case DANH_MUC_PHU_TUNG:     hienThi(new ManHinhQuanLyDanhMucPhuTung()); return true;

            // Kế toán
            case HOA_DON:
            case THANH_TOAN:            hienThi(new ManHinhChiTietHDVaThanhToan()); return true;
            case CONG_NO:               hienThi(new ManHinhDanhSachKHNo()); return true;
            case CA_THU_NGAN:           hienThi(new ManHinhQuanLyCa()); return true;

            // Admin
            case QUAN_LY_NHAN_VIEN:     hienThi(new QuanLyKyThuatVien()); return true;

            default:                    return false;
        }
    }

    private static void hienThi(Component manHinh) {
        if (manHinh instanceof Window) {
            // Màn hình là JFrame/JDialog sẵn có
            Window cua = (Window) manHinh;
            cua.setLocationRelativeTo(null);
            cua.setVisible(true);
            return;
        }

        // Màn hình là JPanel: bọc vào một JFrame riêng để mở
        JFrame khung = new JFrame(manHinh.getClass().getSimpleName());
        khung.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // đóng cửa sổ này, không thoát cả app
        khung.add(manHinh);
        khung.pack();
        khung.setSize(Math.max(khung.getWidth(), 1100), Math.max(khung.getHeight(), 680));
        khung.setLocationRelativeTo(null);
        khung.setVisible(true);
    }

    private JComponent taoTrang(ChucNang cn) {
        if (cn == ChucNang.QUAN_LY_XE) return new QuanLyXePanel(vaiTro == VaiTro.ADMIN);
        return new TrangDangPhatTrien(cn);
    }

    static JButton taoNutChinh(String noiDung, Color mau) {
        JButton btnChinh = new JButton(noiDung);
        btnChinh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnChinh.setForeground(Color.WHITE);
        btnChinh.setBackground(mau);
        btnChinh.setFocusPainted(false);
        btnChinh.setBorderPainted(false);
        btnChinh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnChinh.setPreferredSize(new Dimension(120, 36));
        return btnChinh;
    }

    static JPanel taoTieuDeTrang(String tieuDe, String phuDe) {
        boolean coPhuDe = phuDe != null && !phuDe.trim().isEmpty();
        JPanel pnlTieuDe = new JPanel(new GridLayout(coPhuDe ? 2 : 1, 1, 0, 2));
        pnlTieuDe.setOpaque(false);
        pnlTieuDe.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));
        JLabel lblTieuDe = new JLabel(tieuDe);
        lblTieuDe.setFont(FONT_TIEU_DE);
        lblTieuDe.setForeground(MAU_TEXT);
        pnlTieuDe.add(lblTieuDe);
        if (coPhuDe) {
            JLabel lblPhuDe = new JLabel(phuDe);
            lblPhuDe.setFont(FONT_NHAN);
            lblPhuDe.setForeground(MAU_PHU);
            pnlTieuDe.add(lblPhuDe);
        }
        return pnlTieuDe;
    }

    static ImageIcon loadIcon(String duongDan, int rong, int cao) {
        URL url = TrangChuHeThong.class.getResource(duongDan);
        if (url == null) return null;
        Image img = new ImageIcon(url).getImage().getScaledInstance(rong, cao, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    static Image loadImage(String duongDan) {
        URL url = TrangChuHeThong.class.getResource(duongDan);
        return url == null ? null : new ImageIcon(url).getImage();
    }

    static JLabel taoLogo(String duongDan, int rong, int cao, String chuDuPhong) {
        ImageIcon icoLogo = loadIcon(duongDan, rong, cao);
        JLabel lblLogo = icoLogo != null ? new JLabel(icoLogo) : new JLabel(chuDuPhong);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        return lblLogo;
    }

    private void dangXuat() {
        int luaChon = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất khỏi hệ thống?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (luaChon == JOptionPane.YES_OPTION) {
            dispose();
            new DangNhap().setVisible(true);
        }
    }

    static class NenTrang extends JPanel {
        private final Image anhNen;
        NenTrang(LayoutManager boCuc, Image anhNen) {
            super(boCuc);
            this.anhNen = anhNen;
            setBackground(MAU_NEN);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (anhNen != null) {
                g.drawImage(anhNen, 0, 0, getWidth(), getHeight(), null);
                g.setColor(new Color(244, 246, 248, 225));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    static class TheTrang extends JPanel {
        TheTrang(LayoutManager boCuc) {
            super(boCuc);
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int rong = getWidth(), cao = getHeight();
            g2.setColor(new Color(15, 23, 42, 20));
            g2.fillRoundRect(0, 3, rong - 1, cao - 4, 20, 20);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, rong - 1, cao - 4, 20, 20);
            g2.setColor(MAU_VIEN);
            g2.drawRoundRect(0, 0, rong - 1, cao - 4, 20, 20);
            g2.dispose();
        }
    }

    static class TheThongKe extends TheTrang {
        private final Color mau;
        TheThongKe(String tieuDe, String giaTri, Color mau, Runnable thaoTac) {
            super(new BorderLayout());
            this.mau = mau;
            setBorder(BorderFactory.createEmptyBorder(16, 84, 16, 16));
            setPreferredSize(new Dimension(0, 110));
            JLabel lblGiaTri = new JLabel(giaTri);
            lblGiaTri.setFont(new Font("Segoe UI", Font.BOLD, 26));
            lblGiaTri.setForeground(MAU_TEXT);
            JLabel lblTen = new JLabel(tieuDe);
            lblTen.setFont(FONT_NHAN);
            lblTen.setForeground(MAU_PHU);
            JPanel pnlThongTin = new JPanel(new GridLayout(2, 1, 0, 4));
            pnlThongTin.setOpaque(false);
            pnlThongTin.add(lblGiaTri);
            pnlThongTin.add(lblTen);
            add(pnlThongTin, BorderLayout.CENTER);
            if (thaoTac != null) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) { thaoTac.run(); }
                });
            }
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int duongKinh = 46;
            int y = (getHeight() - duongKinh) / 2;
            g2.setColor(new Color(mau.getRed(), mau.getGreen(), mau.getBlue(), 40));
            g2.fillOval(20, y, duongKinh, duongKinh);
            g2.setColor(mau);
            g2.fillOval(20 + duongKinh / 2 - 7, y + duongKinh / 2 - 7, 14, 14);
            g2.dispose();
        }
    }

    static class BieuDoCot extends TheTrang {
        private final String tieuDe;
        private final String[] nhan;
        private final double[] giaTri;
        BieuDoCot(String tieuDe, String[] nhan, double[] giaTri) {
            super(null);
            this.tieuDe = tieuDe;
            this.nhan = nhan;
            this.giaTri = giaTri;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int rong = getWidth(), cao = getHeight();
            int trai = 56, phai = 20, tren = 50, duoi = 36;
            int rongBieuDo = rong - trai - phai;
            int caoBieuDo = cao - tren - duoi;
            g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
            g2.setColor(MAU_TEXT);
            g2.drawString(tieuDe, 20, 32);
            double max = 1;
            for (double v : giaTri) max = Math.max(max, v);
            max *= 1.2;
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            for (int i = 0; i <= 4; i++) {
                int y = tren + caoBieuDo - i * caoBieuDo / 4;
                g2.setColor(MAU_VIEN);
                g2.drawLine(trai, y, rong - phai, y);
                g2.setColor(MAU_PHU);
                g2.drawString(String.valueOf(Math.round(max * i / 4)), 14, y + 4);
            }
            FontMetrics fm = g2.getFontMetrics();
            int soCot = giaTri.length;
            int oRong = rongBieuDo / soCot;
            int rongCot = Math.min(36, oRong - 18);
            for (int i = 0; i < soCot; i++) {
                int cot = (int) (giaTri[i] / max * caoBieuDo);
                int x = trai + i * oRong + (oRong - rongCot) / 2;
                int y = tren + caoBieuDo - cot;
                g2.setPaint(new GradientPaint(0, y, MAU_XANH, 0, tren + caoBieuDo, new Color(147, 197, 253)));
                g2.fillRoundRect(x, y, rongCot, cot, 10, 10);
                g2.setColor(MAU_PHU);
                g2.drawString(nhan[i], x + (rongCot - fm.stringWidth(nhan[i])) / 2, cao - 14);
            }
            g2.dispose();
        }
    }

    static class BieuDoTron extends TheTrang {
        private final String tieuDe;
        private final String[] nhan;
        private final int[] soLieu;
        private final Color[] mau;
        BieuDoTron(String tieuDe, String[] nhan, int[] soLieu, Color[] mau) {
            super(null);
            this.tieuDe = tieuDe;
            this.nhan = nhan;
            this.soLieu = soLieu;
            this.mau = mau;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
            g2.setColor(MAU_TEXT);
            g2.drawString(tieuDe, 20, 32);
            int tong = 0;
            for (int v : soLieu) tong += v;
            if (tong == 0) {
                g2.dispose();
                return;
            }
            int d = Math.max(Math.min(getHeight() - 100, getWidth() / 2 - 40), 120);
            int x = 30, y = 60, daVe = 0;
            for (int i = 0; i < soLieu.length; i++) {
                int goc = i == soLieu.length - 1 ? 360 - daVe : (int) Math.round(360.0 * soLieu[i] / tong);
                g2.setColor(mau[i]);
                g2.fill(new Arc2D.Double(x, y, d, d, 90 - daVe, -goc, Arc2D.PIE));
                daVe += goc;
            }
            int lo = (int) (d * 0.62);
            g2.setColor(Color.WHITE);
            g2.fill(new Ellipse2D.Double(x + (d - lo) / 2.0, y + (d - lo) / 2.0, lo, lo));
            String chuTong = tong + " xe";
            g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
            g2.setColor(MAU_TEXT);
            FontMetrics fmTong = g2.getFontMetrics();
            g2.drawString(chuTong, x + d / 2 - fmTong.stringWidth(chuTong) / 2, y + d / 2 + fmTong.getAscent() / 3);
            int xChuGiai = x + d + 40, yChuGiai = y + 40;
            for (int i = 0; i < nhan.length; i++) {
                g2.setColor(mau[i]);
                g2.fillRoundRect(xChuGiai, yChuGiai - 12, 14, 14, 4, 4);
                g2.setColor(MAU_TEXT);
                g2.setFont(FONT_NHAN);
                g2.drawString(nhan[i] + ": " + soLieu[i] + " xe", xChuGiai + 22, yChuGiai);
                yChuGiai += 34;
            }
            g2.dispose();
        }
    }

    static class TongQuanPanel extends JPanel {
        TongQuanPanel(String hoTen, VaiTro vt) {
            super(new BorderLayout(0, 18));
            setOpaque(false);
            boolean xemDoanhThu = vt == VaiTro.ADMIN || vt == VaiTro.KE_TOAN;

            add(taoTieuDeTrang("Xin chào, " + hoTen, null), BorderLayout.NORTH);

            JPanel pnlHangThe = new JPanel(new GridLayout(1, 0, 16, 0));
            pnlHangThe.setOpaque(false);
            pnlHangThe.add(new TheThongKe("Xe cần kiểm tra", "5", MAU_CAM,
                    () -> moManHinhNghiepVu(ChucNang.CHAN_DOAN)));
            pnlHangThe.add(new TheThongKe("Phiếu chờ lập", "3", MAU_XANH_LA,
                    () -> moManHinhNghiepVu(ChucNang.PHIEU_SUA_CHUA)));
            pnlHangThe.add(new TheThongKe("Xe đang sửa chữa", "8", MAU_TIM,
                    () -> moManHinhNghiepVu(ChucNang.THUC_HIEN_SUA_CHUA)));
            pnlHangThe.add(new TheThongKe("Xe chờ KCS", "2", MAU_XANH_LA,
                    () -> moManHinhNghiepVu(ChucNang.KIEM_TRA_CHAT_LUONG)));
            pnlHangThe.add(new TheThongKe("Phụ tùng sắp hết", "6", MAU_DO,
                    () -> moManHinhNghiepVu(ChucNang.DANH_MUC_PHU_TUNG)));
            if (xemDoanhThu) {
                pnlHangThe.add(new TheThongKe("Doanh thu hôm nay", "18,5 tr", MAU_TIM,
                        () -> moManHinhNghiepVu(ChucNang.HOA_DON)));
            }

            JPanel pnlHangBieuDo = new JPanel(new GridLayout(1, 0, 16, 0));
            pnlHangBieuDo.setOpaque(false);
            if (xemDoanhThu) {
                pnlHangBieuDo.add(new BieuDoCot("Doanh thu 7 ngày gần nhất (triệu đ)",
                        new String[]{"T2", "T3", "T4", "T5", "T6", "T7", "CN"},
                        new double[]{12.5, 9.8, 15.2, 11.0, 18.7, 21.3, 7.4}));
            }
            pnlHangBieuDo.add(new BieuDoTron("Tình trạng xe trong gara",
                    new String[]{"Đang sửa", "Chờ báo giá", "Chờ phụ tùng", "Hoàn thành"},
                    new int[]{8, 5, 3, 12},
                    new Color[]{MAU_XANH, MAU_CAM, MAU_DO, MAU_XANH_LA}));

            JPanel pnlGiua = new JPanel(new BorderLayout(0, 18));
            pnlGiua.setOpaque(false);
            pnlGiua.add(pnlHangThe, BorderLayout.NORTH);
            pnlGiua.add(pnlHangBieuDo, BorderLayout.CENTER);
            add(pnlGiua, BorderLayout.CENTER);
        }
    }

    static class QuanLyXePanel extends JPanel {
        QuanLyXePanel(boolean coQuyenXoa) {
            super(new BorderLayout(0, 16));
            setOpaque(false);
            add(taoTieuDeTrang("Quản lý xe", "Hồ sơ xe và thông tin khách hàng của gara"), BorderLayout.NORTH);
            String[] cot = {"Biển số", "Hãng", "Dòng xe", "Năm SX", "Chủ xe", "Trạng thái"};
            Object[][] duLieu = {
                {"29A-123.45", "Toyota", "Vios", 2019, "Nguyễn Văn Nam", "Đang sửa"},
                {"30E-678.90", "Honda", "City", 2021, "Trần Thị Hoa", "Chờ báo giá"},
                {"51F-321.56", "Mazda", "CX-5", 2020, "Lê Hoàng Long", "Hoàn thành"},
                {"43A-555.12", "Hyundai", "Accent", 2018, "Phạm Minh Tuấn", "Chờ phụ tùng"}
            };
            DefaultTableModel model = new DefaultTableModel(duLieu, cot) {
                @Override
                public boolean isCellEditable(int row, int column) { return false; }
            };
            JTable tblXe = new JTable(model);
            tblXe.setRowHeight(30);
            tblXe.setFont(FONT_NHAN);
            tblXe.getTableHeader().setFont(FONT_NHAN_DAM);
            tblXe.setSelectionBackground(new Color(219, 234, 254));
            tblXe.setSelectionForeground(MAU_TEXT);
            tblXe.setFillsViewportHeight(true);
            JScrollPane scrXe = new JScrollPane(tblXe);
            scrXe.setBorder(null);
            JPanel pnlCongCu = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            pnlCongCu.setOpaque(false);
            JButton btnThem = taoNutChinh("+ Thêm xe", new Color(37, 99, 235));
            JButton btnSua = taoNutChinh("Sửa", new Color(124, 58, 237));
            btnThem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mở form thêm xe"));
            btnSua.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mở form sửa xe"));
            pnlCongCu.add(btnThem);
            pnlCongCu.add(btnSua);
            if (coQuyenXoa) {
                JButton btnXoa = taoNutChinh("Xóa", MAU_DO);
                btnXoa.addActionListener(e -> JOptionPane.showMessageDialog(this, "Xác nhận xóa xe"));
                pnlCongCu.add(btnXoa);
            }
            TheTrang pnlKhung = new TheTrang(new BorderLayout(0, 12));
            pnlKhung.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
            pnlKhung.add(pnlCongCu, BorderLayout.NORTH);
            pnlKhung.add(scrXe, BorderLayout.CENTER);
            add(pnlKhung, BorderLayout.CENTER);
        }
    }

    static class TrangDangPhatTrien extends JPanel {
        TrangDangPhatTrien(ChucNang cn) {
            super(new BorderLayout(0, 16));
            setOpaque(false);
            add(taoTieuDeTrang(cn.getTen(), cn.getMoTa()), BorderLayout.NORTH);
            TheTrang pnlKhung = new TheTrang(new GridBagLayout());
            JLabel lblThongBao = new JLabel("Chức năng \"" + cn.getTen() + "\" thuộc phân hệ phát triển của thành viên khác!");
            lblThongBao.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblThongBao.setForeground(MAU_PHU);
            pnlKhung.add(lblThongBao);
            add(pnlKhung, BorderLayout.CENTER);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrangChuHeThong("NV001", "Phan Xuân Phụng", VaiTro.LE_TAN).setVisible(true));
    }
}