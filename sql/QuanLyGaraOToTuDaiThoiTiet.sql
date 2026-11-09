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
-- PHẦN 1: TẠO CÁC BẢNG DDL
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

CREATE TABLE HANG_XE (
                         maHangXe VARCHAR(20) PRIMARY KEY,
                         tenHangXe NVARCHAR(100) NOT NULL,
                         xuatXu NVARCHAR(50)
);
GO

--Bảng Dòng Xe
CREATE TABLE DONG_XE (
                         maDongXe VARCHAR(20) PRIMARY KEY,
                         tenDongXe NVARCHAR(100) NOT NULL,
                         maHangXe VARCHAR(20),
                         FOREIGN KEY (maHangXe) REFERENCES HANG_XE(maHangXe) ON DELETE CASCADE
);
GO

-- 2. Bảng Xe
CREATE TABLE XE (
                    bienSo VARCHAR(20) PRIMARY KEY,
                    maDongXe VARCHAR(20),
                    namSanXuat INT,
                    maKhachHang VARCHAR(20),
                    FOREIGN KEY (maKhachHang) REFERENCES KHACH_HANG(maKhachHang) ON DELETE CASCADE,
                    FOREIGN KEY (maDongXe) REFERENCES DONG_XE(maDongXe)
);
GO

-- 3. Bảng Kỹ Thuật Viên
CREATE TABLE KY_THUAT_VIEN (
                               maKTV VARCHAR(20) PRIMARY KEY,
                               hoTen NVARCHAR(100) NOT NULL,
                               soDienThoai VARCHAR(15),
                               diaChi NVARCHAR(200),
                               ngayVaoLam DATETIME DEFAULT GETDATE(),
                               trangThai NVARCHAR(50)
);
GO

-- 4. Bảng Kế Toán (Đồng bộ ngayVaoLam)
CREATE TABLE KE_TOAN (
                         maNhanVien VARCHAR(20) PRIMARY KEY,
                         hoTen NVARCHAR(100) NOT NULL,
                         soDienThoai VARCHAR(15),
                         diaChi NVARCHAR(200),
                         ngayVaoLam DATETIME DEFAULT GETDATE(),
                         trangThai NVARCHAR(50)
);
GO

-- 5. Bảng Thủ Kho (Đồng bộ ngayVaoLam)
CREATE TABLE THU_KHO (
                         maNhanVien VARCHAR(20) PRIMARY KEY,
                         hoTen NVARCHAR(100) NOT NULL,
                         soDienThoai VARCHAR(15),
                         diaChi NVARCHAR(200),
                         ngayVaoLam DATETIME DEFAULT GETDATE(),
                         trangThai NVARCHAR(50)
);
GO

-- 6. Bảng Lễ Tân (Đồng bộ ngayVaoLam)
CREATE TABLE LE_TAN (
                        maNhanVien VARCHAR(20) PRIMARY KEY,
                        hoTen NVARCHAR(100) NOT NULL,
                        soDienThoai VARCHAR(15),
                        diaChi NVARCHAR(200),
                        ngayVaoLam DATETIME DEFAULT GETDATE(),
                        trangThai NVARCHAR(50)
);
GO

-- 7. Bảng Tài Khoản Đăng Nhập Tập Trung
CREATE TABLE TAI_KHOAN (
                           maTaiKhoan VARCHAR(20) PRIMARY KEY,
                           tenDangNhap VARCHAR(50) NOT NULL UNIQUE,
                           matKhau VARCHAR(100) NOT NULL,
                           loaiTaiKhoan NVARCHAR(50),
                           maNhanVien VARCHAR(20)
);
GO

-- 8. Bảng Phương Thức Thanh Toán
CREATE TABLE PHUONG_THUC_THANH_TOAN (
                                        maPhuongThuc VARCHAR(20) PRIMARY KEY,
                                        tenPhuongThuc NVARCHAR(50) NOT NULL
);
GO

-- 9. Bảng Danh Mục Dịch Vụ
CREATE TABLE DICH_VU (
                         maDichVu VARCHAR(20) PRIMARY KEY,
                         tenDichVu NVARCHAR(100) NOT NULL,
                         donGiaHienTai DECIMAL(18, 2),
                         moTa NVARCHAR(MAX),
                         thoiGianBaoHanh INT
);
GO

-- 10. Bảng Kho
CREATE TABLE KHO (
                     maKho VARCHAR(20) PRIMARY KEY,
                     tenKho NVARCHAR(100) NOT NULL,
                     diaChi NVARCHAR(200)
);
GO

-- 11. Bảng Nhà Cung Cấp
CREATE TABLE NHA_CUNG_CAP (
                              maNhaCungCap VARCHAR(20) PRIMARY KEY,
                              tenNhaCungCap NVARCHAR(100) NOT NULL,
                              soDienThoai VARCHAR(15),
                              diaChi NVARCHAR(200),
                              email VARCHAR(100)
);
GO

-- 12. Bảng Phụ Tùng
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

-- 13. Bảng Phiếu Tiếp Nhận
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

-- 14. Bảng Phiếu Sửa Chữa
CREATE TABLE PHIEU_SUA_CHUA (
                                maPhieuSuaChua VARCHAR(20) PRIMARY KEY,
                                ngayLap DATETIME DEFAULT GETDATE(),
                                ngayIn DATETIME,
                                ngayDuKienHoanThanh DATETIME,
                                trangThai NVARCHAR(50),
                                bienSo VARCHAR(20),
                                maKTV VARCHAR(20),
                                maPhieuTiepNhan VARCHAR(20),
                                loiPhatHien NVARCHAR(MAX),
                                nguyenNhan NVARCHAR(MAX),
                                deXuatSuaChua NVARCHAR(MAX),
                                tienDo INT DEFAULT 0,
                                ghiChu NVARCHAR(MAX),
                                ketQuaKCS NVARCHAR(50),
                                ghiChuKCS NVARCHAR(MAX),
                                ngayKiemTraKCS DATE,
                                FOREIGN KEY (bienSo) REFERENCES XE(bienSo),
                                FOREIGN KEY (maKTV) REFERENCES KY_THUAT_VIEN(maKTV),
                                FOREIGN KEY (maPhieuTiepNhan) REFERENCES PHIEU_TIEP_NHAN(maPhieuTiepNhan)
);
GO


-- 15. Bảng Chi Tiết Vật Tư Sửa Chữa
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

-- 16. Bảng Danh Sách Phụ Tùng Dùng Cho Báo Giá
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

-- 17. Bảng Chi Tiết Dịch Vụ Thực Hiện
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

-- 18. Bảng Hóa Đơn
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

-- 19. Bảng Chi Tiết Hóa Đơn
CREATE TABLE CHI_TIET_HOA_DON (
                                  maChiTietHD VARCHAR(20) PRIMARY KEY,
                                  maHD VARCHAR(20),
                                  noiDung NVARCHAR(MAX),
                                  soLuong INT,
                                  dongia DECIMAL(18, 2),
                                  FOREIGN KEY (maHD) REFERENCES HOA_DON(maHoaDon) ON DELETE CASCADE
);
GO

-- 20. Bảng Phiếu Nhập Kho
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

-- 21. Bảng Chi Tiết Phiếu Nhập Kho
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

-- 22. Bảng Cảnh Báo Tồn Kho
CREATE TABLE CANH_BAO_TON_KHO (
                                  maCanhBao VARCHAR(20) PRIMARY KEY,
                                  maPhuTung VARCHAR(20),
                                  ngayCanhBao DATETIME DEFAULT GETDATE(),
                                  loaiCanhBao NVARCHAR(50),
                                  trangThaiDaXem BIT,
                                  FOREIGN KEY (maPhuTung) REFERENCES PHU_TUNG(maPhuTung)
);
GO

