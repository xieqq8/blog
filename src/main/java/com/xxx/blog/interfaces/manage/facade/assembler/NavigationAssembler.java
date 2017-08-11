package com.xxx.blog.interfaces.manage.facade.assembler;

import com.xxx.blog.interfaces.manage.facade.command.NavigationCreateCommand;
import com.xxx.blog.interfaces.manage.facade.command.NavigationUpdateCommond;
import com.xxx.toolbox.model.NavigationNode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Created by xiexx on 2017/10/30.
 */
@Component
public class NavigationAssembler {

    public static NavigationNode updateCommendToDomain(String id, NavigationUpdateCommond updateCommond) {
        NavigationNode menu = new NavigationNode();
        BeanUtils.copyProperties(updateCommond, menu);
        menu.setId(id);
        return menu;
    }

    public static NavigationNode createCommendToDomain(NavigationCreateCommand creteCommand) {
        NavigationNode menu = new NavigationNode();
        BeanUtils.copyProperties(creteCommand, menu);
        return menu;
    }
}
