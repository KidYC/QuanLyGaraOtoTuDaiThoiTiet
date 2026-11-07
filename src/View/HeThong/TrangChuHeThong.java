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
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
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
import javax.swing.SwingConstants;

import DAO.TrangChu_DAO;
import View.DangNhap;
import View.KeToan.ManHinhThongKeDoanhThu;
import View.KyThuatVien.CapNhatTienDoSuaChua;
import View.KyThuatVien.ChiTietKiemTraChanDoan;
import View.KyThuatVien.KiemTraChatLuong;
import View.KyThuatVien.LapPhieuSuaChua;
import View.HeThong.QuanLyNhanVien;
import View.KeToan.ManHinhChiTietHDVaThanhToan;
import View.KeToan.ManHinhDanhSachKHNo;
import View.KeToan.ManHinhDanhSachPhieuSuaChuaDaHoanThanh;
import View.KeToan.ManHinhQuanLyCa;
import View.LeTan.*;
import View.ThuKho.*;
import View.KyThuatVien.ManHinhThongKeSuaChua;

public class TrangChuHeThong extends JFrame implements ActionListener, MouseListener {

    private static final Font FONT_TIEU_DE = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_NHAN = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_NHAN_DAM = new Font("Segoe UI", Font.BOLD, 14);

    private final String maNV;
    private final String hoTen;
    private final VaiTro vaiTro;

    private CardLayout cardLayout;
    private NenTrang pnlNoiDung;

    private final Map<ChucNang, JButton> dsNutMenu = new LinkedHashMap<>();
    private final Map<NhomChucNang, JPanel> dsSubmenu = new LinkedHashMap<>();
    private final Map<NhomChucNang, JButton> dsNutNhom = new LinkedHashMap<>();

    private JButton btnDangChon;
    private JLabel lblLogout;
    private JPanel pnlDsMenu;
    private NhomChucNang nhomDangMo;

    private JPanel pnlHeader;
    private JPanel pnlTrai;
    private JLabel lblTieuDe;
    private JPanel pnlSidebar;
    private JButton btnTongQuan;
    private JButton btnNhom;
    private JPanel pnlSubmenu;
    private JButton btnMenu;
    private JScrollPane scrMenu;
    private JPanel pnlProfile;
    private JSeparator sprProfile;
    private JLabel lblTen;
    private JLabel lblChucVu;
    private JPanel pnlLogout;

    public enum VaiTro {
        ADMIN("Admin"), LE_TAN("Lễ tân"), KY_THUAT("Kỹ thuật viên"), THU_KHO("Thủ kho"), KE_TOAN("Kế toán");

        private final String ten;

        VaiTro(String ten) {
            this.ten = ten;
        }

        public String getTen() {
            return ten;
        }
    }

    public static VaiTro chuyenVaiTro(String loaiTaiKhoan) {
        if (loaiTaiKhoan == null) return null;
        String role = loaiTaiKhoan.trim().toLowerCase(java.util.Locale.ROOT);

        switch (role) {
            case "admin":
            case "quản lý":
            case "chủ gara":
            case "chủ gara (admin)":
            case "quản trị viên":
                return VaiTro.ADMIN;
            case "lễ tân":
                return VaiTro.LE_TAN;
            case "kỹ thuật viên":
            case "ky thuat vien":
                return VaiTro.KY_THUAT;
            case "thủ kho":
            case "thu kho":
                return VaiTro.THU_KHO;
            case "kế toán":
            case "ke toan":
                return VaiTro.KE_TOAN;
            default:
                return null;
        }
    }

    public enum NhomChucNang {
        CHUNG("TỔNG QUAN"),
        TIEP_NHAN("TIẾP NHẬN & KHÁCH HÀNG"),
        KHO("KHO PHỤ TÙNG"),
        SUA_CHUA("SỬA CHỮA & BẢO HÀNH"),
        THANH_TOAN("THANH TOÁN & THU NGÂN"),
        THONG_KE("THỐNG KÊ BÁO CÁO"),
        HE_THONG("HỆ THỐNG");

