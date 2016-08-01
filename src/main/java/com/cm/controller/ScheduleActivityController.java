package com.cm.controller;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.ScheduleActivity;
import com.cm.service.ScheduleActivityService;

@Controller
public class ScheduleActivityController extends BaseController<ScheduleActivity>{
	
	@Autowired
	private ScheduleActivityService sas;
	
	@RequestMapping(value = "ScheduleActivity/create")
	public ModelAndView createScheduleActivity (HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return create(request);
	}
	
	@RequestMapping(value = "ScheduleActivity/delete")
	public ModelAndView deleteScheduleActivity (HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return delete(request);
	}
	

	@Override
	public void feedSort(List<ScheduleActivity> List, String column) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void feedSortLists(LinkedHashMap<String, String> Map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAvatars(List<ScheduleActivity> List) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void filterAllByString(String searchColumn, String searchName, List<ScheduleActivity> result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ScheduleActivity> customList(HttpServletRequest request)
			throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
