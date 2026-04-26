# 数据库变更规则

## 规则名称
添加实体字段数据库变更规则

## 规则描述
当在Java实体类中添加字段时，必须同时执行以下操作：
1. 执行对应的数据库ALTER TABLE语句
2. 将数据库变更语句保存到 `docs/database_changes.sql` 文件中

## 变更记录格式
```sql
-- [字段描述]
-- 执行时间: YYYY-MM-DD
-- 说明: [变更原因]

USE springbootnp4n3;

ALTER TABLE [表名] ADD COLUMN [字段名] [字段类型] COMMENT '[字段注释]';
```

## 示例
假设需要在 `ShetuanhuodongEntity` 中添加 `huodongzhuangtai` 字段：

1. 在实体类中添加字段
2. 执行数据库变更：
   ```sql
   USE springbootnp4n3;
   ALTER TABLE shetuanhuodong ADD COLUMN huodongzhuangtai VARCHAR(255) COMMENT '活动状态';
   ```
3. 将变更语句追加到 `docs/database_changes.sql`

## 注意事项
- 所有数据库变更必须先在测试环境验证
- 变更语句必须包含清晰的注释说明
- 必须记录执行时间

---

# 项目修改后部署规则

## 规则名称
项目修改后自动编译部署规则

## 规则描述
当完成任何代码修改后（包括前端页面、Java后端代码、配置文件等），必须执行以下操作：
1. 重新编译项目（执行 `mvn clean package -DskipTests`）
2. 重新部署项目（重启 Spring Boot 应用）

## 编译命令
```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home
mvn clean package -DskipTests
```

## 部署命令
```bash
java -jar target/springbootnp4n3-0.0.1-SNAPSHOT.jar
```

## 注意事项
- 前端静态文件修改后也需要重新编译部署（因为编译时会复制到 target 目录）
- 如果修改了数据库结构，需要先执行数据库变更语句
- 部署前确认端口 8080 未被占用
- 使用 `mvn clean package` 而非直接 `mvn package`，确保清理旧的编译产物

---

# 项目修改后测试规则

## 规则名称
项目修改后验证测试规则

## 规则描述
当完成任何代码修改并重新部署后，必须通过 web 页面进行验证测试，确保功能正常运行。

## 测试环境推荐
- **网络环境**：本地开发环境

## 访问地址
- **前台地址**：http://localhost:8080/springbootnp4n3/front/index.html
- **后台地址**：http://localhost:8080/springbootnp4n3/admin/dist/index.html

## 测试账号
### 管理员账号
- **用户名**：abo
- **密码**：abo

### 社长账号
- **用户名**：社长1
- **密码**：123456

### 学生账号
- **用户名**：学生1
- **密码**：123456

## 测试内容
1. **功能测试**：验证修改的功能是否正常工作
2. **兼容性测试**：确保在推荐浏览器中显示正常
3. **流程测试**：测试完整的业务流程是否顺畅
4. **边界测试**：测试各种边界情况和异常场景

## 注意事项
- 测试前确保服务已正常启动
- **验证问题必须通过 web 页面进行，禁止使用 curl 等命令行工具**
- 测试过程中注意观察浏览器控制台是否有错误信息
- 测试完成后记录测试结果和发现的问题
- 对于关键功能，建议进行回归测试