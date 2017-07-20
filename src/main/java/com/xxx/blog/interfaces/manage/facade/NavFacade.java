package com.xxx.blog.interfaces.manage.facade;


import com.google.common.eventbus.EventBus;
import com.xxx.blog.application.NavService;
import com.xxx.blog.domain.model.event.NavDeleteEvent;
import com.xxx.blog.domain.model.event.NavUpdateEvent;
import com.xxx.blog.interfaces.manage.facade.assembler.NavigationAssembler;
import com.xxx.blog.interfaces.manage.facade.command.NavigationCreateCommand;
import com.xxx.blog.interfaces.manage.facade.command.NavigationUpdateCommond;
import org.bumishi.toolbox.model.NavigationNode;
import org.bumishi.toolbox.model.repositry.NavigationNodeRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiexx on 2017/12/18.
 */
@Service
public class NavFacade {

    @Autowired
    @Qualifier("menuJdbcRepositry")
    protected NavigationNodeRepositry navigationNodeRepositry;

    @Autowired
    protected NavService navService;

    @Autowired
    protected EventBus eventBus;

    public void add(NavigationCreateCommand createCommand){
        NavigationNode catalog = NavigationAssembler.createCommendToDomain(createCommand);
        navigationNodeRepositry.add(catalog);
        eventBus.post(new NavUpdateEvent(catalog));
    }

    public void update(String id,NavigationUpdateCommond updateCommond){
        NavigationNode catalog = NavigationAssembler.updateCommendToDomain(id, updateCommond);
        navigationNodeRepositry.update(catalog);
        eventBus.post(new NavUpdateEvent(catalog));
    }


    public void switchStatus(String id,boolean disable){
        if(disable){
            navigationNodeRepositry.disable(id);
        }else {
            navigationNodeRepositry.enable(id);
        }
        eventBus.post(new NavDeleteEvent(id));
    }

    public void delete(String id) {
        navigationNodeRepositry.remove(id);
        eventBus.post(new NavDeleteEvent(id));
    }

    public NavigationNode get(String id) {
        return navService.getNav(id);
    }

    public List<NavigationNode> listByOrder() {
        return navService.listByOrder();
    }
}
