# 皮埃诺预约系统

> 一个为高校学生设计的个人练琴室在线预约平台，采用图书馆式即时预约机制：提交即生效，先到先得，高并发安全。

---

## 技术栈

| 层次   | 技术                                               |
|------|--------------------------------------------------|
| 前端   | Vue 3 · Vite · Element Plus · Pinia · TypeScript |
| 后端   | Spring Boot 3 · MyBatis · JWT                    |
| 数据库  | MySQL 8.0                                        |
| 反向代理 | Nginx 1.28.0                                     |

---

## 项目结构

```
finish3.0/
├── piano-room-vue3/            # Vue 3 前端项目
│   ├── src/
│   │   ├── api/                # Axios 请求封装
│   │   ├── composables/        # useSound 等组合式函数
│   │   ├── layouts/            # FrontLayout / AdminLayout
│   │   ├── stores/             # Pinia (auth / settings)
│   │   ├── views/              # 页面组件
│   │   └── main.ts
│   └── vite.config.ts
├── music-booking-system/       # Spring Boot 后端项目
│   └── src/main/java/com/bookingsystem/
│       ├── controller/         # REST 控制器
│       ├── service/            # 业务逻辑
│       ├── mapper/             # MyBatis Mapper
│       └── pojo/               # 实体类
├── db/
│   └── classroombookingdb.sql  # 数据库初始化脚本
├── nginx-1.28.0/
│   └── conf/nginx.conf         # Nginx 配置
└── docs/
    └── API.md                  # 接口文档
```

---

## 环境要求

| 工具       | 版本要求      |
|----------|-----------|
| JDK      | 17+       |
| Maven    | 3.8+      |
| MySQL    | 8.0+      |
| Node.js  | 18+       |
| Nginx    | 1.28.0（已内置）|

---

## 快速启动

### 1. 数据库初始化

在 MySQL 8.0 中执行以下命令：

```sql
CREATE DATABASE classroombookingdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE classroombookingdb;
SOURCE db/classroombookingdb.sql;
```

> 也可以直接用 Navicat / DBeaver 等工具导入 `db/classroombookingdb.sql`。

---

### 2. 后端配置与启动

**第一步：修改数据库密码**

打开 `music-booking-system/src/main/resources/application.yml`，修改以下字段：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/classroombookingdb?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: your_password_here   # ← 改为你的 MySQL 密码
```

**第二步（可选）：配置邮件服务**

如需启用邮件通知功能，继续修改 `application.yml`：

```yaml
spring:
  mail:
    host: smtp.163.com       # 邮件服务器，如 smtp.qq.com / smtp.163.com
    port: 25                 # 端口（SSL 用 465，STARTTLS 用 25 或 587）
    username: your_email@163.com     # 你的邮箱账号
    password: your_email_auth_code   # 邮箱授权码（非登录密码，需在邮箱设置中开启 SMTP）
```

> 不配置邮件也不影响核心预约功能正常使用。

**第三步：编译并启动**

```bash
cd music-booking-system
mvn package -DskipTests
java -jar target/classroom-booking-system-0.0.1-SNAPSHOT.jar
```

后端默认监听 **http://localhost:8099**

---

### 3. 前端安装与构建

```bash
cd piano-room-vue3
npm install
npm run build
# 构建产物输出到 piano-room-vue3/dist/
```

> 开发模式（热更新）：`npm run dev`，默认访问 http://localhost:5173

---

### 4. Nginx 配置与启动

**第一步：修改 Nginx 配置文件**

打开 `nginx-1.28.0/conf/nginx.conf`，找到 `root` 指令，将路径改为前端构建产物的绝对路径：

```nginx
server {
    listen       82;
    
    location / {
        root   D:/nobody/finish3.0/piano-room-vue3/dist;  # ← 改为你的实际路径
        index  index.html;
        try_files $uri $uri/ /index.html;  # 支持 Vue Router history 模式
    }

    location /api/ {
        proxy_pass http://localhost:8099/;  # 代理到后端
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

**第二步：启动 Nginx**

```bash
# Windows
cd nginx-1.28.0
nginx.exe

# 重载配置（修改 nginx.conf 后）
nginx.exe -s reload

# 停止
nginx.exe -s stop
```

前端默认访问地址：**http://localhost:82**

---

## 默认账号（密码均为 `123456`）

| 用户名        | 类型    | 说明         |
|------------|-------|------------|
| student1   | 学生    | 测试学生账号     |
| student2   | 学生    | 测试学生账号     |
| admin1     | 管理员   | 管理后台账号     |
| superadmin | 超级管理员 | 最高权限，可管理管理员 |

---

## 功能特性

### 学生端

- **图书馆式即时预约**：选择琴房和时段，提交即锁定，无需审核
- **时段查询**：以日历 + 方块形式展示当日/次日可用时段，实时刷新
- **我的预约**：查看历史预约，支持取消尚未开始的预约
- **个人中心**：累计练琴时长、违约次数、封禁状态统计
- **提前预约规则**：当日时段随时可预，次日时段在每日零点后开放

### 管理端

- **预约管理**：查看全部预约记录，支持管理员介入取消
- **用户管理**：查看违约次数/封禁状态，支持手动解封
- **惩罚规则配置**：阶梯封禁（第1次警告，第2次封N天，第3次封N天），管理员可在后台调整
- **数据统计**：预约趋势、热门时段、练琴时长报表
- **系统设置**：系统名称、预约时段规则（开始时间/结束时间/时长/开放时间）

---

## 违约机制

| 违约次数     | 默认处理    |
|----------|---------|
| 第 1 次    | 系统警告    |
| 第 2 次    | 封禁 7 天  |
| 第 3 次及以上 | 封禁 30 天 |

> 预约后超过开始时间 10 分钟未签到，系统定时任务自动标记违约。管理员可在后台调整封禁天数。

---

## 并发安全

预约采用双重保障防止超卖：

1. `SELECT ... FOR UPDATE` 悲观锁，同一琴房同一时段只允许一笔成功
2. 应用层冲突检测，失败时立即返回"时段已被抢占"提示

---

## 常见问题

**Q：启动后访问页面显示空白？**  
A：检查 Nginx `nginx.conf` 中的 `root` 路径是否正确指向 `piano-room-vue3/dist`，且路径使用正斜杠 `/`。

**Q：登录报 500 错误？**  
A：通常是数据库连接失败。检查 `application.yml` 中的密码是否正确，以及 MySQL 服务是否已启动。

**Q：邮件发送失败？**  
A：确认 `application.yml` 中的邮箱授权码是否正确（授权码不是邮箱登录密码），以及邮箱 SMTP 功能是否已开启。

---

## 接口文档

详见 [docs/API.md](docs/API.md)
