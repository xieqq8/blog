package com.xxx.blog.domain.service;

import com.xxx.blog.domain.model.BlogId;
import com.xxx.blog.domain.repository.BlogQueryRepositry;
import com.xxx.blog.domain.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by xiexx on 2017/12/4.
 */
@Component
public class DefaultBlogId implements BlogId {

    @Autowired
    @Qualifier("blogElasticSearchRepositry")
    protected BlogQueryRepositry blogQueryRepositry;

    @Override
    public String id(Blog blog) {
        return Long.valueOf(System.currentTimeMillis()).toString();
    }
}
