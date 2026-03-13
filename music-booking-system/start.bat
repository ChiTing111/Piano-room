@echo off
chcp 65001 >nul
echo ========================================
echo 琴房预约系统启动脚本
echo ========================================

:: 请在这里填入你的配置
set DB_PASSWORD=你的数据库密码
set MAIL_USERNAME=你的邮箱账号
set MAIL_PASSWORD=你的邮箱授权码

:: 可选配置（使用默认值可删除）
:: set DB_HOST=localhost
:: set DB_PORT=3306
:: set DB_NAME=classroombookingdb
:: set DB_USERNAME=root
:: set MAIL_HOST=smtp.163.com
:: set MAIL_PORT=25

echo 正在启动服务...
java -jar target/classroom-booking-system-0.0.1-SNAPSHOT.jar

pause
