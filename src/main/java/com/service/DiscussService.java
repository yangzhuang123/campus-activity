package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.DiscussEntity;
import com.utils.PageUtils;
import java.util.Map;

/**
 * 评论服务
 */
public interface DiscussService extends IService<DiscussEntity> {

    PageUtils queryPage(Map<String, Object> params);

    // 获取指定对象的评论列表
    PageUtils getCommentsByObject(String objectType, Long objectId, Map<String, Object> params);

    // 点赞评论
    void likeComment(Long commentId);

    // 取消点赞
    void unlikeComment(Long commentId);

}
