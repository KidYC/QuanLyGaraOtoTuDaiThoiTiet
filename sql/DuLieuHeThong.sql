-- ==========================================================
-- HỆ THỐNG QUẢN LÝ GARA Ô TÔ TỨ ĐẠI THỜI TIẾT
-- ==========================================================
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'QuanLyGaraOToTuDaiThoiTiet')
BEGIN
    CREATE DATABASE QuanLyGaraOToTuDaiThoiTiet;
END
GO

USE QuanLyGaraOToTuDaiThoiTiet;
GO

-- ==========================================================
-- XÓA BẢNG CŨ (NẾU CÓ) ĐỂ TRÁNH LỖI CONFLICT
-- ==========================================================
IF OBJECT_ID('CHI_TIET_HOA_DON', 'U') IS NOT NULL DROP TABLE CHI_TIET_HOA_DON;
IF OBJECT_ID('HOA_DON', 'U') IS NOT NULL DROP TABLE HOA_DON;
IF OBJECT_ID('CHI_TIET_DICH_VU', 'U') IS NOT NULL DROP TABLE CHI_TIET_DICH_VU;
IF OBJECT_ID('DANH_SACH_PHU_TUNG', 'U') IS NOT NULL DROP TABLE DANH_SACH_PHU_TUNG;
IF OBJECT_ID('CHI_TIET_PHIEU_SUA_CHUA', 'U') IS NOT NULL DROP TABLE CHI_TIET_PHIEU_SUA_CHUA;
IF OBJECT_ID('PHIEU_SUA_CHUA', 'U') IS NOT NULL DROP TABLE PHIEU_SUA_CHUA;
IF OBJECT_ID('PHIEU_TIEP_NHAN', 'U') IS NOT NULL DROP TABLE PHIEU_TIEP_NHAN;
IF OBJECT_ID('danhsachKHno', 'U') IS NOT NULL DROP TABLE danhsachKHno;
IF OBJECT_ID('XE', 'U') IS NOT NULL DROP TABLE XE;
IF OBJECT_ID('KHACH_HANG', 'U') IS NOT NULL DROP TABLE KHACH_HANG;
IF OBJECT_ID('CHI_TIET_KIEM_KE', 'U') IS NOT NULL DROP TABLE CHI_TIET_KIEM_KE;
IF OBJECT_ID('PHIEU_KIEM_KE', 'U') IS NOT NULL DROP TABLE PHIEU_KIEM_KE;
IF OBJECT_ID('CHI_TIET_PHIEU_NHAP', 'U') IS NOT NULL DROP TABLE CHI_TIET_PHIEU_NHAP;
IF OBJECT_ID('PHIEU_NHAP_KHO', 'U') IS NOT NULL DROP TABLE PHIEU_NHAP_KHO;
IF OBJECT_ID('CANH_BAO_TON_KHO', 'U') IS NOT NULL DROP TABLE CANH_BAO_TON_KHO;
IF OBJECT_ID('PHU_TUNG', 'U') IS NOT NULL DROP TABLE PHU_TUNG;
IF OBJECT_ID('KHO', 'U') IS NOT NULL DROP TABLE KHO;
IF OBJECT_ID('NHA_CUNG_CAP', 'U') IS NOT NULL DROP TABLE NHA_CUNG_CAP;
IF OBJECT_ID('TAI_KHOAN', 'U') IS NOT NULL DROP TABLE TAI_KHOAN;
IF OBJECT_ID('THU_KHO', 'U') IS NOT NULL DROP TABLE THU_KHO;
IF OBJECT_ID('KE_TOAN', 'U') IS NOT NULL DROP TABLE KE_TOAN;
IF OBJECT_ID('KY_THUAT_VIEN', 'U') IS NOT NULL DROP TABLE KY_THUAT_VIEN;
IF OBJECT_ID('LE_TAN', 'U') IS NOT NULL DROP TABLE LE_TAN;
IF OBJECT_ID('QUAN_LY', 'U') IS NOT NULL DROP TABLE QUAN_LY;
IF OBJECT_ID('PHUONG_THUC_THANH_TOAN', 'U') IS NOT NULL DROP TABLE PHUONG_THUC_THANH_TOAN;
GO

