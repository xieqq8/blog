package com.xxx.blog.infrastructure.persistence.es;

import com.alibaba.fastjson.JSON;
import com.xxx.blog.domain.repository.BlogCommandRepositry;
import com.xxx.blog.domain.repository.BlogQueryRepositry;
import com.xxx.toolbox.model.PageModel;
import org.apache.commons.lang3.StringUtils;
import com.xxx.blog.domain.model.Blog;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiexx on 2017/1/9.
 */
@Repository("blogElasticSearchRepositry")
public class BlogElasticSearchRepositry implements BlogCommandRepositry, BlogQueryRepositry {

    @Autowired
    private TransportClient client;

    @Override
    public void save(Blog blog) {
        client.prepareIndex("blog", "blog").setId(blog.getId())
                .setSource(JSON.toJSONString(blog))
                .get();
    }

    @Override
    public void update(Blog blog) {
        client.prepareUpdate("blog", "blog", blog.getId()).setDoc(JSON.toJSONString(blog))
                .get();
    }

    @Override
    public void remove(String id) {
        client.prepareDelete("blog", "blog", id).get();
    }

    @Override
    public Blog get(String id) {
        return JSON.parseObject(client.prepareGet("blog", "blog", id).get().getSourceAsString(), Blog.class);
    }

    @Override
    public PageModel<Blog> queryByKeyword(int page, int size, String keyword) {
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        if (StringUtils.isNotBlank(keyword)) {
            queryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "secondTitle", "md");
        }
        SearchResponse searchResponse = client.prepareSearch("blog").setTypes("blog").setFrom((page - 1) * size).setSize(size).addSort("publishTime", SortOrder.DESC).setQuery(queryBuilder).highlighter(new HighlightBuilder().field("title").field("md").preTags("<span class='kw'>").postTags("</span>").fragmentSize(50)).get();
        return getBlogPageModel(page, size, searchResponse);
    }


    @Override
    public PageModel<Blog> queryByCatalog(int page, int size, String catalog) {
        SearchResponse searchResponse = client.prepareSearch("blog").setTypes("blog").setFrom((page - 1) * size).setSize(size).setQuery("all".equals(catalog) ? QueryBuilders.matchAllQuery() : QueryBuilders.matchQuery("catalog", catalog)).addSort("publishTime", SortOrder.DESC).get();
        return getBlogPageModel(page, size, searchResponse);
    }

    private PageModel<Blog> getBlogPageModel(int page, int size, SearchResponse searchResponse) {
        SearchHit[] hits = searchResponse.getHits().getHits();
        if (hits == null || hits.length == 0) {
            return new PageModel<>();
        }
        PageModel<Blog> pageModel = new PageModel<>();
        pageModel.setSize(size);
        pageModel.setPage(page);
        pageModel.setHasNext(hits.length >= size);
        List<Blog> blogs = new ArrayList<>();
        for (SearchHit searchHit : hits) {
            Blog blog=JSON.parseObject(searchHit.getSourceAsString(), Blog.class);
            processHighlight(blog,searchHit);
            blogs.add(blog);
        }
        pageModel.setList(blogs);
        return pageModel;
    }

    private void processHighlight(Blog blog,SearchHit searchHit){
        if(!CollectionUtils.isEmpty(searchHit.getHighlightFields())){
            if(searchHit.getHighlightFields().containsKey("title")) {
                blog.setTitle(getText(searchHit.getHighlightFields().get("title").getFragments()));
            }
            if(searchHit.getHighlightFields().containsKey("md")) {
                blog.setDisplay(getText(searchHit.getHighlightFields().get("md").getFragments()));
            }else{
                blog.setDisplay(null);//todo 暂未找到这样过滤返回字段的方法,暂时这样处理
            }
        }
    }

    private String getText(Text[] texts){
        if(texts==null){
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<texts.length;i++){
            stringBuilder.append(texts[i].string());
            if(i<texts.length-1) {
                stringBuilder.append("...");
            }
        }
        return stringBuilder.toString();
    }
    @Override
    public PageModel<Blog> queryByTime(int page, int size) {
        return queryByKeyword(page, size, "");
    }

    @Override
    public int getCount() {
        return Long.valueOf(client.prepareSearch("blog").setTypes("blog").setQuery(QueryBuilders.matchAllQuery()).get().getHits().getTotalHits()).intValue();
    }

    @PreDestroy
    private void closeClient() {
        client.close();
    }
}