-- 23. Bảng Phiếu Kiểm Kê
CREATE TABLE PHIEU_KIEM_KE (
                               maPhieuKiemKe VARCHAR(20) PRIMARY KEY,
                               ngayKiemKe DATETIME DEFAULT GETDATE(),
                               trangThai NVARCHAR(50),
                               maNhanVien VARCHAR(20),
                               FOREIGN KEY (maNhanVien) REFERENCES THU_KHO(maNhanVien)
);
GO

-- 24. Bảng Chi Tiết Kiểm Kê
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

-- 25. Bảng Danh Sách Khách Hàng Nợ
CREATE TABLE danhsachKHno (
                              maKhachHang VARCHAR(20) PRIMARY KEY,
                              hoTen NVARCHAR(50),
                              soDienThoai NVARCHAR(15),
                              hanMucNo DECIMAL(18,2),
                              tongNo DECIMAL(18,2),
                              trangThai NVARCHAR(50),
                              FOREIGN KEY (maKhachHang) REFERENCES KHACH_HANG(maKhachHang) ON DELETE CASCADE
);
GO

-- 26. Bảng lịch sử công nợ
CREATE TABLE LICH_SU_CONG_NO (
                                 maGiaoDich BIGINT IDENTITY(1,1) PRIMARY KEY,

                                 maKhachHang VARCHAR(20) NOT NULL,

                                 maHoaDon VARCHAR(20) NULL,

                                 ngayGiaoDich DATETIME NOT NULL
                                                              DEFAULT GETDATE(),

                                 noiDung NVARCHAR(200) NOT NULL,

                                 ghiNo DECIMAL(18,2) NOT NULL DEFAULT 0,

                                 ghiCo DECIMAL(18,2) NOT NULL DEFAULT 0,

                                 CONSTRAINT FK_LSCN_KHACH_HANG
                                     FOREIGN KEY (maKhachHang)
                                         REFERENCES KHACH_HANG(maKhachHang),

                                 CONSTRAINT FK_LSCN_HOA_DON
                                     FOREIGN KEY (maHoaDon)
                                         REFERENCES HOA_DON(maHoaDon),

                                 CONSTRAINT CK_LSCN_SoTien
                                     CHECK (
                                         ghiNo >= 0
                                             AND ghiCo >= 0
                                             AND NOT (ghiNo > 0 AND ghiCo > 0)
                                         )
);
-- 27 Bang ca lam viec
CREATE TABLE CA_LAM_VIEC (
                             maCa VARCHAR(20) PRIMARY KEY,
                             maNhanVien VARCHAR(20) NOT NULL,
                             thoiGianMoCa DATETIME NOT NULL DEFAULT GETDATE(),
                             thoiGianDongCa DATETIME NULL,
                             tienDauCa DECIMAL(18, 2) NOT NULL,
                             tienCuoiCa DECIMAL(18, 2) NULL,
                             chenhLech DECIMAL(18, 2) NULL,
                             trangThai NVARCHAR(30) NOT NULL DEFAULT N'Đang mở',
                             ghiChu NVARCHAR(500) NULL,

                             CONSTRAINT FK_CA_LAM_VIEC_KE_TOAN
                                 FOREIGN KEY (maNhanVien)
                                     REFERENCES KE_TOAN(maNhanVien),

                             CONSTRAINT CK_CA_LAM_VIEC_TIEN
                                 CHECK (
                                     tienDauCa >= 0
                                         AND (tienCuoiCa IS NULL OR tienCuoiCa >= 0)
                                     )
);
GO
-- 28 Bang thanh toan
CREATE TABLE THANH_TOAN (
                            maThanhToan VARCHAR(20) PRIMARY KEY,
                            maHoaDon VARCHAR(20) NOT NULL,
                            ngayThanhToan DATETIME NOT NULL DEFAULT GETDATE(),
                            soTienThanhToan DECIMAL(18,2) NOT NULL,
                            maPhuongThuc VARCHAR(20) NOT NULL,
                            maNhanVien VARCHAR(20) NOT NULL,
                            trangThai NVARCHAR(30) NOT NULL DEFAULT N'Đã thanh toán',
                            ghiChu NVARCHAR(500),

                            CONSTRAINT FK_THANH_TOAN_HOA_DON
                                FOREIGN KEY (maHoaDon)
                                    REFERENCES HOA_DON(maHoaDon),

                            CONSTRAINT FK_THANH_TOAN_PHUONG_THUC
                                FOREIGN KEY (maPhuongThuc)
                                    REFERENCES PHUONG_THUC_THANH_TOAN(maPhuongThuc),

                            CONSTRAINT FK_THANH_TOAN_KE_TOAN
                                FOREIGN KEY (maNhanVien)
                                    REFERENCES KE_TOAN(maNhanVien),

                            CONSTRAINT CK_THANH_TOAN_SO_TIEN
                                CHECK (soTienThanhToan >= 0)
);
GO

--29. Bảng Bảo Hành
CREATE TABLE BAO_HANH (
                          maPhieuSuaChua VARCHAR(20) NOT NULL,
                          maDichVu       VARCHAR(20) NOT NULL,
                          ngayKichHoat   DATETIME NOT NULL DEFAULT GETDATE(),
                          ngayHetHan     DATETIME NOT NULL,
                          dieuKhoan      NVARCHAR(MAX),
                          trangThai      NVARCHAR(30) NOT NULL DEFAULT N'Đang hiệu lực',
                          PRIMARY KEY (maPhieuSuaChua, maDichVu),
                          FOREIGN KEY (maPhieuSuaChua) REFERENCES PHIEU_SUA_CHUA(maPhieuSuaChua) ON DELETE CASCADE,
                          FOREIGN KEY (maDichVu) REFERENCES DICH_VU(maDichVu)
);
GOg

-- ==========================================================
-- PHẦN 2: THÊM DỮ LIỆU MẪU DML
-- ==========================================================

-- Khách Hàng & Nợ
INSERT INTO KHACH_HANG (maKhachHang, hoTen, soDienThoai, diaChi, email, bienSo) VALUES
                                                                                    ('KH01', N'Nguyễn Văn A', '0901234567', N'123 Nguyễn Trãi, Q1', 'nguyenvanA@gmail.com', '51G-123.45'),
                                                                                    ('KH02', N'Trần Thị B', '0912345678', N'456 Lê Văn Việt, TP. Thủ Đức', 'tranthiB@gmail.com', '60B-987.65'),
                                                                                    ('KH03', N'Lê Văn C', '0987654321', N'789 CMT8, Q3', 'levanC@gmail.com', '51K-555.88'),
                                                                                    ('KH04', N'Phạm Minh Dung', '0984444444', N'12 Hoàng Văn Thụ, Tân Bình', 'dung.pham@gmail.com', '51A-888.88'),
                                                                                    ('KH05', N'Đặng Quốc Hùng', '0985555555', N'88 Cộng Hòa, Tân Bình', 'hung.dang@gmail.com', '51K-999.99');

INSERT INTO danhsachKHno (maKhachHang, hoTen, soDienThoai, hanMucNo, tongNo, trangThai) VALUES
                                                                                            ('KH01', N'Nguyễn Văn A', '0901234567', 100000000, 30000000, N'Chưa vượt mức'),
                                                                                            ('KH02', N'Trần Thị B', '0912345678', 200000000, 10000000, N'Chưa vượt mức'),
                                                                                            ('KH03', N'Lê Văn C', '0987654321', 50000000, 2000000, N'Chưa vượt mức');

