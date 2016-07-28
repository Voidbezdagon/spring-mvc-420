package com.cm.repository;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.MappedSuperclass;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cm.entity.BaseEntity;

import com.cm.util.ReflectionHelper;

@MappedSuperclass
public abstract class BaseRepository<T extends BaseEntity>{
	@Autowired
	protected SessionFactory sessionFactory;

	public Serializable create(T entity) {
		return sessionFactory.getCurrentSession().save(entity);
	}

	public T update(T entity) {
		sessionFactory.getCurrentSession().update(entity);
		return entity;
	}

	public void delete(long id) throws InstantiationException, IllegalAccessException {
		T entity = getById(id);
		sessionFactory.getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() throws InstantiationException, IllegalAccessException {
		T entityClass = ReflectionHelper.CreateInstance(this.getClass(), 1);
		return sessionFactory.getCurrentSession().createQuery(" FROM "+entityClass.getClass().getName()).list();
	}

	@SuppressWarnings("unchecked")
	public T getById(long id) throws InstantiationException, IllegalAccessException {
		T entityClass = ReflectionHelper.CreateInstance(this.getClass(), 1);
		return (T)sessionFactory.getCurrentSession().get(entityClass.getClass(), id);
	}
	
	@SuppressWarnings("unchecked")
	public T getByString(String column, String searchString) throws SQLException, InstantiationException, IllegalAccessException {
		T entityClass = ReflectionHelper.CreateInstance(this.getClass(), 1);
		T result = (T) sessionFactory.getCurrentSession().createQuery("FROM " + entityClass.getClass().getName() + " e WHERE e." + column + " like :nub")
				.setString("nub", searchString)
				.uniqueResult();

		return result;
	}

	public abstract List<T> getAllByString(String column, String searchString) throws Exception;
}
