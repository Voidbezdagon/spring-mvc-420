package com.cm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.Location;
import com.cm.entity.Schedule;
import com.cm.entity.ScheduleActivity;
import com.cm.entity.ScheduleActivityReport;
import com.cm.entity.ScheduleReport;
import com.cm.entity.Team;
import com.cm.entity.User;
import com.cm.service.LocationService;
import com.cm.service.ScheduleActivityReportService;
import com.cm.service.ScheduleActivityService;
import com.cm.service.ScheduleReportService;
import com.cm.service.ScheduleService;
import com.cm.service.TeamService;
import com.cm.service.UserService;
import com.cm.util.ScheduleFormValidator;
import com.cm.util.UserFormValidator;

@Controller
public class ScheduleController extends BaseController<Schedule>{
	
	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	ScheduleActivityService scheduleActivityService;
	
	@Autowired
	ScheduleReportService scheduleReportService;
	
	@Autowired
	ScheduleActivityReportService sarService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LocationService locationService;
	
	@Autowired
	@Qualifier("scheduleValidator")
	private ScheduleFormValidator validator;
	
	@InitBinder("item")
    private void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        binder.setValidator(validator);
	}
	
	@RequestMapping(value="Schedule/create")
	public ModelAndView createSchedule(HttpServletRequest request) throws Exception
	{
		List<Team> teamList = teamService.getAll();
		request.setAttribute("teamList", teamList);
		List<Location> locationList = locationService.getAll();
		request.setAttribute("locationList", locationList);
		return create(request);
	}
	
	@RequestMapping(value="Schedule/edit")
	public ModelAndView editSchedule(HttpServletRequest request) throws Exception
	{
		List<Team> teamList = teamService.getAll();
		request.setAttribute("teamList", teamList);
		List<Location> locationList = locationService.getAll();
		request.setAttribute("locationList", locationList);
		return edit(request);
	}
	
	@RequestMapping(value="Schedule/delete")
	public ModelAndView deleteSchedule(HttpServletRequest request) throws Exception
	{
		Long id = Long.parseLong(request.getParameter("id"));
		
		Schedule schedule = scheduleService.getById(id);
		
		for (ScheduleActivity sa : schedule.getActivities())
		{
			for (ScheduleActivityReport sar : sa.getScheduleActivityReports())
			{
				sar.setScheduleActivity(null);
				sar.setScheduleReport(null);
				sarService.delete(sar.getId());
			}
			scheduleActivityService.delete(sa.getId());
		}
			
		schedule = scheduleService.getById(id);
		
		for (ScheduleReport sr : schedule.getReports())
		{
			for (ScheduleActivityReport sar : sr.getActivityReports())
			{
				sar.setScheduleActivity(null);
				sar.setScheduleReport(null);
				sarService.delete(sar.getId());
			}
			scheduleReportService.delete(sr.getId());
		}
			
		
		schedule.setLocation(null);
		scheduleService.update(schedule);
		
		return delete(request);
	}
	
	@RequestMapping(value="Schedule/save")
	public ModelAndView saveSchedule(@ModelAttribute("item") @Validated Schedule item, BindingResult bindingResult, HttpServletRequest request) throws Exception
	{	
		if (bindingResult.hasErrors()) {
			List<Team> teamList = teamService.getAll();
			request.setAttribute("teamList", teamList);
			List<Location> locationList = locationService.getAll();
			request.setAttribute("locationList", locationList);
			if (item.getId() == null)
				return create(request);
			else
				return edit(request);
		}
		
		try {
			return save(item, request);
		} catch (Exception e) {
			List<Team> teamList = teamService.getAll();
			request.setAttribute("teamList", teamList);
			request.setAttribute("duplicateTitle", "A Schedule with this Title already exists");
			if (item.getId() == null)
				return create(request);
			else
				return edit(request);	
		}
	}
	
	@RequestMapping(value = "Schedule/getAll")
	public ModelAndView getAllSchedules(HttpServletRequest request) throws Exception
	{
		User user = (User) request.getSession().getAttribute("LOGGED_USER");
		request.setAttribute("logged_user", user);
		return getAll(request);
	}

	@Override
	public void feedSort(List<Schedule> List, String column) {
		switch (column)
		{
		case "Title": Collections.sort(List, (p1, p2) -> p1.getTitle().compareTo(p2.getTitle()));
			break;
		case "Description": Collections.sort(List, (p1, p2) -> p1.getDescription().compareTo(p2.getDescription()));
			break;
		case "Start Date": Collections.sort(List, (p1, p2) -> p1.getStartDate().compareTo(p2.getStartDate()));
			break;
		case "End Date": Collections.sort(List, (p1, p2) -> p1.getEndDate().compareTo(p2.getEndDate()));
			break;
		case "Recurring Time": Collections.sort(List, (p1, p2) -> p1.getRecurringTime().compareTo(p2.getRecurringTime()));
			break;
		case "Assigned Team": Collections.sort(List, (p1, p2) -> p1.getAssignedTeam().getTeamname().compareTo(p2.getAssignedTeam().getTeamname()));
			break;
		}
	}

	@Override
	public void feedSortLists(LinkedHashMap<String, String> Map) {
		Map.put("title", "Title");
		Map.put("description", "Description");
		Map.put("startDate", "Start Date");
		Map.put("endDate", "End Date");
		Map.put("recurringTime", "Recurring Time");
		Map.put("assignedTeam", "Assigned Team");
	}

	@Override
	public void setAvatars(List<Schedule> List) throws Exception {
		
	}

	@Override
	public List<Schedule> customList(HttpServletRequest request) throws InstantiationException, IllegalAccessException {
		User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
		User user = userService.getById(loggedUser.getId());

		if (user.getAdmin() != true)
		{
			List<Team> teams = user.getTeams();
			List<Schedule> result = new ArrayList<Schedule>();
			
			for (Team team : teams)
			{
				for (Schedule schedule : team.getSchedules())
				{
					if(!result.contains(schedule))
						result.add(schedule);
				}
			}
			return result;
		}
		return null;
	}

	@Override	
	public List<Schedule> filterAllByString(String searchColumn, String searchName, List<Schedule> result) {
		
		switch (searchColumn)
		{
		case "Title": return (ArrayList<Schedule>) result.stream().filter(p -> p.getTitle().contains(searchName)).collect(Collectors.toList());
		case "Description": return (ArrayList<Schedule>) result.stream().filter(p -> p.getDescription().contains(searchName)).collect(Collectors.toList());
		case "Start Date": return (ArrayList<Schedule>) result.stream().filter(p -> p.getStartDate().toString().contains(searchName)).collect(Collectors.toList());
		case "End Date": return (ArrayList<Schedule>) result.stream().filter(p -> p.getEndDate().toString().contains(searchName)).collect(Collectors.toList());
		case "Recurring Time": return (ArrayList<Schedule>) result.stream().filter(p -> p.getRecurringTime().toString().contains(searchName)).collect(Collectors.toList());
		case "Assigned Team": return (ArrayList<Schedule>) result.stream().filter(p -> p.getAssignedTeam().getTeamname().contains(searchName)).collect(Collectors.toList());
		}
		return result;
		
	}

}
