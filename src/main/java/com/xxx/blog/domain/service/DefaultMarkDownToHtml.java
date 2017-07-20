package com.xxx.blog.domain.service;

import com.xxx.blog.domain.model.MarkDownToHtml;
import com.xxx.blog.interfaces.manage.facade.command.WriteBlogCommand;
import org.springframework.stereotype.Component;

/**
 * Created by xiexx on 2017/12/4.
 */
@Component
public class DefaultMarkDownToHtml implements MarkDownToHtml {

    @Override
    public String convert(WriteBlogCommand markdown) {
        return markdown.getDisplay();
    }
}