        private final String ten;

        NhomChucNang(String ten) {
            this.ten = ten;
        }

        public String getTen() {
            return ten;
        }
    }

    public enum ChucNang {
        TONG_QUAN("Trang chủ", NhomChucNang.CHUNG, "Tổng quan hoạt động của gara", VaiTro.values()),

        QUAN_LY_KHACH_HANG("Quản lý khách hàng", NhomChucNang.TIEP_NHAN, "Danh sách và thông tin khách hàng", VaiTro.LE_TAN),
        QUAN_LY_XE("Quản lý xe", NhomChucNang.TIEP_NHAN, "Hồ sơ xe của khách hàng", VaiTro.LE_TAN),
        TIEP_NHAN_XE("Tiếp nhận xe", NhomChucNang.TIEP_NHAN, "Lập phiếu tiếp nhận xe vào gara", VaiTro.LE_TAN),
        LAP_BAO_GIA("Lập báo giá", NhomChucNang.TIEP_NHAN, "Báo giá sửa chữa cho khách hàng", VaiTro.LE_TAN),
        BAN_GIAO_XE("Bàn giao xe", NhomChucNang.TIEP_NHAN, "Bàn giao xe và chăm sóc khách hàng", VaiTro.LE_TAN),

        DANH_MUC_PHU_TUNG("Danh mục phụ tùng", NhomChucNang.KHO, "Quản lý danh mục phụ tùng", VaiTro.THU_KHO),
        NHAP_KHO("Danh sách phiếu nhập kho", NhomChucNang.KHO, "Tra cứu các phiếu nhập kho", VaiTro.THU_KHO),
        LAP_PHIEU_NHAP_KHO("Lập phiếu nhập kho", NhomChucNang.KHO, "Tạo phiếu nhập phụ tùng", VaiTro.THU_KHO),
        XUAT_KHO("Danh sách yêu cầu xuất kho", NhomChucNang.KHO, "Tra cứu yêu cầu xuất phụ tùng", VaiTro.THU_KHO),
        KIEM_KE("Danh sách phiếu kiểm kê", NhomChucNang.KHO, "Tra cứu phiếu kiểm kê kho", VaiTro.THU_KHO),
        LAP_PHIEU_KIEM_KE("Lập phiếu kiểm kê", NhomChucNang.KHO, "Tạo phiếu kiểm kê kho", VaiTro.THU_KHO),

        CHAN_DOAN("Chẩn đoán xe", NhomChucNang.SUA_CHUA, "Kiểm tra và chẩn đoán lỗi xe", VaiTro.KY_THUAT),
        PHIEU_SUA_CHUA("Phiếu sửa chữa", NhomChucNang.SUA_CHUA, "Lập phiếu, công việc và phụ tùng", VaiTro.KY_THUAT),
        THUC_HIEN_SUA_CHUA("Thực hiện sửa chữa", NhomChucNang.SUA_CHUA, "Cập nhật tiến độ thi công", VaiTro.KY_THUAT),
        KIEM_TRA_CHAT_LUONG("Kiểm tra chất lượng", NhomChucNang.SUA_CHUA, "Kiểm tra xe sau khi sửa chữa", VaiTro.KY_THUAT),

        HOA_DON_THANH_TOAN("Hóa đơn", NhomChucNang.THANH_TOAN, "Lập hóa đơn và thu tiền", VaiTro.KE_TOAN),
        DANH_SACH_PHIEU_HOAN_THANH("Danh sách phiếu hoàn thành", NhomChucNang.THANH_TOAN, "Tra cứu các phiếu sửa chữa đã hoàn thành", VaiTro.KE_TOAN),
        CONG_NO("Công nợ", NhomChucNang.THANH_TOAN, "Theo dõi công nợ khách thân thiết", VaiTro.KE_TOAN),
        CA_THU_NGAN("Quản lý ca thu ngân", NhomChucNang.THANH_TOAN, "Mở ca, đóng ca và đối soát doanh thu", VaiTro.KE_TOAN),

