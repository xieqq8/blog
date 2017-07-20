package com.xxx.blog.domain.repository;


import java.util.Map;

/**
 * Created by xiexx on 2017/12/18.
 */
public interface SiteConfigRepositry {

    void update(Map<String, String> config);

    Map<String,Object> getConfig();
}
