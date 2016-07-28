package com.cm.service;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.MappedSuperclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cm.entity.BaseEntity;
import com.cm.repository.BaseRepository;


@MappedSuperclass
@Transactional
public class BaseService<T extends BaseEntity>{
	
	@Autowired
	protected BaseRepository<T> baseRepository;
	
	public long create(T item) {
		return (long) baseRepository.create(item);
	}

	public T update(T item) {
		return (T) baseRepository.update(item);
	}

	public void delete(long id) throws InstantiationException, IllegalAccessException {
		baseRepository.delete(id);
	}

	public List<T> getAll() throws InstantiationException, IllegalAccessException {
		return baseRepository.getAll();
	}

	public T getById(long l) throws InstantiationException, IllegalAccessException {
		return (T) baseRepository.getById(l);
	}

	public T getByString(String column, String searchString) throws InstantiationException, IllegalAccessException, SQLException {
		return baseRepository.getByString(column, searchString);
	}
	
	public List<T> getAllByString(String column, String searchString) throws Exception {
		return baseRepository.getAllByString(column, searchString);
	}
}
