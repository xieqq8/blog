package com.xxx.blog.infrastructure.persistence.jdbc;

import com.xxx.blog.domain.model.BookIndex;
import com.xxx.blog.domain.repository.BookIndexRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * gitbook 目录索引
 * Created by xiexx on 2017/11/27.
 */
@Repository
public class BookIndexJdbcRepositry  implements BookIndexRepositry {
    
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public List<BookIndex> list() {
        return jdbcTemplate.query("select * from book_index", BeanPropertyRowMapper.newInstance(BookIndex.class));

    }

    @Override
    public void add(BookIndex catalog) {
        jdbcTemplate.update("INSERT book_index (id,label,path,`level`,`order`,`url`,`type`,`style`,`disabled`,`bookId`) VALUES (?,?,?,?,?,?,?,?,?,?)", catalog.getId(), catalog.getLabel(), catalog.getPath(), catalog.getLevel(), catalog.getOrder(), catalog.getUrl(), catalog.getType(), catalog.getStyle(), catalog.isDisabled() ? 1 : 0, catalog.getBookId());
    }

    @Override
    public void update(BookIndex catalog) {
        jdbcTemplate.update("update book_index SET label=?,`order`=?,url=?,disabled=?,`type`=?,`style`=? WHERE id=?", catalog.getLabel(), catalog.getOrder(), catalog.getUrl(), catalog.isDisabled() ? 1 : 0, catalog.getType(), catalog.getStyle(), catalog.getId());

    }

    @Override
    public void remove(String id) {
        jdbcTemplate.update("DELETE FROM book_index WHERE id=?",id);
    }

    @Override
    public void removeByBookId(String bookId) {
        jdbcTemplate.update("DELETE FROM book_index WHERE bookId=?", bookId);
    }

    @Override
    public void disable(String id) {
        jdbcTemplate.update("update book_index SET disabled=1 WHERE id=?",id);

    }

    @Override
    public void enable(String id) {
        jdbcTemplate.update("update book_index SET disabled=0 WHERE id=?",id);
    }

    @Override
    public BookIndex get(String id) {
        try{
            return jdbcTemplate.queryForObject("select * from book_index where id=?",BeanPropertyRowMapper.newInstance(BookIndex.class),id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<BookIndex> rootList() {
        return jdbcTemplate.query("select * from book_index where level=1", BeanPropertyRowMapper.newInstance(BookIndex.class));
    }

    @Override
    public List<BookIndex> getByBook(String bookId) {
        return jdbcTemplate.query("select * from book_index where bookId=?", BeanPropertyRowMapper.newInstance(BookIndex.class), bookId);
    }
}
