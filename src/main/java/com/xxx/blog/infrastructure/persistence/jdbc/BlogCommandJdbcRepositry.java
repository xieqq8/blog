package com.xxx.blog.infrastructure.persistence.jdbc;

import com.xxx.blog.domain.repository.BlogCommandRepositry;
import com.xxx.blog.domain.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by xiexx on 2017/11/26.
 */
@Repository("blogCommandJdbcRepositry")
public class BlogCommandJdbcRepositry implements BlogCommandRepositry {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public void save(Blog blog) {
        int count=jdbcTemplate.queryForObject("select count(*) from blog where id=?",Integer.class,blog.getId());
        if(count==0) {
            jdbcTemplate.update("INSERT blog (id,title,secondTitle,`catalog`,display,md,auther,publishTime,img,wechatLink) VALUE (?,?,?,?,?,?,?,?,?,?)", blog.getId(), blog.getTitle(), blog.getSecondTitle(), blog.getCatalog(), blog.getDisplay(), blog.getMd(), blog.getAuther(), blog.getPublishTime(), blog.getImg(),blog.getWechatLink());
        }else{
            update(blog);
        }
    }

    @Override
    public void update(Blog blog) {
        jdbcTemplate.update("update blog SET title=?,secondTitle=?,`catalog`=?,display=?,md=?,auther=?,publishTime=?,img=?,wechatLink=? WHERE id =?", blog.getTitle(),blog.getSecondTitle(), blog.getCatalog(), blog.getDisplay(), blog.getMd(), blog.getAuther(), blog.getPublishTime(), blog.getImg(),blog.getWechatLink(), blog.getId());

    }


    @Override
    public void remove(String id) {
          jdbcTemplate.update("DELETE FROM blog WHERE id=?",id);
    }


}
