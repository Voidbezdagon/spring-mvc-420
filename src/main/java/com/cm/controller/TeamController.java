package com.cm.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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

import com.cm.entity.Team;
import com.cm.entity.User;
import com.cm.service.TeamService;
import com.cm.service.UserService;
import com.cm.util.TeamFormValidator;

@Controller
public class TeamController extends BaseController<Team>{

	@Autowired
	private TeamService teamService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	@Qualifier("teamValidator")
	private TeamFormValidator validator;

	@InitBinder("item")
	private void initBinder(WebDataBinder binder) {
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
		
		List<User> noob = new ArrayList<User>();
		for (User user : item.getUsers())
		{
			if (user.getId() != null)
				noob.add(userService.getById(user.getId()));
		}
		
		item.setUsers(noob);
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
}
