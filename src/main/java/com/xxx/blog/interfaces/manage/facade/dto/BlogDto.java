package com.xxx.blog.interfaces.manage.facade.dto;

import java.util.Date;

/**
 * Created by xiexx on 2017/12/18.
 */
public class BlogDto {

    /***/
    private String id;

    /**标题*/
    private String title;

    /**副标题*/
    private String secondTitle;

    /**分类标识*/
    private String catalog;

    /**分类展示文本*/
    private String catalogDisplay;

    /**markdownd原内容*/
    private String md;

    /**发布时间*/
    private Date publishTime;

    /**封面图片，主要迎合微信公众号*/
    private String img;

    /**阅读量*/
    private long views;

    private String wechatLink;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getCatalogDisplay() {
        return catalogDisplay;
    }

    public void setCatalogDisplay(String catalogDisplay) {
        this.catalogDisplay = catalogDisplay;
    }


    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }


    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getWechatLink() {
        return wechatLink;
    }

    public void setWechatLink(String wechatLink) {
        this.wechatLink = wechatLink;
    }
}
