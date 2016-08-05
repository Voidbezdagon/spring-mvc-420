package com.cm.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.Schedule;
import com.cm.entity.ScheduleActivity;
import com.cm.entity.ScheduleActivityReport;
import com.cm.entity.ScheduleReport;
import com.cm.entity.Team;
import com.cm.entity.User;
import com.cm.service.ScheduleActivityService;
import com.cm.service.ScheduleReportService;
import com.cm.service.ScheduleService;
import com.cm.service.TeamService;
import com.cm.service.UserService;
import com.cm.util.TeamFormValidator;

@Controller
public class TeamController extends BaseController<Team>{

	@Autowired
	private TeamService teamService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	ScheduleActivityService scheduleActivityService;
	
	@Autowired
	ScheduleReportService scheduleReportService;
	
	@Autowired
	@Qualifier("teamValidator")
	private TeamFormValidator validator;

	@InitBinder("item")
	private void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(List.class, "users", new CustomCollectionEditor(List.class)
        {
              @Override
              protected Object convertElement(Object element)
              {
                  Long id = null;
                  if(element instanceof String && !((String)element).equals("")){
                      //From the JSP 'element' will be a String
                      try{
                          id = Long.parseLong((String) element);
                      }
                      catch (NumberFormatException e) {
                          System.out.println("Element was " + ((String) element));
                          e.printStackTrace();
                      }
                  }
                  else if(element instanceof Long) {
                      //From the database 'element' will be a Long
                      id = (Long) element;
                  }

    			  try {
					return id != null ? userService.getById(id) : null;
    			  } catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
    			  }
				return null;  
              }
        });
		binder.setValidator(validator);
	}
	
	//START BASE CONTROLLER
	
	@RequestMapping(value="Team/create")
	public ModelAndView createTeam(HttpServletRequest request) throws Exception
	{
		request.setAttribute("userList", userService.getAll());
		return create(request);
	}
	
	@RequestMapping(value="Team/edit")
	public ModelAndView editTeam(HttpServletRequest request) throws Exception
	{
		request.setAttribute("userList", userService.getAll());
		return edit(request);
	}
	
	@RequestMapping(value="Team/delete")
	public ModelAndView deleteTeam(HttpServletRequest request) throws Exception
	{
		Long id = Long.parseLong(request.getParameter("id"));
		
		List<Schedule> schedules = scheduleService.getAll();
		for(Schedule s : schedules)
		{
			for (ScheduleActivity sa : s.getActivities())
			{
				scheduleActivityService.delete(sa.getId());
			}
			for (ScheduleReport sr : s.getReports())
			{
				scheduleReportService.delete(sr.getId());
			}
			scheduleService.delete(s.getId());
		}
		
		return delete(request);
	}
	
	@RequestMapping(value="Team/save")
	public ModelAndView saveTeam(@ModelAttribute("item") @Validated Team item, BindingResult bindingResult, @RequestParam(value="file", required=false) MultipartFile file, HttpServletRequest request) throws Exception
	{	
		if (bindingResult.hasErrors()) {
			request.setAttribute("userList", userService.getAll());
			if (item.getId() == null)
				return create(request);
			else
				return edit(request);
		}
		
		
		return save(item, request);
	}
	
	@RequestMapping(value = "Team/getAll")
	public ModelAndView getAllTeams(HttpServletRequest request) throws Exception
	{
		return getAll(request);
	}
	
	@Override
	public void feedSort(List<Team> List, String column) {
		switch (column)
		{
		case "Team Name": Collections.sort(List, (p1, p2) -> p1.getTeamname().compareTo(p2.getTeamname()));
			break;
		}	
	}

	@Override
	public void feedSortLists(LinkedHashMap<String, String> Map) {
		Map.put("teamname", "Team Name");
	}
	
	@Override
	public void setAvatars(List<Team> List) {
	}
	
	@Override
	public List<Team> filterAllByString(String column, String searchName, List<Team> result) {
		switch (column)
		{
		case "Team Name":
			return (ArrayList<Team>) result.stream().filter(p -> p.getTeamname().contains(searchName)).collect(Collectors.toList());
		}
		return result;	
		
	}
	
	//END BASE CONTROLLER
	
	public LinkedHashMap<String, String> feedTeamList() throws Exception
	{
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		List<Team> list = teamService.getAll();
		for (Team item: list)
		{
			result.put(item.getId().toString(), item.getTeamname());
		}
		return null;
	}

	@Override
	public List<Team> customList(HttpServletRequest request) throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
