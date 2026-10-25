-- ==========================================================
-- HỆ THỐNG QUẢN LÝ GARA Ô TÔ TỨ ĐẠI THỜI TIẾT
-- SCRIPT TẠO DATABASE HOÀN CHỈNH 100% (KHỚP DAO JAVA)
-- ==========================================================

USE master;
GO

IF EXISTS (SELECT * FROM sys.databases WHERE name = 'QuanLyGaraOToTuDaiThoiTiet')
BEGIN
    ALTER DATABASE QuanLyGaraOToTuDaiThoiTiet SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE QuanLyGaraOToTuDaiThoiTiet;
END
GO

CREATE DATABASE QuanLyGaraOToTuDaiThoiTiet;
GO

USE QuanLyGaraOToTuDaiThoiTiet;
GO

-- ==========================================================
-- PHẦN 1: TẠO CÁC BẢNG (DDL)
-- ==========================================================

-- 1. Bảng Khách Hàng
CREATE TABLE KHACH_HANG (
    maKhachHang VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    soDienThoai VARCHAR(15),
    diaChi NVARCHAR(200),
    email NVARCHAR(50),
    bienSo VARCHAR(20)
);
GO

-- 2. Bảng Xe
CREATE TABLE XE (
    bienSo VARCHAR(20) PRIMARY KEY,
    hangXe NVARCHAR(50),
    dongXe NVARCHAR(50),
    namSanXuat INT,
    maKhachHang VARCHAR(20),
    FOREIGN KEY (maKhachHang) REFERENCES KHACH_HANG(maKhachHang) ON DELETE CASCADE
);
GO

-- 3. Bảng Kỹ Thuật Viên
CREATE TABLE KY_THUAT_VIEN (
    maKTV VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    diaChi NVARCHAR(200),
    ngayVaoLam DATETIME,
    trangThai NVARCHAR(50)
);
GO 

-- 4. Bảng Kế Toán
CREATE TABLE KE_TOAN (
    maNhanVien VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    soDienThoai VARCHAR(15)
);
GO

-- 5. Bảng Thủ Kho
CREATE TABLE THU_KHO (
    maNhanVien VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    soDienThoai VARCHAR(15),
    tenDangNhap VARCHAR(50) UNIQUE,
    matKhau VARCHAR(100)
);
GO

-- 6. Bảng Tài Khoản (Dùng chung cho hệ thống)
CREATE TABLE TAI_KHOAN (
    maTaiKhoan VARCHAR(20) PRIMARY KEY,
    tenDangNhap VARCHAR(50) NOT NULL UNIQUE,
    matKhau VARCHAR(100) NOT NULL,
    loaiTaiKhoan NVARCHAR(50), 
    maNhanVien VARCHAR(20)     
);
GO

-- 7. Bảng Phương Thức Thanh Toán
CREATE TABLE PHUONG_THUC_THANH_TOAN (
    maPhuongThuc VARCHAR(20) PRIMARY KEY,
    tenPhuongThuc NVARCHAR(50) NOT NULL
);
GO

-- 8. Bảng Dịch Vụ
CREATE TABLE DICH_VU (
    maDichVu VARCHAR(20) PRIMARY KEY,
    tenDichVu NVARCHAR(100) NOT NULL,
    donGiaHienTai DECIMAL(18, 2),
    moTa NVARCHAR(MAX),
    thoiGianBaoHanh INT 
);
GO

-- 9. Bảng Kho
CREATE TABLE KHO (
    maKho VARCHAR(20) PRIMARY KEY,
    tenKho NVARCHAR(100) NOT NULL,
    diaChi NVARCHAR(200)
);
GO

-- 10. Bảng Nhà Cung Cấp
CREATE TABLE NHA_CUNG_CAP (
    maNhaCungCap VARCHAR(20) PRIMARY KEY,
    tenNhaCungCap NVARCHAR(100) NOT NULL,
    soDienThoai VARCHAR(15),
    diaChi NVARCHAR(200),
    email VARCHAR(100)
);
GO

-- 11. Bảng Phụ Tùng
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
GO

-- 12. Bảng Phiếu Tiếp Nhận
CREATE TABLE PHIEU_TIEP_NHAN (
    maPhieuTiepNhan VARCHAR(20) PRIMARY KEY,
    ngayTiepNhan DATETIME DEFAULT GETDATE(),
    tinhTrangXe NVARCHAR(MAX),
    yeuCauKH NVARCHAR(MAX),
    trangThai NVARCHAR(50),
    bienSo VARCHAR(20),
    FOREIGN KEY (bienSo) REFERENCES XE(bienSo)
);
GO

