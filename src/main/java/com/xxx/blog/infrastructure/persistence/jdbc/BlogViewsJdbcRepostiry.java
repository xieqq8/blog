package com.xxx.blog.infrastructure.persistence.jdbc;

import com.xxx.blog.domain.repository.BlogViewsRepostiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by xiexx on 2017/1/10.
 */
@Repository
public class BlogViewsJdbcRepostiry implements BlogViewsRepostiry {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public void addViews(String id) {
        try {
            String row=jdbcTemplate.queryForObject("select id from blog_views where id=?", String.class, id);
            if(id.equals(row)) {
                jdbcTemplate.update("UPDATE blog_views SET views=views+1 WHERE id=?", id);
            }
        } catch (EmptyResultDataAccessException e) {
            jdbcTemplate.update("INSERT blog_views (id,views) VALUES (?,?)", id, 1);
        }

    }


    @Override
    public int getViews(String blogId) {
        try {
            return jdbcTemplate.queryForObject("select views from blog_views WHERE id=?", Integer.class, blogId);
        } catch (Exception e) {
            return 0;
        }
    }
}
