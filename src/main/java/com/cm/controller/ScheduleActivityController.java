package com.cm.controller;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.ScheduleActivity;
import com.cm.service.ScheduleActivityService;
import com.cm.service.ScheduleService;
import com.cm.util.ScheduleActivityFormValidator;

@Controller
public class ScheduleActivityController extends BaseController<ScheduleActivity>{
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private ScheduleActivityService saService;
	
	@Autowired
	@Qualifier("scheduleActivityValidator")
	private ScheduleActivityFormValidator validator;
	
	@InitBinder("item")
	private void initBinder1(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@RequestMapping(value = "ScheduleActivity/create")
	public ModelAndView createScheduleActivity (HttpServletRequest request, @RequestParam long parentId) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		request.setAttribute("parentId", parentId);
		return create(request);
	}
	
	@RequestMapping(value = "ScheduleActivity/delete")
	public ModelAndView deleteScheduleActivity (HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return delete(request);
	}
	
	@RequestMapping(value = "ScheduleActivity/save")
	public ModelAndView saveScheduleActivity (HttpServletRequest request, @ModelAttribute("item") @Validated ScheduleActivity item, BindingResult bindingResult) throws Exception{
		if (bindingResult.hasErrors()) {
			if (item.getId() == null)
				return create(request);
			else
				return edit(request);
		}
		
		long parentId = Long.parseLong(request.getParameter("parentId"));
		item.setSchedule(scheduleService.getById(parentId));
		
		saService.create(item);
		
		return new ModelAndView("redirect:/Schedule/getAll");
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
	public List<ScheduleActivity> filterAllByString(String searchColumn, String searchName, List<ScheduleActivity> result) {
		return result;
	}

	@Override
	public List<ScheduleActivity> customList(HttpServletRequest request)
			throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
