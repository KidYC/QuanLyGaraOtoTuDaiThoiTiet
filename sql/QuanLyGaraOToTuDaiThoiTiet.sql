
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'QuanLyGaraOToTuDaiThoiTiet')
BEGIN
    CREATE DATABASE QuanLyGaraOToTuDaiThoiTiet;
END
GO

USE QuanLyGaraOToTuDaiThoiTiet;
GO

-- Xoa cac bang theo thu tu phu thuoc (bang con truoc, bang cha sau)
IF OBJECT_ID('CHI_TIET_HOA_DON', 'U') IS NOT NULL DROP TABLE CHI_TIET_HOA_DON;
IF OBJECT_ID('CANH_BAO_TON_KHO', 'U') IS NOT NULL DROP TABLE CANH_BAO_TON_KHO;
IF OBJECT_ID('CHI_TIET_KIEM_KE', 'U') IS NOT NULL DROP TABLE CHI_TIET_KIEM_KE;
IF OBJECT_ID('CHI_TIET_PHIEU_NHAP', 'U') IS NOT NULL DROP TABLE CHI_TIET_PHIEU_NHAP;
IF OBJECT_ID('CHI_TIET_PHIEU_SUA_CHUA', 'U') IS NOT NULL DROP TABLE CHI_TIET_PHIEU_SUA_CHUA;
IF OBJECT_ID('CHI_TIET_DICH_VU', 'U') IS NOT NULL DROP TABLE CHI_TIET_DICH_VU;
IF OBJECT_ID('PHIEU_KIEM_KE', 'U') IS NOT NULL DROP TABLE PHIEU_KIEM_KE;
IF OBJECT_ID('PHIEU_NHAP_KHO', 'U') IS NOT NULL DROP TABLE PHIEU_NHAP_KHO;
IF OBJECT_ID('HOA_DON', 'U') IS NOT NULL DROP TABLE HOA_DON;
IF OBJECT_ID('PHIEU_SUA_CHUA', 'U') IS NOT NULL DROP TABLE PHIEU_SUA_CHUA;
IF OBJECT_ID('PHIEU_TIEP_NHAN', 'U') IS NOT NULL DROP TABLE PHIEU_TIEP_NHAN;
IF OBJECT_ID('PHU_TUNG', 'U') IS NOT NULL DROP TABLE PHU_TUNG;
IF OBJECT_ID('XE', 'U') IS NOT NULL DROP TABLE XE;
IF OBJECT_ID('DICH_VU', 'U') IS NOT NULL DROP TABLE DICH_VU;
IF OBJECT_ID('KHO', 'U') IS NOT NULL DROP TABLE KHO;
IF OBJECT_ID('NHA_CUNG_CAP', 'U') IS NOT NULL DROP TABLE NHA_CUNG_CAP;
IF OBJECT_ID('KHACH_HANG', 'U') IS NOT NULL DROP TABLE KHACH_HANG;
IF OBJECT_ID('PHUONG_THUC_THANH_TOAN', 'U') IS NOT NULL DROP TABLE PHUONG_THUC_THANH_TOAN;
IF OBJECT_ID('THU_KHO', 'U') IS NOT NULL DROP TABLE THU_KHO;
IF OBJECT_ID('KE_TOAN', 'U') IS NOT NULL DROP TABLE KE_TOAN;
IF OBJECT_ID('KY_THUAT_VIEN', 'U') IS NOT NULL DROP TABLE KY_THUAT_VIEN;
IF OBJECT_ID('TAI_KHOAN', 'U') IS NOT NULL DROP TABLE TAI_KHOAN;
IF OBJECT_ID('NhanVien', 'U') IS NOT NULL DROP TABLE NhanVien;
GO

-- PHẦN 1: TẠO CƠ SỞ DỮ LIỆU & BẢNG (DDL)


-- 1. Bảng Nhân Viên
IF OBJECT_ID('NhanVien', 'U') IS NOT NULL DROP TABLE NhanVien;
CREATE TABLE NhanVien (
                          MaNV VARCHAR(10) PRIMARY KEY,
                          HoTen NVARCHAR(100) NOT NULL,
                          ChucVu NVARCHAR(50) NOT NULL, -- Admin, Thủ kho, Kế toán, Lễ tân, Kỹ thuật viên
                          SoDienThoai VARCHAR(15),
                          Email VARCHAR(100),
                          DiaChi NVARCHAR(200),
                          TrangThai NVARCHAR(20) DEFAULT N'Đang làm việc'
);

-- 2. Bang TAI_KHOAN (dung ten cot theo TaiKhoan_DAO)
IF OBJECT_ID('TAI_KHOAN', 'U') IS NOT NULL DROP TABLE TAI_KHOAN;
CREATE TABLE TAI_KHOAN (
                          MaTaiKhoan VARCHAR(10) PRIMARY KEY,
                          TenDangNhap VARCHAR(50) NOT NULL UNIQUE,
                          MatKhau VARCHAR(255) NOT NULL,
                          LoaiTaiKhoan NVARCHAR(30) NOT NULL,
                          MaNhanVien VARCHAR(10) NOT NULL,
                          TrangThai NVARCHAR(20) DEFAULT N'Hoạt động',
                          FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNV) ON DELETE CASCADE
);

-- 3. Bảng Khách Hàng
IF OBJECT_ID('KHACH_HANG', 'U') IS NOT NULL DROP TABLE KHACH_HANG;
CREATE TABLE KHACH_HANG (
                           MaKH VARCHAR(10) PRIMARY KEY,
                           HoTen NVARCHAR(100) NOT NULL,
                           SoDienThoai VARCHAR(15) NOT NULL,
                           DiaChi NVARCHAR(200),
                           Email VARCHAR(100),
                           NgayTao DATETIME DEFAULT GETDATE()
);

-- 4. Bảng XE
IF OBJECT_ID('XE', 'U') IS NOT NULL DROP TABLE XE;
CREATE TABLE XE (
                    BienSo VARCHAR(20) PRIMARY KEY,
                    MaKH VARCHAR(10) NOT NULL,
                    HieuXe NVARCHAR(50) NOT NULL,
                    LoaiXe NVARCHAR(50),
                    NamSanXuat INT,
                    SoKhung VARCHAR(50),
                    SoMay VARCHAR(50),
                    FOREIGN KEY (MaKH) REFERENCES KHACH_HANG(MaKH) ON DELETE CASCADE
);