-- ==========================================================
-- 1. TẠO CÁC BẢNG DANH MỤC ĐỘC LẬP
-- ==========================================================
CREATE TABLE KHACH_HANG (
                            maKhachHang VARCHAR(20) PRIMARY KEY,
                            hoTen NVARCHAR(100) NOT NULL,
                            soDienThoai VARCHAR(15),
                            diaChi NVARCHAR(200),
                            email NVARCHAR(50),
                            bienSo VARCHAR(20)
);

CREATE TABLE XE (
                    bienSo VARCHAR(20) PRIMARY KEY,
                    hangXe NVARCHAR(50),
                    dongXe NVARCHAR(50),
                    namSanXuat INT,
                    maKhachHang VARCHAR(20),
                    FOREIGN KEY (maKhachHang) REFERENCES KHACH_HANG(maKhachHang) ON DELETE CASCADE
);

CREATE TABLE KY_THUAT_VIEN (
                               maKTV VARCHAR(20) PRIMARY KEY,
                               hoTen NVARCHAR(100) NOT NULL,
                               diaChi NVARCHAR(200),
                               ngayVaoLam DATETIME DEFAULT GETDATE(),
                               trangThai NVARCHAR(50)
);

CREATE TABLE KE_TOAN (
                         maNhanVien VARCHAR(20) PRIMARY KEY,
                         hoTen NVARCHAR(100) NOT NULL,
                         soDienThoai VARCHAR(15)
);

CREATE TABLE THU_KHO (
                         maNhanVien VARCHAR(20) PRIMARY KEY,
                         hoTen NVARCHAR(100) NOT NULL,
                         soDienThoai VARCHAR(15)
);

CREATE TABLE LE_TAN (
                        maNhanVien VARCHAR(20) PRIMARY KEY,
                        hoTen NVARCHAR(100) NOT NULL,
                        soDienThoai VARCHAR(15)
);

CREATE TABLE QUAN_LY (
                         maNhanVien VARCHAR(20) PRIMARY KEY,
                         hoTen NVARCHAR(100) NOT NULL,
                         soDienThoai VARCHAR(15)
);

CREATE TABLE TAI_KHOAN (
                           maTaiKhoan VARCHAR(20) PRIMARY KEY,
                           tenDangNhap VARCHAR(50) NOT NULL UNIQUE,
                           matKhau VARCHAR(100) NOT NULL,
                           loaiTaiKhoan NVARCHAR(50),
                           maNhanVien VARCHAR(20)
);

CREATE TABLE PHUONG_THUC_THANH_TOAN (
                                        maPhuongThuc VARCHAR(20) PRIMARY KEY,
                                        tenPhuongThuc NVARCHAR(50) NOT NULL
);

CREATE TABLE DICH_VU (
                         maDichVu VARCHAR(20) PRIMARY KEY,
                         tenDichVu NVARCHAR(100) NOT NULL,
                         donGiaHienTai DECIMAL(18, 2),
                         moTa NVARCHAR(MAX),
                         thoiGianBaoHanh INT
);

CREATE TABLE KHO (
                     maKho VARCHAR(20) PRIMARY KEY,
                     tenKho NVARCHAR(100) NOT NULL,
                     diaChi NVARCHAR(200)
);

CREATE TABLE NHA_CUNG_CAP (
                              maNhaCungCap VARCHAR(20) PRIMARY KEY,
                              tenNhaCungCap NVARCHAR(100) NOT NULL,
                              soDienThoai VARCHAR(15),
                              diaChi NVARCHAR(200),
                              email VARCHAR(100)
);

