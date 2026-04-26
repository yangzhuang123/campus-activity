-- 社长表添加单位名称字段
-- 执行时间: 2026-04-23
-- 说明: 社长信息管理功能完善，添加单位名称字段

USE springbootnp4n3;

ALTER TABLE shezhang ADD COLUMN IF NOT EXISTS danweimingcheng VARCHAR(255) COMMENT '单位名称';

-- 社团活动表添加活动状态字段
-- 执行时间: 2026-04-23
-- 说明: 活动管理添加"关闭报名"功能，需要标识活动是否已关闭报名

USE springbootnp4n3;

ALTER TABLE shetuanhuodong ADD COLUMN IF NOT EXISTS huodongzhuangtai VARCHAR(255) COMMENT '活动状态';

-- 用户表添加个人信息字段
-- 执行时间: 2026-04-23
-- 说明: 用户管理功能完善，添加邮箱、手机号、昵称、头像等个人信息字段

USE springbootnp4n3;

ALTER TABLE users ADD COLUMN IF NOT EXISTS email VARCHAR(255) COMMENT '邮箱';
ALTER TABLE users ADD COLUMN IF NOT EXISTS phone VARCHAR(20) COMMENT '手机号';
ALTER TABLE users ADD COLUMN IF NOT EXISTS nickname VARCHAR(50) COMMENT '昵称';
ALTER TABLE users ADD COLUMN IF NOT EXISTS avatar VARCHAR(255) COMMENT '头像';
ALTER TABLE users ADD COLUMN IF NOT EXISTS gender VARCHAR(10) COMMENT '性别';
ALTER TABLE users ADD COLUMN IF NOT EXISTS birthday DATE COMMENT '生日';
ALTER TABLE users ADD COLUMN IF NOT EXISTS introduction TEXT COMMENT '个人简介';
ALTER TABLE users ADD COLUMN IF NOT EXISTS notification_settings VARCHAR(255) COMMENT '通知设置';

-- 社团活动表添加软删除字段
-- 执行时间: 2026-04-23
-- 说明: 活动管理功能增强，添加软删除功能

USE springbootnp4n3;

ALTER TABLE shetuanhuodong ADD COLUMN IF NOT EXISTS is_deleted INT DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除';

-- 创建评论表
-- 执行时间: 2026-04-23
-- 说明: 互动功能开发，添加评论管理功能

USE springbootnp4n3;

CREATE TABLE IF NOT EXISTS discuss (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    content TEXT COMMENT '评论内容',
    user_id BIGINT COMMENT '评论用户ID',
    user_name VARCHAR(50) COMMENT '评论用户名称',
    object_type VARCHAR(50) COMMENT '被评论对象类型（活动、社团等）',
    object_id BIGINT COMMENT '被评论对象ID',
    parent_id BIGINT COMMENT '父评论ID（用于回复功能）',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    status VARCHAR(20) DEFAULT '正常' COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论表';

-- 创建标签表
-- 执行时间: 2026-04-23
-- 说明: 内容管理模块，添加标签管理功能

USE springbootnp4n3;

CREATE TABLE IF NOT EXISTS tag (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    name VARCHAR(50) COMMENT '标签名称',
    type VARCHAR(50) COMMENT '标签类型（活动标签、社团标签等）',
    sort INT DEFAULT 0 COMMENT '排序',
    status VARCHAR(20) DEFAULT '正常' COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签表';