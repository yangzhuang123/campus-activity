# 消息提醒功能 - 验证检查清单

## 数据库验证

- [ ] xiaoxi表已成功创建，包含所有必要字段
- [ ] 数据库连接正常

## 后端验证

- [ ] XxiaoxiEntity实体类编译通过
- [ ] XxiaoxiDao接口编译通过
- [ ] XxiaoxiService服务编译通过
- [ ] XxiaoxiController编译通过
- [ ] 定时任务XiaoxiTimerTask编译通过
- [ ] @EnableScheduling注解已添加到启动类

## 功能验证

- [ ] 学生报名通过后，xiaoxi表有新记录（baomingtongguo类型）
- [ ] 社长发布活动成功后，xiaoxi表有新记录（fabuchenggong类型）
- [ ] 活动报名已满时，xiaoxi表有新记录（baomingyiman类型）
- [ ] 定时任务每小时执行一次
- [ ] 活动前1小时提醒消息能正确发送（huodongqian1xiaoshi类型）

## 前端验证

- [ ] 社长个人中心页面显示消息通知图标
- [ ] 学生个人中心页面显示消息通知图标
- [ ] 点击图标显示下拉面板
- [ ] 未读消息显示角标数量
- [ ] 点击单条消息标记为已读
- [ ] "查看全部"按钮跳转到消息列表页面
- [ ] 个人中心菜单显示"消息通知"菜单项
- [ ] 消息列表页面正常显示
- [ ] 前台首页顶部消息图标正常显示

## 数据安全验证

- [ ] 学生只能看到自己的消息
- [ ] 社长只能看到自己的消息
- [ ] 按yonghu和yonghutable正确过滤数据