CREATE TABLE PHU_TUNG (
                          maPhuTung VARCHAR(20) PRIMARY KEY,
                          tenPhuTung NVARCHAR(100) NOT NULL,
                          donViTinh NVARCHAR(20),
                          donGiaNhap DECIMAL(18, 2),
                          donGiaBan DECIMAL(18, 2),
                          soLuongTon INT,
                          maKho VARCHAR(20),
                          FOREIGN KEY (maKho) REFERENCES KHO(maKho)
);

-- ==========================================================
-- 2. CÁC BẢNG NGHIỆP VỤ (PHIẾU & HÓA ĐƠN)
-- ==========================================================
CREATE TABLE PHIEU_TIEP_NHAN (
                                 maPhieuTiepNhan VARCHAR(20) PRIMARY KEY,
                                 ngayTiepNhan DATETIME DEFAULT GETDATE(),
                                 tinhTrangXe NVARCHAR(MAX),
                                 yeuCauKH NVARCHAR(MAX),
                                 trangThai NVARCHAR(50),
                                 bienSo VARCHAR(20),
                                 maNhanVien VARCHAR(20), -- Lưu thông tin Lễ tân lập phiếu
                                 FOREIGN KEY (bienSo) REFERENCES XE(bienSo),
                                 FOREIGN KEY (maNhanVien) REFERENCES LE_TAN(maNhanVien)
);

CREATE TABLE PHIEU_SUA_CHUA (
                                maPhieuSuaChua VARCHAR(20) PRIMARY KEY,
                                ngayLap DATETIME DEFAULT GETDATE(),
                                ngayIn DATETIME,
                                trangThai NVARCHAR(50),
                                bienSo VARCHAR(20),
                                maKTV VARCHAR(20),
                                maPhieuTiepNhan VARCHAR(20),
                                FOREIGN KEY (bienSo) REFERENCES XE(bienSo),
                                FOREIGN KEY (maKTV) REFERENCES KY_THUAT_VIEN(maKTV),
                                FOREIGN KEY (maPhieuTiepNhan) REFERENCES PHIEU_TIEP_NHAN(maPhieuTiepNhan)
);

CREATE TABLE CHI_TIET_PHIEU_SUA_CHUA (
                                         maPhieuSuaChua VARCHAR(20),
                                         maPhuTung VARCHAR(20),
                                         soLuong INT,
                                         donGia DECIMAL(18, 2),
                                         PRIMARY KEY (maPhieuSuaChua, maPhuTung),
                                         FOREIGN KEY (maPhieuSuaChua) REFERENCES PHIEU_SUA_CHUA(maPhieuSuaChua) ON DELETE CASCADE,
                                         FOREIGN KEY (maPhuTung) REFERENCES PHU_TUNG(maPhuTung)
);

CREATE TABLE DANH_SACH_PHU_TUNG (
                                    maPhieuSuaChua VARCHAR(20),
                                    maPhuTung VARCHAR(20),
                                    soLuongPhuTung INT,
                                    donGiaBan DECIMAL(18, 2),
                                    PRIMARY KEY (maPhieuSuaChua, maPhuTung),
                                    FOREIGN KEY (maPhieuSuaChua) REFERENCES PHIEU_SUA_CHUA(maPhieuSuaChua) ON DELETE CASCADE,
                                    FOREIGN KEY (maPhuTung) REFERENCES PHU_TUNG(maPhuTung)
);

CREATE TABLE CHI_TIET_DICH_VU (
                                  maPhieuSuaChua VARCHAR(20),
                                  maDichVu VARCHAR(20),
                                  soLuong INT,
                                  donGia DECIMAL(18, 2),
                                  thanhTien AS (soLuong * donGia),
                                  PRIMARY KEY (maPhieuSuaChua, maDichVu),
                                  FOREIGN KEY (maPhieuSuaChua) REFERENCES PHIEU_SUA_CHUA(maPhieuSuaChua) ON DELETE CASCADE,
                                  FOREIGN KEY (maDichVu) REFERENCES DICH_VU(maDichVu)
);

