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
import com.entity.XxiaoxiEntity;
import com.entity.view.ShetuanhuodongView;

import com.service.ShetuanhuodongService;
import com.service.XxiaoxiService;
import com.service.TokenService;
import com.service.HuodongbaomingService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;


@RestController
@RequestMapping("/shetuanhuodong")
public class ShetuanhuodongController {
    @Autowired
    private ShetuanhuodongService shetuanhuodongService;
    @Autowired
    private HuodongbaomingService huodongbaomingService;
    @Autowired
    private XxiaoxiService xiaoxiService;

    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ShetuanhuodongEntity shetuanhuodong,
		HttpServletRequest request){
        // 自动更新已结束活动状态
        autoUpdateExpiredActivities();
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("shezhang")) {
			shetuanhuodong.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<ShetuanhuodongEntity> ew = new EntityWrapper<ShetuanhuodongEntity>();
        ew.eq("is_deleted", 0);
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
        if(params.get("huodongzhuangtai") != null && !params.get("huodongzhuangtai").toString().isEmpty()) {
            ew.eq("huodongzhuangtai", params.get("huodongzhuangtai"));
        }
        if(params.get("startDate") != null && !params.get("startDate").toString().isEmpty()) {
            ew.ge("kaishishijian", params.get("startDate"));
        }
        if(params.get("endDate") != null && !params.get("endDate").toString().isEmpty()) {
            ew.le("jieshushijian", params.get("endDate"));
        }
		PageUtils page = shetuanhuodongService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shetuanhuodong), params), params));

        return R.ok().put("data", page);
    }

    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ShetuanhuodongEntity shetuanhuodong,
		HttpServletRequest request){
        // 自动更新已结束活动状态
        autoUpdateExpiredActivities();

        EntityWrapper<ShetuanhuodongEntity> ew = new EntityWrapper<ShetuanhuodongEntity>();
        ew.eq("is_deleted", 0);
        ew.eq("is_publish", "已发布");
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
        if(params.get("huodongzhuangtai") != null && !params.get("huodongzhuangtai").toString().isEmpty()) {
            ew.eq("huodongzhuangtai", params.get("huodongzhuangtai"));
        }
        if(params.get("startDate") != null && !params.get("startDate").toString().isEmpty()) {
            ew.ge("kaishishijian", params.get("startDate"));
        }
        if(params.get("endDate") != null && !params.get("endDate").toString().isEmpty()) {
            ew.le("jieshushijian", params.get("endDate"));
        }
		PageUtils page = shetuanhuodongService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shetuanhuodong), params), params));

        // 为每条活动补充报名人数信息
        List<?> rawList = page.getList();
        if(rawList != null && !rawList.isEmpty()) {
            List<Map<String, Object>> enrichedList = new ArrayList<>();
            for(Object item : rawList) {
                try {
                    Map<String, Object> enriched = BeanUtils.describe(item);
                    String biaoti = (String) enriched.get("biaoti");
                    Object huodongrenshuObj = enriched.get("huodongrenshu");
                    int huodongrenshu = huodongrenshuObj != null ? Integer.parseInt(huodongrenshuObj.toString()) : 0;
                    if(StringUtils.isNotBlank(biaoti) && huodongrenshu > 0) {
                        int count = huodongbaomingService.selectCount(
                            new EntityWrapper<HuodongbaomingEntity>().eq("biaoti", biaoti)
                        );
                        enriched.put("baomingrenshu", count);
                        int shengyu = huodongrenshu - count;
                        enriched.put("shengyuminge", shengyu > 0 ? shengyu : 0);
                    } else {
                        enriched.put("baomingrenshu", 0);
                        enriched.put("shengyuminge", huodongrenshu > 0 ? huodongrenshu : 0);
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

    @RequestMapping("/lists")
    public R getLists(@RequestParam Map<String, Object> params, ShetuanhuodongEntity shetuanhuodong, HttpServletRequest request){
        EntityWrapper<ShetuanhuodongEntity> ew = new EntityWrapper<ShetuanhuodongEntity>();
        ew.eq("is_deleted", 0);
        if(params.get("zhanghao") != null && !params.get("zhanghao").toString().isEmpty()) {
            ew.eq("zhanghao", params.get("zhanghao"));
        }
        PageUtils page = shetuanhuodongService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shetuanhuodong), params), params));
        return R.ok().put("data", page);
    }

    @RequestMapping("/query")
    public R query(ShetuanhuodongEntity shetuanhuodong){
        EntityWrapper< ShetuanhuodongEntity> ew = new EntityWrapper< ShetuanhuodongEntity>();
 		ew.allEq(MPUtil.allEQMapPre( shetuanhuodong, "shetuanhuodong"));
		ShetuanhuodongView shetuanhuodongView =  shetuanhuodongService.selectView(ew);
		return R.ok("查询社团活动成功").put("data", shetuanhuodongView);
    }

    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShetuanhuodongEntity shetuanhuodong = shetuanhuodongService.selectById(id);
        return R.ok().put("data", shetuanhuodong);
    }

    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ShetuanhuodongEntity shetuanhuodong = shetuanhuodongService.selectById(id);

        Map<String, Object> result = new HashMap<>();
        if(shetuanhuodong != null) {
            // 自动更新活动状态：如果结束时间已过，标记为"已结束"
            if(shetuanhuodong.getJieshushijian() != null && shetuanhuodong.getJieshushijian().before(new Date())) {
                if(!"已结束".equals(shetuanhuodong.getHuodongzhuangtai())) {
                    shetuanhuodong.setHuodongzhuangtai("已结束");
                    shetuanhuodongService.updateById(shetuanhuodong);
                }
            }
            try {
                result = BeanUtils.describe(shetuanhuodong);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Integer huodongrenshu = shetuanhuodong.getHuodongrenshu();
            if(huodongrenshu != null && huodongrenshu > 0) {
                int count = huodongbaomingService.selectCount(new EntityWrapper<HuodongbaomingEntity>().eq("biaoti", shetuanhuodong.getBiaoti()));
                int shengyu = huodongrenshu - count;
                result.put("yibaomingrenshu", count);
                result.put("shengyuminge", shengyu > 0 ? shengyu : 0);
            } else {
                result.put("yibaomingrenshu", 0);
                result.put("shengyuminge", 0);
            }
        }

        return R.ok().put("data", result);
    }

    @RequestMapping("/save")
    public R save(@RequestBody ShetuanhuodongEntity shetuanhuodong, HttpServletRequest request){
    	shetuanhuodong.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
        shetuanhuodongService.insert(shetuanhuodong);
        return R.ok();
    }

    @RequestMapping("/add")
    public R add(@RequestBody ShetuanhuodongEntity shetuanhuodong, HttpServletRequest request){
    	shetuanhuodong.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
        shetuanhuodongService.insert(shetuanhuodong);
        return R.ok();
    }

    @RequestMapping("/saveDraft")
    public R saveDraft(@RequestBody ShetuanhuodongEntity shetuanhuodong, HttpServletRequest request){
    	shetuanhuodong.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	shetuanhuodong.setIsPublish("草稿");
    	shetuanhuodong.setBaomingzhuangtai("开放报名");
    	shetuanhuodong.setSfsh("是");
        shetuanhuodongService.insert(shetuanhuodong);
        return R.ok();
    }

    @RequestMapping("/publishNow")
    public R publishNow(@RequestBody ShetuanhuodongEntity shetuanhuodong, HttpServletRequest request){
    	shetuanhuodong.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	shetuanhuodong.setIsPublish("已发布");
    	shetuanhuodong.setBaomingzhuangtai("开放报名");
    	shetuanhuodong.setSfsh("是");
        shetuanhuodongService.insert(shetuanhuodong);

        XxiaoxiEntity xiaoxi = new XxiaoxiEntity();
        xiaoxi.setYonghu(shetuanhuodong.getZhanghao());
        xiaoxi.setYonghutable("shezhang");
        xiaoxi.setXiaoxileixing("fabuchenggong");
        xiaoxi.setXiaoxibiaoti("发布成功");
        xiaoxi.setXiaoxineirong("您的活动「" + shetuanhuodong.getBiaoti() + "」已发布成功");
        xiaoxi.setFabushijian(new Date());
        xiaoxi.setYuedu(0);
        xiaoxiService.sendMessage(xiaoxi);

        return R.ok();
    }

    @RequestMapping("/closeBaoming/{id}")
    public R closeBaoming(@PathVariable("id") Long id){
        ShetuanhuodongEntity shetuanhuodong = shetuanhuodongService.selectById(id);
        if(shetuanhuodong == null) {
            return R.error("活动不存在");
        }
        shetuanhuodong.setBaomingzhuangtai("已关闭");
        shetuanhuodongService.updateById(shetuanhuodong);
        return R.ok();
    }

    @RequestMapping("/update")
    public R update(@RequestBody ShetuanhuodongEntity shetuanhuodong, HttpServletRequest request){
        shetuanhuodongService.updateById(shetuanhuodong);
        return R.ok();
    }


    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        for(Long id : ids) {
            ShetuanhuodongEntity shetuanhuodong = shetuanhuodongService.selectById(id);
            if(shetuanhuodong != null) {
                shetuanhuodong.setIsDeleted(1);
                shetuanhuodongService.updateById(shetuanhuodong);
            }
        }
        return R.ok();
    }

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

	/**
	 * 自动更新已过期活动的状态为"已结束"
	 */
	private void autoUpdateExpiredActivities() {
		try {
			EntityWrapper<ShetuanhuodongEntity> wrapper = new EntityWrapper<ShetuanhuodongEntity>();
			wrapper.eq("is_deleted", 0);
			wrapper.isNotNull("jieshushijian");
			wrapper.lt("jieshushijian", new Date());
			wrapper.ne("huodongzhuangtai", "已结束");
			List<ShetuanhuodongEntity> expiredList = shetuanhuodongService.selectList(wrapper);
			for(ShetuanhuodongEntity item : expiredList) {
				item.setHuodongzhuangtai("已结束");
				shetuanhuodongService.updateById(item);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
