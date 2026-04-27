-- 创建消息通知表
-- 执行时间: 2026-04-26
-- 说明: 为实现消息通知功能，创建xiaoxi表用于存储消息通知数据

USE springbootnp4n3;

CREATE TABLE IF NOT EXISTS xiaoxi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    yonghu INT COMMENT '接收人id',
    yonghutable VARCHAR(255) COMMENT '接收人表',
    xiaoxileixing VARCHAR(255) COMMENT '消息类型',
    xiaoxibiaoti VARCHAR(255) COMMENT '消息标题',
    xiaoxineirong TEXT COMMENT '消息内容',
    fabushijian DATETIME COMMENT '发布时间',
    chushishijian DATETIME COMMENT '触发时间',
    yuedu INT DEFAULT 0 COMMENT '已读状态'
);
