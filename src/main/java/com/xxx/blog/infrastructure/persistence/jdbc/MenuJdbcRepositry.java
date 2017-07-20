package com.xxx.blog.infrastructure.persistence.jdbc;

import org.springframework.stereotype.Repository;

/**
 * Created by xiexx on 2017/11/27.
 */
@Repository("menuJdbcRepositry")
public class MenuJdbcRepositry extends AbstractNavigationNodeJdbcRepositry {

    @Override
    protected String getTable() {
        return "`nav`";
    }
}
