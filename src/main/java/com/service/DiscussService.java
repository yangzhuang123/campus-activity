package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.DiscussEntity;
import com.utils.PageUtils;
import java.util.Map;

/**
 * 评论服务
 */
public interface DiscussService extends IService<DiscussEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Wrapper<DiscussEntity> wrapper);

    // 获取指定对象的评论列表
    PageUtils getCommentsByObject(String objectType, Long objectId, Map<String, Object> params);

    // 点赞评论
    void likeComment(Long commentId);

    // 取消点赞
    void unlikeComment(Long commentId);

    // 获取评论的回复列表
    PageUtils getReplies(Long parentId, Map<String, Object> params);

}
