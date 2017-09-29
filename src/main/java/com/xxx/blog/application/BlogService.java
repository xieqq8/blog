package com.xxx.blog.application;

import com.google.common.eventbus.EventBus;
import com.xxx.blog.domain.model.Blog;
import com.xxx.blog.domain.model.event.BlogDeleteEvent;
import com.xxx.blog.domain.model.event.BlogUpdateEvent;
import com.xxx.blog.domain.repository.BlogCommandRepositry;
import com.xxx.blog.domain.repository.BlogQueryRepositry;
import com.xxx.blog.infrastructure.persistence.jdbc.BlogQueryJdbcRepositry;
import com.xxx.toolbox.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by xiexx on 2017/11/27.
 */
@Service
public class BlogService {

    @Autowired
    @Qualifier("blogElasticSearchRepositry")
    protected BlogCommandRepositry blogCommandRepositry;

    @Autowired
    @Qualifier("blogElasticSearchRepositry")
    protected BlogQueryRepositry blogQueryRepositry;

    @Autowired
    @Qualifier("blogQueryJdbcRepositry")
    protected BlogQueryJdbcRepositry blogQueryJdbcRepositry;


    @Autowired
    protected EventBus eventBus;


    public String addBlog(Blog blog){
        blogCommandRepositry.save(blog);
        eventBus.post(new BlogUpdateEvent(blog));
        return blog.getId();
    }

    public void updateBlog(Blog blog){
        blogCommandRepositry.update(blog);
        eventBus.post(new BlogUpdateEvent(blog));
    }

    public void delete(String id) {
        blogCommandRepositry.remove(id);
        eventBus.post(new BlogDeleteEvent(id));
    }

    @Cacheable(EventHandler.BLOG_CACHE)
    public Blog getBlog(String id){
        return blogQueryRepositry.get(id);
    }

    @Cacheable(EventHandler.BLOG_PAGE_CACHE)
    public PageModel<Blog> queryByTime(int page,int size){
        return blogQueryRepositry.queryByTime(page, size);
    }

    @Cacheable(EventHandler.BLOG_PAGE_CACHE)
    public PageModel<Blog> queryByCatalog(int page, int size, String catalog){
        return blogQueryRepositry.queryByCatalog(page, size, catalog);
    }

    public PageModel<Blog> search(int page, int size, String keywords) {
        return blogQueryRepositry.queryByKeyword(page, size, keywords);
    }

    /**
     *
     * @param kw
     * @return
     */
    public List<Blog> getSimilarBlogs(String kw){
        PageModel<Blog> pageModel= search(1,10,kw);
        if(pageModel==null){
            return Collections.emptyList();
        }
        return pageModel.getList();
    }

    //更新索引
    public void updateIndex(){
        int count=blogQueryJdbcRepositry.getCount();
        List<Blog> list=blogQueryJdbcRepositry.queryByTime(1,count).getList();
        list.stream().forEach(blog ->   blogCommandRepositry.save(blog));
    }

}