INSERT INTO HANG_XE (maHangXe, tenHangXe, xuatXu) VALUES
                                                      ('HX01', N'Toyota', N'Nhật Bản'),
                                                      ('HX02', N'Honda', N'Nhật Bản'),
                                                      ('HX03', N'Mazda', N'Nhật Bản'),
                                                      ('HX04', N'Hyundai', N'Hàn Quốc'),
                                                      ('HX05', N'Kia', N'Hàn Quốc'),
                                                      ('HX06', N'Mercedes-Benz', N'Đức'),
                                                      ('HX07', N'BMW', N'Đức'),
                                                      ('HX08', N'Audi', N'Đức'),
                                                      ('HX09', N'Ford', N'Mỹ'),
                                                      ('HX10', N'Chevrolet', N'Mỹ'),
                                                      ('HX11', N'Lexus', N'Nhật Bản'),
                                                      ('HX12', N'VinFast', N'Việt Nam'),
                                                      ('HX13', N'Nissan', N'Nhật Bản'),
                                                      ('HX14', N'Mitsubishi', N'Nhật Bản'),
                                                      ('HX15', N'Suzuki', N'Nhật Bản'),
                                                      ('HX16', N'Subaru', N'Nhật Bản'),
                                                      ('HX17', N'Porsche', N'Đức'),
                                                      ('HX18', N'Volvo', N'Thụy Điển'),
                                                      ('HX19', N'Peugeot', N'Pháp'),
                                                      ('HX20', N'Land Rover', N'Anh'),
                                                      ('HX21', N'Skoda', N'Cộng hòa Séc'),
                                                      ('HX22', N'MG', N'Anh / Trung Quốc'),
                                                      ('HX23', N'Haval', N'Trung Quốc'),
                                                      ('HX24', N'BYD', N'Trung Quốc'),
                                                      ('HX25', N'Wuling', N'Trung Quốc');
GO

INSERT INTO DONG_XE (maDongXe, tenDongXe, maHangXe) VALUES
('DX01', N'Vios', 'HX01'),
('DX02', N'Camry', 'HX01'),
('DX03', N'Civic', 'HX02'),
('DX04', N'CR-V', 'HX02'),
('DX05', N'Mazda 3', 'HX03'),
('DX06', N'CX-5', 'HX03'),
('DX07', N'Accent', 'HX04'),
('DX08', N'Tucson', 'HX04'),
('DX09', N'Morning', 'HX05'),
('DX10', N'Seltos', 'HX05'),
('DX11', N'C200', 'HX06'),
('DX12', N'E300', 'HX06'),
('DX13', N'320i', 'HX07'),
('DX14', N'X5', 'HX07'),
('DX15', N'A4', 'HX08'),
('DX16', N'Q5', 'HX08'),
('DX17', N'Ranger', 'HX09'),
('DX18', N'Everest', 'HX09'),
('DX19', N'Trailblazer', 'HX10'),
('DX20', N'RX350', 'HX11'),
('DX21', N'VF 5', 'HX12'),
('DX22', N'VF 8', 'HX12'),
('DX23', N'Almera', 'HX13'),
('DX24', N'Xpander', 'HX14'),
('DX25', N'XL7', 'HX15'),
('DX26', N'Forester', 'HX16'),
('DX27', N'Macan', 'HX17'),
('DX28', N'XC90', 'HX18'),
('DX29', N'3008', 'HX19'),
('DX30', N'Range Rover', 'HX20'),
('DX31', N'Kodiaq', 'HX21'),
('DX32', N'MG5', 'HX22'),
('DX33', N'H6', 'HX23'),
('DX34', N'Atto 3', 'HX24'),
('DX35', N'Hongguang Mini EV', 'HX25');
GO

-- Xe
INSERT INTO XE (bienSo, maDongXe, namSanXuat, maKhachHang) VALUES
('51G-123.45', 'DX01', 2022, 'KH01'),
('60B-987.65', 'DX05', 2023, 'KH02'),
('51K-555.88', 'DX03', 2021, 'KH03'),
('51A-888.88', 'DX12', 2022, 'KH04'),
('51K-999.99', 'DX08', 2023, 'KH05');
GO
-- Nhóm Nhân Sự (Đồng bộ ngayVaoLam cho tất cả)
INSERT INTO KY_THUAT_VIEN (maKTV, hoTen, soDienThoai, diaChi, ngayVaoLam, trangThai) VALUES
                                                                                         ('KTV01', N'Phan Xuân Phụng', '0901112233', N'Thủ Đức, TP.HCM', '2023-01-15', N'Đang rảnh'),
                                                                                         ('KTV02', N'Nguyễn Hoàng Nam', '0902223344', N'Quận 9, TP.HCM', '2023-05-20', N'Đang bận'),
                                                                                         ('KTV03', N'Lê Hoàng Long', '0903334455', N'Gò Vấp, TP.HCM', '2024-01-10', N'Đang rảnh');

INSERT INTO KE_TOAN (maNhanVien, hoTen, soDienThoai, diaChi, ngayVaoLam, trangThai) VALUES
                                                                                        ('KT01', N'Lê Duy Minh', '0934567890', N'Quận 1, TP.HCM', '2023-02-01', N'Đang làm việc'),
                                                                                        ('KT02', N'Phạm Thị Dung', '0933111222', N'Quận 3, TP.HCM', '2023-06-15', N'Đang làm việc');

INSERT INTO THU_KHO (maNhanVien, hoTen, soDienThoai, diaChi, ngayVaoLam, trangThai) VALUES
                                                                                        ('TK01', N'Nguyễn Quan Huy', '0912345678', N'Bình Thạnh, TP.HCM', '2023-03-10', N'Đang làm việc'),
                                                                                        ('TK02', N'Lê Duy Minh', '0923456789', N'Tân Bình, TP.HCM', '2023-07-01', N'Đang làm việc');

INSERT INTO LE_TAN (maNhanVien, hoTen, soDienThoai, diaChi, ngayVaoLam, trangThai) VALUES
                                                                                       ('LT01', N'Trần Thanh Liêm', '0935556677', N'Quận 5, TP.HCM', '2023-04-05', N'Đang làm việc'),
                                                                                       ('LT02', N'Nguyễn Mai Linh', '0938889900', N'Quận 10, TP.HCM', '2023-08-12', N'Đang làm việc');

-- Tài Khoản Đăng Nhập
INSERT INTO TAI_KHOAN (maTaiKhoan, tenDangNhap, matKhau, loaiTaiKhoan, maNhanVien) VALUES
                                                                                       ('ACC01', 'admin', 'admin123', N'admin', 'ADMIN'),
                                                                                       ('ACC02', 'huy0804', '123456', N'Thủ kho', 'TK01'),
                                                                                       ('ACC09', 'minh0804', '123456', N'Thủ kho', 'TK02'),
                                                                                       ('ACC03', 'minh0806', '123456', N'Kế toán', 'KT01'),
                                                                                       ('ACC10', 'dung0806', '123456', N'Kế toán', 'KT02'),
                                                                                       ('ACC04', 'liem1507', '123456', N'Lễ tân', 'LT01'),
                                                                                       ('ACC06', 'linh1507', '123456', N'Lễ tân', 'LT02'),
                                                                                       ('ACC05', 'phung2203', '123456', N'Kỹ thuật viên', 'KTV01'),
                                                                                       ('ACC07', 'nam2203', '123456', N'Kỹ thuật viên', 'KTV02'),
                                                                                       ('ACC08', 'long2203', '123456', N'Kỹ thuật viên', 'KTV03');

