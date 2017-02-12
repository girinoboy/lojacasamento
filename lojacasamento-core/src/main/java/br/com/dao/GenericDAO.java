package br.com.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import br.com.factory.HibernateUtility;

@SuppressWarnings({ "unchecked", "rawtypes"})
public class GenericDAO<T, ID extends Serializable> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Class<T> oClass;
	public Session session ;
	public Criteria criteria;

    public GenericDAO() {
        this.oClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
			session = HibernateUtility.getSession();
			criteria = session.createCriteria(oClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public GenericDAO(Class<T> oClass) {
    	 this.oClass = oClass;
    	 try {
 			session = HibernateUtility.getSession();
 			criteria = session.createCriteria(oClass);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
	}

	public Class<T> getObjectClass() {
        return this.oClass;
    }

    public T save(T objeto) throws Exception {
        try {
            Object obj = null;
            HibernateUtility.beginTransaction();
            obj = HibernateUtility.getSession().merge(objeto);
            HibernateUtility.commitTransaction();
            return (T) obj;
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }finally{
        	HibernateUtility.closeSession();
        }
    }

    public void delete(T objeto) throws Exception {
        try {
            HibernateUtility.beginTransaction();
            HibernateUtility.getSession().delete(objeto);
            HibernateUtility.commitTransaction();
            HibernateUtility.closeSession();
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }
    }

    public void deleteItem(T objeto) throws Exception {
        try {
            HibernateUtility.beginTransaction();
            HibernateUtility.getSession().delete(objeto);
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }finally{
        	HibernateUtility.closeSession();
        }
    }

    public List<T> list() throws Exception {
        try {
            List<T> list = HibernateUtility.getSession().createCriteria(oClass)
            		//.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
            		.list();
            HibernateUtility.closeSession();
            return (List<T>) list;
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }finally{
        	HibernateUtility.closeSession();
        }
    }
    
    public List<T> list(T entity) throws Exception {
        try {
            List<T> list = HibernateUtility.getSession().createCriteria(entity.getClass())
            		.add(Example.create( entity ).enableLike().excludeZeroes())
            		//.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
            		.list();
            HibernateUtility.closeSession();
            return (List<T>) list;
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }finally{
        	HibernateUtility.closeSession();
        }
    }
    
    public T getById(Serializable id) throws Exception {
        try {
            return (T) HibernateUtility.getSession().get(oClass, id);
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }finally{
        	HibernateUtility.closeSession();
        }
    }

    public T getById(Serializable id, boolean lock) throws Exception {
        try {
            if (lock) {
				return (T) HibernateUtility.getSession().get(oClass, id, LockOptions.UPGRADE);
            } else {
                return (T) HibernateUtility.getSession().get(oClass, id);
            }
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }finally{
        	HibernateUtility.closeSession();
        }
    }
    
    public List<T> listById(Serializable id) throws Exception {
        try {
			return (List<T>) HibernateUtility.getSession().createCriteria(oClass).add(Restrictions.idEq(id)).list();
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }finally{
        	HibernateUtility.closeSession();
        }
    }

    public List<T> listCriterio(String subClazz, Map<String, Object> filtrosConsulta, int tipoConsulta) throws Exception {
        List<T> lista = new ArrayList<T>();
        Set entradas = filtrosConsulta.entrySet();

        try {
            Criteria crit = HibernateUtility.getSession().createCriteria(oClass);
            if (subClazz == null) {
                for (Iterator it = entradas.iterator(); it.hasNext();) {
                    Entry object = (Entry) it.next();
                    if (object.getValue() instanceof Enum) {
                        crit.add(Restrictions.eq(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 0) {
                        crit.add(Restrictions.ilike(object.getKey().toString(), "%" + object.getValue() + "%"));
                    } else if (tipoConsulta == 1) {
                        crit.add(Restrictions.eq(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 2) {
                    	crit.add(Restrictions.ne(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 3) {
                        crit.add(Restrictions.gt(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 4) {
                        crit.add(Restrictions.ge(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 5) {
                        crit.add(Restrictions.lt(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 6) {
                        crit.add(Restrictions.le(object.getKey().toString(), object.getValue()));
                    }
                }
            } else {
                for (Iterator it = entradas.iterator(); it.hasNext();) {
                    Entry object = (Entry) it.next();
                    //crit.createCriteria(subClazz).add(Restrictions.ilike(object.getKey().toString(), "%" + object.getValue() + "%"));
                    if (object.getValue() instanceof Enum) {
                    } else if (tipoConsulta == 0) {
                        crit.createCriteria(subClazz).add(Restrictions.ilike(object.getKey().toString(), "%" + object.getValue() + "%"));
                    } else if (tipoConsulta == 1) {
                        crit.createCriteria(subClazz).add(Restrictions.eq(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 2) {
                    	crit.createCriteria(subClazz).add(Restrictions.ne(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 3) {
                        crit.createCriteria(subClazz).add(Restrictions.gt(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 4) {
                        crit.createCriteria(subClazz).add(Restrictions.ge(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 5) {
                        crit.createCriteria(subClazz).add(Restrictions.lt(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 6) {
                        crit.createCriteria(subClazz).add(Restrictions.le(object.getKey().toString(), object.getValue()));
                    }
                }
            }
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            crit.setMaxResults(20);
            lista = (List<T>) crit.list();
            //HibernateUtility.closeSession();
            return lista;
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }finally{
        	HibernateUtility.closeSession();
        }
    }

    public List<T> consultaHQL(String consulta) throws HibernateException, Exception {
        return (List<T>) HibernateUtility.getSession().createQuery(consulta).list();
    }

    public void cancel() {
        HibernateUtility.rollbackTransaction();
        HibernateUtility.closeSession();        
    }
}

