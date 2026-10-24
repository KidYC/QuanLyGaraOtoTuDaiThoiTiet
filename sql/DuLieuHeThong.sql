use QuanLyGaraOToTuDaiThoiTiet

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
    FOREIGN KEY (maKhachHang) REFERENCES KHACH_HANG(maKhachHang)
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

-- 5. Bảng Phương Thức Thanh Toán
CREATE TABLE PHUONG_THUC_THANH_TOAN (
    maPhuongThuc VARCHAR(20) PRIMARY KEY,
    tenPhuongThuc NVARCHAR(50) NOT NULL
);
GO

-- 6. Bảng Dịch Vụ
CREATE TABLE DICH_VU (
    maDichVu VARCHAR(20) PRIMARY KEY,
    tenDichVu NVARCHAR(100) NOT NULL,
    donGiaHienTai DECIMAL(18, 2),
    moTa NVARCHAR(MAX),
    thoiGianBaoHanh INT -- Tính bằng tháng
);
GO

-- 7. Bảng Kho
CREATE TABLE KHO (
    maKho VARCHAR(20) PRIMARY KEY,
    tenKho NVARCHAR(100) NOT NULL,
    diaChi NVARCHAR(200)
);
GO

-- 8. Bảng Nhà Cung Cấp
CREATE TABLE NHA_CUNG_CAP (
    maNhaCungCap VARCHAR(20) PRIMARY KEY,
    tenNhaCungCap NVARCHAR(100) NOT NULL,
    soDienThoai VARCHAR(15),
    diaChi NVARCHAR(200),
    email VARCHAR(100)
);
GO

-- 9. Bảng Thủ Kho
create TABLE THU_KHO (
    maNhanVien VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    soDienThoai VARCHAR(15),
    tenDangNhap VARCHAR(50) UNIQUE,
    matKhau VARCHAR(100)
);
GO


-- 10. Bảng Phụ Tùng
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

-- 11. Bảng Phiếu Tiếp Nhận
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

-- 12. Bảng Phiếu Sửa Chữa
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

-- 13. Bảng Chi Tiết Phiếu Sửa Chữa
CREATE TABLE CHI_TIET_PHIEU_SUA_CHUA (
    maPhieuSuaChua VARCHAR(20),
    maPhuTung VARCHAR(20),
    soLuong INT,
    donGia DECIMAL(18, 2),
    PRIMARY KEY (maPhieuSuaChua, maPhuTung),
    FOREIGN KEY (maPhieuSuaChua) REFERENCES PHIEU_SUA_CHUA(maPhieuSuaChua),
    FOREIGN KEY (maPhuTung) REFERENCES PHU_TUNG(maPhuTung)
);
GO

-- 14. Bảng Chi Tiết Dịch Vụ
CREATE TABLE CHI_TIET_DICH_VU (
    maPhieuSuaChua VARCHAR(20),
    maDichVu VARCHAR(20),
    soLuong INT,
    donGia DECIMAL(18, 2),
    thanhTien DECIMAL(18, 2),
    PRIMARY KEY (maPhieuSuaChua, maDichVu),
    FOREIGN KEY (maPhieuSuaChua) REFERENCES PHIEU_SUA_CHUA(maPhieuSuaChua),
    FOREIGN KEY (maDichVu) REFERENCES DICH_VU(maDichVu)
);
GO

-- 15. Bảng Hóa Đơn
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

-- 16. Bảng Chi Tiết Hóa Đơn
CREATE TABLE CHI_TIET_HOA_DON (
    maChiTietHD VARCHAR(20) PRIMARY KEY,
    maHD VARCHAR(20),
    noiDung NVARCHAR(MAX),
    soLuong INT,
    dongia DECIMAL(18, 2),
    FOREIGN KEY (maHD) REFERENCES HOA_DON(maHoaDon)
);
GO

-- 17. Bảng Phiếu Nhập Kho
CREATE TABLE PHIEU_NHAP_KHO (
    maPhieuNhap VARCHAR(20) PRIMARY KEY,
    ngayNhap DATETIME DEFAULT GETDATE(),
    tongTienNhap DECIMAL(18, 2),
    maNhanVien VARCHAR(20), -- Mã thủ kho
    maNhaCungCap VARCHAR(20),
    FOREIGN KEY (maNhanVien) REFERENCES THU_KHO(maNhanVien),
    FOREIGN KEY (maNhaCungCap) REFERENCES NHA_CUNG_CAP(maNhaCungCap)
);
GO

