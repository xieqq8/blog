package com.xxx.blog.infrastructure.persistence.jdbc;

import org.springframework.stereotype.Repository;

/**
 * Created by xiexx on 2017/11/27.
 */
@Repository("catalogJdbcRepositry")
public class CatalogJdbcRepositry extends AbstractNavigationNodeJdbcRepositry {

    @Override
    protected String getTable() {
        return "`catalog`";
    }
}
