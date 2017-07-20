package com.xxx.blog.interfaces.manage.metaweblog;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义请求处理器工厂以支持spring
 * @author xiexx
 * @date 2017/3/31
 */
@Component
public class SpringRequestProcessorFactoryFactory implements RequestProcessorFactoryFactory{

   @Autowired
   private MetaweblogApi metaweblogApi;

    @Override
    public RequestProcessorFactory getRequestProcessorFactory(Class pClass) throws XmlRpcException {
        return new SpringRequestProcessorFactory();
    }

    class SpringRequestProcessorFactory implements RequestProcessorFactory{

        @Override
        public Object getRequestProcessor(XmlRpcRequest pRequest) throws XmlRpcException {
           return metaweblogApi;
        }
    }
}
