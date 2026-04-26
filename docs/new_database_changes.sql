-- 仅执行新增的数据库变更语句
-- 执行时间: 2026-04-23

USE springbootnp4n3;

-- 创建评论表
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
CREATE TABLE IF NOT EXISTS tag (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    name VARCHAR(50) COMMENT '标签名称',
    type VARCHAR(50) COMMENT '标签类型（活动标签、社团标签等）',
    sort INT DEFAULT 0 COMMENT '排序',
    status VARCHAR(20) DEFAULT '正常' COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签表';
