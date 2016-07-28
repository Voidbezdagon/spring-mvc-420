package com.cm.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.entity.Team;
import com.cm.repository.TeamRepository;

@Service
@Transactional
public class TeamService extends BaseService<Team>{

	@Autowired
	protected TeamRepository teamRepository;
}
