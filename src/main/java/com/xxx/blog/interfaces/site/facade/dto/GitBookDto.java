package com.xxx.blog.interfaces.site.facade.dto;

import com.xxx.blog.domain.model.BookIndex;

import java.util.Date;
import java.util.List;

/**
 * @author xiexx
 * @date 2016/12/27
 */
public class GitBookDto {

    private String name;


    /**
     * 分类展示文本
     */
    private String catalogDisplay;

    private String img;//封面图片地址

    private String description;

    private Date publishTime;

    private List<BookIndex> indexs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalogDisplay() {
        return catalogDisplay;
    }

    public void setCatalogDisplay(String catalogDisplay) {
        this.catalogDisplay = catalogDisplay;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public List<BookIndex> getIndexs() {
        return indexs;
    }

    public void setIndexs(List<BookIndex> indexs) {
        this.indexs = indexs;
    }
}