-- 18. Bảng Chi Tiết Phiếu Nhập
CREATE TABLE CHI_TIET_PHIEU_NHAP (
    maPhieuNhap VARCHAR(20),
    maPhuTung VARCHAR(20),
    soLuongNhap INT,
    donGiaNhap DECIMAL(18, 2),
    thanhTien DECIMAL(18, 2),
    PRIMARY KEY (maPhieuNhap, maPhuTung),
    FOREIGN KEY (maPhieuNhap) REFERENCES PHIEU_NHAP_KHO(maPhieuNhap),
    FOREIGN KEY (maPhuTung) REFERENCES PHU_TUNG(maPhuTung)
);
GO

-- 19. Bảng Cảnh Báo Tồn Kho
CREATE TABLE CANH_BAO_TON_KHO (
    maCanhBao VARCHAR(20) PRIMARY KEY,
    maPhuTung VARCHAR(20),
    ngayCanhBao DATETIME DEFAULT GETDATE(),
    loaiCanhBao NVARCHAR(50),
    trangThaiDaXem BIT,
    FOREIGN KEY (maPhuTung) REFERENCES PHU_TUNG(maPhuTung)
);
GO

-- 20. Bảng Phiếu Kiểm Kê
CREATE TABLE PHIEU_KIEM_KE (
    maPhieuKiemKe VARCHAR(20) PRIMARY KEY,
    ngayKiemKe DATETIME DEFAULT GETDATE(),
    trangThai NVARCHAR(50),
    maNhanVien VARCHAR(20), -- Mã thủ kho
    FOREIGN KEY (maNhanVien) REFERENCES THU_KHO(maNhanVien)
);
GO

-- 21. Bảng Chi Tiết Kiểm Kê
CREATE TABLE CHI_TIET_KIEM_KE (
    maPhieuKiemKe VARCHAR(20),
    maPhuTung VARCHAR(20),
    soLuongHeThong INT,
    soLuongThucTe INT,
    chenhLech INT,
    lyDoChenhLech NVARCHAR(MAX),
    PRIMARY KEY (maPhieuKiemKe, maPhuTung),
    FOREIGN KEY (maPhieuKiemKe) REFERENCES PHIEU_KIEM_KE(maPhieuKiemKe),
    FOREIGN KEY (maPhuTung) REFERENCES PHU_TUNG(maPhuTung)
);
GO
-- 22. Bảng Tài Khoản
CREATE TABLE TAI_KHOAN (
    maTaiKhoan VARCHAR(20) PRIMARY KEY,
    tenDangNhap VARCHAR(50) NOT NULL UNIQUE,
    matKhau VARCHAR(100) NOT NULL,
    loaiTaiKhoan NVARCHAR(50), -- 'Thủ kho', 'Kế toán', 'Quản lý'
    maNhanVien VARCHAR(20)     -- Có thể map với mã nhân viên tương ứng
);
GO


--23. Bảng danh sách khách hàng nợ
create table danhsachKHno(
    maKhachHang varchar(20) primary key,
    hoTen nvarchar(50),
	soDienThoai nvarchar(15),
	hanMucNo nvarchar(30),
	tongNo nvarchar(30),
	trangThai nvarchar(50),
	foreign key (maKhachHang) references KHACH_HANG(maKhachHang)
);


-- Bang danhsachKHno
insert into danhsachKHno (maKhachHang,hoTen,soDienThoai,hanMucNo,tongNo,trangThai) values
('KH01',N'Nguyễn Văn A', '0901234567','100000000','30000000',N'Chưa vượt mức'),
('KH02',N'Trần Thị B', '0912345678','200000000','10000000',N'Chưa vượt mức'),
('KH03',N'Lê Văn C', '0987654321','50000000','2000000',N'Chưa vượt mức');

-- Bảng Khách Hàng
INSERT INTO KHACH_HANG (maKhachHang, hoTen, soDienThoai, diaChi,email,bienSo) VALUES
('KH01', N'Nguyễn Văn A', '0901234567', N'123 Nguyễn Trãi, Quận 1, TP.HCM','nguyenvanA@gmail.com','51G-123.45'),
('KH02', N'Trần Thị B', '0912345678', N'456 Lê Văn Việt, TP. Thủ Đức','tranthiB@gmail.com','60B-987.65'),
('KH03', N'Lê Văn C', '0987654321', N'789 Cách Mạng Tháng 8, Quận 3, TP.HCM','levanC@gmail.com','51K-555.88');

-- Bảng Kỹ Thuật Viên
INSERT INTO KY_THUAT_VIEN (maKTV, hoTen, diaChi, ngayVaoLam, trangThai) VALUES
('KTV01', N'Nguyễn Văn Tài', N'12 Phạm Văn Đồng, Thủ Đức', '2023-05-15', N'Đang rảnh'),
('KTV02', N'Trần Đức Mạnh', N'45 Lê Đức Thọ, Gò Vấp', '2022-11-20', N'Đang bận sửa xe'),
('KTV03', N'Lê Hoàng Long', N'78 Nguyễn Thái Sơn, Gò Vấp', '2024-01-10', N'Đang rảnh');

