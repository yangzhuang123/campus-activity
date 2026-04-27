# 代码隔离规则

## 规则名称
主办方与学生代码隔离规则

## 规则描述
当修改主办方（社长）相关代码时，不允许影响学生页面的功能；同理，修改学生相关代码时，不允许影响主办方页面的功能。

## 代码结构隔离

### 后端代码隔离

**社长相关代码**：
- 控制器：`src/main/java/com/controller/ShezhangController.java`
- 实体类：`src/main/java/com/entity/ShezhangEntity.java`
- 服务层：`src/main/java/com/service/ShezhangService.java`
- 数据访问层：`src/main/java/com/dao/ShezhangDao.java`
- 视图对象：`src/main/java/com/entity/vo/ShezhangVO.java`
- 模型对象：`src/main/java/com/entity/model/ShezhangModel.java`

**学生相关代码**：
- 控制器：`src/main/java/com/controller/XueshengController.java`
- 实体类：`src/main/java/com/entity/XueshengEntity.java`
- 服务层：`src/main/java/com/service/XueshengService.java`
- 数据访问层：`src/main/java/com/dao/XueshengDao.java`
- 视图对象：`src/main/java/com/entity/vo/XueshengVO.java`
- 模型对象：`src/main/java/com/entity/model/XueshengModel.java`

### 前端代码隔离

**社长相关页面**：
- 页面目录：`src/main/resources/front/front/pages/shezhang/`
- 包含文件：`center.html`、`mypublish.html`、`huodong_add.html`、`huodong_edit.html` 等

**学生相关页面**：
- 页面目录：`src/main/resources/front/front/pages/xuesheng/`
- 包含文件：`center.html`、`list.html` 等

**公共页面**：
- 活动报名：`src/main/resources/front/front/pages/huodongbaoming/`
- 收藏管理：`src/main/resources/front/front/pages/storeup/`
- 登录页面：`src/main/resources/front/front/pages/login/`

## 实施规则

### 1. 代码修改原则

1. **修改社长代码时**：
   - 只修改 `shezhang` 相关的文件和目录
   - 不修改 `xuesheng` 相关的文件
   - 如需修改公共页面，确保不影响学生用户的使用

2. **修改学生代码时**：
   - 只修改 `xuesheng` 相关的文件和目录
   - 不修改 `shezhang` 相关的文件
   - 如需修改公共页面，确保不影响社长用户的使用

### 2. 配置文件修改规则

- **修改 `config.js` 时**：
  - 分别修改不同角色的菜单配置
  - 确保修改一个角色的配置不影响另一个角色的配置
  - 公共配置（如轮播图、导航栏）需确保对所有角色都适用

### 3. 测试验证规则

- **修改后测试**：
  - 社长功能测试：使用社长账号登录，验证所有社长相关功能
  - 学生功能测试：使用学生账号登录，验证所有学生相关功能
  - 公共功能测试：验证登录、注册、活动浏览等公共功能

- **回归测试**：
  - 修改社长代码后，必须测试学生功能是否正常
  - 修改学生代码后，必须测试社长功能是否正常

### 4. 代码审查规则

- **代码提交前**：
  - 检查修改范围是否超出目标角色
  - 验证修改是否影响其他角色的功能
  - 确保公共代码的修改对所有角色都兼容

## 违反规则的处理

1. **发现违反规则**：
   - 立即停止当前修改
   - 恢复被影响的代码
   - 重新制定修改方案

2. **预防措施**：
   - 使用版本控制系统的分支功能
   - 实施代码审查制度
   - 编写详细的测试用例

## 示例

### 正确的修改示例

**修改社长个人中心页面**：
- 只修改 `src/main/resources/front/front/pages/shezhang/center.html`
- 如需修改公共组件，确保不影响学生页面

**修改学生报名功能**：
- 只修改 `src/main/java/com/controller/XueshengController.java` 中相关方法
- 不修改社长相关的控制器

### 错误的修改示例

**错误**：在修改社长活动发布功能时，修改了学生报名的相关代码
**后果**：可能导致学生无法正常报名活动
**处理**：撤销修改，重新只修改社长相关代码

## 总结

代码隔离规则的目的是确保不同角色的功能相互独立，避免修改一个角色的代码时影响另一个角色的功能。通过严格遵守本规则，可以提高代码的可维护性和系统的稳定性。

所有开发人员在修改代码时必须严格遵守本规则，确保系统的各个功能模块能够独立运行和维护。