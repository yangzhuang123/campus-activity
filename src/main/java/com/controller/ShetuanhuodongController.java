package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.ShetuanhuodongEntity;
import com.entity.HuodongbaomingEntity;
import com.entity.view.ShetuanhuodongView;

import com.service.ShetuanhuodongService;
import com.service.TokenService;
import com.service.HuodongbaomingService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;


/**
 * 社团活动
 * 后端接口
 * @author 
 * @email 
 * @date 2021-05-08 09:49:51
 */
@RestController
@RequestMapping("/shetuanhuodong")
public class ShetuanhuodongController {
    @Autowired
    private ShetuanhuodongService shetuanhuodongService;
    @Autowired
    private HuodongbaomingService huodongbaomingService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ShetuanhuodongEntity shetuanhuodong,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("shezhang")) {
			shetuanhuodong.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<ShetuanhuodongEntity> ew = new EntityWrapper<ShetuanhuodongEntity>();
        // 添加软删除过滤
        ew.eq("is_deleted", 0);
        // 高级搜索
        if(params.get("keyword") != null && !params.get("keyword").toString().isEmpty()) {
            String keyword = params.get("keyword").toString();
            ew.andNew()
                .like("biaoti", keyword)
                .or()
                .like("shetuanmingcheng", keyword)
                .or()
                .like("huodongxiangqing", keyword)
                .or()
                .like("huodongdidian", keyword);
        }
        // 分类筛选
        if(params.get("huodongzhuangtai") != null && !params.get("huodongzhuangtai").toString().isEmpty()) {
            ew.eq("huodongzhuangtai", params.get("huodongzhuangtai"));
        }
        // 时间范围筛选
        if(params.get("startDate") != null && !params.get("startDate").toString().isEmpty()) {
            ew.ge("kaishishijian", params.get("startDate"));
        }
        if(params.get("endDate") != null && !params.get("endDate").toString().isEmpty()) {
            ew.le("jieshushijian", params.get("endDate"));
        }
		PageUtils page = shetuanhuodongService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shetuanhuodong), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ShetuanhuodongEntity shetuanhuodong, 
		HttpServletRequest request){
        EntityWrapper<ShetuanhuodongEntity> ew = new EntityWrapper<ShetuanhuodongEntity>();
        // 添加软删除过滤
        ew.eq("is_deleted", 0);
        // 高级搜索
        if(params.get("keyword") != null && !params.get("keyword").toString().isEmpty()) {
            String keyword = params.get("keyword").toString();
            ew.andNew()
                .like("biaoti", keyword)
                .or()
                .like("shetuanmingcheng", keyword)
                .or()
                .like("huodongxiangqing", keyword)
                .or()
                .like("huodongdidian", keyword);
        }
        // 分类筛选
        if(params.get("huodongzhuangtai") != null && !params.get("huodongzhuangtai").toString().isEmpty()) {
            ew.eq("huodongzhuangtai", params.get("huodongzhuangtai"));
        }
        // 时间范围筛选
        if(params.get("startDate") != null && !params.get("startDate").toString().isEmpty()) {
            ew.ge("kaishishijian", params.get("startDate"));
        }
        if(params.get("endDate") != null && !params.get("endDate").toString().isEmpty()) {
            ew.le("jieshushijian", params.get("endDate"));
        }
		PageUtils page = shetuanhuodongService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shetuanhuodong), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ShetuanhuodongEntity shetuanhuodong){
       	EntityWrapper<ShetuanhuodongEntity> ew = new EntityWrapper<ShetuanhuodongEntity>();
      	ew.allEq(MPUtil.allEQMapPre( shetuanhuodong, "shetuanhuodong")); 
        return R.ok().put("data", shetuanhuodongService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ShetuanhuodongEntity shetuanhuodong){
        EntityWrapper< ShetuanhuodongEntity> ew = new EntityWrapper< ShetuanhuodongEntity>();
 		ew.allEq(MPUtil.allEQMapPre( shetuanhuodong, "shetuanhuodong")); 
		ShetuanhuodongView shetuanhuodongView =  shetuanhuodongService.selectView(ew);
		return R.ok("查询社团活动成功").put("data", shetuanhuodongView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShetuanhuodongEntity shetuanhuodong = shetuanhuodongService.selectById(id);
        return R.ok().put("data", shetuanhuodong);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ShetuanhuodongEntity shetuanhuodong = shetuanhuodongService.selectById(id);
        
        // 计算剩余名额
        Map<String, Object> result = new HashMap<>();
        if(shetuanhuodong != null) {
            // 将实体转换为Map
            try {
                result = BeanUtils.describe(shetuanhuodong);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            Integer huodongrenshu = shetuanhuodong.getHuodongrenshu();
            if(huodongrenshu != null && huodongrenshu > 0) {
                // 统计已报名人数
                int count = huodongbaomingService.selectCount(new EntityWrapper<HuodongbaomingEntity>().eq("biaoti", shetuanhuodong.getBiaoti()));
                int shengyu = huodongrenshu - count;
                result.put("shengyuming'e", shengyu > 0 ? shengyu : 0);
            }
        }
        
        return R.ok().put("data", result);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShetuanhuodongEntity shetuanhuodong, HttpServletRequest request){
    	shetuanhuodong.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(shetuanhuodong);
        shetuanhuodongService.insert(shetuanhuodong);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ShetuanhuodongEntity shetuanhuodong, HttpServletRequest request){
    	shetuanhuodong.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(shetuanhuodong);
        shetuanhuodongService.insert(shetuanhuodong);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ShetuanhuodongEntity shetuanhuodong, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shetuanhuodong);
        shetuanhuodongService.updateById(shetuanhuodong);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        // 软删除
        for(Long id : ids) {
            ShetuanhuodongEntity shetuanhuodong = shetuanhuodongService.selectById(id);
            if(shetuanhuodong != null) {
                shetuanhuodong.setIsDeleted(1);
                shetuanhuodongService.updateById(shetuanhuodong);
            }
        }
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<ShetuanhuodongEntity> wrapper = new EntityWrapper<ShetuanhuodongEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("shezhang")) {
			wrapper.eq("zhanghao", (String)request.getSession().getAttribute("username"));
		}

		int count = shetuanhuodongService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	


}