CREATE TABLE HOA_DON (
                         maHoaDon VARCHAR(20) PRIMARY KEY,
                         ngayLap DATETIME DEFAULT GETDATE(),
                         maPhuongThuc VARCHAR(20),
                         maPhieuSuaChua VARCHAR(20),
                         maNhanVien VARCHAR(20),
                         FOREIGN KEY (maPhuongThuc) REFERENCES PHUONG_THUC_THANH_TOAN(maPhuongThuc),
                         FOREIGN KEY (maPhieuSuaChua) REFERENCES PHIEU_SUA_CHUA(maPhieuSuaChua),
                         FOREIGN KEY (maNhanVien) REFERENCES KE_TOAN(maNhanVien)
);

CREATE TABLE CHI_TIET_HOA_DON (
                                  maChiTietHD VARCHAR(20) PRIMARY KEY,
                                  maHD VARCHAR(20),
                                  noiDung NVARCHAR(MAX),
                                  soLuong INT,
                                  dongia DECIMAL(18, 2),
                                  FOREIGN KEY (maHD) REFERENCES HOA_DON(maHoaDon) ON DELETE CASCADE
);

-- ==========================================================
-- 3. CÁC BẢNG QUẢN LÝ KHO & KHÁCH HÀNG NỢ
-- ==========================================================
CREATE TABLE PHIEU_NHAP_KHO (
                                maPhieuNhap VARCHAR(20) PRIMARY KEY,
                                ngayNhap DATETIME DEFAULT GETDATE(),
                                tongTienNhap DECIMAL(18, 2),
                                maNhanVien VARCHAR(20),
                                maNhaCungCap VARCHAR(20),
                                FOREIGN KEY (maNhanVien) REFERENCES THU_KHO(maNhanVien),
                                FOREIGN KEY (maNhaCungCap) REFERENCES NHA_CUNG_CAP(maNhaCungCap)
);

CREATE TABLE CHI_TIET_PHIEU_NHAP (
                                     maPhieuNhap VARCHAR(20),
                                     maPhuTung VARCHAR(20),
                                     soLuongNhap INT,
                                     donGiaNhap DECIMAL(18, 2),
                                     thanhTien DECIMAL(18, 2),
                                     PRIMARY KEY (maPhieuNhap, maPhuTung),
                                     FOREIGN KEY (maPhieuNhap) REFERENCES PHIEU_NHAP_KHO(maPhieuNhap) ON DELETE CASCADE,
                                     FOREIGN KEY (maPhuTung) REFERENCES PHU_TUNG(maPhuTung)
);

CREATE TABLE CANH_BAO_TON_KHO (
                                  maCanhBao VARCHAR(20) PRIMARY KEY,
                                  maPhuTung VARCHAR(20),
                                  ngayCanhBao DATETIME DEFAULT GETDATE(),
                                  loaiCanhBao NVARCHAR(50),
                                  trangThaiDaXem BIT,
                                  FOREIGN KEY (maPhuTung) REFERENCES PHU_TUNG(maPhuTung)
);

CREATE TABLE PHIEU_KIEM_KE (
                               maPhieuKiemKe VARCHAR(20) PRIMARY KEY,
                               ngayKiemKe DATETIME DEFAULT GETDATE(),
                               trangThai NVARCHAR(50),
                               maNhanVien VARCHAR(20),
                               FOREIGN KEY (maNhanVien) REFERENCES THU_KHO(maNhanVien)
);

CREATE TABLE CHI_TIET_KIEM_KE (
                                  maPhieuKiemKe VARCHAR(20),
                                  maPhuTung VARCHAR(20),
                                  soLuongHeThong INT,
                                  soLuongThucTe INT,
                                  chenhLech INT,
                                  lyDoChenhLech NVARCHAR(MAX),
                                  PRIMARY KEY (maPhieuKiemKe, maPhuTung),
                                  FOREIGN KEY (maPhieuKiemKe) REFERENCES PHIEU_KIEM_KE(maPhieuKiemKe) ON DELETE CASCADE,
                                  FOREIGN KEY (maPhuTung) REFERENCES PHU_TUNG(maPhuTung)
);