-- Ca Làm Việc
INSERT INTO CA_LAM_VIEC
(maCa, maNhanVien, thoiGianMoCa, thoiGianDongCa, tienDauCa, tienCuoiCa, chenhLech, trangThai, ghiChu)
VALUES
    ('CA01', 'KT01', '2026-10-30 07:30:00', '2026-10-30 17:30:00', 2000000, 5450000, 0, N'Đã đóng', N'Ca làm việc mẫu của kế toán Nguyễn Văn Minh'),
    ('CA02', 'KT02', '2026-10-31 07:30:00', NULL, 1500000, NULL, NULL, N'Đang mở', N'Ca làm việc đang mở');

-- Phương Thức Thanh Toán
INSERT INTO PHUONG_THUC_THANH_TOAN (maPhuongThuc, tenPhuongThuc) VALUES
                                                                     ('PT01', N'Tiền mặt'),
                                                                     ('PT02', N'Chuyển khoản'),
                                                                     ('PT03', N'Thẻ tín dụng');

-- Danh Mục Dịch Vụ
INSERT INTO DICH_VU (maDichVu, tenDichVu, donGiaHienTai, moTa, thoiGianBaoHanh) VALUES
                                                                                    ('DV01', N'Bảo dưỡng định kỳ cấp nhỏ', 350000, N'Thay nhớt, kiểm tra tổng quát cơ bản', 3),
                                                                                    ('DV02', N'Thay má phanh trước', 500000, N'Thay má phanh chính hãng trục trước', 6),
                                                                                    ('DV03', N'Cân chỉnh độ chụm', 400000, N'Cân chỉnh điện tử góc đặt bánh xe', 1),
                                                                                    ('DV04', N'Vệ sinh kim phun', 500000, N'Vệ sinh bằng dung dịch chuyên dụng', 1),
                                                                                    ('DV05', N'Thay nhớt động cơ', 100000, N'Công thay nhớt và lọc nhớt', 0),
                                                                                    ('DV06', N'Thay lọc gió động cơ', 50000, N'Thay thế lọc gió chính hãng', 1),
                                                                                    ('DV07', N'Đảo lốp và cân bằng động', 200000, N'Cân bằng mâm và vị trí các lốp xe', 3),
                                                                                    ('DV08', N'Kiểm tra điện lạnh', 150000, N'Kiểm tra áp suất gas và lốc lạnh', 1),
                                                                                    ('DV09', N'Rửa xe ô tô cơ bản', 100000, N'Rửa ngoại thất, vệ sinh kính và lau khô xe', 0),
                                                                                    ('DV10', N'Rửa xe ô tô cao cấp', 180000, N'Rửa ngoại thất, vệ sinh nội thất cơ bản và chăm sóc lốp', 0),
                                                                                    ('DV11', N'Vệ sinh nội thất ô tô', 450000, N'Hút bụi, vệ sinh taplo, ghế và các bề mặt nội thất', 0),
                                                                                    ('DV12', N'Đánh bóng thân xe', 1200000, N'Làm sạch và đánh bóng bề mặt sơn xe', 0),
                                                                                    ('DV13', N'Kiểm tra tổng quát xe', 200000, N'Kiểm tra động cơ, phanh, lốp, hệ thống điện và gầm', 0),
                                                                                    ('DV14', N'Vệ sinh hệ thống điều hòa', 350000, N'Vệ sinh lọc gió và các đường ống hệ thống điều hòa', 0),
                                                                                    ('DV15', N'Bảo dưỡng hệ thống phanh', 300000, N'Kiểm tra, vệ sinh và xả gió hệ thống phanh', 3),
                                                                                    ('DV16', N'Thay nước làm mát', 200000, N'Súc rửa và thay nước làm mát động cơ', 3),
                                                                                    ('DV17', N'Thay bugi đánh lửa', 150000, N'Thay thế bộ bugi đánh lửa chính hãng', 6),
                                                                                    ('DV18', N'Thay lọc xăng', 300000, N'Thay thế lọc nhiên liệu định kỳ', 6),
                                                                                    ('DV19', N'Thay lọc gió điều hòa', 120000, N'Thay lọc gió máy lạnh cabin', 1),
                                                                                    ('DV20', N'Thay dây curoa tổng', 450000, N'Thay thế dây curoa dẫn động chính hãng', 12),
                                                                                    ('DV21', N'Thay phuộc nhún trước', 1500000, N'Thay giảm xóc trước chính hãng', 12),
                                                                                    ('DV22', N'Thay phuộc nhún sau', 1200000, N'Thay giảm xóc sau chính hãng', 12),
                                                                                    ('DV23', N'Láng đĩa phanh', 350000, N'Gia công phẳng bề mặt đĩa phanh', 3),
                                                                                    ('DV24', N'Thay má phanh sau', 450000, N'Thay thế má phanh trục sau chính hãng', 6),
                                                                                    ('DV25', N'Súc rửa hộp số tự động', 800000, N'Thay dầu và làm sạch cặn bẩn hộp số', 6),
                                                                                    ('DV26', N'Thay dầu trợ lực lái', 250000, N'Xả e và thay mới dầu trợ lực', 3),
                                                                                    ('DV27', N'Thay dầu phanh', 300000, N'Xả gió và thay toàn bộ dầu phanh mới', 6),
                                                                                    ('DV28', N'Kiểm tra hệ thống gầm', 150000, N'Kiểm tra ro-tuyn, thanh giằng, cao su gầm xe', 0),
                                                                                    ('DV29', N'Thay thế ro-tuyn lái', 400000, N'Thay thế ro-tuyn lái trong và ngoài', 6),
                                                                                    ('DV30', N'Thay thế cao su càng A', 500000, N'Ép và thay thế cao su giảm chấn càng A', 6),
                                                                                    ('DV31', N'Cân chỉnh góc đặt bánh xe 3D', 450000, N'Cân chỉnh độ chụm và góc đặt bánh xe công nghệ 3D', 3),
                                                                                    ('DV32', N'Thay bình ắc-quy ô tô', 1800000, N'Thay thế bình ắc quy chính hãng mới', 12),
                                                                                    ('DV33', N'Bảo dưỡng máy phát điện', 400000, N'Kiểm tra, vệ sinh cổ góp và chổi than', 3),
                                                                                    ('DV34', N'Sửa chữa còi xe', 100000, N'Kiểm tra, đấu nối hoặc thay rơ-le còi', 3),
                                                                                    ('DV35', N'Thay thế bóng đèn pha', 800000, N'Nâng cấp hoặc thay thế bóng đèn chiếu sáng', 6),
                                                                                    ('DV36', N'Thay thế mô tơ gạt mưa', 450000, N'Thay thế cụm mô tơ gạt kính chắn gió', 6),
                                                                                    ('DV37', N'Thay lưỡi gạt mưa', 150000, N'Thay cao su gạt mưa trước và sau', 1),
                                                                                    ('DV38', N'Vệ sinh họng hút bướm ga', 250000, N'Làm sạch muội than bám ở bướm ga', 1),
                                                                                    ('DV39', N'Phủ gầm chống rỉ sét', 1800000, N'Phủ hóa chất chống mọt gầm xe ô tô', 12),
                                                                                    ('DV40', N'Dán phim cách nhiệt kính lái', 1500000, N'Dán phim cách nhiệt cao cấp chống tia UV', 36),
                                                                                    ('DV41', N'Dán phim cách nhiệt toàn xe', 4500000, N'Dán phim cách nhiệt toàn bộ các kính xe', 36),
                                                                                    ('DV42', N'Khử mùi nội thất Ozone', 250000, N'Khử mùi hôi, diệt khuẩn khoang xe', 0),
                                                                                    ('DV43', N'Vệ sinh khoang máy', 400000, N'Rửa sạch và dưỡng bóng khoang động cơ', 1),
                                                                                    ('DV44', N'Đánh bóng kính lái xóa xước', 500000, N'Xử lý vết ố mốc và xước dăm kính xe', 0),
                                                                                    ('DV45', N'Phục hồi đèn pha ố vàng', 350000, N'Đánh bóng và phủ bảo vệ chóa đèn pha', 6),
                                                                                    ('DV46', N'Lắp đặt camera hành trình', 200000, N'Đi dây và cài đặt camera hành trình trước/sau', 12),
                                                                                    ('DV47', N'Lắp cảm biến áp suất lốp', 300000, N'Lắp bộ cảm biến van hiển thị màn hình', 12),
                                                                                    ('DV48', N'Lắp màn hình Android ô tô', 500000, N'Cắm giắc zin, cấu hình màn hình giải trí thông minh', 12),
                                                                                    ('DV49', N'Đọc và xóa lỗi hộp đen ECU', 150000, N'Quét mã lỗi hệ thống điện tử trên xe', 0),
                                                                                    ('DV50', N'Vá lốp ô tô không săm', 50000, N'Vá nấm hoặc vá ép lốp bị thủng', 1);

