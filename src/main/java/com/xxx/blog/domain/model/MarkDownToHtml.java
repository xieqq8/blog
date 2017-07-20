package com.xxx.blog.domain.model;

import com.xxx.blog.interfaces.manage.facade.command.WriteBlogCommand;

/**
 * markdown转html
 * Created by xiexx on 2017/12/4.
 */
public interface MarkDownToHtml {

    String convert(WriteBlogCommand addBlogCommand);
}
