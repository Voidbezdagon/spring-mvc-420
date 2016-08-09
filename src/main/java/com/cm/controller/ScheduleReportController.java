package com.cm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.Position;
import com.cm.entity.Schedule;
import com.cm.entity.ScheduleActivityReport;
import com.cm.entity.ScheduleReport;
import com.cm.entity.User;
import com.cm.service.PositionService;
import com.cm.service.ScheduleActivityReportService;
import com.cm.service.ScheduleActivityService;
import com.cm.service.ScheduleReportService;
import com.cm.service.ScheduleService;
import com.cm.util.ReportFormValidator;
import com.cm.util.ScheduleFormValidator;

@Controller
public class ScheduleReportController extends BaseController<ScheduleReport>{

	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	ScheduleActivityService saService;
	
	@Autowired
	ScheduleReportService srService;
	
	@Autowired
	ScheduleActivityReportService sarService;
	
	@Autowired
	PositionController positionController;
	
	@Autowired
	@Qualifier("reportValidator")
	private ReportFormValidator validator;
	
	 @InitBinder("item")
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    	binder.registerCustomEditor(List.class, "activityReports", new CustomCollectionEditor(List.class)
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

                  ScheduleActivityReport nub = new ScheduleActivityReport();
                  nub.setIsFinished(true);
                  try {
					nub.setScheduleActivity(saService.getById(id));
                  } catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
                  }

    			  return id != null ? nub : null;    			
              }
           });
        binder.setValidator(validator);
	}

	
	@RequestMapping(value="ScheduleReport/create")
	public ModelAndView createScheduleReport(HttpServletRequest request) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		User user = (User) request.getSession().getAttribute("LOGGED_USER");
		Schedule schedule = scheduleService.getById(Long.parseLong(request.getParameter("parentId")));
		
		if (user.getAdmin() != true)
		{
			//Check if user is in assigned team
			Boolean check = false;
			for (User u : schedule.getAssignedTeam().getUsers())
				if (u.getId() == user.getId())
					check = true;
			
			if (check == false)
				return new ModelAndView("redirect:/Schedule/getAll");
			
			//Check if user is highest position in team
			List<Position> teamPositions = new ArrayList<Position>();
			
			for (User u : schedule.getAssignedTeam().getUsers())
				teamPositions.add(u.getPosition());
			
			if (!user.getPosition().getLevel().equals(positionController.getHighestPosition(teamPositions)))
				return new ModelAndView("redirect:/Schedule/getAll");
		}
			//Check if a report already exists for the current date
			for (ScheduleReport sr : schedule.getReports())
				if (sdf.format(sr.getDate()).equals(sdf.format(new Date())))
					return new ModelAndView("redirect:/Schedule/getAll");
			
			
			//Check if today is the day of the schedule
			for (long i = sdf.parse(sdf.format(schedule.getStartDate())).getTime(); i <= sdf.parse(sdf.format(schedule.getEndDate())).getTime(); i = i + (schedule.getRecurringTime() * 86400000))
			{
				if (i == sdf.parse(sdf.format(new Date())).getTime())
				{
					request.getSession().setAttribute("parentSchedule", scheduleService.getById(Long.parseLong(request.getParameter("parentId"))));
					return create(request);
				}
			}
		return new ModelAndView("redirect:/Schedule/getAll");
	}
	
	@RequestMapping(value="ScheduleReport/save")
	public ModelAndView saveScheduleReport(@ModelAttribute("item") @Validated ScheduleReport item, BindingResult bindingResult, HttpServletRequest request) throws Exception
	{	
		if (bindingResult.hasErrors()) {
			if (item.getId() == null)
				return create(request);
			else
				return edit(request);
		}
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		item.setDate(sdf.parse(sdf.format(date)));
		

		Long srId = srService.create(item);
		
		if (item.getActivityReports() != null)
		{
			for (ScheduleActivityReport noob : item.getActivityReports())
			{
				noob.setScheduleReport(srService.getById(srId));
				noob.setDate(sdf.parse(sdf.format(date)));
				sarService.create(noob);
			}
		}
		request.getSession().removeAttribute("parentSchedule");
		return new ModelAndView("redirect:/Schedule/getAll");
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