-- Kho & Nhà Cung Cấp
INSERT INTO KHO (maKho, tenKho, diaChi) VALUES
                                            ('KHO01', N'Kho Quận 1', N'10 Võ Văn Kiệt, Quận 1'),
                                            ('KHO02', N'Kho Tân Phú', N'50 Luỹ Bán Bích, Tân Phú');

INSERT INTO NHA_CUNG_CAP (maNhaCungCap, tenNhaCungCap, soDienThoai, diaChi, email) VALUES
                                                                                       ('NCC01', N'CTY Phụ Tùng Sài Gòn', '02838222333', N'12 Lý Tự Trọng', 'contact@ptsaigon.vn'),
                                                                                       ('NCC02', N'Toyota Vietnam', '02839998888', N'KCN Biên Hòa 2', 'support@toyotavn.com');

-- Phụ Tùng
INSERT INTO PHU_TUNG (maPhuTung, tenPhuTung, donViTinh, donGiaNhap, donGiaBan, soLuongTon, maKho) VALUES
                                                                                                      ('PT01', N'Nhớt động cơ Castrol 4L', N'Bình', 800000, 1000000, 45, 'KHO01'),
                                                                                                      ('PT02', N'Má phanh trước', N'Bộ', 350000, 500000, 15, 'KHO01'),
                                                                                                      ('PT03', N'Lọc gió điều hòa', N'Cái', 120000, 200000, 30, 'KHO02'),
                                                                                                      ('PT04', N'Bơm cao áp', N'Cái', 4500000, 6200000, 50, 'KHO01'),
                                                                                                      ('PT05', N'Lọc gió động cơ', N'Cái', 150000, 220000, 30, 'KHO01'),
                                                                                                      ('PT06', N'Bugi đánh lửa ô tô', N'Cái', 100000, 160000, 40, 'KHO02'),
                                                                                                      ('PT07', N'Bình ắc quy ô tô 12V', N'Bình', 1200000, 1550000, 20, 'KHO01'),
                                                                                                      ('PT08', N'Dây curoa cam', N'Sợi', 450000, 600000, 18, 'KHO02'),
                                                                                                      ('PT09', N'Giảm xóc trước ô tô', N'Cái', 850000, 1100000, 12, 'KHO01'),
                                                                                                      ('PT10', N'Đĩa phanh trước', N'Cái', 500000, 680000, 20, 'KHO02'),
                                                                                                      ('PT11', N'Bộ ly hợp ô tô', N'Bộ', 1800000, 2400000, 3, 'KHO01'),
                                                                                                      ('PT12', N'Máy phát điện ô tô', N'Cái', 2200000, 2900000, 2, 'KHO02'),
                                                                                                      ('PT13', N'Máy khởi động ô tô', N'Cái', 1800000, 2500000, 4, 'KHO01'),
                                                                                                      ('PT14', N'Két nước làm mát ô tô', N'Cái', 1500000, 2000000, 15, 'KHO02'),
                                                                                                      ('PT15', N'Bơm nước làm mát động cơ', N'Cái', 550000, 750000, 25, 'KHO01'),
                                                                                                      ('PT16', N'Dầu phanh DOT 4', N'Chai', 90000, 140000, 35, 'KHO01'),
                                                                                                      ('PT17', N'Dầu hộp số tự động', N'Can', 320000, 450000, 25, 'KHO02'),
                                                                                                      ('PT18', N'Nước làm mát động cơ 4L', N'Can', 150000, 230000, 30, 'KHO01'),
                                                                                                      ('PT19', N'Lưỡi gạt mưa cao cấp', N'Cái', 70000, 120000, 50, 'KHO02'),
                                                                                                      ('PT20', N'Bóng đèn pha LED H7', N'Cái', 250000, 380000, 20, 'KHO01'),
                                                                                                      ('PT21', N'Rô-tuyn lái ngoài', N'Cái', 180000, 280000, 16, 'KHO02'),
                                                                                                      ('PT22', N'Bạc đạn bánh xe trước', N'Cái', 450000, 650000, 12, 'KHO01'),
                                                                                                      ('PT23', N'Lọc nhớt động cơ', N'Cái', 60000, 100000, 60, 'KHO02'),
                                                                                                      ('PT24', N'Lọc nhiên liệu (Lọc xăng)', N'Cái', 200000, 320000, 18, 'KHO01'),
                                                                                                      ('PT25', N'Dây curoa tổng (Alternator)', N'Sợi', 280000, 420000, 22, 'KHO02');

-- Phiếu Tiếp Nhận
INSERT INTO PHIEU_TIEP_NHAN
(maPhieuTiepNhan, ngayTiepNhan, tinhTrangXe, yeuCauKH, trangThai, bienSo)
VALUES
    ('PTN01', '2026-02-15 08:30:00', N'Dầu động cơ đổi màu, xe đến hạn bảo dưỡng', N'Bảo dưỡng định kỳ và kiểm tra tổng quát', N'Đã chuyển sửa chữa', '51G-123.45'),
    ('PTN02', '2026-03-20 09:00:00', N'Má phanh trước bị mòn', N'Kiểm tra và thay má phanh trước', N'Đã chuyển sửa chữa', '60B-987.65'),
    ('PTN03', '2026-05-15 08:30:00', N'Động cơ rung giật, đèn Check Engine sáng', N'Kiểm tra nguyên nhân động cơ rung giật', N'Đã chuyển sửa chữa', '51A-888.88'),
    ('PTN04', '2026-08-20 09:00:00', N'Xe hao xăng, vận hành kém', N'Kiểm tra và vệ sinh kim phun', N'Đã chuyển sửa chữa', '51K-999.99'),
    ('PTN05', '2026-09-15 08:00:00', N'Điều hòa làm mát yếu', N'Kiểm tra hệ thống điều hòa', N'Đã chuyển sửa chữa', '51K-555.88'),
    ('PTN06', '2026-10-24 10:45:00', N'Dây curoa có dấu hiệu rạn nứt', N'Kiểm tra hệ thống truyền động và sạc', N'Đang sửa chữa', '51G-123.45'),
    ('PTN07', '2026-10-28 08:30:00', N'Cần kiểm tra hệ thống gầm', N'Kiểm tra và bảo dưỡng hệ thống treo', N'Đang sửa chữa', '51A-888.88');
GO

-- Phiếu Nhập Kho
INSERT INTO PHIEU_NHAP_KHO
    (maPhieuNhap, ngayNhap, tongTienNhap, maNhanVien, maNhaCungCap)
