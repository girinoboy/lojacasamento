package br.com.factory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;



//@SuppressWarnings({ "rawtypes" })
public class HibernateUtility {

	private static SessionFactory sessionFactory;
	private static final ThreadLocal<Session> sessionThread = new ThreadLocal<Session>();
	private static final ThreadLocal<Transaction> transactionThread = new ThreadLocal<Transaction>();


	public static Session getSession() throws Exception {
		try{
			Session session = sessionThread.get();
			if ((session == null) || (!(session.isOpen()))) {
				session = sessionFactory.openSession();
				sessionThread.set(session);
			}
		}catch(Exception e){
			throw e;
		}
		return sessionThread.get();
	}

	public static void closeSession() {
		Session session = sessionThread.get();
		if ((session != null) && (session.isOpen())) {
			sessionThread.get().close();
			sessionThread.set(null);
		}
	}

	public static void beginTransaction() throws Exception {
		try{
			Transaction transaction = getSession().beginTransaction();
			transactionThread.set(transaction);
		}catch(Exception e){
			throw e;
		}
	}
	
	public static void flushSession() throws Exception {
		sessionThread.get().flush();  
    }  

	public static void commitTransaction() {
		Transaction transaction = transactionThread.get();
		if ((transaction != null) && (!(transaction.wasCommitted())) && (!(transaction.wasRolledBack()))) {
			transaction.commit();
			transactionThread.set(null);
		}
	}

	public static void rollbackTransaction() {
		Transaction transaction = transactionThread.get();
		if ((transaction != null) && (!(transaction.wasCommitted())) && (!(transaction.wasRolledBack()))) {
			transaction.rollback();
			if(!transaction.wasRolledBack())
			transaction.begin();  
			transactionThread.set(null);
		}
	}

	static {
		try {

			try {  

				Configuration configuration = new Configuration();
				//CADASTROS abaixo coloque todas classes que deseja ser modelo para criacao do banco de dados
				for(Class<?> clazz : getClasses("br.com.dto")){
					//configuration.addAnnotatedClass(NewView.class);
					configuration.addAnnotatedClass(clazz);
				}
				configuration.configure();// configures settings from hibernate.cfg.xml

				StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
				serviceRegistryBuilder.applySettings(configuration.getProperties());
				ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception ex) {  
				//logger.fatal("Something happened here!!! 8-O", ex);
				ex.printStackTrace();
				throw new ExceptionInInitializerError(ex);  
			}  


		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	public static List<Class> getClasses(String pckgname) throws ClassNotFoundException {  
		List<Class> classes = new ArrayList<Class>();  
		ClassLoader cld = Thread.currentThread().getContextClassLoader();  
		String path = '/' + pckgname.replace('.', '/');  
		URL resource = cld.getResource(path);  
		File directory = new File(resource.getFile());  
		if (directory.exists()) {  
			String[] files = directory.list();  
			for (int i = 0; i < files.length; i++) {  
				if (files[i].endsWith(".class")) {  
					classes.add(Class.forName(pckgname + '.'  
							+ files[i].substring(0, files[i].length() - 6)));  
				}  
			}  
		}   
		return classes;  
	}  

}