CREATE TABLE danhsachKHno (
                              maKhachHang VARCHAR(20) PRIMARY KEY,
                              hoTen NVARCHAR(50),
                              soDienThoai NVARCHAR(15),
                              hanMucNo NVARCHAR(30),
                              tongNo NVARCHAR(30),
                              trangThai NVARCHAR(50),
                              FOREIGN KEY (maKhachHang) REFERENCES KHACH_HANG(maKhachHang) ON DELETE CASCADE
);
GO

-- ==========================================================
-- 4. INSERT DỮ LIỆU NHÂN SỰ & TÀI KHOẢN (ĐÚNG YÊU CẦU)
-- ==========================================================
INSERT INTO THU_KHO (maNhanVien, hoTen, soDienThoai) VALUES
('TK01', N'Nguyễn Quang Huy', '0912345678');

INSERT INTO KE_TOAN (maNhanVien, hoTen, soDienThoai) VALUES
    ('KT01', N'Lê Duy Minh', '0923456789');

INSERT INTO KY_THUAT_VIEN (maKTV, hoTen, diaChi, trangThai) VALUES
                                                                ('KTV01', N'Phan Xuân Phụng', N'TP.HCM', N'Đang rảnh'),
                                                                ('KTV02', N'Trần Đức Mạnh', N'Gò Vấp', N'Đang bận sửa xe');

INSERT INTO LE_TAN (maNhanVien, hoTen, soDienThoai) VALUES
    ('LT01', N'Trần Thanh Liêm', '0934567890');

INSERT INTO QUAN_LY (maNhanVien, hoTen, soDienThoai) VALUES
    ('QL01', N'Nguyễn Văn Hùng', '0901111111');

INSERT INTO TAI_KHOAN (maTaiKhoan, tenDangNhap, matKhau, loaiTaiKhoan, maNhanVien) VALUES
                                                                                       ('ACC01', 'admin', '123', N'Quản trị viên', 'QL01'),
                                                                                       ('ACC02', 'huy0804', '123456', N'Thủ kho', 'TK01'),
                                                                                       ('ACC03', 'minh0806', '123456', N'Kế toán', 'KT01'),
                                                                                       ('ACC04', 'phung2203', '123456', N'Kỹ thuật viên', 'KTV01'),
                                                                                       ('ACC05', 'liem1507', '123456', N'Lễ tân', 'LT01');

-- ==========================================================
-- 5. INSERT DỮ LIỆU ĐA DẠNG CHO DASHBOARD
-- ==========================================================
INSERT INTO KHACH_HANG (maKhachHang, hoTen, soDienThoai, diaChi, email, bienSo) VALUES
                                                                                    ('KH01', N'Nguyễn Văn A', '0901234567', N'Quận 1, TP.HCM','nguyenvanA@gmail.com','51G-123.45'),
                                                                                    ('KH02', N'Trần Thị B', '0912345678', N'TP. Thủ Đức','tranthiB@gmail.com','60B-987.65'),
                                                                                    ('KH03', N'Lê Văn C', '0987654321', N'Quận 3, TP.HCM','levanC@gmail.com','51K-555.88');

INSERT INTO danhsachKHno (maKhachHang, hoTen, soDienThoai, hanMucNo, tongNo, trangThai) VALUES
                                                                                            ('KH01', N'Nguyễn Văn A', '0901234567','100.000.000','30.000.000',N'Chưa vượt mức'),
                                                                                            ('KH02', N'Trần Thị B', '0912345678','200.000.000','10.000.000',N'Chưa vượt mức'),
                                                                                            ('KH03', N'Lê Văn C', '0987654321','50.000.000','2.000.000',N'Chưa vượt mức');