-- 5. Bảng Nhà Cung Cấp
IF OBJECT_ID('NHA_CUNG_CAP', 'U') IS NOT NULL DROP TABLE NHA_CUNG_CAP;
CREATE TABLE NHA_CUNG_CAP (
                            MaNCC VARCHAR(10) PRIMARY KEY,
                            TenNCC NVARCHAR(100) NOT NULL,
                            SoDienThoai VARCHAR(15),
                            Email VARCHAR(100),
                            DiaChi NVARCHAR(200)
);

-- 6. Bảng KHO
IF OBJECT_ID('KHO', 'U') IS NOT NULL DROP TABLE KHO;
CREATE TABLE KHO (
                     MaKho VARCHAR(10) PRIMARY KEY,
                     TenKho NVARCHAR(100) NOT NULL,
                     DiaChi NVARCHAR(200),
                     MoTa NVARCHAR(255)
);

-- 7. Bảng Phụ Tùng
IF OBJECT_ID('PHU_TUNG', 'U') IS NOT NULL DROP TABLE PHU_TUNG;
CREATE TABLE PHU_TUNG (
                         MaPhuTung VARCHAR(10) PRIMARY KEY,
                         TenPhuTung NVARCHAR(100) NOT NULL,
                         DonGiaNhap DECIMAL(18,2) NOT NULL,
                         DonGiaBan DECIMAL(18,2) NOT NULL,
                         SoLuongTon INT DEFAULT 0,
                         DonViTinh NVARCHAR(20),
                         MaKho VARCHAR(10),
                         MaNCC VARCHAR(10),
                         SoLuongToiThieu INT DEFAULT 5,
                         FOREIGN KEY (MaKho) REFERENCES KHO(MaKho),
                         FOREIGN KEY (MaNCC) REFERENCES NHA_CUNG_CAP(MaNCC)
);

-- 8. Bảng Dịch Vụ
IF OBJECT_ID('DICH_VU', 'U') IS NOT NULL DROP TABLE DICH_VU;
CREATE TABLE DICH_VU (
                        MaDichVu VARCHAR(10) PRIMARY KEY,
                        TenDichVu NVARCHAR(100) NOT NULL,
                        DonGia DECIMAL(18,2) NOT NULL,
                        MoTa NVARCHAR(255)
);

-- 9. Bảng Phiếu Tiếp Nhận
IF OBJECT_ID('PHIEU_TIEP_NHAN', 'U') IS NOT NULL DROP TABLE PHIEU_TIEP_NHAN;
CREATE TABLE PHIEU_TIEP_NHAN (
                               MaPhieuTiepNhan VARCHAR(10) PRIMARY KEY,
                               BienSo VARCHAR(20) NOT NULL,
                               MaKH VARCHAR(10) NOT NULL,
                               MaNVLeTan VARCHAR(10) NOT NULL,
                               NgayTiepNhan DATETIME DEFAULT GETDATE(),
                               TinhTrangKiemTra NVARCHAR(500),
                               TrangThai NVARCHAR(30) DEFAULT N'Chờ sửa chữa',
                               FOREIGN KEY (BienSo) REFERENCES XE(BienSo),
                               FOREIGN KEY (MaKH) REFERENCES KHACH_HANG(MaKH),
                               FOREIGN KEY (MaNVLeTan) REFERENCES NhanVien(MaNV)
);

-- 10. Bảng Phiếu Sửa Chữa
IF OBJECT_ID('PHIEU_SUA_CHUA', 'U') IS NOT NULL DROP TABLE PHIEU_SUA_CHUA;
CREATE TABLE PHIEU_SUA_CHUA (
                              MaPhieuSuaChua VARCHAR(10) PRIMARY KEY,
                              MaPhieuTiepNhan VARCHAR(10) NOT NULL,
                              MaKTV VARCHAR(10) NOT NULL,
                              NgaySuaChua DATETIME DEFAULT GETDATE(),
                              TongTienDichVu DECIMAL(18,2) DEFAULT 0,
                              TongTienPhuTung DECIMAL(18,2) DEFAULT 0,
                              TongTien DECIMAL(18,2) DEFAULT 0,
                              GhiChu NVARCHAR(255),
                              TrangThai NVARCHAR(30) DEFAULT N'Đang xử lý',
                              FOREIGN KEY (MaPhieuTiepNhan) REFERENCES PHIEU_TIEP_NHAN(MaPhieuTiepNhan),
                              FOREIGN KEY (MaKTV) REFERENCES NhanVien(MaNV)
);

-- 11. Bảng Chi Tiết Dịch Vụ Sửa Chữa
IF OBJECT_ID('CHI_TIET_DICH_VU', 'U') IS NOT NULL DROP TABLE CHI_TIET_DICH_VU;
CREATE TABLE CHI_TIET_DICH_VU (
                               MaPhieuSuaChua VARCHAR(10) NOT NULL,
                               MaDichVu VARCHAR(10) NOT NULL,
                               SoLuong INT DEFAULT 1,
                               DonGia DECIMAL(18,2) NOT NULL,
                               ThanhTien AS (SoLuong * DonGia),
                               PRIMARY KEY (MaPhieuSuaChua, MaDichVu),
                               FOREIGN KEY (MaPhieuSuaChua) REFERENCES PHIEU_SUA_CHUA(MaPhieuSuaChua) ON DELETE CASCADE,
                               FOREIGN KEY (MaDichVu) REFERENCES DICH_VU(MaDichVu)
);

-- 12. Bảng Danh Sách Phụ Tùng Sửa Chữa
IF OBJECT_ID('CHI_TIET_PHIEU_SUA_CHUA', 'U') IS NOT NULL DROP TABLE CHI_TIET_PHIEU_SUA_CHUA;
CREATE TABLE CHI_TIET_PHIEU_SUA_CHUA (
                                 MaPhieuSuaChua VARCHAR(10) NOT NULL,
                                 MaPhuTung VARCHAR(10) NOT NULL,
                                 SoLuong INT NOT NULL,
                                 DonGia DECIMAL(18,2) NOT NULL,
                                 ThanhTien AS (SoLuong * DonGia),
                                 PRIMARY KEY (MaPhieuSuaChua, MaPhuTung),
                                 FOREIGN KEY (MaPhieuSuaChua) REFERENCES PHIEU_SUA_CHUA(MaPhieuSuaChua) ON DELETE CASCADE,
                                 FOREIGN KEY (MaPhuTung) REFERENCES PHU_TUNG(MaPhuTung)
);

