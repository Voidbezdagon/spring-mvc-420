package com.cm.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cm.entity.Schedule;

@Service
@Transactional
public class ScheduleService extends BaseService<Schedule>{

}
