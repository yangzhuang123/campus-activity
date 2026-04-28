package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.TagDao;
import com.entity.TagEntity;
import com.service.TagService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 标签服务实现
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagDao, TagEntity> implements TagService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TagEntity> page = this.selectPage(
                new Query<TagEntity>(params).getPage(),
                new EntityWrapper<TagEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<TagEntity> wrapper) {
        Page<TagEntity> page = this.selectPage(
                new Query<TagEntity>(params).getPage(),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<TagEntity> getTagsByType(String type) {
        return this.selectList(
                new EntityWrapper<TagEntity>()
                        .eq("type", type)
                        .eq("status", "正常")
                        .orderBy("sort", true)
        );
    }

}
