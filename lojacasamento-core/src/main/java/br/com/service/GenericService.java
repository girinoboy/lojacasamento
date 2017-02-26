package br.com.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;

import br.com.dao.GenericDAO;

//@ApplicationScoped
public class GenericService<T, ID extends Serializable> {
	
	public T getFavourite() {
		
		
		return null;

    }
	
	private GenericDAO<T, Serializable> dao;
	
//	public GenericService() {
//	    super();
//	}

	@SuppressWarnings("unchecked")
	 public GenericService() {
//		Type t = getClass().getGenericSuperclass();
//        Type arg;
//        if(t instanceof  ParameterizedType){
//                arg = ((ParameterizedType)t).getActualTypeArguments()[0];
//        }else if(t instanceof Class){
//                arg = ((ParameterizedType)((Class)t).getGenericSuperclass()).getActualTypeArguments()[0];
//                 
//        }else{
//                throw new RuntimeException("Can not handle type construction for '"+getClass()+"'!");
//        }
///*
//        if(arg instanceof Class){
//                this.persistentClass = (Class<T>)arg;
//        }else if(arg instanceof ParameterizedType){
//                this.persistentClass = (Class<T>)((ParameterizedType)arg).getRawType();
//        }else{
//                throw new RuntimeException("Problem dtermining generic class for '"+getClass()+"'! ");
//        }*/
//		Class<T> a = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//		 System.out.println(a);
		this.dao = null;
	}
	
	
//	private final Greeter greeter;


//    @Inject
//    public GenericService(GenericDAO dao) {
//        this.dao = dao;
//        System.out.println(dao);
//    }
//    
//    @Inject
//    public GenericService(T entity) {
//        System.out.println(entity);
//    }


    // public no-arg constructor required for EJBs
    // injection still works fine with the @Inject constructor
//    public GreeterEjb() {
//        this.dao = null;
//    }
}
