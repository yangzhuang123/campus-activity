package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ActivityTagDao;
import com.dao.TagDao;
import com.entity.ActivityTagEntity;
import com.entity.TagEntity;
import com.service.ActivityTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 活动标签关联服务实现
 */
@Service("activityTagService")
public class ActivityTagServiceImpl extends ServiceImpl<ActivityTagDao, ActivityTagEntity> implements ActivityTagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public void bindTags(Long activityId, Long[] tagIds) {
        // 先删除旧关联
        this.delete(new EntityWrapper<ActivityTagEntity>().eq("activity_id", activityId));
        
        // 批量插入新关联
        if (tagIds != null && tagIds.length > 0) {
            List<ActivityTagEntity> list = new ArrayList<>();
            Date now = new Date();
            for (Long tagId : tagIds) {
                ActivityTagEntity entity = new ActivityTagEntity();
                entity.setActivityId(activityId);
                entity.setTagId(tagId);
                entity.setCreateTime(now);
                list.add(entity);
            }
            this.insertBatch(list);
        }
    }

    @Override
    public List<TagEntity> getTagsByActivityId(Long activityId) {
        // 查询关联的标签ID列表
        List<Long> tagIds = this.getTagIdsByActivityId(activityId);
        if (tagIds == null || tagIds.isEmpty()) {
            return new ArrayList<>();
        }
        // 查询标签信息
        return tagDao.selectList(
                new EntityWrapper<TagEntity>()
                        .in("id", tagIds)
                        .eq("status", "正常")
                        .orderBy("sort", true)
        );
    }

    @Override
    public List<Long> getTagIdsByActivityId(Long activityId) {
        List<ActivityTagEntity> relations = this.selectList(
                new EntityWrapper<ActivityTagEntity>().eq("activity_id", activityId)
        );
        List<Long> tagIds = new ArrayList<>();
        for (ActivityTagEntity relation : relations) {
            tagIds.add(relation.getTagId());
        }
        return tagIds;
    }

    @Override
    public void deleteByTagId(Long tagId) {
        this.delete(new EntityWrapper<ActivityTagEntity>().eq("tag_id", tagId));
    }

    @Override
    public void deleteByTagIds(List<Long> tagIds) {
        this.delete(new EntityWrapper<ActivityTagEntity>().in("tag_id", tagIds));
    }
}
