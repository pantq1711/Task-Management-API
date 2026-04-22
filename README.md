Task Management API

Đây là backend RESTful API cho ứng dụng quản lý công việc (tương tự như Trello phiên bản thu nhỏ). Project được build bằng Spring Boot, tập trung vào việc quản lý Board/Task, phân quyền người dùng và bảo mật API bằng JWT.

🚀 Công nghệ sử dụng
Ngôn ngữ: Java 17

Framework chính: Spring Boot 3.5.x

Database: MySQL

Bảo mật: Spring Security, JWT (JSON Web Token)

Khác: Spring Data JPA, Lombok, Maven

✨ Tính năng nổi bật
Xác thực & Phân quyền:

Đăng ký, đăng nhập bằng JWT.

Phân quyền Role-based: ADMIN (có thể xem toàn bộ data) và USER (chỉ xem và thao tác trên bảng/công việc của mình).

Quản lý Session (Refresh Token):

Hỗ trợ Refresh Token xoay vòng (Token Rotation) giúp bảo mật tốt hơn.

Tự động phát hiện và block nếu Refresh Token bị lộ/tái sử dụng.

Giới hạn tối đa 5 thiết bị đăng nhập cùng lúc cho mỗi user.

Có Cron job tự động chạy lúc 2h sáng mỗi ngày để dọn dẹp các token đã hết hạn trong database.

Core: CRUD Bảng công việc (Board) và Công việc (Task - Trạng thái: TODO, DOING, DONE).

🛠 Hướng dẫn cài đặt & Chạy project
1. Yêu cầu hệ thống
Đã cài đặt JDK 17

Đã cài đặt MySQL Server

2. Cấu hình Database & Biến môi trường
Mặc định ứng dụng sẽ trỏ tới database MySQL ở localhost:3306/task_management. Nó sẽ tự động tạo database nếu chưa có.

Tuy nhiên, bạn cần cung cấp các biến môi trường (Environment Variables) trước khi chạy. Bạn có thể cấu hình trực tiếp trên IDE (IntelliJ/Eclipse) hoặc export ra terminal:

Bash
DB_USERNAME=               # Username MySQL của bạn
DB_PASSWORD=               # Password MySQL
JWT_SECRET=mot_chuoi_bi_mat_rat_dai_va_kho_doan_duoc_encode_base_64_nhe
JWT_EXPIRATION=3600000           # Thời gian sống của Access Token (VD: 3600000 ms = 1 tiếng)
3. Build và Chạy
Mở terminal ở thư mục gốc của project và chạy lệnh sau (có tích hợp sẵn Maven Wrapper nên không cần cài Maven ngoài):

Trên Windows:

DOS
mvnw clean install
mvnw spring-boot:run
Trên Linux/Mac:

Bash
./mvnw clean install
./mvnw spring-boot:run
Server sẽ khởi chạy ở port mặc định: http://localhost:8080

📡 API Endpoints cơ bản
Dưới đây là một số endpoint chính của ứng dụng. (Cần đính kèm header Authorization: Bearer <access_token> cho các API bị khóa).

Authentication
POST /api/auth/register: Đăng ký tài khoản mới.

POST /api/auth/login: Đăng nhập (trả về Access Token & Refresh Token).

POST /api/auth/refresh-token: Cấp lại Access Token mới.

Boards
GET /api/boards/my-boards: Lấy danh sách Board của user đang đăng nhập.

GET /api/boards/{id}: Xem chi tiết 1 Board.

POST /api/boards/create: Tạo Board mới.

PUT /api/boards/update/{id}: Cập nhật tên Board.

DELETE /api/boards/{id}: Xóa Board.

GET /api/boards/all: Lấy tất cả Board của toàn hệ thống (Chỉ dành cho ADMIN).

Tasks
GET /api/tasks/my-tasks: Lấy danh sách Task của user đang đăng nhập.

POST /api/tasks/create: Tạo Task mới (Gán vào 1 Board cụ thể).

PUT /api/tasks/update/{id}: Cập nhật trạng thái (TODO/DOING/DONE), thời gian của Task.

DELETE /api/tasks/{id}: Xóa Task.

GET /api/tasks/all: Lấy tất cả Task (Chỉ dành cho ADMIN).

📝 Cấu trúc thư mục (Packages)
controller/: Chứa các REST API endpoint.

service/: Chứa logic nghiệp vụ (Business logic).

repository/: Các interface tương tác với DB qua Spring Data JPA.

entity/: Các model ánh xạ trực tiếp với bảng trong MySQL.

dto/: Các object dùng để hứng data từ client gởi lên hoặc format data trả về.

config/ & filter/: Cấu hình Spring Security và bộ lọc chặn JWT.

exception/: Bắt và xử lý lỗi Global.