        THONG_KE_DOANH_THU("Doanh thu", NhomChucNang.THONG_KE, "Thống kê doanh thu theo kỳ", VaiTro.KE_TOAN),
        THONG_KE_SUA_CHUA("Sửa chữa", NhomChucNang.THONG_KE, "Thống kê xe và phiếu sửa chữa", VaiTro.KY_THUAT),
        THONG_KE_KHO("Kho", NhomChucNang.THONG_KE, "Tồn kho và nhập xuất theo thời gian", VaiTro.THU_KHO),

        QUAN_LY_NHAN_VIEN("Quản lý nhân viên", NhomChucNang.HE_THONG, "Quản lý hồ sơ nhân sự", VaiTro.ADMIN),
        QUAN_LY_DICH_VU("Quản lý dịch vụ", NhomChucNang.HE_THONG, "Quản lý dịch vụ hệ thống hỗ trợ", VaiTro.ADMIN);
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
            for (VaiTro r : cacVaiTro) {
                if (r == vt) return true;
            }
            return false;
        }

        public String getTen() {
            return ten;
        }

        public NhomChucNang getNhom() {
            return nhom;
        }

        public String getMoTa() {
            return moTa;
        }
    }

    public TrangChuHeThong() {
        this("KTV01", "Phan Xuân Phụng", VaiTro.KY_THUAT);
    }

    public TrangChuHeThong(String maNV, String hoTen, VaiTro vaiTro) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.vaiTro = vaiTro;

        setTitle("Hệ Thống Quản Lý Gara Ô Tô - " + vaiTro.getTen());
        getContentPane().setBackground(new Color(244, 246, 248));
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
        pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)), BorderFactory.createEmptyBorder(8, 20, 8, 20)));
        pnlHeader.setPreferredSize(new Dimension(0, 70));

        pnlTrai = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        pnlTrai.setOpaque(false);
        pnlTrai.add(taoLogo("/resources/icons/logo.png", 60, 52, "GO"));

        lblTieuDe = new JLabel("HỆ THỐNG QUẢN LÝ GARA Ô TÔ");
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTieuDe.setForeground(new Color(17, 24, 39));

        pnlTrai.add(lblTieuDe);
        pnlHeader.add(pnlTrai, BorderLayout.WEST);
        return pnlHeader;
    }

    private JPanel taoSidebar() {
        pnlSidebar = new JPanel(new BorderLayout());
        pnlSidebar.setBackground(new Color(226, 232, 240));
        pnlSidebar.setPreferredSize(new Dimension(260, 0));

        pnlDsMenu = new JPanel();
        pnlDsMenu.setLayout(new BoxLayout(pnlDsMenu, BoxLayout.Y_AXIS));
        pnlDsMenu.setBackground(new Color(226, 232, 240));
        pnlDsMenu.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        btnTongQuan = taoNutMenu(ChucNang.TONG_QUAN);
        pnlDsMenu.add(btnTongQuan);
        pnlDsMenu.add(Box.createVerticalStrut(4));
        dsNutMenu.put(ChucNang.TONG_QUAN, btnTongQuan);

        for (NhomChucNang nhom : NhomChucNang.values()) {
            if (nhom == NhomChucNang.CHUNG) continue;

            boolean coChucNang = false;
            for (ChucNang cn : ChucNang.values()) {
                if (cn.getNhom() == nhom && cn.choPhep(vaiTro)) {
                    coChucNang = true;
                    break;
                }
            }
            if (!coChucNang) continue;

            btnNhom = new JButton(nhom.getTen());
            btnNhom.setFont(FONT_NHAN_DAM);
            btnNhom.setForeground(new Color(17, 24, 39));
            btnNhom.setBackground(new Color(226, 232, 240));
            btnNhom.setHorizontalAlignment(SwingConstants.LEFT);
            btnNhom.setFocusPainted(false);
            btnNhom.setBorderPainted(false);
            btnNhom.setOpaque(true);
            btnNhom.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnNhom.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 8));
            btnNhom.setAlignmentX(LEFT_ALIGNMENT);
            btnNhom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

            pnlSubmenu = new JPanel();
            pnlSubmenu.setLayout(new BoxLayout(pnlSubmenu, BoxLayout.Y_AXIS));
            pnlSubmenu.setBackground(new Color(226, 232, 240));
            pnlSubmenu.setBorder(BorderFactory.createEmptyBorder(0, 8, 4, 0));
            pnlSubmenu.setVisible(false);

            for (ChucNang cn : ChucNang.values()) {
                if (cn.getNhom() != nhom || !cn.choPhep(vaiTro)) continue;

                btnMenu = taoNutMenu(cn);
                btnMenu.setBorder(BorderFactory.createEmptyBorder(9, 22, 9, 8));
                btnMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
                pnlSubmenu.add(btnMenu);
                pnlSubmenu.add(Box.createVerticalStrut(2));
                dsNutMenu.put(cn, btnMenu);
            }

            btnNhom.addActionListener(this);

            dsNutNhom.put(nhom, btnNhom);
            dsSubmenu.put(nhom, pnlSubmenu);
            pnlDsMenu.add(btnNhom);
            pnlDsMenu.add(pnlSubmenu);
            pnlDsMenu.add(Box.createVerticalStrut(4));
        }

        scrMenu = new JScrollPane(pnlDsMenu);
        scrMenu.setBorder(null);
        scrMenu.getViewport().setBackground(new Color(226, 232, 240));
        scrMenu.getVerticalScrollBar().setUnitIncrement(12);

        pnlSidebar.add(scrMenu, BorderLayout.CENTER);
        pnlSidebar.add(taoThongTinUser(), BorderLayout.SOUTH);
        return pnlSidebar;
    }

    private JPanel taoThongTinUser() {
        pnlProfile = new JPanel();
        pnlProfile.setLayout(new BoxLayout(pnlProfile, BoxLayout.Y_AXIS));
        pnlProfile.setBackground(new Color(226, 232, 240));
        pnlProfile.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        sprProfile = new JSeparator();
        sprProfile.setForeground(new Color(203, 213, 225));
        sprProfile.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        lblTen = new JLabel(hoTen);
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTen.setForeground(new Color(17, 24, 39));
        lblTen.setAlignmentX(LEFT_ALIGNMENT);

        lblChucVu = new JLabel(vaiTro.getTen());
        lblChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblChucVu.setForeground(new Color(107, 114, 128));
        lblChucVu.setAlignmentX(LEFT_ALIGNMENT);

        pnlLogout = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlLogout.setBackground(new Color(226, 232, 240));
        pnlLogout.setAlignmentX(LEFT_ALIGNMENT);
        pnlLogout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        lblLogout = new JLabel("Đăng xuất");
        ImageIcon icoLogout = loadIcon("/resources/icons/logout.png", 18, 18);
        if (icoLogout != null) lblLogout.setIcon(icoLogout);
        lblLogout.setIconTextGap(7);
        lblLogout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblLogout.setForeground(new Color(220, 38, 38));
        lblLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlLogout.add(lblLogout);
        lblLogout.addMouseListener(this);

        pnlProfile.add(sprProfile);
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
        btnMenu.setForeground(new Color(17, 24, 39));
        btnMenu.setBackground(new Color(226, 232, 240));
        btnMenu.setHorizontalAlignment(SwingConstants.LEFT);
        btnMenu.setFocusPainted(false);
        btnMenu.setBorderPainted(false);
        btnMenu.setOpaque(true);
        btnMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMenu.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 12));
        btnMenu.setAlignmentX(LEFT_ALIGNMENT);
        btnMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnMenu.putClientProperty("ChucNang", cn);
        btnMenu.addActionListener(this);
        return btnMenu;
    }

    private JComponent taoVungNoiDung() {
        cardLayout = new CardLayout();
        pnlNoiDung = new NenTrang(cardLayout, loadImage("/resources/images/background.jpg"));
        pnlNoiDung.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        pnlNoiDung.add(new TongQuanPanel(hoTen, vaiTro), ChucNang.TONG_QUAN.name());
        return pnlNoiDung;
    }

    private void chonChucNang(ChucNang cn, JButton btnMenu) {
        if (btnDangChon != null) {
            btnDangChon.setBackground(new Color(226, 232, 240));
            btnDangChon.setForeground(new Color(17, 24, 39));
            btnDangChon.setFont(FONT_NHAN);
        }

        if (btnMenu != null) {
            btnMenu.setBackground(new Color(186, 230, 253));
            btnMenu.setForeground(new Color(17, 24, 39));
            btnMenu.setFont(FONT_NHAN_DAM);
            btnDangChon = btnMenu;
        }

        if (!moManHinhNghiepVu(cn, this.maNV) && cardLayout != null) {
            cardLayout.show(pnlNoiDung, cn.name());
        }
    }

    static boolean moManHinhNghiepVu(ChucNang cn, String maNV) {
        switch (cn) {
            case QUAN_LY_KHACH_HANG:          hienThi(new ManHinhQuanLyKhachHang()); return true;
            case QUAN_LY_XE:                  hienThi(new DanhSachXeKhachHang("001", "Minh Lo", "0123456789")); return true;
            case TIEP_NHAN_XE:                hienThi(new ManHinhLapPhieuTiepNhan()); return true;
            case LAP_BAO_GIA:                 hienThi(new ManHinhDanhSachXeChoBaoGia()); return true;
            case BAN_GIAO_XE:                 hienThi(new ManHinhDanhSachXeDaHoanThanhSuaChua()); return true;

            case DANH_MUC_PHU_TUNG:           hienThi(new ManHinhQuanLyDanhMucPhuTung()); return true;
            case NHAP_KHO:                    hienThi(new ManHinhDanhSachPhieuNhapKho()); return true;
            case LAP_PHIEU_NHAP_KHO:          hienThi(new ManHinhLapPhieuNhapKho()); return true;
            case XUAT_KHO:                    hienThi(new ManHinhDanhSachYeuCauXuatKho()); return true;
            case KIEM_KE:                     hienThi(new ManHinhDanhSachPhieuKiemKe()); return true;
            case LAP_PHIEU_KIEM_KE:           hienThi(new ManHinhLapPhieuKiemKe()); return true;

            case CHAN_DOAN:                   hienThi(new ChiTietKiemTraChanDoan(maNV)); return true;
            case PHIEU_SUA_CHUA:              hienThi((maNV != null && !maNV.trim().isEmpty()) ? new LapPhieuSuaChua(maNV) : new LapPhieuSuaChua()); return true;
            case THUC_HIEN_SUA_CHUA:          hienThi(new CapNhatTienDoSuaChua()); return true;
            case KIEM_TRA_CHAT_LUONG:         hienThi(new KiemTraChatLuong()); return true;

            case HOA_DON_THANH_TOAN:          hienThi(new ManHinhChiTietHDVaThanhToan()); return true;
            case DANH_SACH_PHIEU_HOAN_THANH:  hienThi(new ManHinhDanhSachPhieuSuaChuaDaHoanThanh()); return true;
            case CONG_NO:                     hienThi(new ManHinhDanhSachKHNo()); return true;
            case CA_THU_NGAN:                 hienThi(new ManHinhQuanLyCa()); return true;

            case THONG_KE_DOANH_THU:          hienThi(new ManHinhThongKeDoanhThu()); return true;
            case THONG_KE_SUA_CHUA:           hienThi(new ManHinhThongKeSuaChua()); return true;
            case THONG_KE_KHO:                hienThi(new ManHinhThongKeKho()); return true;

            case QUAN_LY_NHAN_VIEN:           hienThi(new QuanLyNhanVien()); return true;
            case QUAN_LY_DICH_VU:           hienThi(new QuanLyDichVu()); return true;

            default:                          return false;
        }
    }

    private static void hienThi(Component manHinh) {
        if (manHinh instanceof Window) {
            Window winCua = (Window) manHinh;
            winCua.setLocationRelativeTo(null);
            winCua.setVisible(true);
            return;
        }

        JFrame winKhung = new JFrame(manHinh.getClass().getSimpleName());
        winKhung.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        winKhung.add(manHinh);
        winKhung.pack();
        winKhung.setSize(Math.max(winKhung.getWidth(), 1100), Math.max(winKhung.getHeight(), 680));
        winKhung.setLocationRelativeTo(null);
        winKhung.setVisible(true);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source instanceof JButton) {
            JButton btn = (JButton) source;
            ChucNang cn = (ChucNang) btn.getClientProperty("ChucNang");

            if (cn != null) {
                chonChucNang(cn, btn);
                return;
            }

            for (Map.Entry<NhomChucNang, JButton> entry : dsNutNhom.entrySet()) {
                if (entry.getValue() == btn) {
                    NhomChucNang nhom = entry.getKey();
                    JPanel pnlSubmenu = dsSubmenu.get(nhom);

                    if (pnlSubmenu != null) {
                        boolean mo = !pnlSubmenu.isVisible();

                        for (Map.Entry<NhomChucNang, JPanel> subEntry : dsSubmenu.entrySet()) {
                            subEntry.getValue().setVisible(false);
                            JButton nut = dsNutNhom.get(subEntry.getKey());

                            if (nut != null) {
                                nut.setText(subEntry.getKey().getTen());
                                nut.setBackground(new Color(226, 232, 240));
                                nut.setFont(FONT_NHAN_DAM);
                            }
                        }

                        if (mo) {
                            pnlSubmenu.setVisible(true);
                            btn.setText(nhom.getTen());
                            btn.setBackground(new Color(186, 230, 253));
                            nhomDangMo = nhom;
                        } else {
                            nhomDangMo = null;
                        }

                        if (pnlDsMenu != null) {
                            pnlDsMenu.revalidate();
                            pnlDsMenu.repaint();
                        }
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == lblLogout) dangXuat();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == lblLogout) lblLogout.setForeground(new Color(248, 113, 113));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == lblLogout) lblLogout.setForeground(new Color(220, 38, 38));
    }

    static class NenTrang extends JPanel {
        private final Image anhNen;

        NenTrang(LayoutManager boCuc, Image anhNen) {
            super(boCuc);
            this.anhNen = anhNen;
            setBackground(new Color(244, 246, 248));
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

            int rong = getWidth();
            int cao = getHeight();

            g2.setColor(new Color(15, 23, 42, 20));
            g2.fillRoundRect(0, 3, rong - 1, cao - 4, 20, 20);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, rong - 1, cao - 4, 20, 20);
            g2.setColor(new Color(226, 232, 240));
            g2.drawRoundRect(0, 0, rong - 1, cao - 4, 20, 20);

            g2.dispose();
        }
    }

    static class TheThongKe extends TheTrang {
        private final Color mau;

        private JLabel lblGiaTri;
        private JLabel lblTen;
        private JPanel pnlThongTin;

        TheThongKe(String tieuDe, String giaTri, Color mau, Runnable thaoTac) {
            super(new BorderLayout());
            this.mau = mau;

            setBorder(BorderFactory.createEmptyBorder(16, 84, 16, 16));
            setPreferredSize(new Dimension(0, 110));

            lblGiaTri = new JLabel(giaTri);
            lblGiaTri.setFont(new Font("Segoe UI", Font.BOLD, 24));
            lblGiaTri.setForeground(new Color(17, 24, 39));

            lblTen = new JLabel(tieuDe);
            lblTen.setFont(FONT_NHAN);
            lblTen.setForeground(new Color(107, 114, 128));

            pnlThongTin = new JPanel(new GridLayout(2, 1, 0, 4));
            pnlThongTin.setOpaque(false);
            pnlThongTin.add(lblGiaTri);
            pnlThongTin.add(lblTen);

            add(pnlThongTin, BorderLayout.CENTER);

            if (thaoTac != null) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        thaoTac.run();
                    }
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

            int rong = getWidth();
            int cao = getHeight();
            int trai = 65;
            int phai = 20;
            int tren = 50;
            int duoi = 36;

            int rongBieuDo = rong - trai - phai;
            int caoBieuDo = cao - tren - duoi;

            g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
            g2.setColor(new Color(17, 24, 39));
            g2.drawString(tieuDe, 20, 32);

            double max = 1;
            for (double v : giaTri) max = Math.max(max, v);
            if (max == 0) max = 1;
            max *= 1.2;

            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            DecimalFormat dfShort = new DecimalFormat("#,###");

            for (int i = 0; i <= 4; i++) {
                int y = tren + caoBieuDo - i * caoBieuDo / 4;
                g2.setColor(new Color(226, 232, 240));
                g2.drawLine(trai, y, rong - phai, y);
                g2.setColor(new Color(107, 114, 128));
                double valStr = max * i / 4;
                g2.drawString(dfShort.format(valStr), 10, y + 4);
            }

            FontMetrics fm = g2.getFontMetrics();
            int soCot = giaTri.length;
            int oRong = rongBieuDo / soCot;
            int rongCot = Math.min(28, oRong - 8);

            for (int i = 0; i < soCot; i++) {
                int cot = (int) (giaTri[i] / max * caoBieuDo);
                int x = trai + i * oRong + (oRong - rongCot) / 2;
                int y = tren + caoBieuDo - cot;

                g2.setPaint(new GradientPaint(0, y, new Color(186, 230, 253), 0, tren + caoBieuDo, new Color(147, 197, 253)));
                g2.fillRoundRect(x, y, rongCot, cot, 8, 8);
                g2.setColor(new Color(107, 114, 128));
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
            g2.setColor(new Color(17, 24, 39));
            g2.drawString(tieuDe, 20, 32);

            int tong = 0;
            for (int v : soLieu) tong += v;

            if (tong == 0) {
                g2.setFont(FONT_NHAN);
                g2.setColor(new Color(107, 114, 128));
                g2.drawString("Chưa có dữ liệu xe", 30, 80);
                g2.dispose();
                return;
            }

            int d = Math.max(Math.min(getHeight() - 100, getWidth() / 2 - 40), 120);
            int x = 30;
            int y = 60;
            int daVe = 0;

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
            g2.setColor(new Color(17, 24, 39));

            FontMetrics fmTong = g2.getFontMetrics();
            g2.drawString(chuTong, x + d / 2 - fmTong.stringWidth(chuTong) / 2, y + d / 2 + fmTong.getAscent() / 3);

            int xChuGiai = x + d + 40;
            int yChuGiai = y + 40;

            for (int i = 0; i < nhan.length; i++) {
                g2.setColor(mau[i]);
                g2.fillRoundRect(xChuGiai, yChuGiai - 12, 14, 14, 4, 4);
                g2.setColor(new Color(17, 24, 39));
                g2.setFont(FONT_NHAN);
                g2.drawString(nhan[i] + ": " + soLieu[i] + " xe", xChuGiai + 22, yChuGiai);
                yChuGiai += 34;
            }

            g2.dispose();
        }
    }

    static JPanel taoTieuDeTrang(String tieuDe, String phuDe) {
        boolean coPhuDe = phuDe != null && !phuDe.trim().isEmpty();

        JPanel pnlTieuDe = new JPanel(new GridLayout(coPhuDe ? 2 : 1, 1, 0, 2));
        pnlTieuDe.setOpaque(false);
        pnlTieuDe.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));

        JLabel lblTieuDe = new JLabel(tieuDe);
        lblTieuDe.setFont(FONT_TIEU_DE);
        lblTieuDe.setForeground(new Color(17, 24, 39));
        pnlTieuDe.add(lblTieuDe);

        if (coPhuDe) {
            JLabel lblPhuDe = new JLabel(phuDe);
            lblPhuDe.setFont(FONT_NHAN);
            lblPhuDe.setForeground(new Color(107, 114, 128));
            pnlTieuDe.add(lblPhuDe);
        }

        return pnlTieuDe;
    }

    static class TongQuanPanel extends JPanel {
        private JPanel pnlHangThe;
        private JPanel pnlHangBieuDo;
        private JPanel pnlGiua;

        TongQuanPanel(String hoTen, VaiTro vt) {
            super(new BorderLayout(0, 18));
            setOpaque(false);

            boolean xemDoanhThu = vt == VaiTro.ADMIN || vt == VaiTro.KE_TOAN;

            TrangChu_DAO trangChuDAO = new TrangChu_DAO();
            Map<String, Object> kpiMap = new LinkedHashMap<>();
            double[] doanhThu12Thang = new double[12];
            int namHienTai = Calendar.getInstance().get(Calendar.YEAR);

            try {
                kpiMap = trangChuDAO.laySoLieuKPI();
                doanhThu12Thang = trangChuDAO.layDoanhThu12Thang(namHienTai);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            DecimalFormat df = new DecimalFormat("#,###");

            int soXeDangXuLy = kpiMap.get("SoXeDangXuLy") != null ? (int) kpiMap.get("SoXeDangXuLy") : 0;
            int soHoaDonThang = kpiMap.get("SoHoaDonThangNay") != null ? (int) kpiMap.get("SoHoaDonThangNay") : 0;
            double doanhThuThang = kpiMap.get("DoanhThuThangNay") != null ? (double) kpiMap.get("DoanhThuThangNay") : 0.0;
            int soPhuTungSapHet = kpiMap.get("SoPhuTungSapHet") != null ? (int) kpiMap.get("SoPhuTungSapHet") : 0;

            add(taoTieuDeTrang("Xin chào, " + hoTen, null), BorderLayout.NORTH);

            pnlHangThe = new JPanel(new GridLayout(1, 0, 16, 0));
            pnlHangThe.setOpaque(false);

            pnlHangThe.add(new TheThongKe("Xe đang xử lý", String.valueOf(soXeDangXuLy), new Color(245, 158, 11),
                    () -> moManHinhNghiepVu(ChucNang.CHAN_DOAN, null)));
            pnlHangThe.add(new TheThongKe("Số HĐ tháng này", String.valueOf(soHoaDonThang), new Color(34, 197, 94),
                    () -> moManHinhNghiepVu(ChucNang.HOA_DON_THANH_TOAN, null)));
            pnlHangThe.add(new TheThongKe("Phụ tùng sắp hết", String.valueOf(soPhuTungSapHet), new Color(220, 38, 38),
                    () -> moManHinhNghiepVu(ChucNang.THONG_KE_KHO, null)));

            if (xemDoanhThu) {
                pnlHangThe.add(new TheThongKe("Doanh thu tháng này", df.format(doanhThuThang) + " đ", new Color(219, 234, 254),
                        () -> moManHinhNghiepVu(ChucNang.THONG_KE_DOANH_THU, null)));
            }

            pnlHangBieuDo = new JPanel(new GridLayout(1, 0, 16, 0));
            pnlHangBieuDo.setOpaque(false);

            if (xemDoanhThu) {
                String[] nhanThang = new String[]{"T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "T12"};
                pnlHangBieuDo.add(new BieuDoCot("Doanh thu 12 tháng năm " + namHienTai + " (VNĐ)", nhanThang, doanhThu12Thang));
            }

            pnlHangBieuDo.add(new BieuDoTron("Tình trạng xe trong gara",
                    new String[]{"Đang sửa", "Chờ báo giá", "Chờ phụ tùng", "Hoàn thành"},
                    new int[]{soXeDangXuLy, 3, 2, 5},
                    new Color[]{new Color(186, 230, 253), new Color(245, 158, 11), new Color(220, 38, 38), new Color(34, 197, 94)}));

            pnlGiua = new JPanel(new BorderLayout(0, 18));
            pnlGiua.setOpaque(false);
            pnlGiua.add(pnlHangThe, BorderLayout.NORTH);
            pnlGiua.add(pnlHangBieuDo, BorderLayout.CENTER);

            add(pnlGiua, BorderLayout.CENTER);
        }
    }
}