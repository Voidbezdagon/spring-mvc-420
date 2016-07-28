package com.cm.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.entity.Location;
import com.cm.repository.LocationRepository;

@Service
@Transactional
public class LocationService extends BaseService<Location>{
	@Autowired
	protected LocationRepository locationRepository;
}