-- 13. Bảng Hóa Đơn Thanh Toán
IF OBJECT_ID('HOA_DON', 'U') IS NOT NULL DROP TABLE HOA_DON;
CREATE TABLE HOA_DON (
                        MaHoaDon VARCHAR(10) PRIMARY KEY,
                        MaPhieuSuaChua VARCHAR(10) NOT NULL,
                        MaNVKeToan VARCHAR(10) NOT NULL,
                        NgayLap DATETIME DEFAULT GETDATE(),
                        TongTien DECIMAL(18,2) NOT NULL,
                        PhuongThucThanhToan NVARCHAR(50) DEFAULT N'Tiền mặt',
                        GhiChu NVARCHAR(255),
                        FOREIGN KEY (MaPhieuSuaChua) REFERENCES PHIEU_SUA_CHUA(MaPhieuSuaChua),
                        FOREIGN KEY (MaNVKeToan) REFERENCES NhanVien(MaNV)
);

-- 14. Bảng Phiếu Nhập KHO
IF OBJECT_ID('PHIEU_NHAP_KHO', 'U') IS NOT NULL DROP TABLE PHIEU_NHAP_KHO;
CREATE TABLE PHIEU_NHAP_KHO (
                              MaPhieuNhap VARCHAR(10) PRIMARY KEY,
                              MaKho VARCHAR(10) NOT NULL,
                              MaNCC VARCHAR(10) NOT NULL,
                              MaNVThuKho VARCHAR(10) NOT NULL,
                              NgayNhap DATETIME DEFAULT GETDATE(),
                              TongTien DECIMAL(18,2) DEFAULT 0,
                              GhiChu NVARCHAR(255),
                              FOREIGN KEY (MaKho) REFERENCES KHO(MaKho),
                              FOREIGN KEY (MaNCC) REFERENCES NHA_CUNG_CAP(MaNCC),
                              FOREIGN KEY (MaNVThuKho) REFERENCES NhanVien(MaNV)
);

-- 15. Bảng Chi Tiết Phiếu Nhập
IF OBJECT_ID('CHI_TIET_PHIEU_NHAP', 'U') IS NOT NULL DROP TABLE CHI_TIET_PHIEU_NHAP;
CREATE TABLE CHI_TIET_PHIEU_NHAP (
                                  MaPhieuNhap VARCHAR(10) NOT NULL,
                                  MaPhuTung VARCHAR(10) NOT NULL,
                                  SoLuong INT NOT NULL,
                                  DonGiaNhap DECIMAL(18,2) NOT NULL,
                                  ThanhTien AS (SoLuong * DonGiaNhap),
                                  PRIMARY KEY (MaPhieuNhap, MaPhuTung),
                                  FOREIGN KEY (MaPhieuNhap) REFERENCES PHIEU_NHAP_KHO(MaPhieuNhap) ON DELETE CASCADE,
                                  FOREIGN KEY (MaPhuTung) REFERENCES PHU_TUNG(MaPhuTung)
);

-- 16. Bảng Phiếu Kiểm Kê
IF OBJECT_ID('PHIEU_KIEM_KE', 'U') IS NOT NULL DROP TABLE PHIEU_KIEM_KE;
CREATE TABLE PHIEU_KIEM_KE (
                             MaPhieuKiemKe VARCHAR(10) PRIMARY KEY,
                             MaKho VARCHAR(10) NOT NULL,
                             MaNVThuKho VARCHAR(10) NOT NULL,
                             NgayKiemKe DATETIME DEFAULT GETDATE(),
                             GhiChu NVARCHAR(255),
                             FOREIGN KEY (MaKho) REFERENCES KHO(MaKho),
                             FOREIGN KEY (MaNVThuKho) REFERENCES NhanVien(MaNV)
);

-- 17. Bảng Chi Tiết Kiểm Kê
IF OBJECT_ID('CHI_TIET_KIEM_KE', 'U') IS NOT NULL DROP TABLE CHI_TIET_KIEM_KE;
CREATE TABLE CHI_TIET_KIEM_KE (
                               MaPhieuKiemKe VARCHAR(10) NOT NULL,
                               MaPhuTung VARCHAR(10) NOT NULL,
                               SoLuongHeThong INT NOT NULL,
                               SoLuongThucTe INT NOT NULL,
                               ChenhLech AS (SoLuongThucTe - SoLuongHeThong),
                               LyDo NVARCHAR(255),
                               PRIMARY KEY (MaPhieuKiemKe, MaPhuTung),
                               FOREIGN KEY (MaPhieuKiemKe) REFERENCES PHIEU_KIEM_KE(MaPhieuKiemKe) ON DELETE CASCADE,
                               FOREIGN KEY (MaPhuTung) REFERENCES PHU_TUNG(MaPhuTung)
);

-- 18. Bang KY_THUAT_VIEN (bo sung theo ERD)
CREATE TABLE KY_THUAT_VIEN (
    MaKTV VARCHAR(10) PRIMARY KEY,
    TrangThai NVARCHAR(30) NOT NULL DEFAULT N'Dang lam viec',
    FOREIGN KEY (MaKTV) REFERENCES NhanVien(MaNV)
);

-- 19. Bang KE_TOAN (bo sung theo ERD)
CREATE TABLE KE_TOAN (
    MaNhanVien VARCHAR(10) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    SoDienThoai VARCHAR(15),
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNV)
);

-- 20. Bang THU_KHO (bo sung theo ERD)
CREATE TABLE THU_KHO (
    MaNhanVien VARCHAR(10) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    SoDienThoai VARCHAR(15),
    TenDangNhap VARCHAR(50),
    MatKhau VARCHAR(255),
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNV)
);

-- 21. Bang PHUONG_THUC_THANH_TOAN (bo sung theo ERD)
CREATE TABLE PHUONG_THUC_THANH_TOAN (
    MaPhuongThuc VARCHAR(10) PRIMARY KEY,
    TenPhuongThuc NVARCHAR(50) NOT NULL UNIQUE
);

-- 22. Bang CHI_TIET_HOA_DON (bo sung theo ERD)
CREATE TABLE CHI_TIET_HOA_DON (
    MaChiTietHD VARCHAR(10) PRIMARY KEY,
    MaHD VARCHAR(10) NOT NULL,
    NoiDung NVARCHAR(255) NOT NULL,
    SoLuong INT NOT NULL DEFAULT 1 CHECK (SoLuong > 0),
    DonGia DECIMAL(18,2) NOT NULL CHECK (DonGia >= 0),
    FOREIGN KEY (MaHD) REFERENCES HOA_DON(MaHoaDon) ON DELETE CASCADE
);

-- 23. Bang CANH_BAO_TON_KHO (bo sung theo ERD)
CREATE TABLE CANH_BAO_TON_KHO (
    MaCanhBao VARCHAR(10) PRIMARY KEY,
    MaPhuTung VARCHAR(10) NOT NULL,
    NgayCanhBao DATETIME NOT NULL DEFAULT GETDATE(),
    LoaiCanhBao NVARCHAR(100) NOT NULL,
    TrangThaiDaXem BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (MaPhuTung) REFERENCES PHU_TUNG(MaPhuTung)
);
GO