VALUES
('PNK01', '2026-01-10 08:30:00', 19500000, 'TK01', 'NCC01'),
('PNK02', '2026-04-12 09:15:00', 24300000, 'TK02', 'NCC02');
GO

-- Chi Tiết Phiếu Nhập Kho
INSERT INTO CHI_TIET_PHIEU_NHAP
    (maPhieuNhap, maPhuTung, soLuongNhap, donGiaNhap, thanhTien)
VALUES
('PNK01', 'PT01', 20, 800000, 16000000),
('PNK01', 'PT02', 10, 350000, 3500000),
('PNK02', 'PT03', 15, 120000, 1800000),
('PNK02', 'PT04', 5, 4500000, 22500000);
GO

-- Cảnh Báo Tồn Kho
INSERT INTO CANH_BAO_TON_KHO
    (maCanhBao, maPhuTung, ngayCanhBao, loaiCanhBao, trangThaiDaXem)
VALUES
('CBTK01', 'PT02', '2026-10-25 08:00:00', N'Kiểm tra mức tồn kho', 0),
('CBTK02', 'PT04', '2026-10-28 08:00:00', N'Đối chiếu số lượng tồn kho', 1);
GO

-- Phiếu Kiểm Kê
INSERT INTO PHIEU_KIEM_KE
    (maPhieuKiemKe, ngayKiemKe, trangThai, maNhanVien)
VALUES
('PKK01', '2026-10-25 08:00:00', N'Đã hoàn thành', 'TK01'),
('PKK02', '2026-10-30 08:00:00', N'Đã hoàn thành', 'TK02');
GO

-- Chi Tiết Kiểm Kê
INSERT INTO CHI_TIET_KIEM_KE
    (maPhieuKiemKe, maPhuTung, soLuongHeThong, soLuongThucTe, chenhLech, lyDoChenhLech)
VALUES
('PKK01', 'PT01', 45, 45, 0, N'Không có chênh lệch'),
('PKK01', 'PT02', 15, 14, -1, N'Thiếu 1 bộ khi kiểm kê'),
('PKK01', 'PT03', 30, 30, 0, N'Không có chênh lệch'),
('PKK01', 'PT04', 50, 50, 0, N'Không có chênh lệch'),
('PKK02', 'PT01', 45, 45, 0, N'Không có chênh lệch'),
('PKK02', 'PT02', 15, 15, 0, N'Đã đối chiếu lại'),
('PKK02', 'PT03', 30, 29, -1, N'Thiếu 1 cái khi kiểm kê'),
('PKK02', 'PT04', 50, 50, 0, N'Không có chênh lệch');
GO

-- Phiếu Sửa Chữa
INSERT INTO PHIEU_SUA_CHUA (
    maPhieuSuaChua,
    ngayLap,
    ngayIn,
    ngayDuKienHoanThanh,
    trangThai,
    bienSo,
    maKTV,
    maPhieuTiepNhan,
    loiPhatHien,
    nguyenNhan,
    deXuatSuaChua,
    tienDo,
    ghiChu,
    ketQuaKCS,
    ghiChuKCS,
    ngayKiemTraKCS
)
VALUES
    (
        'PSC01', '2026-02-15 09:00:00', '2026-02-15 11:00:00',
        '2026-02-15 17:00:00', N'Hoàn thành',
        '51G-123.45', 'KTV01', 'PTN01',
        N'Dầu nhớt động cơ đã đen, lọc nhớt bám nhiều cặn bẩn.',
        N'Xe tới hạn bảo dưỡng định kỳ (đã chạy đủ 5000km).',
        N'Thay nhớt động cơ, thay lọc nhớt, vệ sinh lọc gió.',
        100,
        N'Đã hoàn tất thay nhớt và kiểm tra động cơ tổng quát.',
        N'Đạt',
        N'Động cơ hoạt động ổn định, không phát hiện tiếng ồn bất thường.',
        '2026-02-16'
    ),
    (
        'PSC02', '2026-03-20 10:00:00', '2026-03-20 12:00:00',
        '2026-03-20 16:00:00', N'Hoàn thành',
        '60B-987.65', 'KTV02', 'PTN02',
        N'Má phanh trước mòn mỏng, gần sát tới đĩa phanh. Đĩa phanh có vệt xước nhẹ.',
        N'Hao mòn tự nhiên trong quá trình sử dụng.',
        N'Thay bộ má phanh trước mới, vệ sinh cụm heo dầu phanh.',
        100,
        N'Đã thay má phanh và vệ sinh heo dầu thành công.',
        N'Không đạt',
        N'Hệ thống phanh chưa đạt yêu cầu, cần kiểm tra và điều chỉnh lại.',
        '2026-03-21'
    ),
    (
        'PSC03', '2026-05-15 09:00:00', '2026-05-15 10:30:00',
        '2026-05-16 17:00:00', N'Hoàn thành',
        '51A-888.88', 'KTV01', 'PTN03',
        N'Máy nổ rung giật mạnh ở vòng tua thấp, đồng hồ báo đèn Check Engine.',
        N'Chết bô bin đánh lửa máy số 2, bugi bám muội đen.',
        N'Thay bô bin máy số 2, vệ sinh lại 4 bugi và dùng máy đọc lỗi xóa mã lỗi.',
        100,
        N'Đã thay bô bin và xóa mã lỗi trên xe.',
        N'Đạt',
        N'Hệ thống đèn chiếu sáng hoạt động tốt, các kết nối điện ổn định.',
        '2026-05-17'
    ),
    (
        'PSC04', '2026-08-20 10:00:00', '2026-08-20 11:30:00',
        '2026-08-21 17:00:00', N'Hoàn thành',
        '51K-999.99', 'KTV02', 'PTN04',
        N'Xe chạy ỳ, hao xăng hơn bình thường, kim phun có hiện tượng đái xăng do cặn bẩn.',
        N'Sử dụng xăng kém chất lượng lâu ngày sinh cặn.',
        N'Xúc rửa súc béc, vệ sinh kim phun bằng dung dịch chuyên dụng 3M.',
        100,
        N'Đã vệ sinh kim phun, xe chạy bốc trở lại.',
        N'Đạt',
        N'Đã khắc phục tình trạng rung lắc, xe vận hành bình thường khi chạy thử.',
        '2026-08-22'
    ),
    (
        'PSC05', '2026-09-15 08:30:00', '2026-09-15 10:00:00',
        '2026-09-16 17:00:00', N'Hoàn thành',
        '51K-555.88', 'KTV01', 'PTN05',
        N'Điều hòa làm mát yếu, nhiệt độ trong cabin không đạt yêu cầu.',
        N'Hệ thống điều hòa thiếu gas, lọc gió điều hòa bám nhiều bụi bẩn.',
        N'Kiểm tra rò rỉ gas, nạp gas điều hòa và thay lọc gió cabin.',
        100,
        N'Đã nạp gas và thay lọc gió cabin.',
        N'Không đạt',
        N'Điều hòa làm mát chưa đủ công suất, yêu cầu kiểm tra lại hệ thống gas.',
        '2026-09-17'
    ),
    (
        'PSC06', '2026-10-24 11:30:00', '2026-10-24 13:00:00',
        '2026-10-26 17:00:00', N'Đang sửa chữa',
        '51G-123.45', 'KTV01', 'PTN06',
        N'Dây curoa máy phát rạn nứt nhiều đường, ắc quy đo được dòng điện thấp.',
        N'Linh kiện cao su lão hóa theo thời gian, bình ắc quy xuống cấp.',
        N'Thay dây curoa tổng, kích sạc lại ắc quy kiểm tra thêm.',
        60,
        N'Đang tháo dây curoa cũ, chuẩn bị lắp dây mới.',
        N'Chưa kiểm tra',
        N'Đang trong quá trình sửa chữa, chưa thực hiện KCS.',
        '2026-10-26'
    ),
    (
        'PSC07', '2026-10-28 09:00:00', '2026-10-28 10:00:00',
        '2026-11-03 17:00:00', N'Đang sửa chữa',
        '51A-888.88', 'KTV01', 'PTN07',
        N'Kiểm tra tổng quát định kỳ các chi tiết gầm máy.',
        N'Xe di chuyển đường dài thường xuyên.',
        N'Bảo dưỡng hệ thống treo và siết lại ốc gầm.',
        40,
        N'Đang tiến hành kiểm tra phần gầm xe.',
        N'Chưa kiểm tra',
        N'Đang thực hiện, chờ hoàn thiện để KCS.',
        '2026-11-03'
    );
