package com.xxx.blog.application;


import com.xxx.blog.domain.repository.SiteConfigRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author qiang.xie
 * @date 2016/12/27
 */
@Service
@CacheConfig(cacheNames = "siteconfig")
public class SiteConfigService {

    @Autowired
    private SiteConfigRepositry siteConfigRepositry;

    @Cacheable(key = "'siteconfig'")
    public Map<String,Object> siteConfig() {
        return siteConfigRepositry.getConfig();
    }

    @CacheEvict(key = "'siteconfig'")
    public void update(Map<String, String> siteConfig) {
        siteConfigRepositry.update(siteConfig);
    }

    public int pageSize() {
        return Integer.valueOf(siteConfig().getOrDefault("pageSize","10").toString());
    }
}
