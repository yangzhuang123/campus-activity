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
import com.utils.MPUtil;
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
        if(discuss.getContent() != null && !discuss.getContent().isEmpty()) {
            ew.like("content", discuss.getContent());
        }
        if(discuss.getUserName() != null && !discuss.getUserName().isEmpty()) {
            ew.like("user_name", discuss.getUserName());
        }
        if(discuss.getObjectType() != null && !discuss.getObjectType().isEmpty()) {
            ew.eq("object_type", discuss.getObjectType());
        }
        PageUtils page = discussService.queryPage(params, ew);
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
     * 保存（发布评论）
     */
    @RequestMapping("/save")
    public R save(@RequestBody DiscussEntity discuss, HttpServletRequest request){
        Long userId = (Long)request.getSession().getAttribute("userId");
        if(userId == null) {
            return R.error("请先登录");
        }
        // 校验评论内容
        if(discuss.getContent() == null || discuss.getContent().trim().isEmpty()) {
            return R.error("评论内容不能为空");
        }
        if(discuss.getContent().length() > 500) {
            return R.error("评论内容不能超过500字符");
        }
        discuss.setUserId(userId);
        String username = (String)request.getSession().getAttribute("username");
        discuss.setUserName(username != null ? username : "匿名用户");
        discuss.setCreateTime(new java.util.Date());
        discuss.setLikeCount(0);
        discuss.setStatus("正常");
        if(discuss.getParentId() == null) {
            discuss.setParentId(0L);
        }
        discussService.insert(discuss);
        return R.ok();
    }

    /**
     * 修改评论
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
        // 只更新内容字段
        DiscussEntity updateEntity = new DiscussEntity();
        updateEntity.setId(discuss.getId());
        updateEntity.setContent(discuss.getContent());
        discussService.updateById(updateEntity);
        return R.ok();
    }

    /**
     * 删除评论（允许管理员删除任意评论）
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids, HttpServletRequest request){
        Long userId = (Long)request.getSession().getAttribute("userId");
        if(userId == null) {
            return R.error("请先登录");
        }
        String tableName = (String)request.getSession().getAttribute("tableName");
        boolean isAdmin = "admin".equals(tableName);
        
        for(Long id : ids) {
            DiscussEntity discuss = discussService.selectById(id);
            if(discuss != null) {
                // 管理员可删除任意评论，普通用户只能删除自己的
                if(!isAdmin && !discuss.getUserId().equals(userId)) {
                    return R.error("无权删除此评论");
                }
                // 同时删除该评论的所有回复
                discussService.delete(new EntityWrapper<DiscussEntity>().eq("parent_id", id));
            }
        }
        discussService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 获取评论回复列表
     */
    @IgnoreAuth
    @RequestMapping("/replies/{parentId}")
    public R getReplies(@PathVariable("parentId") Long parentId, @RequestParam Map<String, Object> params){
        PageUtils page = discussService.getReplies(parentId, params);
        return R.ok().put("data", page);
    }

    /**
     * 点赞
     */
    @RequestMapping("/like/{id}")
    public R like(@PathVariable("id") Long id){
        DiscussEntity comment = discussService.selectById(id);
        if(comment == null) {
            return R.error("评论不存在");
        }
        discussService.likeComment(id);
        return R.ok("点赞成功");
    }

    /**
     * 取消点赞
     */
    @RequestMapping("/unlike/{id}")
    public R unlike(@PathVariable("id") Long id){
        DiscussEntity comment = discussService.selectById(id);
        if(comment == null) {
            return R.error("评论不存在");
        }
        if(comment.getLikeCount() <= 0) {
            return R.error("未点赞");
        }
        discussService.unlikeComment(id);
        return R.ok("取消点赞成功");
    }

}
