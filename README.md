# Task Management API

Đây là backend RESTful API cho ứng dụng quản lý công việc (tương tự như Trello phiên bản thu nhỏ). Project được build bằng Spring Boot, tập trung vào việc quản lý Board/Task, phân quyền người dùng và bảo mật API bằng JWT.

## 🚀 Công nghệ sử dụng
* **Ngôn ngữ:** Java 17
* **Framework chính:** Spring Boot 3.5.x
* **Database:** MySQL
* **Bảo mật:** Spring Security, JWT (JSON Web Token)
* **Khác:** Spring Data JPA, Lombok, Maven

## ✨ Tính năng nổi bật
* **Xác thực & Phân quyền:**
  * Đăng ký, đăng nhập bằng JWT.
  * Phân quyền Role-based: `ADMIN` (có thể xem toàn bộ data) và `USER` (chỉ xem và thao tác trên bảng/công việc của mình).
* **Quản lý Session (Refresh Token):**
  * Hỗ trợ Refresh Token xoay vòng (Token Rotation) giúp bảo mật tốt hơn.
  * Tự động phát hiện và block nếu Refresh Token bị lộ/tái sử dụng.
  * Giới hạn tối đa 5 thiết bị đăng nhập cùng lúc cho mỗi user.
  * Có **Cron job** tự động chạy lúc 2h sáng mỗi ngày để dọn dẹp các token đã hết hạn trong database.
* **Core:** CRUD Bảng công việc (Board) và Công việc (Task - Trạng thái: TODO, DOING, DONE).

## 🛠 Hướng dẫn cài đặt & Chạy project

### 1. Yêu cầu hệ thống
* Đã cài đặt JDK 17
* Đã cài đặt MySQL Server

### 2. Cấu hình Database & Biến môi trường
Mặc định ứng dụng sẽ trỏ tới database MySQL ở `localhost:3306/task_management`. Nó sẽ tự động tạo database nếu chưa có.

Tuy nhiên, bạn cần cung cấp các biến môi trường (Environment Variables) trước khi chạy. Bạn có thể cấu hình trực tiếp trên IDE (IntelliJ/Eclipse) hoặc export ra terminal:

```bash
DB_USERNAME=root                 # Username MySQL của bạn
DB_PASSWORD=123456               # Password MySQL
JWT_SECRET=mot_chuoi_bi_mat_rat_dai_va_kho_doan_duoc_encode_base_64_nhe
JWT_EXPIRATION=3600000           # Thời gian sống của Access Token (VD: 3600000 ms = 1 tiếng)
