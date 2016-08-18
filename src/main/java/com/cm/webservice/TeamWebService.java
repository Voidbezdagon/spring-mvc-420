package com.cm.webservice;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cm.entity.Position;
import com.cm.entity.Schedule;
import com.cm.entity.ScheduleActivity;
import com.cm.entity.ScheduleReport;
import com.cm.entity.Team;
import com.cm.entity.User;
import com.cm.service.PositionService;
import com.cm.service.ScheduleActivityService;
import com.cm.service.ScheduleReportService;
import com.cm.service.ScheduleService;
import com.cm.service.TeamService;
import com.cm.service.UserService;
import com.cm.util.PositionFormValidator;
import com.cm.util.TeamFormValidator;

@RestController
@EnableWebMvc
public class TeamWebService extends BaseWebService<Team>{
	@Autowired
	ScheduleService sService;
	
	@Autowired
	ScheduleActivityService saService;
	
	@Autowired
	ScheduleReportService srService;
	
	@Autowired
	TeamService tService;
	
	@Autowired
	UserService uService;
	
	@Autowired
	@Qualifier("teamValidator")
	private TeamFormValidator validator;

	@InitBinder
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
	                 }                  try {
	                    return id != null ? uService.getById(id) : null;
	                 } catch (InstantiationException | IllegalAccessException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                 }
	                return null;  
	             }
	       });
		binder.setValidator(validator);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/Team/getAll", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Team>> getAllTeams() throws InstantiationException, IllegalAccessException
	{
		return getAll();
	}
	 
	@CrossOrigin
	@RequestMapping(value = "/api/Team/save", method = RequestMethod.POST)
	public ResponseEntity<Team> createTeam(@Valid @RequestBody Team item, BindingResult bindingResult) throws InstantiationException, IllegalAccessException
	{
		if (bindingResult.hasErrors()) {
			System.out.println("deba maznata pi6ka");	
			return new ResponseEntity<Team>(item, HttpStatus.BAD_REQUEST);
		}
		
		try 
		{
			return save(item);
		} 
		catch (Exception e) 
		{
			System.out.println("Duplicate Name");
			return new ResponseEntity<Team>(item, HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/Team/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<Team> getTeamById(@PathVariable("id") long id) throws InstantiationException, IllegalAccessException
	{
		return getById(id);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/Team/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Team> deleteTeam(@PathVariable("id") long id) throws InstantiationException, IllegalAccessException
	{
		List<Schedule> schedules = sService.getAll();
		for(Schedule s : schedules)
		{
			for (ScheduleActivity sa : s.getActivities())
			{
				saService.delete(sa.getId());
			}
			for (ScheduleReport sr : s.getReports())
			{
				srService.delete(sr.getId());
			}
			sService.delete(s.getId());
		}
		
		return delete(id);
	}
}
