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
import com.entity.DiscussEntity;
import com.service.DiscussService;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 评论管理
 */
@RequestMapping("/discuss")
@RestController
public class DiscussController {
	
	@Autowired
	private DiscussService discussService;

	/**
	 * 列表
	 */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,DiscussEntity discuss){
        EntityWrapper<DiscussEntity> ew = new EntityWrapper<DiscussEntity>();
		PageUtils page = discussService.queryPage(params);
        return R.ok().put("data", page);
    }

    /**
     * 获取指定对象的评论列表
     */
    @IgnoreAuth
    @RequestMapping("/list/{objectType}/{objectId}")
    public R getComments(@PathVariable("objectType") String objectType, @PathVariable("objectId") Long objectId, @RequestParam Map<String, Object> params){
        PageUtils page = discussService.getCommentsByObject(objectType, objectId, params);
        return R.ok().put("data", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        DiscussEntity discuss = discussService.selectById(id);
        return R.ok().put("data", discuss);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DiscussEntity discuss, HttpServletRequest request){
        Long userId = (Long)request.getSession().getAttribute("userId");
        if(userId == null) {
            return R.error("请先登录");
        }
        discuss.setUserId(userId);
        discuss.setCreateTime(new java.util.Date());
        discuss.setLikeCount(0);
        discuss.setStatus("正常");
        discussService.insert(discuss);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody DiscussEntity discuss, HttpServletRequest request){
        Long userId = (Long)request.getSession().getAttribute("userId");
        if(userId == null) {
            return R.error("请先登录");
        }
        // 只能修改自己的评论
        DiscussEntity oldDiscuss = discussService.selectById(discuss.getId());
        if(oldDiscuss == null || !oldDiscuss.getUserId().equals(userId)) {
            return R.error("无权修改此评论");
        }
        discussService.updateById(discuss);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids, HttpServletRequest request){
        Long userId = (Long)request.getSession().getAttribute("userId");
        if(userId == null) {
            return R.error("请先登录");
        }
        for(Long id : ids) {
            DiscussEntity discuss = discussService.selectById(id);
            if(discuss != null && !discuss.getUserId().equals(userId)) {
                return R.error("无权删除此评论");
            }
        }
        discussService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 点赞
     */
    @RequestMapping("/like/{id}")
    public R like(@PathVariable("id") Long id){
        discussService.likeComment(id);
        return R.ok("点赞成功");
    }

    /**
     * 取消点赞
     */
    @RequestMapping("/unlike/{id}")
    public R unlike(@PathVariable("id") Long id){
        discussService.unlikeComment(id);
        return R.ok("取消点赞成功");
    }

}
