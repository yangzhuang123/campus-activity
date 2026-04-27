#!/bin/bash

# ============================================
# 学生社团活动管理系统 - 部署脚本
# ============================================

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 项目配置
PROJECT_NAME="springbootnp4n3"
JAR_FILE="target/${PROJECT_NAME}-0.0.1-SNAPSHOT.jar"
PID_FILE="${PROJECT_NAME}.pid"
LOG_FILE="${PROJECT_NAME}.log"

# Java配置
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home

# 打印信息
print_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 检查Java
check_java() {
    print_info "检查Java环境..."
    if [ -z "$JAVA_HOME" ]; then
        print_error "JAVA_HOME未设置"
        exit 1
    fi
    
    java_version=$($JAVA_HOME/bin/java -version 2>&1 | head -n 1)
    print_info "Java版本: $java_version"
}

# 检查Maven
check_maven() {
    print_info "检查Maven环境..."
    if ! command -v mvn &> /dev/null; then
        print_error "Maven未安装"
        exit 1
    fi
    
    mvn_version=$(mvn -version | head -n 1)
    print_info "Maven版本: $mvn_version"
}

# 编译项目
build_project() {
    print_info "开始编译项目..."
    
    export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home
    
    mvn clean package -DskipTests
    
    if [ $? -eq 0 ]; then
        print_info "编译成功!"
        ls -lh $JAR_FILE
    else
        print_error "编译失败!"
        exit 1
    fi
}

# 停止服务
stop_service() {
    print_info "检查服务状态..."
    
    if [ -f "$PID_FILE" ]; then
        PID=$(cat $PID_FILE)
        if ps -p $PID > /dev/null; then
            print_info "停止服务 (PID: $PID)..."
            kill $PID
            sleep 2
            
            # 确认是否停止
            if ps -p $PID > /dev/null; then
                print_warn "服务未停止，强制终止..."
                kill -9 $PID
            fi
            
            print_info "服务已停止"
            rm -f $PID_FILE
        else
            print_warn "进程不存在，清理PID文件"
            rm -f $PID_FILE
        fi
    else
        # 尝试通过进程名查找
        PID=$(ps aux | grep $JAR_FILE | grep -v grep | awk '{print $2}')
        if [ ! -z "$PID" ]; then
            print_info "发现运行中的服务 (PID: $PID)，正在停止..."
            kill $PID
            sleep 2
            print_info "服务已停止"
        else
            print_info "没有运行中的服务"
        fi
    fi
}

# 启动服务
start_service() {
    print_info "启动服务..."
    
    if [ ! -f "$JAR_FILE" ]; then
        print_error "JAR文件不存在: $JAR_FILE"
        print_info "请先运行: $0 build"
        exit 1
    fi
    
    # 检查端口是否被占用
    PORT=8080
    if lsof -Pi :$PORT -sTCP:LISTEN -t >/dev/null 2>&1; then
        print_error "端口 $PORT 已被占用"
        print_info "占用进程: $(lsof -Pi :$PORT -sTCP:LISTEN)"
        exit 1
    fi
    
    # 后台启动
    nohup java -Xms512m -Xmx1024m -jar $JAR_FILE > $LOG_FILE 2>&1 &
    PID=$!
    echo $PID > $PID_FILE
    
    print_info "服务启动中 (PID: $PID)..."
    sleep 3
    
    # 检查是否启动成功
    if ps -p $PID > /dev/null; then
        print_info "服务启动成功!"
        print_info "访问地址:"
        print_info "  - 后端API: http://localhost:8080/springbootnp4n3"
        print_info "  - 管理面板: http://localhost:8080/springbootnp4n3/admin/admin/index.html"
        print_info "  - 学生门户: http://localhost:8080/springbootnp4n3/front/front/index.html"
        print_info ""
        print_info "查看日志: tail -f $LOG_FILE"
        print_info "停止服务: $0 stop"
    else
        print_error "服务启动失败，请查看日志: $LOG_FILE"
        exit 1
    fi
}

# 查看日志
view_logs() {
    if [ ! -f "$LOG_FILE" ]; then
        print_error "日志文件不存在: $LOG_FILE"
        exit 1
    fi
    
    print_info "查看日志 (Ctrl+C退出)..."
    tail -f $LOG_FILE
}

# 查看状态
check_status() {
    if [ -f "$PID_FILE" ]; then
        PID=$(cat $PID_FILE)
        if ps -p $PID > /dev/null; then
            print_info "服务运行中 (PID: $PID)"
            
            # 显示内存使用
            memory=$(ps -o rss= -p $PID | awk '{printf "%.2f MB", $1/1024}')
            print_info "内存使用: $memory"
            
            # 显示启动时间
            start_time=$(ps -o lstart= -p $PID)
            print_info "启动时间: $start_time"
        else
            print_warn "PID文件存在，但进程不存在"
            rm -f $PID_FILE
        fi
    else
        PID=$(ps aux | grep $JAR_FILE | grep -v grep | awk '{print $2}')
        if [ ! -z "$PID" ]; then
            print_info "服务运行中 (PID: $PID)"
        else
            print_info "服务未运行"
        fi
    fi
}

# 重启服务
restart_service() {
    print_info "重启服务..."
    stop_service
    sleep 2
    start_service
}

# 显示帮助
show_help() {
    echo "学生社团活动管理系统 - 部署脚本"
    echo ""
    echo "用法: $0 {build|start|stop|restart|status|logs|help}"
    echo ""
    echo "命令:"
    echo "  build    编译项目"
    echo "  start    启动服务"
    echo "  stop     停止服务"
    echo "  restart  重启服务"
    echo "  status   查看服务状态"
    echo "  logs     查看服务日志"
    echo "  help     显示此帮助信息"
    echo ""
    echo "示例:"
    echo "  $0 build          # 编译项目"
    echo "  $0 start          # 启动服务"
    echo "  $0 restart        # 重启服务"
    echo "  $0 logs           # 查看日志"
    echo ""
}

# 主函数
main() {
    case "$1" in
        build)
            check_java
            check_maven
            build_project
            ;;
        start)
            check_java
            start_service
            ;;
        stop)
            stop_service
            ;;
        restart)
            check_java
            restart_service
            ;;
        status)
            check_status
            ;;
        logs)
            view_logs
            ;;
        help|--help|-h)
            show_help
            ;;
        *)
            print_error "未知命令: $1"
            show_help
            exit 1
            ;;
    esac
}

# 执行主函数
main "$@"
