package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.TagEntity;
import com.utils.PageUtils;
import java.util.Map;

/**
 * 标签服务
 */
public interface TagService extends IService<TagEntity> {

    PageUtils queryPage(Map<String, Object> params);

    // 根据类型获取标签列表
    java.util.List<TagEntity> getTagsByType(String type);

}
