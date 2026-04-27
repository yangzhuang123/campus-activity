# 活动管理功能 - The Implementation Plan (Decomposed and Prioritized Task List)

## [ ] Task 1: 添加社长菜单"报名审核"
- **Priority**: P0
- **Depends On**: None
- **Description**: 
  - 修改 `src/main/resources/front/front/js/config.js` 文件
  - 在社长菜单数组中添加"报名审核"菜单项
  - 菜单项指向新建的审核页面 `../shezhang/baomingreview.html`
- **Acceptance Criteria Addressed**: [AC-1, AC-2]
- **Test Requirements**:
  - `human-judgement` TR-1.1: 社长登录后在个人中心能看到"报名审核"菜单
  - `human-judgement` TR-1.2: 点击"报名审核"菜单能跳转到对应页面
- **Notes**: 在"我的发布"菜单项之后添加

## [ ] Task 2: 创建报名审核页面基础结构
- **Priority**: P0
- **Depends On**: Task 1
- **Description**: 
  - 新建 `src/main/resources/front/front/pages/shezhang/baomingreview.html` 文件
  - 复用 `mypublish.html` 的左侧导航和右侧内容结构
  - 实现基本的Vue数据绑定和Layui组件引入
- **Acceptance Criteria Addressed**: [AC-3]
- **Test Requirements**:
  - `human-judgement` TR-2.1: 页面布局与mypublish.html一致
  - `human-judgement` TR-2.2: 左侧导航菜单正确显示且"报名审核"高亮
- **Notes**: 页面标题设为"报名审核"

## [ ] Task 3: 实现报名列表展示功能
- **Priority**: P0
- **Depends On**: Task 2
- **Description**: 
  - 调用 `huodongbaoming/list` 接口获取报名列表
  - 只显示当前社长发布活动的报名（通过社长账号筛选）
  - 使用卡片式布局展示报名信息（参考学生端"我的活动"页面）
  - 显示字段：活动标题、学生姓名、学号、手机、报名日期、审核状态
- **Acceptance Criteria Addressed**: [AC-3, AC-10]
- **Test Requirements**:
  - `human-judgement` TR-3.1: 报名列表正常加载显示
  - `human-judgement` TR-3.2: 只显示当前社长发布活动的报名
  - `human-judgement` TR-3.3: 卡片布局美观，信息完整
- **Notes**: 需要确认后端是否支持按社长账号筛选，可能需要查看Shezhang字段关联关系

## [ ] Task 4: 实现按活动筛选功能
- **Priority**: P1
- **Depends On**: Task 3
- **Description**: 
  - 在页面顶部添加活动下拉筛选框
  - 下拉框数据来源于当前社长发布的活动列表
  - 选择活动后自动刷新报名列表
- **Acceptance Criteria Addressed**: [AC-4]
- **Test Requirements**:
  - `human-judgement` TR-4.1: 活动下拉框正常显示社长发布的活动
  - `human-judgement` TR-4.2: 选择活动后列表正确筛选
  - `human-judgement` TR-4.3: 支持选择"全部"显示所有报名
- **Notes**: 接口可能需要活动ID参数进行筛选

## [ ] Task 5: 实现按审核状态筛选功能
- **Priority**: P1
- **Depends On**: Task 3
- **Description**: 
  - 在页面顶部添加审核状态下拉筛选框
  - 状态选项：全部/审核中/已通过/已拒绝
  - 选择状态后自动刷新报名列表
- **Acceptance Criteria Addressed**: [AC-5]
- **Test Requirements**:
  - `human-judgement` TR-5.1: 状态下拉框正常显示
  - `human-judgement` TR-5.2: 选择状态后列表正确筛选
  - `human-judgement` TR-5.3: 不同状态有不同的视觉标识
- **Notes**: sfsh字段值：null/空表示审核中，"是"表示通过，"否"表示拒绝

## [ ] Task 6: 实现审核通过功能
- **Priority**: P0
- **Depends On**: Task 3
- **Description**: 
  - 在每个报名卡片上添加"通过"按钮
  - 点击按钮弹出审核对话框，包含审核回复输入框
  - 调用 `huodongbaoming/update` 接口更新审核状态和回复
  - 提交成功后自动刷新列表并显示成功提示
