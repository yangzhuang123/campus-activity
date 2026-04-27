
var projectName = '';
/**
 * 轮播图配置
 */
var swiper = {
	// 设定轮播容器宽度，支持像素和百分比
	width: '100%',
	height: '400px',
	// hover（悬停显示）
	// always（始终显示）
	// none（始终不显示）
	arrow: 'none',
	// default（左右切换）
	// updown（上下切换）
	// fade（渐隐渐显切换）
	anim: 'default',
	// 自动切换的时间间隔
	// 默认3000
	interval: 2000,
	// 指示器位置
	// inside（容器内部）
	// outside（容器外部）
	// none（不显示）
	indicator: 'outside'
}

/**
 * 个人中心菜单
 */
var getUserTable = function() {
    return localStorage.getItem('userTable') || '';
};

var centerMenu = function() {
    if (getUserTable() === 'xuesheng') {
        return [
            {
                name: '个人信息',
                url: '../xuesheng/center.html'
            },
            {
                name: '我的收藏',
                url: '../storeup/list.html'
            },
            {
                name: '我的活动',
                url: '../huodongbaoming/list.html'
            },
            {
                name: '消息通知',
                url: '../xiaoxi/list.html'
            },
            {
                name: '修改密码',
                url: '../users/password.html'
            }
        ];
    }

    if (getUserTable() === 'shezhang') {
        return [
            {
                name: '个人中心',
                url: '../shezhang/center.html'
            },
            {
                name: '我的收藏',
                url: '../storeup/list.html'
            },
            {
                name: '我的发布',
                url: '../shezhang/mypublish.html'
            },
            {
                name: '报名审核',
                url: '../shezhang/baomingreview.html'
            },
            {
                name: '消息通知',
                url: '../xiaoxi/list.html'
            },
            {
                name: '修改密码',
                url: '../users/password.html'
            }
        ];
    }

    return [
        {
            name: '个人中心',
            url: '../' + getUserTable() + '/center.html'
        },
        {
            name: '我的收藏',
            url: '../storeup/list.html'
        },
        {
            name: '我的发布',
            url: '../' + getUserTable() + '/mypublish.html'
        },
        {
            name: '消息通知',
            url: '../xiaoxi/list.html'
        }
    ];
};


var indexNav = [

{
	name: '校园活动',
	url: './pages/shetuanhuodong/list.html'
}

]

var adminurl =  "../admin/index.html";

var cartFlag = false

var chatFlag = false




