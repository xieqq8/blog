package com.xxx.blog.interfaces.manage.facade.command;

/**
 * Created by xiexx on 2017/12/18.
 */
public class BookUpdateCommand {

    private String name;

    private String catalog;

    private String img;//封面图片地址

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
