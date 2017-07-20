package com.xxx.blog.interfaces.site;

import com.alibaba.fastjson.JSON;
import com.xxx.blog.domain.model.Blog;
import com.xxx.blog.infrastructure.persistence.es.BlogElasticSearchRepositry;
import com.xxx.blog.infrastructure.persistence.jdbc.BlogQueryJdbcRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author xiexx
 * @date 2017/1/11
 */
//@RestController
//@RequestMapping("/sync")
public class Tmep {

    @Autowired
    protected BlogElasticSearchRepositry blogElasticSearchRepositry;

    @Autowired
    protected BlogQueryJdbcRepositry blogQueryJdbcRepositry;

    @GetMapping
    public void save() {
        List<Blog> blogs=blogQueryJdbcRepositry.queryByTime(1,100).getList();
        for(Blog blog : blogs){
            blogElasticSearchRepositry.save(blog);
        }

        System.out.println(JSON.toJSONString(blogElasticSearchRepositry.queryByKeyword(1, 1,"管理")));

    }
}
