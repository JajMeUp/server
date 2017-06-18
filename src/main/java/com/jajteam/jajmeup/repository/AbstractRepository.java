package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.exception.EntityNotFoundException;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.hibernate.HibernateQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractRepository<PK extends Serializable, T> {

    @Inject
    private SessionFactory sessionFactory;

    private final Class<T> persistentClass;

    public AbstractRepository() {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public abstract EntityPath<T> getEntityPath();

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void persist(T entity) {
        getSession().persist(entity);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    public T getByKey(PK key) throws EntityNotFoundException {
        T result = getSession().get(persistentClass, key);
        if (result == null) {
            throw new EntityNotFoundException(persistentClass.getSimpleName(), key.toString());
        }
        return result;
    }

    public List<T> findAll() {
        List<T> results = new HibernateQuery<T>(getSession())
                .from(getEntityPath())
                .fetch();
        return results;
    }
}