-- PHẦN 2: TỰ ĐỘNG HÓA KHO VÀ TÍNH TỔNG TIỀN (TRIGGERS & INDEXES)


-- Trigger 1: Tự động TRỪ tồn kho khi xuất phụ tùng sửa chữa
GO
CREATE TRIGGER trg_XuatPhuTung
    ON CHI_TIET_PHIEU_SUA_CHUA
    AFTER INSERT
AS
BEGIN
UPDATE PHU_TUNG
SET SoLuongTon = PHU_TUNG.SoLuongTon - i.SoLuong
    FROM PHU_TUNG INNER JOIN inserted i ON PHU_TUNG.MaPhuTung = i.MaPhuTung;
END;
GO

-- Trigger 2: Tự động CỘNG tồn kho khi nhập phụ tùng
GO
CREATE TRIGGER trg_NhapPhuTung
    ON CHI_TIET_PHIEU_NHAP
    AFTER INSERT
AS
BEGIN
UPDATE PHU_TUNG
SET SoLuongTon = PHU_TUNG.SoLuongTon + i.SoLuong
    FROM PHU_TUNG INNER JOIN inserted i ON PHU_TUNG.MaPhuTung = i.MaPhuTung;
END;
GO

-- Trigger 3: Tự động tính tổng tiền ở PHIEU_SUA_CHUA khi thay đổi Dịch Vụ
GO
CREATE TRIGGER trg_CapNhatTongTienDichVu
    ON CHI_TIET_DICH_VU
    AFTER INSERT, UPDATE, DELETE
    AS
BEGIN
UPDATE PHIEU_SUA_CHUA
SET TongTienDichVu = ISNULL((SELECT SUM(ThanhTien) FROM CHI_TIET_DICH_VU WHERE MaPhieuSuaChua = PHIEU_SUA_CHUA.MaPhieuSuaChua), 0),
    TongTien = ISNULL((SELECT SUM(ThanhTien) FROM CHI_TIET_DICH_VU WHERE MaPhieuSuaChua = PHIEU_SUA_CHUA.MaPhieuSuaChua), 0) + TongTienPhuTung
WHERE MaPhieuSuaChua IN (SELECT DISTINCT MaPhieuSuaChua FROM inserted UNION SELECT DISTINCT MaPhieuSuaChua FROM deleted);
END;
GO

-- Trigger 4: Tự động tính tổng tiền ở PHIEU_SUA_CHUA khi thay đổi Phụ Tùng
GO
CREATE TRIGGER trg_CapNhatTongTienPhuTung
    ON CHI_TIET_PHIEU_SUA_CHUA
    AFTER INSERT, UPDATE, DELETE
    AS
BEGIN
UPDATE PHIEU_SUA_CHUA
SET TongTienPhuTung = ISNULL((SELECT SUM(ThanhTien) FROM CHI_TIET_PHIEU_SUA_CHUA WHERE MaPhieuSuaChua = PHIEU_SUA_CHUA.MaPhieuSuaChua), 0),
    TongTien = TongTienDichVu + ISNULL((SELECT SUM(ThanhTien) FROM CHI_TIET_PHIEU_SUA_CHUA WHERE MaPhieuSuaChua = PHIEU_SUA_CHUA.MaPhieuSuaChua), 0)
WHERE MaPhieuSuaChua IN (SELECT DISTINCT MaPhieuSuaChua FROM inserted UNION SELECT DISTINCT MaPhieuSuaChua FROM deleted);
END;
GO

-- Tạo Index tăng tốc truy vấn Dashboard & Tìm kiếm
CREATE INDEX IX_PhieuSuaChua_NgaySuaChua ON PHIEU_SUA_CHUA(NgaySuaChua);
CREATE INDEX IX_PhieuTiepNhan_NgayTiepNhan ON PHIEU_TIEP_NHAN(NgayTiepNhan);
CREATE INDEX IX_PhieuNhapKho_NgayNhap ON PHIEU_NHAP_KHO(NgayNhap);
CREATE INDEX IX_Xe_MaKH ON XE(MaKH);
GO

-- PHẦN 3: DỮ LIỆU MẪU ĐA DẠNG (DML 10 THÁNG NĂM 2026)


-- Nhân Viên
INSERT INTO NhanVien (MaNV, HoTen, ChucVu, SoDienThoai, Email, DiaChi) VALUES
('NV01', N'Phạm Thanh Hùng', N'Admin', '0901234567', 'hung.pt@gara.com', N'Quận Gò Vấp, TP.HCM'),
('NV02', N'Nguyễn Quang Huy', N'Thủ kho', '0912345678', 'huy.nq@gara.com', N'Quận 12, TP.HCM'),
('NV03', N'Lê Duy Minh', N'Kế toán', '0923456789', 'minh.ld@gara.com', N'Quận Bình Thạnh, TP.HCM'),
('NV04', N'Trần Thanh Liêm', N'Lễ tân', '0934567890', 'liem.tt@gara.com', N'Quận Tân Bình, TP.HCM'),
('NV05', N'Phan Xuân Phụng', N'Kỹ thuật viên', '0945678901', 'phung.px@gara.com', N'Quận Thủ Đức, TP.HCM'),
('NV06', N'Nguyễn Hoàng Nam', N'Kỹ thuật viên', '0956789012', 'nam.nh@gara.com', N'Quận 9, TP.HCM');

-- Tai khoan mau (maTaiKhoan, tenDangNhap, matKhau, loaiTaiKhoan, maNhanVien)
INSERT INTO TAI_KHOAN (MaTaiKhoan, TenDangNhap, MatKhau, LoaiTaiKhoan, MaNhanVien, TrangThai) VALUES
    ('TK01', 'admin', 'admin123', N'Admin', 'NV01', N'Hoạt động'),
    ('TK02', 'huy0804', 'huy0804', N'Thủ kho', 'NV02', N'Hoạt động'),
    ('TK03', 'minh0806', 'minh0806', N'Kế toán', 'NV03', N'Hoạt động'),
    ('TK04', 'liem1507', 'liem1507', N'Lễ tân', 'NV04', N'Hoạt động'),
    ('TK05', 'phung2203', 'phung2203', N'Kỹ thuật viên', 'NV05', N'Hoạt động');

