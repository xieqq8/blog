package com.xxx.blog.infrastructure.persistence.jdbc;

import com.xxx.blog.domain.model.Book;
import com.xxx.blog.domain.repository.BookQueryRepositry;
import org.bumishi.toolbox.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by xiexx on 2017/11/27.
 */
@Repository
public class BookQueryJdbcRepositry implements BookQueryRepositry {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public Book get(String id) {
        try {
            return jdbcTemplate.queryForObject("select * from book where id=?", BeanPropertyRowMapper.newInstance(Book.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public PageModel<Book> queryByCatalog(int page, int size, String catalog) {
        PageModel<Book> pageModel = new PageModel<>(page, size);
        List<Book> blogs = jdbcTemplate.query("select * from book where `catalog`=? ORDER BY publishTime limit ? offset ?", BeanPropertyRowMapper.newInstance(Book.class), catalog, size, (page - 1) * size);
        if (!CollectionUtils.isEmpty(blogs)) {
            pageModel.setList(blogs);
            if (blogs.size() >= size) {
                pageModel.setHasNext(true);
            }
        }
        return pageModel;
    }

    @Override
    public PageModel<Book> queryByTime(int page, int size) {
        PageModel<Book> pageModel = new PageModel<>(page, size);
        List<Book> blogs = jdbcTemplate.query("select * from book ORDER BY publishTime DESC limit ? offset ?", BeanPropertyRowMapper.newInstance(Book.class), size, (page - 1) * size);
        if (!CollectionUtils.isEmpty(blogs)) {
            pageModel.setList(blogs);
            if (blogs.size() >= size) {
                pageModel.setHasNext(true);
            }
        }
        return pageModel;
    }
}