INSERT INTO XE (bienSo, hangXe, dongXe, namSanXuat, maKhachHang) VALUES
                                                                     ('51G-123.45', 'Toyota', 'Vios', 2022, 'KH01'),
                                                                     ('60B-987.65', 'Mazda', 'Mazda 3', 2023, 'KH02'),
                                                                     ('51K-555.88', 'Honda', 'Civic', 2021, 'KH03');

INSERT INTO PHUONG_THUC_THANH_TOAN (maPhuongThuc, tenPhuongThuc) VALUES
                                                                     ('PT01', N'Tiền mặt'),
                                                                     ('PT02', N'Chuyển khoản ngân hàng'),
                                                                     ('PT03', N'Thẻ tín dụng / Thẻ ATM');

INSERT INTO DICH_VU (maDichVu, tenDichVu, donGiaHienTai, moTa, thoiGianBaoHanh) VALUES
                                                                                    ('DV01', N'Bảo dưỡng định kỳ 5000km', 350000, N'Thay nhớt, kiểm tra tổng quát', 3),
                                                                                    ('DV02', N'Thay má phanh trước', 500000, N'Thay thế bộ má phanh chính hãng', 6),
                                                                                    ('DV03', N'Cân chỉnh độ chụm bánh xe', 400000, N'Sử dụng máy điện tử cân chỉnh', 1);

INSERT INTO KHO (maKho, tenKho, diaChi) VALUES
                                            ('KHO01', N'Kho Phụ Tùng Quận 1', N'10 Võ Văn Kiệt, Quận 1, TP.HCM'),
                                            ('KHO02', N'Kho Vật Tư Tân Phú', N'50 Luỹ Bán Bích, Tân Phú, TP.HCM');

INSERT INTO NHA_CUNG_CAP (maNhaCungCap, tenNhaCungCap, soDienThoai, diaChi, email) VALUES
                                                                                       ('NCC01', N'CTY Phụ Tùng Ô Tô Sài Gòn', '02838222333', N'12 Lý Tự Trọng, Quận 1', 'contact@ptsaigon.vn'),
                                                                                       ('NCC02', N'Toyota Vietnam Distributor', '02839998888', N'KCN Biên Hòa 2', 'support@toyotavn.com');

INSERT INTO PHU_TUNG (maPhuTung, tenPhuTung, donViTinh, donGiaNhap, donGiaBan, soLuongTon, maKho) VALUES
                                                                                                      ('PT01', N'Nhớt động cơ Castrol 4L', N'Bình', 800000, 1000000, 45, 'KHO01'),
                                                                                                      ('PT02', N'Má phanh trước Toyota Vios', N'Bộ', 350000, 500000, 15, 'KHO01'),
                                                                                                      ('PT03', N'Lọc gió điều hòa Mazda', N'Cái', 120000, 200000, 30, 'KHO02'),
                                                                                                      ('PT04', N'Bơm nước rửa kính', N'Cái', 200000, 350000, 2, 'KHO01'); -- Sắp hết hàng để test cảnh báo

-- Tạo dữ liệu rải đều các tháng để test Biểu Đồ Thống Kê Doanh Thu
INSERT INTO PHIEU_TIEP_NHAN (maPhieuTiepNhan, ngayTiepNhan, tinhTrangXe, trangThai, bienSo, maNhanVien) VALUES
                                                                                                            ('PTN01', '2026-02-10 08:30:00', N'Bảo dưỡng định kỳ', N'Đã thanh toán', '51G-123.45', 'LT01'),
                                                                                                            ('PTN02', '2026-05-15 09:15:00', N'Thay má phanh trước', N'Đã thanh toán', '60B-987.65', 'LT01'),
                                                                                                            ('PTN03', '2026-10-01 10:00:00', N'Sửa điện', N'Chờ sửa chữa', '51K-555.88', 'LT01');

