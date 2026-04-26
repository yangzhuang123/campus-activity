package com.controller;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.TagEntity;
import com.service.TagService;
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

	/**
	 * 列表
	 */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,TagEntity tag){
        EntityWrapper<TagEntity> ew = new EntityWrapper<TagEntity>();
		PageUtils page = tagService.queryPage(params);
        return R.ok().put("data", page);
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
        tagService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

}