- **Acceptance Criteria Addressed**: [AC-6, AC-7]
- **Test Requirements**:
  - `human-judgement` TR-6.1: 点击"通过"按钮弹出对话框
  - `human-judgement` TR-6.2: 对话框包含审核回复输入框
  - `human-judgement` TR-6.3: 提交后状态正确更新为"已通过"
  - `human-judgement` TR-6.4: 审核回复正确保存并显示
  - `human-judgement` TR-6.5: 操作成功有明确反馈提示
- **Notes**: sfsh字段设为"是"，shhf字段设为审核回复内容

## [ ] Task 7: 实现审核拒绝功能
- **Priority**: P0
- **Depends On**: Task 6
- **Description**: 
  - 在每个报名卡片上添加"拒绝"按钮
  - 点击按钮弹出审核对话框，包含审核回复输入框
  - 调用 `huodongbaoming/update` 接口更新审核状态和回复
  - 提交成功后自动刷新列表并显示成功提示
- **Acceptance Criteria Addressed**: [AC-7]
- **Test Requirements**:
  - `human-judgement` TR-7.1: 点击"拒绝"按钮弹出对话框
  - `human-judgement` TR-7.2: 对话框包含审核回复输入框
  - `human-judgement` TR-7.3: 提交后状态正确更新为"已拒绝"
  - `human-judgement` TR-7.4: 审核回复正确保存并显示
  - `human-judgement` TR-7.5: 操作成功有明确反馈提示
- **Notes**: sfsh字段设为"否"，shhf字段设为审核回复内容

## [x] Task 8: 在"我的发布"页面添加"查看报名"按钮
- **Priority**: P1
- **Depends On**: Task 1
- **Description**: 
  - 修改 `src/main/resources/front/front/pages/shezhang/mypublish.html` 文件
  - 在活动卡片的操作区域添加"查看报名"按钮
  - 按钮位于"修改信息"和"关闭报名"按钮旁边
- **Acceptance Criteria Addressed**: [AC-8]
- **Test Requirements**:
  - `human-judgement` TR-8.1: 每个活动卡片都显示"查看报名"按钮
  - `human-judgement` TR-8.2: 按钮样式与现有按钮风格一致
- **Notes**:

## [x] Task 9: 实现查看报名跳转功能
- **Priority**: P1
- **Depends On**: Task 8
- **Description**: 
  - 点击"查看报名"按钮跳转到报名审核页面
  - 跳转时在URL中附带活动ID参数（如 `?activityId=xxx`）
  - 报名审核页面根据URL参数自动筛选该活动的报名
- **Acceptance Criteria Addressed**: [AC-9]
- **Test Requirements**:
  - `human-judgement` TR-9.1: 点击按钮正确跳转到报名审核页面
  - `human-judgement` TR-9.2: URL中包含正确的活动ID参数
  - `human-judgement` TR-9.3: 跳转后自动筛选该活动的报名
  - `human-judgement` TR-9.4: 活动下拉框自动选中对应的活动
- **Notes**: 使用 `http.getParam('activityId')` 获取URL参数

## [x] Task 10: 实现分页功能
- **Priority**: P2
- **Depends On**: Task 3
- **Description**: 
  - 在报名审核页面添加分页组件
  - 使用Layui的laypage组件实现分页
  - 分页时保持当前筛选条件
- **Acceptance Criteria Addressed**: [AC-3]
- **Test Requirements**:
  - `human-judgement` TR-10.1: 分页组件正常显示
  - `human-judgement` TR-10.2: 点击页码能正确切换页面
  - `human-judgement` TR-10.3: 分页时保持筛选条件
- **Notes**: 参考mypublish.html的分页实现

## [ ] Task 11: 实现查看报名详情功能
- **Priority**: P2
- **Depends On**: Task 3
- **Description**: 
  - 在报名卡片上添加"查看详情"功能
  - 点击卡片或详情按钮在右侧iframe中展示详情
  - 复用现有的 `detail.html` 页面
- **Acceptance Criteria Addressed**: [AC-3]
- **Test Requirements**:
  - `human-judgement` TR-11.1: 能正常查看报名详情
  - `human-judgement` TR-11.2: 详情展示完整美观
  - `human-judgement` TR-11.3: 有返回按钮能回到列表
- **Notes**: 可选功能，可根据用户需求决定是否实现
