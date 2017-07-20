package com.xxx.blog.interfaces.site.facade.dto;

import java.util.Date;

/**
 * 文章列表页对象
 * Created by xiexx on 2017/1/11.
 */
public class SimpleBlog {

    /**
     * 标题
     */
    private String title;

    /**
     * 文章概要
     */
    private String summary;

    /**
     * 文章链接
     */
    private String link;

    /**
     * 分类展示文本
     */
    private String catalogDisplay;

    /**
     * 分类id
     */
    private String catalog;


    /**
     * 发布时间
     */
    private Date publishTime;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCatalogDisplay() {
        return catalogDisplay;
    }

    public void setCatalogDisplay(String catalogDisplay) {
        this.catalogDisplay = catalogDisplay;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}