var menu = [{"backMenu":[{"child":[{"buttons":["查看","修改","删除"],"menu":"学生","menuJump":"列表","tableName":"xuesheng"}],"menu":"学生管理"},{"child":[{"buttons":["查看","修改","删除"],"menu":"社长","menuJump":"列表","tableName":"shezhang"}],"menu":"社长管理"},{"child":[{"buttons":["新增","查看","修改","删除"],"menu":"社团分类","menuJump":"列表","tableName":"shetuanfenlei"}],"menu":"社团分类管理"},{"child":[{"buttons":["新增","查看","修改","删除","审核","查看评论"],"menu":"社团信息","menuJump":"列表","tableName":"shetuanxinxi"}],"menu":"校园活动管理"},{"child":[{"buttons":["查看","修改","删除"],"menu":"加入社团","menuJump":"列表","tableName":"jiarushetuan"}],"menu":"加入社团管理"},{"child":[{"buttons":["查看","修改","删除"],"menu":"社团成员","menuJump":"列表","tableName":"shetuanchengyuan"}],"menu":"社团成员管理"},{"child":[{"buttons":["新增","查看","修改","删除","审核"],"menu":"校园活动","menuJump":"列表","tableName":"shetuanhuodong"}],"menu":"校园活动管理"},{"child":[{"buttons":["查看","修改","删除"],"menu":"活动报名","menuJump":"列表","tableName":"huodongbaoming"}],"menu":"活动报名管理"},{"child":[{"buttons":["新增","查看","修改","删除"],"menu":"社团新闻","tableName":"news"},{"buttons":["查看","修改"],"menu":"轮播图管理","tableName":"config"}],"menu":"系统管理"}],"frontMenu":[{"child":[{"buttons":["查看","申请加入"],"menu":"社团信息列表","menuJump":"列表","tableName":"shetuanxinxi"}],"menu":"社团信息模块"},{"child":[{"buttons":["查看","报名"],"menu":"校园活动列表","menuJump":"列表","tableName":"shetuanhuodong"}],"menu":"校园活动模块"}],"hasBackLogin":"是","hasBackRegister":"否","hasFrontLogin":"是","hasFrontRegister":"是","roleName":"管理员","tableName":"users"},{"backMenu":[{"child":[{"buttons":["查看","删除"],"menu":"加入社团","menuJump":"列表","tableName":"jiarushetuan"}],"menu":"加入社团管理"},{"child":[{"buttons":["查看","删除"],"menu":"活动报名","menuJump":"列表","tableName":"huodongbaoming"}],"menu":"活动报名管理"},{"child":[{"buttons":["查看","删除"],"menu":"我的收藏管理","tableName":"storeup"}],"menu":"我的收藏管理"}],"frontMenu":[{"child":[{"buttons":["查看","申请加入"],"menu":"社团信息列表","menuJump":"列表","tableName":"shetuanxinxi"}],"menu":"社团信息模块"},{"child":[{"buttons":["查看","报名"],"menu":"校园活动列表","menuJump":"列表","tableName":"shetuanhuodong"}],"menu":"校园活动模块"}],"hasBackLogin":"是","hasBackRegister":"否","hasFrontLogin":"是","hasFrontRegister":"是","roleName":"社长","tableName":"shezhang"},{"backMenu":[{"child":[{"buttons":["查看","删除","审核"],"menu":"活动报名","menuJump":"列表","tableName":"huodongbaoming"}],"menu":"活动报名管理"},{"child":[{"buttons":["查看","删除"],"menu":"我的收藏管理","tableName":"storeup"}],"menu":"我的收藏管理"},{"child":[{"buttons":["查看","删除"],"menu":"消息通知管理","tableName":"xiaoxi"}],"menu":"消息通知管理"}],"frontMenu":[{"child":[{"buttons":["查看","报名"],"menu":"校园活动列表","menuJump":"列表","tableName":"shetuanhuodong"}],"menu":"校园活动模块"}],"hasBackLogin":"是","hasBackRegister":"否","hasFrontLogin":"是","hasFrontRegister":"是","roleName":"学生","tableName":"xuesheng"}]


var isAuth = function (tableName,key) {
    let role = localStorage.getItem("userTable");
    let menus = menu;
    for(let i=0;i<menus.length;i++){
        if(menus[i].tableName==role){
            for(let j=0;j<menus[i].backMenu.length;j++){
                for(let k=0;k<menus[i].backMenu[j].child.length;k++){
                    if(tableName==menus[i].backMenu[j].child[k].tableName){
                        let buttons = menus[i].backMenu[j].child[k].buttons.join(',');
                        return buttons.indexOf(key) !== -1 || false
                    }
                }
            }
        }
    }
    return false;
}

var isFrontAuth = function (tableName,key) {
    let role = localStorage.getItem("userTable");
    let menus = menu;
    for(let i=0;i<menus.length;i++){
        if(menus[i].tableName==role){
            for(let j=0;j<menus[i].frontMenu.length;j++){
                for(let k=0;k<menus[i].frontMenu[j].child.length;k++){
                    if(tableName==menus[i].frontMenu[j].child[k].tableName){
                        let buttons = menus[i].frontMenu[j].child[k].buttons.join(',');
                        return buttons.indexOf(key) !== -1 || false
                    }
                }
            }
        }
    }
    return false;
}
