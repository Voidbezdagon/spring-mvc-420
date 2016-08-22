package com.cm.webservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

import com.cm.controller.PositionController;
import com.cm.entity.ScheduleActivity;
import com.cm.entity.ScheduleActivityReport;
import com.cm.entity.ScheduleReport;
import com.cm.service.ScheduleActivityReportService;
import com.cm.service.ScheduleActivityService;
import com.cm.service.ScheduleReportService;
import com.cm.service.ScheduleService;
import com.cm.util.ReportFormValidator;

@RestController
@EnableWebMvc
public class ScheduleReportWebService extends BaseWebService<ScheduleReport>{
	
	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	ScheduleActivityService saService;
	
	@Autowired
	ScheduleReportService srService;
	
	@Autowired
	ScheduleActivityReportService sarService;
	
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
	
	@CrossOrigin
	@RequestMapping(value = "/api/ScheduleReport/getAll/{id}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<ScheduleReport>> getAllScheduleReports(@PathVariable("id") long id) throws InstantiationException, IllegalAccessException
	{	
		return getAll();
	}
	 
	@CrossOrigin
	@RequestMapping(value = "/api/ScheduleReport/save", method = RequestMethod.POST)
	public ResponseEntity<ScheduleReport> createScheduleReport(@Valid @RequestBody ScheduleReport item, BindingResult bindingResult) throws InstantiationException, IllegalAccessException, ParseException
	{
		if (bindingResult.hasErrors()) {
			System.out.println("deba maznata pi6ka");	
			return new ResponseEntity<ScheduleReport>(item, HttpStatus.BAD_REQUEST);
		}
		
		//TODO: Report Validations Copy-Paste from Controller after we have Login ready.
		
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
		
		return save(item);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/ScheduleReport/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<ScheduleReport> getScheduleReportById(@PathVariable("id") long id) throws InstantiationException, IllegalAccessException
	{
		return getById(id);
	}
}