-- Khách Hàng
INSERT INTO KHACH_HANG (MaKH, HoTen, SoDienThoai, DiaChi, Email) VALUES
                                                                    ('KH01', N'Nguyễn Văn An', '0981111111', N'123 Nguyễn Oanh, Gò Vấp, TP.HCM', 'an.nguyen@gmail.com'),
                                                                    ('KH02', N'Trần Thị Bích', '0982222222', N'456 Lê Văn Thọ, Gò Vấp, TP.HCM', 'bich.tran@gmail.com'),
                                                                    ('KH03', N'Lê Hoàng Cường', '0983333333', N'789 Quang Trung, Gò Vấp, TP.HCM', 'cuong.le@gmail.com'),
                                                                    ('KH04', N'Phạm Minh Dung', '0984444444', N'12 Hoàng Văn Thụ, Tân Bình, TP.HCM', 'dung.pham@gmail.com'),
                                                                    ('KH05', N'Đặng Quốc Hùng', '0985555555', N'88 Cộng Hòa, Tân Bình, TP.HCM', 'hung.dang@gmail.com'),
                                                                    ('KH06', N'Cty Vận Tải An Phát', '0289999888', N'102 Kha Vạn Cân, Thủ Đức, TP.HCM', 'contact@anphatlogistics.com');

-- XE (KH06 sở hữu 2 xe)
INSERT INTO XE (BienSo, MaKH, HieuXe, LoaiXe, NamSanXuat, SoKhung, SoMay) VALUES
                                                                              ('51H-123.45', 'KH01', N'Toyota', N'Camry 2.5Q', 2021, 'SK10001', 'SM10001'),
                                                                              ('51G-678.90', 'KH02', N'Honda', N'CR-V L', 2020, 'SK10002', 'SM10002'),
                                                                              ('51F-432.10', 'KH03', N'Ford', N'Ranger Wildtrak', 2019, 'SK10003', 'SM10003'),
                                                                              ('51A-888.88', 'KH04', N'Mercedes-Benz', N'E300 AMG', 2022, 'SK10004', 'SM10004'),
                                                                              ('51K-999.99', 'KH05', N'Hyundai', N'Tucson 1.6T', 2023, 'SK10005', 'SM10005'),
                                                                              ('50LD-111.22', 'KH06', N'Ford', N'Transit 16 chỗ', 2018, 'SK10006', 'SM10006'),
                                                                              ('50LD-333.44', 'KH06', N'Hyundai', N'Solati 16 chỗ', 2020, 'SK10007', 'SM10007');

-- KHO
INSERT INTO KHO (MaKho, TenKho, DiaChi, MoTa) VALUES
                                                  ('K01', N'KHO Phụ Tùng Chính', N'Khu A - Gara Ô Tô', N'Chứa động cơ, lốp, dầu nhớt chính hãng'),
                                                  ('K02', N'KHO Sơ Cấp & Tiêu Hao', N'Khu B - Gara Ô Tô', N'Chứa lọc gió, bugi, hóa chất vệ sinh');

-- Nhà Cung Cấp
INSERT INTO NHA_CUNG_CAP (MaNCC, TenNCC, SoDienThoai, Email, DiaChi) VALUES
                                                                       ('NCC01', N'Công ty Phụ tùng Ô tô Toyota Việt Nam', '02838001122', 'contact@toyotaparts.vn', N'Quận 1, TP.HCM'),
                                                                       ('NCC02', N'Nhà phân phối Lốp Bosch & Michelin Việt Nam', '02839003344', 'sales@boschmichelin.vn', N'Quận 7, TP.HCM'),
                                                                       ('NCC03', N'Tổng đại lý Dầu nhớt Castrol & Shell', '02837005566', 'distributor@shellcastrol.vn', N'Quận Bình Tân, TP.HCM');

-- Phụ Tùng (Có PT08 hoàn toàn HẾT HÀNG để test cảnh báo)
INSERT INTO PHU_TUNG (MaPhuTung, TenPhuTung, DonGiaNhap, DonGiaBan, SoLuongTon, DonViTinh, MaKho, MaNCC, SoLuongToiThieu) VALUES
                                                                                                                             ('PT01', N'Dầu nhớt Castrol Magnatec 5W-30 (4L)', 450000, 650000, 45, N'Can', 'K01', 'NCC03', 10),
                                                                                                                             ('PT02', N'Lọc dầu Toyota chính hãng', 80000, 150000, 30, N'Cái', 'K02', 'NCC01', 5),
                                                                                                                             ('PT03', N'Lọc gió động cơ Honda CR-V', 120000, 220000, 3, N'Cái', 'K02', 'NCC01', 5),
                                                                                                                             ('PT04', N'Má phanh trước Camry', 850000, 1250000, 8, N'Bộ', 'K01', 'NCC01', 4),
                                                                                                                             ('PT05', N'Bugi Iridium Denso', 150000, 250000, 50, N'Cái', 'K02', 'NCC01', 10),
                                                                                                                             ('PT06', N'Lốp xe Michelin 235/55R19', 2800000, 3500000, 2, N'Cái', 'K01', 'NCC02', 4),
                                                                                                                             ('PT07', N'Gạt mưa Bosch Aerotwin 24 inch', 200000, 350000, 15, N'Cặp', 'K02', 'NCC02', 5),
                                                                                                                             ('PT08', N'Bơm cao áp xe Ford Transit', 4500000, 6200000, 0, N'Cái', 'K01', 'NCC01', 2);

-- Dịch Vụ
INSERT INTO DICH_VU (MaDichVu, TenDichVu, DonGia, MoTa) VALUES
                                                           ('DV01', N'Thay dầu nhớt & Lọc nhớt', 150000, N'Công thay dầu, lọc nhớt và kiểm tra tổng quát'),
                                                           ('DV02', N'Bảo dưỡng hệ thống phanh 4 bánh', 350000, N'Vệ sinh, mài đĩa phanh, tra mỡ chốt phanh'),
                                                           ('DV03', N'Cân bằng động & Cân chỉnh thước lái', 450000, N'Đo cân bằng động bánh xe bằng máy laser'),
                                                           ('DV04', N'Vệ sinh kim phun & Họng hút Bạc', 500000, N'Vệ sinh bằng dung dịch chuyên dụng sóng siêu âm'),
                                                           ('DV05', N'Đọc lỗi & Chẩn đoán hệ thống điện ECU', 200000, N'Quét lỗi tự động bằng máy chẩn đoán chuyên dụng'),
                                                           ('DV06', N'Rửa xe & Dọn nội thất cao cấp', 300000, N'Hút bụi, dưỡng ghế da, khử mùi Ozon');

