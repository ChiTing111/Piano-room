# 数据库迁移脚本

## 命名规范
`V{版本号}__{描述}.sql`

示例：
- `V1.0__init_schema.sql` - 初始建表
- `V1.1__alter_maintenance_type_length.sql` - 修改字段长度
- `V1.2__add_new_table.sql` - 新增表

## 执行顺序
按版本号从小到大依次执行。

## 当前变更

### V1.1 - 2026-03-13
- 修改 `room_maintenance.maintenance_type` 字段长度为 VARCHAR(20)
- 修改 `room_maintenance.status` 字段长度为 VARCHAR(20)
- 原因：支持中文字段值（定期维护、故障修复、清洁保养）