GO
-- Chi Tiết Giao Dịch
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

INSERT INTO HOA_DON (maHoaDon, ngayLap, maPhuongThuc, maPhieuSuaChua, maNhanVien) VALUES
                                                                                      ('HD01', '2026-02-15 11:45:00', 'PT01', 'PSC01', 'KT01'),
                                                                                      ('HD02', '2026-03-20 12:00:00', 'PT02', 'PSC02', 'KT01'),
                                                                                      ('HD03', '2026-05-15 14:00:00', 'PT01', 'PSC03', 'KT01'),
                                                                                      ('HD04', '2026-08-20 15:00:00', 'PT02', 'PSC04', 'KT01');

-- Thanh Toán
INSERT INTO THANH_TOAN
(maThanhToan, maHoaDon, ngayThanhToan, soTienThanhToan, maPhuongThuc, maNhanVien, trangThai, ghiChu)
VALUES
    ('TT01', 'HD01', '2026-02-15 11:45:00', 1350000, 'PT01', 'KT01', N'Đã thanh toán', N'Thanh toán tiền mặt'),
    ('TT02', 'HD02', '2026-03-20 12:00:00', 1500000, 'PT02', 'KT01', N'Đã thanh toán', N'Thanh toán chuyển khoản'),
    ('TT03', 'HD03', '2026-05-15 14:00:00', 600000, 'PT01', 'KT01', N'Đã thanh toán', N'Thanh toán tiền mặt'),
    ('TT04', 'HD04', '2026-08-20 15:00:00', 500000, 'PT02', 'KT01', N'Đã thanh toán', N'Thanh toán chuyển khoản');


INSERT INTO CHI_TIET_HOA_DON (maChiTietHD, maHD, noiDung, soLuong, dongia) VALUES
                                                                               ('CTHD01', 'HD01', N'Dịch vụ và phụ tùng', 1, 1350000),
                                                                               ('CTHD02', 'HD02', N'Dịch vụ và phụ tùng', 1, 1500000),
                                                                               ('CTHD03', 'HD03', N'Dịch vụ và phụ tùng', 1, 600000),
                                                                               ('CTHD04', 'HD04', N'Dịch vụ và phụ tùng', 1, 500000);
INSERT INTO LICH_SU_CONG_NO
(
    maKhachHang,
    ngayGiaoDich,
    noiDung,
    ghiNo,
    ghiCo
)
VALUES
    (
        'KH01',
        '2023-10-01 00:00:00',
        N'Số dư đầu kỳ',
        0,
        0
    ),
    (
        'KH01',
        '2023-10-01 09:00:00',
        N'Sửa xe 29H-123.45',
        20000000,
        0
    ),

    (
        'KH01',
        '2023-10-15 10:00:00',
        N'Sửa xe 29H-456.78',
        15000000,
        0
    ),
    (
        'KH01',
        '2023-10-26 14:00:00',
        N'Thu tiền mặt KH',
        0,
        10000000
    );
USE QuanLyGaraOToTuDaiThoiTiet;
GO

INSERT INTO LICH_SU_CONG_NO
    (maKhachHang, maHoaDon, ngayGiaoDich, noiDung, ghiNo, ghiCo)
VALUES
-- Khách hàng KH01
('KH01', NULL, '2023-09-01 08:15:00', N'Phát sinh công nợ sửa chữa xe',2500000, 0),
('KH01', NULL, '2023-09-03 10:30:00', N'Thanh toán một phần công nợ',0, 1000000),
('KH01', NULL, '2023-09-05 14:45:00', N'Thay dầu và bảo dưỡng xe',1200000, 0),
('KH01', NULL, '2023-09-10 09:20:00', N'Thanh toán công nợ',0, 500000),
('KH01', NULL, '2023-09-15 16:10:00', N'Thay má phanh',1800000, 0),
('KH01', NULL, '2023-09-20 11:05:00', N'Thanh toán một phần',0, 800000),
('KH01', NULL, '2023-10-02 13:25:00', N'Sửa chữa hệ thống điện',2200000, 0),
('KH01', NULL, '2023-10-12 15:40:00', N'Thanh toán công nợ',0, 700000),
('KH01', NULL, '2023-10-20 08:50:00', N'Thay lốp xe',3000000, 0),
('KH01', NULL, '2023-10-26 17:30:00', N'Thanh toán cuối kỳ',0, 1000000);
GO

USE QuanLyGaraOToTuDaiThoiTiet;
GO

-- =========================
-- DỮ LIỆU LỊCH SỬ CÔNG NỢ KH02
-- =========================
INSERT INTO LICH_SU_CONG_NO
    (maKhachHang, maHoaDon, ngayGiaoDich, noiDung, ghiNo, ghiCo)
VALUES
('KH02', NULL, '2023-09-02 08:25:00', N'Sửa chữa động cơ xe', 3500000, 0),
('KH02', NULL, '2023-09-04 11:15:00', N'Thanh toán một phần công nợ', 0, 1500000),
('KH02', NULL, '2023-09-08 14:30:00', N'Thay bình ắc quy', 1800000, 0),
('KH02', NULL, '2023-09-12 09:40:00', N'Thanh toán công nợ', 0, 800000),
('KH02', NULL, '2023-09-18 16:20:00', N'Sửa chữa hệ thống phanh', 2400000, 0),
('KH02', NULL, '2023-09-22 10:10:00', N'Thanh toán một phần', 0, 1000000),
('KH02', NULL, '2023-10-03 13:35:00', N'Bảo dưỡng định kỳ', 1500000, 0),
('KH02', NULL, '2023-10-11 15:50:00', N'Thanh toán công nợ', 0, 1200000),
('KH02', NULL, '2023-10-19 08:45:00', N'Thay lốp xe', 2800000, 0),
('KH02', NULL, '2023-10-27 17:15:00', N'Thanh toán cuối kỳ', 0, 1000000);
GO


-- =========================
-- DỮ LIỆU LỊCH SỬ CÔNG NỢ KH03
-- =========================
INSERT INTO LICH_SU_CONG_NO
    (maKhachHang, maHoaDon, ngayGiaoDich, noiDung, ghiNo, ghiCo)
