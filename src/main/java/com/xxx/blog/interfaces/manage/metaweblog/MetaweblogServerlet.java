package com.xxx.blog.interfaces.manage.metaweblog;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcHandlerMapping;
import org.apache.xmlrpc.webserver.XmlRpcServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author qiang.xie
 * @date 2017/3/31
 */
@Component
public class MetaweblogServerlet extends XmlRpcServlet{

    @Autowired
    private SpringRequestProcessorFactoryFactory springRequestProcessorFactoryFactory;


    protected XmlRpcHandlerMapping newXmlRpcHandlerMapping() throws XmlRpcException {
        PropertyHandlerMapping mapping
                = new PropertyHandlerMapping();
        mapping.setRequestProcessorFactoryFactory(springRequestProcessorFactoryFactory);
        mapping.addHandler("metaWeblog", MetaweblogApi.class);
        mapping.addHandler("blogger",MetaweblogApi.class);
        return mapping;
    }
}