-- 13. Bảng Phiếu Sửa Chữa
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
GO

-- 14. Bảng Chi Tiết Phiếu Sửa Chữa (Dành cho ThuKho_DAO)
CREATE TABLE CHI_TIET_PHIEU_SUA_CHUA (
    maPhieuSuaChua VARCHAR(20),
    maPhuTung VARCHAR(20),
    soLuong INT,
    donGia DECIMAL(18, 2),
    PRIMARY KEY (maPhieuSuaChua, maPhuTung),
    FOREIGN KEY (maPhieuSuaChua) REFERENCES PHIEU_SUA_CHUA(maPhieuSuaChua) ON DELETE CASCADE,
    FOREIGN KEY (maPhuTung) REFERENCES PHU_TUNG(maPhuTung)
);
GO

-- 15. Bảng Danh Sách Phụ Tùng (Dành cho DanhSachPhuTung_DAO_2)
CREATE TABLE DANH_SACH_PHU_TUNG (
    maPhieuSuaChua VARCHAR(20),
    maPhuTung VARCHAR(20),
    soLuongPhuTung INT,
    donGiaBan DECIMAL(18, 2),
    PRIMARY KEY (maPhieuSuaChua, maPhuTung),
    FOREIGN KEY (maPhieuSuaChua) REFERENCES PHIEU_SUA_CHUA(maPhieuSuaChua) ON DELETE CASCADE,
    FOREIGN KEY (maPhuTung) REFERENCES PHU_TUNG(maPhuTung)
);
GO

-- 16. Bảng Chi Tiết Dịch Vụ (Đã cấu hình thanhTien tự động tính để tránh lỗi code DAO)
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
GO

-- 17. Bảng Hóa Đơn
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
GO

-- 18. Bảng Chi Tiết Hóa Đơn
CREATE TABLE CHI_TIET_HOA_DON (
    maChiTietHD VARCHAR(20) PRIMARY KEY,
    maHD VARCHAR(20),
    noiDung NVARCHAR(MAX),
    soLuong INT,
    dongia DECIMAL(18, 2),
    FOREIGN KEY (maHD) REFERENCES HOA_DON(maHoaDon) ON DELETE CASCADE
);
GO

-- 19. Bảng Phiếu Nhập Kho
CREATE TABLE PHIEU_NHAP_KHO (
    maPhieuNhap VARCHAR(20) PRIMARY KEY,
    ngayNhap DATETIME DEFAULT GETDATE(),
    tongTienNhap DECIMAL(18, 2),
    maNhanVien VARCHAR(20), 
    maNhaCungCap VARCHAR(20),
    FOREIGN KEY (maNhanVien) REFERENCES THU_KHO(maNhanVien),
    FOREIGN KEY (maNhaCungCap) REFERENCES NHA_CUNG_CAP(maNhaCungCap)
);
GO

-- 20. Bảng Chi Tiết Phiếu Nhập
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
GO

-- 21. Bảng Cảnh Báo Tồn Kho
CREATE TABLE CANH_BAO_TON_KHO (
    maCanhBao VARCHAR(20) PRIMARY KEY,
    maPhuTung VARCHAR(20),
    ngayCanhBao DATETIME DEFAULT GETDATE(),
    loaiCanhBao NVARCHAR(50),
    trangThaiDaXem BIT,
    FOREIGN KEY (maPhuTung) REFERENCES PHU_TUNG(maPhuTung)
);
GO

-- 22. Bảng Phiếu Kiểm Kê
CREATE TABLE PHIEU_KIEM_KE (
    maPhieuKiemKe VARCHAR(20) PRIMARY KEY,
    ngayKiemKe DATETIME DEFAULT GETDATE(),
    trangThai NVARCHAR(50),
    maNhanVien VARCHAR(20), 
    FOREIGN KEY (maNhanVien) REFERENCES THU_KHO(maNhanVien)
);
GO

-- 23. Bảng Chi Tiết Kiểm Kê
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
GO

