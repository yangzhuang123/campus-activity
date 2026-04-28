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

import com.entity.HuodongbaomingEntity;
import com.entity.ShetuanhuodongEntity;
import com.entity.XxiaoxiEntity;
import com.entity.view.HuodongbaomingView;

import com.service.HuodongbaomingService;
import com.service.TokenService;
import com.service.ShetuanhuodongService;
import com.service.XxiaoxiService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;


@RestController
@RequestMapping("/huodongbaoming")
public class HuodongbaomingController {
    @Autowired
    private HuodongbaomingService huodongbaomingService;
    @Autowired
    private ShetuanhuodongService shetuanhuodongService;
    @Autowired
    private XxiaoxiService xiaoxiService;



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,HuodongbaomingEntity huodongbaoming,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("shezhang")) {
			huodongbaoming.setZhanghao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("xuesheng")) {
			huodongbaoming.setXuehao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<HuodongbaomingEntity> ew = new EntityWrapper<HuodongbaomingEntity>();
		PageUtils page = huodongbaomingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, huodongbaoming), params), params));

        return R.ok().put("data", page);
    }

    /**
     * 前端列表
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,HuodongbaomingEntity huodongbaoming,
		HttpServletRequest request){
        EntityWrapper<HuodongbaomingEntity> ew = new EntityWrapper<HuodongbaomingEntity>();
        
        // 根据当前登录用户筛选
        String tableName = request.getSession().getAttribute("tableName") != null ? 
                          request.getSession().getAttribute("tableName").toString() : null;
        if(tableName != null && tableName.equals("xuesheng")) {
            // 学生只能看到自己的报名记录
            String xuehao = (String)request.getSession().getAttribute("username");
            if(StringUtils.isNotBlank(xuehao)) {
                ew.eq("xuehao", xuehao);
            }
        }
        
		PageUtils page = huodongbaomingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, huodongbaoming), params), params));

        // 为每条报名记录补充活动状态信息
        List<?> rawList = page.getList();
        if(rawList != null && !rawList.isEmpty()) {
            List<Map<String, Object>> enrichedList = new ArrayList<>();
            for(Object item : rawList) {
                try {
                    // 转换为 Map 以便添加扩展属性
                    Map<String, Object> enriched = cn.hutool.core.bean.BeanUtil.beanToMap(item);
                    String biaoti = (String) enriched.get("biaoti");
                    if(StringUtils.isNotBlank(biaoti)) {
                        ShetuanhuodongEntity activity = shetuanhuodongService.selectOne(
                            new EntityWrapper<ShetuanhuodongEntity>().eq("biaoti", biaoti).last("LIMIT 1")
                        );
                        if(activity != null) {
                            // 自动更新已结束状态
                            if(activity.getJieshushijian() != null && activity.getJieshushijian().before(new Date())) {
                                if(!"已结束".equals(activity.getHuodongzhuangtai())) {
                                    activity.setHuodongzhuangtai("已结束");
                                    shetuanhuodongService.updateById(activity);
                                }
                            }
                            enriched.put("huodongzhuangtai", activity.getHuodongzhuangtai());
                        }
                    }
                    enrichedList.add(enriched);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            page.setList(enrichedList);
        }

        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( HuodongbaomingEntity huodongbaoming, @RequestParam Map<String, Object> params){
        	EntityWrapper<HuodongbaomingEntity> ew = new EntityWrapper<HuodongbaomingEntity>();
        	String sfsh = params.get("sfsh") != null ? params.get("sfsh").toString() : null;
        	if ("isNull".equals(sfsh)) {
        		ew.isNull("sfsh");
        	} else if (sfsh != null && !sfsh.isEmpty()) {
        		ew.eq("sfsh", sfsh);
        	}
       	ew.allEq(MPUtil.allEQMapPre( huodongbaoming, "huodongbaoming"));
        PageUtils page = huodongbaomingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, huodongbaoming), params), params));
        return R.ok().put("data", page);
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(HuodongbaomingEntity huodongbaoming){
        EntityWrapper< HuodongbaomingEntity> ew = new EntityWrapper< HuodongbaomingEntity>();
		ew.allEq(MPUtil.allEQMapPre( huodongbaoming, "huodongbaoming"));
		HuodongbaomingView huodongbaomingView =  huodongbaomingService.selectView(ew);
		return R.ok("查询活动报名成功").put("data", huodongbaomingView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        HuodongbaomingEntity huodongbaoming = huodongbaomingService.selectById(id);
        return R.ok().put("data", huodongbaoming);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        HuodongbaomingEntity huodongbaoming = huodongbaomingService.selectById(id);
        return R.ok().put("data", huodongbaoming);
    }




    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody HuodongbaomingEntity huodongbaoming, HttpServletRequest request){
    	huodongbaoming.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(huodongbaoming);

        // 检查重复报名
        String xuehao = huodongbaoming.getXuehao();
        String biaoti = huodongbaoming.getBiaoti();
        if(StringUtils.isNotBlank(xuehao) && StringUtils.isNotBlank(biaoti)) {
            int existsCount = huodongbaomingService.selectCount(
                new EntityWrapper<HuodongbaomingEntity>()
                    .eq("xuehao", xuehao)
                    .eq("biaoti", biaoti)
            );
            if(existsCount > 0) {
                return R.error("您已报名该活动，请勿重复报名");
            }
        }

        if(StringUtils.isNotBlank(biaoti)) {
            ShetuanhuodongEntity shetuanhuodong = shetuanhuodongService.selectOne(new EntityWrapper<ShetuanhuodongEntity>().eq("biaoti", biaoti));
            if(shetuanhuodong != null) {
                Integer huodongrenshu = shetuanhuodong.getHuodongrenshu();
                if(huodongrenshu != null && huodongrenshu > 0) {
                    int count = huodongbaomingService.selectCount(new EntityWrapper<HuodongbaomingEntity>().eq("biaoti", biaoti));
                    if(count >= huodongrenshu) {
                        return R.error("活动名额已满，无法报名");
                    }
                    if(count + 1 >= huodongrenshu) {
                        XxiaoxiEntity xiaoxi = new XxiaoxiEntity();
                        xiaoxi.setYonghu(shetuanhuodong.getZhanghao());
                        xiaoxi.setYonghutable("shezhang");
                        xiaoxi.setXiaoxileixing("baomingyiman");
                        xiaoxi.setXiaoxibiaoti( "报名已满");
                        xiaoxi.setXiaoxineirong("您的活动「" + biaoti + "」报名已满");
                        xiaoxi.setFabushijian(new Date());
                        xiaoxi.setYuedu(0);
                        xiaoxiService.sendMessage(xiaoxi);
                    }
                }
            }
        }

        huodongbaomingService.insert(huodongbaoming);
        return R.ok();
    }

    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody HuodongbaomingEntity huodongbaoming, HttpServletRequest request){
    	huodongbaoming.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(huodongbaoming);

        // 检查重复报名
        String xuehao = huodongbaoming.getXuehao();
        String biaoti = huodongbaoming.getBiaoti();
        if(StringUtils.isNotBlank(xuehao) && StringUtils.isNotBlank(biaoti)) {
            int existsCount = huodongbaomingService.selectCount(
                new EntityWrapper<HuodongbaomingEntity>()
                    .eq("xuehao", xuehao)
                    .eq("biaoti", biaoti)
            );
            if(existsCount > 0) {
                return R.error("您已报名该活动，请勿重复报名");
            }
        }

        if(StringUtils.isNotBlank(biaoti)) {
            ShetuanhuodongEntity shetuanhuodong = shetuanhuodongService.selectOne(new EntityWrapper<ShetuanhuodongEntity>().eq("biaoti", biaoti));
            if(shetuanhuodong != null) {
                Integer huodongrenshu = shetuanhuodong.getHuodongrenshu();
                if(huodongrenshu != null && huodongrenshu > 0) {
                    int count = huodongbaomingService.selectCount(new EntityWrapper<HuodongbaomingEntity>().eq("biaoti", biaoti));
                    if(count >= huodongrenshu) {
                        return R.error("活动名额已满，无法报名");
                    }
                    if(count + 1 >= huodongrenshu) {
                        XxiaoxiEntity xiaoxi = new XxiaoxiEntity();
                        xiaoxi.setYonghu(shetuanhuodong.getZhanghao());
                        xiaoxi.setYonghutable("shezhang");
                        xiaoxi.setXiaoxileixing("baomingyiman");
                        xiaoxi.setXiaoxibiaoti("报名已满");
                        xiaoxi.setXiaoxineirong("您的活动「" + biaoti + "」报名已满");
                        xiaoxi.setFabushijian(new Date());
                        xiaoxi.setYuedu(0);
                        xiaoxiService.sendMessage(xiaoxi);
                    }
                }
            }
        }

        huodongbaomingService.insert(huodongbaoming);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody HuodongbaomingEntity huodongbaoming, HttpServletRequest request){
        HuodongbaomingEntity old = huodongbaomingService.selectById(huodongbaoming.getId());
        if(old != null && "否".equals(old.getSfsh()) && "是".equals(huodongbaoming.getSfsh())) {
            XxiaoxiEntity xiaoxi = new XxiaoxiEntity();
            xiaoxi.setYonghu(huodongbaoming.getXuehao());
            xiaoxi.setYonghutable("xuesheng");
            xiaoxi.setXiaoxileixing("baomingtongguo");
            xiaoxi.setXiaoxibiaoti("报名通过");
            xiaoxi.setXiaoxineirong("您的活动「" + huodongbaoming.getBiaoti() + "」报名已通过审核");
            xiaoxi.setFabushijian(new Date());
            xiaoxi.setYuedu(0);
            xiaoxiService.sendMessage(xiaoxi);
        }
        huodongbaomingService.updateById(huodongbaoming);
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        huodongbaomingService.deleteBatchIds(Arrays.asList(ids));
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

		Wrapper<HuodongbaomingEntity> wrapper = new EntityWrapper<HuodongbaomingEntity>();
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
		if(tableName.equals("xuesheng")) {
			wrapper.eq("xuehao", (String)request.getSession().getAttribute("username"));
		}

		int count = huodongbaomingService.selectCount(wrapper);
		return R.ok().put("count", count);
	}



}