-- Phiếu Tiếp Nhận (Dữ liệu rải từ Tháng 01 đến Tháng 10/2026)
INSERT INTO PHIEU_TIEP_NHAN (MaPhieuTiepNhan, BienSo, MaKH, MaNVLeTan, NgayTiepNhan, TinhTrangKiemTra, TrangThai) VALUES
                                                                                                                    ('TN01', '51H-123.45', 'KH01', 'NV04', '2026-01-15 08:30:00', N'Bảo dưỡng định kỳ 10.000km', N'Đã thanh toán'),
                                                                                                                    ('TN02', '51G-678.90', 'KH02', 'NV04', '2026-02-20 09:15:00', N'XE bị giật khi tăng tốc', N'Đã thanh toán'),
                                                                                                                    ('TN03', '51F-432.10', 'KH03', 'NV04', '2026-03-10 14:00:00', N'Thay lốp và cân bằng động', N'Đã thanh toán'),
                                                                                                                    ('TN04', '51A-888.88', 'KH04', 'NV04', '2026-04-05 10:00:00', N'Báo lỗi đèn Engine Check trên đồng hồ', N'Đã thanh toán'),
                                                                                                                    ('TN05', '51K-999.99', 'KH05', 'NV04', '2026-05-12 11:00:00', N'Thay dầu máy và gạt mưa', N'Đã thanh toán'),
                                                                                                                    ('TN06', '50LD-111.22', 'KH06', 'NV04', '2026-06-18 08:00:00', N'Bảo dưỡng tổng thể xe khách', N'Đã thanh toán'),
                                                                                                                    ('TN07', '51H-123.45', 'KH01', 'NV04', '2026-07-22 13:30:00', N'Tiếng kêu lạ ở phanh trước', N'Đã thanh toán'),
                                                                                                                    ('TN08', '50LD-333.44', 'KH06', 'NV04', '2026-08-09 15:00:00', N'Vệ sinh kim phun, máy yếu', N'Đã thanh toán'),
                                                                                                                    ('TN09', '51G-678.90', 'KH02', 'NV04', '2026-09-14 09:00:00', N'Thay dầu nhớt định kỳ', N'Đã thanh toán'),
                                                                                                                    ('TN10', '51A-888.88', 'KH04', 'NV04', '2026-10-01 08:30:00', N'Thay dầu và bảo dưỡng phanh', N'Đã thanh toán'),
                                                                                                                    ('TN11', '51F-432.10', 'KH03', 'NV04', '2026-10-10 14:00:00', N'Thay cặp lốp Michelin trước', N'Đang sửa chữa'),
                                                                                                                    ('TN12', '51K-999.99', 'KH05', 'NV04', '2026-10-20 10:00:00', N'Kiểm tra hệ thống điện', N'Chờ sửa chữa'),
                                                                                                                    ('TN13', '50LD-111.22', 'KH06', 'NV04', '2026-10-22 16:00:00', N'Khách hoãn sửa chữa', N'Đã hủy');

-- Phiếu Sửa Chữa
INSERT INTO PHIEU_SUA_CHUA (MaPhieuSuaChua, MaPhieuTiepNhan, MaKTV, NgaySuaChua, GhiChu, TrangThai) VALUES
                                                                                                      ('SC01', 'TN01', 'NV05', '2026-01-15 10:00:00', N'Thay dầu Castrol & Lọc nhớt', N'Hoàn thành'),
                                                                                                      ('SC02', 'TN02', 'NV06', '2026-02-20 11:30:00', N'Thay 4 bugi Denso & Vệ sinh kim phun', N'Hoàn thành'),
                                                                                                      ('SC03', 'TN03', 'NV05', '2026-03-10 16:00:00', N'Thay 2 lốp Michelin, cân bằng động', N'Hoàn thành'),
                                                                                                      ('SC04', 'TN04', 'NV06', '2026-04-05 14:00:00', N'Đọc lỗi ECU, vệ sinh họng hút', N'Hoàn thành'),
                                                                                                      ('SC05', 'TN05', 'NV05', '2026-05-12 14:30:00', N'Thay dầu và gạt mưa Bosch', N'Hoàn thành'),
                                                                                                      ('SC06', 'TN06', 'NV06', '2026-06-18 11:00:00', N'Bảo dưỡng phanh, thay lọc dầu', N'Hoàn thành'),
                                                                                                      ('SC07', 'TN07', 'NV05', '2026-07-22 15:30:00', N'Thay má phanh trước Camry', N'Hoàn thành'),
                                                                                                      ('SC08', 'TN08', 'NV06', '2026-08-09 17:00:00', N'Vệ sinh kim phun siêu âm', N'Hoàn thành'),
                                                                                                      ('SC09', 'TN09', 'NV05', '2026-09-14 11:00:00', N'Thay dầu máy Castrol', N'Hoàn thành'),
                                                                                                      ('SC10', 'TN10', 'NV05', '2026-10-01 10:00:00', N'Thay dầu và bảo dưỡng phanh', N'Hoàn thành'),
                                                                                                      ('SC11', 'TN11', 'NV06', '2026-10-10 15:30:00', N'Đang thay cặp lốp Michelin', N'Đang xử lý');

-- Chi Tiết Dịch Vụ
INSERT INTO CHI_TIET_DICH_VU (MaPhieuSuaChua, MaDichVu, SoLuong, DonGia) VALUES
                                                                          ('SC01', 'DV01', 1, 150000),
                                                                          ('SC02', 'DV04', 1, 500000),
                                                                          ('SC03', 'DV03', 1, 450000),
                                                                          ('SC04', 'DV05', 1, 200000), ('SC04', 'DV04', 1, 500000),
                                                                          ('SC05', 'DV01', 1, 150000),
                                                                          ('SC06', 'DV02', 1, 350000), ('SC06', 'DV01', 1, 150000),
                                                                          ('SC07', 'DV02', 1, 350000),
                                                                          ('SC08', 'DV04', 1, 500000),
                                                                          ('SC09', 'DV01', 1, 150000),
                                                                          ('SC10', 'DV01', 1, 150000), ('SC10', 'DV02', 1, 350000),
                                                                          ('SC11', 'DV03', 1, 450000);

