-- 修改维修类型字段长度，支持中文
ALTER TABLE room_maintenance MODIFY maintenance_type VARCHAR(20);

-- 修改状态字段长度
ALTER TABLE room_maintenance MODIFY status VARCHAR(20);