INSERT INTO PHIEU_SUA_CHUA (maPhieuSuaChua, ngayLap, trangThai, bienSo, maKTV, maPhieuTiepNhan) VALUES
                                                                                                    ('PSC01', '2026-02-10 10:00:00', N'Hoàn thành', '51G-123.45', 'KTV01', 'PTN01'),
                                                                                                    ('PSC02', '2026-05-15 11:30:00', N'Hoàn thành', '60B-987.65', 'KTV02', 'PTN02');

INSERT INTO CHI_TIET_DICH_VU (maPhieuSuaChua, maDichVu, soLuong, donGia) VALUES
                                                                             ('PSC01', 'DV01', 1, 350000),
                                                                             ('PSC02', 'DV02', 1, 500000);

INSERT INTO HOA_DON (maHoaDon, ngayLap, maPhuongThuc, maPhieuSuaChua, maNhanVien) VALUES
                                                                                      ('HD01', '2026-02-10 11:45:00', 'PT01', 'PSC01', 'KT01'),
                                                                                      ('HD02', '2026-05-15 14:00:00', 'PT02', 'PSC02', 'KT01');

INSERT INTO CHI_TIET_HOA_DON (maChiTietHD, maHD, noiDung, soLuong, dongia) VALUES
                                                                               ('CTHD01', 'HD01', N'Dịch vụ bảo dưỡng tháng 2', 1, 1350000),
                                                                               ('CTHD02', 'HD02', N'Sửa chữa phanh tháng 5', 1, 2500000);
GO

-- ==========================================================
-- 6. VIEWS & PROCEDURES HỖ TRỢ TRANG CHỦ & BIỂU ĐỒ
-- ==========================================================

-- 6.1 View Lấy KPI Tổng Quan 
CREATE VIEW v_Dashboard_KPI AS
SELECT
    (SELECT COUNT(*) FROM PHIEU_TIEP_NHAN WHERE trangThai IN (N'Chờ sửa chữa', N'Đang xử lý')) AS SoXeDangXuLy,
    (SELECT COUNT(*) FROM HOA_DON WHERE MONTH(ngayLap) = MONTH(GETDATE()) AND YEAR(ngayLap) = YEAR(GETDATE())) AS SoHoaDonThangNay,
    (SELECT ISNULL(SUM(soLuong * dongia), 0) FROM CHI_TIET_HOA_DON HD JOIN HOA_DON H ON HD.maHD = H.maHoaDon WHERE MONTH(H.ngayLap) = MONTH(GETDATE()) AND YEAR(H.ngayLap) = YEAR(GETDATE())) AS DoanhThuThangNay,
    (SELECT COUNT(*) FROM PHU_TUNG WHERE soLuongTon <= 5) AS SoPhuTungSapHet;
GO

-- 6.2 View Lấy Phụ Tùng Cảnh Báo 
CREATE VIEW v_Dashboard_CanhBaoKho AS
SELECT
    maPhuTung,
    tenPhuTung,
    soLuongTon,
    CASE
        WHEN soLuongTon = 0 THEN N'Hết hàng'
        ELSE N'Sắp hết'
        END AS MucDoCanhBao
FROM PHU_TUNG
WHERE soLuongTon <= 5;
GO

-- 6.3 Stored Procedure Lấy Doanh Thu 12 Tháng (Dùng cho Biểu Đồ Cột)
CREATE PROCEDURE sp_ThongKeDoanhThu12Thang
    @Nam INT
AS
BEGIN
    SET NOCOUNT ON;
SELECT
    Thang.Thang,
    ISNULL(SUM(CT.soLuong * CT.dongia), 0) AS TongDoanhThu
FROM (
         SELECT 1 AS Thang UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6
         UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12
     ) AS Thang
         LEFT JOIN HOA_DON HD ON MONTH(HD.ngayLap) = Thang.Thang AND YEAR(HD.ngayLap) = @Nam
    LEFT JOIN CHI_TIET_HOA_DON CT ON HD.maHoaDon = CT.maHD
GROUP BY Thang.Thang
ORDER BY Thang.Thang;
END;
GO