package com.cm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.Schedule;
import com.cm.entity.ScheduleReport;
import com.cm.service.ScheduleService;

@Controller
public class ScheduleReportController extends BaseController<ScheduleReport>{

	@Autowired
	ScheduleService scheduleService;
	
	/*@Autowired
	@Qualifier("scheduleValidator")
	private ScheduleFormValidator validator;*/
	
	@InitBinder("item")
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        //binder.setValidator(validator);
	}
	
	@RequestMapping(value="ScheduleReport/create")
	public ModelAndView createScheduleReport(HttpServletRequest request) throws Exception
	{
		return create(request);
	}
	
	@RequestMapping(value="ScheduleReport/save")
	public ModelAndView saveScheduleReport(@ModelAttribute("item") ScheduleReport item, BindingResult bindingResult, HttpServletRequest request) throws Exception
	{	
		/*if (bindingResult.hasErrors()) {
			if (item.getId() == null)
				return create(request);
			else
				return edit(request);
		}*/
		
		/*List<User> noob = new ArrayList<User>();
		for (User user : item.getUsers())
		{
			if (user.getId() != null)
				noob.add(userService.getById(user.getId()));
		}
		
		item.setUsers(noob);*/
		return save(item, request);
	}
	
	@Override
	public void feedSort(List<ScheduleReport> List, String column) {
		
	}

	@Override
	public void feedSortLists(LinkedHashMap<String, String> Map) {
		
	}

	@Override
	public void setAvatars(List<ScheduleReport> List) throws Exception {
		
	}

	@Override
	public List<ScheduleReport> customList(HttpServletRequest request)
			throws InstantiationException, IllegalAccessException {
		
		return null;
	}

	@Override
	public List<ScheduleReport> filterAllByString(String searchColumn, String searchName, List<ScheduleReport> result) {
		return result;
		
	}

}
