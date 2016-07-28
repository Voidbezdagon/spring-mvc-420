package com.cm.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.entity.LocationItem;
import com.cm.repository.LocationItemRepository;

@Service
@Transactional
public class LocationItemService extends BaseService<LocationItem>{
	@Autowired
	protected LocationItemRepository locationItemRepository;
}
