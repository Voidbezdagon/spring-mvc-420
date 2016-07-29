package com.cm.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.BaseEntity;
import com.cm.service.BaseService;
import com.cm.util.ReflectionHelper;

@MappedSuperclass
public abstract class BaseController<T extends BaseEntity>{
	
	@Autowired
	private BaseService<T> baseService;
	
	public ModelAndView create(HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		T item = ReflectionHelper.CreateInstance(this.getClass(), 1);
		request.setAttribute("item", item);
		ModelAndView mav = new ModelAndView(item.getClass().getSimpleName() + "Form");
		return mav;
	}
	
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		T item = ReflectionHelper.CreateInstance(this.getClass(), 1);
		request.setAttribute("item", item.getClass().cast(baseService.getById(Long.parseLong(request.getParameter("id")))));
		ModelAndView mav = new ModelAndView(item.getClass().getSimpleName() + "Form");
		return mav;
	}
	
	public ModelAndView delete(HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		T item = ReflectionHelper.CreateInstance(this.getClass(), 1);
		baseService.delete(Long.parseLong(request.getParameter("id")));
		ModelAndView mav = new ModelAndView("redirect:getAll");
		return mav;
	}
	
	public ModelAndView save(@ModelAttribute T item, HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		if (item.getId() == null)
			baseService.create(item);
		else
			baseService.update(item);
		
		ModelAndView mav = new ModelAndView("redirect:getAll");
		return mav;
	}
	
	public ModelAndView getAll(HttpServletRequest request) throws Exception
	{
		T item = ReflectionHelper.CreateInstance(this.getClass(), 1);
		List<T> ItemList = new ArrayList<T>();
		String searchName = request.getParameter("searchName" + item.getClass().getSimpleName());
		String searchColumn = request.getParameter("searchColumn" + item.getClass().getSimpleName());
		String sortColumn = request.getParameter("sortColumn" + item.getClass().getSimpleName());
		String sortOrder = request.getParameter("sortOrder" + item.getClass().getSimpleName());
		String pageString = request.getParameter("page" + item.getClass().getSimpleName());
		String itemsPerPage = request.getParameter("itemsPerPage" + item.getClass().getSimpleName());
		LinkedHashMap<String, String> columnNames = new LinkedHashMap<String, String>();
		feedSortLists(columnNames);
		Integer page = null;
		Integer items = 3;
		if (pageString != null)
		{
			page = Integer.parseInt(pageString);
		}
		if (itemsPerPage != null)
			items = Integer.parseInt(itemsPerPage);
		
		
		if(request.getParameter("parentId")!=null){
			ItemList = customList(Long.parseLong(request.getParameter("parentid")));
		} else {
			ItemList = (List<T>) baseService.getAll();
		}
		if(searchName!=null){
			filterAllByString(searchColumn ,searchName, ItemList);
		}
		setAvatars(ItemList);
			
		if (sortColumn != null)
			sortByColumn(ItemList, sortOrder, sortColumn);
		
		PagedListHolder<T> PageList = generatePageList(ItemList, items, page);
		
		ModelAndView mav = new ModelAndView(item.getClass().getSimpleName() + "List", "ItemList", PageList);
		mav.addObject("page", PageList.getPage());
		mav.addObject("maxPages", PageList.getPageCount());
		mav.addObject("searchName", searchName);
		mav.addObject("searchColumn", searchColumn);
		mav.addObject("sortColumn", sortColumn);
		mav.addObject("sortOrder", sortOrder);
		mav.addObject("itemsPerPage", items);
		mav.addObject("columnNames", columnNames);
		mav.addObject("itemClass", item);
		return mav;
	}
	
	public PagedListHolder<T> generatePageList(List<T> list, Integer size, Integer page)
	{
		PagedListHolder<T> PageList = new PagedListHolder<T>(list);
		PageList.setPageSize(size);
		if (page == null)
			PageList.setPage(0);
		else
			PageList.setPage(page);
		
		return PageList;
	}
	
	public void sortByColumn(List<T> List, String order, String column)
	{
		switch (column)
		{
		case "id": Collections.sort(List, (p1, p2) -> p1.getId().compareTo(p2.getId()));
			break;
		default:
			feedSort(List, column);
			break;
		}
		if (order.compareTo("descending") == 0)
			Collections.reverse(List);
	}
	
	public abstract void feedSort(List<T> List, String column);
	public abstract void feedSortLists(LinkedHashMap<String, String> Map);
	public abstract void setAvatars(List<T> List) throws Exception; 
	public abstract List<T> customList(Long id) throws InstantiationException, IllegalAccessException;
	public abstract void filterAllByString(String searchColumn ,String searchName, List<T> result);
}