-- Danh Sách Phụ Tùng Sửa Chữa (Sẽ tự kích hoạt Trigger tính tổng tiền & trừ kho)
INSERT INTO CHI_TIET_PHIEU_SUA_CHUA (MaPhieuSuaChua, MaPhuTung, SoLuong, DonGia) VALUES
                                                                             ('SC01', 'PT01', 1, 650000), ('SC01', 'PT02', 1, 150000),
                                                                             ('SC02', 'PT05', 4, 250000),
                                                                             ('SC03', 'PT06', 2, 3500000),
                                                                             ('SC05', 'PT01', 1, 650000), ('SC05', 'PT07', 1, 350000),
                                                                             ('SC06', 'PT02', 2, 150000),
                                                                             ('SC07', 'PT04', 1, 1250000),
                                                                             ('SC09', 'PT01', 1, 650000),
                                                                             ('SC10', 'PT01', 1, 650000), ('SC10', 'PT02', 1, 150000), ('SC10', 'PT04', 1, 1250000),
                                                                             ('SC11', 'PT06', 2, 3500000);

-- Hóa Đơn Thanh Toán (Tương ứng các phiếu hoàn thành)
INSERT INTO HOA_DON (MaHoaDon, MaPhieuSuaChua, MaNVKeToan, NgayLap, TongTien, PhuongThucThanhToan, GhiChu) VALUES
                                                                                                              ('HD01', 'SC01', 'NV03', '2026-01-15 11:00:00', 950000, N'Tiền mặt', N'Đã thanh toán đủ'),
                                                                                                              ('HD02', 'SC02', 'NV03', '2026-02-20 12:00:00', 1500000, N'Chuyển khoản', N'Thanh toán qua QR Bank'),
                                                                                                              ('HD03', 'SC03', 'NV03', '2026-03-10 17:00:00', 7450000, N'Quẹt thẻ', N'Thanh toán thẻ Visa'),
                                                                                                              ('HD04', 'SC04', 'NV03', '2026-04-05 15:00:00', 700000, N'Tiền mặt', N'Đã thanh toán đủ'),
                                                                                                              ('HD05', 'SC05', 'NV03', '2026-05-12 15:00:00', 1150000, N'Tiền mặt', N'Đã thanh toán đủ'),
                                                                                                              ('HD06', 'SC06', 'NV03', '2026-06-18 12:00:00', 800000, N'Chuyển khoản', N'Cty An Phát chuyển khoản'),
                                                                                                              ('HD07', 'SC07', 'NV03', '2026-07-22 16:30:00', 1600000, N'Tiền mặt', N'Đã thanh toán đủ'),
                                                                                                              ('HD08', 'SC08', 'NV03', '2026-08-09 17:30:00', 500000, N'Chuyển khoản', N'Đã thanh toán đủ'),
                                                                                                              ('HD09', 'SC09', 'NV03', '2026-09-14 11:30:00', 800000, N'Tiền mặt', N'Đã thanh toán đủ'),
                                                                                                              ('HD10', 'SC10', 'NV03', '2026-10-01 11:00:00', 2550000, N'Chuyển khoản', N'Đã thanh toán đủ');

-- Phiếu Nhập KHO (Rải từ Tháng 01 đến Tháng 09/2026)
INSERT INTO PHIEU_NHAP_KHO (MaPhieuNhap, MaKho, MaNCC, MaNVThuKho, NgayNhap, TongTien, GhiChu) VALUES
                                                                                                 ('PN01', 'K01', 'NCC03', 'NV02', '2026-01-05 09:00:00', 13500000, N'Nhập dầu nhớt Castrol đầu năm'),
                                                                                                 ('PN02', 'K02', 'NCC01', 'NV02', '2026-03-01 14:00:00', 6000000, N'Nhập lọc dầu & bugi Denso'),
                                                                                                 ('PN03', 'K01', 'NCC02', 'NV02', '2026-05-02 10:00:00', 14000000, N'Nhập lốp Michelin đợt 1'),
                                                                                                 ('PN04', 'K01', 'NCC03', 'NV02', '2026-09-01 09:00:00', 9000000, N'Nhập dầu nhớt Castrol tháng 9');

-- Chi Tiết Phiếu Nhập (Kích hoạt Trigger tự CỘNG tồn kho)
INSERT INTO CHI_TIET_PHIEU_NHAP (MaPhieuNhap, MaPhuTung, SoLuong, DonGiaNhap) VALUES
                                                                               ('PN01', 'PT01', 30, 450000),
                                                                               ('PN02', 'PT02', 25, 80000), ('PN02', 'PT05', 20, 150000),
                                                                               ('PN03', 'PT06', 5, 2800000),
                                                                               ('PN04', 'PT01', 20, 450000);

-- Phiếu Kiểm Kê KHO
INSERT INTO PHIEU_KIEM_KE (MaPhieuKiemKe, MaKho, MaNVThuKho, NgayKiemKe, GhiChu) VALUES
                                                                                   ('KK01', 'K01', 'NV02', '2026-06-30 17:00:00', N'Kiểm kê định kỳ 6 tháng đầu năm'),
                                                                                   ('KK02', 'K02', 'NV02', '2026-09-30 17:00:00', N'Kiểm kê định kỳ cuối quý 3');

INSERT INTO CHI_TIET_KIEM_KE (MaPhieuKiemKe, MaPhuTung, SoLuongHeThong, SoLuongThucTe, LyDo) VALUES
                                                                                              ('KK01', 'PT01', 45, 45, N'Đúng số lượng'),
                                                                                              ('KK01', 'PT06', 3, 2, N'Hỏng 1 lốp do vận chuyển kho'),
                                                                                              ('KK02', 'PT02', 30, 30, N'Đúng số lượng');

-- Du lieu mau cho cac bang bo sung theo ERD
INSERT INTO KY_THUAT_VIEN (MaKTV, TrangThai) VALUES
    ('NV05', N'Đang làm việc'),
    ('NV06', N'Đang làm việc');

INSERT INTO KE_TOAN (MaNhanVien, HoTen, SoDienThoai) VALUES
    ('NV03', N'Lê Duy Minh', '0923456789');

INSERT INTO THU_KHO (MaNhanVien, HoTen, SoDienThoai, TenDangNhap, MatKhau) VALUES
    ('NV02', N'Nguyễn Quang Huy', '0912345678', 'huy0804', 'huy0804');

INSERT INTO PHUONG_THUC_THANH_TOAN (MaPhuongThuc, TenPhuongThuc) VALUES
    ('PTT01', N'Tiền mặt'),
    ('PTT02', N'Chuyển khoản'),
    ('PTT03', N'Quẹt thẻ');