VALUES
('KH03', NULL, '2023-09-01 09:10:00', N'Thay dầu và lọc dầu', 900000, 0),
('KH03', NULL, '2023-09-06 13:20:00', N'Thanh toán công nợ', 0, 400000),
('KH03', NULL, '2023-09-09 10:45:00', N'Sửa chữa hệ thống điện', 2100000, 0),
('KH03', NULL, '2023-09-14 15:30:00', N'Thanh toán một phần công nợ', 0, 700000),
('KH03', NULL, '2023-09-19 08:50:00', N'Thay má phanh', 1600000, 0),
('KH03', NULL, '2023-09-25 11:25:00', N'Thanh toán công nợ', 0, 600000),
('KH03', NULL, '2023-10-04 14:05:00', N'Sửa chữa hệ thống làm mát', 2500000, 0),
('KH03', NULL, '2023-10-13 09:35:00', N'Thanh toán một phần', 0, 1000000),
('KH03', NULL, '2023-10-21 16:40:00', N'Thay lốp và cân bằng bánh xe', 3200000, 0),
('KH03', NULL, '2023-10-28 10:55:00', N'Thanh toán cuối kỳ', 0, 1500000);
GO
INSERT INTO LICH_SU_CONG_NO
    (maKhachHang, maHoaDon, ngayGiaoDich, noiDung, ghiNo, ghiCo)
VALUES
-- Khách hàng KH01
('KH04', NULL, '2023-09-01 08:15:00', N'Phát sinh công nợ sửa chữa xe',25000000, 0),
('KH04', NULL, '2023-09-03 10:30:00', N'Thanh toán một phần công nợ',0, 10000000),
('KH04', NULL, '2023-09-05 14:45:00', N'Thay dầu và bảo dưỡng xe',12000000, 0),
('KH04', NULL, '2023-09-10 09:20:00', N'Thanh toán công nợ',0, 5000000),
('KH04', NULL, '2023-09-15 16:10:00', N'Thay má phanh',18000000, 0),
('KH04', NULL, '2023-09-20 11:05:00', N'Thanh toán một phần',0, 8000000),
('KH04', NULL, '2023-10-02 13:25:00', N'Sửa chữa hệ thống điện',22000000, 0),
('KH04', NULL, '2023-10-12 15:40:00', N'Thanh toán công nợ',0, 7000000),
('KH04', NULL, '2023-10-20 08:50:00', N'Thay lốp xe',30000000, 0),
('KH04', NULL, '2023-10-26 17:30:00', N'Thanh toán cuối kỳ',0, 10000000);
GO
INSERT INTO LICH_SU_CONG_NO
    (maKhachHang, maHoaDon, ngayGiaoDich, noiDung, ghiNo, ghiCo)
VALUES
-- Khách hàng KH01
('KH05', NULL, '2023-09-01 08:15:00', N'Phát sinh công nợ sửa chữa xe',250000, 0),
('KH05', NULL, '2023-09-03 10:30:00', N'Thanh toán một phần công nợ',0, 100000),
('KH05', NULL, '2023-09-05 14:45:00', N'Thay dầu và bảo dưỡng xe',120000, 0),
('KH05', NULL, '2023-09-10 09:20:00', N'Thanh toán công nợ',0, 50000),
('KH05', NULL, '2023-09-15 16:10:00', N'Thay má phanh',180000, 0),
('KH05', NULL, '2023-09-20 11:05:00', N'Thanh toán một phần',0, 80000),
('KH05', NULL, '2023-10-02 13:25:00', N'Sửa chữa hệ thống điện',220000, 0),
('KH05', NULL, '2023-10-12 15:40:00', N'Thanh toán công nợ',0, 70000),
('KH05', NULL, '2023-10-20 08:50:00', N'Thay lốp xe',300000, 0),
('KH05', NULL, '2023-10-26 17:30:00', N'Thanh toán cuối kỳ',0, 100000);
GO
insert into danhsachKHno values
                             ('KH04','Phạm Minh Dung','0984444444',10000000000,5000000,N'Chưa vượt mức'),
                             ('KH05','Đặng Quốc Hùng','0985555555',20000000000,70000000,N'Chưa vượt mức');
GO
-- ==========================================================
-- PHẦN 3: VIEWS & PROCEDURES THỐNG KÊ DASHBOARD
-- ==========================================================

-- View Thống kê sửa chữa
    IF OBJECT_ID('v_ThongKeSuaChua', 'V') IS NOT NULL DROP VIEW v_ThongKeSuaChua;
GO
CREATE VIEW v_ThongKeSuaChua AS
SELECT
    p.maPhieuSuaChua,
    p.ngayLap,
    p.maKTV,
    p.trangThai,
    COUNT(ct.maPhuTung) AS tongSoPhuTungDung,
    SUM(ISNULL(ct.soLuong * ct.donGia, 0)) AS tongTienVatTu
FROM PHIEU_SUA_CHUA p
         LEFT JOIN CHI_TIET_PHIEU_SUA_CHUA ct ON p.maPhieuSuaChua = ct.maPhieuSuaChua
GROUP BY p.maPhieuSuaChua, p.ngayLap, p.maKTV, p.trangThai;
GO

-- View Thống kê doanh thu theo tháng/năm
IF OBJECT_ID('v_ThongKeDoanhThu', 'V') IS NOT NULL DROP VIEW v_ThongKeDoanhThu;
GO
CREATE VIEW v_ThongKeDoanhThu AS
SELECT
        MONTH(hd.ngayLap) AS thang,
        YEAR(hd.ngayLap) AS nam,
        COUNT(hd.maHoaDon) AS tongSoHoaDon,
        SUM(ISNULL(ct.soLuong * ct.dongia, 0)) AS doanhThu
        FROM HOA_DON hd
        LEFT JOIN CHI_TIET_HOA_DON ct ON hd.maHoaDon = ct.maHD
        GROUP BY MONTH(hd.ngayLap), YEAR(hd.ngayLap);
GO

-- View Thống kê kho
IF OBJECT_ID('v_ThongKeKho', 'V') IS NOT NULL DROP VIEW v_ThongKeKho;
GO
CREATE VIEW v_ThongKeKho AS
SELECT
    p.maPhuTung,
    p.tenPhuTung,
    p.donViTinh,
    p.soLuongTon,
    p.donGiaNhap,
    p.donGiaBan,
    CASE
        WHEN p.soLuongTon <= 5 THEN N'Sắp hết'
        ELSE N'Bình thường'
        END AS trangThaiTon
FROM PHU_TUNG p;
GO

-- View KPI Trang chủ Dashboard
IF OBJECT_ID('v_Dashboard_KPI', 'V') IS NOT NULL DROP VIEW v_Dashboard_KPI;
GO
CREATE VIEW v_Dashboard_KPI AS
SELECT
    (SELECT COUNT(*) FROM PHIEU_TIEP_NHAN WHERE trangThai = N'Chờ sửa chữa' OR trangThai = N'Đang xử lý') AS SoXeDangXuLy,
    (SELECT COUNT(*) FROM HOA_DON WHERE MONTH(ngayLap) = MONTH(GETDATE()) AND YEAR(ngayLap) = YEAR(GETDATE())) AS SoHoaDonThangNay,
    (SELECT ISNULL(SUM(soLuong * dongia), 0) FROM CHI_TIET_HOA_DON HD JOIN HOA_DON H ON HD.maHD = H.maHoaDon WHERE MONTH(H.ngayLap) = MONTH(GETDATE()) AND YEAR(H.ngayLap) = YEAR(GETDATE())) AS DoanhThuThangNay,
    (SELECT COUNT(*) FROM PHU_TUNG WHERE soLuongTon < 5) AS SoPhuTungSapHet;
GO

-- View Cảnh báo tồn kho
IF OBJECT_ID('v_Dashboard_CanhBaoKho', 'V') IS NOT NULL DROP VIEW v_Dashboard_CanhBaoKho;
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

-- Procedure Thống kê doanh thu 12 tháng
IF OBJECT_ID('sp_ThongKeDoanhThu12Thang', 'P') IS NOT NULL DROP PROCEDURE sp_ThongKeDoanhThu12Thang;
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
PRINT N'Khởi tạo CSDL thành công';