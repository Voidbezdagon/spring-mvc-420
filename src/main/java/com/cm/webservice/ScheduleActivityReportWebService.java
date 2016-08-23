package com.cm.webservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cm.entity.ScheduleActivity;
import com.cm.entity.ScheduleActivityReport;
import com.cm.entity.User;
import com.cm.service.ScheduleActivityReportService;
import com.cm.service.UserService;

@RestController
@EnableWebMvc
public class ScheduleActivityReportWebService extends BaseWebService<ScheduleActivityReport>{
	
	@Autowired
	UserService uService;
	
	@Autowired
	private ScheduleActivityReportService sars;
	 
	@CrossOrigin
	@RequestMapping(value = "/api/ScheduleActivityReport/save", method = RequestMethod.POST)
	public ResponseEntity<ScheduleActivityReport> createScheduleActivityReport(@RequestBody ScheduleActivityReport item, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException, ParseException
	{
		if (key != null)
		{
			User user = uService.getUserByKey(key);
			if (user != null)
			{
				if (user.getAdmin() == true)
				{
					item.setIsFinished(true);
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date();
					
					item.setDate(sdf.parse(sdf.format(date)));
					
					sars.update(item);
					
					return new ResponseEntity<ScheduleActivityReport>(item, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<ScheduleActivityReport>(new ScheduleActivityReport(), HttpStatus.FORBIDDEN);
	}
}
