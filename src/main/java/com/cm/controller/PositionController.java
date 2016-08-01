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
import com.cm.service.PositionService;
import com.cm.util.PositionFormValidator;

@Controller
public class PositionController extends BaseController<Position>{

	@Autowired
	private PositionService positionService;
	
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
		
		
		
		return save(item, request);
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
		case "Superior Position": Collections.sort(List, (p1, p2) -> p1.getParentId().compareTo(p2.getParentId()));
			break;
		case "Name": Collections.sort(List, (p1, p2) -> p1.getName().compareTo(p2.getName()));
			break;
		}
		
	}

	@Override
	public void feedSortLists(LinkedHashMap<String, String> Map) {
		Map.put("parentId", "Superior Position");
		Map.put("name", "Name");
	}
	
	@Override
	public void setAvatars(List<Position> List) {
	}
	
	@Override
	public void filterAllByString(String column, String searchName, List<Position> result) {
		switch (column)
		{
		case "Superior Position":
			result = (ArrayList<Position>) result.stream().filter(p -> p.getParentId().equals(Long.parseLong(searchName))).collect(Collectors.toList());
			break;
		case "Name":
			result = (ArrayList<Position>) result.stream().filter(p -> p.getName().contains(searchName)).collect(Collectors.toList());
			break;
		}	
		
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

	

	
}
