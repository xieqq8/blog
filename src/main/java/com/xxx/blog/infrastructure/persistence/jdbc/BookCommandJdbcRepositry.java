package com.xxx.blog.infrastructure.persistence.jdbc;

import com.xxx.blog.domain.model.Book;
import com.xxx.blog.domain.repository.BookCommandRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by xiexx on 2017/11/27.
 */
@Repository
public class BookCommandJdbcRepositry implements BookCommandRepositry {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public void save(Book catalog) {
        jdbcTemplate.update("INSERT book (id,`name`,`catalog`,publishTime,`description`,`img`) VALUES (?,?,?,?,?,?)", catalog.getId(), catalog.getName(), catalog.getCatalog(), catalog.getPublishTime(), catalog.getDescription(), catalog.getImg());
    }

    @Override
    public void update(Book catalog) {
        jdbcTemplate.update("update book SET `name`=?,`catalog`=?,publishTime=?,`description`=?,`img`=? WHERE id=?", catalog.getName(), catalog.getCatalog(), catalog.getPublishTime(), catalog.getDescription(), catalog.getImg(), catalog.getId());
    }

    @Override
    public void remove(String id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }
}
