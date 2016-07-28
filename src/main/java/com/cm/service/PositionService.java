package com.cm.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.entity.Position;
import com.cm.repository.PositionRepository;

@Service
@Transactional
public class PositionService extends BaseService<Position>{
	@Autowired
	protected PositionRepository positionRepository;
}
