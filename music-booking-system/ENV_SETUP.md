# 环境变量配置说明

本项目使用环境变量来管理敏感信息（数据库密码、邮箱授权码等），避免将敏感信息提交到 GitHub。

## 快速开始

### 1. 复制配置文件

```bash
cp src/main/resources/application-example.yml src/main/resources/application-dev.yml
```

然后在 `application-dev.yml` 中填入你的真实配置。

### 2. 方式一：使用环境变量（推荐）

**Windows:**
```cmd
set DB_PASSWORD=你的数据库密码
set MAIL_USERNAME=你的邮箱账号
set MAIL_PASSWORD=你的邮箱授权码
java -jar target/classroom-booking-system-0.0.1-SNAPSHOT.jar
```

**Linux/Mac:**
```bash
export DB_PASSWORD=你的数据库密码
export MAIL_USERNAME=你的邮箱账号
export MAIL_PASSWORD=你的邮箱授权码
java -jar target/classroom-booking-system-0.0.1-SNAPSHOT.jar
```

### 3. 方式二：使用命令行参数

```bash
java -jar target/classroom-booking-system-0.0.1-SNAPSHOT.jar \
  --spring.datasource.password=你的数据库密码 \
  --spring.mail.username=你的邮箱账号 \
  --spring.mail.password=你的邮箱授权码
```

### 4. 方式三：使用 IDE 配置

**IntelliJ IDEA:**
1. 打开 Run/Debug Configurations
2. 在 Environment variables 中添加：
   ```
   DB_PASSWORD=你的数据库密码;MAIL_USERNAME=你的邮箱账号;MAIL_PASSWORD=你的邮箱授权码
   ```

## 环境变量说明

| 变量名 | 说明 | 默认值 |
|--------|------|--------|
| `DB_HOST` | 数据库主机 | localhost |
| `DB_PORT` | 数据库端口 | 3306 |
| `DB_NAME` | 数据库名称 | classroombookingdb |
| `DB_USERNAME` | 数据库用户名 | root |
| `DB_PASSWORD` | 数据库密码 | （必填） |
| `MAIL_HOST` | 邮件服务器 | smtp.163.com |
| `MAIL_PORT` | 邮件端口 | 25 |
| `MAIL_USERNAME` | 邮箱账号 | （必填） |
| `MAIL_PASSWORD` | 邮箱授权码 | （必填） |

## 注意事项

1. `application.yml` 已使用环境变量占位符，可以直接提交到 GitHub
2. `application-dev.yml` 包含真实配置，已被 `.gitignore` 忽略，不会上传
3. 生产环境建议使用更安全的配置管理方式（如配置中心）
