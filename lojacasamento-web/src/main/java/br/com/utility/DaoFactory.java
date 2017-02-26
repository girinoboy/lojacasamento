package br.com.utility;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.logging.Logger;

import br.com.dao.GenericDAO;

public class DaoFactory{
	
    @Produces
    public <T> GenericDAO<T, Serializable> create(InjectionPoint injectionPoint){  
        ParameterizedType type = (ParameterizedType) injectionPoint.getType();  
        Class<T> classe = (Class<T>) type.getActualTypeArguments()[0];  
        return new GenericDAO(classe);  
    }  
    
//    @Produces  
//    public Logger createLogger(InjectionPoint ip) {  
//      return Logger.getLogger(ip.getMember().getDeclaringClass().getName()); // or sth similar, depends on your implementation  
//    }  
//    
//    @Produces
//    public org.slf4j.Logger getLogger(final InjectionPoint ip)
//    {
//        return org.slf4j.LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
//    }
}