-- 24. Bảng Danh Sách Khách Hàng Nợ
CREATE TABLE danhsachKHno (
    maKhachHang VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(50),
    soDienThoai NVARCHAR(15),
    hanMucNo decimal (18,2),
    tongNo decimal (18,2),
    trangThai NVARCHAR(50),
    FOREIGN KEY (maKhachHang) REFERENCES KHACH_HANG(maKhachHang) ON DELETE CASCADE
);
GO



-- ==========================================================
-- PHẦN 2: THÊM DỮ LIỆU MẪU (DML)
-- ==========================================================

-- 1. Khách Hàng & Nợ
INSERT INTO KHACH_HANG (maKhachHang, hoTen, soDienThoai, diaChi, email, bienSo) VALUES
('KH01', N'Nguyễn Văn A', '0901234567', N'123 Nguyễn Trãi, Quận 1', 'nguyenvanA@gmail.com', '51G-123.45'),
('KH02', N'Trần Thị B', '0912345678', N'456 Lê Văn Việt, TP. Thủ Đức', 'tranthiB@gmail.com', '60B-987.65'),
('KH03', N'Lê Văn C', '0987654321', N'789 CMT8, Quận 3', 'levanC@gmail.com', '51K-555.88'),
('KH04', N'Phạm Minh Dung', '0984444444', N'12 Hoàng Văn Thụ, Tân Bình', 'dung.pham@gmail.com', '51A-888.88'),
('KH05', N'Đặng Quốc Hùng', '0985555555', N'88 Cộng Hòa, Tân Bình', 'hung.dang@gmail.com', '51K-999.99');

INSERT INTO danhsachKHno (maKhachHang, hoTen, soDienThoai, hanMucNo, tongNo, trangThai) VALUES
('KH01', N'Nguyễn Văn A', '0901234567', 100000000,30000000, N'Chưa vượt mức'),
('KH02', N'Trần Thị B', '0912345678', 200000000,10000000, N'Chưa vượt mức'),
('KH03', N'Lê Văn C', '0987654321', 50000000, 2000000, N'Chưa vượt mức');
select * from danhsachKHno

-- 2. Xe
INSERT INTO XE (bienSo, hangXe, dongXe, namSanXuat, maKhachHang) VALUES
('51G-123.45', 'Toyota', 'Vios', 2022, 'KH01'),
('60B-987.65', 'Mazda', 'Mazda 3', 2023, 'KH02'),
('51K-555.88', 'Honda', 'Civic', 2021, 'KH03'),
('51A-888.88', 'Mercedes', 'E300', 2022, 'KH04'),
('51K-999.99', 'Hyundai', 'Tucson', 2023, 'KH05');

-- 3. Nhân Sự (Đầy đủ 5 người: Phụng, Huy, Minh, Liêm và Admin)
INSERT INTO KY_THUAT_VIEN (maKTV, hoTen, diaChi, ngayVaoLam, trangThai) VALUES
('KTV01', N'Phan Xuân Phụng', N'Quận Thủ Đức, TP.HCM', '2023-01-15', N'Đang rảnh'),
('KTV02', N'Nguyễn Hoàng Nam', N'Quận 9, TP.HCM', '2023-05-20', N'Đang bận sửa xe'),
('KTV03', N'Lê Hoàng Long', N'Gò Vấp, TP.HCM', '2024-01-10', N'Đang rảnh');

INSERT INTO KE_TOAN (maNhanVien, hoTen, soDienThoai) VALUES
('KT01', N'Lê Duy Minh', '0934567890'),
('KT02', N'Phạm Thị Dung', '0933111222');

INSERT INTO THU_KHO (maNhanVien, hoTen, soDienThoai, tenDangNhap, matKhau) VALUES
('TK01', N'Nguyễn Quang Huy', '0912345678', 'huy0804', '123456'),
('TK02', N'Lê Duy Minh', '0923456789', 'minh0806', '123456');

-- 4. Tài Khoản (Đăng nhập khớp hoàn toàn với DangNhap.java)
INSERT INTO TAI_KHOAN (maTaiKhoan, tenDangNhap, matKhau, loaiTaiKhoan, maNhanVien) VALUES
('ACC01', 'admin', 'admin123', N'admin', 'ADMIN'),
('ACC02', 'huy0804', '123456', N'Thủ kho', 'TK01'),
('ACC03', 'minh0806', '123456', N'Kế toán', 'KT01'),
('ACC04', 'liem1507', '123456', N'Lễ tân', 'LT01'),
('ACC05', 'phung2203', '123456', N'Kỹ thuật viên', 'KTV01');