-- Bảng Kế Toán
INSERT INTO KE_TOAN (maNhanVien, hoTen, soDienThoai) VALUES
('KT01', N'Phạm Thị Dung', '0933111222'),
('KT02', N'Hoàng Văn Nam', '0944333555');

-- Bảng Phương Thức Thanh Toán
INSERT INTO PHUONG_THUC_THANH_TOAN (maPhuongThuc, tenPhuongThuc) VALUES
('PT01', N'Tiền mặt'),
('PT02', N'Chuyển khoản ngân hàng'),
('PT03', N'Thẻ tín dụng / Thẻ ATM');

-- Bảng Dịch Vụ
INSERT INTO DICH_VU (maDichVu, tenDichVu, donGiaHienTai, moTa, thoiGianBaoHanh) VALUES
('DV01', N'Bảo dưỡng định kỳ 5000km', 350000, N'Thay nhớt, kiểm tra tổng quát gầm máy', 3),
('DV02', N'Thay má phanh trước', 500000, N'Thay thế bộ má phanh chính hãng', 6),
('DV03', N'Cân chỉnh độ chụm bánh xe (Lái)', 400000, N'Sử dụng máy điện tử cân chỉnh thước lái', 1);

-- Bảng Kho
INSERT INTO KHO (maKho, tenKho, diaChi) VALUES
('KHO01', N'Kho Phụ Tùng Chính Hãng Quận 1', N'10 Võ Văn Kiệt, Quận 1, TP.HCM'),
('KHO02', N'Kho Vật Tư Phụ Tân Phú', N'50 Luỹ Bán Bích, Tân Phú, TP.HCM');

-- Bảng Nhà Cung Cấp
INSERT INTO NHA_CUNG_CAP (maNhaCungCap, tenNhaCungCap, soDienThoai, diaChi, email) VALUES
('NCC01', N'Công ty TNHH Phụ Tùng Ô Tô Sài Gòn', '02838222333', N'12 Lý Tự Trọng, Quận 1, TP.HCM', 'contact@ptsaigon.vn'),
('NCC02', N'Toyota Motor Vietnam Distributor', '02839998888', N'KCN Biên Hòa 2, Đồng Nai', 'support@toyotavn.com');

-- Bảng Thủ Kho
INSERT INTO THU_KHO (maNhanVien, hoTen, soDienThoai, tenDangNhap) VALUES
('TK01', N'Vũ Đình Khoa', '0977888999', 'khoa.vd'),
('TK02', N'Bùi Thị Hoa', '0966554433', 'hoa.bt');



-- ==========================================================
-- 2. CÁC BẢNG PHỤ THUỘC CẤP 1
-- ==========================================================

-- Bảng Xe (Phụ thuộc KHACH_HANG)
INSERT INTO XE (bienSo, hangXe, dongXe, namSanXuat, maKhachHang) VALUES
('51G-123.45', 'Toyota', 'Vios', 2022, 'KH01'),
('60B-987.65', 'Mazda', 'Mazda 3', 2023, 'KH02'),
('51K-555.88', 'Honda', 'Civic', 2021, 'KH03');

-- Bảng Phụ Tùng (Phụ thuộc KHO)
INSERT INTO PHU_TUNG (maPhuTung, tenPhuTung, donViTinh, donGiaNhap, donGiaBan, soLuongTon, maKho) VALUES
('PT01', N'Nhớt động cơ Castrol 4L', N'Bình', 800000, 1000000, 45, 'KHO01'),
('PT02', N'Má phanh trước Toyota Vios', N'Bộ', 350000, 500000, 15, 'KHO01'),
('PT03', N'Lọc gió điều hòa Mazda', N'Cái', 120000, 200000, 30, 'KHO02');


-- ==========================================================
-- 3. CÁC BẢNG PHỤ THUỘC CẤP 2
-- ==========================================================

-- Bảng Phiếu Tiếp Nhận (Phụ thuộc XE)
INSERT INTO PHIEU_TIEP_NHAN (maPhieuTiepNhan, ngayTiepNhan, tinhTrangXe, yeuCauKH, trangThai, bienSo) VALUES
('PTN01', '2026-10-01 08:30:00', N'Xe trầy xước nhẹ cản trước, nhớt cũ', N'Bảo dưỡng định kỳ và kiểm tra phanh', N'Đã tiếp nhận', '51G-123.45'),
('PTN02', '2026-10-02 09:15:00', N'Xe hoạt động bình thường', N'Thay má phanh trước', N'Đang xử lý', '60B-987.65');

