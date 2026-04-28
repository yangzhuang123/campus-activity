package com.controller;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.WebDataBinder;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.TagEntity;
import com.service.ActivityTagService;
import com.service.TagService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 标签管理
 */
@RequestMapping("/tag")
@RestController
public class TagController {
	
	@Autowired
	private TagService tagService;
	@Autowired
	private ActivityTagService activityTagService;

	/**
	 * 排除 sort 字段的自动绑定，避免与分页排序参数 sort 冲突
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("sort");
	}

	/**
	 * 列表
	 */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,TagEntity tag){
        EntityWrapper<TagEntity> ew = new EntityWrapper<TagEntity>();
        if(params.get("name") != null && !params.get("name").toString().isEmpty()) {
            ew.like("name", params.get("name").toString());
        }
        if(params.get("type") != null && !params.get("type").toString().isEmpty()) {
            ew.eq("type", params.get("type").toString());
        }
        PageUtils page = tagService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tag), params), params));
        return R.ok().put("data", page);
    }

    @IgnoreAuth
    @RequestMapping("/list")
    public R listAll(){
        java.util.List<TagEntity> tags = tagService.selectList(
            new EntityWrapper<TagEntity>().eq("status", "正常").orderBy("sort", true)
        );
        return R.ok().put("data", tags);
    }

    /**
     * 根据类型获取标签列表
     */
    @IgnoreAuth
    @RequestMapping("/list/{type}")
    public R getTagsByType(@PathVariable("type") String type){
        java.util.List<TagEntity> tags = tagService.getTagsByType(type);
        return R.ok().put("data", tags);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        TagEntity tag = tagService.selectById(id);
        return R.ok().put("data", tag);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TagEntity tag){
        // 校验标签名称唯一性
        int count = tagService.selectCount(
                new EntityWrapper<TagEntity>()
                        .eq("name", tag.getName())
                        .eq("type", tag.getType())
        );
        if(count > 0) {
            return R.error("标签名称已存在");
        }
        tag.setCreateTime(new java.util.Date());
        tag.setStatus("正常");
        tagService.insert(tag);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TagEntity tag){
        tagService.updateById(tag);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        // 删除标签的同时删除关联
        activityTagService.deleteByTagIds(Arrays.asList(ids));
        tagService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

}