-- 5. Phương thức & Dịch vụ & Kho & NCC
INSERT INTO PHUONG_THUC_THANH_TOAN (maPhuongThuc, tenPhuongThuc) VALUES
('PT01', N'Tiền mặt'),
('PT02', N'Chuyển khoản ngân hàng'),
('PT03', N'Thẻ tín dụng / Thẻ ATM');

INSERT INTO DICH_VU (maDichVu, tenDichVu, donGiaHienTai, moTa, thoiGianBaoHanh) VALUES
('DV01', N'Bảo dưỡng định kỳ 5000km', 350000, N'Thay nhớt, kiểm tra tổng quát', 3),
('DV02', N'Thay má phanh trước', 500000, N'Thay thế bộ má phanh chính hãng', 6),
('DV03', N'Cân chỉnh độ chụm bánh xe', 400000, N'Sử dụng máy điện tử cân chỉnh', 1),
('DV04', N'Vệ sinh kim phun siêu âm', 500000, N'Vệ sinh bằng dung dịch', 1);

INSERT INTO KHO (maKho, tenKho, diaChi) VALUES
('KHO01', N'Kho Phụ Tùng Quận 1', N'10 Võ Văn Kiệt, Quận 1, TP.HCM'),
('KHO02', N'Kho Vật Tư Tân Phú', N'50 Luỹ Bán Bích, Tân Phú, TP.HCM');

INSERT INTO NHA_CUNG_CAP (maNhaCungCap, tenNhaCungCap, soDienThoai, diaChi, email) VALUES
('NCC01', N'CTY Phụ Tùng Ô Tô Sài Gòn', '02838222333', N'12 Lý Tự Trọng, Quận 1', 'contact@ptsaigon.vn'),
('NCC02', N'Toyota Vietnam Distributor', '02839998888', N'KCN Biên Hòa 2', 'support@toyotavn.com');

-- 6. Phụ tùng (Cố tình cho PT04 hết hàng để Test Cảnh báo)
INSERT INTO PHU_TUNG (maPhuTung, tenPhuTung, donViTinh, donGiaNhap, donGiaBan, soLuongTon, maKho) VALUES
('PT01', N'Nhớt động cơ Castrol 4L', N'Bình', 800000, 1000000, 45, 'KHO01'),
('PT02', N'Má phanh trước Toyota Vios', N'Bộ', 350000, 500000, 15, 'KHO01'),
('PT03', N'Lọc gió điều hòa Mazda', N'Cái', 120000, 200000, 30, 'KHO02'),
('PT04', N'Bơm cao áp xe Ford', N'Cái', 4500000, 6200000, 0, 'KHO01'); -- Hết hàng

-- 7. Phiếu Tiếp Nhận & Sửa Chữa (Rải đều các tháng cho biểu đồ)
INSERT INTO PHIEU_TIEP_NHAN (maPhieuTiepNhan, ngayTiepNhan, tinhTrangXe, yeuCauKH, trangThai, bienSo) VALUES
('PTN01', '2026-02-15 08:30:00', N'Xe trầy xước nhẹ', N'Bảo dưỡng định kỳ', N'Đã hoàn thành', '51G-123.45'),
('PTN02', '2026-03-20 09:15:00', N'Bình thường', N'Thay má phanh trước', N'Đã hoàn thành', '60B-987.65'),
('PTN03', '2026-05-15 08:00:00', N'Lỗi động cơ', N'Check lỗi máy', N'Đã hoàn thành', '51A-888.88'),
('PTN04', '2026-08-20 09:00:00', N'Bình thường', N'Vệ sinh kim phun', N'Đã hoàn thành', '51K-999.99'),
('PTN05', '2026-10-05 10:00:00', N'Hỏng phanh', N'Kiểm tra và thay phanh', N'Chờ sửa chữa', '51K-555.88'),
('PTN06', '2026-10-24 11:00:00', N'Bình thường', N'Bảo dưỡng', N'Đang xử lý', '51G-123.45');