-- Bảng Phiếu Nhập Kho (Phụ thuộc THU_KHO, NHA_CUNG_CAP)
INSERT INTO PHIEU_NHAP_KHO (maPhieuNhap, ngayNhap, tongTienNhap, maNhanVien, maNhaCungCap) VALUES
('PN01', '2026-09-25 10:00:00', 48500000, 'TK01', 'NCC01'),
('PN02', '2026-09-28 14:00:00', 12000000, 'TK02', 'NCC02');

-- Bảng Phiếu Kiểm Kê (Phụ thuộc THU_KHO)
INSERT INTO PHIEU_KIEM_KE (maPhieuKiemKe, ngayKiemKe, trangThai, maNhanVien) VALUES
('PKK01', '2026-09-30 17:00:00', N'Đã hoàn thành', 'TK01');


-- ==========================================================
-- 4. CÁC BẢNG PHỤ THUỘC CẤP 3 TRỞ LÊN
-- ==========================================================

-- Bảng Phiếu Sửa Chữa (Phụ thuộc XE, KY_THUAT_VIEN, PHIEU_TIEP_NHAN)
INSERT INTO PHIEU_SUA_CHUA (maPhieuSuaChua, ngayLap, ngayIn, trangThai, bienSo, maKTV, maPhieuTiepNhan) VALUES
('PSC01', '2026-10-01 09:00:00', '2026-10-01 11:30:00', N'Hoàn thành', '51G-123.45', 'KTV01', 'PTN01');

-- Bảng Chi Tiết Phiếu Nhập (Phụ thuộc PHIEU_NHAP_KHO, PHU_TUNG)
INSERT INTO CHI_TIET_PHIEU_NHAP (maPhieuNhap, maPhuTung, soLuongNhap, donGiaNhap, thanhTien) VALUES
('PN01', 'PT01', 50, 800000, 40000000),
('PN01', 'PT02', 20, 350000, 7000000),
('PN02', 'PT03', 50, 120000, 6000000);

-- Bảng Cảnh Báo Tồn Kho (Phụ thuộc PHU_TUNG)
INSERT INTO CANH_BAO_TON_KHO (maCanhBao, maPhuTung, ngayCanhBao, loaiCanhBao, trangThaiDaXem) VALUES
('CB01', 'PT02', '2026-10-10 08:00:00', N'Sắp hết hàng', 0);

-- Bảng Chi Tiết Kiểm Kê (Phụ thuộc PHIEU_KIEM_KE, PHU_TUNG)
INSERT INTO CHI_TIET_KIEM_KE (maPhieuKiemKe, maPhuTung, soLuongHeThong, soLuongThucTe, chenhLech, lyDoChenhLech) VALUES
('PKK01', 'PT01', 45, 45, 0, N'Khớp số lượng'),
('PKK01', 'PT02', 16, 15, -1, N'Thất thoát 1 bộ trong quá trình vận chuyển kho');

-- Bảng Chi Tiết Phiếu Sửa Chữa (Phụ thuộc PHIEU_SUA_CHUA, PHU_TUNG)
INSERT INTO CHI_TIET_PHIEU_SUA_CHUA (maPhieuSuaChua, maPhuTung, soLuong, donGia) VALUES
('PSC01', 'PT01', 1, 1000000),
('PSC01', 'PT02', 2, 500000);

-- Bảng Chi Tiết Dịch Vụ (Phụ thuộc PHIEU_SUA_CHUA, DICH_VU)
INSERT INTO CHI_TIET_DICH_VU (maPhieuSuaChua, maDichVu, soLuong, donGia, thanhTien) VALUES
('PSC01', 'DV01', 1, 350000, 350000),
('PSC01', 'DV02', 1, 500000, 500000);

-- Bảng Hóa Đơn (Phụ thuộc PHUONG_THUC_THANH_TOAN, PHIEU_SUA_CHUA, KE_TOAN)
INSERT INTO HOA_DON (maHoaDon, ngayLap, maPhuongThuc, maPhieuSuaChua, maNhanVien) VALUES
('HD01', '2026-10-01 11:45:00', 'PT01', 'PSC01', 'KT01');

-- Bảng Chi Tiết Hóa Đơn (Phụ thuộc HOA_DON)
INSERT INTO CHI_TIET_HOA_DON (maChiTietHD, maHD, noiDung, soLuong, dongia) VALUES
('CTHD01', 'HD01', N'Thanh toán tiền dịch vụ bảo dưỡng và phụ tùng thay thế', 1, 2350000);

-- Bảng Tài Khoản
INSERT INTO TAI_KHOAN (maTaiKhoan, tenDangNhap, matKhau, loaiTaiKhoan, maNhanVien) VALUES
('TK01', 'minh0806', '123456', N'Thủ kho', 'TK01'),
('TK02', 'phung2203', '123456', N'Thủ kho', 'TK02'),
('TK03', 'liem1507', '123456', N'Kế toán', 'KT01'),
('TK04', 'huy0804', '123456', N'Admin', 'NULL');






