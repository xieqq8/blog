package com.xxx.blog.infrastructure.persistence.jdbc;

import org.apache.commons.collections.map.HashedMap;
import com.xxx.blog.domain.repository.SiteConfigRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by xiexx on 2017/12/18.
 */
@Repository
public class SiteConfigJdbcRepositry implements SiteConfigRepositry {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void update(Map<String,String> config) {
        if(CollectionUtils.isEmpty(config))return;
       jdbcTemplate.update("DELETE FROM site_config");
        Iterator<String> keys=config.keySet().iterator();
        jdbcTemplate.batchUpdate("INSERT site_config (`k`,`v`) VALUES (?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                    try {
                        String key=keys.next();
                        ps.setString(1, key);
                        ps.setString(2, config.get(key));
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
            }

            @Override
            public int getBatchSize() {
                return config.size();
            }
        });
    }

    @Override
    public Map<String,Object> getConfig() {
        List<Map<String,Object>> list = jdbcTemplate.queryForList("select * from site_config");
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyMap();
        }
        Map<String,Object> map=new HashedMap(list.size());
        list.stream().forEach(item->{
            map.put(item.get("k").toString(),item.get("v"));
        });
        return map;
    }
}
