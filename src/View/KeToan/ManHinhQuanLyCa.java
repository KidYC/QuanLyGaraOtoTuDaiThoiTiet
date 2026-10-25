
package View.KeToan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManHinhQuanLyCa extends JFrame implements ActionListener {

    private final JLabel lbltieude;
    private final JPanel pWest;
    private final JLabel lblmenu;
    private final JLabel tieude;
    private final JPanel pCenter;
    private final JPanel card1;
    private final JLabel dangmo;
    private final JLabel nhanvien;
    private final JLabel giomo;
    private final JLabel sotien;
    private final JPanel card2;
    private final JButton dongca;
    private final JButton moca;
    private final JPanel pNorth;

    private JButton trangchu;
    private JButton qlca;
    private JButton dsphieusuachua;
    private JButton quanlycongno;
    private JButton baocaodoanhthu;
    private JButton quaylai;

    // BẢNG MÀU CHUNG
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

    private static final Font FONT_TIEU_DE =
            new Font("Segoe UI", Font.BOLD, 22);

    private static final Font FONT_NHAN =
            new Font("Segoe UI", Font.PLAIN, 14);

    private static final Font FONT_NHAN_DAM =
            new Font("Segoe UI", Font.BOLD, 14);

    public ManHinhQuanLyCa() {

        setTitle("Quản lý ca làm việc");
        setSize(1050, 800);
        setMinimumSize(new Dimension(900, 650));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());



        pNorth = new JPanel(new BorderLayout());
        pNorth.setBackground(MAU_NEN);
        pNorth.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, MAU_VIEN),
                new EmptyBorder(22, 30, 22, 30)
        ));

        lbltieude = new JLabel("QUẢN LÝ CA LÀM VIỆC");
        lbltieude.setFont(FONT_TIEU_DE);
        lbltieude.setForeground(MAU_TEXT);

        JLabel lblmota = new JLabel(
                "Theo dõi trạng thái và quản lý hoạt động ca kế toán"
        );
        lblmota.setFont(FONT_NHAN);
        lblmota.setForeground(MAU_PHU);

        JPanel tieudeNorth = new JPanel();
        tieudeNorth.setLayout(
                new BoxLayout(tieudeNorth, BoxLayout.Y_AXIS)
        );
        tieudeNorth.setBackground(MAU_NEN);

        tieudeNorth.add(lbltieude);
        tieudeNorth.add(Box.createVerticalStrut(7));
        tieudeNorth.add(lblmota);

        pNorth.add(tieudeNorth, BorderLayout.WEST);
        add(pNorth, BorderLayout.NORTH);



        pWest = new JPanel();
        pWest.setLayout(new BoxLayout(pWest, BoxLayout.Y_AXIS));
        pWest.setBackground(MAU_SIDEBAR);
        pWest.setPreferredSize(new Dimension(230, 0));
        pWest.setBorder(new EmptyBorder(25, 15, 20, 15));

        lblmenu = new JLabel("MENU CHÍNH");
        lblmenu.setFont(FONT_NHAN_DAM);
        lblmenu.setForeground(MAU_CHU_SIDEBAR);
        lblmenu.setAlignmentX(Component.LEFT_ALIGNMENT);

        trangchu = taoNutMenu("Trang chủ kế toán");
        qlca = taoNutMenu("Quản lý ca");
        dsphieusuachua = taoNutMenu("Phiếu sửa chữa");
        quanlycongno = taoNutMenu("Quản lý công nợ");
        baocaodoanhthu = taoNutMenu("Báo cáo doanh thu");
        quaylai = taoNutMenu("Quay lại trang chủ");

        pWest.add(lblmenu);
        pWest.add(Box.createVerticalStrut(25));

        pWest.add(trangchu);
        pWest.add(Box.createVerticalStrut(10));

        pWest.add(qlca);
        pWest.add(Box.createVerticalStrut(10));

        pWest.add(dsphieusuachua);
        pWest.add(Box.createVerticalStrut(10));

        pWest.add(quanlycongno);
        pWest.add(Box.createVerticalStrut(10));

        pWest.add(baocaodoanhthu);

        pWest.add(Box.createVerticalGlue());
        pWest.add(new JSeparator());
        pWest.add(Box.createVerticalStrut(15));
        pWest.add(quaylai);

        add(pWest, BorderLayout.WEST);



        pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
        pCenter.setBackground(MAU_NEN);
        pCenter.setBorder(new EmptyBorder(30, 35, 30, 35));

        tieude = new JLabel("Tổng quan ca làm việc");
        tieude.setFont(FONT_TIEU_DE);
        tieude.setForeground(MAU_TEXT);
        tieude.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblmotaCenter = new JLabel(
                "Thông tin ca hiện tại và các thao tác quản lý"
        );
        lblmotaCenter.setFont(FONT_NHAN);
        lblmotaCenter.setForeground(MAU_PHU);
        lblmotaCenter.setAlignmentX(Component.LEFT_ALIGNMENT);

        pCenter.add(tieude);
        pCenter.add(Box.createVerticalStrut(8));
        pCenter.add(lblmotaCenter);
        pCenter.add(Box.createVerticalStrut(28));



        card1 = new JPanel();
        card1.setLayout(new BoxLayout(card1, BoxLayout.Y_AXIS));
        card1.setBackground(Color.WHITE);
        card1.setAlignmentX(Component.LEFT_ALIGNMENT);
        card1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));

        card1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAU_VIEN, 1),
                new EmptyBorder(24, 25, 24, 25)
        ));

        JLabel lblcard = new JLabel("THÔNG TIN CA HIỆN TẠI");
        lblcard.setFont(FONT_NHAN_DAM);
        lblcard.setForeground(MAU_PHU);
        lblcard.setAlignmentX(Component.LEFT_ALIGNMENT);

        dangmo = new JLabel("Đang mở ca");
        dangmo.setFont(FONT_NHAN_DAM);
        dangmo.setForeground(MAU_XANH_LA);
        dangmo.setAlignmentX(Component.LEFT_ALIGNMENT);

        nhanvien = taoLabelThongTin(
                "Nhân viên phụ trách",
                "KT Trần Thanh Liêm"
        );

        giomo = taoLabelThongTin(
                "Thời gian mở ca",
                "08:30 - 03/10/2026"
        );

        sotien = taoLabelThongTin(
                "Số tiền ban đầu",
                "2.000.000 VNĐ"
        );

        card1.add(lblcard);
        card1.add(Box.createVerticalStrut(20));
        card1.add(dangmo);
        card1.add(Box.createVerticalStrut(18));
        card1.add(new JSeparator());
        card1.add(Box.createVerticalStrut(18));
        card1.add(nhanvien);
        card1.add(Box.createVerticalStrut(15));
        card1.add(giomo);
        card1.add(Box.createVerticalStrut(15));
        card1.add(sotien);

        pCenter.add(card1);
        pCenter.add(Box.createVerticalStrut(25));


        card2 = new JPanel();
        card2.setLayout(new BoxLayout(card2, BoxLayout.Y_AXIS));
        card2.setBackground(Color.WHITE);
        card2.setAlignmentX(Component.LEFT_ALIGNMENT);
        card2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        card2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAU_VIEN, 1),
                new EmptyBorder(24, 25, 24, 25)
        ));

        JLabel lblthaotac = new JLabel("THAO TÁC CA LÀM VIỆC");
        lblthaotac.setFont(FONT_NHAN_DAM);
        lblthaotac.setForeground(MAU_TEXT);
        lblthaotac.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblmotaThaoTac = new JLabel(
                "Chọn thao tác phù hợp với trạng thái ca làm việc."
        );
        lblmotaThaoTac.setFont(FONT_NHAN);
        lblmotaThaoTac.setForeground(MAU_PHU);
        lblmotaThaoTac.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel pNut = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 12, 0)
        );
        pNut.setBackground(Color.WHITE);
        pNut.setAlignmentX(Component.LEFT_ALIGNMENT);

        moca = new JButton("Mở ca làm việc");
        dongca = new JButton("Đóng ca hiện tại");

        taoNutHanhDong(moca, MAU_XANH, MAU_TEXT);
        taoNutHanhDong(
                dongca,
                new Color(254, 226, 226),
                MAU_DO
        );
        pNut.add(moca);
        pNut.add(dongca);
        card2.add(lblthaotac);
        card2.add(Box.createVerticalStrut(10));
        card2.add(lblmotaThaoTac);
        card2.add(Box.createVerticalStrut(22));
        card2.add(pNut);

        pCenter.add(card2);

        add(pCenter, BorderLayout.CENTER);


        trangchu.addActionListener(this);
        qlca.addActionListener(this);
        dsphieusuachua.addActionListener(this);
        quanlycongno.addActionListener(this);
        baocaodoanhthu.addActionListener(this);
        quaylai.addActionListener(this);

        chinhmau();
    }


    private JButton taoNutMenu(String text) {
        JButton btn = new JButton(text);

        btn.setFont(FONT_NHAN);
        btn.setForeground(MAU_CHU_SIDEBAR);
        btn.setBackground(MAU_SIDEBAR);

        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setPreferredSize(new Dimension(200, 45));

        btn.setBorder(new EmptyBorder(0, 14, 0, 8));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    // ==================== TẠO NHÃN THÔNG TIN ====================

    private JLabel taoLabelThongTin(String ten, String giaTri) {
        JLabel lbl = new JLabel(
                "<html><span style='color:#6B7280'>"
                        + ten
                        + "</span><br><b>"
                        + giaTri
                        + "</b></html>"
        );

        lbl.setFont(FONT_NHAN);
        lbl.setForeground(MAU_TEXT);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        return lbl;
    }

    private void taoNutHanhDong(
            JButton btn,
            Color nen,
            Color chu
    ) {
        btn.setFont(FONT_NHAN_DAM);
        btn.setForeground(chu);
        btn.setBackground(nen);

        btn.setPreferredSize(new Dimension(190, 45));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void chinhmau() {

        getContentPane().setBackground(MAU_NEN);

        pNorth.setBackground(MAU_NEN);
        pWest.setBackground(MAU_SIDEBAR);
        pCenter.setBackground(MAU_NEN);

        card1.setBackground(Color.WHITE);
        card2.setBackground(Color.WHITE);

        lbltieude.setForeground(MAU_TEXT);
        lblmenu.setForeground(MAU_CHU_SIDEBAR);
        tieude.setForeground(MAU_TEXT);

        dangmo.setForeground(MAU_XANH_LA);
        nhanvien.setForeground(MAU_TEXT);
        giomo.setForeground(MAU_TEXT);
        sotien.setForeground(MAU_TEXT);

        JButton[] dsNutMenu = {
                trangchu,
                qlca,
                dsphieusuachua,
                quanlycongno,
                baocaodoanhthu,
                quaylai
        };

        for (JButton btn : dsNutMenu) {
            btn.setBackground(MAU_SIDEBAR);
            btn.setForeground(MAU_CHU_SIDEBAR);
            btn.setFont(FONT_NHAN);
        }


        qlca.setBackground(MAU_SIDEBAR_CHON);
        qlca.setForeground(MAU_CHU_SIDEBAR);
        qlca.setFont(FONT_NHAN_DAM);


        moca.setBackground(MAU_XANH);
        moca.setForeground(MAU_TEXT);
        moca.setFont(FONT_NHAN_DAM);


        dongca.setBackground(new Color(254, 226, 226));
        dongca.setForeground(MAU_DO);
        dongca.setFont(FONT_NHAN_DAM);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManHinhQuanLyCa UI = new ManHinhQuanLyCa();
            UI.setVisible(true);
        });
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        Object o = e.getSource();

        if (o == trangchu || o == quaylai) {

            ManHinhTrangChuKeToan UI =
                    new ManHinhTrangChuKeToan();

            UI.setVisible(true);
            dispose();

        } else if (o == qlca) {



        } else if (o == dsphieusuachua) {

            ManHinhDanhSachPhieuSuaChuaDaHoanThanh UI =
                    new ManHinhDanhSachPhieuSuaChuaDaHoanThanh();

            UI.setVisible(true);
            dispose();

        } else if (o == quanlycongno) {

            ManHinhDanhSachKHNo UI =
                    new ManHinhDanhSachKHNo();

            UI.setVisible(true);
            dispose();

        } else if (o == baocaodoanhthu) {

            JOptionPane.showMessageDialog(
                    this,
                    "Chức năng báo cáo doanh thu chưa được kết nối.",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}