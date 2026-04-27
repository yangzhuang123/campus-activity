# 消息提醒功能 - 任务清单

## 第一阶段：后端基础架构

- [x] Task 1.1: 创建xiaoxi数据库表
  - 执行SQL建表语句

- [x] Task 1.2: 创建XxiaoxiEntity实体类
  - 路径: src/main/java/com/entity/XxiaoxiEntity.java
  - 包含所有字段的getter/setter

- [x] Task 1.3: 创建XxiaoxiView视图类
  - 路径: src/main/java/com/entity/view/XxiaoxiView.java

- [x] Task 1.4: 创建XxiaoxiDao接口
  - 路径: src/main/java/com/dao/XxiaoxiDao.java

- [x] Task 1.5: 创建XxiaoxiService服务接口
  - 路径: src/main/java/com/service/XxiaoxiService.java

- [x] Task 1.6: 创建XxiaoxiServiceImpl服务实现类
  - 路径: src/main/java/com/service/impl/XxiaoxiServiceImpl.java

- [x] Task 1.7: 创建XxiaoxiController控制器
  - 路径: src/main/java/com/controller/XxiaoxiController.java
  - 实现列表查询和已读更新接口

## 第二阶段：消息触发逻辑

- [x] Task 2.1: 修改HuodongbaomingController - 报名通过提醒
  - 在update方法中，当sfsh变为"是"时插入消息
  - 消息类型: baomingtongguo

- [x] Task 2.2: 修改ShetuanhuodongController - 发布成功提醒
  - 在publishNow方法中插入消息
  - 消息类型: fabuchenggong

- [x] Task 2.3: 修改HuodongbaomingController - 报名已满提醒
  - 在save/add方法中检查人数是否已满，满时插入消息
  - 消息类型: baomingyiman

## 第三阶段：定时任务

- [x] Task 3.1: 创建XiaoxiTimerTask定时任务类
  - 路径: src/main/java/com/timer/XiaoxiTimerTask.java
  - 使用@Scheduled注解，每小时执行
  - 查询活动开始时间在1小时内的活动
  - 发送huodongqian1xiaoshi类型消息给相关用户

- [x] Task 3.2: 修改SpringbootSchemaApplication启动类
  - 添加@EnableScheduling注解启用定时任务

## 第四阶段：前端页面

- [x] Task 4.1: 创建消息通知列表页面
  - 路径: src/main/resources/front/front/pages/xiaoxi/list.html
  - 样式与"我的收藏"页面保持一致
  - 支持标记已读功能

- [x] Task 4.2: 修改config.js添加消息菜单
  - 在centerMenu中添加"消息通知"菜单项

- [x] Task 4.3: 修改社长个人中心页面
  - 路径: src/main/resources/front/front/pages/shezhang/center.html
  - 添加顶部消息通知图标组件

- [x] Task 4.4: 修改学生个人中心页面
  - 路径: src/main/resources/front/front/pages/xuesheng/center.html
  - 添加顶部消息通知图标组件

- [x] Task 4.5: 修改前台首页index.html
  - 调用xiaoxi/list接口获取消息

## 第五阶段：数据库变更记录

- [x] Task 5.1: 更新database_changes.sql
  - 添加建表语句和变更说明

## 任务依赖关系

- Task 1.2-1.7 依赖 Task 1.1
- Task 2.1-2.3 依赖 Task 1.5-1.6
- Task 3.1 依赖 Task 1.5-1.6
- Task 4.1 依赖 Task 1.7
- Task 4.3-4.4 依赖 Task 4.1-4.2
