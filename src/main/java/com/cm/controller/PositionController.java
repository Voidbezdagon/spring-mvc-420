package com.cm.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.Position;
import com.cm.entity.User;
import com.cm.service.PositionService;
import com.cm.service.UserService;
import com.cm.util.PositionFormValidator;

@Controller
public class PositionController extends BaseController<Position>{

	@Autowired
	private PositionService positionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	@Qualifier("positionValidator")
	private PositionFormValidator validator;

	@InitBinder("item")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	//START BASE CONTROLLER
	@RequestMapping(value="Position/create")
	public ModelAndView createPosition(HttpServletRequest request) throws Exception
	{
		request.setAttribute("positionList", positionService.getAll());
		return create(request);
	}
	
	@RequestMapping(value="Position/edit")
	public ModelAndView editPosition(HttpServletRequest request) throws Exception
	{
		request.setAttribute("positionList", positionService.getAll());
		return edit(request);
	}
	
	@RequestMapping(value="Position/delete")
	public ModelAndView deletePosition(HttpServletRequest request) throws Exception
	{
		Long id = Long.parseLong(request.getParameter("id"));
		List<Position> positions = positionService.getAll();
		for (Position i : positions)
		{
			if (i.getParentId().equals(id))
			{
				i.setParentId((long) 0);
				positionService.update(i);
			}
		}		

		Position pos = positionService.getById(id);
		for (User u : pos.getUsers())
		{
			u.setPosition(null);
			userService.update(u);
		}
		
		return delete(request);
	}
	
	@RequestMapping(value="Position/save")
	public ModelAndView savePosition(@ModelAttribute("item") @Validated Position item, BindingResult bindingResult, @RequestParam(value="file", required=false) MultipartFile file, HttpServletRequest request) throws Exception
	{	
		if (bindingResult.hasErrors()) {
			request.setAttribute("positionList", positionService.getAll());
			if (item.getId() == null)
				return create(request);
			else
				return edit(request);
		}
		
		if (item.getParentId() == 0)
			item.setLevel((long) 0);
		else
			item.setLevel(positionService.getById(item.getParentId()).getLevel() + 1);
		
		
		try {
			return save(item, request);
		} catch (Exception e) {
			request.setAttribute("positionList", positionService.getAll());
			request.setAttribute("duplicateName", "A Position with this Name already exists");
			if (item.getId() == null)
				return create(request);
			else
				return edit(request);	
		}
	}
	
	@RequestMapping(value = "Position/getAll")
	public ModelAndView getAllPositions(HttpServletRequest request) throws Exception
	{
		return getAll(request);
	}
	
	@Override
	public void feedSort(List<Position> List, String column) {
		switch (column)
		{
		case "Name": Collections.sort(List, (p1, p2) -> p1.getName().compareTo(p2.getName()));
			break;
		case "Level": Collections.sort(List, (p1, p2) -> p1.getLevel().compareTo(p2.getLevel()));
			break;
		}
		
	}

	@Override
	public void feedSortLists(LinkedHashMap<String, String> Map) {
		Map.put("name", "Name");
		Map.put("level", "Level");
	}
	
	@Override
	public void setAvatars(List<Position> List) {
	}
	
	@Override
	public List<Position> filterAllByString(String column, String searchName, List<Position> result) {
		switch (column)
		{
		case "Name":
			return (ArrayList<Position>) result.stream().filter(p -> p.getName().contains(searchName)).collect(Collectors.toList());
		case "Level":
			return (ArrayList<Position>) result.stream().filter(p -> p.getLevel().toString().contains(searchName)).collect(Collectors.toList());
		}
		return result;	
	}
	
	//END BASE CONTROLLER
	
	public LinkedHashMap<String, String> feedPositionList() throws Exception
	{
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		List<Position> list = positionService.getAll();
		for (Position item: list)
		{
			result.put(item.getParentId().toString(), item.getName());
		}
		return null;
	}

	@Override
	public List<Position> customList(HttpServletRequest request) throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getHighestPosition(List<Position> positions) throws InstantiationException, IllegalAccessException
	{
		Collections.sort(positions, (p1, p2) -> p1.getLevel().compareTo(p2.getLevel()));
		
		System.out.println(positions.get(0).getLevel() + "--------------------------------------------------------------------------------------------");
		return positions.get(0).getLevel();
	}

	
}
