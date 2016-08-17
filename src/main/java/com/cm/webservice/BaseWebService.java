package com.cm.webservice;

import java.util.List;

import javax.persistence.MappedSuperclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.cm.entity.BaseEntity;
import com.cm.service.BaseService;

@MappedSuperclass
public class BaseWebService<T extends BaseEntity> {
	@Autowired
	BaseService<T> baseService;
    
    public ResponseEntity<T> getById(Long id) throws InstantiationException, IllegalAccessException {
        return new ResponseEntity<T>(baseService.getById(id), HttpStatus.OK);
    }
    
    public ResponseEntity<T> save(T item) {
    	if (item.getId() == null)
    		baseService.create(item);
    	else
    		baseService.update(item);
 
        return new ResponseEntity<T>(item, HttpStatus.OK);
    }
    
    public ResponseEntity<T> delete(Long id) throws InstantiationException, IllegalAccessException {
    	baseService.delete(id);
        return new ResponseEntity<T>(HttpStatus.NO_CONTENT);
    }
	
	public ResponseEntity<List<T>> getAll() throws InstantiationException, IllegalAccessException
	{
		return new ResponseEntity<List<T>>(baseService.getAll(), HttpStatus.OK);
	}
}
