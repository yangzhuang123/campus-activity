package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.DiscussDao;
import com.entity.DiscussEntity;
import com.service.DiscussService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 评论服务实现
 */
@Service("discussService")
public class DiscussServiceImpl extends ServiceImpl<DiscussDao, DiscussEntity> implements DiscussService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DiscussEntity> page = this.selectPage(
                new Query<DiscussEntity>(params).getPage(),
                new EntityWrapper<DiscussEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils getCommentsByObject(String objectType, Long objectId, Map<String, Object> params) {
        Page<DiscussEntity> page = this.selectPage(
                new Query<DiscussEntity>(params).getPage(),
                new EntityWrapper<DiscussEntity>()
                        .eq("object_type", objectType)
                        .eq("object_id", objectId)
                        .eq("parent_id", 0) // 只查询顶级评论
                        .orderBy("create_time", false)
        );

        return new PageUtils(page);
    }

    @Override
    public void likeComment(Long commentId) {
        DiscussEntity comment = this.selectById(commentId);
        if (comment != null) {
            comment.setLikeCount(comment.getLikeCount() + 1);
            this.updateById(comment);
        }
    }

    @Override
    public void unlikeComment(Long commentId) {
        DiscussEntity comment = this.selectById(commentId);
        if (comment != null && comment.getLikeCount() > 0) {
            comment.setLikeCount(comment.getLikeCount() - 1);
            this.updateById(comment);
        }
    }

}
