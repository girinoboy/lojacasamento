package br.com.factory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import br.com.dto.LojaDTO;
import br.com.dto.ProdutoDTO;



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
//				System.out.println(getEntityClassesFromPackage("br.com.dto"));
				Configuration configuration = new Configuration();
				configuration.addPackage("br.com.dto");
				configuration.addAnnotatedClass(LojaDTO.class);
				configuration.addAnnotatedClass(ProdutoDTO.class);
				//CADASTROS abaixo coloque todas classes que deseja ser modelo para criacao do banco de dados
				for(Class<?> clazz : getClasses("br.com.dto")){
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
	
	public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException {
	    try {
	        Configuration configuration = new Configuration().configure();
	        configuration.addPackage("br.com.dto");
	        for (Class cls : getEntityClassesFromPackage("br.com.dto")) {
	            configuration.addAnnotatedClass(cls);
	        }
	        sessionFactory = configuration.buildSessionFactory();
	    } catch (Throwable ex) {
	        System.err.println("Failed to create sessionFactory object." + ex);
	        throw new ExceptionInInitializerError(ex);
	    }
	}

	public static List<Class<?>> getEntityClassesFromPackage(String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
	    List<String> classNames = getClassNamesFromPackage(packageName);
	    List<Class<?>> classes = new ArrayList<Class<?>>();
	    for (String className : classNames) {
	        Class<?> cls = Class.forName(packageName + "." + className);
	        Annotation[] annotations = cls.getAnnotations();

	        for (Annotation annotation : annotations) {
	            System.out.println(cls.getCanonicalName() + ": " + annotation.toString());
	            if (annotation instanceof javax.persistence.Entity) {
	                classes.add(cls);
	            }
	        }
	    }

	    return classes;
	}

	public static ArrayList<String> getClassNamesFromPackage(String packageName) throws IOException, URISyntaxException, ClassNotFoundException {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    ArrayList<String> names = new ArrayList<String>();

	    packageName = packageName.replace(".", "/");
	    URL packageURL = classLoader.getResource(packageName);

	    URI uri = new URI(packageURL.toString());
	    File folder = new File(uri.getPath());
	    File[] files = folder.listFiles();
	    for (File file: files) {
	        String name = file.getName();
	        name = name.substring(0, name.lastIndexOf('.'));  // remove ".class"
	        names.add(name);
	    }

	    return names;
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

