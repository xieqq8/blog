package com.xxx.blog.application;

import com.xxx.toolbox.model.NavigationNode;
import com.xxx.toolbox.model.TreeModel;
import com.xxx.toolbox.model.repositry.NavigationNodeRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiexx
 * @date 2016/12/26
 */
@Service
public class CatalogService {


    @Autowired
    @Qualifier("catalogJdbcRepositry")
    protected NavigationNodeRepositry navigationNodeRepositry;

    @Cacheable(value = EventHandler.CATALOG_PAGE_CACHE)
    public List<NavigationNode> listWithTree(boolean includeDisable) {
        return (List<NavigationNode>) new TreeModel(navigationNodeRepositry.list()).buildTree(includeDisable);
    }

    @Cacheable(value = EventHandler.CATALOG_PAGE_CACHE)
    public List<NavigationNode> listByOrder() {
        List<NavigationNode> list = navigationNodeRepositry.list();
        TreeModel.sortByTree(list);
        return list;
    }

    @Cacheable(value = EventHandler.CATALOG_CACHE)
    public NavigationNode getCatalog(String id){
        return navigationNodeRepositry.get(id);
    }
}