-- Moi hoa don co mot dong chi tiet tong hop voi thanh tien bang TongTien cua hoa don goc
INSERT INTO CHI_TIET_HOA_DON (MaChiTietHD, MaHD, NoiDung, SoLuong, DonGia) VALUES
    ('CTHD01', 'HD01', N'Chi phí sửa chữa theo phiếu SC01', 1, 950000),
    ('CTHD02', 'HD02', N'Chi phí sửa chữa theo phiếu SC02', 1, 1500000),
    ('CTHD03', 'HD03', N'Chi phí sửa chữa theo phiếu SC03', 1, 7450000),
    ('CTHD04', 'HD04', N'Chi phí sửa chữa theo phiếu SC04', 1, 700000),
    ('CTHD05', 'HD05', N'Chi phí sửa chữa theo phiếu SC05', 1, 1150000),
    ('CTHD06', 'HD06', N'Chi phí sửa chữa theo phiếu SC06', 1, 800000),
    ('CTHD07', 'HD07', N'Chi phí sửa chữa theo phiếu SC07', 1, 1600000),
    ('CTHD08', 'HD08', N'Chi phí sửa chữa theo phiếu SC08', 1, 500000),
    ('CTHD09', 'HD09', N'Chi phí sửa chữa theo phiếu SC09', 1, 800000),
    ('CTHD10', 'HD10', N'Chi phí sửa chữa theo phiếu SC10', 1, 2550000);

-- Du lieu canh bao ton kho mau: PT03 sap het, PT06 sap het, PT08 het hang
INSERT INTO CANH_BAO_TON_KHO (MaCanhBao, MaPhuTung, NgayCanhBao, LoaiCanhBao, TrangThaiDaXem) VALUES
    ('CB01', 'PT03', '2026-10-23 09:00:00', N'Sắp hết hàng', 0),
    ('CB02', 'PT06', '2026-10-23 09:05:00', N'Sắp hết hàng', 0),
    ('CB03', 'PT08', '2026-10-23 09:10:00', N'Hết hàng', 0);
GO


-- PHẦN 4: HỖ TRỢ DASHBOARD & JAVA DAO (VIEWS & PROCEDURES)


-- View 1: Thẻ KPI Tổng Quan Trang Chủ
GO
CREATE VIEW v_DashboardKPI AS
SELECT
    (SELECT ISNULL(SUM(TongTien), 0) FROM PHIEU_SUA_CHUA WHERE MONTH(NgaySuaChua) = 10 AND YEAR(NgaySuaChua) = 2026 AND TrangThai = N'Hoàn thành') AS DoanhThuThangHienTai,
    (SELECT ISNULL(SUM(TongTien), 0) FROM PHIEU_SUA_CHUA WHERE MONTH(NgaySuaChua) = 9 AND YEAR(NgaySuaChua) = 2026 AND TrangThai = N'Hoàn thành') AS DoanhThuThangTruoc,
    (SELECT COUNT(*) FROM PHIEU_TIEP_NHAN WHERE TrangThai = N'Chờ sửa chữa') AS SoXeChoSua,
    (SELECT COUNT(*) FROM PHIEU_TIEP_NHAN WHERE TrangThai = N'Đang sửa chữa') AS SoXeDangSua,
    (SELECT COUNT(*) FROM PHIEU_SUA_CHUA WHERE TrangThai = N'Hoàn thành' AND MaPhieuSuaChua NOT IN (SELECT MaPhieuSuaChua FROM HOA_DON)) AS SoXeChoThanhToan,
    (SELECT COUNT(*) FROM PHU_TUNG WHERE SoLuongTon = 0) AS SoPhuTungHetHang,
    (SELECT COUNT(*) FROM PHU_TUNG WHERE SoLuongTon > 0 AND SoLuongTon <= SoLuongToiThieu) AS SoPhuTungSapHet;
GO

-- View 2: Biểu đồ Tròn Thống kê theo Hãng XE
GO
CREATE VIEW v_ThongKeTheoHangXe AS
SELECT
    X.HieuXe,
    COUNT(PTN.MaPhieuTiepNhan) AS SoLuotTiepNhan,
    CAST(ROUND(COUNT(PTN.MaPhieuTiepNhan) * 100.0 / (SELECT COUNT(*) FROM PHIEU_TIEP_NHAN), 2) AS DECIMAL(5,2)) AS TyLePhanTram
FROM XE X
         JOIN PHIEU_TIEP_NHAN PTN ON X.BienSo = PTN.BienSo
GROUP BY X.HieuXe;
GO

-- View 3: Cảnh báo Phụ Tùng Tồn KHO
GO
CREATE VIEW v_PhuTungCanhBao AS
SELECT
    PT.MaPhuTung,
    PT.TenPhuTung,
    PT.SoLuongTon,
    PT.SoLuongToiThieu,
    K.TenKho,
    CASE
        WHEN PT.SoLuongTon = 0 THEN N'HẾT HÀNG'
        ELSE N'SẮP HẾT'
        END AS MucDoCanhBao
FROM PHU_TUNG PT
         JOIN KHO K ON PT.MaKho = K.MaKho
WHERE PT.SoLuongTon <= PT.SoLuongToiThieu;
GO

-- Stored Procedure: Dữ liệu Biểu đồ Doanh thu & Chi phí 12 Tháng theo Năm
GO
CREATE PROCEDURE sp_GetDoanhThuChiPhiTheoNam
    @Nam INT
AS
BEGIN
    SET NOCOUNT ON;
SELECT
    Thang.Thang,
    ISNULL(DT.TongDoanhThu, 0) AS TongDoanhThu,
    ISNULL(DT.TienDichVu, 0) AS TienDichVu,
    ISNULL(DT.TienPhuTung, 0) AS TienPhuTung,
    ISNULL(CP.TongChiPhiNhap, 0) AS ChiPhiNhapKho
FROM (
         SELECT 1 AS Thang UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6
         UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12
     ) AS Thang
         LEFT JOIN (
    SELECT
        MONTH(NgaySuaChua) AS Thang,
        SUM(TongTien) AS TongDoanhThu,
        SUM(TongTienDichVu) AS TienDichVu,
        SUM(TongTienPhuTung) AS TienPhuTung
    FROM PHIEU_SUA_CHUA
    WHERE YEAR(NgaySuaChua) = @Nam AND TrangThai = N'Hoàn thành'
    GROUP BY MONTH(NgaySuaChua)
) DT ON Thang.Thang = DT.Thang
         LEFT JOIN (
    SELECT
        MONTH(NgayNhap) AS Thang,
        SUM(TongTien) AS TongChiPhiNhap
    FROM PHIEU_NHAP_KHO
    WHERE YEAR(NgayNhap) = @Nam
    GROUP BY MONTH(NgayNhap)
) CP ON Thang.Thang = CP.Thang
ORDER BY Thang.Thang ASC;
END;
GO

PRINT N'=== KHỞI TẠO HỆ THỐNG QUẢN LÝ GARA Ô TÔ THÀNH CÔNG 100% ===';