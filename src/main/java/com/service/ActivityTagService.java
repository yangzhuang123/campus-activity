package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.ActivityTagEntity;
import com.entity.TagEntity;

import java.util.List;

/**
 * 活动标签关联服务
 */
public interface ActivityTagService extends IService<ActivityTagEntity> {

    /**
     * 绑定活动标签（先删后插）
     */
    void bindTags(Long activityId, Long[] tagIds);

    /**
     * 获取活动的标签列表
     */
    List<TagEntity> getTagsByActivityId(Long activityId);

    /**
     * 根据活动ID查询关联的标签ID列表
     */
    List<Long> getTagIdsByActivityId(Long activityId);

    /**
     * 根据标签ID删除关联
     */
    void deleteByTagId(Long tagId);

    /**
     * 根据标签ID列表批量删除关联
     */
    void deleteByTagIds(List<Long> tagIds);

}