INSERT INTO PHIEU_SUA_CHUA (maPhieuSuaChua, ngayLap, trangThai, bienSo, maKTV, maPhieuTiepNhan) VALUES
('PSC01', '2026-02-15 09:00:00', N'Hoàn thành', '51G-123.45', 'KTV01', 'PTN01'),
('PSC02', '2026-03-20 10:00:00', N'Hoàn thành', '60B-987.65', 'KTV02', 'PTN02'),
('PSC03', '2026-05-15 09:00:00', N'Hoàn thành', '51A-888.88', 'KTV01', 'PTN03'),
('PSC04', '2026-08-20 10:00:00', N'Hoàn thành', '51K-999.99', 'KTV02', 'PTN04'),
('PSC06', '2026-10-24 11:30:00', N'Đang xử lý', '51G-123.45', 'KTV01', 'PTN06');

-- 8. Chi tiết Sửa Chữa (Dịch vụ & Phụ tùng)
INSERT INTO CHI_TIET_DICH_VU (maPhieuSuaChua, maDichVu, soLuong, donGia) VALUES
('PSC01', 'DV01', 1, 350000),
('PSC02', 'DV02', 1, 500000),
('PSC03', 'DV03', 1, 400000),
('PSC04', 'DV04', 1, 500000);

INSERT INTO DANH_SACH_PHU_TUNG (maPhieuSuaChua, maPhuTung, soLuongPhuTung, donGiaBan) VALUES
('PSC01', 'PT01', 1, 1000000),
('PSC02', 'PT02', 2, 500000),
('PSC03', 'PT03', 1, 200000);

INSERT INTO CHI_TIET_PHIEU_SUA_CHUA (maPhieuSuaChua, maPhuTung, soLuong, donGia) VALUES
('PSC01', 'PT01', 1, 1000000),
('PSC02', 'PT02', 2, 500000),
('PSC03', 'PT03', 1, 200000);

-- 9. Hóa đơn (Phát sinh doanh thu các tháng 2, 3, 5, 8)
INSERT INTO HOA_DON (maHoaDon, ngayLap, maPhuongThuc, maPhieuSuaChua, maNhanVien) VALUES
('HD01', '2026-02-15 11:45:00', 'PT01', 'PSC01', 'KT01'),
('HD02', '2026-03-20 12:00:00', 'PT02', 'PSC02', 'KT01'),
('HD03', '2026-05-15 14:00:00', 'PT01', 'PSC03', 'KT01'),
('HD04', '2026-08-20 15:00:00', 'PT02', 'PSC04', 'KT01');

INSERT INTO CHI_TIET_HOA_DON (maChiTietHD, maHD, noiDung, soLuong, dongia) VALUES
('CTHD01', 'HD01', N'Dịch vụ và phụ tùng', 1, 1350000),
('CTHD02', 'HD02', N'Dịch vụ và phụ tùng', 1, 1500000),
('CTHD03', 'HD03', N'Dịch vụ và phụ tùng', 1, 600000),
('CTHD04', 'HD04', N'Dịch vụ và phụ tùng', 1, 500000);


-- ==========================================================
-- PHẦN 3: VIEWS & PROCEDURES (THỐNG KÊ DASHBOARD TRANG CHỦ)
-- ==========================================================

-- 1. View KPI Tổng Quan
GO
CREATE VIEW v_Dashboard_KPI AS
SELECT 
    (SELECT COUNT(*) FROM PHIEU_TIEP_NHAN WHERE trangThai = N'Chờ sửa chữa' OR trangThai = N'Đang xử lý') AS SoXeDangXuLy,
    (SELECT COUNT(*) FROM HOA_DON WHERE MONTH(ngayLap) = MONTH(GETDATE()) AND YEAR(ngayLap) = YEAR(GETDATE())) AS SoHoaDonThangNay,
    (SELECT ISNULL(SUM(soLuong * dongia), 0) FROM CHI_TIET_HOA_DON HD JOIN HOA_DON H ON HD.maHD = H.maHoaDon WHERE MONTH(H.ngayLap) = MONTH(GETDATE()) AND YEAR(H.ngayLap) = YEAR(GETDATE())) AS DoanhThuThangNay,
    (SELECT COUNT(*) FROM PHU_TUNG WHERE soLuongTon < 5) AS SoPhuTungSapHet;
GO

-- 2. View Cảnh Báo Phụ Tùng Tồn Kho
GO
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
WHERE soLuongTon < 5;
GO

-- 3. Procedure Lấy Doanh Thu 12 Tháng (Vẽ Biểu Đồ)
GO
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

PRINT N'Cơ sở dữ liệu hoàn tất 100%. Sẵn sàng kết nối với Java DAO và hiển thị lên Dashboard!';



