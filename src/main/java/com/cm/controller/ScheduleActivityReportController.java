package com.cm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.ScheduleActivityReport;
import com.cm.service.ScheduleActivityReportService;

@Controller
public class ScheduleActivityReportController extends BaseController<ScheduleActivityReport>{
	
	@Autowired
	private ScheduleActivityReportService sars;

	@RequestMapping(value="ScheduleActivityReport/customSave")
	public ModelAndView editActivityReport(HttpServletRequest request, @ModelAttribute ScheduleActivityReport scheduleActivityReport) throws ParseException{
		
		scheduleActivityReport.setIsFinished(true);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		scheduleActivityReport.setDate(sdf.parse(sdf.format(date)));
		
		sars.update(scheduleActivityReport);
		
		ModelAndView mav = new ModelAndView("redirect:getAll");
		return mav;
	}
	
	@Override
	public void feedSort(List<ScheduleActivityReport> List, String column) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void feedSortLists(LinkedHashMap<String, String> Map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAvatars(List<ScheduleActivityReport> List) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ScheduleActivityReport> filterAllByString(String searchColumn, String searchName, List<ScheduleActivityReport> result) {
		return result;
	}

	@Override
	public List<ScheduleActivityReport> customList(HttpServletRequest request)
			throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
